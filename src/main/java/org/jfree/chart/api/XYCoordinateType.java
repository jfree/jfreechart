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
 * XYCoordinateType.java
 * ---------------------
 * (C) Copyright 2007-2022 by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.api;

/**
 * Represents several possible interpretations for an (x, y) coordinate.
 */
public enum XYCoordinateType {

    /** The (x, y) coordinates represent a point in the data space. */
    DATA,

    /**
     * The (x, y) coordinates represent a relative position in the data space.
     * In this case, the values should be in the range (0.0 to 1.0).
     */
    RELATIVE,

    /**
     * The (x, y) coordinates represent indices in a dataset.
     * In this case, the values should be in the range (0.0 to 1.0).
     */
    INDEX

}
