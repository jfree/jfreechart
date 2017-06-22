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
 * -------------
 * PaintMap.java
 * -------------
 * (C) Copyright 2006-2016, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 27-Sep-2006 : Version 1 (DG);
 * 17-Jan-2007 : Changed TreeMap to HashMap, so that different classes that
 *               implement Comparable can be used as keys (DG);
 * 02-Jul-2013 : Use ParamChecks class (DG);
 *
 */

package org.jfree.chart;

import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.SerialUtils;

/**
 * A storage structure that maps {@code Comparable} instances with
 * {@code Paint} instances.
 * <br><br>
 * To support cloning and serialization, you should only use keys that are
 * cloneable and serializable.  Special handling for the {@code Paint}
 * instances is included in this class.
 *
 * @since 1.0.3
 */
public class PaintMap implements Cloneable, Serializable {

    /** For serialization. */
    static final long serialVersionUID = -4639833772123069274L;

    /** Storage for the keys and values. */
    private transient Map store;

    /**
     * Creates a new (empty) map.
     */
    public PaintMap() {
        this.store = new HashMap();
    }

    /**
     * Returns the paint associated with the specified key, or
     * {@code null}.
     *
     * @param key  the key ({@code null} not permitted).
     *
     * @return The paint, or {@code null}.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    public Paint getPaint(Comparable key) {
        Args.nullNotPermitted(key, "key");
        return (Paint) this.store.get(key);
    }

    /**
     * Returns {@code true} if the map contains the specified key, and
     * {@code false} otherwise.
     *
     * @param key  the key.
     *
     * @return {@code true} if the map contains the specified key, and
     * {@code false} otherwise.
     */
    public boolean containsKey(Comparable key) {
        return this.store.containsKey(key);
    }

    /**
     * Adds a mapping between the specified {@code key} and
     * {@code Paint} values.
     *
     * @param key  the key ({@code null} not permitted).
     * @param paint  the paint.
     *
     * @throws IllegalArgumentException if {@code key} is
     *     {@code null}.
     */
    public void put(Comparable key, Paint paint) {
        Args.nullNotPermitted(key, "key");
        this.store.put(key, paint);
    }

    /**
     * Resets the map to empty.
     */
    public void clear() {
        this.store.clear();
    }

    /**
     * Tests this map for equality with an arbitrary object.
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
        if (!(obj instanceof PaintMap)) {
            return false;
        }
        PaintMap that = (PaintMap) obj;
        if (this.store.size() != that.store.size()) {
            return false;
        }
        Set keys = this.store.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Comparable key = (Comparable) iterator.next();
            Paint p1 = getPaint(key);
            Paint p2 = that.getPaint(key);
            if (!PaintUtils.equal(p1, p2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a clone of this {@code PaintMap}.
     *
     * @return A clone of this instance.
     *
     * @throws CloneNotSupportedException if any key is not cloneable.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        PaintMap clone = (PaintMap) super.clone();
        clone.store = new HashMap();
        clone.store.putAll(this.store);
        // TODO: I think we need to make sure the keys are actually cloned,
        // whereas the paint instances are always immutable so they're OK
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.store.size());
        Set keys = this.store.keySet();
        Iterator iterator = keys.iterator();
        while (iterator.hasNext()) {
            Comparable key = (Comparable) iterator.next();
            stream.writeObject(key);
            Paint paint = getPaint(key);
            SerialUtils.writePaint(paint, stream);
        }
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.store = new HashMap();
        int keyCount = stream.readInt();
        for (int i = 0; i < keyCount; i++) {
            Comparable key = (Comparable) stream.readObject();
            Paint paint = SerialUtils.readPaint(stream);
            this.store.put(key, paint);
        }
    }

}
