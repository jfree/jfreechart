/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * --------------------
 * XIntervalSeries.java
 * --------------------
 * (C) Copyright 2006-2021, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.data.ComparableObjectItem;
import org.jfree.data.ComparableObjectSeries;
import org.jfree.data.general.SeriesChangeEvent;

/**
 * A list of (x, x-low, x-high, y) data items.
 *
 * @see XIntervalSeriesCollection
 */
public class XIntervalSeries extends ComparableObjectSeries {

    /**
     * Creates a new empty series.  By default, items added to the series will
     * be sorted into ascending order by x-value, and duplicate x-values will
     * be allowed (these defaults can be modified with another constructor.
     *
     * @param key  the series key ({@code null} not permitted).
     */
    public XIntervalSeries(Comparable key) {
        this(key, true, true);
    }

    /**
     * Constructs a new xy-series that contains no data.  You can specify
     * whether or not duplicate x-values are allowed for the series.
     *
     * @param key  the series key ({@code null} not permitted).
     * @param autoSort  a flag that controls whether or not the items in the
     *                  series are sorted.
     * @param allowDuplicateXValues  a flag that controls whether duplicate
     *                               x-values are allowed.
     */
    public XIntervalSeries(Comparable key, boolean autoSort,
            boolean allowDuplicateXValues) {
        super(key, autoSort, allowDuplicateXValues);
    }

    /**
     * Adds a data item to the series and sends a {@link SeriesChangeEvent} to 
     * all registered listeners.
     *
     * @param x  the x-value.
     * @param y  the y-value.
     * @param xLow  the lower bound of the y-interval.
     * @param xHigh  the upper bound of the y-interval.
     */
    public void add(double x, double xLow, double xHigh, double y) {
        add(new XIntervalDataItem(x, xLow, xHigh, y), true);
    }

    /**
     * Adds a data item to the series and, if requested, sends a 
     * {@link SeriesChangeEvent} to all registered listeners.
     * 
     * @param item the data item ({@code null} not permitted).
     * @param notify  notify listeners?
     */
    public void add(XIntervalDataItem item, boolean notify) {
        super.add(item, notify);
    }

    /**
     * Returns the x-value for the specified item.
     *
     * @param index  the item index.
     *
     * @return The x-value (never {@code null}).
     */
    public Number getX(int index) {
        XIntervalDataItem item = (XIntervalDataItem) getDataItem(index);
        return item.getX();
    }

    /**
     * Returns the lower bound of the x-interval for the specified item.
     *
     * @param index  the item index.
     *
     * @return The lower bound of the x-interval.
     */
    public double getXLowValue(int index) {
        XIntervalDataItem item = (XIntervalDataItem) getDataItem(index);
        return item.getXLowValue();
    }

    /**
     * Returns the upper bound of the x-interval for the specified item.
     *
     * @param index  the item index.
     *
     * @return The upper bound of the x-interval.
     */
    public double getXHighValue(int index) {
        XIntervalDataItem item = (XIntervalDataItem) getDataItem(index);
        return item.getXHighValue();
    }

    /**
     * Returns the y-value for the specified item.
     *
     * @param index  the item index.
     *
     * @return The y-value.
     */
    public double getYValue(int index) {
        XIntervalDataItem item = (XIntervalDataItem) getDataItem(index);
        return item.getYValue();
    }

    /**
     * Returns the data item at the specified index.
     *
     * @param index  the item index.
     *
     * @return The data item.
     */
    @Override
    public ComparableObjectItem getDataItem(int index) {
        return super.getDataItem(index);
    }

}
