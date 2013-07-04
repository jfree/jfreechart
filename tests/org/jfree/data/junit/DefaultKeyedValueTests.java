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
 * ---------------------------
 * DefaultKeyedValueTests.java
 * ---------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.DefaultKeyedValue;

/**
 * Tests for the {@link DefaultKeyedValue} class.
 */
public class DefaultKeyedValueTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DefaultKeyedValueTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DefaultKeyedValueTests(String name) {
        super(name);
    }

    /**
     * Simple checks for the constructor.
     */
    public void testConstructor() {
        DefaultKeyedValue v = new DefaultKeyedValue("A", new Integer(1));
        assertEquals("A", v.getKey());
        assertEquals(new Integer(1), v.getValue());

        // try null key
        boolean pass = false;
        try {
            /*v =*/ new DefaultKeyedValue(null, new Integer(1));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // try a null value
        v = new DefaultKeyedValue("A", null);
        assertNull(v.getValue());
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        DefaultKeyedValue v1 = new DefaultKeyedValue("Test", new Double(45.5));
        DefaultKeyedValue v2 = new DefaultKeyedValue("Test", new Double(45.5));
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));

        v1 = new DefaultKeyedValue("Test 1", new Double(45.5));
        v2 = new DefaultKeyedValue("Test 2", new Double(45.5));
        assertFalse(v1.equals(v2));

        v1 = new DefaultKeyedValue("Test", new Double(45.5));
        v2 = new DefaultKeyedValue("Test", new Double(45.6));
        assertFalse(v1.equals(v2));

    }

    /**
     * Some checks for the clone() method.
     */
    public void testCloning() {
        DefaultKeyedValue v1 = new DefaultKeyedValue("Test", new Double(45.5));
        DefaultKeyedValue v2 = null;
        try {
            v2 = (DefaultKeyedValue) v1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(v1 != v2);
        assertTrue(v1.getClass() == v2.getClass());
        assertTrue(v1.equals(v2));

        // confirm that the clone is independent of the original
        v2.setValue(new Double(12.3));
        assertFalse(v1.equals(v2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DefaultKeyedValue v1 = new DefaultKeyedValue("Test", new Double(25.3));
        DefaultKeyedValue v2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(v1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            v2 = (DefaultKeyedValue) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(v1, v2);

    }

}
