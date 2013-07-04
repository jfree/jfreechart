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
 * -------------------
 * DateRangeTests.java
 * -------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Mar-2004 : Version 1 (DG);
 * 11-Jan-2005 : Added test to ensure Cloneable is not implemented (DG);
 *
 */

package org.jfree.data.time.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.time.DateRange;

/**
 * Some tests for the {@link DateRange} class.
 */
public class DateRangeTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DateRangeTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DateRangeTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        DateRange r2 = new DateRange(new Date(1000L), new Date(2000L));
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));

        r1 = new DateRange(new Date(1111L), new Date(2000L));
        assertFalse(r1.equals(r2));
        r2 = new DateRange(new Date(1111L), new Date(2000L));
        assertTrue(r1.equals(r2));

        r1 = new DateRange(new Date(1111L), new Date(2222L));
        assertFalse(r1.equals(r2));
        r2 = new DateRange(new Date(1111L), new Date(2222L));
        assertTrue(r1.equals(r2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        DateRange r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (DateRange) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(r1, r2);
    }

    /**
     * The {@link DateRange} class is immutable, so it doesn't need to
     * be cloneable.
     */
    public void testClone() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        assertFalse(r1 instanceof Cloneable);
    }

    /**
     * Confirm that a DateRange is immutable.
     */
    public void testImmutable() {
        Date d1 = new Date(10L);
        Date d2 = new Date(20L);
        DateRange r = new DateRange(d1, d2);
        d1.setTime(11L);
        assertEquals(new Date(10L), r.getLowerDate());
        r.getUpperDate().setTime(22L);
        assertEquals(new Date(20L), r.getUpperDate());
    }

}
