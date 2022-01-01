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
 * ----------------
 * WindDataset.java
 * ----------------
 * (C) Copyright 2001-2020, by Achilleus Mantzios and Contributors.
 *
 * Original Author:  Achilleus Mantzios;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.data.xy;

/**
 * Interface for a dataset that supplies wind intensity and direction values
 * observed at various points in time.
 */
public interface WindDataset<S extends Comparable<S>> extends XYDataset<S> {

    /**
     * Returns the wind direction (should be in the range 0 to 12,
     * corresponding to the positions on an upside-down clock face).
     *
     * @param series  the series (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item (in the range {@code 0} to
     *     {@code getItemCount(series) - 1}).
     *
     * @return The wind direction.
     */
    Number getWindDirection(int series, int item);

    /**
     * Returns the wind force on the Beaufort scale (0 to 12).  See:
     * <p>
     * http://en.wikipedia.org/wiki/Beaufort_scale
     *
     * @param series  the series (in the range {@code 0} to
     *     {@code getSeriesCount() - 1}).
     * @param item  the item (in the range {@code 0} to
     *     {@code getItemCount(series) - 1}).
     *
     * @return The wind force.
     */
    Number getWindForce(int series, int item);

}
