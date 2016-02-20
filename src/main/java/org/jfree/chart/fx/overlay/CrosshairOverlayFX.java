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
 * --------------
 * OverlayFX.java
 * --------------
 * (C) Copyright 2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 20-Feb-2016 : Version 1 (DG);
 *
 */

package org.jfree.chart.fx.overlay;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.fx.ChartCanvas;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleEdge;

/**
 * An overlay for a {@link ChartViewer} that draws crosshairs on a plot.
 *
 * @since 1.0.20
 */
public class CrosshairOverlayFX extends CrosshairOverlay implements OverlayFX {

    @Override
    public void paintOverlay(Graphics2D g2, ChartCanvas chartCanvas) {
        Shape savedClip = g2.getClip();
        Rectangle2D dataArea = chartCanvas.getRenderingInfo().getPlotInfo().getDataArea();
        g2.clip(dataArea);
        JFreeChart chart = chartCanvas.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
        Iterator iterator = getDomainCrosshairs().iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double x = ch.getValue();
                double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawVerticalCrosshair(g2, dataArea, xx, ch);
                } else {
                    drawHorizontalCrosshair(g2, dataArea, xx, ch);
                }
            }
        }
        ValueAxis yAxis = plot.getRangeAxis();
        RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
        iterator = getRangeCrosshairs().iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double y = ch.getValue();
                double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawHorizontalCrosshair(g2, dataArea, yy, ch);
                } else {
                    drawVerticalCrosshair(g2, dataArea, yy, ch);
                }
            }
        }
        g2.setClip(savedClip);
    }
    
}
