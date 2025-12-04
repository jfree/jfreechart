/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 */

package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.internal.Args;

/**
 * Encapsulates the rendering context for a plot, including the graphics
 * device, data area, rendering info, and crosshair state. This class reduces
 * method parameter counts and improves code clarity.
 *
 * @param <R> the row key type
 * @param <C> the column key type
 *
 * @since 2.1.0
 */
public class RenderingContext<R extends Comparable<R>, C extends Comparable<C>> {

    /** The graphics device. */
    private final Graphics2D g2;

    /** The data area. */
    private final Rectangle2D dataArea;

    /** The rendering info. */
    private final PlotRenderingInfo renderingInfo;

    /** The crosshair state. */
    private final CategoryCrosshairState<R, C> crosshairState;

    /**
     * Creates a new rendering context.
     *
     * @param g2 the graphics device ({@code null} not permitted).
     * @param dataArea the data area ({@code null} not permitted).
     * @param renderingInfo the rendering info ({@code null} not permitted).
     * @param crosshairState the crosshair state ({@code null} not permitted).
     */
    public RenderingContext(Graphics2D g2, Rectangle2D dataArea,
            PlotRenderingInfo renderingInfo,
            CategoryCrosshairState<R, C> crosshairState) {
        Args.nullNotPermitted(g2, "g2");
        Args.nullNotPermitted(dataArea, "dataArea");
        Args.nullNotPermitted(renderingInfo, "renderingInfo");
        Args.nullNotPermitted(crosshairState, "crosshairState");

        this.g2 = g2;
        this.dataArea = dataArea;
        this.renderingInfo = renderingInfo;
        this.crosshairState = crosshairState;
    }

    /**
     * Returns the graphics device.
     *
     * @return The graphics device (never {@code null}).
     */
    public Graphics2D getG2() {
        return this.g2;
    }

    /**
     * Returns the data area.
     *
     * @return The data area (never {@code null}).
     */
    public Rectangle2D getDataArea() {
        return this.dataArea;
    }

    /**
     * Returns the rendering info.
     *
     * @return The rendering info (never {@code null}).
     */
    public PlotRenderingInfo getRenderingInfo() {
        return this.renderingInfo;
    }

    /**
     * Returns the crosshair state.
     *
     * @return The crosshair state (never {@code null}).
     */
    public CategoryCrosshairState<R, C> getCrosshairState() {
        return this.crosshairState;
    }
}
