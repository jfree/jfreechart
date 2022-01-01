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
 * ---------------
 * CloneUtils.java
 * ---------------
 * (C) Copyright 2014-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */
package org.jfree.chart.internal;

import org.jfree.chart.api.PublicCloneable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilities for cloning.
 */
public class CloneUtils {
    
    /**
     * Returns a copy of the specified object, which should be a clone if the
     * object is cloneable otherwise a reference to the specified object is
     * returned.  If the object is {@code null} this method returns {@code null}.
     *
     * @param object the object to copy ({@code null} permitted).
     * 
     * @param <T>  the object type.
     * 
     * @return A clone of the specified object, or {@code null}.
     * 
     * @throws CloneNotSupportedException if the object cannot be cloned.
     */
    public static <T> T copy(T object) throws CloneNotSupportedException {
        if (object == null) {
            return null;
        }
        if (object instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) object;
            return (T) pc.clone();
        } else {
            try {
                Method method = object.getClass().getMethod("clone",
                        (Class[]) null);
                if (Modifier.isPublic(method.getModifiers())) {
                    return (T) method.invoke(object, (Object[]) null);
                } else {
                    return object;
                }
            } catch (NoSuchMethodException e) {
                return object;
            } catch (IllegalAccessException e) {
                throw new CloneNotSupportedException("Object.clone(): unable to call method.");
            } catch (InvocationTargetException e) {
                throw new CloneNotSupportedException("Object without clone() method is impossible.");
            }
        }
    }

    /**
     * Returns a clone of the specified object, if it can be cloned, otherwise
     * throws a {@code CloneNotSupportedException}.  If the object is 
     * {@code null} this method returns {@code null}.
     *
     * @param object the object to clone ({@code null} permitted).
     * 
     * @param <T>  the object type.
     * 
     * @return A clone of the specified object, or {@code null}.
     * 
     * @throws CloneNotSupportedException if the object cannot be cloned.
     */
    public static <T> T clone(T object) throws CloneNotSupportedException {
        if (object == null) {
            return null;
        }
        if (object instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) object;
            return (T) pc.clone();
        } else {
            try {
                Method method = object.getClass().getMethod("clone",
                        (Class[]) null);
                if (Modifier.isPublic(method.getModifiers())) {
                    return (T) method.invoke(object, (Object[]) null);
                }
            } catch (NoSuchMethodException e) {
                throw new CloneNotSupportedException("Object without clone() method is impossible.");
            } catch (IllegalAccessException e) {
                throw new CloneNotSupportedException("Object.clone(): unable to call method.");
            } catch (InvocationTargetException e) {
                throw new CloneNotSupportedException("Object without clone() method is impossible.");
            }
        }
        throw new CloneNotSupportedException("Failed to clone.");
    }

    /**
     * Returns a list containing copies (clones if possible) of the items in 
     * the source list.
     * 
     * @param source  the source list ({@code null} not permitted).
     * 
     * @param <T>  the type of the list items.
     * 
     * @return A new list. 
     */
    public static <T> List<T>cloneList(List<T> source) {
        Args.nullNotPermitted(source, "source");
        List<T> result = new ArrayList<>();
        for (Object obj: source) {
            try {
                result.add((T) copy(obj));
            } catch (CloneNotSupportedException ex) {
                throw new RuntimeException(ex);
            }
        }
        return result;
    }
    
    /**
     * Returns a new map that contains the same keys and copies of the
     * values from the source map.
     * 
     * @param source  the source map ({@code null} not permitted).
     * 
     * @param <K>  the type for the keys.
     * @param <V>  the type for the values.
     * 
     * @return A new map. 
     */
    public static <K, V> Map<K, V> cloneMapValues(Map<K, V> source) {
        Args.nullNotPermitted(source, "source");
        Map<K, V> result = new HashMap<>();
        for (K key : source.keySet()) {
            V value = source.get(key);
            if (value != null) {
                try {
                    result.put(key, copy(value));
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                result.put(key, null);
            }
        }
        return result;
    }
   
}
