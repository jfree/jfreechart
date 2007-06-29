/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * WaterfallBarRenderer.java
 * -------------------------
 * (C) Copyright 2003-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Darshan Shah;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id: WaterfallBarRenderer.java,v 1.9.2.3 2007/06/08 13:57:38 mungady Exp $
 *
 * Changes
 * -------
 * 20-Oct-2003 : Version 1, contributed by Darshan Shah (DG);
 * 06-Nov-2003 : Changed order of parameters in constructor, and added support 
 *               for GradientPaint (DG);
 * 10-Feb-2004 : Updated drawItem() method to make cut-and-paste overriding 
 *               easier.  Also fixed a bug that meant the minimum bar length 
 *               was being ignored (DG);
 * 04-Oct-2004 : Reworked equals() method and renamed PaintUtils 
 *               --> PaintUtilities (DG);
 * 05-Nov-2004 : Modified drawItem() signature (DG);
 * 07-Jan-2005 : Renamed getRangeExtent() --> findRangeBounds (DG);
 * 23-Feb-2005 : Added argument checking (DG);
 * 20-Apr-2005 : Renamed CategoryLabelGenerator 
 *               --> CategoryItemLabelGenerator (DG);
 * 09-Jun-2005 : Use addItemEntity() from superclass (DG);
 * 
 */

