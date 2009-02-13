/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * CrosshairLayerUI.java
 * ---------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 29-Jan-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.jxlayer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Iterator;
import java.util.List;
import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.AbstractLayerUI;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleEdge;

/**
 * A layer for drawing crosshairs over a {@link ChartPanel} using the
 * JXLayer library.
 *
 * @since 1.0.13
 */
public class CrosshairLayerUI extends AbstractLayerUI 
        implements PropertyChangeListener {

    private ChartPanel chartPanel;

    /** Storage for the crosshairs along the x-axis. */
    private List xCrosshairs;

    /** Storage for the crosshairs along the y-axis. */
    private List yCrosshairs;

    /**
     * Creates a new layer UI.
     *
     * @param panel  the chart panel.
     */
    public CrosshairLayerUI(ChartPanel panel) {
        super();
        this.chartPanel = panel;
        this.xCrosshairs = new java.util.ArrayList();
    }

    /**
     * Adds a crosshair against the domain axis.
     *
     * @param crosshair  the crosshair.
     */
    public void addDomainCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        this.xCrosshairs.add(crosshair);
        crosshair.addPropertyChangeListener(this);
        this.chartPanel.repaint();
    }

    /**
     * Adds a crosshair against the range axis.
     *
     * @param crosshair  the crosshair.
     */
    public void addRangeCrosshair(Crosshair crosshair) {
        if (crosshair == null) {
            throw new IllegalArgumentException("Null 'crosshair' argument.");
        }
        this.yCrosshairs.add(crosshair);
        crosshair.addPropertyChangeListener(this);
        this.chartPanel.repaint();
    }

    // TODO:  add methods to remove a crosshair, and clear all crosshairs

    /**
     * Receives a property change event.
     *
     * @param e  the event.
     */
    public void propertyChange(PropertyChangeEvent e) {
        this.chartPanel.repaint();
    }

    /**
     * Paints the crosshairs in the layer.
     *
     * @param g2  the graphics target.
     * @param l  the layer.
     */
    protected void paintLayer(Graphics2D g2, JXLayer l) {
        super.paintLayer(g2, l);
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        Shape savedClip = g2.getClip();
        g2.clip(dataArea);
        JFreeChart chart = this.chartPanel.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
        Iterator iterator = this.xCrosshairs.iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double x = ch.getValue();
                double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
                Line2D.Double line = new Line2D.Double(xx, dataArea.getMinY(),
                        xx, dataArea.getMaxY());
                g2.setPaint(ch.getPaint());
                g2.setStroke(ch.getStroke());
                g2.draw(line);
                // TODO : draw label
            }
        }
        ValueAxis yAxis = plot.getRangeAxis();
        RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
        iterator = this.yCrosshairs.iterator();
        while (iterator.hasNext()) {
            Crosshair ch = (Crosshair) iterator.next();
            if (ch.isVisible()) {
                double y = ch.getValue();
                double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
                Line2D.Double line = new Line2D.Double(dataArea.getMinX(), yy,
                        dataArea.getMaxX(), yy);
                g2.setPaint(ch.getPaint());
                g2.setStroke(ch.getStroke());
                g2.draw(line);
                // TODO : draw label
            }
        }
        g2.setClip(savedClip);
    }

}
