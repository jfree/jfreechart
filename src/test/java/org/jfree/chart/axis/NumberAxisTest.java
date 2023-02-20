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
 * -------------------
 * NumberAxisTest.java
 * -------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.RangeType;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link NumberAxis} class.
 */
public class NumberAxisTest {

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        NumberAxis a1 = new NumberAxis("Test");
        NumberAxis a2 = CloneUtils.clone(a1);
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

        //private boolean autoRangeIncludesZero;
        a1.setAutoRangeIncludesZero(false);
        assertNotEquals(a1, a2);
        a2.setAutoRangeIncludesZero(false);
        assertEquals(a1, a2);

        //private boolean autoRangeStickyZero;
        a1.setAutoRangeStickyZero(false);
        assertNotEquals(a1, a2);
        a2.setAutoRangeStickyZero(false);
        assertEquals(a1, a2);

        //private NumberTickUnit tickUnit;
        a1.setTickUnit(new NumberTickUnit(25.0));
        assertNotEquals(a1, a2);
        a2.setTickUnit(new NumberTickUnit(25.0));
        assertEquals(a1, a2);

        //private NumberFormat numberFormatOverride;
        a1.setNumberFormatOverride(new DecimalFormat("0.00"));
        assertNotEquals(a1, a2);
        a2.setNumberFormatOverride(new DecimalFormat("0.00"));
        assertEquals(a1, a2);

