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
 * --------------------------
 * DefaultTableXYDataset.java
 * --------------------------
 * (C) Copyright 2003-2021, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   Jody Brownell;
 *                   David Gilbert;
 *                   Andreas Schroeder;
 * 
 */

package org.jfree.data.xy;

import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DomainInfo;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.general.SeriesChangeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * An {@link XYDataset} where every series shares the same x-values (required
 * for generating stacked area charts).
 * 
 * @param <S> The type for the series keys.
 */
public class DefaultTableXYDataset<S extends Comparable<S>> 
        extends AbstractIntervalXYDataset<S>
        implements TableXYDataset<S>, IntervalXYDataset<S>, DomainInfo,
                   PublicCloneable {

    /**
     * Storage for the data - this list will contain zero, one or many
     * XYSeries objects.
     */
    private List<XYSeries<S>> data = null;

    /** Storage for the x values. */
    private HashSet xPoints = null;

    /** A flag that controls whether or not events are propogated. */
    private boolean propagateEvents = true;

    /** A flag that controls auto pruning. */
    private boolean autoPrune = false;

    /** The delegate used to control the interval width. */
    private IntervalXYDelegate intervalDelegate;

    /**
     * Creates a new empty dataset.
     */
    public DefaultTableXYDataset() {
        this(false);
    }

    /**
     * Creates a new empty dataset.
     *
     * @param autoPrune  a flag that controls whether or not x-values are
     *                   removed whenever the corresponding y-values are all
     *                   {@code null}.
     */
    public DefaultTableXYDataset(boolean autoPrune) {
        this.autoPrune = autoPrune;
        this.data = new ArrayList<>();
        this.xPoints = new HashSet();
        this.intervalDelegate = new IntervalXYDelegate(this, false);
        addChangeListener(this.intervalDelegate);
    }

    /**
     * Returns the flag that controls whether or not x-values are removed from
     * the dataset when the corresponding y-values are all {@code null}.
     *
     * @return A boolean.
     */
    public boolean isAutoPrune() {
        return this.autoPrune;
    }

    /**
     * Adds a series to the collection and sends a {@link DatasetChangeEvent}
     * to all registered listeners.  The series should be configured to NOT
     * allow duplicate x-values.
     *
     * @param series  the series ({@code null} not permitted).
     */
    public void addSeries(XYSeries<S> series) {
        Args.nullNotPermitted(series, "series");
        if (series.getAllowDuplicateXValues()) {
            throw new IllegalArgumentException(
                "Cannot accept XYSeries that allow duplicate values. "
                + "Use XYSeries(seriesName, <sort>, false) constructor."
            );
        }
        updateXPoints(series);
        this.data.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }

    /**
     * Adds any unique x-values from 'series' to the dataset, and also adds any
     * x-values that are in the dataset but not in 'series' to the series.
     *
     * @param series  the series ({@code null} not permitted).
     */
    private void updateXPoints(XYSeries<S> series) {
        Args.nullNotPermitted(series, "series");
        HashSet seriesXPoints = new HashSet();
        boolean savedState = this.propagateEvents;
        this.propagateEvents = false;
        for (int itemNo = 0; itemNo < series.getItemCount(); itemNo++) {
            Number xValue = series.getX(itemNo);
            seriesXPoints.add(xValue);
            if (!this.xPoints.contains(xValue)) {
                this.xPoints.add(xValue);
                int seriesCount = this.data.size();
                for (int seriesNo = 0; seriesNo < seriesCount; seriesNo++) {
                    XYSeries<S> dataSeries = this.data.get(seriesNo);
                    if (!dataSeries.equals(series)) {
                        dataSeries.add(xValue, null);
                    }
                }
            }
        }
        for (Object point : this.xPoints) {
            Number xPoint = (Number) point;
            if (!seriesXPoints.contains(xPoint)) {
                series.add(xPoint, null);
            }
        }
        this.propagateEvents = savedState;
    }

    /**
     * Updates the x-values for all the series in the dataset.
     */
    public void updateXPoints() {
        this.propagateEvents = false;
        for (int s = 0; s < this.data.size(); s++) {
            updateXPoints(this.data.get(s));
        }
        if (this.autoPrune) {
            prune();
        }
        this.propagateEvents = true;
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
     * Returns the number of x values in the dataset.
     *
     * @return The number of x values in the dataset.
     */
    @Override
    public int getItemCount() {
        if (this.xPoints == null) {
            return 0;
        }
        else {
            return this.xPoints.size();
        }
    }

    /**
     * Returns a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The series (never {@code null}).
     */
    public XYSeries<S> getSeries(int series) {
        Args.requireInRange(series, "series", 0, this.data.size() - 1);
        return this.data.get(series);
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The key for a series.
     */
    @Override
    public S getSeriesKey(int series) {
        // check arguments...delegated
        return getSeries(series).getKey();
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the series (zero-based index).
     *
     * @return The number of items in the specified series.
     */
    @Override
    public int getItemCount(int series) {
        // check arguments...delegated
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
        XYSeries<S> s = this.data.get(series);
        return s.getX(item);

    }

    /**
     * Returns the starting X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting X value.
     */
    @Override
    public Number getStartX(int series, int item) {
        return this.intervalDelegate.getStartX(series, item);
    }

    /**
     * Returns the ending X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The ending X value.
     */
    @Override
    public Number getEndX(int series, int item) {
        return this.intervalDelegate.getEndX(series, item);
    }

    /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param index  the index of the item of interest (zero-based).
     *
     * @return The y-value for the specified series and item (possibly
     *         {@code null}).
     */
    @Override
    public Number getY(int series, int index) {
        XYSeries<S> s = this.data.get(series);
        return s.getY(index);
    }

    /**
     * Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The starting Y value.
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
     * @return The ending Y value.
     */
    @Override
    public Number getEndY(int series, int item) {
        return getY(series, item);
    }

    /**
     * Removes all the series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     */
    public void removeAllSeries() {

        // Unregister the collection as a change listener to each series in
        // the collection.
        for (XYSeries<S> series : this.data) {
            series.removeChangeListener(this);
        }

        // Remove all the series from the collection and notify listeners.
        this.data.clear();
        this.xPoints.clear();
        fireDatasetChanged();
    }

    /**
     * Removes a series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series ({@code null} not permitted).
     */
    public void removeSeries(XYSeries<S> series) {
        Args.nullNotPermitted(series, "series");
        if (this.data.contains(series)) {
            series.removeChangeListener(this);
            this.data.remove(series);
            if (this.data.isEmpty()) {
                this.xPoints.clear();
            }
            fireDatasetChanged();
        }
    }

    /**
     * Removes a series from the collection and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param series  the series (zero based index).
     */
    public void removeSeries(int series) {
        Args.requireInRange(series, "series", 0, this.data.size() - 1);

        // fetch the series, remove the change listener, then remove the series.
        XYSeries<S> s = this.data.get(series);
        s.removeChangeListener(this);
        this.data.remove(series);
        if (this.data.isEmpty()) {
            this.xPoints.clear();
        }
        else if (this.autoPrune) {
            prune();
        }
        fireDatasetChanged();

    }

    /**
     * Removes the items from all series for a given x value.
     *
     * @param x  the x-value.
     */
    public void removeAllValuesForX(Number x) {
        Args.nullNotPermitted(x, "x");
        boolean savedState = this.propagateEvents;
        this.propagateEvents = false;
        for (int s = 0; s < this.data.size(); s++) {
            XYSeries<S> series = this.data.get(s);
            series.remove(x);
        }
        this.propagateEvents = savedState;
        this.xPoints.remove(x);
        fireDatasetChanged();
    }

    /**
     * Returns {@code true} if all the y-values for the specified x-value
     * are {@code null} and {@code false} otherwise.
     *
     * @param x  the x-value.
     *
     * @return A boolean.
     */
    protected boolean canPrune(Number x) {
        for (int s = 0; s < this.data.size(); s++) {
            XYSeries<S> series = this.data.get(s);
            if (series.getY(series.indexOf(x)) != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes all x-values for which all the y-values are {@code null}.
     */
    public void prune() {
        HashSet hs = (HashSet) this.xPoints.clone();
        for (Object h : hs) {
            Number x = (Number) h;
            if (canPrune(x)) {
                removeAllValuesForX(x);
            }
        }
    }

    /**
     * This method receives notification when a series belonging to the dataset
     * changes.  It responds by updating the x-points for the entire dataset
     * and sending a {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param event  information about the change.
     */
    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        if (this.propagateEvents) {
            updateXPoints();
            fireDatasetChanged();
        }
    }

    /**
     * Tests this collection for equality with an arbitrary object.
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
        if (!(obj instanceof DefaultTableXYDataset)) {
            return false;
        }
        DefaultTableXYDataset that = (DefaultTableXYDataset) obj;
        if (this.autoPrune != that.autoPrune) {
            return false;
        }
        if (this.propagateEvents != that.propagateEvents) {
            return false;
        }
        if (!this.intervalDelegate.equals(that.intervalDelegate)) {
            return false;
        }
        if (!Objects.equals(this.data, that.data)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = (this.data != null ? this.data.hashCode() : 0);
        result = 29 * result
                 + (this.xPoints != null ? this.xPoints.hashCode() : 0);
        result = 29 * result + (this.propagateEvents ? 1 : 0);
        result = 29 * result + (this.autoPrune ? 1 : 0);
        return result;
    }

    /**
     * Returns an independent copy of this dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is some reason that cloning
     *     cannot be performed.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultTableXYDataset clone = (DefaultTableXYDataset) super.clone();
        int seriesCount = this.data.size();
        clone.data = new ArrayList<>(seriesCount);
        for (XYSeries<S> series : this.data) {
            clone.data.add(CloneUtils.clone(series));
        }

        clone.intervalDelegate = new IntervalXYDelegate(clone);
        // need to configure the intervalDelegate to match the original
        clone.intervalDelegate.setFixedIntervalWidth(getIntervalWidth());
        clone.intervalDelegate.setAutoWidth(isAutoWidth());
        clone.intervalDelegate.setIntervalPositionFactor(
                getIntervalPositionFactor());
        clone.updateXPoints();
        return clone;
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
        return this.intervalDelegate.getDomainLowerBound(includeInterval);
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
        return this.intervalDelegate.getDomainUpperBound(includeInterval);
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
        if (includeInterval) {
            return this.intervalDelegate.getDomainBounds(includeInterval);
        }
        else {
            return DatasetUtils.iterateDomainBounds(this, includeInterval);
        }
    }

    /**
     * Returns the interval position factor.
     *
     * @return The interval position factor.
     */
    public double getIntervalPositionFactor() {
        return this.intervalDelegate.getIntervalPositionFactor();
    }

    /**
     * Sets the interval position factor. Must be between 0.0 and 1.0 inclusive.
     * If the factor is 0.5, the gap is in the middle of the x values. If it
     * is lesser than 0.5, the gap is farther to the left and if greater than
     * 0.5 it gets farther to the right.
     *
     * @param d the new interval position factor.
     */
    public void setIntervalPositionFactor(double d) {
        this.intervalDelegate.setIntervalPositionFactor(d);
        fireDatasetChanged();
    }

    /**
     * returns the full interval width.
     *
     * @return The interval width to use.
     */
    public double getIntervalWidth() {
        return this.intervalDelegate.getIntervalWidth();
    }

    /**
     * Sets the interval width to a fixed value, and sends a
     * {@link DatasetChangeEvent} to all registered listeners.
     *
     * @param d  the new interval width (must be &gt; 0).
     */
    public void setIntervalWidth(double d) {
        this.intervalDelegate.setFixedIntervalWidth(d);
        fireDatasetChanged();
    }

    /**
     * Returns whether the interval width is automatically calculated or not.
     *
     * @return A flag that determines whether or not the interval width is
     *         automatically calculated.
     */
    public boolean isAutoWidth() {
        return this.intervalDelegate.isAutoWidth();
    }

    /**
     * Sets the flag that indicates whether the interval width is automatically
     * calculated or not.
     *
     * @param b  a boolean.
     */
    public void setAutoWidth(boolean b) {
        this.intervalDelegate.setAutoWidth(b);
        fireDatasetChanged();
    }

}
