/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * ------------------
 * SeriesDataset.java
 * ------------------
 * (C) Copyright 2000-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.general;

/**
 * The interface for a dataset consisting of one or many series of data.
 *
 * @see org.jfree.data.category.CategoryDataset
 * @see org.jfree.data.xy.IntervalXYDataset
 * @see org.jfree.data.xy.IntervalXYZDataset
 * @see org.jfree.data.xy.XYDataset
 * @see org.jfree.data.xy.XYZDataset
 */
public interface SeriesDataset extends Dataset {

    /**
     * Returns the number of series in the dataset.
     *
     * @return The series count.
     */
    int getSeriesCount();

    /**
     * Returns the key for a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     *
     * @return The key for the series.
     */
    Comparable getSeriesKey(int series);

    /**
     * Returns the index of the series with the specified key, or -1 if there
     * is no such series in the dataset.
     *
     * @param seriesKey  the series key ({@code null} permitted).
     *
     * @return The index, or -1.
     */
    int indexOf(Comparable seriesKey);

}
