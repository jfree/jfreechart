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
 * -------------------
 * MouseHandlerFX.java
 * -------------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Jun-2014 : Version 1 (DG);
 *
 */package org.jfree.chart.fx.interaction;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.chart.fx.ChartCanvas;

/**
 * The interface for a mouse handler, which is an object that listens for
 * mouse events on a {@link ChartCanvas} and performs a function in response
 * to those events (typical functions include tooltip display, drag zooming,
 * mouse wheel zooming and panning).  A handler can be registered with the
 * {@link ChartCanvas} as a regular handler or as an auxiliary handler.  Upon a 
 * mouse pressed event, the canvas will select *one* regular handler to be the
 * current "live" handler - this selection normally takes into account the
 * modifier keys that are specified for the handler.  The live handler is
 * responsible for unregistering itself once it has finished handling mouse
 * events (it can be reselected again on subsequent mouse pressed events).
 * The auxiliary handlers are always called to respond to mouse events, but
 * after the live handler has dealt with the event first.  Auxiliary handlers
 * should not perform tasks that could interfere with the live handler.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public interface MouseHandlerFX {

    /**
     * Returns the ID for the handler.
     * 
     * @return The ID (never {@code null}). 
     */
    String getID();
    
    /**
     * Returns {@code true} if the mouse handler is enabled, and 
     * {@code false} if it is disabled.
     * 
     * @return A boolean. 
     */
    boolean isEnabled();
    
    /**
     * Returns {@code true} if the specified mouse event has modifier
     * keys that match this handler.
     * 
     * @param e  the mouse event ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    boolean hasMatchingModifiers(MouseEvent e);
    
    /**
     * Handles a mouse moved event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleMouseMoved(ChartCanvas canvas, MouseEvent e);
    
    /**
     * Handles a mouse clicked event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleMouseClicked(ChartCanvas canvas, MouseEvent e);
    
    /**
     * Handles a mouse pressed event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleMousePressed(ChartCanvas canvas, MouseEvent e);
    
    /**
     * Handles a mouse dragged event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleMouseDragged(ChartCanvas canvas, MouseEvent e);
    
    /**
     * Handles a mouse released event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleMouseReleased(ChartCanvas canvas, MouseEvent e);

    /**
     * Handles a scroll event.
     * 
     * @param canvas  the canvas ({@code null} not permitted).
     * @param e  the event ({@code null} not permitted).
     */
    void handleScroll(ChartCanvas canvas, ScrollEvent e);
    
}
