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
 * ---------------
 * TimeSeries.java
 * ---------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Bryan Scott;
 *                   Nick Guenther;
 *
 */

package org.jfree.data.time;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.Range;
import org.jfree.data.general.Series;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesException;

/**
 * Represents a sequence of zero or more data items in the form (period, value)
 * where 'period' is some instance of a subclass of {@link RegularTimePeriod}.
 * The time series will ensure that (a) all data items have the same type of
 * period (for example, {@link Day}) and (b) that each period appears at
 * most one time in the series.
 * 
 * @param <S>  the type for the series keys ({@code String} is commonly used).
 */
public class TimeSeries<S extends Comparable<S>> extends Series<S> 
        implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -5032960206869675528L;

    /** The type of period for the data. */
    protected Class timePeriodClass;

    /** The list of data items in the series. */
    protected List<TimeSeriesDataItem> data;

    /** The maximum number of items for the series. */
    private int maximumItemCount;

    /**
     * The maximum age of items for the series, specified as a number of
     * time periods.
     */
    private long maximumItemAge;

    /**
     * The minimum y-value in the series.
     * 
     * @since 1.0.14
     */
    private double minY;

    /**
     * The maximum y-value in the series.
     *
     * @since 1.0.14
     */
    private double maxY;

    /**
     * Creates a new (empty) time series.  By default, a daily time series is
     * created.  Use one of the other constructors if you require a different
     * time period.
     *
     * @param name  the series name ({@code null} not permitted).
     */
    public TimeSeries(S name) {
        super(name);
        this.timePeriodClass = null;
        this.data = new ArrayList<>();
        this.maximumItemCount = Integer.MAX_VALUE;
        this.maximumItemAge = Long.MAX_VALUE;
        this.minY = Double.NaN;
        this.maxY = Double.NaN;
    }

    /**
     * Returns the number of items in the series.
     *
     * @return The item count.
     */
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Returns the list of data items for the series (the list contains
     * {@link TimeSeriesDataItem} objects and is unmodifiable).
     *
     * @return The list of data items.
     */
    public List<TimeSeriesDataItem> getItems() {
        return CloneUtils.cloneList(this.data);
    }

    /**
     * Returns the maximum number of items that will be retained in the series.
     * The default value is {@code Integer.MAX_VALUE}.
     *
     * @return The maximum item count.
     *
     * @see #setMaximumItemCount(int)
     */
    public int getMaximumItemCount() {
        return this.maximumItemCount;
    }

    /**
     * Sets the maximum number of items that will be retained in the series.
     * If you add a new item to the series such that the number of items will
     * exceed the maximum item count, then the FIRST element in the series is
     * automatically removed, ensuring that the maximum item count is not
     * exceeded.
     *
     * @param maximum  the maximum (requires &gt;= 0).
     *
     * @see #getMaximumItemCount()
     */
    public void setMaximumItemCount(int maximum) {
        if (maximum < 0) {
            throw new IllegalArgumentException("Negative 'maximum' argument.");
        }
        this.maximumItemCount = maximum;
        int count = this.data.size();
        if (count > maximum) {
            delete(0, count - maximum - 1);
        }
    }

    /**
     * Returns the maximum item age (in time periods) for the series.
     *
     * @return The maximum item age.
     *
     * @see #setMaximumItemAge(long)
     */
    public long getMaximumItemAge() {
        return this.maximumItemAge;
    }

    /**
     * Sets the number of time units in the 'history' for the series.  This
     * provides one mechanism for automatically dropping old data from the
     * time series. For example, if a series contains daily data, you might set
     * the history count to 30.  Then, when you add a new data item, all data
     * items more than 30 days older than the latest value are automatically
     * dropped from the series.
     *
     * @param periods  the number of time periods.
     *
     * @see #getMaximumItemAge()
     */
    public void setMaximumItemAge(long periods) {
        if (periods < 0) {
            throw new IllegalArgumentException("Negative 'periods' argument.");
        }
        this.maximumItemAge = periods;
        removeAgedItems(true);  // remove old items and notify if necessary
    }

    /**
     * Returns the range of y-values in the time series.  Any {@code null} or 
     * {@code Double.NaN} data values in the series will be ignored (except for
     * the special case where all data values are {@code null}, in which case 
     * the return value is {@code Range(Double.NaN, Double.NaN)}).  If the time 
     * series contains no items, this method will return {@code null}.
     * 
     * @return The range of y-values in the time series (possibly {@code null}).
     * 
     * @since 1.0.18
     */
    public Range findValueRange() {
        if (this.data.isEmpty()) {
            return null;
        }
        return new Range(this.minY, this.maxY);
    }
    
    /**
     * Returns the range of y-values in the time series that fall within 
     * the specified range of x-values.  This is equivalent to
     * {@code findValueRange(xRange, TimePeriodAnchor.MIDDLE, timeZone)}.
     * 
     * @param xRange  the subrange of x-values ({@code null} not permitted).
     * @param timeZone  the time zone used to convert x-values to time periods
     *     ({@code null} not permitted).
     * 
     * @return The range. 
     * 
     * @since 1.0.18
     */
    public Range findValueRange(Range xRange, TimeZone timeZone) {
        return findValueRange(xRange, TimePeriodAnchor.MIDDLE, timeZone);
    }
    
    /**
     * Finds the range of y-values that fall within the specified range of
     * x-values (where the x-values are interpreted as milliseconds since the
     * epoch and converted to time periods using the specified timezone).
     * 
     * @param xRange  the subset of x-values to use ({@code null} not
     *     permitted).
     * @param xAnchor  the anchor point for the x-values ({@code null}
     *     not permitted).
     * @param zone  the time zone ({@code null} not permitted).
     * 
     * @return The range of y-values.
     * 
     * @since 1.0.18
     */
    public Range findValueRange(Range xRange, TimePeriodAnchor xAnchor, 
            TimeZone zone) {
        Args.nullNotPermitted(xRange, "xRange");
        Args.nullNotPermitted(xAnchor, "xAnchor");
        Args.nullNotPermitted(zone, "zone");
        if (this.data.isEmpty()) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(zone);
        return findValueRange(xRange, xAnchor, calendar);
    }

    /**
     * Finds the range of y-values that fall within the specified range of
     * x-values (where the x-values are interpreted as milliseconds since the
     * epoch and converted to time periods using the specified timezone).
     * 
     * @param xRange  the subset of x-values to use ({@code null} not
     *     permitted).
     * @param xAnchor  the anchor point for the x-values ({@code null}
     *     not permitted).
     * @param calendar  the calendar ({@code null} not permitted).
     * 
     * @return The range of y-values.
     */
    public Range findValueRange(Range xRange, TimePeriodAnchor xAnchor, Calendar calendar) {
        // since the items are ordered, we could be more clever here and avoid
        // iterating over all the data
        double lowY = Double.POSITIVE_INFINITY;
        double highY = Double.NEGATIVE_INFINITY;
        for (TimeSeriesDataItem item : this.data) {
            long millis = item.getPeriod().getMillisecond(xAnchor, calendar);
            if (xRange.contains(millis)) {
                Number n = item.getValue();
                if (n != null) {
                    double v = n.doubleValue();
                    lowY = minIgnoreNaN(lowY, v);
                    highY = maxIgnoreNaN(highY, v);
                }
            }
        }
        if (Double.isInfinite(lowY) && Double.isInfinite(highY)) {
            if (lowY < highY) {
                return new Range(lowY, highY);
            } else {
                return new Range(Double.NaN, Double.NaN);
            }
        }
        return new Range(lowY, highY);
    }

    /**
     * Returns the smallest y-value in the series, ignoring any 
     * {@code null} and {@code Double.NaN} values.  This method 
     * returns {@code Double.NaN} if there is no smallest y-value (for 
     * example, when the series is empty).
     *
     * @return The smallest y-value.
     *
     * @see #getMaxY()
     *
     * @since 1.0.14
     */
    public double getMinY() {
        return this.minY;
    }

    /**
     * Returns the largest y-value in the series, ignoring any 
     * {@code null} and {@code Double.NaN} values.  This method 
     * returns {@code Double.NaN} if there is no largest y-value
     * (for example, when the series is empty).
     *
     * @return The largest y-value.
     *
     * @see #getMinY()
     *
     * @since 1.0.14
     */
    public double getMaxY() {
        return this.maxY;
    }

    /**
     * Returns the time period class for this series.
     * <p>
     * Only one time period class can be used within a single series (enforced).
     * If you add a data item with a {@link Year} for the time period, then all
     * subsequent data items must also have a {@link Year} for the time period.
     *
     * @return The time period class (may be {@code null} but only for
     *     an empty series).
     */
    public Class getTimePeriodClass() {
        return this.timePeriodClass;
    }

    /**
     * Returns a data item from the dataset.  Note that the returned object
     * is a clone of the item in the series, so modifying it will have no 
     * effect on the data series.
     * 
     * @param index  the item index.
     * 
     * @return The data item.
     */
    public TimeSeriesDataItem getDataItem(int index) {
        TimeSeriesDataItem item = this.data.get(index);
        return (TimeSeriesDataItem) item.clone();
    }

    /**
     * Returns the data item for a specific period.  Note that the returned
     * object is a clone of the item in the series, so modifying it will have
     * no effect on the data series.
     *
     * @param period  the period of interest ({@code null} not allowed).
     *
     * @return The data item matching the specified period (or
     *         {@code null} if there is no match).
     *
     * @see #getDataItem(int)
     */
    public TimeSeriesDataItem getDataItem(RegularTimePeriod period) {
        int index = getIndex(period);
        if (index >= 0) {
            return getDataItem(index);
        }
        return null;
    }

    /**
     * Returns a data item for the series.  This method returns the object
     * that is used for the underlying storage - you should not modify the
     * contents of the returned value unless you know what you are doing.
     *
     * @param index  the item index (zero-based).
     *
     * @return The data item.
     *
     * @see #getDataItem(int)
     *
     * @since 1.0.14
     */
    TimeSeriesDataItem getRawDataItem(int index) {
        return this.data.get(index);
    }

    /**
     * Returns a data item for the series.  This method returns the object
     * that is used for the underlying storage - you should not modify the
     * contents of the returned value unless you know what you are doing.
     *
     * @param period  the item index (zero-based).
     *
     * @return The data item.
     *
     * @see #getDataItem(RegularTimePeriod)
     *
     * @since 1.0.14
     */
    TimeSeriesDataItem getRawDataItem(RegularTimePeriod period) {
        int index = getIndex(period);
        if (index >= 0) {
            return this.data.get(index);
        }
        return null;
    }

    /**
     * Returns the time period at the specified index.
     *
     * @param index  the index of the data item.
     *
     * @return The time period.
     */
    public RegularTimePeriod getTimePeriod(int index) {
        return getRawDataItem(index).getPeriod();
    }

    /**
     * Returns a time period that would be the next in sequence on the end of
     * the time series.
     *
     * @return The next time period.
     */
    public RegularTimePeriod getNextTimePeriod() {
        RegularTimePeriod last = getTimePeriod(getItemCount() - 1);
        return last.next();
    }

    /**
     * Returns a collection of all the time periods in the time series.
     *
     * @return A collection of all the time periods.
     */
    public Collection getTimePeriods() {
        Collection result = new java.util.ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            result.add(getTimePeriod(i));
        }
        return result;
    }

    /**
     * Returns a collection of time periods in the specified series, but not in
     * this series, and therefore unique to the specified series.
     *
     * @param series  the series to check against this one.
     *
     * @return The unique time periods.
     */
    public Collection getTimePeriodsUniqueToOtherSeries(TimeSeries<S> series) {
        Collection result = new java.util.ArrayList();
        for (int i = 0; i < series.getItemCount(); i++) {
            RegularTimePeriod period = series.getTimePeriod(i);
            int index = getIndex(period);
            if (index < 0) {
                result.add(period);
            }
        }
        return result;
    }

    /**
     * Returns the index for the item (if any) that corresponds to a time
     * period.
     *
     * @param period  the time period ({@code null} not permitted).
     *
     * @return The index.
     */
    public int getIndex(RegularTimePeriod period) {
        Args.nullNotPermitted(period, "period");
        TimeSeriesDataItem dummy = new TimeSeriesDataItem(
              period, Integer.MIN_VALUE);
        return Collections.binarySearch(this.data, dummy);
    }

    /**
     * Returns the value at the specified index.
     *
     * @param index  index of a value.
     *
     * @return The value (possibly {@code null}).
     */
    public Number getValue(int index) {
        return getRawDataItem(index).getValue();
    }

    /**
     * Returns the value for a time period.  If there is no data item with the
     * specified period, this method will return {@code null}.
     *
     * @param period  time period ({@code null} not permitted).
     *
     * @return The value (possibly {@code null}).
     */
    public Number getValue(RegularTimePeriod period) {
        int index = getIndex(period);
        if (index >= 0) {
            return getValue(index);
        }
        return null;
    }

    /**
     * Adds a data item to the series and sends a {@link SeriesChangeEvent} to
     * all registered listeners.
     *
     * @param item  the (timeperiod, value) pair ({@code null} not permitted).
     */
    public void add(TimeSeriesDataItem item) {
        add(item, true);
    }

    /**
     * Adds a data item to the series and sends a {@link SeriesChangeEvent} to
     * all registered listeners.
     *
     * @param item  the (timeperiod, value) pair ({@code null} not permitted).
     * @param notify  notify listeners?
     */
    public void add(TimeSeriesDataItem item, boolean notify) {
        Args.nullNotPermitted(item, "item");
        item = (TimeSeriesDataItem) item.clone();
        Class c = item.getPeriod().getClass();
        if (this.timePeriodClass == null) {
            this.timePeriodClass = c;
        } else if (!this.timePeriodClass.equals(c)) {
            StringBuilder b = new StringBuilder();
            b.append("You are trying to add data where the time period class ");
            b.append("is ");
            b.append(item.getPeriod().getClass().getName());
            b.append(", but the TimeSeries is expecting an instance of ");
            b.append(this.timePeriodClass.getName());
            b.append(".");
            throw new SeriesException(b.toString());
        }

        // make the change (if it's not a duplicate time period)...
        boolean added = false;
        int count = getItemCount();
        if (count == 0) {
            this.data.add(item);
            added = true;
        }
        else {
            RegularTimePeriod last = getTimePeriod(getItemCount() - 1);
            if (item.getPeriod().compareTo(last) > 0) {
                this.data.add(item);
                added = true;
            }
            else {
                int index = Collections.binarySearch(this.data, item);
                if (index < 0) {
                    this.data.add(-index - 1, item);
                    added = true;
                }
                else {
                    StringBuilder b = new StringBuilder();
                    b.append("You are attempting to add an observation for ");
                    b.append("the time period ");
                    b.append(item.getPeriod().toString());
                    b.append(" but the series already contains an observation");
                    b.append(" for that time period. Duplicates are not ");
                    b.append("permitted.  Try using the addOrUpdate() method.");
                    throw new SeriesException(b.toString());
                }
            }
        }
        if (added) {
            updateBoundsForAddedItem(item);
            // check if this addition will exceed the maximum item count...
            if (getItemCount() > this.maximumItemCount) {
                TimeSeriesDataItem d = this.data.remove(0);
                updateBoundsForRemovedItem(d);
            }

            removeAgedItems(false);  // remove old items if necessary, but
                                     // don't notify anyone, because that
                                     // happens next anyway...
            if (notify) {
                fireSeriesChanged();
            }
        }

    }

    /**
     * Adds a new data item to the series and sends a {@link SeriesChangeEvent}
     * to all registered listeners.
     *
     * @param period  the time period ({@code null} not permitted).
     * @param value  the value.
     */
    public void add(RegularTimePeriod period, double value) {
        // defer argument checking...
        add(period, value, true);
    }

    /**
     * Adds a new data item to the series and sends a {@link SeriesChangeEvent}
     * to all registered listeners.
     *
     * @param period  the time period ({@code null} not permitted).
     * @param value  the value.
     * @param notify  notify listeners?
     */
    public void add(RegularTimePeriod period, double value, boolean notify) {
        // defer argument checking...
        TimeSeriesDataItem item = new TimeSeriesDataItem(period, value);
        add(item, notify);
    }

    /**
     * Adds a new data item to the series and sends
     * a {@link org.jfree.data.general.SeriesChangeEvent} to all registered
     * listeners.
     *
     * @param period  the time period ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public void add(RegularTimePeriod period, Number value) {
        // defer argument checking...
        add(period, value, true);
    }

    /**
     * Adds a new data item to the series and sends a {@link SeriesChangeEvent}
     * to all registered listeners.
     *
     * @param period  the time period ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     * @param notify  notify listeners?
     */
    public void add(RegularTimePeriod period, Number value, boolean notify) {
        // defer argument checking...
        TimeSeriesDataItem item = new TimeSeriesDataItem(period, value);
        add(item, notify);
    }

    /**
     * Updates (changes) the value for a time period.  Throws a
     * {@link SeriesException} if the period does not exist.
     *
     * @param period  the period ({@code null} not permitted).
     * @param value  the value.
     * 
     * @since 1.0.14
     */
    public void update(RegularTimePeriod period, double value) {
      update(period, Double.valueOf(value));
    }

    /**
     * Updates (changes) the value for a time period.  Throws a
     * {@link SeriesException} if the period does not exist.
     *
     * @param period  the period ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public void update(RegularTimePeriod period, Number value) {
        TimeSeriesDataItem temp = new TimeSeriesDataItem(period, value);
        int index = Collections.binarySearch(this.data, temp);
        if (index < 0) {
            throw new SeriesException("There is no existing value for the "
                    + "specified 'period'.");
        }
        update(index, value);
    }

    /**
     * Updates (changes) the value of a data item.
     *
     * @param index  the index of the data item.
     * @param value  the new value ({@code null} permitted).
     */
    public void update(int index, Number value) {
        TimeSeriesDataItem item = this.data.get(index);
        boolean iterate = false;
        Number oldYN = item.getValue();
        if (oldYN != null) {
            double oldY = oldYN.doubleValue();
            if (!Double.isNaN(oldY)) {
                iterate = oldY <= this.minY || oldY >= this.maxY;
            }
        }
        item.setValue(value);
        if (iterate) {
            updateMinMaxYByIteration();
        }
        else if (value != null) {
            double yy = value.doubleValue();
            this.minY = minIgnoreNaN(this.minY, yy);
            this.maxY = maxIgnoreNaN(this.maxY, yy);
        }
        fireSeriesChanged();
    }

    /**
     * Adds or updates data from one series to another.  Returns another series
     * containing the values that were overwritten.
     *
     * @param series  the series to merge with this.
     *
     * @return A series containing the values that were overwritten.
     */
    public TimeSeries<S> addAndOrUpdate(TimeSeries<S> series) {
        TimeSeries<S> overwritten = new TimeSeries<>(getKey());
        for (int i = 0; i < series.getItemCount(); i++) {
            TimeSeriesDataItem item = series.getRawDataItem(i);
            TimeSeriesDataItem oldItem = addOrUpdate(item.getPeriod(),
                    item.getValue());
            if (oldItem != null) {
                overwritten.add(oldItem);
            }
        }
        return overwritten;
    }

    /**
     * Adds or updates an item in the times series and sends a
     * {@link SeriesChangeEvent} to all registered listeners.
     *
     * @param period  the time period to add/update ({@code null} not
     *                permitted).
     * @param value  the new value.
     *
     * @return A copy of the overwritten data item, or {@code null} if no
     *         item was overwritten.
     */
    public TimeSeriesDataItem addOrUpdate(RegularTimePeriod period,
                                          double value) {
        return addOrUpdate(period, Double.valueOf(value));
    }

    /**
     * Adds or updates an item in the times series and sends a
     * {@link SeriesChangeEvent} to all registered listeners.
     *
     * @param period  the time period to add/update ({@code null} not
     *                permitted).
     * @param value  the new value ({@code null} permitted).
     *
     * @return A copy of the overwritten data item, or {@code null} if no
     *         item was overwritten.
     */
    public TimeSeriesDataItem addOrUpdate(RegularTimePeriod period,
            Number value) {
        return addOrUpdate(new TimeSeriesDataItem(period, value));
    }

    /**
     * Adds or updates an item in the times series and sends a
     * {@link SeriesChangeEvent} to all registered listeners.
     *
     * @param item  the data item ({@code null} not permitted).
     *
     * @return A copy of the overwritten data item, or {@code null} if no
     *         item was overwritten.
     *
     * @since 1.0.14
     */
    public TimeSeriesDataItem addOrUpdate(TimeSeriesDataItem item) {

        Args.nullNotPermitted(item, "item");
        Class periodClass = item.getPeriod().getClass();
        if (this.timePeriodClass == null) {
            this.timePeriodClass = periodClass;
        }
        else if (!this.timePeriodClass.equals(periodClass)) {
            String msg = "You are trying to add data where the time "
                    + "period class is " + periodClass.getName()
                    + ", but the TimeSeries is expecting an instance of "
                    + this.timePeriodClass.getName() + ".";
            throw new SeriesException(msg);
        }
        TimeSeriesDataItem overwritten = null;
        int index = Collections.binarySearch(this.data, item);
        if (index >= 0) {
            TimeSeriesDataItem existing = this.data.get(index);
            overwritten = (TimeSeriesDataItem) existing.clone();
            // figure out if we need to iterate through all the y-values
            // to find the revised minY / maxY
            boolean iterate = false;
            Number oldYN = existing.getValue();
            double oldY = oldYN != null ? oldYN.doubleValue() : Double.NaN;
            if (!Double.isNaN(oldY)) {
                iterate = oldY <= this.minY || oldY >= this.maxY;
            }
            existing.setValue(item.getValue());
            if (iterate) {
                updateMinMaxYByIteration();
            }
            else if (item.getValue() != null) {
                double yy = item.getValue().doubleValue();
                this.minY = minIgnoreNaN(this.minY, yy);
                this.maxY = maxIgnoreNaN(this.maxY, yy);
            }
        }
        else {
            item = (TimeSeriesDataItem) item.clone();
            this.data.add(-index - 1, item);
            updateBoundsForAddedItem(item);

            // check if this addition will exceed the maximum item count...
            if (getItemCount() > this.maximumItemCount) {
                TimeSeriesDataItem d = this.data.remove(0);
                updateBoundsForRemovedItem(d);
            }
        }
        removeAgedItems(false);  // remove old items if necessary, but
                                 // don't notify anyone, because that
                                 // happens next anyway...
        fireSeriesChanged();
        return overwritten;

    }

    /**
     * Age items in the series.  Ensure that the timespan from the youngest to
     * the oldest record in the series does not exceed maximumItemAge time
     * periods.  Oldest items will be removed if required.
     *
     * @param notify  controls whether or not a {@link SeriesChangeEvent} is
     *                sent to registered listeners IF any items are removed.
     */
    public void removeAgedItems(boolean notify) {
        // check if there are any values earlier than specified by the history
        // count...
        if (getItemCount() > 1) {
            long latest = getTimePeriod(getItemCount() - 1).getSerialIndex();
            boolean removed = false;
            while ((latest - getTimePeriod(0).getSerialIndex())
                    > this.maximumItemAge) {
                this.data.remove(0);
                removed = true;
            }
            if (removed) {
                updateMinMaxYByIteration();
                if (notify) {
                    fireSeriesChanged();
                }
            }
        }
    }

    /**
     * Age items in the series.  Ensure that the timespan from the supplied
     * time to the oldest record in the series does not exceed history count.
     * oldest items will be removed if required.
     *
     * @param latest  the time to be compared against when aging data
     *     (specified in milliseconds).
     * @param notify  controls whether or not a {@link SeriesChangeEvent} is
     *                sent to registered listeners IF any items are removed.
     */
    public void removeAgedItems(long latest, boolean notify) {
        if (this.data.isEmpty()) {
            return;  // nothing to do
        }
        // find the serial index of the period specified by 'latest'
        long index = Long.MAX_VALUE;
        try {
            Method m = RegularTimePeriod.class.getDeclaredMethod(
                    "createInstance", Class.class, Date.class,
                    TimeZone.class, Locale.class);
            RegularTimePeriod newest = (RegularTimePeriod) m.invoke(
                    this.timePeriodClass, new Object[] {this.timePeriodClass,
                            new Date(latest), TimeZone.getDefault(), Locale.getDefault()});
            index = newest.getSerialIndex();
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // check if there are any values earlier than specified by the history
        // count...
        boolean removed = false;
        while (getItemCount() > 0 && (index
                - getTimePeriod(0).getSerialIndex()) > this.maximumItemAge) {
            this.data.remove(0);
            removed = true;
        }
        if (removed) {
            updateMinMaxYByIteration();
            if (notify) {
                fireSeriesChanged();
            }
        }
    }

    /**
     * Removes all data items from the series and sends a
     * {@link SeriesChangeEvent} to all registered listeners.
     */
    public void clear() {
        if (this.data.size() > 0) {
            this.data.clear();
            this.timePeriodClass = null;
            this.minY = Double.NaN;
            this.maxY = Double.NaN;
            fireSeriesChanged();
        }
    }

    /**
     * Deletes the data item for the given time period and sends a
     * {@link SeriesChangeEvent} to all registered listeners.  If there is no
     * item with the specified time period, this method does nothing.
     *
     * @param period  the period of the item to delete ({@code null} not
     *                permitted).
     */
    public void delete(RegularTimePeriod period) {
        int index = getIndex(period);
        if (index >= 0) {
            TimeSeriesDataItem item = this.data.remove(index);
            updateBoundsForRemovedItem(item);
            if (this.data.isEmpty()) {
                this.timePeriodClass = null;
            }
            fireSeriesChanged();
        }
    }

    /**
     * Deletes data from start until end index (end inclusive).
     *
     * @param start  the index of the first period to delete.
     * @param end  the index of the last period to delete.
     */
    public void delete(int start, int end) {
        delete(start, end, true);
    }

    /**
     * Deletes data from start until end index (end inclusive).
     *
     * @param start  the index of the first period to delete.
     * @param end  the index of the last period to delete.
     * @param notify  notify listeners?
     *
     * @since 1.0.14
     */
    public void delete(int start, int end, boolean notify) {
        if (end < start) {
            throw new IllegalArgumentException("Requires start <= end.");
        }
        for (int i = 0; i <= (end - start); i++) {
            this.data.remove(start);
        }
        updateMinMaxYByIteration();
        if (this.data.isEmpty()) {
            this.timePeriodClass = null;
        }
        if (notify) {
            fireSeriesChanged();
        }
    }

    /**
     * Returns a clone of the time series.
     * <P>
     * Notes:
     * <ul>
     *   <li>no need to clone the domain and range descriptions, since String
     *     object is immutable;</li>
     *   <li>we pass over to the more general method clone(start, end).</li>
     * </ul>
     *
     * @return A clone of the time series.
     *
     * @throws CloneNotSupportedException not thrown by this class, but
     *         subclasses may differ.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        TimeSeries<S> clone = (TimeSeries) super.clone();
        clone.data = CloneUtils.cloneList(this.data);
        return clone;
    }

    /**
     * Creates a new timeseries by copying a subset of the data in this time
     * series.
     *
     * @param start  the index of the first time period to copy.
     * @param end  the index of the last time period to copy.
     *
     * @return A series containing a copy of this times series from start until
     *         end.
     *
     * @throws CloneNotSupportedException if there is a cloning problem.
     */
    public TimeSeries<S> createCopy(int start, int end)
            throws CloneNotSupportedException {
        if (start < 0) {
            throw new IllegalArgumentException("Requires start >= 0.");
        }
        if (end < start) {
            throw new IllegalArgumentException("Requires start <= end.");
        }
        TimeSeries<S> copy = (TimeSeries) super.clone();
        copy.minY = Double.NaN;
        copy.maxY = Double.NaN;
        copy.data = new java.util.ArrayList();
        if (this.data.size() > 0) {
            for (int index = start; index <= end; index++) {
                TimeSeriesDataItem item = this.data.get(index);
                TimeSeriesDataItem clone = (TimeSeriesDataItem) item.clone();
                try {
                    copy.add(clone);
                }
                catch (SeriesException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return copy;
    }

    /**
     * Creates a new timeseries by copying a subset of the data in this time
     * series.
     *
     * @param start  the first time period to copy ({@code null} not
     *         permitted).
     * @param end  the last time period to copy ({@code null} not permitted).
     *
     * @return A time series containing a copy of this time series from start
     *         until end.
     *
     * @throws CloneNotSupportedException if there is a cloning problem.
     */
    public TimeSeries<S> createCopy(RegularTimePeriod start, RegularTimePeriod end)
        throws CloneNotSupportedException {

        Args.nullNotPermitted(start, "start");
        Args.nullNotPermitted(end, "end");
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(
                    "Requires start on or before end.");
        }
        boolean emptyRange = false;
        int startIndex = getIndex(start);
        if (startIndex < 0) {
            startIndex = -(startIndex + 1);
            if (startIndex == this.data.size()) {
                emptyRange = true;  // start is after last data item
            }
        }
        int endIndex = getIndex(end);
        if (endIndex < 0) {             // end period is not in original series
            endIndex = -(endIndex + 1); // this is first item AFTER end period
            endIndex = endIndex - 1;    // so this is last item BEFORE end
        }
        if ((endIndex < 0)  || (endIndex < startIndex)) {
            emptyRange = true;
        }
        if (emptyRange) {
            TimeSeries<S> copy = (TimeSeries) super.clone();
            copy.data = new java.util.ArrayList();
            return copy;
        }
        return createCopy(startIndex, endIndex);
    }

    /**
     * Tests the series for equality with an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeSeries)) {
            return false;
        }
        TimeSeries<S> that = (TimeSeries) obj;
        if (!Objects.equals(this.timePeriodClass, that.timePeriodClass)) {
            return false;
        }
        if (getMaximumItemAge() != that.getMaximumItemAge()) {
            return false;
        }
        if (getMaximumItemCount() != that.getMaximumItemCount()) {
            return false;
        }
        int count = getItemCount();
        if (count != that.getItemCount()) {
            return false;
        }
        if (!Objects.equals(this.data, that.data)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (this.timePeriodClass != null
                ? this.timePeriodClass.hashCode() : 0);
        // it is too slow to look at every data item, so let's just look at
        // the first, middle and last items...
        int count = getItemCount();
        if (count > 0) {
            TimeSeriesDataItem item = getRawDataItem(0);
            result = 29 * result + item.hashCode();
        }
        if (count > 1) {
            TimeSeriesDataItem item = getRawDataItem(count - 1);
            result = 29 * result + item.hashCode();
        }
        if (count > 2) {
            TimeSeriesDataItem item = getRawDataItem(count / 2);
            result = 29 * result + item.hashCode();
        }
        result = 29 * result + this.maximumItemCount;
        result = 29 * result + (int) this.maximumItemAge;
        return result;
    }

    /**
     * Updates the cached values for the minimum and maximum data values.
     *
     * @param item  the item added ({@code null} not permitted).
     *
     * @since 1.0.14
     */
    private void updateBoundsForAddedItem(TimeSeriesDataItem item) {
        Number yN = item.getValue();
        if (item.getValue() != null) {
            double y = yN.doubleValue();
            this.minY = minIgnoreNaN(this.minY, y);
            this.maxY = maxIgnoreNaN(this.maxY, y);
        }
    }
    
    /**
     * Updates the cached values for the minimum and maximum data values on
     * the basis that the specified item has just been removed.
     *
     * @param item  the item added ({@code null} not permitted).
     *
     * @since 1.0.14
     */
    private void updateBoundsForRemovedItem(TimeSeriesDataItem item) {
        Number yN = item.getValue();
        if (yN != null) {
            double y = yN.doubleValue();
            if (!Double.isNaN(y)) {
                if (y <= this.minY || y >= this.maxY) {
                    updateMinMaxYByIteration();
                }
            }
        }
    }

    /**
     * Finds the bounds of the x and y values for the series, by iterating
     * through all the data items.
     *
     * @since 1.0.14
     */
    private void updateMinMaxYByIteration() {
        this.minY = Double.NaN;
        this.maxY = Double.NaN;
        for (TimeSeriesDataItem item : this.data) {
            updateBoundsForAddedItem(item);
        }
    }

    /**
     * A function to find the minimum of two values, but ignoring any
     * Double.NaN values.
     *
     * @param a  the first value.
     * @param b  the second value.
     *
     * @return The minimum of the two values.
     */
    private double minIgnoreNaN(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        }
        if (Double.isNaN(b)) {
            return a;
        }
        return Math.min(a, b);
    }

    /**
     * A function to find the maximum of two values, but ignoring any
     * Double.NaN values.
     *
     * @param a  the first value.
     * @param b  the second value.
     *
     * @return The maximum of the two values.
     */
    private double maxIgnoreNaN(double a, double b) {
        if (Double.isNaN(a)) {
            return b;
        }
        if (Double.isNaN(b)) {
            return a;
        }
        else {
            return Math.max(a, b);
        }
    }

}
