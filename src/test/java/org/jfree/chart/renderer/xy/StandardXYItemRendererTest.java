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
 * StandardXYItemRendererTest.java
 * -------------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.TestUtils;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.api.UnitType;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link StandardXYItemRenderer} class.
 */
public class StandardXYItemRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        StandardXYItemRenderer r1 = new StandardXYItemRenderer();
        StandardXYItemRenderer r2 = new StandardXYItemRenderer();
        assertEquals(r1, r2);

        r1.setBaseShapesVisible(true);
        assertNotEquals(r1, r2);
        r2.setBaseShapesVisible(true);
        assertEquals(r1, r2);

        r1.setPlotLines(false);
        assertNotEquals(r1, r2);
        r2.setPlotLines(false);
        assertEquals(r1, r2);

        r1.setPlotImages(true);
        assertNotEquals(r1, r2);
        r2.setPlotImages(true);
        assertEquals(r1, r2);

        r1.setPlotDiscontinuous(true);
        assertNotEquals(r1, r2);
        r2.setPlotDiscontinuous(true);
        assertEquals(r1, r2);

        r1.setGapThresholdType(UnitType.ABSOLUTE);
        assertNotEquals(r1, r2);
        r2.setGapThresholdType(UnitType.ABSOLUTE);
        assertEquals(r1, r2);

        r1.setGapThreshold(1.23);
        assertNotEquals(r1, r2);
        r2.setGapThreshold(1.23);
        assertEquals(r1, r2);

        r1.setLegendLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(r1, r2);
        r2.setLegendLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(1, Boolean.TRUE);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesFilled(1, Boolean.TRUE);
        assertEquals(r1, r2);

        r1.setBaseShapesFilled(false);
        assertNotEquals(r1, r2);
        r2.setBaseShapesFilled(false);
        assertEquals(r1, r2);

        r1.setDrawSeriesLineAsPath(true);
        assertNotEquals(r1, r2);
        r2.setDrawSeriesLineAsPath(true);
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        StandardXYItemRenderer r1 = new StandardXYItemRenderer();
        StandardXYItemRenderer r2 = new StandardXYItemRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StandardXYItemRenderer r1 = new StandardXYItemRenderer();
        Rectangle2D rect1 = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        r1.setLegendLine(rect1);
        StandardXYItemRenderer r2 = CloneUtils.clone(r1);
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);

        // check independence
        rect1.setRect(4.0, 3.0, 2.0, 1.0);
        assertNotEquals(r1, r2);
        r2.setLegendLine(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(1, Boolean.TRUE);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesFilled(1, Boolean.TRUE);
        assertEquals(r1, r2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        StandardXYItemRenderer r1 = new StandardXYItemRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StandardXYItemRenderer r1 = new StandardXYItemRenderer();
        StandardXYItemRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
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

        StandardXYItemRenderer r = new StandardXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(d1, new NumberAxis("x"),
                new NumberAxis("y"), r);
        plot.setDataset(1, d2);
        JFreeChart chart = new JFreeChart(plot);
        LegendItem li = r.getLegendItem(1, 2);
        assertEquals("S5", li.getLabel());
        assertEquals(1, li.getDatasetIndex());
        assertEquals(2, li.getSeriesIndex());
    }

    /**
     * A check to ensure that an item that falls outside the plot's data area
     * does NOT generate an item entity.
     */
    @Test
    public void testNoDisplayedItem() {
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("S1");
        s1.add(10.0, 10.0);
        dataset.addSeries(s1);
        JFreeChart chart = ChartFactory.createXYLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        plot.setRenderer(new StandardXYItemRenderer());
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setRange(0.0, 5.0);
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(0.0, 5.0);
        BufferedImage image = new BufferedImage(200 , 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        ChartRenderingInfo info = new ChartRenderingInfo();
        chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, info);
        g2.dispose();
        EntityCollection ec = info.getEntityCollection();
        assertFalse(TestUtils.containsInstanceOf(ec.getEntities(), XYItemEntity.class));
    }

}
