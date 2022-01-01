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
 * -----------------------
 * MarkerAxisBandTest.java
 * -----------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.Font;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MarkerAxisBand} class.
 */
public class MarkerAxisBandTest {

    /**
     * Test that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        Font font1 = new Font("SansSerif", Font.PLAIN, 12);
        Font font2 = new Font("SansSerif", Font.PLAIN, 14);

        MarkerAxisBand a1 = new MarkerAxisBand(null, 1.0, 1.0, 1.0, 1.0, font1);
        MarkerAxisBand a2 = new MarkerAxisBand(null, 1.0, 1.0, 1.0, 1.0, font1);
        assertEquals(a1, a2);

        a1 = new MarkerAxisBand(null, 2.0, 1.0, 1.0, 1.0, font1);
        assertNotEquals(a1, a2);
        a2 = new MarkerAxisBand(null, 2.0, 1.0, 1.0, 1.0, font1);
        assertEquals(a1, a2);

        a1 = new MarkerAxisBand(null, 2.0, 3.0, 1.0, 1.0, font1);
        assertNotEquals(a1, a2);
        a2 = new MarkerAxisBand(null, 2.0, 3.0, 1.0, 1.0, font1);
        assertEquals(a1, a2);

        a1 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 1.0, font1);
        assertNotEquals(a1, a2);
        a2 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 1.0, font1);
        assertEquals(a1, a2);

        a1 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 5.0, font1);
        assertNotEquals(a1, a2);
        a2 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 5.0, font1);
        assertEquals(a1, a2);

        a1 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 5.0, font2);
        assertNotEquals(a1, a2);
        a2 = new MarkerAxisBand(null, 2.0, 3.0, 4.0, 5.0, font2);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Font font1 = new Font("SansSerif", Font.PLAIN, 12);

        MarkerAxisBand a1 = new MarkerAxisBand(null, 1.0, 1.0, 1.0, 1.0, font1);
        MarkerAxisBand a2 = new MarkerAxisBand(null, 1.0, 1.0, 1.0, 1.0, font1);
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
        MarkerAxisBand a1 = new MarkerAxisBand(null, 1.0, 1.0, 1.0, 1.0, null);
        MarkerAxisBand a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}
