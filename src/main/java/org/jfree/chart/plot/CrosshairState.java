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
 * -------------------
 * CrosshairState.java
 * -------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.geom.Point2D;

/**
 * Maintains state information about crosshairs on a plot between successive
 * calls to the renderer's draw method.  This class is used internally by
 * JFreeChart - it is not intended for external use.
 */
public class CrosshairState {

    /**
     * A flag that controls whether the distance is calculated in data space
     * or Java2D space.
     */
    private boolean calculateDistanceInDataSpace = false;

    /** The x-value (in data space) for the anchor point. */
    private double anchorX;

    /** The y-value (in data space) for the anchor point. */
    private double anchorY;

    /** The anchor point in Java2D space - if null, don't update crosshair. */
    private Point2D anchor;

    /** The x-value for the current crosshair point. */
    private double crosshairX;

    /** The y-value for the current crosshair point. */
    private double crosshairY;

    /**
     * The dataset index that the crosshair point relates to (this determines
     * the axes that the crosshairs will be plotted against).
     */
    private int datasetIndex;

    /**
     * The smallest distance (so far) between the anchor point and a data
     * point.
     */
    private double distance;

    /**
     * Creates a new {@code crosshairState} instance that calculates
     * distance in Java2D space.
     */
    public CrosshairState() {
        this(false);
    }

    /**
     * Creates a new {@code crosshairState} instance.  Determination of the
     * data point nearest the anchor point can be calculated in either
     * dataspace or Java2D space.  The former should only be used for charts
     * with a single set of axes.
     *
     * @param calculateDistanceInDataSpace  a flag that controls whether the
     *                                      distance is calculated in data
     *                                      space or Java2D space.
     */
    public CrosshairState(boolean calculateDistanceInDataSpace) {
        this.calculateDistanceInDataSpace = calculateDistanceInDataSpace;
    }

    /**
     * Returns the distance between the anchor point and the current crosshair
     * point.
     *
     * @return The distance.
     *
     * @see #setCrosshairDistance(double)
     */
    public double getCrosshairDistance() {
        return this.distance;
    }

    /**
     * Sets the distance between the anchor point and the current crosshair
     * point.  As each data point is processed, its distance to the anchor
     * point is compared with this value and, if it is closer, the data point
     * becomes the new crosshair point.
     *
     * @param distance  the distance.
     *
     * @see #getCrosshairDistance()
     */
    public void setCrosshairDistance(double distance) {
        this.distance = distance;
    }
    
    /**
     * Updates the crosshair point.
     * 
     * @param x  the x-value.
     * @param y  the y-value.
     * @param datasetIndex  the dataset index.
     * @param transX  the x-value in Java2D space.
     * @param transY  the y-value in Java2D space.
     * @param orientation  the plot orientation ({@code null} not permitted).
     */
    public void updateCrosshairPoint(double x, double y, int datasetIndex,
            double transX, double transY, PlotOrientation orientation) {

        if (this.anchor != null) {
            double d = 0.0;
            if (this.calculateDistanceInDataSpace) { 
                d = (x - this.anchorX) * (x - this.anchorX)
                  + (y - this.anchorY) * (y - this.anchorY);
            }
            else {
                // anchor point is in Java2D coordinates
                double xx = this.anchor.getX();
                double yy = this.anchor.getY();
                if (orientation == PlotOrientation.HORIZONTAL) {
                    double temp = yy;
                    yy = xx;
                    xx = temp;
                }
                d = (transX - xx) * (transX - xx)
                    + (transY - yy) * (transY - yy);
            }

            if (d < this.distance) {
                this.crosshairX = x;
                this.crosshairY = y;
                this.datasetIndex = datasetIndex;
                this.distance = d;
            }
        }

    }
    
    /**
     * Checks to see if the specified data point is the closest to the
     * anchor point and, if yes, updates the current state.
     * 
     * @param x  the x-value.
     * @param transX  the x-value in Java2D space.
     * @param datasetIndex  the dataset index.
     */
    public void updateCrosshairX(double x, double transX, int datasetIndex) {
        if (this.anchor == null) {
            return;
        }
        double d = Math.abs(transX - this.anchor.getX());
        if (d < this.distance) {
            this.crosshairX = x;
            this.datasetIndex = datasetIndex;
            this.distance = d;
        }        
    }

