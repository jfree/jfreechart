/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * ----------------
 * DialCapTest.java
 * ----------------
 * (C) Copyright 2006-2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;


/**
 * Tests for the {@link DialCap} class.
 */
public class DialCapTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DialCap c1 = new DialCap();
        DialCap c2 = new DialCap();
        assertTrue(c1.equals(c2));

        // radius
        c1.setRadius(0.5);
        assertFalse(c1.equals(c2));
        c2.setRadius(0.5);
        assertTrue(c1.equals(c2));

        // fill paint
        c1.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));
        assertFalse(c1.equals(c2));
        c2.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));

        // outline paint
        c1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.GRAY));
        assertFalse(c1.equals(c2));
        c2.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.GRAY));

        assertTrue(c1.equals(c2));

        // outline stroke
        c1.setOutlineStroke(new BasicStroke(1.1f));
        assertFalse(c1.equals(c2));
        c2.setOutlineStroke(new BasicStroke(1.1f));
        assertTrue(c1.equals(c2));

        // check an inherited attribute
        c1.setVisible(false);
        assertFalse(c1.equals(c2));
        c2.setVisible(false);
        assertTrue(c1.equals(c2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DialCap c1 = new DialCap();
        DialCap c2 = new DialCap();
        assertTrue(c1.equals(c2));
        int h1 = c1.hashCode();
        int h2 = c2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        // test a default instance
        DialCap c1 = new DialCap();
        DialCap c2 = (DialCap) c1.clone();

        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // test a customised instance
        c1 = new DialCap();
        c1.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));
        c1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.GRAY));
        c1.setOutlineStroke(new BasicStroke(2.0f));
        c2 = (DialCap) c1.clone();
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check that the listener lists are independent
        MyDialLayerChangeListener l1 = new MyDialLayerChangeListener();
        c1.addChangeListener(l1);
        assertTrue(c1.hasListener(l1));
        assertFalse(c2.hasListener(l1));
    }


    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        // test a default instance
        DialCap c1 = new DialCap();
        DialCap c2 = (DialCap) TestUtils.serialised(c1);
        assertEquals(c1, c2);

        // test a custom instance
        c1 = new DialCap();
        c1.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));
        c1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.GRAY));
        c1.setOutlineStroke(new BasicStroke(2.0f));

        c2 = (DialCap) TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

}
