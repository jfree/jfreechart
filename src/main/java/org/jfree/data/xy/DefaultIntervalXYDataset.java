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
 * -----------------------------
 * DefaultIntervalXYDataset.java
 * -----------------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.general.DatasetChangeEvent;

/**
 * A dataset that defines a range (interval) for both the x-values and the
 * y-values.  This implementation uses six arrays to store the x, start-x,
 * end-x, y, start-y and end-y values.
 * <br><br>
 * An alternative implementation of the {@link IntervalXYDataset} interface
 * is provided by the {@link XYIntervalSeriesCollection} class.
 *
 * @since 1.0.3
 */
public class DefaultIntervalXYDataset<S extends Comparable<S>> 
        extends AbstractIntervalXYDataset<S>
        implements PublicCloneable {

    /**
     * Storage for the series keys.  This list must be kept in sync with the
     * seriesList.
     */
    private List<S> seriesKeys;

    /**
     * Storage for the series in the dataset.  We use a list because the
     * order of the series is significant.  This list must be kept in sync
     * with the seriesKeys list.
     */
    private List<double[][]> seriesList;

    /**
     * Creates a new {@code DefaultIntervalXYDataset} instance, initially
     * containing no data.
     */
    public DefaultIntervalXYDataset() {
        this.seriesKeys = new ArrayList<>();
        this.seriesList = new ArrayList<>();
    }

    /**
     * Returns the number of series in the dataset.
     *
     * @return The series count.
     */
    @Override
    public int getSeriesCount() {
        return this.seriesList.size();
    }

    /**
     * Returns the key for a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     *
     * @return The key for the series.
     *
     * @throws IllegalArgumentException if {@code series} is not in the
     *     specified range.
     */
    @Override
    public S getSeriesKey(int series) {
        Args.requireInRange(series, "series", 0, this.seriesKeys.size() - 1);
        return this.seriesKeys.get(series);
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     *
     * @return The item count.
     *
     * @throws IllegalArgumentException if {@code series} is not in the
     *     specified range.
     */
    @Override
    public int getItemCount(int series) {
        Args.requireInRange(series, "series", 0, this.seriesList.size() - 1);
        double[][] seriesArray = this.seriesList.get(series);
        return seriesArray[0].length;
    }

    /**
     * Returns the x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getX(int, int)
     */
    @Override
    public double getXValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[0][item];
    }

    /**
     * Returns the y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getY(int, int)
     */
    @Override
    public double getYValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[3][item];
    }

    /**
     * Returns the starting x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The starting x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getStartX(int, int)
     */
    @Override
    public double getStartXValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[1][item];
    }

    /**
     * Returns the ending x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The ending x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getEndX(int, int)
     */
    @Override
    public double getEndXValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[2][item];
    }

    /**
     * Returns the starting y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The starting y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getStartY(int, int)
     */
    @Override
    public double getStartYValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[4][item];
    }

    /**
     * Returns the ending y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The ending y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getEndY(int, int)
     */
    @Override
    public double getEndYValue(int series, int item) {
        double[][] seriesData = this.seriesList.get(series);
        return seriesData[5][item];
    }

    /**
     * Returns the ending x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The ending x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getEndXValue(int, int)
     */
    @Override
    public Number getEndX(int series, int item) {
        return getEndXValue(series, item);
    }

    /**
     * Returns the ending y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The ending y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getEndYValue(int, int)
     */
    @Override
    public Number getEndY(int series, int item) {
        return getEndYValue(series, item);
    }

    /**
     * Returns the starting x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The starting x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getStartXValue(int, int)
     */
    @Override
    public Number getStartX(int series, int item) {
        return getStartXValue(series, item);
    }

    /**
     * Returns the starting y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The starting y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getStartYValue(int, int)
     */
    @Override
    public Number getStartY(int series, int item) {
        return getStartYValue(series, item);
    }

    /**
     * Returns the x-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The x-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getXValue(int, int)
     */
    @Override
    public Number getX(int series, int item) {
        return getXValue(series, item);
    }

    /**
     * Returns the y-value for an item within a series.
     *
     * @param series  the series index (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item index (in the range {@code 0} to
     *     {@code getItemCount(series)}).
     *
     * @return The y-value.
     *
     * @throws ArrayIndexOutOfBoundsException if {@code series} is not
     *     within the specified range.
     * @throws ArrayIndexOutOfBoundsException if {@code item} is not
     *     within the specified range.
     *
     * @see #getYValue(int, int)
     */
    @Override
    public Number getY(int series, int item) {
        return getYValue(series, item);
    }

    /**
     * Adds a series or if a series with the same key already exists replaces
     * the data for that series, then sends a {@link DatasetChangeEvent} to
     * all registered listeners.
     *
     * @param seriesKey  the series key ({@code null} not permitted).
     * @param data  the data (must be an array with length 6, containing six
     *     arrays of equal length, the first three containing the x-values
     *     (x, xLow and xHigh) and the last three containing the y-values
     *     (y, yLow and yHigh)).
     */
    public void addSeries(S seriesKey, double[][] data) {
        Args.nullNotPermitted(seriesKey, "seriesKey");
        Args.nullNotPermitted(data, "data");
        if (data.length != 6) {
            throw new IllegalArgumentException(
                    "The 'data' array must have length == 6.");
        }
        int length = data[0].length;
        if (length != data[1].length || length != data[2].length
                || length != data[3].length || length != data[4].length
                || length != data[5].length) {
            throw new IllegalArgumentException(
                "The 'data' array must contain six arrays with equal length.");
        }
        int seriesIndex = indexOf(seriesKey);
        if (seriesIndex == -1) {  // add a new series
            this.seriesKeys.add(seriesKey);
            this.seriesList.add(data);
        }
        else {  // replace an existing series
            this.seriesList.remove(seriesIndex);
            this.seriesList.add(seriesIndex, data);
        }
        notifyListeners(new DatasetChangeEvent(this, this));
    }

    /**
     * Tests this {@code DefaultIntervalXYDataset} instance for equality
     * with an arbitrary object.  This method returns {@code true} if and
     * only if:
     * <ul>
     * <li>{@code obj} is not {@code null};</li>
     * <li>{@code obj} is an instance of {@code DefaultIntervalXYDataset};</li>
     * <li>both datasets have the same number of series, each containing
     *         exactly the same values.</li>
     * </ul>
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultIntervalXYDataset)) {
            return false;
        }
        DefaultIntervalXYDataset<String> that = (DefaultIntervalXYDataset) obj;
        if (!this.seriesKeys.equals(that.seriesKeys)) {
            return false;
        }
        for (int i = 0; i < this.seriesList.size(); i++) {
            double[][] d1 = (double[][]) this.seriesList.get(i);
            double[][] d2 = (double[][]) that.seriesList.get(i);
            double[] d1x = d1[0];
            double[] d2x = d2[0];
            if (!Arrays.equals(d1x, d2x)) {
                return false;
            }
            double[] d1xs = d1[1];
            double[] d2xs = d2[1];
            if (!Arrays.equals(d1xs, d2xs)) {
                return false;
            }
            double[] d1xe = d1[2];
            double[] d2xe = d2[2];
            if (!Arrays.equals(d1xe, d2xe)) {
                return false;
            }
            double[] d1y = d1[3];
            double[] d2y = d2[3];
            if (!Arrays.equals(d1y, d2y)) {
                return false;
            }
            double[] d1ys = d1[4];
            double[] d2ys = d2[4];
            if (!Arrays.equals(d1ys, d2ys)) {
                return false;
            }
            double[] d1ye = d1[5];
            double[] d2ye = d2[5];
            if (!Arrays.equals(d1ye, d2ye)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = this.seriesKeys.hashCode();
        result = 29 * result + this.seriesList.hashCode();
        return result;
    }

    /**
     * Returns a clone of this dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the dataset contains a series with
     *         a key that cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultIntervalXYDataset clone
                = (DefaultIntervalXYDataset) super.clone();
        clone.seriesKeys = new ArrayList<>(this.seriesKeys);
        clone.seriesList = new ArrayList<>(this.seriesList.size());
        for (int i = 0; i < this.seriesList.size(); i++) {
            double[][] data = (double[][]) this.seriesList.get(i);
            double[] x = data[0];
            double[] xStart = data[1];
            double[] xEnd = data[2];
            double[] y = data[3];
            double[] yStart = data[4];
            double[] yEnd = data[5];
            double[] xx = new double[x.length];
            double[] xxStart = new double[xStart.length];
            double[] xxEnd = new double[xEnd.length];
            double[] yy = new double[y.length];
            double[] yyStart = new double[yStart.length];
            double[] yyEnd = new double[yEnd.length];
            System.arraycopy(x, 0, xx, 0, x.length);
            System.arraycopy(xStart, 0, xxStart, 0, xStart.length);
            System.arraycopy(xEnd, 0, xxEnd, 0, xEnd.length);
            System.arraycopy(y, 0, yy, 0, y.length);
            System.arraycopy(yStart, 0, yyStart, 0, yStart.length);
            System.arraycopy(yEnd, 0, yyEnd, 0, yEnd.length);
            clone.seriesList.add(i, new double[][] {xx, xxStart, xxEnd, yy,
                    yyStart, yyEnd});
        }
        return clone;
    }

}
