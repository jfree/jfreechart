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
 * -----------------
 * ArcDialFrame.java
 * -----------------
 * (C) Copyright 2006-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;

/**
 * A standard frame for the {@link DialPlot} class.
 */
public class ArcDialFrame extends AbstractDialLayer implements DialFrame,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    static final long serialVersionUID = -4089176959553523499L;

    /**
     * The color used for the front of the panel.  This field is transient
     * because it requires special handling for serialization.
     */
    private transient Paint backgroundPaint;

    /**
     * The color used for the border around the window. This field is transient
     * because it requires special handling for serialization.
     */
    private transient Paint foregroundPaint;

    /**
     * The stroke for drawing the frame outline.  This field is transient
     * because it requires special handling for serialization.
     */
    private transient Stroke stroke;

    /**
     * The start angle.
     */
    private double startAngle;

    /**
     * The end angle.
     */
    private double extent;

    /** The inner radius, relative to the framing rectangle. */
    private double innerRadius;

    /** The outer radius, relative to the framing rectangle. */
    private double outerRadius;

    /**
     * Creates a new instance of {@code ArcDialFrame} that spans
     * 180 degrees.
     */
    public ArcDialFrame() {
        this(0, 180);
    }

    /**
     * Creates a new instance of {@code ArcDialFrame} that spans
     * the arc specified.
     *
     * @param startAngle  the startAngle (in degrees).
     * @param extent  the extent of the arc (in degrees, counter-clockwise).
     */
    public ArcDialFrame(double startAngle, double extent) {
        this.backgroundPaint = Color.GRAY;
        this.foregroundPaint = new Color(100, 100, 150);
        this.stroke = new BasicStroke(2.0f);
        this.innerRadius = 0.25;
        this.outerRadius = 0.75;
        this.startAngle = startAngle;
        this.extent = extent;
    }

    /**
     * Returns the background paint (never {@code null}).
     *
     * @return The background paint.
     *
     * @see #setBackgroundPaint(Paint)
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getBackgroundPaint()
     */
    public void setBackgroundPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.backgroundPaint = paint;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the foreground paint.
     *
     * @return The foreground paint (never {@code null}).
     *
     * @see #setForegroundPaint(Paint)
     */
    public Paint getForegroundPaint() {
        return this.foregroundPaint;
    }

    /**
     * Sets the foreground paint and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getForegroundPaint()
     */
    public void setForegroundPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.foregroundPaint = paint;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the stroke.
     *
     * @return The stroke (never {@code null}).
     *
     * @see #setStroke(Stroke)
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getStroke()
     */
    public void setStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.stroke = stroke;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the inner radius, relative to the framing rectangle.
     *
     * @return The inner radius.
     *
     * @see #setInnerRadius(double)
     */
    public double getInnerRadius() {
        return this.innerRadius;
    }

    /**
     * Sets the inner radius and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param radius  the inner radius.
     *
     * @see #getInnerRadius()
     */
    public void setInnerRadius(double radius) {
        if (radius < 0.0) {
            throw new IllegalArgumentException("Negative 'radius' argument.");
        }
        this.innerRadius = radius;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the outer radius, relative to the framing rectangle.
     *
     * @return The outer radius.
     *
     * @see #setOuterRadius(double)
     */
    public double getOuterRadius() {
        return this.outerRadius;
    }

    /**
     * Sets the outer radius and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param radius  the outer radius.
     *
     * @see #getOuterRadius()
     */
    public void setOuterRadius(double radius) {
        if (radius < 0.0) {
            throw new IllegalArgumentException("Negative 'radius' argument.");
        }
        this.outerRadius = radius;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the start angle.
     *
     * @return The start angle.
     *
     * @see #setStartAngle(double)
     */
    public double getStartAngle() {
        return this.startAngle;
    }

    /**
     * Sets the start angle and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param angle  the angle.
     *
     * @see #getStartAngle()
     */
    public void setStartAngle(double angle) {
        this.startAngle = angle;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the extent.
     *
     * @return The extent.
     *
     * @see #setExtent(double)
     */
    public double getExtent() {
        return this.extent;
    }

    /**
     * Sets the extent and sends a {@link DialLayerChangeEvent} to
     * all registered listeners.
     *
     * @param extent  the extent.
     *
     * @see #getExtent()
     */
    public void setExtent(double extent) {
        this.extent = extent;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the shape for the window for this dial.  Some dial layers will
     * request that their drawing be clipped within this window.
     *
     * @param frame  the reference frame ({@code null} not permitted).
     *
     * @return The shape of the dial's window.
     */
    @Override
    public Shape getWindow(Rectangle2D frame) {

        Rectangle2D innerFrame = DialPlot.rectangleByRadius(frame,
                this.innerRadius, this.innerRadius);
        Rectangle2D outerFrame = DialPlot.rectangleByRadius(frame,
                this.outerRadius, this.outerRadius);
        Arc2D inner = new Arc2D.Double(innerFrame, this.startAngle,
                this.extent, Arc2D.OPEN);
        Arc2D outer = new Arc2D.Double(outerFrame, this.startAngle
                + this.extent, -this.extent, Arc2D.OPEN);
        GeneralPath p = new GeneralPath();
        Point2D point1 = inner.getStartPoint();
        p.moveTo((float) point1.getX(), (float) point1.getY());
        p.append(inner, true);
        p.append(outer, true);
        p.closePath();
        return p;

    }

    /**
     * Returns the outer window.
     *
     * @param frame  the frame.
     *
     * @return The outer window.
     */
    protected Shape getOuterWindow(Rectangle2D frame) {
        double radiusMargin = 0.02;
        double angleMargin = 1.5;
        Rectangle2D innerFrame = DialPlot.rectangleByRadius(frame,
                this.innerRadius - radiusMargin, this.innerRadius
                - radiusMargin);
        Rectangle2D outerFrame = DialPlot.rectangleByRadius(frame,
                this.outerRadius + radiusMargin, this.outerRadius
                + radiusMargin);
        Arc2D inner = new Arc2D.Double(innerFrame, this.startAngle
                - angleMargin, this.extent + 2 * angleMargin, Arc2D.OPEN);
        Arc2D outer = new Arc2D.Double(outerFrame, this.startAngle
                + angleMargin + this.extent, -this.extent - 2 * angleMargin,
                Arc2D.OPEN);
        GeneralPath p = new GeneralPath();
        Point2D point1 = inner.getStartPoint();
        p.moveTo((float) point1.getX(), (float) point1.getY());
        p.append(inner, true);
        p.append(outer, true);
        p.closePath();
        return p;
    }

    /**
     * Draws the frame.
     *
     * @param g2  the graphics target.
     * @param plot  the plot.
     * @param frame  the dial's reference frame.
     * @param view  the dial's view rectangle.
     */
    @Override
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame,
            Rectangle2D view) {

        Shape window = getWindow(frame);
        Shape outerWindow = getOuterWindow(frame);

        Area area1 = new Area(outerWindow);
        Area area2 = new Area(window);
        area1.subtract(area2);
        g2.setPaint(Color.lightGray);
        g2.fill(area1);

        g2.setStroke(this.stroke);
        g2.setPaint(this.foregroundPaint);
        g2.draw(window);
        g2.draw(outerWindow);

    }

    /**
     * Returns {@code false} to indicate that this dial layer is not
     * clipped to the dial window.
     *
     * @return {@code false}.
     */
    @Override
    public boolean isClippedToWindow() {
        return false;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
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
        if (!(obj instanceof ArcDialFrame)) {
            return false;
        }
        ArcDialFrame that = (ArcDialFrame) obj;
        if (!PaintUtils.equal(this.backgroundPaint, that.backgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.foregroundPaint, that.foregroundPaint)) {
            return false;
        }
        if (this.startAngle != that.startAngle) {
            return false;
        }
        if (this.extent != that.extent) {
            return false;
        }
        if (this.innerRadius != that.innerRadius) {
            return false;
        }
        if (this.outerRadius != that.outerRadius) {
            return false;
        }
        if (!this.stroke.equals(that.stroke)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;
        long temp = Double.doubleToLongBits(this.startAngle);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.extent);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.innerRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.outerRadius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + HashUtils.hashCodeForPaint(
                this.backgroundPaint);
        result = 37 * result + HashUtils.hashCodeForPaint(
                this.foregroundPaint);
        result = 37 * result + this.stroke.hashCode();
        return result;
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if any attribute of this instance
     *     cannot be cloned.
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
        SerialUtils.writePaint(this.backgroundPaint, stream);
        SerialUtils.writePaint(this.foregroundPaint, stream);
        SerialUtils.writeStroke(this.stroke, stream);
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
        this.backgroundPaint = SerialUtils.readPaint(stream);
        this.foregroundPaint = SerialUtils.readPaint(stream);
        this.stroke = SerialUtils.readStroke(stream);
    }

}
