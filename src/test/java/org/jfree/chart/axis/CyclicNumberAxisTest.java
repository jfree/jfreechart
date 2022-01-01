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
 * -------------------
 * CyclicAxisTest.java
 * -------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Nicolas Brodu
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CyclicNumberAxis} class.
 */
public class CyclicNumberAxisTest  {

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CyclicNumberAxis a1 = new CyclicNumberAxis(10, 0, "Test");
        CyclicNumberAxis a2 = (CyclicNumberAxis) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        CyclicNumberAxis a1 = new CyclicNumberAxis(10, 0, "Test");
        CyclicNumberAxis a2 = new CyclicNumberAxis(10, 0, "Test");
        assertEquals(a1, a2);

        // period
        a1.setPeriod(5);
        assertNotEquals(a1, a2);
        a2.setPeriod(5);
        assertEquals(a1, a2);

        // offset
        a1.setOffset(2.0);
        assertNotEquals(a1, a2);
        a2.setOffset(2.0);
        assertEquals(a1, a2);

        // advance line Paint
        a1.setAdvanceLinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertNotEquals(a1, a2);
        a2.setAdvanceLinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(a1, a2);

        // advance line Stroke
        Stroke stroke = new BasicStroke(0.2f);
        a1.setAdvanceLineStroke(stroke);
        assertNotEquals(a1, a2);
        a2.setAdvanceLineStroke(stroke);
        assertEquals(a1, a2);

        // advance line Visible
        a1.setAdvanceLineVisible(!a1.isAdvanceLineVisible());
        assertNotEquals(a1, a2);
        a2.setAdvanceLineVisible(a1.isAdvanceLineVisible());
        assertEquals(a1, a2);

        // cycle bound mapping
        a1.setBoundMappedToLastCycle(!a1.isBoundMappedToLastCycle());
        assertNotEquals(a1, a2);
        a2.setBoundMappedToLastCycle(a1.isBoundMappedToLastCycle());
        assertEquals(a1, a2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        CyclicNumberAxis a1 = new CyclicNumberAxis(10, 0, "Test");
        CyclicNumberAxis a2 = new CyclicNumberAxis(10, 0, "Test");
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CyclicNumberAxis a1 = new CyclicNumberAxis(10, 0, "Test Axis");
        CyclicNumberAxis a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}

