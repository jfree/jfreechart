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
 * -------------------------------
 * CategoryLabelWidthTypeTest.java
 * -------------------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-May-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link CategoryLabelWidthType} class.
 */
public class CategoryLabelWidthTypeTest {

    /**
     * Confirm that the equals() method distinguishes the known values.
     */
    @Test
    public void testEquals() {
        assertEquals(CategoryLabelWidthType.CATEGORY,
                CategoryLabelWidthType.CATEGORY);
        assertEquals(CategoryLabelWidthType.RANGE,
                CategoryLabelWidthType.RANGE);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        CategoryLabelWidthType a1 = CategoryLabelWidthType.CATEGORY;
        CategoryLabelWidthType a2 = CategoryLabelWidthType.CATEGORY;
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryLabelWidthType w1 = CategoryLabelWidthType.RANGE;
        CategoryLabelWidthType w2 = (CategoryLabelWidthType) TestUtilities.serialised(w1);
        assertEquals(w1, w2);
        assertTrue(w1 == w2);
    }

}
