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
 * ----------------------
 * SpiderWebPlotTest.java
 * ----------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.TestUtils;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.Rotation;
import org.jfree.chart.api.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SpiderWebPlot} class.
 */
public class SpiderWebPlotTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset<String, String>());
        SpiderWebPlot p2 = new SpiderWebPlot(new DefaultCategoryDataset<String, String>());
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        // dataExtractOrder
        p1.setDataExtractOrder(TableOrder.BY_COLUMN);
        assertNotEquals(p1, p2);
        p2.setDataExtractOrder(TableOrder.BY_COLUMN);
        assertEquals(p1, p2);

        // headPercent
        p1.setHeadPercent(0.321);
        assertNotEquals(p1, p2);
        p2.setHeadPercent(0.321);
        assertEquals(p1, p2);

        // interiorGap
        p1.setInteriorGap(0.123);
        assertNotEquals(p1, p2);
        p2.setInteriorGap(0.123);
        assertEquals(p1, p2);

        // startAngle
        p1.setStartAngle(0.456);
        assertNotEquals(p1, p2);
        p2.setStartAngle(0.456);
        assertEquals(p1, p2);

        // direction
        p1.setDirection(Rotation.ANTICLOCKWISE);
        assertNotEquals(p1, p2);
        p2.setDirection(Rotation.ANTICLOCKWISE);
        assertEquals(p1, p2);

        // maxValue
        p1.setMaxValue(123.4);
        assertNotEquals(p1, p2);
        p2.setMaxValue(123.4);
        assertEquals(p1, p2);

        // legendItemShape
        p1.setLegendItemShape(new Rectangle(1, 2, 3, 4));
        assertNotEquals(p1, p2);
        p2.setLegendItemShape(new Rectangle(1, 2, 3, 4));
        assertEquals(p1, p2);

        // seriesPaintList
        p1.setSeriesPaint(1, new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(p1, p2);
        p2.setSeriesPaint(1, new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(p1, p2);

        // defaultSeriesPaint
        p1.setDefaultSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertNotEquals(p1, p2);
        p2.setDefaultSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(p1, p2);

        // seriesOutlinePaintList
        p1.setSeriesOutlinePaint(1, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));
        assertNotEquals(p1, p2);
        p2.setSeriesOutlinePaint(1, new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.GREEN));
        assertEquals(p1, p2);

        // defaultSeriesOutlinePaint
        p1.setDefaultSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.CYAN,
                3.0f, 4.0f, Color.GREEN));
        assertNotEquals(p1, p2);
        p2.setDefaultSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.CYAN,
                3.0f, 4.0f, Color.GREEN));
        assertEquals(p1, p2);

        BasicStroke s = new BasicStroke(1.23f);

        // seriesOutlineStrokeList
        p1.setSeriesOutlineStroke(1, s);
        assertNotEquals(p1, p2);
        p2.setSeriesOutlineStroke(1, s);
        assertEquals(p1, p2);

        // defaultSeriesOutlineStroke
        p1.setDefaultSeriesOutlineStroke(s);
        assertNotEquals(p1, p2);
        p2.setDefaultSeriesOutlineStroke(s);
        assertEquals(p1, p2);

        // webFilled
        p1.setWebFilled(false);
        assertNotEquals(p1, p2);
        p2.setWebFilled(false);
        assertEquals(p1, p2);

        // axisLabelGap
        p1.setAxisLabelGap(0.11);
        assertNotEquals(p1, p2);
        p2.setAxisLabelGap(0.11);
        assertEquals(p1, p2);

        // labelFont
        p1.setLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertNotEquals(p1, p2);
        p2.setLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertEquals(p1, p2);

        // labelPaint
        p1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(p1, p2);
        p2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(p1, p2);

        // labelGenerator
        p1.setLabelGenerator(new StandardCategoryItemLabelGenerator("XYZ: {0}",
                new DecimalFormat("0.000")));
        assertNotEquals(p1, p2);
        p2.setLabelGenerator(new StandardCategoryItemLabelGenerator("XYZ: {0}",
                new DecimalFormat("0.000")));
        assertEquals(p1, p2);

        // toolTipGenerator
        p1.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertNotEquals(p1, p2);
        p2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertEquals(p1, p2);

        // urlGenerator
        p1.setURLGenerator(new StandardCategoryURLGenerator());
        assertNotEquals(p1, p2);
        p2.setURLGenerator(new StandardCategoryURLGenerator());
        assertEquals(p1, p2);

        // axisLinePaint
        p1.setAxisLinePaint(Color.RED);
        assertNotEquals(p1, p2);
        p2.setAxisLinePaint(Color.RED);
        assertEquals(p1, p2);

        // axisLineStroke
        p1.setAxisLineStroke(new BasicStroke(1.1f));
        assertNotEquals(p1, p2);
        p2.setAxisLineStroke(new BasicStroke(1.1f));
        assertEquals(p1, p2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset<String, String>());
        Rectangle2D legendShape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        p1.setLegendItemShape(legendShape);
        SpiderWebPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // change the legendItemShape
        legendShape.setRect(4.0, 3.0, 2.0, 1.0);
        assertNotEquals(p1, p2);
        p2.setLegendItemShape(legendShape);
        assertEquals(p1, p2);

        // change a series paint
        p1.setSeriesPaint(1, Color.BLACK);
        assertNotEquals(p1, p2);
        p2.setSeriesPaint(1, Color.BLACK);
        assertEquals(p1, p2);

        // change a series outline paint
        p1.setSeriesOutlinePaint(0, Color.RED);
        assertNotEquals(p1, p2);
        p2.setSeriesOutlinePaint(0, Color.RED);
        assertEquals(p1, p2);

        // change a series outline stroke
        p1.setSeriesOutlineStroke(0, new BasicStroke(1.1f));
        assertNotEquals(p1, p2);
        p2.setSeriesOutlineStroke(0, new BasicStroke(1.1f));
        assertEquals(p1, p2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset<String, String>());
        p1.setSeriesPaint(1, new GradientPaint(1.0f, 2.0f, Color.BLUE, 3.0f, 4.0f, Color.RED));
        p1.setSeriesOutlinePaint(1, new GradientPaint(4.0f, 3.0f, Color.BLUE, 2.0f, 1.0f, Color.RED));
        p1.setSeriesOutlineStroke(1, new BasicStroke(9.0f));
        SpiderWebPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Draws the chart with a null info object to make sure that no exceptions
     * are thrown.
     */
    @Test
    public void testDrawWithNullInfo() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        dataset.addValue(35.0, "S1", "C1");
        dataset.addValue(45.0, "S1", "C2");
        dataset.addValue(55.0, "S1", "C3");
        dataset.addValue(15.0, "S1", "C4");
        dataset.addValue(25.0, "S1", "C5");
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        JFreeChart chart = new JFreeChart(plot);
        try {
            BufferedImage image = new BufferedImage(200 , 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
        }
        catch (Exception e) {
            fail("There should be no exception.");
        }
    }

    /**
     * Fetches the legend items and checks the values.
     */
    @Test
    public void testGetLegendItems() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        dataset.addValue(35.0, "S1", "C1");
        dataset.addValue(45.0, "S1", "C2");
        dataset.addValue(55.0, "S2", "C1");
        dataset.addValue(15.0, "S2", "C2");
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        JFreeChart chart = new JFreeChart(plot);
        LegendItemCollection legendItems = plot.getLegendItems();
        assertEquals(2, legendItems.getItemCount());
        LegendItem item1 = legendItems.get(0);
        assertEquals("S1", item1.getLabel());
        assertEquals("S1", item1.getSeriesKey());
        assertEquals(0, item1.getSeriesIndex());
        assertEquals(dataset, item1.getDataset());
        assertEquals(0, item1.getDatasetIndex());

        LegendItem item2 = legendItems.get(1);
        assertEquals("S2", item2.getLabel());
        assertEquals("S2", item2.getSeriesKey());
        assertEquals(1, item2.getSeriesIndex());
        assertEquals(dataset, item2.getDataset());
        assertEquals(0, item2.getDatasetIndex());
    }

}
