/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * CategoryPlotTest.java
 * ---------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 15-Sep-2003 : Added a unit test to reproduce a bug in serialization (now
 *               fixed) (DG);
 * 05-Feb-2007 : Added testAddDomainMarker() and testAddRangeMarker() (DG);
 * 07-Feb-2007 : Added test1654215() (DG);
 * 07-Apr-2008 : Added testRemoveDomainMarker() and
 *               testRemoveRangeMarker() (DG);
 * 23-Apr-2008 : Extended testEquals() and testCloning(), and added
 *               testCloning2() and testCloning3() (DG);
 * 26-Jun-2008 : Updated testEquals() (DG);
 * 21-Jan-2009 : Updated testEquals() for new fields (DG);
 * 10-Jul-2009 : Updated testEquals() for new field (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.TestUtils;
import org.jfree.chart.annotations.CategoryLineAnnotation;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.DefaultCategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.chart.util.SortOrder;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tests for the {@link CategoryPlot} class.
 */
public class CategoryPlotTest {

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor() {
        CategoryPlot plot = new CategoryPlot();
        assertEquals(RectangleInsets.ZERO_INSETS, plot.getAxisOffset());
    }

    /**
     * A test for a bug reported in the forum.
     */
    @Test
    public void testAxisRange() {
        DefaultCategoryDataset datasetA = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetB = new DefaultCategoryDataset();
        datasetB.addValue(50.0, "R1", "C1");
        datasetB.addValue(80.0, "R1", "C1");
        CategoryPlot plot = new CategoryPlot(datasetA, new CategoryAxis(null),
                new NumberAxis(null), new LineAndShapeRenderer());
        plot.setDataset(1, datasetB);
        plot.setRenderer(1, new LineAndShapeRenderer());
        Range r = plot.getRangeAxis().getRange();
        assertEquals(84.0, r.getUpperBound(), 0.00001);
    }

    /**
     * Test that the equals() method differentiates all the required fields.
     */
    @Test
    public void testEquals() {
        CategoryPlot plot1 = new CategoryPlot();
        CategoryPlot plot2 = new CategoryPlot();
        assertTrue(plot1.equals(plot2));
        assertTrue(plot2.equals(plot1));

        // orientation...
        plot1.setOrientation(PlotOrientation.HORIZONTAL);
        assertFalse(plot1.equals(plot2));
        plot2.setOrientation(PlotOrientation.HORIZONTAL);
        assertTrue(plot1.equals(plot2));

        // axisOffset...
        plot1.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertFalse(plot1.equals(plot2));
        plot2.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertTrue(plot1.equals(plot2));

        // domainAxis - no longer a separate field but test anyway...
        plot1.setDomainAxis(new CategoryAxis("Category Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxis(new CategoryAxis("Category Axis"));
        assertTrue(plot1.equals(plot2));

        // domainAxes...
        plot1.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertTrue(plot1.equals(plot2));

        // domainAxisLocation - no longer a separate field but test anyway...
        plot1.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // domainAxisLocations...
        plot1.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // draw shared domain axis...
        plot1.setDrawSharedDomainAxis(!plot1.getDrawSharedDomainAxis());
        assertFalse(plot1.equals(plot2));
        plot2.setDrawSharedDomainAxis(!plot2.getDrawSharedDomainAxis());
        assertTrue(plot1.equals(plot2));

        // rangeAxis - no longer a separate field but test anyway...
        plot1.setRangeAxis(new NumberAxis("Range Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxis(new NumberAxis("Range Axis"));
        assertTrue(plot1.equals(plot2));

        // rangeAxes...
        plot1.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertTrue(plot1.equals(plot2));

        // rangeAxisLocation - no longer a separate field but test anyway...
        plot1.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // rangeAxisLocations...
        plot1.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // datasetToDomainAxisMap...
        plot1.mapDatasetToDomainAxis(11, 11);
        assertFalse(plot1.equals(plot2));
        plot2.mapDatasetToDomainAxis(11, 11);
        assertTrue(plot1.equals(plot2));

        // datasetToRangeAxisMap...
        plot1.mapDatasetToRangeAxis(11, 11);
        assertFalse(plot1.equals(plot2));
        plot2.mapDatasetToRangeAxis(11, 11);
        assertTrue(plot1.equals(plot2));

        // renderer - no longer a separate field but test anyway...
        plot1.setRenderer(new AreaRenderer());
        assertFalse(plot1.equals(plot2));
        plot2.setRenderer(new AreaRenderer());
        assertTrue(plot1.equals(plot2));

        // renderers...
        plot1.setRenderer(11, new AreaRenderer());
        assertFalse(plot1.equals(plot2));
        plot2.setRenderer(11, new AreaRenderer());
        assertTrue(plot1.equals(plot2));

        // rendering order...
        plot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertFalse(plot1.equals(plot2));
        plot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertTrue(plot1.equals(plot2));

        // columnRenderingOrder...
        plot1.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertFalse(plot1.equals(plot2));
        plot2.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertTrue(plot1.equals(plot2));

        // rowRenderingOrder...
        plot1.setRowRenderingOrder(SortOrder.DESCENDING);
        assertFalse(plot1.equals(plot2));
        plot2.setRowRenderingOrder(SortOrder.DESCENDING);
        assertTrue(plot1.equals(plot2));

        // domainGridlinesVisible
        plot1.setDomainGridlinesVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinesVisible(true);
        assertTrue(plot1.equals(plot2));

        // domainGridlinePosition
        plot1.setDomainGridlinePosition(CategoryAnchor.END);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinePosition(CategoryAnchor.END);
        assertTrue(plot1.equals(plot2));

        // domainGridlineStroke
        Stroke stroke = new BasicStroke(2.0f);
        plot1.setDomainGridlineStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlineStroke(stroke);
        assertTrue(plot1.equals(plot2));

        // domainGridlinePaint
        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        // rangeGridlinesVisible
        plot1.setRangeGridlinesVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinesVisible(false);
        assertTrue(plot1.equals(plot2));

        // rangeGridlineStroke
        plot1.setRangeGridlineStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlineStroke(stroke);
        assertTrue(plot1.equals(plot2));

        // rangeGridlinePaint
        plot1.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        // anchorValue
        plot1.setAnchorValue(100.0);
        assertFalse(plot1.equals(plot2));
        plot2.setAnchorValue(100.0);
        assertTrue(plot1.equals(plot2));

        // rangeCrosshairVisible
        plot1.setRangeCrosshairVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairVisible(true);
        assertTrue(plot1.equals(plot2));

        // rangeCrosshairValue
        plot1.setRangeCrosshairValue(100.0);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairValue(100.0);
        assertTrue(plot1.equals(plot2));

        // rangeCrosshairStroke
        plot1.setRangeCrosshairStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairStroke(stroke);
        assertTrue(plot1.equals(plot2));

        // rangeCrosshairPaint
        plot1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        // rangeCrosshairLockedOnData
        plot1.setRangeCrosshairLockedOnData(false);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairLockedOnData(false);
        assertTrue(plot1.equals(plot2));

        // foreground domain markers
        plot1.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertTrue(plot1.equals(plot2));

        // background domain markers
        plot1.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertTrue(plot1.equals(plot2));

        // range markers - no longer separate fields but test anyway...
        plot1.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertTrue(plot1.equals(plot2));

        plot1.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertTrue(plot1.equals(plot2));

        // foreground range markers...
        plot1.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertTrue(plot1.equals(plot2));

        // background range markers...
        plot1.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertTrue(plot1.equals(plot2));

        // annotations
        plot1.addAnnotation(new CategoryTextAnnotation("Text", "Category",
                43.0));
        assertFalse(plot1.equals(plot2));
        plot2.addAnnotation(new CategoryTextAnnotation("Text", "Category",
                43.0));
        assertTrue(plot1.equals(plot2));

        // weight
        plot1.setWeight(3);
        assertFalse(plot1.equals(plot2));
        plot2.setWeight(3);
        assertTrue(plot1.equals(plot2));

        // fixed domain axis space...
        plot1.setFixedDomainAxisSpace(new AxisSpace());
        assertFalse(plot1.equals(plot2));
        plot2.setFixedDomainAxisSpace(new AxisSpace());
        assertTrue(plot1.equals(plot2));

        // fixed range axis space...
        plot1.setFixedRangeAxisSpace(new AxisSpace());
        assertFalse(plot1.equals(plot2));
        plot2.setFixedRangeAxisSpace(new AxisSpace());
        assertTrue(plot1.equals(plot2));

        // fixed legend items
        plot1.setFixedLegendItems(new LegendItemCollection());
        assertFalse(plot1.equals(plot2));
        plot2.setFixedLegendItems(new LegendItemCollection());
        assertTrue(plot1.equals(plot2));

        // crosshairDatasetIndex
        plot1.setCrosshairDatasetIndex(99);
        assertFalse(plot1.equals(plot2));
        plot2.setCrosshairDatasetIndex(99);
        assertTrue(plot1.equals(plot2));

        // domainCrosshairColumnKey
        plot1.setDomainCrosshairColumnKey("A");
        assertFalse(plot1.equals(plot2));
        plot2.setDomainCrosshairColumnKey("A");
        assertTrue(plot1.equals(plot2));

        // domainCrosshairRowKey
        plot1.setDomainCrosshairRowKey("B");
        assertFalse(plot1.equals(plot2));
        plot2.setDomainCrosshairRowKey("B");
        assertTrue(plot1.equals(plot2));

        // domainCrosshairVisible
        plot1.setDomainCrosshairVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainCrosshairVisible(true);
        assertTrue(plot1.equals(plot2));

        // domainCrosshairPaint
        plot1.setDomainCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertTrue(plot1.equals(plot2));

        // domainCrosshairStroke
        plot1.setDomainCrosshairStroke(new BasicStroke(1.23f));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainCrosshairStroke(new BasicStroke(1.23f));
        assertTrue(plot1.equals(plot2));

        plot1.setRangeMinorGridlinesVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeMinorGridlinesVisible(true);
        assertTrue(plot1.equals(plot2));

        plot1.setRangeMinorGridlinePaint(new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeMinorGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertTrue(plot1.equals(plot2));

        plot1.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertTrue(plot1.equals(plot2));

        plot1.setRangeZeroBaselineVisible(!plot1.isRangeZeroBaselineVisible());
        assertFalse(plot1.equals(plot2));
        plot2.setRangeZeroBaselineVisible(!plot2.isRangeZeroBaselineVisible());
        assertTrue(plot1.equals(plot2));

        plot1.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertTrue(plot1.equals(plot2));

        plot1.setRangeZeroBaselineStroke(new BasicStroke(1.23f));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeZeroBaselineStroke(new BasicStroke(1.23f));
        assertTrue(plot1.equals(plot2));

        // shadowGenerator
        plot1.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertFalse(plot1.equals(plot2));
        plot2.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertTrue(plot1.equals(plot2));

        plot1.setShadowGenerator(null);
        assertFalse(plot1.equals(plot2));
        plot2.setShadowGenerator(null);
        assertTrue(plot1.equals(plot2));
    }

    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setDomainAxis(new CategoryAxis("A"));
        CategoryPlot p2 = new CategoryPlot();
        p2.setDomainAxis(new CategoryAxis("A"));
        assertEquals(p1, p2);
        p2.setDomainAxis(1, new CategoryAxis("B"));
        assertNotEquals(p1, p2);
    }
    
    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList2() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        CategoryPlot p2 = new CategoryPlot();
        p2.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(p1, p2);
        p2.setDomainAxisLocation(1, AxisLocation.TOP_OR_LEFT);
        assertNotEquals(p1, p2);
    }

    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList3() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setRangeAxis(new NumberAxis("A"));
        CategoryPlot p2 = new CategoryPlot();
        p2.setRangeAxis(new NumberAxis("A"));
        assertEquals(p1, p2);
        p2.setRangeAxis(1, new NumberAxis("B"));
        assertNotEquals(p1, p2);
    }
    
    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList4() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        CategoryPlot p2 = new CategoryPlot();
        p2.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(p1, p2);
        p2.setRangeAxisLocation(1, AxisLocation.TOP_OR_LEFT);
        assertNotEquals(p1, p2);
    }

    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList5() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setRenderer(new BarRenderer());
        CategoryPlot p2 = new CategoryPlot();
        p2.setRenderer(new BarRenderer());
        assertEquals(p1, p2);
        p2.setRenderer(1, new LineAndShapeRenderer());
        assertNotEquals(p1, p2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        p1.setRangeMinorGridlinePaint(new GradientPaint(2.0f, 3.0f, Color.WHITE,
                4.0f, 5.0f, Color.RED));
        p1.setRangeZeroBaselinePaint(new GradientPaint(3.0f, 4.0f, Color.RED,
                5.0f, 6.0f, Color.WHITE));
        CategoryPlot p2;
        try {
            p2 = (CategoryPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        // check independence
        p1.addAnnotation(new CategoryLineAnnotation("C1", 1.0, "C2", 2.0,
                Color.RED, new BasicStroke(1.0f)));
        assertFalse(p1.equals(p2));
        p2.addAnnotation(new CategoryLineAnnotation("C1", 1.0, "C2", 2.0,
                Color.RED, new BasicStroke(1.0f)));
        assertTrue(p1.equals(p2));

        p1.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertFalse(p1.equals(p2));
        p2.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertTrue(p1.equals(p2));

        p1.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertFalse(p1.equals(p2));
        p2.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertTrue(p1.equals(p2));

        p1.addRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
        assertFalse(p1.equals(p2));
        p2.addRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
        assertTrue(p1.equals(p2));

        p1.addRangeMarker(new ValueMarker(2.0), Layer.BACKGROUND);
        assertFalse(p1.equals(p2));
        p2.addRangeMarker(new ValueMarker(2.0), Layer.BACKGROUND);
        assertTrue(p1.equals(p2));
    }

    /**
     * Some more cloning checks.
     */
    @Test
    public void testCloning2() {
        AxisSpace da1 = new AxisSpace();
        AxisSpace ra1 = new AxisSpace();
        CategoryPlot p1 = new CategoryPlot();
        p1.setFixedDomainAxisSpace(da1);
        p1.setFixedRangeAxisSpace(ra1);
        CategoryPlot p2;
        try {
            p2 = (CategoryPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        da1.setBottom(99.0);
        assertFalse(p1.equals(p2));
        p2.getFixedDomainAxisSpace().setBottom(99.0);
        assertTrue(p1.equals(p2));

        ra1.setBottom(11.0);
        assertFalse(p1.equals(p2));
        p2.getFixedRangeAxisSpace().setBottom(11.0);
        assertTrue(p1.equals(p2));
    }

    /**
     * Some more cloning checks.
     */
    @Test
    public void testCloning3() {
        LegendItemCollection c1 = new LegendItemCollection();
        CategoryPlot p1 = new CategoryPlot();
        p1.setFixedLegendItems(c1);
        CategoryPlot p2 = null;
        try {
            p2 = (CategoryPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        c1.add(new LegendItem("X", "XX", "tt", "url", true,
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true, Color.RED,
                true, Color.YELLOW, new BasicStroke(1.0f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(1.0f),
                Color.GREEN));
        assertFalse(p1.equals(p2));
        p2.getFixedLegendItems().add(new LegendItem("X", "XX", "tt", "url",
                true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true,
                Color.RED, true, Color.YELLOW, new BasicStroke(1.0f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(1.0f),
                Color.GREEN));
        assertTrue(p1.equals(p2));
    }

    /**
     * Renderers that belong to the plot are being cloned but they are
     * retaining a reference to the original plot.
     */
    @Test
    public void testBug2817504() {
        CategoryPlot p1 = new CategoryPlot();
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        p1.setRenderer(r1);
        CategoryPlot p2;
        try {
            p2 = (CategoryPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        // check for independence
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) p2.getRenderer();
        assertTrue(r2.getPlot() == p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(dataset, domainAxis, rangeAxis,
                renderer);
        p1.setOrientation(PlotOrientation.HORIZONTAL);
        CategoryPlot p2 = (CategoryPlot) TestUtils.serialised(p1);
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(data, domainAxis, rangeAxis,
                renderer);
        p1.setOrientation(PlotOrientation.VERTICAL);
        CategoryPlot p2 = (CategoryPlot) TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization3() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Test Chart", "Category Axis", "Value Axis", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        JFreeChart chart2 = (JFreeChart) TestUtils.serialised(chart);

        // now check that the chart is usable...
        try {
            chart2.createBufferedImage(300, 200);
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }

    /**
     * This test ensures that a plot with markers is serialized correctly.
     */
    @Test
    public void testSerialization4() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
                "Test Chart", "Category Axis", "Value Axis",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.addRangeMarker(new ValueMarker(1.1), Layer.FOREGROUND);
        plot.addRangeMarker(new IntervalMarker(2.2, 3.3), Layer.BACKGROUND);
        JFreeChart chart2 = (JFreeChart) TestUtils.serialised(chart);
        assertEquals(chart, chart2);

        // now check that the chart is usable...
        try {
            chart2.createBufferedImage(300, 200);
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }

    /**
     * Tests a bug where the plot is no longer registered as a listener
     * with the dataset(s) and axes after deserialization.  See patch 1209475
     * at SourceForge.
     */
    @Test
    public void testSerialization5() {
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        CategoryAxis domainAxis1 = new CategoryAxis("Domain 1");
        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        BarRenderer renderer1 = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(dataset1, domainAxis1, rangeAxis1,
                renderer1);
        CategoryAxis domainAxis2 = new CategoryAxis("Domain 2");
        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
        BarRenderer renderer2 = new BarRenderer();
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        p1.setDataset(1, dataset2);
        p1.setDomainAxis(1, domainAxis2);
        p1.setRangeAxis(1, rangeAxis2);
        p1.setRenderer(1, renderer2);
        CategoryPlot p2 = (CategoryPlot) TestUtils.serialised(p1);
        assertEquals(p1, p2);

        // now check that all datasets, renderers and axes are being listened
        // too...
        CategoryAxis domainAxisA = p2.getDomainAxis(0);
        NumberAxis rangeAxisA = (NumberAxis) p2.getRangeAxis(0);
        DefaultCategoryDataset datasetA
                = (DefaultCategoryDataset) p2.getDataset(0);
        BarRenderer rendererA = (BarRenderer) p2.getRenderer(0);
        CategoryAxis domainAxisB = p2.getDomainAxis(1);
        NumberAxis rangeAxisB = (NumberAxis) p2.getRangeAxis(1);
        DefaultCategoryDataset datasetB
                = (DefaultCategoryDataset) p2.getDataset(1);
        BarRenderer rendererB  = (BarRenderer) p2.getRenderer(1);
        assertTrue(datasetA.hasListener(p2));
        assertTrue(domainAxisA.hasListener(p2));
        assertTrue(rangeAxisA.hasListener(p2));
        assertTrue(rendererA.hasListener(p2));
        assertTrue(datasetB.hasListener(p2));
        assertTrue(domainAxisB.hasListener(p2));
        assertTrue(rangeAxisB.hasListener(p2));
        assertTrue(rendererB.hasListener(p2));
    }

    /**
     * A test for a bug where setting the renderer doesn't register the plot
     * as a RendererChangeListener.
     */
    @Test
    public void testSetRenderer() {
        CategoryPlot plot = new CategoryPlot();
        CategoryItemRenderer renderer = new LineAndShapeRenderer();
        plot.setRenderer(renderer);
        // now make a change to the renderer and see if it triggers a plot
        // change event...
        MyPlotChangeListener listener = new MyPlotChangeListener();
        plot.addChangeListener(listener);
        renderer.setSeriesPaint(0, Color.BLACK);
        assertTrue(listener.getEvent() != null);
    }

    /**
     * A test for bug report 1169972.
     */
    @Test
    public void test1169972() {
        CategoryPlot plot = new CategoryPlot(null, null, null, null);
        plot.setDomainAxis(new CategoryAxis("C"));
        plot.setRangeAxis(new NumberAxis("Y"));
        plot.setRenderer(new BarRenderer());
        plot.setDataset(new DefaultCategoryDataset());
        assertTrue(true); // we didn't get an exception so all is good
    }

    /**
     * Some tests for the addDomainMarker() method(s).
     */
    @Test
    public void testAddDomainMarker() {
        CategoryPlot plot = new CategoryPlot();
        CategoryMarker m = new CategoryMarker("C1");
        plot.addDomainMarker(m);
        List<EventListener> listeners = Arrays.asList(m.getListeners(
                MarkerChangeListener.class));
        assertTrue(listeners.contains(plot));
        plot.clearDomainMarkers();
        listeners = Arrays.asList(m.getListeners(MarkerChangeListener.class));
        assertFalse(listeners.contains(plot));
    }

    /**
     * Some tests for the addRangeMarker() method(s).
     */
    @Test
    public void testAddRangeMarker() {
        CategoryPlot plot = new CategoryPlot();
        Marker m = new ValueMarker(1.0);
        plot.addRangeMarker(m);
        List<EventListener> listeners = Arrays.asList(m.getListeners(
                MarkerChangeListener.class));
        assertTrue(listeners.contains(plot));
        plot.clearRangeMarkers();
        listeners = Arrays.asList(m.getListeners(MarkerChangeListener.class));
        assertFalse(listeners.contains(plot));
    }

    /**
     * A test for bug 1654215 (where a renderer is added to the plot without
     * a corresponding dataset and it throws an exception at drawing time).
     */
    @Test
    public void test1654215() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRenderer(1, new LineAndShapeRenderer());
        try {
            BufferedImage image = new BufferedImage(200 , 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }

    /**
     * Some checks for the getDomainAxisIndex() method.
     */
    @Test
    public void testGetDomainAxisIndex() {
        CategoryAxis domainAxis1 = new CategoryAxis("X1");
        CategoryAxis domainAxis2 = new CategoryAxis("X2");
        NumberAxis rangeAxis1 = new NumberAxis("Y1");
        CategoryPlot plot = new CategoryPlot(null, domainAxis1, rangeAxis1,
                null);
        assertEquals(0, plot.getDomainAxisIndex(domainAxis1));
        assertEquals(-1, plot.getDomainAxisIndex(domainAxis2));
        plot.setDomainAxis(1, domainAxis2);
        assertEquals(1, plot.getDomainAxisIndex(domainAxis2));
        assertEquals(-1, plot.getDomainAxisIndex(new CategoryAxis("X2")));
        boolean pass = false;
        try {
            plot.getDomainAxisIndex(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getRangeAxisIndex() method.
     */
    @Test
    public void testGetRangeAxisIndex() {
        CategoryAxis domainAxis1 = new CategoryAxis("X1");
        NumberAxis rangeAxis1 = new NumberAxis("Y1");
        NumberAxis rangeAxis2 = new NumberAxis("Y2");
        CategoryPlot plot = new CategoryPlot(null, domainAxis1, rangeAxis1,
                null);
        assertEquals(0, plot.getRangeAxisIndex(rangeAxis1));
        assertEquals(-1, plot.getRangeAxisIndex(rangeAxis2));
        plot.setRangeAxis(1, rangeAxis2);
        assertEquals(1, plot.getRangeAxisIndex(rangeAxis2));
        assertEquals(-1, plot.getRangeAxisIndex(new NumberAxis("Y2")));
        boolean pass = false;
        try {
            plot.getRangeAxisIndex(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Check that removing a marker that isn't assigned to the plot returns
     * false.
     */
    @Test
    public void testRemoveDomainMarker() {
        CategoryPlot plot = new CategoryPlot();
        assertFalse(plot.removeDomainMarker(new CategoryMarker("Category 1")));
    }

    /**
     * Check that removing a marker that isn't assigned to the plot returns
     * false.
     */
    @Test
    public void testRemoveRangeMarker() {
        CategoryPlot plot = new CategoryPlot();
        assertFalse(plot.removeRangeMarker(new ValueMarker(0.5)));
    }

    /**
     * Some tests for the getDomainAxisForDataset() method.
     */
    @Test
    public void testGetDomainAxisForDataset() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        assertEquals(xAxis, plot.getDomainAxisForDataset(0));

        // should get IllegalArgumentException for negative index
        boolean pass = false;
        try {
            plot.getDomainAxisForDataset(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // if multiple axes are mapped, the first in the list should be
        // returned...
        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(1, xAxis2);
        assertEquals(xAxis, plot.getDomainAxisForDataset(0));

        plot.mapDatasetToDomainAxis(0, 1);
        assertEquals(xAxis2, plot.getDomainAxisForDataset(0));

        List<Integer> axisIndices = Arrays.asList(new Integer[] {0, 1});
        plot.mapDatasetToDomainAxes(0, axisIndices);
        assertEquals(xAxis, plot.getDomainAxisForDataset(0));

        axisIndices = Arrays.asList(new Integer[] {1, 2});
        plot.mapDatasetToDomainAxes(0, axisIndices);
        assertEquals(xAxis2, plot.getDomainAxisForDataset(0));
    }

    /**
     * Some tests for the getRangeAxisForDataset() method.
     */
    @Test
    public void testGetRangeAxisForDataset() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new DefaultCategoryItemRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        assertEquals(yAxis, plot.getRangeAxisForDataset(0));

        // should get IllegalArgumentException for negative index
        boolean pass = false;
        try {
            plot.getRangeAxisForDataset(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // if multiple axes are mapped, the first in the list should be
        // returned...
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(1, yAxis2);
        assertEquals(yAxis, plot.getRangeAxisForDataset(0));

        plot.mapDatasetToRangeAxis(0, 1);
        assertEquals(yAxis2, plot.getRangeAxisForDataset(0));

        List<Integer> axisIndices = Arrays.asList(new Integer[] {0, 1});
        plot.mapDatasetToRangeAxes(0, axisIndices);
        assertEquals(yAxis, plot.getRangeAxisForDataset(0));

        axisIndices = Arrays.asList(new Integer[] {1, 2});
        plot.mapDatasetToRangeAxes(0, axisIndices);
        assertEquals(yAxis2, plot.getRangeAxisForDataset(0));
    }
    
    /**
     * Datasets are now stored in a Map, and it should be possible to assign
     * them an arbitrary key (index).
     */
    @Test
    public void testDatasetIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        
        assertEquals(dataset, plot.getDataset(0));
        
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        
        // we should be able to give a dataset an arbitrary index
        plot.setDataset(99, dataset2);
        assertEquals(2, plot.getDatasetCount());
        assertEquals(dataset2, plot.getDataset(99));
        
        assertEquals(0, plot.indexOf(dataset));
        assertEquals(99, plot.indexOf(dataset2));
    }
    
    @Test
    public void testAxisIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        assertEquals(xAxis, plot.getDomainAxis(0));        
        assertEquals(yAxis, plot.getRangeAxis(0)); 
        
        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(99, xAxis2);
        assertEquals(xAxis2, plot.getDomainAxis(99));
        
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(99, yAxis2);
        assertEquals(yAxis2, plot.getRangeAxis(99));
    }
    
    @Test 
    public void testAxisLocationIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        CategoryAxis xAxis2 = new CategoryAxis("X2");
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setDomainAxis(99, xAxis2);
        plot.setRangeAxis(99, yAxis2);
        
        plot.setDomainAxisLocation(99, AxisLocation.BOTTOM_OR_RIGHT);
        assertEquals(AxisLocation.BOTTOM_OR_RIGHT, 
                plot.getDomainAxisLocation(99));
        plot.setRangeAxisLocation(99, AxisLocation.BOTTOM_OR_LEFT);
        assertEquals(AxisLocation.BOTTOM_OR_LEFT, 
                plot.getRangeAxisLocation(99));
    }
    
    @Test 
    public void testRendererIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        
        assertEquals(renderer, plot.getRenderer(0));
        
        // we should be able to give a renderer an arbitrary index
        CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(20, renderer2);
        assertEquals(2, plot.getRendererCount());
        assertEquals(renderer2, plot.getRenderer(20));
        
        assertEquals(20, plot.getIndexOf(renderer2));
    }

    @Test 
    public void testGetRendererForDataset2() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        // add a second dataset
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset2);
       
        // by default, the renderer with index 0 is used
        assertEquals(renderer, plot.getRendererForDataset(dataset2));
        
        // add a second renderer with the same index as dataset2, now it will
        // be used
        CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(99, renderer2);
        assertEquals(renderer2, plot.getRendererForDataset(dataset2));
    }
    
    @Test
    public void testMapDatasetToDomainAxis() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(11, xAxis2);
        
        // add a second dataset
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        
        assertEquals(xAxis, plot.getDomainAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToDomainAxis(99, 11);
        assertEquals(xAxis2, plot.getDomainAxisForDataset(99));
    }

    @Test
    public void testMapDatasetToRangeAxis() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(22, yAxis2);
        
        // add a second dataset
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        
        assertEquals(yAxis, plot.getRangeAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToRangeAxis(99, 22);
        assertEquals(yAxis2, plot.getRangeAxisForDataset(99));
    }
    
    @Test
    public void testDomainMarkerIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        
        // add a second dataset, plotted against a second x axis
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(1, xAxis2);
        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(99, renderer2);
        plot.mapDatasetToDomainAxis(99, 1);
        
        CategoryMarker xMarker1 = new CategoryMarker(123);
        plot.addDomainMarker(99, xMarker1, Layer.FOREGROUND);
        assertTrue(plot.getDomainMarkers(99, Layer.FOREGROUND).contains(
                xMarker1));
    }

    @Test
    public void testRangeMarkerIndices() {
        CategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
        
        // add a second dataset, plotted against a second axis
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(1, yAxis2);
        LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        plot.setRenderer(99, renderer2);
        plot.mapDatasetToRangeAxis(99, 1);
        
        ValueMarker yMarker1 = new ValueMarker(123);
        plot.addRangeMarker(99, yMarker1, Layer.FOREGROUND);
        assertTrue(plot.getRangeMarkers(99, Layer.FOREGROUND).contains(
                yMarker1));
    }

}
