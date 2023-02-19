package org.jfree.chart.charts;

import java.awt.Font;
import java.util.Locale;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public class RingChart extends JFreeChart {
    public RingChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a ring chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link RingPlot}
     * instance as the plot.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend   a flag specifying whether or not a legend is required.
     * @param tooltips configure chart to generate tool tips?
     * @param locale   the locale ({@code null} not permitted).
     *
     * @return A ring chart.
     */
    public static JFreeChart createRingChart(String title, PieDataset dataset,
            boolean legend, boolean tooltips, Locale locale) {

        RingPlot plot = new RingPlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(locale));
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator(locale));
        }
        return new RingChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

    }

    /**
     * Creates a ring chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link RingPlot}
     * instance as the plot.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend   a flag specifying whether or not a legend is required.
     * @param tooltips configure chart to generate tool tips?
     * @param urls     configure chart to generate URLs?
     *
     * @return A ring chart.
     */
    public static JFreeChart createRingChart(String title, PieDataset dataset,
            boolean legend, boolean tooltips, boolean urls) {

        RingPlot plot = new RingPlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        }
        if (urls) {
            plot.setURLGenerator(new StandardPieURLGenerator());
        }
        return new RingChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
