/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -------------------------------
 * DefaultDrawingSupplierTest.java
 * -------------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DefaultDrawingSupplier} class.
 */
public class DefaultDrawingSupplierTest {

    /**
     * Check that the equals() method can distinguish all required fields.
     */
    @Test
    public void testEquals() {
        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = new DefaultDrawingSupplier();
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));

        // set up some objects...
        Paint[] ps1A = new Paint[] {Color.RED, Color.BLUE};
        Paint[] ps2A = new Paint[] {Color.GREEN, Color.YELLOW, Color.WHITE};
        Paint[] ops1A = new Paint[] {Color.LIGHT_GRAY, Color.BLUE};
        Paint[] ops2A = new Paint[] {Color.BLACK, Color.YELLOW, Color.CYAN};
        Stroke[] ss1A = new Stroke[] {new BasicStroke(1.1f)};
        Stroke[] ss2A
            = new Stroke[] {new BasicStroke(2.2f), new BasicStroke(3.3f)};
        Stroke[] oss1A = new Stroke[] {new BasicStroke(4.4f)};
        Stroke[] oss2A
            = new Stroke[] {new BasicStroke(5.5f), new BasicStroke(6.6f)};
        Shape[] shapes1A = new Shape[] {
            new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0)
        };
        Shape[] shapes2A = new Shape[] {
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0),
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0)
        };
        Paint[] ps1B = new Paint[] {Color.RED, Color.BLUE};
        Paint[] ps2B = new Paint[] {Color.GREEN, Color.YELLOW, Color.WHITE};
        Paint[] ops1B = new Paint[] {Color.LIGHT_GRAY, Color.BLUE};
        Paint[] ops2B = new Paint[] {Color.BLACK, Color.YELLOW, Color.CYAN};
        Stroke[] ss1B = new Stroke[] {new BasicStroke(1.1f)};
        Stroke[] ss2B
            = new Stroke[] {new BasicStroke(2.2f), new BasicStroke(3.3f)};
        Stroke[] oss1B = new Stroke[] {new BasicStroke(4.4f)};
        Stroke[] oss2B
            = new Stroke[] {new BasicStroke(5.5f), new BasicStroke(6.6f)};
        Shape[] shapes1B = new Shape[] {
            new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0)
        };
        Shape[] shapes2B = new Shape[] {
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0),
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0)
        };

        r1 = new DefaultDrawingSupplier(ps1A, ops1A, ss1A, oss1A, shapes1A);
        r2 = new DefaultDrawingSupplier(ps1B, ops1B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));

        // paint sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops1A, ss1A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops1B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // outline paint sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss1A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // stroke sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // outline stroke sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss2A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss2B, shapes1B);
        assertTrue(r1.equals(r2));
        // shape sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss2A, shapes2A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss2B, shapes2B);
        assertTrue(r1.equals(r2));

        // paint index
        r1.getNextPaint();
        assertFalse(r1.equals(r2));
        r2.getNextPaint();
        assertTrue(r1.equals(r2));

        // outline paint index
        r1.getNextOutlinePaint();
        assertFalse(r1.equals(r2));
        r2.getNextOutlinePaint();
        assertTrue(r1.equals(r2));

        // stroke index
        r1.getNextStroke();
        assertFalse(r1.equals(r2));
        r2.getNextStroke();
        assertTrue(r1.equals(r2));

        // outline stroke index
        r1.getNextOutlineStroke();
        assertFalse(r1.equals(r2));
        r2.getNextOutlineStroke();
        assertTrue(r1.equals(r2));

        // shape index
        r1.getNextShape();
        assertFalse(r1.equals(r2));
        r2.getNextShape();
        assertTrue(r1.equals(r2));
    }

    /**
     * Some basic checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = (DefaultDrawingSupplier) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = (DefaultDrawingSupplier) 
                TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }

}
