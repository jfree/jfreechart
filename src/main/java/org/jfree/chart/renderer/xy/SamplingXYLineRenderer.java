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
 * ---------------------------
 * SamplingXYLineRenderer.java
 * ---------------------------
 * (C) Copyright 2008-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.chart.internal.ShapeUtils;
import org.jfree.data.xy.XYDataset;

/**
 * A renderer that draws line charts.  The renderer doesn't necessarily plot
 * every data item - instead, it tries to plot only those data items that
 * make a difference to the visual output (the other data items are skipped).  
 * This renderer is designed for use with the {@link XYPlot} class.
 */
public class SamplingXYLineRenderer extends AbstractXYItemRenderer
        implements XYItemRenderer, Cloneable, PublicCloneable, Serializable {

    /** The shape that is used to represent a line in the legend. */
    private transient Shape legendLine;

    /**
     * Creates a new renderer.
     */
    public SamplingXYLineRenderer() {
        this.legendLine = new Line2D.Double(-7.0, 0.0, 7.0, 0.0);
        setDefaultLegendShape(this.legendLine);
        setTreatLegendShapeAsLine(true);
    }

    /**
     * Returns the number of passes through the data that the renderer requires
     * in order to draw the chart.  Most charts will require a single pass, but
     * some require two passes.
     *
     * @return The pass count.
     */
    @Override
    public int getPassCount() {
        return 1;
    }

    /**
     * Records the state for the renderer.  This is used to preserve state
     * information between calls to the drawItem() method for a single chart
     * drawing.
     */
    public static class State extends XYItemRendererState {

        /** The path for the current series. */
        GeneralPath seriesPath;

        /**
         * A second path that draws vertical intervals to cover any extreme
         * values.
         */
        GeneralPath intervalPath;

        /**
         * The minimum change in the x-value needed to trigger an update to
         * the seriesPath.
         */
        double dX = 1.0;

        /** The last x-coordinate visited by the seriesPath. */
        double lastX;

        /** The initial y-coordinate for the current x-coordinate. */
        double openY = 0.0;

        /** The highest y-coordinate for the current x-coordinate. */
        double highY = 0.0;

        /** The lowest y-coordinate for the current x-coordinate. */
        double lowY = 0.0;

        /** The final y-coordinate for the current x-coordinate. */
        double closeY = 0.0;

        /**
         * A flag that indicates if the last (x, y) point was 'good'
         * (non-null).
         */
        boolean lastPointGood;

        /**
         * Creates a new state instance.
         *
         * @param info  the plot rendering info.
         */
        public State(PlotRenderingInfo info) {
            super(info);
        }

        /**
         * This method is called by the {@link XYPlot} at the start of each
         * series pass.  We reset the state for the current series.
         *
         * @param dataset  the dataset.
         * @param series  the series index.
         * @param firstItem  the first item index for this pass.
         * @param lastItem  the last item index for this pass.
         * @param pass  the current pass index.
         * @param passCount  the number of passes.
         */
        @Override
        public void startSeriesPass(XYDataset dataset, int series,
                int firstItem, int lastItem, int pass, int passCount) {
            this.seriesPath.reset();
            this.intervalPath.reset();
            this.lastPointGood = false;
            super.startSeriesPass(dataset, series, firstItem, lastItem, pass,
                    passCount);
        }

    }

    /**
     * Initialises the renderer.
     * <P>
     * This method will be called before the first item is rendered, giving the
     * renderer an opportunity to initialise any state information it wants to
     * maintain.  The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param data  the data.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The renderer state.
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2,
            Rectangle2D dataArea, XYPlot plot, XYDataset data,
            PlotRenderingInfo info) {

        double dpi = 72;
    //        Integer dpiVal = (Integer) g2.getRenderingHint(HintKey.DPI);
    //        if (dpiVal != null) {
    //            dpi = dpiVal.intValue();
    //        }
        State state = new State(info);
        state.seriesPath = new GeneralPath();
        state.intervalPath = new GeneralPath();
        state.dX = 72.0 / dpi;
        return state;
    }

    /**
     * Draws the visual representation of a single data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area within which the data is being drawn.
     * @param info  collects information about the drawing.
     * @param plot  the plot (can be used to obtain standard color
     *              information etc).
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param crosshairState  crosshair information for the plot
     *                        ({@code null} permitted).
     * @param pass  the pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state, 
            Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
            ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
            int series, int item, CrosshairState crosshairState, int pass) {

        // do nothing if item is not visible
        if (!getItemVisible(series, item)) {
            return;
        }
        RectangleEdge xAxisLocation = plot.getDomainAxisEdge();
        RectangleEdge yAxisLocation = plot.getRangeAxisEdge();

        // get the data point...
        double x1 = dataset.getXValue(series, item);
        double y1 = dataset.getYValue(series, item);
        double transX1 = domainAxis.valueToJava2D(x1, dataArea, xAxisLocation);
        double transY1 = rangeAxis.valueToJava2D(y1, dataArea, yAxisLocation);

        State s = (State) state;
        // update path to reflect latest point
        if (!Double.isNaN(transX1) && !Double.isNaN(transY1)) {
            float x = (float) transX1;
            float y = (float) transY1;
            PlotOrientation orientation = plot.getOrientation();
            if (orientation == PlotOrientation.HORIZONTAL) {
                x = (float) transY1;
                y = (float) transX1;
            }
            if (s.lastPointGood) {
                if ((Math.abs(x - s.lastX) > s.dX)) {
                    if (s.lowY < s.highY) {
                        s.intervalPath.moveTo((float) s.lastX, (float) s.lowY);
                        s.intervalPath.lineTo((float) s.lastX, (float) s.highY);

                        s.seriesPath.moveTo((float) s.lastX, (float) s.closeY);
                    }
                    s.seriesPath.lineTo(x, y);
                    s.lastX = x;
                    s.openY = y;
                    s.highY = y;
                    s.lowY = y;
                    s.closeY = y;
                }
                else {
                    s.highY = Math.max(s.highY, y);
                    s.lowY = Math.min(s.lowY, y);
                    s.closeY = y;
                }
            }
            else {
                s.seriesPath.moveTo(x, y);
                s.lastX = x;
                s.openY = y;
                s.highY = y;
                s.lowY = y;
                s.closeY = y;
            }
            s.lastPointGood = true;
        }
        else {
            s.lastPointGood = false;
        }
        // if this is the last item, draw the path ...
        if (item == s.getLastItemIndex()) {
            // draw path
            PathIterator pi = s.seriesPath.getPathIterator(null);
            int count = 0;
            while (!pi.isDone()) {
                count++;
                pi.next();
            }
            g2.setStroke(getItemStroke(series, item));
            g2.setPaint(getItemPaint(series, item));
            g2.draw(s.seriesPath);
            g2.draw(s.intervalPath);
        }
    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the clone cannot be created.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        SamplingXYLineRenderer clone = (SamplingXYLineRenderer) super.clone();
        clone.legendLine = CloneUtils.clone(this.legendLine);
        return clone;
    }

    /**
     * Tests this renderer for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SamplingXYLineRenderer)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        SamplingXYLineRenderer that = (SamplingXYLineRenderer) obj;
        if (!ShapeUtils.equal(this.legendLine, that.legendLine)) {
            return false;
        }
        return true;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.legendLine = SerialUtils.readShape(stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeShape(this.legendLine, stream);
    }

}