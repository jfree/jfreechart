/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ------------------
 * DateRangeTest.java
 * ------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import java.util.Date;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link DateRange} class.
 */
public class DateRangeTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        DateRange r2 = new DateRange(new Date(1000L), new Date(2000L));
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1 = new DateRange(new Date(1111L), new Date(2000L));
        assertNotEquals(r1, r2);
        r2 = new DateRange(new Date(1111L), new Date(2000L));
        assertEquals(r1, r2);

        r1 = new DateRange(new Date(1111L), new Date(2222L));
        assertNotEquals(r1, r2);
        r2 = new DateRange(new Date(1111L), new Date(2222L));
        assertEquals(r1, r2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        DateRange r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * The {@link DateRange} class is immutable, so it doesn't need to
     * be cloneable.
     */
    @Test
    public void testClone() {
        DateRange r1 = new DateRange(new Date(1000L), new Date(2000L));
        assertFalse(r1 instanceof Cloneable);
    }

    /**
     * Confirm that a DateRange is immutable.
     */
    @Test
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
