/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * ------------------
 * ZoomHandlerFX.java
 * ------------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Jun-2014 : Version 1 (DG);
 *
 */

package org.jfree.chart.fx.interaction;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javafx.scene.input.MouseEvent;
import org.jfree.chart.fx.ChartCanvas;
import org.jfree.chart.fx.ChartViewer;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.util.ShapeUtilities;

/**
 * Handles drag zooming of charts on a {@link ChartCanvas}.  This 
 * handler should be configured with the required modifier keys and installed 
 * as a live handler (not an auxiliary handler).  This handler only works for
 * a <b>ChartCanvas</b> that is embedded in a {@link ChartViewer}, since it 
 * relies on the <b>ChartViewer</b> for drawing the zoom rectangle.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class ZoomHandlerFX extends AbstractMouseHandlerFX {

    /** The viewer is used to overlay the zoom rectangle. */
    private ChartViewer viewer;
    
    /** The starting point for the zoom. */
    private Point2D startPoint;
    
    /**
     * Creates a new instance with no modifier keys required.
     * 
     * @param id  the handler ID (<code>null</code> not permitted).
     * @param parent  the chart viewer.
     */
    public ZoomHandlerFX(String id, ChartViewer parent) { 
        this(id, parent, false, false, false, false);
    }
    
    /**
     * Creates a new instance that will be activated using the specified 
     * combination of modifier keys.
     * 
     * @param id  the handler ID (<code>null</code> not permitted).
     * @param parent  the chart viewer.
     * @param altKey  require ALT key?
     * @param ctrlKey  require CTRL key?
     * @param metaKey  require META key?
     * @param shiftKey   require SHIFT key?
     */
    public ZoomHandlerFX(String id, ChartViewer parent, boolean altKey, 
            boolean ctrlKey, boolean metaKey, boolean shiftKey) {
        super(id, altKey, ctrlKey, metaKey, shiftKey);
        this.viewer = parent;
    }
    
    /**
     * Handles a mouse pressed event by recording the initial mouse pointer
     * location.
     * 
     * @param canvas  the JavaFX canvas (<code>null</code> not permitted).
     * @param e  the mouse event (<code>null</code> not permitted).
     */
    @Override
    public void handleMousePressed(ChartCanvas canvas, MouseEvent e) {
        Point2D pt = new Point2D.Double(e.getX(), e.getY());
        Rectangle2D dataArea = canvas.findDataArea(pt);
        if (dataArea != null) {
            this.startPoint = ShapeUtilities.getPointInRectangle(e.getX(),
                    e.getY(), dataArea);
        } else {
            this.startPoint = null;
            canvas.clearLiveHandler();
        }
    }
    
    /**
     * Handles a mouse dragged event by updating the zoom rectangle displayed
     * in the ChartViewer.
     * 
     * @param canvas  the JavaFX canvas (<code>null</code> not permitted).
     * @param e  the mouse event (<code>null</code> not permitted).
     */
    @Override
    public void handleMouseDragged(ChartCanvas canvas, MouseEvent e) {
        if (this.startPoint == null) {
            //no initial zoom rectangle exists but the handler is set
            //as life handler unregister
            canvas.clearLiveHandler();
            return;
        }

        boolean hZoom, vZoom;
        Plot p = canvas.getChart().getPlot();
        if (!(p instanceof Zoomable)) {
            return;
        }
        Zoomable z = (Zoomable) p;
        if (z.getOrientation().isHorizontal()) {
            hZoom = z.isRangeZoomable();
            vZoom = z.isDomainZoomable();
        } else {
            hZoom = z.isDomainZoomable();
            vZoom = z.isRangeZoomable();
        }
        Rectangle2D dataArea = canvas.findDataArea(this.startPoint);
        
        double x = this.startPoint.getX();
        double y = this.startPoint.getY();
        double w = 0;
        double h = 0;
        if (hZoom && vZoom) {
            // selected rectangle shouldn't extend outside the data area...
            double xmax = Math.min(e.getX(), dataArea.getMaxX());
            double ymax = Math.min(e.getY(), dataArea.getMaxY());
            w = xmax - this.startPoint.getX();
            h = ymax - this.startPoint.getY();
        }
        else if (hZoom) {
            double xmax = Math.min(e.getX(), dataArea.getMaxX());
            y = dataArea.getMinY();
            w = xmax - this.startPoint.getX();
            h = dataArea.getHeight();
        }
        else if (vZoom) {
            double ymax = Math.min(e.getY(), dataArea.getMaxY());
            x = dataArea.getMinX();
            w = dataArea.getWidth();
            h = ymax - this.startPoint.getY();
        }
        viewer.showZoomRectangle(x, y, w, h);
    }

    @Override
    public void handleMouseReleased(ChartCanvas canvas, MouseEvent e) {  
        Plot p = canvas.getChart().getPlot();
        if (!(p instanceof Zoomable)) {
            return;
        }
        boolean hZoom, vZoom;
        Zoomable z = (Zoomable) p;
        if (z.getOrientation().isHorizontal()) {
            hZoom = z.isRangeZoomable();
            vZoom = z.isDomainZoomable();
        } else {
            hZoom = z.isDomainZoomable();
            vZoom = z.isRangeZoomable();
        }

        boolean zoomTrigger1 = hZoom && Math.abs(e.getX()
                - this.startPoint.getX()) >= 10;
        boolean zoomTrigger2 = vZoom && Math.abs(e.getY()
                - this.startPoint.getY()) >= 10;
        if (zoomTrigger1 || zoomTrigger2) {
            Point2D endPoint = new Point2D.Double(e.getX(), e.getY());
            PlotRenderingInfo pri = canvas.getRenderingInfo().getPlotInfo();
            if ((hZoom && (e.getX() < this.startPoint.getX()))
                    || (vZoom && (e.getY() < this.startPoint.getY()))) {
                boolean saved = p.isNotify();
                p.setNotify(false);
                z.zoomDomainAxes(0, pri, endPoint);
                z.zoomRangeAxes(0, pri, endPoint);
                p.setNotify(saved);
            } else {
                double x = this.startPoint.getX();
                double y = this.startPoint.getY();
                double w = e.getX() - x;
                double h = e.getY() - y;
                Rectangle2D dataArea = canvas.findDataArea(this.startPoint);
                double maxX = dataArea.getMaxX();
                double maxY = dataArea.getMaxY();
                // for mouseReleased event, (horizontalZoom || verticalZoom)
                // will be true, so we can just test for either being false;
                // otherwise both are true
                if (!vZoom) {
                    y = dataArea.getMinY();
                    w = Math.min(w, maxX - this.startPoint.getX());
                    h = dataArea.getHeight();
                }
                else if (!hZoom) {
                    x = dataArea.getMinX();
                    w = dataArea.getWidth();
                    h = Math.min(h, maxY - this.startPoint.getY());
                }
                else {
                    w = Math.min(w, maxX - this.startPoint.getX());
                    h = Math.min(h, maxY - this.startPoint.getY());
                }
                Rectangle2D zoomArea = new Rectangle2D.Double(x, y, w, h);
                
                boolean saved = p.isNotify();
                p.setNotify(false);
                double pw0 = percentW(x, dataArea);
                double pw1 = percentW(x + w, dataArea);
                double ph0 = percentH(y, dataArea);
                double ph1 = percentH(y + h, dataArea);
                PlotRenderingInfo info 
                        = this.viewer.getRenderingInfo().getPlotInfo();
                if (z.getOrientation().isVertical()) {
                    z.zoomDomainAxes(pw0, pw1, info, endPoint);
                    z.zoomRangeAxes(1 - ph1, 1 - ph0, info, endPoint);
                } else {
                    z.zoomRangeAxes(pw0, pw1, info, endPoint);
                    z.zoomDomainAxes(1 - ph1, 1 - ph0, info, endPoint);
                }
                p.setNotify(saved);
                
            }
        }
        viewer.hideZoomRectangle();
        this.startPoint = null;
        canvas.clearLiveHandler();
    }

    private double percentW(double x, Rectangle2D r) {
        return (x - r.getMinX()) / r.getWidth();
    }
    
    private double percentH(double y, Rectangle2D r) {
        return (y - r.getMinY()) / r.getHeight();
    }
}
