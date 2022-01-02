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
 * TimeSeriesCollection.java
 * -------------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DomainInfo;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.Series;
import org.jfree.data.xy.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.*;

/**
 * A collection of time series objects.  This class implements the
 * {@link XYDataset} interface, as well as the extended
 * {@link IntervalXYDataset} interface.  This makes it a convenient dataset for
 * use with the {@link org.jfree.chart.plot.XYPlot} class.
 */
public class TimeSeriesCollection<S extends Comparable<S>> 
        extends AbstractIntervalXYDataset
        implements XYDataset, IntervalXYDataset, DomainInfo, XYDomainInfo,
        XYRangeInfo, VetoableChangeListener, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 834149929022371137L;

    /** Storage for the time series. */
    private List<TimeSeries<S>> data;

    /** A working calendar (to recycle) */
    private Calendar workingCalendar;

    /**
     * The point within each time period that is used for the X value when this
     * collection is used as an {@link org.jfree.data.xy.XYDataset}.  This can
     * be the start, middle or end of the time period.
     */
    private TimePeriodAnchor xPosition;

    /**
     * Constructs an empty dataset, tied to the default timezone.
     */
    public TimeSeriesCollection() {
        this(null, TimeZone.getDefault());
    }

    /**
     * Constructs an empty dataset, tied to a specific timezone.
     *
     * @param zone  the timezone ({@code null} permitted, will use
     *              {@code TimeZone.getDefault()} in that case).
     */
    public TimeSeriesCollection(TimeZone zone) {
        // FIXME: need a locale as well as a timezone
        this(null, zone);
    }

    /**
     * Constructs a dataset containing a single series (more can be added),
     * tied to the default timezone.
     *
     * @param series the series ({@code null} permitted).
     */
    public TimeSeriesCollection(TimeSeries<S> series) {
        this(series, TimeZone.getDefault());
    }

    /**
     * Constructs a dataset containing a single series (more can be added),
     * tied to a specific timezone.
     *
     * @param series  a series to add to the collection ({@code null}
     *                permitted).
     * @param zone  the timezone ({@code null} permitted, will use
     *              {@code TimeZone.getDefault()} in that case).
     */
    public TimeSeriesCollection(TimeSeries<S> series, TimeZone zone) {
        // FIXME:  need a locale as well as a timezone
        if (zone == null) {
            zone = TimeZone.getDefault();
        }
        this.workingCalendar = Calendar.getInstance(zone);
        this.data = new ArrayList<>();
        if (series != null) {
            this.data.add(series);
            series.addChangeListener(this);
        }
        this.xPosition = TimePeriodAnchor.START;
    }

    /**
     * Returns the order of the domain values in this dataset.
     *
     * @return {@link DomainOrder#ASCENDING}
     */
    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    /**
     * Returns the position within each time period that is used for the X
     * value when the collection is used as an
     * {@link org.jfree.data.xy.XYDataset}.
     *
     * @return The anchor position (never {@code null}).
     */
    public TimePeriodAnchor getXPosition() {
        return this.xPosition;
    }

    /**
     * Sets the position within each time period that is used for the X values
     * when the collection is used as an {@link XYDataset}, then sends a
     * {@link DatasetChangeEvent} is sent to all registered listeners.
     *
     * @param anchor  the anchor position ({@code null} not permitted).
     */
    public void setXPosition(TimePeriodAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.xPosition = anchor;
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    /**
     * Returns a list of all the series in the collection.
     *
     * @return The list (which is unmodifiable).
     */
    public List<TimeSeries<S>> getSeries() {
        return Collections.unmodifiableList(this.data);
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
     * Returns the index of the specified series, or -1 if that series is not
     * present in the dataset.
     *
     * @param series  the series ({@code null} not permitted).
     *
     * @return The series index.
     *
     * @since 1.0.6
     */
    public int indexOf(TimeSeries<S> series) {
        Args.nullNotPermitted(series, "series");
        return this.data.indexOf(series);
    }

    /**
     * Returns a series.
     *
     * @param series  the index of the series (zero-based).
     *
     * @return The series.
     */
    public TimeSeries<S> getSeries(int series) {
        Args.requireInRange(series, "series", 0, getSeriesCount() - 1);
        return this.data.get(series);
    }

    /**
     * Returns the series with the specified key, or {@code null} if
     * there is no such series.
     *
     * @param key  the series key ({@code null} permitted).
     *
     * @return The series with the given key.
     */
    public TimeSeries<S> getSeries(S key) {
        for (TimeSeries series : this.data) {
            if (series.getKey() != null && series.getKey().equals(key)) {
                return series;
            }
        }
        return null;
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the index of the series (zero-based).
     *
     * @return The key for a series.
     */
    @Override
    public Comparable getSeriesKey(int series) {
        // check arguments...delegated
        // fetch the series name...
        return getSeries(series).getKey();
    }

    /**
     * Returns the index of the series with the specified key, or -1 if no
     * series has that key.
     * 
     * @param key  the key ({@code null} not permitted).
     * 
     * @return The index.
     * 
     * @since 1.0.17
     */
    public int getSeriesIndex(Comparable key) {
        Args.nullNotPermitted(key, "key");
        int seriesCount = getSeriesCount();
        for (int i = 0; i < seriesCount; i++) {
            TimeSeries<S> series = this.data.get(i);
            if (key.equals(series.getKey())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Adds a series to the collection and sends a {@link DatasetChangeEvent} to
     * all registered listeners.
     *
     * @param series  the series ({@code null} not permitted).
     */
    public void addSeries(TimeSeries<S> series) {
        Args.nullNotPermitted(series, "series");
        this.data.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Removes the specified series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series ({@code null} not permitted).
     */
    public void removeSeries(TimeSeries<S> series) {
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
        TimeSeries<S> series = getSeries(index);
        if (series != null) {
            removeSeries(series);
        }
    }

    /**
     * Removes all the series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     */
    public void removeAllSeries() {

        // deregister the collection as a change listener to each series in the
        // collection
        for (TimeSeries<S> series : this.data) {
            series.removeChangeListener(this);
        }

        // remove all the series from the collection and notify listeners.
        this.data.clear();
        fireDatasetChanged();
    }

    /**
     * Returns the number of items in the specified series.  This method is
     * provided for convenience.
     *
     * @param series  the series index (zero-based).
     *
     * @return The item count.
     */
    @Override
    public int getItemCount(int series) {
        return getSeries(series).getItemCount();
    }

    /**
     * Returns the x-value (as a double primitive) for an item within a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value.
     */
    @Override
    public double getXValue(int series, int item) {
        TimeSeries<S> s = this.data.get(series);
        RegularTimePeriod period = s.getTimePeriod(item);
        return getX(period);
    }

    /**
     * Returns the x-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value.
     */
    @Override
    public Number getX(int series, int item) {
        TimeSeries<S> ts = this.data.get(series);
        RegularTimePeriod period = ts.getTimePeriod(item);
        return getX(period);
    }

    /**
     * Returns the x-value for a time period.
     *
     * @param period  the time period ({@code null} not permitted).
     *
     * @return The x-value.
     */
    protected synchronized long getX(RegularTimePeriod period) {
        long result = 0L;
        if (this.xPosition == TimePeriodAnchor.START) {
            result = period.getFirstMillisecond(this.workingCalendar);
        }
        else if (this.xPosition == TimePeriodAnchor.MIDDLE) {
            result = period.getMiddleMillisecond(this.workingCalendar);
        }
        else if (this.xPosition == TimePeriodAnchor.END) {
            result = period.getLastMillisecond(this.workingCalendar);
        }
        return result;
    }

    /**
     * Returns the starting X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value.
     */
    @Override
    public synchronized Number getStartX(int series, int item) {
        TimeSeries<S> ts = this.data.get(series);
        return ts.getTimePeriod(item).getFirstMillisecond(this.workingCalendar);
    }

    /**
     * Returns the ending X value for the specified series and item.
     *
     * @param series The series (zero-based index).
     * @param item  The item (zero-based index).
     *
     * @return The value.
     */
    @Override
    public synchronized Number getEndX(int series, int item) {
        TimeSeries<S> ts = this.data.get(series);
        return ts.getTimePeriod(item).getLastMillisecond(this.workingCalendar);
    }

    /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getY(int series, int item) {
        TimeSeries<S> ts = this.data.get(series);
        return ts.getValue(item);
    }

    /**
     * Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getStartY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Returns the ending Y value for the specified series and item.
     *
     * @param series  te series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getEndY(int series, int item) {
        return getY(series, item);
    }


    /**
     * Returns the indices of the two data items surrounding a particular
     * millisecond value.
     *
     * @param series  the series index.
     * @param milliseconds  the time.
     *
     * @return An array containing the (two) indices of the items surrounding
     *         the time.
     */
    public int[] getSurroundingItems(int series, long milliseconds) {
        int[] result = new int[] {-1, -1};
        TimeSeries<S> timeSeries = getSeries(series);
        for (int i = 0; i < timeSeries.getItemCount(); i++) {
            Number x = getX(series, i);
            long m = x.longValue();
            if (m <= milliseconds) {
                result[0] = i;
            }
            if (m >= milliseconds) {
                result[1] = i;
                break;
            }
        }
        return result;
    }

    /**
     * Returns the minimum x-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
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
     * @param includeInterval  a flag that determines whether or not the
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
     * @param includeInterval  a flag that determines whether or not the
     *                         x-interval is taken into account.
     *
     * @return The range.
     */
    @Override
    public Range getDomainBounds(boolean includeInterval) {
        Range result = null;
        for (TimeSeries<S> series : this.data) {
            int count = series.getItemCount();
            if (count > 0) {
                RegularTimePeriod start = series.getTimePeriod(0);
                RegularTimePeriod end = series.getTimePeriod(count - 1);
                Range temp;
                if (!includeInterval) {
                    temp = new Range(getX(start), getX(end));
                }
                else {
                    temp = new Range(
                            start.getFirstMillisecond(this.workingCalendar),
                            end.getLastMillisecond(this.workingCalendar));
                }
                result = Range.combine(result, temp);
            }
        }
        return result;
    }

    /**
     * Returns the bounds of the domain values for the specified series.
     *
     * @param visibleSeriesKeys  a list of keys for the visible series.
     * @param includeInterval  include the x-interval?
     *
     * @return A range.
     *
     * @since 1.0.13
     */
    @Override
    public Range getDomainBounds(List visibleSeriesKeys,
            boolean includeInterval) {
        Range result = null;
        for (Object visibleSeriesKey : visibleSeriesKeys) {
            Comparable seriesKey = (Comparable) visibleSeriesKey;
            TimeSeries<S> series = getSeries((S) seriesKey);
            int count = series.getItemCount();
            if (count > 0) {
                RegularTimePeriod start = series.getTimePeriod(0);
                RegularTimePeriod end = series.getTimePeriod(count - 1);
                Range temp;
                if (!includeInterval) {
                    temp = new Range(getX(start), getX(end));
                }
                else {
                    temp = new Range(
                            start.getFirstMillisecond(this.workingCalendar),
                            end.getLastMillisecond(this.workingCalendar));
                }
                result = Range.combine(result, temp);
            }
        }
        return result;
    }

    /**
     * Returns the bounds for the y-values in the dataset.
     * 
     * @param includeInterval  ignored for this dataset.
     * 
     * @return The range of value in the dataset (possibly {@code null}).
     *
     * @since 1.0.15
     */
    public Range getRangeBounds(boolean includeInterval) {
        Range result = null;
        for (TimeSeries<S> series : this.data) {
            Range r = new Range(series.getMinY(), series.getMaxY());
            result = Range.combineIgnoringNaN(result, r);
        }
        return result;
    }

    /**
     * Returns the bounds for the y-values in the dataset.
     *
     * @param visibleSeriesKeys  the visible series keys.
     * @param xRange  the x-range ({@code null} not permitted).
     * @param includeInterval  ignored.
     *
     * @return The bounds.
     *
     * @since 1.0.14
     */
    @Override
    public Range getRangeBounds(List visibleSeriesKeys, Range xRange,
            boolean includeInterval) {
        Range result = null;
        for (Object visibleSeriesKey : visibleSeriesKeys) {
            Comparable seriesKey = (Comparable) visibleSeriesKey;
            TimeSeries<S> series = getSeries((S) seriesKey);
            Range r = series.findValueRange(xRange, this.xPosition,
                    this.workingCalendar);
            result = Range.combineIgnoringNaN(result, r);
        }
        return result;
    }

    /**
     * Receives notification that the key for one of the series in the 
     * collection has changed, and vetos it if the key is already present in 
     * the collection.
     * 
     * @param e  the event.
     * 
     * @since 1.0.17
     */
    @Override
    public void vetoableChange(PropertyChangeEvent e)
            throws PropertyVetoException {
        // if it is not the series name, then we have no interest
        if (!"Key".equals(e.getPropertyName())) {
            return;
        }
        
        // to be defensive, let's check that the source series does in fact
        // belong to this collection
        Series s = (Series) e.getSource();
        if (getSeriesIndex(s.getKey()) == -1) {
            throw new IllegalStateException("Receiving events from a series " +
                    "that does not belong to this collection.");
        }
        // check if the new series name already exists for another series
        Comparable key = (Comparable) e.getNewValue();
        if (getSeriesIndex(key) >= 0) {
            throw new PropertyVetoException("Duplicate key2", e);
        }
    }

    /**
     * Tests this time series collection for equality with another object.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimeSeriesCollection)) {
            return false;
        }
        TimeSeriesCollection that = (TimeSeriesCollection) obj;
        if (this.xPosition != that.xPosition) {
            return false;
        }
        if (!Objects.equals(this.data, that.data)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    @Override
    public int hashCode() {
        int result;
        result = this.data.hashCode();
        result = 29 * result + (this.workingCalendar != null
                ? this.workingCalendar.hashCode() : 0);
        result = 29 * result + (this.xPosition != null
                ? this.xPosition.hashCode() : 0);
        return result;
    }

    /**
     * Returns a clone of this time series collection.
     *
     * @return A clone.
     *
     * @throws java.lang.CloneNotSupportedException if there is a problem 
     *         cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        TimeSeriesCollection clone = (TimeSeriesCollection) super.clone();
        clone.data = CloneUtils.cloneList(this.data);
        clone.workingCalendar = (Calendar) this.workingCalendar.clone();
        return clone;
    }

}
