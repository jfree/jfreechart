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
 * -----------------------
 * KeyedObjects2DTest.java
 * -----------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 01-Mar-2004 : Version 1 (DG);
 * 28-Sep-2007 : Added testEquals() and enhanced testClone() (DG);
 * 03-Oct-2007 : Added new tests (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtilities;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Tests for the {@link KeyedObjects2D} class.
 */
public class KeyedObjects2DTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        KeyedObjects2D k1 = new KeyedObjects2D();
        KeyedObjects2D k2 = new KeyedObjects2D();
        assertTrue(k1.equals(k2));
        assertTrue(k2.equals(k1));

        k1.addObject(new Integer(99), "R1", "C1");
        assertFalse(k1.equals(k2));
        k2.addObject(new Integer(99), "R1", "C1");
        assertTrue(k1.equals(k2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyedObjects2D o1 = new KeyedObjects2D();
        o1.setObject(new Integer(1), "V1", "C1");
        o1.setObject(null, "V2", "C1");
        o1.setObject(new Integer(3), "V3", "C2");
        KeyedObjects2D o2 = (KeyedObjects2D) o1.clone();
        assertTrue(o1 != o2);
        assertTrue(o1.getClass() == o2.getClass());
        assertTrue(o1.equals(o2));

        // check independence
        o1.addObject("XX", "R1", "C1");
        assertFalse(o1.equals(o2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyedObjects2D ko2D1 = new KeyedObjects2D();
        ko2D1.addObject(new Double(234.2), "Row1", "Col1");
        ko2D1.addObject(null, "Row1", "Col2");
        ko2D1.addObject(new Double(345.9), "Row2", "Col1");
        ko2D1.addObject(new Double(452.7), "Row2", "Col2");

        KeyedObjects2D ko2D2 = (KeyedObjects2D) TestUtilities.serialised(ko2D1);
        assertEquals(ko2D1, ko2D2);
    }

    /**
     * Some checks for the getValue(int, int) method.
     */
    @Test
    public void testGetValueByIndex() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.addObject("Obj1", "R1", "C1");
        data.addObject("Obj2", "R2", "C2");
        assertEquals("Obj1", data.getObject(0, 0));
        assertEquals("Obj2", data.getObject(1, 1));
        assertNull(data.getObject(0, 1));
        assertNull(data.getObject(1, 0));

        // check invalid indices
        boolean pass = false;
        try {
            data.getObject(-1, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject(0, -1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject(2, 0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject(0, 2);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getValue(Comparable, Comparable) method.
     */
    @Test
    public void testGetValueByKey() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.addObject("Obj1", "R1", "C1");
        data.addObject("Obj2", "R2", "C2");
        assertEquals("Obj1", data.getObject("R1", "C1"));
        assertEquals("Obj2", data.getObject("R2", "C2"));
        assertNull(data.getObject("R1", "C2"));
        assertNull(data.getObject("R2", "C1"));

        // check invalid indices
        boolean pass = false;
        try {
            data.getObject("XX", "C1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject("R1", "XX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject("XX", "C1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.getObject("R1", "XX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the setObject(Object, Comparable, Comparable) method.
     */
    @Test
    public void testSetObject() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        assertEquals("Obj1", data.getObject("R1", "C1"));
        assertEquals("Obj2", data.getObject("R2", "C2"));
        assertNull(data.getObject("R1", "C2"));
        assertNull(data.getObject("R2", "C1"));

        // confirm overwriting an existing value
        data.setObject("ABC", "R2", "C2");
        assertEquals("ABC", data.getObject("R2", "C2"));

        // try null keys
        boolean pass = false;
        try {
            data.setObject("X", null, "C1");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            data.setObject("X", "R1", null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeRow(int) method.
     */
    @Test
    public void testRemoveRowByIndex() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeRow(0);
        assertEquals(1, data.getRowCount());
        assertEquals("Obj2", data.getObject(0, 1));

        // try negative row index
        boolean pass = false;
        try {
            data.removeRow(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        // try row index too high
        pass = false;
        try {
            data.removeRow(data.getRowCount());
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeColumn(int) method.
     */
    @Test
    public void testRemoveColumnByIndex() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeColumn(0);
        assertEquals(1, data.getColumnCount());
        assertEquals("Obj2", data.getObject(1, 0));

        // try negative column index
        boolean pass = false;
        try {
            data.removeColumn(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        // try column index too high
        pass = false;
        try {
            data.removeColumn(data.getColumnCount());
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeRow(Comparable) method.
     */
    @Test
    public void testRemoveRowByKey() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeRow("R2");
        assertEquals(1, data.getRowCount());
        assertEquals("Obj1", data.getObject(0, 0));

        // try unknown row key
        boolean pass = false;
        try {
            data.removeRow("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // try null row key
        pass = false;
        try {
            data.removeRow(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeColumn(Comparable) method.
     */
    @Test
    public void testRemoveColumnByKey() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeColumn("C2");
        assertEquals(1, data.getColumnCount());
        assertEquals("Obj1", data.getObject(0, 0));

        // try unknown column key
        boolean pass = false;
        try {
            data.removeColumn("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // try null column key
        pass = false;
        try {
            data.removeColumn(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * A simple check for the removeValue() method.
     */
    @Test
    public void testRemoveValue() {
        KeyedObjects2D data = new KeyedObjects2D();
        data.setObject("Obj1", "R1", "C1");
        data.setObject("Obj2", "R2", "C2");
        data.removeObject("R2", "C2");
        assertEquals(1, data.getRowCount());
        assertEquals(1, data.getColumnCount());
        assertEquals("Obj1", data.getObject(0, 0));
    }

}
