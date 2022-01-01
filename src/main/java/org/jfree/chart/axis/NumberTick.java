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
 * ---------------
 * NumberTick.java
 * ---------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.text.TextAnchor;

/**
 * A numerical tick.
 */
public class NumberTick extends ValueTick {

    /** The number. */
    private final Number number;

    /**
     * Creates a new tick.
     *
     * @param number  the number ({@code null} not permitted).
     * @param label  the label.
     * @param textAnchor  the part of the label that is aligned with the anchor
     *                    point.
     * @param rotationAnchor  defines the rotation point relative to the text.
     * @param angle  the rotation angle (in radians).
     */
    public NumberTick(Number number, String label,
                      TextAnchor textAnchor,
                      TextAnchor rotationAnchor, double angle) {

        super(number.doubleValue(), label, textAnchor, rotationAnchor, angle);
        this.number = number;

    }

    /**
     * Creates a new tick.
     *
     * @param tickType  the tick type.
     * @param value  the value.
     * @param label  the label.
     * @param textAnchor  the part of the label that is aligned with the anchor
     *                    point.
     * @param rotationAnchor  defines the rotation point relative to the text.
     * @param angle  the rotation angle (in radians).
     */
    public NumberTick(TickType tickType, double value, String label,
            TextAnchor textAnchor, TextAnchor rotationAnchor, double angle) {
        super(tickType, value, label, textAnchor, rotationAnchor, angle);
        this.number = value;
    }

    /**
     * Returns the number.
     *
     * @return The number.
     */
    public Number getNumber() {
        return this.number;
    }

}
