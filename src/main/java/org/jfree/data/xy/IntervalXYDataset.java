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
 * ----------------------
 * IntervalXYDataset.java
 * ----------------------
 * (C) Copyright 2001-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Mark Watson (www.markwatson.com);
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.data.xy;

/**
 * An extension of the {@link XYDataset} interface that allows an x-interval
 * and a y-interval to be defined.  Note that the x and y values defined
 * by the parent interface are NOT required to fall within these intervals.
 * This interface is used to support (among other things) bar plots against
 * numerical axes.
 */
public interface IntervalXYDataset<S extends Comparable<S>> extends XYDataset<S> {

    /**
     * Returns the lower bound of the x-interval for the specified series and
     * item.  If this lower bound is specified, it should be less than or
     * equal to the upper bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The lower bound of the x-interval ({@code null} permitted).
     */
    Number getStartX(int series, int item);

    /**
     * Returns the lower bound of the x-interval (as a double primitive) for
     * the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The lower bound of the x-interval.
     *
     * @see #getStartX(int, int)
     */
    double getStartXValue(int series, int item);

    /**
     * Returns the upper bound of the x-interval for the specified series and
     * item.  If this upper bound is specified, it should be greater than or
     * equal to the lower bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the x-interval ({@code null} permitted).
     */
    Number getEndX(int series, int item);

    /**
     * Returns the upper bound of the x-interval (as a double primitive) for
     * the specified series and item.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the x-interval.
     *
     * @see #getEndX(int, int)
     */
    double getEndXValue(int series, int item);

    /**
     * Returns the lower bound of the y-interval for the specified series and
     * item.  If this lower bound is specified, it should be less than or
     * equal to the upper bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The lower bound of the y-interval ({@code null} permitted).
     */
    Number getStartY(int series, int item);

    /**
     * Returns the lower bound of the y-interval (as a double primitive) for
     * the specified series and item.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The lower bound of the y-interval.
     *
     * @see #getStartY(int, int)
     */
    double getStartYValue(int series, int item);

    /**
     * Returns the upper bound of the y-interval for the specified series and
     * item.  If this upper bound is specified, it should be greater than or
     * equal to the lower bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the y-interval ({@code null} permitted).
     */
    Number getEndY(int series, int item);

    /**
     * Returns the upper bound of the y-interval (as a double primitive) for
     * the specified series and item.
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the y-interval.
     *
     * @see #getEndY(int, int)
     */
    double getEndYValue(int series, int item);

}
