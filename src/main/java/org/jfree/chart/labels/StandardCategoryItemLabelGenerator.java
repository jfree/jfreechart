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
 * ---------------------------------------
 * StandardCategoryItemLabelGenerator.java
 * ---------------------------------------
 * (C) Copyright 2004-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.category.CategoryDataset;

/**
 * A standard label generator that can be used with a
 * {@link org.jfree.chart.renderer.category.CategoryItemRenderer}.
 *
 * @param <R> the row key type.
 * @param <C> the column key type.
 */
public class StandardCategoryItemLabelGenerator<R extends Comparable<R>, C extends Comparable<C>>
    extends AbstractCategoryItemLabelGenerator<R, C>
    implements CategoryItemLabelGenerator<R, C>, Cloneable, PublicCloneable,
               Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3499701401211412882L;

    /** The default format string. */
    public static final String DEFAULT_LABEL_FORMAT_STRING = "{2}";

    /**
     * Creates a new generator with a default number formatter.
     */
    public StandardCategoryItemLabelGenerator() {
        super(DEFAULT_LABEL_FORMAT_STRING, NumberFormat.getInstance());
    }

    /**
     * Creates a new generator with the specified number formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the number formatter ({@code null} not permitted).
     */
    public StandardCategoryItemLabelGenerator(String labelFormat,
            NumberFormat formatter) {
        super(labelFormat, formatter);
    }

    /**
     * Creates a new generator with the specified number formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the number formatter ({@code null} not permitted).
     * @param percentFormatter  the percent formatter ({@code null} not
     *     permitted).
     */
    public StandardCategoryItemLabelGenerator(String labelFormat,
            NumberFormat formatter, NumberFormat percentFormatter) {
        super(labelFormat, formatter, percentFormatter);
    }

    /**
     * Creates a new generator with the specified date formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the date formatter ({@code null} not permitted).
     */
    public StandardCategoryItemLabelGenerator(String labelFormat,
                                              DateFormat formatter) {
        super(labelFormat, formatter);
    }

    /**
     * Generates the label for an item in a dataset.  Note: in the current
     * dataset implementation, each row is a series, and each column contains
     * values for a particular category.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The label (possibly {@code null}).
     */
    @Override
    public String generateLabel(CategoryDataset<R, C> dataset, int row, int column) {
        return generateLabelString(dataset, row, column);
    }

    /**
     * Tests this generator for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} if this generator is equal to
     *     {@code obj}, and {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardCategoryItemLabelGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
