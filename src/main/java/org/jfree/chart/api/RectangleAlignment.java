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
import org.jfree.chart.internal.Args;
    
/**
 * Used to indicate how to align one rectangle with another rectangle.
 */
public enum RectangleAlignment {

    /** Fill the frame */
    FILL,
    
    /** Fill the height of the frame. */
    FILL_VERTICAL,
    
    /** Fill the width of the frame. */
    FILL_HORIZONTAL,
    
    /** Align to the top left of the frame. */
    TOP_LEFT,
    
    /** Align to the top of the frame. */
    TOP_CENTER, 
    
    /** Align to the top right of the frame. */
    TOP_RIGHT, 
    
    /** Align to the left side of the frame, centered vertically. */
    CENTER_LEFT,

    /** Align to the center of the frame. */
    CENTER,

    /** Align to the right side of the frame, centered vertically. */    
    CENTER_RIGHT,
    
    /** Align to the bottom left of the frame. */    
    BOTTOM_LEFT,
    
    /** Align to the bottom of the frame. */    
    BOTTOM_CENTER,
    
    /** Align to the bottom right of the frame. */    
    BOTTOM_RIGHT;

    /**
     * Returns the anchor point relative to the specified rectangle.
     * 
     * @param rect  the rectangle to align ({@code null} not permitted).
     * @param frame  the frame to align with ({@code null} not permitted).
     */
    public void align(Rectangle2D rect, Rectangle2D frame) {
        Args.nullNotPermitted(rect, "rect");
        Args.nullNotPermitted(frame, "frame");
        double x = rect.getX();
        double y = rect.getY();
        double w = rect.getWidth();
        double h = rect.getHeight();

        switch (this) {
            case BOTTOM_CENTER:
                x = frame.getCenterX() - rect.getWidth() / 2.0;
                y = frame.getMaxY() - h;
                break;
            case BOTTOM_LEFT:
                x = frame.getX();
                y = frame.getMaxY() - h;
                break;
            case BOTTOM_RIGHT:
                x = frame.getMaxX() - w;
                y = frame.getMaxY() - h;
                break;
            case CENTER:
                x = frame.getCenterX() - rect.getWidth() / 2.0;
                y = frame.getCenterY() - rect.getHeight() / 2.0;
                break;
            case FILL:
                x = frame.getX();
                y = frame.getY();
                w = frame.getWidth();
                h = frame.getHeight();
                break;
            case FILL_HORIZONTAL:
                x = frame.getX();
                w = frame.getWidth();
                break;
            case FILL_VERTICAL:
                y = frame.getY();
                h = frame.getHeight();
                break;
            case CENTER_LEFT:
                x = frame.getX(); 
                y = frame.getCenterY() - rect.getHeight() / 2.0;
                break;
            case CENTER_RIGHT:
                x = frame.getMaxX() - w;                
                y = frame.getCenterY() - rect.getHeight() / 2.0;
                break;
            case TOP_CENTER:
                x = frame.getCenterX() - rect.getWidth() / 2.0;
                y = frame.getY();                
                break;
            case TOP_LEFT:
                x = frame.getX();
                y = frame.getY();                
                break;
            case TOP_RIGHT:
                x = frame.getMaxX() - w; 
                y = frame.getY();                
                break;
            default:
                throw new IllegalStateException("Unexpected RectangleAlignment value");
        }
        rect.setRect(x, y, w, h);
    }
    
}
