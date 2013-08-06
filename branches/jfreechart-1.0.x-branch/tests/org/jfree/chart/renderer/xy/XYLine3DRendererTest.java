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
 * -------------------------
 * XYLine3DRendererTest.java
 * -------------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Apr-2007 : Version 1 (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link XYLine3DRenderer} class.
 */
public class XYLine3DRendererTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        XYLine3DRenderer r1 = new XYLine3DRenderer();
        XYLine3DRenderer r2 = new XYLine3DRenderer();
        assertEquals(r1, r2);

        r1.setXOffset(11.1);
        assertFalse(r1.equals(r2));
        r2.setXOffset(11.1);
        assertTrue(r1.equals(r2));

        r1.setYOffset(11.1);
        assertFalse(r1.equals(r2));
        r2.setYOffset(11.1);
        assertTrue(r1.equals(r2));

        r1.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.blue));
        assertFalse(r1.equals(r2));
        r2.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.blue));
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        XYLine3DRenderer r1 = new XYLine3DRenderer();
        XYLine3DRenderer r2 = new XYLine3DRenderer();
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
        XYLine3DRenderer r1 = new XYLine3DRenderer();
        r1.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.blue));
        XYLine3DRenderer r2 = (XYLine3DRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        XYLine3DRenderer r1 = new XYLine3DRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYLine3DRenderer r1 = new XYLine3DRenderer();
        r1.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.blue));
        XYLine3DRenderer r2 = (XYLine3DRenderer) TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

}
