package org.jfree.chart.charts;

public class WindPlot {
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
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;

    }
}
