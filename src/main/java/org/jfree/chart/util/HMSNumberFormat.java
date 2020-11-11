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
 * --------------------
 * HMSNumberFormat.java
 * --------------------
 * (C) Copyright 2013-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * A custom number formatter that formats numbers (in seconds) as HH:MM:SS.
 * Created in response to:
 * 
 * http://stackoverflow.com/questions/19028908/jfreechart-need-to-customize-y-axis-just-for-printing
 * 
 * @since 1.0.17
 */
public class HMSNumberFormat extends NumberFormat {

    private NumberFormat formatter = new DecimalFormat("00");
    
    /**
     * Creates a new instance.
     */
    public HMSNumberFormat() {
        // nothing to do
    }

    /**
     * Formats the specified number as a string of the form HH:MM:SS.  The 
     * decimal fraction is ignored.
     *
     * @param number  the number to format.
     * @param toAppendTo  the buffer to append to (ignored here).
     * @param pos  the field position (ignored here).
     *
     * @return The string buffer.
     */
    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos) {
        return format((long) number, toAppendTo, pos);
    }

    /**
     * Formats the specified number as a string of the form HH:MM:SS.
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
        StringBuffer sb = new StringBuffer();
        long hours = number / 3600;
        sb.append(this.formatter.format(hours)).append(":");
        long remaining = number - (hours * 3600);
        long minutes = remaining / 60;
        sb.append(this.formatter.format(minutes)).append(":");
        long seconds = remaining - (minutes * 60);
        sb.append(this.formatter.format(seconds));
        return sb;
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

}
