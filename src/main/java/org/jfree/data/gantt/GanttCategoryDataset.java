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
 * -------------------------
 * GanttCategoryDataset.java
 * -------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

 package org.jfree.data.gantt;

import org.jfree.data.category.IntervalCategoryDataset;

/**
 * An extension of the {@link IntervalCategoryDataset} interface that adds
 * support for multiple sub-intervals.
 */
public interface GanttCategoryDataset<R extends Comparable<R>, C extends Comparable<C>> 
        extends IntervalCategoryDataset<R, C> {

    /**
     * Returns the percent complete for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The percent complete.
     *
     * @see #getPercentComplete(Comparable, Comparable)
     */
    Number getPercentComplete(int row, int column);

    /**
     * Returns the percent complete for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The percent complete.
     *
     * @see #getPercentComplete(int, int)
     */
    Number getPercentComplete(R rowKey, C columnKey);

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The sub-interval count.
     *
     * @see #getSubIntervalCount(Comparable, Comparable)
     */
    int getSubIntervalCount(int row, int column);

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The sub-interval count.
     *
     * @see #getSubIntervalCount(int, int)
     */
    int getSubIntervalCount(R rowKey, C columnKey);

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval index (zero-based).
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(int, int, int)
     */
    Number getStartValue(int row, int column, int subinterval);

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(Comparable, Comparable, int)
     */
    Number getStartValue(R rowKey, C columnKey, int subinterval);

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(int, int, int)
     */
    Number getEndValue(int row, int column, int subinterval);

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(Comparable, Comparable, int)
     */
    Number getEndValue(R rowKey, C columnKey, int subinterval);

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The percent complete value (possibly {@code null}).
     *
     * @see #getPercentComplete(Comparable, Comparable, int)
     */
    Number getPercentComplete(int row, int column, int subinterval);

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The percent complete value (possibly {@code null}).
     *
     * @see #getPercentComplete(int, int, int)
     */
    Number getPercentComplete(R rowKey, C columnKey, int subinterval);

}
