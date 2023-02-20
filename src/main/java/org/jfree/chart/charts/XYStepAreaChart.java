package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYStepAreaRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class XYStepAreaChart extends JFreeChart {

    public XYStepAreaChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a filled stepped XY plot with default settings.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel a label for the Y-axis ({@code null} permitted).
     * @param dataset    the dataset for the chart ({@code null} permitted).
     *
     * @return A chart.
     */
    public static JFreeChart createXYStepAreaChart(String title,
            String xAxisLabel, String yAxisLabel, XYDataset dataset) {
        return createXYStepAreaChart(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates a filled stepped XY plot with default settings.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param xAxisLabel  a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel  a label for the Y-axis ({@code null} permitted).
     * @param dataset     the dataset for the chart ({@code null} permitted).
     * @param orientation the plot orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted).
     * @param legend      a flag specifying whether or not a legend is required.
     * @param tooltips    configure chart to generate tool tips?
     * @param urls        configure chart to generate URLs?
     *
     * @return A chart.
     */
    public static JFreeChart createXYStepAreaChart(String title,
            String xAxisLabel, String yAxisLabel, XYDataset dataset,
            PlotOrientation orientation, boolean legend, boolean tooltips,
            boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);

        XYToolTipGenerator toolTipGenerator = null;
        if (tooltips) {
            toolTipGenerator = new StandardXYToolTipGenerator();
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }
        XYItemRenderer renderer = new XYStepAreaRenderer(
                XYStepAreaRenderer.AREA_AND_SHAPES, toolTipGenerator,
                urlGenerator);

        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, null);
        plot.setRenderer(renderer);
        plot.setOrientation(orientation);
        plot.setDomainCrosshairVisible(false);
        plot.setRangeCrosshairVisible(false);
        return new XYStepAreaChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
