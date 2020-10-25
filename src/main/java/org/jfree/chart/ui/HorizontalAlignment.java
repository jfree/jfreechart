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
 */

package org.jfree.chart.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * An enumeration of the horizontal alignment types ({@code LEFT}, 
 * {@code RIGHT} and {@code CENTER}).
 */
public final class HorizontalAlignment implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -8249740987565309567L;
    
    /** Left alignment. */
    public static final HorizontalAlignment LEFT 
        = new HorizontalAlignment("HorizontalAlignment.LEFT");

    /** Right alignment. */
    public static final HorizontalAlignment RIGHT 
        = new HorizontalAlignment("HorizontalAlignment.RIGHT");

    /** Center alignment. */
    public static final HorizontalAlignment CENTER 
        = new HorizontalAlignment("HorizontalAlignment.CENTER");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private HorizontalAlignment(String name) {
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
     * Returns {@code true} if this object is equal to the specified 
     * object, and {@code false} otherwise.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HorizontalAlignment)) {
            return false;
        }
        final HorizontalAlignment that = (HorizontalAlignment) obj;
        if (!this.name.equals(that.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
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
        HorizontalAlignment result = null;
        if (this.equals(HorizontalAlignment.LEFT)) {
            result = HorizontalAlignment.LEFT;
        }
        else if (this.equals(HorizontalAlignment.RIGHT)) {
            result = HorizontalAlignment.RIGHT;
        }
        else if (this.equals(HorizontalAlignment.CENTER)) {
            result = HorizontalAlignment.CENTER;
        }
        return result;
    }
    
}