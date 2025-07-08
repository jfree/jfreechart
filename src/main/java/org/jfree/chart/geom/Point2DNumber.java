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
 * ---------------------
 * Point2DNumber.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.geom;

import java.awt.geom.Point2D;

/**
 * The {@link Point2DNumber} class defines a point that avoids rounding errors
 * by referencing the original {@link Number} types.
 */
public class Point2DNumber extends Point2D {
    /**
     * The X coordinate of this <code>Point2D</code>.
     */
    public Number x;

    /**
     * The Y coordinate of this <code>Point2D</code>.
     */
    public Number y;

    /**
     * Constructs and initializes a <code>Point2D</code> with the specified
     * coordinates.
     *
     * @param x the X coordinate of the newly constructed <code>Point2D</code>
     * @param y the Y coordinate of the newly constructed <code>Point2D</code>
     * @since 1.2
     */
    public Point2DNumber(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x == null ? java.lang.Double.NaN : x.doubleValue();
    }

    @Override
    public double getY() {
        return y == null ? java.lang.Double.NaN : y.doubleValue();
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the location of this <code>Point2D</code> to the specified
     * {@link Number} coordinates.
     *
     * @param x the new X coordinate of this {@code Point2D}
     * @param y the new Y coordinate of this {@code Point2D}
     */
    public void setLocation(Number x, Number y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((x == null) ? 0 : x.hashCode());
        result = prime * result + ((y == null) ? 0 : y.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2DNumber other = (Point2DNumber) obj;
        if (x == null) {
            if (other.x != null)
                return false;
        } else if (!x.equals(other.x))
            return false;
        if (y == null) {
            if (other.y != null)
                return false;
        } else if (!y.equals(other.y))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Point2DNumber[" + x + ", " + y + "]";
    }
}