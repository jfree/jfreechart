package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.renderer.DefaultPolarItemRenderer;
import org.jfree.data.xy.XYDataset;

public class PolarChart extends JFreeChart {

    public PolarChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a polar plot for the specified dataset (x-values interpreted as
     * angles in degrees). The chart object returned by this method uses a
     * {@link PolarPlot} instance as the plot, with a {@link NumberAxis} for
     * the radial axis.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset ({@code null} permitted).
     * @param legend   legend required?
     * @param tooltips tooltips required?
     * @param urls     URLs required?
     *
     * @return A chart.
     */
    public static JFreeChart createPolarChart(String title, XYDataset dataset,
            boolean legend, boolean tooltips, boolean urls) {

        PolarPlot plot = new PolarPlot();
        plot.setDataset(dataset);
        NumberAxis rangeAxis = new NumberAxis();
        rangeAxis.setAxisLineVisible(false);
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setTickLabelInsets(new RectangleInsets(0.0, 0.0, 0.0, 0.0));
        plot.setAxis(rangeAxis);
        plot.setRenderer(new DefaultPolarItemRenderer());

        return new PolarChart(
                title, JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

    }
}
