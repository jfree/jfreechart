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
 * LineFunction2D.java
 * -------------------
 * (C) Copyright 2002-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 01-Oct-2002 : Version 1 (DG);
 * 28-May-2009 : Added accessor methods for co-efficients, implemented
 *               equals() and hashCode(), and added Serialization support (DG);
 *
 */

package org.jfree.data.function;

import java.io.Serializable;

import org.jfree.chart.HashUtils;

/**
 * A function in the form y = a + bx.
 */
public class LineFunction2D implements Function2D, Serializable {

    /** The intercept. */
    private double a;

    /** The slope of the line. */
    private double b;

    /**
     * Constructs a new line function.
     *
     * @param a  the intercept.
     * @param b  the slope.
     */
    public LineFunction2D(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the 'a' coefficient that was specified in the constructor.
     *
     * @return The 'a' coefficient.
     *
     * @since 1.0.14
     */
    public double getIntercept() {
        return this.a;
    }

    /**
     * Returns the 'b' coefficient that was specified in the constructor.
     *
     * @return The 'b' coefficient.
     *
     * @since 1.0.14
     */
    public double getSlope() {
        return this.b;
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    @Override
    public double getValue(double x) {
        return this.a + this.b * x;
    }

    /**
     * Tests this function for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LineFunction2D)) {
            return false;
        }
        LineFunction2D that = (LineFunction2D) obj;
        if (this.a != that.a) {
            return false;
        }
        if (this.b != that.b) {
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
        int result = 29;
        result = HashUtils.hashCode(result, this.a);
        result = HashUtils.hashCode(result, this.b);
        return result;
    }
}
