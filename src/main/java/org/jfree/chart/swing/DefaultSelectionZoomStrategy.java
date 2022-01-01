/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------------------------
 * DefaultSelectionZoomStrategy.java
 * ---------------------------------
 * (C) Copyright 2021-2022 by David Gilbert and Contributors.
 *
 * Original Author:  -;
 * Contributor(s):   David Gilbert;
 *              
 *
 */

package org.jfree.chart.swing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.jfree.chart.internal.SerialUtils;

/**
 * {@inheritDoc}
 *
 * This implementation can be extended to override default behavior.
 */
public class DefaultSelectionZoomStrategy implements SelectionZoomStrategy {

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
     */
    private transient Paint zoomOutlinePaint;

    /**
     * The zoom fill paint (should use transparency).
     */
    private transient Paint zoomFillPaint;

    public DefaultSelectionZoomStrategy() {
        zoomTriggerDistance = DEFAULT_ZOOM_TRIGGER_DISTANCE;
        this.zoomOutlinePaint = Color.BLUE;
        this.zoomFillPaint = new Color(0, 0, 255, 63);
    }

    @Override
    public boolean isActivated() {
        return zoomRectangle != null;
    }

    @Override
    public Point2D getZoomPoint() {
        return zoomPoint;
    }

    @Override
    public void setZoomPoint(Point2D zoomPoint) {
        this.zoomPoint = zoomPoint;
    }

    @Override
    public void setZoomTriggerDistance(int distance) {
        this.zoomTriggerDistance = distance;
    }

    @Override
    public int getZoomTriggerDistance() {
        return zoomTriggerDistance;
    }

    @Override
    public Paint getZoomOutlinePaint() {
        return zoomOutlinePaint;
    }

    @Override
    public void setZoomOutlinePaint(Paint paint) {
        this.zoomOutlinePaint = paint;
    }

    @Override
    public Paint getZoomFillPaint() {
        return zoomFillPaint;
    }

    @Override
    public void setZoomFillPaint(Paint paint) {
        this.zoomFillPaint = paint;
    }

    @Override
    public boolean getFillZoomRectangle() {
        return this.fillZoomRectangle;
    }

    @Override
    public void setFillZoomRectangle(boolean flag) {
        this.fillZoomRectangle = flag;
    }

    @Override
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

    @Override
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

    @Override
    public void reset() {
        zoomPoint = null;
        zoomRectangle = null;
    }

    @Override
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