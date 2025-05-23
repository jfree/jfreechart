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
 * AbstractCategoryItemLabelGenerator.java
 * ---------------------------------------
 * (C) Copyright 2005-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Objects;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.data.DataUtils;
import org.jfree.data.category.CategoryDataset;

/**
 * A base class that can be used to create a label or tooltip generator that
 * can be assigned to a
 * {@link org.jfree.chart.renderer.category.CategoryItemRenderer}.
 *
 * @param <R> the row key type.
 * @param <C> the column key type.
 */
public abstract class AbstractCategoryItemLabelGenerator<R extends Comparable<R>, C extends Comparable<C>>
        implements PublicCloneable, Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -7108591260223293197L;

    /**
     * The label format string used by a {@code MessageFormat} object to
     * combine the standard items:  {0} = series name, {1} = category,
     * {2} = value, {3} = value as a percentage of the column total.
     */
    private final String labelFormat;

    /** The string used to represent a null value. */
    private final String nullValueString;

    /**
     * A number formatter used to preformat the value before it is passed to
     * the MessageFormat object.
     */
    private NumberFormat numberFormat;

    /**
     * A date formatter used to preformat the value before it is passed to the
     * MessageFormat object.
     */
    private DateFormat dateFormat;

    /**
     * A number formatter used to preformat the percentage value before it is
     * passed to the MessageFormat object.
     */
    private final NumberFormat percentFormat;

    /**
     * Creates a label generator with the specified number formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the number formatter ({@code null} not permitted).
     */
    protected AbstractCategoryItemLabelGenerator(String labelFormat,
            NumberFormat formatter) {
        this(labelFormat, formatter, NumberFormat.getPercentInstance());
    }

    /**
     * Creates a label generator with the specified number formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the number formatter ({@code null} not permitted).
     * @param percentFormatter  the percent formatter ({@code null} not
     *     permitted).
     */
    protected AbstractCategoryItemLabelGenerator(String labelFormat,
            NumberFormat formatter, NumberFormat percentFormatter) {
        Args.nullNotPermitted(labelFormat, "labelFormat");
        Args.nullNotPermitted(formatter, "formatter");
        Args.nullNotPermitted(percentFormatter, "percentFormatter");
        this.labelFormat = labelFormat;
        this.numberFormat = formatter;
        this.percentFormat = percentFormatter;
        this.dateFormat = null;
        this.nullValueString = "-";
    }

    /**
     * Creates a label generator with the specified date formatter.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param formatter  the date formatter ({@code null} not permitted).
     */
    protected AbstractCategoryItemLabelGenerator(String labelFormat,
            DateFormat formatter) {
        Args.nullNotPermitted(labelFormat, "labelFormat");
        Args.nullNotPermitted(formatter, "formatter");
        this.labelFormat = labelFormat;
        this.numberFormat = null;
        this.percentFormat = NumberFormat.getPercentInstance();
        this.dateFormat = formatter;
        this.nullValueString = "-";
    }

    /**
     * Generates a label for the specified row.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param row  the row index (zero-based).
     *
     * @return The label.
     */
    public String generateRowLabel(CategoryDataset<R, C> dataset, int row) {
        return dataset.getRowKey(row).toString();
    }

    /**
     * Generates a label for the specified row.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param column  the column index (zero-based).
     *
     * @return The label.
     */
    public String generateColumnLabel(CategoryDataset<R, C> dataset, int column) {
        return dataset.getColumnKey(column).toString();
    }

    /**
     * Returns the label format string.
     *
     * @return The label format string (never {@code null}).
     */
    public String getLabelFormat() {
        return this.labelFormat;
    }

    /**
     * Returns the number formatter.
     *
     * @return The number formatter (possibly {@code null}).
     */
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    /**
     * Returns the date formatter.
     *
     * @return The date formatter (possibly {@code null}).
     */
    public DateFormat getDateFormat() {
        return this.dateFormat;
    }

    /**
     * Generates a for the specified item.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The label (possibly {@code null}).
     */
    protected String generateLabelString(CategoryDataset<R, C> dataset,
                                         int row, int column) {
        Args.nullNotPermitted(dataset, "dataset");
        String result;
        Object[] items = createItemArray(dataset, row, column);
        result = MessageFormat.format(this.labelFormat, items);
        return result;

    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The items (never {@code null}).
     */
    protected Object[] createItemArray(CategoryDataset<R, C> dataset,
                                       int row, int column) {
        Object[] result = new Object[4];
        result[0] = dataset.getRowKey(row).toString();
        result[1] = dataset.getColumnKey(column).toString();
        Number value = dataset.getValue(row, column);
        if (value != null) {
            if (this.numberFormat != null) {
                result[2] = this.numberFormat.format(value);
            }
            else if (this.dateFormat != null) {
                result[2] = this.dateFormat.format(value);
            }
        }
        else {
            result[2] = this.nullValueString;
        }
        if (value != null) {
            double total = DataUtils.calculateColumnTotal(dataset, column);
            double percent = value.doubleValue() / total;
            result[3] = this.percentFormat.format(percent);
        }

        return result;
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the other object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractCategoryItemLabelGenerator)) {
            return false;
        }

        AbstractCategoryItemLabelGenerator<R, C> that
            = (AbstractCategoryItemLabelGenerator<R, C>) obj;
        if (!this.labelFormat.equals(that.labelFormat)) {
            return false;
        }
        if (!Objects.equals(this.dateFormat, that.dateFormat)) {
            return false;
        }
        if (!Objects.equals(this.numberFormat, that.numberFormat)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 127;
        result = HashUtils.hashCode(result, this.labelFormat);
        result = HashUtils.hashCode(result, this.nullValueString);
        result = HashUtils.hashCode(result, this.dateFormat);
        result = HashUtils.hashCode(result, this.numberFormat);
        result = HashUtils.hashCode(result, this.percentFormat);
        return result;
    }

    /**
     * Returns an independent copy of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  should not happen.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        AbstractCategoryItemLabelGenerator<R, C> clone
            = (AbstractCategoryItemLabelGenerator<R, C>) super.clone();
        if (this.numberFormat != null) {
            clone.numberFormat = (NumberFormat) this.numberFormat.clone();
        }
        if (this.dateFormat != null) {
            clone.dateFormat = (DateFormat) this.dateFormat.clone();
        }
        return clone;
    }

}
