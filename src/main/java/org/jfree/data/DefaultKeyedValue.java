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
 * DefaultKeyedValue.java
 * ----------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand (generics for bug fix to PiePlot);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.Objects;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;

/**
 * A (key, value) pair. This class provides a default implementation of the 
 * {@link KeyedValue} interface.
 * 
 * @param <K> the key type ({@code String} is a good default).
 */
public class DefaultKeyedValue<K extends Comparable<K>> 
        implements KeyedValue<K>, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -7388924517460437712L;

    /** The key. */
    private final K key;

    /** The value. */
    private Number value;

    /**
     * Creates a new (key, value) item.
     *
     * @param key  the key (should be immutable, {@code null} not permitted).
     * @param value  the value ({@code null} permitted).
     */
    public DefaultKeyedValue(K key, Number value) {
        Args.nullNotPermitted(key, "key");
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key.
     *
     * @return The key (never {@code null}).
     */
    @Override
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value.
     *
     * @return The value (possibly {@code null}).
     */
    @Override
    public Number getValue() {
        return this.value;
    }

    /**
     * Sets the value.
     *
     * @param value  the value ({@code null} permitted).
     */
    public synchronized void setValue(Number value) {
        this.value = value;
    }

    /**
     * Tests this key-value pair for equality with an arbitrary object.
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
        if (!(obj instanceof DefaultKeyedValue)) {
            return false;
        }
        DefaultKeyedValue<K> that = (DefaultKeyedValue) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        return Objects.equals(this.value, that.value);
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = (this.key != null ? this.key.hashCode() : 0);
        result = 29 * result + (this.value != null ? this.value.hashCode() : 0);
        return result;
    }

    /**
     * Returns a clone.  It is assumed that both the key and value are
     * immutable objects, so only the references are cloned, not the objects
     * themselves.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException Not thrown by this class, but
     *         subclasses (if any) might.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return (DefaultKeyedValue) super.clone();
    }

    /**
     * Returns a string representing this instance, primarily useful for
     * debugging.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return "(" + this.key.toString() + ", " + this.value.toString() + ")";
    }

}
