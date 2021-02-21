/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * AbstractXYItemRenderer.java
 * ---------------------------
 * (C) Copyright 2002-2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Richard Atkinson;
 *                   Focus Computer Services Limited;
 *                   Tim Bardzil;
 *                   Sergei Ivanov;
 *                   Peter Kolb (patch 2809117);
 *                   Martin Krauskopf;
 */

package org.jfree.chart.renderer.xy;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.annotations.Annotation;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.Layer;
import org.jfree.chart.ui.LengthAdjustmentType;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.CloneUtils;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYItemKey;

/**
 * A base class that can be used to create new {@link XYItemRenderer}
 * implementations.
 */
public abstract class AbstractXYItemRenderer extends AbstractRenderer
        implements XYItemRenderer, AnnotationChangeListener,
        Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8019124836026607990L;

    /** The plot. */
    private XYPlot plot;

    /** A list of item label generators (one per series). */
    private Map<Integer, XYItemLabelGenerator> itemLabelGeneratorMap;

    /** The default item label generator. */
    private XYItemLabelGenerator defaultItemLabelGenerator;

    /** A list of tool tip generators (one per series). */
    private Map<Integer, XYToolTipGenerator> toolTipGeneratorMap;

    /** The default tool tip generator. */
    private XYToolTipGenerator defaultToolTipGenerator;

    /** The URL text generator. */
    private XYURLGenerator urlGenerator;

    /**
     * Annotations to be drawn in the background layer ('underneath' the data
     * items).
     */
    private List backgroundAnnotations;

    /**
     * Annotations to be drawn in the foreground layer ('on top' of the data
     * items).
     */
    private List foregroundAnnotations;

    /** The legend item label generator. */
    private XYSeriesLabelGenerator legendItemLabelGenerator;

    /** The legend item tool tip generator. */
    private XYSeriesLabelGenerator legendItemToolTipGenerator;

    /** The legend item URL generator. */
    private XYSeriesLabelGenerator legendItemURLGenerator;

    /**
     * Creates a renderer where the tooltip generator and the URL generator are
     * both {@code null}.
     */
    protected AbstractXYItemRenderer() {
        super();
        this.itemLabelGeneratorMap 
                = new HashMap<Integer, XYItemLabelGenerator>();
        this.toolTipGeneratorMap = new HashMap<Integer, XYToolTipGenerator>();
        this.urlGenerator = null;
        this.backgroundAnnotations = new java.util.ArrayList();
        this.foregroundAnnotations = new java.util.ArrayList();
        this.legendItemLabelGenerator = new StandardXYSeriesLabelGenerator(
                "{0}");
    }

    /**
     * Returns the number of passes through the data that the renderer requires
     * in order to draw the chart.  Most charts will require a single pass, but
     * some require two passes.
     *
     * @return The pass count.
     */
    @Override
    public int getPassCount() {
        return 1;
    }

    /**
     * Returns the plot that the renderer is assigned to.
     *
     * @return The plot (possibly {@code null}).
     */
    @Override
    public XYPlot getPlot() {
        return this.plot;
    }

    /**
     * Sets the plot that the renderer is assigned to.
     *
     * @param plot  the plot ({@code null} permitted).
     */
    @Override
    public void setPlot(XYPlot plot) {
        this.plot = plot;
    }

    /**
     * Initialises the renderer and returns a state object that should be
     * passed to all subsequent calls to the drawItem() method.
     * <P>
     * This method will be called before the first item is rendered, giving the
     * renderer an opportunity to initialise any state information it wants to
     * maintain.  The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param dataset  the dataset.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The renderer state (never {@code null}).
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea,
            XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {
        return new XYItemRendererState(info);
    }

    /**
     * Adds a {@code KEY_BEGIN_ELEMENT} hint to the graphics target.  This
     * hint is recognised by <b>JFreeSVG</b> (in theory it could be used by 
     * other {@code Graphics2D} implementations also).
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param seriesKey  the series key that identifies the element 
     *     ({@code null} not permitted).
     * @param itemIndex  the item index. 
     */
    protected void beginElementGroup(Graphics2D g2, Comparable seriesKey,
            int itemIndex) {
        beginElementGroup(g2, new XYItemKey(seriesKey, itemIndex));    
    }

    // ITEM LABEL GENERATOR

    /**
     * Returns the label generator for a data item.  This implementation simply
     * passes control to the {@link #getSeriesItemLabelGenerator(int)} method.
     * If, for some reason, you want a different generator for individual
     * items, you can override this method.
     *
     * @param series  the series index (zero based).
     * @param item  the item index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getItemLabelGenerator(int series, int item) {

        // otherwise look up the generator table
        XYItemLabelGenerator generator = this.itemLabelGeneratorMap.get(series);
        if (generator == null) {
            generator = this.defaultItemLabelGenerator;
        }
        return generator;
    }

    /**
     * Returns the item label generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getSeriesItemLabelGenerator(int series) {
        return this.itemLabelGeneratorMap.get(series);
    }

    /**
     * Sets the item label generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setSeriesItemLabelGenerator(int series,
            XYItemLabelGenerator generator) {
        this.itemLabelGeneratorMap.put(series, generator);
        fireChangeEvent();
    }

    /**
     * Returns the default item label generator.
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYItemLabelGenerator getDefaultItemLabelGenerator() {
        return this.defaultItemLabelGenerator;
    }

    /**
     * Sets the default item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setDefaultItemLabelGenerator(XYItemLabelGenerator generator) {
        this.defaultItemLabelGenerator = generator;
        fireChangeEvent();
    }

    // TOOL TIP GENERATOR

    /**
     * Returns the tool tip generator for a data item.  If, for some reason,
     * you want a different generator for individual items, you can override
     * this method.
     *
     * @param series  the series index (zero based).
     * @param item  the item index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYToolTipGenerator getToolTipGenerator(int series, int item) {

        // otherwise look up the generator table
        XYToolTipGenerator generator = this.toolTipGeneratorMap.get(series);
        if (generator == null) {
            generator = this.defaultToolTipGenerator;
        }
        return generator;
    }

    /**
     * Returns the tool tip generator for a series.
     *
     * @param series  the series index (zero based).
     *
     * @return The generator (possibly {@code null}).
     */
    @Override
    public XYToolTipGenerator getSeriesToolTipGenerator(int series) {
        return this.toolTipGeneratorMap.get(series);
    }

    /**
     * Sets the tool tip generator for a series and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param series  the series index (zero based).
     * @param generator  the generator ({@code null} permitted).
     */
    @Override
    public void setSeriesToolTipGenerator(int series,
            XYToolTipGenerator generator) {
        this.toolTipGeneratorMap.put(series, generator);
        fireChangeEvent();
    }

    /**
     * Returns the default tool tip generator.
     *
     * @return The generator (possibly {@code null}).
     *
     * @see #setDefaultToolTipGenerator(XYToolTipGenerator)
     */
    @Override
    public XYToolTipGenerator getDefaultToolTipGenerator() {
        return this.defaultToolTipGenerator;
    }

    /**
     * Sets the default tool tip generator and sends a 
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} permitted).
     *
     * @see #getDefaultToolTipGenerator()
     */
    @Override
    public void setDefaultToolTipGenerator(XYToolTipGenerator generator) {
        this.defaultToolTipGenerator = generator;
        fireChangeEvent();
    }

    // URL GENERATOR

    /**
     * Returns the URL generator for HTML image maps.
     *
     * @return The URL generator (possibly {@code null}).
     */
    @Override
    public XYURLGenerator getURLGenerator() {
        return this.urlGenerator;
    }

    /**
     * Sets the URL generator for HTML image maps and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param urlGenerator  the URL generator ({@code null} permitted).
     */
    @Override
    public void setURLGenerator(XYURLGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
        fireChangeEvent();
    }

    /**
     * Adds an annotation and sends a {@link RendererChangeEvent} to all
     * registered listeners.  The annotation is added to the foreground
     * layer.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     */
    @Override
    public void addAnnotation(XYAnnotation annotation) {
        // defer argument checking
        addAnnotation(annotation, Layer.FOREGROUND);
    }

    /**
     * Adds an annotation to the specified layer and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param annotation  the annotation ({@code null} not permitted).
     * @param layer  the layer ({@code null} not permitted).
     */
    @Override
    public void addAnnotation(XYAnnotation annotation, Layer layer) {
        Args.nullNotPermitted(annotation, "annotation");
        if (layer.equals(Layer.FOREGROUND)) {
            this.foregroundAnnotations.add(annotation);
            annotation.addChangeListener(this);
            fireChangeEvent();
        }
        else if (layer.equals(Layer.BACKGROUND)) {
            this.backgroundAnnotations.add(annotation);
            annotation.addChangeListener(this);
            fireChangeEvent();
        }
        else {
            // should never get here
            throw new RuntimeException("Unknown layer.");
        }
    }
    /**
     * Removes the specified annotation and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     *
     * @param annotation  the annotation to remove ({@code null} not
     *                    permitted).
     *
     * @return A boolean to indicate whether or not the annotation was
     *         successfully removed.
     */
    @Override
    public boolean removeAnnotation(XYAnnotation annotation) {
        boolean removed = this.foregroundAnnotations.remove(annotation);
        removed = removed & this.backgroundAnnotations.remove(annotation);
        annotation.removeChangeListener(this);
        fireChangeEvent();
        return removed;
    }

    /**
     * Removes all annotations and sends a {@link RendererChangeEvent}
     * to all registered listeners.
     */
    @Override
    public void removeAnnotations() {
        for(int i = 0; i < this.foregroundAnnotations.size(); i++){
            XYAnnotation annotation 
                    = (XYAnnotation) this.foregroundAnnotations.get(i);
            annotation.removeChangeListener(this);
        }
         for(int i = 0; i < this.backgroundAnnotations.size(); i++){
            XYAnnotation annotation 
                    = (XYAnnotation) this.backgroundAnnotations.get(i);
            annotation.removeChangeListener(this);
        }
        this.foregroundAnnotations.clear();
        this.backgroundAnnotations.clear();
        fireChangeEvent();
    }


    /**
     * Receives notification of a change to an {@link Annotation} added to
     * this renderer.
     *
     * @param event  information about the event (not used here).
     */
    @Override
    public void annotationChanged(AnnotationChangeEvent event) {
        fireChangeEvent();
    }

    /**
     * Returns a collection of the annotations that are assigned to the
     * renderer.
     *
     * @return A collection of annotations (possibly empty but never
     *     {@code null}).
     */
    public Collection getAnnotations() {
        List result = new java.util.ArrayList(this.foregroundAnnotations);
        result.addAll(this.backgroundAnnotations);
        return result;
    }

    /**
     * Returns the legend item label generator.
     *
     * @return The label generator (never {@code null}).
     *
     * @see #setLegendItemLabelGenerator(XYSeriesLabelGenerator)
     */
    @Override
    public XYSeriesLabelGenerator getLegendItemLabelGenerator() {
        return this.legendItemLabelGenerator;
    }

    /**
     * Sets the legend item label generator and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param generator  the generator ({@code null} not permitted).
     *
     * @see #getLegendItemLabelGenerator()
     */
    @Override
    public void setLegendItemLabelGenerator(XYSeriesLabelGenerator generator) {
        Args.nullNotPermitted(generator, "generator");
        this.legendItemLabelGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the legend item tool tip generator.
     *
     * @return The tool tip generator (possibly {@code null}).
     *
     * @see #setLegendItemToolTipGenerator(XYSeriesLabelGenerator)
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
     */
    public void setLegendItemURLGenerator(XYSeriesLabelGenerator generator) {
        this.legendItemURLGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Returns the lower and upper bounds (range) of the x-values in the
     * specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @see #findRangeBounds(XYDataset)
     */
    @Override
    public Range findDomainBounds(XYDataset dataset) {
        return findDomainBounds(dataset, false);
    }

    /**
     * Returns the lower and upper bounds (range) of the x-values in the
     * specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     * @param includeInterval  include the interval (if any) for the dataset?
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     */
    protected Range findDomainBounds(XYDataset dataset,
            boolean includeInterval) {
        if (dataset == null) {
            return null;
        }
        if (getDataBoundsIncludesVisibleSeriesOnly()) {
            List visibleSeriesKeys = new ArrayList();
            int seriesCount = dataset.getSeriesCount();
            for (int s = 0; s < seriesCount; s++) {
                if (isSeriesVisible(s)) {
                    visibleSeriesKeys.add(dataset.getSeriesKey(s));
                }
            }
            return DatasetUtils.findDomainBounds(dataset,
                    visibleSeriesKeys, includeInterval);
        }
        return DatasetUtils.findDomainBounds(dataset, includeInterval);
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     *
     * @see #findDomainBounds(XYDataset)
     */
    @Override
    public Range findRangeBounds(XYDataset dataset) {
        return findRangeBounds(dataset, false);
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     * @param includeInterval  include the interval (if any) for the dataset?
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     */
    protected Range findRangeBounds(XYDataset dataset,
            boolean includeInterval) {
        if (dataset == null) {
            return null;
        }
        if (getDataBoundsIncludesVisibleSeriesOnly()) {
            List visibleSeriesKeys = new ArrayList();
            int seriesCount = dataset.getSeriesCount();
            for (int s = 0; s < seriesCount; s++) {
                if (isSeriesVisible(s)) {
                    visibleSeriesKeys.add(dataset.getSeriesKey(s));
                }
            }
            // the bounds should be calculated using just the items within
            // the current range of the x-axis...if there is one
            Range xRange = null;
            XYPlot p = getPlot();
            if (p != null) {
                ValueAxis xAxis = null;
                int index = p.getIndexOf(this);
                if (index >= 0) {
                    xAxis = this.plot.getDomainAxisForDataset(index);
                }
                if (xAxis != null) {
                    xRange = xAxis.getRange();
                }
            }
            if (xRange == null) {
                xRange = new Range(Double.NEGATIVE_INFINITY,
                        Double.POSITIVE_INFINITY);
            }
            return DatasetUtils.findRangeBounds(dataset,
                    visibleSeriesKeys, xRange, includeInterval);
        }
        return DatasetUtils.findRangeBounds(dataset, includeInterval);
    }

    /**
     * Returns a (possibly empty) collection of legend items for the series
     * that this renderer is responsible for drawing.
     *
     * @return The legend item collection (never {@code null}).
     */
    @Override
    public LegendItemCollection getLegendItems() {
        if (this.plot == null) {
            return new LegendItemCollection();
        }
        LegendItemCollection result = new LegendItemCollection();
        int index = this.plot.getIndexOf(this);
        XYDataset dataset = this.plot.getDataset(index);
        if (dataset != null) {
            int seriesCount = dataset.getSeriesCount();
            for (int i = 0; i < seriesCount; i++) {
                if (isSeriesVisibleInLegend(i)) {
                    LegendItem item = getLegendItem(index, i);
                    if (item != null) {
                        result.add(item);
                    }
                }
            }

        }
        return result;
    }

    /**
     * Returns a default legend item for the specified series.  Subclasses
     * should override this method to generate customised items.
     *
     * @param datasetIndex  the dataset index (zero-based).
     * @param series  the series index (zero-based).
     *
     * @return A legend item for the series.
     */
    @Override
    public LegendItem getLegendItem(int datasetIndex, int series) {
        XYPlot xyplot = getPlot();
        if (xyplot == null) {
            return null;
        }
        XYDataset dataset = xyplot.getDataset(datasetIndex);
        if (dataset == null) {
            return null;
        }
        String label = this.legendItemLabelGenerator.generateLabel(dataset,
                series);
        String description = label;
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
        Shape shape = lookupLegendShape(series);
        Paint paint = lookupSeriesPaint(series);
        LegendItem item = new LegendItem(label, paint);
        item.setToolTipText(toolTipText);
        item.setURLText(urlText);
        item.setLabelFont(lookupLegendTextFont(series));
        Paint labelPaint = lookupLegendTextPaint(series);
        if (labelPaint != null) {
            item.setLabelPaint(labelPaint);
        }
        item.setSeriesKey(dataset.getSeriesKey(series));
        item.setSeriesIndex(series);
        item.setDataset(dataset);
        item.setDatasetIndex(datasetIndex);

        if (getTreatLegendShapeAsLine()) {
            item.setLineVisible(true);
            item.setLine(shape);
            item.setLinePaint(paint);
            item.setShapeVisible(false);
        }
        else {
            Paint outlinePaint = lookupSeriesOutlinePaint(series);
            Stroke outlineStroke = lookupSeriesOutlineStroke(series);
            item.setOutlinePaint(outlinePaint);
            item.setOutlineStroke(outlineStroke);
        }
        return item;
    }

    /**
     * Fills a band between two values on the axis.  This can be used to color
     * bands between the grid lines.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the domain axis.
     * @param dataArea  the data area.
     * @param start  the start value.
     * @param end  the end value.
     */
    @Override
    public void fillDomainGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double start, double end) {

        double x1 = axis.valueToJava2D(start, dataArea,
                plot.getDomainAxisEdge());
        double x2 = axis.valueToJava2D(end, dataArea,
                plot.getDomainAxisEdge());
        Rectangle2D band;
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
            band = new Rectangle2D.Double(Math.min(x1, x2), dataArea.getMinY(),
                    Math.abs(x2 - x1), dataArea.getHeight());
        }
        else {
            band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(x1, x2),
                    dataArea.getWidth(), Math.abs(x2 - x1));
        }
        Paint paint = plot.getDomainTickBandPaint();

        if (paint != null) {
            g2.setPaint(paint);
            g2.fill(band);
        }

    }

    /**
     * Fills a band between two values on the range axis.  This can be used to
     * color bands between the grid lines.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the range axis.
     * @param dataArea  the data area.
     * @param start  the start value.
     * @param end  the end value.
     */
    @Override
    public void fillRangeGridBand(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double start, double end) {

        double y1 = axis.valueToJava2D(start, dataArea,
                plot.getRangeAxisEdge());
        double y2 = axis.valueToJava2D(end, dataArea, plot.getRangeAxisEdge());
        Rectangle2D band;
        if (plot.getOrientation() == PlotOrientation.VERTICAL) {
            band = new Rectangle2D.Double(dataArea.getMinX(), Math.min(y1, y2),
                dataArea.getWidth(), Math.abs(y2 - y1));
        }
        else {
            band = new Rectangle2D.Double(Math.min(y1, y2), dataArea.getMinY(),
                    Math.abs(y2 - y1), dataArea.getHeight());
        }
        Paint paint = plot.getRangeTickBandPaint();

        if (paint != null) {
            g2.setPaint(paint);
            g2.fill(band);
        }

    }

    /**
     * Draws a line perpendicular to the domain axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data.
     * @param value  the value at which the grid line should be drawn.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     */
    @Override
    public void drawDomainLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {

        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();
        Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, 
                plot.getDomainAxisEdge());
        if (orientation.isHorizontal()) {
            line = new Line2D.Double(dataArea.getMinX(), v, dataArea.getMaxX(),
                    v);
        } else if (orientation.isVertical()) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(line);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }

    /**
     * Draws a line perpendicular to the range axis.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param axis  the value axis.
     * @param dataArea  the area for plotting data.
     * @param value  the value at which the grid line should be drawn.
     * @param paint  the paint.
     * @param stroke  the stroke.
     */
    @Override
    public void drawRangeLine(Graphics2D g2, XYPlot plot, ValueAxis axis,
            Rectangle2D dataArea, double value, Paint paint, Stroke stroke) {

        Range range = axis.getRange();
        if (!range.contains(value)) {
            return;
        }

        PlotOrientation orientation = plot.getOrientation();
        Line2D line = null;
        double v = axis.valueToJava2D(value, dataArea, plot.getRangeAxisEdge());      
        if (orientation == PlotOrientation.HORIZONTAL) {
            line = new Line2D.Double(v, dataArea.getMinY(), v,
                    dataArea.getMaxY());
        } else if (orientation == PlotOrientation.VERTICAL) {
            line = new Line2D.Double(dataArea.getMinX(), v,
                    dataArea.getMaxX(), v);
        }

        g2.setPaint(paint);
        g2.setStroke(stroke);
        Object saved = g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_NORMALIZE);
        g2.draw(line);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, saved);
    }

    /**
     * Draws a line on the chart perpendicular to the x-axis to mark
     * a value or range of values.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param marker  the marker line.
     * @param dataArea  the axis data area.
     */
    @Override
    public void drawDomainMarker(Graphics2D g2, XYPlot plot, 
            ValueAxis domainAxis, Marker marker, Rectangle2D dataArea) {

        if (marker instanceof ValueMarker) {
            ValueMarker vm = (ValueMarker) marker;
            double value = vm.getValue();
            Range range = domainAxis.getRange();
            if (!range.contains(value)) {
                return;
            }

            double v = domainAxis.valueToJava2D(value, dataArea,
                    plot.getDomainAxisEdge());
            PlotOrientation orientation = plot.getOrientation();
            Line2D line = null;
            if (orientation == PlotOrientation.HORIZONTAL) {
                line = new Line2D.Double(dataArea.getMinX(), v,
                        dataArea.getMaxX(), v);
            } else if (orientation == PlotOrientation.VERTICAL) {
                line = new Line2D.Double(v, dataArea.getMinY(), v,
                        dataArea.getMaxY());
            } else {
                throw new IllegalStateException("Unrecognised orientation.");
            }

            final Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, marker.getAlpha()));
            g2.setPaint(marker.getPaint());
            g2.setStroke(marker.getStroke());
            g2.draw(line);

            String label = marker.getLabel();
            RectangleAnchor anchor = marker.getLabelAnchor();
            if (label != null) {
                Font labelFont = marker.getLabelFont();
                g2.setFont(labelFont);
                Point2D coords = calculateDomainMarkerTextAnchorPoint(
                        g2, orientation, dataArea, line.getBounds2D(),
                        marker.getLabelOffset(),
                        LengthAdjustmentType.EXPAND, anchor);
                Rectangle2D r = TextUtils.calcAlignedStringBounds(label, 
                        g2, (float) coords.getX(), (float) coords.getY(), 
                        marker.getLabelTextAnchor());
                g2.setPaint(marker.getLabelBackgroundColor());
                g2.fill(r);
                g2.setPaint(marker.getLabelPaint());
                TextUtils.drawAlignedString(label, g2,
                        (float) coords.getX(), (float) coords.getY(),
                        marker.getLabelTextAnchor());
            }
            g2.setComposite(originalComposite);
        } else if (marker instanceof IntervalMarker) {
            IntervalMarker im = (IntervalMarker) marker;
            double start = im.getStartValue();
            double end = im.getEndValue();
            Range range = domainAxis.getRange();
            if (!(range.intersects(start, end))) {
                return;
            }

            double start2d = domainAxis.valueToJava2D(start, dataArea,
                    plot.getDomainAxisEdge());
            double end2d = domainAxis.valueToJava2D(end, dataArea,
                    plot.getDomainAxisEdge());
            double low = Math.min(start2d, end2d);
            double high = Math.max(start2d, end2d);

            PlotOrientation orientation = plot.getOrientation();
            Rectangle2D rect = null;
            if (orientation == PlotOrientation.HORIZONTAL) {
                // clip top and bottom bounds to data area
                low = Math.max(low, dataArea.getMinY());
                high = Math.min(high, dataArea.getMaxY());
                rect = new Rectangle2D.Double(dataArea.getMinX(),
                        low, dataArea.getWidth(),
                        high - low);
            } else if (orientation == PlotOrientation.VERTICAL) {
                // clip left and right bounds to data area
                low = Math.max(low, dataArea.getMinX());
                high = Math.min(high, dataArea.getMaxX());
                rect = new Rectangle2D.Double(low,
                        dataArea.getMinY(), high - low,
                        dataArea.getHeight());
            }

            final Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, marker.getAlpha()));
            Paint p = marker.getPaint();
            if (p instanceof GradientPaint) {
                GradientPaint gp = (GradientPaint) p;
                GradientPaintTransformer t = im.getGradientPaintTransformer();
                if (t != null) {
                    gp = t.transform(gp, rect);
                }
                g2.setPaint(gp);
            } else {
                g2.setPaint(p);
            }
            g2.fill(rect);

            // now draw the outlines, if visible...
            if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
                if (orientation == PlotOrientation.VERTICAL) {
                    Line2D line = new Line2D.Double();
                    double y0 = dataArea.getMinY();
                    double y1 = dataArea.getMaxY();
                    g2.setPaint(im.getOutlinePaint());
                    g2.setStroke(im.getOutlineStroke());
                    if (range.contains(start)) {
                        line.setLine(start2d, y0, start2d, y1);
                        g2.draw(line);
                    }
                    if (range.contains(end)) {
                        line.setLine(end2d, y0, end2d, y1);
                        g2.draw(line);
                    }
                } else { // PlotOrientation.HORIZONTAL
                    Line2D line = new Line2D.Double();
                    double x0 = dataArea.getMinX();
                    double x1 = dataArea.getMaxX();
                    g2.setPaint(im.getOutlinePaint());
                    g2.setStroke(im.getOutlineStroke());
                    if (range.contains(start)) {
                        line.setLine(x0, start2d, x1, start2d);
                        g2.draw(line);
                    }
                    if (range.contains(end)) {
                        line.setLine(x0, end2d, x1, end2d);
                        g2.draw(line);
                    }
                }
            }

            String label = marker.getLabel();
            RectangleAnchor anchor = marker.getLabelAnchor();
            if (label != null) {
                Font labelFont = marker.getLabelFont();
                g2.setFont(labelFont);
                Point2D coords = calculateDomainMarkerTextAnchorPoint(
                        g2, orientation, dataArea, rect,
                        marker.getLabelOffset(), marker.getLabelOffsetType(),
                        anchor);
                Rectangle2D r = TextUtils.calcAlignedStringBounds(label, 
                        g2, (float) coords.getX(), (float) coords.getY(), 
                        marker.getLabelTextAnchor());
                g2.setPaint(marker.getLabelBackgroundColor());
                g2.fill(r);
                g2.setPaint(marker.getLabelPaint());
                TextUtils.drawAlignedString(label, g2,
                        (float) coords.getX(), (float) coords.getY(),
                        marker.getLabelTextAnchor());
            }
            g2.setComposite(originalComposite);
        }
    }

    /**
     * Calculates the {@code (x, y)} coordinates for drawing a marker label.
     *
     * @param g2  the graphics device.
     * @param orientation  the plot orientation.
     * @param dataArea  the data area.
     * @param markerArea  the rectangle surrounding the marker area.
     * @param markerOffset  the marker label offset.
     * @param labelOffsetType  the label offset type.
     * @param anchor  the label anchor.
     *
     * @return The coordinates for drawing the marker label.
     */
    protected Point2D calculateDomainMarkerTextAnchorPoint(Graphics2D g2,
            PlotOrientation orientation, Rectangle2D dataArea,
            Rectangle2D markerArea, RectangleInsets markerOffset,
            LengthAdjustmentType labelOffsetType, RectangleAnchor anchor) {

        Rectangle2D anchorRect = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    LengthAdjustmentType.CONTRACT, labelOffsetType);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    labelOffsetType, LengthAdjustmentType.CONTRACT);
        }
        return anchor.getAnchorPoint(anchorRect);

    }

    /**
     * Draws a line on the chart perpendicular to the y-axis to mark a value
     * or range of values.
     *
     * @param g2  the graphics device.
     * @param plot  the plot.
     * @param rangeAxis  the range axis.
     * @param marker  the marker line.
     * @param dataArea  the axis data area.
     */
    @Override
    public void drawRangeMarker(Graphics2D g2, XYPlot plot, ValueAxis rangeAxis,
            Marker marker, Rectangle2D dataArea) {

        if (marker instanceof ValueMarker) {
            ValueMarker vm = (ValueMarker) marker;
            double value = vm.getValue();
            Range range = rangeAxis.getRange();
            if (!range.contains(value)) {
                return;
            }

            double v = rangeAxis.valueToJava2D(value, dataArea,
                    plot.getRangeAxisEdge());
            PlotOrientation orientation = plot.getOrientation();
            Line2D line = null;
            if (orientation == PlotOrientation.HORIZONTAL) {
                line = new Line2D.Double(v, dataArea.getMinY(), v,
                        dataArea.getMaxY());
            } else if (orientation == PlotOrientation.VERTICAL) {
                line = new Line2D.Double(dataArea.getMinX(), v,
                        dataArea.getMaxX(), v);
            } else {
                throw new IllegalStateException("Unrecognised orientation.");
            }

            final Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, marker.getAlpha()));
            g2.setPaint(marker.getPaint());
            g2.setStroke(marker.getStroke());
            g2.draw(line);

            String label = marker.getLabel();
            RectangleAnchor anchor = marker.getLabelAnchor();
            if (label != null) {
                Font labelFont = marker.getLabelFont();
                g2.setFont(labelFont);
                Point2D coords = calculateRangeMarkerTextAnchorPoint(
                        g2, orientation, dataArea, line.getBounds2D(),
                        marker.getLabelOffset(),
                        LengthAdjustmentType.EXPAND, anchor);
                Rectangle2D r = TextUtils.calcAlignedStringBounds(label, 
                        g2, (float) coords.getX(), (float) coords.getY(), 
                        marker.getLabelTextAnchor());
                g2.setPaint(marker.getLabelBackgroundColor());
                g2.fill(r);
                g2.setPaint(marker.getLabelPaint());
                TextUtils.drawAlignedString(label, g2,
                        (float) coords.getX(), (float) coords.getY(),
                        marker.getLabelTextAnchor());
            }
            g2.setComposite(originalComposite);
        } else if (marker instanceof IntervalMarker) {
            IntervalMarker im = (IntervalMarker) marker;
            double start = im.getStartValue();
            double end = im.getEndValue();
            Range range = rangeAxis.getRange();
            if (!(range.intersects(start, end))) {
                return;
            }

            double start2d = rangeAxis.valueToJava2D(start, dataArea,
                    plot.getRangeAxisEdge());
            double end2d = rangeAxis.valueToJava2D(end, dataArea,
                    plot.getRangeAxisEdge());
            double low = Math.min(start2d, end2d);
            double high = Math.max(start2d, end2d);

            PlotOrientation orientation = plot.getOrientation();
            Rectangle2D rect = null;
            if (orientation == PlotOrientation.HORIZONTAL) {
                // clip left and right bounds to data area
                low = Math.max(low, dataArea.getMinX());
                high = Math.min(high, dataArea.getMaxX());
                rect = new Rectangle2D.Double(low,
                        dataArea.getMinY(), high - low,
                        dataArea.getHeight());
            } else if (orientation == PlotOrientation.VERTICAL) {
                // clip top and bottom bounds to data area
                low = Math.max(low, dataArea.getMinY());
                high = Math.min(high, dataArea.getMaxY());
                rect = new Rectangle2D.Double(dataArea.getMinX(),
                        low, dataArea.getWidth(),
                        high - low);
            }

            final Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, marker.getAlpha()));
            Paint p = marker.getPaint();
            if (p instanceof GradientPaint) {
                GradientPaint gp = (GradientPaint) p;
                GradientPaintTransformer t = im.getGradientPaintTransformer();
                if (t != null) {
                    gp = t.transform(gp, rect);
                }
                g2.setPaint(gp);
            } else {
                g2.setPaint(p);
            }
            g2.fill(rect);

            // now draw the outlines, if visible...
            if (im.getOutlinePaint() != null && im.getOutlineStroke() != null) {
                if (orientation == PlotOrientation.VERTICAL) {
                    Line2D line = new Line2D.Double();
                    double x0 = dataArea.getMinX();
                    double x1 = dataArea.getMaxX();
                    g2.setPaint(im.getOutlinePaint());
                    g2.setStroke(im.getOutlineStroke());
                    if (range.contains(start)) {
                        line.setLine(x0, start2d, x1, start2d);
                        g2.draw(line);
                    }
                    if (range.contains(end)) {
                        line.setLine(x0, end2d, x1, end2d);
                        g2.draw(line);
                    }
                } else { // PlotOrientation.HORIZONTAL
                    Line2D line = new Line2D.Double();
                    double y0 = dataArea.getMinY();
                    double y1 = dataArea.getMaxY();
                    g2.setPaint(im.getOutlinePaint());
                    g2.setStroke(im.getOutlineStroke());
                    if (range.contains(start)) {
                        line.setLine(start2d, y0, start2d, y1);
                        g2.draw(line);
                    }
                    if (range.contains(end)) {
                        line.setLine(end2d, y0, end2d, y1);
                        g2.draw(line);
                    }
                }
            }

            String label = marker.getLabel();
            RectangleAnchor anchor = marker.getLabelAnchor();
            if (label != null) {
                Font labelFont = marker.getLabelFont();
                g2.setFont(labelFont);
                Point2D coords = calculateRangeMarkerTextAnchorPoint(
                        g2, orientation, dataArea, rect,
                        marker.getLabelOffset(), marker.getLabelOffsetType(),
                        anchor);
                Rectangle2D r = TextUtils.calcAlignedStringBounds(label, 
                        g2, (float) coords.getX(), (float) coords.getY(), 
                        marker.getLabelTextAnchor());
                g2.setPaint(marker.getLabelBackgroundColor());
                g2.fill(r);
                g2.setPaint(marker.getLabelPaint());
                TextUtils.drawAlignedString(label, g2,
                        (float) coords.getX(), (float) coords.getY(),
                        marker.getLabelTextAnchor());
            }
            g2.setComposite(originalComposite);
        }
    }

    /**
     * Calculates the (x, y) coordinates for drawing a marker label.
     *
     * @param g2  the graphics device.
     * @param orientation  the plot orientation.
     * @param dataArea  the data area.
     * @param markerArea  the marker area.
     * @param markerOffset  the marker offset.
     * @param labelOffsetForRange  ??
     * @param anchor  the label anchor.
     *
     * @return The coordinates for drawing the marker label.
     */
    private Point2D calculateRangeMarkerTextAnchorPoint(Graphics2D g2,
           PlotOrientation orientation, Rectangle2D dataArea,
           Rectangle2D markerArea, RectangleInsets markerOffset,
           LengthAdjustmentType labelOffsetForRange, RectangleAnchor anchor) {

        Rectangle2D anchorRect = null;
        if (orientation == PlotOrientation.HORIZONTAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    labelOffsetForRange, LengthAdjustmentType.CONTRACT);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            anchorRect = markerOffset.createAdjustedRectangle(markerArea,
                    LengthAdjustmentType.CONTRACT, labelOffsetForRange);
        }
        return anchor.getAnchorPoint(anchorRect);

    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the renderer does not support
     *         cloning.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        AbstractXYItemRenderer clone = (AbstractXYItemRenderer) super.clone();
        // 'plot' : just retain reference, not a deep copy

        clone.itemLabelGeneratorMap = CloneUtils.cloneMapValues(
                this.itemLabelGeneratorMap);
        if (this.defaultItemLabelGenerator != null
                && this.defaultItemLabelGenerator instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.defaultItemLabelGenerator;
            clone.defaultItemLabelGenerator = (XYItemLabelGenerator) pc.clone();
        }

        clone.toolTipGeneratorMap = CloneUtils.cloneMapValues(
                this.toolTipGeneratorMap);
        if (this.defaultToolTipGenerator != null
                && this.defaultToolTipGenerator instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.defaultToolTipGenerator;
            clone.defaultToolTipGenerator = (XYToolTipGenerator) pc.clone();
        }

        if (this.legendItemLabelGenerator instanceof PublicCloneable) {
            clone.legendItemLabelGenerator = (XYSeriesLabelGenerator)
                    ObjectUtils.clone(this.legendItemLabelGenerator);
        }
        if (this.legendItemToolTipGenerator instanceof PublicCloneable) {
            clone.legendItemToolTipGenerator = (XYSeriesLabelGenerator)
                    ObjectUtils.clone(this.legendItemToolTipGenerator);
        }
        if (this.legendItemURLGenerator instanceof PublicCloneable) {
            clone.legendItemURLGenerator = (XYSeriesLabelGenerator)
                    ObjectUtils.clone(this.legendItemURLGenerator);
        }

        clone.foregroundAnnotations = (List) ObjectUtils.deepClone(
                this.foregroundAnnotations);
        clone.backgroundAnnotations = (List) ObjectUtils.deepClone(
                this.backgroundAnnotations);

        return clone;
    }

    /**
     * Tests this renderer for equality with another object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractXYItemRenderer)) {
            return false;
        }
        AbstractXYItemRenderer that = (AbstractXYItemRenderer) obj;
        if (!this.itemLabelGeneratorMap.equals(that.itemLabelGeneratorMap)) {
            return false;
        }
        if (!Objects.equals(this.defaultItemLabelGenerator,
                that.defaultItemLabelGenerator)) {
            return false;
        }
        if (!this.toolTipGeneratorMap.equals(that.toolTipGeneratorMap)) {
            return false;
        }
        if (!Objects.equals(this.defaultToolTipGenerator,
                that.defaultToolTipGenerator)) {
            return false;
        }
        if (!Objects.equals(this.urlGenerator, that.urlGenerator)) {
            return false;
        }
        if (!this.foregroundAnnotations.equals(that.foregroundAnnotations)) {
            return false;
        }
        if (!this.backgroundAnnotations.equals(that.backgroundAnnotations)) {
            return false;
        }
        if (!Objects.equals(this.legendItemLabelGenerator,
                that.legendItemLabelGenerator)) {
            return false;
        }
        if (!Objects.equals(this.legendItemToolTipGenerator,
                that.legendItemToolTipGenerator)) {
            return false;
        }
        if (!Objects.equals(this.legendItemURLGenerator,
                that.legendItemURLGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns the drawing supplier from the plot.
     *
     * @return The drawing supplier (possibly {@code null}).
     */
    @Override
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        XYPlot p = getPlot();
        if (p != null) {
            result = p.getDrawingSupplier();
        }
        return result;
    }

    /**
     * Considers the current (x, y) coordinate and updates the crosshair point
     * if it meets the criteria (usually means the (x, y) coordinate is the
     * closest to the anchor point so far).
     *
     * @param crosshairState  the crosshair state ({@code null} permitted,
     *                        but the method does nothing in that case).
     * @param x  the x-value (in data space).
     * @param y  the y-value (in data space).
     * @param datasetIndex  the index of the dataset for the point.
     * @param transX  the x-value translated to Java2D space.
     * @param transY  the y-value translated to Java2D space.
     * @param orientation  the plot orientation ({@code null} not
     *                     permitted).
     */
    protected void updateCrosshairValues(CrosshairState crosshairState,
            double x, double y, int datasetIndex,
            double transX, double transY, PlotOrientation orientation) {

        Args.nullNotPermitted(orientation, "orientation");
        if (crosshairState != null) {
            // do we need to update the crosshair values?
            if (this.plot.isDomainCrosshairLockedOnData()) {
                if (this.plot.isRangeCrosshairLockedOnData()) {
                    // both axes
                    crosshairState.updateCrosshairPoint(x, y, datasetIndex,
                            transX, transY, orientation);
                }
                else {
                    // just the domain axis...
                    crosshairState.updateCrosshairX(x, transX, datasetIndex);
                }
            }
            else {
                if (this.plot.isRangeCrosshairLockedOnData()) {
                    // just the range axis...
                    crosshairState.updateCrosshairY(y, transY, datasetIndex);
                }
            }
        }

    }

    /**
     * Draws an item label.
     *
     * @param g2  the graphics device.
     * @param orientation  the orientation.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param x  the x coordinate (in Java2D space).
     * @param y  the y coordinate (in Java2D space).
     * @param negative  indicates a negative value (which affects the item
     *                  label position).
     */
    protected void drawItemLabel(Graphics2D g2, PlotOrientation orientation,
            XYDataset dataset, int series, int item, double x, double y,
            boolean negative) {

        XYItemLabelGenerator generator = getItemLabelGenerator(series, item);
        if (generator != null) {
            Font labelFont = getItemLabelFont(series, item);
            Paint paint = getItemLabelPaint(series, item);
            g2.setFont(labelFont);
            g2.setPaint(paint);
            String label = generator.generateLabel(dataset, series, item);

            // get the label position..
            ItemLabelPosition position;
            if (!negative) {
                position = getPositiveItemLabelPosition(series, item);
            }
            else {
                position = getNegativeItemLabelPosition(series, item);
            }

            // work out the label anchor point...
            Point2D anchorPoint = calculateLabelAnchorPoint(
                    position.getItemLabelAnchor(), x, y, orientation);
            TextUtils.drawRotatedString(label, g2,
                    (float) anchorPoint.getX(), (float) anchorPoint.getY(),
                    position.getTextAnchor(), position.getAngle(),
                    position.getRotationAnchor());
        }

    }

    /**
     * Draws all the annotations for the specified layer.
     *
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param layer  the layer ({@code null} not permitted).
     * @param info  the plot rendering info.
     */
    @Override
    public void drawAnnotations(Graphics2D g2, Rectangle2D dataArea,
            ValueAxis domainAxis, ValueAxis rangeAxis, Layer layer,
            PlotRenderingInfo info) {

        Iterator iterator = null;
        if (layer.equals(Layer.FOREGROUND)) {
            iterator = this.foregroundAnnotations.iterator();
        }
        else if (layer.equals(Layer.BACKGROUND)) {
            iterator = this.backgroundAnnotations.iterator();
        }
        else {
            // should not get here
            throw new RuntimeException("Unknown layer.");
        }
        while (iterator.hasNext()) {
            XYAnnotation annotation = (XYAnnotation) iterator.next();
            int index = this.plot.getIndexOf(this);
            annotation.draw(g2, this.plot, dataArea, domainAxis, rangeAxis,
                    index, info);
        }

    }

    /**
     * Adds an entity to the collection.  Note the the {@code entityX} and
     * {@code entityY} coordinates are in Java2D space, should already be 
     * adjusted for the plot orientation, and will only be used if 
     * {@code hotspot} is {@code null}.
     *
     * @param entities  the entity collection being populated.
     * @param hotspot  the entity area (if {@code null} a default will be
     *              used).
     * @param dataset  the dataset.
     * @param series  the series.
     * @param item  the item.
     * @param entityX  the entity x-coordinate (in Java2D space, only used if 
     *         {@code hotspot} is {@code null}).
     * @param entityY  the entity y-coordinate (in Java2D space, only used if 
     *         {@code hotspot} is {@code null}).
     */
    protected void addEntity(EntityCollection entities, Shape hotspot,
            XYDataset dataset, int series, int item, double entityX, 
            double entityY) {
        
        if (!getItemCreateEntity(series, item)) {
            return;
        }

        // if not hotspot is provided, we create a default based on the 
        // provided data coordinates (which are already in Java2D space)
        if (hotspot == null) {
            double r = getDefaultEntityRadius();
            double w = r * 2;
            hotspot = new Ellipse2D.Double(entityX - r, entityY - r, w, w);
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
     * Utility method delegating to {@link GeneralPath#moveTo} taking double as
     * parameters.
     *
     * @param hotspot  the region under construction ({@code null} not 
     *           permitted);
     * @param x  the x coordinate;
     * @param y  the y coordinate;
     */
    protected static void moveTo(GeneralPath hotspot, double x, double y) {
        hotspot.moveTo((float) x, (float) y);
    }

    /**
     * Utility method delegating to {@link GeneralPath#lineTo} taking double as
     * parameters.
     *
     * @param hotspot  the region under construction ({@code null} not 
     *           permitted);
     * @param x  the x coordinate;
     * @param y  the y coordinate;
     */
    protected static void lineTo(GeneralPath hotspot, double x, double y) {
        hotspot.lineTo((float) x, (float) y);
    }
 
}
