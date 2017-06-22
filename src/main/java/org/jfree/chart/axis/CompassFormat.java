/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * ------------------
 * CompassFormat.java
 * ------------------
 * (C) Copyright 2003-2016, by Sylvain Vieujot and Contributors.
 *
 * Original Author:  Sylvain Vieujot;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *                   Simon Legner (GitHub #298);
 *
 * Changes
 * -------
 * 18-Feb-2004 : Version 1 contributed by Sylvain Vieujot (DG);
 * 04-Feb-2014 : Make direction strings user-definable (SL);
 * 
 */

package org.jfree.chart.axis;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import org.jfree.chart.util.Args;

/**
 * A formatter that displays numbers as directions.
 */
public class CompassFormat extends NumberFormat {

    /** The directions. */
    public final String[] directions;

    /**
     * Creates a new formatter using English identifiers.
     */
    public CompassFormat() {
        this("N", "E", "S", "W");
    }

    /**
     * Creates a new formatter using the specified identifiers for
     * the base wind directions.
     * 
     * @param n  the code for NORTH.
     * @param e  the code for EAST.
     * @param s  the code for SOUTH.
     * @param w  the code for WEST.
     * 
     * @since 1.0.18
     */
    public CompassFormat(String n, String e, String s, String w) {
        this(new String[] {
            n, n + n + e, n + e, e + n + e, e, e + s + e, s + e, s + s + e, s,
            s + s + w, s + w, w + s + w, w, w + n + w, n + w, n + n + w
        });
    }

    /**
     * Creates a new formatter using the specified identifiers.
     * 
     * @param directions  an array containing 16 strings representing
     *     the directions of a compass.
     * 
     * @since 1.0.18
     */
    public CompassFormat(String[] directions) {
        super();
        Args.nullNotPermitted(directions, "directions");
        if (directions.length != 16) {
            throw new IllegalArgumentException("The 'directions' array must "
                    + "contain exactly 16 elements");
        }
        this.directions = directions;
    }

    /**
     * Returns a string representing the direction.
     *
     * @param direction  the direction.
     *
     * @return A string.
     */
    public String getDirectionCode(double direction) {
        direction = direction % 360;
        if (direction < 0.0) {
            direction = direction + 360.0;
        }
        int index = ((int) Math.floor(direction / 11.25) + 1) / 2;
        return directions[index];
    }

    /**
     * Formats a number into the specified string buffer.
     *
     * @param number  the number to format.
     * @param toAppendTo  the string buffer.
     * @param pos  the field position (ignored here).
     *
     * @return The string buffer.
     */
    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos) {
        return toAppendTo.append(getDirectionCode(number));
    }

    /**
     * Formats a number into the specified string buffer.
     *
     * @param number  the number to format.
     * @param toAppendTo  the string buffer.
     * @param pos  the field position (ignored here).
     *
     * @return The string buffer.
     */
    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo,
            FieldPosition pos) {
        return toAppendTo.append(getDirectionCode(number));
    }

    /**
     * This method returns {@code null} for all inputs.  This class cannot
     * be used for parsing.
     *
     * @param source  the source string.
     * @param parsePosition  the parse position.
     *
     * @return {@code null}.
     */
    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return null;
    }

}
