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
 */

package org.jfree.chart.ui;

import java.awt.GradientPaint;
import java.awt.Shape;

/**
 * The interface for a class that can transform a {@code GradientPaint} to
 * fit an arbitrary shape.
 */
public interface GradientPaintTransformer {
    
    /**
     * Transforms a {@code GradientPaint} instance to fit some target 
     * shape.  Classes that implement this method typically return a new
     * instance of {@code GradientPaint}.
     * 
     * @param paint  the original paint (not {@code null}).
     * @param target  the reference area (not {@code null}).
     * 
     * @return A transformed paint.
     */
    public GradientPaint transform(GradientPaint paint, Shape target);

}

