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
 * -----------------
 * ColorBarTest.java
 * -----------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() method (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;

import org.jfree.chart.plot.GreyPalette;
import org.junit.Test;

/**
 * Tests for the <code>ColorBar</code> class.
 */
public class ColorBarTest {

    /**
     * Check that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        ColorBar c1 = new ColorBar("Test");
        ColorBar c2 = new ColorBar("Test");
        assertEquals(c1, c2);

        c1.setAxis(new NumberAxis("Axis 1"));
        assertTrue(!c1.equals(c2));
        c2.setAxis(new NumberAxis("Axis 1"));
        assertTrue(c1.equals(c2));

        c1.setColorPalette(new GreyPalette());
        assertTrue(!c1.equals(c2));
        c2.setColorPalette(new GreyPalette());
        assertTrue(c1.equals(c2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        ColorBar c1 = new ColorBar("Test");
        ColorBar c2 = new ColorBar("Test");
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
        ColorBar c1 = new ColorBar("Test");
        ColorBar c2 = (ColorBar) c1.clone();
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ColorBar a1 = new ColorBar("Test Axis");
        ColorBar a2 = (ColorBar) TestUtilities.serialised(a1);
        assertEquals(a1, a2);
    }

}
