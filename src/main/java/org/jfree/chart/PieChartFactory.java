package org.jfree.chart;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.plot.pie.MultiplePiePlot;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.plot.RingPlot;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.chart.api.TableOrder;
import org.jfree.chart.internal.Args;

/**
 * A factory class that creates different types of pie charts.
 */
public class PieChartFactory {

    /** The chart theme. */
    private static ChartTheme currentTheme = new StandardChartTheme("JFree");

    /**
     * Creates a pie chart with default settings.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset) {
        return createPieChart(title, dataset, true, true, false);
    }

    /**
     * Creates a pie chart with default settings.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param locale  the locale ({@code null} not permitted).
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset,
                                            boolean legend, boolean tooltips, Locale locale) {
        PiePlot plot = new PiePlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(locale));
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator(locale));
        }
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates a pie chart with default settings.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend  a flag specifying whether or not a legend is required.
     * @param tooltips  configure chart to generate tool tips?
     * @param urls  configure chart to generate URLs?
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset,
                                            boolean legend, boolean tooltips, boolean urls) {
        PiePlot plot = new PiePlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));
        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        }
        if (urls) {
            plot.setURLGenerator(new StandardPieURLGenerator());
        }
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates a pie chart with a comparison dataset.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset ({@code null} permitted).
     * @param previousDataset  the dataset for comparison ({@code null} permitted).
     * @param percentDiffForMaxScale  the percentage difference for maximum scale.
     * @param greenForIncrease  show increases in green?
     * @param legend  include legend?
     * @param tooltips  generate tooltips?
     * @param locale  the locale ({@code null} not permitted).
     * @param subTitle  show subtitle?
     * @param showDifference  show the difference in values?
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset,
                                            PieDataset previousDataset, int percentDiffForMaxScale,
                                            boolean greenForIncrease, boolean legend, boolean tooltips,
                                            Locale locale, boolean subTitle, boolean showDifference) {

        PiePlot plot = new PiePlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(locale));
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));

        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator(locale));
        }

        List keys = dataset.getKeys();
        DefaultPieDataset series = null;
        if (showDifference) {
            series = new DefaultPieDataset();
        }

        double colorPerPercent = 255.0 / percentDiffForMaxScale;
        for (Iterator it = keys.iterator(); it.hasNext();) {
            Comparable key = (Comparable) it.next();
            Number newValue = dataset.getValue(key);
            Number oldValue = previousDataset.getValue(key);

            if (oldValue == null) {
                if (greenForIncrease) {
                    plot.setSectionPaint(key, Color.GREEN);
                }
                else {
                    plot.setSectionPaint(key, Color.RED);
                }
                if (showDifference) {
                    series.setValue(key + " (+100%)", newValue);
                }
            }
            else {
                double percentChange = (newValue.doubleValue()
                        / oldValue.doubleValue() - 1.0) * 100.0;
                double shade
                        = (Math.abs(percentChange) >= percentDiffForMaxScale ? 255
                        : Math.abs(percentChange) * colorPerPercent);
                if (greenForIncrease
                        && newValue.doubleValue() > oldValue.doubleValue()
                        || !greenForIncrease && newValue.doubleValue()
                        < oldValue.doubleValue()) {
                    plot.setSectionPaint(key, new Color(0, (int) shade, 0));
                }
                else {
                    plot.setSectionPaint(key, new Color((int) shade, 0, 0));
                }
                if (showDifference) {
                    series.setValue(key + " (" + (percentChange >= 0 ? "+" : "")
                            + NumberFormat.getPercentInstance().format(
                            percentChange / 100.0) + ")", newValue);
                }
            }
        }

        if (showDifference) {
            plot.setDataset(series);
        }

        JFreeChart chart =  new JFreeChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

        if (subTitle) {
            TextTitle subtitle = new TextTitle("Bright " + (greenForIncrease
                    ? "red" : "green") + "=change >=-" + percentDiffForMaxScale
                    + "%, Bright " + (!greenForIncrease ? "red" : "green")
                    + "=change >=+" + percentDiffForMaxScale + "%",
                    new Font("SansSerif", Font.PLAIN, 10));
            chart.addSubtitle(subtitle);
        }
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates a ring chart with default settings.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend  include legend?
     * @param tooltips  generate tooltips?
     * @param locale  the locale ({@code null} not permitted).
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
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates a ring chart with default settings.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend  include legend?
     * @param tooltips  generate tooltips?
     * @param urls  generate URLs?
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
        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }

    /**
     * Creates a chart that displays multiple pie plots.
     *
     * @param title  the chart title ({@code null} permitted).
     * @param dataset  the dataset ({@code null} permitted).
     * @param order  the order that data is extracted (by row or by column)
     * @param legend  include legend?
     * @param tooltips  generate tooltips?
     * @param urls  generate URLs?
     *
     * @return A chart.
     */
    public static JFreeChart createMultiplePieChart(String title,
                                                    CategoryDataset dataset, TableOrder order, boolean legend,
                                                    boolean tooltips, boolean urls) {

        Args.nullNotPermitted(order, "order");
        MultiplePiePlot plot = new MultiplePiePlot(dataset);
        plot.setDataExtractOrder(order);
        plot.setBackgroundPaint(null);
        plot.setOutlineStroke(null);

        if (tooltips) {
            PieToolTipGenerator tooltipGenerator
                    = new StandardPieToolTipGenerator();
            PiePlot pp = (PiePlot) plot.getPieChart().getPlot();
            pp.setToolTipGenerator(tooltipGenerator);
        }

        if (urls) {
            PieURLGenerator urlGenerator = new StandardPieURLGenerator();
            PiePlot pp = (PiePlot) plot.getPieChart().getPlot();
            pp.setURLGenerator(urlGenerator);
        }

        JFreeChart chart = new JFreeChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        currentTheme.apply(chart);
        return chart;
    }
}