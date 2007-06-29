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
 * ---------------------
 * PaintScaleLegend.java
 * ---------------------
 * (C) Copyright 2007, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PaintScaleLegend.java,v 1.1.2.1 2007/01/31 14:15:16 mungady Exp $
 *
 * Changes
 * -------
 * 22-Jan-2007 : Version 1 (DG);
 * 
 */

package org.jfree.chart.title;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.PaintScale;
import org.jfree.data.Range;
import org.jfree.io.SerialUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.Size2D;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A legend that shows a range of values and their associated colors, driven
 * by an underlying {@link PaintScale} implementation.
 * 
 * @since 1.0.4
 */
public class PaintScaleLegend extends Title implements PublicCloneable {

    /** The paint scale (never <code>null</code>). */
    private PaintScale scale;
    
    /** The value axis (never <code>null</code>). */
    private ValueAxis axis;
    
    /** 
     * The axis location (handles both orientations, never 
     * <code>null</code>). 
     */
    private AxisLocation axisLocation;

    /** The offset between the axis and the paint strip (in Java2D units). */
    private double axisOffset;
    
    /** The thickness of the paint strip (in Java2D units). */
    private double stripWidth;
   
    /** 
     * A flag that controls whether or not an outline is drawn around the
     * paint strip.
     */
    private boolean stripOutlineVisible;
    
    /** The paint used to draw an outline around the paint strip. */
    private transient Paint stripOutlinePaint;
    
    /** The stroke used to draw an outline around the paint strip. */
    private transient Stroke stripOutlineStroke;
    
    /** The background paint (never <code>null</code>). */
    private transient Paint backgroundPaint;
    
    /**
     * Creates a new instance.
     * 
     * @param scale  the scale (<code>null</code> not permitted).
     * @param axis  the axis (<code>null</code> not permitted).
     */
    public PaintScaleLegend(PaintScale scale, ValueAxis axis) {
        if (axis == null) {
            throw new IllegalArgumentException("Null 'axis' argument.");
        }
        this.scale = scale;
        this.axis = axis;
        this.axisLocation = AxisLocation.BOTTOM_OR_LEFT;
        this.axisOffset = 0.0;
        this.stripWidth = 15.0;
        this.stripOutlineVisible = false;
        this.stripOutlinePaint = Color.gray;
        this.stripOutlineStroke = new BasicStroke(0.5f);
        this.backgroundPaint = Color.white;
    }
    
    /**
     * Returns the scale used to convert values to colors.
     * 
     * @return The scale (never <code>null</code>).
     * 
     * @see #setScale(PaintScale)
     */
    public PaintScale getScale() {
        return this.scale;    
    }
    
