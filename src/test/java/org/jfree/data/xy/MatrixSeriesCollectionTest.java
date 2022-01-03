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
 * MatrixSeriesCollectionTest.java
 * -------------------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
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
 * Tests for the {@link MatrixSeriesCollection} class.
 */
public class MatrixSeriesCollectionTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        MatrixSeries<String> s1 = new MatrixSeries<>("Series", 2, 3);
        s1.update(0, 0, 1.1);
        MatrixSeriesCollection<String> c1 = new MatrixSeriesCollection<>();
        c1.addSeries(s1);
        MatrixSeries<String> s2 = new MatrixSeries<>("Series", 2, 3);
        s2.update(0, 0, 1.1);
        MatrixSeriesCollection<String> c2 = new MatrixSeriesCollection<>();
        c2.addSeries(s2);
        assertEquals(c1, c2);
        assertEquals(c2, c1);

        c1.addSeries(new MatrixSeries<>("Empty Series", 1, 1));
        assertNotEquals(c1, c2);

        c2.addSeries(new MatrixSeries<>("Empty Series", 1, 1));
        assertEquals(c1, c2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        MatrixSeries<String> s1 = new MatrixSeries<>("Series", 2, 3);
        s1.update(0, 0, 1.1);
        MatrixSeriesCollection<String> c1 = new MatrixSeriesCollection<>();
        c1.addSeries(s1);
        MatrixSeriesCollection<String> c2 = CloneUtils.clone(c1);

        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        c2.removeAllSeries();
        assertNotEquals(c1, c2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        MatrixSeriesCollection<String> c1 = new MatrixSeriesCollection<>();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MatrixSeries<String> s1 = new MatrixSeries<>("Series", 2, 3);
        s1.update(0, 0, 1.1);
        MatrixSeriesCollection<String> c1 = new MatrixSeriesCollection<>();
        c1.addSeries(s1);
        MatrixSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

}
