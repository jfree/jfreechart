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
 * ------------
 * Overlay.java
 * ------------
 * (C) Copyright 2009-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.swing;

import java.awt.Graphics2D;

/**
 * An {@code Overlay} is anything that can be drawn over top of a chart to add
 * additional information to the chart.  This interface defines the operations
 * that must be supported for an overlay that can be added to a 
 * {@link ChartPanel} in Swing.
 * <br><br>
 * Note: if you are using JavaFX rather than Swing, then you need to look at 
 * the {@code OverlayFX} interface in the <b>JFreeChart-FX</b> project.
 */
public interface Overlay {

    /**
     * Paints the visual representation of the overlay.  This method will be
     * called by the {@link ChartPanel} after the underlying chart has been 
     * fully rendered.  When implementing this method, the {@code chartPanel} 
     * argument can be used to get state information from the chart (you can, 
     * for example, extract the axis ranges for the chart).
     *
     * @param g2  the graphics target (never {@code null}).
     * @param chartPanel  the chart panel (never {@code null}).
     */
    void paintOverlay(Graphics2D g2, ChartPanel chartPanel);

    /**
     * Registers a change listener with the overlay.  Typically this method
     * not be called by user code, it exists so that the {@link ChartPanel}
     * can register and receive notification of changes to the overlay (such
     * changes will trigger an automatic repaint of the chart).
     * 
     * @param listener  the listener ({@code null} not permitted).
     * 
     * @see #removeChangeListener(org.jfree.chart.event.OverlayChangeListener) 
     */
    void addChangeListener(OverlayChangeListener listener);

    /**
     * Deregisters a listener from the overlay.
     * 
     * @param listener  the listener ({@code null} not permitted).
     * 
     * @see #addChangeListener(org.jfree.chart.event.OverlayChangeListener) 
     */
    void removeChangeListener(OverlayChangeListener listener);

}
