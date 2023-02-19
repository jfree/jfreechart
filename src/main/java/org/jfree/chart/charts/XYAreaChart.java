package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StackedXYAreaRenderer2;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.urls.StandardXYURLGenerator;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;

public class XYAreaChart extends JFreeChart {

    public XYAreaChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates an area chart using an {@link XYDataset}.
     * <P>
     * The chart object returned by this method uses an {@link XYPlot} instance
     * as the plot, with a {@link NumberAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link XYAreaRenderer} as
     * the renderer.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel a label for the Y-axis ({@code null} permitted).
     * @param dataset    the dataset for the chart ({@code null} permitted).
     *
     * @return An XY area chart.
     * 
     * @param <S> the type for series keys.
     */
    public static <S extends Comparable<S>> JFreeChart createXYAreaChart(
            String title, String xAxisLabel, String yAxisLabel,
            XYDataset<S> dataset) {
        return createXYAreaChart(title, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates an area chart using an {@link XYDataset}.
     * <P>
     * The chart object returned by this method uses an {@link XYPlot} instance
     * as the plot, with a {@link NumberAxis} for the domain axis, a
     * {@link NumberAxis} as the range axis, and a {@link XYAreaRenderer} as
     * the renderer.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param xAxisLabel  a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel  a label for the Y-axis ({@code null} permitted).
     * @param dataset     the dataset for the chart ({@code null} permitted).
     * @param orientation the plot orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted).
     * @param legend      a flag specifying whether or not a legend is required.
     * @param tooltips    configure chart to generate tool tips?
     * @param urls        configure chart to generate URLs?
     *
     * @param <S>         the type for series keys.
     * 
     * @return An XY area chart.
     */
    public static <S extends Comparable<S>> JFreeChart createXYAreaChart(
            String title, String xAxisLabel,
            String yAxisLabel, XYDataset<S> dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYPlot<S> plot = new XYPlot<>(dataset, xAxis, yAxis, null);
        plot.setOrientation(orientation);
        plot.setForegroundAlpha(0.5f);

        XYToolTipGenerator tipGenerator = null;
        if (tooltips) {
            tipGenerator = new StandardXYToolTipGenerator();
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }

        plot.setRenderer(new XYAreaRenderer(XYAreaRenderer.AREA, tipGenerator,
                urlGenerator));

        return new XYAreaChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

    }

    /**
     * Creates a stacked XY area plot. The chart object returned by this
     * method uses an {@link XYPlot} instance as the plot, with a
     * {@link NumberAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and a {@link StackedXYAreaRenderer2} as the renderer.
     *
     * @param title      the chart title ({@code null} permitted).
     * @param xAxisLabel a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel a label for the Y-axis ({@code null} permitted).
     * @param dataset    the dataset for the chart ({@code null} permitted).
     *
     * @return A stacked XY area chart.
     */
    public static JFreeChart createStackedXYAreaChart(String title,
            String xAxisLabel, String yAxisLabel, TableXYDataset dataset) {
        return createStackedXYAreaChart(title, xAxisLabel, yAxisLabel,
                dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    /**
     * Creates a stacked XY area plot. The chart object returned by this
     * method uses an {@link XYPlot} instance as the plot, with a
     * {@link NumberAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and a {@link StackedXYAreaRenderer2} as the renderer.
     *
     * @param title       the chart title ({@code null} permitted).
     * @param xAxisLabel  a label for the X-axis ({@code null} permitted).
     * @param yAxisLabel  a label for the Y-axis ({@code null} permitted).
     * @param dataset     the dataset for the chart ({@code null} permitted).
     * @param orientation the plot orientation (horizontal or vertical)
     *                    ({@code null} NOT permitted).
     * @param legend      a flag specifying whether or not a legend is required.
     * @param tooltips    configure chart to generate tool tips?
     * @param urls        configure chart to generate URLs?
     *
     * @return A stacked XY area chart.
     */
    public static JFreeChart createStackedXYAreaChart(String title,
            String xAxisLabel, String yAxisLabel, TableXYDataset dataset,
            PlotOrientation orientation, boolean legend, boolean tooltips,
            boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        NumberAxis xAxis = new NumberAxis(xAxisLabel);
        xAxis.setAutoRangeIncludesZero(false);
        xAxis.setLowerMargin(0.0);
        xAxis.setUpperMargin(0.0);
        NumberAxis yAxis = new NumberAxis(yAxisLabel);
        XYToolTipGenerator toolTipGenerator = null;
        if (tooltips) {
            toolTipGenerator = new StandardXYToolTipGenerator();
        }

        XYURLGenerator urlGenerator = null;
        if (urls) {
            urlGenerator = new StandardXYURLGenerator();
        }
        StackedXYAreaRenderer2 renderer = new StackedXYAreaRenderer2(
                toolTipGenerator, urlGenerator);
        renderer.setOutline(true);
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer);
        plot.setOrientation(orientation);

        plot.setRangeAxis(yAxis); // forces recalculation of the axis range

        return new XYAreaChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                plot, legend);

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
