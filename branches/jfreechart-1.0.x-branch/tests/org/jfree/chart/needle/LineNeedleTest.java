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
 * LineNeedleTest.java
 * -------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Jun-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.needle;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.jfree.chart.TestUtilities;

/**
 * Tests for the {@link LineNeedle} class.
 */
public class LineNeedleTest extends TestCase {
    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(LineNeedleTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public LineNeedleTest(String name) {
        super(name);
    }

    /**
     * Check that the equals() method can distinguish all fields.
     */
    public void testEquals() {
       LineNeedle n1 = new LineNeedle();
       LineNeedle n2 = new LineNeedle();
       assertTrue(n1.equals(n2));
       assertTrue(n2.equals(n1));
    }

    /**
     * Check that cloning works.
     */
    public void testCloning() throws CloneNotSupportedException {
        LineNeedle n1 = new LineNeedle();
        LineNeedle n2 = (LineNeedle) n1.clone();
        assertTrue(n1 != n2);
        assertTrue(n1.getClass() == n2.getClass());
        assertTrue(n1.equals(n2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        LineNeedle n1 = new LineNeedle();
        LineNeedle n2 = (LineNeedle) TestUtilities.serialised(n1);
        assertTrue(n1.equals(n2));
    }

}
