/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * OHLC.java
 * ---------
 * (C) Copyright 2006, 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 * 23-May-2009 : Implemented hashCode() (DG);
 *
 */

package org.jfree.data.time.ohlc;

import java.io.Serializable;
import org.jfree.chart.HashUtilities;

/**
 * A data record containing open-high-low-close data (immutable).  This class 
 * is used internally by the {@link OHLCItem} class.
 *
 * @since 1.0.4
 */
public class OHLC implements Serializable {

    /** The open value. */
    private double open;

    /** The close value. */
    private double close;

    /** The high value. */
    private double high;

    /** The low value. */
    private double low;

    /**
     * Creates a new instance of <code>OHLC</code>.
     *
     * @param open  the open value.
     * @param close  the close value.
     * @param high  the high value.
     * @param low  the low value.
     */
    public OHLC(double open, double high, double low, double close) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
    }

    /**
     * Returns the open value.
     *
     * @return The open value.
     */
    public double getOpen() {
        return this.open;
    }

    /**
     * Returns the close value.
     *
     * @return The close value.
     */
    public double getClose() {
        return this.close;
    }

    /**
     * Returns the high value.
     *
     * @return The high value.
     */
    public double getHigh() {
        return this.high;
    }

    /**
     * Returns the low value.
     *
     * @return The low value.
     */
    public double getLow() {
        return this.low;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof OHLC)) {
            return false;
        }
        OHLC that = (OHLC) obj;
        if (this.open != that.open) {
            return false;
        }
        if (this.close != that.close) {
            return false;
        }
        if (this.high != that.high) {
            return false;
        }
        if (this.low != that.low) {
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
        int result = 193;
        result = HashUtilities.hashCode(result, this.open);
        result = HashUtilities.hashCode(result, this.high);
        result = HashUtilities.hashCode(result, this.low);
        result = HashUtilities.hashCode(result, this.close);
        return result;
    }

}
