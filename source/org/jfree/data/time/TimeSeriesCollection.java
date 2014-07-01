/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * (C) Copyright 2001-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 11-Oct-2001 : Version 1 (DG);
 * 18-Oct-2001 : Added implementation of IntervalXYDataSource so that bar plots
 *               (using numerical axes) can be plotted from time series
 *               data (DG);
 * 22-Oct-2001 : Renamed DataSource.java --> Dataset.java etc. (DG);
 * 15-Nov-2001 : Added getSeries() method.  Changed name from TimeSeriesDataset
 *               to TimeSeriesCollection (DG);
 * 07-Dec-2001 : TimeSeries --> BasicTimeSeries (DG);
 * 01-Mar-2002 : Added a time zone offset attribute, to enable fast calculation
 *               of the time period start and end values (DG);
 * 29-Mar-2002 : The collection now registers itself with all the time series
 *               objects as a SeriesChangeListener.  Removed redundant
 *               calculateZoneOffset method (DG);
 * 06-Jun-2002 : Added a setting to control whether the x-value supplied in the
 *               getXValue() method comes from the START, MIDDLE, or END of the
 *               time period.  This is a workaround for JFreeChart, where the
 *               current date axis always labels the start of a time
 *               period (DG);
 * 24-Jun-2002 : Removed unnecessary import (DG);
 * 24-Aug-2002 : Implemented DomainInfo interface, and added the
 *               DomainIsPointsInTime flag (DG);
 * 07-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 16-Oct-2002 : Added remove methods (DG);
 * 10-Jan-2003 : Changed method names in RegularTimePeriod class (DG);
 * 13-Mar-2003 : Moved to com.jrefinery.data.time package and implemented
 *               Serializable (DG);
 * 04-Sep-2003 : Added getSeries(String) method (DG);
 * 15-Sep-2003 : Added a removeAllSeries() method to match
 *               XYSeriesCollection (DG);
 * 05-May-2004 : Now extends AbstractIntervalXYDataset (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 06-Oct-2004 : Updated for changed in DomainInfo interface (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0
 *               release (DG);
 * 28-Mar-2005 : Fixed bug in getSeries(int) method (1170825) (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 13-Dec-2005 : Deprecated the 'domainIsPointsInTime' flag as it is
 *               redundant.  Fixes bug 1243050 (DG);
 * 04-May-2007 : Override getDomainOrder() to indicate that items are sorted
 *               by x-value (ascending) (DG);
 * 08-May-2007 : Added indexOf(TimeSeries) method (DG);
 * 18-Jan-2008 : Changed getSeries(String) to getSeries(Comparable) (DG);
 * 19-May-2009 : Implemented XYDomainInfo (DG);
 * 26-May-2009 : Implemented XYRangeInfo (DG);
 * 09-Jun-2009 : Apply some short-cuts to series value lookups (DG);
 * 26-Jun-2009 : Fixed clone() (DG);
 * 08-Jan-2012 : Fixed getRangeBounds() method (bug 3445507) (DG);
 * 02-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.data.time;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import org.jfree.chart.util.ParamChecks;

import org.jfree.data.DomainInfo;
import org.jfree.data.DomainOrder;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.Series;
import org.jfree.data.xy.AbstractIntervalXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYDomainInfo;
import org.jfree.data.xy.XYRangeInfo;
import org.jfree.util.ObjectUtilities;

/**
 * A collection of time series objects.  This class implements the
 * {@link XYDataset} interface, as well as the extended
 * {@link IntervalXYDataset} interface.  This makes it a convenient dataset for
 * use with the {@link org.jfree.chart.plot.XYPlot} class.
 */
public class TimeSeriesCollection extends AbstractIntervalXYDataset
        implements XYDataset, IntervalXYDataset, DomainInfo, XYDomainInfo,
        XYRangeInfo, VetoableChangeListener, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 834149929022371137L;

    /** Storage for the time series. */
    private List data;

    /** A working calendar (to recycle) */
    private Calendar workingCalendar;

    /**
     * The point within each time period that is used for the X value when this
     * collection is used as an {@link org.jfree.data.xy.XYDataset}.  This can
     * be the start, middle or end of the time period.
     */
    private TimePeriodAnchor xPosition;

    /**
     * A flag that indicates that the domain is 'points in time'.  If this
     * flag is true, only the x-value is used to determine the range of values
     * in the domain, the start and end x-values are ignored.
     *
     * @deprecated No longer used (as of 1.0.1).
     */
    private boolean domainIsPointsInTime;

    /**
     * Constructs an empty dataset, tied to the default timezone.
     */
    public TimeSeriesCollection() {
        this(null, TimeZone.getDefault());
    }

    /**
     * Constructs an empty dataset, tied to a specific timezone.
     *
     * @param zone  the timezone (<code>null</code> permitted, will use
     *              <code>TimeZone.getDefault()</code> in that case).
     */
    public TimeSeriesCollection(TimeZone zone) {
        // FIXME: need a locale as well as a timezone
        this(null, zone);
    }

    /**
     * Constructs a dataset containing a single series (more can be added),
     * tied to the default timezone.
     *
     * @param series the series (<code>null</code> permitted).
     */
    public TimeSeriesCollection(TimeSeries series) {
        this(series, TimeZone.getDefault());
    }

    /**
     * Constructs a dataset containing a single series (more can be added),
     * tied to a specific timezone.
     *
     * @param series  a series to add to the collection (<code>null</code>
     *                permitted).
     * @param zone  the timezone (<code>null</code> permitted, will use
     *              <code>TimeZone.getDefault()</code> in that case).
     */
    public TimeSeriesCollection(TimeSeries series, TimeZone zone) {
        // FIXME:  need a locale as well as a timezone
        if (zone == null) {
            zone = TimeZone.getDefault();
        }
        this.workingCalendar = Calendar.getInstance(zone);
        this.data = new ArrayList();
        if (series != null) {
            this.data.add(series);
            series.addChangeListener(this);
        }
        this.xPosition = TimePeriodAnchor.START;
        this.domainIsPointsInTime = true;

    }

    /**
     * Returns a flag that controls whether the domain is treated as 'points in
     * time'.  This flag is used when determining the max and min values for
     * the domain.  If <code>true</code>, then only the x-values are considered
     * for the max and min values.  If <code>false</code>, then the start and
     * end x-values will also be taken into consideration.
     *
     * @return The flag.
     *
     * @deprecated This flag is no longer used (as of 1.0.1).
     */
    public boolean getDomainIsPointsInTime() {
        return this.domainIsPointsInTime;
    }

    /**
     * Sets a flag that controls whether the domain is treated as 'points in
     * time', or time periods.
     *
     * @param flag  the flag.
     *
     * @deprecated This flag is no longer used, as of 1.0.1.  The
     *             <code>includeInterval</code> flag in methods such as
     *             {@link #getDomainBounds(boolean)} makes this unnecessary.
     */
    public void setDomainIsPointsInTime(boolean flag) {
        this.domainIsPointsInTime = flag;
        notifyListeners(new DatasetChangeEvent(this, this));
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
     * @return The anchor position (never <code>null</code>).
     */
    public TimePeriodAnchor getXPosition() {
        return this.xPosition;
    }

    /**
     * Sets the position within each time period that is used for the X values
     * when the collection is used as an {@link XYDataset}, then sends a
     * {@link DatasetChangeEvent} is sent to all registered listeners.
     *
     * @param anchor  the anchor position (<code>null</code> not permitted).
     */
    public void setXPosition(TimePeriodAnchor anchor) {
        ParamChecks.nullNotPermitted(anchor, "anchor");
        this.xPosition = anchor;
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    /**
     * Returns a list of all the series in the collection.
     *
     * @return The list (which is unmodifiable).
     */
    public List getSeries() {
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
     * @param series  the series (<code>null</code> not permitted).
     *
     * @return The series index.
     *
     * @since 1.0.6
     */
    public int indexOf(TimeSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        return this.data.indexOf(series);
    }

    /**
     * Returns a series.
     *
     * @param series  the index of the series (zero-based).
     *
     * @return The series.
     */
    public TimeSeries getSeries(int series) {
        if ((series < 0) || (series >= getSeriesCount())) {
            throw new IllegalArgumentException(
                "The 'series' argument is out of bounds (" + series + ").");
        }
        return (TimeSeries) this.data.get(series);
    }

    /**
     * Returns the series with the specified key, or <code>null</code> if
     * there is no such series.
     *
     * @param key  the series key (<code>null</code> permitted).
     *
     * @return The series with the given key.
     */
    public TimeSeries getSeries(Comparable key) {
        TimeSeries result = null;
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            TimeSeries series = (TimeSeries) iterator.next();
            Comparable k = series.getKey();
            if (k != null && k.equals(key)) {
                result = series;
            }
        }
        return result;
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
     * @param key  the key (<code>null</code> not permitted).
     * 
     * @return The index.
     * 
     * @since 1.0.17
     */
    public int getSeriesIndex(Comparable key) {
        ParamChecks.nullNotPermitted(key, "key");
        int seriesCount = getSeriesCount();
        for (int i = 0; i < seriesCount; i++) {
            TimeSeries series = (TimeSeries) this.data.get(i);
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
     * @param series  the series (<code>null</code> not permitted).
     */
    public void addSeries(TimeSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        this.data.add(series);
        series.addChangeListener(this);
        series.addVetoableChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Removes the specified series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (<code>null</code> not permitted).
     */
    public void removeSeries(TimeSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        this.data.remove(series);
        series.removeChangeListener(this);
        series.removeVetoableChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Removes a series from the collection.
     *
     * @param index  the series index (zero-based).
     */
    public void removeSeries(int index) {
        TimeSeries series = getSeries(index);
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
        for (int i = 0; i < this.data.size(); i++) {
            TimeSeries series = (TimeSeries) this.data.get(i);
            series.removeChangeListener(this);
            series.removeVetoableChangeListener(this);
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
        TimeSeries s = (TimeSeries) this.data.get(series);
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
        TimeSeries ts = (TimeSeries) this.data.get(series);
        RegularTimePeriod period = ts.getTimePeriod(item);
        return new Long(getX(period));
    }

    /**
     * Returns the x-value for a time period.
     *
     * @param period  the time period (<code>null</code> not permitted).
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
        TimeSeries ts = (TimeSeries) this.data.get(series);
        return new Long(ts.getTimePeriod(item).getFirstMillisecond(
                this.workingCalendar));
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
        TimeSeries ts = (TimeSeries) this.data.get(series);
        return new Long(ts.getTimePeriod(item).getLastMillisecond(
                this.workingCalendar));
    }

    /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value (possibly <code>null</code>).
     */
    @Override
    public Number getY(int series, int item) {
        TimeSeries ts = (TimeSeries) this.data.get(series);
        return ts.getValue(item);
    }

    /**
     * Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The value (possibly <code>null</code>).
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
     * @return The value (possibly <code>null</code>).
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
        TimeSeries timeSeries = getSeries(series);
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
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            TimeSeries series = (TimeSeries) iterator.next();
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
        Iterator iterator = visibleSeriesKeys.iterator();
        while (iterator.hasNext()) {
            Comparable seriesKey = (Comparable) iterator.next();
            TimeSeries series = getSeries(seriesKey);
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
     * @return The range of value in the dataset (possibly <code>null</code>).
     *
     * @since 1.0.15
     */
    public Range getRangeBounds(boolean includeInterval) {
        Range result = null;
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            TimeSeries series = (TimeSeries) iterator.next();
            Range r = new Range(series.getMinY(), series.getMaxY());
            result = Range.combineIgnoringNaN(result, r);
        }
        return result;
    }

    /**
     * Returns the bounds for the y-values in the dataset.
     *
     * @param visibleSeriesKeys  the visible series keys.
     * @param xRange  the x-range (<code>null</code> not permitted).
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
        Iterator iterator = visibleSeriesKeys.iterator();
        while (iterator.hasNext()) {
            Comparable seriesKey = (Comparable) iterator.next();
            TimeSeries series = getSeries(seriesKey);
            Range r = series.findValueRange(xRange, this.xPosition, 
                    this.workingCalendar.getTimeZone());
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
        if (this.domainIsPointsInTime != that.domainIsPointsInTime) {
            return false;
        }
        if (!ObjectUtilities.equal(this.data, that.data)) {
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
        result = 29 * result + (this.domainIsPointsInTime ? 1 : 0);
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
        clone.data = (List) ObjectUtilities.deepClone(this.data);
        clone.workingCalendar = (Calendar) this.workingCalendar.clone();
        return clone;
    }

}
