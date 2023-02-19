package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.internal.Args;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.WaferMapPlot;
import org.jfree.chart.renderer.WaferMapRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.WaferMapDataset;
import org.jfree.data.xy.XYDataset;

public class WaferMapChart extends JFreeChart {

    public WaferMapChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

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

        return new WaferMapChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

    }

    @Override
    public JFreeChart createChart(String title, String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, CategoryDataset dataset, boolean legend, boolean tooltips,
            boolean urls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, String valueAxisLabel, XYDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }
}
