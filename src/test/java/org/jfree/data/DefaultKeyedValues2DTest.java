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
 * -----------------------------
 * DefaultKeyedValues2DTest.java
 * -----------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultKeyedValues2D} class.
 */
public class DefaultKeyedValues2DTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
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
     * Some checks for the clone() method.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValues2D<String, String> v1 = new DefaultKeyedValues2D<>();
        v1.setValue(1, "V1", "C1");
        v1.setValue(null, "V2", "C1");
        v1.setValue(3, "V3", "C2");
        DefaultKeyedValues2D<String, String> v2 = CloneUtils.clone(v1);
        assertNotSame(v1, v2);
        assertSame(v1.getClass(), v2.getClass());
        assertEquals(v1, v2);

        // check that clone is independent of the original
        v2.setValue(2, "V2", "C1");
        assertNotEquals(v1, v2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValues2D<String, String> kv2D1 = new DefaultKeyedValues2D<>();
        kv2D1.addValue(234.2, "Row1", "Col1");
        kv2D1.addValue(null, "Row1", "Col2");
        kv2D1.addValue(345.9, "Row2", "Col1");
        kv2D1.addValue(452.7, "Row2", "Col2");

        DefaultKeyedValues2D<String, String> kv2D2 = TestUtils.serialised(kv2D1);
        assertEquals(kv2D1, kv2D2);
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        DefaultKeyedValues2D<String, String> d1 = new DefaultKeyedValues2D<>();
        DefaultKeyedValues2D<String, String> d2 = new DefaultKeyedValues2D<>();
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        d1.addValue(1.0, "R1", "C1");
        assertNotEquals(d1, d2);
        d2.addValue(1.0, "R1", "C1");
        assertEquals(d1, d2);
    }

    /**
     * Populates a data structure with sparse entries, then checks that
     * the unspecified entries return null.
     */
    @Test
    public void testSparsePopulation() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        d.addValue(11, "R1", "C1");
        d.addValue(22, "R2", "C2");

        assertEquals(11, d.getValue("R1", "C1"));
        assertNull(d.getValue("R1", "C2"));
        assertEquals(22, d.getValue("R2", "C2"));
        assertNull(d.getValue("R2", "C1"));
    }

    /**
     * Some basic checks for the getRowCount() method.
     */
    @Test
    public void testRowCount() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        assertEquals(0, d.getRowCount());
        d.addValue(1.0, "R1", "C1");
        assertEquals(1, d.getRowCount());
        d.addValue(2.0, "R2", "C1");
        assertEquals(2, d.getRowCount());
    }

    /**
     * Some basic checks for the getColumnCount() method.
     */
    @Test
    public void testColumnCount() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        assertEquals(0, d.getColumnCount());
        d.addValue(1.0, "R1", "C1");
        assertEquals(1, d.getColumnCount());
        d.addValue(2.0, "R1", "C2");
        assertEquals(2, d.getColumnCount());
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some basic checks for the getValue(int, int) method.
     */
    @Test
    public void testGetValue2() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        boolean pass = false;
        try {
            d.getValue(0, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(1.0, "R1", "C1");
        assertEquals(1.0, d.getValue(0, 0).doubleValue(), EPSILON);
        d.addValue(2.0, "R2", "C2");
        assertEquals(2.0, d.getValue(1, 1).doubleValue(), EPSILON);
        assertNull(d.getValue(1, 0));
        assertNull(d.getValue(0, 1));

        pass = false;
        try {
            d.getValue(2, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some basic checks for the getRowKey() method.
     */
    @Test
    public void testGetRowKey() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        boolean pass = false;
        try {
            d.getRowKey(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(1.0, "R1", "C1");
        d.addValue(1.0, "R2", "C1");
        assertEquals("R1", d.getRowKey(0));
        assertEquals("R2", d.getRowKey(1));

        // check sorted rows
        d = new DefaultKeyedValues2D<>(true);
        d.addValue(1.0, "R1", "C1");
        assertEquals("R1", d.getRowKey(0));
        d.addValue(0.0, "R0", "C1");
        assertEquals("R0", d.getRowKey(0));
        assertEquals("R1", d.getRowKey(1));
    }

    /**
     * Some basic checks for the getColumnKey() method.
     */
    @Test
    public void testGetColumnKey() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        boolean pass = false;
        try {
            d.getColumnKey(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(1.0, "R1", "C1");
        d.addValue(1.0, "R1", "C2");
        assertEquals("C1", d.getColumnKey(0));
        assertEquals("C2", d.getColumnKey(1));
    }

    /**
     * Some basic checks for the removeValue() method.
     */
    @Test
    public void testRemoveValue() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        d.removeValue("R1", "C1");
        d.addValue(1.0, "R1", "C1");
        d.removeValue("R1", "C1");
        assertEquals(0, d.getRowCount());
        assertEquals(0, d.getColumnCount());

        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C1");
        d.removeValue("R1", "C1");
        assertEquals(2.0, d.getValue(0, 0));
    }

    /**
     * A test for bug 1690654.
     */
    @Test
    public void testRemoveValueBug1690654() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C2");
        assertEquals(2, d.getColumnCount());
        assertEquals(2, d.getRowCount());
        d.removeValue("R2", "C2");
        assertEquals(1, d.getColumnCount());
        assertEquals(1, d.getRowCount());
        assertEquals(1.0, d.getValue(0, 0));
    }

    /**
     * Some basic checks for the removeRow() method.
     */
    @Test
    public void testRemoveRow() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        boolean pass = false;
        try {
            d.removeRow(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some basic checks for the removeColumn(Comparable) method.
     */
    @Test
    public void testRemoveColumnByKey() {
        DefaultKeyedValues2D<String, String> d = new DefaultKeyedValues2D<>();
        d.addValue(1.0, "R1", "C1");
        d.addValue(2.0, "R2", "C2");
        d.removeColumn("C2");
        d.addValue(3.0, "R2", "C2");
        assertEquals(3.0, d.getValue("R2", "C2").doubleValue(), EPSILON);

        // check for unknown column
        boolean pass = false;
        try {
            d.removeColumn("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

}
