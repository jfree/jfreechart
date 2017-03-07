/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * ----------------------------------------
 * DirectionalGradientPaintTransformer.java
 * ----------------------------------------
 * (C) Copyright 2013-2016 by Peter Kolb and Contributors.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes:
 * --------
 * 21-Nov-2013 : Version 1, with modifications by DG (PK);
 *
 */

package org.jfree.chart.util;

import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;
import org.jfree.chart.ui.GradientPaintTransformer;

/**
 * Transforms a {@code GradientPaint} to range over the width of a target 
 * shape.  The orientation of the resulting {@code GradientPaint}
 * depend on the coordinates of the original paint:
 *
 * <ul>
 * <li> If the original paint starts at 0,0 and ends at a point 0, y != 0,
 * the resulting paint will have a vertical orientation.
 * <li> If the original paint starts at 0,0 and ends at a point x !=0, 0,
 * the resulting paint will have a horizontal orientation.
 * <li> If the original paint starts at 0,0 and ends at a point x != 0, y != 0,
 * the resulting paint will have a diagonal orientation from the upper left to
 * the lower right edge. Lines of equal color will have a 45 ∞ angle,
 * pointing upwards from left to right.
 * <li> If the original paint starts at a point x != 0, y != 0,
 * the resulting paint will have a diagonal orientation from the lower left to
 * the upper right edge. Lines of equal color will have a 45 ∞ angle,
 * pointing downwards from left to right.
 * </ul>
 * <p>In all cases, the cyclic flag of the original paint will be taken into 
 * account.</p>
 *
 * @author Peter Kolb
 * @since 1.0.17
 */
public class DirectionalGradientPaintTransformer 
        implements GradientPaintTransformer {
    
    /**
     * Default constructor.
     */
    public DirectionalGradientPaintTransformer() {
        super();    
    }
    
    /**
     * Transforms a {@code GradientPaint} instance to fit some target 
     * shape.
     * 
     * @param paint  the original paint (not {@code null}).
     * @param target  the reference area (not {@code null}).
     * 
     * @return A transformed paint.
     */
    @Override
    public GradientPaint transform(GradientPaint paint, Shape target) {
        //get the coordinates of the original GradientPaint
        final double px1 = paint.getPoint1().getX();
        final double py1 = paint.getPoint1().getY();
        final double px2 = paint.getPoint2().getX();
        final double py2 = paint.getPoint2().getY();
        //get the coordinates of the shape that is to be filled
        final Rectangle2D bounds = target.getBounds();
        final float bx = (float)bounds.getX();
        final float by = (float)bounds.getY();
        final float bw = (float)bounds.getWidth();
        final float bh = (float)bounds.getHeight();
        //reserve variables to store the coordinates of the resulting GradientPaint
        float rx1, ry1, rx2, ry2;
        if (px1 == 0 && py1 == 0) {
            //start point is upper left corner
            rx1 = bx;
            ry1 = by;
            if (px2 != 0.0f && py2 != 0.0f) {
                //end point is lower right corner --> diagonal gradient
                float offset = (paint.isCyclic()) ? (bw + bh) / 4.0f 
                        : (bw + bh) / 2.0f ;
                rx2 = bx + offset;
                ry2 = by + offset;
            }
            else {
                //end point is either lower left corner --> vertical gradient
                //or end point is upper right corner --> horizontal gradient
                rx2 = (px2 == 0) ? rx1 : (paint.isCyclic() ? (rx1 + bw / 2.0f) 
                        : (rx1 + bw));
                ry2 = (py2 == 0) ? ry1 : (paint.isCyclic() ? (ry1 + bh / 2.0f) 
                        : (ry1 + bh));
            }
        }
        else {
            //start point is lower left right corner --> diagonal gradient
            rx1 = bx;
            ry1 = by + bh;
            float offset = (paint.isCyclic()) ? (bw + bh) / 4.0f 
                    : (bw + bh) / 2.0f;
            rx2 = bx + offset;
            ry2 = by + bh - offset;
        }
        return new GradientPaint(rx1, ry1, paint.getColor1(), rx2, ry2, 
                paint.getColor2(), paint.isCyclic());
    }
}