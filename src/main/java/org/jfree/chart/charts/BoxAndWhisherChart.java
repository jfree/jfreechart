package org.jfree.chart.charts;

import java.awt.Font;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.xy.XYBoxAndWhiskerRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.xy.XYDataset;

public class BoxAndWhisherChart extends JFreeChart {

        public BoxAndWhisherChart(String title, Font titleFont, Plot plot, boolean createLegend) {
                super(title, titleFont, plot, createLegend);
        }

        /**
         * Creates and returns a default instance of a box and whisker chart
         * based on data from a {@link BoxAndWhiskerCategoryDataset}.
         *
         * @param title             the chart title ({@code null} permitted).
         * @param categoryAxisLabel a label for the category axis
         *                          ({@code null} permitted).
         * @param valueAxisLabel    a label for the value axis ({@code null}
         *                          permitted).
         * @param dataset           the dataset for the chart ({@code null} permitted).
         * @param legend            a flag specifying whether or not a legend is
         *                          required.
         *
         * @return A box and whisker chart.
         */
        public static JFreeChart createBoxAndWhiskerChart(String title,
                        String categoryAxisLabel, String valueAxisLabel,
                        BoxAndWhiskerCategoryDataset dataset, boolean legend) {

                CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
                NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
                valueAxis.setAutoRangeIncludesZero(false);

                BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
                renderer.setDefaultToolTipGenerator(new BoxAndWhiskerToolTipGenerator());

                CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                                renderer);
                JFreeChart chart = new BoxAndWhisherChart(title, JFreeChart.DEFAULT_TITLE_FONT,
                                plot, legend);
                return chart;
        }

        /**
         * Creates and returns a default instance of a box and whisker chart.
         *
         * @param title          the chart title ({@code null} permitted).
         * @param timeAxisLabel  a label for the time axis ({@code null}
         *                       permitted).
         * @param valueAxisLabel a label for the value axis ({@code null}
         *                       permitted).
         * @param dataset        the dataset for the chart ({@code null} permitted).
         * @param legend         a flag specifying whether or not a legend is required.
         *
         * @return A box and whisker chart.
         */
        public static JFreeChart createBoxAndWhiskerChart(String title,
                        String timeAxisLabel, String valueAxisLabel,
                        BoxAndWhiskerXYDataset dataset, boolean legend) {

                ValueAxis timeAxis = new DateAxis(timeAxisLabel);
                NumberAxis valueAxis = new NumberAxis(valueAxisLabel);
                valueAxis.setAutoRangeIncludesZero(false);
                XYBoxAndWhiskerRenderer renderer = new XYBoxAndWhiskerRenderer(10.0);
                XYPlot plot = new XYPlot(dataset, timeAxis, valueAxis, renderer);
                JFreeChart chart = new BoxAndWhisherChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
