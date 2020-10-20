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
 * ---------------------
 * MatrixSeriesTest.java
 * ---------------------
 * (C) Copyright 2004-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-May-2004 : Version 1 (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;


/**
 * Tests for the {@link MatrixSeries} class.
 */
public class MatrixSeriesTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        MatrixSeries m1 = new MatrixSeries("Test", 8, 3);
        m1.update(0, 0, 11.0);
        m1.update(7, 2, 22.0);
        MatrixSeries m2 = new MatrixSeries("Test", 8, 3);
        m2.update(0, 0, 11.0);
        m2.update(7, 2, 22.0);
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));

        m1 = new MatrixSeries("Test 2", 8, 3);
        assertFalse(m1.equals(m2));
        m2 = new MatrixSeries("Test 2", 8, 3);
        assertTrue(m1.equals(m2));

        m1 = new MatrixSeries("Test 2", 10, 3);
        assertFalse(m1.equals(m2));
        m2 = new MatrixSeries("Test 2", 10, 3);
        assertTrue(m1.equals(m2));

        m1 = new MatrixSeries("Test 2", 10, 5);
        assertFalse(m1.equals(m2));
        m2 = new MatrixSeries("Test 2", 10, 5);
        assertTrue(m1.equals(m2));

        m1.update(0, 0, 99);
        assertFalse(m1.equals(m2));
        m2.update(0, 0, 99);
        assertTrue(m1.equals(m2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        MatrixSeries m1 = new MatrixSeries("Test", 8, 3);
        m1.update(0, 0, 11.0);
        m1.update(7, 2, 22.0);
        MatrixSeries m2 = (MatrixSeries) m1.clone();
        assertTrue(m1 != m2);
        assertTrue(m1.getClass() == m2.getClass());
        assertTrue(m1.equals(m2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MatrixSeries m1 = new MatrixSeries("Test", 8, 3);
        m1.update(0, 0, 11.0);
        m1.update(7, 2, 22.0);
        MatrixSeries m2 = (MatrixSeries) TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * Tests the getItemColumn() method.
     */
    @Test
    public void testGetItemColumn() {
        MatrixSeries m = new MatrixSeries("Test", 3, 2);
        assertEquals(0, m.getItemColumn(0));
        assertEquals(1, m.getItemColumn(1));
        assertEquals(0, m.getItemColumn(2));
        assertEquals(1, m.getItemColumn(3));
        assertEquals(0, m.getItemColumn(4));
        assertEquals(1, m.getItemColumn(5));
    }

    /**
     * Tests the getItemRow() method.
     */
    @Test
    public void testGetItemRow() {
        MatrixSeries m = new MatrixSeries("Test", 3, 2);
        assertEquals(0, m.getItemRow(0));
        assertEquals(0, m.getItemRow(1));
        assertEquals(1, m.getItemRow(2));
        assertEquals(1, m.getItemRow(3));
        assertEquals(2, m.getItemRow(4));
        assertEquals(2, m.getItemRow(5));
    }

    /**
     * Tests the getItem() method.
     */
    @Test
    public void testGetItem() {
        MatrixSeries m = new MatrixSeries("Test", 3, 2);
        m.update(0, 0, 0.0);
        m.update(0, 1, 1.0);
        m.update(1, 0, 2.0);
        m.update(1, 1, 3.0);
        m.update(2, 0, 4.0);
        m.update(2, 1, 5.0);
        assertEquals(0.0, m.getItem(0).doubleValue(), 0.001);
        assertEquals(1.0, m.getItem(1).doubleValue(), 0.001);
        assertEquals(2.0, m.getItem(2).doubleValue(), 0.001);
        assertEquals(3.0, m.getItem(3).doubleValue(), 0.001);
        assertEquals(4.0, m.getItem(4).doubleValue(), 0.001);
        assertEquals(5.0, m.getItem(5).doubleValue(), 0.001);
    }
}
