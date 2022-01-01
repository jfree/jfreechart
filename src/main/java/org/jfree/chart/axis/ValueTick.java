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
 * ValueTick.java
 * --------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import org.jfree.chart.text.TextAnchor;

/**
 * A value tick.
 */
public abstract class ValueTick extends Tick {

    /** The value. */
    private double value;

    /**
     * The tick type (major or minor).
     */
    private TickType tickType;

    /**
     * Creates a new value tick.
     *
     * @param value  the value.
     * @param label  the label.
     * @param textAnchor  the part of the label that is aligned to the anchor
     *                    point.
     * @param rotationAnchor  defines the rotation point relative to the label.
     * @param angle  the rotation angle (in radians).
     */
    public ValueTick(double value, String label,
                     TextAnchor textAnchor, TextAnchor rotationAnchor,
                     double angle) {

        this(TickType.MAJOR, value, label, textAnchor, rotationAnchor, angle);
        this.value = value;

    }

    /**
     * Creates a new value tick.
     *
     * @param tickType  the tick type (major or minor, {@code null} not 
     *     permitted).
     * @param value  the value.
     * @param label  the label.
     * @param textAnchor  the part of the label that is aligned to the anchor
     *                    point.
     * @param rotationAnchor  defines the rotation point relative to the label.
     * @param angle  the rotation angle (in radians).
     */
    public ValueTick(TickType tickType, double value, String label,
                     TextAnchor textAnchor, TextAnchor rotationAnchor,
                     double angle) {

        super(label, textAnchor, rotationAnchor, angle);
        this.value = value;
        this.tickType = tickType;
    }

    /**
     * Returns the value.
     *
     * @return The value.
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Returns the tick type (major or minor).
     *
     * @return The tick type.
     */
    public TickType getTickType() {
        return this.tickType;
    }

    /**
     * Tests this tick for equality with an arbitrary object.
     *
     * @param obj  the object to test ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ValueTick)) {
            return false;
        }
        ValueTick that = (ValueTick) obj;
        if (this.value != that.value) {
            return false;
        }
        if (!this.tickType.equals(that.tickType)) {
            return false;
        }
        return super.equals(obj);
    }

}
