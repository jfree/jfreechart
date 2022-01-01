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
 * ---------------
 * BlockFrame.java
 * ---------------
 * (C) Copyright 2007-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.block;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.PublicCloneable;

/**
 * A block frame is a type of border that can be drawn around the outside of
 * any {@link AbstractBlock}.  Classes that implement this interface should
 * implement {@link PublicCloneable} OR be immutable.
 */
public interface BlockFrame {

    /**
     * Returns the space reserved for the border.
     *
     * @return The space (never {@code null}).
     */
    RectangleInsets getInsets();

    /**
     * Draws the border by filling in the reserved space (in black).
     *
     * @param g2  the graphics device.
     * @param area  the area.
     */
    void draw(Graphics2D g2, Rectangle2D area);

}