        a1.setRangeType(RangeType.POSITIVE);
        assertNotEquals(a1, a2);
        a2.setRangeType(RangeType.POSITIVE);
        assertEquals(a1, a2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        NumberAxis a1 = new NumberAxis("Test");
        NumberAxis a2 = new NumberAxis("Test");
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    private static final double EPSILON = 0.0000001;

    /**
     * Test the translation of Java2D values to data values.
     */
    @Test
    public void testTranslateJava2DToValue() {
        NumberAxis axis = new NumberAxis();
        axis.setRange(50.0, 100.0);
        Rectangle2D dataArea = new Rectangle2D.Double(10.0, 50.0, 400.0, 300.0);
        double y1 = axis.java2DToValue(75.0, dataArea, RectangleEdge.LEFT);
        assertEquals(y1, 95.8333333, EPSILON);
        double y2 = axis.java2DToValue(75.0, dataArea, RectangleEdge.RIGHT);
        assertEquals(y2, 95.8333333, EPSILON);
        double x1 = axis.java2DToValue(75.0, dataArea, RectangleEdge.TOP);
        assertEquals(x1, 58.125, EPSILON);
        double x2 = axis.java2DToValue(75.0, dataArea, RectangleEdge.BOTTOM);
        assertEquals(x2, 58.125, EPSILON);
        axis.setInverted(true);
        double y3 = axis.java2DToValue(75.0, dataArea, RectangleEdge.LEFT);
        assertEquals(y3, 54.1666667, EPSILON);
        double y4 = axis.java2DToValue(75.0, dataArea, RectangleEdge.RIGHT);
        assertEquals(y4, 54.1666667, EPSILON);
        double x3 = axis.java2DToValue(75.0, dataArea, RectangleEdge.TOP);
        assertEquals(x3, 91.875, EPSILON);
        double x4 = axis.java2DToValue(75.0, dataArea, RectangleEdge.BOTTOM);
        assertEquals(x4, 91.875, EPSILON);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        NumberAxis a1 = new NumberAxis("Test Axis");
        NumberAxis a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

    /**
     * A simple test for the auto-range calculation looking at a
     * NumberAxis used as the range axis for a CategoryPlot.
     */
    @Test
    public void testAutoRange1() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        dataset.setValue(100.0, "Row 1", "Column 1");
        dataset.setValue(200.0, "Row 1", "Column 2");
        JFreeChart chart = ChartFactory.createBarChart("Test", "Categories",
                "Value", dataset);
        CategoryPlot<?, ?> plot = (CategoryPlot) chart.getPlot();
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        assertEquals(axis.getLowerBound(), 0.0, EPSILON);
        assertEquals(axis.getUpperBound(), 210.0, EPSILON);
    }

    /**
     * A simple test for the auto-range calculation looking at a
     * NumberAxis used as the range axis for a CategoryPlot.  In this
     * case, the 'autoRangeIncludesZero' flag is set to false.
     */
    @Test
    public void testAutoRange2() {
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        dataset.setValue(100.0, "Row 1", "Column 1");
        dataset.setValue(200.0, "Row 1", "Column 2");
        JFreeChart chart = ChartFactory.createLineChart("Test", "Categories",
                "Value", dataset, PlotOrientation.VERTICAL, false, false,
                false);
        CategoryPlot<?, ?> plot = (CategoryPlot) chart.getPlot();
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setAutoRangeIncludesZero(false);
        assertEquals(axis.getLowerBound(), 95.0, EPSILON);
        assertEquals(axis.getUpperBound(), 205.0, EPSILON);
    }

    /**
     * A simple test for the auto-range calculation looking at a
     * NumberAxis used as the range axis for a CategoryPlot.  In this
     * case, the 'autoRangeIncludesZero' flag is set to false AND the
     * original dataset is replaced with a new dataset.
     */
    @Test
    public void testAutoRange3() {
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        dataset.setValue(100.0, "Row 1", "Column 1");
        dataset.setValue(200.0, "Row 1", "Column 2");
        JFreeChart chart = ChartFactory.createLineChart("Test", "Categories",
                "Value", dataset, PlotOrientation.VERTICAL, false, false,
                false);
        @SuppressWarnings("unchecked")
        CategoryPlot<String, String> plot = (CategoryPlot) chart.getPlot();
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setAutoRangeIncludesZero(false);
        assertEquals(axis.getLowerBound(), 95.0, EPSILON);
        assertEquals(axis.getUpperBound(), 205.0, EPSILON);

        // now replacing the dataset should update the axis range...
        DefaultCategoryDataset<String,String> dataset2 = new DefaultCategoryDataset<>();
        dataset2.setValue(900.0, "Row 1", "Column 1");
        dataset2.setValue(1000.0, "Row 1", "Column 2");
        plot.setDataset(dataset2);
        assertEquals(axis.getLowerBound(), 895.0, EPSILON);
        assertEquals(axis.getUpperBound(), 1005.0, EPSILON);
    }

    /**
     * A check for the interaction between the 'autoRangeIncludesZero' flag
     * and the base setting in the BarRenderer.
     */
    @Test
    public void testAutoRange4() {
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        dataset.setValue(100.0, "Row 1", "Column 1");
        dataset.setValue(200.0, "Row 1", "Column 2");
        JFreeChart chart = ChartFactory.createBarChart("Test", "Categories",
                "Value", dataset, PlotOrientation.VERTICAL, false, false,
                false);
        @SuppressWarnings("unchecked")
        CategoryPlot<String, String> plot = (CategoryPlot) chart.getPlot(); 
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setAutoRangeIncludesZero(false);
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setIncludeBaseInRange(false);
        assertEquals(95.0, axis.getLowerBound(), EPSILON);
        assertEquals(205.0, axis.getUpperBound(), EPSILON);

        br.setIncludeBaseInRange(true);
        assertEquals(0.0, axis.getLowerBound(), EPSILON);
        assertEquals(210.0, axis.getUpperBound(), EPSILON);

        axis.setAutoRangeIncludesZero(true);
        assertEquals(0.0, axis.getLowerBound(), EPSILON);
        assertEquals(210.0, axis.getUpperBound(), EPSILON);

        br.setIncludeBaseInRange(true);
        assertEquals(0.0, axis.getLowerBound(), EPSILON);
        assertEquals(210.0, axis.getUpperBound(), EPSILON);

        // now replacing the dataset should update the axis range...
        DefaultCategoryDataset<String,String> dataset2 = new DefaultCategoryDataset<>();
        dataset2.setValue(900.0, "Row 1", "Column 1");
        dataset2.setValue(1000.0, "Row 1", "Column 2");
        plot.setDataset(dataset2);
        assertEquals(0.0, axis.getLowerBound(), EPSILON);
        assertEquals(1050.0, axis.getUpperBound(), EPSILON);

        br.setIncludeBaseInRange(false);
        assertEquals(0.0, axis.getLowerBound(), EPSILON);
        assertEquals(1050.0, axis.getUpperBound(), EPSILON);

        axis.setAutoRangeIncludesZero(false);
        assertEquals(895.0, axis.getLowerBound(), EPSILON);
        assertEquals(1005.0, axis.getUpperBound(), EPSILON);
    }

    /**
     * Checks that the auto-range for the domain axis on an XYPlot is
     * working as expected.
     */
    @Test
    public void testXYAutoRange1() {
        XYSeries<String> series = new XYSeries<>("Series 1");
        series.add(1.0, 1.0);
        series.add(2.0, 2.0);
        series.add(3.0, 3.0);
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createScatterPlot("Test", "X", "Y",
                dataset);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        NumberAxis axis = (NumberAxis) plot.getDomainAxis();
        axis.setAutoRangeIncludesZero(false);
        assertEquals(0.9, axis.getLowerBound(), EPSILON);
        assertEquals(3.1, axis.getUpperBound(), EPSILON);
    }

    /**
     * Checks that the auto-range for the range axis on an XYPlot is
     * working as expected.
     */
    @Test
    public void testXYAutoRange2() {
        XYSeries<String> series = new XYSeries<>("Series 1");
        series.add(1.0, 1.0);
        series.add(2.0, 2.0);
        series.add(3.0, 3.0);
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createScatterPlot("Test", "X", "Y",
                dataset);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        NumberAxis axis = (NumberAxis) plot.getRangeAxis();
        axis.setAutoRangeIncludesZero(false);
        assertEquals(0.9, axis.getLowerBound(), EPSILON);
        assertEquals(3.1, axis.getUpperBound(), EPSILON);
    }

//    /**
//     * Some checks for the setRangeType() method.
//     */
//    public void testSetRangeType() {
//
//        NumberAxis axis = new NumberAxis("X");
//        axis.setRangeType(RangeType.POSITIVE);
//        assertEquals(RangeType.POSITIVE, axis.getRangeType());
//
//        // test a change to RangeType.POSITIVE
//        axis.setRangeType(RangeType.FULL);
//        axis.setRange(-5.0, 5.0);
//        axis.setRangeType(RangeType.POSITIVE);
//        assertEquals(new Range(0.0, 5.0), axis.getRange());
//
//        axis.setRangeType(RangeType.FULL);
//        axis.setRange(-10.0, -5.0);
//        axis.setRangeType(RangeType.POSITIVE);
//        assertEquals(new Range(0.0, axis.getAutoRangeMinimumSize()),
//                axis.getRange());
//
//        // test a change to RangeType.NEGATIVE
//        axis.setRangeType(RangeType.FULL);
//        axis.setRange(-5.0, 5.0);
//        axis.setRangeType(RangeType.NEGATIVE);
//        assertEquals(new Range(-5.0, 0.0), axis.getRange());
//
//        axis.setRangeType(RangeType.FULL);
//        axis.setRange(5.0, 10.0);
//        axis.setRangeType(RangeType.NEGATIVE);
//        assertEquals(new Range(-axis.getAutoRangeMinimumSize(), 0.0),
//                axis.getRange());
//
//        // try null
//        boolean pass = false;
//        try {
//            axis.setRangeType(null);
//        }
//        catch (IllegalArgumentException e) {
//            pass = true;
//        }
//        assertTrue(pass);
//    }

    /**
     * Some checks for the setLowerBound() method.
     */
    @Test
    public void testSetLowerBound() {
        NumberAxis axis = new NumberAxis("X");
        axis.setRange(0.0, 10.0);
        axis.setLowerBound(5.0);
        assertEquals(5.0, axis.getLowerBound(), EPSILON);
        axis.setLowerBound(10.0);
        assertEquals(10.0, axis.getLowerBound(), EPSILON);
        assertEquals(11.0, axis.getUpperBound(), EPSILON);

        //axis.setRangeType(RangeType.POSITIVE);
        //axis.setLowerBound(-5.0);
        //assertEquals(0.0, axis.getLowerBound(), EPSILON);
    }

}
