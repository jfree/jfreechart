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
 * ----------------------
 * HashUtilitiesTest.java
 * ----------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 06-Mar-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link HashUtilities} class.
 */
public class HashUtilitiesTest  {

    /**
     * Some sanity checks for the hashCodeForDoubleArray() method.
     */
    @Test
    public void testHashCodeForDoubleArray() {
        double[] a1 = new double[] {1.0};
        double[] a2 = new double[] {1.0};
        int h1 = HashUtilities.hashCodeForDoubleArray(a1);
        int h2 = HashUtilities.hashCodeForDoubleArray(a2);
        assertEquals(h1, h2);

        double[] a3 = new double[] {0.5, 1.0};
        int h3 = HashUtilities.hashCodeForDoubleArray(a3);
        assertFalse(h1 == h3);
    }
}
