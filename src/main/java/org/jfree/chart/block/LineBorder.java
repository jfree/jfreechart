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
 * ---------------
 * LineBorder.java
 * ---------------
 * (C) Copyright 2007-2021, by Christo Zietsman and Contributors.
 *
 * Original Author:  Christo Zietsman;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.block;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.SerialUtils;

/**
 * A line border for any {@link AbstractBlock}.
 */
public class LineBorder implements BlockFrame, Serializable {

    /** For serialization. */
    static final long serialVersionUID = 4630356736707233924L;

    /** The line color. */
    private transient Paint paint;

    /** The line stroke. */
    private transient Stroke stroke;

    /** The insets. */
    private final RectangleInsets insets;

    /**
     * Creates a default border.
     */
    public LineBorder() {
        this(Color.BLACK, new BasicStroke(1.0f), new RectangleInsets(1.0, 1.0,
                1.0, 1.0));
    }

    /**
     * Creates a new border with the specified color.
     *
     * @param paint  the color ({@code null} not permitted).
     * @param stroke  the border stroke ({@code null} not permitted).
     * @param insets  the insets ({@code null} not permitted).
     */
    public LineBorder(Paint paint, Stroke stroke, RectangleInsets insets) {
        Args.nullNotPermitted(paint, "paint");
        Args.nullNotPermitted(stroke, "stroke");
        Args.nullNotPermitted(insets, "insets");
        this.paint = paint;
        this.stroke = stroke;
        this.insets = insets;
    }

    /**
     * Returns the paint.
     *
     * @return The paint (never {@code null}).
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Returns the insets.
     *
     * @return The insets (never {@code null}).
     */
    @Override
    public RectangleInsets getInsets() {
        return this.insets;
    }

    /**
     * Returns the stroke.
     *
     * @return The stroke (never {@code null}).
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Draws the border by filling in the reserved space (in black).
     *
     * @param g2  the graphics device.
     * @param area  the area.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        double w = area.getWidth();
        double h = area.getHeight();
        // if the area has zero height or width, we shouldn't draw anything
        if (w <= 0.0 || h <= 0.0) {
            return;
        }
        double t = this.insets.calculateTopInset(h);
        double b = this.insets.calculateBottomInset(h);
        double l = this.insets.calculateLeftInset(w);
        double r = this.insets.calculateRightInset(w);
        double x = area.getX();
        double y = area.getY();
        double x0 = x + l / 2.0;
        double x1 = x + w - r / 2.0;
        double y0 = y + h - b / 2.0;
        double y1 = y + t / 2.0;
        g2.setPaint(getPaint());
        g2.setStroke(getStroke());
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        Line2D line = new Line2D.Double();
        if (t > 0.0) {
            line.setLine(x0, y1, x1, y1);
            g2.draw(line);
        }
        if (b > 0.0) {
            line.setLine(x0, y0, x1, y0);
            g2.draw(line);
        }
        if (l > 0.0) {
            line.setLine(x0, y0, x0, y1);
            g2.draw(line);
        }
        if (r > 0.0) {
            line.setLine(x1, y0, x1, y1);
            g2.draw(line);
        }
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }

    /**
     * Tests this border for equality with an arbitrary instance.
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
        if (!(obj instanceof LineBorder)) {
            return false;
        }
        LineBorder that = (LineBorder) obj;
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (!Objects.equals(this.stroke, that.stroke)) {
            return false;
        }
        if (!this.insets.equals(that.insets)) {
            return false;
        }
        return true;
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
        this.paint = SerialUtils.readPaint(stream);
        this.stroke = SerialUtils.readStroke(stream);
    }
}


