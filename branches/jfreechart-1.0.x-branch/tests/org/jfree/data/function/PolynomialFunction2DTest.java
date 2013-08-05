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
 * ------------------------------
 * PolynomialFunction2DTests.java
 * ------------------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
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

import java.util.Arrays;
import org.jfree.chart.TestUtilities;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the {@link PolynomialFunction2D} class.
 */
public class PolynomialFunction2DTest {

    /**
     * Some tests for the constructor.
     */
    @Test
    public void testConstructor() {
        PolynomialFunction2D f = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        assertTrue(Arrays.equals(new double[] {1.0, 2.0}, f.getCoefficients()));

        boolean pass = false;
        try {
            f = new PolynomialFunction2D(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getCoefficients() method.
     */
    @Test
    public void testGetCoefficients() {
        PolynomialFunction2D f = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        double[] c = f.getCoefficients();
        assertTrue(Arrays.equals(new double[] {1.0, 2.0}, c));

        // make sure that modifying the returned array doesn't change the
        // function
        c[0] = 99.9;
        assertTrue(Arrays.equals(new double[] {1.0, 2.0}, f.getCoefficients()));
    }

    /**
     * Some checks for the getOrder() method.
     */
    @Test
    public void testGetOrder() {
        PolynomialFunction2D f = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        assertEquals(1, f.getOrder());
    }

    /**
     * For datasets, the equals() method just checks keys and values.
     */
    @Test
    public void testEquals() {
        PolynomialFunction2D f1 = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        PolynomialFunction2D f2 = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        assertTrue(f1.equals(f2));
        f1 = new PolynomialFunction2D(new double[] {2.0, 3.0});
        assertFalse(f1.equals(f2));
        f2 = new PolynomialFunction2D(new double[] {2.0, 3.0});
        assertTrue(f1.equals(f2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PolynomialFunction2D f1 = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        PolynomialFunction2D f2 = (PolynomialFunction2D) 
                TestUtilities.serialised(f1);
        assertEquals(f1, f2);
    }

    /**
     * Objects that are equal should have the same hash code otherwise FindBugs
     * will tell on us...
     */
    @Test
    public void testHashCode() {
        PolynomialFunction2D f1 = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        PolynomialFunction2D f2 = new PolynomialFunction2D(new double[] {1.0,
                2.0});
        assertEquals(f1.hashCode(), f2.hashCode());

    }

}

