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
 * ----------------------------------------
 * DefaultBoxAndWhiskerCategoryDataset.java
 * ----------------------------------------
 * (C) Copyright 2003-2007, by David Browning and Contributors.
 *
 * Original Author:  David Browning (for Australian Institute of Marine 
 *                   Science);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 05-Aug-2003 : Version 1, contributed by David Browning (DG);
 * 27-Aug-2003 : Moved from org.jfree.data --> org.jfree.data.statistics (DG);
 * 12-Nov-2003 : Changed 'data' from private to protected and added a new 'add' 
 *               method as proposed by Tim Bardzil.  Also removed old code (DG);
 * 01-Mar-2004 : Added equals() method (DG);
 * 18-Nov-2004 : Updates for changes in RangeInfo interface (DG);
 * 11-Jan-2005 : Removed deprecated code in preparation for the 1.0.0 
 *               release (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Removed author tags from all over JFreeChart sources (DG);
 * 17-Apr-2007 : Fixed bug 1701822 (DG);
 * 13-Jun-2007 : Fixed error in previous patch (DG);
 * 28-Sep-2007 : Fixed cloning bug (DG);
 * 02-Oct-2007 : Fixed bug in updating cached bounds (DG);
 *
 */

package org.jfree.data.statistics;

import java.util.List;

import org.jfree.data.KeyedObjects2D;
import org.jfree.data.Range;
import org.jfree.data.RangeInfo;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

/**
 * A convenience class that provides a default implementation of the
 * {@link BoxAndWhiskerCategoryDataset} interface.
 */
public class DefaultBoxAndWhiskerCategoryDataset extends AbstractDataset
        implements BoxAndWhiskerCategoryDataset, RangeInfo, PublicCloneable {

    /** Storage for the data. */
    protected KeyedObjects2D data;

    /** The minimum range value. */
    private double minimumRangeValue;
    
    /** The row index for the cell that the minimum range value comes from. */
    private int minimumRangeValueRow;
    
    /** 
     * The column index for the cell that the minimum range value comes from. 
     */
    private int minimumRangeValueColumn;

    /** The maximum range value. */
    private double maximumRangeValue;

    /** The row index for the cell that the maximum range value comes from. */
    private int maximumRangeValueRow;
    
    /** 
     * The column index for the cell that the maximum range value comes from. 
     */
    private int maximumRangeValueColumn;
    
    /** The range of values. */
    private Range rangeBounds;

    /**
     * Creates a new dataset.
     */
    public DefaultBoxAndWhiskerCategoryDataset() {
        this.data = new KeyedObjects2D();
        this.minimumRangeValue = Double.NaN;
        this.minimumRangeValueRow = -1;
        this.minimumRangeValueColumn = -1;
        this.maximumRangeValue = Double.NaN;
        this.maximumRangeValueRow = -1;
        this.maximumRangeValueColumn = -1;
        this.rangeBounds = new Range(0.0, 0.0);
    }

    /**
     * Adds a list of values relating to one box-and-whisker entity to the 
     * table.  The various median values are calculated.
     *
     * @param list  a collection of values from which the various medians will 
     *              be calculated.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void add(List list, Comparable rowKey, Comparable columnKey) {
        BoxAndWhiskerItem item 
            = BoxAndWhiskerCalculator.calculateBoxAndWhiskerStatistics(list);
        add(item, rowKey, columnKey);
    }
    
    /**
     * Adds a list of values relating to one Box and Whisker entity to the 
     * table.  The various median values are calculated.
     *
     * @param item  a box and whisker item (<code>null</code> not permitted).
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void add(BoxAndWhiskerItem item, 
                    Comparable rowKey, 
                    Comparable columnKey) {

        this.data.addObject(item, rowKey, columnKey);
        
        // update cached min and max values
        int r = this.data.getRowIndex(rowKey);
        int c = this.data.getColumnIndex(columnKey);
        if (this.maximumRangeValueRow == r 
                && this.maximumRangeValueColumn == c) {
            this.maximumRangeValue = Double.NaN;
        }
        if (this.minimumRangeValueRow == r 
                && this.minimumRangeValueColumn == c) {
            this.minimumRangeValue = Double.NaN;
        }
        
        
        double minval = Double.NaN;
        if (item.getMinOutlier() != null) {
            minval = item.getMinOutlier().doubleValue();
        }
        double maxval = Double.NaN;
        if (item.getMaxOutlier() != null) {
            maxval = item.getMaxOutlier().doubleValue();
        }
        
        if (Double.isNaN(this.maximumRangeValue)) {
            this.maximumRangeValue = maxval;
            this.maximumRangeValueRow = r;
            this.maximumRangeValueColumn = c;
        }
        else if (maxval > this.maximumRangeValue) {
            this.maximumRangeValue = maxval;
            this.maximumRangeValueRow = r;
            this.maximumRangeValueColumn = c;
        }
        
        if (Double.isNaN(this.minimumRangeValue)) {
            this.minimumRangeValue = minval;
            this.minimumRangeValueRow = r;
            this.minimumRangeValueColumn = c;
        }
        else if (minval < this.minimumRangeValue) {
            this.minimumRangeValue = minval;
            this.minimumRangeValueRow = r;
            this.minimumRangeValueColumn = c;
        }
        
        this.rangeBounds = new Range(this.minimumRangeValue,
              this.maximumRangeValue);

        fireDatasetChanged();

    }

    /**
     * Return an item from within the dataset.
     * 
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The item.
     */
    public BoxAndWhiskerItem getItem(int row, int column) {
        return (BoxAndWhiskerItem) this.data.getObject(row, column);  
    }

    /**
     * Returns the value for an item.
     *
     * @param row  the row index.
     * @param column  the column index.
     *
     * @return The value.
     * 
     * @see #getMedianValue(int, int)
     * @see #getValue(Comparable, Comparable)
     */
    public Number getValue(int row, int column) {
        return getMedianValue(row, column);
    }

    /**
     * Returns the value for an item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the columnKey.
     *
     * @return The value.
     * 
     * @see #getMedianValue(Comparable, Comparable)
     * @see #getValue(int, int)
     */
    public Number getValue(Comparable rowKey, Comparable columnKey) {
        return getMedianValue(rowKey, columnKey);
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
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(row, 
                column);
        if (item != null) {
            result = item.getMean();
        }
        return result;

    }

    /**
     * Returns the mean value for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The mean value.
     */
    public Number getMeanValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMean();
        }
        return result;
    }

    /**
     * Returns the median value for an item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The median value.
     */
    public Number getMedianValue(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(row, 
                column);
        if (item != null) {
            result = item.getMedian();
        }
        return result;
    }

    /**
     * Returns the median value for an item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the columnKey.
     *
     * @return The median value.
     */
    public Number getMedianValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMedian();
        }
        return result;
    }

    /**
     * Returns the first quartile value.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The first quartile value.
     */
    public Number getQ1Value(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getQ1();
        }
        return result;
    }

    /**
     * Returns the first quartile value.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The first quartile value.
     */
    public Number getQ1Value(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getQ1();
        }
        return result;
    }

    /**
     * Returns the third quartile value.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The third quartile value.
     */
    public Number getQ3Value(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getQ3();
        }
        return result;
    }

    /**
     * Returns the third quartile value.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The third quartile value.
     */
    public Number getQ3Value(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getQ3();
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
     * Returns the minimum y-value in the dataset.
     *
     * @param includeInterval  a flag that determines whether or not the
     *                         y-interval is taken into account.
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
     *                         y-interval is taken into account.
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
        return this.rangeBounds;
    }
    
    /**
     * Returns the minimum regular (non outlier) value for an item.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The minimum regular value.
     */
    public Number getMinRegularValue(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getMinRegularValue();
        }
        return result;
    }

    /**
     * Returns the minimum regular (non outlier) value for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The minimum regular value.
     */
    public Number getMinRegularValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMinRegularValue();
        }
        return result;
    }

    /**
     * Returns the maximum regular (non outlier) value for an item.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The maximum regular value.
     */
    public Number getMaxRegularValue(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getMaxRegularValue();
        }
        return result;
    }

    /**
     * Returns the maximum regular (non outlier) value for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The maximum regular value.
     */
    public Number getMaxRegularValue(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMaxRegularValue();
        }
        return result;
    }

    /**
     * Returns the minimum outlier (non farout) value for an item.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The minimum outlier.
     */
    public Number getMinOutlier(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getMinOutlier();
        }
        return result;
    }

    /**
     * Returns the minimum outlier (non farout) value for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The minimum outlier.
     */
    public Number getMinOutlier(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMinOutlier();
        }
        return result;
    }

    /**
     * Returns the maximum outlier (non farout) value for an item.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return The maximum outlier.
     */
    public Number getMaxOutlier(int row, int column) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getMaxOutlier();
        }
        return result;
    }

    /**
     * Returns the maximum outlier (non farout) value for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return The maximum outlier.
     */
    public Number getMaxOutlier(Comparable rowKey, Comparable columnKey) {
        Number result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getMaxOutlier();
        }
        return result;
    }

    /**
     * Returns a list of outlier values for an item.
     * 
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * 
     * @return A list of outlier values.
     */
    public List getOutliers(int row, int column) {
        List result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                row, column);
        if (item != null) {
            result = item.getOutliers();
        }
        return result;
    }

    /**
     * Returns a list of outlier values for an item.
     * 
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * 
     * @return A list of outlier values.
     */
    public List getOutliers(Comparable rowKey, Comparable columnKey) {
        List result = null;
        BoxAndWhiskerItem item = (BoxAndWhiskerItem) this.data.getObject(
                rowKey, columnKey);
        if (item != null) {
            result = item.getOutliers();
        }
        return result;
    }
    
    /**
     * Tests this dataset for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;   
        }
        if (obj instanceof DefaultBoxAndWhiskerCategoryDataset) {
            DefaultBoxAndWhiskerCategoryDataset dataset 
                    = (DefaultBoxAndWhiskerCategoryDataset) obj;
            return ObjectUtilities.equal(this.data, dataset.data);
        }
        return false;
    }
    
    /**
     * Returns a clone of this dataset.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException if cloning is not possible.
     */
    public Object clone() throws CloneNotSupportedException {
        DefaultBoxAndWhiskerCategoryDataset clone 
                = (DefaultBoxAndWhiskerCategoryDataset) super.clone();
        clone.data = (KeyedObjects2D) this.data.clone();
        return clone;
    }

}
