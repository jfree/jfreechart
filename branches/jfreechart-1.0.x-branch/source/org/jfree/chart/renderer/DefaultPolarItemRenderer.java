/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -----------------------------
 * DefaultPolarItemRenderer.java
 * -----------------------------
 * (C) Copyright 2004-2009, by Solution Engineering, Inc. and
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
 *
 */

package org.jfree.chart.renderer;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.text.TextUtilities;
import org.jfree.util.BooleanList;
import org.jfree.util.BooleanUtilities;
import org.jfree.util.ShapeUtilities;

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
    private Composite fillComposite;

    /**
     * Flag that controls whether item shapes are visible or not.
     * 
     * @since 1.0.14
     */
    private boolean shapesVisible;
    
    
    /**
     * Creates a new instance of DefaultPolarItemRenderer
     */
    public DefaultPolarItemRenderer() {
        this.seriesFilled = new BooleanList();
        this.drawOutlineWhenFilled = true;
        this.fillComposite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.3f);
        this.shapesVisible = true;
    }

    /**
     * Set the plot associated with this renderer.
     *
     * @param plot  the plot.
     *
     * @see #getPlot()
     */
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
    public PolarPlot getPlot() {
        return this.plot;
    }

    /**
     * Returns <code>true</code> if the renderer will draw an outline around
     * a filled polygon, <code>false/<code> otherwise.
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
     * @return The composite (never <code>null</code>).
     *
     * @since 1.0.14
     */
    public Composite getFillComposite() {
        return this.fillComposite;
    }

    /**
     * Set the composite which will be used for filling polygons and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param composite  the composite to use (<code>null</code> not
     *         permitted).
     *
     * @since 1.0.14
     */
    public void setFillComposite(Composite composite) {
        if (composite == null) {
            throw new IllegalArgumentException("Null 'composite' argument.");
        }
        this.fillComposite = composite;
        fireChangeEvent();
    }

    /**
     * Returns <code>true</tt> if a shape will be drawn for every item, or
     * <code>false</code> if not.
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
    public void setShapesVisible(boolean shapesVisible) {
        this.shapesVisible = shapesVisible;
    }

    /**
     * Returns the drawing supplier from the plot.
     *
     * @return The drawing supplier.
     */
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        PolarPlot p = getPlot();
        if (p != null) {
            result = p.getDrawingSupplier();
        }
        return result;
    }

    /**
     * Returns <code>true</code> if the renderer should fill the specified
     * series, and <code>false</code> otherwise.
     *
     * @param series  the series index (zero-based).
     *
     * @return A boolean.
     */
    public boolean isSeriesFilled(int series) {
        boolean result = false;
        Boolean b = this.seriesFilled.getBoolean(series);
        if (b != null) {
            result = b.booleanValue();
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
        this.seriesFilled.setBoolean(series, BooleanUtilities.valueOf(filled));
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
    public void drawSeries(Graphics2D g2, Rectangle2D dataArea,
            PlotRenderingInfo info, PolarPlot plot, XYDataset dataset,
            int seriesIndex) {

        Polygon poly = new Polygon();
        ValueAxis axis = plot.getAxisForDataset(plot.indexOf(dataset));
        final int numPoints = dataset.getItemCount(seriesIndex);
        for (int i = 0; i < numPoints; i++) {
            double theta = dataset.getXValue(seriesIndex, i);
            double radius = dataset.getYValue(seriesIndex, i);
            Point p = plot.translateToJava2D(theta, radius, axis, dataArea);
            poly.addPoint(p.x, p.y);
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
            for (int i = 0; i < numPoints; i++) {
                final int x = poly.xpoints[i];
                final int y = poly.ypoints[i];
                final Shape shape = ShapeUtilities.createTranslatedShape(
                        getItemShape(seriesIndex, i), x,  y);

                g2.setPaint(lookupSeriesFillPaint(seriesIndex));
                g2.fill(shape);
                g2.setPaint(lookupSeriesOutlinePaint(seriesIndex));
                g2.setStroke(lookupSeriesOutlineStroke(seriesIndex));
                g2.draw(shape);
            }
        }
    }

    /**
     * Draw the angular gridlines - the spokes.
     *
     * @param g2  the drawing surface.
     * @param plot  the plot.
     * @param ticks  the ticks.
     * @param dataArea  the data area.
     */
    public void drawAngularGridLines(Graphics2D g2, PolarPlot plot,
                List ticks, Rectangle2D dataArea) {

        g2.setFont(plot.getAngleLabelFont());
        g2.setStroke(plot.getAngleGridlineStroke());
        g2.setPaint(plot.getAngleGridlinePaint());

        double axisMin = plot.getAxis().getLowerBound();
        double maxRadius = plot.getAxis().getUpperBound();
        Point center = plot.translateValueThetaRadiusToJava2D(axisMin, axisMin,
                dataArea);
        Iterator iterator = ticks.iterator();
        while (iterator.hasNext()) {
            NumberTick tick = (NumberTick) iterator.next();
            double tickVal = tick.getNumber().doubleValue();
            Point p = plot.translateValueThetaRadiusToJava2D(
                    tickVal, maxRadius, dataArea);
            g2.setPaint(plot.getAngleGridlinePaint());
            g2.drawLine(center.x, center.y, p.x, p.y);
            if (plot.isAngleLabelsVisible()) {
                int x = p.x;
                int y = p.y;
                g2.setPaint(plot.getAngleLabelPaint());
                TextUtilities.drawAlignedString(tick.getText(), g2, x, y,
                        tick.getTextAnchor());
            }
        }
     }

    /**
     * Draw the radial gridlines - the rings.
     *
     * @param g2  the drawing surface.
     * @param plot  the plot.
     * @param radialAxis  the radial axis.
     * @param ticks  the ticks.
     * @param dataArea  the data area.
     */
    public void drawRadialGridLines(Graphics2D g2,
                                    PolarPlot plot,
                                    ValueAxis radialAxis,
                                    List ticks,
                                    Rectangle2D dataArea) {

        g2.setFont(radialAxis.getTickLabelFont());
        g2.setPaint(plot.getRadiusGridlinePaint());
        g2.setStroke(plot.getRadiusGridlineStroke());

        double axisMin = radialAxis.getLowerBound();
        Point center = plot.translateValueThetaRadiusToJava2D(axisMin, axisMin,
                dataArea);

        Iterator iterator = ticks.iterator();
        while (iterator.hasNext()) {
            NumberTick tick = (NumberTick) iterator.next();
            Point p = plot.translateValueThetaRadiusToJava2D(90.0,
                    tick.getNumber().doubleValue(), dataArea);
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
    public LegendItem getLegendItem(int series) {
        LegendItem result = null;
        PolarPlot plot = getPlot();
        if (plot == null) {
            return null;
        }
        XYDataset dataset = plot.getDataset(plot.getIndexOf(this));
        if (dataset != null) {
            String label = dataset.getSeriesKey(series).toString();
            String description = label;
            Shape shape = lookupSeriesShape(series);
            Paint paint = lookupSeriesPaint(series);
            Paint outlinePaint = lookupSeriesOutlinePaint(series);
            Stroke outlineStroke = lookupSeriesOutlineStroke(series);
            result = new LegendItem(label, description, null, null,
                    shape, paint, outlineStroke, outlinePaint);
            result.setDataset(dataset);
        }
        return result;
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> not permitted).
     *
     * @return <code>true</code> if this renderer is equal to <code>obj</code>,
     *     and <code>false</code> otherwise.
     */
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
        if (!this.fillComposite.equals(that.fillComposite)) {
            return false;
        }
        if (this.shapesVisible != that.shapesVisible) {
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
    public Object clone() throws CloneNotSupportedException {
        DefaultPolarItemRenderer clone
                = (DefaultPolarItemRenderer) super.clone();
        clone.seriesFilled = (BooleanList) this.seriesFilled.clone();
        return clone;
    }

}