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
 * -------------------
 * XYDataItemTest.java
 * -------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Dec-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link XYDataItem} class.
 */
public class XYDataItemTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        XYDataItem i1 = new XYDataItem(1.0, 1.1);
        XYDataItem i2 = new XYDataItem(1.0, 1.1);
        assertTrue(i1.equals(i2));
        assertTrue(i2.equals(i1));

        i1.setY(9.9);
        assertFalse(i1.equals(i2));

        i2.setY(9.9);
        assertTrue(i1.equals(i2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        XYDataItem i1 = new XYDataItem(1.0, 1.1);
        XYDataItem i2 = (XYDataItem) i1.clone();
        assertTrue(i1 != i2);
        assertTrue(i1.getClass() == i2.getClass());
        assertTrue(i1.equals(i2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYDataItem i1 = new XYDataItem(1.0, 1.1);
        XYDataItem i2 = (XYDataItem) TestUtils.serialised(i1);
        assertEquals(i1, i2);
    }

}
