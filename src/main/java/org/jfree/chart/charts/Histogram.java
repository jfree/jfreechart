package org.jfree.chart.charts;

public class Histogram {

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
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
}
