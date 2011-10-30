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
 * ---------------------
 * DomainOrderTests.java
 * ---------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-May-2005 : Version 1 (DG);
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

import org.jfree.data.DomainOrder;

/**
 * Tests for the {@link DomainOrder} class.
 */
public class DomainOrderTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DomainOrderTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DomainOrderTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        assertEquals(DomainOrder.NONE, DomainOrder.NONE);
        assertEquals(DomainOrder.ASCENDING, DomainOrder.ASCENDING);
        assertEquals(DomainOrder.DESCENDING, DomainOrder.DESCENDING);
        assertFalse(DomainOrder.NONE.equals(DomainOrder.ASCENDING));
        assertFalse(DomainOrder.NONE.equals(DomainOrder.DESCENDING));
        assertFalse(DomainOrder.NONE.equals(null));
        assertFalse(DomainOrder.ASCENDING.equals(DomainOrder.NONE));
        assertFalse(DomainOrder.ASCENDING.equals(DomainOrder.DESCENDING));
        assertFalse(DomainOrder.ASCENDING.equals(null));
        assertFalse(DomainOrder.DESCENDING.equals(DomainOrder.NONE));
        assertFalse(DomainOrder.DESCENDING.equals(DomainOrder.ASCENDING));
        assertFalse(DomainOrder.DESCENDING.equals(null));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        DomainOrder d1 = DomainOrder.ASCENDING;
        DomainOrder d2 = DomainOrder.ASCENDING;
        assertTrue(d1.equals(d2));
        int h1 = d1.hashCode();
        int h2 = d2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        DomainOrder d1 = DomainOrder.ASCENDING;
        DomainOrder d2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            d2 = (DomainOrder) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(d1, d2);
        boolean same = d1 == d2;
        assertEquals(true, same);
    }

}
