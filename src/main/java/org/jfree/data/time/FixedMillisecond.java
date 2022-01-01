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
 * ---------------------
 * FixedMillisecond.java
 * ---------------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Ulrich Voigt;
 *
 */

package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Wrapper for a {@code java.util.Date} object that allows it to be used
 * as a {@link RegularTimePeriod}.  This class is immutable, which is a
 * requirement for all {@link RegularTimePeriod} subclasses.
 */
public class FixedMillisecond extends RegularTimePeriod
        implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7867521484545646931L;

    /** The millisecond. */
    private final long time;

    /**
     * Constructs a millisecond based on the current system time.
     */
    public FixedMillisecond() {
        this(System.currentTimeMillis());
    }

    /**
     * Constructs a millisecond.
     *
     * @param millisecond  the millisecond (same encoding as java.util.Date).
     */
    public FixedMillisecond(long millisecond) {
        this.time = millisecond;
    }

    /**
     * Constructs a millisecond.
     *
     * @param time  the time ({@code null} not permitted).
     */
    public FixedMillisecond(Date time) {
        this(time.getTime());
    }

    /**
     * Returns the date/time (creates a new {@code Date} instance each time 
     * this method is called).
     *
     * @return The date/time.
     */
    public Date getTime() {
        return new Date(this.time);
    }

    /**
     * This method is overridden to do nothing.
     *
     * @param calendar  ignored
     *
     * @since 1.0.3
     */
    @Override
    public void peg(Calendar calendar) {
        // nothing to do
    }

    /**
     * Returns the millisecond preceding this one.
     *
     * @return The millisecond preceding this one.
     */
    @Override
    public RegularTimePeriod previous() {
        RegularTimePeriod result = null;
        long t = this.time;
        if (t != Long.MIN_VALUE) {
            result = new FixedMillisecond(t - 1);
        }
        return result;
    }

    /**
     * Returns the millisecond following this one.
     *
     * @return The millisecond following this one.
     */
    @Override
    public RegularTimePeriod next() {
        RegularTimePeriod result = null;
        long t = this.time;
        if (t != Long.MAX_VALUE) {
            result = new FixedMillisecond(t + 1);
        }
        return result;
    }

    /**
     * Tests the equality of this object against an arbitrary Object.
     *
     * @param object  the object to compare
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof FixedMillisecond) {
            FixedMillisecond m = (FixedMillisecond) object;
            return this.time == m.getFirstMillisecond();
        }
        else {
            return false;
        }

    }

    /**
     * Returns a hash code for this object instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return (int) this.time;
    }

    /**
     * Returns an integer indicating the order of this Millisecond object
     * relative to the specified
     * object: negative == before, zero == same, positive == after.
     *
     * @param o1    the object to compare.
     *
     * @return negative == before, zero == same, positive == after.
     */
    @Override
    public int compareTo(Object o1) {

        int result;
        long difference;

        // CASE 1 : Comparing to another Second object
        // -------------------------------------------
        if (o1 instanceof FixedMillisecond) {
            FixedMillisecond t1 = (FixedMillisecond) o1;
            difference = this.time - t1.time;
            if (difference > 0) {
                result = 1;
            }
            else {
                if (difference < 0) {
                   result = -1;
                }
                else {
                    result = 0;
                }
            }
        }

        // CASE 2 : Comparing to another TimePeriod object
        // -----------------------------------------------
        else if (o1 instanceof RegularTimePeriod) {
            // more difficult case - evaluate later...
            result = 0;
        }

        // CASE 3 : Comparing to a non-TimePeriod object
        // ---------------------------------------------
        else {
            // consider time periods to be ordered after general objects
            result = 1;
        }

        return result;

    }

    /**
     * Returns the first millisecond of the time period.
     *
     * @return The first millisecond of the time period.
     */
    @Override
    public long getFirstMillisecond() {
        return this.time;
    }


    /**
     * Returns the first millisecond of the time period.
     *
     * @param calendar  the calendar.
     *
     * @return The first millisecond of the time period.
     */
    @Override
    public long getFirstMillisecond(Calendar calendar) {
        return this.time;
    }

    /**
     * Returns the last millisecond of the time period.
     *
     * @return The last millisecond of the time period.
     */
    @Override
    public long getLastMillisecond() {
        return this.time;
    }

    /**
     * Returns the last millisecond of the time period.
     *
     * @param calendar  the calendar.
     *
     * @return The last millisecond of the time period.
     */
    @Override
    public long getLastMillisecond(Calendar calendar) {
        return this.time;
    }

    /**
     * Returns the millisecond closest to the middle of the time period.
     *
     * @return The millisecond closest to the middle of the time period.
     */
    @Override
    public long getMiddleMillisecond() {
        return this.time;
    }

    /**
     * Returns the millisecond closest to the middle of the time period.
     *
     * @param calendar  the calendar.
     *
     * @return The millisecond closest to the middle of the time period.
     */
    @Override
    public long getMiddleMillisecond(Calendar calendar) {
        return this.time;
    }

    /**
     * Returns a serial index number for the millisecond.
     *
     * @return The serial index number.
     */
    @Override
    public long getSerialIndex() {
        return this.time;
    }

}
