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
 * ValueAxisTest.java
 * ------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ValueAxis} class.
 */
public class ValueAxisTest {

    private static final double EPSILON = 0.000000001;

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        ValueAxis a1 = new NumberAxis("Test");
        ValueAxis a2 = (NumberAxis) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        NumberAxis a1 = new NumberAxis("Test");
        NumberAxis a2 = new NumberAxis("Test");
        assertEquals(a1, a2);

        // axis line visible flag...
        a1.setAxisLineVisible(false);
        assertNotEquals(a1, a2);
        a2.setAxisLineVisible(false);
        assertEquals(a1, a2);

        // positiveArrowVisible;
        a1.setPositiveArrowVisible(true);
        assertNotEquals(a1, a2);
        a2.setPositiveArrowVisible(true);
        assertEquals(a1, a2);

        // negativeArrowVisible;
        a1.setNegativeArrowVisible(true);
        assertNotEquals(a1, a2);
        a2.setNegativeArrowVisible(true);
        assertEquals(a1, a2);

        //private Shape upArrow;

        //private Shape downArrow;

        //private Shape leftArrow;

        //private Shape rightArrow;

        // axisLinePaint
        a1.setAxisLinePaint(Color.BLUE);
        assertNotEquals(a1, a2);
        a2.setAxisLinePaint(Color.BLUE);
        assertEquals(a1, a2);

        // axisLineStroke
        Stroke stroke = new BasicStroke(2.0f);
        a1.setAxisLineStroke(stroke);
        assertNotEquals(a1, a2);
        a2.setAxisLineStroke(stroke);
        assertEquals(a1, a2);

        // inverted
        a1.setInverted(true);
        assertNotEquals(a1, a2);
        a2.setInverted(true);
        assertEquals(a1, a2);

        // range
        a1.setRange(new Range(50.0, 75.0));
        assertNotEquals(a1, a2);
        a2.setRange(new Range(50.0, 75.0));
        assertEquals(a1, a2);

        // autoRange
        a1.setAutoRange(true);
        assertNotEquals(a1, a2);
        a2.setAutoRange(true);
        assertEquals(a1, a2);

        // autoRangeMinimumSize
        a1.setAutoRangeMinimumSize(3.33);
        assertNotEquals(a1, a2);
        a2.setAutoRangeMinimumSize(3.33);
        assertEquals(a1, a2);

        a1.setDefaultAutoRange(new Range(1.2, 3.4));
        assertNotEquals(a1, a2);
        a2.setDefaultAutoRange(new Range(1.2, 3.4));
        assertEquals(a1, a2);

        // upperMargin
        a1.setUpperMargin(0.09);
        assertNotEquals(a1, a2);
        a2.setUpperMargin(0.09);
        assertEquals(a1, a2);

        // lowerMargin
        a1.setLowerMargin(0.09);
        assertNotEquals(a1, a2);
        a2.setLowerMargin(0.09);
        assertEquals(a1, a2);

        //private double fixedAutoRange;
        a1.setFixedAutoRange(50.0);
        assertNotEquals(a1, a2);
        a2.setFixedAutoRange(50.0);
        assertEquals(a1, a2);

        //private boolean autoTickUnitSelection;
        a1.setAutoTickUnitSelection(false);
        assertNotEquals(a1, a2);
        a2.setAutoTickUnitSelection(false);
        assertEquals(a1, a2);

        //private TickUnits standardTickUnits;
        a1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        assertNotEquals(a1, a2);
        a2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        assertEquals(a1, a2);

        // verticalTickLabels
        a1.setVerticalTickLabels(true);
        assertNotEquals(a1, a2);
        a2.setVerticalTickLabels(true);
        assertEquals(a1, a2);


        //private int autoTickIndex;
        //protected double reservedForTickLabels;
        //protected double reservedForAxisLabel;

    }

    /**
     * Tests the the lower and upper margin settings produce the expected
     * results.
     */
    @Test
    public void testAxisMargins() {
        XYSeries<String> series = new XYSeries<>("S1");
        series.add(100.0, 1.1);
        series.add(200.0, 2.2);
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>(series);
        dataset.setIntervalWidth(0.0);
        JFreeChart chart = ChartFactory.createScatterPlot("Title", "X", "Y", 
                dataset);
        ValueAxis domainAxis = ((XYPlot) chart.getPlot()).getDomainAxis();
        Range r = domainAxis.getRange();
        assertEquals(110.0, r.getLength(), EPSILON);
        domainAxis.setLowerMargin(0.10);
        domainAxis.setUpperMargin(0.10);
        r = domainAxis.getRange();
        assertEquals(120.0, r.getLength(), EPSILON);
    }
    
    /**
     * A test for bug 3555275 (where the fixed axis space is calculated 
     * incorrectly).
     */
    @Test
    public void test3555275() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        JFreeChart chart = ChartFactory.createLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        CategoryPlot<String, String> plot = (CategoryPlot) chart.getPlot();
        plot.setInsets(RectangleInsets.ZERO_INSETS);
        plot.setAxisOffset(RectangleInsets.ZERO_INSETS);
        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setFixedDimension(100.0);
        BufferedImage image = new BufferedImage(500, 300, 
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        ChartRenderingInfo info = new ChartRenderingInfo();
        chart.draw(g2, new Rectangle2D.Double(0, 0, 500, 300), info);
        g2.dispose();
        Rectangle2D rect = info.getPlotInfo().getDataArea();
        double x = rect.getMinX();
        assertEquals(100.0, x, 1.0);
    }

}
