/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * YIntervalDataItemTest.java
 * --------------------------
 * (C) Copyright 2006-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link YIntervalDataItem} class.
 */
public class YIntervalDataItemTest {

    private static final double EPSILON = 0.00000000001;

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor1() {
        YIntervalDataItem item1 = new YIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        assertEquals(1.0, item1.getX());
        assertEquals(2.0, item1.getYValue(), EPSILON);
        assertEquals(3.0, item1.getYLowValue(), EPSILON);
        assertEquals(4.0, item1.getYHighValue(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        YIntervalDataItem item1 = new YIntervalDataItem(1.0, 2.0, 1.5, 2.5);
        YIntervalDataItem item2 = new YIntervalDataItem(1.0, 2.0, 1.5, 2.5);
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));

        // x
        item1 = new YIntervalDataItem(1.1, 2.0, 1.5, 2.5);
        assertFalse(item1.equals(item2));
        item2 = new YIntervalDataItem(1.1, 2.0, 1.5, 2.5);
        assertTrue(item1.equals(item2));

        // y
        item1 = new YIntervalDataItem(1.1, 2.2, 1.5, 2.5);
        assertFalse(item1.equals(item2));
        item2 = new YIntervalDataItem(1.1, 2.2, 1.5, 2.5);
        assertTrue(item1.equals(item2));

        // yLow
        item1 = new YIntervalDataItem(1.1, 2.2, 1.55, 2.5);
        assertFalse(item1.equals(item2));
        item2 = new YIntervalDataItem(1.1, 2.2, 1.55, 2.5);
        assertTrue(item1.equals(item2));

        // yHigh
        item1 = new YIntervalDataItem(1.1, 2.2, 1.55, 2.55);
        assertFalse(item1.equals(item2));
        item2 = new YIntervalDataItem(1.1, 2.2, 1.55, 2.55);
        assertTrue(item1.equals(item2));
    }

    /**
     * Some checks for the clone() method.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        YIntervalDataItem item1 = new YIntervalDataItem(1.0, 2.0, 1.5, 2.5);
        YIntervalDataItem item2 = (YIntervalDataItem) item1.clone();
        assertTrue(item1 != item2);
        assertTrue(item1.getClass() == item2.getClass());
        assertTrue(item1.equals(item2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        YIntervalDataItem item1 = new YIntervalDataItem(1.0, 2.0, 1.5, 2.5);
        YIntervalDataItem item2 = (YIntervalDataItem) 
                TestUtils.serialised(item1);
        assertEquals(item1, item2);
    }

}
