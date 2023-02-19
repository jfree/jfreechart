package org.jfree.chart.charts;

import java.awt.Font;
import java.text.DateFormat;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;

public class GantChart extends JFreeChart {
        public GantChart(String title, Font titleFont, Plot plot, boolean createLegend) {
                super(title, titleFont, plot, createLegend);
        }

        /**
         * Creates a Gantt chart using the supplied attributes plus default values
         * where required. The chart object returned by this method uses a
         * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
         * for the domain axis, a {@link DateAxis} as the range axis, and a
         * {@link GanttRenderer} as the renderer.
         *
         * @param title             the chart title ({@code null} permitted).
         * @param categoryAxisLabel the label for the category axis
         *                          ({@code null} permitted).
         * @param dateAxisLabel     the label for the date axis
         *                          ({@code null} permitted).
         * @param dataset           the dataset for the chart ({@code null} permitted).
         *
         * @return A Gantt chart.
         */
        public static JFreeChart createGanttChart(String title,
                        String categoryAxisLabel, String dateAxisLabel,
                        IntervalCategoryDataset dataset) {
                return createGanttChart(title, categoryAxisLabel, dateAxisLabel,
                                dataset, true, true, false);
        }

        /**
         * Creates a Gantt chart using the supplied attributes plus default values
         * where required. The chart object returned by this method uses a
         * {@link CategoryPlot} instance as the plot, with a {@link CategoryAxis}
         * for the domain axis, a {@link DateAxis} as the range axis, and a
         * {@link GanttRenderer} as the renderer.
         *
         * @param title             the chart title ({@code null} permitted).
         * @param categoryAxisLabel the label for the category axis
         *                          ({@code null} permitted).
         * @param dateAxisLabel     the label for the date axis
         *                          ({@code null} permitted).
         * @param dataset           the dataset for the chart ({@code null} permitted).
         * @param legend            a flag specifying whether or not a legend is
         *                          required.
         * @param tooltips          configure chart to generate tool tips?
         * @param urls              configure chart to generate URLs?
         *
         * @return A Gantt chart.
         */
        public static JFreeChart createGanttChart(String title,
                        String categoryAxisLabel, String dateAxisLabel,
                        IntervalCategoryDataset dataset, boolean legend, boolean tooltips,
                        boolean urls) {

                CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
                DateAxis dateAxis = new DateAxis(dateAxisLabel);

                CategoryItemRenderer renderer = new GanttRenderer();
                if (tooltips) {
                        renderer.setDefaultToolTipGenerator(
                                        new IntervalCategoryToolTipGenerator(
                                                        "{3} - {4}", DateFormat.getDateInstance()));
                }
                if (urls) {
                        renderer.setDefaultItemURLGenerator(
                                        new StandardCategoryURLGenerator());
                }

                CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, dateAxis,
                                renderer);
                plot.setOrientation(PlotOrientation.HORIZONTAL);
                JFreeChart chart = new GantChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
                // TODO Auto-generated method stub
                return null;
        }

        @Override
        public JFreeChart createChart(String title, String timeAxisLabel, String valueAxisLabel, XYDataset dataset) {
                // TODO Auto-generated method stub
                return null;
        }
}
