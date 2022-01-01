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
 * --------------
 * XYDataset.java
 * --------------
 * (C) Copyright 2000-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.data.DomainOrder;
import org.jfree.data.general.SeriesDataset;

/**
 * An interface through which data in the form of (x, y) items can be accessed.
 */
public interface XYDataset<S extends Comparable<S>> extends SeriesDataset<S> {

    /**
     * Returns the order of the domain (or X) values returned by the dataset.
     *
     * @return The order (never {@code null}).
     */
    DomainOrder getDomainOrder();

    /**
     * Returns the number of items in a series.
     * <br><br>
     * It is recommended that classes that implement this method should throw
     * an {@code IllegalArgumentException} if the {@code series}
     * argument is outside the specified range.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     *
     * @return The item count.
     */
    int getItemCount(int series);

    /**
     * Returns the x-value for an item within a series.  The x-values may or
     * may not be returned in ascending order, that is up to the class
     * implementing the interface.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The x-value (never {@code null}).
     */
    Number getX(int series, int item);

    /**
     * Returns the x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The x-value.
     */
    double getXValue(int series, int item);

    /**
     * Returns the y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The y-value (possibly {@code null}).
     */
    Number getY(int series, int item);

    /**
     * Returns the y-value (as a double primitive) for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The y-value.
     */
    double getYValue(int series, int item);

}
