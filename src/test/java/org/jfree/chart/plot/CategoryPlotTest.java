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
 * ---------------------
 * CategoryPlotTest.java
 * ---------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

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
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
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
import org.jfree.chart.api.Layer;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.chart.api.SortOrder;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryPlot} class.
 */
public class CategoryPlotTest {

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor() {
        CategoryPlot<String, String> plot = new CategoryPlot<>();
        assertEquals(RectangleInsets.ZERO_INSETS, plot.getAxisOffset());
    }

    /**
     * A test for a bug reported in the forum.
     */
    @Test
    public void testAxisRange() {
        DefaultCategoryDataset<String, String> datasetA = new DefaultCategoryDataset<>();
        DefaultCategoryDataset<String, String> datasetB = new DefaultCategoryDataset<>();
        datasetB.addValue(50.0, "R1", "C1");
        datasetB.addValue(80.0, "R1", "C1");
        CategoryPlot<String, String> plot = new CategoryPlot<>(datasetA, 
                new CategoryAxis(null), new NumberAxis(null), 
                new LineAndShapeRenderer());
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
        CategoryPlot<String, String> plot1 = new CategoryPlot<>();
        CategoryPlot<String, String> plot2 = new CategoryPlot<>();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        // orientation...
        plot1.setOrientation(PlotOrientation.HORIZONTAL);
        assertNotEquals(plot1, plot2);
        plot2.setOrientation(PlotOrientation.HORIZONTAL);
        assertEquals(plot1, plot2);

        // axisOffset...
        plot1.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertNotEquals(plot1, plot2);
        plot2.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertEquals(plot1, plot2);

        // domainAxis - no longer a separate field but test anyway...
        plot1.setDomainAxis(new CategoryAxis("Category Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxis(new CategoryAxis("Category Axis"));
        assertEquals(plot1, plot2);

        // domainAxes...
        plot1.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertEquals(plot1, plot2);

        // domainAxisLocation - no longer a separate field but test anyway...
        plot1.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // domainAxisLocations...
        plot1.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // draw shared domain axis...
        plot1.setDrawSharedDomainAxis(!plot1.getDrawSharedDomainAxis());
        assertNotEquals(plot1, plot2);
        plot2.setDrawSharedDomainAxis(!plot2.getDrawSharedDomainAxis());
        assertEquals(plot1, plot2);

        // rangeAxis - no longer a separate field but test anyway...
        plot1.setRangeAxis(new NumberAxis("Range Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxis(new NumberAxis("Range Axis"));
        assertEquals(plot1, plot2);

        // rangeAxes...
        plot1.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertEquals(plot1, plot2);

        // rangeAxisLocation - no longer a separate field but test anyway...
        plot1.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // rangeAxisLocations...
        plot1.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // datasetToDomainAxisMap...
        plot1.mapDatasetToDomainAxis(11, 11);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToDomainAxis(11, 11);
        assertEquals(plot1, plot2);

        // datasetToRangeAxisMap...
        plot1.mapDatasetToRangeAxis(11, 11);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToRangeAxis(11, 11);
        assertEquals(plot1, plot2);

        // renderer - no longer a separate field but test anyway...
        plot1.setRenderer(new AreaRenderer());
        assertNotEquals(plot1, plot2);
        plot2.setRenderer(new AreaRenderer());
        assertEquals(plot1, plot2);

        // renderers...
        plot1.setRenderer(11, new AreaRenderer());
        assertNotEquals(plot1, plot2);
        plot2.setRenderer(11, new AreaRenderer());
        assertEquals(plot1, plot2);

        // rendering order...
        plot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertNotEquals(plot1, plot2);
        plot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertEquals(plot1, plot2);

        // columnRenderingOrder...
        plot1.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertNotEquals(plot1, plot2);
        plot2.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertEquals(plot1, plot2);

        // rowRenderingOrder...
        plot1.setRowRenderingOrder(SortOrder.DESCENDING);
        assertNotEquals(plot1, plot2);
        plot2.setRowRenderingOrder(SortOrder.DESCENDING);
        assertEquals(plot1, plot2);

        // domainGridlinesVisible
        plot1.setDomainGridlinesVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinesVisible(true);
        assertEquals(plot1, plot2);

        // domainGridlinePosition
        plot1.setDomainGridlinePosition(CategoryAnchor.END);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinePosition(CategoryAnchor.END);
        assertEquals(plot1, plot2);

        // domainGridlineStroke
        Stroke stroke = new BasicStroke(2.0f);
        plot1.setDomainGridlineStroke(stroke);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlineStroke(stroke);
        assertEquals(plot1, plot2);

        // domainGridlinePaint
        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        // rangeGridlinesVisible
        plot1.setRangeGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlinesVisible(false);
        assertEquals(plot1, plot2);

        // rangeGridlineStroke
        plot1.setRangeGridlineStroke(stroke);
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlineStroke(stroke);
        assertEquals(plot1, plot2);

        // rangeGridlinePaint
        plot1.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        // anchorValue
        plot1.setAnchorValue(100.0);
        assertNotEquals(plot1, plot2);
        plot2.setAnchorValue(100.0);
        assertEquals(plot1, plot2);

        // rangeCrosshairVisible
        plot1.setRangeCrosshairVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairVisible(true);
        assertEquals(plot1, plot2);

        // rangeCrosshairValue
        plot1.setRangeCrosshairValue(100.0);
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairValue(100.0);
        assertEquals(plot1, plot2);

        // rangeCrosshairStroke
        plot1.setRangeCrosshairStroke(stroke);
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairStroke(stroke);
        assertEquals(plot1, plot2);

        // rangeCrosshairPaint
        plot1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        // rangeCrosshairLockedOnData
        plot1.setRangeCrosshairLockedOnData(false);
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairLockedOnData(false);
        assertEquals(plot1, plot2);

        // foreground domain markers
        plot1.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertEquals(plot1, plot2);

        // background domain markers
        plot1.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertEquals(plot1, plot2);

        // range markers - no longer separate fields but test anyway...
        plot1.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertEquals(plot1, plot2);

        plot1.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertEquals(plot1, plot2);

        // foreground range markers...
        plot1.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertEquals(plot1, plot2);

        // background range markers...
        plot1.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertEquals(plot1, plot2);

        // annotations
        plot1.addAnnotation(new CategoryTextAnnotation("Text", "Category",
                43.0));
        assertNotEquals(plot1, plot2);
        plot2.addAnnotation(new CategoryTextAnnotation("Text", "Category",
                43.0));
        assertEquals(plot1, plot2);

        // weight
        plot1.setWeight(3);
        assertNotEquals(plot1, plot2);
        plot2.setWeight(3);
        assertEquals(plot1, plot2);

        // fixed domain axis space...
        plot1.setFixedDomainAxisSpace(new AxisSpace());
        assertNotEquals(plot1, plot2);
        plot2.setFixedDomainAxisSpace(new AxisSpace());
        assertEquals(plot1, plot2);

        // fixed range axis space...
        plot1.setFixedRangeAxisSpace(new AxisSpace());
        assertNotEquals(plot1, plot2);
        plot2.setFixedRangeAxisSpace(new AxisSpace());
        assertEquals(plot1, plot2);

        // fixed legend items
        plot1.setFixedLegendItems(new LegendItemCollection());
        assertNotEquals(plot1, plot2);
        plot2.setFixedLegendItems(new LegendItemCollection());
        assertEquals(plot1, plot2);

        // crosshairDatasetIndex
        plot1.setCrosshairDatasetIndex(99);
        assertNotEquals(plot1, plot2);
        plot2.setCrosshairDatasetIndex(99);
        assertEquals(plot1, plot2);

        // domainCrosshairColumnKey
        plot1.setDomainCrosshairColumnKey("A");
        assertNotEquals(plot1, plot2);
        plot2.setDomainCrosshairColumnKey("A");
        assertEquals(plot1, plot2);

        // domainCrosshairRowKey
        plot1.setDomainCrosshairRowKey("B");
        assertNotEquals(plot1, plot2);
        plot2.setDomainCrosshairRowKey("B");
        assertEquals(plot1, plot2);

        // domainCrosshairVisible
        plot1.setDomainCrosshairVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setDomainCrosshairVisible(true);
        assertEquals(plot1, plot2);

        // domainCrosshairPaint
        plot1.setDomainCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setDomainCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        // domainCrosshairStroke
        plot1.setDomainCrosshairStroke(new BasicStroke(1.23f));
        assertNotEquals(plot1, plot2);
        plot2.setDomainCrosshairStroke(new BasicStroke(1.23f));
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlinesVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlinesVisible(true);
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlinePaint(new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertEquals(plot1, plot2);

        plot1.setRangeZeroBaselineVisible(!plot1.isRangeZeroBaselineVisible());
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselineVisible(!plot2.isRangeZeroBaselineVisible());
        assertEquals(plot1, plot2);

        plot1.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        plot1.setRangeZeroBaselineStroke(new BasicStroke(1.23f));
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselineStroke(new BasicStroke(1.23f));
        assertEquals(plot1, plot2);

        // shadowGenerator
        plot1.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertNotEquals(plot1, plot2);
        plot2.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertEquals(plot1, plot2);

        plot1.setShadowGenerator(null);
        assertNotEquals(plot1, plot2);
        plot2.setShadowGenerator(null);
        assertEquals(plot1, plot2);
    }

    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList() {
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setDomainAxis(new CategoryAxis("A"));
        CategoryPlot<String, String> p2 = new CategoryPlot<>();
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
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        CategoryPlot<String, String> p2 = new CategoryPlot<>();
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
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setRangeAxis(new NumberAxis("A"));
        CategoryPlot<String, String> p2 = new CategoryPlot<>();
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
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        CategoryPlot<String, String> p2 = new CategoryPlot<>();
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
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setRenderer(new BarRenderer());
        CategoryPlot<String, String> p2 = new CategoryPlot<>();
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
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW));
        p1.setRangeMinorGridlinePaint(new GradientPaint(2.0f, 3.0f, Color.WHITE,
                4.0f, 5.0f, Color.RED));
        p1.setRangeZeroBaselinePaint(new GradientPaint(3.0f, 4.0f, Color.RED,
                5.0f, 6.0f, Color.WHITE));
        CategoryPlot<String, String> p2;
        try {
            p2 = CloneUtils.clone(p1);
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check independence
        p1.addAnnotation(new CategoryLineAnnotation("C1", 1.0, "C2", 2.0,
                Color.RED, new BasicStroke(1.0f)));
        assertNotEquals(p1, p2);
        p2.addAnnotation(new CategoryLineAnnotation("C1", 1.0, "C2", 2.0,
                Color.RED, new BasicStroke(1.0f)));
        assertEquals(p1, p2);

        p1.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertNotEquals(p1, p2);
        p2.addDomainMarker(new CategoryMarker("C1"), Layer.FOREGROUND);
        assertEquals(p1, p2);

        p1.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertNotEquals(p1, p2);
        p2.addDomainMarker(new CategoryMarker("C2"), Layer.BACKGROUND);
        assertEquals(p1, p2);

        p1.addRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
        assertNotEquals(p1, p2);
        p2.addRangeMarker(new ValueMarker(1.0), Layer.FOREGROUND);
        assertEquals(p1, p2);

        p1.addRangeMarker(new ValueMarker(2.0), Layer.BACKGROUND);
        assertNotEquals(p1, p2);
        p2.addRangeMarker(new ValueMarker(2.0), Layer.BACKGROUND);
        assertEquals(p1, p2);
    }

    /**
     * Some more cloning checks.
     */
    @Test
    public void testCloning2() {
        AxisSpace da1 = new AxisSpace();
        AxisSpace ra1 = new AxisSpace();
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setFixedDomainAxisSpace(da1);
        p1.setFixedRangeAxisSpace(ra1);
        CategoryPlot<String, String> p2;
        try {
            p2 = CloneUtils.clone(p1);
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        da1.setBottom(99.0);
        assertNotEquals(p1, p2);
        p2.getFixedDomainAxisSpace().setBottom(99.0);
        assertEquals(p1, p2);

        ra1.setBottom(11.0);
        assertNotEquals(p1, p2);
        p2.getFixedRangeAxisSpace().setBottom(11.0);
        assertEquals(p1, p2);
    }

    /**
     * Some more cloning checks.
     */
    @Test
    public void testCloning3() {
        LegendItemCollection c1 = new LegendItemCollection();
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        p1.setFixedLegendItems(c1);
        CategoryPlot<String, String> p2;
        try {
            p2 = CloneUtils.clone(p1);
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        c1.add(new LegendItem("X", "XX", "tt", "url", true,
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true, Color.RED,
                true, Color.YELLOW, new BasicStroke(1.0f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(1.0f),
                Color.GREEN));
        assertNotEquals(p1, p2);
        p2.getFixedLegendItems().add(new LegendItem("X", "XX", "tt", "url",
                true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true,
                Color.RED, true, Color.YELLOW, new BasicStroke(1.0f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(1.0f),
                Color.GREEN));
        assertEquals(p1, p2);
    }

    /**
     * Renderers that belong to the plot are being cloned but they are
     * retaining a reference to the original plot.
     */
    @Test
    public void testBug2817504() {
        CategoryPlot<String, String> p1 = new CategoryPlot<>();
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        p1.setRenderer(r1);
        CategoryPlot<String, String> p2;
        try {
            p2 = CloneUtils.clone(p1);
        }
        catch (CloneNotSupportedException e) {
            fail("Cloning failed.");
            return;
        }
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check for independence
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) p2.getRenderer();
        assertSame(r2.getPlot(), p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> p1 = new CategoryPlot<>(dataset, domainAxis, 
                rangeAxis, renderer);
        p1.setOrientation(PlotOrientation.HORIZONTAL);
        CategoryPlot<String, String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        DefaultCategoryDataset<String, String> data = new DefaultCategoryDataset<>();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> p1 = new CategoryPlot<>(data, domainAxis, 
                rangeAxis, renderer);
        p1.setOrientation(PlotOrientation.VERTICAL);
        CategoryPlot<String, String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization3() {
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        JFreeChart chart = ChartFactory.createBarChart(
                "Test Chart", "Category Axis", "Value Axis", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        JFreeChart chart2 = TestUtils.serialised(chart);

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
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        JFreeChart chart = ChartFactory.createBarChart(
                "Test Chart", "Category Axis", "Value Axis",
                dataset, PlotOrientation.VERTICAL, true, true, false);
        @SuppressWarnings("unchecked")
        CategoryPlot<String, String> plot = (CategoryPlot) chart.getPlot();
        plot.addRangeMarker(new ValueMarker(1.1), Layer.FOREGROUND);
        plot.addRangeMarker(new IntervalMarker(2.2, 3.3), Layer.BACKGROUND);
        JFreeChart chart2 = TestUtils.serialised(chart);
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
        DefaultCategoryDataset<String, String> dataset1 = new DefaultCategoryDataset<>();
        CategoryAxis domainAxis1 = new CategoryAxis("Domain 1");
        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        BarRenderer renderer1 = new BarRenderer();
        CategoryPlot<String, String> p1 = new CategoryPlot<>(dataset1, 
                domainAxis1, rangeAxis1, renderer1);
        CategoryAxis domainAxis2 = new CategoryAxis("Domain 2");
        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
        BarRenderer renderer2 = new BarRenderer();
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
        p1.setDataset(1, dataset2);
        p1.setDomainAxis(1, domainAxis2);
        p1.setRangeAxis(1, rangeAxis2);
        p1.setRenderer(1, renderer2);
        CategoryPlot<String, String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);

        // now check that all datasets, renderers and axes are being listened
        // too...
        CategoryAxis domainAxisA = p2.getDomainAxis(0);
        NumberAxis rangeAxisA = (NumberAxis) p2.getRangeAxis(0);
        @SuppressWarnings("unchecked")
        DefaultCategoryDataset<String, String> datasetA 
                = (DefaultCategoryDataset) p2.getDataset(0);
        BarRenderer rendererA = (BarRenderer) p2.getRenderer(0);
        CategoryAxis domainAxisB = p2.getDomainAxis(1);
        NumberAxis rangeAxisB = (NumberAxis) p2.getRangeAxis(1);
        @SuppressWarnings("unchecked")
        DefaultCategoryDataset<String, String> datasetB
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
        CategoryPlot<String, String> plot = new CategoryPlot<>();
        CategoryItemRenderer renderer = new LineAndShapeRenderer();
        plot.setRenderer(renderer);
        // now make a change to the renderer and see if it triggers a plot
        // change event...
        MyPlotChangeListener listener = new MyPlotChangeListener();
        plot.addChangeListener(listener);
        renderer.setSeriesPaint(0, Color.BLACK);
        assertNotNull(listener.getEvent());
    }

    /**
     * A test for bug report 1169972.
     */
    @Test
    public void test1169972() {
        CategoryPlot<String, String> plot = new CategoryPlot<>(null, null, null, null);
        plot.setDomainAxis(new CategoryAxis("C"));
        plot.setRangeAxis(new NumberAxis("Y"));
        plot.setRenderer(new BarRenderer());
        plot.setDataset(new DefaultCategoryDataset<>());
        assertTrue(true); // we didn't get an exception so all is good
    }

    /**
     * Some tests for the addDomainMarker() method(s).
     */
    @Test
    public void testAddDomainMarker() {
        CategoryPlot<String, String> plot = new CategoryPlot<>();
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
        CategoryPlot<String, String> plot = new CategoryPlot<>();
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
        DefaultCategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        JFreeChart chart = ChartFactory.createLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        @SuppressWarnings("unchecked")
        CategoryPlot<String, String> plot = (CategoryPlot) chart.getPlot();
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
        CategoryPlot<String, String> plot = new CategoryPlot<>(null, domainAxis1, 
                rangeAxis1, null);
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
        CategoryPlot<String, String> plot = new CategoryPlot<>(null, domainAxis1, 
                rangeAxis1, null);
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
        CategoryPlot<String, String> plot = new CategoryPlot<>();
        assertFalse(plot.removeDomainMarker(new CategoryMarker("Category 1")));
    }

    /**
     * Check that removing a marker that isn't assigned to the plot returns
     * false.
     */
    @Test
    public void testRemoveRangeMarker() {
        CategoryPlot<String, String> plot = new CategoryPlot<>();
        assertFalse(plot.removeRangeMarker(new ValueMarker(0.5)));
    }

    /**
     * Some tests for the getDomainAxisForDataset() method.
     */
    @Test
    public void testGetDomainAxisForDataset() {
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new DefaultCategoryItemRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
        
        assertEquals(dataset, plot.getDataset(0));
        
        DefaultCategoryDataset<String,String> dataset2 = new DefaultCategoryDataset<>();
        dataset2.setValue(1, "R1", "C1");
        
        // we should be able to give a dataset an arbitrary index
        plot.setDataset(99, dataset2);
        assertEquals(2, plot.getDatasetCount());
        assertEquals(dataset2, plot.getDataset(99));
        
        assertEquals(0, plot.indexOf(dataset));
        assertEquals(99, plot.indexOf(dataset2));
    }
    
    /**
     * Tests for the getDomainAxis() and getRangeAxis() methods.
     */
    @Test
    public void testAxisIndices() {
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
        assertEquals(xAxis, plot.getDomainAxis(0));        
        assertEquals(yAxis, plot.getRangeAxis(0)); 
        
        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(99, xAxis2);
        assertEquals(xAxis2, plot.getDomainAxis(99));
        
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(99, yAxis2);
        assertEquals(yAxis2, plot.getRangeAxis(99));
    }

    /**
     * Tests for the getDomainAxisLocation() and getRangeAxisLocation() methods.
     */
    @Test 
    public void testAxisLocationIndices() {
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);

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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
        
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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);

        // add a second dataset
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);

        CategoryAxis xAxis2 = new CategoryAxis("X2");
        plot.setDomainAxis(11, xAxis2);
        
        // add a second dataset
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        
        assertEquals(xAxis, plot.getDomainAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToDomainAxis(99, 11);
        assertEquals(xAxis2, plot.getDomainAxisForDataset(99));
    }

    @Test
    public void testMapDatasetToRangeAxis() {
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);

        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(22, yAxis2);
        
        // add a second dataset
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
        dataset2.setValue(1, "R1", "C1");
        plot.setDataset(99, dataset);    
        
        assertEquals(yAxis, plot.getRangeAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToRangeAxis(99, 22);
        assertEquals(yAxis2, plot.getRangeAxisForDataset(99));
    }
    
    @Test
    public void testDomainMarkerIndices() {
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
        
        // add a second dataset, plotted against a second x axis
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
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
        CategoryDataset<String, String> dataset = new DefaultCategoryDataset<>();
        CategoryAxis xAxis = new CategoryAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        CategoryItemRenderer renderer = new BarRenderer();
        CategoryPlot<String, String> plot = new CategoryPlot<>(dataset, xAxis, 
                yAxis, renderer);
        
        // add a second dataset, plotted against a second axis
        DefaultCategoryDataset<String, String> dataset2 = new DefaultCategoryDataset<>();
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
