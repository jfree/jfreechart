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
 * --------------------
 * VectorXYDataset.java
 * --------------------
 * (C) Copyright 2007-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

/**
 * An extension of the {@link XYDataset} interface that allows a vector to be
 * defined at a specific (x, y) location.
 */
public interface VectorXYDataset<S extends Comparable<S>> extends XYDataset<S> {

    /**
     * Returns the x-component of the vector for an item in a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The x-component of the vector.
     */
    double getVectorXValue(int series, int item);

    /**
     * Returns the y-component of the vector for an item in a series.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The y-component of the vector.
     */
    double getVectorYValue(int series, int item);

    /**
     * Returns the vector for an item in a series.  Depending on the particular
     * dataset implementation, this may involve creating a new {@link Vector}
     * instance --- if you are just interested in the x and y components,
     * use the {@link #getVectorXValue(int, int)} and
     * {@link #getVectorYValue(int, int)} methods instead.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The vector (possibly {@code null}).
     */
    Vector getVector(int series, int item);

}