    /**
     * Evaluates a y-value and if it is the closest to the anchor y-value it
     * becomes the new crosshair value.
     * <P>
     * Used in cases where only the y-axis is numerical.
     *
     * @param candidateY  y position of the candidate for the new crosshair
     *                    point.
     * @param transY  the y-value in Java2D space.
     * @param datasetIndex  the index of the range axis for this y-value.
     */
    public void updateCrosshairY(double candidateY, double transY, int datasetIndex) {
        if (this.anchor == null) {
            return;
        }
        double d = Math.abs(transY - this.anchor.getY());
        if (d < this.distance) {
            this.crosshairY = candidateY;
            this.datasetIndex = datasetIndex;
            this.distance = d;
        }

    }

    /**
     * Returns the anchor point.
     *
     * @return The anchor point.
     *
     * @see #setAnchor(Point2D)
     */
    public Point2D getAnchor() {
        return this.anchor;
    }

    /**
     * Sets the anchor point.  This is usually the mouse click point in a chart
     * panel, and the crosshair point will often be the data item that is
     * closest to the anchor point.
     * <br><br>
     * Note that the x and y coordinates (in data space) are not updated by
     * this method - the caller is responsible for ensuring that this happens
     * in sync.
     *
     * @param anchor  the anchor point ({@code null} permitted).
     *
     * @see #getAnchor()
     */
    public void setAnchor(Point2D anchor) {
        this.anchor = anchor;
    }

    /**
     * Returns the x-coordinate (in data space) for the anchor point.
     *
     * @return The x-coordinate of the anchor point.
     */
    public double getAnchorX() {
        return this.anchorX;
    }

    /**
     * Sets the x-coordinate (in data space) for the anchor point.  Note that
     * this does NOT update the anchor itself - the caller is responsible for
     * ensuring this is done in sync.
     *
     * @param x  the x-coordinate.
     */
    public void setAnchorX(double x) {
        this.anchorX = x;
    }

    /**
     * Returns the y-coordinate (in data space) for the anchor point.
     *
     * @return The y-coordinate of teh anchor point.
     */
    public double getAnchorY() {
        return this.anchorY;
    }

    /**
     * Sets the y-coordinate (in data space) for the anchor point.  Note that
     * this does NOT update the anchor itself - the caller is responsible for
     * ensuring this is done in sync.
     *
     * @param y  the y-coordinate.
     */
    public void setAnchorY(double y) {
        this.anchorY = y;
    }

    /**
     * Get the x-value for the crosshair point.
     *
     * @return The x position of the crosshair point.
     *
     * @see #setCrosshairX(double)
     */
    public double getCrosshairX() {
        return this.crosshairX;
    }

    /**
     * Sets the x coordinate for the crosshair.  This is the coordinate in data
     * space measured against the domain axis.
     *
     * @param x the coordinate.
     *
     * @see #getCrosshairX()
     * @see #setCrosshairY(double)
     * @see #updateCrosshairPoint(double, double, int, double, double,
     * PlotOrientation)
     */
    public void setCrosshairX(double x) {
        this.crosshairX = x;
    }

    /**
     * Get the y-value for the crosshair point.  This is the coordinate in data
     * space measured against the range axis.
     *
     * @return The y position of the crosshair point.
     *
     * @see #setCrosshairY(double)
     */
    public double getCrosshairY() {
        return this.crosshairY;
    }

    /**
     * Sets the y coordinate for the crosshair.
     *
     * @param y  the y coordinate.
     *
     * @see #getCrosshairY()
     * @see #setCrosshairX(double)
     * @see #updateCrosshairPoint(double, double, int, double, double,
     * PlotOrientation)
     */
    public void setCrosshairY(double y) {
        this.crosshairY = y;
    }

    /**
     * Returns the dataset index that the crosshair values relate to.  The
     * dataset is mapped to specific axes, and this is how the crosshairs are
     * mapped also.
     *
     * @return The dataset index.
     *
     * @see #setDatasetIndex(int)
     */
    public int getDatasetIndex() {
        return this.datasetIndex;
    }

    /**
     * Sets the dataset index that the current crosshair values relate to.
     *
     * @param index  the dataset index.
     *
     * @see #getDatasetIndex()
     */
    public void setDatasetIndex(int index) {
        this.datasetIndex = index;
    }
}
