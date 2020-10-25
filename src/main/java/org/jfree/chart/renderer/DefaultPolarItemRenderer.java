/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -----------------------------
 * DefaultPolarItemRenderer.java
 * -----------------------------
 * (C) Copyright 2004-2017, by Solution Engineering, Inc. and
 *     Contributors.
 *
 * Original Author:  Daniel Bridenbecker, Solution Engineering, Inc.;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Martin Hoeller (patch 2850344);
 *
 * Changes
 * -------
 * 19-Jan-2004 : Version 1, contributed by DB with minor changes by DG (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 04-Oct-2004 : Renamed BooleanUtils --> BooleanUtilities (DG);
 * 20-Apr-2005 : Update for change to LegendItem class (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 04-Aug-2006 : Implemented equals() and clone() (DG);
 * 02-Feb-2007 : Removed author tags from all over JFreeChart sources (DG);
 * 14-Mar-2007 : Fixed clone() method (DG);
 * 04-May-2007 : Fixed lookup for series paint and stroke (DG);
 * 18-May-2007 : Set dataset for LegendItem (DG);
 * 03-Sep-2009 : Applied patch 2850344 by Martin Hoeller (DG);
 * 27-Nov-2009 : Updated for modification to PolarItemRenderer interface (DG);
 * 03-Oct-2011 : Fixed potential NPE in equals() (MH);
 * 03-Oct-2011 : Added flag to connectFirstAndLastPoint (MH);
 * 03-Oct-2011 : Added tooltip and URL generator support (MH);
 * 03-Oct-2011 : Added some configuration options for the legend (MH);
 * 03-Oct-2011 : Added support for PolarPlot's angleOffset and direction (MH);
 * 16-Oct-2011 : Fixed serialization problems with fillComposite (MH);
 * 18-Sep-2012 : Fixed bug 3508799: seriesKey always null in LegendItem (DG);
 * 01-Jul-2013 : Remove deprecated method calls (DG);
 * 04-Jul-2013 : Fix rendering bug when axis is inverted (DG);
 * 
 */

package org.jfree.chart.renderer;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.BooleanList;
import org.jfree.chart.util.ObjectList;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.xy.XYDataset;

/**
 * A renderer that can be used with the {@link PolarPlot} class.
 */
public class DefaultPolarItemRenderer extends AbstractRenderer
        implements PolarItemRenderer {

    /** The plot that the renderer is assigned to. */
    private PolarPlot plot;

    /** Flags that control whether the renderer fills each series or not. */
    private BooleanList seriesFilled;

    /**
     * Flag that controls whether an outline is drawn for filled series or
     * not.
     *
     * @since 1.0.14
     */
    private boolean drawOutlineWhenFilled;

    /**
     * The composite to use when filling series.
     * 
     * @since 1.0.14
     */
    private transient Composite fillComposite;

    /**
     * A flag that controls whether the fill paint is used for filling
     * shapes.
     * 
     * @since 1.0.14
     */
    private boolean useFillPaint;

    /**
     * The shape that is used to represent a line in the legend.
     * 
     * @since 1.0.14
     */
    private transient Shape legendLine;

    /**
     * Flag that controls whether item shapes are visible or not.
     * 
     * @since 1.0.14
     */
    private boolean shapesVisible;

    /**
     * Flag that controls if the first and last point of the dataset should be
     * connected or not.
     * 
     *  @since 1.0.14
     */
    private boolean connectFirstAndLastPoint;
    
    /**
     * A list of tool tip generators (one per series).
     * 
     * @since 1.0.14
     */
    private ObjectList toolTipGeneratorList;

    /**
     * The base tool tip generator.
     * 
     * @since 1.0.14
     */
    private XYToolTipGenerator baseToolTipGenerator;

    /**
     * The URL text generator.
     * 
     * @since 1.0.14
     */
    private XYURLGenerator urlGenerator;

    /**
     * The legend item tool tip generator.
     * 
     * @since 1.0.14
     */
    private XYSeriesLabelGenerator legendItemToolTipGenerator;

    /**
     * The legend item URL generator.
     * 
     * @since 1.0.14
     */
    private XYSeriesLabelGenerator legendItemURLGenerator;

    /**
     * Creates a new instance of DefaultPolarItemRenderer
     */
    public DefaultPolarItemRenderer() {
        this.seriesFilled = new BooleanList();
        this.drawOutlineWhenFilled = true;
        this.fillComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.3f);
        this.useFillPaint = false;     // use item paint for fills by default
        this.legendLine = new Line2D.Double(-7.0, 0.0, 7.0, 0.0);
        this.shapesVisible = true;
        this.connectFirstAndLastPoint = true;
        
        this.toolTipGeneratorList = new ObjectList();
        this.urlGenerator = null;
        this.legendItemToolTipGenerator = null;
        this.legendItemURLGenerator = null;
    }

    /**
     * Set the plot associated with this renderer.
     *
     * @param plot  the plot.
     *
     * @see #getPlot()
     */
    @Override
    public void setPlot(PolarPlot plot) {
        this.plot = plot;
    }

    /**
     * Return the plot associated with this renderer.
     *
     * @return The plot.
     *
     * @see #setPlot(PolarPlot)
     */
    @Override
    public PolarPlot getPlot() {
        return this.plot;
    }

    /**
     * Returns {@code true} if the renderer will draw an outline around
     * a filled polygon, {@code false} otherwise.
     *
     * @return A boolean.
     *
     * @since 1.0.14
     */
    public boolean getDrawOutlineWhenFilled() {
        return this.drawOutlineWhenFilled;
    }

    /**
     * Set the flag that controls whether the outline around a filled
     * polygon will be drawn or not and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param drawOutlineWhenFilled  the flag.
     *
     * @since 1.0.14
     */
    public void setDrawOutlineWhenFilled(boolean drawOutlineWhenFilled) {
        this.drawOutlineWhenFilled = drawOutlineWhenFilled;
        fireChangeEvent();
    }

    /**
     * Get the composite that is used for filling.
     *
     * @return The composite (never {@code null}).
     *
     * @since 1.0.14
     */
    public Composite getFillComposite() {
        return this.fillComposite;
    }

    /**
     * Sets the composite which will be used for filling polygons and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param composite  the composite to use ({@code null} not
     *         permitted).
     *
     * @since 1.0.14
     */
    public void setFillComposite(Composite composite) {
        Args.nullNotPermitted(composite, "composite");
        this.fillComposite = composite;
        fireChangeEvent();
    }

    /**
     * Returns {@code true} if a shape will be drawn for every item, or
     * {@code false} if not.
     *
     * @return A boolean.
     *
     * @since 1.0.14
     */
    public boolean getShapesVisible() {
        return this.shapesVisible;
    }

    /**
     * Set the flag that controls whether a shape will be drawn for every
     * item, or not and sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param visible  the flag.
     *
     * @since 1.0.14
     */
    public void setShapesVisible(boolean visible) {
        this.shapesVisible = visible;
        fireChangeEvent();
    }

    /**
     * Returns {@code true} if first and last point of a series will be
     * connected, {@code false} otherwise.
     * 
     * @return The current status of the flag.
     * 
     * @since 1.0.14
     */
    public boolean getConnectFirstAndLastPoint() {
        return this.connectFirstAndLastPoint;
    }

    /**
     * Set the flag that controls whether the first and last point of a series
     * will be connected or not and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     * 
     * @param connect the flag.
     * 
     * @since 1.0.14
     */
    public void setConnectFirstAndLastPoint(boolean connect) {
        this.connectFirstAndLastPoint = connect;
        fireChangeEvent();
    }

    /**
     * Returns the drawing supplier from the plot.
     *
     * @return The drawing supplier.
     */
    @Override
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        PolarPlot p = getPlot();
        if (p != null) {
            result = p.getDrawingSupplier();
        }
        return result;
    }

    /**
     * Returns {@code true} if the renderer should fill the specified
     * series, and {@code false} otherwise.
     *
     * @param series  the series index (zero-based).
     *
     * @return A boolean.
     */
    public boolean isSeriesFilled(int series) {
        boolean result = false;
        Boolean b = this.seriesFilled.getBoolean(series);
        if (b != null) {
            result = b;
        }
        return result;
    }

    /**
     * Sets a flag that controls whether or not a series is filled.
     *
     * @param series  the series index.
     * @param filled  the flag.
     */
    public void setSeriesFilled(int series, boolean filled) {
        this.seriesFilled.setBoolean(series, filled);
    }

    /**
     * Returns {@code true} if the renderer should use the fill paint
     * setting to fill shapes, and {@code false} if it should just
     * use the regular paint.
     *
     * @return A boolean.
     *
     * @see #setUseFillPaint(boolean)
     * @since 1.0.14
     */
    public boolean getUseFillPaint() {
        return this.useFillPaint;
    }

    /**
     * Sets the flag that controls whether the fill paint is used to fill
     * shapes, and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     *
     * @param flag  the flag.
     *
     * @see #getUseFillPaint()
     * @since 1.0.14
     */
    public void setUseFillPaint(boolean flag) {
        this.useFillPaint = flag;
        fireChangeEvent();
    }

    /**
     * Returns the shape used to represent a line in the legend.
     *
     * @return The legend line (never {@code null}).
     *
     * @see #setLegendLine(Shape)
     */
    public Shape getLegendLine() {
        return this.legendLine;
    }

    /**
     * Sets the shape used as a line in each legend item and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param line  the line ({@code null} not permitted).
     *
     * @see #getLegendLine()
     */
    public void setLegendLine(Shape line) {
        Args.nullNotPermitted(line, "line");
        this.legendLine = line;
        fireChangeEvent();
    }

    /**
     * Adds an entity to the collection.
     *
     * @param entities  the entity collection being populated.
     * @param area  the entity area (if {@code null} a default will be
     *              used).
     * @param dataset  the dataset.
     * @param series  the series.
     * @param item  the item.
     * @param entityX  the entity's center x-coordinate in user space (only
     *                 used if {@code area} is {@code null}).
     * @param entityY  the entity's center y-coordinate in user space (only
     *                 used if {@code area} is {@code null}).
     */
    protected void addEntity(EntityCollection entities, Shape area,
                             XYDataset dataset, int series, int item,
                             double entityX, double entityY) {
        if (!getItemCreateEntity(series, item)) {
            return;
        }
        Shape hotspot = area;
        if (hotspot == null) {
            double r = getDefaultEntityRadius();
            double w = r * 2;
            if (getPlot().getOrientation() == PlotOrientation.VERTICAL) {
                hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
            }
            else {
                hotspot = new Ellipse2D.Double(entityY - r, entityX - r, w, w);
            }
        }
        String tip = null;
        XYToolTipGenerator generator = getToolTipGenerator(series, item);
        if (generator != null) {
            tip = generator.generateToolTip(dataset, series, item);
        }
        String url = null;
        if (getURLGenerator() != null) {
            url = getURLGenerator().generateURL(dataset, series, item);
        }
        XYItemEntity entity = new XYItemEntity(hotspot, dataset, series, item,
                tip, url);
        entities.add(entity);
    }

    /**
     * Plots the data for a given series.
     *
     * @param g2  the drawing surface.
     * @param dataArea  the data area.
     * @param info  collects plot rendering info.
     * @param plot  the plot.
     * @param dataset  the dataset.
     * @param seriesIndex  the series index.
     */
    @Override
    public void drawSeries(Graphics2D g2, Rectangle2D dataArea,
            PlotRenderingInfo info, PolarPlot plot, XYDataset dataset,
            int seriesIndex) {

        final int numPoints = dataset.getItemCount(seriesIndex);
        if (numPoints == 0) {
            return;
        }
        GeneralPath poly = null;
        ValueAxis axis = plot.getAxisForDataset(plot.indexOf(dataset));
        for (int i = 0; i < numPoints; i++) {
            double theta = dataset.getXValue(seriesIndex, i);
            double radius = dataset.getYValue(seriesIndex, i);
            Point p = plot.translateToJava2D(theta, radius, axis, dataArea);
            if (poly == null) {
                poly = new GeneralPath();
                poly.moveTo(p.x, p.y);
            }
            else {
                poly.lineTo(p.x, p.y);
            }
        }
        assert poly != null;
        if (getConnectFirstAndLastPoint()) {
            poly.closePath();
        }

        g2.setPaint(lookupSeriesPaint(seriesIndex));
        g2.setStroke(lookupSeriesStroke(seriesIndex));
        if (isSeriesFilled(seriesIndex)) {
            Composite savedComposite = g2.getComposite();
            g2.setComposite(this.fillComposite);
            g2.fill(poly);
            g2.setComposite(savedComposite);
            if (this.drawOutlineWhenFilled) {
                // draw the outline of the filled polygon
                g2.setPaint(lookupSeriesOutlinePaint(seriesIndex));
                g2.draw(poly);
            }
        }
        else {
            // just the lines, no filling
            g2.draw(poly);
        }
        
        // draw the item shapes
        if (this.shapesVisible) {
            // setup for collecting optional entity info...
            EntityCollection entities = null;
            if (info != null) {
                entities = info.getOwner().getEntityCollection();
            }

            PathIterator pi = poly.getPathIterator(null);
            int i = 0;
            while (!pi.isDone()) {
                final float[] coords = new float[6];
                final int segType = pi.currentSegment(coords);
                pi.next();
                if (segType != PathIterator.SEG_LINETO &&
                        segType != PathIterator.SEG_MOVETO) {
                    continue;
                }
                final int x = Math.round(coords[0]);
                final int y = Math.round(coords[1]);
                final Shape shape = ShapeUtils.createTranslatedShape(
                        getItemShape(seriesIndex, i++), x,  y);

                Paint paint;
                if (useFillPaint) {
                    paint = lookupSeriesFillPaint(seriesIndex);
                }
                else {
                    paint = lookupSeriesPaint(seriesIndex);
                }
                g2.setPaint(paint);
                g2.fill(shape);
                if (isSeriesFilled(seriesIndex) && this.drawOutlineWhenFilled) {
                    g2.setPaint(lookupSeriesOutlinePaint(seriesIndex));
                    g2.setStroke(lookupSeriesOutlineStroke(seriesIndex));
                    g2.draw(shape);
                }

                // add an entity for the item, but only if it falls within the
                // data area...
                if (entities != null && ShapeUtils.isPointInRect(dataArea, x, 
                        y)) {
                    addEntity(entities, shape, dataset, seriesIndex, i-1, x, y);
                }
            }
        }
    }

    /**
     * Draw the angular gridlines - the spokes.
     *
     * @param g2  the drawing surface.
     * @param plot  the plot ({@code null} not permitted).
     * @param ticks  the ticks ({@code null} not permitted).
     * @param dataArea  the data area.
     */
    @Override
    public void drawAngularGridLines(Graphics2D g2, PolarPlot plot,
                List ticks, Rectangle2D dataArea) {

        g2.setFont(plot.getAngleLabelFont());
        g2.setStroke(plot.getAngleGridlineStroke());
        g2.setPaint(plot.getAngleGridlinePaint());

        ValueAxis axis = plot.getAxis();
        double centerValue, outerValue;
        if (axis.isInverted()) {
            outerValue = axis.getLowerBound();
            centerValue = axis.getUpperBound();
        } else {
            outerValue = axis.getUpperBound();
            centerValue = axis.getLowerBound();
        }
        Point center = plot.translateToJava2D(0, centerValue, axis, dataArea);
        Iterator iterator = ticks.iterator();
        while (iterator.hasNext()) {
            NumberTick tick = (NumberTick) iterator.next();
            double tickVal = tick.getNumber().doubleValue();
            Point p = plot.translateToJava2D(tickVal, outerValue, axis, 
                    dataArea);
            g2.setPaint(plot.getAngleGridlinePaint());
            g2.drawLine(center.x, center.y, p.x, p.y);
            if (plot.isAngleLabelsVisible()) {
                int x = p.x;
                int y = p.y;
                g2.setPaint(plot.getAngleLabelPaint());
                TextUtils.drawAlignedString(tick.getText(), g2, x, y,
                        tick.getTextAnchor());
            }
        }
    }

    /**
     * Draw the radial gridlines - the rings.
     *
     * @param g2  the drawing surface ({@code null} not permitted).
     * @param plot  the plot ({@code null} not permitted).
     * @param radialAxis  the radial axis ({@code null} not permitted).
     * @param ticks  the ticks ({@code null} not permitted).
     * @param dataArea  the data area.
     */
    @Override
    public void drawRadialGridLines(Graphics2D g2, PolarPlot plot, 
            ValueAxis radialAxis, List ticks, Rectangle2D dataArea) {

        Args.nullNotPermitted(radialAxis, "radialAxis");
        g2.setFont(radialAxis.getTickLabelFont());
        g2.setPaint(plot.getRadiusGridlinePaint());
        g2.setStroke(plot.getRadiusGridlineStroke());

        double centerValue;
        if (radialAxis.isInverted()) {
            centerValue = radialAxis.getUpperBound();
        } else {
            centerValue = radialAxis.getLowerBound();
        }
        Point center = plot.translateToJava2D(0, centerValue, radialAxis, dataArea);

        Iterator iterator = ticks.iterator();
        while (iterator.hasNext()) {
            NumberTick tick = (NumberTick) iterator.next();
            double angleDegrees = plot.isCounterClockwise() 
                    ? plot.getAngleOffset() : -plot.getAngleOffset();
            Point p = plot.translateToJava2D(angleDegrees,
                    tick.getNumber().doubleValue(), radialAxis, dataArea);
            int r = p.x - center.x;
            int upperLeftX = center.x - r;
            int upperLeftY = center.y - r;
            int d = 2 * r;
            Ellipse2D ring = new Ellipse2D.Double(upperLeftX, upperLeftY, d, d);
            g2.setPaint(plot.getRadiusGridlinePaint());
            g2.draw(ring);
        }
    }

    /**
     * Return the legend for the given series.
     *
     * @param series  the series index.
     *
     * @return The legend item.
     */
    @Override
    public LegendItem getLegendItem(int series) {
        LegendItem result;
        PolarPlot p = getPlot();
        if (p == null) {
            return null;
        }
        XYDataset dataset = p.getDataset(p.getIndexOf(this));
        if (dataset == null) {
            return null;
        }
        
        String toolTipText = null;
        if (getLegendItemToolTipGenerator() != null) {
            toolTipText = getLegendItemToolTipGenerator().generateLabel(
                    dataset, series);
        }
        String urlText = null;
        if (getLegendItemURLGenerator() != null) {
            urlText = getLegendItemURLGenerator().generateLabel(dataset,
                    series);
        }

        Comparable seriesKey = dataset.getSeriesKey(series);
        String label = seriesKey.toString();
        String description = label;
        Shape shape = lookupSeriesShape(series);
        Paint paint;
        if (this.useFillPaint) {
            paint = lookupSeriesFillPaint(series);
        }
        else {
            paint = lookupSeriesPaint(series);
        }
        Stroke stroke = lookupSeriesStroke(series);
        Paint outlinePaint = lookupSeriesOutlinePaint(series);
        Stroke outlineStroke = lookupSeriesOutlineStroke(series);
        boolean shapeOutlined = isSeriesFilled(series)
                && this.drawOutlineWhenFilled;
        result = new LegendItem(label, description, toolTipText, urlText,
                getShapesVisible(), shape, /* shapeFilled=*/ true, paint,
                shapeOutlined, outlinePaint, outlineStroke, 
                /* lineVisible= */ true, this.legendLine, stroke, paint);
        result.setToolTipText(toolTipText);
        result.setURLText(urlText);
        result.setDataset(dataset);
        result.setSeriesKey(seriesKey);
        result.setSeriesIndex(series);

        return result;
    }

    /**
     * Returns the tooltip generator for the specified series and item.
     * 
     * @param series  the series index.
     * @param item  the item index.
     * 
     * @return The tooltip generator (possibly {@code null}).
     * 
     * @since 1.0.14
     */
    @Override
    public XYToolTipGenerator getToolTipGenerator(int series, int item) {
        XYToolTipGenerator generator
            = (XYToolTipGenerator) this.toolTipGeneratorList.get(series);
        if (generator == null) {
            generator = this.baseToolTipGenerator;
        }
        return generator;
    }

    /**
     * Returns the tool tip generator for the specified series.
     * 
     * @return The tooltip generator (possibly {@code null}).
     *
     * @since 1.0.14
     */
    @Override
    public XYToolTipGenerator getSeriesToolTipGenerator(int series) {
        return (XYToolTipGenerator) this.toolTipGeneratorList.get(series);
    }

    /**
     * Sets the tooltip generator for the specified series.
     * 
     * @param series  the series index.
     * @param generator  the tool tip generator ({@code null} permitted).
     * 
     * @since 1.0.14
     */
    @Override
    public void setSeriesToolTipGenerator(int series,
            XYToolTipGenerator generator) {
        this.toolTipGeneratorList.set(series, generator);
        fireChangeEvent();
    }

    /**
     * Returns the default tool tip generator.
     * 
     * @return The default tool tip generator (possibly {@code null}).
     * 
     * @since 1.0.14
     */
    @Override
    public XYToolTipGenerator getBaseToolTipGenerator() {
        return this.baseToolTipGenerator;
    }

    /**
     * Sets the default tool tip generator and sends a 
     * {@link RendererChangeEvent} to all registered listeners.
     * 
     * @param generator  the generator ({@code null} permitted).
     * 
     * @since 1.0.14
     */
    @Override
    public void setBaseToolTipGenerator(XYToolTipGenerator generator) {
        this.baseToolTipGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the URL generator.
     * 
     * @return The URL generator (possibly {@code null}).
     * 
     * @since 1.0.14
     */
    @Override
    public XYURLGenerator getURLGenerator() {
        return this.urlGenerator;
    }

    /**
     * Sets the URL generator.
     * 
     * @param urlGenerator  the generator ({@code null} permitted)
     * 
     * @since 1.0.14
     */
    @Override
    public void setURLGenerator(XYURLGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
        fireChangeEvent();
    }

    /**
     * Returns the legend item tool tip generator.
     *
     * @return The tool tip generator (possibly {@code null}).
     *
     * @see #setLegendItemToolTipGenerator(XYSeriesLabelGenerator)
     * @since 1.0.14
     */
    public XYSeriesLabelGenerator getLegendItemToolTipGenerator() {
        return this.legendItemToolTipGenerator;
    }

    /**
     * Sets the legend item tool tip generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getLegendItemToolTipGenerator()
     * @since 1.0.14
     */
    public void setLegendItemToolTipGenerator(
            XYSeriesLabelGenerator generator) {
        this.legendItemToolTipGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the legend item URL generator.
     *
     * @return The URL generator (possibly {@code null}).
     *
     * @see #setLegendItemURLGenerator(XYSeriesLabelGenerator)
     * @since 1.0.14
     */
    public XYSeriesLabelGenerator getLegendItemURLGenerator() {
        return this.legendItemURLGenerator;
    }

    /**
     * Sets the legend item URL generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getLegendItemURLGenerator()
     * @since 1.0.14
     */
    public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator) {
        this.legendItemURLGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} not permitted).
     *
     * @return {@code true} if this renderer is equal to {@code obj},
     *     and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DefaultPolarItemRenderer)) {
            return false;
        }
        DefaultPolarItemRenderer that = (DefaultPolarItemRenderer) obj;
        if (!this.seriesFilled.equals(that.seriesFilled)) {
            return false;
        }
        if (this.drawOutlineWhenFilled != that.drawOutlineWhenFilled) {
            return false;
        }
        if (!ObjectUtils.equal(this.fillComposite, that.fillComposite)) {
            return false;
        }
        if (this.useFillPaint != that.useFillPaint) {
            return false;
        }
        if (!ShapeUtils.equal(this.legendLine, that.legendLine)) {
            return false;
        }
        if (this.shapesVisible != that.shapesVisible) {
            return false;
        }
        if (this.connectFirstAndLastPoint != that.connectFirstAndLastPoint) {
            return false;
        }
        if (!this.toolTipGeneratorList.equals(that.toolTipGeneratorList)) {
            return false;
        }
        if (!ObjectUtils.equal(this.baseToolTipGenerator,
                that.baseToolTipGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.urlGenerator, that.urlGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendItemToolTipGenerator,
                that.legendItemToolTipGenerator)) {
            return false;
        }
        if (!ObjectUtils.equal(this.legendItemURLGenerator,
                that.legendItemURLGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the renderer cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultPolarItemRenderer clone
                = (DefaultPolarItemRenderer) super.clone();
        if (this.legendLine != null) {
            clone.legendLine = ShapeUtils.clone(this.legendLine);
        }
        clone.seriesFilled = (BooleanList) this.seriesFilled.clone();
        clone.toolTipGeneratorList
                = (ObjectList) this.toolTipGeneratorList.clone();
        if (clone.baseToolTipGenerator instanceof PublicCloneable) {
            clone.baseToolTipGenerator = (XYToolTipGenerator)
                    ObjectUtils.clone(this.baseToolTipGenerator);
        }
        if (clone.urlGenerator instanceof PublicCloneable) {
            clone.urlGenerator = (XYURLGenerator)
                    ObjectUtils.clone(this.urlGenerator);
        }
        if (clone.legendItemToolTipGenerator instanceof PublicCloneable) {
            clone.legendItemToolTipGenerator = (XYSeriesLabelGenerator)
                    ObjectUtils.clone(this.legendItemToolTipGenerator);
        }
        if (clone.legendItemURLGenerator instanceof PublicCloneable) {
            clone.legendItemURLGenerator = (XYSeriesLabelGenerator)
                    ObjectUtils.clone(this.legendItemURLGenerator);
        }
        return clone;
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
        this.legendLine = SerialUtils.readShape(stream);
        this.fillComposite = SerialUtils.readComposite(stream);
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
        SerialUtils.writeShape(this.legendLine, stream);
        SerialUtils.writeComposite(this.fillComposite, stream);
    }
}
