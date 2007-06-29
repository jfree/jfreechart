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
 * ---------------------------
 * StatisticalBarRenderer.java
 * ---------------------------
 * (C) Copyright 2002-2007, by Pascal Collet and Contributors.
 *
 * Original Author:  Pascal Collet;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Christian W. Zuckschwerdt;
 *
 * $Id: StatisticalBarRenderer.java,v 1.4.2.6 2007/02/02 15:52:07 mungady Exp $
 *
 * Changes
 * -------
 * 21-Aug-2002 : Version 1, contributed by Pascal Collet (DG);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 24-Oct-2002 : Changes to dataset interface (DG);
 * 05-Nov-2002 : Base dataset is now TableDataset not CategoryDataset (DG);
 * 05-Feb-2003 : Updates for new DefaultStatisticalCategoryDataset (DG);
 * 25-Mar-2003 : Implemented Serializable (DG);
 * 30-Jul-2003 : Modified entity constructor (CZ);
 * 06-Oct-2003 : Corrected typo in exception message (DG);
 * 05-Nov-2004 : Modified drawItem() signature (DG);
 * 15-Jun-2005 : Added errorIndicatorPaint attribute (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 19-May-2006 : Added support for tooltips and URLs (DG);
 * 12-Jul-2006 : Added support for item labels (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 *
 */

package org.jfree.chart.renderer.category;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Line2D;
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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.statistics.StatisticalCategoryDataset;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A renderer that handles the drawing a bar plot where
 * each bar has a mean value and a standard deviation line.
 */
public class StatisticalBarRenderer extends BarRenderer
                                    implements CategoryItemRenderer, 
                                               Cloneable, PublicCloneable, 
                                               Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4986038395414039117L;
    
    /** The paint used to show the error indicator. */
    private transient Paint errorIndicatorPaint;
    
    /**
     * Default constructor.
     */
    public StatisticalBarRenderer() {
        super();
        this.errorIndicatorPaint = Color.gray;
    }

    /**
     * Returns the paint used for the error indicators.
     * 
     * @return The paint used for the error indicators (possibly 
     *         <code>null</code>).
     */
    public Paint getErrorIndicatorPaint() {
        return this.errorIndicatorPaint;   
    }

    /**
     * Sets the paint used for the error indicators (if <code>null</code>, 
     * the item outline paint is used instead)
     * 
     * @param paint  the paint (<code>null</code> permitted).
     */
    public void setErrorIndicatorPaint(Paint paint) {
        this.errorIndicatorPaint = paint;
        notifyListeners(new RendererChangeEvent(this));
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
    public void drawItem(Graphics2D g2,
                         CategoryItemRendererState state,
                         Rectangle2D dataArea,
                         CategoryPlot plot,
                         CategoryAxis domainAxis,
                         ValueAxis rangeAxis,
                         CategoryDataset data,
                         int row,
                         int column,
                         int pass) {

        // defensive check
        if (!(data instanceof StatisticalCategoryDataset)) {
            throw new IllegalArgumentException(
                "Requires StatisticalCategoryDataset.");
        }
        StatisticalCategoryDataset statData = (StatisticalCategoryDataset) data;

        PlotOrientation orientation = plot.getOrientation();
        if (orientation == PlotOrientation.HORIZONTAL) {
            drawHorizontalItem(g2, state, dataArea, plot, domainAxis, 
                    rangeAxis, statData, row, column);
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            drawVerticalItem(g2, state, dataArea, plot, domainAxis, rangeAxis, 
                    statData, row, column);
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
                                      int row,
                                      int column) {
                                     
        RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        
        // BAR Y
        double rectY = domainAxis.getCategoryStart(column, getColumnCount(), 
                dataArea, xAxisLocation);

        int seriesCount = getRowCount();
        int categoryCount = getColumnCount();
        if (seriesCount > 1) {
            double seriesGap = dataArea.getHeight() * getItemMargin()
                               / (categoryCount * (seriesCount - 1));
            rectY = rectY + row * (state.getBarWidth() + seriesGap);
        }
        else {
            rectY = rectY + row * state.getBarWidth();
        }

        // BAR X
        Number meanValue = dataset.getMeanValue(row, column);

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
        Paint seriesPaint = getItemPaint(row, column);
        g2.setPaint(seriesPaint);
        g2.fill(bar);
        if (state.getBarWidth() > 3) {
            g2.setStroke(getItemStroke(row, column));
            g2.setPaint(getItemOutlinePaint(row, column));
            g2.draw(bar);
        }

        // standard deviation lines
        double valueDelta = dataset.getStdDevValue(row, column).doubleValue();
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
        Line2D line = null;
        line = new Line2D.Double(lowVal, rectY + rectHeight / 2.0d, 
                                 highVal, rectY + rectHeight / 2.0d);
        g2.draw(line);
        line = new Line2D.Double(highVal, rectY + rectHeight * 0.25, 
                                 highVal, rectY + rectHeight * 0.75);
        g2.draw(line);
        line = new Line2D.Double(lowVal, rectY + rectHeight * 0.25, 
                                 lowVal, rectY + rectHeight * 0.75);
        g2.draw(line);
        
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
                                    int row,
                                    int column) {
                                     
        RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        
        // BAR X
        double rectX = domainAxis.getCategoryStart(
            column, getColumnCount(), dataArea, xAxisLocation
        );

        int seriesCount = getRowCount();
        int categoryCount = getColumnCount();
        if (seriesCount > 1) {
            double seriesGap = dataArea.getWidth() * getItemMargin()
                               / (categoryCount * (seriesCount - 1));
            rectX = rectX + row * (state.getBarWidth() + seriesGap);
        }
        else {
            rectX = rectX + row * state.getBarWidth();
        }

        // BAR Y
        Number meanValue = dataset.getMeanValue(row, column);

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
        Paint seriesPaint = getItemPaint(row, column);
        g2.setPaint(seriesPaint);
        g2.fill(bar);
        if (state.getBarWidth() > 3) {
            g2.setStroke(getItemStroke(row, column));
            g2.setPaint(getItemOutlinePaint(row, column));
            g2.draw(bar);
        }

        // standard deviation lines
        double valueDelta = dataset.getStdDevValue(row, column).doubleValue();
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
        Line2D line = null;
        line = new Line2D.Double(rectX + rectWidth / 2.0d, lowVal,
                                 rectX + rectWidth / 2.0d, highVal);
        g2.draw(line);
        line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, highVal,
                                 rectX + rectWidth / 2.0d + 5.0d, highVal);
        g2.draw(line);
        line = new Line2D.Double(rectX + rectWidth / 2.0d - 5.0d, lowVal,
                                 rectX + rectWidth / 2.0d + 5.0d, lowVal);
        g2.draw(line);
        
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
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;   
        }
        if (!(obj instanceof StatisticalBarRenderer)) {
            return false;   
        }
        if (!super.equals(obj)) {
            return false;   
        }
        StatisticalBarRenderer that = (StatisticalBarRenderer) obj;
        if (!PaintUtilities.equal(this.errorIndicatorPaint, 
                that.errorIndicatorPaint)) {
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
        SerialUtilities.writePaint(this.errorIndicatorPaint, stream);
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
        this.errorIndicatorPaint = SerialUtilities.readPaint(stream);
    }

}
