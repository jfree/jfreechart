/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * --------------------------------
 * SlidingGanttCategoryDataset.java
 * --------------------------------
 * (C) Copyright 2008, 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 09-May-2008 : Version 1 (DG);
 *
 */

package org.jfree.data.gantt;

import java.util.Collections;
import java.util.List;
import org.jfree.chart.util.PublicCloneable;

import org.jfree.data.UnknownKeyException;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DatasetChangeEvent;

/**
 * A {@link GanttCategoryDataset} implementation that presents a subset of the
 * categories in an underlying dataset.  The index of the first "visible"
 * category can be modified, which provides a means of "sliding" through
 * the categories in the underlying dataset.
 *
 * @since 1.0.10
 */
public class SlidingGanttCategoryDataset extends AbstractDataset
        implements GanttCategoryDataset {

    /** The underlying dataset. */
    private GanttCategoryDataset underlying;

    /** The index of the first category to present. */
    private int firstCategoryIndex;

    /** The maximum number of categories to present. */
    private int maximumCategoryCount;

    /**
     * Creates a new instance.
     *
     * @param underlying  the underlying dataset ({@code null} not
     *     permitted).
     * @param firstColumn  the index of the first visible column from the
     *     underlying dataset.
     * @param maxColumns  the maximumColumnCount.
     */
    public SlidingGanttCategoryDataset(GanttCategoryDataset underlying,
            int firstColumn, int maxColumns) {
        this.underlying = underlying;
        this.firstCategoryIndex = firstColumn;
        this.maximumCategoryCount = maxColumns;
    }

    /**
     * Returns the underlying dataset that was supplied to the constructor.
     *
     * @return The underlying dataset (never {@code null}).
     */
    public GanttCategoryDataset getUnderlyingDataset() {
        return this.underlying;
    }

    /**
     * Returns the index of the first visible category.
     *
     * @return The index.
     *
     * @see #setFirstCategoryIndex(int)
     */
    public int getFirstCategoryIndex() {
        return this.firstCategoryIndex;
    }

    /**
     * Sets the index of the first category that should be used from the
     * underlying dataset, and sends a {@link DatasetChangeEvent} to all
     * registered listeners.
     *
     * @param first  the index.
     *
     * @see #getFirstCategoryIndex()
     */
    public void setFirstCategoryIndex(int first) {
        if (first < 0 || first >= this.underlying.getColumnCount()) {
            throw new IllegalArgumentException("Invalid index.");
        }
        this.firstCategoryIndex = first;
        fireDatasetChanged();
    }

    /**
     * Returns the maximum category count.
     *
     * @return The maximum category count.
     *
     * @see #setMaximumCategoryCount(int)
     */
    public int getMaximumCategoryCount() {
        return this.maximumCategoryCount;
    }

    /**
     * Sets the maximum category count and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param max  the maximum.
     *
     * @see #getMaximumCategoryCount()
     */
    public void setMaximumCategoryCount(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("Requires 'max' >= 0.");
        }
        this.maximumCategoryCount = max;
        fireDatasetChanged();
    }

    /**
     * Returns the index of the last column for this dataset, or -1.
     *
     * @return The index.
     */
    private int lastCategoryIndex() {
        if (this.maximumCategoryCount == 0) {
            return -1;
        }
        return Math.min(this.firstCategoryIndex + this.maximumCategoryCount,
                this.underlying.getColumnCount()) - 1;
    }

    /**
     * Returns the index for the specified column key.
     *
     * @param key  the key.
     *
     * @return The column index, or -1 if the key is not recognised.
     */
    @Override
    public int getColumnIndex(Comparable key) {
        int index = this.underlying.getColumnIndex(key);
        if (index >= this.firstCategoryIndex && index <= lastCategoryIndex()) {
            return index - this.firstCategoryIndex;
        }
        return -1;  // we didn't find the key
    }

    /**
     * Returns the column key for a given index.
     *
     * @param column  the column index (zero-based).
     *
     * @return The column key.
     *
     * @throws IndexOutOfBoundsException if {@code row} is out of bounds.
     */
    @Override
    public Comparable getColumnKey(int column) {
        return this.underlying.getColumnKey(column + this.firstCategoryIndex);
    }

    /**
     * Returns the column keys.
     *
     * @return The keys.
     *
     * @see #getColumnKey(int)
     */
    @Override
    public List getColumnKeys() {
        List result = new java.util.ArrayList();
        int last = lastCategoryIndex();
        for (int i = this.firstCategoryIndex; i < last; i++) {
            result.add(this.underlying.getColumnKey(i));
        }
        return Collections.unmodifiableList(result);
    }

    /**
     * Returns the row index for a given key.
     *
     * @param key  the row key.
     *
     * @return The row index, or {@code -1} if the key is unrecognised.
     */
    @Override
    public int getRowIndex(Comparable key) {
        return this.underlying.getRowIndex(key);
    }

    /**
     * Returns the row key for a given index.
     *
     * @param row  the row index (zero-based).
     *
     * @return The row key.
     *
     * @throws IndexOutOfBoundsException if {@code row} is out of bounds.
     */
    @Override
    public Comparable getRowKey(int row) {
        return this.underlying.getRowKey(row);
    }

    /**
     * Returns the row keys.
     *
     * @return The keys.
     */
    @Override
    public List getRowKeys() {
        return this.underlying.getRowKeys();
    }

    /**
     * Returns the value for a pair of keys.
     *
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     *
     * @return The value (possibly {@code null}).
     *
     * @throws UnknownKeyException if either key is not defined in the dataset.
     */
    @Override
    public Number getValue(Comparable rowKey, Comparable columnKey) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getValue(r, c + this.firstCategoryIndex);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the number of columns in the table.
     *
     * @return The column count.
     */
    @Override
    public int getColumnCount() {
        int last = lastCategoryIndex();
        if (last == -1) {
            return 0;
        }
        else {
            return Math.max(last - this.firstCategoryIndex + 1, 0);
        }
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return The row count.
     */
    @Override
    public int getRowCount() {
        return this.underlying.getRowCount();
    }

    /**
     * Returns a value from the table.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getValue(int row, int column) {
        return this.underlying.getValue(row, column + this.firstCategoryIndex);
    }

    /**
     * Returns the percent complete for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The percent complete.
     */
    @Override
    public Number getPercentComplete(Comparable rowKey, Comparable columnKey) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getPercentComplete(r,
                    c + this.firstCategoryIndex);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The percent complete value (possibly {@code null}).
     *
     * @see #getPercentComplete(int, int, int)
     */
    @Override
    public Number getPercentComplete(Comparable rowKey, Comparable columnKey,
            int subinterval) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getPercentComplete(r,
                    c + this.firstCategoryIndex, subinterval);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(Comparable, Comparable, int)
     */
    @Override
    public Number getEndValue(Comparable rowKey, Comparable columnKey,
            int subinterval) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getEndValue(r,
                    c + this.firstCategoryIndex, subinterval);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(int, int, int)
     */
    @Override
    public Number getEndValue(int row, int column, int subinterval) {
        return this.underlying.getEndValue(row,
                column + this.firstCategoryIndex, subinterval);
    }

    /**
     * Returns the percent complete for a given item.
     *
     * @param series  the row index (zero-based).
     * @param category  the column index (zero-based).
     *
     * @return The percent complete.
     */
    @Override
    public Number getPercentComplete(int series, int category) {
        return this.underlying.getPercentComplete(series,
                category + this.firstCategoryIndex);
    }

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The percent complete value (possibly {@code null}).
     *
     * @see #getPercentComplete(Comparable, Comparable, int)
     */
    @Override
    public Number getPercentComplete(int row, int column, int subinterval) {
        return this.underlying.getPercentComplete(row,
                column + this.firstCategoryIndex, subinterval);
    }

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(Comparable, Comparable, int)
     */
    @Override
    public Number getStartValue(Comparable rowKey, Comparable columnKey,
            int subinterval) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getStartValue(r,
                    c + this.firstCategoryIndex, subinterval);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval index (zero-based).
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(int, int, int)
     */
    @Override
    public Number getStartValue(int row, int column, int subinterval) {
        return this.underlying.getStartValue(row,
                column + this.firstCategoryIndex, subinterval);
    }

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The sub-interval count.
     *
     * @see #getSubIntervalCount(int, int)
     */
    @Override
    public int getSubIntervalCount(Comparable rowKey, Comparable columnKey) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getSubIntervalCount(r,
                    c + this.firstCategoryIndex);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The sub-interval count.
     *
     * @see #getSubIntervalCount(Comparable, Comparable)
     */
    @Override
    public int getSubIntervalCount(int row, int column) {
        return this.underlying.getSubIntervalCount(row,
                column + this.firstCategoryIndex);
    }

    /**
     * Returns the start value for the interval for a given series and category.
     *
     * @param rowKey  the series key.
     * @param columnKey  the category key.
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(Comparable, Comparable)
     */
    @Override
    public Number getStartValue(Comparable rowKey, Comparable columnKey) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getStartValue(r,
                    c + this.firstCategoryIndex);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the start value for the interval for a given series and category.
     *
     * @param row  the series (zero-based index).
     * @param column  the category (zero-based index).
     *
     * @return The start value (possibly {@code null}).
     *
     * @see #getEndValue(int, int)
     */
    @Override
    public Number getStartValue(int row, int column) {
        return this.underlying.getStartValue(row,
                column + this.firstCategoryIndex);
    }

    /**
     * Returns the end value for the interval for a given series and category.
     *
     * @param rowKey  the series key.
     * @param columnKey  the category key.
     *
     * @return The end value (possibly {@code null}).
     *
     * @see #getStartValue(Comparable, Comparable)
     */
    @Override
    public Number getEndValue(Comparable rowKey, Comparable columnKey) {
        int r = getRowIndex(rowKey);
        int c = getColumnIndex(columnKey);
        if (c != -1) {
            return this.underlying.getEndValue(r, c + this.firstCategoryIndex);
        }
        else {
            throw new UnknownKeyException("Unknown columnKey: " + columnKey);
        }
    }

    /**
     * Returns the end value for the interval for a given series and category.
     *
     * @param series  the series (zero-based index).
     * @param category  the category (zero-based index).
     *
     * @return The end value (possibly {@code null}).
     */
    @Override
    public Number getEndValue(int series, int category) {
        return this.underlying.getEndValue(series,
                category + this.firstCategoryIndex);
    }

    /**
     * Tests this {@code SlidingGanttCategoryDataset} instance for equality 
     * with an arbitrary object.
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
        if (!(obj instanceof SlidingGanttCategoryDataset)) {
            return false;
        }
        SlidingGanttCategoryDataset that = (SlidingGanttCategoryDataset) obj;
        if (this.firstCategoryIndex != that.firstCategoryIndex) {
            return false;
        }
        if (this.maximumCategoryCount != that.maximumCategoryCount) {
            return false;
        }
        if (!this.underlying.equals(that.underlying)) {
            return false;
        }
        return true;
    }

    /**
     * Returns an independent copy of the dataset.  Note that:
     * <ul>
     * <li>the underlying dataset is only cloned if it implements the
     * {@link PublicCloneable} interface;</li>
     * <li>the listeners registered with this dataset are not carried over to
     * the cloned dataset.</li>
     * </ul>
     *
     * @return An independent copy of the dataset.
     *
     * @throws CloneNotSupportedException if the dataset cannot be cloned for
     *         any reason.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SlidingGanttCategoryDataset clone
                = (SlidingGanttCategoryDataset) super.clone();
        if (this.underlying instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.underlying;
            clone.underlying = (GanttCategoryDataset) pc.clone();
        }
        return clone;
    }

}
