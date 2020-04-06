package org.jfree.chart;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.util.Args;
import org.jfree.chart.util.SerialUtils;

/**
 * A strategy for zooming plots on the chart panel by selecting a smaller region on the initial screen.
 * This implementation can be extended to override default behavior.
 * See {@link ChartPanel#setSelectionZoomStrategy(DefaultSelectionZoomStrategy)}
 */
public class DefaultSelectionZoomStrategy implements Serializable {

    private static final long serialVersionUID = -8042265475645652131L;

    /** The minimum size required to perform a zoom on a rectangle */
    public static final int DEFAULT_ZOOM_TRIGGER_DISTANCE = 10;

    /**
     * The zoom rectangle starting point (selected by the user with a mouse
     * click).  This is a point on the screen, not the chart (which may have
     * been scaled up or down to fit the panel).
     */
    protected Point2D zoomPoint = null;

    /**
     * The zoom rectangle (selected by the user with the mouse).
     */
    protected transient Rectangle2D zoomRectangle = null;

    /**
     * Controls if the zoom rectangle is drawn as an outline or filled.
     */
    private boolean fillZoomRectangle = true;

    /**
     * The minimum distance required to drag the mouse to trigger a zoom.
     */
    private int zoomTriggerDistance;

    /**
     * The paint used to draw the zoom rectangle outline.
     *
     * @since 1.0.13
     */
    private transient Paint zoomOutlinePaint;

    /**
     * The zoom fill paint (should use transparency).
     *
     * @since 1.0.13
     */
    private transient Paint zoomFillPaint;

    public DefaultSelectionZoomStrategy() {
        zoomTriggerDistance = DEFAULT_ZOOM_TRIGGER_DISTANCE;
        this.zoomOutlinePaint = Color.BLUE;
        this.zoomFillPaint = new Color(0, 0, 255, 63);
    }

    /**
     * If controller currently tracking zoom rectangle
     *
     * @return {@code true} if zoomRectangle exists for this controller
     *          and {@code false} otherwise
     */
    public boolean isActivated() {
        return zoomRectangle != null;
    }

    public Point2D getZoomPoint() {
        return zoomPoint;
    }

    public void setZoomPoint(Point2D zoomPoint) {
        this.zoomPoint = zoomPoint;
    }

    /**
     * Sets the zoom trigger distance.  This controls how far the mouse must
     * move before a zoom action is triggered.
     *
     * @param distance  the distance (in Java2D units).
     */
    public void setZoomTriggerDistance(int distance) {
        this.zoomTriggerDistance = distance;
    }

    /**
     * Returns the zoom trigger distance.  This controls how far the mouse must
     * move before a zoom action is triggered.
     *
     * @return The distance (in Java2D units).
     */
    public int getZoomTriggerDistance() {
        return zoomTriggerDistance;
    }

    /**
     * Returns the zoom rectangle outline paint.
     *
     * @return The zoom rectangle outline paint (never {@code null}).
     *
     * @see #setZoomOutlinePaint(java.awt.Paint)
     * @see #setFillZoomRectangle(boolean)
     *
     * @since 1.0.13
     */
    public Paint getZoomOutlinePaint() {
        return zoomOutlinePaint;
    }

    /**
     * Sets the zoom rectangle outline paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getZoomOutlinePaint()
     * @see #getFillZoomRectangle()
     *
     * @since 1.0.13
     */
    public void setZoomOutlinePaint(Paint paint) {
        this.zoomOutlinePaint = paint;
    }

    /**
     * Returns the zoom rectangle fill paint.
     *
     * @return The zoom rectangle fill paint (never {@code null}).
     *
     * @see #setZoomFillPaint(java.awt.Paint)
     * @see #setFillZoomRectangle(boolean)
     *
     * @since 1.0.13
     */
    public Paint getZoomFillPaint() {
        return zoomFillPaint;
    }

