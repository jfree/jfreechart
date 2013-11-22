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
 * -----------------------------
 * KeyedValueComparatorType.java
 * -----------------------------
 * (C) Copyright 2003-2012, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 05-Mar-2003 : Version 1 (DG);
 * 23-Sep-2012 : Make this class serializable (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;

/**
 * Used to indicate the type of a {@link KeyedValueComparator} : 'by key' or
 * 'by value'.
 */
public final class KeyedValueComparatorType implements Serializable {

    /** An object representing 'by key' sorting. */
    public static final KeyedValueComparatorType BY_KEY
        = new KeyedValueComparatorType("KeyedValueComparatorType.BY_KEY");

    /** An object representing 'by value' sorting. */
    public static final KeyedValueComparatorType BY_VALUE
        = new KeyedValueComparatorType("KeyedValueComparatorType.BY_VALUE");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private KeyedValueComparatorType(String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns <code>true</code> if this object is equal to the specified
     * object, and <code>false</code> otherwise.
     *
     * @param o  the other object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyedValueComparatorType)) {
            return false;
        }

        KeyedValueComparatorType type = (KeyedValueComparatorType) o;
        if (!this.name.equals(type.name)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }
}

