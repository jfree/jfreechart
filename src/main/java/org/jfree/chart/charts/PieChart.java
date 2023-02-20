package org.jfree.chart.charts;

import java.awt.Font;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.awt.Color;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.TableOrder;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.PieToolTipGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.pie.MultiplePiePlot;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.PieURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public class PieChart extends JFreeChart {

    public PieChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    public PieChart(PiePlot piePlot) {
        super(null, null, piePlot, true);
    }

    public PieChart() {
        super();
    }

    /**
     * Creates a pie chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend   a flag specifying whether or not a legend is required.
     * @param tooltips configure chart to generate tool tips?
     * @param locale   the locale ({@code null} not permitted).
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
        JFreeChart chart = new PieChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        return chart;

    }

    /**
     * Creates a pie chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     *
     * @param title   the chart title ({@code null} permitted).
     * @param dataset the dataset for the chart ({@code null} permitted).
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset) {
        return createPieChart(title, dataset, true, true, false);
    }

    /**
     * Creates a pie chart with default settings.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset for the chart ({@code null} permitted).
     * @param legend   a flag specifying whether or not a legend is required.
     * @param tooltips configure chart to generate tool tips?
     * @param urls     configure chart to generate URLs?
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
        JFreeChart chart = new PieChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);
        return chart;
    }

    /**
     * Creates a pie chart with default settings that compares 2 datasets.
     * The colour of each section will be determined by the move from the value
     * for the same key in {@code previousDataset}. ie if value1 &gt;
     * value2 then the section will be in green (unless
     * {@code greenForIncrease} is {@code false}, in which case it
     * would be {@code red}). Each section can have a shade of red or
     * green as the difference can be tailored between 0% (black) and
     * percentDiffForMaxScale% (bright red/green).
     * <p>
     * For instance if {@code percentDiffForMaxScale} is 10 (10%), a
     * difference of 5% will have a half shade of red/green, a difference of
     * 10% or more will have a maximum shade/brightness of red/green.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     * <p>
     * Written by <a href="mailto:opensource@objectlab.co.uk">Benoit
     * Xhenseval</a>.
     *
     * @param title                  the chart title ({@code null} permitted).
     * @param dataset                the dataset for the chart ({@code null}
     *                               permitted).
     * @param previousDataset        the dataset for the last run, this will be used
     *                               to compare each key in the dataset
     * @param percentDiffForMaxScale scale goes from bright red/green to black,
     *                               percentDiffForMaxScale indicate the change
     *                               required to reach top scale.
     * @param greenForIncrease       an increase since previousDataset will be
     *                               displayed in green (decrease red) if true.
     * @param legend                 a flag specifying whether or not a legend is
     *                               required.
     * @param tooltips               configure chart to generate tool tips?
     * @param locale                 the locale ({@code null} not permitted).
     * @param subTitle               displays a subtitle with colour scheme if true
     * @param showDifference         create a new dataset that will show the %
     *                               difference between the two datasets.
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
                } else {
                    plot.setSectionPaint(key, Color.RED);
                }
                if (showDifference) {
                    assert series != null; // suppresses compiler warning
                    series.setValue(key + " (+100%)", newValue);
                }
            } else {
                double percentChange = (newValue.doubleValue()
                        / oldValue.doubleValue() - 1.0) * 100.0;
                double shade = (Math.abs(percentChange) >= percentDiffForMaxScale ? 255
                        : Math.abs(percentChange) * colorPerPercent);
                if (greenForIncrease
                        && newValue.doubleValue() > oldValue.doubleValue()
                        || !greenForIncrease && newValue.doubleValue() < oldValue.doubleValue()) {
                    plot.setSectionPaint(key, new Color(0, (int) shade, 0));
                } else {
                    plot.setSectionPaint(key, new Color((int) shade, 0, 0));
                }
                if (showDifference) {
                    assert series != null; // suppresses compiler warning
                    series.setValue(key + " (" + (percentChange >= 0 ? "+" : "")
                            + NumberFormat.getPercentInstance().format(
                                    percentChange / 100.0)
                            + ")", newValue);
                }
            }
        }

        if (showDifference) {
            plot.setDataset(series);
        }

        JFreeChart chart = new PieChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

        if (subTitle) {
            TextTitle subtitle = new TextTitle("Bright " + (greenForIncrease
                    ? "red"
                    : "green") + "=change >=-" + percentDiffForMaxScale
                    + "%, Bright " + (!greenForIncrease ? "red" : "green")
                    + "=change >=+" + percentDiffForMaxScale + "%",
                    new Font("SansSerif", Font.PLAIN, 10));
            chart.addSubtitle(subtitle);
        }
        return chart;
    }

    /**
     * Creates a pie chart with default settings that compares 2 datasets.
     * The colour of each section will be determined by the move from the value
     * for the same key in {@code previousDataset}. ie if value1 &gt;
     * value2 then the section will be in green (unless
     * {@code greenForIncrease} is {@code false}, in which case it
     * would be {@code red}). Each section can have a shade of red or
     * green as the difference can be tailored between 0% (black) and
     * percentDiffForMaxScale% (bright red/green).
     * <p>
     * For instance if {@code percentDiffForMaxScale} is 10 (10%), a
     * difference of 5% will have a half shade of red/green, a difference of
     * 10% or more will have a maximum shade/brightness of red/green.
     * <P>
     * The chart object returned by this method uses a {@link PiePlot} instance
     * as the plot.
     * <p>
     * Written by <a href="mailto:opensource@objectlab.co.uk">Benoit
     * Xhenseval</a>.
     *
     * @param title                  the chart title ({@code null} permitted).
     * @param dataset                the dataset for the chart ({@code null}
     *                               permitted).
     * @param previousDataset        the dataset for the last run, this will be used
     *                               to compare each key in the dataset
     * @param percentDiffForMaxScale scale goes from bright red/green to black,
     *                               percentDiffForMaxScale indicate the change
     *                               required to reach top scale.
     * @param greenForIncrease       an increase since previousDataset will be
     *                               displayed in green (decrease red) if true.
     * @param legend                 a flag specifying whether or not a legend is
     *                               required.
     * @param tooltips               configure chart to generate tool tips?
     * @param urls                   configure chart to generate URLs?
     * @param subTitle               displays a subtitle with colour scheme if true
     * @param showDifference         create a new dataset that will show the %
     *                               difference between the two datasets.
     *
     * @return A pie chart.
     */
    public static JFreeChart createPieChart(String title, PieDataset dataset,
            PieDataset previousDataset, int percentDiffForMaxScale,
            boolean greenForIncrease, boolean legend, boolean tooltips,
            boolean urls, boolean subTitle, boolean showDifference) {

        PiePlot plot = new PiePlot(dataset);
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator());
        plot.setInsets(new RectangleInsets(0.0, 5.0, 5.0, 5.0));

        if (tooltips) {
            plot.setToolTipGenerator(new StandardPieToolTipGenerator());
        }
        if (urls) {
            plot.setURLGenerator(new StandardPieURLGenerator());
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
                } else {
                    plot.setSectionPaint(key, Color.RED);
                }
                if (showDifference) {
                    assert series != null; // suppresses compiler warning
                    series.setValue(key + " (+100%)", newValue);
                }
            } else {
                double percentChange = (newValue.doubleValue()
                        / oldValue.doubleValue() - 1.0) * 100.0;
                double shade = (Math.abs(percentChange) >= percentDiffForMaxScale ? 255
                        : Math.abs(percentChange) * colorPerPercent);
                if (greenForIncrease
                        && newValue.doubleValue() > oldValue.doubleValue()
                        || !greenForIncrease && newValue.doubleValue() < oldValue.doubleValue()) {
                    plot.setSectionPaint(key, new Color(0, (int) shade, 0));
                } else {
                    plot.setSectionPaint(key, new Color((int) shade, 0, 0));
                }
                if (showDifference) {
                    assert series != null; // suppresses compiler warning
                    series.setValue(key + " (" + (percentChange >= 0 ? "+" : "")
                            + NumberFormat.getPercentInstance().format(
                                    percentChange / 100.0)
                            + ")", newValue);
                }
            }
        }

        if (showDifference) {
            plot.setDataset(series);
        }

        JFreeChart chart = new PieChart(title,
                JFreeChart.DEFAULT_TITLE_FONT, plot, legend);

        if (subTitle) {
            TextTitle subtitle = new TextTitle("Bright " + (greenForIncrease
                    ? "red"
                    : "green") + "=change >=-" + percentDiffForMaxScale
                    + "%, Bright " + (!greenForIncrease ? "red" : "green")
                    + "=change >=+" + percentDiffForMaxScale + "%",
                    new Font("SansSerif", Font.PLAIN, 10));
            chart.addSubtitle(subtitle);
        }
        return chart;
    }

    /**
     * Creates a chart that displays multiple pie plots. The chart object
     * returned by this method uses a {@link MultiplePiePlot} instance as the
     * plot.
     *
     * @param title    the chart title ({@code null} permitted).
     * @param dataset  the dataset ({@code null} permitted).
     * @param order    the order that the data is extracted (by row or by column)
     *                 ({@code null} not permitted).
     * @param legend   include a legend?
     * @param tooltips generate tooltips?
     * @param urls     generate URLs?
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
            PieToolTipGenerator tooltipGenerator = new StandardPieToolTipGenerator();
            PiePlot pp = (PiePlot) plot.getPieChart().getPlot();
            pp.setToolTipGenerator(tooltipGenerator);
        }

        if (urls) {
            PieURLGenerator urlGenerator = new StandardPieURLGenerator();
            PiePlot pp = (PiePlot) plot.getPieChart().getPlot();
            pp.setURLGenerator(urlGenerator);
        }

        JFreeChart chart = new PieChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
    public JFreeChart createChart(String title, PieDataset dataset, boolean legend, boolean tooltips,
            boolean urls) {
        return createPieChart(title, dataset);
    }

    @Override
    public JFreeChart createChart(String title, String timeAxisLabel, String valueAxisLabel, XYDataset dataset) {
        // TODO Auto-generated method stub
        return null;
    }

}
