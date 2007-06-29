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
 * ---------------------------------
 * XYSmoothLineAndShapeRenderer.java
 * ---------------------------------
 * (C) Copyright 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  -;
 * Contributor(s):   -;
 *
 * $Id: XYSmoothLineAndShapeRenderer.java,v 1.1.2.1 2007/06/14 09:32:00 mungady Exp $
 *
 * Changes
 * -------
 * 14-Jun-2007 : Version 1;
 *
 */

package org.jfree.experimental.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRendererState;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

/**
 * A line and shape renderer that performs line smoothing.  See
 * http://www.jfree.org/phpBB2/viewtopic.php?t=20671
 * 
 * WARNING: THIS CLASS IS NOT PART OF THE STANDARD JFREECHART API AND IS 
 * SUBJECT TO ALTERATION OR REMOVAL.  DO NOT RELY ON THIS CLASS FOR 
 * PRODUCTION USE.  Please experiment with this code and provide feedback.
 */
public class XYSmoothLineAndShapeRenderer extends XYLineAndShapeRenderer {
   
    protected void drawPrimaryLine(XYItemRendererState state, Graphics2D g2,
            XYPlot plot, XYDataset dataset, int pass, int series, int item,
            ValueAxis domainAxis, ValueAxis rangeAxis, Rectangle2D dataArea) {
           
        if (item == 0) {
            return;
        }
           
        // get the data point...
        double x1 = dataset.getXValue(series, item);
        double y1 = dataset.getYValue(series, item);
        if (Double.isNaN(y1) || Double.isNaN(x1)) {
            return;
        }

        double x0 = dataset.getXValue(series, item - 1);
        double y0 = dataset.getYValue(series, item - 1);
        if (Double.isNaN(y0) || Double.isNaN(x0)) {
            return;
        }

        RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

        double transX0 = domainAxis.valueToJava2D(x0, dataArea, xAxisLocation);
        double transY0 = rangeAxis.valueToJava2D(y0, dataArea, yAxisLocation);

        double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
        double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

        // only draw if we have good values
        if (Double.isNaN(transX0) || Double.isNaN(transY0)
                || Double.isNaN(transX1) || Double.isNaN(transY1)) {
            return;
        }
             
        Point2D.Double point0 = new Point2D.Double();
        Point2D.Double point1 = new Point2D.Double();
        Point2D.Double point2 = new Point2D.Double();
        Point2D.Double point3 = new Point2D.Double();
           
        if (item == 1) {
            point0 = null;
        } 
        else {
            point0.x = domainAxis.valueToJava2D(dataset.getXValue(series, 
                    item - 2), dataArea, xAxisLocation);
            point0.y = rangeAxis.valueToJava2D(dataset.getYValue(series, 
                    item - 2), dataArea, yAxisLocation);
        }
           
        point1.x = transX0;
        point1.y = transY0;
           
        point2.x = transX1;
        point2.y = transY1;
           
        if ((item + 1) == dataset.getItemCount(series)) {
            point3 = null;
        } 
        else {
            point3.x = domainAxis.valueToJava2D(dataset.getXValue(series, 
                    item + 1), dataArea, xAxisLocation);
            point3.y = rangeAxis.valueToJava2D(dataset.getYValue(series, 
                    item + 1), dataArea, yAxisLocation);
        }

        int steps = ((int) ((point2.x - point1.x) / 0.2) < 30) 
                ? (int) ((point2.x - point1.x) / 0.2) : 30;
           
        Point2D.Double[] points = getBezierCurve(point0, point1, point2, 
                point3, 1, steps);
           
        for (int i = 1; i < points.length; i++) {
            transX0 = points[i - 1].x;
            transY0 = points[i - 1].y;
            transX1 = points[i].x;
            transY1 = points[i].y;
             
            PlotOrientation orientation = plot.getOrientation();
            if (orientation == PlotOrientation.HORIZONTAL) {
                state.workingLine.setLine(transY0, transX0, transY1, transX1);
            }
            else if (orientation == PlotOrientation.VERTICAL) {
                state.workingLine.setLine(transX0, transY0, transX1, transY1);
            }
 
            if (state.workingLine.intersects(dataArea)) {
                drawFirstPassShape(g2, pass, series, item, state.workingLine);
            }
        }
    }
         
