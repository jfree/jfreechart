/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * -------------
 * Zoomable.java
 * -------------
 *
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Rune Fauske;
 *
 */

package org.jfree.chart.plot;

import java.awt.geom.Point2D;

/**
 * A plot that is zoomable must implement this interface to provide a
 * mechanism for user interface components to control the zooming.
 */
public interface Zoomable {

    /**
     * Returns {@code true} if the plot's domain is zoomable, and {@code false}
     * otherwise.
     *
     * @return A boolean.
     *
     * @see #isRangeZoomable()
     */
    boolean isDomainZoomable();

    /**
     * Returns {@code true} if the plot's range is zoomable, and {@code false} 
     * otherwise.
     *
     * @return A boolean.
     *
     * @see #isDomainZoomable()
     */
    boolean isRangeZoomable();

    /**
     * Returns the orientation of the plot.
     *
     * @return The orientation (never {@code null}).
     */
    PlotOrientation getOrientation();

    /**
     * Multiplies the range on the domain axis/axes by the specified factor.
     * The {@code source} point can be used in some cases to identify a
     * subplot, or to determine the center of zooming (refer to the
     * documentation of the implementing class for details).
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     *
     * @see #zoomRangeAxes(double, PlotRenderingInfo, Point2D)
     */
    void zoomDomainAxes(double factor, PlotRenderingInfo state, Point2D source);

    /**
     * Multiplies the range on the domain axis/axes by the specified factor.
     * The {@code source} point can be used in some cases to identify a
     * subplot, or to determine the center of zooming (refer to the
     * documentation of the implementing class for details).
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     * @param useAnchor  use source point as zoom anchor?
     *
     * @see #zoomRangeAxes(double, PlotRenderingInfo, Point2D, boolean)
     */
    void zoomDomainAxes(double factor, PlotRenderingInfo state,
            Point2D source, boolean useAnchor);

    /**
     * Zooms in on the domain axes.  The {@code source} point can be used
     * in some cases to identify a subplot for zooming.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     *
     * @see #zoomRangeAxes(double, double, PlotRenderingInfo, Point2D)
     */
    void zoomDomainAxes(double lowerPercent, double upperPercent,
            PlotRenderingInfo state, Point2D source);

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     * The {@code source} point can be used in some cases to identify a
     * subplot, or to determine the center of zooming (refer to the
     * documentation of the implementing class for details).
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     *
     * @see #zoomDomainAxes(double, PlotRenderingInfo, Point2D)
     */
    void zoomRangeAxes(double factor, PlotRenderingInfo state,
            Point2D source);

    /**
     * Multiplies the range on the range axis/axes by the specified factor.
     * The {@code source} point can be used in some cases to identify a
     * subplot, or to determine the center of zooming (refer to the
     * documentation of the implementing class for details).
     *
     * @param factor  the zoom factor.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     * @param useAnchor  use source point as zoom anchor?
     *
     * @see #zoomDomainAxes(double, PlotRenderingInfo, Point2D)
     */
    void zoomRangeAxes(double factor, PlotRenderingInfo state,
            Point2D source, boolean useAnchor);

    /**
     * Zooms in on the range axes.  The {@code source} point can be used
     * in some cases to identify a subplot for zooming.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     * @param state  the plot state.
     * @param source  the source point (in Java2D coordinates).
     *
     * @see #zoomDomainAxes(double, double, PlotRenderingInfo, Point2D)
     */
    void zoomRangeAxes(double lowerPercent, double upperPercent,
            PlotRenderingInfo state, Point2D source);

}
