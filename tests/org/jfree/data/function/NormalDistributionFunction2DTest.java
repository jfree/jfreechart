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
 * -------------------------------------
 * NormalDistributionFunction2DTest.java
 * -------------------------------------
 * (C) Copyright 2009-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 28-May-2009 : Version 1 (DG);
 *
 */

package org.jfree.data.function;

import org.jfree.chart.TestUtilities;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the {@link NormalDistributionFunction2D} class.
 */
public class NormalDistributionFunction2DTest {

    private static final double EPSILON = 0.000000001;

    /**
     * Some tests for the constructor.
     */
    @Test
    public void testConstructor() {
        NormalDistributionFunction2D f = new NormalDistributionFunction2D(1.0,
                2.0);
        assertEquals(1.0, f.getMean(), EPSILON);
        assertEquals(2.0, f.getStandardDeviation(), EPSILON);
    }

    /**
     * For datasets, the equals() method just checks keys and values.
     */
    @Test
    public void testEquals() {
        NormalDistributionFunction2D f1 = new NormalDistributionFunction2D(1.0,
                2.0);
        NormalDistributionFunction2D f2 = new NormalDistributionFunction2D(1.0,
                2.0);
        assertEquals(f1, f2);
        f1 = new NormalDistributionFunction2D(2.0, 3.0);
        assertNotEquals(f1, f2);
        f2 = new NormalDistributionFunction2D(2.0, 3.0);
        assertEquals(f1, f2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        NormalDistributionFunction2D f1 = new NormalDistributionFunction2D(1.0,
                2.0);
        NormalDistributionFunction2D f2 = (NormalDistributionFunction2D) 
                TestUtilities.serialised(f1);
        assertEquals(f1, f2);
    }

    /**
     * Objects that are equal should have the same hash code otherwise FindBugs
     * will tell on us...
     */
    @Test
    public void testHashCode() {
        NormalDistributionFunction2D f1 = new NormalDistributionFunction2D(1.0,
                2.0);
        NormalDistributionFunction2D f2 = new NormalDistributionFunction2D(1.0,
                2.0);
        assertEquals(f1.hashCode(), f2.hashCode());
    }

}


