/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------------------------
 * DefaultStatisticalCategoryDataset.java
 * --------------------------------------
 * (C) Copyright 2002-2007, by Pascal Collet and Contributors.
 *
 * Original Author:  Pascal Collet;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id: DefaultStatisticalCategoryDataset.java,v 1.8.2.4 2007/02/02 15:50:24 mungady Exp $
 *
 * Changes
 * -------
 * 21-Aug-2002 : Version 1, contributed by Pascal Collet (DG);
 * 07-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 05-Feb-2003 : Revised implementation to use KeyedObjects2D (DG);
 * 28-Aug-2003 : Moved from org.jfree.data --> org.jfree.data.statistics (DG);
 * 06-Oct-2003 : Removed incorrect Javadoc text (DG);
 * 18-Nov-2004 : Updated for changes in RangeInfo interface (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0 
 *               release (DG);
 * 01-Feb-2005 : Changed minimumRangeValue and maximumRangeValue from Double
 *               to double (DG);
 * 05-Feb-2005 : Implemented equals() method (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 08-Aug-2006 : Reworked implementation of RangeInfo methods (DG);
 * 02-Feb-2007 : Removed author tags from all over JFreeChart sources (DG);
 * 
 */

package org.jfree.data.statistics;

import java.util.List;

import org.jfree.data.KeyedObjects2D;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractDataset;

/**
 * A convenience class that provides a default implementation of the
 * {@link StatisticalCategoryDataset} interface.
 */
public class DefaultStatisticalCategoryDataset extends AbstractDataset
    implements StatisticalCategoryDataset, RangeInfo {

    /** Storage for the data. */
    private KeyedObjects2D data;

    /** The minimum range value. */
    private double minimumRangeValue;

    /** The minimum range value including the standard deviation. */
    private double minimumRangeValueIncStdDev;
    
    /** The maximum range value. */
    private double maximumRangeValue;

    /** The maximum range value including the standard deviation. */
    private double maximumRangeValueIncStdDev;

    /**
     * Creates a new dataset.
     */
    public DefaultStatisticalCategoryDataset() {
        this.data = new KeyedObjects2D();
        this.minimumRangeValue = Double.NaN;
        this.maximumRangeValue = Double.NaN;
        this.minimumRangeValueIncStdDev = Double.NaN;
        this.maximumRangeValueIncStdDev = Double.NaN;
    }

    /**
     * Returns the mean value for an item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The mean value.
     */
    public Number getMeanValue(int row, int column) {
        Number result = null;
        MeanAndStandardDeviation masd 
            = (MeanAndStandardDeviation) this.data.getObject(row, column);
        if (masd != null) {
            result = masd.getMean();
        }
        return result;
    }

    /**
     * Returns the value for an item (for this dataset, the mean value is
     * returned).
     *
     * @param row  the row index.
     * @param column  the column index.
     *
     * @return The value.
     */
    public Number getValue(int row, int column) {
        return getMeanValue(row, column);
    }

    /**
     * Returns the value for an item (for this dataset, the mean value is
     * returned).
     *
     * @param rowKey  the row key.
     * @param columnKey  the columnKey.
     *
     * @return The value.
     */
    public Number getValue(Comparable rowKey, Comparable columnKey) {
        return getMeanValue(rowKey, columnKey);
    }

    /**
     * Returns the mean value for an item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the columnKey.
     *
     * @return The mean value.
     */
    public Number getMeanValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        MeanAndStandardDeviation masd
            = (MeanAndStandardDeviation) this.data.getObject(rowKey, columnKey);
        if (masd != null) {
            result = masd.getMean();
        }
        return result;
    }

    /**
     * Returns the standard deviation value for an item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The standard deviation.
     */
    public Number getStdDevValue(int row, int column) {
        Number result = null;
        MeanAndStandardDeviation masd 
            = (MeanAndStandardDeviation) this.data.getObject(row, column);
        if (masd != null) {
            result = masd.getStandardDeviation();
        }
        return result;
    }

    /**
     * Returns the standard deviation value for an item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the columnKey.
     *
     * @return The standard deviation.
     */
    public Number getStdDevValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        MeanAndStandardDeviation masd
            = (MeanAndStandardDeviation) this.data.getObject(rowKey, columnKey);
        if (masd != null) {
            result = masd.getStandardDeviation();
        }
        return result;
    }

    /**
     * Returns the column index for a given key.
     *
     * @param key  the column key.
     *
     * @return The column index.
     */
    public int getColumnIndex(Comparable key) {
        return this.data.getColumnIndex(key);
    }

    /**
     * Returns a column key.
     *
     * @param column  the column index (zero-based).
     *
     * @return The column key.
     */
    public Comparable getColumnKey(int column) {
        return this.data.getColumnKey(column);
    }

    /**
     * Returns the column keys.
     *
     * @return The keys.
     */
    public List getColumnKeys() {
        return this.data.getColumnKeys();
    }

    /**
     * Returns the row index for a given key.
     *
     * @param key  the row key.
     *
     * @return The row index.
     */
    public int getRowIndex(Comparable key) {
        return this.data.getRowIndex(key);
    }

    /**
     * Returns a row key.
     *
     * @param row  the row index (zero-based).
     *
     * @return The row key.
     */
    public Comparable getRowKey(int row) {
        return this.data.getRowKey(row);
    }

    /**
     * Returns the row keys.
     *
     * @return The keys.
     */
    public List getRowKeys() {
        return this.data.getRowKeys();
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return The row count.
     */
    public int getRowCount() {
        return this.data.getRowCount();
    }

    /**
     * Returns the number of columns in the table.
     *
     * @return The column count.
     */
    public int getColumnCount() {
        return this.data.getColumnCount();
    }

    /**
     * Adds a mean and standard deviation to the table.
     *
     * @param mean  the mean.
     * @param standardDeviation  the standard deviation.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void add(double mean, double standardDeviation,
                    Comparable rowKey, Comparable columnKey) {
        add(new Double(mean), new Double(standardDeviation), rowKey, columnKey);
    }

    /**
     * Adds a mean and standard deviation to the table.
     *
     * @param mean  the mean.
     * @param standardDeviation  the standard deviation.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void add(Number mean, Number standardDeviation,
                    Comparable rowKey, Comparable columnKey) {
        MeanAndStandardDeviation item = new MeanAndStandardDeviation(
                mean, standardDeviation);
        this.data.addObject(item, rowKey, columnKey);
        double m = 0.0;
        double sd = 0.0;
        if (mean != null) {
            m = mean.doubleValue();
        }
        if (standardDeviation != null) {
            sd = standardDeviation.doubleValue();   
        }
        
        if (!Double.isNaN(m)) {
            if (Double.isNaN(this.maximumRangeValue) 
                    || m > this.maximumRangeValue) {
                this.maximumRangeValue = m;
            }
        }
        
        if (!Double.isNaN(m + sd)) {
            if (Double.isNaN(this.maximumRangeValueIncStdDev) 
                    || (m + sd) > this.maximumRangeValueIncStdDev) {
                this.maximumRangeValueIncStdDev = m + sd;
            }
        }

        if (!Double.isNaN(m)) {
            if (Double.isNaN(this.minimumRangeValue) 
                    || m < this.minimumRangeValue) {
                this.minimumRangeValue = m;
            }
        }

        if (!Double.isNaN(m - sd)) {
            if (Double.isNaN(this.minimumRangeValueIncStdDev) 
                    || (m - sd) < this.minimumRangeValueIncStdDev) {
                this.minimumRangeValueIncStdDev = m - sd;
            }
        }

        fireDatasetChanged();
    }

    /**
     * Returns the minimum y-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         y-interval is taken into account (ignored for
     *                         this dataset).
     * 
     * @return The minimum value.
     */
    public double getRangeLowerBound(boolean includeInterval) {
        return this.minimumRangeValue;        
    }

    /**
     * Returns the maximum y-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         y-interval is taken into account (ignored for
     *                         this dataset).
     * 
     * @return The maximum value.
     */
    public double getRangeUpperBound(boolean includeInterval) {
        return this.maximumRangeValue;        
    }

    /**
     * Returns the range of the values in this dataset's range.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         y-interval is taken into account.
     * 
     * @return The range.
     */
    public Range getRangeBounds(boolean includeInterval) {
        Range result = null;
        if (includeInterval) {
            if (!Double.isNaN(this.minimumRangeValueIncStdDev) 
                    && !Double.isNaN(this.maximumRangeValueIncStdDev))
            result = new Range(this.minimumRangeValueIncStdDev, 
                    this.maximumRangeValueIncStdDev);
        }
        else {
            if (!Double.isNaN(this.minimumRangeValue) 
                    && !Double.isNaN(this.maximumRangeValue))
            result = new Range(this.minimumRangeValue, this.maximumRangeValue);            
        }
        return result;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;   
        }
        if (!(obj instanceof DefaultStatisticalCategoryDataset)) {
            return false;   
        }
        DefaultStatisticalCategoryDataset that 
            = (DefaultStatisticalCategoryDataset) obj;
        if (!this.data.equals(that.data)) {
            return false;   
        }
        return true;
    }
}
