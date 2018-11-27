/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2017, by Object Refinery Limited and Contributors.
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
 * Changes:
 * --------
 * 26-Nov-2018 : Made RectangleEdge an enum (TH);
 *
 */

package org.jfree.chart.ui;

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
        RectangleEdge result = null;
        if (edge == RectangleEdge.TOP) {
            result = RectangleEdge.BOTTOM;
        }
        else if (edge == RectangleEdge.BOTTOM) {
            result = RectangleEdge.TOP;
        }
        else if (edge == RectangleEdge.LEFT) {
            result = RectangleEdge.RIGHT;
        }
        else if (edge == RectangleEdge.RIGHT) {
            result = RectangleEdge.LEFT;
        }
        return result;
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
        double result = 0.0;
        if (edge == RectangleEdge.TOP) {
            result = rectangle.getMinY();
        }
        else if (edge == RectangleEdge.BOTTOM) {
            result = rectangle.getMaxY();
        }
        else if (edge == RectangleEdge.LEFT) {
            result = rectangle.getMinX();
        }
        else if (edge == RectangleEdge.RIGHT) {
            result = rectangle.getMaxX();
        }
        return result;
    }
    
}
