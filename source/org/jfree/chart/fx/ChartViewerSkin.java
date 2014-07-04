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
 * --------------------
 * ChartViewerSkin.java
 * --------------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 20-Jun-2014 : Version 1 (DG);
 */


package org.jfree.chart.fx;

import org.jfree.chart.fx.interaction.ZoomHandlerFX;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.fx.interaction.ChartMouseListenerFX;
import org.jfree.chart.util.ParamChecks;

/**
 * A default skin for the {@link ChartViewer} control.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class ChartViewerSkin extends SkinBase<ChartViewer>  {

    /** The chart canvas. */
    private ChartCanvas canvas;
    
    /** 
     * The zoom rectangle is used to display the zooming region when
     * doing a drag-zoom with the mouse.  Most of the time this rectangle
     * is not visible.
     */
    private Rectangle zoomRectangle;
    
    /**
     * Creates a new instance.
     * 
     * @param control  the control ({@code null} not permitted). 
     */    
    public ChartViewerSkin(ChartViewer control) {
        super(control);
        getChildren().add(createNode(control));
        this.zoomRectangle = new Rectangle(0, 0, new Color(0, 0, 1, 0.25));
        this.zoomRectangle.setManaged(false);
        this.zoomRectangle.setVisible(false);
        getChildren().add(this.zoomRectangle);
    }
    
    /**
     * Returns the rendering info from the most recent drawing of the chart.
     * 
     * @return The rendering info (possibly {@code null}).
     * 
     * @since 1.0.19
     */
    public ChartRenderingInfo getRenderingInfo() {
        return this.canvas.getRenderingInfo();
    }

    /**
     * Sets the chart displayed by this control.
     * 
     * @param chart  the chart ({@code null} not permitted). 
     */
    public void setChart(JFreeChart chart) {
        this.canvas.setChart(chart);
    }
    
    public void setTooltipEnabled(boolean enabled) {
        this.canvas.setTooltipEnabled(enabled);        
    }
    
    /**
     * Returns the current fill paint for the zoom rectangle.
     * 
     * @return The fill paint.
     */
    public Paint getZoomFillPaint() {
        return this.zoomRectangle.getFill();
    }
    
    /**
     * Sets the fill paint for the zoom rectangle.
     * 
     * @param paint  the new paint. 
     */
    public void setZoomFillPaint(Paint paint) {
        this.zoomRectangle.setFill(paint);
    }
    
    /**
     * Registers a listener to receive {@link ChartMouseEvent} notifications
     * from the chart viewer.
     *
     * @param listener  the listener ({@code null} not permitted).
     */
    public void addChartMouseListener(ChartMouseListenerFX listener) {
        ParamChecks.nullNotPermitted(listener, "listener");
        this.canvas.addChartMouseListener(listener);
    }

    /**
     * Removes a listener from the list of objects listening for chart mouse
     * events.
     *
     * @param listener  the listener.
     */
    public void removeChartMouseListener(ChartMouseListenerFX listener) {
        this.canvas.removeChartMouseListener(listener);
    }
    
    /**
     * Sets the visibility of the zoom rectangle.
     * 
     * @param visible  the new flag value.
     */
    public void setZoomRectangleVisible(boolean visible) {
        this.zoomRectangle.setVisible(visible);
    }
    
    /**
     * Sets the location and size of the zoom rectangle and makes it visible
     * if it is not already visible.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param w  the width.
     * @param h  the height.
     */
    public void showZoomRectangle(double x, double y, double w, double h) {
        this.zoomRectangle.setX(x);
        this.zoomRectangle.setY(y);
        this.zoomRectangle.setWidth(w);
        this.zoomRectangle.setHeight(h);
        this.zoomRectangle.setVisible(true);
    }

    /** 
     * Creates the node representing this control.
     * 
     * @return The node.
     */
    private BorderPane createNode(ChartViewer control) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(800, 500);
        StackPane sp = new StackPane();
        sp.setMinSize(10, 10);
        sp.setPrefSize(600, 400);
        this.canvas = new ChartCanvas(getSkinnable().getChart());
        this.canvas.setTooltipEnabled(control.isTooltipEnabled());
        this.canvas.addChartMouseListener(control);
        this.canvas.widthProperty().bind(sp.widthProperty());
        this.canvas.heightProperty().bind(sp.heightProperty());
 
        this.canvas.addMouseHandler(new ZoomHandlerFX("zoom", control));
        sp.getChildren().add(this.canvas);
        
        borderPane.setCenter(sp);
        return borderPane;
    }
    
}
