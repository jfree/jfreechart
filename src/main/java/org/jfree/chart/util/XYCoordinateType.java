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
 * ---------------------
 * XYCoordinateType.java
 * ---------------------
 * (C) Copyright 2007, 2008 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 02-Feb-2007 : Version 1 (DG);
 * 03-Sep-2008 : Moved from experimental to main (DG);
 * 26-Nov-2018 : Made XYCoordinateType an enum (TH);
 *
 */

package org.jfree.chart.util;

/**
 * Represents several possible interpretations for an (x, y) coordinate.
 *
 * @since 1.0.11
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
