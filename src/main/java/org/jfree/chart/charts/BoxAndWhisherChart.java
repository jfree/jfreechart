package org.jfree.chart.charts;

public class BoxAndWhisherChart {

    /**
     * Creates and returns a default instance of a box and whisker chart
     * based on data from a {@link BoxAndWhiskerCategoryDataset}.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel a label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    a label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     *
     * @return A box and whisker chart.
     */
    public static JFreeChart createBoxAndWhiskerChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            BoxAndWhiskerCategoryDataset dataset, boolean legend) {

        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
        valueAxis.setAutoRangeIncludesZero(false);

        BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setDefaultToolTipGenerator(new BoxAndWhiskerToolTipGenerator());

        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                renderer);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates and returns a default instance of a box and whisker chart.
     *
     * @param title          the chart title ({@code null} permitted).
     * @param timeAxisLabel  a label for the time axis ({@code null}
     *                       permitted).
     * @param valueAxisLabel a label for the value axis ({@code null}
     *                       permitted).
     * @param dataset        the dataset for the chart ({@code null} permitted).
     * @param legend         a flag specifying whether or not a legend is required.
     *
     * @return A box and whisker chart.
     */
    public static JFreeChart createBoxAndWhiskerChart(String title,
            String timeAxisLabel, String valueAxisLabel,
            BoxAndWhiskerXYDataset dataset, boolean legend) {

        ValueAxis timeAxis = new DateAxis(timeAxisLabel);
        NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
        valueAxis.setAutoRangeIncludesZero(false);
        XYBoxAndWhiskerRenderer renderer = new XYBoxAndWhiskerRenderer(10.0);
        XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, renderer);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
}
