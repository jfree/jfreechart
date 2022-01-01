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
 * AbstractXYZDataset.java
 * -----------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert.
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

/**
 * An base class that you can use to create new implementations of the
 * {@link XYZDataset} interface.
 */
public abstract class AbstractXYZDataset<S extends Comparable<S>> 
        extends AbstractXYDataset<S> implements XYZDataset<S> {

    /**
     * Returns the z-value (as a double primitive) for an item within a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The z-value.
     */
    @Override
    public double getZValue(int series, int item) {
        double result = Double.NaN;
        Number z = getZ(series, item);
        if (z != null) {
            result = z.doubleValue();
        }
        return result;
    }

}
