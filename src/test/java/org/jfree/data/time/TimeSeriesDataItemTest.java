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
 * ---------------------------
 * TimeSeriesDataItemTest.java
 * ---------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.time;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TimeSeriesDataItem} class.
 */
public class TimeSeriesDataItemTest {

    /**
     * Test that an instance is equal to itself.
     *
     * SourceForge Bug ID: 558850.
     */
    @Test
    public void testEqualsSelf() {
        TimeSeriesDataItem item = new TimeSeriesDataItem(new Day(23, 9, 2001), 
                99.7);
        assertTrue(item.equals(item));
    }

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        TimeSeriesDataItem item1 = new TimeSeriesDataItem(new Day(23, 9, 2001), 
                99.7);
        TimeSeriesDataItem item2 = new TimeSeriesDataItem(new Day(23, 9, 2001), 
                99.7);
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));

        item1.setValue(5);
        assertFalse(item1.equals(item2));
        item2.setValue(5);
        assertTrue(item1.equals(item2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TimeSeriesDataItem item1 = new TimeSeriesDataItem(new Day(23, 9, 2001), 
                99.7);
        TimeSeriesDataItem item2 = (TimeSeriesDataItem) 
                TestUtils.serialised(item1);
        assertEquals(item1, item2);
    }

}
