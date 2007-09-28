/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -----------------
 * KeyedObjects.java
 * -----------------
 * (C) Copyright 2003-2007, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 31-Oct-2002 : Version 1 (DG);
 * 11-Jan-2005 : Minor tidy up (DG);
 * 28-Sep-2007 : Clean up equals() method (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.jfree.util.PublicCloneable;

/**
 * A collection of (key, object) pairs.
 */
public class KeyedObjects implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1321582394193530984L;
    
    /** Storage for the data. */
    private List data;

    /**
     * Creates a new collection (initially empty).
     */
    public KeyedObjects() {
        this.data = new java.util.ArrayList();
    }

    /**
     * Returns the number of items (values) in the collection.
     *
     * @return The item count.
     */
    public int getItemCount() {
        return this.data.size();
    }

    /**
     * Returns an object.
     *
     * @param item  the item index (zero-based).
     *
     * @return The object (<code>null</code> if the index is out of range).
     */
    public Object getObject(int item) {
        Object result = null;
        if (item >= 0 && item < this.data.size()) {
            KeyedObject kobj = (KeyedObject) this.data.get(item);
            if (kobj != null) {
                result = kobj.getObject();
            }
        }
        return result;
    }

    /**
     * Returns the key at the specified position in the list.
     *
     * @param index  the item index (zero-based).
     *
     * @return The row key (or <code>null</code>).
     * 
     * @see #getIndex(Comparable)
     */
    public Comparable getKey(int index) {
        Comparable result = null;
        if (index >= 0 && index < this.data.size()) {
            KeyedObject item = (KeyedObject) this.data.get(index);
            if (item != null) {
                result = item.getKey();
            }
        }
        return result;
    }

    /**
     * Returns the index for a given key.
     *
     * @param key  the key.
     *
     * @return The index, or <code>-1</code> if the key is unrecognised.
     * 
     * @see #getKey(int)
     */
    public int getIndex(Comparable key) {
        int result = -1;
        int i = 0;
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            KeyedObject ko = (KeyedObject) iterator.next();
            if (ko.getKey().equals(key)) {
                result = i;
            }
            i++;
        }
        return result;
    }

    /**
     * Returns a list containing all the keys in the list.
     *
     * @return The keys (never <code>null</code>).
     */
    public List getKeys() {
        List result = new java.util.ArrayList();
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            KeyedObject ko = (KeyedObject) iterator.next();
            result.add(ko.getKey());
        }
        return result;
    }

    /**
     * Returns the object for a given key. If the key is not recognised, the 
     * method should return <code>null</code>.
     *
     * @param key  the key.
     *
     * @return The object (possibly <code>null</code>).
     * 
     * @see #addObject(Comparable, Object)
     */
    public Object getObject(Comparable key) {
        return getObject(getIndex(key));
    }

    /**
     * Adds a new object to the collection, or overwrites an existing object.  
     * This is the same as the {@link #setObject(Comparable, Object)} method.
     *
     * @param key  the key.
     * @param object  the object.
     * 
     * @see #getObject(Comparable)
     */
    public void addObject(Comparable key, Object object) {
        setObject(key, object);
    }

    /**
     * Replaces an existing object, or adds a new object to the collection.
     * This is the same as the {@link #addObject(Comparable, Object)} 
     * method.
     *
     * @param key  the key.
     * @param object  the object.
     * 
     * @see #getObject(Comparable)
     */
    public void setObject(Comparable key, Object object) {
        int keyIndex = getIndex(key);
        if (keyIndex >= 0) {
            KeyedObject ko = (KeyedObject) this.data.get(keyIndex);
            ko.setObject(object);
        }
        else {
            KeyedObject ko = new KeyedObject(key, object);
            this.data.add(ko);
        }
    }

    /**
     * Removes a value from the collection.
     *
     * @param index  the index of the item to remove.
     */
    public void removeValue(int index) {
        this.data.remove(index);
    }

    /**
     * Removes a value from the collection.
     *
     * @param key  the key of the item to remove.
     */
    public void removeValue(Comparable key) {
        removeValue(getIndex(key));
    }
    
    /**
     * Returns a clone of this object.  Keys in the list should be immutable
     * and are not cloned.  Objects in the list are cloned only if they
     * implement {@link PublicCloneable}.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    public Object clone() throws CloneNotSupportedException {
        KeyedObjects clone = (KeyedObjects) super.clone();
        clone.data = new java.util.ArrayList();
        Iterator iterator = this.data.iterator();
        while (iterator.hasNext()) {
            KeyedObject ko = (KeyedObject) iterator.next();
            clone.data.add(ko.clone());
        }
        return clone;      
    }
    
    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof KeyedObjects)) {
            return false;
        }
        KeyedObjects that = (KeyedObjects) obj;
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
            Object o1 = getObject(i);
            Object o2 = that.getObject(i);
            if (o1 == null) {
                if (o2 != null) {
                    return false;
                }
            }
            else {
                if (!o1.equals(o2)) {
                    return false;
                }
            }
        }
        return true;

    }
    
}
