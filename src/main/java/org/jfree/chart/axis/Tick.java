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
 * ---------
 * Tick.java
 * ---------
 * (C) Copyright 2000-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Nicolas Brodu;
 *
 */

package org.jfree.chart.axis;

import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.Args;

/**
 * The base class used to represent labeled ticks along an axis.
 */
public abstract class Tick implements Serializable, Cloneable {

    /** For serialization. */
    private static final long serialVersionUID = 6668230383875149773L;

    /** A text version of the tick value. */
    private String text;

    /** The text anchor for the tick label. */
    private TextAnchor textAnchor;

    /** The rotation anchor for the tick label. */
    private TextAnchor rotationAnchor;

    /** The rotation angle. */
    private double angle;

    /**
     * Creates a new tick.
     *
     * @param text  the formatted version of the tick value.
     * @param textAnchor  the text anchor ({@code null} not permitted).
     * @param rotationAnchor  the rotation anchor ({@code null} not
     *                        permitted).
     * @param angle  the angle.
     */
    public Tick(String text, TextAnchor textAnchor, TextAnchor rotationAnchor,
                double angle) {
        Args.nullNotPermitted(textAnchor, "textAnchor");
        Args.nullNotPermitted(rotationAnchor, "rotationAnchor");
        this.text = text;
        this.textAnchor = textAnchor;
        this.rotationAnchor = rotationAnchor;
        this.angle = angle;
    }

    /**
     * Returns the text version of the tick value.
     *
     * @return A string (possibly {@code null});
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the text anchor.
     *
     * @return The text anchor (never {@code null}).
     */
    public TextAnchor getTextAnchor() {
        return this.textAnchor;
    }

    /**
     * Returns the text anchor that defines the point around which the label is
     * rotated.
     *
     * @return A text anchor (never {@code null}).
     */
    public TextAnchor getRotationAnchor() {
        return this.rotationAnchor;
    }

    /**
     * Returns the angle.
     *
     * @return The angle.
     */
    public double getAngle() {
        return this.angle;
    }

    /**
     * Tests this tick for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Tick) {
            Tick t = (Tick) obj;
            if (!Objects.equals(this.text, t.text)) {
                return false;
            }
            if (!Objects.equals(this.textAnchor, t.textAnchor)) {
                return false;
            }
            if (!Objects.equals(this.rotationAnchor, t.rotationAnchor)) {
                return false;
            }
            if (!(this.angle == t.angle)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a clone of the tick.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Tick clone = (Tick) super.clone();
        return clone;
    }

    /**
     * Returns a string representation of the tick.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return this.text;
    }
}
