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
 * -------------------------------------
 * StandardCategoryToolTipGenerator.java
 * -------------------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;

import org.jfree.data.category.CategoryDataset;

/**
 * A standard tool tip generator that can be used with a
 * {@link org.jfree.chart.renderer.category.CategoryItemRenderer}.
 */
public class StandardCategoryToolTipGenerator
        extends AbstractCategoryItemLabelGenerator
        implements CategoryToolTipGenerator, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -6768806592218710764L;

    /** The default format string. */
    public static final String DEFAULT_TOOL_TIP_FORMAT_STRING
            = "({0}, {1}) = {2}";

    /**
     * Creates a new generator with a default number formatter.
     */
    public StandardCategoryToolTipGenerator() {
        super(DEFAULT_TOOL_TIP_FORMAT_STRING, NumberFormat.getInstance());
    }

    /**
     * Creates a new generator with the specified number formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the number formatter ({@code null} not permitted).
     */
    public StandardCategoryToolTipGenerator(String labelFormat,
            NumberFormat formatter) {
        super(labelFormat, formatter);
    }

    /**
     * Creates a new generator with the specified date formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the date formatter ({@code null} not permitted).
     */
    public StandardCategoryToolTipGenerator(String labelFormat,
            DateFormat formatter) {
        super(labelFormat, formatter);
    }

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
    @Override
    public String generateToolTip(CategoryDataset dataset,
                                  int row, int column) {
        return generateLabelString(dataset, row, column);
    }

    /**
     * Tests this generator for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryToolTipGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

}
