/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2017, by Object Refinery Limited and Contributors.
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
 * -------------------
 * XYAreaRenderer.java
 * -------------------
 * (C) Copyright 2002-2017, by Hari and Contributors.
 *
 * Original Author:  Hari (ourhari@hotmail.com);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Richard Atkinson;
 *                   Christian W. Zuckschwerdt;
 *                   Martin Krauskopf;
 *                   Ulrich Voigt (patch #312);
 *
 * Changes:
 * --------
 * 03-Apr-2002 : Version 1, contributed by Hari.  This class is based on the
 *               StandardXYItemRenderer class (DG);
 * 09-Apr-2002 : Removed the translated zero from the drawItem method -
 *               overridden the initialise() method to calculate it (DG);
 * 30-May-2002 : Added tool tip generator to constructor to match super
 *               class (DG);
 * 25-Jun-2002 : Removed unnecessary local variable (DG);
 * 05-Aug-2002 : Small modification to drawItem method to support URLs for HTML
 *               image maps (RA);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 07-Nov-2002 : Renamed AreaXYItemRenderer --> XYAreaRenderer (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 01-May-2003 : Modified drawItem() method signature (DG);
 * 27-Jul-2003 : Made line and polygon properties protected rather than
 *               private (RA);
 * 30-Jul-2003 : Modified entity constructor (CZ);
 * 20-Aug-2003 : Implemented Cloneable and PublicCloneable (DG);
 * 16-Sep-2003 : Changed ChartRenderingInfo --> PlotRenderingInfo (DG);
 * 07-Oct-2003 : Added renderer state (DG);
 * 08-Dec-2003 : Modified hotspot for chart entity (DG);
 * 10-Feb-2004 : Changed the drawItem() method to make cut-and-paste overriding
 *               easier.  Also moved state class into this class (DG);
 * 25-Feb-2004 : Replaced CrosshairInfo with CrosshairState.  Renamed
 *               XYToolTipGenerator --> XYItemLabelGenerator (DG);
 * 15-Jul-2004 : Switched getX() with getXValue() and getY() with
 *               getYValue() (DG);
 * 11-Nov-2004 : Now uses ShapeUtilities to translate shapes (DG);
 * 19-Jan-2005 : Now accesses primitives only from dataset (DG);
 * 21-Mar-2005 : Override getLegendItem() and equals() methods (DG);
 * 20-Apr-2005 : Use generators for legend tooltips and URLs (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 06-Feb-2007 : Fixed bug 1086307, crosshairs with multiple axes (DG);
 * 14-Feb-2007 : Fixed bug in clone() (DG);
 * 20-Apr-2007 : Updated getLegendItem() for renderer change (DG);
 * 04-May-2007 : Set processVisibleItemsOnly flag to false (DG);
 * 17-May-2007 : Set datasetIndex and seriesIndex in getLegendItem() (DG);
 * 18-May-2007 : Set dataset and seriesKey for LegendItem (DG);
 * 17-Jun-2008 : Apply legend font and paint attributes (DG);
 * 31-Dec-2008 : Fix for bug 2471906 - dashed outlines performance issue (DG);
 * 11-Jun-2009 : Added a useFillPaint flag and a GradientPaintTransformer for
 *               the paint under the series (DG);
 * 06-Oct-2011 : Avoid GeneralPath methods requiring Java 1.5 (MK);
 * 03-Jul-2013 : Use ParamChecks (DG);
 * 04-Aug-2014 : Restrict entity hotspot to plot area (patch #312) (UV);
 * 18-Feb-2017 : Updates for crosshairs (bug #36) (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jfree.chart.HashUtils;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.XYSeriesLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.GradientPaintTransformer;
import org.jfree.chart.ui.StandardGradientPaintTransformer;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.xy.XYDataset;

/**
 * Area item renderer for an {@link XYPlot}.  This class can draw (a) shapes at
 * each point, or (b) lines between points, or (c) both shapes and lines,
 * or (d) filled areas, or (e) filled areas and shapes. The example shown here
 * is generated by the {@code XYAreaRendererDemo1.java} program included
 * in the JFreeChart demo collection:
 * <br><br>
 * <img src="../../../../../images/XYAreaRendererSample.png"
 * alt="XYAreaRendererSample.png">
 */
public class XYAreaRenderer extends AbstractXYItemRenderer
        implements XYItemRenderer, PublicCloneable {

    /** For serialization. */
    private static final long serialVersionUID = -4481971353973876747L;

    /**
     * A state object used by this renderer.
     */
    static class XYAreaRendererState extends XYItemRendererState {

        /** Working storage for the area under one series. */
        public GeneralPath area;

        /** Working line that can be recycled. */
        public Line2D line;

        /**
         * Creates a new state.
         *
         * @param info  the plot rendering info.
         */
        public XYAreaRendererState(PlotRenderingInfo info) {
            super(info);
            this.area = new GeneralPath();
            this.line = new Line2D.Double();
        }

    }

    /** Useful constant for specifying the type of rendering (shapes only). */
    public static final int SHAPES = 1;

    /** Useful constant for specifying the type of rendering (lines only). */
    public static final int LINES = 2;

    /**
     * Useful constant for specifying the type of rendering (shapes and lines).
     */
    public static final int SHAPES_AND_LINES = 3;

    /** Useful constant for specifying the type of rendering (area only). */
    public static final int AREA = 4;

    /**
     * Useful constant for specifying the type of rendering (area and shapes).
     */
    public static final int AREA_AND_SHAPES = 5;

    /** A flag indicating whether or not shapes are drawn at each XY point. */
    private boolean plotShapes;

    /** A flag indicating whether or not lines are drawn between XY points. */
    private boolean plotLines;

    /** A flag indicating whether or not Area are drawn at each XY point. */
    private boolean plotArea;

    /** A flag that controls whether or not the outline is shown. */
    private boolean showOutline;

    /**
     * The shape used to represent an area in each legend item (this should
     * never be {@code null}).
     */
    private transient Shape legendArea;

    /**
     * A flag that can be set to specify that the fill paint should be used
     * to fill the area under the renderer.
     * 
     * @since 1.0.14
     */
    private boolean useFillPaint;

    /**
     * A transformer that is applied to the paint used to fill under the
     * area *if* it is an instance of GradientPaint.
     *
     * @since 1.0.14
     */
    private GradientPaintTransformer gradientTransformer;

    /**
     * Constructs a new renderer.
     */
    public XYAreaRenderer() {
        this(AREA);
    }

    /**
     * Constructs a new renderer.
     *
     * @param type  the type of the renderer.
     */
    public XYAreaRenderer(int type) {
        this(type, null, null);
    }

    /**
     * Constructs a new renderer.  To specify the type of renderer, use one of
     * the constants: {@code SHAPES}, {@code LINES}, {@code SHAPES_AND_LINES}, 
     * {@code AREA} or {@code AREA_AND_SHAPES}.
     *
     * @param type  the type of renderer.
     * @param toolTipGenerator  the tool tip generator ({@code null} permitted).
     * @param urlGenerator  the URL generator ({@code null} permitted).
     */
    public XYAreaRenderer(int type, XYToolTipGenerator toolTipGenerator,
                          XYURLGenerator urlGenerator) {

        super();
        setDefaultToolTipGenerator(toolTipGenerator);
        setURLGenerator(urlGenerator);

        if (type == SHAPES) {
            this.plotShapes = true;
        }
        if (type == LINES) {
            this.plotLines = true;
        }
        if (type == SHAPES_AND_LINES) {
            this.plotShapes = true;
            this.plotLines = true;
        }
        if (type == AREA) {
            this.plotArea = true;
        }
        if (type == AREA_AND_SHAPES) {
            this.plotArea = true;
            this.plotShapes = true;
        }
        this.showOutline = false;
        GeneralPath area = new GeneralPath();
        area.moveTo(0.0f, -4.0f);
        area.lineTo(3.0f, -2.0f);
        area.lineTo(4.0f, 4.0f);
        area.lineTo(-4.0f, 4.0f);
        area.lineTo(-3.0f, -2.0f);
        area.closePath();
        this.legendArea = area;
        this.useFillPaint = false;
        this.gradientTransformer = new StandardGradientPaintTransformer();
    }

    /**
     * Returns true if shapes are being plotted by the renderer.
     *
     * @return {@code true} if shapes are being plotted by the renderer.
     */
    public boolean getPlotShapes() {
        return this.plotShapes;
    }

    /**
     * Returns true if lines are being plotted by the renderer.
     *
     * @return {@code true} if lines are being plotted by the renderer.
     */
    public boolean getPlotLines() {
        return this.plotLines;
    }

    /**
     * Returns true if Area is being plotted by the renderer.
     *
     * @return {@code true} if Area is being plotted by the renderer.
     */
    public boolean getPlotArea() {
        return this.plotArea;
    }

    /**
     * Returns a flag that controls whether or not outlines of the areas are
     * drawn.
     *
     * @return The flag.
     *
     * @see #setOutline(boolean)
     */
    public boolean isOutline() {
        return this.showOutline;
    }

    /**
     * Sets a flag that controls whether or not outlines of the areas are drawn
     * and sends a {@link RendererChangeEvent} to all registered listeners.
     *
     * @param show  the flag.
     *
     * @see #isOutline()
     */
    public void setOutline(boolean show) {
        this.showOutline = show;
        fireChangeEvent();
    }

    /**
     * Returns the shape used to represent an area in the legend.
     *
     * @return The legend area (never {@code null}).
     */
    public Shape getLegendArea() {
        return this.legendArea;
    }

    /**
     * Sets the shape used as an area in each legend item and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param area  the area ({@code null} not permitted).
     */
    public void setLegendArea(Shape area) {
        Args.nullNotPermitted(area, "area");
        this.legendArea = area;
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether the series fill paint is used to
     * fill the area under the line.
     *
     * @return A boolean.
     *
     * @since 1.0.14
     */
    public boolean getUseFillPaint() {
        return this.useFillPaint;
    }

    /**
     * Sets the flag that controls whether or not the series fill paint is
     * used to fill the area under the line and sends a
     * {@link RendererChangeEvent} to all listeners.
     *
     * @param use  the new flag value.
     *
     * @since 1.0.14
     */
    public void setUseFillPaint(boolean use) {
        this.useFillPaint = use;
        fireChangeEvent();
    }

    /**
     * Returns the gradient paint transformer.
     *
     * @return The gradient paint transformer (never {@code null}).
     *
     * @since 1.0.14
     */
    public GradientPaintTransformer getGradientTransformer() {
        return this.gradientTransformer;
    }

    /**
     * Sets the gradient paint transformer and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param transformer  the transformer ({@code null} not permitted).
     *
     * @since 1.0.14
     */
    public void setGradientTransformer(GradientPaintTransformer transformer) {
        Args.nullNotPermitted(transformer, "transformer");
        this.gradientTransformer = transformer;
        fireChangeEvent();
    }

    /**
     * Initialises the renderer and returns a state object that should be
     * passed to all subsequent calls to the drawItem() method.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param data  the data.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return A state object for use by the renderer.
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea,
            XYPlot plot, XYDataset data, PlotRenderingInfo info) {
        XYAreaRendererState state = new XYAreaRendererState(info);

        // in the rendering process, there is special handling for item
        // zero, so we can't support processing of visible data items only
        state.setProcessVisibleItemsOnly(false);
        return state;
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
        LegendItem result = null;
        XYPlot xyplot = getPlot();
        if (xyplot != null) {
            XYDataset dataset = xyplot.getDataset(datasetIndex);
            if (dataset != null) {
                XYSeriesLabelGenerator lg = getLegendItemLabelGenerator();
                String label = lg.generateLabel(dataset, series);
                String description = label;
                String toolTipText = null;
                if (getLegendItemToolTipGenerator() != null) {
                    toolTipText = getLegendItemToolTipGenerator().generateLabel(
                            dataset, series);
                }
                String urlText = null;
                if (getLegendItemURLGenerator() != null) {
                    urlText = getLegendItemURLGenerator().generateLabel(
                            dataset, series);
                }
                Paint paint = lookupSeriesPaint(series);
                result = new LegendItem(label, description, toolTipText,
                        urlText, this.legendArea, paint);
                result.setLabelFont(lookupLegendTextFont(series));
                Paint labelPaint = lookupLegendTextPaint(series);
                if (labelPaint != null) {
                    result.setLabelPaint(labelPaint);
                }
                result.setDataset(dataset);
                result.setDatasetIndex(datasetIndex);
                result.setSeriesKey(dataset.getSeriesKey(series));
                result.setSeriesIndex(series);
            }
        }
        return result;
    }

    /**
     * Draws the visual representation of a single data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area within which the data is being drawn.
     * @param info  collects information about the drawing.
     * @param plot  the plot (can be used to obtain standard color information
     *              etc).
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param crosshairState  crosshair information for the plot
     *                        ({@code null} permitted).
     * @param pass  the pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state,
            Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
            ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
            int series, int item, CrosshairState crosshairState, int pass) {

        if (!getItemVisible(series, item)) {
            return;
        }
        XYAreaRendererState areaState = (XYAreaRendererState) state;

        // get the data point...
        double x1 = dataset.getXValue(series, item);
        double y1 = dataset.getYValue(series, item);
        if (Double.isNaN(y1)) {
            y1 = 0.0;
        }
        double transX1 = domainAxis.valueToJava2D(x1, dataArea,
                plot.getDomainAxisEdge());
        double transY1 = rangeAxis.valueToJava2D(y1, dataArea,
                plot.getRangeAxisEdge());

        // get the previous point and the next point so we can calculate a
        // "hot spot" for the area (used by the chart entity)...
        int itemCount = dataset.getItemCount(series);
        double x0 = dataset.getXValue(series, Math.max(item - 1, 0));
        double y0 = dataset.getYValue(series, Math.max(item - 1, 0));
        if (Double.isNaN(y0)) {
            y0 = 0.0;
        }
        double transX0 = domainAxis.valueToJava2D(x0, dataArea,
                plot.getDomainAxisEdge());
        double transY0 = rangeAxis.valueToJava2D(y0, dataArea,
                plot.getRangeAxisEdge());

        double x2 = dataset.getXValue(series, Math.min(item + 1,
                itemCount - 1));
        double y2 = dataset.getYValue(series, Math.min(item + 1,
                itemCount - 1));
        if (Double.isNaN(y2)) {
            y2 = 0.0;
        }
        double transX2 = domainAxis.valueToJava2D(x2, dataArea,
                plot.getDomainAxisEdge());
        double transY2 = rangeAxis.valueToJava2D(y2, dataArea,
                plot.getRangeAxisEdge());

        double transZero = rangeAxis.valueToJava2D(0.0, dataArea,
                plot.getRangeAxisEdge());

        if (item == 0) {  // create a new area polygon for the series
            areaState.area = new GeneralPath();
            // the first point is (x, 0)
            double zero = rangeAxis.valueToJava2D(0.0, dataArea,
                    plot.getRangeAxisEdge());
            if (plot.getOrientation().isVertical()) {
                moveTo(areaState.area, transX1, zero);
            } else if (plot.getOrientation().isHorizontal()) {
                moveTo(areaState.area, zero, transX1);
            }
        }

        // Add each point to Area (x, y)
        if (plot.getOrientation().isVertical()) {
            lineTo(areaState.area, transX1, transY1);
        } else if (plot.getOrientation().isHorizontal()) {
            lineTo(areaState.area, transY1, transX1);
        }

        PlotOrientation orientation = plot.getOrientation();
        Paint paint = getItemPaint(series, item);
        Stroke stroke = getItemStroke(series, item);
        g2.setPaint(paint);
        g2.setStroke(stroke);

        Shape shape;
        if (getPlotShapes()) {
            shape = getItemShape(series, item);
            if (orientation == PlotOrientation.VERTICAL) {
                shape = ShapeUtils.createTranslatedShape(shape, transX1,
                        transY1);
            } else if (orientation == PlotOrientation.HORIZONTAL) {
                shape = ShapeUtils.createTranslatedShape(shape, transY1,
                        transX1);
            }
            g2.draw(shape);
        }

        if (getPlotLines()) {
            if (item > 0) {
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    areaState.line.setLine(transX0, transY0, transX1, transY1);
                } else if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
                    areaState.line.setLine(transY0, transX0, transY1, transX1);
                }
                g2.draw(areaState.line);
            }
        }

        // Check if the item is the last item for the series.
        // and number of items > 0.  We can't draw an area for a single point.
        if (getPlotArea() && item > 0 && item == (itemCount - 1)) {

            if (orientation == PlotOrientation.VERTICAL) {
                // Add the last point (x,0)
                lineTo(areaState.area, transX1, transZero);
                areaState.area.closePath();
            } else if (orientation == PlotOrientation.HORIZONTAL) {
                // Add the last point (x,0)
                lineTo(areaState.area, transZero, transX1);
                areaState.area.closePath();
            }

            if (this.useFillPaint) {
                paint = lookupSeriesFillPaint(series);
            }
            if (paint instanceof GradientPaint) {
                GradientPaint gp = (GradientPaint) paint;
                GradientPaint adjGP = this.gradientTransformer.transform(gp,
                        dataArea);
                g2.setPaint(adjGP);
            }
            g2.fill(areaState.area);

            // draw an outline around the Area.
            if (isOutline()) {
                Shape area = areaState.area;

                // Java2D has some issues drawing dashed lines around "large"
                // geometrical shapes - for example, see bug 6620013 in the
                // Java bug database.  So, we'll check if the outline is
                // dashed and, if it is, do our own clipping before drawing
                // the outline...
                Stroke outlineStroke = lookupSeriesOutlineStroke(series);
                if (outlineStroke instanceof BasicStroke) {
                    BasicStroke bs = (BasicStroke) outlineStroke;
                    if (bs.getDashArray() != null) {
                        Area poly = new Area(areaState.area);
                        // we make the clip region slightly larger than the
                        // dataArea so that the clipped edges don't show lines
                        // on the chart
                        Area clip = new Area(new Rectangle2D.Double(
                                dataArea.getX() - 5.0, dataArea.getY() - 5.0,
                                dataArea.getWidth() + 10.0,
                                dataArea.getHeight() + 10.0));
                        poly.intersect(clip);
                        area = poly;
                    }
                } // end of workaround

                g2.setStroke(outlineStroke);
                g2.setPaint(lookupSeriesOutlinePaint(series));
                g2.draw(area);
            }
        }

        int datasetIndex = plot.indexOf(dataset);
        updateCrosshairValues(crosshairState, x1, y1, datasetIndex,
                transX1, transY1, orientation);

        // collect entity and tool tip information...
        EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            GeneralPath hotspot = new GeneralPath();
            if (plot.getOrientation() == PlotOrientation.HORIZONTAL) {
                moveTo(hotspot, transZero, ((transX0 + transX1) / 2.0));
                lineTo(hotspot, ((transY0 + transY1) / 2.0), ((transX0 + transX1) / 2.0));
                lineTo(hotspot, transY1, transX1);
                lineTo(hotspot, ((transY1 + transY2) / 2.0), ((transX1 + transX2) / 2.0));
                lineTo(hotspot, transZero, ((transX1 + transX2) / 2.0));
            } else { // vertical orientation
                moveTo(hotspot, ((transX0 + transX1) / 2.0), transZero);
                lineTo(hotspot, ((transX0 + transX1) / 2.0), ((transY0 + transY1) / 2.0));
                lineTo(hotspot, transX1, transY1);
                lineTo(hotspot, ((transX1 + transX2) / 2.0), ((transY1 + transY2) / 2.0));
                lineTo(hotspot, ((transX1 + transX2) / 2.0), transZero);
            }
            hotspot.closePath();

            // limit the entity hotspot area to the data area
            Area dataAreaHotspot = new Area(hotspot);
            dataAreaHotspot.intersect(new Area(dataArea));

            if (dataAreaHotspot.isEmpty() == false) {
                addEntity(entities, dataAreaHotspot, dataset, series, item, 
                        0.0, 0.0);
            }
        }

    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the renderer cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        XYAreaRenderer clone = (XYAreaRenderer) super.clone();
        clone.legendArea = ShapeUtils.clone(this.legendArea);
        return clone;
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
        if (!(obj instanceof XYAreaRenderer)) {
            return false;
        }
        XYAreaRenderer that = (XYAreaRenderer) obj;
        if (this.plotArea != that.plotArea) {
            return false;
        }
        if (this.plotLines != that.plotLines) {
            return false;
        }
        if (this.plotShapes != that.plotShapes) {
            return false;
        }
        if (this.showOutline != that.showOutline) {
            return false;
        }
        if (this.useFillPaint != that.useFillPaint) {
            return false;
        }
        if (!this.gradientTransformer.equals(that.gradientTransformer)) {
            return false;
        }
        if (!ShapeUtils.equal(this.legendArea, that.legendArea)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = HashUtils.hashCode(result, this.plotArea);
        result = HashUtils.hashCode(result, this.plotLines);
        result = HashUtils.hashCode(result, this.plotShapes);
        result = HashUtils.hashCode(result, this.useFillPaint);
        return result;
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
        this.legendArea = SerialUtils.readShape(stream);
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
        SerialUtils.writeShape(this.legendArea, stream);
    }
}
