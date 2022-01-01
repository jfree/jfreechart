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
 * --------------------------
 * DefaultHighLowDataset.java
 * --------------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.util.Arrays;
import java.util.Date;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;

/**
 * A simple implementation of the {@link OHLCDataset} interface.  See also
 * the {@link DefaultOHLCDataset} class, which provides another implementation
 * that is very similar.
 */
public class DefaultHighLowDataset extends AbstractXYDataset
        implements OHLCDataset, PublicCloneable {

    /** The series key. */
    private Comparable seriesKey;

    /** Storage for the dates. */
    private Date[] date;

    /** Storage for the high values. */
    private Number[] high;

    /** Storage for the low values. */
    private Number[] low;

    /** Storage for the open values. */
    private Number[] open;

    /** Storage for the close values. */
    private Number[] close;

    /** Storage for the volume values. */
    private Number[] volume;

    /**
     * Constructs a new high/low/open/close dataset.
     * <p>
     * The current implementation allows only one series in the dataset.
     * This may be extended in a future version.
     *
     * @param seriesKey  the key for the series ({@code null} not
     *     permitted).
     * @param date  the dates ({@code null} not permitted).
     * @param high  the high values ({@code null} not permitted).
     * @param low  the low values ({@code null} not permitted).
     * @param open  the open values ({@code null} not permitted).
     * @param close  the close values ({@code null} not permitted).
     * @param volume  the volume values ({@code null} not permitted).
     */
    public DefaultHighLowDataset(Comparable seriesKey, Date[] date,
            double[] high, double[] low, double[] open, double[] close,
            double[] volume) {

        Args.nullNotPermitted(seriesKey, "seriesKey");
        Args.nullNotPermitted(date, "date");
        this.seriesKey = seriesKey;
        this.date = date;
        this.high = createNumberArray(high);
        this.low = createNumberArray(low);
        this.open = createNumberArray(open);
        this.close = createNumberArray(close);
        this.volume = createNumberArray(volume);

    }

    /**
     * Returns the key for the series stored in this dataset.
     *
     * @param series  the index of the series (ignored, this dataset supports
     *     only one series and this method always returns the key for series 0).
     *
     * @return The series key (never {@code null}).
     */
    @Override
    public Comparable getSeriesKey(int series) {
        return this.seriesKey;
    }

    /**
     * Returns the x-value for one item in a series.  The value returned is a
     * {@code Long} instance generated from the underlying
     * {@code Date} object.  To avoid generating a new object instance,
     * you might prefer to call {@link #getXValue(int, int)}.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value.
     *
     * @see #getXValue(int, int)
     * @see #getXDate(int, int)
     */
    @Override
    public Number getX(int series, int item) {
        return this.date[item].getTime();
    }

    /**
     * Returns the x-value for one item in a series, as a Date.
     * <p>
     * This method is provided for convenience only.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The x-value as a Date.
     *
     * @see #getX(int, int)
     */
    public Date getXDate(int series, int item) {
        return this.date[item];
    }

    /**
     * Returns the y-value for one item in a series.
     * <p>
     * This method (from the {@link XYDataset} interface) is mapped to the
     * {@link #getCloseValue(int, int)} method.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The y-value.
     *
     * @see #getYValue(int, int)
     */
    @Override
    public Number getY(int series, int item) {
        return getClose(series, item);
    }

    /**
     * Returns the high-value for one item in a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The high-value.
     *
     * @see #getHighValue(int, int)
     */
    @Override
    public Number getHigh(int series, int item) {
        return this.high[item];
    }

    /**
     * Returns the high-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The high-value.
     *
     * @see #getHigh(int, int)
     */
    @Override
    public double getHighValue(int series, int item) {
        double result = Double.NaN;
        Number h = getHigh(series, item);
        if (h != null) {
            result = h.doubleValue();
        }
        return result;
    }

    /**
     * Returns the low-value for one item in a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The low-value.
     *
     * @see #getLowValue(int, int)
     */
    @Override
    public Number getLow(int series, int item) {
        return this.low[item];
    }

    /**
     * Returns the low-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The low-value.
     *
     * @see #getLow(int, int)
     */
    @Override
    public double getLowValue(int series, int item) {
        double result = Double.NaN;
        Number l = getLow(series, item);
        if (l != null) {
            result = l.doubleValue();
        }
        return result;
    }

    /**
     * Returns the open-value for one item in a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The open-value.
     *
     * @see #getOpenValue(int, int)
     */
    @Override
    public Number getOpen(int series, int item) {
        return this.open[item];
    }

    /**
     * Returns the open-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The open-value.
     *
     * @see #getOpen(int, int)
     */
    @Override
    public double getOpenValue(int series, int item) {
        double result = Double.NaN;
        Number open = getOpen(series, item);
        if (open != null) {
            result = open.doubleValue();
        }
        return result;
    }

    /**
     * Returns the close-value for one item in a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The close-value.
     *
     * @see #getCloseValue(int, int)
     */
    @Override
    public Number getClose(int series, int item) {
        return this.close[item];
    }

    /**
     * Returns the close-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The close-value.
     *
     * @see #getClose(int, int)
     */
    @Override
    public double getCloseValue(int series, int item) {
        double result = Double.NaN;
        Number c = getClose(series, item);
        if (c != null) {
            result = c.doubleValue();
        }
        return result;
    }

    /**
     * Returns the volume-value for one item in a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The volume-value.
     *
     * @see #getVolumeValue(int, int)
     */
    @Override
    public Number getVolume(int series, int item) {
        return this.volume[item];
    }

    /**
     * Returns the volume-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The volume-value.
     *
     * @see #getVolume(int, int)
     */
    @Override
    public double getVolumeValue(int series, int item) {
        double result = Double.NaN;
        Number v = getVolume(series, item);
        if (v != null) {
            result = v.doubleValue();
        }
        return result;
    }

    /**
     * Returns the number of series in the dataset.
     * <p>
     * This implementation only allows one series.
     *
     * @return The number of series.
     */
    @Override
    public int getSeriesCount() {
        return 1;
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param series  the index (zero-based) of the series.
     *
     * @return The number of items in the specified series.
     */
    @Override
    public int getItemCount(int series) {
        return this.date.length;
    }

    /**
     * Tests this dataset for equality with an arbitrary instance.
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
        if (!(obj instanceof DefaultHighLowDataset)) {
            return false;
        }
        DefaultHighLowDataset that = (DefaultHighLowDataset) obj;
        if (!this.seriesKey.equals(that.seriesKey)) {
            return false;
        }
        if (!Arrays.equals(this.date, that.date)) {
            return false;
        }
        if (!Arrays.equals(this.open, that.open)) {
            return false;
        }
        if (!Arrays.equals(this.high, that.high)) {
            return false;
        }
        if (!Arrays.equals(this.low, that.low)) {
            return false;
        }
        if (!Arrays.equals(this.close, that.close)) {
            return false;
        }
        if (!Arrays.equals(this.volume, that.volume)) {
            return false;
        }
        return true;
    }

    /**
     * Constructs an array of Number objects from an array of doubles.
     *
     * @param data  the double values to convert ({@code null} not
     *     permitted).
     *
     * @return The data as an array of Number objects.
     */
    public static Number[] createNumberArray(double[] data) {
        Number[] result = new Number[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }

}
