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
 * ---------------------------
 * RectangleAlignmentTest.java
 * ---------------------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.awt.geom.Rectangle2D;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link RectangleAlignment} enumeration.
 */
public class RectangleAlignmentTest {

    /**
     * Basic check on the alignment calculations.
     */
    @Test
    public void testAlign() {
        Rectangle2D frame = new Rectangle2D.Double(5.0, 10.0, 100.0, 200.0);
        Rectangle2D rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        
        RectangleAlignment.FILL.align(rect, frame);
        assertEquals(new Rectangle2D.Double(5.0, 10.0, 100.0, 200.0), rect);
        
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.FILL_VERTICAL.align(rect, frame);
        assertEquals(new Rectangle2D.Double(22.0, 10.0, 44.0, 200.0), rect);

        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.FILL_HORIZONTAL.align(rect, frame);
        assertEquals(new Rectangle2D.Double(5.0, 33.0, 100.0, 55.0), rect);

        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.TOP_LEFT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(5.0, 10.0, 44.0, 55.0), rect);
    
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.TOP_CENTER.align(rect, frame);
        assertEquals(new Rectangle2D.Double(33.0, 10.0, 44.0, 55.0), rect);

        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.TOP_RIGHT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(61.0, 10.0, 44.0, 55.0), rect);
       
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.CENTER_LEFT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(5.0, 82.5, 44.0, 55.0), rect);
    
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.CENTER.align(rect, frame);
        assertEquals(new Rectangle2D.Double(33.0, 82.5, 44.0, 55.0), rect);

        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.CENTER_RIGHT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(61.0, 82.5, 44.0, 55.0), rect);

        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.BOTTOM_LEFT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(5.0, 155.0, 44.0, 55.0), rect);
    
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.BOTTOM_CENTER.align(rect, frame);
        assertEquals(new Rectangle2D.Double(33.0, 155.0, 44.0, 55.0), rect);
        
        rect = new Rectangle2D.Double(22.0, 33.0, 44.0, 55.0);
        RectangleAlignment.BOTTOM_RIGHT.align(rect, frame);
        assertEquals(new Rectangle2D.Double(61.0, 155.0, 44.0, 55.0), rect);   
    }

}
