package org.jfree.chart.charts;

public class WaferMapChart {

    /**
     * Creates a wafer map chart.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param dataset     the dataset ({@code null} permitted).
     * @param orientation the plot orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted.
     * @param legend      display a legend?
     * @param tooltips    generate tooltips?
     * @param urls        generate URLs?
     *
     * @return A wafer map chart.
     */
    public static JFreeChart createWaferMapChart(String title,
            WaferMapDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        WaferMapPlot plot = new WaferMapPlot(dataset);
        WaferMapRenderer renderer = new WaferMapRenderer();
        plot.setRenderer(renderer);

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }
}
