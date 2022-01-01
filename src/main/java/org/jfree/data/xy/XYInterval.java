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
 * XYInterval.java
 * ---------------
 * (C) Copyright 2006-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.io.Serializable;

/**
 * An  xy-interval.  This class is used internally by the
 * {@link XYIntervalDataItem} class.
 */
public class XYInterval implements Serializable {

    /** The lower bound of the x-interval. */
    private double xLow;

    /** The upper bound of the y-interval. */
    private double xHigh;

    /** The y-value. */
    private double y;

    /** The lower bound of the y-interval. */
    private double yLow;

    /** The upper bound of the y-interval. */
    private double yHigh;

    /**
     * Creates a new instance of {@code XYInterval}.
     *
     * @param xLow  the lower bound of the x-interval.
     * @param xHigh  the upper bound of the y-interval.
     * @param y  the y-value.
     * @param yLow  the lower bound of the y-interval.
     * @param yHigh  the upper bound of the y-interval.
     */
    public XYInterval(double xLow, double xHigh, double y, double yLow,
            double yHigh) {
        this.xLow = xLow;
        this.xHigh = xHigh;
        this.y = y;
        this.yLow = yLow;
        this.yHigh = yHigh;
    }

    /**
     * Returns the lower bound of the x-interval.
     *
     * @return The lower bound of the x-interval.
     */
    public double getXLow() {
        return this.xLow;
    }

    /**
     * Returns the upper bound of the x-interval.
     *
     * @return The upper bound of the x-interval.
     */
    public double getXHigh() {
        return this.xHigh;
    }

    /**
     * Returns the y-value.
     *
     * @return The y-value.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the lower bound of the y-interval.
     *
     * @return The lower bound of the y-interval.
     */
    public double getYLow() {
        return this.yLow;
    }

    /**
     * Returns the upper bound of the y-interval.
     *
     * @return The upper bound of the y-interval.
     */
    public double getYHigh() {
        return this.yHigh;
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
        if (!(obj instanceof XYInterval)) {
            return false;
        }
        XYInterval that = (XYInterval) obj;
        if (this.xLow != that.xLow) {
            return false;
        }
        if (this.xHigh != that.xHigh) {
            return false;
        }
        if (this.y != that.y) {
            return false;
        }
        if (this.yLow != that.yLow) {
            return false;
        }
        if (this.yHigh != that.yHigh) {
            return false;
        }
        return true;
    }

}
