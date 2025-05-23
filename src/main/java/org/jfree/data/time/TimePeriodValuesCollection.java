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
 * -------------------------------
 * TimePeriodValuesCollection.java
 * -------------------------------
 * (C) Copyright 2003-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.internal.Args;

import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;

/**
 * A collection of {@link TimePeriodValues} objects.
 * <P>
 * This class implements the {@link org.jfree.data.xy.XYDataset} interface, as
 * well as the extended {@link IntervalXYDataset} interface.  This makes it a
 * convenient dataset for use with the {@link org.jfree.chart.plot.XYPlot}
 * class.
 *
 * @param <S> the series key type.
 */
public class TimePeriodValuesCollection<S extends Comparable<S>> extends AbstractIntervalXYDataset<S>
        implements IntervalXYDataset<S>, DomainInfo, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -3077934065236454199L;

    /** Storage for the time series. */
    private final List<TimePeriodValues<S>> data;

    /**
     * The position within a time period to return as the x-value (START,
     * MIDDLE or END).
     */
    private TimePeriodAnchor xPosition;

    /**
     * Constructs an empty dataset.
     */
    public TimePeriodValuesCollection() {
        this(null);
    }

    /**
     * Constructs a dataset containing a single series.  Additional series can
     * be added.
     *
     * @param series  the series ({@code null} ignored).
     */
    public TimePeriodValuesCollection(TimePeriodValues<S> series) {
        super();
        this.data = new ArrayList<>();
        this.xPosition = TimePeriodAnchor.MIDDLE;
        if (series != null) {
            this.data.add(series);
            series.addChangeListener(this);
        }
    }

    /**
     * Returns the position of the X value within each time period.
     *
     * @return The position (never {@code null}).
     *
     * @see #setXPosition(TimePeriodAnchor)
     */
    public TimePeriodAnchor getXPosition() {
        return this.xPosition;
    }

    /**
     * Sets the position of the x-axis within each time period.
     *
     * @param position  the position ({@code null} not permitted).
     *
     * @see #getXPosition()
     */
    public void setXPosition(TimePeriodAnchor position) {
        Args.nullNotPermitted(position, "position");
        this.xPosition = position;
    }

    /**
     * Returns the number of series in the collection.
     *
     * @return The series count.
     */
    @Override
    public int getSeriesCount() {
        return this.data.size();
    }

    /**
     * Returns a series.
     *
     * @param series  the index of the series (zero-based).
     *
     * @return The series.
     */
    public TimePeriodValues<S> getSeries(int series) {
        Args.requireInRange(series, "series", 0, getSeriesCount() - 1);
        if ((series < 0) || (series >= getSeriesCount())) {
            throw new IllegalArgumentException("Index 'series' out of range.");
        }
        return this.data.get(series);
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the index of the series (zero-based).
     *
     * @return The key for a series.
     */
    @Override
    public S getSeriesKey(int series) {
        // defer argument checking
        return getSeries(series).getKey();
    }

    /**
     * Adds a series to the collection.  A
     * {@link org.jfree.data.general.DatasetChangeEvent} is sent to all
     * registered listeners.
     *
     * @param series  the time series.
     */
    public void addSeries(TimePeriodValues<S> series) {
        Args.nullNotPermitted(series, "series");
        this.data.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Removes the specified series from the collection.
     *
     * @param series  the series to remove ({@code null} not permitted).
     */
    public void removeSeries(TimePeriodValues<S> series) {
        Args.nullNotPermitted(series, "series");
        this.data.remove(series);
        series.removeChangeListener(this);
        fireDatasetChanged();

    }

    /**
     * Removes a series from the collection.
     *
     * @param index  the series index (zero-based).
     */
    public void removeSeries(int index) {
        TimePeriodValues<S> series = getSeries(index);
        if (series != null) {
            removeSeries(series);
        }
    }

    /**
     * Returns the number of items in the specified series.
     * <P>
     * This method is provided for convenience.
     *
     * @param series  the index of the series of interest (zero-based).
     *
     * @return The number of items in the specified series.
     */
    @Override
    public int getItemCount(int series) {
        return getSeries(series).getItemCount();
    }

    /**
     * Returns the x-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value for the specified series and item.
     */
    @Override
    public Number getX(int series, int item) {
        TimePeriodValues<S> ts = this.data.get(series);
        TimePeriodValue dp = ts.getDataItem(item);
        TimePeriod period = dp.getPeriod();
        return getX(period);
    }

    /**
     * Returns the x-value for a time period.
     *
     * @param period  the time period.
     *
     * @return The x-value.
     */
    private long getX(TimePeriod period) {

        if (this.xPosition == TimePeriodAnchor.START) {
            return period.getStart().getTime();
        }
        else if (this.xPosition == TimePeriodAnchor.MIDDLE) {
            return period.getStart().getTime()
                / 2 + period.getEnd().getTime() / 2;
        }
        else if (this.xPosition == TimePeriodAnchor.END) {
            return period.getEnd().getTime();
        }
        else {
            throw new IllegalStateException("TimePeriodAnchor unknown.");
        }

    }

    /**
     * Returns the starting X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting X value for the specified series and item.
     */
    @Override
    public Number getStartX(int series, int item) {
        TimePeriodValues<S> ts = this.data.get(series);
        TimePeriodValue dp = ts.getDataItem(item);
        return dp.getPeriod().getStart().getTime();
    }

    /**
     * Returns the ending X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The ending X value for the specified series and item.
     */
    @Override
    public Number getEndX(int series, int item) {
        TimePeriodValues<S> ts = this.data.get(series);
        TimePeriodValue dp = ts.getDataItem(item);
        return dp.getPeriod().getEnd().getTime();
    }

    /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The y-value for the specified series and item.
     */
    @Override
    public Number getY(int series, int item) {
        TimePeriodValues<S> ts = this.data.get(series);
        TimePeriodValue dp = ts.getDataItem(item);
        return dp.getValue();
    }

    /**
     * Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting Y value for the specified series and item.
     */
    @Override
    public Number getStartY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Returns the ending Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The ending Y value for the specified series and item.
     */
    @Override
    public Number getEndY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Returns the minimum x-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether the
     *                         x-interval is taken into account.
     *
     * @return The minimum value.
     */
    @Override
    public double getDomainLowerBound(boolean includeInterval) {
        double result = Double.NaN;
        Range r = getDomainBounds(includeInterval);
        if (r != null) {
            result = r.getLowerBound();
        }
        return result;
    }

    /**
     * Returns the maximum x-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether the
     *                         x-interval is taken into account.
     *
     * @return The maximum value.
     */
    @Override
    public double getDomainUpperBound(boolean includeInterval) {
        double result = Double.NaN;
        Range r = getDomainBounds(includeInterval);
        if (r != null) {
            result = r.getUpperBound();
        }
        return result;
    }

    /**
     * Returns the range of the values in this dataset's domain.
     *
     * @param includeInterval  a flag that determines whether the
     *                         x-interval is taken into account.
     *
     * @return The range.
     */
    @Override
    public Range getDomainBounds(boolean includeInterval) {
        Range result = null;
        Range temp = null;
        for (TimePeriodValues<S> series : this.data) {
            int count = series.getItemCount();
            if (count > 0) {
                TimePeriod start = series.getTimePeriod(
                        series.getMinStartIndex());
                TimePeriod end = series.getTimePeriod(series.getMaxEndIndex());
                if (!includeInterval) {
                    if (this.xPosition == TimePeriodAnchor.START) {
                        TimePeriod maxStart = series.getTimePeriod(
                                series.getMaxStartIndex());
                        temp = new Range(start.getStart().getTime(),
                                maxStart.getStart().getTime());
                    }
                    else if (this.xPosition == TimePeriodAnchor.MIDDLE) {
                        TimePeriod minMiddle = series.getTimePeriod(
                                series.getMinMiddleIndex());
                        long s1 = minMiddle.getStart().getTime();
                        long e1 = minMiddle.getEnd().getTime();
                        TimePeriod maxMiddle = series.getTimePeriod(
                                series.getMaxMiddleIndex());
                        long s2 = maxMiddle.getStart().getTime();
                        long e2 = maxMiddle.getEnd().getTime();
                        temp = new Range(s1 + (e1 - s1) / 2.0,
                                s2 + (e2 - s2) / 2.0);
                    }
                    else if (this.xPosition == TimePeriodAnchor.END) {
                        TimePeriod minEnd = series.getTimePeriod(
                                series.getMinEndIndex());
                        temp = new Range(minEnd.getEnd().getTime(),
                                end.getEnd().getTime());
                    }
                }
                else {
                    temp = new Range(start.getStart().getTime(),
                            end.getEnd().getTime());
                }
                result = Range.combine(result, temp);
            }
        }
        return result;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimePeriodValuesCollection)) {
            return false;
        }
        TimePeriodValuesCollection<S> that = (TimePeriodValuesCollection<S>) obj;
        if (this.xPosition != that.xPosition) {
            return false;
        }
        if (!Objects.equals(this.data, that.data)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.data);
        hash = 83 * hash + Objects.hashCode(this.xPosition);
        return hash;
    }

}
