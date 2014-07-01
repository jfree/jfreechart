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
 * ----------------------
 * ChartMouseEventFX.java
 * ----------------------
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

import java.io.Serializable;
import java.util.EventObject;
import javafx.scene.input.MouseEvent;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.fx.ChartViewer;

/**
 * A mouse event for a chart that is displayed in a (JavaFX) 
 * {@link ChartViewer}.
 *
 * @see ChartMouseListenerFX
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class ChartMouseEventFX extends EventObject implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -682393837314562149L;

    /** The chart that the mouse event relates to. */
    private JFreeChart chart;

    /** The Java mouse event that triggered this event. */
    private MouseEvent trigger;

    /** The chart entity (if any). */
    private ChartEntity entity;

    /**
     * Constructs a new event.
     *
     * @param chart  the source chart ({@code null} not permitted).
     * @param trigger  the mouse event that triggered this event
     *                 ({@code null} not permitted).
     * @param entity  the chart entity (if any) under the mouse point
     *                ({@code null} permitted).
     */
    public ChartMouseEventFX(JFreeChart chart, MouseEvent trigger,
                           ChartEntity entity) {
        super(chart);
        this.chart = chart;
        this.trigger = trigger;
        this.entity = entity;
    }

    /**
     * Returns the chart that the mouse event relates to.
     *
     * @return The chart (never {@code null}).
     */
    public JFreeChart getChart() {
        return this.chart;
    }

    /**
     * Returns the mouse event that triggered this event.
     *
     * @return The event (never {@code null}).
     */
    public MouseEvent getTrigger() {
        return this.trigger;
    }

    /**
     * Returns the chart entity (if any) under the mouse point.
     *
     * @return The chart entity (possibly {@code null}).
     */
    public ChartEntity getEntity() {
        return this.entity;
    }

}
