/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * (C) Copyright 2003-2013 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Mar-2003 : Version 1 (DG);
 * 15-Sep-2004 : Updated cloning test (DG);
 * 06-Oct-2005 : Added testEquals() (DG);
 * 18-Jan-2007 : Added testSparsePopulation() (DG);
 * 26-Feb-2007 : Added some basic tests (DG);
 * 30-Mar-2007 : Added a test for bug 1690654 (DG);
 * 21-Nov-2007 : Added testRemoveColumnByKey() method (DG);
 *
 */

package org.jfree.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link DefaultKeyedValues2D} class.
 */
public class DefaultKeyedValues2DTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        d.addValue(new Double(1.0), "R1", "C1");
        assertEquals(new Double(1.0), d.getValue("R1", "C1"));
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
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValues2D v1 = new DefaultKeyedValues2D();
        v1.setValue(new Integer(1), "V1", "C1");
        v1.setValue(null, "V2", "C1");
        v1.setValue(new Integer(3), "V3", "C2");
        DefaultKeyedValues2D v2 = (DefaultKeyedValues2D) v1.clone();
        assertTrue(v1 != v2);
        assertTrue(v1.getClass() == v2.getClass());
        assertTrue(v1.equals(v2));

        // check that clone is independent of the original
        v2.setValue(new Integer(2), "V2", "C1");
        assertFalse(v1.equals(v2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValues2D kv2D1 = new DefaultKeyedValues2D();
        kv2D1.addValue(new Double(234.2), "Row1", "Col1");
        kv2D1.addValue(null, "Row1", "Col2");
        kv2D1.addValue(new Double(345.9), "Row2", "Col1");
        kv2D1.addValue(new Double(452.7), "Row2", "Col2");

        DefaultKeyedValues2D kv2D2 = (DefaultKeyedValues2D) 
                TestUtilities.serialised(kv2D1);
        assertEquals(kv2D1, kv2D2);
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        DefaultKeyedValues2D d1 = new DefaultKeyedValues2D();
        DefaultKeyedValues2D d2 = new DefaultKeyedValues2D();
        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        d1.addValue(new Double(1.0), new Double(2.0), "S1");
        assertFalse(d1.equals(d2));
        d2.addValue(new Double(1.0), new Double(2.0), "S1");
        assertTrue(d1.equals(d2));
    }

    /**
     * Populates a data structure with sparse entries, then checks that
     * the unspecified entries return null.
     */
    @Test
    public void testSparsePopulation() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        d.addValue(new Integer(11), "R1", "C1");
        d.addValue(new Integer(22), "R2", "C2");

        assertEquals(new Integer(11), d.getValue("R1", "C1"));
        assertNull(d.getValue("R1", "C2"));
        assertEquals(new Integer(22), d.getValue("R2", "C2"));
        assertNull(d.getValue("R2", "C1"));
    }

    /**
     * Some basic checks for the getRowCount() method.
     */
    @Test
    public void testRowCount() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        assertEquals(0, d.getRowCount());
        d.addValue(new Double(1.0), "R1", "C1");
        assertEquals(1, d.getRowCount());
        d.addValue(new Double(2.0), "R2", "C1");
        assertEquals(2, d.getRowCount());
    }

    /**
     * Some basic checks for the getColumnCount() method.
     */
    @Test
    public void testColumnCount() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        assertEquals(0, d.getColumnCount());
        d.addValue(new Double(1.0), "R1", "C1");
        assertEquals(1, d.getColumnCount());
        d.addValue(new Double(2.0), "R1", "C2");
        assertEquals(2, d.getColumnCount());
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some basic checks for the getValue(int, int) method.
     */
    @Test
    public void testGetValue2() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        boolean pass = false;
        try {
            d.getValue(0, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(new Double(1.0), "R1", "C1");
        assertEquals(1.0, d.getValue(0, 0).doubleValue(), EPSILON);
        d.addValue(new Double(2.0), "R2", "C2");
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
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        boolean pass = false;
        try {
            d.getRowKey(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(new Double(1.0), "R1", "C1");
        d.addValue(new Double(1.0), "R2", "C1");
        assertEquals("R1", d.getRowKey(0));
        assertEquals("R2", d.getRowKey(1));

        // check sorted rows
        d = new DefaultKeyedValues2D(true);
        d.addValue(new Double(1.0), "R1", "C1");
        assertEquals("R1", d.getRowKey(0));
        d.addValue(new Double(0.0), "R0", "C1");
        assertEquals("R0", d.getRowKey(0));
        assertEquals("R1", d.getRowKey(1));
    }

    /**
     * Some basic checks for the getColumnKey() method.
     */
    @Test
    public void testGetColumnKey() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        boolean pass = false;
        try {
            d.getColumnKey(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
        d.addValue(new Double(1.0), "R1", "C1");
        d.addValue(new Double(1.0), "R1", "C2");
        assertEquals("C1", d.getColumnKey(0));
        assertEquals("C2", d.getColumnKey(1));
    }

    /**
     * Some basic checks for the removeValue() method.
     */
    @Test
    public void testRemoveValue() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        d.removeValue("R1", "C1");
        d.addValue(new Double(1.0), "R1", "C1");
        d.removeValue("R1", "C1");
        assertEquals(0, d.getRowCount());
        assertEquals(0, d.getColumnCount());

        d.addValue(new Double(1.0), "R1", "C1");
        d.addValue(new Double(2.0), "R2", "C1");
        d.removeValue("R1", "C1");
        assertEquals(new Double(2.0), d.getValue(0, 0));
    }

    /**
     * A test for bug 1690654.
     */
    @Test
    public void testRemoveValueBug1690654() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        d.addValue(new Double(1.0), "R1", "C1");
        d.addValue(new Double(2.0), "R2", "C2");
        assertEquals(2, d.getColumnCount());
        assertEquals(2, d.getRowCount());
        d.removeValue("R2", "C2");
        assertEquals(1, d.getColumnCount());
        assertEquals(1, d.getRowCount());
        assertEquals(new Double(1.0), d.getValue(0, 0));
    }

    /**
     * Some basic checks for the removeRow() method.
     */
    @Test
    public void testRemoveRow() {
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
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
        DefaultKeyedValues2D d = new DefaultKeyedValues2D();
        d.addValue(new Double(1.0), "R1", "C1");
        d.addValue(new Double(2.0), "R2", "C2");
        d.removeColumn("C2");
        d.addValue(new Double(3.0), "R2", "C2");
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
