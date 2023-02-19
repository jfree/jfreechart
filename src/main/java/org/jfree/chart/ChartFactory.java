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

import java.awt.Font;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.charts.BarChart;
import org.jfree.chart.charts.PieChart;
import org.jfree.chart.charts.TimeSeriesChart;
import org.jfree.chart.plot.Plot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

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

    public JFreeChart getChart(String chartType)
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
                DefaultCategoryDataset.class);
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

        Method createMethod = classObj.getMethod("createChart", String.class, DefaultPieDataset.class, boolean.class,
                boolean.class, boolean.class);
        return (JFreeChart) createMethod.invoke(chartObj, title, dataset, false, false, false);

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

        Method createMethod = classObj.getMethod("createChart", String.class, TimeSeriesCollection.class, String.class);
        return (JFreeChart) createMethod.invoke(chartObj, title, valueAxisLabel, dataset);
    }

}
