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
 * BoxAndWhiskerXYDataset.java
 * ---------------------------
 * (C) Copyright 2003-2008, by David Browning and Contributors.
 *
 * Original Author:  David Browning (for Australian Institute of Marine
 *                   Science);
 * Contributor(s):   David Gilbert;
 *
 * Changes
 * -------
 * 05-Aug-2003 : Version 1, contributed by David Browning (DG);
 * 12-Aug-2003 : Added new methods: getMaxNonOutlierValue
 *                                  getMaxNonFaroutValue
 *                                  getOutlierCoefficient
 *                                  setOutlierCoefficient
 *                                  getFaroutCoefficient
 *                                  setFaroutCoefficient
 *                                  getInterquartileRange (DB)
 * 27-Aug-2003 : Renamed BoxAndWhiskerDataset --> BoxAndWhiskerXYDataset, and
 *               cut down methods (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 02-Feb-2007 : Removed author tags from all over JFreeChart sources (DG);
 *
 */

package org.jfree.data.statistics;

import java.util.List;

import org.jfree.data.xy.XYDataset;

/**
 * An interface that defines data in the form of (x, max, min, average, median)
 * tuples.
 * <P>
 * Example: JFreeChart uses this interface to obtain data for AIMS
 * max-min-average-median plots.
 */
public interface BoxAndWhiskerXYDataset<S extends Comparable<S>> 
        extends XYDataset<S> {

    /**
     * Returns the mean for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The mean for the specified series and item.
     */
    Number getMeanValue(int series, int item);

    /**
     * Returns the median-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The median-value for the specified series and item.
     */
    Number getMedianValue(int series, int item);

    /**
     * Returns the Q1 median-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The Q1 median-value for the specified series and item.
     */
    Number getQ1Value(int series, int item);

    /**
     * Returns the Q3 median-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The Q3 median-value for the specified series and item.
     */
    Number getQ3Value(int series, int item);

    /**
     * Returns the min-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The min-value for the specified series and item.
     */
    Number getMinRegularValue(int series, int item);

    /**
     * Returns the max-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The max-value for the specified series and item.
     */
    Number getMaxRegularValue(int series, int item);

    /**
     * Returns the minimum value which is not a farout.
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return A {@code Number} representing the maximum non-farout value.
     */
    Number getMinOutlier(int series, int item);

    /**
     * Returns the maximum value which is not a farout, ie Q3 + (interquartile
     * range * farout coefficient).
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return A {@code Number} representing the maximum non-farout value.
     */
    Number getMaxOutlier(int series, int item);

    /**
     * Returns a list of outliers for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The list of outliers for the specified series and item
     *         (possibly {@code null}).
     */
    List getOutliers(int series, int item);

    /**
     * Returns the value used as the outlier coefficient. The outlier
     * coefficient gives an indication of the degree of certainty in an
     * unskewed distribution.  Increasing the coefficient increases the number
     * of values included.  Currently only used to ensure farout coefficient
     * is greater than the outlier coefficient
     *
     * @return A {@code double} representing the value used to calculate
     *         outliers
     */
    double getOutlierCoefficient();

    /**
     * Returns the value used as the farout coefficient. The farout coefficient
     * allows the calculation of which values will be off the graph.
     *
     * @return A {@code double} representing the value used to calculate
     *         farouts
     */
    double getFaroutCoefficient();

}
