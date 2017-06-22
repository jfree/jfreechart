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
 * -------------------------
 * ComparableObjectItem.java
 * -------------------------
 * (C) Copyright 2006-2016, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Oct-2006 : New class, based on XYDataItem (DG);
 * 03-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;

/**
 * Represents one (Comparable, Object) data item for use in a
 * {@link ComparableObjectSeries}.
 *
 * @since 1.0.3
 */
public class ComparableObjectItem implements Cloneable, Comparable,
        Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2751513470325494890L;

    /** The x-value. */
    private Comparable x;

    /** The y-value. */
    private Object obj;

    /**
     * Constructs a new data item.
     *
     * @param x  the x-value ({@code null} NOT permitted).
     * @param y  the y-value ({@code null} permitted).
     */
    public ComparableObjectItem(Comparable x, Object y) {
        Args.nullNotPermitted(x, "x");
        this.x = x;
        this.obj = y;
    }

    /**
     * Returns the x-value.
     *
     * @return The x-value (never {@code null}).
     */
    protected Comparable getComparable() {
        return this.x;
    }

    /**
     * Returns the y-value.
     *
     * @return The y-value (possibly {@code null}).
     */
    protected Object getObject() {
        return this.obj;
    }

    /**
     * Sets the y-value for this data item.  Note that there is no
     * corresponding method to change the x-value.
     *
     * @param y  the new y-value ({@code null} permitted).
     */
    protected void setObject(Object y) {
        this.obj = y;
    }

    /**
     * Returns an integer indicating the order of this object relative to
     * another object.
     * <P>
     * For the order we consider only the x-value:
     * negative == "less-than", zero == "equal", positive == "greater-than".
     *
     * @param o1  the object being compared to.
     *
     * @return An integer indicating the order of this data pair object
     *      relative to another object.
     */
    @Override
    public int compareTo(Object o1) {

        int result;

        // CASE 1 : Comparing to another ComparableObjectItem object
        // ---------------------------------------------------------
        if (o1 instanceof ComparableObjectItem) {
            ComparableObjectItem that = (ComparableObjectItem) o1;
            return this.x.compareTo(that.x);
        }

        // CASE 2 : Comparing to a general object
        // ---------------------------------------------
        else {
            // consider these to be ordered after general objects
            result = 1;
        }

        return result;

    }

    /**
     * Returns a clone of this object.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException not thrown by this class, but
     *         subclasses may differ.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        if (!(obj instanceof ComparableObjectItem)) {
            return false;
        }
        ComparableObjectItem that = (ComparableObjectItem) obj;
        if (!this.x.equals(that.x)) {
            return false;
        }
        if (!ObjectUtils.equal(this.obj, that.obj)) {
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
        result = 29 * result + (this.obj != null ? this.obj.hashCode() : 0);
        return result;
    }

}
