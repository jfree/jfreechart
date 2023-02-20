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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.jfree.chart.charts.AreaChart;
import org.jfree.chart.charts.BarChart;
import org.jfree.chart.charts.BoxAndWhiskerChart;
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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.general.WaferMapDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
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
            case "StackedBarChart":
                return BarChart.createStackedBarChart(title, category, value, (CategoryDataset) dataset);

            case "StackedAreaChart":
                return AreaChart.createStackedAreaChart(title, category, value, (CategoryDataset) dataset);

            case "PieChart":
                return PieChart.createPieChart(title, (PieDataset) dataset, true, true, true);

            case "BarChart":
                return BarChart.createBarChart(title, category, value, (CategoryDataset) dataset);

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

    public JFreeChart getChartReflection(String chartType)
            throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> classObj = Class.forName(chartType);
        Constructor<?> chartConstructor = classObj.getConstructor();

        String chart = classObj.getSimpleName();

        if (chart == null || chart == "") {
            return null;
        }

        Object chartObj = chartConstructor.newInstance();
        return getChartObject(chart, classObj, chartObj);

    }

    public JFreeChart getChartObject(String simpleClassName, Class<?> classObj, Object chartObj)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        switch (simpleClassName) {
            case "BarChart":
                return handleBarChart(chartObj, classObj);
            case "PieChart":
                return handlePieChart(chartObj, classObj);
            case "TimeSeriesChart":
                return handleTimeSeriesChart(chartObj, classObj);
            default:
                return null;
        }
    }

    public JFreeChart handleBarChart(Object chartObj, Class<?> classObj) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String title = "Performance: JFreeSVG vs Batik";
        String valueAxisLabel = "Miliseconds";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");

        Method createMethod = classObj.getMethod("createChart", String.class, String.class, String.class,
                CategoryDataset.class);
        return (JFreeChart) createMethod.invoke(chartObj, title, valueAxisLabel, valueAxisLabel, dataset);

    }

    public JFreeChart handlePieChart(Object chartObj, Class<?> classObj) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String title = "Smart Phones Manufactured / Q3 2011";

        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Samsung", new Double(27.8));
        dataset.setValue("Others", new Double(55.3));
        dataset.setValue("Nokia", new Double(16.8));
        dataset.setValue("Apple", new Double(17.1));

        Method createMethod = classObj.getMethod("createChart", String.class, PieDataset.class, boolean.class,
                boolean.class, boolean.class);
        return (JFreeChart) createMethod.invoke(chartObj, title, dataset, true, true, true);

    }

    public JFreeChart handleTimeSeriesChart(Object chartObj, Class<?> classObj) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String title = "Legal & General Unit Trust Prices";
        String timeAxisLabel = "Date";
        String valueAxisLabel = "Price per Unit";

        TimeSeries s1 = new TimeSeries("L&G European Index Trust");
        s1.add(new Month(2, 2001), 181.8);
        s1.add(new Month(3, 2001), 167.3);
        s1.add(new Month(4, 2001), 153.8);
        s1.add(new Month(5, 2001), 167.6);
        s1.add(new Month(6, 2001), 158.8);
        s1.add(new Month(7, 2001), 148.3);
        s1.add(new Month(8, 2001), 153.9);
        s1.add(new Month(9, 2001), 142.7);
        s1.add(new Month(10, 2001), 123.2);
        s1.add(new Month(11, 2001), 131.8);
        s1.add(new Month(12, 2001), 139.6);
        s1.add(new Month(1, 2002), 142.9);
        s1.add(new Month(2, 2002), 138.7);
        s1.add(new Month(3, 2002), 137.3);
        s1.add(new Month(4, 2002), 143.9);
        s1.add(new Month(5, 2002), 139.8);
        s1.add(new Month(6, 2002), 137.0);
        s1.add(new Month(7, 2002), 132.8);

        TimeSeries s2 = new TimeSeries("L&G UK Index Trust");
        s2.add(new Month(2, 2001), 129.6);
        s2.add(new Month(3, 2001), 123.2);
        s2.add(new Month(4, 2001), 117.2);
        s2.add(new Month(5, 2001), 124.1);
        s2.add(new Month(6, 2001), 122.6);
        s2.add(new Month(7, 2001), 119.2);
        s2.add(new Month(8, 2001), 116.5);
        s2.add(new Month(9, 2001), 112.7);
        s2.add(new Month(10, 2001), 101.5);
        s2.add(new Month(11, 2001), 106.1);
        s2.add(new Month(12, 2001), 110.3);
        s2.add(new Month(1, 2002), 111.7);
        s2.add(new Month(2, 2002), 111.0);
        s2.add(new Month(3, 2002), 109.6);
        s2.add(new Month(4, 2002), 113.2);
        s2.add(new Month(5, 2002), 111.6);
        s2.add(new Month(6, 2002), 108.8);
        s2.add(new Month(7, 2002), 101.6);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        Method createMethod = classObj.getMethod("createChart", String.class, String.class, String.class,
                TimeSeriesCollection.class);
        return (JFreeChart) createMethod.invoke(chartObj, title, valueAxisLabel, dataset);
    }

}
