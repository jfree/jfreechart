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
 * -----------------
 * PanHandlerFX.java
 * -----------------
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
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartCanvas;
import org.jfree.chart.plot.Pannable;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;

/**
 * Handles panning of charts on a {@link ChartCanvas}.  This handler
 * should be configured with the required modifier keys and installed as a
 * live handler (not an auxiliary handler).
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class PanHandlerFX extends AbstractMouseHandlerFX {

    /** The last mouse location seen during panning. */
    private Point2D panLast;
 
    private double panW;
    private double panH;
    
    /**
     * Creates a new instance that requires no modifier keys.
     * 
     * @param id  the id (<code>null</code> not permitted).
     */
    public PanHandlerFX(String id) { 
        this(id, false, false, false, false);
    }
    
    /**
     * Creates a new instance that will be activated using the specified 
     * combination of modifier keys.
     * 
     * @param id  the id (<code>null</code> not permitted).
     * @param altKey  require ALT key?
     * @param ctrlKey  require CTRL key?
     * @param metaKey  require META key?
     * @param shiftKey   require SHIFT key?
     */
    public PanHandlerFX(String id, boolean altKey, boolean ctrlKey, 
            boolean metaKey, boolean shiftKey) {
        super(id, altKey, ctrlKey, metaKey, shiftKey);
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
        Plot plot = canvas.getChart().getPlot();
        if (!(plot instanceof Pannable)) {
            canvas.clearLiveHandler();
            return;
        }
        Pannable pannable = (Pannable) plot;
        if (pannable.isDomainPannable() || pannable.isRangePannable()) {
            Point2D point = new Point2D.Double(e.getX(), e.getY());
            Rectangle2D dataArea = canvas.findDataArea(point);
            if (dataArea != null && dataArea.contains(point)) {
                this.panW = dataArea.getWidth();
                this.panH = dataArea.getHeight();
                this.panLast = point;
                canvas.setCursor(javafx.scene.Cursor.MOVE);
            }
        }
        // the actual panning occurs later in the mouseDragged() method
    }
    
    /**
     * Handles a mouse dragged event by calculating the distance panned and
     * updating the axes accordingly.
     * 
     * @param canvas  the JavaFX canvas (<code>null</code> not permitted).
     * @param e  the mouse event (<code>null</code> not permitted).
     */
    public void handleMouseDragged(ChartCanvas canvas, MouseEvent e) {
        if (this.panLast == null) {
            //handle panning if we have a start point else unregister
            canvas.clearLiveHandler();
            return;
        }

        JFreeChart chart = canvas.getChart();
        double dx = e.getX() - this.panLast.getX();
        double dy = e.getY() - this.panLast.getY();
        if (dx == 0.0 && dy == 0.0) {
            return;
        }
        double wPercent = -dx / this.panW;
        double hPercent = dy / this.panH;
        boolean old = chart.getPlot().isNotify();
        chart.getPlot().setNotify(false);
        Pannable p = (Pannable) chart.getPlot();
        PlotRenderingInfo info = canvas.getRenderingInfo().getPlotInfo();
        if (p.getOrientation().isVertical()) {
            p.panDomainAxes(wPercent, info, this.panLast);
            p.panRangeAxes(hPercent, info, this.panLast);
        }
        else {
            p.panDomainAxes(hPercent, info, this.panLast);
            p.panRangeAxes(wPercent, info, this.panLast);
        }
        this.panLast = new Point2D.Double(e.getX(), e.getY());
        chart.getPlot().setNotify(old);
    }

    public void handleMouseReleased(ChartCanvas canvas, MouseEvent e) {  
        //if we have been panning reset the cursor
        //unregister in any case
        if (this.panLast != null) {
            canvas.setCursor(javafx.scene.Cursor.DEFAULT);
        }
        this.panLast = null;
        canvas.clearLiveHandler();
    }

}
