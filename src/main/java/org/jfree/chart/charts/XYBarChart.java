package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

public class XYBarChart extends JFreeChart {
    public XYBarChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates and returns a default instance of an XY bar chart.
     * <P>
     * The chart object returned by this method uses an {@link XYPlot} instance
     * as the plot, with a {@link DateAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link XYBarRenderer} as the
     * renderer.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel a label for the X-axis ({@code null} permitted).
     * @param dateAxis   make the domain axis display dates?
     * @param yAxisLabel a label for the Y-axis ({@code null} permitted).
     * @param dataset    the dataset for the chart ({@code null} permitted).
     *
     * @return An XY bar chart.
     */
    public static JFreeChart createXYBarChart(String title, String xAxisLabel,
            boolean dateAxis, String yAxisLabel, IntervalXYDataset dataset) {
        return createXYBarChart(title, xAxisLabel, dateAxis, yAxisLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates and returns a default instance of an XY bar chart.
     * <P>
     * The chart object returned by this method uses an {@link XYPlot} instance
     * as the plot, with a {@link DateAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link XYBarRenderer} as the
     * renderer.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param xAxisLabel  a label for the X-axis ({@code null} permitted).
     * @param dateAxis    make the domain axis display dates?
     * @param yAxisLabel  a label for the Y-axis ({@code null} permitted).
     * @param dataset     the dataset for the chart ({@code null} permitted).
     * @param orientation the orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted).
     * @param legend      a flag specifying whether or not a legend is required.
     * @param tooltips    configure chart to generate tool tips?
     * @param urls        configure chart to generate URLs?
     *
     * @return An XY bar chart.
     */
    public static JFreeChart createXYBarChart(String title, String xAxisLabel,
            boolean dateAxis, String yAxisLabel, IntervalXYDataset dataset,
            PlotOrientation orientation, boolean legend, boolean tooltips,
            boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        ValueAxis domainAxis;
        if (dateAxis) {
            domainAxis = new DateAxis(xAxisLabel);
        } else {
            NumberAxis axis = new NumberAxis(xAxisLabel);
            axis.setAutoRangeIncludesZero(false);
            domainAxis = axis;
        }
        ValueAxis valueAxis = new NumberAxis(yAxisLabel);

        XYBarRenderer renderer = new XYBarRenderer();
        if (tooltips) {
            XYToolTipGenerator tt;
            if (dateAxis) {
                tt = StandardXYToolTipGenerator.getTimeSeriesInstance();
            } else {
                tt = new StandardXYToolTipGenerator();
            }
            renderer.setDefaultToolTipGenerator(tt);
        }
        if (urls) {
            renderer.setURLGenerator(new StandardXYURLGenerator());
        }

        XYPlot plot = new XYPlot(dataset, domainAxis, valueAxis, renderer);
        plot.setOrientation(orientation);

        return new XYBarChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
