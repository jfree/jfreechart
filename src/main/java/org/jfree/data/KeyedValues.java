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
 * ----------------
 * KeyedValues.java
 * ----------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand (generics for bug fix to PiePlot);
 *
 */

package org.jfree.data;

import java.util.List;

/**
 * An ordered list of (key, value) items where the keys are unique and
 * non-{@code null}.
 *
 * @see Values
 * @see DefaultKeyedValues
 */
public interface KeyedValues<K extends Comparable<K>> extends Values {

    /**
     * Returns the key associated with the item at a given position.  Note
     * that some implementations allow re-ordering of the data items, so the
     * result may be transient.
     *
     * @param index  the item index (in the range {@code 0} to
     *     {@code getItemCount() - 1}).
     *
     * @return The key (never {@code null}).
     *
     * @throws IndexOutOfBoundsException if {@code index} is not in the
     *     specified range.
     */
    K getKey(int index);

    /**
     * Returns the index for a given key.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The index, or {@code -1} if the key is unrecognised.
     *
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    int getIndex(K key);

    /**
     * Returns the keys for the values in the collection.  Note that you can
     * access the values in this collection by key or by index.  For this
     * reason, the key order is important - this method should return the keys
     * in order.  The returned list may be unmodifiable.
     *
     * @return The keys (never {@code null}).
     */
    List<K> getKeys();

    /**
     * Returns the value for a given key.
     *
     * @param key  the key.
     *
     * @return The value (possibly {@code null}).
     *
     * @throws UnknownKeyException if the key is not recognised.
     */
    Number getValue(K key);

}
