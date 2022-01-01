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
 * --------------------
 * PlotChangeEvent.java
 * --------------------
 * (C) Copyright 2000-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.event;

import org.jfree.chart.plot.Plot;

/**
 * An event that can be forwarded to any
 * {@link org.jfree.chart.event.PlotChangeListener} to signal a change to a
 * plot.
 */
public class PlotChangeEvent extends ChartChangeEvent {

    /** The plot that generated the event. */
    private final Plot plot;

    /**
     * Creates a new PlotChangeEvent.
     *
     * @param plot  the plot that generated the event ({@code null} not 
     *     permitted).
     */
    public PlotChangeEvent(Plot plot) {
        super(plot);
        this.plot = plot;
    }

    /**
     * Returns the plot that generated the event (set in the constructor).
     *
     * @return The plot that generated the event.
     */
    public Plot getPlot() {
        return this.plot;
    }

}
