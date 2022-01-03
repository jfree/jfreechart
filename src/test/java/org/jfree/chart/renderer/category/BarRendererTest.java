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
 * BarRendererTest.java
 * --------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer.category;

import java.awt.BasicStroke;

import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.RendererChangeDetector;
import org.jfree.chart.util.GradientPaintTransformType;
import org.jfree.chart.util.StandardGradientPaintTransformer;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link BarRenderer} class.
 */
public class BarRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        BarRenderer r1 = new BarRenderer();
        BarRenderer r2 = new BarRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        // base value
        r1.setBase(0.123);
        assertNotEquals(r1, r2);
        r2.setBase(0.123);
        assertEquals(r1, r2);

        // itemMargin
        r1.setItemMargin(0.22);
        assertNotEquals(r1, r2);
        r2.setItemMargin(0.22);
        assertEquals(r1, r2);

        // drawBarOutline
        r1.setDrawBarOutline(!r1.isDrawBarOutline());
        assertNotEquals(r1, r2);
        r2.setDrawBarOutline(!r2.isDrawBarOutline());
        assertEquals(r1, r2);

        // maximumBarWidth
        r1.setMaximumBarWidth(0.11);
        assertNotEquals(r1, r2);
        r2.setMaximumBarWidth(0.11);
        assertEquals(r1, r2);

        // minimumBarLength
        r1.setMinimumBarLength(0.04);
        assertNotEquals(r1, r2);
        r2.setMinimumBarLength(0.04);
        assertEquals(r1, r2);

        // gradientPaintTransformer
        r1.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_VERTICAL));
        assertNotEquals(r1, r2);
        r2.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_VERTICAL));
        assertEquals(r1, r2);

        // positiveItemLabelPositionFallback
        r1.setPositiveItemLabelPositionFallback(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertNotEquals(r1, r2);
        r2.setPositiveItemLabelPositionFallback(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertEquals(r1, r2);

        // negativeItemLabelPositionFallback
        r1.setNegativeItemLabelPositionFallback(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertNotEquals(r1, r2);
        r2.setNegativeItemLabelPositionFallback(new ItemLabelPosition(
                ItemLabelAnchor.INSIDE1, TextAnchor.CENTER));
        assertEquals(r1, r2);

        // barPainter
        r1.setBarPainter(new GradientBarPainter(0.1, 0.2, 0.3));
        assertNotEquals(r1, r2);
        r2.setBarPainter(new GradientBarPainter(0.1, 0.2, 0.3));
        assertEquals(r1, r2);

        // shadowsVisible
        r1.setShadowVisible(false);
        assertNotEquals(r1, r2);
        r2.setShadowVisible(false);
        assertEquals(r1, r2);

        r1.setShadowPaint(Color.RED);
        assertNotEquals(r1, r2);
        r2.setShadowPaint(Color.RED);
        assertEquals(r1, r2);

        // shadowXOffset
        r1.setShadowXOffset(3.3);
        assertNotEquals(r1, r2);
        r2.setShadowXOffset(3.3);
        assertEquals(r1, r2);

        // shadowYOffset
        r1.setShadowYOffset(3.3);
        assertNotEquals(r1, r2);
        r2.setShadowYOffset(3.3);
        assertEquals(r1, r2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        BarRenderer r1 = new BarRenderer();
        BarRenderer r2 = new BarRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        BarRenderer r1 = new BarRenderer();
        r1.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        r1.setBarPainter(new GradientBarPainter(0.11, 0.22, 0.33));
        BarRenderer r2 = (BarRenderer) r1.clone();
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
        TestUtils.checkIndependence(r1, r2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        BarRenderer r1 = new BarRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        BarRenderer r1 = new BarRenderer();
        r1.setSeriesOutlineStroke(1, new BasicStroke(9.0f));
        r1.setDefaultOutlineStroke(new BasicStroke(4.5f));
        BarRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
        assertNotSame(r1, r2);
        TestUtils.checkIndependence(r1, r2);
    }

    /**
     * Tests each setter method to ensure that it sends an event notification.
     */
    @Test
    public void testEventNotification() {

        RendererChangeDetector detector = new RendererChangeDetector();
        BarRenderer r1 = new BarRenderer();
        r1.addChangeListener(detector);

        detector.setNotified(false);
        r1.setDefaultPaint(Color.RED);
        assertTrue(detector.getNotified());

    }

    /**
     * Some checks for the getLegendItem() method.
     */
    @Test
    public void testGetLegendItem() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        dataset.addValue(21.0, "R1", "C1");
        BarRenderer r = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, 
                new CategoryAxis("x"), new NumberAxis("y"), r);
        /*JFreeChart chart =*/ new JFreeChart(plot);
        LegendItem li = r.getLegendItem(0, 0);
        assertNotNull(li);
        r.setSeriesVisibleInLegend(0, Boolean.FALSE);
        li = r.getLegendItem(0, 0);
        assertNull(li);
    }

    /**
     * A check for the datasetIndex and seriesIndex fields in the LegendItem
     * returned by the getLegendItem() method.
     */
    @Test
    public void testGetLegendItemSeriesIndex() {
        DefaultCategoryDataset<String, String> dataset0 = new DefaultCategoryDataset<>();
        dataset0.addValue(21.0, "R1", "C1");
        dataset0.addValue(22.0, "R2", "C1");
        DefaultCategoryDataset<String, String> dataset1 = new DefaultCategoryDataset<>();
        dataset1.addValue(23.0, "R3", "C1");
        dataset1.addValue(24.0, "R4", "C1");
        dataset1.addValue(25.0, "R5", "C1");
        BarRenderer r = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset0, 
                new CategoryAxis("x"), new NumberAxis("y"), r);
        plot.setDataset(1, dataset1);
        /*JFreeChart chart =*/ new JFreeChart(plot);
        LegendItem li = r.getLegendItem(1, 2);
        assertEquals("R5", li.getLabel());
        assertEquals(1, li.getDatasetIndex());
        assertEquals(2, li.getSeriesIndex());
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        BarRenderer r = new BarRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        assertNull(r.findRangeBounds(dataset));

        dataset.addValue(1.0, "R1", "C1");
        assertEquals(new Range(0.0, 1.0), r.findRangeBounds(dataset));
        r.setIncludeBaseInRange(false);
        assertEquals(new Range(1.0, 1.0), r.findRangeBounds(dataset));
        r.setIncludeBaseInRange(true);

        dataset.addValue(-2.0, "R1", "C2");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(null, "R1", "C3");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(-6.0, "R2", "C1");
        assertEquals(new Range(-6.0, 1.0), r.findRangeBounds(dataset));

        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));
    }

}
