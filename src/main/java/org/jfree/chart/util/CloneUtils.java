/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * (C) Copyright 2014-2021, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */
package org.jfree.chart.util;

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
     * Returns a clone of the specified object, if it can be cloned, otherwise
     * throws a {@code CloneNotSupportedException}.
     *
     * @param object the object to clone ({@code null} not permitted).
     * @return A clone of the specified object.
     * @throws CloneNotSupportedException if the object cannot be cloned.
     */
    public static Object clone(Object object)
        throws CloneNotSupportedException {
        if (object == null) {
            throw new IllegalArgumentException("Null 'object' argument.");
        }
        if (object instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) object;
            return pc.clone();
        }
        else {
            try {
                Method method = object.getClass().getMethod("clone",
                        (Class[]) null);
                if (Modifier.isPublic(method.getModifiers())) {
                    return method.invoke(object, (Object[]) null);
                }
            }
            catch (NoSuchMethodException e) {
                throw new CloneNotSupportedException("Object without clone() method is impossible.");
            }
            catch (IllegalAccessException e) {
                throw new CloneNotSupportedException("Object.clone(): unable to call method.");
            }
            catch (InvocationTargetException e) {
                throw new CloneNotSupportedException("Object without clone() method is impossible.");
            }
        }
        throw new CloneNotSupportedException("Failed to clone.");
    }

    /**
     * Returns a list containing cloned copies of the items in the source
     * list.
     * 
     * @param source  the source list ({@code null} not permitted).
     * 
     * @return A new list. 
     */
    public static List<?> cloneList(List<?> source) {
        Args.nullNotPermitted(source, "source");
        List result = new ArrayList();
        for (Object obj: source) {
            if (obj == null) {
                result.add(null);
            } else if (obj.getClass() == String.class) {
                result.add(obj);
            } else {
                try {
                    result.add(ObjectUtils.clone(obj));
                } catch (CloneNotSupportedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        return result;
    }
    
    /**
     * Returns a new map that contains the same keys and cloned copied of the
     * values.
     * 
     * @param source  the source map ({@code null} not permitted).
     * 
     * @return A new map. 
     */
    public static Map cloneMapValues(Map source) {
        Args.nullNotPermitted(source, "source");
        Map result = new HashMap();
        for (Object key : source.keySet()) {
            Object value = source.get(key);
            if (value != null) {
                try {
                    result.put(key, ObjectUtils.clone(value));
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
