/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * (C) Copyright 2001-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Mark Watson (www.markwatson.com);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 18-Oct-2001 : Version 1, thanks to Mark Watson (DG);
 * 22-Oct-2001 : Renamed DataSource.java --> Dataset.java etc (DG);
 * 06-May-2004 : Added methods that return double primitives (DG);
 * 15-Sep-2009 : Added clarifications to API docs (DG);
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
public interface IntervalXYDataset extends XYDataset {

    /**
     * Returns the lower bound of the x-interval for the specified series and
     * item.  If this lower bound is specified, it should be less than or
     * equal to the upper bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The lower bound of the x-interval (<code>null</code> permitted).
     */
    public Number getStartX(int series, int item);

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
    public double getStartXValue(int series, int item);

    /**
     * Returns the upper bound of the x-interval for the specified series and
     * item.  If this upper bound is specified, it should be greater than or
     * equal to the lower bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the x-interval (<code>null</code> permitted).
     */
    public Number getEndX(int series, int item);

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
    public double getEndXValue(int series, int item);

    /**
     * Returns the lower bound of the y-interval for the specified series and
     * item.  If this lower bound is specified, it should be less than or
     * equal to the upper bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The lower bound of the y-interval (<code>null</code> permitted).
     */
    public Number getStartY(int series, int item);

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
    public double getStartYValue(int series, int item);

    /**
     * Returns the upper bound of the y-interval for the specified series and
     * item.  If this upper bound is specified, it should be greater than or
     * equal to the lower bound of the interval (if one is specified).
     *
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The upper bound of the y-interval (<code>null</code> permitted).
     */
    public Number getEndY(int series, int item);

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
    public double getEndYValue(int series, int item);

}
