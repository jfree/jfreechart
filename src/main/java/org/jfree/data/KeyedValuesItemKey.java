/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * KeyedValuesItemKey.java
 * -----------------------
 * (C) Copyright 2014-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.data;

import java.io.Serializable;
import org.jfree.chart.util.Args;

/**
 * A key that references one item in a {@link KeyedValues} data structure.
 */
public class KeyedValuesItemKey implements ItemKey, Serializable {
    
    /** The key for the item. */
    Comparable<? extends Object> key;
    
    /**
     * Creates a new instance.
     * 
     * @param key  the key ({@code null} not permitted).
     */
    public KeyedValuesItemKey(Comparable<? extends Object> key) {
        Args.nullNotPermitted(key, "key");
        this.key = key;
    }
    
    /**
     * Returns the key.
     * 
     * @return The key (never {@code null}). 
     */
    public Comparable<?> getKey() {
        return this.key;
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyedValuesItemKey)) {
            return false;
        }
        KeyedValuesItemKey that = (KeyedValuesItemKey) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toJSONString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"key\": \"").append(this.key.toString()).append("\"}");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KeyedValuesItemKey[");
        sb.append(this.key.toString());
        sb.append("]");
        return sb.toString();
    }
}
