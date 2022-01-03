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
 * --------------------
 * DomainOrderTest.java
 * --------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DomainOrder} class.
 */
public class DomainOrderTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(DomainOrder.NONE, DomainOrder.NONE);
        assertEquals(DomainOrder.ASCENDING, DomainOrder.ASCENDING);
        assertEquals(DomainOrder.DESCENDING, DomainOrder.DESCENDING);
        assertNotEquals(DomainOrder.NONE, DomainOrder.ASCENDING);
        assertNotEquals(DomainOrder.NONE, DomainOrder.DESCENDING);
        assertNotEquals(null, DomainOrder.NONE);
        assertNotEquals(DomainOrder.ASCENDING, DomainOrder.NONE);
        assertNotEquals(DomainOrder.ASCENDING, DomainOrder.DESCENDING);
        assertNotEquals(null, DomainOrder.ASCENDING);
        assertNotEquals(DomainOrder.DESCENDING, DomainOrder.NONE);
        assertNotEquals(DomainOrder.DESCENDING, DomainOrder.ASCENDING);
        assertNotEquals(null, DomainOrder.DESCENDING);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DomainOrder d1 = DomainOrder.ASCENDING;
        DomainOrder d2 = DomainOrder.ASCENDING;
        assertEquals(d1, d2);
        int h1 = d1.hashCode();
        int h2 = d2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DomainOrder d1 = DomainOrder.ASCENDING;
        DomainOrder d2 = TestUtils.serialised(d1);
        assertSame(d1, d2);
    }

}
