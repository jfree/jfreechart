/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ------------------
 * TestUtilities.java
 * ------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 08-Jun-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.junit;

import java.util.Collection;
import java.util.Iterator;

/**
 * Some utility methods for use by the testing code.
 */
public class TestUtilities {

    /**
     * Returns <code>true</code> if the collections contains any object that
     * is an instance of the specified class, and <code>false</code> otherwise.
     *
     * @param collection  the collection.
     * @param c  the class.
     *
     * @return A boolean.
     */
    public static boolean containsInstanceOf(Collection collection, Class c) {
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj != null && obj.getClass().equals(c)) {
                return true;
            }
        }
        return false;
    }

}
