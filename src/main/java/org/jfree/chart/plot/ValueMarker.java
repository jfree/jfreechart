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
 * ----------------
 * ValueMarker.java
 * ----------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Paint;
import java.awt.Stroke;

import org.jfree.chart.event.MarkerChangeEvent;

/**
 * A marker that represents a single value.  Markers can be added to plots to
 * highlight specific values.
 */
public class ValueMarker extends Marker {

    /** The value. */
    private double value;

    /**
     * Creates a new marker.
     *
     * @param value  the value.
     */
    public ValueMarker(double value) {
        super();
        this.value = value;
    }

    /**
     * Creates a new marker.
     *
     * @param value  the value.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public ValueMarker(double value, Paint paint, Stroke stroke) {
        this(value, paint, stroke, paint, stroke, 1.0f);
    }

    /**
     * Creates a new value marker.
     *
     * @param value  the value.
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     * @param outlinePaint  the outline paint ({@code null} permitted).
     * @param outlineStroke  the outline stroke ({@code null} permitted).
     * @param alpha  the alpha transparency (in the range 0.0f to 1.0f).
     */
    public ValueMarker(double value, Paint paint, Stroke stroke,
                       Paint outlinePaint, Stroke outlineStroke, float alpha) {
        super(paint, stroke, outlinePaint, outlineStroke, alpha);
        this.value = value;
    }

    /**
     * Returns the value.
     *
     * @return The value.
     *
     * @see #setValue(double)
     */
    public double getValue() {
        return this.value;
    }

    /**
     * Sets the value for the marker and sends a {@link MarkerChangeEvent} to
     * all registered listeners.
     *
     * @param value  the value.
     *
     * @see #getValue()
     */
    public void setValue(double value) {
        this.value = value;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Tests this marker for equality with an arbitrary object.  This method
     * returns {@code true} if:
     *
     * <ul>
     * <li>{@code obj} is not {@code null};</li>
     * <li>{@code obj} is an instance of {@code ValueMarker};</li>
     * <li>{@code obj} has the same value as this marker;</li>
     * <li>{@code super.equals(obj)} returns {@code true}.</li>
     * </ul>
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof ValueMarker)) {
            return false;
        }
        ValueMarker that = (ValueMarker) obj;
        if (this.value != that.value) {
            return false;
        }
        return true;
    }
}
