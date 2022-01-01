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
 * ----------------------
 * MarkerChangeEvent.java
 * ----------------------
 * (C) Copyright 2006-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.event;

import org.jfree.chart.plot.Marker;

/**
 * An event that can be forwarded to any {@link MarkerChangeListener} to
 * signal a change to a {@link Marker}.
 */
public class MarkerChangeEvent extends ChartChangeEvent {

    /** The plot that generated the event. */
    private final Marker marker;

    /**
     * Creates a new {@code MarkerChangeEvent} instance.
     *
     * @param marker  the marker that triggered the event ({@code null}
     *     not permitted).
     */
    public MarkerChangeEvent(Marker marker) {
        super(marker); // null check is in here
        this.marker = marker;
    }

    /**
     * Returns the marker that triggered the event.
     *
     * @return The marker that triggered the event (never {@code null}).
     */
    public Marker getMarker() {
        return this.marker;
    }

}