    /**
     * Sets the scale and sends a {@link TitleChangeEvent} to all registered
     * listeners.
     * 
     * @param scale  the scale (<code>null</code> not permitted).
     * 
     * @see #getScale()
     */
    public void setScale(PaintScale scale) {
        if (scale == null) {
            throw new IllegalArgumentException("Null 'scale' argument.");
        }
        this.scale = scale;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the axis for the paint scale.
     * 
     * @return The axis (never <code>null</code>).
     * 
     * @see #setAxis(ValueAxis)
     */
    public ValueAxis getAxis() {
        return this.axis;
    }
    
    /**
     * Sets the axis for the paint scale and sends a {@link TitleChangeEvent}
     * to all registered listeners.
     * 
     * @param axis  the axis (<code>null</code> not permitted).
     * 
     * @see #getAxis()
     */
    public void setAxis(ValueAxis axis) {
        if (axis == null) {
            throw new IllegalArgumentException("Null 'axis' argument.");
        }
        this.axis = axis;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the axis location.
     * 
     * @return The axis location (never <code>null</code>).
     * 
     * @see #setAxisLocation(AxisLocation)
     */
    public AxisLocation getAxisLocation() {
        return this.axisLocation;
    }
    
    /**
     * Sets the axis location and sends a {@link TitleChangeEvent} to all 
     * registered listeners.
     * 
     * @param location  the location (<code>null</code> not permitted).
     * 
     * @see #getAxisLocation()
     */
    public void setAxisLocation(AxisLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Null 'location' argument.");
        }
        this.axisLocation = location;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the offset between the axis and the paint strip.
     * 
     * @return The offset between the axis and the paint strip.
     * 
     * @see #setAxisOffset(double)
     */
    public double getAxisOffset() {
        return this.axisOffset;
    }
    
    /**
     * Sets the offset between the axis and the paint strip and sends a 
     * {@link TitleChangeEvent} to all registered listeners.
     * 
     * @param offset  the offset.
     */
    public void setAxisOffset(double offset) {
        this.axisOffset = offset;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the width of the paint strip, in Java2D units.
     * 
     * @return The width of the paint strip.
     * 
     * @see #setStripWidth(double)
     */
    public double getStripWidth() {
        return this.stripWidth;
    }
    
    /**
     * Sets the width of the paint strip and sends a {@link TitleChangeEvent}
     * to all registered listeners.
     * 
     * @param width  the width.
     * 
     * @see #getStripWidth()
     */
    public void setStripWidth(double width) {
        this.stripWidth = width;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the flag that controls whether or not an outline is drawn 
     * around the paint strip.
     * 
     * @return A boolean.
     * 
     * @see #setStripOutlineVisible(boolean)
     */
    public boolean isStripOutlineVisible() {
        return this.stripOutlineVisible;
    }
    
    /**
     * Sets the flag that controls whether or not an outline is drawn around
     * the paint strip, and sends a {@link TitleChangeEvent} to all registered
     * listeners.
     * 
     * @param visible  the flag.
     * 
     * @see #isStripOutlineVisible()
     */
    public void setStripOutlineVisible(boolean visible) {
        this.stripOutlineVisible = visible;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the paint used to draw the outline of the paint strip.
     * 
     * @return The paint (never <code>null</code>).
     * 
     * @see #setStripOutlinePaint(Paint)
     */
    public Paint getStripOutlinePaint() {
        return this.stripOutlinePaint;
    }
    
    /**
     * Sets the paint used to draw the outline of the paint strip, and sends
     * a {@link TitleChangeEvent} to all registered listeners.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getStripOutlinePaint()
     */
    public void setStripOutlinePaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.stripOutlinePaint = paint;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the stroke used to draw the outline around the paint strip.
     * 
     * @return The stroke (never <code>null</code>).
     * 
     * @see #setStripOutlineStroke(Stroke)
     */
    public Stroke getStripOutlineStroke() {
        return this.stripOutlineStroke;
    }
    
    /**
     * Sets the stroke used to draw the outline around the paint strip and 
     * sends a {@link TitleChangeEvent} to all registered listeners.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     * 
     * @see #getStripOutlineStroke()
     */
    public void setStripOutlineStroke(Stroke stroke) {
        if (stroke == null) {
            throw new IllegalArgumentException("Null 'stroke' argument.");
        }
        this.stripOutlineStroke = stroke;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Returns the background paint.
     * 
     * @return The background paint.
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }
    
    /**
     * Sets the background paint and sends a {@link TitleChangeEvent} to all
     * registered listeners.
     * 
     * @param paint  the paint (<code>null</code> permitted).
     */
    public void setBackgroundPaint(Paint paint) {
        this.backgroundPaint = paint;
        notifyListeners(new TitleChangeEvent(this));
    }
    
    /**
     * Arranges the contents of the block, within the given constraints, and 
     * returns the block size.
     * 
     * @param g2  the graphics device.
     * @param constraint  the constraint (<code>null</code> not permitted).
     * 
     * @return The block size (in Java2D units, never <code>null</code>).
     */
    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        RectangleConstraint cc = toContentConstraint(constraint);
        LengthConstraintType w = cc.getWidthConstraintType();
        LengthConstraintType h = cc.getHeightConstraintType();
        Size2D contentSize = null;
        if (w == LengthConstraintType.NONE) {
            if (h == LengthConstraintType.NONE) {
                contentSize = new Size2D(getWidth(), getHeight()); 
            }
            else if (h == LengthConstraintType.RANGE) {
                throw new RuntimeException("Not yet implemented."); 
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");                 
            }            
        }
        else if (w == LengthConstraintType.RANGE) {
            if (h == LengthConstraintType.NONE) {
                throw new RuntimeException("Not yet implemented."); 
            }
            else if (h == LengthConstraintType.RANGE) {
                contentSize = arrangeRR(g2, cc.getWidthRange(), 
                        cc.getHeightRange()); 
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");                 
            }
        }
        else if (w == LengthConstraintType.FIXED) {
            if (h == LengthConstraintType.NONE) {
                throw new RuntimeException("Not yet implemented."); 
            }
            else if (h == LengthConstraintType.RANGE) {
                throw new RuntimeException("Not yet implemented."); 
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");                 
            }
        }
        return new Size2D(calculateTotalWidth(contentSize.getWidth()),
                calculateTotalHeight(contentSize.getHeight()));
    }
    
    /**
     * Returns the content size for the title.  This will reflect the fact that
     * a text title positioned on the left or right of a chart will be rotated
     * 90 degrees.
     * 
     * @param g2  the graphics device.
     * @param widthRange  the width range.
     * @param heightRange  the height range.
     * 
     * @return The content size.
     */
    protected Size2D arrangeRR(Graphics2D g2, Range widthRange, 
            Range heightRange) {
        
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
            
            
            float maxWidth = (float) widthRange.getUpperBound();
            
            // determine the space required for the axis
            AxisSpace space = this.axis.reserveSpace(g2, null, 
                    new Rectangle2D.Double(0, 0, maxWidth, 100), 
                    RectangleEdge.BOTTOM, null);
            
            return new Size2D(maxWidth, this.stripWidth + this.axisOffset 
                    + space.getTop() + space.getBottom());
        }
        else if (position == RectangleEdge.LEFT || position 
                == RectangleEdge.RIGHT) {
            float maxHeight = (float) heightRange.getUpperBound();
            AxisSpace space = this.axis.reserveSpace(g2, null, 
                    new Rectangle2D.Double(0, 0, 100, maxHeight), 
                    RectangleEdge.RIGHT, null);
            return new Size2D(this.stripWidth + this.axisOffset 
                    + space.getLeft() + space.getRight(), maxHeight);
        }
        else {
            throw new RuntimeException("Unrecognised position.");
        }
    }

    /**
     * Draws the legend within the specified area.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the drawing area (<code>null</code> not permitted).
     */
    public void draw(Graphics2D g2, Rectangle2D area) {
        draw(g2, area, null);
    }

    /** 
     * The number of subdivisions to use when drawing the paint strip.  Maybe
     * this need to be user controllable? 
     */
    private static final int SUBDIVISIONS = 200;
    
    /**
     * Draws the legend within the specified area.
     * 
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param area  the drawing area (<code>null</code> not permitted).
     * @param params  drawing parameters (ignored here).
     * 
     * @return <code>null</code>.
     */
    public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
        
        Rectangle2D target = (Rectangle2D) area.clone();
        target = trimMargin(target);
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(target);
        }
        getBorder().draw(g2, target);
        getBorder().getInsets().trim(target);
        target = trimPadding(target);
        double base = this.axis.getLowerBound();
        double increment = this.axis.getRange().getLength() / SUBDIVISIONS;
        Rectangle2D r = new Rectangle2D.Double();
        
        
        if (RectangleEdge.isTopOrBottom(getPosition())) {
            RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(
                    this.axisLocation, PlotOrientation.HORIZONTAL);
            double ww = Math.ceil(target.getWidth() / SUBDIVISIONS);
            if (axisEdge == RectangleEdge.TOP) {
                for (int i = 0; i < SUBDIVISIONS; i++) {
                    double v = base + (i * increment);
                    Paint p = this.scale.getPaint(v);
                    double vv = this.axis.valueToJava2D(v, target, 
                            RectangleEdge.BOTTOM);
                    r.setRect(vv, target.getMaxY() - this.stripWidth, ww, 
                            this.stripWidth);
                    g2.setPaint(p);
                    g2.fill(r);                  
                }
                g2.setPaint(this.stripOutlinePaint);
                g2.setStroke(this.stripOutlineStroke);
                g2.draw(new Rectangle2D.Double(target.getMinX(), 
                        target.getMaxY() - this.stripWidth, target.getWidth(), 
                        this.stripWidth));
                this.axis.draw(g2, target.getMaxY() - this.stripWidth 
                        - this.axisOffset, target, target, RectangleEdge.TOP, 
                        null);                
            }
            else if (axisEdge == RectangleEdge.BOTTOM) {
                for (int i = 0; i < SUBDIVISIONS; i++) {
                    double v = base + (i * increment);
                    Paint p = this.scale.getPaint(v);
                    double vv = this.axis.valueToJava2D(v, target, 
                            RectangleEdge.BOTTOM);
                    r.setRect(vv, target.getMinY(), ww, this.stripWidth);
                    g2.setPaint(p);
                    g2.fill(r);
                }
                g2.setPaint(this.stripOutlinePaint);
                g2.setStroke(this.stripOutlineStroke);
                g2.draw(new Rectangle2D.Double(target.getMinX(), 
                        target.getMinY(), target.getWidth(), this.stripWidth));
                this.axis.draw(g2, target.getMinY() + this.stripWidth 
                        + this.axisOffset, target, target, 
                        RectangleEdge.BOTTOM, null);                
            }
        }
        else {
            RectangleEdge axisEdge = Plot.resolveRangeAxisLocation(
                    this.axisLocation, PlotOrientation.VERTICAL);
            double hh = Math.ceil(target.getHeight() / SUBDIVISIONS);
            if (axisEdge == RectangleEdge.LEFT) {
                for (int i = 0; i < SUBDIVISIONS; i++) {
                    double v = base + (i * increment);
                    Paint p = this.scale.getPaint(v);
                    double vv = this.axis.valueToJava2D(v, target, 
                            RectangleEdge.LEFT);
                    r.setRect(target.getMaxX() - this.stripWidth, vv - hh, 
                            this.stripWidth, hh);
                    g2.setPaint(p);
                    g2.fill(r);
                }
                g2.setPaint(this.stripOutlinePaint);
                g2.setStroke(this.stripOutlineStroke);
                g2.draw(new Rectangle2D.Double(target.getMaxX() 
                        - this.stripWidth, target.getMinY(), this.stripWidth, 
                        target.getHeight()));
                this.axis.draw(g2, target.getMaxX() - this.stripWidth 
                        - this.axisOffset, target, target, RectangleEdge.LEFT, 
                        null);
            }
            else if (axisEdge == RectangleEdge.RIGHT) {
                for (int i = 0; i < SUBDIVISIONS; i++) {
                    double v = base + (i * increment);
                    Paint p = this.scale.getPaint(v);
                    double vv = this.axis.valueToJava2D(v, target, 
                            RectangleEdge.LEFT);
                    r.setRect(target.getMinX(), vv - hh, this.stripWidth, hh);
                    g2.setPaint(p);
                    g2.fill(r);
                }
                g2.setPaint(this.stripOutlinePaint);
                g2.setStroke(this.stripOutlineStroke);
                g2.draw(new Rectangle2D.Double(target.getMinX(), 
                        target.getMinY(), this.stripWidth, target.getHeight()));
                this.axis.draw(g2, target.getMinX() + this.stripWidth 
                        + this.axisOffset, target, target, RectangleEdge.RIGHT,
                        null);                
            }
        }
        return null;
    }
    
    /**
     * Tests this legend for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof PaintScaleLegend)) {
            return false;
        }
        PaintScaleLegend that = (PaintScaleLegend) obj;
        if (!this.scale.equals(that.scale)) {
            return false;
        }
        if (!this.axis.equals(that.axis)) {
            return false;
        }
        if (!this.axisLocation.equals(that.axisLocation)) {
            return false;
        }
        if (this.axisOffset != that.axisOffset) {
            return false;
        }
        if (this.stripWidth != that.stripWidth) {
            return false;
        }
        if (this.stripOutlineVisible != that.stripOutlineVisible) {
            return false;
        }
        if (!PaintUtilities.equal(this.stripOutlinePaint, 
                that.stripOutlinePaint)) {
            return false;
        }
        if (!this.stripOutlineStroke.equals(that.stripOutlineStroke)) {
            return false;
        }
        if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
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
        SerialUtilities.writePaint(this.backgroundPaint, stream);
        SerialUtilities.writePaint(this.stripOutlinePaint, stream);
        SerialUtilities.writeStroke(this.stripOutlineStroke, stream);
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
        this.backgroundPaint = SerialUtilities.readPaint(stream);
        this.stripOutlinePaint = SerialUtilities.readPaint(stream);
        this.stripOutlineStroke = SerialUtilities.readStroke(stream);
    }

}
