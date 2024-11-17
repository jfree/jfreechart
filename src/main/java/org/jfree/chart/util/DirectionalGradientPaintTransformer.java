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
 * ----------------------------------------
 * DirectionalGradientPaintTransformer.java
 * ----------------------------------------
 * (C) Copyright 2013-2021 by Peter Kolb and Contributors.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.util;

import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;
import java.awt.Shape;

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
 */
public class DirectionalGradientPaintTransformer implements GradientPaintTransformer {
    
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
        final double paintStartX = paint.getPoint1().getX();
        final double paintStartY = paint.getPoint1().getY();
        final double paintEndX = paint.getPoint2().getX();
        final double paintEndY = paint.getPoint2().getY();

        //get the coordinates of the shape that is to be filled
        final Rectangle2D bounds = target.getBounds();
        final float targetX = (float)bounds.getX();
        final float targetY = (float)bounds.getY();
        final float targetWidth = (float)bounds.getWidth();
        final float targetHeight = (float)bounds.getHeight();

        //reserve variables to store the coordinates of the resulting GradientPaint
        float resultStartX, resultStartY, resultEndX, resultEndY;

        if (paintStartX == 0 && paintStartY == 0) {
            //start point is upper left corner
            resultStartX = targetX;
            resultStartY = targetY;
            if (paintEndX != 0.0f && paintEndY != 0.0f) {
                //end point is lower right corner --> diagonal gradient
                float offset = (paint.isCyclic()) ? (targetWidth + targetHeight) / 4.0f
                        : (targetWidth + targetHeight) / 2.0f;
                resultEndX = targetX + offset;
                resultEndY = targetY + offset;
            }
            else {
                //end point is either lower left corner --> vertical gradient
                //or end point is upper right corner --> horizontal gradient
                resultEndX = (paintEndX == 0) ? resultStartX :
                        (paint.isCyclic() ? (resultStartX + targetWidth / 2.0f)
                                : (resultStartX + targetWidth));
                resultEndY = (paintEndY == 0) ? resultStartY :
                        (paint.isCyclic() ? (resultStartY + targetHeight / 2.0f)
                                : (resultStartY + targetHeight));
            }
        }
        else {
            //start point is lower left right corner --> diagonal gradient
            resultStartX = targetX;
            resultStartY = targetY + targetHeight;
            float offset = (paint.isCyclic()) ? (targetWidth + targetHeight) / 4.0f
                    : (targetWidth + targetHeight) / 2.0f;
            resultEndX = targetX + offset;
            resultEndY = targetY + targetHeight - offset;
        }

        return new GradientPaint(resultStartX, resultStartY, paint.getColor1(),
                resultEndX, resultEndY, paint.getColor2(), paint.isCyclic());
    }
}