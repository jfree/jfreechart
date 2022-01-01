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
 * DefaultPieDataset.java
 * ----------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Sam (oldman);
 *                   Tracy Hiltbrand (generics for bug fix to PiePlot);
 */

package org.jfree.data.general;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.api.SortOrder;

import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.KeyedValues;
import org.jfree.data.UnknownKeyException;

/**
 * A default implementation of the {@link PieDataset} interface.
 * 
 * @param <K> Key type for PieDataset
 */
public class DefaultPieDataset<K extends Comparable<K>> extends AbstractDataset
        implements PieDataset<K>, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2904745139106540618L;

    /** Storage for the data. */
    private DefaultKeyedValues<K> data;

    /**
     * Constructs a new dataset, initially empty.
     */
    public DefaultPieDataset() {
        this.data = new DefaultKeyedValues<>();
    }

    /**
     * Creates a new dataset by copying data from a {@link KeyedValues}
     * instance.
     *
     * @param source  the data ({@code null} not permitted).
     */
    public DefaultPieDataset(KeyedValues<K> source) {
        Args.nullNotPermitted(source, "source");
        this.data = new DefaultKeyedValues<>();
        for (int i = 0; i < source.getItemCount(); i++) {
            this.data.addValue(source.getKey(i), source.getValue(i));
        }
    }

    /**
     * Returns the number of items in the dataset.
     *
     * @return The item count.
     */
    @Override
    public int getItemCount() {
        return this.data.getItemCount();
    }

    /**
     * Returns the categories in the dataset.  The returned list is
     * unmodifiable.
     *
     * @return The categories in the dataset.
     */
    @Override
    public List<K> getKeys() {
        return Collections.unmodifiableList(this.data.getKeys());
    }

    /**
     * Returns the key for the specified item, or {@code null}.
     *
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount() - 1}).
     *
     * @return The key, or {@code null}.
     *
     * @throws IndexOutOfBoundsException if {@code item} is not in the
     *     specified range.
     */
    @Override
    public K getKey(int item) {
        return this.data.getKey(item);
    }

    /**
     * Returns the index for a key, or -1 if the key is not recognised.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The index, or {@code -1} if the key is unrecognised.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    @Override
    public int getIndex(K key) {
        return this.data.getIndex(key);
    }

    /**
     * Returns a value.
     *
     * @param item  the value index.
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getValue(int item) {
        return this.data.getValue(item);
    }

    /**
     * Returns the data value associated with a key.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The value (possibly {@code null}).
     *
     * @throws UnknownKeyException if the key is not recognised.
     */
    @Override
    public Number getValue(K key) {
        Args.nullNotPermitted(key, "key");
        return this.data.getValue(key);
    }

    /**
     * Sets the data value for a key and sends a {@link DatasetChangeEvent} to
     * all registered listeners.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    public void setValue(K key, Number value) {
        this.data.setValue(key, value);
        fireDatasetChanged();
    }

    /**
     * Sets the data value for a key and sends a {@link DatasetChangeEvent} to
     * all registered listeners.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    public void setValue(K key, double value) {
        setValue(key, Double.valueOf(value));
    }

    /**
     * Inserts a new value at the specified position in the dataset or, if
     * there is an existing item with the specified key, updates the value
     * for that item and moves it to the specified position.  After the change
     * is made, this methods sends a {@link DatasetChangeEvent} to all
     * registered listeners.
     *
     * @param position  the position (in the range 0 to getItemCount()).
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     *
     * @since 1.0.6
     */
    public void insertValue(int position, K key, double value) {
        insertValue(position, key, Double.valueOf(value));
    }

    /**
     * Inserts a new value at the specified position in the dataset or, if
     * there is an existing item with the specified key, updates the value
     * for that item and moves it to the specified position.  After the change
     * is made, this methods sends a {@link DatasetChangeEvent} to all
     * registered listeners.
     *
     * @param position  the position (in the range 0 to getItemCount()).
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     *
     * @since 1.0.6
     */
    public void insertValue(int position, K key, Number value) {
        this.data.insertValue(position, key, value);
        fireDatasetChanged();
    }

    /**
     * Removes an item from the dataset and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    public void remove(K key) {
        this.data.removeValue(key);
        fireDatasetChanged();
    }

    /**
     * Clears all data from this dataset and sends a {@link DatasetChangeEvent}
     * to all registered listeners (unless the dataset was already empty).
     *
     * @since 1.0.2
     */
    public void clear() {
        if (getItemCount() > 0) {
            this.data.clear();
            fireDatasetChanged();
        }
    }

    /**
     * Sorts the dataset's items by key and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param order  the sort order ({@code null} not permitted).
     *
     * @since 1.0.3
     */
    public void sortByKeys(SortOrder order) {
        this.data.sortByKeys(order);
        fireDatasetChanged();
    }

    /**
     * Sorts the dataset's items by value and sends a {@link DatasetChangeEvent}
     * to all registered listeners.
     *
     * @param order  the sort order ({@code null} not permitted).
     *
     * @since 1.0.3
     */
    public void sortByValues(SortOrder order) {
        this.data.sortByValues(order);
        fireDatasetChanged();
    }

    /**
     * Tests if this object is equal to another.
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

        if (!(obj instanceof PieDataset)) {
            return false;
        }
        PieDataset<K> that = (PieDataset) obj;
        int count = getItemCount();
        if (that.getItemCount() != count) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            K k1 = getKey(i);
            K k2 = that.getKey(i);
            if (!k1.equals(k2)) {
                return false;
            }

            Number v1 = getValue(i);
            Number v2 = that.getValue(i);
            if (v1 == null) {
                if (v2 != null) {
                    return false;
                }
            }
            else {
                if (!v1.equals(v2)) {
                    return false;
                }
            }
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
        return this.data.hashCode();
    }

    /**
     * Returns a clone of the dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException This class will not throw this
     *         exception, but subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultPieDataset<K> clone = (DefaultPieDataset) super.clone();
        clone.data = CloneUtils.clone(this.data);
        return clone;
    }

}
