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
 * --------------------
 * ScrollHandlerFX.java
 * --------------------
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
import javafx.scene.input.ScrollEvent;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.ChartCanvas;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;

/**
 * Handles scroll events (mouse wheel etc) on a {@link ChartCanvas}.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class ScrollHandlerFX extends AbstractMouseHandlerFX 
        implements MouseHandlerFX {

    /** The zoom factor. */
    private double zoomFactor = 0.1;
    
    /**
     * Creates a new instance with the specified ID.
     * 
     * @param id  the handler ID (<code>null</code> not permitted).
     */
    public ScrollHandlerFX(String id) {
        super(id, false, false, false, false);
        this.zoomFactor = 0.1;
    };

    /**
     * Returns the zoom factor.  The default value is 0.10 (ten percent).
     * 
     * @return The zoom factor. 
     */
    public double getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * Sets the zoom factor (a percentage amount by which the mouse wheel 
     * movement will change the chart size).
     * 
     * @param zoomFactor  the zoom factor.
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }
    
    @Override
    public void handleScroll(ChartCanvas canvas, ScrollEvent e) {
        JFreeChart chart = canvas.getChart();
        Plot plot = chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable zoomable = (Zoomable) plot;
            handleZoomable(canvas, zoomable, e);
        }
        else if (plot instanceof PiePlot) {
            PiePlot pp = (PiePlot) plot;
            pp.handleMouseWheelRotation((int) e.getDeltaY());
        }
    }
    
    /**
     * Handle the case where a plot implements the {@link Zoomable} interface.
     *
     * @param zoomable  the zoomable plot.
     * @param e  the mouse wheel event.
     */
    private void handleZoomable(ChartCanvas canvas, Zoomable zoomable, 
            ScrollEvent e) {
        // don't zoom unless the mouse pointer is in the plot's data area
        ChartRenderingInfo info = canvas.getRenderingInfo();
        PlotRenderingInfo pinfo = info.getPlotInfo();
        Point2D p = new Point2D.Double(e.getX(), e.getY());
        if (pinfo.getDataArea().contains(p)) {
            Plot plot = (Plot) zoomable;
            // do not notify while zooming each axis
            boolean notifyState = plot.isNotify();
            plot.setNotify(false);
            int clicks = (int) e.getDeltaY();
            double zf = 1.0 + this.zoomFactor;
            if (clicks < 0) {
                zf = 1.0 / zf;
            }
            if (true) { //this.chartPanel.isDomainZoomable()) {
                zoomable.zoomDomainAxes(zf, pinfo, p, true);
            }
            if (true) { //this.chartPanel.isRangeZoomable()) {
                zoomable.zoomRangeAxes(zf, pinfo, p, true);
            }
            plot.setNotify(notifyState);  // this generates the change event too
        } 
    }

}
