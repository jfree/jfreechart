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

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.internal.Args;
import org.jfree.chart.block.Size2D;

/**
 * Used to indicate an anchor point for a rectangle.
 */
public enum RectangleAnchor {

    /** Center. */
    CENTER,

    /** Top. */
    TOP,

    /** Top-Left. */
    TOP_LEFT,

    /** Top-Right. */
    TOP_RIGHT,

    /** Bottom. */
    BOTTOM,

    /** Bottom-Left. */
    BOTTOM_LEFT,

    /** Bottom-Right. */
    BOTTOM_RIGHT,

    /** Left. */
    LEFT,

    /** Right. */
    RIGHT;

    /**
     * Returns the anchor point relative to the specified rectangle.
     * 
     * @param rectangle  the rectangle (<code>null</code> not permitted).
     * 
     * @return The anchor point (never <code>null</code>). 
     */
    public Point2D getAnchorPoint(Rectangle2D rectangle) {
        Args.nullNotPermitted(rectangle, "rectangle");
        Point2D result = new Point2D.Double();
        switch (this) {
            case CENTER:
                result.setLocation(rectangle.getCenterX(), rectangle.getCenterY());
                break;
            case TOP:
                result.setLocation(rectangle.getCenterX(), rectangle.getMinY());
                break;
            case BOTTOM:
                result.setLocation(rectangle.getCenterX(), rectangle.getMaxY());
                break;
            case LEFT:
                result.setLocation(rectangle.getMinX(), rectangle.getCenterY());
                break;
            case RIGHT:
                result.setLocation(rectangle.getMaxX(), rectangle.getCenterY());
                break;
            case TOP_LEFT:
                result.setLocation(rectangle.getMinX(), rectangle.getMinY());
                break;
            case TOP_RIGHT:
                result.setLocation(rectangle.getMaxX(), rectangle.getMinY());
                break;
            case BOTTOM_LEFT:
                result.setLocation(rectangle.getMinX(), rectangle.getMaxY());
                break;
            case BOTTOM_RIGHT:
                result.setLocation(rectangle.getMaxX(), rectangle.getMaxY());
                break;
            default:
                break;
        }
        return result;
    }
    
    /**
     * Creates a new rectangle with the specified dimensions that is aligned to
     * the given anchor point {@code (anchorX, anchorY)}.
     * 
     * @param dimensions  the dimensions ({@code null} not permitted).
     * @param anchorX  the x-anchor.
     * @param anchorY  the y-anchor.
     * @param anchor  the anchor ({@code null} not permitted).
     * 
     * @return A rectangle.
     */
    public static Rectangle2D createRectangle(Size2D dimensions, 
            double anchorX, double anchorY, RectangleAnchor anchor) {
        Args.nullNotPermitted(dimensions, "dimensions");
        Args.nullNotPermitted(anchor, "anchor");
        Rectangle2D result = null;
        double w = dimensions.getWidth();
        double h = dimensions.getHeight();
        switch (anchor) {
            case CENTER:
                result = new Rectangle2D.Double(anchorX - w / 2.0,
                        anchorY - h / 2.0, w, h);
                break;
            case TOP:
                result = new Rectangle2D.Double(anchorX - w / 2.0, anchorY, w, h);
                break;
            case BOTTOM:
                result = new Rectangle2D.Double(anchorX - w / 2.0, anchorY - h,
                        w, h);
                break;
            case LEFT:
                result = new Rectangle2D.Double(anchorX, anchorY - h / 2.0, w, h);
                break;
            case RIGHT:
                result = new Rectangle2D.Double(anchorX - w, anchorY - h / 2.0,
                        w, h);
                break;
            case TOP_LEFT:
                result = new Rectangle2D.Double(anchorX, anchorY, w, h);
                break;
            case TOP_RIGHT:
                result = new Rectangle2D.Double(anchorX - w, anchorY, w, h);
                break;
            case BOTTOM_LEFT:
                result = new Rectangle2D.Double(anchorX, anchorY - h, w, h);
                break;
            case BOTTOM_RIGHT:
                result = new Rectangle2D.Double(anchorX - w, anchorY - h, w, h);
                break;
            default:
                break;
        }
        return result;
    }
    
}
