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
 * --------------------
 * DomainOrderTest.java
 * --------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-May-2005 : Version 1 (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtilities;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;

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
        assertFalse(DomainOrder.NONE.equals(DomainOrder.ASCENDING));
        assertFalse(DomainOrder.NONE.equals(DomainOrder.DESCENDING));
        assertFalse(DomainOrder.NONE.equals(null));
        assertFalse(DomainOrder.ASCENDING.equals(DomainOrder.NONE));
        assertFalse(DomainOrder.ASCENDING.equals(DomainOrder.DESCENDING));
        assertFalse(DomainOrder.ASCENDING.equals(null));
        assertFalse(DomainOrder.DESCENDING.equals(DomainOrder.NONE));
        assertFalse(DomainOrder.DESCENDING.equals(DomainOrder.ASCENDING));
        assertFalse(DomainOrder.DESCENDING.equals(null));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DomainOrder d1 = DomainOrder.ASCENDING;
        DomainOrder d2 = DomainOrder.ASCENDING;
        assertTrue(d1.equals(d2));
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
        DomainOrder d2 = (DomainOrder) TestUtilities.serialised(d1);
        assertSame(d1, d2);
    }

}