package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.StandardGradientPaintTransformer;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A renderer that handles the drawing of waterfall bar charts, for use with 
 * the {@link CategoryPlot} class.  Note that the bar colors are defined 
 * using special methods in this class - the inherited methods (for example,
 * {@link AbstractRenderer#setSeriesPaint(int, Paint)}) are ignored.
 */
public class WaterfallBarRenderer extends BarRenderer 
                                  implements Cloneable, PublicCloneable, 
                                             Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2482910643727230911L;
    
    /** The paint used to draw the first bar. */
    private transient Paint firstBarPaint;

    /** The paint used to draw the last bar. */
    private transient Paint lastBarPaint;

    /** The paint used to draw bars having positive values. */
    private transient Paint positiveBarPaint;

    /** The paint used to draw bars having negative values. */
    private transient Paint negativeBarPaint;

    /**
     * Constructs a new renderer with default values for the bar colors.
     */
    public WaterfallBarRenderer() {
        this(new GradientPaint(0.0f, 0.0f, new Color(0x22, 0x22, 0xFF), 
                0.0f, 0.0f, new Color(0x66, 0x66, 0xFF)), 
                new GradientPaint(0.0f, 0.0f, new Color(0x22, 0xFF, 0x22), 
                0.0f, 0.0f, new Color(0x66, 0xFF, 0x66)), 
                new GradientPaint(0.0f, 0.0f, new Color(0xFF, 0x22, 0x22), 
                0.0f, 0.0f, new Color(0xFF, 0x66, 0x66)),
                new GradientPaint(0.0f, 0.0f, new Color(0xFF, 0xFF, 0x22), 
                0.0f, 0.0f, new Color(0xFF, 0xFF, 0x66)));
    }

    /**
     * Constructs a new waterfall renderer.
     *
     * @param firstBarPaint  the color of the first bar (<code>null</code> not 
     *                       permitted).
     * @param positiveBarPaint  the color for bars with positive values 
     *                          (<code>null</code> not permitted).
     * @param negativeBarPaint  the color for bars with negative values 
     *                          (<code>null</code> not permitted).
     * @param lastBarPaint  the color of the last bar (<code>null</code> not 
     *                      permitted).
     */
    public WaterfallBarRenderer(Paint firstBarPaint, 
                                Paint positiveBarPaint, 
                                Paint negativeBarPaint,
                                Paint lastBarPaint) {
        super();
        if (firstBarPaint == null) {
            throw new IllegalArgumentException("Null 'firstBarPaint' argument");
        }
        if (positiveBarPaint == null) {
            throw new IllegalArgumentException(
                    "Null 'positiveBarPaint' argument");   
        }
        if (negativeBarPaint == null) {
            throw new IllegalArgumentException(
                    "Null 'negativeBarPaint' argument");   
        }
        if (lastBarPaint == null) {
            throw new IllegalArgumentException("Null 'lastBarPaint' argument");
        }
        this.firstBarPaint = firstBarPaint;
        this.lastBarPaint = lastBarPaint;
        this.positiveBarPaint = positiveBarPaint;
        this.negativeBarPaint = negativeBarPaint;
        setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_VERTICAL));
        setMinimumBarLength(1.0);
    }

    /**
     * Returns the range of values the renderer requires to display all the 
     * items from the specified dataset.
     * 
     * @param dataset  the dataset (<code>null</code> not permitted).
     * 
     * @return The range (or <code>null</code> if the dataset is empty).
     */
    public Range findRangeBounds(CategoryDataset dataset) {
        return DatasetUtilities.findCumulativeRangeBounds(dataset);   
    }

    /**
     * Returns the paint used to draw the first bar.
     * 
     * @return The paint (never <code>null</code>).
     */
    public Paint getFirstBarPaint() {
        return this.firstBarPaint;
    }
    
    /**
     * Sets the paint that will be used to draw the first bar and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setFirstBarPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");   
        }
        this.firstBarPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Returns the paint used to draw the last bar.
     * 
     * @return The paint (never <code>null</code>).
     */
    public Paint getLastBarPaint() {
        return this.lastBarPaint;
    }
    
    /**
     * Sets the paint that will be used to draw the last bar.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setLastBarPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");   
        }
        this.lastBarPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Returns the paint used to draw bars with positive values.
     * 
     * @return The paint (never <code>null</code>).
     */
    public Paint getPositiveBarPaint() {
        return this.positiveBarPaint;
    }
    
    /**
     * Sets the paint that will be used to draw bars having positive values.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setPositiveBarPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");   
        }
        this.positiveBarPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Returns the paint used to draw bars with negative values.
     * 
     * @return The paint (never <code>null</code>).
     */
    public Paint getNegativeBarPaint() {
        return this.negativeBarPaint;
    }
    
    /**
     * Sets the paint that will be used to draw bars having negative values.
     *
     * @param paint  the paint (<code>null</code> not permitted).
     */
    public void setNegativeBarPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument");   
        }
        this.negativeBarPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
    }

    /**
     * Draws the bar for a single (series, category) data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the data area.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param pass  the pass index.
     */
    public void drawItem(Graphics2D g2,
                         CategoryItemRendererState state,
                         Rectangle2D dataArea,
                         CategoryPlot plot,
                         CategoryAxis domainAxis,
                         ValueAxis rangeAxis,
                         CategoryDataset dataset,
                         int row,
                         int column,
                         int pass) {

        double previous = state.getSeriesRunningTotal();
        if (column == dataset.getColumnCount() - 1) {
            previous = 0.0;
        }
        double current = 0.0;
        Number n = dataset.getValue(row, column);
        if (n != null) {
            current = previous + n.doubleValue();
        }
        state.setSeriesRunningTotal(current);
        
        int seriesCount = getRowCount();
        int categoryCount = getColumnCount();
        PlotOrientation orientation = plot.getOrientation();
        
        double rectX = 0.0;
        double rectY = 0.0;

        RectangleEdge domainAxisLocation = plot.getDomainAxisEdge();
        RectangleEdge rangeAxisLocation = plot.getRangeAxisEdge();
        
        // Y0
        double j2dy0 = rangeAxis.valueToJava2D(previous, dataArea, 
                rangeAxisLocation);

        // Y1
        double j2dy1 = rangeAxis.valueToJava2D(current, dataArea, 
                rangeAxisLocation);

        double valDiff = current - previous;
        if (j2dy1 < j2dy0) {
            double temp = j2dy1;
            j2dy1 = j2dy0;
            j2dy0 = temp;
        }

        // BAR WIDTH
        double rectWidth = state.getBarWidth();

        // BAR HEIGHT
        double rectHeight = Math.max(getMinimumBarLength(), 
                Math.abs(j2dy1 - j2dy0));

        if (orientation == PlotOrientation.HORIZONTAL) {
            // BAR Y
            rectY = domainAxis.getCategoryStart(column, getColumnCount(), 
                    dataArea, domainAxisLocation);
            if (seriesCount > 1) {
                double seriesGap = dataArea.getHeight() * getItemMargin()
                                   / (categoryCount * (seriesCount - 1));
                rectY = rectY + row * (state.getBarWidth() + seriesGap);
            }
            else {
                rectY = rectY + row * state.getBarWidth();
            }
             
            rectX = j2dy0;
            rectHeight = state.getBarWidth();
            rectWidth = Math.max(getMinimumBarLength(), 
                    Math.abs(j2dy1 - j2dy0));

        }
        else if (orientation == PlotOrientation.VERTICAL) {
            // BAR X
            rectX = domainAxis.getCategoryStart(column, getColumnCount(), 
                    dataArea, domainAxisLocation);

            if (seriesCount > 1) {
                double seriesGap = dataArea.getWidth() * getItemMargin()
                                   / (categoryCount * (seriesCount - 1));
                rectX = rectX + row * (state.getBarWidth() + seriesGap);
            }
            else {
                rectX = rectX + row * state.getBarWidth();
            }

            rectY = j2dy0;
        }
        Rectangle2D bar = new Rectangle2D.Double(rectX, rectY, rectWidth, 
                rectHeight);
        Paint seriesPaint = getFirstBarPaint();
        if (column == 0) {
            seriesPaint = getFirstBarPaint();
        }
        else if (column == categoryCount - 1) {
            seriesPaint = getLastBarPaint();    
        } 
        else {
            if (valDiff < 0.0) {
                seriesPaint = getNegativeBarPaint();
            } 
            else if (valDiff > 0.0) {
                seriesPaint = getPositiveBarPaint();
            } 
            else {
                seriesPaint = getLastBarPaint();
            }
        }
        if (getGradientPaintTransformer() != null 
                && seriesPaint instanceof GradientPaint) {
            GradientPaint gp = (GradientPaint) seriesPaint;
            seriesPaint = getGradientPaintTransformer().transform(gp, bar);
        }
        g2.setPaint(seriesPaint);
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
        
        CategoryItemLabelGenerator generator 
            = getItemLabelGenerator(row, column);
        if (generator != null && isItemLabelVisible(row, column)) {
            drawItemLabel(g2, dataset, row, column, plot, generator, bar, 
                    (valDiff < 0.0));
        }        

        // add an item entity, if this information is being collected
        EntityCollection entities = state.getEntityCollection();
        if (entities != null) {
            addItemEntity(entities, dataset, row, column, bar);
        }

    }
    
    /**
     * Tests an object for equality with this instance.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        
        if (obj == this) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof WaterfallBarRenderer)) {
            return false;
        }
        WaterfallBarRenderer that = (WaterfallBarRenderer) obj;
        if (!PaintUtilities.equal(this.firstBarPaint, that.firstBarPaint)) {
            return false;
        }
        if (!PaintUtilities.equal(this.lastBarPaint, that.lastBarPaint)) {
            return false;
        }             
        if (!PaintUtilities.equal(this.positiveBarPaint, 
                that.positiveBarPaint)) {
            return false;
        }             
        if (!PaintUtilities.equal(this.negativeBarPaint, 
                that.negativeBarPaint)) {
            return false;
        }             
        return true;
        
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
        SerialUtilities.writePaint(this.firstBarPaint, stream);
        SerialUtilities.writePaint(this.lastBarPaint, stream);
        SerialUtilities.writePaint(this.positiveBarPaint, stream);
        SerialUtilities.writePaint(this.negativeBarPaint, stream);
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
        this.firstBarPaint = SerialUtilities.readPaint(stream);
        this.lastBarPaint = SerialUtilities.readPaint(stream);
        this.positiveBarPaint = SerialUtilities.readPaint(stream);
        this.negativeBarPaint = SerialUtilities.readPaint(stream);
    }

}
