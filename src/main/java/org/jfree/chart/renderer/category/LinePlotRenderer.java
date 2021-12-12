package org.jfree.chart.renderer.category;

import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.ShapeUtils;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;

public class LinePlotRenderer extends LineAndShapeRenderer
        implements Cloneable, PublicCloneable, Serializable {

    public LinePlotRenderer() {
        this(true, true);
    }

    public LinePlotRenderer(boolean lines, boolean shapes) {
        super();
        this.seriesLinesVisibleMap = new HashMap<>();
        this.defaultLinesVisible = lines;
        this.seriesShapesVisibleMap = new HashMap<>();
        this.defaultShapesVisible = shapes;
        this.seriesShapesFilledMap = new HashMap<>();
        this.defaultShapesFilled = true;
        this.useFillPaint = false;
        this.drawOutlines = true;
        this.useOutlinePaint = false;
        this.useSeriesOffset = false; // preserves old behaviour
        this.itemMargin = 0.0;
    }

    /**
     * Returns the flag used to control whether or not the line for an item is
     * visible.
     *
     * @param series the series index (zero-based).
     * @param item   the item index (zero-based).
     *
     * @return A boolean.
     */
    public boolean getItemLineVisible(int series, int item) {
        Boolean flag = getSeriesLinesVisible(series);
        if (flag != null) {
            return flag;
        }
        return this.defaultLinesVisible;
    }

    public void setUseFillPaint(boolean flag) {
        this.useFillPaint = flag;
        fireChangeEvent();
    }

    @Override
    public void drawItem(Graphics2D g2, CategoryItemRendererState state,
            Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
            ValueAxis rangeAxis, CategoryDataset dataset, int row, int column,
            int pass) {

        // do nothing if item is not visible
        if (!getItemVisible(row, column)) {
            return;
        }

        // nothing is drawn for null...
        Number v = dataset.getValue(row, column);
        if (v == null) {
            return;
        }

        int visibleRow = state.getVisibleSeriesIndex(row);
        if (visibleRow < 0) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();

        double value = v.doubleValue();
        double y1 = rangeAxis.valueToJava2D(value, dataArea,
                plot.getRangeAxisEdge());

        if (pass == 0 && getItemLineVisible(row, column)) {
            if (column != 0) {
                Number previousValue = dataset.getValue(row, column - 1);
                if (previousValue != null) {
                    // previous data point...
                    double previous = previousValue.doubleValue();
                    double x0;
                    if (this.useSeriesOffset) {
                        x0 = domainAxis.getCategorySeriesMiddle(
                                column - 1, dataset.getColumnCount(),
                                visibleRow, visibleRowCount,
                                this.itemMargin, dataArea,
                                plot.getDomainAxisEdge());
                    } else {
                        x0 = domainAxis.getCategoryMiddle(column - 1,
                                getColumnCount(), dataArea,
                                plot.getDomainAxisEdge());
                    }
                    double y0 = rangeAxis.valueToJava2D(previous, dataArea,
                            plot.getRangeAxisEdge());

                    Line2D line = null;
                    if (orientation == PlotOrientation.HORIZONTAL) {
                        line = new Line1D.Double(y0, y1);
                    } else if (orientation == PlotOrientation.VERTICAL) {
                        line = new Line1D.Double(y0, y1);
                    }
                    g2.setPaint(getItemPaint(row, column));
                    g2.setStroke(getItemStroke(row, column));
                    g2.draw(line);
                }
            }
        }
    }
}
