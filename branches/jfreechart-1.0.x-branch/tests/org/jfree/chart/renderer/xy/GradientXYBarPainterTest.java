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
 * -----------------------------
 * GradientXYBarPainterTest.java
 * -----------------------------
 * (C) Copyright 2008-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Jun-2008 : Version 1 (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link GradientXYBarPainter} class.
 */
public class GradientXYBarPainterTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        GradientXYBarPainter p1 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        GradientXYBarPainter p2 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        assertEquals(p1, p2);

        p1 = new GradientXYBarPainter(0.11, 0.2, 0.3);
        assertFalse(p1.equals(p2));
        p2 = new GradientXYBarPainter(0.11, 0.2, 0.3);
        assertTrue(p1.equals(p2));

        p1 = new GradientXYBarPainter(0.11, 0.22, 0.3);
        assertFalse(p1.equals(p2));
        p2 = new GradientXYBarPainter(0.11, 0.22, 0.3);
        assertTrue(p1.equals(p2));

        p1 = new GradientXYBarPainter(0.11, 0.22, 0.33);
        assertFalse(p1.equals(p2));
        p2 = new GradientXYBarPainter(0.11, 0.22, 0.33);
        assertTrue(p1.equals(p2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        GradientXYBarPainter p1 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        GradientXYBarPainter p2 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        assertTrue(p1.equals(p2));
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning isn't implemented (it isn't required, because
     * instances of the class are immutable).
     */
    @Test
    public void testCloning() {
        GradientXYBarPainter p1 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        assertFalse(p1 instanceof Cloneable);
        assertFalse(p1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        GradientXYBarPainter p1 = new GradientXYBarPainter(0.1, 0.2, 0.3);
        GradientXYBarPainter p2 = (GradientXYBarPainter) 
                TestUtilities.serialised(p1);
        assertEquals(p1, p2);
    }

}
