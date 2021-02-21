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
 */

package org.jfree.chart.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * An enumeration of the vertical alignment types ({@code TOP}, 
 * {@code BOTTOM} and {@code CENTER}).
 */
public final class VerticalAlignment implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7272397034325429853L;
    
    /** Top alignment. */
    public static final VerticalAlignment TOP 
        = new VerticalAlignment("VerticalAlignment.TOP");

    /** Bottom alignment. */
    public static final VerticalAlignment BOTTOM 
        = new VerticalAlignment("VerticalAlignment.BOTTOM");

    /** Center alignment. */
    public static final VerticalAlignment CENTER 
        = new VerticalAlignment("VerticalAlignment.CENTER");

    /** The name. */
    private final String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private VerticalAlignment(String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return the string.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns {@code true} if this object is equal to the specified 
     * object, and {@code false} otherwise.
     *
     * @param o  the other object.
     *
     * @return a boolean.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof VerticalAlignment)) {
            return false;
        }

        VerticalAlignment alignment = (VerticalAlignment) o;
        if (!this.name.equals(alignment.name)) {
            return false;
        }

        return true;
    }
    
    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return The object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        if (this.equals(VerticalAlignment.TOP)) {
            return VerticalAlignment.TOP;
        }
        else if (this.equals(VerticalAlignment.BOTTOM)) {
            return VerticalAlignment.BOTTOM;
        }
        else if (this.equals(VerticalAlignment.CENTER)) {
            return VerticalAlignment.CENTER;
        }
        else {
            return null;  // this should never happen
        }
    }
    
}

