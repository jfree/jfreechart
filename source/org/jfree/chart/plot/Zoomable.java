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
 * -------------
 * Zoomable.java
 * -------------
 *
 * (C) Copyright 2004, 2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Rune Fauske;
 *
 * Changes
 * -------
 * 12-Nov-2004 : Version 1 (DG);
 * 26-Jan-2004 : Added getOrientation() method (DG);
 * 04-Sep-2006 : Added credit for Rune Fauske, see patch 1050659 (DG);
 *
 */

package org.jfree.chart.plot;

import java.awt.geom.Point2D;

import org.jfree.chart.ChartPanel;

/**
 * A plot that is zoomable must implement this interface to provide a
 * mechanism for the {@link ChartPanel} to control the zooming.
 */
public interface Zoomable {

    /**
     * Returns <code>true</code> if the plot's domain is zoomable, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     */
    public boolean isDomainZoomable();
    
    /**
     * Returns <code>true</code> if the plot's range is zoomable, and 
     * <code>false</code> otherwise.
     * 
     * @return A boolean.
     */
    public boolean isRangeZoomable();

    /**
     * Returns the orientation of the plot.
     * 
     * @return The orientation.
     */
    public PlotOrientation getOrientation();
    
    /**
     * Multiplies the range on the domain axis/axes by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomDomainAxes(double factor, PlotRenderingInfo state, 
                               Point2D source);

    /**
     * Zooms in on the domain axes.
     * 
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomDomainAxes(double lowerPercent, double upperPercent, 
                               PlotRenderingInfo state, Point2D source);

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomRangeAxes(double factor, PlotRenderingInfo state, 
                              Point2D source);

    /**
     * Zooms in on the range axes.
     * 
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     */
    public void zoomRangeAxes(double lowerPercent, double upperPercent, 
                              PlotRenderingInfo state, Point2D source);

}
