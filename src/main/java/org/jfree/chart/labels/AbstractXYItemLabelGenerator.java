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
 * ---------------------------------
 * AbstractXYItemLabelGenerator.java
 * ---------------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
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
import java.util.Date;
import java.util.Objects;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.Args;
import org.jfree.data.xy.XYDataset;

/**
 * A base class for creating item label generators.
 */
public class AbstractXYItemLabelGenerator implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 5869744396278660636L;

    /** The item label format string. */
    private final String formatString;

    /** A number formatter for the x value. */
    private NumberFormat xFormat;

    /** A date formatter for the x value. */
    private DateFormat xDateFormat;

    /** A formatter for the y value. */
    private NumberFormat yFormat;

    /** A date formatter for the y value. */
    private DateFormat yDateFormat;

    /** The string used to represent 'null' for the y-value. */
    private String nullYString = "null";

    /**
     * Creates an item label generator using default number formatters.
     */
    protected AbstractXYItemLabelGenerator() {
        this("{2}", NumberFormat.getNumberInstance(),
                NumberFormat.getNumberInstance());
    }

    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    protected AbstractXYItemLabelGenerator(String formatString, 
            NumberFormat xFormat, NumberFormat yFormat) {

        Args.nullNotPermitted(formatString, "formatString");
        Args.nullNotPermitted(xFormat, "xFormat");
        Args.nullNotPermitted(yFormat, "yFormat");
        this.formatString = formatString;
        this.xFormat = xFormat;
        this.yFormat = yFormat;
    }

    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    protected AbstractXYItemLabelGenerator(String formatString, 
            DateFormat xFormat, NumberFormat yFormat) {

        this(formatString, NumberFormat.getInstance(), yFormat);
        this.xDateFormat = xFormat;
    }

    /**
     * Creates an item label generator using the specified formatters (a
     * number formatter for the x-values and a date formatter for the
     * y-values).
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    protected AbstractXYItemLabelGenerator(String formatString,
            NumberFormat xFormat, DateFormat yFormat) {

        this(formatString, xFormat, NumberFormat.getInstance());
        this.yDateFormat = yFormat;
    }

    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    protected AbstractXYItemLabelGenerator(String formatString, 
            DateFormat xFormat, DateFormat yFormat) {

        this(formatString, NumberFormat.getInstance(),
                NumberFormat.getInstance());
        this.xDateFormat = xFormat;
        this.yDateFormat = yFormat;
    }

    /**
     * Returns the format string (this controls the overall structure of the
     * label).
     *
     * @return The format string (never {@code null}).
     */
    public String getFormatString() {
        return this.formatString;
    }

    /**
     * Returns the number formatter for the x-values.
     *
     * @return The number formatter (possibly {@code null}).
     */
    public NumberFormat getXFormat() {
        return this.xFormat;
    }

    /**
     * Returns the date formatter for the x-values.
     *
     * @return The date formatter (possibly {@code null}).
     */
    public DateFormat getXDateFormat() {
        return this.xDateFormat;
    }

    /**
     * Returns the number formatter for the y-values.
     *
     * @return The number formatter (possibly {@code null}).
     */
    public NumberFormat getYFormat() {
        return this.yFormat;
    }

    /**
     * Returns the date formatter for the y-values.
     *
     * @return The date formatter (possibly {@code null}).
     */
    public DateFormat getYDateFormat() {
        return this.yDateFormat;
    }

    /**
     * Generates a label string for an item in the dataset.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The label (possibly {@code null}).
     */
    public String generateLabelString(XYDataset dataset, int series, int item) {
        String result;
        Object[] items = createItemArray(dataset, series, item);
        result = MessageFormat.format(this.formatString, items);
        return result;
    }

    /**
     * Returns the string representing a null value.
     *
     * @return The string representing a null value.
     */
    public String getNullYString() {
        return this.nullYString;
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return An array of three items from the dataset formatted as
     *         {@code String} objects (never {@code null}).
     */
    protected Object[] createItemArray(XYDataset dataset, int series,
                                       int item) {
        Object[] result = new Object[3];
        result[0] = dataset.getSeriesKey(series).toString();

        double x = dataset.getXValue(series, item);
        if (this.xDateFormat != null) {
            result[1] = this.xDateFormat.format(new Date((long) x));
        }
        else {
            result[1] = this.xFormat.format(x);
        }

        double y = dataset.getYValue(series, item);
        if (Double.isNaN(y) && dataset.getY(series, item) == null) {
            result[2] = this.nullYString;
        }
        else {
            if (this.yDateFormat != null) {
                result[2] = this.yDateFormat.format(new Date((long) y));
            }
            else {
                result[2] = this.yFormat.format(y);
            }
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
        if (!(obj instanceof AbstractXYItemLabelGenerator)) {
            return false;
        }
        AbstractXYItemLabelGenerator that = (AbstractXYItemLabelGenerator) obj;
        if (!this.formatString.equals(that.formatString)) {
            return false;
        }
        if (!Objects.equals(this.xFormat, that.xFormat)) {
            return false;
        }
        if (!Objects.equals(this.xDateFormat, that.xDateFormat)) {
            return false;
        }
        if (!Objects.equals(this.yFormat, that.yFormat)) {
            return false;
        }
        if (!Objects.equals(this.yDateFormat, that.yDateFormat)) {
            return false;
        }
        if (!this.nullYString.equals(that.nullYString)) {
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
        result = HashUtils.hashCode(result, this.formatString);
        result = HashUtils.hashCode(result, this.xFormat);
        result = HashUtils.hashCode(result, this.xDateFormat);
        result = HashUtils.hashCode(result, this.yFormat);
        result = HashUtils.hashCode(result, this.yDateFormat);
        return result;
    }

    /**
     * Returns an independent copy of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        AbstractXYItemLabelGenerator clone
                = (AbstractXYItemLabelGenerator) super.clone();
        if (this.xFormat != null) {
            clone.xFormat = (NumberFormat) this.xFormat.clone();
        }
        if (this.yFormat != null) {
            clone.yFormat = (NumberFormat) this.yFormat.clone();
        }
        if (this.xDateFormat != null) {
            clone.xDateFormat = (DateFormat) this.xDateFormat.clone();
        }
        if (this.yDateFormat != null) {
            clone.yDateFormat = (DateFormat) this.yDateFormat.clone();
        }
        return clone;
    }

}
