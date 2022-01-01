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
 * --------------
 * LogFormat.java
 * --------------
 * (C) Copyright 2007-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.text.format;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import org.jfree.chart.internal.Args;

/**
 * A number formatter for logarithmic values.  This formatter does not support
 * parsing.
 */
public class LogFormat extends NumberFormat {

    /** The log base value. */
    private double base;

    /** The natural logarithm of the base value. */
    private double baseLog;

    /** The label for the log base (for example, "e"). */
    private String baseLabel;

    /**
     * The label for the power symbol.
     */
    private String powerLabel;

    /** A flag that controls whether or not the base is shown. */
    private boolean showBase;

    /** The number formatter for the exponent. */
    private NumberFormat formatter = new DecimalFormat("0.0#");

    /**
     * Creates a new instance using base 10.
     */
    public LogFormat() {
        this(10.0, "10", true);
    }

    /**
     * Creates a new instance.
     *
     * @param base  the base.
     * @param baseLabel  the base label ({@code null} not permitted).
     * @param showBase  a flag that controls whether or not the base value is
     *                  shown.
     */
    public LogFormat(double base, String baseLabel, boolean showBase) {
        this(base, baseLabel, "^", showBase);
    }

    /**
     * Creates a new instance.
     *
     * @param base  the base.
     * @param baseLabel  the base label ({@code null} not permitted).
     * @param powerLabel  the power label ({@code null} not permitted).
     * @param showBase  a flag that controls whether or not the base value is
     *                  shown.
     */
    public LogFormat(double base, String baseLabel, String powerLabel,
            boolean showBase) {
        Args.nullNotPermitted(baseLabel, "baseLabel");
        Args.nullNotPermitted(powerLabel, "powerLabel");
        this.base = base;
        this.baseLog = Math.log(this.base);
        this.baseLabel = baseLabel;
        this.showBase = showBase;
        this.powerLabel = powerLabel;
    }

    /**
     * Returns the number format used for the exponent.
     *
     * @return The number format (never {@code null}).
     */
    public NumberFormat getExponentFormat() {
        return (NumberFormat) this.formatter.clone();
    }

    /**
     * Sets the number format used for the exponent.
     *
     * @param format  the formatter ({@code null} not permitted).
     */
    public void setExponentFormat(NumberFormat format) {
        Args.nullNotPermitted(format, "format");
        this.formatter = format;
    }

    /**
     * Calculates the log of a given value.
     *
     * @param value  the value.
     *
     * @return The log of the value.
     */
    private double calculateLog(double value) {
        return Math.log(value) / this.baseLog;
    }

    /**
     * Returns a formatted representation of the specified number.
     *
     * @param number  the number.
     * @param toAppendTo  the string buffer to append to.
     * @param pos  the position.
     *
     * @return A string buffer containing the formatted value.
     */
    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos) {
        StringBuffer result = new StringBuffer();
        if (this.showBase) {
            result.append(this.baseLabel);
            result.append(this.powerLabel);
        }
        result.append(this.formatter.format(calculateLog(number)));
        return result;
    }

    /**
     * Formats the specified number as a hexadecimal string.  The decimal
     * fraction is ignored.
     *
     * @param number  the number to format.
     * @param toAppendTo  the buffer to append to (ignored here).
     * @param pos  the field position (ignored here).
     *
     * @return The string buffer.
     */
    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo,
            FieldPosition pos) {
        StringBuffer result = new StringBuffer();
        if (this.showBase) {
            result.append(this.baseLabel);
            result.append(this.powerLabel);
        }
        result.append(this.formatter.format(calculateLog(number)));
        return result;
    }

    /**
     * Parsing is not implemented, so this method always returns
     * {@code null}.
     *
     * @param source  ignored.
     * @param parsePosition  ignored.
     *
     * @return Always {@code null}.
     */
    @Override
    public Number parse (String source, ParsePosition parsePosition) {
        return null; // don't bother with parsing
    }

    /**
     * Tests this formatter for equality with an arbitrary object.
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
        if (!(obj instanceof LogFormat)) {
            return false;
        }
        LogFormat that = (LogFormat) obj;
        if (this.base != that.base) {
            return false;
        }
        if (!this.baseLabel.equals(that.baseLabel)) {
            return false;
        }
        if (this.baseLog != that.baseLog) {
            return false;
        }
        if (this.showBase != that.showBase) {
            return false;
        }
        if (!this.formatter.equals(that.formatter)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of this instance.
     *
     * @return A clone.
     */
    @Override
    public Object clone() {
        LogFormat clone = (LogFormat) super.clone();
        clone.formatter = (NumberFormat) this.formatter.clone();
        return clone;
    }

}
