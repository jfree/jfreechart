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
 * AxisSpaceTests.java
 * -------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 14-Aug-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added hashCode test (DG);
 *
 */

package org.jfree.chart.axis.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.AxisSpace;

/**
 * Tests for the {@link AxisSpace} class.
 */
public class AxisSpaceTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(AxisSpaceTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public AxisSpaceTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method can distinguish all fields.
     */
    public void testEquals() {
        AxisSpace a1 = new AxisSpace();
        AxisSpace a2 = new AxisSpace();
        assertEquals(a1, a2);

        a1.setTop(1.11);
        assertFalse(a1.equals(a2));
        a2.setTop(1.11);
        assertTrue(a1.equals(a2));

        a1.setBottom(2.22);
        assertFalse(a1.equals(a2));
        a2.setBottom(2.22);
        assertTrue(a1.equals(a2));

        a1.setLeft(3.33);
        assertFalse(a1.equals(a2));
        a2.setLeft(3.33);
        assertTrue(a1.equals(a2));

        a1.setRight(4.44);
        assertFalse(a1.equals(a2));
        a2.setRight(4.44);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        AxisSpace s1 = new AxisSpace();
        AxisSpace s2 = new AxisSpace();
        assertTrue(s1.equals(s2));
        int h1 = s1.hashCode();
        int h2 = s2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        AxisSpace s1 = new AxisSpace();
        AxisSpace s2 = null;
        try {
            s2 = (AxisSpace) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

}

