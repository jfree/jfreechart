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
 * ---------------------------
 * SimpleHistogramBinTest.java
 * ---------------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jan-2005 : Version 1 (DG);
 *
 */

package org.jfree.data.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link SimpleHistogramBin} class.
 */
public class SimpleHistogramBinTest {

    /**
     * Some checks for the accepts() method.
     */
    @Test
    public void testAccepts() {
        SimpleHistogramBin bin1 = new SimpleHistogramBin(1.0, 2.0);
        assertFalse(bin1.accepts(0.0));
        assertTrue(bin1.accepts(1.0));
        assertTrue(bin1.accepts(1.5));
        assertTrue(bin1.accepts(2.0));
        assertFalse(bin1.accepts(2.1));
        assertFalse(bin1.accepts(Double.NaN));

        SimpleHistogramBin bin2
            = new SimpleHistogramBin(1.0, 2.0, false, false);
        assertFalse(bin2.accepts(0.0));
        assertFalse(bin2.accepts(1.0));
        assertTrue(bin2.accepts(1.5));
        assertFalse(bin2.accepts(2.0));
        assertFalse(bin2.accepts(2.1));
        assertFalse(bin2.accepts(Double.NaN));
    }

    /**
     * Some checks for the overlapsWith() method.
     */
    @Test
    public void testOverlapsWidth() {
        SimpleHistogramBin b1 = new SimpleHistogramBin(1.0, 2.0);
        SimpleHistogramBin b2 = new SimpleHistogramBin(2.0, 3.0);
        SimpleHistogramBin b3 = new SimpleHistogramBin(3.0, 4.0);
        SimpleHistogramBin b4 = new SimpleHistogramBin(0.0, 5.0);
        SimpleHistogramBin b5 = new SimpleHistogramBin(2.0, 3.0, false, true);
        SimpleHistogramBin b6 = new SimpleHistogramBin(2.0, 3.0, true, false);
        assertTrue(b1.overlapsWith(b2));
        assertTrue(b2.overlapsWith(b1));
        assertFalse(b1.overlapsWith(b3));
        assertFalse(b3.overlapsWith(b1));
        assertTrue(b1.overlapsWith(b4));
        assertTrue(b4.overlapsWith(b1));
        assertFalse(b1.overlapsWith(b5));
        assertFalse(b5.overlapsWith(b1));
        assertTrue(b1.overlapsWith(b6));
        assertTrue(b6.overlapsWith(b1));
    }

    /**
     * Ensure that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        SimpleHistogramBin b1 = new SimpleHistogramBin(1.0, 2.0);
        SimpleHistogramBin b2 = new SimpleHistogramBin(1.0, 2.0);
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));

        b1 = new SimpleHistogramBin(1.1, 2.0, true, true);
        assertFalse(b1.equals(b2));
        b2 = new SimpleHistogramBin(1.1, 2.0, true, true);
        assertTrue(b1.equals(b2));

        b1 = new SimpleHistogramBin(1.1, 2.2, true, true);
        assertFalse(b1.equals(b2));
        b2 = new SimpleHistogramBin(1.1, 2.2, true, true);
        assertTrue(b1.equals(b2));

        b1 = new SimpleHistogramBin(1.1, 2.2, false, true);
        assertFalse(b1.equals(b2));
        b2 = new SimpleHistogramBin(1.1, 2.2, false, true);
        assertTrue(b1.equals(b2));

        b1 = new SimpleHistogramBin(1.1, 2.2, false, false);
        assertFalse(b1.equals(b2));
        b2 = new SimpleHistogramBin(1.1, 2.2, false, false);
        assertTrue(b1.equals(b2));

        b1.setItemCount(99);
        assertFalse(b1.equals(b2));
        b2.setItemCount(99);
        assertTrue(b1.equals(b2));
    }

    /**
     * Some checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        SimpleHistogramBin b1 = new SimpleHistogramBin(1.1, 2.2, false, true);
        b1.setItemCount(99);
        SimpleHistogramBin b2 = (SimpleHistogramBin) b1.clone();
        assertTrue(b1 != b2);
        assertTrue(b1.getClass() == b2.getClass());
        assertTrue(b1.equals(b2));

        // check that clone is independent of the original
        b2.setItemCount(111);
        assertFalse(b1.equals(b2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        SimpleHistogramBin b1 = new SimpleHistogramBin(1.0, 2.0, false, true);
        b1.setItemCount(123);
        SimpleHistogramBin b2 = (SimpleHistogramBin) TestUtilities.serialised(b1);
        assertEquals(b1, b2);
    }

}
