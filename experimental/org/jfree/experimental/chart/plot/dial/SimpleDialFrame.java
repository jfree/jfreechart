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
 * --------------------
 * SimpleDialFrame.java
 * --------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SimpleDialFrame.java,v 1.1.2.3 2007/03/08 16:51:07 mungady Exp $
 *
 * Changes
 * -------
 * 03-Nov-2006 : Version 1 (DG);
 * 08-Mar-2007 : Fix in hashCode() (DG);
 * 
 */

package org.jfree.experimental.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.HashUtilities;
import org.jfree.io.SerialUtilities;
import org.jfree.util.PaintUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A simple circular frame for the {@link DialPlot} class.
 */
public class SimpleDialFrame extends AbstractDialLayer implements DialFrame, 
        Cloneable, PublicCloneable, Serializable {
    
    /** The outer radius, relative to the framing rectangle. */
    private double radius;
    
    /**
     * The color used for the front of the panel.  This field is transient
     * because it requires special handling for serialization.
     */
    private transient Paint backgroundPaint;
    
    /**
     * The color used for the border around the window. This field is transient
     * because it requires special handling for serialization.
     */
    private transient Paint foregroundPaint;
    
    /**
     * The stroke for drawing the frame outline.  This field is transient
     * because it requires special handling for serialization.
     */
    private transient Stroke stroke;
    
    /**
     * Creates a new instance of <code>SimpleDialFrame</code>.
     */
    public SimpleDialFrame() {
        this.backgroundPaint = Color.gray;
        this.foregroundPaint = Color.black;
        this.stroke = new BasicStroke(2.0f);
        this.radius = 0.95;   
    }
    
    /**
     * Returns the radius, relative to the framing rectangle.
     *
     * @return The radius. 
     */
    public double getRadius() {
        return this.radius;
    }
    
    /**
     * Sets the radius.
     *
     * @param radius  the radius.
     */
    public void setRadius(double radius) {
        // TODO: validation
        this.radius = radius;
        notifyListeners(new DialLayerChangeEvent(this));
    }

    /**
     * Returns the background paint.
     * 
     * @return The background paint (never <code>null</code>).
     * 
     * @see #setBackgroundPaint(Paint)
     */
    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }
    
    /**
     * Sets the background paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getBackgroundPaint()
     */
    public void setBackgroundPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.backgroundPaint = paint;
        notifyListeners(new DialLayerChangeEvent(this));
    }
    
    /**
     * Returns the foreground paint.
     * 
     * @return The foreground paint (never <code>null</code>).
     * 
     * @see #setForegroundPaint(Paint)
     */
    public Paint getForegroundPaint() {
        return this.foregroundPaint;
    }
    
    /**
     * Sets the foreground paint.
     * 
     * @param paint  the paint (<code>null</code> not permitted).
     * 
     * @see #getForegroundPaint()
     */
    public void setForegroundPaint(Paint paint) {
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.foregroundPaint = paint;
        notifyListeners(new DialLayerChangeEvent(this));
    }
    
    /**
     * Returns the stroke for the frame.
     * 
     * @return The stroke (never <code>null</code>).
     * 
     * @see #setStroke(Stroke)
     */
    public Stroke getStroke() {
        return this.stroke;
    }
    
    /**
     * Sets the stroke.
     * 
     * @param stroke  the stroke (<code>null</code> not permitted).
     * 
     * @see #getStroke()
     */
    public void setStroke(Stroke stroke) {
        if (stroke == null) { 
            throw new IllegalArgumentException("Null 'stroke' argument.");
        }
        this.stroke = stroke;
        notifyListeners(new DialLayerChangeEvent(this));
    }
        
    /**
     * Returns the shape for the window for this dial.  Some dial layers will
     * request that their drawing be clipped within this window.
     *
     * @param frame  the reference frame (<code>null</code> not permitted).
     *
     * @return The shape of the dial's window.
     */
    public Shape getWindow(Rectangle2D frame) { 
        Rectangle2D f = DialPlot.rectangleByRadius(frame, this.radius, 
                this.radius);
        return new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), 
                f.getHeight());  
    }
     
    /**
     * Returns <code>false</code> to indicate that this dial layer is not
     * clipped to the dial window.
     *
     * @return A boolean.
     */
    public boolean isClippedToWindow() {
        return false;
    }
    
    /**
     * Draws the frame.  This method is called by the {@link DialPlot} class,
     * you shouldn't need to call it directly.
     *
     * @param g2  the graphics target (<code>null</code> not permitted).
     * @param plot  the plot (<code>null</code> not permitted).
     * @param frame  the frame (<code>null</code> not permitted).
     * @param view  the view (<code>null</code> not permitted).
     */
    public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, 
            Rectangle2D view) {
        
        Shape window = getWindow(frame);
        
        Rectangle2D f = DialPlot.rectangleByRadius(frame, this.radius + 0.02, 
                this.radius + 0.02);
        Ellipse2D e = new Ellipse2D.Double(f.getX(), f.getY(), f.getWidth(), 
                f.getHeight());
        
        Area area = new Area(e);
        Area area2 = new Area(window);
        area.subtract(area2);
        g2.setPaint(this.backgroundPaint);
        g2.fill(area);
        
        g2.setStroke(this.stroke);
        g2.setPaint(this.foregroundPaint);
        g2.draw(window);
        g2.draw(e);
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SimpleDialFrame)) {
            return false;
        }
        SimpleDialFrame that = (SimpleDialFrame) obj;
        if (!PaintUtilities.equal(this.backgroundPaint, that.backgroundPaint)) {
            return false;
        }
        if (!PaintUtilities.equal(this.foregroundPaint, that.foregroundPaint)) {
            return false;
        }
        if (this.radius != that.radius) {
            return false;
        }
        if (!this.stroke.equals(that.stroke)) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns a hash code for this instance.
     * 
     * @return The hash code.
     */
    public int hashCode() {
        int result = 193;
        long temp = Double.doubleToLongBits(this.radius);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        result = 37 * result + HashUtilities.hashCodeForPaint(
                this.backgroundPaint);
        result = 37 * result + HashUtilities.hashCodeForPaint(
                this.foregroundPaint);
        result = 37 * result + this.stroke.hashCode();
        return result;
    }
    
    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     * 
     * @throws CloneNotSupportedException if any of the frame's attributes 
     *     cannot be cloned.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        SerialUtilities.writePaint(this.foregroundPaint, stream);
        SerialUtilities.writeStroke(this.stroke, stream);
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
        this.foregroundPaint = SerialUtilities.readPaint(stream);
        this.stroke = SerialUtilities.readStroke(stream);
    }
    
}
