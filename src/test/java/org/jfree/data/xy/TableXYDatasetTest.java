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
 * -----------------------
 * TableXYDatasetTest.java
 * -----------------------
 * (C) Copyright 2003-2021, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link DefaultTableXYDataset}.
 */
public class TableXYDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultTableXYDataset<String> d1 = new DefaultTableXYDataset<>();
        DefaultTableXYDataset<String> d2 = new DefaultTableXYDataset<>();
        assertEquals(d1, d2);
        assertEquals(d2, d1);

        d1.addSeries(createSeries1());
        assertNotEquals(d1, d2);

        d2.addSeries(createSeries1());
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultTableXYDataset<String> d1 = new DefaultTableXYDataset<>();
        d1.addSeries(createSeries1());
        DefaultTableXYDataset<String> d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        DefaultTableXYDataset<String> d1 = new DefaultTableXYDataset<>();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultTableXYDataset<String> d1 = new DefaultTableXYDataset<>();
        d1.addSeries(createSeries2());
        DefaultTableXYDataset<String> d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Assorted tests.
     */
    @Test
    public void testTableXYDataset() {
        XYSeries<String> series1 = createSeries1();
        XYSeries<String> series2 = createSeries2();

        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>();
        dataset.addSeries(series1);
        dataset.addSeries(series2);

        //  Test that there are 6 X points and some specific values
        assertEquals(6, dataset.getItemCount());
        assertEquals(6, dataset.getX(0, 5).intValue());
        assertNull(dataset.getY(0, 5));
        assertEquals(6, dataset.getX(1, 5).intValue());
        assertEquals(2, dataset.getY(1, 5).intValue());

        // after adding a point to a series, check that there are now 7
        // items in each series
        series2.add(7, 2);
        assertEquals(7, dataset.getItemCount());
        assertNull(dataset.getY(0, 6));
        assertEquals(2, dataset.getY(1, 6).intValue());

        //  Remove series 1
        dataset.removeSeries(series1);
        //  Test that there are still 7 X points
        assertEquals(7, dataset.getItemCount());

        //  Remove series 2 and add new series
        dataset.removeSeries(series2);
        series1 = createSeries1();
        dataset.addSeries(series1);

        //  Test that there are now 4 X points
        assertEquals(4, dataset.getItemCount());
    }

    /**
     * A test for bug report 788597.
     */
    @Test
    public void test788597() {
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>();
        dataset.addSeries(createSeries1());
        assertEquals(4, dataset.getItemCount());
        dataset.removeAllSeries();
        assertEquals(0, dataset.getItemCount());
    }

    /**
     * Test that removing all values for a given x works.
     */
    @Test
    public void testRemoveAllValuesForX() {
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>();
        dataset.addSeries(createSeries1());
        dataset.addSeries(createSeries2());
        dataset.removeAllValuesForX(2.0);
        assertEquals(5, dataset.getItemCount());
        assertEquals(1.0, dataset.getX(0, 0));
        assertEquals(3.0, dataset.getX(0, 1));
        assertEquals(4.0, dataset.getX(0, 2));
        assertEquals(5.0, dataset.getX(0, 3));
        assertEquals(6.0, dataset.getX(0, 4));
    }

    /**
     * Tests to see that pruning removes unwanted x values.
     */
    @Test
    public void testPrune() {
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>();
        dataset.addSeries(createSeries1());
        dataset.addSeries(createSeries2());
        dataset.removeSeries(1);
        dataset.prune();
        assertEquals(4, dataset.getItemCount());
    }

    /**
     * Tests the auto-pruning feature.
     */
    @Test
    public void testAutoPrune() {

        // WITH AUTOPRUNING
        DefaultTableXYDataset<String> dataset = new DefaultTableXYDataset<>(true);
        dataset.addSeries(createSeriesA());
        assertEquals(2, dataset.getItemCount());  // should be 2 items
        dataset.addSeries(createSeriesB());
        assertEquals(2, dataset.getItemCount());  // still 2
        dataset.removeSeries(1);
        assertEquals(1, dataset.getItemCount());  // 1 value pruned.

        // WITHOUT AUTOPRUNING
        DefaultTableXYDataset<String> dataset2 = new DefaultTableXYDataset<>(true);
        dataset2.addSeries(createSeriesA());
        assertEquals(2, dataset2.getItemCount());  // should be 2 items
        dataset2.addSeries(createSeriesB());
        assertEquals(2, dataset2.getItemCount());  // still 2
        dataset2.removeSeries(1);
        assertEquals(1, dataset2.getItemCount());  // still 2.

    }

    /**
     * Creates a series for testing.
     *
     * @return A series.
     */
    private XYSeries<String> createSeriesA() {
        XYSeries<String> s = new XYSeries<>("A", true, false);
        s.add(1.0, 1.1);
        s.add(2.0, null);
        return s;
    }

    /**
     * Creates a series for testing.
     *
     * @return A series.
     */
    private XYSeries<String> createSeriesB() {
        XYSeries<String> s = new XYSeries<>("B", true, false);
        s.add(1.0, null);
        s.add(2.0, 2.2);
        return s;
    }

    /**
     * Creates a series for testing.
     *
     * @return A series.
     */
    private XYSeries<String> createSeries1() {
        XYSeries<String> series1 = new XYSeries<>("Series 1", true, false);
        series1.add(1.0, 1.0);
        series1.add(2.0, 1.0);
        series1.add(4.0, 1.0);
        series1.add(5.0, 1.0);
        return series1;
    }

    /**
     * Creates a series for testing.
     *
     * @return A series.
     */
    private XYSeries<String> createSeries2() {
        XYSeries<String> series2 = new XYSeries<>("Series 2", true, false);
        series2.add(2.0, 2.0);
        series2.add(3.0, 2.0);
        series2.add(4.0, 2.0);
        series2.add(5.0, 2.0);
        series2.add(6.0, 2.0);
        return series2;
    }

}
