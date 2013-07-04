/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * --------------------
 * ShadowGenerator.java
 * --------------------
 * (C) Copyright 2009, 2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 10-Jul-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.util;

import java.awt.image.BufferedImage;

/**
 * An interface that defines the API for a shadow generator.  Some plot
 * classes use this to create drop shadows.
 *
 * @since 1.0.14
 */
public interface ShadowGenerator {

    /**
     * Creates and returns an image containing the drop shadow for the
     * specified source image.
     *
     * @param source  the source image.
     *
     * @return A new image containing the shadow.
     */
    public BufferedImage createDropShadow(BufferedImage source);

    /**
     * Calculates the x-offset for drawing the shadow image relative to the
     * source.
     *
     * @return The x-offset.
     */
    public int calculateOffsetX();

    /**
     * Calculates the y-offset for drawing the shadow image relative to the
     * source.
     *
     * @return The y-offset.
     */
    public int calculateOffsetY();

}
