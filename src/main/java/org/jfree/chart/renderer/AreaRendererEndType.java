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
 * ------------------------
 * AreaRendererEndType.java
 * ------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 29-Apr-2004 : Version 1 (DG);
 * 08-Jul-2018 : Made AreaRendererEndType an enum (TH);
 *
 */

package org.jfree.chart.renderer;

/**
 * An enumeration of the 'end types' for an area renderer.
 */
public enum AreaRendererEndType {

    /**
     * The area tapers from the first or last value down to zero.
     */
    TAPER,

    /**
     * The area is truncated at the first or last value.
     */
    TRUNCATE,

    /**
     * The area is leveled at the first or last value.
     */
    LEVEL
}
