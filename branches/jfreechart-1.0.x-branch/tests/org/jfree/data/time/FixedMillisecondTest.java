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
 * -------------------------
 * FixedMillisecondTest.java
 * -------------------------
 * (C) Copyright 2002-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Jan-2002 : Version 1 (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 21-Oct-2003 : Added hashCode test (DG);
 * 28-May-2008 : Added test for immutability (DG);
 *
 */

package org.jfree.data.time;

import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.TestUtilities;

/**
 * Tests for the {@link FixedMillisecond} class.
 */
public class FixedMillisecondTest extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(FixedMillisecondTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public FixedMillisecondTest(String name) {
        super(name);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        FixedMillisecond m1 = new FixedMillisecond();
        FixedMillisecond m2 = (FixedMillisecond) TestUtilities.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        FixedMillisecond m1 = new FixedMillisecond(500000L);
        FixedMillisecond m2 = new FixedMillisecond(500000L);
        assertTrue(m1.equals(m2));
        int h1 = m1.hashCode();
        int h2 = m2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * The {@link FixedMillisecond} class is immutable, so should not be
     * {@link Cloneable}.
     */
    public void testNotCloneable() {
        FixedMillisecond m = new FixedMillisecond(500000L);
        assertFalse(m instanceof Cloneable);
    }

    /**
     * A check for immutability.
     */
    public void testImmutability() {
        Date d = new Date(20L);
        FixedMillisecond fm = new FixedMillisecond(d);
        d.setTime(22L);
        assertEquals(20L, fm.getFirstMillisecond());
    }
}
