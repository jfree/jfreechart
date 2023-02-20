package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

public class Histogram extends JFreeChart {

    public Histogram(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
    }

    /**
     * Creates a histogram chart. This chart is constructed with an
     * {@link XYPlot} using an {@link XYBarRenderer}. The domain and range
     * axes are {@link NumberAxis} instances.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel the x axis label ({@code null} permitted).
     * @param yAxisLabel the y axis label ({@code null} permitted).
     * @param dataset    the dataset ({@code null} permitted).
     * 
     * @return A chart.
     */
    public static JFreeChart createHistogram(String title,
            String xAxisLabel, String yAxisLabel, IntervalXYDataset dataset) {
        return createHistogram(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates a histogram chart. This chart is constructed with an
     * {@link XYPlot} using an {@link XYBarRenderer}. The domain and range
     * axes are {@link NumberAxis} instances.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param xAxisLabel  the x axis label ({@code null} permitted).
     * @param yAxisLabel  the y axis label ({@code null} permitted).
     * @param dataset     the dataset ({@code null} permitted).
     * @param orientation the orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted).
     * @param legend      create a legend?
     * @param tooltips    display tooltips?
     * @param urls        generate URLs?
     *
     * @return The chart.
     */
    public static JFreeChart createHistogram(String title,
            String xAxisLabel, String yAxisLabel, IntervalXYDataset dataset,
            PlotOrientation orientation, boolean legend, boolean tooltips,
            boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        ValueAxis yAxis = new NumberAxis(yAxisLabel);

        XYItemRenderer renderer = new XYBarRenderer();
        if (tooltips) {
            renderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
        }
        if (urls) {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(orientation);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        return new Histogram(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

    }

    @Override
    public JFreeChart createChart(String title, String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, PieDataset dataset, boolean legend, boolean tooltips, boolean urls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, String timeAxisLabel, String valueAxisLabel,
            TimeSeriesCollection dataset) {
        // TODO Auto-generated method stub
        return null;
    }

}
