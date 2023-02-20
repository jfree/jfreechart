package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.WindItemRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.WindDataset;
import org.jfree.data.xy.XYDataset;

public class WindPlot extends JFreeChart {
    public WindPlot(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a wind plot with default settings.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel a label for the x-axis ({@code null} permitted).
     * @param yAxisLabel a label for the y-axis ({@code null} permitted).
     * @param dataset    the dataset for the chart ({@code null} permitted).
     * @param legend     a flag that controls whether or not a legend is created.
     * @param tooltips   configure chart to generate tool tips?
     * @param urls       configure chart to generate URLs?
     *
     * @return A wind plot.
     *
     */
    public static JFreeChart createWindPlot(String title, String xAxisLabel,
            String yAxisLabel, WindDataset dataset, boolean legend,
            boolean tooltips, boolean urls) {

        ValueAxis xAxis = new DateAxis(xAxisLabel);
        ValueAxis yAxis = new NumberAxis(yAxisLabel);
        yAxis.setRange(-12.0, 12.0);

        WindItemRenderer renderer = new WindItemRenderer();
        if (tooltips) {
            renderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator());
        }
        if (urls) {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        return new WindPlot(title, JFreeChart.DEFAULT_TITLE_FONT,
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
