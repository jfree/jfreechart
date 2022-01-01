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
 * YWithXIntervalTest.java
 * -----------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link YWithXInterval} class.
 */
public class YWithXIntervalTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        YWithXInterval i1 = new YWithXInterval(1.0, 0.5, 1.5);
        YWithXInterval i2 = new YWithXInterval(1.0, 0.5, 1.5);
        assertEquals(i1, i2);

        i1 = new YWithXInterval(1.1, 0.5, 1.5);
        assertNotEquals(i1, i2);
        i2 = new YWithXInterval(1.1, 0.5, 1.5);
        assertEquals(i1, i2);

        i1 = new YWithXInterval(1.1, 0.55, 1.5);
        assertNotEquals(i1, i2);
        i2 = new YWithXInterval(1.1, 0.55, 1.5);
        assertEquals(i1, i2);

        i1 = new YWithXInterval(1.1, 0.55, 1.55);
        assertNotEquals(i1, i2);
        i2 = new YWithXInterval(1.1, 0.55, 1.55);
        assertEquals(i1, i2);
    }

    /**
     * This class is immutable.
     */
    @Test
    public void testCloning() {
        YWithXInterval i1 = new YWithXInterval(1.0, 0.5, 1.5);
        assertFalse(i1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        YWithXInterval i1 = new YWithXInterval(1.0, 0.5, 1.5);
        YWithXInterval i2 = TestUtils.serialised(i1);
        assertEquals(i1, i2);
    }

}
