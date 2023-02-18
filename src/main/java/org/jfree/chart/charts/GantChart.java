package org.jfree.chart.charts;

public class GantChart {
    /**
     * Creates a Gantt chart using the supplied attributes plus default values
     * where required. The chart object returned by this method uses a
     * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
     * for the domain axis, a {@link DateAxis} as the range axis, and a
     * {@link GanttRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param dateAxisLabel     the label for the date axis
     *                          ({@code null} permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     *
     * @return A Gantt chart.
     */
    public static JFreeChart createGanttChart(String title,
            String categoryAxisLabel, String dateAxisLabel,
            IntervalCategoryDataset dataset) {
        return createGanttChart(title, categoryAxisLabel, dateAxisLabel,
                dataset, true, true, false);
    }

    /**
     * Creates a Gantt chart using the supplied attributes plus default values
     * where required. The chart object returned by this method uses a
     * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
     * for the domain axis, a {@link DateAxis} as the range axis, and a
     * {@link GanttRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param dateAxisLabel     the label for the date axis
     *                          ({@code null} permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     * @param tooltips          configure chart to generate tool tips?
     * @param urls              configure chart to generate URLs?
     *
     * @return A Gantt chart.
     */
    public static JFreeChart createGanttChart(String title,
            String categoryAxisLabel, String dateAxisLabel,
            IntervalCategoryDataset dataset, boolean legend, boolean tooltips,
            boolean urls) {

        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        DateAxis dateAxis = new DateAxis(dateAxisLabel);

        CategoryItemRenderer renderer = new GanttRenderer();
        if (tooltips) {
            renderer.setDefaultToolTipGenerator(
                    new IntervalCategoryToolTipGenerator(
                            "{3} - {4}", DateFormat.getDateInstance()));
        }
        if (urls) {
            renderer.setDefaultItemURLGenerator(
                    new StandardCategoryURLGenerator());
        }

        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, dateAxis,
                renderer);
        plot.setOrientation(PlotOrientation.HORIZONTAL);
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
}
