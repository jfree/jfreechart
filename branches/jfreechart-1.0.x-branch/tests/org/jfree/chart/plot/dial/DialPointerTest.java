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
 * DialPointerTest.java
 * --------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Apr-2007 : Version 1 (DG);
 * 23-Nov-2007 : Added testEqualsPointer() and testSerialization2() (DG);
 *
 */

package org.jfree.chart.plot.dial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link DialPointer} class.
 */
public class DialPointerTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DialPointer i1 = new DialPointer.Pin(1);
        DialPointer i2 = new DialPointer.Pin(1);
        assertTrue(i1.equals(i2));

        // dataset index
        i1 = new DialPointer.Pin(2);
        assertFalse(i1.equals(i2));
        i2 = new DialPointer.Pin(2);
        assertTrue(i1.equals(i2));

        // check an inherited attribute
        i1.setVisible(false);
        assertFalse(i1.equals(i2));
        i2.setVisible(false);
        assertTrue(i1.equals(i2));
    }

    /**
     * Check the equals() method for the DialPointer.Pin class.
     */
    @Test
    public void testEqualsPin() {
        DialPointer.Pin p1 = new DialPointer.Pin();
        DialPointer.Pin p2 = new DialPointer.Pin();
        assertEquals(p1, p2);

        p1.setPaint(Color.green);
        assertFalse(p1.equals(p2));
        p2.setPaint(Color.green);
        assertTrue(p1.equals(p2));

        BasicStroke s = new BasicStroke(4.4f);
        p1.setStroke(s);
        assertFalse(p1.equals(p2));
        p2.setStroke(s);
        assertTrue(p1.equals(p2));
    }

    /**
     * Check the equals() method for the DialPointer.Pointer class.
     */
    @Test
    public void testEqualsPointer() {
        DialPointer.Pointer p1 = new DialPointer.Pointer();
        DialPointer.Pointer p2 = new DialPointer.Pointer();
        assertEquals(p1, p2);

        p1.setFillPaint(Color.green);
        assertFalse(p1.equals(p2));
        p2.setFillPaint(Color.green);
        assertTrue(p1.equals(p2));

        p1.setOutlinePaint(Color.green);
        assertFalse(p1.equals(p2));
        p2.setOutlinePaint(Color.green);
        assertTrue(p1.equals(p2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DialPointer i1 = new DialPointer.Pin(1);
        DialPointer i2 = new DialPointer.Pin(1);
        assertTrue(i1.equals(i2));
        int h1 = i1.hashCode();
        int h2 = i2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DialPointer i1 = new DialPointer.Pin(1);
        DialPointer i2 = (DialPointer) i1.clone();
        assertTrue(i1 != i2);
        assertTrue(i1.getClass() == i2.getClass());
        assertTrue(i1.equals(i2));

        // check that the listener lists are independent
        MyDialLayerChangeListener l1 = new MyDialLayerChangeListener();
        i1.addChangeListener(l1);
        assertTrue(i1.hasListener(l1));
        assertFalse(i2.hasListener(l1));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        // test a default instance
        DialPointer i1 = new DialPointer.Pin(1);
        DialPointer i2 = (DialPointer) TestUtilities.serialised(i1);
        assertEquals(i1, i2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        DialPointer i1 = new DialPointer.Pointer(1);
        DialPointer i2 = (DialPointer) TestUtilities.serialised(i1);
        assertEquals(i1, i2);
    }
}
