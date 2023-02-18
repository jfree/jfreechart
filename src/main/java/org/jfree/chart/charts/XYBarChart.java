package org.jfree.chart.charts;

public class XYBarChart {
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

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }

}
