/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * ------------------------------
 * MultiValueCategoryDataset.java
 * ------------------------------
 * (C) Copyright 2007-2020, by David Forslund and Contributors.
 *
 * Original Author:  David Forslund;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 */

package org.jfree.data.statistics;

import java.util.List;

import org.jfree.data.category.CategoryDataset;

/**
 * A category dataset that defines multiple values for each item.
 *
 * @since 1.0.7
 */
public interface MultiValueCategoryDataset extends CategoryDataset {

    /**
     * Returns a list (possibly empty) of the values for the specified item.
     * The returned list should be unmodifiable.
     *
     * @param row  the row index (zero-based).
     * @param column   the column index (zero-based).
     *
     * @return The list of values.
     */
    public List getValues(int row, int column);

    /**
     * Returns a list (possibly empty) of the values for the specified item.
     * The returned list should be unmodifiable.
     *
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     *
     * @return The list of values.
     */
    public List getValues(Comparable rowKey, Comparable columnKey);

}