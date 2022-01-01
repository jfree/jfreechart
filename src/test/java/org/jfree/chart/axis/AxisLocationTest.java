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
 * ---------------------
 * AxisLocationTest.java
 * ---------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link AxisLocation} class.
 */
public class AxisLocationTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(AxisLocation.TOP_OR_RIGHT, AxisLocation.TOP_OR_RIGHT);
        assertEquals(AxisLocation.BOTTOM_OR_RIGHT,
                AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(AxisLocation.TOP_OR_LEFT, AxisLocation.TOP_OR_LEFT);
        assertEquals(AxisLocation.BOTTOM_OR_LEFT, AxisLocation.BOTTOM_OR_LEFT);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        AxisLocation a1 = AxisLocation.TOP_OR_RIGHT;
        AxisLocation a2 = AxisLocation.TOP_OR_RIGHT;
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
        AxisLocation location1 = AxisLocation.BOTTOM_OR_RIGHT;
        AxisLocation location2 = TestUtils.serialised(location1);
        assertEquals(location1, location2);
        boolean same = location1 == location2;
        assertTrue(same);
    }

}
