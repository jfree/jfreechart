/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -------------
 * OHLCTest.java
 * -------------
 * (C) Copyright 2006-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 * 23-May-2009 : Added testHashCode() (DG);
 *
 */

package org.jfree.data.time.ohlc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link OHLC} class.
 */
public class OHLCTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        OHLC i2 = new OHLC(2.0, 4.0, 1.0, 3.0);
        assertEquals(i1, i2);

        i1 = new OHLC(2.2, 4.0, 1.0, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.0, 1.0, 3.0);
        assertTrue(i1.equals(i2));

        i1 = new OHLC(2.2, 4.4, 1.0, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.0, 3.0);
        assertTrue(i1.equals(i2));

        i1 = new OHLC(2.2, 4.4, 1.1, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.1, 3.0);
        assertTrue(i1.equals(i2));

        i1 = new OHLC(2.2, 4.4, 1.1, 3.3);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.1, 3.3);
        assertTrue(i1.equals(i2));
    }

    /**
     * This class is immutable.
     */
    @Test
    public void testCloning() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        assertFalse(i1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        OHLC i2 = (OHLC) TestUtils.serialised(i1);
        assertEquals(i1, i2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        OHLC i2 = new OHLC(2.0, 4.0, 1.0, 3.0);
        assertTrue(i1.equals(i2));
        int h1 = i1.hashCode();
        int h2 = i2.hashCode();
        assertEquals(h1, h2);
    }

}
