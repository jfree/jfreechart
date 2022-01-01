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
 * -----------------------
 * DefaultKeyedValues.java
 * -----------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Thomas Morgner;
 *                   Tracy Hiltbrand (generics for bug fix to PiePlot);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.api.SortOrder;

/**
 * An ordered list of (key, value) items.  This class provides a default
 * implementation of the {@link KeyedValues} interface.
 * 
 * @param <K> the key type ({@code String} is a good default).
 */
public class DefaultKeyedValues<K extends Comparable<K>> 
        implements KeyedValues<K>, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8468154364608194797L;

    /** Storage for the keys. */
    private List<K> keys;

    /** Storage for the values. */
    private List<Number> values;

    /**
     * Contains (key, Integer) mappings, where the Integer is the index for
     * the key in the list.
     */
    private Map<K, Integer> indexMap;

  /**
     * Creates a new collection (initially empty).
     */
    public DefaultKeyedValues() {
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
        this.indexMap = new HashMap<>();
    }

    /**
     * Returns the number of items (values) in the collection.
     *
     * @return The item count.
     */
    @Override
    public int getItemCount() {
        return this.indexMap.size();
    }

    /**
     * Returns a value.
     *
     * @param item  the item of interest (zero-based index).
     *
     * @return The value (possibly {@code null}).
     *
     * @throws IndexOutOfBoundsException if {@code item} is out of bounds.
     */
    @Override
    public Number getValue(int item) {
        return this.values.get(item);
    }

    /**
     * Returns a key.
     *
     * @param index  the item index (zero-based).
     *
     * @return The row key.
     *
     * @throws IndexOutOfBoundsException if {@code item} is out of bounds.
     */
    @Override
    public K getKey(int index) {
        return this.keys.get(index);
    }

    /**
     * Returns the index for a given key.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The index, or {@code -1} if the key is not recognised.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    @Override
    public int getIndex(K key) {
        Args.nullNotPermitted(key, "key");
        final Integer i = this.indexMap.get(key);
        if (i == null) {
            return -1;  // key not found
        }
        return i;
    }

    /**
     * Returns the keys for the values in the collection.
     *
     * @return The keys (never {@code null}).
     */
    @Override
    public List<K> getKeys() {
        return new ArrayList<>(this.keys);
    }

    /**
     * Returns the value for a given key.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The value (possibly {@code null}).
     *
     * @throws UnknownKeyException if the key is not recognised.
     *
     * @see #getValue(int)
     */
    @Override
    public Number getValue(K key) {
        int index = getIndex(key);
        if (index < 0) {
            throw new UnknownKeyException("Key not found: " + key);
        }
        return getValue(index);
    }

    /**
     * Updates an existing value, or adds a new value to the collection.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     *
     * @see #addValue(Comparable, Number)
     */
    public void addValue(K key, double value) {
        addValue(key, Double.valueOf(value));
    }

    /**
     * Adds a new value to the collection, or updates an existing value.
     * This method passes control directly to the
     * {@link #setValue(Comparable, Number)} method.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public void addValue(K key, Number value) {
        setValue(key, value);
    }

    /**
     * Updates an existing value, or adds a new value to the collection.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     */
    public void setValue(K key, double value) {
        setValue(key, Double.valueOf(value));
    }

    /**
     * Updates an existing value, or adds a new value to the collection.
     *
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public void setValue(K key, Number value) {
        Args.nullNotPermitted(key, "key");
        int keyIndex = getIndex(key);
        if (keyIndex >= 0) {
            this.keys.set(keyIndex, key);
            this.values.set(keyIndex, value);
        }
        else {
            this.keys.add(key);
            this.values.add(value);
            this.indexMap.put(key, this.keys.size() - 1);
        }
    }

    /**
     * Inserts a new value at the specified position in the dataset or, if
     * there is an existing item with the specified key, updates the value
     * for that item and moves it to the specified position.
     *
     * @param position  the position (in the range 0 to getItemCount()).
     * @param key  the key ({@code null} not permitted).
     * @param value  the value.
     *
     * @since 1.0.6
     */
    public void insertValue(int position, K key, double value) {
        insertValue(position, key, Double.valueOf(value));
    }

    /**
     * Inserts a new value at the specified position in the dataset or, if
     * there is an existing item with the specified key, updates the value
     * for that item and moves it to the specified position.
     *
     * @param position  the position (in the range 0 to getItemCount()).
     * @param key  the key ({@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     *
     * @since 1.0.6
     */
    public void insertValue(int position, K key, Number value) {
        if (position < 0 || position > getItemCount()) {
            throw new IllegalArgumentException("'position' out of bounds.");
        }
        Args.nullNotPermitted(key, "key");
        int pos = getIndex(key);
        if (pos == position) {
            this.keys.set(pos, key);
            this.values.set(pos, value);
        }
        else {
            if (pos >= 0) {
                this.keys.remove(pos);
                this.values.remove(pos);
            }

            this.keys.add(position, key);
            this.values.add(position, value);
            rebuildIndex();
        }
    }

    /**
     * Rebuilds the key to indexed-position mapping after an positioned insert
     * or a remove operation.
     */
    private void rebuildIndex () {
        this.indexMap.clear();
        for (int i = 0; i < this.keys.size(); i++) {
            final K key = this.keys.get(i);
            this.indexMap.put(key, i);
        }
    }

    /**
     * Removes a value from the collection.
     *
     * @param index  the index of the item to remove (in the range
     *     {@code 0} to {@code getItemCount() -1}).
     *
     * @throws IndexOutOfBoundsException if {@code index} is not within
     *     the specified range.
     */
    public void removeValue(int index) {
        this.keys.remove(index);
        this.values.remove(index);
        rebuildIndex();
    }

    /**
     * Removes a value from the collection.
     *
     * @param key  the item key ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     * @throws UnknownKeyException if {@code key} is not recognised.
     */
    public void removeValue(K key) {
        int index = getIndex(key);
        if (index < 0) {
            throw new UnknownKeyException("The key (" + key
                    + ") is not recognised.");
        }
        removeValue(index);
    }

    /**
     * Clears all values from the collection.
     *
     * @since 1.0.2
     */
    public void clear() {
        this.keys.clear();
        this.values.clear();
        this.indexMap.clear();
    }

    /**
     * Sorts the items in the list by key.
     *
     * @param order  the sort order ({@code null} not permitted).
     */
    public void sortByKeys(SortOrder order) {
        final int size = this.keys.size();
        final DefaultKeyedValue<K>[] data = new DefaultKeyedValue[size];

        for (int i = 0; i < size; i++) {
            data[i] = new DefaultKeyedValue(this.keys.get(i), this.values.get(i));
        }

        Comparator comparator = new KeyedValueComparator(
                KeyedValueComparatorType.BY_KEY, order);
        Arrays.sort(data, comparator);
        clear();

        for (int i = 0; i < data.length; i++) {
            final DefaultKeyedValue<K> value = data[i];
            addValue(value.getKey(), value.getValue());
        }
    }

    /**
     * Sorts the items in the list by value.  If the list contains
     * {@code null} values, they will sort to the end of the list,
     * irrespective of the sort order.
     *
     * @param order  the sort order ({@code null} not permitted).
     */
    public void sortByValues(SortOrder order) {
        final int size = this.keys.size();
        final DefaultKeyedValue[] data = new DefaultKeyedValue[size];
        for (int i = 0; i < size; i++) {
            data[i] = new DefaultKeyedValue((Comparable) this.keys.get(i),
                    (Number) this.values.get(i));
        }

        Comparator comparator = new KeyedValueComparator(
                KeyedValueComparatorType.BY_VALUE, order);
        Arrays.sort(data, comparator);

        clear();
        for (int i = 0; i < data.length; i++) {
            final DefaultKeyedValue<K> value = data[i];
            addValue(value.getKey(), value.getValue());
        }
    }

    /**
     * Tests if this object is equal to another.
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

        if (!(obj instanceof KeyedValues)) {
            return false;
        }

        KeyedValues that = (KeyedValues) obj;
        int count = getItemCount();
        if (count != that.getItemCount()) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            Comparable k1 = getKey(i);
            Comparable k2 = that.getKey(i);
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
        return (this.keys != null ? this.keys.hashCode() : 0);
    }

    /**
     * Returns a clone.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  this class will not throw this
     *         exception, but subclasses might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultKeyedValues clone = (DefaultKeyedValues) super.clone();
        clone.keys = new ArrayList<>(this.keys);
        clone.values = new ArrayList<>(this.values);
        clone.indexMap = new HashMap(this.indexMap);
        return clone;
    }

}
