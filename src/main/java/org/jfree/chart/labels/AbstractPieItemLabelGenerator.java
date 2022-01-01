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
 * ----------------------------------
 * AbstractPieItemLabelGenerator.java
 * ----------------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.MessageFormat;
import java.text.NumberFormat;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.Args;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.general.PieDataset;

/**
 * A base class used for generating pie chart item labels.
 */
public class AbstractPieItemLabelGenerator implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7347703325267846275L;

    /** The label format string. */
    private final String labelFormat;

    /** A number formatter for the value. */
    private NumberFormat numberFormat;

    /** A number formatter for the percentage. */
    private NumberFormat percentFormat;

    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param numberFormat  the format object for the values ({@code null}
     *                      not permitted).
     * @param percentFormat  the format object for the percentages
     *                       ({@code null} not permitted).
     */
    protected AbstractPieItemLabelGenerator(String labelFormat, 
            NumberFormat numberFormat, NumberFormat percentFormat) {
        Args.nullNotPermitted(labelFormat, "labelFormat");
        Args.nullNotPermitted(numberFormat, "numberFormat");
        Args.nullNotPermitted(percentFormat, "percentFormat");
        this.labelFormat = labelFormat;
        this.numberFormat = numberFormat;
        this.percentFormat = percentFormat;
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
     * @return The formatter (never {@code null}).
     */
    public NumberFormat getNumberFormat() {
        return this.numberFormat;
    }

    /**
     * Returns the percent formatter.
     *
     * @return The formatter (never {@code null}).
     */
    public NumberFormat getPercentFormat() {
        return this.percentFormat;
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.  The returned array
     * contains four values:
     * <ul>
     * <li>result[0] = the section key converted to a {@code String};</li>
     * <li>result[1] = the formatted data value;</li>
     * <li>result[2] = the formatted percentage (of the total);</li>
     * <li>result[3] = the formatted total value.</li>
     * </ul>
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the key ({@code null} not permitted).
     *
     * @return The items (never {@code null}).
     */
    protected Object[] createItemArray(PieDataset dataset, Comparable key) {
        Object[] result = new Object[4];
        double total = DatasetUtils.calculatePieDatasetTotal(dataset);
        result[0] = key.toString();
        Number value = dataset.getValue(key);
        if (value != null) {
            result[1] = this.numberFormat.format(value);
        }
        else {
            result[1] = "null";
        }
        double percent = 0.0;
        if (value != null) {
            double v = value.doubleValue();
            if (v > 0.0) {
                percent = v / total;
            }
        }
        result[2] = this.percentFormat.format(percent);
        result[3] = this.numberFormat.format(total);
        return result;
    }

    /**
     * Generates a label for a pie section.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the section key ({@code null} not permitted).
     *
     * @return The label (possibly {@code null}).
     */
    protected String generateSectionLabel(PieDataset dataset, Comparable key) {
        String result = null;
        if (dataset != null) {
            Object[] items = createItemArray(dataset, key);
            result = MessageFormat.format(this.labelFormat, items);
        }
        return result;
    }

    /**
     * Tests the generator for equality with an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AbstractPieItemLabelGenerator)) {
            return false;
        }

        AbstractPieItemLabelGenerator that
                = (AbstractPieItemLabelGenerator) obj;
        if (!this.labelFormat.equals(that.labelFormat)) {
            return false;
        }
        if (!this.numberFormat.equals(that.numberFormat)) {
            return false;
        }
        if (!this.percentFormat.equals(that.percentFormat)) {
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
        AbstractPieItemLabelGenerator clone
                = (AbstractPieItemLabelGenerator) super.clone();
        if (this.numberFormat != null) {
            clone.numberFormat = (NumberFormat) this.numberFormat.clone();
        }
        if (this.percentFormat != null) {
            clone.percentFormat = (NumberFormat) this.percentFormat.clone();
        }
        return clone;
    }

}
