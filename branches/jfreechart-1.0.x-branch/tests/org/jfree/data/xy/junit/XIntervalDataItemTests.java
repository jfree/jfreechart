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
 * ---------------------------
 * XIntervalDataItemTests.java
 * ---------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
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

import org.jfree.data.xy.XIntervalDataItem;

/**
 * Tests for the {@link XIntervalDataItem} class.
 */
public class XIntervalDataItemTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XIntervalDataItemTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XIntervalDataItemTests(String name) {
        super(name);
    }

    private static final double EPSILON = 0.00000000001;

    /**
     * Some checks for the constructor.
     */
    public void testConstructor1() {
        XIntervalDataItem item1 = new XIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        assertEquals(new Double(1.0), item1.getX());
        assertEquals(2.0, item1.getXLowValue(), EPSILON);
        assertEquals(3.0, item1.getXHighValue(), EPSILON);
        assertEquals(4.0, item1.getYValue(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        XIntervalDataItem item1 = new XIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        XIntervalDataItem item2 = new XIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));

        // x
        item1 = new XIntervalDataItem(1.1, 2.0, 3.0, 4.0);
        assertFalse(item1.equals(item2));
        item2 = new XIntervalDataItem(1.1, 2.0, 3.0, 4.0);
        assertTrue(item1.equals(item2));

        // xLow
        item1 = new XIntervalDataItem(1.1, 2.2, 3.0, 4.0);
        assertFalse(item1.equals(item2));
        item2 = new XIntervalDataItem(1.1, 2.2, 3.0, 4.0);
        assertTrue(item1.equals(item2));

        // xHigh
        item1 = new XIntervalDataItem(1.1, 2.2, 3.3, 4.0);
        assertFalse(item1.equals(item2));
        item2 = new XIntervalDataItem(1.1, 2.2, 3.3, 4.0);
        assertTrue(item1.equals(item2));

        // y
        item1 = new XIntervalDataItem(1.1, 2.2, 3.3, 4.4);
        assertFalse(item1.equals(item2));
        item2 = new XIntervalDataItem(1.1, 2.2, 3.3, 4.4);
        assertTrue(item1.equals(item2));

    }

    /**
     * Some checks for the clone() method.
     */
    public void testCloning() {
        XIntervalDataItem item1 = new XIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        XIntervalDataItem item2 = null;
        try {
            item2 = (XIntervalDataItem) item1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(item1 != item2);
        assertTrue(item1.getClass() == item2.getClass());
        assertTrue(item1.equals(item2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XIntervalDataItem item1 = new XIntervalDataItem(1.0, 2.0, 3.0, 4.0);
        XIntervalDataItem item2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(item1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            item2 = (XIntervalDataItem) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(item1, item2);
    }

}
