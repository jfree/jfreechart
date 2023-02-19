package org.jfree.chart.charts;

import java.awt.Font;

import javax.swing.text.AttributeSet.ColorAttribute;

import org.jfree.chart.ChartColor;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.api.Layer;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.internal.Args;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.WaterfallBarRenderer;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;

public class WaterFallChart extends JFreeChart {
    public WaterFallChart(String title, Font titleFont, Plot plot, boolean createLegend) {
        super(title, titleFont, plot, createLegend);
        // TODO Auto-generated constructor stub
    }

    /**
     * Creates a waterfall chart. The chart object returned by this method
     * uses a {@link CategoryPlot} instance as the plot, with a
     * {@link CategoryAxis} for the domain axis, a {@link NumberAxis} as the
     * range axis, and a {@link WaterfallBarRenderer} as the renderer.
     *
     * @param title             the chart title ({@code null} permitted).
     * @param categoryAxisLabel the label for the category axis
     *                          ({@code null} permitted).
     * @param valueAxisLabel    the label for the value axis ({@code null}
     *                          permitted).
     * @param dataset           the dataset for the chart ({@code null} permitted).
     * @param orientation       the plot orientation (horizontal or vertical)
     *                          ({@code null} NOT permitted).
     * @param legend            a flag specifying whether or not a legend is
     *                          required.
     * @param tooltips          configure chart to generate tool tips?
     * @param urls              configure chart to generate URLs?
     *
     * @return A waterfall chart.
     */
    public static JFreeChart createWaterfallChart(String title,
            String categoryAxisLabel, String valueAxisLabel,
            CategoryDataset dataset, PlotOrientation orientation,
            boolean legend, boolean tooltips, boolean urls) {

        Args.nullNotPermitted(orientation, "orientation");
        CategoryAxis categoryAxis = new CategoryAxis(categoryAxisLabel);
        categoryAxis.setCategoryMargin(0.0);

        ValueAxis valueAxis = new NumberAxis(valueAxisLabel);

        WaterfallBarRenderer renderer = new WaterfallBarRenderer();
        if (orientation == PlotOrientation.HORIZONTAL) {
            ItemLabelPosition position = new ItemLabelPosition(
                    ItemLabelAnchor.CENTER, TextAnchor.CENTER,
                    TextAnchor.CENTER, Math.PI / 2.0);
            renderer.setDefaultPositiveItemLabelPosition(position);
            renderer.setDefaultNegativeItemLabelPosition(position);
        } else if (orientation == PlotOrientation.VERTICAL) {
            ItemLabelPosition position = new ItemLabelPosition(
                    ItemLabelAnchor.CENTER, TextAnchor.CENTER,
                    TextAnchor.CENTER, 0.0);
            renderer.setDefaultPositiveItemLabelPosition(position);
            renderer.setDefaultNegativeItemLabelPosition(position);
        }
        if (tooltips) {
            StandardCategoryToolTipGenerator generator = new StandardCategoryToolTipGenerator();
            renderer.setDefaultToolTipGenerator(generator);
        }
        if (urls) {
            renderer.setDefaultItemURLGenerator(
                    new StandardCategoryURLGenerator());
        }

        CategoryPlot plot = new CategoryPlot(dataset, categoryAxis, valueAxis,
                renderer);
        plot.clearRangeMarkers();
        Marker baseline = new ValueMarker(0.0);
        baseline.setPaint(ChartColor.BLACK);
        plot.addRangeMarker(baseline, Layer.FOREGROUND);
        plot.setOrientation(orientation);
        return new WaterFallChart(title, JFreeChart.DEFAULT_TITLE_FONT,
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
