/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * ------------------------
 * MultiplePiePlotTest.java
 * ------------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.pie;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.TestUtils;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link MultiplePiePlot} class.
 */
public class MultiplePiePlotTest implements PlotChangeListener {

    /** The last event received. */
    PlotChangeEvent lastEvent;

    /**
     * Receives a plot change event and records it.  Some tests will use this
     * to check that events have been generated (or not) when required.
     *
     * @param event  the event.
     */
    @Override
    public void plotChanged(PlotChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Some checks for the constructors.
     */
    @Test
    public void testConstructor() {
        MultiplePiePlot plot = new MultiplePiePlot();
        assertNull(plot.getDataset());

        // the following checks that the plot registers itself as a listener
        // with the dataset passed to the constructor - see patch 1943021
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        plot = new MultiplePiePlot(dataset);
        assertTrue(dataset.hasListener(plot));
    }

    /**
     * Check that the equals() method distinguishes the required fields.
     */
    @Test
    public void testEquals() {
        MultiplePiePlot p1 = new MultiplePiePlot();
        MultiplePiePlot p2 = new MultiplePiePlot();
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        p1.setDataExtractOrder(TableOrder.BY_ROW);
        assertNotEquals(p1, p2);
        p2.setDataExtractOrder(TableOrder.BY_ROW);
        assertEquals(p1, p2);

        p1.setLimit(1.23);
        assertNotEquals(p1, p2);
        p2.setLimit(1.23);
        assertEquals(p1, p2);

        p1.setAggregatedItemsKey("Aggregated Items");
        assertNotEquals(p1, p2);
        p2.setAggregatedItemsKey("Aggregated Items");
        assertEquals(p1, p2);

        p1.setAggregatedItemsPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(p1, p2);
        p2.setAggregatedItemsPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(p1, p2);

        p1.setPieChart(ChartFactory.createPieChart("Title", null, true, true,
                true));
        assertNotEquals(p1, p2);
        p2.setPieChart(ChartFactory.createPieChart("Title", null, true, true,
                true));
        assertEquals(p1, p2);

        p1.setLegendItemShape(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(p1, p2);
        p2.setLegendItemShape(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(p1, p2);
    }

    /**
     * Some basic checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        MultiplePiePlot p1 = new MultiplePiePlot();
        Rectangle2D rect = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        p1.setLegendItemShape(rect);
        MultiplePiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check independence
        rect.setRect(2.0, 3.0, 4.0, 5.0);
        assertNotEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MultiplePiePlot p1 = new MultiplePiePlot(null);
        p1.setAggregatedItemsPaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.RED));
        MultiplePiePlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Fetches the legend items and checks the values.
     */
    @Test
    public void testGetLegendItems() {
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        dataset.addValue(35.0, "S1", "C1");
        dataset.addValue(45.0, "S1", "C2");
        dataset.addValue(55.0, "S2", "C1");
        dataset.addValue(15.0, "S2", "C2");
        MultiplePiePlot plot = new MultiplePiePlot(dataset);
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

    /**
     * Tests for the {@link MultiplePiePlot#setPieChart(JFreeChart)} method.
     */
    @Test
    public void testSetPieChart() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        PiePlot piePlot = new PiePlot(new DefaultPieDataset());
        JFreeChart pieChart = new JFreeChart(piePlot);
        
        plot.setPieChart(pieChart);
        
        assertNotNull(plot.getPieChart(), "Pie chart should not be null after setting");
        assertSame(pieChart, plot.getPieChart(), "Pie chart should be the same as the one set");
        assertTrue(plot.getPieChart().getPlot() instanceof PiePlot, "Plot should be an instance of PiePlot");
    }
    
    @Test
    public void testSetPieChartWithNull() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> plot.setPieChart(null), 
                "Should throw IllegalArgumentException when pieChart is null");
        
        assertNotNull(exception.getMessage(), "Exception message should not be null");
        assertTrue(exception.getMessage().contains("pieChart"), "Exception message should contain 'pieChart'");
        assertTrue(exception.getMessage().contains("Null"), "Exception message should contain 'Null'");
    }
    
    @Test
    public void testSetPieChartWithNullDoesNotChangeState() {
        MultiplePiePlot plot = new MultiplePiePlot();
        JFreeChart originalChart = plot.getPieChart();
        assertNotNull(originalChart, "Original pie chart should not be null");
        
        assertThrows(IllegalArgumentException.class, () -> plot.setPieChart(null), 
                "Should throw IllegalArgumentException when setting null");
        
        assertSame(originalChart, plot.getPieChart(), "Pie chart should remain unchanged after failed set");
    }
    
    @Test
    public void testSetPieChartWithInvalidPlotType() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        dataset.addValue(1.0, "Row1", "Col1");
        JFreeChart categoryChart = ChartFactory.createBarChart("Test", "Category", "Value", dataset);
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> plot.setPieChart(categoryChart), 
                "Should throw IllegalArgumentException when chart is not based on PiePlot");
        
