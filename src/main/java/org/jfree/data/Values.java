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
 * -----------
 * Values.java
 * -----------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

/**
 * An interface through which (single-dimension) data values can be accessed.
 */
public interface Values {

    /**
     * Returns the number of items (values) in the collection.
     *
     * @return The item count (possibly zero).
     */
    int getItemCount();

    /**
     * Returns the value with the specified index.
     *
     * @param index  the item index (in the range {@code 0} to
     *     {@code getItemCount() -1}).
     *
     * @return The value (possibly {@code null}).
     *
     * @throws IndexOutOfBoundsException if {@code index} is not in the
     *     specified range.
     */
    Number getValue(int index);

}
