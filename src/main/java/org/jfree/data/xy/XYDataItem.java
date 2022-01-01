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
 * XYDataItem.java
 * ---------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.internal.Args;

/**
 * Represents one (x, y) data item for an {@link XYSeries}.  Note that
 * subclasses are REQUIRED to support cloning.
 */
public class XYDataItem implements Cloneable, Comparable<XYDataItem>, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2751513470325494890L;

    /** The x-value ({@code null} not permitted). */
    private Number x;

    /** The y-value. */
    private Number y;

    /**
     * Constructs a new data item.
     *
     * @param x  the x-value ({@code null} NOT permitted).
     * @param y  the y-value ({@code null} permitted).
     */
    public XYDataItem(Number x, Number y) {
        Args.nullNotPermitted(x, "x");
        this.x = x;
        this.y = y;
    }

    /**
     * Constructs a new data item.
     *
     * @param x  the x-value.
     * @param y  the y-value.
     */
    public XYDataItem(double x, double y) {
        this(Double.valueOf(x), Double.valueOf(y));
    }

    /**
     * Returns the x-value.
     *
     * @return The x-value (never {@code null}).
     */
    public Number getX() {
        return this.x;
    }

    /**
     * Returns the x-value as a double primitive.
     *
     * @return The x-value.
     *
     * @see #getX()
     * @see #getYValue()
     */
    public double getXValue() {
        // this.x is not allowed to be null...
        return this.x.doubleValue();
    }

    /**
     * Returns the y-value.
     *
     * @return The y-value (possibly {@code null}).
     */
    public Number getY() {
        return this.y;
    }

    /**
     * Returns the y-value as a double primitive.
     *
     * @return The y-value.
     *
     * @see #getY()
     * @see #getXValue()
     */
    public double getYValue() {
        double result = Double.NaN;
        if (this.y != null) {
            result = this.y.doubleValue();
        }
        return result;
    }

    /**
     * Sets the y-value for this data item.  Note that there is no
     * corresponding method to change the x-value.
     *
     * @param y  the new y-value.
     */
    public void setY(double y) {
        setY(Double.valueOf(y));
    }

    /**
     * Sets the y-value for this data item.  Note that there is no
     * corresponding method to change the x-value.
     *
     * @param y  the new y-value ({@code null} permitted).
     */
    public void setY(Number y) {
        this.y = y;
    }

    /**
     * Returns an integer indicating the order of this object relative to
     * another object.
     * <P>
     * For the order we consider only the x-value:
     * negative == "less-than", zero == "equal", positive == "greater-than".
     *
     * @param other  the data item being compared to.
     *
     * @return An integer indicating the order of this data pair object
     *      relative to another object.
     */
    @Override
    public int compareTo(XYDataItem other) {
        int result;
        double compare = this.x.doubleValue() - other.getX().doubleValue();
        if (compare > 0.0) {
            result = 1;
        } else {
            if (compare < 0.0) {
                result = -1;
            } else {
                result = 0;
            }
        }
        return result;
    }

    /**
     * Returns a clone of this object.
     *
     * @return A clone.
     */
    @Override
    public Object clone() {
        Object clone = null;
        try {
            clone = super.clone();
        }
        catch (CloneNotSupportedException e) { // won't get here...
            e.printStackTrace();
        }
        return clone;
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the object to test against for equality ({@code null}
     *             permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof XYDataItem)) {
            return false;
        }
        XYDataItem that = (XYDataItem) obj;
        if (!this.x.equals(that.x)) {
            return false;
        }
        if (!Objects.equals(this.y, that.y)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result;
        result = this.x.hashCode();
        result = 29 * result + (this.y != null ? this.y.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representing this instance, primarily for debugging
     * use.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return "[" + getXValue() + ", " + getYValue() + "]";
    }

}
