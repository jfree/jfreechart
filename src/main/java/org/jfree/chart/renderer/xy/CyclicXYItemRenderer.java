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
 * CyclicXYItemRenderer.java
 * ---------------------------
 * (C) Copyright 2003-2021, by Nicolas Brodu and Contributors.
 *
 * Original Author:  Nicolas Brodu;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.chart.axis.CyclicNumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.urls.XYURLGenerator;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.xy.XYDataset;

/**
 * The Cyclic XY item renderer is specially designed to handle cyclic axis.
 * While the standard renderer would draw a line across the plot when a cycling
 * occurs, the cyclic renderer splits the line at each cycle end instead. This
 * is done by interpolating new points at cycle boundary. Thus, correct
 * appearance is restored.
 *
 * The Cyclic XY item renderer works exactly like a standard XY item renderer
 * with non-cyclic axis.
 */
public class CyclicXYItemRenderer extends StandardXYItemRenderer
                                  implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 4035912243303764892L;

    /**
     * Default constructor.
     */
    public CyclicXYItemRenderer() {
        super();
    }

    /**
     * Creates a new renderer.
     *
     * @param type  the renderer type.
     */
    public CyclicXYItemRenderer(int type) {
        super(type);
    }

    /**
     * Creates a new renderer.
     *
     * @param type  the renderer type.
     * @param labelGenerator  the tooltip generator.
     */
    public CyclicXYItemRenderer(int type, XYToolTipGenerator labelGenerator) {
        super(type, labelGenerator);
    }

    /**
     * Creates a new renderer.
     *
     * @param type  the renderer type.
     * @param labelGenerator  the tooltip generator.
     * @param urlGenerator  the url generator.
     */
    public CyclicXYItemRenderer(int type,
                                XYToolTipGenerator labelGenerator,
                                XYURLGenerator urlGenerator) {
        super(type, labelGenerator, urlGenerator);
    }


    /**
     * Draws the visual representation of a single data item.
     * When using cyclic axis, do not draw a line from right to left when
     * cycling as would a standard XY item renderer, but instead draw a line
     * from the previous point to the cycle bound in the last cycle, and a line
     * from the cycle bound to current point in the current cycle.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the data area.
     * @param info  the plot rendering info.
     * @param plot  the plot.
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index.
     * @param item  the item index.
     * @param crosshairState  crosshair information for the plot
     *                        ({@code null} permitted).
     * @param pass  the current pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state,
                         Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
                         ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
                         int series, int item, CrosshairState crosshairState, int pass) {

        // Check if we should use standard drawing
        if (shouldUseStandardDrawing(domainAxis, rangeAxis, item)) {
            super.drawItem(g2, state, dataArea, info, plot, domainAxis,
                    rangeAxis, dataset, series, item, crosshairState, pass);
            return;
        }

        // Check if previous point is valid
        if (isPreviousPointInvalid(dataset, series, item)) {
            super.drawItem(g2, state, dataArea, info, plot, domainAxis,
                    rangeAxis, dataset, series, item, crosshairState, pass);
            return;
        }

        // get the data point...
        double xn = dataset.getXValue(series, item);
        double yn = dataset.getYValue(series, item);

        // Check if current point is invalid
        if (Double.isNaN(yn)) {
            return;
        }

        // Initialize points arrays
        double[] x = {dataset.getXValue(series, item - 1), xn};
        double[] y = {dataset.getYValue(series, item - 1), yn};

        // Handle cyclic axes
        double xcycleBound = Double.NaN;
        double ycycleBound = Double.NaN;
        boolean xBoundMapping = false, yBoundMapping = false;
        CyclicNumberAxis cnax = null, cnay = null;

        // Process domain axis
        if (domainAxis instanceof CyclicNumberAxis) {
            processdomainAxisCycling(domainAxis, x, y);
            cnax = (CyclicNumberAxis) domainAxis;
            xcycleBound = cnax.getCycleBound();
            xBoundMapping = cnax.isBoundMappedToLastCycle();
        }

        // Process range axis
        if (rangeAxis instanceof CyclicNumberAxis) {
            processRangeAxisCycling(rangeAxis, x, y);
            cnay = (CyclicNumberAxis) rangeAxis;
            ycycleBound = cnay.getCycleBound();
            yBoundMapping = cnay.isBoundMappedToLastCycle();
        }

        // If no cycling occurred, use standard drawing
        if (x.length == 2) {
            super.drawItem(g2, state, dataArea, info, plot, domainAxis,
                    rangeAxis, dataset, series, item, crosshairState, pass);
            return;
        }

        // Draw the cyclic segments
        drawCyclicSegments(g2, state, dataArea, info, plot, domainAxis, rangeAxis,
                dataset, series, item, crosshairState, pass, x, y,
                cnax, cnay, xcycleBound, ycycleBound, xBoundMapping, yBoundMapping);
    }

    /**
     * Checks if standard drawing should be used instead of cyclic drawing.
     */
    private boolean shouldUseStandardDrawing(ValueAxis domainAxis, ValueAxis rangeAxis, int item) {
        return !getPlotLines()
                || (!(domainAxis instanceof CyclicNumberAxis) && !(rangeAxis instanceof CyclicNumberAxis))
                || item <= 0;
    }

    /**
     * Checks if the previous point is invalid (NaN).
     */
    private boolean isPreviousPointInvalid(XYDataset dataset, int series, int item) {
        return Double.isNaN(dataset.getYValue(series, item - 1));
    }

    /**
     * Processes cycling for the domain axis.
     */
    private void processdomainAxisCycling(ValueAxis domainAxis, double[] x, double[] y) {
        CyclicNumberAxis cnax = (CyclicNumberAxis) domainAxis;
        double xcycleBound = cnax.getCycleBound();

        if (shouldSplitSegment(x[0], x[1], xcycleBound)) {
            double[] nx = new double[3];
            double[] ny = new double[3];
            nx[0] = x[0]; nx[2] = x[1];
            ny[0] = y[0]; ny[2] = y[1];
            nx[1] = xcycleBound;
            ny[1] = interpolateValue(x[0], y[0], x[1], y[1], xcycleBound);
            x = nx;
            y = ny;
        }
    }

    /**
     * Processes cycling for the range axis.
     */
    private void processRangeAxisCycling(ValueAxis rangeAxis, double[] x, double[] y) {
        CyclicNumberAxis cnay = (CyclicNumberAxis) rangeAxis;
        double ycycleBound = cnay.getCycleBound();

        if (shouldSplitSegment(y[0], y[1], ycycleBound)) {
            double[] nx = new double[3];
            double[] ny = new double[3];
            nx[0] = x[0]; nx[2] = x[1];
            ny[0] = y[0]; ny[2] = y[1];
            ny[1] = ycycleBound;
            nx[1] = interpolateValue(y[0], x[0], y[1], x[1], ycycleBound);
            x = nx;
            y = ny;
        }
    }

    /**
     * Checks if a segment should be split at the cycle boundary.
     */
    private boolean shouldSplitSegment(double start, double end, double cycleBound) {
        return (start != end)
                && ((cycleBound >= start && cycleBound <= end)
                || (cycleBound >= end && cycleBound <= start));
    }

    /**
     * Interpolates a value based on the cycle boundary.
     */
    private double interpolateValue(double x1, double y1, double x2, double y2, double x) {
        return (y2 - y1) * (x - x1) / (x2 - x1) + y1;
    }

    /**
     * Draws the segments for cyclic data.
     */
    private void drawCyclicSegments(Graphics2D g2, XYItemRendererState state,
                                    Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
                                    ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
                                    int series, int item, CrosshairState crosshairState, int pass,
                                    double[] x, double[] y, CyclicNumberAxis cnax, CyclicNumberAxis cnay,
                                    double xcycleBound, double ycycleBound,
                                    boolean xBoundMapping, boolean yBoundMapping) {

        OverwriteDataSet newset = new OverwriteDataSet(x, y, dataset);

        // Draw segments
        for (int i = 1; i < x.length; i++) {
            updateAxisMappings(cnax, cnay, x, y, i, xcycleBound, ycycleBound);
            super.drawItem(g2, state, dataArea, info, plot, domainAxis, rangeAxis,
                    newset, series, i, crosshairState, pass);
        }

        // Restore original mappings
        if (cnax != null) {
            cnax.setBoundMappedToLastCycle(xBoundMapping);
        }
        if (cnay != null) {
            cnay.setBoundMappedToLastCycle(yBoundMapping);
        }
    }

    /**
     * Updates the axis mappings for cyclic axes.
     */
    private void updateAxisMappings(CyclicNumberAxis cnax, CyclicNumberAxis cnay,
                                    double[] x, double[] y, int index,
                                    double xcycleBound, double ycycleBound) {
        if (cnax != null) {
            if (xcycleBound == x[index - 1]) {
                cnax.setBoundMappedToLastCycle(x[index] <= xcycleBound);
            }
            if (xcycleBound == x[index]) {
                cnax.setBoundMappedToLastCycle(x[index - 1] <= xcycleBound);
            }
        }
        if (cnay != null) {
            if (ycycleBound == y[index - 1]) {
                cnay.setBoundMappedToLastCycle(y[index] <= ycycleBound);
            }
            if (ycycleBound == y[index]) {
                cnay.setBoundMappedToLastCycle(y[index - 1] <= ycycleBound);
            }
        }
    }

    /**
     * A dataset to hold the interpolated points when drawing new lines.
     */
    protected static class OverwriteDataSet implements XYDataset {

        /** The delegate dataset. */
        protected XYDataset delegateSet;

        /** Storage for the x and y values. */
        Double[] x, y;

        /**
         * Creates a new dataset.
         *
         * @param x  the x values.
         * @param y  the y values.
         * @param delegateSet  the dataset.
         */
        public OverwriteDataSet(double[] x, double[] y, XYDataset delegateSet) {
            this.delegateSet = delegateSet;
            this.x = new Double[x.length]; this.y = new Double[y.length];
            for (int i = 0; i < x.length; ++i) {
                this.x[i] = x[i];
                this.y[i] = y[i];
            }
        }

        /**
         * Returns the order of the domain (X) values.
         *
         * @return The domain order.
         */
        @Override
        public DomainOrder getDomainOrder() {
            return DomainOrder.NONE;
        }

        /**
         * Returns the number of items for the given series.
         *
         * @param series  the series index (zero-based).
         *
         * @return The item count.
         */
        @Override
        public int getItemCount(int series) {
            return this.x.length;
        }

        /**
         * Returns the x-value.
         *
         * @param series  the series index (zero-based).
         * @param item  the item index (zero-based).
         *
         * @return The x-value.
         */
        @Override
        public Number getX(int series, int item) {
            return this.x[item];
        }

        /**
         * Returns the x-value (as a double primitive) for an item within a
         * series.
         *
         * @param series  the series (zero-based index).
         * @param item  the item (zero-based index).
         *
         * @return The x-value.
         */
        @Override
        public double getXValue(int series, int item) {
            double result = Double.NaN;
            Number xx = getX(series, item);
            if (xx != null) {
                result = xx.doubleValue();
            }
            return result;
        }

        /**
         * Returns the y-value.
         *
         * @param series  the series index (zero-based).
         * @param item  the item index (zero-based).
         *
         * @return The y-value.
         */
        @Override
        public Number getY(int series, int item) {
            return this.y[item];
        }

        /**
         * Returns the y-value (as a double primitive) for an item within a
         * series.
         *
         * @param series  the series (zero-based index).
         * @param item  the item (zero-based index).
         *
         * @return The y-value.
         */
        @Override
        public double getYValue(int series, int item) {
            double result = Double.NaN;
            Number yy = getY(series, item);
            if (yy != null) {
                result = yy.doubleValue();
            }
            return result;
        }

        /**
         * Returns the number of series in the dataset.
         *
         * @return The series count.
         */
        @Override
        public int getSeriesCount() {
            return this.delegateSet.getSeriesCount();
        }

        /**
         * Returns the name of the given series.
         *
         * @param series  the series index (zero-based).
         *
         * @return The series name.
         */
        @Override
        public Comparable getSeriesKey(int series) {
            return this.delegateSet.getSeriesKey(series);
        }

        /**
         * Returns the index of the named series, or -1.
         *
         * @param seriesName  the series name.
         *
         * @return The index.
         */
        @Override
        public int indexOf(Comparable seriesName) {
            return this.delegateSet.indexOf(seriesName);
        }

        /**
         * Does nothing.
         *
         * @param listener  ignored.
         */
        @Override
        public void addChangeListener(DatasetChangeListener listener) {
            // unused in parent
        }

        /**
         * Does nothing.
         *
         * @param listener  ignored.
         */
        @Override
        public void removeChangeListener(DatasetChangeListener listener) {
            // unused in parent
        }

    }

}


