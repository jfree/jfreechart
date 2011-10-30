/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * SpiderWebPlotTests.java
 * -----------------------
 * (C) Copyright 2005-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jun-2005 : Version 1 (DG);
 * 01-Jun-2006 : Added testDrawWithNullInfo() method (DG);
 * 05-Feb-2007 : Added more checks to testCloning (DG);
 * 01-Jun-2009 : Added test for getLegendItems() bug, series key is not
 *               set (DG);
 *
 */

package org.jfree.chart.plot.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.Rotation;
import org.jfree.util.TableOrder;

/**
 * Tests for the {@link SpiderWebPlot} class.
 */
public class SpiderWebPlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(SpiderWebPlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public SpiderWebPlotTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset());
        SpiderWebPlot p2 = new SpiderWebPlot(new DefaultCategoryDataset());
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        // dataExtractOrder
        p1.setDataExtractOrder(TableOrder.BY_COLUMN);
        assertFalse(p1.equals(p2));
        p2.setDataExtractOrder(TableOrder.BY_COLUMN);
        assertTrue(p1.equals(p2));

        // headPercent
        p1.setHeadPercent(0.321);
        assertFalse(p1.equals(p2));
        p2.setHeadPercent(0.321);
        assertTrue(p1.equals(p2));

        // interiorGap
        p1.setInteriorGap(0.123);
        assertFalse(p1.equals(p2));
        p2.setInteriorGap(0.123);
        assertTrue(p1.equals(p2));

        // startAngle
        p1.setStartAngle(0.456);
        assertFalse(p1.equals(p2));
        p2.setStartAngle(0.456);
        assertTrue(p1.equals(p2));

        // direction
        p1.setDirection(Rotation.ANTICLOCKWISE);
        assertFalse(p1.equals(p2));
        p2.setDirection(Rotation.ANTICLOCKWISE);
        assertTrue(p1.equals(p2));

        // maxValue
        p1.setMaxValue(123.4);
        assertFalse(p1.equals(p2));
        p2.setMaxValue(123.4);
        assertTrue(p1.equals(p2));

        // legendItemShape
        p1.setLegendItemShape(new Rectangle(1, 2, 3, 4));
        assertFalse(p1.equals(p2));
        p2.setLegendItemShape(new Rectangle(1, 2, 3, 4));
        assertTrue(p1.equals(p2));

        // seriesPaint
        p1.setSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.white));
        assertFalse(p1.equals(p2));
        p2.setSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.white));
        assertTrue(p1.equals(p2));

        // seriesPaintList
        p1.setSeriesPaint(1, new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.white));
        assertFalse(p1.equals(p2));
        p2.setSeriesPaint(1, new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.white));
        assertTrue(p1.equals(p2));

        // baseSeriesPaint
        p1.setBaseSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.black));
        assertFalse(p1.equals(p2));
        p2.setBaseSeriesPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.black));
        assertTrue(p1.equals(p2));

        // seriesOutlinePaint
        p1.setSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.black));
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.black));
        assertTrue(p1.equals(p2));

        // seriesOutlinePaintList
        p1.setSeriesOutlinePaint(1, new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.green));
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlinePaint(1, new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.green));
        assertTrue(p1.equals(p2));

        // baseSeriesOutlinePaint
        p1.setBaseSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.green));
        assertFalse(p1.equals(p2));
        p2.setBaseSeriesOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.green));
        assertTrue(p1.equals(p2));

        // seriesOutlineStroke
        BasicStroke s = new BasicStroke(1.23f);
        p1.setSeriesOutlineStroke(s);
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlineStroke(s);
        assertTrue(p1.equals(p2));

        // seriesOutlineStrokeList
        p1.setSeriesOutlineStroke(1, s);
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlineStroke(1, s);
        assertTrue(p1.equals(p2));

        // baseSeriesOutlineStroke
        p1.setBaseSeriesOutlineStroke(s);
        assertFalse(p1.equals(p2));
        p2.setBaseSeriesOutlineStroke(s);
        assertTrue(p1.equals(p2));

        // webFilled
        p1.setWebFilled(false);
        assertFalse(p1.equals(p2));
        p2.setWebFilled(false);
        assertTrue(p1.equals(p2));

        // axisLabelGap
        p1.setAxisLabelGap(0.11);
        assertFalse(p1.equals(p2));
        p2.setAxisLabelGap(0.11);
        assertTrue(p1.equals(p2));

        // labelFont
        p1.setLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertFalse(p1.equals(p2));
        p2.setLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertTrue(p1.equals(p2));

        // labelPaint
        p1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        assertFalse(p1.equals(p2));
        p2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        assertTrue(p1.equals(p2));

        // labelGenerator
        p1.setLabelGenerator(new StandardCategoryItemLabelGenerator("XYZ: {0}",
                new DecimalFormat("0.000")));
        assertFalse(p1.equals(p2));
        p2.setLabelGenerator(new StandardCategoryItemLabelGenerator("XYZ: {0}",
                new DecimalFormat("0.000")));
        assertTrue(p1.equals(p2));

        // toolTipGenerator
        p1.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertFalse(p1.equals(p2));
        p2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertTrue(p1.equals(p2));

        // urlGenerator
        p1.setURLGenerator(new StandardCategoryURLGenerator());
        assertFalse(p1.equals(p2));
        p2.setURLGenerator(new StandardCategoryURLGenerator());
        assertTrue(p1.equals(p2));

        // axisLinePaint
        p1.setAxisLinePaint(Color.red);
        assertFalse(p1.equals(p2));
        p2.setAxisLinePaint(Color.red);
        assertTrue(p1.equals(p2));

        // axisLineStroke
        p1.setAxisLineStroke(new BasicStroke(1.1f));
        assertFalse(p1.equals(p2));
        p2.setAxisLineStroke(new BasicStroke(1.1f));
        assertTrue(p1.equals(p2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset());
        Rectangle2D legendShape = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        p1.setLegendItemShape(legendShape);
        SpiderWebPlot p2 = null;
        try {
            p2 = (SpiderWebPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        // change the legendItemShape
        legendShape.setRect(4.0, 3.0, 2.0, 1.0);
        assertFalse(p1.equals(p2));
        p2.setLegendItemShape(legendShape);
        assertTrue(p1.equals(p2));

        // change a series paint
        p1.setSeriesPaint(1, Color.black);
        assertFalse(p1.equals(p2));
        p2.setSeriesPaint(1, Color.black);
        assertTrue(p1.equals(p2));

        // change a series outline paint
        p1.setSeriesOutlinePaint(0, Color.red);
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlinePaint(0, Color.red);
        assertTrue(p1.equals(p2));

        // change a series outline stroke
        p1.setSeriesOutlineStroke(0, new BasicStroke(1.1f));
        assertFalse(p1.equals(p2));
        p2.setSeriesOutlineStroke(0, new BasicStroke(1.1f));
        assertTrue(p1.equals(p2));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        SpiderWebPlot p1 = new SpiderWebPlot(new DefaultCategoryDataset());
        SpiderWebPlot p2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (SpiderWebPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, p2);

    }

    /**
     * Draws the chart with a null info object to make sure that no exceptions
     * are thrown.
     */
    public void testDrawWithNullInfo() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(35.0, "S1", "C1");
        dataset.addValue(45.0, "S1", "C2");
        dataset.addValue(55.0, "S1", "C3");
        dataset.addValue(15.0, "S1", "C4");
        dataset.addValue(25.0, "S1", "C5");
        SpiderWebPlot plot = new SpiderWebPlot(dataset);
        JFreeChart chart = new JFreeChart(plot);
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(200 , 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            success = false;
        }
        assertTrue(success);
    }

    /**
     * Fetches the legend items and checks the values.
     */
    public void testGetLegendItems() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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
