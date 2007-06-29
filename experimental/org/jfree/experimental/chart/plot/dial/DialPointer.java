/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * ----------------
 * DialPointer.java
 * ----------------
 * (C) Copyright 2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DialPointer.java,v 1.1.2.4 2007/04/30 21:38:31 mungady Exp $
 *
 * Changes
 * -------
 * 03-Nov-2006 : Version 1 (DG);
 * 
 */

package org.jfree.experimental.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.util.PublicCloneable;

/**
 * A base class for the pointer in a {@link DialPlot}.
 */
public abstract class DialPointer extends AbstractDialLayer 
        implements DialLayer, Cloneable, Serializable {
    
    /** The needle radius. */
    double radius;
    
    /**
     * The dataset index for the needle.
     */
    int datasetIndex;
    
    /** 
     * Creates a new <code>DialPointer</code> instance.
     */
    public DialPointer() {
        this(0);
    }
    
    /**
     * Creates a new pointer for the specified dataset.
     * 
     * @param datasetIndex  the dataset index.
     */
    public DialPointer(int datasetIndex) {
        this.radius = 0.675;
        this.datasetIndex = datasetIndex;
    }
    
    /**
     * Returns the dataset index that the pointer maps to.
     * 
     * @return The dataset index.
     */
    public int getDatasetIndex() {
        return this.datasetIndex;
    }
    
    /**
     * Sets the dataset index for the pointer.
     * 
     * @param index  the index.
     */
    public void setDatasetIndex(int index) {
        this.datasetIndex = index;
        notifyListeners(new DialLayerChangeEvent(this));
    }
    
    /**
     * Returns the radius of the pointer.
     * 
     * @return The radius.
     * 
     * @see #setRadius(double)
     */
    public double getRadius() {
        return this.radius;
    }
    
    /**
     * Sets the radius of the pointer.
     * 
     * @param radius  the radius.
     * 
     * @see #getRadius()
     */
    public void setRadius(double radius) {
        this.radius = radius;
        notifyListeners(new DialLayerChangeEvent(this));
    }
    
    /**
     * Returns <code>true</code> to indicate that this layer should be 
     * clipped within the dial window.
     * 
     * @return <code>true</code>.
     */
    public boolean isClippedToWindow() {
        return true;
    }
    
    /**
     * Returns a clone of the pointer.
     * 
     * @return a clone.
     * 
     * @throws CloneNotSupportedException if one of the attributes cannot
     *     be cloned.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * A dial pointer that draws a thin line (like a pin).
     */
    public static class Pin extends DialPointer implements PublicCloneable {
    
        /** The paint. */
        private transient Paint paint;
    
        /** The stroke. */
        private transient Stroke stroke;
        
        /**
         * Creates a new instance.
         */
        public Pin() {
            this(0);
        }
        
        /**
         * Creates a new instance.
         * 
         * @param datasetIndex  the dataset index.
         */
        public Pin(int datasetIndex) {
            super(datasetIndex);
            this.paint = Color.red;
            this.stroke = new BasicStroke(3.0f, BasicStroke.CAP_ROUND, 
                    BasicStroke.JOIN_BEVEL);
        }
        
        /**
         * Returns the paint.
         * 
         * @return The paint.
         */
        public Paint getPaint() {
            return this.paint;
        }
        
        /**
         * Sets the paint.
         * 
         * @param paint  the paint (<code>null</code> not permitted).
         */
        public void setPaint(Paint paint) {
            this.paint = paint;
            notifyListeners(new DialLayerChangeEvent(this));
        }
        
        /**
         * Returns the stroke.
         * 
         * @return The stroke.
         */
        public Stroke getStroke() {
            return this.stroke;
        }
        
        /**
         * Sets the stroke.
         * 
         * @param stroke  the stroke (<code>null</code> not permitted).
         */
        public void setStroke(Stroke stroke) {
            this.stroke = stroke;
            notifyListeners(new DialLayerChangeEvent(this));
        }
        
        /**
         * Draws the pointer.
         * 
         * @param g2  the graphics target.
         * @param plot  the plot.
         * @param frame  the dial's reference frame.
         * @param view  the dial's view.
         */
        public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, 
            Rectangle2D view) {
        
            g2.setPaint(this.paint);
            g2.setStroke(this.stroke);
            Rectangle2D arcRect = DialPlot.rectangleByRadius(frame, 
                    this.radius, this.radius);

            double value = plot.getValue(this.datasetIndex);
            DialScale scale = plot.getScaleForDataset(this.datasetIndex);
            double angle = scale.valueToAngle(value);
        
            Arc2D arc = new Arc2D.Double(arcRect, angle, 0, Arc2D.OPEN);
            Point2D pt = arc.getEndPoint();
        
            Line2D line = new Line2D.Double(frame.getCenterX(), 
                    frame.getCenterY(), pt.getX(), pt.getY());
            g2.draw(line);
        }
        
    }
    
    /**
     * A dial pointer.
     */
    public static class Pointer extends DialPointer implements PublicCloneable {
        
        /**
         * The radius that defines the width of the pointer at the base.
         */
        private double widthRadius;
    
        /**
         * Creates a new instance.
         */
        public Pointer() {
            this(0);
        }
        
        /**
         * Creates a new instance.
         * 
         * @param datasetIndex  the dataset index.
         */
        public Pointer(int datasetIndex) {
            super(datasetIndex);
            this.radius = 0.9;
            this.widthRadius = 0.05;
        }
        
        /**
         * Returns the width radius.
         * 
         * @return The width radius.
         */
        public double getWidthRadius() {
            return this.widthRadius;
        }
        
        /**
         * Sets the width radius.
         * 
         * @param radius  the radius.
         */
        public void setWidthRadius(double radius) {
            this.widthRadius = radius;
            notifyListeners(new DialLayerChangeEvent(this));
        }
        
        /**
         * Draws the pointer.
         * 
         * @param g2  the graphics target.
         * @param plot  the plot.
         * @param frame  the dial's reference frame.
         * @param view  the dial's view.
         */
        public void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame, 
                Rectangle2D view) {
        
            g2.setPaint(Color.blue);
            g2.setStroke(new BasicStroke(1.0f));
            Rectangle2D lengthRect = DialPlot.rectangleByRadius(frame, 
                    this.radius, this.radius);
            Rectangle2D widthRect = DialPlot.rectangleByRadius(frame, 
                    this.widthRadius, this.widthRadius);
            double value = plot.getValue(this.datasetIndex);
            DialScale scale = plot.getScaleForDataset(this.datasetIndex);
            double angle = scale.valueToAngle(value);
        
            Arc2D arc1 = new Arc2D.Double(lengthRect, angle, 0, Arc2D.OPEN);
            Point2D pt1 = arc1.getEndPoint();
            Arc2D arc2 = new Arc2D.Double(widthRect, angle - 90.0, 180.0, 
                    Arc2D.OPEN);
            Point2D pt2 = arc2.getStartPoint();
            Point2D pt3 = arc2.getEndPoint();
            Arc2D arc3 = new Arc2D.Double(widthRect, angle - 180.0, 0.0, 
                    Arc2D.OPEN);
            Point2D pt4 = arc3.getStartPoint();
        
            GeneralPath gp = new GeneralPath();
            gp.moveTo((float) pt1.getX(), (float) pt1.getY());
            gp.lineTo((float) pt2.getX(), (float) pt2.getY());
            gp.lineTo((float) pt4.getX(), (float) pt4.getY());
            gp.lineTo((float) pt3.getX(), (float) pt3.getY());
            gp.closePath();
            g2.setPaint(Color.gray);
            g2.fill(gp);
        
            g2.setPaint(Color.black);
            Line2D line = new Line2D.Double(frame.getCenterX(), 
                    frame.getCenterY(), pt1.getX(), pt1.getY());
            g2.draw(line);
        
            line.setLine(pt2, pt3);
            g2.draw(line);
        
            line.setLine(pt3, pt1);
            g2.draw(line);
        
            line.setLine(pt2, pt1);
            g2.draw(line);
        
            line.setLine(pt2, pt4);
            g2.draw(line);

            line.setLine(pt3, pt4);
            g2.draw(line);
        }
        
    }

}
