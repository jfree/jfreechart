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
 * ----------------
 * ChartCanvas.java
 * ----------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Jun-2014 : Version 1 (DG);
 * 19-Jul-2014 : Add clearRect() call for each draw (DG);
 *
 */

package org.jfree.chart.fx;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.fx.interaction.AnchorHandlerFX;
import org.jfree.chart.fx.interaction.DispatchHandlerFX;
import org.jfree.chart.fx.interaction.ChartMouseEventFX;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.fx.interaction.TooltipHandlerFX;
import org.jfree.chart.fx.interaction.ScrollHandlerFX;
import org.jfree.chart.fx.interaction.PanHandlerFX;
import org.jfree.chart.fx.interaction.MouseHandlerFX;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.util.ParamChecks;

/**
 * A canvas for displaying a {@link JFreeChart} in JavaFX.  You can use the
 * canvas directly to display charts, but usually the {@link ChartViewer}
 * class (which embeds a canvas) is a better option.
 * <p>
 * The canvas installs several default mouse handlers, if you don't like the
 * behaviour provided by these you can retrieve the handler by ID and
 * disable or remove it (the IDs are "tooltip", "scroll", "anchor", "pan" and 
 * "dispatch").
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class ChartCanvas extends Canvas implements ChartChangeListener {
    
    /** The chart being displayed in the canvas (never null). */
    private JFreeChart chart;
    
    /**
     * The graphics drawing context (will be an instance of FXGraphics2D).
     */
    private Graphics2D g2;
   
    /** 
     * The anchor point (can be null) is usually updated to reflect the most 
     * recent mouse click and is used during chart rendering to update 
     * crosshairs belonging to the chart.  
     */
    private Point2D anchor;
    
    /** The chart rendering info from the most recent drawing of the chart. */
    private ChartRenderingInfo info;
    
    /** The tooltip object for the canvas (can be null). */
    private Tooltip tooltip;
    
    /** 
     * A flag that controls whether or not tooltips will be generated from the
     * chart as the mouse pointer moves over it.
     */
    private boolean tooltipEnabled;
    
    /** Storage for registered chart mouse listeners. */
    private transient List<ChartMouseListenerFX> chartMouseListeners;

    /** The current live handler (can be null). */
    private MouseHandlerFX liveHandler;
    
    /** 
     * The list of available live mouse handlers (can be empty but not null). 
     */
    private List<MouseHandlerFX> availableMouseHandlers;
    
    /** The auxiliary mouse handlers (can be empty but not null). */
    private List<MouseHandlerFX> auxiliaryMouseHandlers;
    
    /**
     * Creates a new canvas to display the supplied chart in JavaFX.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public ChartCanvas(JFreeChart chart) {
        ParamChecks.nullNotPermitted(chart, "chart");
        this.chart = chart;
        this.chart.addChangeListener(this);
        this.tooltip = null;
        this.tooltipEnabled = true;
        this.chartMouseListeners = new ArrayList<ChartMouseListenerFX>();
        
        widthProperty().addListener(evt -> draw());
        heightProperty().addListener(evt -> draw());
        this.g2 = new FXGraphics2D(getGraphicsContext2D());
        this.liveHandler = null;
        this.availableMouseHandlers = new ArrayList<MouseHandlerFX>();
        
        this.availableMouseHandlers.add(new PanHandlerFX("pan", true, false, 
                false, false));
 
        this.auxiliaryMouseHandlers = new ArrayList<MouseHandlerFX>();
        this.auxiliaryMouseHandlers.add(new TooltipHandlerFX("tooltip"));
        this.auxiliaryMouseHandlers.add(new ScrollHandlerFX("scroll"));
        this.auxiliaryMouseHandlers.add(new AnchorHandlerFX("anchor"));
        this.auxiliaryMouseHandlers.add(new DispatchHandlerFX("dispatch"));
        
        setOnMouseMoved((MouseEvent e) -> { handleMouseMoved(e); });
        setOnMouseClicked((MouseEvent e) -> { handleMouseClicked(e); });
        setOnMousePressed((MouseEvent e) -> { handleMousePressed(e); });
        setOnMouseDragged((MouseEvent e) -> { handleMouseDragged(e); });
        setOnMouseReleased((MouseEvent e) -> { handleMouseReleased(e); });
        setOnScroll((ScrollEvent event) -> { handleScroll(event); });
    }
    
    /**
     * Returns the chart that is being displayed by this node.
     * 
     * @return The chart (never {@code null}). 
     */
    public JFreeChart getChart() {
        return this.chart;
    }
    
    /**
     * Sets the chart to be displayed by this node.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public void setChart(JFreeChart chart) {
        ParamChecks.nullNotPermitted(chart, "chart");
        this.chart.removeChangeListener(this);
        this.chart = chart;
        this.chart.addChangeListener(this);
        draw();
    }
    
    /**
     * Returns the rendering info from the most recent drawing of the chart.
     * 
     * @return The rendering info (possibly {@code null}). 
     */
    public ChartRenderingInfo getRenderingInfo() {
        return this.info;
    }

    /**
     * Returns the flag that controls whether or not tooltips are enabled.  
     * The default value is {@code true}.  The {@link TooltipHandlerFX} 
     * class will only update the tooltip if this flag is set to 
     * {@code true}.
     * 
     * @return The flag. 
     */
    public boolean isTooltipEnabled() {
        return this.tooltipEnabled;
    }

    /**
     * Sets the flag that controls whether or not tooltips are enabled.
     * 
     * @param tooltipEnabled  the new flag value. 
     */
    public void setTooltipEnabled(boolean tooltipEnabled) {
        this.tooltipEnabled = tooltipEnabled;
    }
    
    /**
     * Set the anchor point and forces a redraw of the chart (the anchor point
     * is used to determine the position of the crosshairs on the chart, if
     * they are visible).
     * 
     * @param anchor  the anchor ({@code null} permitted). 
     */
    public void setAnchor(Point2D anchor) {
        this.anchor = anchor;
        this.chart.setNotify(true);  // force a redraw
    }

    /**
     * Registers a listener to receive {@link ChartMouseEvent} notifications.
     *
     * @param listener  the listener ({@code null} not permitted).
     */
    public void addChartMouseListener(ChartMouseListenerFX listener) {
        ParamChecks.nullNotPermitted(listener, "listener");
        this.chartMouseListeners.add(listener);
    }

    /**
     * Removes a listener from the list of objects listening for chart mouse
     * events.
     *
     * @param listener  the listener.
     */
    public void removeChartMouseListener(ChartMouseListenerFX listener) {
        this.chartMouseListeners.remove(listener);
    }
    
    /**
     * Returns the mouse handler with the specified ID, or {@code null} if
     * there is no handler with that ID.  This method will look for handlers
     * in both the regular and auxiliary handler lists.
     * 
     * @param id  the ID ({@code null} not permitted).
     * 
     * @return The handler with the specified ID 
     */
    public MouseHandlerFX getMouseHandler(String id) {
        for (MouseHandlerFX h: this.availableMouseHandlers) {
            if (h.getID().equals(id)) {
                return h;
            }
        }
        for (MouseHandlerFX h: this.auxiliaryMouseHandlers) {
            if (h.getID().equals(id)) {
                return h;
            }
        }
        return null;    
    }
    
    /**
     * Adds a mouse handler to the list of available handlers (handlers that
     * are candidates to take the position of live handler).  The handler must
     * have an ID that uniquely identifies it amongst the handlers registered
     * with this canvas.
     * 
     * @param handler  the handler ({@code null} not permitted). 
     */
    public void addMouseHandler(MouseHandlerFX handler) {
        if (!this.hasUniqueID(handler)) {
            throw new IllegalArgumentException(
                    "There is already a handler with that ID (" 
                            + handler.getID() + ").");
        }
        this.availableMouseHandlers.add(handler);
    }
    
    /**
     * Removes a handler from the list of available handlers.
     * 
     * @param handler  the handler ({@code null} not permitted). 
     */
    public void removeMouseHandler(MouseHandlerFX handler) {
        this.availableMouseHandlers.remove(handler);
    }

    /**
     * Validates that the specified handler has an ID that uniquely identifies 
     * it amongst the existing handlers for this canvas.
     * 
     * @param handler  the handler ({@code null} not permitted).
     * 
     * @return A boolean.
     */
    private boolean hasUniqueID(MouseHandlerFX handler) {
        for (MouseHandlerFX h: this.availableMouseHandlers) {
            if (handler.getID().equals(h.getID())) {
                return false;
            }
        }
        for (MouseHandlerFX h: this.auxiliaryMouseHandlers) {
            if (handler.getID().equals(h.getID())) {
                return false;
            }
        }
        return true;    
    }
    
    /**
     * Clears the current live handler.  This method is intended for use by the
     * handlers themselves, you should not call it directly.
     */
    public void clearLiveHandler() {
        this.liveHandler = null;    
    }
    
    /**
     * Draws the content of the canvas and updates the 
     * {@code renderingInfo} attribute with the latest rendering 
     * information.
     */
    public final void draw() {
        GraphicsContext ctx = getGraphicsContext2D();
        ctx.save();
        double width = getWidth();
        double height = getHeight();
        if (width > 0 && height > 0) {
            ctx.clearRect(0, 0, width, height);
            this.info = new ChartRenderingInfo();
            this.chart.draw(this.g2, new Rectangle((int) width, (int) height), 
                    this.anchor, this.info);
        }
        ctx.restore();
        this.anchor = null;
    }
 
    /**
     * Returns the data area (the area inside the axes) for the plot or subplot.
     *
     * @param point  the selection point (for subplot selection).
     *
     * @return The data area.
     */
    public Rectangle2D findDataArea(Point2D point) {
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D result;
        if (plotInfo.getSubplotCount() == 0) {
            result = plotInfo.getDataArea();
        }
        else {
            int subplotIndex = plotInfo.getSubplotIndex(point);
            if (subplotIndex == -1) {
                return null;
            }
            result = plotInfo.getSubplotInfo(subplotIndex).getDataArea();
        }
        return result;
    }
    
    /**
     * Return {@code true} to indicate the canvas is resizable.
     * 
     * @return {@code true}. 
     */
    @Override
    public boolean isResizable() {
        return true;
    }

    /**
     * Sets the tooltip text, with the (x, y) location being used for the
     * anchor.  If the text is {@code null}, no tooltip will be displayed.
     * This method is intended for calling by the {@link TooltipHandlerFX}
     * class, you won't normally call it directly.
     * 
     * @param text  the text ({@code null} permitted).
     * @param x  the x-coordinate of the mouse pointer.
     * @param y  the y-coordinate of the mouse pointer.
     */
    public void setTooltip(String text, double x, double y) {
        if (text != null) {
            if (this.tooltip == null) {
                this.tooltip = new Tooltip(text);
                Tooltip.install(this, this.tooltip);
            } else {
                this.tooltip.setText(text);           
                this.tooltip.setAnchorX(x);
                this.tooltip.setAnchorY(y);
            }                   
        } else {
            Tooltip.uninstall(this, this.tooltip);
            this.tooltip = null;
        }
    }
    
    /**
     * Handles a mouse pressed event by (1) selecting a live handler if one
     * is not already selected, (2) passing the event to the live handler if
     * there is one, and (3) passing the event to all enabled auxiliary 
     * handlers.
     * 
     * @param e  the mouse event.
     */
    private void handleMousePressed(MouseEvent e) {
        if (this.liveHandler == null) {
            for (MouseHandlerFX handler: this.availableMouseHandlers) {
                if (handler.isEnabled() && handler.hasMatchingModifiers(e)) {
                    this.liveHandler = handler;      
                }
            }
        }
        
        if (this.liveHandler != null) {
            this.liveHandler.handleMousePressed(this, e);
        }
        
        // pass on the event to the auxiliary handlers
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleMousePressed(this, e);
            }
        }
    }
    
    /**
     * Handles a mouse moved event by passing it on to the registered handlers.
     * 
     * @param e  the mouse event.
     */
    private void handleMouseMoved(MouseEvent e) {
        if (this.liveHandler != null && this.liveHandler.isEnabled()) {
            this.liveHandler.handleMouseMoved(this, e);
        }
        
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleMouseMoved(this, e);
            }
        }
    }

    /**
     * Handles a mouse dragged event by passing it on to the registered 
     * handlers.
     * 
     * @param e  the mouse event.
     */
    private void handleMouseDragged(MouseEvent e) {
        if (this.liveHandler != null && this.liveHandler.isEnabled()) {
            this.liveHandler.handleMouseDragged(this, e);
        }
        
        // pass on the event to the auxiliary handlers
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleMouseDragged(this, e);
            }
        }
    }

    /**
     * Handles a mouse released event by passing it on to the registered 
     * handlers.
     * 
     * @param e  the mouse event.
     */
    private void handleMouseReleased(MouseEvent e) {
        if (this.liveHandler != null && this.liveHandler.isEnabled()) {
            this.liveHandler.handleMouseReleased(this, e);
        }
        
        // pass on the event to the auxiliary handlers
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleMouseReleased(this, e);
            }
        }
    }
    
    /**
     * Handles a mouse released event by passing it on to the registered 
     * handlers.
     * 
     * @param e  the mouse event.
     */
    private void handleMouseClicked(MouseEvent e) {
        if (this.liveHandler != null && this.liveHandler.isEnabled()) {
            this.liveHandler.handleMouseClicked(this, e);
        }

        // pass on the event to the auxiliary handlers
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleMouseClicked(this, e);
            }
        }
    }

    /**
     * Handles a scroll event by passing it on to the registered handlers.
     * 
     * @param e  the scroll event.
     */
    protected void handleScroll(ScrollEvent e) {
        if (this.liveHandler != null && this.liveHandler.isEnabled()) {
            this.liveHandler.handleScroll(this, e);
        }
        for (MouseHandlerFX handler: this.auxiliaryMouseHandlers) {
            if (handler.isEnabled()) {
                handler.handleScroll(this, e);
            }
        }
    }
    
    /**
     * Receives a notification from the chart that it has been changed and
     * responds by redrawing the chart entirely.
     * 
     * @param event  event information. 
     */
    @Override
    public void chartChanged(ChartChangeEvent event) {
        draw();
    }
    
    public void dispatchMouseMovedEvent(Point2D point, MouseEvent e) {
        double x = point.getX();
        double y = point.getY();
        ChartEntity entity = this.info.getEntityCollection().getEntity(x, y);
        ChartMouseEventFX event = new ChartMouseEventFX(this.chart, e, entity);
        for (ChartMouseListenerFX listener : this.chartMouseListeners) {
            listener.chartMouseMoved(event);
        }
    }

    public void dispatchMouseClickedEvent(Point2D point, MouseEvent e) {
        double x = point.getX();
        double y = point.getY();
        ChartEntity entity = this.info.getEntityCollection().getEntity(x, y);
        ChartMouseEventFX event = new ChartMouseEventFX(this.chart, e, entity);
        for (ChartMouseListenerFX listener : this.chartMouseListeners) {
            listener.chartMouseClicked(event);
        }
    }
}

