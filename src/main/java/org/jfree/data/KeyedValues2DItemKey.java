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
 * KeyedValues2DItemKey.java
 * -------------------------
 * (C) Copyright 2014-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.internal.Args;

/**
 * An object that references one data item in a {@link KeyedValues2D} data
 * structure.  Instances of this class are immutable (subject to the caller
 * using series, row and column keys that are immutable).
 * 
 * @param <R> the row key type.
 * @param <C> the column key type.
 * @since 1.3
 */
public class KeyedValues2DItemKey<R extends Comparable<R>, 
        C extends Comparable<C>> implements ItemKey, 
        Comparable<KeyedValues2DItemKey<R, C>>, Serializable {
    
    /** The row key. */
    R rowKey;
    
    /** The column key. */
    C columnKey;
    
    /**
     * Creates a new instance.
     * 
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public KeyedValues2DItemKey(R rowKey, C columnKey) {
        Args.nullNotPermitted(rowKey, "rowKey");
        Args.nullNotPermitted(columnKey, "columnKey");
        this.rowKey = rowKey;
        this.columnKey = columnKey;
    }
    
    /**
     * Returns the row key.
     * 
     * @return The row key (never {@code null}).
     */
    public R getRowKey() {
        return this.rowKey;
    }
    
    /**
     * Returns the column key.
     * 
     * @return The column key (never {@code null}).
     */
    public C getColumnKey() {
        return this.columnKey;
    }
    
    @Override
    public int compareTo(KeyedValues2DItemKey<R, C> key) {
        int result = this.rowKey.compareTo(key.rowKey);
        if (result == 0) {
            result = this.columnKey.compareTo(key.columnKey);
        }
        return result;
    }
    
    /**
     * Tests this key for equality with an arbitrary object.
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
        if (!(obj instanceof KeyedValues2DItemKey)) {
            return false;
        }
        KeyedValues2DItemKey that = (KeyedValues2DItemKey) obj;
        if (!this.rowKey.equals(that.rowKey)) {
            return false;
        }
        if (!this.columnKey.equals(that.columnKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.rowKey);
        hash = 17 * hash + Objects.hashCode(this.columnKey);
        return hash;
    }

    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"rowKey\": \"").append(this.rowKey.toString());
        sb.append("\", ");
        sb.append("\"columnKey\": \"").append(this.columnKey.toString());
        sb.append("\"}");
        return sb.toString();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Values2DItemKey[row=");
        sb.append(rowKey.toString()).append(",column=");
        sb.append(columnKey.toString());
        sb.append("]");
        return sb.toString();
    }

}