        assertNotNull(exception.getMessage(), "Exception message should not be null");
        assertTrue(exception.getMessage().contains("PiePlot"), "Exception message should contain 'PiePlot'");
    }
    
    @Test
    public void testSetPieChartUpdatesProperty() {
        MultiplePiePlot plot = new MultiplePiePlot();
        JFreeChart initialChart = plot.getPieChart();
        assertNotNull(initialChart, "Initial pie chart should not be null");
        
        PiePlot newPiePlot = new PiePlot();
        newPiePlot.setBackgroundPaint(Color.BLUE);
        JFreeChart newPieChart = new JFreeChart(newPiePlot);
        
        plot.setPieChart(newPieChart);
        
        assertNotSame(initialChart, plot.getPieChart(), "Pie chart should be different after update");
        assertSame(newPieChart, plot.getPieChart(), "Pie chart should be the newly set chart");
        assertSame(newPiePlot, plot.getPieChart().getPlot(), "Plot should be the newly set PiePlot");
        assertEquals(Color.BLUE, ((PiePlot) plot.getPieChart().getPlot()).getBackgroundPaint(), 
                "Background paint should be preserved");
    }
    
    @Test
    public void testSetPieChartMultipleValidSets() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        PiePlot firstPiePlot = new PiePlot();
        firstPiePlot.setSectionPaint("A", Color.RED);
        JFreeChart firstChart = new JFreeChart(firstPiePlot);
        
        PiePlot secondPiePlot = new PiePlot();
        secondPiePlot.setSectionPaint("B", Color.GREEN);
        JFreeChart secondChart = new JFreeChart(secondPiePlot);
        
        plot.setPieChart(firstChart);
        assertSame(firstChart, plot.getPieChart(), "First chart should be set correctly");
        assertEquals(Color.RED, ((PiePlot) plot.getPieChart().getPlot()).getSectionPaint("A"), 
                "First chart's section paint should be preserved");
        
        plot.setPieChart(secondChart);
        assertSame(secondChart, plot.getPieChart(), "Second chart should be set correctly");
        assertEquals(Color.GREEN, ((PiePlot) plot.getPieChart().getPlot()).getSectionPaint("B"), 
                "Second chart's section paint should be preserved");
    }
    
    @Test
    public void testSetPieChartConsecutiveSetsWithErrorChecking() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        PiePlot piePlot = new PiePlot();
        JFreeChart validChart = new JFreeChart(piePlot);
        
        plot.setPieChart(validChart);
        assertSame(validChart, plot.getPieChart(), "Valid chart should be set correctly");
        
        DefaultCategoryDataset<String,String> dataset = new DefaultCategoryDataset<>();
        JFreeChart invalidChart = ChartFactory.createLineChart("Test", "X", "Y", dataset);
        
        assertThrows(IllegalArgumentException.class, () -> plot.setPieChart(invalidChart), 
                "Invalid chart should throw exception");
        
        assertSame(validChart, plot.getPieChart(), "Pie chart should still be the valid chart after failed set");
    }
    
    @Test
    public void testSetPieChartWithDataset() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Item1", 10.0);
        pieDataset.setValue("Item2", 20.0);
        PiePlot piePlot = new PiePlot(pieDataset);
        JFreeChart pieChart = new JFreeChart(piePlot);
        
        plot.setPieChart(pieChart);
        
        assertSame(pieChart, plot.getPieChart(), "Pie chart with dataset should be set successfully");
        assertSame(pieDataset, ((PiePlot) plot.getPieChart().getPlot()).getDataset(), 
                "Dataset should be preserved in the plot");
    }
    
    @Test
    public void testSetPieChartWithNullDataset() {
        MultiplePiePlot plot = new MultiplePiePlot();
        
        PiePlot piePlot = new PiePlot(null);
        JFreeChart pieChart = new JFreeChart(piePlot);
        
        plot.setPieChart(pieChart);
        
        assertSame(pieChart, plot.getPieChart(), "Pie chart with null dataset should be set successfully");
        assertNull(((PiePlot) plot.getPieChart().getPlot()).getDataset(), "Dataset should be null in the plot");
    }

}
