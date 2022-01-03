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
 * ---------------
 * XYPlotTest.java
 * ---------------
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
import java.awt.geom.Point2D;
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
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.date.MonthConstants;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.renderer.xy.DefaultXYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.api.Layer;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYPlot} class.
 */
public class XYPlotTest {

    /**
     * Added this test in response to a bug report.
     */
    @Test
    public void testGetDatasetCount() {
        XYPlot<String> plot = new XYPlot<>();
        assertEquals(0, plot.getDatasetCount());
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {

        XYPlot<String> plot1 = new XYPlot<>();
        XYPlot<String> plot2 = new XYPlot<>();
        assertEquals(plot1, plot2);

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

        // domainAxis...
        plot1.setDomainAxis(new NumberAxis("Domain Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxis(new NumberAxis("Domain Axis"));
        assertEquals(plot1, plot2);

        // domainAxisLocation...
        plot1.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // secondary DomainAxes...
        plot1.setDomainAxis(11, new NumberAxis("Secondary Domain Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxis(11, new NumberAxis("Secondary Domain Axis"));
        assertEquals(plot1, plot2);

        // secondary DomainAxisLocations...
        plot1.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // rangeAxis...
        plot1.setRangeAxis(new NumberAxis("Range Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxis(new NumberAxis("Range Axis"));
        assertEquals(plot1, plot2);

        // rangeAxisLocation...
        plot1.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // secondary RangeAxes...
        plot1.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertEquals(plot1, plot2);

        // secondary RangeAxisLocations...
        plot1.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertEquals(plot1, plot2);

        // secondary DatasetDomainAxisMap...
        plot1.mapDatasetToDomainAxis(11, 11);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToDomainAxis(11, 11);
        assertEquals(plot1, plot2);

        // secondaryDatasetRangeAxisMap...
        plot1.mapDatasetToRangeAxis(11, 11);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToRangeAxis(11, 11);
        assertEquals(plot1, plot2);

        // renderer
        plot1.setRenderer(new DefaultXYItemRenderer());
        assertNotEquals(plot1, plot2);
        plot2.setRenderer(new DefaultXYItemRenderer());
        assertEquals(plot1, plot2);

        // secondary renderers
        plot1.setRenderer(11, new DefaultXYItemRenderer());
        assertNotEquals(plot1, plot2);
        plot2.setRenderer(11, new DefaultXYItemRenderer());
        assertEquals(plot1, plot2);

        // domainGridlinesVisible
        plot1.setDomainGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinesVisible(false);
        assertEquals(plot1, plot2);

        // domainGridlineStroke
        Stroke stroke = new BasicStroke(2.0f);
        plot1.setDomainGridlineStroke(stroke);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlineStroke(stroke);
        assertEquals(plot1, plot2);

        // domainGridlinePaint
        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
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
                3.0f, 4.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.RED));
        assertEquals(plot1, plot2);

        // rangeZeroBaselineVisible
        plot1.setRangeZeroBaselineVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselineVisible(true);
        assertEquals(plot1, plot2);

        // rangeZeroBaselineStroke
        plot1.setRangeZeroBaselineStroke(stroke);
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselineStroke(stroke);
        assertEquals(plot1, plot2);

        // rangeZeroBaselinePaint
        plot1.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setRangeZeroBaselinePaint(new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.RED));
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
        plot1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.PINK,
                3.0f, 4.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.PINK,
                3.0f, 4.0f, Color.RED));
        assertEquals(plot1, plot2);

        // rangeCrosshairLockedOnData
        plot1.setRangeCrosshairLockedOnData(false);
        assertNotEquals(plot1, plot2);
        plot2.setRangeCrosshairLockedOnData(false);
        assertEquals(plot1, plot2);

        // range markers
        plot1.addRangeMarker(new ValueMarker(4.0));
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(new ValueMarker(4.0));
        assertEquals(plot1, plot2);

        // secondary range markers
        plot1.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertEquals(plot1, plot2);

        plot1.addRangeMarker(1, new ValueMarker(99.0), Layer.BACKGROUND);
        assertNotEquals(plot1, plot2);
        plot2.addRangeMarker(1, new ValueMarker(99.0), Layer.BACKGROUND);
        assertEquals(plot1, plot2);

        // fixed legend items
        plot1.setFixedLegendItems(new LegendItemCollection());
        assertNotEquals(plot1, plot2);
        plot2.setFixedLegendItems(new LegendItemCollection());
        assertEquals(plot1, plot2);

        // weight
        plot1.setWeight(3);
        assertNotEquals(plot1, plot2);
        plot2.setWeight(3);
        assertEquals(plot1, plot2);

        // quadrant origin
        plot1.setQuadrantOrigin(new Point2D.Double(12.3, 45.6));
        assertNotEquals(plot1, plot2);
        plot2.setQuadrantOrigin(new Point2D.Double(12.3, 45.6));
        assertEquals(plot1, plot2);

        // quadrant paint
        plot1.setQuadrantPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setQuadrantPaint(0, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);
        plot1.setQuadrantPaint(1, new GradientPaint(2.0f, 3.0f, Color.RED,
                4.0f, 5.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setQuadrantPaint(1, new GradientPaint(2.0f, 3.0f, Color.RED,
                4.0f, 5.0f, Color.BLUE));
        assertEquals(plot1, plot2);
        plot1.setQuadrantPaint(2, new GradientPaint(3.0f, 4.0f, Color.RED,
                5.0f, 6.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setQuadrantPaint(2, new GradientPaint(3.0f, 4.0f, Color.RED,
                5.0f, 6.0f, Color.BLUE));
        assertEquals(plot1, plot2);
        plot1.setQuadrantPaint(3, new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setQuadrantPaint(3, new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        plot1.setDomainTickBandPaint(Color.RED);
        assertNotEquals(plot1, plot2);
        plot2.setDomainTickBandPaint(Color.RED);
        assertEquals(plot1, plot2);

        plot1.setRangeTickBandPaint(Color.BLUE);
        assertNotEquals(plot1, plot2);
        plot2.setRangeTickBandPaint(Color.BLUE);
        assertEquals(plot1, plot2);

        plot1.setDomainMinorGridlinesVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setDomainMinorGridlinesVisible(true);
        assertEquals(plot1, plot2);

        plot1.setDomainMinorGridlinePaint(Color.RED);
        assertNotEquals(plot1, plot2);
        plot2.setDomainMinorGridlinePaint(Color.RED);
        assertEquals(plot1, plot2);

        plot1.setDomainGridlineStroke(new BasicStroke(1.1f));
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlineStroke(new BasicStroke(1.1f));
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlinesVisible(true);
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlinesVisible(true);
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlinePaint(Color.BLUE);
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlinePaint(Color.BLUE);
        assertEquals(plot1, plot2);

        plot1.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertNotEquals(plot1, plot2);
        plot2.setRangeMinorGridlineStroke(new BasicStroke(1.23f));
        assertEquals(plot1, plot2);

        List<Integer> axisIndices = Arrays.asList(new Integer[] {0, 1});
        plot1.mapDatasetToDomainAxes(0, axisIndices);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToDomainAxes(0, axisIndices);
        assertEquals(plot1, plot2);

        plot1.mapDatasetToRangeAxes(0, axisIndices);
        assertNotEquals(plot1, plot2);
        plot2.mapDatasetToRangeAxes(0, axisIndices);
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
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList() {
        XYPlot<String> p1 = new XYPlot<>();
        p1.setDomainAxis(new NumberAxis("A"));
        XYPlot<String> p2 = new XYPlot<>();
        p2.setDomainAxis(new NumberAxis("A"));
        assertEquals(p1, p2);
        p2.setDomainAxis(1, new NumberAxis("B"));
        assertNotEquals(p1, p2);
    }
    
    /**
     * This test covers a flaw in the ObjectList equals() method.
     */
    @Test
    public void testEquals_ObjectList2() {
        XYPlot<String> p1 = new XYPlot<>();
        p1.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        XYPlot<String> p2 = new XYPlot<>();
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
        XYPlot<String> p1 = new XYPlot<>();
        p1.setRangeAxis(new NumberAxis("A"));
        XYPlot<String> p2 = new XYPlot<>();
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
        XYPlot<String> p1 = new XYPlot<>();
        p1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        XYPlot<String> p2 = new XYPlot<>();
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
        XYPlot<String> p1 = new XYPlot<>();
        p1.setRenderer(new XYBarRenderer());
        XYPlot<String> p2 = new XYPlot<>();
        p2.setRenderer(new XYBarRenderer());
        assertEquals(p1, p2);
        p2.setRenderer(1, new XYLineAndShapeRenderer());
        assertNotEquals(p1, p2);
    }

    /**
     * Confirm that basic cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>();
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Tests cloning for a more complex plot.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>(null, new NumberAxis("Domain Axis"),
                new NumberAxis("Range Axis"), new StandardXYItemRenderer());
        p1.setRangeAxis(1, new NumberAxis("Range Axis 2"));
        List<Integer> axisIndices = Arrays.asList(new Integer[] {0, 1});
        p1.mapDatasetToDomainAxes(0, axisIndices);
        p1.mapDatasetToRangeAxes(0, axisIndices);
        p1.setRenderer(1, new XYBarRenderer());
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Tests cloning for a plot where the fixed legend items have been
     * specified.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning3() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>(null, new NumberAxis("Domain Axis"),
                new NumberAxis("Range Axis"), new StandardXYItemRenderer());
        LegendItemCollection c1 = new LegendItemCollection();
        p1.setFixedLegendItems(c1);
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // verify independence of fixed legend item collection
        c1.add(new LegendItem("X"));
        assertNotEquals(p1, p2);
    }

    /**
     * Tests cloning to ensure that the cloned plot is registered as a listener
     * on the cloned renderer.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning4() throws CloneNotSupportedException {
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        XYPlot<String> p1 = new XYPlot<>(null, new NumberAxis("Domain Axis"),
                new NumberAxis("Range Axis"), r1);
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // verify that the plot is listening to the cloned renderer
        XYLineAndShapeRenderer r2 = (XYLineAndShapeRenderer) p2.getRenderer();
        assertTrue(r2.hasListener(p2));
    }

    /**
     * Confirm that cloning captures the quadrantOrigin field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_QuadrantOrigin() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>();
        Point2D p = new Point2D.Double(1.2, 3.4);
        p1.setQuadrantOrigin(p);
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
        assertNotSame(p2.getQuadrantOrigin(), p);
    }

    /**
     * Confirm that cloning captures the quadrantOrigin field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_QuadrantPaint() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>();
        p1.setQuadrantPaint(3, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check for independence
        p1.setQuadrantPaint(1, Color.RED);
        assertNotEquals(p1, p2);
        p2.setQuadrantPaint(1, Color.RED);
        assertEquals(p1, p2);
    }

    /**
     * Renderers that belong to the plot are being cloned but they are
     * retaining a reference to the original plot.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testBug2817504() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>();
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer();
        p1.setRenderer(r1);
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check for independence
        XYLineAndShapeRenderer r2 = (XYLineAndShapeRenderer) p2.getRenderer();
        assertSame(r2.getPlot(), p2);
    }

    /**
     * Tests the independence of the clones.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloneIndependence() throws CloneNotSupportedException {
        XYPlot<String> p1 = new XYPlot<>(null, new NumberAxis("Domain Axis"),
                new NumberAxis("Range Axis"), new StandardXYItemRenderer());
        p1.setDomainAxis(1, new NumberAxis("Domain Axis 2"));
        p1.setDomainAxisLocation(1, AxisLocation.BOTTOM_OR_LEFT);
        p1.setRangeAxis(1, new NumberAxis("Range Axis 2"));
        p1.setRangeAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
        p1.setRenderer(1, new XYBarRenderer());
        XYPlot<String> p2 = CloneUtils.clone(p1);
        assertEquals(p1, p2);

        p1.getDomainAxis().setLabel("Label");
        assertNotEquals(p1, p2);
        p2.getDomainAxis().setLabel("Label");
        assertEquals(p1, p2);

        p1.getDomainAxis(1).setLabel("S1");
        assertNotEquals(p1, p2);
        p2.getDomainAxis(1).setLabel("S1");
        assertEquals(p1, p2);

        p1.setDomainAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
        assertNotEquals(p1, p2);
        p2.setDomainAxisLocation(1, AxisLocation.TOP_OR_RIGHT);
        assertEquals(p1, p2);

        p1.mapDatasetToDomainAxis(2, 1);
        assertNotEquals(p1, p2);
        p2.mapDatasetToDomainAxis(2, 1);
        assertEquals(p1, p2);

        p1.getRangeAxis().setLabel("Label");
        assertNotEquals(p1, p2);
        p2.getRangeAxis().setLabel("Label");
        assertEquals(p1, p2);

        p1.getRangeAxis(1).setLabel("S1");
        assertNotEquals(p1, p2);
        p2.getRangeAxis(1).setLabel("S1");
        assertEquals(p1, p2);

        p1.setRangeAxisLocation(1, AxisLocation.TOP_OR_LEFT);
        assertNotEquals(p1, p2);
        p2.setRangeAxisLocation(1, AxisLocation.TOP_OR_LEFT);
        assertEquals(p1, p2);

        p1.mapDatasetToRangeAxis(2, 1);
        assertNotEquals(p1, p2);
        p2.mapDatasetToRangeAxis(2, 1);
        assertEquals(p1, p2);

    }

    /**
     * Setting a null renderer should be allowed, but is generating a null
     * pointer exception in 0.9.7.
     */
    @Test
    public void testSetNullRenderer() {
        boolean failed = false;
        try {
            XYPlot<String> plot = new XYPlot<>(null, new NumberAxis("X"),
                    new NumberAxis("Y"), null);
            plot.setRenderer(null);
        }
        catch (Exception e) {
            failed = true;
        }
        assertFalse(failed);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization1() {
        XYDataset<String> data = new XYSeriesCollection<>();
        NumberAxis domainAxis = new NumberAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        StandardXYItemRenderer renderer = new StandardXYItemRenderer();
        XYPlot<String> p1 = new XYPlot<>(data, domainAxis, rangeAxis, renderer);
        XYPlot<String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.  This test
     * uses a {@link DateAxis} and a {@link StandardXYToolTipGenerator}.
     */
    @Test
    public void testSerialization2() {
        IntervalXYDataset<String> data1 = createDataset1();
        XYItemRenderer renderer1 = new XYBarRenderer(0.20);
        renderer1.setDefaultToolTipGenerator(
                StandardXYToolTipGenerator.getTimeSeriesInstance());
        XYPlot<String> p1 = new XYPlot<>(data1, new DateAxis("Date"), null, renderer1);
        XYPlot<String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Problem to reproduce a bug in serialization.  The bug (first reported
     * against the {@link org.jfree.chart.plot.CategoryPlot} class) is a null
     * pointer exception that occurs when drawing a plot after deserialization.
     * It is caused by four temporary storage structures (axesAtTop,
     * axesAtBottom, axesAtLeft and axesAtRight - all initialized as empty
     * lists in the constructor) not being initialized by the readObject()
     * method following deserialization.  This test has been written to
     * reproduce the bug (now fixed).
     */
    @Test
    public void testSerialization3() {

        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        JFreeChart chart = ChartFactory.createXYLineChart("Test Chart",
                "Domain Axis", "Range Axis", dataset);
        JFreeChart chart2 = TestUtils.serialised(chart);
        assertEquals(chart, chart2);
        try {
            chart2.createBufferedImage(300, 200);
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }

    /**
     * A test to reproduce a bug in serialization: the domain and/or range
     * markers for a plot are not being serialized.
     */
    @Test
    public void testSerialization4() {

        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        JFreeChart chart = ChartFactory.createXYLineChart("Test Chart",
                "Domain Axis", "Range Axis", dataset);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        plot.addDomainMarker(new ValueMarker(1.0), Layer.FOREGROUND);
        plot.addDomainMarker(new IntervalMarker(2.0, 3.0), Layer.BACKGROUND);
        plot.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        plot.addRangeMarker(new IntervalMarker(5.0, 6.0), Layer.BACKGROUND);
        JFreeChart chart2 = TestUtils.serialised(chart);
        assertEquals(chart, chart2);
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
        XYSeriesCollection<String> dataset1 = new XYSeriesCollection<>();
        NumberAxis domainAxis1 = new NumberAxis("Domain 1");
        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        StandardXYItemRenderer renderer1 = new StandardXYItemRenderer();
        XYPlot<String> p1 = new XYPlot<>(dataset1, domainAxis1, rangeAxis1, renderer1);
        NumberAxis domainAxis2 = new NumberAxis("Domain 2");
        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
        StandardXYItemRenderer renderer2 = new StandardXYItemRenderer();
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        p1.setDataset(1, dataset2);
        p1.setDomainAxis(1, domainAxis2);
        p1.setRangeAxis(1, rangeAxis2);
        p1.setRenderer(1, renderer2);
        XYPlot<String> p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);

        // now check that all datasets, renderers and axes are being listened
        // too...
        NumberAxis domainAxisA = (NumberAxis) p2.getDomainAxis(0);
        NumberAxis rangeAxisA = (NumberAxis) p2.getRangeAxis(0);
        XYSeriesCollection<?> datasetA = (XYSeriesCollection) p2.getDataset(0);
        StandardXYItemRenderer rendererA
            = (StandardXYItemRenderer) p2.getRenderer(0);
        NumberAxis domainAxisB = (NumberAxis) p2.getDomainAxis(1);
        NumberAxis rangeAxisB = (NumberAxis) p2.getRangeAxis(1);
        XYSeriesCollection<?> datasetB = (XYSeriesCollection) p2.getDataset(1);
        StandardXYItemRenderer rendererB
            = (StandardXYItemRenderer) p2.getRenderer(1);
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
     * Some checks for the getRendererForDataset() method.
     */
    @Test
    public void testGetRendererForDataset() {
        XYDataset<String> d0 = new XYSeriesCollection<>();
        XYDataset<String> d1 = new XYSeriesCollection<>();
        XYDataset<String> d2 = new XYSeriesCollection<>();
        XYDataset<String> d3 = new XYSeriesCollection<>();  // not used by plot
        XYItemRenderer r0 = new XYLineAndShapeRenderer();
        XYItemRenderer r2 = new XYLineAndShapeRenderer();
        XYPlot<String> plot = new XYPlot<>();
        plot.setDataset(0, d0);
        plot.setDataset(1, d1);
        plot.setDataset(2, d2);
        plot.setRenderer(0, r0);
        // no renderer 1
        plot.setRenderer(2, r2);
        assertEquals(r0, plot.getRendererForDataset(d0));
        assertEquals(r0, plot.getRendererForDataset(d1));
        assertEquals(r2, plot.getRendererForDataset(d2));
        assertNull(plot.getRendererForDataset(d3));
        assertNull(plot.getRendererForDataset(null));
    }

    /**
     * Some checks for the getLegendItems() method.
     */
    @Test
    public void testGetLegendItems() {
        // check the case where there is a secondary dataset that doesn't
        // have a renderer (i.e. falls back to renderer 0)
        XYDataset<String> d0 = createDataset1();
        XYDataset<String> d1 = createDataset2();
        XYItemRenderer r0 = new XYLineAndShapeRenderer();
        XYPlot<String> plot = new XYPlot<>();
        plot.setDataset(0, d0);
        plot.setDataset(1, d1);
        plot.setRenderer(0, r0);
        LegendItemCollection items = plot.getLegendItems();
        assertEquals(2, items.getItemCount());
    }

    /**
     * Creates a sample dataset.
     *
     * @return Series 1.
     */
    private IntervalXYDataset<String> createDataset1() {

        // create dataset 1...
        TimeSeries<String> series1 = new TimeSeries<>("Series 1");
        series1.add(new Day(1, MonthConstants.MARCH, 2002), 12353.3);
        series1.add(new Day(2, MonthConstants.MARCH, 2002), 13734.4);
        series1.add(new Day(3, MonthConstants.MARCH, 2002), 14525.3);
        series1.add(new Day(4, MonthConstants.MARCH, 2002), 13984.3);
        series1.add(new Day(5, MonthConstants.MARCH, 2002), 12999.4);
        series1.add(new Day(6, MonthConstants.MARCH, 2002), 14274.3);
        series1.add(new Day(7, MonthConstants.MARCH, 2002), 15943.5);
        series1.add(new Day(8, MonthConstants.MARCH, 2002), 14845.3);
        series1.add(new Day(9, MonthConstants.MARCH, 2002), 14645.4);
        series1.add(new Day(10, MonthConstants.MARCH, 2002), 16234.6);
        series1.add(new Day(11, MonthConstants.MARCH, 2002), 17232.3);
        series1.add(new Day(12, MonthConstants.MARCH, 2002), 14232.2);
        series1.add(new Day(13, MonthConstants.MARCH, 2002), 13102.2);
        series1.add(new Day(14, MonthConstants.MARCH, 2002), 14230.2);
        series1.add(new Day(15, MonthConstants.MARCH, 2002), 11235.2);

        TimeSeriesCollection<String> collection = new TimeSeriesCollection<>(series1);
        return collection;

    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    private XYDataset<String> createDataset2() {
        // create dataset 1...
        XYSeries<String> series = new XYSeries<>("Series 2");
        XYSeriesCollection<String> collection = new XYSeriesCollection<>(series);
        return collection;

    }

    /**
     * A test for a bug where setting the renderer doesn't register the plot
     * as a RendererChangeListener.
     */
    @Test
    public void testSetRenderer() {
        XYPlot<String> plot = new XYPlot<>();
        XYItemRenderer renderer = new XYLineAndShapeRenderer();
        plot.setRenderer(renderer);
        // now make a change to the renderer and see if it triggers a plot
        // change event...
        MyPlotChangeListener listener = new MyPlotChangeListener();
        plot.addChangeListener(listener);
        renderer.setSeriesPaint(0, Color.BLACK);
        assertNotNull(listener.getEvent());
    }

    /**
     * Some checks for the removeAnnotation() method.
     */
    @Test
    public void testRemoveAnnotation() {
        XYPlot<String> plot = new XYPlot<>();
        XYTextAnnotation a1 = new XYTextAnnotation("X", 1.0, 2.0);
        XYTextAnnotation a2 = new XYTextAnnotation("X", 3.0, 4.0);
        XYTextAnnotation a3 = new XYTextAnnotation("X", 1.0, 2.0);
        plot.addAnnotation(a1);
        plot.addAnnotation(a2);
        plot.addAnnotation(a3);
        plot.removeAnnotation(a2);
        XYTextAnnotation x = (XYTextAnnotation) plot.getAnnotations().get(0);
        assertEquals(x, a1);

        // now remove a3, but since a3.equals(a1), this will in fact remove
        // a1...
        assertEquals(a1, a3);
        plot.removeAnnotation(a3);  // actually removes a1
        x = (XYTextAnnotation) plot.getAnnotations().get(0);
        assertEquals(x, a3);
    }

    /**
     * Some tests for the addDomainMarker() method(s).
     */
    @Test
    public void testAddDomainMarker() {
        XYPlot<String> plot = new XYPlot<>();
        Marker m = new ValueMarker(1.0);
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
        XYPlot<String> plot = new XYPlot<>();
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
        DefaultXYDataset<String> dataset = new DefaultXYDataset<>();
        JFreeChart chart = ChartFactory.createXYLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        plot.setRenderer(1, new XYLineAndShapeRenderer());
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
     * A test for drawing range grid lines when there is no primary renderer.
     * In 1.0.4, this is throwing a NullPointerException.
     */
    @Test
    public void testDrawRangeGridlines() {
        DefaultXYDataset<String> dataset = new DefaultXYDataset<>();
        JFreeChart chart = ChartFactory.createXYLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        XYPlot<?> plot = (XYPlot) chart.getPlot();
        plot.setRenderer(null);
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
     * A test for drawing a plot where a series has zero items.  With
     * JFreeChart 1.0.5+cvs this was throwing an exception at one point.
     */
    @Test
    public void testDrawSeriesWithZeroItems() {
        DefaultXYDataset<String> dataset = new DefaultXYDataset<>();
        dataset.addSeries("Series 1", new double[][] {{1.0, 2.0}, {3.0, 4.0}});
        dataset.addSeries("Series 2", new double[][] {{}, {}});
        JFreeChart chart = ChartFactory.createXYLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
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
     * Check that removing a marker that isn't assigned to the plot returns
     * false.
     */
    @Test
    public void testRemoveDomainMarker() {
        XYPlot<String> plot = new XYPlot<>();
        assertFalse(plot.removeDomainMarker(new ValueMarker(0.5)));
    }

    /**
     * Check that removing a marker that isn't assigned to the plot returns
     * false.
     */
    @Test
    public void testRemoveRangeMarker() {
        XYPlot<String> plot = new XYPlot<>();
        assertFalse(plot.removeRangeMarker(new ValueMarker(0.5)));
    }

    /**
     * Some tests for the getDomainAxisForDataset() method.
     */
    @Test
    public void testGetDomainAxisForDataset() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
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
        NumberAxis xAxis2 = new NumberAxis("X2");
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
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
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
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        
        assertEquals(dataset, plot.getDataset(0));
        
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        
        // we should be able to give a dataset an arbitrary index
        plot.setDataset(99, dataset2);
        assertEquals(2, plot.getDatasetCount());
        assertEquals(dataset2, plot.getDataset(99));
        
        assertEquals(0, plot.indexOf(dataset));
        assertEquals(99, plot.indexOf(dataset2));
    }
    
    @Test
    public void testAxisIndices() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        assertEquals(xAxis, plot.getDomainAxis(0));        
        assertEquals(yAxis, plot.getRangeAxis(0)); 
        
        NumberAxis xAxis2 = new NumberAxis("X2");
        plot.setDomainAxis(99, xAxis2);
        assertEquals(xAxis2, plot.getDomainAxis(99));
        
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(99, yAxis2);
        assertEquals(yAxis2, plot.getRangeAxis(99));
    }
    
    @Test 
    public void testAxisLocationIndices() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);

        NumberAxis xAxis2 = new NumberAxis("X2");
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
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        
        assertEquals(renderer, plot.getRenderer(0));
        
        // we should be able to give a renderer an arbitrary index
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setRenderer(20, renderer2);
        assertEquals(2, plot.getRendererCount());
        assertEquals(renderer2, plot.getRenderer(20));
        
        assertEquals(20, plot.getIndexOf(renderer2));
    }

    @Test 
    public void testGetRendererForDataset2() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);

        // add a second dataset
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        plot.setDataset(99, dataset2);
       
        // by default, the renderer with index 0 is used
        assertEquals(renderer, plot.getRendererForDataset(dataset2));
        
        // add a second renderer with the same index as dataset2, now it will
        // be used
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setRenderer(99, renderer);
        assertEquals(renderer2, plot.getRendererForDataset(dataset2));
    }
    
    @Test
    public void testMapDatasetToDomainAxis() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);

        NumberAxis xAxis2 = new NumberAxis("X2");
        plot.setDomainAxis(11, xAxis2);
        
        // add a second dataset
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        plot.setDataset(99, dataset);    
        
        assertEquals(xAxis, plot.getDomainAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToDomainAxis(99, 11);
        assertEquals(xAxis2, plot.getDomainAxisForDataset(99));
    }

    @Test
    public void testMapDatasetToRangeAxis() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);

        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(22, yAxis2);
        
        // add a second dataset
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        plot.setDataset(99, dataset);    
        
        assertEquals(yAxis, plot.getRangeAxisForDataset(99));

        // now map the dataset to the second xAxis
        plot.mapDatasetToRangeAxis(99, 22);
        assertEquals(yAxis2, plot.getRangeAxisForDataset(99));
    }
    
    @Test
    public void testDomainMarkerIndices() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        
        // add a second dataset, plotted against a second x axis
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        plot.setDataset(99, dataset);    
        NumberAxis xAxis2 = new NumberAxis("X2");
        plot.setDomainAxis(1, xAxis2);
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setRenderer(99, renderer2);
        plot.mapDatasetToDomainAxis(99, 1);
        
        ValueMarker xMarker1 = new ValueMarker(123);
        plot.addDomainMarker(99, xMarker1, Layer.FOREGROUND);
        assertTrue(plot.getDomainMarkers(99, Layer.FOREGROUND).contains(
                xMarker1));
    }

    @Test
    public void testRangeMarkerIndices() {
        XYDataset<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        
        // add a second dataset, plotted against a second axis
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        dataset2.addSeries(new XYSeries<>("Series in dataset 2"));
        plot.setDataset(99, dataset);    
        NumberAxis yAxis2 = new NumberAxis("Y2");
        plot.setRangeAxis(1, yAxis2);
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setRenderer(99, renderer2);
        plot.mapDatasetToRangeAxis(99, 1);
        
        ValueMarker yMarker1 = new ValueMarker(123);
        plot.addRangeMarker(99, yMarker1, Layer.FOREGROUND);
        assertTrue(plot.getRangeMarkers(99, Layer.FOREGROUND).contains(yMarker1));
    }
    
    /** 
     * Some tests for the getDataRange() method.
     */
    @Test
    public void testGetDataRange() {
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset, xAxis, yAxis, renderer);
        assertNull(plot.getDataRange(xAxis));
        assertNull(plot.getDataRange(yAxis));
        
        XYSeries<String> s1 = new XYSeries<>("S1");
        s1.add(1.0, 2.0);
        dataset.addSeries(s1);
        assertEquals(new Range(1.0, 1.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 2.0), plot.getDataRange(yAxis));
        
        s1.add(5.0, null);
        assertEquals(new Range(1.0, 5.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 2.0), plot.getDataRange(yAxis));
        
        s1.add(6.0, Double.NaN);
        assertEquals(new Range(1.0, 6.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 2.0), plot.getDataRange(yAxis));
    }
    
    /** 
     * Some tests for the getDataRange() method.
     */
    @Test
    public void testGetDataRangeWithMultipleDatasets() {
        XYSeriesCollection<String> dataset1 = new XYSeriesCollection<>();
        XYSeriesCollection<String> dataset2 = new XYSeriesCollection<>();
        NumberAxis xAxis = new NumberAxis("X");
        NumberAxis yAxis = new NumberAxis("Y");
        XYItemRenderer renderer = new DefaultXYItemRenderer();
        XYPlot<String> plot = new XYPlot<>(dataset1, xAxis, yAxis, renderer);
        plot.setDataset(1, dataset2);
        plot.mapDatasetToDomainAxis(1, 0);
        plot.mapDatasetToRangeAxis(1, 0);
        assertNull(plot.getDataRange(xAxis));
        assertNull(plot.getDataRange(yAxis));
        
        XYSeries<String> s1 = new XYSeries<>("S1");
        s1.add(1.0, 2.0);
        dataset1.addSeries(s1);
        assertEquals(new Range(1.0, 1.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 2.0), plot.getDataRange(yAxis));
        
        XYSeries<String> s2 = new XYSeries<>("S2");
        s2.add(5.0, 10.0);
        dataset2.addSeries(s2);
        assertEquals(new Range(1.0, 5.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 10.0), plot.getDataRange(yAxis));
        
        s2.add(6.0, Double.NaN);
        assertEquals(new Range(1.0, 6.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 10.0), plot.getDataRange(yAxis));
        
        s2.add(Double.NaN, 0.5); 
        assertEquals(new Range(1.0, 6.0), plot.getDataRange(xAxis));
        assertEquals(new Range(2.0, 10.0), plot.getDataRange(yAxis)); // only y-values for items in the x-range        
    }    
}
