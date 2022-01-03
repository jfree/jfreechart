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
 * ------------------
 * PolarPlotTest.java
 * ------------------
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
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link PolarPlot} class.
 */
public class PolarPlotTest {

    /**
     * Some checks for the getLegendItems() method.
     */
    @Test
    public void testGetLegendItems() {
        XYSeriesCollection<String> d = new XYSeriesCollection<>();
        d.addSeries(new XYSeries<>("A"));
        d.addSeries(new XYSeries<>("B"));
        DefaultPolarItemRenderer r = new DefaultPolarItemRenderer();
        PolarPlot plot = new PolarPlot();
        plot.setDataset(d);
        plot.setRenderer(r);
        LegendItemCollection items = plot.getLegendItems();
        assertEquals(2, items.getItemCount());
        LegendItem item1 = items.get(0);
        assertEquals("A", item1.getLabel());
        LegendItem item2 = items.get(1);
        assertEquals("B", item2.getLabel());
    }

    /**
     * Some checks for the getLegendItems() method with multiple datasets.
     */
    @Test
    public void testGetLegendItems2() {
        XYSeriesCollection<String> d1 = new XYSeriesCollection<>();
        d1.addSeries(new XYSeries<>("A"));
        d1.addSeries(new XYSeries<>("B"));
        XYSeriesCollection<String> d2 = new XYSeriesCollection<>();
        d2.addSeries(new XYSeries<>("C"));
        d2.addSeries(new XYSeries<>("D"));
        DefaultPolarItemRenderer r = new DefaultPolarItemRenderer();
        PolarPlot plot = new PolarPlot();
        plot.setDataset(d1);
        plot.setDataset(1, d2);
        plot.setRenderer(r);
        plot.setRenderer(1, new DefaultPolarItemRenderer());
        LegendItemCollection items = plot.getLegendItems();
        assertEquals(4, items.getItemCount());
        LegendItem item1 = items.get(0);
        assertEquals("A", item1.getLabel());
        LegendItem item2 = items.get(1);
        assertEquals("B", item2.getLabel());
        LegendItem item3 = items.get(2);
        assertEquals("C", item3.getLabel());
        LegendItem item4 = items.get(3);
        assertEquals("D", item4.getLabel());
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        PolarPlot plot1 = new PolarPlot();
        PolarPlot plot2 = new PolarPlot();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        plot1.setAngleGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setAngleGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        Stroke s = new BasicStroke(1.23f);
        plot1.setAngleGridlineStroke(s);
        assertNotEquals(plot1, plot2);
        plot2.setAngleGridlineStroke(s);
        assertEquals(plot1, plot2);

        plot1.setAngleTickUnit(new NumberTickUnit(11.0));
        assertNotEquals(plot1, plot2);
        plot2.setAngleTickUnit(new NumberTickUnit(11.0));
        assertEquals(plot1, plot2);

        plot1.setAngleGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setAngleGridlinesVisible(false);
        assertEquals(plot1, plot2);

        plot1.setAngleLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertNotEquals(plot1, plot2);
        plot2.setAngleLabelFont(new Font("Serif", Font.PLAIN, 9));
        assertEquals(plot1, plot2);

        plot1.setAngleLabelPaint(new GradientPaint(9.0f, 8.0f, Color.BLUE,
                7.0f, 6.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setAngleLabelPaint(new GradientPaint(9.0f, 8.0f, Color.BLUE,
                7.0f, 6.0f, Color.RED));
        assertEquals(plot1, plot2);

        plot1.setAngleLabelsVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setAngleLabelsVisible(false);
        assertEquals(plot1, plot2);

        plot1.setAxis(new NumberAxis("Test"));
        assertNotEquals(plot1, plot2);
        plot2.setAxis(new NumberAxis("Test"));
        assertEquals(plot1, plot2);

        plot1.setRadiusGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.BLACK));
        assertNotEquals(plot1, plot2);
        plot2.setRadiusGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.BLACK));
        assertEquals(plot1, plot2);

        plot1.setRadiusGridlineStroke(s);
        assertNotEquals(plot1, plot2);
        plot2.setRadiusGridlineStroke(s);
        assertEquals(plot1, plot2);

        plot1.setRadiusGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setRadiusGridlinesVisible(false);
        assertEquals(plot1, plot2);

        plot1.setRadiusMinorGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setRadiusMinorGridlinesVisible(false);
        assertEquals(plot1, plot2);

        plot1.addCornerTextItem("XYZ");
        assertNotEquals(plot1, plot2);
        plot2.addCornerTextItem("XYZ");
        assertEquals(plot1, plot2);

        plot1.setMargin(6);
        assertNotEquals(plot1, plot2);
        plot2.setMargin(6);
        assertEquals(plot1, plot2);

        LegendItemCollection lic1 = new LegendItemCollection();
        lic1.add(new LegendItem("XYZ", Color.RED));
        plot1.setFixedLegendItems(lic1);
        assertNotEquals(plot1, plot2);
        LegendItemCollection lic2 = new LegendItemCollection();
        lic2.add(new LegendItem("XYZ", Color.RED));
        plot2.setFixedLegendItems(lic2);
        assertEquals(plot1, plot2);
    }

    /**
     * Some basic checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PolarPlot p1 = new PolarPlot();
        PolarPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check independence
        p1.addCornerTextItem("XYZ");
        assertNotEquals(p1, p2);
        p2.addCornerTextItem("XYZ");
        assertEquals(p1, p2);

        p1 = new PolarPlot(new DefaultXYDataset<String>(), new NumberAxis("A1"),
                new DefaultPolarItemRenderer());
        p2 = (PolarPlot) p1.clone();
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check independence
        p1.getAxis().setLabel("ABC");
        assertNotEquals(p1, p2);
        p2.getAxis().setLabel("ABC");
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PolarPlot p1 = new PolarPlot();
        p1.setAngleGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        p1.setAngleLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        p1.setRadiusGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        PolarPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    @Test
    public void testTranslateToJava2D_NumberAxis() {
        
        Rectangle2D dataArea = new Rectangle2D.Double(0.0, 0.0, 100.0, 100.0);
        ValueAxis axis = new NumberAxis();
        axis.setRange(0.0, 20.0);

        PolarPlot plot = new PolarPlot(null, axis, null);
        plot.setMargin(0);
        plot.setAngleOffset(0.0);

        Point point = plot.translateToJava2D(0.0, 10.0, axis, dataArea );
        assertEquals(75.0, point.getX(), 0.5);
        assertEquals(50.0, point.getY(), 0.5);

        point = plot.translateToJava2D(90.0, 5.0, axis, dataArea );
        assertEquals(50.0, point.getX(), 0.5);
        assertEquals(62.5, point.getY(), 0.5);

        point = plot.translateToJava2D(45.0, 20.0, axis, dataArea );
        assertEquals(85.0, point.getX(), 0.5);
        assertEquals(85.0, point.getY(), 0.5);

        point = plot.translateToJava2D(135.0, 20.0, axis, dataArea );
        assertEquals(15.0, point.getX(), 0.5);
        assertEquals(85.0, point.getY(), 0.5);

        point = plot.translateToJava2D(225.0, 15.0, axis, dataArea );
        assertEquals(23.0, point.getX(), 0.5);
        assertEquals(23.0, point.getY(), 0.5);

        point = plot.translateToJava2D(315.0, 15.0, axis, dataArea );
        assertEquals(77.0, point.getX(), 0.5);
        assertEquals(23.0, point.getY(), 0.5);
        
        point = plot.translateToJava2D(21.0, 11.5, axis, dataArea );
        assertEquals(77.0, point.getX(), 0.5);
        assertEquals(60.0, point.getY(), 0.5);
        
        point = plot.translateToJava2D(162.0, 7.0, axis, dataArea );
        assertEquals(33.0, point.getX(), 0.5);
        assertEquals(55.0, point.getY(), 0.5);
        
    }

    @Test
    public void testTranslateToJava2D_NumberAxisAndMargin() {
        
        Rectangle2D dataArea = new Rectangle2D.Double(10.0, 10.0, 80.0, 80.0);
        ValueAxis axis = new NumberAxis();
        axis.setRange(-2.0, 2.0);

        PolarPlot plot = new PolarPlot(null, axis, null);
        plot.setAngleOffset(0.0);

        Point point = plot.translateToJava2D(0.0, 10.0, axis, dataArea );
        assertEquals(110.0, point.getX(), 0.5);
        assertEquals(50.0, point.getY(), 0.5);

        point = plot.translateToJava2D(90.0, 5.0, axis, dataArea );
        assertEquals(50.0, point.getX(), 0.5);
        assertEquals(85.0, point.getY(), 0.5);

        point = plot.translateToJava2D(45.0, 20.0, axis, dataArea );
        assertEquals(128.0, point.getX(), 0.5);
        assertEquals(128.0, point.getY(), 0.5);

        point = plot.translateToJava2D(135.0, 20.0, axis, dataArea );
        assertEquals(-28.0, point.getX(), 0.5);
        assertEquals(128.0, point.getY(), 0.5);

        point = plot.translateToJava2D(225.0, 15.0, axis, dataArea );
        assertEquals(-10.0, point.getX(), 0.5);
        assertEquals(-10.0, point.getY(), 0.5);

        point = plot.translateToJava2D(315.0, 15.0, axis, dataArea );
        assertEquals(110.0, point.getX(), 0.5);
        assertEquals(-10.0, point.getY(), 0.5);
        
        point = plot.translateToJava2D(21.0, 11.5, axis, dataArea );
        assertEquals(113.0, point.getX(), 0.5);
        assertEquals(74.0, point.getY(), 0.5);
        
        point = plot.translateToJava2D(162.0, 7.0, axis, dataArea );
        assertEquals(7.0, point.getX(), 0.5);
        assertEquals(64.0, point.getY(), 0.5);
        
    }

    @Test
    public void testTranslateToJava2D_LogAxis() {
        
        Rectangle2D dataArea = new Rectangle2D.Double(0.0, 0.0, 100.0, 100.0);
        ValueAxis axis = new LogAxis();
        axis.setRange(1.0, 100.0);

        PolarPlot plot = new PolarPlot(null, axis, null);
        plot.setMargin(0);
        plot.setAngleOffset(0.0);

        Point point = plot.translateToJava2D(0.0, 10.0, axis, dataArea );
        assertEquals(75.0, point.getX(), 0.5);
        assertEquals(50.0, point.getY(), 0.5);

        point = plot.translateToJava2D(90.0, 5.0, axis, dataArea );
        assertEquals(50.0, point.getX(), 0.5);
        assertEquals(67.5, point.getY(), 0.5);

        point = plot.translateToJava2D(45.0, 20.0, axis, dataArea );
        assertEquals(73.0, point.getX(), 0.5);
        assertEquals(73.0, point.getY(), 0.5);
    }
}
