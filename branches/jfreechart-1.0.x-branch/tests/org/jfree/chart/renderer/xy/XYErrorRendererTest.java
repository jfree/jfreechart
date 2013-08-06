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
 * ------------------------
 * XYErrorRendererTest.java
 * ------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Oct-2006 : Version 1 (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 * 28-Jan-2009 : Updated tests for new errorStroke field (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link XYErrorRenderer} class.
 */
public class XYErrorRendererTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        XYErrorRenderer r2 = new XYErrorRenderer();
        assertEquals(r1, r2);

        // drawXError
        r1.setDrawXError(false);
        assertFalse(r1.equals(r2));
        r2.setDrawXError(false);
        assertTrue(r1.equals(r2));

        // drawYError
        r1.setDrawYError(false);
        assertFalse(r1.equals(r2));
        r2.setDrawYError(false);
        assertTrue(r1.equals(r2));

        // capLength
        r1.setCapLength(9.0);
        assertFalse(r1.equals(r2));
        r2.setCapLength(9.0);
        assertTrue(r1.equals(r2));

        // errorPaint
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.green));
        assertFalse(r1.equals(r2));
        r2.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.green));
        assertTrue(r1.equals(r2));

        // errorStroke
        r1.setErrorStroke(new BasicStroke(1.5f));
        assertFalse(r1.equals(r2));
        r2.setErrorStroke(new BasicStroke(1.5f));
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        XYErrorRenderer r2 = new XYErrorRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        XYErrorRenderer r2 = (XYErrorRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * A test for cloning.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorStroke(new BasicStroke(1.5f));
        XYErrorRenderer r2 = (XYErrorRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        XYErrorRenderer r2 = (XYErrorRenderer) TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorStroke(new BasicStroke(1.5f));
        XYErrorRenderer r2 = (XYErrorRenderer) TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * Some checks for the findDomainBounds() method.
     */
    @Test
    public void testFindDomainBounds() {
        XYErrorRenderer r = new XYErrorRenderer();
        assertNull(r.findDomainBounds(null));
    }


    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        XYErrorRenderer r = new XYErrorRenderer();
        assertNull(r.findRangeBounds(null));
    }
}
