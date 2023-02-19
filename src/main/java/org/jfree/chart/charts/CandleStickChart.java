package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

public class CandleStickChart extends JFreeChart {

    public CandleStickChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
    }

    /**
     * Creates and returns a default instance of a candlesticks chart.
     *
     * @param title          the chart title ({@code null} permitted).
     * @param timeAxisLabel  a label for the time axis ({@code null}
     *                       permitted).
     * @param valueAxisLabel a label for the value axis ({@code null}
     *                       permitted).
     * @param dataset        the dataset for the chart ({@code null} permitted).
     * @param legend         a flag specifying whether or not a legend is required.
     *
     * @return A candlestick chart.
     */
    public static JFreeChart createCandlestickChart(String title,
            String timeAxisLabel, String valueAxisLabel, OHLCDataset dataset,
            boolean legend) {

        ValueAxis timeAxis = new DateAxis(timeAxisLabel);
        NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
        XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, null);
        plot.setRenderer(new CandlestickRenderer());
        JFreeChart chart = new CandleStickChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        return chart;

    }

    @Override
    public JFreeChart createChart(String title, String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, PieDataset dataset, boolean legend, boolean tooltips, boolean urls) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JFreeChart createChart(String title, String timeAxisLabel, String valueAxisLabel, XYDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }
}
