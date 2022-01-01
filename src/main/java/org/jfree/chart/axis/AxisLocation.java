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
 * AxisLocation.java
 * -----------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Nick Guenther, Tracy Hiltbrand;
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.internal.Args;

/**
 * Used to indicate the location of an axis on a 2D plot, prior to knowing the
 * orientation of the plot.
 */
public enum AxisLocation {

    /** Axis at the top or left. */
    TOP_OR_LEFT,

    /** Axis at the top or right. */
    TOP_OR_RIGHT,

    /** Axis at the bottom or left. */
    BOTTOM_OR_LEFT,

    /** Axis at the bottom or right. */
    BOTTOM_OR_RIGHT;

    /**
     * Returns the location that is opposite to this location.
     *
     * @return The opposite location.
     */
    public AxisLocation getOpposite() {
        return getOpposite(this);
    }

    /**
     * Returns the location that is opposite to the supplied location.
     *
     * @param location  the location ({@code null} not permitted).
     *
     * @return The opposite location.
     */
    public static AxisLocation getOpposite(AxisLocation location) {
        Args.nullNotPermitted(location, "location");
        switch (location) {
            case TOP_OR_LEFT:
                return AxisLocation.BOTTOM_OR_RIGHT;
            case TOP_OR_RIGHT:
                return AxisLocation.BOTTOM_OR_LEFT;
            case BOTTOM_OR_LEFT:
                return AxisLocation.TOP_OR_RIGHT;
            case BOTTOM_OR_RIGHT:
                return AxisLocation.TOP_OR_LEFT;
            default:
                throw new IllegalStateException("AxisLocation not recognised.");
        }
    }

}
