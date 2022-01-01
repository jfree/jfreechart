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
 */

package org.jfree.chart.api;

import java.awt.geom.Rectangle2D;

/**
 * Used to indicate the edge of a rectangle.
 */
public enum RectangleEdge {

    /** Top. */
    TOP,

    /** Bottom. */
    BOTTOM,

    /** Left. */
    LEFT,

    /** Right. */
    RIGHT;

    /**
     * Returns {@code true} if the edge is {@code TOP} or 
     * {@code BOTTOM}, and {@code false} otherwise.
     * 
     * @param edge  the edge.
     * 
     * @return A boolean.
     */
    public static boolean isTopOrBottom(RectangleEdge edge) {
        return (edge == RectangleEdge.TOP || edge == RectangleEdge.BOTTOM);    
    }
    
    /**
     * Returns {@code true} if the edge is {@code LEFT} or 
     * {@code RIGHT}, and {@code false} otherwise.
     * 
     * @param edge  the edge.
     * 
     * @return A boolean.
     */
    public static boolean isLeftOrRight(RectangleEdge edge) {
        return (edge == RectangleEdge.LEFT || edge == RectangleEdge.RIGHT);    
    }

    /**
     * Returns the opposite edge.
     * 
     * @param edge  an edge.
     * 
     * @return The opposite edge.
     */
    public static RectangleEdge opposite(RectangleEdge edge) {
        switch (edge) {
            case TOP:
                return RectangleEdge.BOTTOM;
            case BOTTOM:
                return RectangleEdge.TOP;
            case LEFT:
                return RectangleEdge.RIGHT;
            case RIGHT:
                return RectangleEdge.LEFT;
            default:
                return null;
        }
    }
    
    /**
     * Returns the x or y coordinate of the specified edge.
     * 
     * @param rectangle  the rectangle.
     * @param edge  the edge.
     * 
     * @return The coordinate.
     */
    public static double coordinate(Rectangle2D rectangle, RectangleEdge edge) {
        switch (edge) {
            case TOP:
                return rectangle.getMinY();
            case BOTTOM:
                return rectangle.getMaxY();
            case LEFT:
                return rectangle.getMinX();
            case RIGHT:
                return rectangle.getMaxX();
            default:
                return 0.0;
        }
    }
    
}
