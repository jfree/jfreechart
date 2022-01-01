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
 * ------------
 * Quarter.java
 * ------------
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
import org.jfree.chart.date.MonthConstants;
import org.jfree.chart.date.SerialDate;

/**
 * Defines a quarter (in a given year).  The range supported is Q1 1900 to
 * Q4 9999.  This class is immutable, which is a requirement for all
 * {@link RegularTimePeriod} subclasses.
 */
public class Quarter extends RegularTimePeriod implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3810061714380888671L;

    /** Constant for quarter 1. */
    public static final int FIRST_QUARTER = 1;

    /** Constant for quarter 4. */
    public static final int LAST_QUARTER = 4;

    /** The first month in each quarter. */
    public static final int[] FIRST_MONTH_IN_QUARTER = {
        0, MonthConstants.JANUARY, MonthConstants.APRIL, MonthConstants.JULY,
        MonthConstants.OCTOBER
    };

    /** The last month in each quarter. */
    public static final int[] LAST_MONTH_IN_QUARTER = {
        0, MonthConstants.MARCH, MonthConstants.JUNE, MonthConstants.SEPTEMBER,
        MonthConstants.DECEMBER
    };

    /** The year in which the quarter falls. */
    private short year;

    /** The quarter (1-4). */
    private byte quarter;

    /** The first millisecond. */
    private long firstMillisecond;

    /** The last millisecond. */
    private long lastMillisecond;

    /**
     * Constructs a new Quarter, based on the current system date/time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     */
    public Quarter() {
        this(new Date());
    }

    /**
     * Constructs a new quarter.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param year  the year (1900 to 9999).
     * @param quarter  the quarter (1 to 4).
     */
    public Quarter(int quarter, int year) {
        if ((quarter < FIRST_QUARTER) || (quarter > LAST_QUARTER)) {
            throw new IllegalArgumentException("Quarter outside valid range.");
        }
        this.year = (short) year;
        this.quarter = (byte) quarter;
        peg(getCalendarInstance());
    }

    /**
     * Constructs a new quarter.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param quarter  the quarter (1 to 4).
     * @param year  the year (1900 to 9999).
     */
    public Quarter(int quarter, Year year) {
        if ((quarter < FIRST_QUARTER) || (quarter > LAST_QUARTER)) {
            throw new IllegalArgumentException("Quarter outside valid range.");
        }
        this.year = (short) year.getYear();
        this.quarter = (byte) quarter;
        peg(getCalendarInstance());
    }

    /**
     * Constructs a new instance, based on a date/time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param time  the date/time ({@code null} not permitted).
     *
     * @see #Quarter(Date, TimeZone, Locale)
     */
    public Quarter(Date time) {
        this(time, getCalendarInstance());
    }

    /**
     * Creates a new {@code Quarter} instance, using the specified
     * zone and locale.
     *
     * @param time  the current time.
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @since 1.0.12
     */
    public Quarter(Date time, TimeZone zone, Locale locale) {
        Calendar calendar = Calendar.getInstance(zone, locale);
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH) + 1;
        this.quarter = (byte) SerialDate.monthCodeToQuarter(month);
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
    public Quarter(Date time, Calendar calendar) {
        calendar.setTime(time);
        int month = calendar.get(Calendar.MONTH) + 1;
        this.quarter = (byte) SerialDate.monthCodeToQuarter(month);
        this.year = (short) calendar.get(Calendar.YEAR);
        peg(calendar);
    }

    /**
     * Returns the quarter.
     *
     * @return The quarter.
     */
    public int getQuarter() {
        return this.quarter;
    }

    /**
     * Returns the year.
     *
     * @return The year.
     */
    public Year getYear() {
        return new Year(this.year);
    }

    /**
     * Returns the year.
     *
     * @return The year.
     *
     * @since 1.0.3
     */
    public int getYearValue() {
        return this.year;
    }

    /**
     * Returns the first millisecond of the quarter.  This will be determined
     * relative to the time zone specified in the constructor, or in the
     * calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The first millisecond of the quarter.
     *
     * @see #getLastMillisecond()
     */
    @Override
    public long getFirstMillisecond() {
        return this.firstMillisecond;
    }

    /**
     * Returns the last millisecond of the quarter.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The last millisecond of the quarter.
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
     * Returns the quarter preceding this one.
     * No matter what time zone and locale this instance was created with,
     * the returned instance will use the default calendar for time
     * calculations, obtained with {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @return The quarter preceding this one (or {@code null} if this is
     *     Q1 1900).
     */
    @Override
    public RegularTimePeriod previous() {
        Quarter result;
        if (this.quarter > FIRST_QUARTER) {
            result = new Quarter(this.quarter - 1, this.year);
        }
        else {
            if (this.year > 1900) {
                result = new Quarter(LAST_QUARTER, this.year - 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns the quarter following this one.
     * No matter what time zone and locale this instance was created with,
     * the returned instance will use the default calendar for time
     * calculations, obtained with {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @return The quarter following this one (or null if this is Q4 9999).
     */
    @Override
    public RegularTimePeriod next() {
        Quarter result;
        if (this.quarter < LAST_QUARTER) {
            result = new Quarter(this.quarter + 1, this.year);
        }
        else {
            if (this.year < 9999) {
                result = new Quarter(FIRST_QUARTER, this.year + 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns a serial index number for the quarter.
     *
     * @return The serial index number.
     */
    @Override
    public long getSerialIndex() {
        return this.year * 4L + this.quarter;
    }

    /**
     * Tests the equality of this Quarter object to an arbitrary object.
     * Returns {@code true} if the target is a Quarter instance
     * representing the same quarter as this object.  In all other cases,
     * returns {@code false}.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} if quarter and year of this and the object are
     *         the same.
     */
    @Override
    public boolean equals(Object obj) {

        if (obj != null) {
            if (obj instanceof Quarter) {
                Quarter target = (Quarter) obj;
                return (this.quarter == target.getQuarter()
                        && (this.year == target.getYearValue()));
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    /**
     * Returns a hash code for this object instance.  The approach described by
     * Joshua Bloch in "Effective Java" has been used here:
     * <p>
     * {@code http://developer.java.sun.com/developer/Books/effectivejava
     * /Chapter3.pdf}
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.quarter;
        result = 37 * result + this.year;
        return result;
    }

    /**
     * Returns an integer indicating the order of this Quarter object relative
     * to the specified object:
     *
     * negative == before, zero == same, positive == after.
     *
     * @param o1  the object to compare
     *
     * @return negative == before, zero == same, positive == after.
     */
    @Override
    public int compareTo(Object o1) {

        int result;

        // CASE 1 : Comparing to another Quarter object
        // --------------------------------------------
        if (o1 instanceof Quarter) {
            Quarter q = (Quarter) o1;
            result = this.year - q.getYearValue();
            if (result == 0) {
                result = this.quarter - q.getQuarter();
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
     * Returns a string representing the quarter (e.g. "Q1/2002").
     *
     * @return A string representing the quarter.
     */
    @Override
    public String toString() {
        return "Q" + this.quarter + "/" + this.year;
    }

    /**
     * Returns the first millisecond in the Quarter, evaluated using the
     * supplied calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The first millisecond in the Quarter.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getFirstMillisecond(Calendar calendar) {
        int month = Quarter.FIRST_MONTH_IN_QUARTER[this.quarter];
        calendar.set(this.year, month - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Returns the last millisecond of the Quarter, evaluated using the
     * supplied calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The last millisecond of the Quarter.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getLastMillisecond(Calendar calendar) {
        int month = Quarter.LAST_MONTH_IN_QUARTER[this.quarter];
        int eom = SerialDate.lastDayOfMonth(month, this.year);
        calendar.set(this.year, month - 1, eom, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Parses the string argument as a quarter.
     * <P>
     * This method should accept the following formats: "YYYY-QN" and "QN-YYYY",
     * where the "-" can be a space, a forward-slash (/), comma or a dash (-).
     * @param s A string representing the quarter.
     *
     * @return The quarter.
     */
    public static Quarter parseQuarter(String s) {

        // find the Q and the integer following it (remove both from the
        // string)...
        int i = s.indexOf("Q");
        if (i == -1) {
            throw new TimePeriodFormatException("Missing Q.");
        }

        if (i == s.length() - 1) {
            throw new TimePeriodFormatException("Q found at end of string.");
        }

        String qstr = s.substring(i + 1, i + 2);
        int quarter = Integer.parseInt(qstr);
        String remaining = s.substring(0, i) + s.substring(i + 2, s.length());

        // replace any / , or - with a space
        remaining = remaining.replace('/', ' ');
        remaining = remaining.replace(',', ' ');
        remaining = remaining.replace('-', ' ');

        // parse the string...
        Year year = Year.parseYear(remaining.trim());
        Quarter result = new Quarter(quarter, year);
        return result;

    }

}
