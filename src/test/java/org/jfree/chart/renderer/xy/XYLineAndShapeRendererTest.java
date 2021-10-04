/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by David Gilbert and Contributors.
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
 * XYLineAndShapeRendererTest.java
 * -------------------------------
 * (C) Copyright 2004-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.urls.TimeSeriesURLGenerator;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.Range;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYLineAndShapeRenderer} class.
 */
public class XYLineAndShapeRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {

        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setSeriesLinesVisible(3, true);
        assertNotEquals(r1, r2);
        r2.setSeriesLinesVisible(3, true);
        assertEquals(r1, r2);

        r1.setDefaultLinesVisible(false);
        assertNotEquals(r1, r2);
        r2.setDefaultLinesVisible(false);
        assertEquals(r1, r2);

        r1.setLegendLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(r1, r2);
        r2.setLegendLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(r1, r2);

        r1.setSeriesShapesVisible(3, true);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesVisible(3, true);
        assertEquals(r1, r2);

        r1.setDefaultShapesVisible(false);
        assertNotEquals(r1, r2);
        r2.setDefaultShapesVisible(false);
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(3, true);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesFilled(3, true);
        assertEquals(r1, r2);

        r1.setDefaultShapesFilled(false);
        assertNotEquals(r1, r2);
        r2.setDefaultShapesFilled(false);
        assertEquals(r1, r2);

        r1.setDrawOutlines(!r1.getDrawOutlines());
        assertNotEquals(r1, r2);
        r2.setDrawOutlines(r1.getDrawOutlines());
        assertEquals(r1, r2);

        r1.setUseOutlinePaint(true);
        assertNotEquals(r1, r2);
        r2.setUseOutlinePaint(true);
        assertEquals(r1, r2);

        r1.setUseFillPaint(true);
        assertNotEquals(r1, r2);
        r2.setUseFillPaint(true);
        assertEquals(r1, r2);

        r1.setDrawSeriesLineAsPath(true);
        assertNotEquals(r1, r2);
        r2.setDrawSeriesLineAsPath(true);
        assertEquals(r1, r2);
    }

    /**
     * Test that the equals() method works for a TimeSeriesURLGenerator.
     */
    @Test
    public void testEquals2() {
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setURLGenerator(new TimeSeriesURLGenerator());
        assertNotEquals(r1, r2);
        r2.setURLGenerator(new TimeSeriesURLGenerator());
        assertEquals(r1, r2);
    }


    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer();
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
        Rectangle2D legendShape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        r1.setLegendLine(legendShape);
        XYLineAndShapeRenderer r2 = CloneUtils.clone(r1);
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);

        r1.setSeriesLinesVisible(0, false);
        assertNotEquals(r1, r2);
        r2.setSeriesLinesVisible(0, false);
        assertEquals(r1, r2);

        legendShape.setRect(4.0, 3.0, 2.0, 1.0);
        assertNotEquals(r1, r2);
        r2.setLegendLine(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertEquals(r1, r2);

        r1.setSeriesShapesVisible(1, true);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesVisible(1, true);
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(1, true);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesFilled(1, true);
        assertEquals(r1, r2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        r1.setSeriesShape(1, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        r1.setDefaultShape(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        r1.setLegendLine(new Rectangle(1, 2, 3, 4));
        r1.setDefaultLegendShape(new Ellipse2D.Double(1.0, 2.0, 3.0, 4.0));
        XYLineAndShapeRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * Check that the renderer is calculating the domain bounds correctly.
     */
    @Test
    public void testFindDomainBounds() {
        XYSeriesCollection<String> dataset
                = RendererXYPackageUtils.createTestXYSeriesCollection();
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Test Chart", "X", "Y", dataset, PlotOrientation.VERTICAL,
                false, false, false);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        Range bounds = domainAxis.getRange();
        assertFalse(bounds.contains(0.9));
        assertTrue(bounds.contains(1.0));
        assertTrue(bounds.contains(2.0));
        assertFalse(bounds.contains(2.10));
    }

    /**
     * Check that the renderer is calculating the range bounds correctly.
     */
    @Test
    public void testFindRangeBounds() {
        TableXYDataset<String> dataset
                = RendererXYPackageUtils.createTestTableXYDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Test Chart", "X", "Y", dataset, PlotOrientation.VERTICAL,
                false, false, false);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setAutoRangeIncludesZero(false);
        Range bounds = rangeAxis.getRange();
        assertFalse(bounds.contains(1.0));
        assertTrue(bounds.contains(2.0));
        assertTrue(bounds.contains(5.0));
        assertFalse(bounds.contains(6.0));
    }

    /**
     * A check for the datasetIndex and seriesIndex fields in the LegendItem
     * returned by the getLegendItem() method.
     */
    @Test
    public void testGetLegendItemSeriesIndex() {
        XYSeriesCollection<String> d1 = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("S1");
        s1.add(1.0, 1.1);
        XYSeries<String> s2 = new XYSeries<>("S2");
        s2.add(1.0, 1.1);
        d1.addSeries(s1);
        d1.addSeries(s2);

        XYSeriesCollection<String> d2 = new XYSeriesCollection<>();
        XYSeries<String> s3 = new XYSeries<>("S3");
        s3.add(1.0, 1.1);
        XYSeries<String> s4 = new XYSeries<>("S4");
        s4.add(1.0, 1.1);
        XYSeries<String> s5 = new XYSeries<>("S5");
        s5.add(1.0, 1.1);
        d2.addSeries(s3);
        d2.addSeries(s4);
        d2.addSeries(s5);

        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer();
        XYPlot<String> plot = new XYPlot<>(d1, new NumberAxis("x"),
                new NumberAxis("y"), r);
        plot.setDataset(1, d2);
        JFreeChart chart = new JFreeChart(plot);
        LegendItem li = r.getLegendItem(1, 2);
        assertEquals("S5", li.getLabel());
        assertEquals(1, li.getDatasetIndex());
        assertEquals(2, li.getSeriesIndex());
    }

}
