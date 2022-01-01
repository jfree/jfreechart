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
 * Changes
 * -------
 * 08-Jul-2018 : Made TextBlockAnchor an enum (TH);
 * 
 */

package org.jfree.chart.text;

/**
 * Used to indicate the position of an anchor point for a text block.
 */
public enum TextBlockAnchor {

    /** Top/left. */
    TOP_LEFT,

    /** Top/center. */
    TOP_CENTER,

    /** Top/right. */
    TOP_RIGHT,

    /** Middle/left. */
    CENTER_LEFT,

    /** Middle/center. */
    CENTER,

    /** Middle/right. */
    CENTER_RIGHT,

    /** Bottom/left. */
    BOTTOM_LEFT,

    /** Bottom/center. */
    BOTTOM_CENTER,

    /** Bottom/right. */
    BOTTOM_RIGHT
}

