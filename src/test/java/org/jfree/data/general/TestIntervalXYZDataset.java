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
 * TestIntervalXYZDataset.java
 * ---------------------------
 * (C) Copyright 2020-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.general;

import org.jfree.data.DomainOrder;
import org.jfree.data.xy.IntervalXYZDataset;

/**
 * An implementation of the {@link IntervalXYZDataset} interface that can be
 * used for testing.
 */
public class TestIntervalXYZDataset implements IntervalXYZDataset<Integer> {
    
    private final int seriesCount;
    
    private final int itemCount;
    
    /**
     * Creates a new instance.
     * 
     * @param seriesCount  the number of series.
     * @param itemCount  the number of items per series.
     */
    public TestIntervalXYZDataset(int seriesCount, int itemCount) {
        this.seriesCount = seriesCount;
        this.itemCount = itemCount;
    }
    
    @Override
    public Number getStartXValue(int series, int item) {
        return getXValue(series, item) - 0.5;
    }

    @Override
    public Number getEndXValue(int series, int item) {
        return getXValue(series, item) + 0.5;
    }

    @Override
    public Number getStartYValue(int series, int item) {
        return getYValue(series, item) - 1.5;
    }

    @Override
    public Number getEndYValue(int series, int item) {
        return getYValue(series, item) + 1.5;
    }

    @Override
    public Number getStartZValue(int series, int item) {
        return getZValue(series, item) - 2.5;
    }

    @Override
    public Number getEndZValue(int series, int item) {
        return getZValue(series, item) + 2.5;
    }

    @Override
    public Number getZ(int series, int item) {
        return getZValue(series, item);
    }

    @Override
    public double getZValue(int series, int item) {
        return 3 * getXValue(series, item);
    }

    @Override
    public DomainOrder getDomainOrder() {
        return DomainOrder.ASCENDING;
    }

    @Override
    public int getItemCount(int series) {
        // for this test dataset, every series has the same number of items
        return this.itemCount; 
    }

    @Override
    public Number getX(int series, int item) {
        return getXValue(series, item);
    }

    @Override
    public double getXValue(int series, int item) {
        return series * this.itemCount + item;
    }

    @Override
    public Number getY(int series, int item) {
        return getYValue(series, item);
    }

    @Override
    public double getYValue(int series, int item) {
        return 2 * getXValue(series, item);
    }

    @Override
    public int getSeriesCount() {
        return this.seriesCount;
    }

    @Override
    public Integer getSeriesKey(int series) {
        return series;
    }

    @Override
    public int indexOf(Integer seriesKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addChangeListener(DatasetChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void removeChangeListener(DatasetChangeListener listener) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
