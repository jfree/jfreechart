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
 * -----------------
 * ChartFactory.java
 * -----------------
 * (C) Copyright 2001-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Serge V. Grachov;
 *                   Joao Guilherme Del Valle;
 *                   Bill Kelemen;
 *                   Jon Iles;
 *                   Jelai Wang;
 *                   Richard Atkinson;
 *                   David Browning (for Australian Institute of Marine
 *                       Science);
 *                   Benoit Xhenseval;
 * 
 */

package org.jfree.chart;

import java.lang.reflect.InvocationTargetException;

import org.jfree.chart.charts.AreaChart;
import org.jfree.chart.charts.BarChart;
import org.jfree.chart.charts.BubbleChart;
import org.jfree.chart.charts.CandleStickChart;
import org.jfree.chart.charts.GantChart;
import org.jfree.chart.charts.HighLowChart;
import org.jfree.chart.charts.Histogram;
import org.jfree.chart.charts.LineChart;
import org.jfree.chart.charts.PieChart;
import org.jfree.chart.charts.PolarChart;
import org.jfree.chart.charts.RingChart;
import org.jfree.chart.charts.ScatterPlot;
import org.jfree.chart.charts.TimeSeriesChart;
import org.jfree.chart.charts.WaferMapChart;
import org.jfree.chart.charts.WaterFallChart;
import org.jfree.chart.charts.WindPlot;
import org.jfree.chart.charts.XYAreaChart;
import org.jfree.chart.charts.XYBarChart;
import org.jfree.chart.charts.XYLineChart;
import org.jfree.chart.charts.XYStepAreaChart;
import org.jfree.chart.charts.XYStepChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.WaferMapDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.WindDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * A collection of utility methods for creating some standard charts with
 * JFreeChart.
 */
public abstract class ChartFactory {

    /** The chart theme. */
    // private static ChartTheme currentTheme = new StandardChartTheme("JFree");

    // /**
    // * Returns the current chart theme used by the factory.
    // *
    // * @return The chart theme.
    // *
    // * @see #setChartTheme(ChartTheme)
    // * @see ChartUtils#applyCurrentTheme(JFreeChart)
    // */
    // public static ChartTheme getChartTheme() {
    // return currentTheme;
    // }

    // /**
    // * Sets the current chart theme. This will be applied to all new charts
    // * created via methods in this class.
    // *
    // * @param theme the theme ({@code null} not permitted).
    // *
    // * @see #getChartTheme()
    // * @see ChartUtils#applyCurrentTheme(JFreeChart)
    // */
    // public static void setChartTheme(ChartTheme theme) {
    // Args.nullNotPermitted(theme, "theme");
    // currentTheme = theme;

    // // here we do a check to see if the user is installing the "Legacy"
    // // theme, and reset the bar painters in that case...
    // if (theme instanceof StandardChartTheme) {
    // StandardChartTheme sct = (StandardChartTheme) theme;
    // if (sct.getName().equals("Legacy")) {
    // BarRenderer.setDefaultBarPainter(new StandardBarPainter());
    // XYBarRenderer.setDefaultBarPainter(new StandardXYBarPainter());
    // } else {
    // BarRenderer.setDefaultBarPainter(new GradientBarPainter());
    // XYBarRenderer.setDefaultBarPainter(new GradientXYBarPainter());
    // }
    // }
    // }

    /**
     * Creates a dynamic createChart method using Reflection.
     *
     * @param type the chart type to be returned by the method
     * 
     * @return A chart specified by the type
     * @throws ClassNotFoundException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */

    public static JFreeChart getChartRegular(String chartType, String title, String category, String value,
            Dataset dataset) {
        switch (chartType) {
            case "TimeSeriesChart":
                return TimeSeriesChart.createTimeSeriesChart(title, category, value, (XYDataset) dataset);

            case "StackedBarChart":
                return BarChart.createStackedBarChart(title, category, value, (CategoryDataset) dataset);

            case "StackedAreaChart":
                return AreaChart.createStackedAreaChart(title, category, value, (CategoryDataset) dataset);

            case "PieChart":
                return PieChart.createPieChart(title, (PieDataset) dataset, true, true, true);

            case "BarChart":
                return BarChart.createBarChart(title, category, value, (DefaultCategoryDataset) dataset);

            case "AreaChart":
                return AreaChart.createAreaChart(title, category, value, (CategoryDataset) dataset);

            case "BubbleChart":
                return BubbleChart.createBubbleChart(title, category, value, (XYZDataset) dataset);

            case "CandleStickChart":
                return CandleStickChart.createCandlestickChart(title, value, chartType, (OHLCDataset) dataset,
                        false);

            case "GantChart":
                return GantChart.createGanttChart(title, category, value, (IntervalCategoryDataset) dataset);

            case "HighLowChart":
                return HighLowChart.createHighLowChart(title, category, value, (OHLCDataset) dataset, false);

            case "Histogram":
                return Histogram.createHistogram(title, category, value, (IntervalXYDataset) dataset);

            case "LineChart":
                return LineChart.createLineChart(title, category, value, (CategoryDataset) dataset);

            case "PolarChart":
                return PolarChart.createPolarChart(title, (XYDataset) dataset, false, false, false);

            case "RingChart":
                return RingChart.createRingChart(title, (PieDataset) dataset, false, false, null);

            case "ScatterPlot":
                return ScatterPlot.createScatterPlot(title, category, value, (XYDataset) dataset);

            case "WaferMapChart":
                return WaferMapChart.createWaferMapChart(title, (WaferMapDataset) dataset, null, false, false,
                        false);

            case "WaterFallChart":
                return WaterFallChart.createWaterfallChart(title, category, value, (CategoryDataset) dataset, null,
                        true, true,
                        true);

            case "WindPlot":
                return WindPlot.createWindPlot(title, category, value, (WindDataset) dataset, false, false,
                        false);

            case "XYAreaChart":
                return XYAreaChart.createXYAreaChart(title, category, value, (XYDataset) dataset);

            case "XYBarChart":
                return XYBarChart.createXYBarChart(title, category, false, value, (IntervalXYDataset) dataset);

            case "XYLineChart":
                return XYLineChart.createXYLineChart(title, category, value, (XYDataset) dataset);

            case "XYStepAreaChart":
                return XYStepAreaChart.createXYStepAreaChart(title, category, value, (XYDataset) dataset);

            case "XYStepChart":
                return XYStepChart.createXYStepChart(title, category, value, (XYDataset) dataset,
                        PlotOrientation.VERTICAL, true, true, true);

            default:
                return null;
        }
    }

}