    protected void drawSecondaryPass(Graphics2D g2, XYPlot plot,
            XYDataset dataset, int pass, int series, int item,
            ValueAxis domainAxis, Rectangle2D dataArea,
            ValueAxis rangeAxis, CrosshairState crosshairState,
            EntityCollection entities) {
        // super.drawSecondaryPass(g2, plot, dataset, pass, series, item, 
        // domainAxis, dataArea, rangeAxis, crosshairState, entities);
    }
     
    /**
     * Updates the control points.
     * 
     * @param point0
     * @param point1
     * @param point2
     * @param point3
     * @param control1
     * @param control2
     * @param smooth
     */
    public static void getControlPoints(Point2D.Double point0, 
            Point2D.Double point1, Point2D.Double point2, 
            Point2D.Double point3, Point2D.Double control1,
            Point2D.Double control2, double smooth) {
         
        // Reference: http://www.antigrain.com/research/bezier_interpolation/
        
        if (point0 == null) point0 = point1; //new Point2D.Double(0, 0);
        if (point3 == null) point3 = point2; //new Point2D.Double(0, 0);
        
        Point2D.Double c1 = new Point2D.Double(
               (point0.x + point1.x) / 2.0, (point0.y + point1.y) / 2.0);
        Point2D.Double c2 = new Point2D.Double(
               (point1.x + point2.x) / 2.0, (point1.y + point2.y) / 2.0);
        Point2D.Double c3 = new Point2D.Double(
               (point2.x + point3.x) / 2.0, (point2.y + point3.y) / 2.0);
        
        double len1 = point1.distance(point0);
        double len2 = point2.distance(point1);
        double len3 = point3.distance(point2);
        
        double k1 = len1 / (len1 + len2);
        double k2 = len2 / (len2 + len3);
        
        Point2D.Double m1 = new Point2D.Double(
               c1.x + (c2.x - c1.x) * k1, c1.y + (c2.y - c1.y) * k1);
        Point2D.Double m2 = new Point2D.Double(
               c2.x + (c3.x - c2.x) * k2, c2.y + (c3.y - c2.y) * k2);
        
        control1.setLocation(new Point2D.Double(
               m1.x + (c2.x - m1.x) * smooth + point1.x - m1.x,
               m1.y + (c2.y - m1.y) * smooth + point1.y - m1.y));
        control2.setLocation(new Point2D.Double(
               m2.x + (c2.x - m2.x) * smooth + point2.x - m2.x,
               m2.y + (c2.y - m2.y) * smooth + point2.y - m2.y));
    }
      
    /**
     * Returns the points for a bezier curve.
     * 
     * @param point0
     * @param point1
     * @param point2
     * @param point3
     * @param smooth
     * @param steps
     * 
     * @return The curve points.
     */
    public static Point2D.Double[] getBezierCurve(Point2D.Double point0,
            Point2D.Double point1, Point2D.Double point2, 
            Point2D.Double point3, double smooth, int steps) {
        Point2D.Double control1 = new Point2D.Double();
        Point2D.Double control2 = new Point2D.Double();
        
        getControlPoints(point0, point1, point2, point3, control1, control2, 
                smooth);
        
        Point2D.Double C = new Point2D.Double(
               3 * (control1.x - point1.x), 3 * (control1.y - point1.y));
        Point2D.Double B = new Point2D.Double(3 * (control2.x - control1.x) 
                - C.x, 3 * (control2.y - control1.y) - C.y);
        Point2D.Double A = new Point2D.Double(point2.x - point1.x - C.x - B.x, 
                point2.y - point1.y - C.y - B.y);

        Point2D.Double[] res = new Point2D.Double[steps + 1];
        double stepSize = 1.0 / steps;
        double step = stepSize;
      
        res[0] = point1;
        for (int i = 1; i < steps; i++) {
            res[i] = new Point2D.Double(A.x * Math.pow(step, 3) + B.x 
                    * Math.pow(step, 2) + C.x * step + point1.x, A.y 
                    * Math.pow(step, 3) + B.y * Math.pow(step, 2) + C.y * step 
                    + point1.y);
            //System.out.println(step + " : " + res[i]);
            step += stepSize;
        }
        res[steps] = point2;
        
        return res;
    }

}
