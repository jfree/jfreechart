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
 * ---------------------------
 * MatrixSeriesCollection.java
 * ---------------------------
 * (C) Copyright 2003-2014, by Barak Naveh and Contributors.
 *
 * Original Author:  Barak Naveh;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 10-Jul-2003 : Version 1 contributed by Barak Naveh (DG);
 * 05-May-2004 : Now extends AbstractXYZDataset (DG);
 * 15-Jul-2004 : Switched getZ() and getZValue() methods (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 27-Nov-2006 : Added clone() override (DG);
 * 02-Feb-2007 : Removed author tags all over JFreeChart sources (DG);
 * 22-Apr-2008 : Implemented PublicCloneable (DG);
 */

package org.jfree.data.xy;

import java.io.Serializable;
import java.util.List;
import org.jfree.chart.util.ParamChecks;

import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

/**
 * Represents a collection of {@link MatrixSeries} that can be used as a
 * dataset.
 *
 * @see org.jfree.data.xy.MatrixSeries
 */
public class MatrixSeriesCollection extends AbstractXYZDataset
        implements XYZDataset, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -3197705779242543945L;

    /** The series that are included in the collection. */
    private List seriesList;

    /**
     * Constructs an empty dataset.
     */
    public MatrixSeriesCollection() {
        this(null);
    }


    /**
     * Constructs a dataset and populates it with a single matrix series.
     *
     * @param series the time series.
     */
    public MatrixSeriesCollection(MatrixSeries series) {
        this.seriesList = new java.util.ArrayList();

        if (series != null) {
            this.seriesList.add(series);
            series.addChangeListener(this);
        }
    }

    /**
     * Returns the number of items in the specified series.
     *
     * @param seriesIndex zero-based series index.
     *
     * @return The number of items in the specified series.
     */
    @Override
    public int getItemCount(int seriesIndex) {
        return getSeries(seriesIndex).getItemCount();
    }


    /**
     * Returns the series having the specified index.
     *
     * @param seriesIndex zero-based series index.
     *
     * @return The series.
     */
    public MatrixSeries getSeries(int seriesIndex) {
        if ((seriesIndex < 0) || (seriesIndex > getSeriesCount())) {
            throw new IllegalArgumentException("Index outside valid range.");
        }
        MatrixSeries series = (MatrixSeries) this.seriesList.get(seriesIndex);
        return series;
    }


    /**
     * Returns the number of series in the collection.
     *
     * @return The number of series in the collection.
     */
    @Override
    public int getSeriesCount() {
        return this.seriesList.size();
    }


    /**
     * Returns the key for a series.
     *
     * @param seriesIndex zero-based series index.
     *
     * @return The key for a series.
     */
    @Override
    public Comparable getSeriesKey(int seriesIndex) {
        return getSeries(seriesIndex).getKey();
    }


    /**
     * Returns the j index value of the specified Mij matrix item in the
     * specified matrix series.
     *
     * @param seriesIndex zero-based series index.
     * @param itemIndex zero-based item index.
     *
     * @return The j index value for the specified matrix item.
     *
     * @see org.jfree.data.xy.XYDataset#getXValue(int, int)
     */
    @Override
    public Number getX(int seriesIndex, int itemIndex) {
        MatrixSeries series = (MatrixSeries) this.seriesList.get(seriesIndex);
        int x = series.getItemColumn(itemIndex);

        return new Integer(x); // I know it's bad to create object. better idea?
    }


    /**
     * Returns the i index value of the specified Mij matrix item in the
     * specified matrix series.
     *
     * @param seriesIndex zero-based series index.
     * @param itemIndex zero-based item index.
     *
     * @return The i index value for the specified matrix item.
     *
     * @see org.jfree.data.xy.XYDataset#getYValue(int, int)
     */
    @Override
    public Number getY(int seriesIndex, int itemIndex) {
        MatrixSeries series = (MatrixSeries) this.seriesList.get(seriesIndex);
        int y = series.getItemRow(itemIndex);

        return new Integer(y); // I know it's bad to create object. better idea?
    }


    /**
     * Returns the Mij item value of the specified Mij matrix item in the
     * specified matrix series.
     *
     * @param seriesIndex the series (zero-based index).
     * @param itemIndex zero-based item index.
     *
     * @return The Mij item value for the specified matrix item.
     *
     * @see org.jfree.data.xy.XYZDataset#getZValue(int, int)
     */
    @Override
    public Number getZ(int seriesIndex, int itemIndex) {
        MatrixSeries series = (MatrixSeries) this.seriesList.get(seriesIndex);
        Number z = series.getItem(itemIndex);
        return z;
    }


    /**
     * Adds a series to the collection.
     * <P>
     * Notifies all registered listeners that the dataset has changed.
     * </p>
     *
     * @param series the series (<code>null</code> not permitted).
     */
    public void addSeries(MatrixSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        // FIXME: Check that there isn't already a series with the same key

        // add the series...
        this.seriesList.add(series);
        series.addChangeListener(this);
        fireDatasetChanged();
    }


    /**
     * Tests this collection for equality with an arbitrary object.
     *
     * @param obj the object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj instanceof MatrixSeriesCollection) {
            MatrixSeriesCollection c = (MatrixSeriesCollection) obj;

            return ObjectUtilities.equal(this.seriesList, c.seriesList);
        }

        return false;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return (this.seriesList != null ? this.seriesList.hashCode() : 0);
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        MatrixSeriesCollection clone = (MatrixSeriesCollection) super.clone();
        clone.seriesList = (List) ObjectUtilities.deepClone(this.seriesList);
        return clone;
    }

    /**
     * Removes all the series from the collection.
     * <P>
     * Notifies all registered listeners that the dataset has changed.
     * </p>
     */
    public void removeAllSeries() {
        // Unregister the collection as a change listener to each series in
        // the collection.
        for (int i = 0; i < this.seriesList.size(); i++) {
            MatrixSeries series = (MatrixSeries) this.seriesList.get(i);
            series.removeChangeListener(this);
        }

        // Remove all the series from the collection and notify listeners.
        this.seriesList.clear();
        fireDatasetChanged();
    }


    /**
     * Removes a series from the collection.
     * <P>
     * Notifies all registered listeners that the dataset has changed.
     * </p>
     *
     * @param series the series (<code>null</code>).
     */
    public void removeSeries(MatrixSeries series) {
        ParamChecks.nullNotPermitted(series, "series");
        if (this.seriesList.contains(series)) {
            series.removeChangeListener(this);
            this.seriesList.remove(series);
            fireDatasetChanged();
        }
    }


    /**
     * Removes a series from the collection.
     * <P>
     * Notifies all registered listeners that the dataset has changed.
     *
     * @param seriesIndex the series (zero based index).
     */
    public void removeSeries(int seriesIndex) {
        // check arguments...
        if ((seriesIndex < 0) || (seriesIndex > getSeriesCount())) {
            throw new IllegalArgumentException("Index outside valid range.");
        }

        // fetch the series, remove the change listener, then remove the series.
        MatrixSeries series = (MatrixSeries) this.seriesList.get(seriesIndex);
        series.removeChangeListener(this);
        this.seriesList.remove(seriesIndex);
        fireDatasetChanged();
    }

}
