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
 * -----------------------------
 * ComparableObjectItemTest.java
 * -----------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtilities;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link ComparableObjectItem} class.
 */
public class ComparableObjectItemTest {

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor() {
        // check null argument 1
        try {
            /* ComparableObjectItem item1 = */ new ComparableObjectItem(null,
                    "XYZ");
            fail("There should be an exception.");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        ComparableObjectItem item1 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        ComparableObjectItem item2 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        assertEquals(item1, item2);

        item1 = new ComparableObjectItem(new Integer(2), "XYZ");
        assertNotEquals(item1, item2);
        item2 = new ComparableObjectItem(new Integer(2), "XYZ");
        assertEquals(item1, item2);

        item1 = new ComparableObjectItem(new Integer(2), null);
        assertNotEquals(item1, item2);
        item2 = new ComparableObjectItem(new Integer(2), null);
        assertEquals(item1, item2);
    }

    /**
     * Some checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        ComparableObjectItem item1 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        ComparableObjectItem item2 = (ComparableObjectItem) item1.clone();
        assertNotSame(item1, item2);
        assertSame(item1.getClass(), item2.getClass());
        assertEquals(item1, item2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ComparableObjectItem item1 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        ComparableObjectItem item2 = (ComparableObjectItem) 
                TestUtilities.serialised(item1);
        assertEquals(item1, item2);
    }

    /**
     * Some checks for the compareTo() method.
     */
    @Test
    public void testCompareTo() {
        ComparableObjectItem item1 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        ComparableObjectItem item2 = new ComparableObjectItem(new Integer(2),
                "XYZ");
        ComparableObjectItem item3 = new ComparableObjectItem(new Integer(3),
                "XYZ");
        ComparableObjectItem item4 = new ComparableObjectItem(new Integer(1),
                "XYZ");
        assertTrue(item2.compareTo(item1) > 0);
        assertTrue(item3.compareTo(item1) > 0);
        assertTrue(item4.compareTo(item1) == 0);
        assertTrue(item1.compareTo(item2) < 0);
    }

}
