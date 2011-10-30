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
 * ---------------
 * RangeTests.java
 * ---------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Sergei Ivanov;
 *
 * Changes
 * -------
 * 14-Aug-2003 : Version 1 (DG);
 * 18-Dec-2007 : Additional tests from Sergei Ivanov (DG);
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

import org.jfree.data.Range;

/**
 * Tests for the {@link Range} class.
 */
public class RangeTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RangeTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RangeTests(String name) {
        super(name);
    }

    /**
     * Confirm that the constructor initializes all the required fields.
     */
    public void testConstructor() {
        Range r1 = new Range(0.1, 1000.0);
        assertEquals(r1.getLowerBound(), 0.1, 0.0d);
        assertEquals(r1.getUpperBound(), 1000.0, 0.0d);

        try {
            /*Range r2 =*/ new Range(10.0, 0.0);
            fail("Lower bound cannot be greater than the upper");
        }
        catch (Exception e) {
            // ignore
        }
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        Range r1 = new Range(0.0, 1.0);
        Range r2 = new Range(0.0, 1.0);
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1 = new Range(0.0, 1.0);
        r2 = new Range(0.5, 1.0);
        assertFalse(r1.equals(r2));

        r1 = new Range(0.0, 1.0);
        r2 = new Range(0.0, 2.0);
        assertFalse(r1.equals(r2));

        assertFalse(r1.equals(new Double(0.0)));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        Range a1 = new Range(1.0, 100.0);
        Range a2 = new Range(1.0, 100.0);
        assertEquals(a1.hashCode(), a2.hashCode());

        a1 = new Range(-100.0, 2.0);
        a2 = new Range(-100.0, 2.0);
        assertEquals(a1.hashCode(), a2.hashCode());
    }

    /**
     * Simple tests for the contains() method.
     */
    public void testContains() {
        Range r1 = new Range(0.0, 1.0);
        assertFalse(r1.contains(Double.NaN));
        assertFalse(r1.contains(Double.NEGATIVE_INFINITY));
        assertFalse(r1.contains(-1.0));
        assertTrue(r1.contains(0.0));
        assertTrue(r1.contains(0.5));
        assertTrue(r1.contains(1.0));
        assertFalse(r1.contains(2.0));
        assertFalse(r1.contains(Double.POSITIVE_INFINITY));
    }

    /**
     * Tests the constrain() method for various values.
     */
    public void testConstrain() {
        Range r1 = new Range(0.0, 1.0);

        double d = r1.constrain(0.5);
        assertEquals(0.5, d, 0.0000001);

        d = r1.constrain(0.0);
        assertEquals(0.0, d, 0.0000001);

        d = r1.constrain(1.0);
        assertEquals(1.0, d, 0.0000001);

        d = r1.constrain(-1.0);
        assertEquals(0.0, d, 0.0000001);

        d = r1.constrain(2.0);
        assertEquals(1.0, d, 0.0000001);

        d = r1.constrain(Double.POSITIVE_INFINITY);
        assertEquals(1.0, d, 0.0000001);

        d = r1.constrain(Double.NEGATIVE_INFINITY);
        assertEquals(0.0, d, 0.0000001);

        d = r1.constrain(Double.NaN);
        assertTrue(Double.isNaN(d));
    }

    /**
     * Simple tests for the intersects() method.
     */
    public void testIntersects() {
        Range r1 = new Range(0.0, 1.0);
        assertFalse(r1.intersects(-2.0, -1.0));
        assertFalse(r1.intersects(-2.0, 0.0));
        assertTrue(r1.intersects(-2.0, 0.5));
        assertTrue(r1.intersects(-2.0, 1.0));
        assertTrue(r1.intersects(-2.0, 1.5));
        assertTrue(r1.intersects(0.0, 0.5));
        assertTrue(r1.intersects(0.0, 1.0));
        assertTrue(r1.intersects(0.0, 1.5));
        assertTrue(r1.intersects(0.5, 0.6));
        assertTrue(r1.intersects(0.5, 1.0));
        assertTrue(r1.intersects(0.5, 1.5));
        assertFalse(r1.intersects(1.0, 1.1));
        assertFalse(r1.intersects(1.5, 2.0));
    }

    /**
     * A simple test for the expand() method.
     */
    public void testExpand() {
        Range r1 = new Range(0.0, 100.0);
        Range r2 = Range.expand(r1, 0.10, 0.10);
        assertEquals(-10.0, r2.getLowerBound(), 0.001);
        assertEquals(110.0, r2.getUpperBound(), 0.001);

        // Expand by 0% does not change the range
        r2 = Range.expand(r1, 0.0, 0.0);
        assertEquals(r1, r2);

        try {
            Range.expand(null, 0.1, 0.1);
            fail("Null value is accepted");
        }
        catch (Exception e) {
        }

        // Lower > upper: mid point is used
        r2 = Range.expand(r1, -0.8, -0.5);
        assertEquals(65.0, r2.getLowerBound(), 0.001);
        assertEquals(65.0, r2.getUpperBound(), 0.001);
    }

    /**
     * A simple test for the scale() method.
     */
    public void testShift() {
        Range r1 = new Range(10.0, 20.0);
        Range r2 = Range.shift(r1, 20.0);
        assertEquals(30.0, r2.getLowerBound(), 0.001);
        assertEquals(40.0, r2.getUpperBound(), 0.001);

        r1 = new Range(0.0, 100.0);
        r2 = Range.shift(r1, -50.0, true);
        assertEquals(-50.0, r2.getLowerBound(), 0.001);
        assertEquals(50.0, r2.getUpperBound(), 0.001);

        r1 = new Range(-10.0, 20.0);
        r2 = Range.shift(r1, 20.0, true);
        assertEquals(10.0, r2.getLowerBound(), 0.001);
        assertEquals(40.0, r2.getUpperBound(), 0.001);

        r1 = new Range(-10.0, 20.0);
        r2 = Range.shift(r1, -30.0, true);
        assertEquals(-40.0, r2.getLowerBound(), 0.001);
        assertEquals(-10.0, r2.getUpperBound(), 0.001);

        r1 = new Range(-10.0, 20.0);
        r2 = Range.shift(r1, 20.0, false);
        assertEquals(0.0, r2.getLowerBound(), 0.001);
        assertEquals(40.0, r2.getUpperBound(), 0.001);

        r1 = new Range(-10.0, 20.0);
        r2 = Range.shift(r1, -30.0, false);
        assertEquals(-40.0, r2.getLowerBound(), 0.001);
        assertEquals(0.0, r2.getUpperBound(), 0.001);

        // Shifting with a delta of 0 does not change the range
        r2 = Range.shift(r1, 0.0);
        assertEquals(r1, r2);

        try {
            Range.shift(null, 0.1);
            fail("Null value is accepted");
        }
        catch (Exception e) {
        }
    }

    /**
     * A simple test for the scale() method.
     */
    public void testScale() {
        Range r1 = new Range(0.0, 100.0);
        Range r2 = Range.scale(r1, 0.10);
        assertEquals(0.0, r2.getLowerBound(), 0.001);
        assertEquals(10.0, r2.getUpperBound(), 0.001);

        r1 = new Range(-10.0, 100.0);
        r2 = Range.scale(r1, 2.0);
        assertEquals(-20.0, r2.getLowerBound(), 0.001);
        assertEquals(200.0, r2.getUpperBound(), 0.001);

        // Scaling with a factor of 1 does not change the range
        r2 = Range.scale(r1, 1.0);
        assertEquals(r1, r2);

        try {
            Range.scale(null, 0.1);
            fail("Null value is accepted");
        }
        catch (Exception e) {
        }

        try {
            Range.scale(r1, -0.5);
            fail("Negative factor accepted");
        }
        catch (Exception e) {
        }
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        Range r1 = new Range(25.0, 133.42);
        Range r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            r2 = (Range) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
            fail("Serialization is not supported");
        }
        assertEquals(r1, r2);

    }

}
