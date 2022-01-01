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
 * ----------------------------
 * IntervalCategoryDataset.java
 * ----------------------------
 * (C) Copyright 2002-2020, by Eduard Martinescu and Contributors.
 *
 * Original Author:  Eduard Martinescu;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.data.category;

/**
 * A category dataset that defines a value range for each series/category
 * combination.
 */
public interface IntervalCategoryDataset<R extends Comparable<R>, 
        C extends Comparable<C>> extends CategoryDataset<R, C> {

    /**
     * Returns the start value for the interval for a given series and category.
     *
     * @param series  the series (zero-based index).
     * @param category  the category (zero-based index).
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(int, int)
     */
    Number getStartValue(int series, int category);

    /**
     * Returns the start value for the interval for a given series and category.
     *
     * @param series  the series key.
     * @param category  the category key.
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(Comparable, Comparable)
     */
    Number getStartValue(R series, C category);

    /**
     * Returns the end value for the interval for a given series and category.
     *
     * @param series  the series (zero-based index).
     * @param category  the category (zero-based index).
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(int, int)
     */
    Number getEndValue(int series, int category);

    /**
     * Returns the end value for the interval for a given series and category.
     *
     * @param series  the series key.
     * @param category  the category key.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(Comparable, Comparable)
     */
    Number getEndValue(R series, C category);

}
