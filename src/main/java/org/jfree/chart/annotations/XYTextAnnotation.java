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
 * ---------------------
 * XYTextAnnotation.java
 * ---------------------
 * (C) Copyright 2002-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (patch 2809117);
 *
 */

package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;

/**
 * A text annotation that can be placed at a particular (x, y) location on an
 * {@link XYPlot}.
 */
public class XYTextAnnotation extends AbstractXYAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2946063342782506328L;

    /** The default font. */
    public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN,
            10);

    /** The default paint. */
    public static final Paint DEFAULT_PAINT = Color.BLACK;

    /** The default text anchor. */
    public static final TextAnchor DEFAULT_TEXT_ANCHOR = TextAnchor.CENTER;

    /** The default rotation anchor. */
    public static final TextAnchor DEFAULT_ROTATION_ANCHOR = TextAnchor.CENTER;

    /** The default rotation angle. */
    public static final double DEFAULT_ROTATION_ANGLE = 0.0;

    /** The text. */
    private String text;

    /** The font. */
    private Font font;

    /** The paint. */
    private transient Paint paint;

    /** The x-coordinate. */
    private double x;

    /** The y-coordinate. */
    private double y;

    /** The text anchor (to be aligned with (x, y)). */
    private TextAnchor textAnchor;

    /** The rotation anchor. */
    private TextAnchor rotationAnchor;

    /** The rotation angle. */
    private double rotationAngle;

    /**
     * The background paint (possibly null).
     */
    private transient Paint backgroundPaint;

    /**
     * The flag that controls the visibility of the outline.
     */
    private boolean outlineVisible;

    /**
     * The outline paint (never null).
     */
    private transient Paint outlinePaint;

    /**
     * The outline stroke (never null).
     */
    private transient Stroke outlineStroke;

    /**
     * Creates a new annotation to be displayed at the given coordinates.  The
     * coordinates are specified in data space (they will be converted to
     * Java2D space for display).
     *
     * @param text  the text ({@code null} not permitted).
     * @param x  the x-coordinate (in data space, must be finite).
     * @param y  the y-coordinate (in data space, must be finite).
     */
    public XYTextAnnotation(String text, double x, double y) {
        super();
        Args.nullNotPermitted(text, "text");
        Args.requireFinite(x, "x");
        Args.requireFinite(y, "y");
        this.text = text;
        this.font = DEFAULT_FONT;
        this.paint = DEFAULT_PAINT;
        this.x = x;
        this.y = y;
        this.textAnchor = DEFAULT_TEXT_ANCHOR;
        this.rotationAnchor = DEFAULT_ROTATION_ANCHOR;
        this.rotationAngle = DEFAULT_ROTATION_ANGLE;

        // by default the outline and background won't be visible
        this.backgroundPaint = null;
        this.outlineVisible = false;
        this.outlinePaint = Color.BLACK;
        this.outlineStroke = new BasicStroke(0.5f);
    }

    /**
     * Returns the text for the annotation.
     *
     * @return The text (never {@code null}).
     *
     * @see #setText(String)
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text for the annotation.
     *
     * @param text  the text ({@code null} not permitted).
     *
     * @see #getText()
     */
    public void setText(String text) {
        Args.nullNotPermitted(text, "text");
        this.text = text;
        fireAnnotationChanged();
    }

    /**
     * Returns the font for the annotation.
     *
     * @return The font (never {@code null}).
     *
     * @see #setFont(Font)
     */
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getFont()
     */
    public void setFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.font = font;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint for the annotation.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setPaint(Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.paint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the text anchor.
     *
     * @return The text anchor (never {@code null}).
     *
     * @see #setTextAnchor(TextAnchor)
     */
    public TextAnchor getTextAnchor() {
        return this.textAnchor;
    }

    /**
     * Sets the text anchor (the point on the text bounding rectangle that is
     * aligned to the (x, y) coordinate of the annotation) and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param anchor  the anchor point ({@code null} not permitted).
     *
     * @see #getTextAnchor()
     */
    public void setTextAnchor(TextAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.textAnchor = anchor;
        fireAnnotationChanged();
    }

    /**
     * Returns the rotation anchor.
     *
     * @return The rotation anchor point (never {@code null}).
     *
     * @see #setRotationAnchor(TextAnchor)
     */
    public TextAnchor getRotationAnchor() {
        return this.rotationAnchor;
    }

    /**
     * Sets the rotation anchor point and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param anchor  the anchor ({@code null} not permitted).
     *
     * @see #getRotationAnchor()
     */
    public void setRotationAnchor(TextAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.rotationAnchor = anchor;
        fireAnnotationChanged();
    }

    /**
     * Returns the rotation angle.
     *
     * @return The rotation angle.
     *
     * @see #setRotationAngle(double)
     */
    public double getRotationAngle() {
        return this.rotationAngle;
    }

    /**
     * Sets the rotation angle and sends an {@link AnnotationChangeEvent} to
     * all registered listeners.  The angle is measured clockwise in radians.
     *
     * @param angle  the angle (in radians).
     *
     * @see #getRotationAngle()
     */
    public void setRotationAngle(double angle) {
        this.rotationAngle = angle;
        fireAnnotationChanged();
    }

    /**
     * Returns the x coordinate for the text anchor point (measured against the
     * domain axis).
     *
     * @return The x coordinate (in data space).
     *
     * @see #setX(double)
     */
    public double getX() {
        return this.x;
    }

    /**
     * Sets the x coordinate for the text anchor point (measured against the
     * domain axis) and sends an {@link AnnotationChangeEvent} to all
     * registered listeners.
     *
     * @param x  the x coordinate (in data space).
     *
     * @see #getX()
     */
    public void setX(double x) {
        Args.requireFinite(x, "x");
        this.x = x;
        fireAnnotationChanged();
    }

    /**
     * Returns the y coordinate for the text anchor point (measured against the
     * range axis).
     *
     * @return The y coordinate (in data space).
     *
     * @see #setY(double)
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the y coordinate for the text anchor point (measured against the
     * range axis) and sends an {@link AnnotationChangeEvent} to all registered
     * listeners.
     *
     * @param y  the y coordinate.
     *
     * @see #getY()
     */
    public void setY(double y) {
        Args.requireFinite(y, "y");
        this.y = y;
        fireAnnotationChanged();
    }

    /**
     * Returns the background paint for the annotation.
     *
     * @return The background paint (possibly {@code null}).
     *
     * @see #setBackgroundPaint(Paint)
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getBackgroundPaint()
     */
    public void setBackgroundPaint(Paint paint) {
        this.backgroundPaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the outline paint for the annotation.
     *
     * @return The outline paint (never {@code null}).
     *
     * @see #setOutlinePaint(Paint)
     */
    public Paint getOutlinePaint() {
        return this.outlinePaint;
    }

    /**
     * Sets the outline paint for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getOutlinePaint()
     */
    public void setOutlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.outlinePaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the outline stroke for the annotation.
     *
     * @return The outline stroke (never {@code null}).
     *
     * @see #setOutlineStroke(Stroke)
     */
    public Stroke getOutlineStroke() {
        return this.outlineStroke;
    }

    /**
     * Sets the outline stroke for the annotation and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getOutlineStroke()
     */
    public void setOutlineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.outlineStroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Returns the flag that controls whether or not the outline is drawn.
     *
     * @return A boolean.
     */
    public boolean isOutlineVisible() {
        return this.outlineVisible;
    }

    /**
     * Sets the flag that controls whether or not the outline is drawn and
     * sends an {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param visible  the new flag value.
     */
    public void setOutlineVisible(boolean visible) {
        this.outlineVisible = visible;
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
     * @param info  an optional info object that will be populated with
     *              entity information.
     */
    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
                     ValueAxis domainAxis, ValueAxis rangeAxis,
                     int rendererIndex, PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);

        float anchorX = (float) domainAxis.valueToJava2D(
                this.x, dataArea, domainEdge);
        float anchorY = (float) rangeAxis.valueToJava2D(
                this.y, dataArea, rangeEdge);

        if (orientation == PlotOrientation.HORIZONTAL) {
            float tempAnchor = anchorX;
            anchorX = anchorY;
            anchorY = tempAnchor;
        }

        g2.setFont(getFont());
        Shape hotspot = TextUtils.calculateRotatedStringBounds(
                getText(), g2, anchorX, anchorY, getTextAnchor(),
                getRotationAngle(), getRotationAnchor());
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(hotspot);
        }
        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, anchorX, anchorY,
                getTextAnchor(), getRotationAngle(), getRotationAnchor());
        if (this.outlineVisible) {
            g2.setStroke(this.outlineStroke);
            g2.setPaint(this.outlinePaint);
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
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYTextAnnotation)) {
            return false;
        }
        XYTextAnnotation that = (XYTextAnnotation) obj;
        if (!this.text.equals(that.text)) {
            return false;
        }
        if (this.x != that.x) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (!this.font.equals(that.font)) {
            return false;
        }
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (!this.rotationAnchor.equals(that.rotationAnchor)) {
            return false;
        }
        if (this.rotationAngle != that.rotationAngle) {
            return false;
        }
        if (!this.textAnchor.equals(that.textAnchor)) {
            return false;
        }
        if (this.outlineVisible != that.outlineVisible) {
            return false;
        }
        if (!PaintUtils.equal(this.backgroundPaint, that.backgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.outlinePaint, that.outlinePaint)) {
            return false;
        }
        if (!(this.outlineStroke.equals(that.outlineStroke))) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for the object.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;
        result = 37 * result + this.text.hashCode();
        result = 37 * result + this.font.hashCode();
        result = 37 * result + HashUtils.hashCodeForPaint(this.paint);
        long temp = Double.doubleToLongBits(this.x);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.y);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + this.textAnchor.hashCode();
        result = 37 * result + this.rotationAnchor.hashCode();
        temp = Double.doubleToLongBits(this.rotationAngle);
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
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.paint, stream);
        SerialUtils.writePaint(this.backgroundPaint, stream);
        SerialUtils.writePaint(this.outlinePaint, stream);
        SerialUtils.writeStroke(this.outlineStroke, stream);
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
        this.paint = SerialUtils.readPaint(stream);
        this.backgroundPaint = SerialUtils.readPaint(stream);
        this.outlinePaint = SerialUtils.readPaint(stream);
        this.outlineStroke = SerialUtils.readStroke(stream);
    }

}
