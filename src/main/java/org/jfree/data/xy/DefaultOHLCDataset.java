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
 * -----------------------
 * DefaultOHLCDataset.java
 * -----------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.util.Arrays;
import java.util.Date;
import org.jfree.chart.api.PublicCloneable;

/**
 * A simple implementation of the {@link OHLCDataset} interface.  This
 * implementation supports only one series.
 */
public class DefaultOHLCDataset extends AbstractXYDataset
        implements OHLCDataset, PublicCloneable {

    /** The series key. */
    private Comparable key;

    /** Storage for the data items. */
    private OHLCDataItem[] data;

    /**
     * Creates a new dataset.
     *
     * @param key  the series key.
     * @param data  the data items.
     */
    public DefaultOHLCDataset(Comparable key, OHLCDataItem[] data) {
        this.key = key;
        this.data = data;
    }

    /**
     * Returns the series key.
     *
     * @param series  the series index (ignored).
     *
     * @return The series key.
     */
    @Override
    public Comparable getSeriesKey(int series) {
        return this.key;
    }

    /**
     * Returns the x-value for a data item.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The x-value.
     */
    @Override
    public Number getX(int series, int item) {
        return this.data[item].getDate().getTime();
    }

    /**
     * Returns the x-value for a data item as a date.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The x-value as a date.
     */
    public Date getXDate(int series, int item) {
        return this.data[item].getDate();
    }

    /**
     * Returns the y-value.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The y value.
     */
    @Override
    public Number getY(int series, int item) {
        return getClose(series, item);
    }

    /**
     * Returns the high value.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The high value.
     */
    @Override
    public Number getHigh(int series, int item) {
        return this.data[item].getHigh();
    }

    /**
     * Returns the high-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The high-value.
     */
    @Override
    public double getHighValue(int series, int item) {
        double result = Double.NaN;
        Number high = getHigh(series, item);
        if (high != null) {
            result = high.doubleValue();
        }
        return result;
    }

    /**
     * Returns the low value.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The low value.
     */
    @Override
    public Number getLow(int series, int item) {
        return this.data[item].getLow();
    }

    /**
     * Returns the low-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The low-value.
     */
    @Override
    public double getLowValue(int series, int item) {
        double result = Double.NaN;
        Number low = getLow(series, item);
        if (low != null) {
            result = low.doubleValue();
        }
        return result;
    }

    /**
     * Returns the open value.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The open value.
     */
    @Override
    public Number getOpen(int series, int item) {
        return this.data[item].getOpen();
    }

    /**
     * Returns the open-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The open-value.
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
     * Returns the close value.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The close value.
     */
    @Override
    public Number getClose(int series, int item) {
        return this.data[item].getClose();
    }

    /**
     * Returns the close-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The close-value.
     */
    @Override
    public double getCloseValue(int series, int item) {
        double result = Double.NaN;
        Number close = getClose(series, item);
        if (close != null) {
            result = close.doubleValue();
        }
        return result;
    }

    /**
     * Returns the trading volume.
     *
     * @param series  the series index (ignored).
     * @param item  the item index (zero-based).
     *
     * @return The trading volume.
     */
    @Override
    public Number getVolume(int series, int item) {
        return this.data[item].getVolume();
    }

    /**
     * Returns the volume-value (as a double primitive) for an item within a
     * series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The volume-value.
     */
    @Override
    public double getVolumeValue(int series, int item) {
        double result = Double.NaN;
        Number volume = getVolume(series, item);
        if (volume != null) {
            result = volume.doubleValue();
        }
        return result;
    }

    /**
     * Returns the series count.
     *
     * @return 1.
     */
    @Override
    public int getSeriesCount() {
        return 1;
    }

    /**
     * Returns the item count for the specified series.
     *
     * @param series  the series index (ignored).
     *
     * @return The item count.
     */
    @Override
    public int getItemCount(int series) {
        return this.data.length;
    }

    /**
     * Sorts the data into ascending order by date.
     */
    public void sortDataByDate() {
        Arrays.sort(this.data);
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultOHLCDataset)) {
            return false;
        }
        DefaultOHLCDataset that = (DefaultOHLCDataset) obj;
        if (!this.key.equals(that.key)) {
            return false;
        }
        if (!Arrays.equals(this.data, that.data)) {
            return false;
        }
        return true;
    }

    /**
     * Returns an independent copy of this dataset.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a cloning problem.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultOHLCDataset clone = (DefaultOHLCDataset) super.clone();
        clone.data = new OHLCDataItem[this.data.length];
        System.arraycopy(this.data, 0, clone.data, 0, this.data.length);
        return clone;
    }

}
