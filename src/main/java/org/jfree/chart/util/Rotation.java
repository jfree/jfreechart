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

package org.jfree.chart.util;

/**
 * Represents a direction of rotation ({@code CLOCKWISE} or 
 * {@code ANTICLOCKWISE}).
 */
public enum Rotation {

    /** Clockwise. */
    CLOCKWISE("Rotation.CLOCKWISE", -1.0),

    /** The reverse order renders the primary dataset first. */
    ANTICLOCKWISE("Rotation.ANTICLOCKWISE", 1.0);

    /** The name. */
    private String name;

    /**
     * The factor (-1.0 for {@code CLOCKWISE} and 1.0 for
     * {@code ANTICLOCKWISE}).
     */
    private double factor;

    /**
     * Private constructor.
     *
     * @param name  the name.
     * @param factor  the rotation factor.
     */
    private Rotation(String name, double factor) {
        this.name = name;
        this.factor = factor;
    }

    /**
     * Returns a string representing the object.
     *
     * @return the string (never {@code null}).
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns the rotation factor, which is -1.0 for {@code CLOCKWISE}
     * and 1.0 for {@code ANTICLOCKWISE}.
     *
     * @return the rotation factor.
     */
    public double getFactor() {
        return this.factor;
    }
}

