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
 * ---------------------
 * ChartChangeEvent.java
 * ---------------------
 * (C) Copyright 2000-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.event;

import java.util.EventObject;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.internal.Args;

/**
 * A change event that encapsulates information about a change to a chart.
 */
public class ChartChangeEvent extends EventObject {

    /** The type of event. */
    private ChartChangeEventType type;

    /** The chart that generated the event. */
    private JFreeChart chart;

    /**
     * Creates a new chart change event.
     *
     * @param source  the source of the event (could be the chart, a title,
     *                an axis etc., {@code null} not permitted).
     */
    public ChartChangeEvent(Object source) {
        this(source, null, ChartChangeEventType.GENERAL);
    }

    /**
     * Creates a new chart change event.
     *
     * @param source  the source of the event (could be the chart, a title, an
     *                axis etc., {@code null} not permitted).
     * @param chart  the chart that generated the event.
     */
    public ChartChangeEvent(Object source, JFreeChart chart) {
        this(source, chart, ChartChangeEventType.GENERAL);
    }

    /**
     * Creates a new chart change event.
     *
     * @param source  the source of the event (could be the chart, a title, an
                      axis etc., {@code null} not permitted).
     * @param chart  the chart that generated the event.
     * @param type  the type of event ({@code null} not permitted).
     */
    public ChartChangeEvent(Object source, JFreeChart chart,
            ChartChangeEventType type) {
        super(source);
        Args.nullNotPermitted(type, "type");
        this.chart = chart;
        this.type = type;
    }

    /**
     * Returns the chart that generated the change event.
     *
     * @return The chart that generated the change event.
     */
    public JFreeChart getChart() {
        return this.chart;
    }

    /**
     * Sets the chart that generated the change event.
     *
     * @param chart  the chart that generated the event.
     */
    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }

    /**
     * Returns the event type.
     *
     * @return The event type (never {@code null}).
     */
    public ChartChangeEventType getType() {
        return this.type;
    }

    /**
     * Sets the event type.
     *
     * @param type  the event type ({@code null} not permitted).
     */
    public void setType(ChartChangeEventType type) {
        Args.nullNotPermitted(type, "type");
        this.type = type;
    }

}
