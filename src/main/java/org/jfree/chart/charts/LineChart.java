package org.jfree.chart.charts;

public class LineChart {

    /**
     * Creates a line chart with default settings. The chart object returned
     * by this method uses a {@link CategoryPlot} instance as the plot, with a
     * {@link CategoryAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and a {@link LineAndShapeRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     *
     * @return A line chart.
     */
    public static JFreeChart createLineChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        return createLineChart(title, categoryAxisLabel, valueAxisLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates a line chart with default settings. The chart object returned
     * by this method uses a {@link CategoryPlot} instance as the plot, with a
     * {@link CategoryAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and a {@link LineAndShapeRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param orientation       the chart orientation (horizontal or vertical)
     *                          ({@code null} not permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     * @param tooltips          configure chart to generate tool tips?
     * @param urls              configure chart to generate URLs?
     *
     * @return A line chart.
     */
    public static JFreeChart createLineChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, false);
        if (tooltips) {
            renderer.setDefaultToolTipGenerator(
                    new StandardCategoryToolTipGenerator());
        }
        if (urls) {
            renderer.setDefaultItemURLGenerator(
                    new StandardCategoryURLGenerator());
        }
        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                renderer);
        plot.setOrientation(orientation);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
}
