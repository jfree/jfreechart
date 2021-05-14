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
 * MovableChartEntity.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.entity;

import java.awt.Shape;
import java.awt.geom.Point2D;

/**
 * Support for moving chart entities by end-user
 */
public interface MovableChartEntity {
    /**
     * This method can be used to (dis)allow the move or to guide the move to a
     * preferred location.
     * 
     * @param from starting point for the move
     * @param to   end point for the move
     * @return <code>to</code> to allow the move as is (default),
     *         <code>null</code> to disallow the move and another point to guide
     *         the move to an allowed location.
     * @see MovableChartEntity#move(Point2D, Point2D)
     */
    default Point2D tryMove(Point2D from, Point2D to) {
        return to;
    }

    /**
     * Moves this entity to the <code>to</code> location.
     * 
     * @param from starting point for the move
     * @param to   end point for the move
     * @see #tryMove(Point2D, Point2D)
     */
    void move(Point2D from, Point2D to);

    /**
     * Returns the area occupied by the entity (in Java 2D space).
     *
     * @return The area (never <code>null</code>).
     * @see ChartEntity#getArea()
     */
    Shape getArea();
}
