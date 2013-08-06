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
 * --------------------------
 * StandardDialFrameTest.java
 * --------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Nov-2006 : Version 1 (DG);
 * 29-Oct-2007 : Renamed StandardDialFrameTests (DG);
 *
 */

package org.jfree.chart.plot.dial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link StandardDialFrame} class.
 */
public class StandardDialFrameTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        StandardDialFrame f1 = new StandardDialFrame();
        StandardDialFrame f2 = new StandardDialFrame();
        assertTrue(f1.equals(f2));

        // radius
        f1.setRadius(0.2);
        assertFalse(f1.equals(f2));
        f2.setRadius(0.2);
        assertTrue(f1.equals(f2));

        // backgroundPaint
        f1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.white, 3.0f,
                4.0f, Color.yellow));
        assertFalse(f1.equals(f2));
        f2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.white, 3.0f,
                4.0f, Color.yellow));
        assertTrue(f1.equals(f2));

        // foregroundPaint
        f1.setForegroundPaint(new GradientPaint(1.0f, 2.0f, Color.blue, 3.0f,
                4.0f, Color.green));
        assertFalse(f1.equals(f2));
        f2.setForegroundPaint(new GradientPaint(1.0f, 2.0f, Color.blue, 3.0f,
                4.0f, Color.green));
        assertTrue(f1.equals(f2));

        // stroke
        f1.setStroke(new BasicStroke(2.4f));
        assertFalse(f1.equals(f2));
        f2.setStroke(new BasicStroke(2.4f));
        assertTrue(f1.equals(f2));

        // check an inherited attribute
        f1.setVisible(false);
        assertFalse(f1.equals(f2));
        f2.setVisible(false);
        assertTrue(f1.equals(f2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        StandardDialFrame f1 = new StandardDialFrame();
        StandardDialFrame f2 = new StandardDialFrame();
        assertTrue(f1.equals(f2));
        int h1 = f1.hashCode();
        int h2 = f2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StandardDialFrame f1 = new StandardDialFrame();
        StandardDialFrame f2 = (StandardDialFrame) f1.clone();
        assertTrue(f1 != f2);
        assertTrue(f1.getClass() == f2.getClass());
        assertTrue(f1.equals(f2));

        // check that the listener lists are independent
        MyDialLayerChangeListener l1 = new MyDialLayerChangeListener();
        f1.addChangeListener(l1);
        assertTrue(f1.hasListener(l1));
        assertFalse(f2.hasListener(l1));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StandardDialFrame f1 = new StandardDialFrame();
        StandardDialFrame f2 = (StandardDialFrame) TestUtilities.serialised(f1);
        assertEquals(f1, f2);
    }

}
