/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * ----------------------
 * MouseWheelHandler.java
 * ----------------------
 * (C) Copyright 2009-2016 by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Ulrich Voigt - patch 2686040;
 *                   Jim Goodwin - bug fix;
 *
 * Changes
 * -------
 * 18-Mar-2009 : Version 1, based on ideas by UV in patch 2686040 (DG);
 * 26-Mar-2009 : Implemented Serializable (DG);
 * 10-Sep-2009 : Bug fix by Jim Goodwin to respect domain/rangeZoomable flags
 *               in the ChartPanel (DG);
 * 04-Nov-2009 : Pass mouse wheel notification to PiePlot (DG);
 *
 */

package org.jfree.chart;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.ui.RectangleEdge;

/**
 * A class that handles mouse wheel events for the {@link ChartPanel} class.
 *
 * @since 1.0.13
 */
class MouseWheelHandler implements MouseWheelListener, Serializable {

    /** The chart panel. */
    private ChartPanel chartPanel;

    /** The zoom factor. */
    double zoomFactor;

    /**
     * Creates a new instance for the specified chart panel.
     *
     * @param chartPanel  the chart panel ({@code null} not permitted).
     */
    public MouseWheelHandler(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
        this.zoomFactor = 0.10;
        this.chartPanel.addMouseWheelListener(this);
    }

    /**
     * Returns the current zoom factor.  The default value is 0.10 (ten
     * percent).
     *
     * @return The zoom factor.
     *
     * @see #setZoomFactor(double)
     */
    public double getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * Sets the zoom factor.
     *
     * @param zoomFactor  the zoom factor.
     *
     * @see #getZoomFactor()
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    /**
     * Handles a mouse wheel event from the underlying chart panel.
     *
     * @param e  the event.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        JFreeChart chart = this.chartPanel.getChart();
        if (chart == null) {
            return;
        }
        Plot plot = chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable zoomable = (Zoomable) plot;
            handleZoomable(zoomable, e);
        }
        else if (plot instanceof PiePlot) {
            PiePlot pp = (PiePlot) plot;
            pp.handleMouseWheelRotation(e.getWheelRotation());
        }
    }

    /**
     * Handle the case where a plot implements the {@link Zoomable} interface.
     *
     * @param zoomable  the zoomable plot.
     * @param e  the mouse wheel event.
     */
    private void handleZoomable(Zoomable zoomable, MouseWheelEvent e) {
        // don't zoom unless the mouse pointer is in the plot's data area
        ChartRenderingInfo info = this.chartPanel.getChartRenderingInfo();
        PlotRenderingInfo pinfo = info.getPlotInfo();
        Point2D p = this.chartPanel.translateScreenToJava2D(e.getPoint());

        // If the event is on a specific axis, zoom just that axis.
        AxisEntity axisEntity = this.chartPanel.findAxis(p);
        if (axisEntity != null && axisEntity.getAxis() instanceof ValueAxis) {
            ValueAxis axis = (ValueAxis) axisEntity.getAxis();
            Plot plot = (Plot) zoomable;
            // do not notify while zooming each axis
            boolean notifyState = plot.isNotify();
            plot.setNotify(false);
            int clicks = e.getWheelRotation();
            double zf = 1.0 + this.zoomFactor;
            if (clicks < 0) {
                zf = 1.0 / zf;
            }

            Rectangle2D axisArea = axisEntity.getArea().getBounds2D();
            Rectangle2D dataArea = pinfo.getDataArea();
            if (p.getX() < dataArea.getMinX() || p.getX() > dataArea.getMaxX()) {
                // This axis runs vertical
                double anchor = axis.java2DToValue(e.getY(), axisArea, RectangleEdge.RIGHT);
                axis.resizeRange2(zf, anchor);
            } else {
                double anchor = axis.java2DToValue(e.getX(), axisArea, RectangleEdge.TOP);
                axis.resizeRange2(zf, anchor);
            }

            plot.setNotify(notifyState);  // this generates the change event too
            return;
        }

        if (!pinfo.getDataArea().contains(p)) {
            return;
        }

        Plot plot = (Plot) zoomable;
        // do not notify while zooming each axis
        boolean notifyState = plot.isNotify();
        plot.setNotify(false);
        int clicks = e.getWheelRotation();
        double zf = 1.0 + this.zoomFactor;
        if (clicks < 0) {
            zf = 1.0 / zf;
        }
        if (chartPanel.isDomainZoomable()) {
            zoomable.zoomDomainAxes(zf, pinfo, p, true);
        }
        if (chartPanel.isRangeZoomable()) {
            zoomable.zoomRangeAxes(zf, pinfo, p, true);
        }
        plot.setNotify(notifyState);  // this generates the change event too
    }

}
