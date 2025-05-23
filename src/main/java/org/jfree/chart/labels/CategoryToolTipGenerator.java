/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * -----------------------------
 * CategoryToolTipGenerator.java
 * -----------------------------
 * (C) Copyright 2001-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.category.CategoryDataset;

/**
 * A <i>category tool tip generator</i> is an object that can be assigned to a
 * {@link org.jfree.chart.renderer.category.CategoryItemRenderer} and that
 * assumes responsibility for creating text items to be used as tooltips for the
 * items in a {@link org.jfree.chart.plot.CategoryPlot}.
 * <p>
 * To assist with cloning charts, classes that implement this interface should
 * also implement the {@link PublicCloneable} interface.
 *
 * @param <R> the row key type.
 * @param <C> the column key type.
 */
public interface CategoryToolTipGenerator<R extends Comparable<R>, C extends Comparable<C>> {

    /**
     * Generates the tool tip text for an item in a dataset.  Note: in the
     * current dataset implementation, each row is a series, and each column
     * contains values for a particular category.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The tooltip text (possibly {@code null}).
     */
    String generateToolTip(CategoryDataset<R, C> dataset, int row, int column);

}
