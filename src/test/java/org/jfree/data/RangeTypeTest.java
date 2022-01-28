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
 * ------------------
 * RangeTypeTest.java
 * ------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link RangeType} class.
 */
public class RangeTypeTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(RangeType.FULL, RangeType.FULL);
        assertEquals(RangeType.NEGATIVE, RangeType.NEGATIVE);
        assertEquals(RangeType.POSITIVE, RangeType.POSITIVE);
        assertNotEquals(RangeType.FULL, RangeType.NEGATIVE);
        assertNotEquals(RangeType.FULL, RangeType.POSITIVE);
        assertNotEquals(null, RangeType.FULL);
        assertNotEquals(RangeType.NEGATIVE, RangeType.FULL);
        assertNotEquals(RangeType.NEGATIVE, RangeType.POSITIVE);
        assertNotEquals(null, RangeType.NEGATIVE);
        assertNotEquals(RangeType.POSITIVE, RangeType.NEGATIVE);
        assertNotEquals(RangeType.POSITIVE, RangeType.FULL);
        assertNotEquals(null, RangeType.POSITIVE);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        RangeType r1 = RangeType.FULL;
        RangeType r2 = RangeType.FULL;
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        RangeType r1 = RangeType.FULL;
        RangeType r2 = TestUtils.serialised(r1);
        assertSame(r1, r2);
    }

}
