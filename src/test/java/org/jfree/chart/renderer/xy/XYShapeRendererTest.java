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
 * ------------------------
 * XYShapeRendererTest.java
 * ------------------------
 * (C) Copyright 2010-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Martin Hoeller (patch 2952086);
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Color;

import org.jfree.chart.TestUtils;

import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYShapeRenderer} class.
 */
public class XYShapeRendererTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        XYShapeRenderer r1 = new XYShapeRenderer();
        XYShapeRenderer r2 = new XYShapeRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setPaintScale(new LookupPaintScale(1.0, 2.0, Color.WHITE));
        assertNotEquals(r1, r2);
        r2.setPaintScale(new LookupPaintScale(1.0, 2.0, Color.WHITE));
        assertEquals(r1, r2);

        r1.setDrawOutlines(true);
        assertNotEquals(r1, r2);
        r2.setDrawOutlines(true);
        assertEquals(r1, r2);

        r1.setUseOutlinePaint(false);
        assertNotEquals(r1, r2);
        r2.setUseOutlinePaint(false);
        assertEquals(r1, r2);

        r1.setUseFillPaint(true);
        assertNotEquals(r1, r2);
        r2.setUseFillPaint(true);
        assertEquals(r1, r2);

        r1.setGuideLinesVisible(true);
        assertNotEquals(r1, r2);
        r2.setGuideLinesVisible(true);
        assertEquals(r1, r2);

        r1.setGuideLinePaint(Color.RED);
        assertNotEquals(r1, r2);
        r2.setGuideLinePaint(Color.RED);
        assertEquals(r1, r2);

    }

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYShapeRenderer r1 = new XYShapeRenderer();
        XYShapeRenderer r2 = CloneUtils.clone(r1);
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYShapeRenderer r1 = new XYShapeRenderer();
        XYShapeRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Check if finding the bounds in z-dimension of an XYZDataset works. 
     */
    @Test
    public void testFindZBounds() {
        XYShapeRenderer r = new XYShapeRenderer();
        assertNull(r.findZBounds(null));

        DefaultXYZDataset<String> dataset = new DefaultXYZDataset<>();
        Range range;

        double[][] data1 = { {1,1,1}, {1,1,1}, {1,2,3} };
        dataset.addSeries("series1", data1);
        range = r.findZBounds(dataset);
        assertNotNull(range);
        assertEquals(1d, range.getLowerBound(), EPSILON);
        assertEquals(3d, range.getUpperBound(), EPSILON);

        double[][] data2 = { {1,1,1}, {1,1,1}, {-1,-2,-3} };
        dataset.removeSeries("series1");
        dataset.addSeries("series2", data2);
        range = r.findZBounds(dataset);
        assertNotNull(range);
        assertEquals(-3d, range.getLowerBound(), EPSILON);
        assertEquals(-1d, range.getUpperBound(), EPSILON);

        double[][] data3 = { {1,1,1}, {1,1,1}, {-1.2,2.9,3.8} };
        dataset.removeSeries("series2");
        dataset.addSeries("series3", data3);
        range = r.findZBounds(dataset);
        assertNotNull(range);
        assertEquals(-1.2d, range.getLowerBound(), EPSILON);
        assertEquals(3.8d, range.getUpperBound(), EPSILON);
    }

    /**
     * Test for bug 3026341.
     */
    @Test
    public void test3026341() {
        XYShapeRenderer renderer = new XYShapeRenderer();
        assertNull(renderer.findRangeBounds(null));

        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        XYSeries<String> series = new XYSeries<>("S1");
        series.add(1.0, null);
        dataset.addSeries(series);
        Range r = renderer.findRangeBounds(dataset);
        assertNull(r);

        // test findDomainBounds as well
        r = renderer.findDomainBounds(dataset);
        assertEquals(r.getLowerBound(), 1.0, EPSILON);
        assertEquals(r.getUpperBound(), 1.0, EPSILON);

        dataset.removeAllSeries();
        r = renderer.findDomainBounds(dataset);
        assertNull(r);
    }

}
