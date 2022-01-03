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
 * -------------------------------
 * SlidingCategoryDatasetTest.java
 * -------------------------------
 * (C) Copyright 2008-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.category;

import java.util.List;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SlidingCategoryDataset} class.
 */
public class SlidingCategoryDatasetTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        DefaultCategoryDataset<String, String> u1 = new DefaultCategoryDataset<>();
        u1.addValue(1.0, "R1", "C1");
        u1.addValue(2.0, "R1", "C2");
        SlidingCategoryDataset<String, String> d1 = new SlidingCategoryDataset<>(u1, 0, 5);
        DefaultCategoryDataset<String, String> u2 = new DefaultCategoryDataset<>();
        u2.addValue(1.0, "R1", "C1");
        u2.addValue(2.0, "R1", "C2");
        SlidingCategoryDataset<String, String> d2 = new SlidingCategoryDataset<>(u2, 0, 5);
        assertEquals(d1, d2);

        d1.setFirstCategoryIndex(1);
        assertNotEquals(d1, d2);
        d2.setFirstCategoryIndex(1);
        assertEquals(d1, d2);

        d1.setMaximumCategoryCount(99);
        assertNotEquals(d1, d2);
        d2.setMaximumCategoryCount(99);
        assertEquals(d1, d2);

        u1.addValue(3.0, "R1", "C3");
        assertNotEquals(d1, d2);
        u2.addValue(3.0, "R1", "C3");
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultCategoryDataset<String, String> u1 = new DefaultCategoryDataset<>();
        u1.addValue(1.0, "R1", "C1");
        u1.addValue(2.0, "R1", "C2");
        SlidingCategoryDataset<String, String> d1 = new SlidingCategoryDataset<>(u1, 0, 5);
        SlidingCategoryDataset<String, String> d2;
        d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // basic check for independence
        u1.addValue(3.0, "R1", "C3");
        assertNotEquals(d1, d2);
        DefaultCategoryDataset<String, String> u2 
                = (DefaultCategoryDataset<String, String>) d2.getUnderlyingDataset();
        u2.addValue(3.0, "R1", "C3");
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultCategoryDataset<String, String> u1 = new DefaultCategoryDataset<>();
        u1.addValue(1.0, "R1", "C1");
        u1.addValue(2.0, "R1", "C2");
        SlidingCategoryDataset<String, String> d1 = new SlidingCategoryDataset<>(u1, 0, 5);
        SlidingCategoryDataset<String, String> d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);

        // basic check for independence
        u1.addValue(3.0, "R1", "C3");
        assertNotEquals(d1, d2);
        DefaultCategoryDataset<String, String> u2
                = (DefaultCategoryDataset) d2.getUnderlyingDataset();
        u2.addValue(3.0, "R1", "C3");
        assertEquals(d1, d2);
    }

    /**
     * Some checks for the getColumnCount() method.
     */
    @Test
    public void testGetColumnCount() {
        DefaultCategoryDataset<String, String> underlying = new DefaultCategoryDataset<>();
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 10, 2);
        assertEquals(0, dataset.getColumnCount());
        underlying.addValue(1.0, "R1", "C1");
        assertEquals(0, dataset.getColumnCount());
        underlying.addValue(1.0, "R1", "C2");
        assertEquals(0, dataset.getColumnCount());
        dataset.setFirstCategoryIndex(0);
        assertEquals(2, dataset.getColumnCount());
        underlying.addValue(1.0, "R1", "C3");
        assertEquals(2, dataset.getColumnCount());
        dataset.setFirstCategoryIndex(2);
        assertEquals(1, dataset.getColumnCount());
        underlying.clear();
        assertEquals(0, dataset.getColumnCount());
    }

    /**
     * Some checks for the getRowCount() method.
     */
    @Test
    public void testGetRowCount() {
        DefaultCategoryDataset<String, String> underlying = new DefaultCategoryDataset<>();
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 10, 5);
        assertEquals(0, dataset.getRowCount());
        underlying.addValue(1.0, "R1", "C1");
        assertEquals(1, dataset.getRowCount());

        underlying.clear();
        assertEquals(0, dataset.getRowCount());
    }

    /**
     * Some checks for the getColumnIndex() method.
     */
    @Test
    public void testGetColumnIndex() {
        DefaultCategoryDataset<String, String> underlying = new DefaultCategoryDataset<>();
        underlying.addValue(1.0, "R1", "C1");
        underlying.addValue(2.0, "R1", "C2");
        underlying.addValue(3.0, "R1", "C3");
        underlying.addValue(4.0, "R1", "C4");
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 1, 2);
        assertEquals(-1, dataset.getColumnIndex("C1"));
        assertEquals(0, dataset.getColumnIndex("C2"));
        assertEquals(1, dataset.getColumnIndex("C3"));
        assertEquals(-1, dataset.getColumnIndex("C4"));
    }

    /**
     * Some checks for the getRowIndex() method.
     */
    @Test
    public void testGetRowIndex() {
        DefaultCategoryDataset<String, String> underlying = new DefaultCategoryDataset<>();
        underlying.addValue(1.0, "R1", "C1");
        underlying.addValue(2.0, "R2", "C1");
        underlying.addValue(3.0, "R3", "C1");
        underlying.addValue(4.0, "R4", "C1");
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 1, 2);
        assertEquals(0, dataset.getRowIndex("R1"));
        assertEquals(1, dataset.getRowIndex("R2"));
        assertEquals(2, dataset.getRowIndex("R3"));
        assertEquals(3, dataset.getRowIndex("R4"));
    }

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultCategoryDataset<String, String> underlying 
                = new DefaultCategoryDataset<>();
        underlying.addValue(1.0, "R1", "C1");
        underlying.addValue(2.0, "R1", "C2");
        underlying.addValue(3.0, "R1", "C3");
        underlying.addValue(4.0, "R1", "C4");
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 1, 2);
        assertEquals(2.0, dataset.getValue("R1", "C2"));
        assertEquals(3.0, dataset.getValue("R1", "C3"));
        boolean pass = false;
        try {
            dataset.getValue("R1", "C1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            dataset.getValue("R1", "C4");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getColumnKeys() method.
     */
    @Test
    public void testGetColumnKeys() {
        DefaultCategoryDataset<String, String> underlying 
                = new DefaultCategoryDataset<>();
        underlying.addValue(1.0, "R1", "C1");
        underlying.addValue(2.0, "R1", "C2");
        underlying.addValue(3.0, "R1", "C3");
        underlying.addValue(4.0, "R1", "C4");
        SlidingCategoryDataset<String, String> dataset 
                = new SlidingCategoryDataset<>(underlying, 1, 2);
        List<String> keys = dataset.getColumnKeys();
        assertTrue(keys.contains("C2"));
        assertTrue(keys.contains("C3"));
        assertEquals(2, keys.size());

        dataset.setFirstCategoryIndex(3);
        keys = dataset.getColumnKeys();
        assertTrue(keys.contains("C4"));
        assertEquals(1, keys.size());
    }

}
