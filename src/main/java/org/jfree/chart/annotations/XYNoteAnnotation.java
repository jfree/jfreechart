/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * ---------------------
 * XYNoteAnnotation.java
 * ---------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert (as XYPointerAnnotation.java);
 * Contributor(s):   Yuri Blankenstein (copy to XYNoteAnnotation.java; for ESI TNO);
 *
 */
package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.HashUtils;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;

/**
 * An line and label that can be placed on an {@link XYPlot}.  The line is
 * drawn at a user-definable angle so that it points towards the (x, y)
 * location for the annotation.
 * <p>
 * The line length (and its offset from the (x, y) location) is controlled by
 * the tip radius and the base radius attributes.  Imagine two circles around
 * the (x, y) coordinate: the inner circle defined by the tip radius, and the
 * outer circle defined by the base radius.  Now, draw the line starting at
 * some point on the outer circle (the point is determined by the angle), with
 * the line tip being drawn at a corresponding point on the inner circle.
 */
public class XYNoteAnnotation extends XYTextAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4031161445009858551L;

    /** The default tip radius (in Java2D units). */
    public static final double DEFAULT_TIP_RADIUS = 0.0;

    /** The default base radius (in Java2D units). */
    public static final double DEFAULT_BASE_RADIUS = 30.0;

    /** The default label offset (in Java2D units). */
    public static final double DEFAULT_LABEL_OFFSET = 3.0;
    
    /** The default line stroke. */
    public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, new float[] {2.0f}, 0.0f);

    /** The default line stroke. */
    public static final Paint DEFAULT_BACKGROUND_PAINT = new Color(255, 255, 203);

    /** The default line stroke. */
    public static final Paint DEFAULT_OUTLINE_PAINT = new Color(255, 204, 102);

    /** The angle of the line's line (in radians). */
    private double angle;

    /**
     * The radius from the (x, y) point to the tip of the line (in Java2D
     * units).
     */
    private double tipRadius;

    /**
     * The radius from the (x, y) point to the start of the line line (in
     * Java2D units).
     */
    private double baseRadius;

    /** The line stroke. */
    private transient Stroke lineStroke;

    /** The line paint. */
    private transient Paint linePaint;

    /** The radius from the base point to the anchor point for the label. */
    private double labelOffset;

    /**
     * Creates a new label and line annotation.
     *
     * @param label  the label ({@code null} permitted).
     * @param x  the x-coordinate (measured against the chart's domain axis).
     * @param y  the y-coordinate (measured against the chart's range axis).
     * @param angle  the angle of the line's line (in radians).
     */
    public XYNoteAnnotation(String label, double x, double y, double angle) {
        super(label, x, y);
        this.angle = angle;
        this.tipRadius = DEFAULT_TIP_RADIUS;
        this.baseRadius = DEFAULT_BASE_RADIUS;
        this.labelOffset = DEFAULT_LABEL_OFFSET;
        this.lineStroke = DEFAULT_LINE_STROKE;
        this.linePaint = Color.BLACK;

        setBackgroundPaint(DEFAULT_BACKGROUND_PAINT);
        setOutlineVisible(true);
        setOutlinePaint(DEFAULT_OUTLINE_PAINT);
        setOutlineStroke(new BasicStroke(1.0f));
    }

    /**
     * Returns the angle of the line.
     *
     * @return The angle (in radians).
     *
     * @see #setAngle(double)
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Sets the angle of the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param angle  the angle (in radians).
     *
     * @see #getAngle()
     */
    public void setAngle(double angle) {
        this.angle = angle;
        fireAnnotationChanged();
    }

    /**
     * Returns the tip radius.
     *
     * @return The tip radius (in Java2D units).
     *
     * @see #setTipRadius(double)
     */
    public double getTipRadius() {
        return this.tipRadius;
    }

    /**
     * Sets the tip radius and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param radius  the radius (in Java2D units).
     *
     * @see #getTipRadius()
     */
    public void setTipRadius(double radius) {
        this.tipRadius = radius;
        fireAnnotationChanged();
    }

    /**
     * Returns the base radius.
     *
     * @return The base radius (in Java2D units).
     *
     * @see #setBaseRadius(double)
     */
    public double getBaseRadius() {
        return this.baseRadius;
    }

    /**
     * Sets the base radius and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param radius  the radius (in Java2D units).
     *
     * @see #getBaseRadius()
     */
    public void setBaseRadius(double radius) {
        this.baseRadius = radius;
        fireAnnotationChanged();
    }

    /**
     * Returns the label offset.
     *
     * @return The label offset (in Java2D units).
     *
     * @see #setLabelOffset(double)
     */
    public double getLabelOffset() {
        return this.labelOffset;
    }

    /**
     * Sets the label offset (from the line base, continuing in a straight
     * line, in Java2D units) and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param offset  the offset (in Java2D units).
     *
     * @see #getLabelOffset()
     */
    public void setLabelOffset(double offset) {
        this.labelOffset = offset;
        fireAnnotationChanged();
    }

    /**
     * Returns the stroke used to draw the line line.
     *
     * @return The line stroke (never {@code null}).
     *
     * @see #setLineStroke(Stroke)
     */
    public Stroke getLineStroke() {
        return this.lineStroke;
    }

    /**
     * Sets the stroke used to draw the line line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getLineStroke()
     */
    public void setLineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.lineStroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint used for the line.
     *
     * @return The line paint (never {@code null}).
     *
     * @see #setLinePaint(Paint)
     */
    public Paint getLinePaint() {
        return this.linePaint;
    }

    /**
     * Sets the paint used for the line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the line paint ({@code null} not permitted).
     *
     * @see #getLinePaint()
     */
    public void setLinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.linePaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Draws the annotation.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param rendererIndex  the renderer index.
     * @param info  the plot rendering info.
     */
    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
            ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, 
            PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);
        double j2DX = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
        double j2DY = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
        if (orientation == PlotOrientation.HORIZONTAL) {
            double temp = j2DX;
            j2DX = j2DY;
            j2DY = temp;
        }
        double startX = j2DX + Math.cos(this.angle) * this.baseRadius;
        double startY = j2DY + Math.sin(this.angle) * this.baseRadius;

        double endX = j2DX + Math.cos(this.angle) * this.tipRadius;
        double endY = j2DY + Math.sin(this.angle) * this.tipRadius;

        g2.setStroke(this.lineStroke);
        g2.setPaint(this.linePaint);
        Line2D line = new Line2D.Double(startX, startY, endX, endY);
        g2.draw(line);

        // draw the label
        double labelX = j2DX + Math.cos(this.angle) * (this.baseRadius
                + this.labelOffset);
        double labelY = j2DY + Math.sin(this.angle) * (this.baseRadius
                + this.labelOffset);
        g2.setFont(getFont());
        Shape hotspot = TextUtils.calculateRotatedStringBounds(
                getText(), g2, (float) labelX, (float) labelY, getTextAnchor(),
                getRotationAngle(), getRotationAnchor());
        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(hotspot);
        }
        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, (float) labelX,
                (float) labelY, getTextAnchor(), getRotationAngle(),
                getRotationAnchor());
        if (isOutlineVisible()) {
            g2.setStroke(getOutlineStroke());
            g2.setPaint(getOutlinePaint());
            g2.draw(hotspot);
        }

        String toolTip = getToolTipText();
        String url = getURL();
        if (toolTip != null || url != null) {
            addEntity(info, hotspot, rendererIndex, toolTip, url);
        }

    }

    /**
     * Tests this annotation for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYNoteAnnotation)) {
            return false;
        }
        XYNoteAnnotation that = (XYNoteAnnotation) obj;
        if (this.angle != that.angle) {
            return false;
        }
        if (this.tipRadius != that.tipRadius) {
            return false;
        }
        if (this.baseRadius != that.baseRadius) {
            return false;
        }
        if (!this.linePaint.equals(that.linePaint)) {
            return false;
        }
        if (!Objects.equals(this.lineStroke, that.lineStroke)) {
            return false;
        }
        if (this.labelOffset != that.labelOffset) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp = Double.doubleToLongBits(this.angle);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.tipRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.baseRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = result * 37 + HashUtils.hashCodeForPaint(this.linePaint);
        result = result * 37 + this.lineStroke.hashCode();
        temp = Double.doubleToLongBits(this.labelOffset);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the annotation can't be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.linePaint, stream);
        SerialUtils.writeStroke(this.lineStroke, stream);
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
        this.linePaint = SerialUtils.readPaint(stream);
        this.lineStroke = SerialUtils.readStroke(stream);
    }
}
