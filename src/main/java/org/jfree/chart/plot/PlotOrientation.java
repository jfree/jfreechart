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
 * --------------------
 * PlotOrientation.java
 * --------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

/**
 * Used to indicate the orientation (horizontal or vertical) of a 2D plot.
 * It is the direction of the y-axis that is the determinant (a conventional
 * plot has a vertical y-axis).
 */
public enum PlotOrientation {

    /** For a plot where the range axis is horizontal. */
    HORIZONTAL,

    /** For a plot where the range axis is vertical. */
    VERTICAL;

    /**
     * Returns {@code true} if this orientation is {@code HORIZONTAL},
     * and {@code false} otherwise.  
     * 
     * @return A boolean.
     */
    public boolean isHorizontal() {
        return this.equals(PlotOrientation.HORIZONTAL);
    }
    
    /**
     * Returns {@code true} if this orientation is {@code VERTICAL},
     * and {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isVertical() {
        return this.equals(PlotOrientation.VERTICAL);
    }

}
