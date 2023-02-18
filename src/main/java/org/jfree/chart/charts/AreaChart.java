package org.jfree.chart.charts;

public class AreaChart {
    /**
     * Creates an area chart with default settings. The chart object returned
     * by this method uses a {@link CategoryPlot} instance as the plot, with a
     * {@link CategoryAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and an {@link AreaRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     *
     * @return An area chart.
     */
    public static JFreeChart createAreaChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        return createAreaChart(title, categoryAxisLabel, valueAxisLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates an area chart with default settings. The chart object returned
     * by this method uses a {@link CategoryPlot} instance as the plot, with a
     * {@link CategoryAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and an {@link AreaRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param orientation       the plot orientation ({@code null} not
     *                          permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     * @param tooltips          configure chart to generate tool tips?
     * @param urls              configure chart to generate URLs?
     *
     * @return An area chart.
     */
    public static JFreeChart createAreaChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        categoryAxis.setCategoryMargin(0.0);

        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        AreaRenderer renderer = new AreaRenderer();
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

    /**
     * Creates a stacked area chart with default settings. The chart object
     * returned by this method uses a {@link CategoryPlot} instance as the
     * plot, with a {@link CategoryAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link StackedAreaRenderer}
     * as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     *
     * @return A stacked area chart.
     */
    public static JFreeChart createStackedAreaChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        return createStackedAreaChart(title, categoryAxisLabel, valueAxisLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates a stacked area chart with default settings. The chart object
     * returned by this method uses a {@link CategoryPlot} instance as the
     * plot, with a {@link CategoryAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link StackedAreaRenderer}
     * as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param orientation       the plot orientation (horizontal or vertical)
     *                          ({@code null} not permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     * @param tooltips          configure chart to generate tool tips?
     * @param urls              configure chart to generate URLs?
     *
     * @return A stacked area chart.
     */
    public static JFreeChart createStackedAreaChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        categoryAxis.setCategoryMargin(0.0);
        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        StackedAreaRenderer renderer = new StackedAreaRenderer();
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
