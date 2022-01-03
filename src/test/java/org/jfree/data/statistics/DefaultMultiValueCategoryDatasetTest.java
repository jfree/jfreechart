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
 * -----------------------------------------
 * DefaultMultiValueCategoryDatasetTest.java
 * -----------------------------------------
 * (C) Copyright 2007-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.statistics;

import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultMultiValueCategoryDataset} class.
 */
public class DefaultMultiValueCategoryDatasetTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultMultiValueCategoryDataset<String, String> d
                = new DefaultMultiValueCategoryDataset<>();
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        d.add(values, "R1", "C1");
        assertEquals(1.5, d.getValue("R1", "C1"));
        boolean pass = false;
        try {
            d.getValue("XX", "C1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d.getValue("R1", "XX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * A simple check for the getValue(int, int) method.
     */
    @Test
    public void testGetValue2() {
        DefaultMultiValueCategoryDataset<String, String> d
                = new DefaultMultiValueCategoryDataset<>();
        boolean pass = false;
        try {
            /* Number n =*/ d.getValue(0, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some tests for the getRowCount() method.
     */
    @Test
    public void testGetRowCount() {
        DefaultMultiValueCategoryDataset<String, String> d
                = new DefaultMultiValueCategoryDataset<>();
        assertEquals(0, d.getRowCount());
        List<Number> values = new ArrayList<>();
        d.add(values, "R1", "C1");
        assertEquals(1, d.getRowCount());

        d.add(values, "R2", "C1");
        assertEquals(2, d.getRowCount());

        d.add(values, "R2", "C1");
        assertEquals(2, d.getRowCount());
    }

    /**
     * Some tests for the getColumnCount() method.
     */
    @Test
    public void testGetColumnCount() {
        DefaultMultiValueCategoryDataset<String, String> d
                = new DefaultMultiValueCategoryDataset<>();
        assertEquals(0, d.getColumnCount());

        List<Number> values = new ArrayList<>();
        d.add(values, "R1", "C1");
        assertEquals(1, d.getColumnCount());

        d.add(values, "R1", "C2");
        assertEquals(2, d.getColumnCount());

        d.add(values, "R1", "C2");
        assertEquals(2, d.getColumnCount());

    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultMultiValueCategoryDataset<String, String> d1
                = new DefaultMultiValueCategoryDataset<>();
        DefaultMultiValueCategoryDataset<String, String> d2
                = new DefaultMultiValueCategoryDataset<>();
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        List<Number> values = new ArrayList<>();
        d1.add(values, "R1", "C1");
        assertNotEquals(d1, d2);
        d2.add(values, "R1", "C1");
        assertEquals(d1, d2);

        values.add(99);
        d1.add(values, "R1", "C1");
        assertNotEquals(d1, d2);
        d2.add(values, "R1", "C1");
        assertEquals(d1, d2);

        values.add(99);
        d1.add(values, "R1", "C2");
        assertNotEquals(d1, d2);
        d2.add(values, "R1", "C2");
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultMultiValueCategoryDataset<String, String> d1
                = new DefaultMultiValueCategoryDataset<>();
        DefaultMultiValueCategoryDataset<String, String> d2 
                = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Some checks for the add() method.
     */
    @Test
    public void testAddValue() {
        DefaultMultiValueCategoryDataset<String, String> d1
                = new DefaultMultiValueCategoryDataset<>();

        boolean pass = false;
        try {
            d1.add(null, "R1", "C1");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        List<Number> values = new ArrayList<>();
        d1.add(values, "R2", "C1");
        assertEquals(values, d1.getValues("R2", "C1"));

        pass = false;
        try {
            d1.add(values, null, "C2");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Confirm that clon
     * @throws java.lang.CloneNotSupportedExceptioning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultMultiValueCategoryDataset<String, String> d1
                = new DefaultMultiValueCategoryDataset<>();
        DefaultMultiValueCategoryDataset<String, String> d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // try a dataset with some content...
        List<Integer> values = new ArrayList<>();
        values.add(99);
        d1.add(values, "R1", "C1");
        d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // check that the clone doesn't share the same underlying arrays.
        List<Integer> values2 = new ArrayList<>();
        values2.add(111);
        d1.add(values2, "R2", "C2");
        assertNotEquals(d1, d2);
        d2.add(values2, "R2", "C2");
        assertEquals(d1, d2);
    }

}
