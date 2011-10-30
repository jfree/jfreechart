/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * ----------------------
 * MatrixSeriesTests.java
 * ----------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-May-2004 : Version 1 (DG);
 *
 */

package org.jfree.data.xy.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.xy.MatrixSeries;

/**
 * Tests for the {@link MatrixSeries} class.
 */
public class MatrixSeriesTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MatrixSeriesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MatrixSeriesTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
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
    public void testCloning() {
        MatrixSeries m1 = new MatrixSeries("Test", 8, 3);
        m1.update(0, 0, 11.0);
        m1.update(7, 2, 22.0);
        MatrixSeries m2 = null;
        try {
            m2 = (MatrixSeries) m1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(m1 != m2);
        assertTrue(m1.getClass() == m2.getClass());
        assertTrue(m1.equals(m2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        MatrixSeries m1 = new MatrixSeries("Test", 8, 3);
        m1.update(0, 0, 11.0);
        m1.update(7, 2, 22.0);
        MatrixSeries m2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(m1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            m2 = (MatrixSeries) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(m1, m2);
    }

    /**
     * Tests the getItemColumn() method.
     */
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
