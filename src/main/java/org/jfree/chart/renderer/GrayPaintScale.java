/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * -------------------
 * GrayPaintScale.java
 * -------------------
 * (C) Copyright 2006-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Jul-2006 : Version 1 (DG);
 * 31-Jan-2007 : Renamed min and max to lowerBound and upperBound (DG);
 * 26-Sep-2007 : Fixed bug 1767315, problem in getPaint() method (DG);
 * 29-Jan-2009 : Added alpha transparency field and hashCode() method (DG);
 *
 */

package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.Paint;
import java.io.Serializable;

import org.jfree.chart.HashUtils;
import org.jfree.chart.util.PublicCloneable;

/**
 * A paint scale that returns shades of gray.
 *
 * @since 1.0.4
 */
public class GrayPaintScale
        implements PaintScale, PublicCloneable, Serializable {

    /** The lower bound. */
    private double lowerBound;

    /** The upper bound. */
    private double upperBound;

    /**
     * The alpha transparency (0-255).
     *
     * @since 1.0.13
     */
    private int alpha;

    /**
     * Creates a new {@code GrayPaintScale} instance with default values.
     */
    public GrayPaintScale() {
        this(0.0, 1.0);
    }

    /**
     * Creates a new paint scale for values in the specified range.
     *
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     *
     * @throws IllegalArgumentException if {@code lowerBound} is not
     *       less than {@code upperBound}.
     */
    public GrayPaintScale(double lowerBound, double upperBound) {
        this(lowerBound, upperBound, 255);
    }

    /**
     * Creates a new paint scale for values in the specified range.
     *
     * @param lowerBound  the lower bound.
     * @param upperBound  the upper bound.
     * @param alpha  the alpha transparency (0-255).
     *
     * @throws IllegalArgumentException if {@code lowerBound} is not
     *       less than {@code upperBound}, or {@code alpha} is not in
     *       the range 0 to 255.
     *
     * @since 1.0.13
     */
    public GrayPaintScale(double lowerBound, double upperBound, int alpha) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException(
                    "Requires lowerBound < upperBound.");
        }
        if (alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException(
                    "Requires alpha in the range 0 to 255.");

        }
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.alpha = alpha;
    }

    /**
     * Returns the lower bound.
     *
     * @return The lower bound.
     *
     * @see #getUpperBound()
     */
    @Override
    public double getLowerBound() {
        return this.lowerBound;
    }

    /**
     * Returns the upper bound.
     *
     * @return The upper bound.
     *
     * @see #getLowerBound()
     */
    @Override
    public double getUpperBound() {
        return this.upperBound;
    }

    /**
     * Returns the alpha transparency that was specified in the constructor.
     * 
     * @return The alpha transparency (in the range 0 to 255).
     * 
     * @since 1.0.13
     */
    public int getAlpha() {
        return this.alpha;
    }

    /**
     * Returns a paint for the specified value.
     *
     * @param value  the value (must be within the range specified by the
     *         lower and upper bounds for the scale).
     *
     * @return A paint for the specified value.
     */
    @Override
    public Paint getPaint(double value) {
        double v = Math.max(value, this.lowerBound);
        v = Math.min(v, this.upperBound);
        int g = (int) ((v - this.lowerBound) / (this.upperBound
                - this.lowerBound) * 255.0);
        // FIXME:  it probably makes sense to allocate an array of 256 Colors
        // and lazily populate this array...
        return new Color(g, g, g, this.alpha);
    }

    /**
     * Tests this {@code GrayPaintScale} instance for equality with an
     * arbitrary object.  This method returns {@code true} if and only
     * if:
     * <ul>
     * <li>{@code obj} is not {@code null};</li>
     * <li>{@code obj} is an instance of {@code GrayPaintScale};</li>
     * </ul>
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
        if (!(obj instanceof GrayPaintScale)) {
            return false;
        }
        GrayPaintScale that = (GrayPaintScale) obj;
        if (this.lowerBound != that.lowerBound) {
            return false;
        }
        if (this.upperBound != that.upperBound) {
            return false;
        }
        if (this.alpha != that.alpha) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = HashUtils.hashCode(hash, this.lowerBound);
        hash = HashUtils.hashCode(hash, this.upperBound);
        hash = 43 * hash + this.alpha;
        return hash;
    }

    /**
     * Returns a clone of this {@code GrayPaintScale} instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning this
     *     instance.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
