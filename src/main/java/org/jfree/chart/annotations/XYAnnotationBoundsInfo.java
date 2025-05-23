/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * ---------------------------
 * XYAnnotationBoundsInfo.java
 * ---------------------------
 * (C) Copyright 2009-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.annotations;

import org.jfree.data.Range;

/**
 * An interface that supplies information about the bounds of the annotation.
 */
public interface XYAnnotationBoundsInfo {

    /**
     * Returns a flag that determines whether the annotation's
     * bounds should be taken into account for auto-range calculations on
     * the axes that the annotation is plotted against.
     *
     * @return A boolean.
     */
    boolean getIncludeInDataBounds();

    /**
     * Returns the range of x-values (in data space) that the annotation
     * uses.
     *
     * @return The x-range.
     */
    Range getXRange();

    /**
     * Returns the range of y-values (in data space) that the annotation
     * uses.
     *
     * @return The y-range.
     */
    Range getYRange();

}
