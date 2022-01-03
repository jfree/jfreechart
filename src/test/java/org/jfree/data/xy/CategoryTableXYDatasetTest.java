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
 * CategoryTableXYDatasetTest.java
 * -------------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryTableXYDataset} class.
 */
public class CategoryTableXYDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        d1.add(1.0, 1.1, "Series 1");
        d1.add(2.0, 2.2, "Series 1");

        CategoryTableXYDataset d2 = new CategoryTableXYDataset();
        d2.add(1.0, 1.1, "Series 1");
        d2.add(2.0, 2.2, "Series 1");

        assertEquals(d1, d2);
        assertEquals(d2, d1);

        d1.add(3.0, 3.3, "Series 1");
        assertNotEquals(d1, d2);

        d2.add(3.0, 3.3, "Series 1");
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        d1.add(1.0, 1.1, "Series 1");
        d1.add(2.0, 2.2, "Series 1");

        CategoryTableXYDataset d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        d1.add(3.0, 3.3, "Series 1");
        assertNotEquals(d1, d2);
        d2.add(3.0, 3.3, "Series 1");
        assertEquals(d1, d2);

        d1.setIntervalPositionFactor(0.33);
        assertNotEquals(d1, d2);
        d2.setIntervalPositionFactor(0.33);
        assertEquals(d1, d2);
    }

    /**
     * Another check for cloning - making sure it works for a customised
     * interval delegate.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        d1.add(1.0, 1.1, "Series 1");
        d1.add(2.0, 2.2, "Series 1");
        d1.setIntervalWidth(1.23);

        CategoryTableXYDataset d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        d1.add(3.0, 3.3, "Series 1");
        assertNotEquals(d1, d2);
        d2.add(3.0, 3.3, "Series 1");
        assertEquals(d1, d2);

        d1.setIntervalPositionFactor(0.33);
        assertNotEquals(d1, d2);
        d2.setIntervalPositionFactor(0.33);
        assertEquals(d1, d2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        d1.add(1.0, 1.1, "Series 1");
        d1.add(2.0, 2.2, "Series 1");
        CategoryTableXYDataset d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * This is a test for bug 1312066 - adding a new series should trigger a
     * recalculation of the interval width, if it is being automatically
     * calculated.
     */
    @Test
    public void testAddSeries() {
        CategoryTableXYDataset d1 = new CategoryTableXYDataset();
        d1.setAutoWidth(true);
        d1.add(3.0, 1.1, "Series 1");
        d1.add(7.0, 2.2, "Series 1");
        assertEquals(3.0, d1.getXValue(0, 0), EPSILON);
        assertEquals(7.0, d1.getXValue(0, 1), EPSILON);
        assertEquals(1.0, d1.getStartXValue(0, 0), EPSILON);
        assertEquals(5.0, d1.getStartXValue(0, 1), EPSILON);
        assertEquals(5.0, d1.getEndXValue(0, 0), EPSILON);
        assertEquals(9.0, d1.getEndXValue(0, 1), EPSILON);

        // now add some more data
        d1.add(7.5, 1.1, "Series 2");
        d1.add(9.0, 2.2, "Series 2");

        assertEquals(3.0, d1.getXValue(1, 0), EPSILON);
        assertEquals(7.0, d1.getXValue(1, 1), EPSILON);
        assertEquals(7.5, d1.getXValue(1, 2), EPSILON);
        assertEquals(9.0, d1.getXValue(1, 3), EPSILON);

        assertEquals(7.25, d1.getStartXValue(1, 2), EPSILON);
        assertEquals(8.75, d1.getStartXValue(1, 3), EPSILON);
        assertEquals(7.75, d1.getEndXValue(1, 2), EPSILON);
        assertEquals(9.25, d1.getEndXValue(1, 3), EPSILON);

        // and check the first series too...
        assertEquals(2.75, d1.getStartXValue(0, 0), EPSILON);
        assertEquals(6.75, d1.getStartXValue(0, 1), EPSILON);
        assertEquals(3.25, d1.getEndXValue(0, 0), EPSILON);
        assertEquals(7.25, d1.getEndXValue(0, 1), EPSILON);
    }
}
