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
 * LegendItemCollection.java
 * -------------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.legend;

import org.jfree.chart.legend.LegendItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.jfree.chart.internal.CloneUtils;

/**
 * A collection of legend items.
 */
public class LegendItemCollection implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1365215565589815953L;

    /** Storage for the legend items. */
    private List<LegendItem> items;

    /**
     * Constructs a new legend item collection, initially empty.
     */
    public LegendItemCollection() {
        this.items = new ArrayList<>();
    }

    /**
     * Adds a legend item to the collection.
     *
     * @param item  the item to add.
     */
    public void add(LegendItem item) {
        this.items.add(item);
    }

    /**
     * Adds the legend items from another collection to this collection.
     *
     * @param collection  the other collection ({@code null} not
     *     permitted).
     */
    public void addAll(LegendItemCollection collection) {
        this.items.addAll(collection.items);
    }

    /**
     * Returns a legend item from the collection.
     *
     * @param index  the legend item index (zero-based).
     *
     * @return The legend item.
     */
    public LegendItem get(int index) {
        return this.items.get(index);
    }

    /**
     * Returns the number of legend items in the collection.
     *
     * @return The item count.
     */
    public int getItemCount() {
        return this.items.size();
    }

    /**
     * Returns an iterator that provides access to all the legend items.
     *
     * @return An iterator.
     */
    public Iterator<LegendItem> iterator() {
        return this.items.iterator();
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
        if (!(obj instanceof LegendItemCollection)) {
            return false;
        }
        LegendItemCollection that = (LegendItemCollection) obj;
        if (!this.items.equals(that.items)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode( this.items );
        return hash;
    }

    /**
     * Returns a clone of the collection.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if an item in the collection is not
     *         cloneable.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LegendItemCollection clone = (LegendItemCollection) super.clone();
        clone.items = CloneUtils.cloneList(this.items);
        return clone;
    }

}
