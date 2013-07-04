/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ------------------------
 * CombinedXYPlotDemo1.java
 * ------------------------
 * (C) Copyright 2008, 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * Changes
 * -------
 * 05-May-2008 : Version 1 (DG);
 *
 */

package org.jfree.experimental.chart.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.plot.CombinedXYPlot;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demonstration application showing a {@link CombinedXYPlot} with
 * two subplots.
 */
public class CombinedXYPlotDemo1 extends ApplicationFrame {

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    public CombinedXYPlotDemo1(String title) {
        super(title);
        JPanel panel = createDemoPanel();
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);
    }

    /**
     * Creates an overlaid chart.
     *
     * @return The chart.
     */
    private static JFreeChart createCombinedChart() {

        // create plot ...
        IntervalXYDataset data1 = createDataset1();
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(true, false);
        renderer1.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")));
        renderer1.setSeriesStroke(0, new BasicStroke(4.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        renderer1.setSeriesPaint(0, Color.blue);

        DateAxis domainAxis = new DateAxis("Year");
        domainAxis.setLowerMargin(0.0);
        domainAxis.setUpperMargin(0.02);
        ValueAxis rangeAxis = new NumberAxis("$billion");
        XYPlot plot1 = new XYPlot(data1, null, rangeAxis, renderer1);
        plot1.setBackgroundPaint(Color.lightGray);
        plot1.setDomainGridlinePaint(Color.white);
        plot1.setRangeGridlinePaint(Color.white);

        // add a second dataset and renderer...
        IntervalXYDataset data2 = createDataset2();
        XYBarRenderer renderer2 = new XYBarRenderer() {
            public Paint getItemPaint(int series, int item) {
                XYDataset dataset = getPlot().getDataset();
                if (dataset.getYValue(series, item) >= 0.0) {
                    return Color.red;
                }
                else {
                    return Color.green;
                }
            }
        };
        renderer2.setSeriesPaint(0, Color.red);
        renderer2.setDrawBarOutline(false);
        renderer2.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,
                new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00")));

        XYPlot plot2 = new XYPlot(data2, null, new NumberAxis("$billion"),
                renderer2);
        plot2.setBackgroundPaint(Color.lightGray);
        plot2.setDomainGridlinePaint(Color.white);
        plot2.setRangeGridlinePaint(Color.white);

        CombinedXYPlot cplot = new CombinedXYPlot(domainAxis, rangeAxis);
        cplot.add(plot1, 3);
        cplot.add(plot2, 2);
        cplot.setGap(8.0);
        cplot.setDomainGridlinePaint(Color.white);
        cplot.setDomainGridlinesVisible(true);

        // return a new chart containing the overlaid plot...
        JFreeChart chart = new JFreeChart("CombinedXYPlotDemo1",
                JFreeChart.DEFAULT_TITLE_FONT, cplot, false);
        chart.setBackgroundPaint(Color.white);
        LegendTitle legend = new LegendTitle(cplot);
        chart.addSubtitle(legend);
        return chart;
    }

    /**
     * Creates a sample dataset.  You wouldn't normally hard-code the
     * population of a dataset in this way (it would be better to read the
     * values from a file or a database query), but for a self-contained demo
     * this is the least complicated solution.
     *
     * @return The dataset.
     */
    private static IntervalXYDataset createDataset1() {

        // create dataset 1...
        TimeSeries series1 = new TimeSeries("Series 1");
        series1.add(new Month(1, 2005), 7627.743);
        series1.add(new Month(2, 2005), 7713.138);
        series1.add(new Month(3, 2005), 6776.939);
        series1.add(new Month(4, 2005), 5764.537);
        series1.add(new Month(5, 2005), 4777.880);
        series1.add(new Month(6, 2005), 4836.496);
        series1.add(new Month(7, 2005), 3887.618);
        series1.add(new Month(8, 2005), 3926.933);
        series1.add(new Month(9, 2005), 4932.710);
        series1.add(new Month(10, 2005), 4027.123);
        series1.add(new Month(11, 2005), 8092.322);
        series1.add(new Month(12, 2005), 8170.414);
        series1.add(new Month(1, 2006), 8196.070);
        series1.add(new Month(2, 2006), 8269.886);
        series1.add(new Month(3, 2006), 5371.156);
        series1.add(new Month(4, 2006), 5355.718);
        series1.add(new Month(5, 2006), 5356.777);
        series1.add(new Month(6, 2006), 8420.042);
        series1.add(new Month(7, 2006), 8444.347);
        series1.add(new Month(8, 2006), 8515.034);
        series1.add(new Month(9, 2006), 8506.974);
        series1.add(new Month(10, 2006), 8584.329);
        series1.add(new Month(11, 2006), 8633.246);
        series1.add(new Month(12, 2006), 8680.224);
        series1.add(new Month(1, 2007), 8707.561);
        return new TimeSeriesCollection(series1);

    }

    /**
     * Creates a sample dataset.  You wouldn't normally hard-code the
     * population of a dataset in this way (it would be better to read the
     * values from a file or a database query), but for a self-contained demo
     * this is the least complicated solution.
     *
     * @return A sample dataset.
     */
    private static IntervalXYDataset createDataset2() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries series1 = new TimeSeries("Series 2");
        series1.add(new Month(1, 2005), 1200);
        series1.add(new Month(2, 2005), 1400);
        series1.add(new Month(3, 2005), 1500);
        series1.add(new Month(4, 2005), 1700);
        series1.add(new Month(5, 2005), 1600);
        series1.add(new Month(6, 2005), 2400);
        series1.add(new Month(7, 2005), 2100);
        series1.add(new Month(8, 2005), 2200);
        series1.add(new Month(9, 2005), 800);
        series1.add(new Month(10, 2005), 2350);
        series1.add(new Month(11, 2005), 500);
        series1.add(new Month(12, 2005), 700);
        series1.add(new Month(1, 2006), 900);
        series1.add(new Month(2, 2006), 1500);
        series1.add(new Month(3, 2006), 2100);
        series1.add(new Month(4, 2006), 2200);
        series1.add(new Month(5, 2006), 1900);
        series1.add(new Month(6, 2006), 3000);
        series1.add(new Month(7, 2006), 3780);
        series1.add(new Month(8, 2006), 4000);
        series1.add(new Month(9, 2006), 4500);
        series1.add(new Month(10, 2006), 7000);
        series1.add(new Month(11, 2006), 5500);
        series1.add(new Month(12, 2006), 6000);
        series1.add(new Month(1, 2007), 6500);
        dataset.addSeries(series1);
        return dataset;

    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public static JPanel createDemoPanel() {
        JFreeChart chart = createCombinedChart();
        return new ChartPanel(chart);
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        CombinedXYPlotDemo1 demo = new CombinedXYPlotDemo1(
                "JFreeChart : CombinedXYPlotDemo1");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}
