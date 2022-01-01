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
 * --------------
 * DialLayer.java
 * --------------
 * (C) Copyright 2006-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.EventListener;

/**
 * A dial layer draws itself within a reference frame.  The view frame is a
 * subset of the reference frame, and defines the area that is actually
 * visible.
 * <br><br>
 * Classes that implement this interface should be {@link Serializable},
 * otherwise chart serialization may fail.
 */
public interface DialLayer {

    /**
     * Returns a flag that indicates whether or not the layer is visible.
     *
     * @return A boolean.
     */
    boolean isVisible();

    /**
     * Registers a listener with this layer, so that it receives notification
     * of changes to this layer.
     *
     * @param listener  the listener.
     */
    void addChangeListener(DialLayerChangeListener listener);

    /**
     * Deregisters a listener, so that it no longer receives notification of
     * changes to this layer.
     *
     * @param listener  the listener.
     */
    void removeChangeListener(DialLayerChangeListener listener);

    /**
     * Returns {@code true} if the specified listener is currently
     * registered with the this layer.
     *
     * @param listener  the listener.
     *
     * @return A boolean.
     */
    boolean hasListener(EventListener listener);

    /**
     * Returns {@code true} if the drawing should be clipped to the
     * dial window (which is defined by the {@link DialFrame}), and
     * {@code false} otherwise.
     *
     * @return A boolean.
     */
    boolean isClippedToWindow();

    /**
     * Draws the content of this layer.
     *
     * @param g2  the graphics target ({@code null} not permitted).
     * @param plot  the plot (typically this should not be {@code null},
     *     but for a layer that doesn't need to reference the plot, it may
     *     be permitted).
     * @param frame  the reference frame for the dial's geometry
     *     ({@code null} not permitted).  This is typically larger than
     *     the visible area of the dial (see the next parameter).
     * @param view  the visible area for the dial ({@code null} not
     *     permitted).
     */
    void draw(Graphics2D g2, DialPlot plot, Rectangle2D frame,
            Rectangle2D view);

}
