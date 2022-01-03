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
 * --------------------------------
 * DefaultCategoryDatasetTests.java
 * --------------------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.category;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.UnknownKeyException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultCategoryDataset} class.
 */
public class DefaultCategoryDatasetTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultCategoryDataset<String, String> d = new DefaultCategoryDataset<>();
        d.addValue(1.0, "R1", "C1");
        assertEquals(1.0, d.getValue("R1", "C1"));
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
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
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
     * Some checks for the incrementValue() method.
     */
    @Test
    public void testIncrementValue() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        d.addValue(1.0, "R1", "C1");
        d.incrementValue(2.0, "R1", "C1");
        assertEquals(3.0, d.getValue("R1", "C1"));

        // increment a null value
        d.addValue(null, "R2", "C1");
        d.incrementValue(2.0, "R2", "C1");
        assertEquals(2.0, d.getValue("R2", "C1"));

        // increment an unknown row
        boolean pass = false;
        try {
            d.incrementValue(1.0, "XX", "C1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // increment an unknown column
        pass = false;
        try {
            d.incrementValue(1.0, "R1", "XX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some tests for the getRowCount() method.
     */
    @Test
    public void testGetRowCount() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        assertEquals(0, d.getRowCount());

        d.addValue(1.0, "R1", "C1");
        assertEquals(1, d.getRowCount());

        d.addValue(1.0, "R2", "C1");
        assertEquals(2, d.getRowCount());

        d.addValue(2.0, "R2", "C1");
        assertEquals(2, d.getRowCount());

        // a row of all null values is still counted...
        d.setValue(null, "R2", "C1");
        assertEquals(2, d.getRowCount());
    }

    /**
     * Some tests for the getColumnCount() method.
     */
    @Test
    public void testGetColumnCount() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        assertEquals(0, d.getColumnCount());

        d.addValue(1.0, "R1", "C1");
        assertEquals(1, d.getColumnCount());

        d.addValue(1.0, "R1", "C2");
        assertEquals(2, d.getColumnCount());

        d.addValue(2.0, "R1", "C2");
        assertEquals(2, d.getColumnCount());

        // a column of all null values is still counted...
        d.setValue(null, "R1", "C2");
        assertEquals(2, d.getColumnCount());
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultCategoryDataset<String,String> d1 = new DefaultCategoryDataset<>();
        d1.setValue(23.4, "R1", "C1");
        DefaultCategoryDataset<String,String> d2 = new DefaultCategoryDataset<>();
        d2.setValue(23.4, "R1", "C1");
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        d1.setValue(36.5, "R1", "C2");
        assertNotEquals(d1, d2);
        d2.setValue(36.5, "R1", "C2");
        assertEquals(d1, d2);

        d1.setValue(null, "R1", "C1");
        assertNotEquals(d1, d2);
        d2.setValue(null, "R1", "C1");
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultCategoryDataset<String,String> d1 = new DefaultCategoryDataset<>();
        d1.setValue(23.4, "R1", "C1");
        DefaultCategoryDataset<String, String> d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Some checks for the addValue() method.
     */
    @Test
    public void testAddValue() {
        DefaultCategoryDataset<String,String> d1 = new DefaultCategoryDataset<>();
        d1.addValue(null, "R1", "C1");
        assertNull(d1.getValue("R1", "C1"));
        d1.addValue(1.0, "R2", "C1");
        assertEquals(1.0, d1.getValue("R2", "C1"));

        boolean pass = false;
        try {
            d1.addValue(1.1, null, "C2");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some basic checks for the removeValue() method.
     */
    @Test
    public void testRemoveValue() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        d.removeValue("R1", "C1");
        d.addValue(1.0, "R1", "C1");
        d.removeValue("R1", "C1");
        assertEquals(0, d.getRowCount());
        assertEquals(0, d.getColumnCount());

        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C1");
        d.removeValue("R1", "C1");
        assertEquals(2.0, d.getValue(0, 0));

        boolean pass = false;
        try {
            d.removeValue(null, "C1");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d.removeValue("R1", null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultCategoryDataset<String,String> d1 = new DefaultCategoryDataset<>();
        DefaultCategoryDataset<String,String> d2 = CloneUtils.clone(d1);

        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // try a dataset with some content...
        d1.addValue(1.0, "R1", "C1");
        d1.addValue(2.0, "R1", "C2");
        d2 = CloneUtils.clone(d1);

        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // check that the clone doesn't share the same underlying arrays.
        d1.addValue(3.0, "R1", "C1");
        assertNotEquals(d1, d2);
        d2.addValue(3.0, "R1", "C1");
        assertEquals(d1, d2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        assertTrue(d instanceof PublicCloneable);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * A test for bug 1835955.
     */
    @Test
    public void testBug1835955() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C2");
        d.removeColumn("C2");
        d.addValue(3.0, "R2", "C2");
        assertEquals(3.0, d.getValue("R2", "C2").doubleValue(), EPSILON);
    }

    /**
     * Some checks for the removeColumn(Comparable) method.
     */
    @Test
    public void testRemoveColumn() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C2");
        assertEquals(2, d.getColumnCount());
        d.removeColumn("C2");
        assertEquals(1, d.getColumnCount());

        boolean pass = false;
        try {
            d.removeColumn("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d.removeColumn(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeRow(Comparable) method.
     */
    @Test
    public void testRemoveRow() {
        DefaultCategoryDataset<String,String> d = new DefaultCategoryDataset<>();
        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C2");
        assertEquals(2, d.getRowCount());
        d.removeRow("R2");
        assertEquals(1, d.getRowCount());

        boolean pass = false;
        try {
            d.removeRow("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d.removeRow(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

}
