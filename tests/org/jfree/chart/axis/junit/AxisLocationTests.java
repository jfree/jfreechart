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
 * AxisLocationTests.java
 * ----------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jul-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 *
 */

package org.jfree.chart.axis.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.AxisLocation;

/**
 * Tests for the {@link AxisLocation} class.
 */
public class AxisLocationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(AxisLocationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public AxisLocationTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        assertEquals(AxisLocation.TOP_OR_RIGHT, AxisLocation.TOP_OR_RIGHT);
        assertEquals(AxisLocation.BOTTOM_OR_RIGHT,
                AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(AxisLocation.TOP_OR_LEFT, AxisLocation.TOP_OR_LEFT);
        assertEquals(AxisLocation.BOTTOM_OR_LEFT, AxisLocation.BOTTOM_OR_LEFT);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        AxisLocation a1 = AxisLocation.TOP_OR_RIGHT;
        AxisLocation a2 = AxisLocation.TOP_OR_RIGHT;
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        AxisLocation location1 = AxisLocation.BOTTOM_OR_RIGHT;
        AxisLocation location2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(location1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            location2 = (AxisLocation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(location1, location2);
        boolean same = location1 == location2;
        assertEquals(true, same);
    }

}
