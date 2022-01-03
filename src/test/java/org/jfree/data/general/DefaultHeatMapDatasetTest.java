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
 * ------------------------------
 * DefaultHeatMapDatasetTest.java
 * ------------------------------
 * (C) Copyright 2009-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.general;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Somes tests for the {@link DefaultHeatMapDataset} class.
 */
public class DefaultHeatMapDatasetTest implements DatasetChangeListener {

    /** The last event received. */
    private DatasetChangeEvent lastEvent;

    /**
     * Records the last event.
     *
     * @param event  the last event.
     */
    @Override
    public void datasetChanged(DatasetChangeEvent event) {
        this.lastEvent = event;
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some general tests.
     */
    @Test
    public void testGeneral() {
        DefaultHeatMapDataset d = new DefaultHeatMapDataset(10, 5, 0.0, 9.0,
                0.0, 5.0);
        assertEquals(10, d.getXSampleCount());
        assertEquals(5, d.getYSampleCount());
        assertEquals(0.0, d.getMinimumXValue(), EPSILON);
        assertEquals(9.0, d.getMaximumXValue(), EPSILON);
        assertEquals(0.0, d.getMinimumYValue(), EPSILON);
        assertEquals(5.0, d.getMaximumYValue(), EPSILON);
        assertEquals(0.0, d.getZValue(0, 0), EPSILON);
        d.addChangeListener(this);
        d.setZValue(0, 0, 1.0, false);
        assertEquals(1.0, d.getZValue(0, 0), EPSILON);
        assertNull(this.lastEvent);
        d.setZValue(1, 2, 2.0);
        assertEquals(2.0, d.getZValue(1, 2), EPSILON);
        assertNotNull(this.lastEvent);
    }

    /**
     * Some tests for the equals() method.
     */
    @Test
    public void testEquals() {
        DefaultHeatMapDataset d1 = new DefaultHeatMapDataset(5, 10, 1.0, 2.0,
                3.0, 4.0);
        DefaultHeatMapDataset d2 = new DefaultHeatMapDataset(5, 10, 1.0, 2.0,
                3.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 10, 1.0, 2.0, 3.0, 4.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 10, 1.0, 2.0, 3.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 11, 1.0, 2.0, 3.0, 4.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 11, 1.0, 2.0, 3.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 11, 2.0, 2.0, 3.0, 4.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 11, 2.0, 2.0, 3.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 3.0, 4.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 3.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 4.0, 4.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 4.0, 4.0);
        assertEquals(d1, d2);

        d1 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 4.0, 5.0);
        assertNotEquals(d1, d2);
        d2 = new DefaultHeatMapDataset(6, 11, 2.0, 3.0, 4.0, 5.0);
        assertEquals(d1, d2);

        d1.setZValue(1, 2, 3.0);
        assertNotEquals(d1, d2);
        d2.setZValue(1, 2, 3.0);
        assertEquals(d1, d2);

        d1.setZValue(0, 0, Double.NEGATIVE_INFINITY);
        assertNotEquals(d1, d2);
        d2.setZValue(0, 0, Double.NEGATIVE_INFINITY);
        assertEquals(d1, d2);

        d1.setZValue(0, 1, Double.POSITIVE_INFINITY);
        assertNotEquals(d1, d2);
        d2.setZValue(0, 1, Double.POSITIVE_INFINITY);
        assertEquals(d1, d2);

        d1.setZValue(0, 2, Double.NaN);
        assertNotEquals(d1, d2);
        d2.setZValue(0, 2, Double.NaN);
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultHeatMapDataset d1 = new DefaultHeatMapDataset(2, 3, -1.0, 4.0,
                -2.0, 5.0);
        d1.setZValue(0, 0, 10.0);
        d1.setZValue(0, 1, Double.NEGATIVE_INFINITY);
        d1.setZValue(0, 2, Double.POSITIVE_INFINITY);
        d1.setZValue(1, 0, Double.NaN);
        DefaultHeatMapDataset d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // simple check for independence
        d1.setZValue(0, 0, 11.0);
        assertNotEquals(d1, d2);
        d2.setZValue(0, 0, 11.0);
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultHeatMapDataset d1 = new DefaultHeatMapDataset(2, 3, -1.0, 4.0,
                -2.0, 5.0);
        d1.setZValue(0, 0, 10.0);
        d1.setZValue(0, 1, Double.NEGATIVE_INFINITY);
        d1.setZValue(0, 2, Double.POSITIVE_INFINITY);
        d1.setZValue(1, 0, Double.NaN);
        DefaultHeatMapDataset d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

}
