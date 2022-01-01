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
 * Year.java
 * ---------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Represents a year in the range -9999 to 9999.  This class is immutable,
 * which is a requirement for all {@link RegularTimePeriod} subclasses.
 */
public class Year extends RegularTimePeriod implements Serializable {

    /**
     * The minimum year value.
     *
     * @since 1.0.11
     */
    public static final int MINIMUM_YEAR = -9999;

    /**
     * The maximum year value.
     *
     * @since 1.0.11
     */
    public static final int MAXIMUM_YEAR = 9999;

    /** For serialization. */
    private static final long serialVersionUID = -7659990929736074836L;

    /** The year. */
    private short year;

    /** The first millisecond. */
    private long firstMillisecond;

    /** The last millisecond. */
    private long lastMillisecond;

    /**
     * Creates a new {@code Year}, based on the current system date/time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     */
    public Year() {
        this(new Date());
    }

    /**
     * Creates a time period representing a single year.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param year  the year.
     */
    public Year(int year) {
        if ((year < Year.MINIMUM_YEAR) || (year > Year.MAXIMUM_YEAR)) {
            throw new IllegalArgumentException(
                "Year constructor: year (" + year + ") outside valid range.");
        }
        this.year = (short) year;
        peg(getCalendarInstance());
    }

    /**
     * Creates a new {@code Year}, based on a particular instant in time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param time  the time ({@code null} not permitted).
     *
     * @see #Year(Date, TimeZone, Locale)
     */
    public Year(Date time) {
        this(time, getCalendarInstance());
    }

    /**
     * Creates a new {@code Year} instance, for the specified time zone
     * and locale.
     *
     * @param time  the current time ({@code null} not permitted).
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @since 1.0.12
     */
    public Year(Date time, TimeZone zone, Locale locale) {
        Calendar calendar = Calendar.getInstance(zone, locale);
        calendar.setTime(time);
        this.year = (short) calendar.get(Calendar.YEAR);
        peg(calendar);
    }

    /**
     * Constructs a new instance, based on a particular date/time.
     * The time zone and locale are determined by the {@code calendar}
     * parameter.
     *
     * @param time the date/time ({@code null} not permitted).
     * @param calendar the calendar to use for calculations ({@code null} not permitted).
     */
    public Year(Date time, Calendar calendar) {
        calendar.setTime(time);
        this.year = (short) calendar.get(Calendar.YEAR);
        peg(calendar);
    }

    /**
     * Returns the year.
     *
     * @return The year.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the first millisecond of the year.  This will be determined
     * relative to the time zone specified in the constructor, or in the
     * calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The first millisecond of the year.
     *
     * @see #getLastMillisecond()
     */
    @Override
    public long getFirstMillisecond() {
        return this.firstMillisecond;
    }

    /**
     * Returns the last millisecond of the year.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The last millisecond of the year.
     *
     * @see #getFirstMillisecond()
     */
    @Override
    public long getLastMillisecond() {
        return this.lastMillisecond;
    }

    /**
     * Recalculates the start date/time and end date/time for this time period
     * relative to the supplied calendar (which incorporates a time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @since 1.0.3
     */
    @Override
    public void peg(Calendar calendar) {
        this.firstMillisecond = getFirstMillisecond(calendar);
        this.lastMillisecond = getLastMillisecond(calendar);
    }

    /**
     * Returns the year preceding this one.
     * No matter what time zone and locale this instance was created with,
     * the returned instance will use the default calendar for time
     * calculations, obtained with {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @return The year preceding this one (or {@code null} if the
     *         current year is -9999).
     */
    @Override
    public RegularTimePeriod previous() {
        if (this.year > Year.MINIMUM_YEAR) {
            return new Year(this.year - 1);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the year following this one.
     * No matter what time zone and locale this instance was created with,
     * the returned instance will use the default calendar for time
     * calculations, obtained with {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @return The year following this one (or {@code null} if the current
     *         year is 9999).
     */
    @Override
    public RegularTimePeriod next() {
        if (this.year < Year.MAXIMUM_YEAR) {
            return new Year(this.year + 1);
        }
        else {
            return null;
        }
    }

    /**
     * Returns a serial index number for the year.
     * <P>
     * The implementation simply returns the year number (e.g. 2002).
     *
     * @return The serial index number.
     */
    @Override
    public long getSerialIndex() {
        return this.year;
    }

    /**
     * Returns the first millisecond of the year, evaluated using the supplied
     * calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The first millisecond of the year.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getFirstMillisecond(Calendar calendar) {
        calendar.set(this.year, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Returns the last millisecond of the year, evaluated using the supplied
     * calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The last millisecond of the year.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getLastMillisecond(Calendar calendar) {
        calendar.set(this.year, Calendar.DECEMBER, 31, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Tests the equality of this {@code Year} object to an arbitrary
     * object.  Returns {@code true} if the target is a {@code Year}
     * instance representing the same year as this object.  In all other cases,
     * returns {@code false}.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} if the year of this and the object are the
     *         same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Year)) {
            return false;
        }
        Year that = (Year) obj;
        return (this.year == that.year);
    }

    /**
     * Returns a hash code for this object instance.  The approach described by
     * Joshua Bloch in "Effective Java" has been used here:
     * <p>
     * {@code http://developer.java.sun.com/developer/Books/effectivejava
     *     /Chapter3.pdf}
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        int c = this.year;
        result = 37 * result + c;
        return result;
    }

    /**
     * Returns an integer indicating the order of this {@code Year} object
     * relative to the specified object:
     *
     * negative == before, zero == same, positive == after.
     *
     * @param o1  the object to compare.
     *
     * @return negative == before, zero == same, positive == after.
     */
    @Override
    public int compareTo(Object o1) {

        int result;

        // CASE 1 : Comparing to another Year object
        // -----------------------------------------
        if (o1 instanceof Year) {
            Year y = (Year) o1;
            result = this.year - y.getYear();
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
     * Returns a string representing the year..
     *
     * @return A string representing the year.
     */
    @Override
    public String toString() {
        return Integer.toString(this.year);
    }

    /**
     * Parses the string argument as a year.
     * <P>
     * The string format is YYYY.
     *
     * @param s  a string representing the year.
     *
     * @return {@code null} if the string is not parseable, the year
     *         otherwise.
     */
    public static Year parseYear(String s) {

        // parse the string...
        int y;
        try {
            y = Integer.parseInt(s.trim());
        }
        catch (NumberFormatException e) {
            throw new TimePeriodFormatException("Cannot parse string.");
        }

        // create the year...
        try {
            return new Year(y);
        }
        catch (IllegalArgumentException e) {
            throw new TimePeriodFormatException("Year outside valid range.");
        }
    }

}
