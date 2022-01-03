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
 * ------------------
 * SpiderWebPlot.java
 * ------------------
 * (C) Copyright 2005-2021, by Heaps of Flavour Pty Ltd and Contributors.
 *
 * Company Info:  http://www.i4-talent.com
 *
 * Original Author:  Don Elliott;
 * Contributor(s):   David Gilbert;
 *                   Nina Jeliazkova;
 *
 */

package org.jfree.chart.plot;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.Rotation;
import org.jfree.chart.api.TableOrder;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.internal.*;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.legend.LegendItem;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.urls.CategoryURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetUtils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.*;

/**
 * A plot that displays data from a {@link CategoryDataset} in the form of a
 * "spider web".  Multiple series can be plotted on the same axis to allow
 * easy comparison.  This plot doesn't support negative values at present.
 */
public class SpiderWebPlot extends Plot implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -5376340422031599463L;

    /** The default head radius percent (currently 1%). */
    public static final double DEFAULT_HEAD = 0.01;

    /** The default axis label gap (currently 10%). */
    public static final double DEFAULT_AXIS_LABEL_GAP = 0.10;

    /** The default interior gap. */
    public static final double DEFAULT_INTERIOR_GAP = 0.25;

    /** The maximum interior gap (currently 40%). */
    public static final double MAX_INTERIOR_GAP = 0.40;

    /** The default starting angle for the radar chart axes. */
    public static final double DEFAULT_START_ANGLE = 90.0;

    /** The default series label font. */
    public static final Font DEFAULT_LABEL_FONT = new Font("SansSerif",
            Font.PLAIN, 10);

    /** The default series label paint. */
    public static final Paint  DEFAULT_LABEL_PAINT = Color.BLACK;

    /** The default series label background paint. */
    public static final Paint  DEFAULT_LABEL_BACKGROUND_PAINT
            = new Color(255, 255, 192);

    /** The default series label outline paint. */
    public static final Paint  DEFAULT_LABEL_OUTLINE_PAINT = Color.BLACK;

    /** The default series label outline stroke. */
    public static final Stroke DEFAULT_LABEL_OUTLINE_STROKE
            = new BasicStroke(0.5f);

    /** The default series label shadow paint. */
    public static final Paint  DEFAULT_LABEL_SHADOW_PAINT = Color.LIGHT_GRAY;

    /**
     * The default maximum value plotted - forces the plot to evaluate
     *  the maximum from the data passed in
     */
    public static final double DEFAULT_MAX_VALUE = -1.0;

    /** The head radius as a percentage of the available drawing area. */
    protected double headPercent;

    /** The space left around the outside of the plot as a percentage. */
    private double interiorGap;

    /** The gap between the labels and the axes as a %age of the radius. */
    private double axisLabelGap;

    /** The paint used to draw the axis lines. */
    private transient Paint axisLinePaint;

    /** The stroke used to draw the axis lines. */
    private transient Stroke axisLineStroke;

    /** The dataset. */
    private CategoryDataset dataset;

    /** The maximum value we are plotting against on each category axis */
    private double maxValue;

    /**
     * The data extract order (BY_ROW or BY_COLUMN). This denotes whether
     * the data series are stored in rows (in which case the category names are
     * derived from the column keys) or in columns (in which case the category
     * names are derived from the row keys).
     */
    private TableOrder dataExtractOrder;

    /** The starting angle. */
    private double startAngle;

    /** The direction for drawing the radar axis and plots. */
    private Rotation direction;

    /** The legend item shape. */
    private transient Shape legendItemShape;

    /** The series paint list. */
    private transient Map<Integer, Paint> seriesPaints;

    /** The default series paint. */
    private transient Paint defaultSeriesPaint;

    /** The series outline paint list. */
    private transient Map<Integer, Paint> seriesOutlinePaints;

    /** The default series outline paint. */
    private transient Paint defaultSeriesOutlinePaint;

    /** The series outline stroke list. */
    private transient Map<Integer, Stroke> seriesOutlineStrokes;

    /** The default series outline stroke. */
    private transient Stroke defaultSeriesOutlineStroke;

    /** The font used to display the category labels. */
    private Font labelFont;

    /** The color used to draw the category labels. */
    private transient Paint labelPaint;

    /** The label generator. */
    private CategoryItemLabelGenerator labelGenerator;

    /** controls if the web polygons are filled or not */
    private boolean webFilled = true;

    /** A tooltip generator for the plot ({@code null} permitted). */
    private CategoryToolTipGenerator toolTipGenerator;

    /** A URL generator for the plot ({@code null} permitted). */
    private CategoryURLGenerator urlGenerator;

    /**
     * Creates a default plot with no dataset.
     */
    public SpiderWebPlot() {
        this(null);
    }

    /**
     * Creates a new spider web plot with the given dataset, with each row
     * representing a series.
     *
     * @param dataset  the dataset ({@code null} permitted).
     */
    public SpiderWebPlot(CategoryDataset dataset) {
        this(dataset, TableOrder.BY_ROW);
    }

    /**
     * Creates a new spider web plot with the given dataset.
     *
     * @param dataset  the dataset.
     * @param extract  controls how data is extracted ({@link TableOrder#BY_ROW}
     *                 or {@link TableOrder#BY_COLUMN}).
     */
    public SpiderWebPlot(CategoryDataset dataset, TableOrder extract) {
        super();
        Args.nullNotPermitted(extract, "extract");
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        this.dataExtractOrder = extract;
        this.headPercent = DEFAULT_HEAD;
        this.axisLabelGap = DEFAULT_AXIS_LABEL_GAP;
        this.axisLinePaint = Color.BLACK;
        this.axisLineStroke = new BasicStroke(1.0f);

        this.interiorGap = DEFAULT_INTERIOR_GAP;
        this.startAngle = DEFAULT_START_ANGLE;
        this.direction = Rotation.CLOCKWISE;
        this.maxValue = DEFAULT_MAX_VALUE;

        this.seriesPaints = new HashMap<>();
        this.defaultSeriesPaint = null;

        this.seriesOutlinePaints = new HashMap<>();
        this.defaultSeriesOutlinePaint = DEFAULT_OUTLINE_PAINT;

        this.seriesOutlineStrokes = new HashMap<>();
        this.defaultSeriesOutlineStroke = DEFAULT_OUTLINE_STROKE;

        this.labelFont = DEFAULT_LABEL_FONT;
        this.labelPaint = DEFAULT_LABEL_PAINT;
        this.labelGenerator = new StandardCategoryItemLabelGenerator();

        this.legendItemShape = DEFAULT_LEGEND_ITEM_CIRCLE;
    }

    /**
     * Returns a short string describing the type of plot.
     *
     * @return The plot type.
     */
    @Override
    public String getPlotType() {
        // return localizationResources.getString("Radar_Plot");
        return ("Spider Web Plot");
    }

    /**
     * Returns the dataset.
     *
     * @return The dataset (possibly {@code null}).
     *
     * @see #setDataset(CategoryDataset)
     */
    public CategoryDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset used by the plot and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @see #getDataset()
     */
    public void setDataset(CategoryDataset dataset) {
        // if there is an existing dataset, remove the plot from the list of
        // change listeners...
        if (this.dataset != null) {
            this.dataset.removeChangeListener(this);
        }

        // set the new dataset, and register the chart as a change listener...
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }

        // send a dataset change event to self to trigger plot change event
        datasetChanged(new DatasetChangeEvent(this, dataset));
    }

    /**
     * Method to determine if the web chart is to be filled.
     *
     * @return A boolean.
     *
     * @see #setWebFilled(boolean)
     */
    public boolean isWebFilled() {
        return this.webFilled;
    }

    /**
     * Sets the webFilled flag and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param flag  the flag.
     *
     * @see #isWebFilled()
     */
    public void setWebFilled(boolean flag) {
        this.webFilled = flag;
        fireChangeEvent();
    }

    /**
     * Returns the data extract order (by row or by column).
     *
     * @return The data extract order (never {@code null}).
     *
     * @see #setDataExtractOrder(TableOrder)
     */
    public TableOrder getDataExtractOrder() {
        return this.dataExtractOrder;
    }

    /**
     * Sets the data extract order (by row or by column) and sends a
     * {@link PlotChangeEvent}to all registered listeners.
     *
     * @param order the order ({@code null} not permitted).
     *
     * @throws IllegalArgumentException if {@code order} is
     *     {@code null}.
     *
     * @see #getDataExtractOrder()
     */
    public void setDataExtractOrder(TableOrder order) {
        Args.nullNotPermitted(order, "order");
        this.dataExtractOrder = order;
        fireChangeEvent();
    }

    /**
     * Returns the head percent (the default value is 0.01).
     *
     * @return The head percent (always > 0).
     *
     * @see #setHeadPercent(double)
     */
    public double getHeadPercent() {
        return this.headPercent;
    }

    /**
     * Sets the head percent and sends a {@link PlotChangeEvent} to all
     * registered listeners.  Note that 0.10 is 10 percent.
     *
     * @param percent  the percent (must be greater than zero).
     *
     * @see #getHeadPercent()
     */
    public void setHeadPercent(double percent) {
        Args.requireNonNegative(percent, "percent");
        this.headPercent = percent;
        fireChangeEvent();
    }

    /**
     * Returns the start angle for the first radar axis.
     * <BR>
     * This is measured in degrees starting from 3 o'clock (Java Arc2D default)
     * and measuring anti-clockwise.
     *
     * @return The start angle.
     *
     * @see #setStartAngle(double)
     */
    public double getStartAngle() {
        return this.startAngle;
    }

    /**
     * Sets the starting angle and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     * <P>
     * The initial default value is 90 degrees, which corresponds to 12 o'clock.
     * A value of zero corresponds to 3 o'clock... this is the encoding used by
     * Java's Arc2D class.
     *
     * @param angle  the angle (in degrees).
     *
     * @see #getStartAngle()
     */
    public void setStartAngle(double angle) {
        this.startAngle = angle;
        fireChangeEvent();
    }

    /**
     * Returns the maximum value any category axis can take.
     *
     * @return The maximum value.
     *
     * @see #setMaxValue(double)
     */
    public double getMaxValue() {
        return this.maxValue;
    }

    /**
     * Sets the maximum value any category axis can take and sends
     * a {@link PlotChangeEvent} to all registered listeners.
     *
     * @param value  the maximum value.
     *
     * @see #getMaxValue()
     */
    public void setMaxValue(double value) {
        this.maxValue = value;
        fireChangeEvent();
    }

    /**
     * Returns the direction in which the radar axes are drawn
     * (clockwise or anti-clockwise).
     *
     * @return The direction (never {@code null}).
     *
     * @see #setDirection(Rotation)
     */
    public Rotation getDirection() {
        return this.direction;
    }

    /**
     * Sets the direction in which the radar axes are drawn and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param direction  the direction ({@code null} not permitted).
     *
     * @see #getDirection()
     */
    public void setDirection(Rotation direction) {
        Args.nullNotPermitted(direction, "direction");
        this.direction = direction;
        fireChangeEvent();
    }

    /**
     * Returns the interior gap, measured as a percentage of the available
     * drawing space.
     *
     * @return The gap (as a percentage of the available drawing space).
     *
     * @see #setInteriorGap(double)
     */
    public double getInteriorGap() {
        return this.interiorGap;
    }

    /**
     * Sets the interior gap and sends a {@link PlotChangeEvent} to all
     * registered listeners. This controls the space between the edges of the
     * plot and the plot area itself (the region where the axis labels appear).
     *
     * @param percent  the gap (as a percentage of the available drawing space).
     *
     * @see #getInteriorGap()
     */
    public void setInteriorGap(double percent) {
        if ((percent < 0.0) || (percent > MAX_INTERIOR_GAP)) {
            throw new IllegalArgumentException(
                    "Percentage outside valid range.");
        }
        if (this.interiorGap != percent) {
            this.interiorGap = percent;
            fireChangeEvent();
        }
    }

    /**
     * Returns the axis label gap.
     *
     * @return The axis label gap.
     *
     * @see #setAxisLabelGap(double)
     */
    public double getAxisLabelGap() {
        return this.axisLabelGap;
    }

    /**
     * Sets the axis label gap and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param gap  the gap.
     *
     * @see #getAxisLabelGap()
     */
    public void setAxisLabelGap(double gap) {
        this.axisLabelGap = gap;
        fireChangeEvent();
    }

    /**
     * Returns the paint used to draw the axis lines.
     *
     * @return The paint used to draw the axis lines (never {@code null}).
     *
     * @see #setAxisLinePaint(Paint)
     * @see #getAxisLineStroke()
     */
    public Paint getAxisLinePaint() {
        return this.axisLinePaint;
    }

    /**
     * Sets the paint used to draw the axis lines and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getAxisLinePaint()
     */
    public void setAxisLinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.axisLinePaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the stroke used to draw the axis lines.
     *
     * @return The stroke used to draw the axis lines (never {@code null}).
     *
     * @see #setAxisLineStroke(Stroke)
     * @see #getAxisLinePaint()
     */
    public Stroke getAxisLineStroke() {
        return this.axisLineStroke;
    }

    /**
     * Sets the stroke used to draw the axis lines and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getAxisLineStroke()
     */
    public void setAxisLineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.axisLineStroke = stroke;
        fireChangeEvent();
    }

    //// SERIES PAINT /////////////////////////

    /**
     * Returns the paint for the specified series.
     *
     * @param series  the series index (zero-based).
     *
     * @return The paint (never {@code null}).
     *
     * @see #setSeriesPaint(int, Paint)
     */
    public Paint getSeriesPaint(int series) {
        // look up the paint list
        Paint result = this.seriesPaints.get(series);
        if (result == null) {
            DrawingSupplier supplier = getDrawingSupplier();
            if (supplier != null) {
                Paint p = supplier.getNextPaint();
                this.seriesPaints.put(series, p);
                result = p;
            } else {
                result = this.defaultSeriesPaint;
            }
        }
        return result;
    }

    /**
     * Sets the paint used to fill a series of the radar and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getSeriesPaint(int)
     */
    public void setSeriesPaint(int series, Paint paint) {
        this.seriesPaints.put(series, paint);
        fireChangeEvent();
    }

    /**
     * Returns the default series paint, used when no other paint is
     * available.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setDefaultSeriesPaint(Paint)
     */
    public Paint getDefaultSeriesPaint() {
      return this.defaultSeriesPaint;
    }

    /**
     * Sets the default series paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getDefaultSeriesPaint()
     */
    public void setDefaultSeriesPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultSeriesPaint = paint;
        fireChangeEvent();
    }

    //// SERIES OUTLINE PAINT ////////////////////////////

    /**
     * Returns the paint for the specified series.
     *
     * @param series  the series index (zero-based).
     *
     * @return The paint (never {@code null}).
     */
    public Paint getSeriesOutlinePaint(int series) {
        // otherwise look up the paint list
        Paint result = this.seriesOutlinePaints.get(series);
        if (result == null) {
            result = this.defaultSeriesOutlinePaint;
        }
        return result;
    }

    /**
     * Sets the paint used to fill a series of the radar and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param paint  the paint ({@code null} permitted).
     */
    public void setSeriesOutlinePaint(int series, Paint paint) {
        this.seriesOutlinePaints.put(series, paint);
        fireChangeEvent();
    }

    /**
     * Returns the base series paint. This is used when no other paint is
     * available.
     *
     * @return The paint (never {@code null}).
     */
    public Paint getDefaultSeriesOutlinePaint() {
        return this.defaultSeriesOutlinePaint;
    }

    /**
     * Sets the base series paint and sends a change event to all registered
     * listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setDefaultSeriesOutlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultSeriesOutlinePaint = paint;
        fireChangeEvent();
    }

    //// SERIES OUTLINE STROKE /////////////////////

    /**
     * Returns the stroke for the specified series.
     *
     * @param series  the series index (zero-based).
     *
     * @return The stroke (never {@code null}).
     */
    public Stroke getSeriesOutlineStroke(int series) {
        Stroke result = this.seriesOutlineStrokes.get(series);
        if (result == null) {
            result = this.defaultSeriesOutlineStroke;
        }
        return result;

    }

    /**
     * Sets the stroke used to fill a series of the radar and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero-based).
     * @param stroke  the stroke ({@code null} permitted).
     */
    public void setSeriesOutlineStroke(int series, Stroke stroke) {
        this.seriesOutlineStrokes.put(series, stroke);
        fireChangeEvent();
    }

    /**
     * Returns the default series stroke. This is used when no other stroke is
     * available.
     *
     * @return The stroke (never {@code null}).
     */
    public Stroke getDefaultSeriesOutlineStroke() {
        return this.defaultSeriesOutlineStroke;
    }

    /**
     * Sets the default series stroke and sends a change event to all 
     * registered listeners.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setDefaultSeriesOutlineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.defaultSeriesOutlineStroke = stroke;
        fireChangeEvent();
    }

    /**
     * Returns the shape used for legend items.
     *
     * @return The shape (never {@code null}).
     *
     * @see #setLegendItemShape(Shape)
     */
    public Shape getLegendItemShape() {
        return this.legendItemShape;
    }

    /**
     * Sets the shape used for legend items and sends a {@link PlotChangeEvent}
     * to all registered listeners.
     *
     * @param shape  the shape ({@code null} not permitted).
     *
     * @see #getLegendItemShape()
     */
    public void setLegendItemShape(Shape shape) {
        Args.nullNotPermitted(shape, "shape");
        this.legendItemShape = shape;
        fireChangeEvent();
    }

    /**
     * Returns the series label font.
     *
     * @return The font (never {@code null}).
     *
     * @see #setLabelFont(Font)
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Sets the series label font and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getLabelFont()
     */
    public void setLabelFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.labelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the series label paint.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setLabelPaint(Paint)
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Sets the series label paint and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLabelPaint()
     */
    public void setLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.labelPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the label generator.
     *
     * @return The label generator (never {@code null}).
     *
     * @see #setLabelGenerator(CategoryItemLabelGenerator)
     */
    public CategoryItemLabelGenerator getLabelGenerator() {
        return this.labelGenerator;
    }

    /**
     * Sets the label generator and sends a {@link PlotChangeEvent} to all
     * registered listeners.
     *
     * @param generator  the generator ({@code null} not permitted).
     *
     * @see #getLabelGenerator()
     */
    public void setLabelGenerator(CategoryItemLabelGenerator generator) {
        Args.nullNotPermitted(generator, "generator");
        this.labelGenerator = generator;
    }

    /**
     * Returns the tool tip generator for the plot.
     *
     * @return The tool tip generator (possibly {@code null}).
     *
     * @see #setToolTipGenerator(CategoryToolTipGenerator)
     */
    public CategoryToolTipGenerator getToolTipGenerator() {
        return this.toolTipGenerator;
    }

    /**
     * Sets the tool tip generator for the plot and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getToolTipGenerator()
     */
    public void setToolTipGenerator(CategoryToolTipGenerator generator) {
        this.toolTipGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the URL generator for the plot.
     *
     * @return The URL generator (possibly {@code null}).
     *
     * @see #setURLGenerator(CategoryURLGenerator)
     */
    public CategoryURLGenerator getURLGenerator() {
        return this.urlGenerator;
    }

    /**
     * Sets the URL generator for the plot and sends a
     * {@link PlotChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getURLGenerator()
     */
    public void setURLGenerator(CategoryURLGenerator generator) {
        this.urlGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns a collection of legend items for the spider web chart.
     *
     * @return The legend items (never {@code null}).
     */
    @Override
    public LegendItemCollection getLegendItems() {
        LegendItemCollection result = new LegendItemCollection();
        if (getDataset() == null) {
            return result;
        }
        List keys = null;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            keys = this.dataset.getRowKeys();
        }
        else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
            keys = this.dataset.getColumnKeys();
        }
        if (keys == null) {
            return result;
        }

        int series = 0;
        Iterator iterator = keys.iterator();
        Shape shape = getLegendItemShape();
        while (iterator.hasNext()) {
            Comparable key = (Comparable) iterator.next();
            String label = key.toString();
            String description = label;
            Paint paint = getSeriesPaint(series);
            Paint outlinePaint = getSeriesOutlinePaint(series);
            Stroke stroke = getSeriesOutlineStroke(series);
            LegendItem item = new LegendItem(label, description,
                    null, null, shape, paint, stroke, outlinePaint);
            item.setDataset(getDataset());
            item.setSeriesKey(key);
            item.setSeriesIndex(series);
            result.add(item);
            series++;
        }
        return result;
    }

    /**
     * Returns a cartesian point from a polar angle, length and bounding box
     *
     * @param bounds  the area inside which the point needs to be.
     * @param angle  the polar angle, in degrees.
     * @param length  the relative length. Given in percent of maximum extend.
     *
     * @return The cartesian point.
     */
    protected Point2D getWebPoint(Rectangle2D bounds,
                                  double angle, double length) {

        double angrad = Math.toRadians(angle);
        double x = Math.cos(angrad) * length * bounds.getWidth() / 2;
        double y = -Math.sin(angrad) * length * bounds.getHeight() / 2;

        return new Point2D.Double(bounds.getX() + x + bounds.getWidth() / 2,
                bounds.getY() + y + bounds.getHeight() / 2);
    }

    /**
     * Draws the plot on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device.
     * @param area  the area within which the plot should be drawn.
     * @param anchor  the anchor point ({@code null} permitted).
     * @param parentState  the state from the parent plot, if there is one.
     * @param info  collects info about the drawing.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor,
            PlotState parentState, PlotRenderingInfo info) {

        // adjust for insets...
        RectangleInsets insets = getInsets();
        insets.trim(area);

        if (info != null) {
            info.setPlotArea(area);
            info.setDataArea(area);
        }

        drawBackground(g2, area);
        drawOutline(g2, area);

        Shape savedClip = g2.getClip();

        g2.clip(area);
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                getForegroundAlpha()));

        if (!DatasetUtils.isEmptyOrNull(this.dataset)) {
            int seriesCount, catCount;

            if (this.dataExtractOrder == TableOrder.BY_ROW) {
                seriesCount = this.dataset.getRowCount();
                catCount = this.dataset.getColumnCount();
            }
            else {
                seriesCount = this.dataset.getColumnCount();
                catCount = this.dataset.getRowCount();
            }

            // ensure we have a maximum value to use on the axes
            if (this.maxValue == DEFAULT_MAX_VALUE) {
                calculateMaxValue(seriesCount, catCount);
            }

            // Next, setup the plot area

            // adjust the plot area by the interior spacing value

            double gapHorizontal = area.getWidth() * getInteriorGap();
            double gapVertical = area.getHeight() * getInteriorGap();

            double X = area.getX() + gapHorizontal / 2;
            double Y = area.getY() + gapVertical / 2;
            double W = area.getWidth() - gapHorizontal;
            double H = area.getHeight() - gapVertical;

            double headW = area.getWidth() * this.headPercent;
            double headH = area.getHeight() * this.headPercent;

            // make the chart area a square
            double min = Math.min(W, H) / 2;
            X = (X + X + W) / 2 - min;
            Y = (Y + Y + H) / 2 - min;
            W = 2 * min;
            H = 2 * min;

            Point2D  centre = new Point2D.Double(X + W / 2, Y + H / 2);
            Rectangle2D radarArea = new Rectangle2D.Double(X, Y, W, H);

            // draw the axis and category label
            for (int cat = 0; cat < catCount; cat++) {
                double angle = getStartAngle()
                        + (getDirection().getFactor() * cat * 360 / catCount);

                Point2D endPoint = getWebPoint(radarArea, angle, 1);
                                                     // 1 = end of axis
                Line2D  line = new Line2D.Double(centre, endPoint);
                g2.setPaint(this.axisLinePaint);
                g2.setStroke(this.axisLineStroke);
                g2.draw(line);
                drawLabel(g2, radarArea, 0.0, cat, angle, 360.0 / catCount);
            }

            // Now actually plot each of the series polygons..
            for (int series = 0; series < seriesCount; series++) {
                drawRadarPoly(g2, radarArea, centre, info, series, catCount,
                        headH, headW);
            }
        } else {
            drawNoDataMessage(g2, area);
        }
        g2.setClip(savedClip);
        g2.setComposite(originalComposite);
        drawOutline(g2, area);
    }

    /**
     * loop through each of the series to get the maximum value
     * on each category axis
     *
     * @param seriesCount  the number of series
     * @param catCount  the number of categories
     */
    private void calculateMaxValue(int seriesCount, int catCount) {
        double v;
        Number nV;

        for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
            for (int catIndex = 0; catIndex < catCount; catIndex++) {
                nV = getPlotValue(seriesIndex, catIndex);
                if (nV != null) {
                    v = nV.doubleValue();
                    if (v > this.maxValue) {
                        this.maxValue = v;
                    }
                }
            }
        }
    }

    /**
     * Draws a radar plot polygon.
     *
     * @param g2 the graphics device.
     * @param plotArea the area we are plotting in (already adjusted).
     * @param centre the centre point of the radar axes
     * @param info chart rendering info.
     * @param series the series within the dataset we are plotting
     * @param catCount the number of categories per radar plot
     * @param headH the data point height
     * @param headW the data point width
     */
    protected void drawRadarPoly(Graphics2D g2, Rectangle2D plotArea,
            Point2D centre, PlotRenderingInfo info, int series, int catCount,
            double headH, double headW) {

        Polygon polygon = new Polygon();

        EntityCollection entities = null;
        if (info != null) {
            entities = info.getOwner().getEntityCollection();
        }

        // plot the data...
        for (int cat = 0; cat < catCount; cat++) {

            Number dataValue = getPlotValue(series, cat);

            if (dataValue != null) {
                double value = dataValue.doubleValue();

                if (value >= 0) { // draw the polygon series...

                    // Finds our starting angle from the centre for this axis

                    double angle = getStartAngle()
                        + (getDirection().getFactor() * cat * 360 / catCount);

                    // The following angle calc will ensure there isn't a top
                    // vertical axis - this may be useful if you don't want any
                    // given criteria to 'appear' move important than the
                    // others..
                    //  + (getDirection().getFactor()
                    //        * (cat + 0.5) * 360 / catCount);

                    // find the point at the appropriate distance end point
                    // along the axis/angle identified above and add it to the
                    // polygon

                    Point2D point = getWebPoint(plotArea, angle,
                            value / this.maxValue);
                    polygon.addPoint((int) point.getX(), (int) point.getY());

                    // put an elipse at the point being plotted..

                    Paint paint = getSeriesPaint(series);
                    Paint outlinePaint = getSeriesOutlinePaint(series);
                    Stroke outlineStroke = getSeriesOutlineStroke(series);

                    Ellipse2D head = new Ellipse2D.Double(point.getX()
                            - headW / 2, point.getY() - headH / 2, headW,
                            headH);
                    g2.setPaint(paint);
                    g2.fill(head);
                    g2.setStroke(outlineStroke);
                    g2.setPaint(outlinePaint);
                    g2.draw(head);

                    if (entities != null) {
                        int row, col;
                        if (this.dataExtractOrder == TableOrder.BY_ROW) {
                            row = series;
                            col = cat;
                        } else {
                            row = cat;
                            col = series;
                        }
                        String tip = null;
                        if (this.toolTipGenerator != null) {
                            tip = this.toolTipGenerator.generateToolTip(
                                    this.dataset, row, col);
                        }

                        String url = null;
                        if (this.urlGenerator != null) {
                            url = this.urlGenerator.generateURL(this.dataset,
                                   row, col);
                        }

                        Shape area = new Rectangle(
                                (int) (point.getX() - headW),
                                (int) (point.getY() - headH),
                                (int) (headW * 2), (int) (headH * 2));
                        CategoryItemEntity entity = new CategoryItemEntity(
                                area, tip, url, this.dataset,
                                this.dataset.getRowKey(row),
                                this.dataset.getColumnKey(col));
                        entities.add(entity);
                    }

                }
            }
        }
        // Plot the polygon

        Paint paint = getSeriesPaint(series);
        g2.setPaint(paint);
        g2.setStroke(getSeriesOutlineStroke(series));
        g2.draw(polygon);

        // Lastly, fill the web polygon if this is required

        if (this.webFilled) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    0.1f));
            g2.fill(polygon);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    getForegroundAlpha()));
        }
    }

    /**
     * Returns the value to be plotted at the intersection of the
     * series and the category.  This allows us to plot
     * {@code BY_ROW} or {@code BY_COLUMN} which basically is just
     * reversing the definition of the categories and data series being
     * plotted.
     *
     * @param series the series to be plotted.
     * @param cat the category within the series to be plotted.
     *
     * @return The value to be plotted (possibly {@code null}).
     *
     * @see #getDataExtractOrder()
     */
    protected Number getPlotValue(int series, int cat) {
        Number value = null;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            value = this.dataset.getValue(series, cat);
        } else if (this.dataExtractOrder == TableOrder.BY_COLUMN) {
            value = this.dataset.getValue(cat, series);
        }
        return value;
    }

    /**
     * Draws the label for one axis.
     *
     * @param g2  the graphics device.
     * @param plotArea  the plot area
     * @param value  the value of the label (ignored).
     * @param cat  the category (zero-based index).
     * @param startAngle  the starting angle.
     * @param extent  the extent of the arc.
     */
    protected void drawLabel(Graphics2D g2, Rectangle2D plotArea, double value,
                             int cat, double startAngle, double extent) {
        FontRenderContext frc = g2.getFontRenderContext();

        String label;
        if (this.dataExtractOrder == TableOrder.BY_ROW) {
            // if series are in rows, then the categories are the column keys
            label = this.labelGenerator.generateColumnLabel(this.dataset, cat);
        } else {
            // if series are in columns, then the categories are the row keys
            label = this.labelGenerator.generateRowLabel(this.dataset, cat);
        }

        Rectangle2D labelBounds = getLabelFont().getStringBounds(label, frc);
        LineMetrics lm = getLabelFont().getLineMetrics(label, frc);
        double ascent = lm.getAscent();

        Point2D labelLocation = calculateLabelLocation(labelBounds, ascent,
                plotArea, startAngle);

        Composite saveComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setPaint(getLabelPaint());
        g2.setFont(getLabelFont());
        g2.drawString(label, (float) labelLocation.getX(),
                (float) labelLocation.getY());
        g2.setComposite(saveComposite);
    }

    /**
     * Returns the location for a label
     *
     * @param labelBounds the label bounds.
     * @param ascent the ascent (height of font).
     * @param plotArea the plot area
     * @param startAngle the start angle for the pie series.
     *
     * @return The location for a label.
     */
    protected Point2D calculateLabelLocation(Rectangle2D labelBounds,
            double ascent, Rectangle2D plotArea, double startAngle) {
        Arc2D arc1 = new Arc2D.Double(plotArea, startAngle, 0, Arc2D.OPEN);
        Point2D point1 = arc1.getEndPoint();

        double deltaX = -(point1.getX() - plotArea.getCenterX())
                        * this.axisLabelGap;
        double deltaY = -(point1.getY() - plotArea.getCenterY())
                        * this.axisLabelGap;

        double labelX = point1.getX() - deltaX;
        double labelY = point1.getY() - deltaY;

        if (labelX < plotArea.getCenterX()) {
            labelX -= labelBounds.getWidth();
        }

        if (labelX == plotArea.getCenterX()) {
            labelX -= labelBounds.getWidth() / 2;
        }

        if (labelY > plotArea.getCenterY()) {
            labelY += ascent;
        }

        return new Point2D.Double(labelX, labelY);
    }

    /**
     * Tests this plot for equality with an arbitrary object.
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
        if (!(obj instanceof SpiderWebPlot)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        SpiderWebPlot that = (SpiderWebPlot) obj;
        if (!this.dataExtractOrder.equals(that.dataExtractOrder)) {
            return false;
        }
        if (this.headPercent != that.headPercent) {
            return false;
        }
        if (this.interiorGap != that.interiorGap) {
            return false;
        }
        if (this.startAngle != that.startAngle) {
            return false;
        }
        if (!this.direction.equals(that.direction)) {
            return false;
        }
        if (this.maxValue != that.maxValue) {
            return false;
        }
        if (this.webFilled != that.webFilled) {
            return false;
        }
        if (this.axisLabelGap != that.axisLabelGap) {
            return false;
        }
        if (!PaintUtils.equal(this.axisLinePaint, that.axisLinePaint)) {
            return false;
        }
        if (!this.axisLineStroke.equals(that.axisLineStroke)) {
            return false;
        }
        if (!ShapeUtils.equal(this.legendItemShape, that.legendItemShape)) {
            return false;
        }
        if (!PaintUtils.equal(this.seriesPaints, that.seriesPaints)) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultSeriesPaint, that.defaultSeriesPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.seriesOutlinePaints, that.seriesOutlinePaints)) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultSeriesOutlinePaint,
                that.defaultSeriesOutlinePaint)) {
            return false;
        }

        if (!this.seriesOutlineStrokes.equals(that.seriesOutlineStrokes)) {
            return false;
        }
        if (!this.defaultSeriesOutlineStroke.equals(that.defaultSeriesOutlineStroke)) {
            return false;
        }
        if (!this.labelFont.equals(that.labelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelPaint, that.labelPaint)) {
            return false;
        }
        if (!this.labelGenerator.equals(that.labelGenerator)) {
            return false;
        }
        if (!Objects.equals(this.toolTipGenerator, that.toolTipGenerator)) {
            return false;
        }
        if (!Objects.equals(this.urlGenerator, that.urlGenerator)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a clone of this plot.
     *
     * @return A clone of this plot.
     *
     * @throws CloneNotSupportedException if the plot cannot be cloned for
     *         any reason.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SpiderWebPlot clone = (SpiderWebPlot) super.clone();
        clone.legendItemShape = CloneUtils.clone(this.legendItemShape);
        clone.seriesPaints = CloneUtils.cloneMapValues(this.seriesPaints);
        clone.seriesOutlinePaints = CloneUtils.cloneMapValues(this.seriesOutlinePaints);
        clone.seriesOutlineStrokes = CloneUtils.cloneMapValues(this.seriesOutlineStrokes);
        return clone;
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

        SerialUtils.writeShape(this.legendItemShape, stream);
        SerialUtils.writeMapOfPaint(this.seriesPaints, stream);
        SerialUtils.writePaint(this.defaultSeriesPaint, stream);
        SerialUtils.writeMapOfPaint(this.seriesOutlinePaints, stream);
        SerialUtils.writePaint(this.defaultSeriesOutlinePaint, stream);
        SerialUtils.writeMapOfStroke(this.seriesOutlineStrokes, stream);
        SerialUtils.writeStroke(this.defaultSeriesOutlineStroke, stream);
        SerialUtils.writePaint(this.labelPaint, stream);
        SerialUtils.writePaint(this.axisLinePaint, stream);
        SerialUtils.writeStroke(this.axisLineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream) throws IOException,
            ClassNotFoundException {
        stream.defaultReadObject();

        this.legendItemShape = SerialUtils.readShape(stream);
        this.seriesPaints = SerialUtils.readMapOfPaint(stream);
        this.defaultSeriesPaint = SerialUtils.readPaint(stream);
        this.seriesOutlinePaints = SerialUtils.readMapOfPaint(stream);
        this.defaultSeriesOutlinePaint = SerialUtils.readPaint(stream);
        this.seriesOutlineStrokes = SerialUtils.readMapOfStroke(stream);
        this.defaultSeriesOutlineStroke = SerialUtils.readStroke(stream);
        this.labelPaint = SerialUtils.readPaint(stream);
        this.axisLinePaint = SerialUtils.readPaint(stream);
        this.axisLineStroke = SerialUtils.readStroke(stream);
        if (this.dataset != null) {
            this.dataset.addChangeListener(this);
        }
    }

}
