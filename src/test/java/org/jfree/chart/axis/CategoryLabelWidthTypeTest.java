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
 * -------------------------------
 * CategoryLabelWidthTypeTest.java
 * -------------------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(a1, a2);
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
        CategoryLabelWidthType w2 = TestUtils.serialised(w1);
        assertEquals(w1, w2);
        assertSame(w1, w2);
    }

}