    /**
     * Sets the zoom rectangle fill paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getZoomFillPaint()
     * @see #getFillZoomRectangle()
     *
     * @since 1.0.13
     */
    public void setZoomFillPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.zoomFillPaint = paint;
    }

    /**
     * Returns the flag that controls whether or not the zoom rectangle is
     * filled when drawn.
     *
     * @return A boolean.
     */
    public boolean getFillZoomRectangle() {
        return this.fillZoomRectangle;
    }

    /**
     * A flag that controls how the zoom rectangle is drawn.
     *
     * @param flag  {@code true} instructs to fill the rectangle on
     *              zoom, otherwise it will be outlined.
     */
    public void setFillZoomRectangle(boolean flag) {
        this.fillZoomRectangle = flag;
    }

    /**
     * Updates zoom rectangle with new mouse position

     * @param e mouse event
     * @param hZoom if horizontal zoom allowed
     * @param vZoom if vertical zoom allowed
     * @param scaledDataArea plot area in screen coordinates
     */
    public void updateZoomRectangleSelection(MouseEvent e, boolean hZoom, boolean vZoom, Rectangle2D scaledDataArea) {
        if (hZoom && vZoom) {
            // selected rectangle shouldn't extend outside the data area...
            double xMax = Math.min(e.getX(), scaledDataArea.getMaxX());
            double yMax = Math.min(e.getY(), scaledDataArea.getMaxY());
            zoomRectangle = new Rectangle2D.Double(
                    zoomPoint.getX(), zoomPoint.getY(),
                    xMax - zoomPoint.getX(), yMax - zoomPoint.getY());
        }
        else if (hZoom) {
            double xMax = Math.min(e.getX(), scaledDataArea.getMaxX());
            zoomRectangle = new Rectangle2D.Double(
                    zoomPoint.getX(), scaledDataArea.getMinY(),
                    xMax - zoomPoint.getX(), scaledDataArea.getHeight());
        }
        else if (vZoom) {
            double yMax = Math.min(e.getY(), scaledDataArea.getMaxY());
            zoomRectangle = new Rectangle2D.Double(
                    scaledDataArea.getMinX(), zoomPoint.getY(),
                    scaledDataArea.getWidth(), yMax - zoomPoint.getY());
        }
    }

    /**
     * Creates and returns current zoom rectangle
     *
     * @param hZoom if horizontal zoom acceptable
     * @param vZoom if vertical zoom acceptable
     * @param screenDataArea rectangle that describes plot on the screen
     *
     * @return rectangle in java2d screen coordinates selected by user
     */
    public Rectangle2D getZoomRectangle(boolean hZoom, boolean vZoom, Rectangle2D screenDataArea) {
        double x, y, w, h;
        double maxX = screenDataArea.getMaxX();
        double maxY = screenDataArea.getMaxY();
        // for mouseReleased event, (horizontalZoom || verticalZoom)
        // will be true, so we can just test for either being false;
        // otherwise both are true
        if (!vZoom) {
            x = zoomPoint.getX();
            y = screenDataArea.getMinY();
            w = Math.min(zoomRectangle.getWidth(),
                    maxX - zoomPoint.getX());
            h = screenDataArea.getHeight();
        }
        else if (!hZoom) {
            x = screenDataArea.getMinX();
            y = zoomPoint.getY();
            w = screenDataArea.getWidth();
            h = Math.min(zoomRectangle.getHeight(),
                    maxY - zoomPoint.getY());
        }
        else {
            x = zoomPoint.getX();
            y = zoomPoint.getY();
            w = Math.min(zoomRectangle.getWidth(),
                    maxX - zoomPoint.getX());
            h = Math.min(zoomRectangle.getHeight(),
                    maxY - zoomPoint.getY());
        }
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Removes zoom rectangle
     */
    public void reset() {
        zoomPoint = null;
        zoomRectangle = null;
    }

    /**
     * Draws zoom rectangle (if present).
     * The drawing is performed in XOR mode, therefore
     * when this method is called twice in a row,
     * the second call will completely restore the state
     * of the canvas.
     *
     *  @param g2 the graphics device.
     * @param xor  use XOR for drawing?
     */
    public void drawZoomRectangle(Graphics2D g2, boolean xor) {
        if (zoomRectangle != null) {
            if (xor) {
                 // Set XOR mode to draw the zoom rectangle
                g2.setXORMode(Color.GRAY);
            }
            if (fillZoomRectangle) {
                g2.setPaint(zoomFillPaint);
                g2.fill(zoomRectangle);
            }
            else {
                g2.setPaint(zoomOutlinePaint);
                g2.draw(zoomRectangle);
            }
            if (xor) {
                // Reset to the default 'overwrite' mode
                g2.setPaintMode();
            }
        }
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.zoomFillPaint, stream);
        SerialUtils.writePaint(this.zoomOutlinePaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.zoomFillPaint = SerialUtils.readPaint(stream);
        this.zoomOutlinePaint = SerialUtils.readPaint(stream);
    }
}