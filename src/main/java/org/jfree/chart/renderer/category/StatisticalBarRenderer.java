/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ---------------------------
 * StatisticalBarRenderer.java
 * ---------------------------
 * (C) Copyright 2002-2022, by Pascal Collet and Contributors.
 *
 * Original Author:  Pascal Collet;
 * Contributor(s):   David Gilbert;
 *                   Christian W. Zuckschwerdt;
 *                   Peter Kolb (patches 2497611, 2791407);
 *                   Martin Hoeller;
 *
 */

package org.jfree.chart.renderer.category;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;

/**
 * A renderer that handles the drawing a bar plot where
 * each bar has a mean value and a standard deviation line.  The example shown
 * here is generated by the {@code StatisticalBarChartDemo1.java} program
 * included in the JFreeChart Demo Collection:
 * <br><br>
 * <img src="doc-files/StatisticalBarRendererSample.png"
 * alt="StatisticalBarRendererSample.png">
 */
public class StatisticalBarRenderer extends BarRenderer
        implements CategoryItemRenderer, Cloneable, PublicCloneable,
                   Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4986038395414039117L;

    /** The paint used to show the error indicator. */
    private transient Paint errorIndicatorPaint;

    /**
     * The stroke used to draw the error indicators.
     */
    private transient Stroke errorIndicatorStroke;

    /**
     * Default constructor.
     */
    public StatisticalBarRenderer() {
        super();
        this.errorIndicatorPaint = Color.GRAY;
        this.errorIndicatorStroke = new BasicStroke(1.0f);
    }

    /**
     * Returns the paint used for the error indicators.
     *
     * @return The paint used for the error indicators (possibly
     *         {@code null}).
     *
     * @see #setErrorIndicatorPaint(Paint)
     */
    public Paint getErrorIndicatorPaint() {
        return this.errorIndicatorPaint;
    }

    /**
     * Sets the paint used for the error indicators (if {@code null},
     * the item outline paint is used instead) and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getErrorIndicatorPaint()
     */
    public void setErrorIndicatorPaint(Paint paint) {
        this.errorIndicatorPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the error indicators.  If this is
     * {@code null}, the renderer will use the item outline stroke).
     *
     * @return The stroke (possibly {@code null}).
     *
     * @see #setErrorIndicatorStroke(Stroke)
     */
    public Stroke getErrorIndicatorStroke() {
        return this.errorIndicatorStroke;
    }

    /**
     * Sets the stroke used to draw the error indicators, and sends a
     * {@link RendererChangeEvent} to all registered listeners.  If you set
     * this to {@code null}, the renderer will use the item outline
     * stroke.
     *
     * @param stroke  the stroke ({@code null} permitted).
     *
     * @see #getErrorIndicatorStroke()
     */
    public void setErrorIndicatorStroke(Stroke stroke) {
        this.errorIndicatorStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset. This takes into account the range
     * between the min/max values, possibly ignoring invisible series.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range (or {@code null} if the dataset is
     *         {@code null} or empty).
     */
    @Override
    public Range findRangeBounds(CategoryDataset dataset) {
         return findRangeBounds(dataset, true);
    }

    /**
     * Draws the bar with its standard deviation line range for a single
     * (series, category) data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the data area.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param data  the data.
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param pass  the pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, CategoryItemRendererState state,
            Rectangle2D dataArea, CategoryPlot plot, CategoryAxis domainAxis,
            ValueAxis rangeAxis, CategoryDataset data, int row, int column,
            int pass) {

        int visibleRow = state.getVisibleSeriesIndex(row);
        if (visibleRow < 0) {
            return;
        }
        // defensive check
        if (!(data instanceof StatisticalCategoryDataset)) {
            throw new IllegalArgumentException(
                "Requires StatisticalCategoryDataset.");
        }
        StatisticalCategoryDataset statData = (StatisticalCategoryDataset) data;

        PlotOrientation orientation = plot.getOrientation();
        if (orientation == PlotOrientation.HORIZONTAL) {
            drawHorizontalItem(g2, state, dataArea, plot, domainAxis,
                    rangeAxis, statData, visibleRow, row, column);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis,
                    statData, visibleRow, row, column);
        }
    }

    /**
     * Draws an item for a plot with a horizontal orientation.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the data area.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the data.
     * @param visibleRow  the visible row index.
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     */
    protected void drawHorizontalItem(Graphics2D g2,
                                      CategoryItemRendererState state,
                                      Rectangle2D dataArea,
                                      CategoryPlot plot,
                                      CategoryAxis domainAxis,
                                      ValueAxis rangeAxis,
                                      StatisticalCategoryDataset dataset,
                                      int visibleRow,
                                      int row,
                                      int column) {

        // BAR Y
        double rectY = calculateBarW0(plot, PlotOrientation.HORIZONTAL, 
                dataArea, domainAxis, state, visibleRow, column);

        // BAR X
        Number meanValue = dataset.getMeanValue(row, column);
        if (meanValue == null) {
            return;
        }
        double value = meanValue.doubleValue();
        double base = 0.0;
        double lclip = getLowerClip();
        double uclip = getUpperClip();

        if (uclip <= 0.0) {  // cases 1, 2, 3 and 4
            if (value >= uclip) {
                return; // bar is not visible
            }
            base = uclip;
            if (value <= lclip) {
                value = lclip;
            }
        }
        else if (lclip <= 0.0) { // cases 5, 6, 7 and 8
            if (value >= uclip) {
                value = uclip;
            }
            else {
                if (value <= lclip) {
                    value = lclip;
                }
            }
        }
        else { // cases 9, 10, 11 and 12
            if (value <= lclip) {
                return; // bar is not visible
            }
            base = getLowerClip();
            if (value >= uclip) {
               value = uclip;
            }
        }

        RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
        double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
        double transY2 = rangeAxis.valueToJava2D(value, dataArea,
                yAxisLocation);
        double rectX = Math.min(transY2, transY1);

        double rectHeight = state.getBarWidth();
        double rectWidth = Math.abs(transY2 - transY1);

        Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth,
                rectHeight);
        Paint itemPaint = getItemPaint(row, column);
        GradientPaintTransformer t = getGradientPaintTransformer();
        if (t != null && itemPaint instanceof GradientPaint) {
            itemPaint = t.transform((GradientPaint) itemPaint, bar);
        }
        g2.setPaint(itemPaint);
        g2.fill(bar);

        // draw the outline...
        if (isDrawBarOutline()
                && state.getBarWidth() > BAR_OUTLINE_WIDTH_THRESHOLD) {
            Stroke stroke = getItemOutlineStroke(row, column);
            Paint paint = getItemOutlinePaint(row, column);
            if (stroke != null && paint != null) {
                g2.setStroke(stroke);
                g2.setPaint(paint);
                g2.draw(bar);
            }
        }

        // standard deviation lines
        Number n = dataset.getStdDevValue(row, column);
        if (n != null) {
            double valueDelta = n.doubleValue();
            double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue()
                    + valueDelta, dataArea, yAxisLocation);
            double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue()
                    - valueDelta, dataArea, yAxisLocation);

            if (this.errorIndicatorPaint != null) {
                g2.setPaint(this.errorIndicatorPaint);
            }
            else {
                g2.setPaint(getItemOutlinePaint(row, column));
            }
            if (this.errorIndicatorStroke != null) {
                g2.setStroke(this.errorIndicatorStroke);
            }
            else {
                g2.setStroke(getItemOutlineStroke(row, column));
            }
            Line2D line;
            line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0d,
                                     highVal, rectY + rectHeight / 2.0d);
            g2.draw(line);
            line = new Line2D.Double(highVal, rectY + rectHeight * 0.25,
                                     highVal, rectY + rectHeight * 0.75);
            g2.draw(line);
            line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25,
                                     lowVal, rectY + rectHeight * 0.75);
            g2.draw(line);
        }

        CategoryItemLabelGenerator generator = getItemLabelGenerator(row,
                column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar,
                    (value < 0.0));
        }

        // add an item entity, if this information is being collected
        EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, bar);
        }

    }

    /**
     * Draws an item for a plot with a vertical orientation.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the data area.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the data.
     * @param visibleRow  the visible row index.
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     */
    protected void drawVerticalItem(Graphics2D g2,
                                    CategoryItemRendererState state,
                                    Rectangle2D dataArea,
                                    CategoryPlot plot,
                                    CategoryAxis domainAxis,
                                    ValueAxis rangeAxis,
                                    StatisticalCategoryDataset dataset,
                                    int visibleRow,
                                    int row,
                                    int column) {

        // BAR X
        double rectX = calculateBarW0(plot, PlotOrientation.VERTICAL, dataArea,
                domainAxis, state, visibleRow, column);

        // BAR Y
        Number meanValue = dataset.getMeanValue(row, column);
        if (meanValue == null) {
            return;
        }

        double value = meanValue.doubleValue();
        double base = 0.0;
        double lclip = getLowerClip();
        double uclip = getUpperClip();

        if (uclip <= 0.0) {  // cases 1, 2, 3 and 4
            if (value >= uclip) {
                return; // bar is not visible
            }
            base = uclip;
            if (value <= lclip) {
                value = lclip;
            }
        }
        else if (lclip <= 0.0) { // cases 5, 6, 7 and 8
            if (value >= uclip) {
                value = uclip;
            }
            else {
                if (value <= lclip) {
                    value = lclip;
                }
            }
        }
        else { // cases 9, 10, 11 and 12
            if (value <= lclip) {
                return; // bar is not visible
            }
            base = getLowerClip();
            if (value >= uclip) {
               value = uclip;
            }
        }

        RectangleEdge yAxisLocation = plot.getRangeAxisEdge();
        double transY1 = rangeAxis.valueToJava2D(base, dataArea, yAxisLocation);
        double transY2 = rangeAxis.valueToJava2D(value, dataArea,
                yAxisLocation);
        double rectY = Math.min(transY2, transY1);

        double rectWidth = state.getBarWidth();
        double rectHeight = Math.abs(transY2 - transY1);

        Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth,
                rectHeight);
        Paint itemPaint = getItemPaint(row, column);
        GradientPaintTransformer t = getGradientPaintTransformer();
        if (t != null && itemPaint instanceof GradientPaint) {
            itemPaint = t.transform((GradientPaint) itemPaint, bar);
        }
        g2.setPaint(itemPaint);
        g2.fill(bar);
        // draw the outline...
        if (isDrawBarOutline()
                && state.getBarWidth() > BAR_OUTLINE_WIDTH_THRESHOLD) {
            Stroke stroke = getItemOutlineStroke(row, column);
            Paint paint = getItemOutlinePaint(row, column);
            if (stroke != null && paint != null) {
                g2.setStroke(stroke);
                g2.setPaint(paint);
                g2.draw(bar);
            }
        }

        // standard deviation lines
        Number n = dataset.getStdDevValue(row, column);
        if (n != null) {
            double valueDelta = n.doubleValue();
            double highVal = rangeAxis.valueToJava2D(meanValue.doubleValue()
                    + valueDelta, dataArea, yAxisLocation);
            double lowVal = rangeAxis.valueToJava2D(meanValue.doubleValue()
                    - valueDelta, dataArea, yAxisLocation);

            if (this.errorIndicatorPaint != null) {
                g2.setPaint(this.errorIndicatorPaint);
            }
            else {
                g2.setPaint(getItemOutlinePaint(row, column));
            }
            if (this.errorIndicatorStroke != null) {
                g2.setStroke(this.errorIndicatorStroke);
            }
            else {
                g2.setStroke(getItemOutlineStroke(row, column));
            }

            Line2D line;
            line = new Line2D.Double(rectX + rectWidth / 2.0d, lowVal,
                                     rectX + rectWidth / 2.0d, highVal);
            g2.draw(line);
            line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, highVal,
                                     rectX + rectWidth / 2.0d + 5.0d, highVal);
            g2.draw(line);
            line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, lowVal,
                                     rectX + rectWidth / 2.0d + 5.0d, lowVal);
            g2.draw(line);
        }

        CategoryItemLabelGenerator generator = getItemLabelGenerator(row,
                column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar,
                    (value < 0.0));
        }

        // add an item entity, if this information is being collected
        EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, bar);
        }
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StatisticalBarRenderer)) {
            return false;
        }
        StatisticalBarRenderer that = (StatisticalBarRenderer) obj;
        if (!PaintUtils.equal(this.errorIndicatorPaint,
                that.errorIndicatorPaint)) {
            return false;
        }
        if (!Objects.equals(this.errorIndicatorStroke,
                that.errorIndicatorStroke)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.errorIndicatorPaint, stream);
        SerialUtils.writeStroke(this.errorIndicatorStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.errorIndicatorPaint = SerialUtils.readPaint(stream);
        this.errorIndicatorStroke = SerialUtils.readStroke(stream);
    }

}
