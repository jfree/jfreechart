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
 * ---------------------
 * XYCoordinateTest.java
 * ---------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jan-2007 : Version 1 (DG);
 *
 */

package org.jfree.data.xy;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.TestUtilities;


/**
 * Tests for the {@link XYCoordinate} class.
 */
public class XYCoordinateTest extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYCoordinateTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYCoordinateTest(String name) {
        super(name);
    }

    /**
     * Test that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        // default instances
        XYCoordinate v1 = new XYCoordinate(1.0, 2.0);
        XYCoordinate v2 = new XYCoordinate(1.0, 2.0);
        assertTrue(v1.equals(v2));
        assertTrue(v2.equals(v1));

        v1 = new XYCoordinate(1.1, 2.0);
        assertFalse(v1.equals(v2));
        v2 = new XYCoordinate(1.1, 2.0);
        assertTrue(v1.equals(v2));

        v1 = new XYCoordinate(1.1, 2.2);
        assertFalse(v1.equals(v2));
        v2 = new XYCoordinate(1.1, 2.2);
        assertTrue(v1.equals(v2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        XYCoordinate v1 = new XYCoordinate(1.0, 2.0);
        XYCoordinate v2 = new XYCoordinate(1.0, 2.0);
        assertTrue(v1.equals(v2));
        int h1 = v1.hashCode();
        int h2 = v2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Immutable class is not cloneable.
     */
    public void testCloning() {
        XYCoordinate v1 = new XYCoordinate(1.0, 2.0);
        assertFalse(v1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYCoordinate v1 = new XYCoordinate(1.0, 2.0);
        XYCoordinate v2 = (XYCoordinate) TestUtilities.serialised(v1);
        assertEquals(v1, v2);
    }

}
