package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class BarChart extends JFreeChart {

        public BarChart(String title, Font titleFont, Plot plot, boolean createLegend) {
                super(title, titleFont, plot, createLegend);

        }

        public BarChart() {
                super();
        }

        public BarChart(Plot plot) {
                super(plot);
        }

        /**
         * Creates a bar chart with a vertical orientation. The chart object
         * returned by this method uses a {@link CategoryPlot} instance as the
         * plot, with a {@link CategoryAxis} for the domain axis, a
         * {@link NumberAxis} as the range axis, and a {@link BarRenderer} as the
         * renderer.
         *
         * @param title             the chart title ({@code null} permitted).
         * @param categoryAxisLabel the label for the category axis
         *                          ({@code null} permitted).
         * @param valueAxisLabel    the label for the value axis
         *                          ({@code null} permitted).
         * @param dataset           the dataset for the chart ({@code null} permitted).
         *
         * @return A bar chart.
         */
        public static JFreeChart createBarChart(String title,
                        String categoryAxisLabel, String valueAxisLabel,
                        CategoryDataset dataset) {
                return createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset,
                                PlotOrientation.VERTICAL, true, true, false);
        }

        /**
         * Creates a bar chart. The chart object returned by this method uses a
         * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
         * for the domain axis, a {@link NumberAxis} as the range axis, and a
         * {@link BarRenderer} as the renderer.
         *
         * @param title             the chart title ({@code null} permitted).
         * @param categoryAxisLabel the label for the category axis
         *                          ({@code null} permitted).
         * @param valueAxisLabel    the label for the value axis
         *                          ({@code null} permitted).
         * @param dataset           the dataset for the chart ({@code null} permitted).
         * @param orientation       the plot orientation (horizontal or vertical)
         *                          ({@code null} not permitted).
         * @param legend            a flag specifying whether or not a legend is
         *                          required.
         * @param tooltips          configure chart to generate tool tips?
         * @param urls              configure chart to generate URLs?
         *
         * @return A bar chart.
         */
        public static JFreeChart createBarChart(String title,
                        String categoryAxisLabel, String valueAxisLabel,
                        CategoryDataset dataset, PlotOrientation orientation,
                        boolean legend, boolean tooltips, boolean urls) {

                Args.nullNotPermitted(orientation, "orientation");
                CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
                ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

                BarRenderer renderer = new BarRenderer();
                if (orientation == PlotOrientation.HORIZONTAL) {
                        ItemLabelPosition position1 = new ItemLabelPosition(
                                        ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
                        renderer.setDefaultPositiveItemLabelPosition(position1);
                        ItemLabelPosition position2 = new ItemLabelPosition(
                                        ItemLabelAnchor.OUTSIDE9, TextAnchor.CENTER_RIGHT);
                        renderer.setDefaultNegativeItemLabelPosition(position2);
                } else if (orientation == PlotOrientation.VERTICAL) {
                        ItemLabelPosition position1 = new ItemLabelPosition(
                                        ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER);
                        renderer.setDefaultPositiveItemLabelPosition(position1);
                        ItemLabelPosition position2 = new ItemLabelPosition(
                                        ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_CENTER);
                        renderer.setDefaultNegativeItemLabelPosition(position2);
                }
                if (tooltips) {
                        renderer.setDefaultToolTipGenerator(
                                        new StandardCategoryToolTipGenerator());
                }
                if (urls) {
                        renderer.setDefaultItemURLGenerator(
                                        new StandardCategoryURLGenerator());
                }

                CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                                renderer);
                plot.setOrientation(orientation);
                JFreeChart chart = new BarChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                                plot, legend);
                return chart;

        }

        /**
         * Creates a stacked bar chart with default settings. The chart object
         * returned by this method uses a {@link CategoryPlot} instance as the
         * plot, with a {@link CategoryAxis} for the domain axis, a
         * {@link NumberAxis} as the range axis, and a {@link StackedBarRenderer}
         * as the renderer.
         *
         * @param title           the chart title ({@code null} permitted).
         * @param domainAxisLabel the label for the category axis
         *                        ({@code null} permitted).
         * @param rangeAxisLabel  the label for the value axis
         *                        ({@code null} permitted).
         * @param dataset         the dataset for the chart ({@code null} permitted).
         *
         * @return A stacked bar chart.
         */
        public static JFreeChart createStackedBarChart(String title,
                        String domainAxisLabel, String rangeAxisLabel,
                        CategoryDataset dataset) {
                return createStackedBarChart(title, domainAxisLabel, rangeAxisLabel,
                                dataset, PlotOrientation.VERTICAL, true, true, false);
        }

        /**
         * Creates a stacked bar chart with default settings. The chart object
         * returned by this method uses a {@link CategoryPlot} instance as the
         * plot, with a {@link CategoryAxis} for the domain axis, a
         * {@link NumberAxis} as the range axis, and a {@link StackedBarRenderer}
         * as the renderer.
         *
         * @param title           the chart title ({@code null} permitted).
         * @param domainAxisLabel the label for the category axis
         *                        ({@code null} permitted).
         * @param rangeAxisLabel  the label for the value axis
         *                        ({@code null} permitted).
         * @param dataset         the dataset for the chart ({@code null} permitted).
         * @param orientation     the orientation of the chart (horizontal or
         *                        vertical) ({@code null} not permitted).
         * @param legend          a flag specifying whether or not a legend is required.
         * @param tooltips        configure chart to generate tool tips?
         * @param urls            configure chart to generate URLs?
         *
         * @return A stacked bar chart.
         */
        public static JFreeChart createStackedBarChart(String title,
                        String domainAxisLabel, String rangeAxisLabel,
                        CategoryDataset dataset, PlotOrientation orientation,
                        boolean legend, boolean tooltips, boolean urls) {

                Args.nullNotPermitted(orientation, "orientation");

                CategoryAxis categoryAxis = new CategoryAxis(domainAxisLabel);
                ValueAxis valueAxis = new NumberAxis(rangeAxisLabel);

                StackedBarRenderer renderer = new StackedBarRenderer();
                if (tooltips) {
                        renderer.setDefaultToolTipGenerator(
                                        new StandardCategoryToolTipGenerator());
                }
                if (urls) {
                        renderer.setDefaultItemURLGenerator(
                                        new StandardCategoryURLGenerator());
                }

                CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                                renderer);
                plot.setOrientation(orientation);
                JFreeChart chart = new BarChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                                plot, legend);
                return chart;

        }

        @Override
        public BarChart createChart(String title, String categoryAxisLabel, String valueAxisLabel,
                        DefaultCategoryDataset dataset) {
                return (BarChart) createBarChart(title, categoryAxisLabel, valueAxisLabel, dataset);
        }

        @Override
        public JFreeChart createChart(String title, PieDataset dataset, boolean legend, boolean tooltips,
                        boolean urls) {
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public JFreeChart createChart(String title, String timeAxisLabel, String valueAxisLabel,
                        TimeSeriesCollection dataset) {
                // TODO Auto-generated method stub
                return null;
        }

}
