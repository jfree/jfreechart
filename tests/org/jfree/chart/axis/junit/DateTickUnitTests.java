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
 * DateTickUnitTests.java
 * ----------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() (DG);
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

import org.jfree.chart.axis.DateTickUnit;

/**
 * Tests for the {@link DateTickUnit} class.
 */
public class DateTickUnitTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DateTickUnitTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DateTickUnitTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        DateTickUnit t1 = new DateTickUnit(DateTickUnit.DAY, 1);
        DateTickUnit t2 = new DateTickUnit(DateTickUnit.DAY, 1);
        assertTrue(t1.equals(t2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        DateTickUnit t1 = new DateTickUnit(DateTickUnit.DAY, 1);
        DateTickUnit t2 = new DateTickUnit(DateTickUnit.DAY, 1);
        assertTrue(t1.equals(t2));
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DateTickUnit a1 = new DateTickUnit(DateTickUnit.DAY, 7);
        DateTickUnit a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            a2 = (DateTickUnit) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(a1, a2);

    }

}
