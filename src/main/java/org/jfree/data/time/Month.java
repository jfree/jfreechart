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
 * ----------
 * Month.java
 * ----------
 * (C) Copyright 2001-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Chris Boek;
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
 * Represents a single month.  This class is immutable, which is a requirement
 * for all {@link RegularTimePeriod} subclasses.
 */
public class Month extends RegularTimePeriod implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -5090216912548722570L;

    /** The month (1-12). */
    private int month;

    /** The year in which the month falls. */
    private int year;

    /** The first millisecond. */
    private long firstMillisecond;

    /** The last millisecond. */
    private long lastMillisecond;

    /**
     * Constructs a new Month, based on the current system time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     */
    public Month() {
        this(new Date());
    }

    /**
     * Constructs a new month instance.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param month  the month (in the range 1 to 12).
     * @param year  the year.
     */
    public Month(int month, int year) {
        if ((month < 1) || (month > 12)) {
            throw new IllegalArgumentException("Month outside valid range.");
        }
        this.month = month;
        this.year = year;
        peg(getCalendarInstance());
    }

    /**
     * Constructs a new month instance.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param month  the month (in the range 1 to 12).
     * @param year  the year.
     */
    public Month(int month, Year year) {
        if ((month < 1) || (month > 12)) {
            throw new IllegalArgumentException("Month outside valid range.");
        }
        this.month = month;
        this.year = year.getYear();
        peg(getCalendarInstance());
    }

    /**
     * Constructs a new {@code Month} instance, based on a date/time.
     * The time zone and locale are determined by the calendar
     * returned by {@link RegularTimePeriod#getCalendarInstance()}.
     *
     * @param time  the date/time ({@code null} not permitted).
     *
     * @see #Month(Date, TimeZone, Locale)
     */
    public Month(Date time) {
        this(time, getCalendarInstance());
    }

    /**
     * Creates a new {@code Month} instance, based on the specified time,
     * zone and locale.
     *
     * @param time  the current time.
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @since 1.0.12
     */
    public Month(Date time, TimeZone zone, Locale locale) {
        Calendar calendar = Calendar.getInstance(zone, locale);
        calendar.setTime(time);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
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
    public Month(Date time, Calendar calendar) {
        calendar.setTime(time);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
        peg(calendar);
    }

    /**
     * Returns the year in which the month falls.
     *
     * @return The year in which the month falls (as a Year object).
     */
    public Year getYear() {
        return new Year(this.year);
    }

    /**
     * Returns the year in which the month falls.
     *
     * @return The year in which the month falls (as an int).
     */
    public int getYearValue() {
        return this.year;
    }

    /**
     * Returns the month.  Note that 1=JAN, 2=FEB, ...
     *
     * @return The month.
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the first millisecond of the month.  This will be determined
     * relative to the time zone specified in the constructor, or in the
     * calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The first millisecond of the month.
     *
     * @see #getLastMillisecond()
     */
    @Override
    public long getFirstMillisecond() {
        return this.firstMillisecond;
    }

    /**
     * Returns the last millisecond of the month.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The last millisecond of the month.
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
     * Returns the month preceding this one.  Note that the returned
     * {@link Month} is "pegged" using the default calendar, obtained
     * with {@link RegularTimePeriod#getCalendarInstance()}, irrespective of
     * the time-zone used to peg of the current month (which is not recorded
     * anywhere).  See the {@link #peg(Calendar)} method.
     *
     * @return The month preceding this one.
     */
    @Override
    public RegularTimePeriod previous() {
        Month result;
        if (this.month != MonthConstants.JANUARY) {
            result = new Month(this.month - 1, this.year);
        }
        else {
            if (this.year > 1900) {
                result = new Month(MonthConstants.DECEMBER, this.year - 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns the month following this one.  Note that the returned
     * {@link Month} is "pegged" using the default calendar, obtained
     * with {@link RegularTimePeriod#getCalendarInstance()}, irrespective of
     * the time-zone used to peg of the current month (which is not recorded
     * anywhere).  See the {@link #peg(Calendar)} method.
     *
     * @return The month following this one.
     */
    @Override
    public RegularTimePeriod next() {
        Month result;
        if (this.month != MonthConstants.DECEMBER) {
            result = new Month(this.month + 1, this.year);
        }
        else {
            if (this.year < 9999) {
                result = new Month(MonthConstants.JANUARY, this.year + 1);
            }
            else {
                result = null;
            }
        }
        return result;
    }

    /**
     * Returns a serial index number for the month.
     *
     * @return The serial index number.
     */
    @Override
    public long getSerialIndex() {
        return this.year * 12L + this.month;
    }

    /**
     * Returns a string representing the month (e.g. "January 2002").
     * <P>
     * To do: look at internationalisation.
     *
     * @return A string representing the month.
     */
    @Override
    public String toString() {
        return SerialDate.monthCodeToString(this.month) + " " + this.year;
    }

    /**
     * Tests the equality of this Month object to an arbitrary object.
     * Returns true if the target is a Month instance representing the same
     * month as this object.  In all other cases, returns false.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} if month and year of this and object are the
     *         same.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Month)) {
            return false;
        }
        Month that = (Month) obj;
        if (this.month != that.month) {
            return false;
        }
        if (this.year != that.year) {
            return false;
        }
        return true;
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
        result = 37 * result + this.month;
        result = 37 * result + this.year;
        return result;
    }

    /**
     * Returns an integer indicating the order of this Month object relative to
     * the specified
     * object: negative == before, zero == same, positive == after.
     *
     * @param o1  the object to compare.
     *
     * @return negative == before, zero == same, positive == after.
     */
    @Override
    public int compareTo(Object o1) {

        int result;

        // CASE 1 : Comparing to another Month object
        // --------------------------------------------
        if (o1 instanceof Month) {
            Month m = (Month) o1;
            result = this.year - m.getYearValue();
            if (result == 0) {
                result = this.month - m.getMonth();
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
     * Returns the first millisecond of the month, evaluated using the supplied
     * calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The first millisecond of the month.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getFirstMillisecond(Calendar calendar) {
        calendar.set(this.year, this.month - 1, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * Returns the last millisecond of the month, evaluated using the supplied
     * calendar (which determines the time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The last millisecond of the month.
     *
     * @throws NullPointerException if {@code calendar} is
     *     {@code null}.
     */
    @Override
    public long getLastMillisecond(Calendar calendar) {
        int eom = SerialDate.lastDayOfMonth(this.month, this.year);
        calendar.set(this.year, this.month - 1, eom, 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * Parses the string argument as a month.  This method is required to
     * accept the format "YYYY-MM".  It will also accept "MM-YYYY". Anything
     * else, at the moment, is a bonus.
     *
     * @param s  the string to parse ({@code null} permitted).
     *
     * @return {@code null} if the string is not parseable, the month
     *         otherwise.
     */
    public static Month parseMonth(String s) {
        Month result = null;
        if (s == null) {
            return result;
        }
        // trim whitespace from either end of the string
        s = s.trim();
        int i = Month.findSeparator(s);
        String s1, s2;
        boolean yearIsFirst;
        // if there is no separator, we assume the first four characters
        // are YYYY
        if (i == -1) {
            yearIsFirst = true;
            s1 = s.substring(0, 5);
            s2 = s.substring(5);
        }
        else {
            s1 = s.substring(0, i).trim();
            s2 = s.substring(i + 1, s.length()).trim();
            // now it is trickier to determine if the month or year is first
            Year y1 = Month.evaluateAsYear(s1);
            if (y1 == null) {
                yearIsFirst = false;
            }
            else {
                Year y2 = Month.evaluateAsYear(s2);
                if (y2 == null) {
                    yearIsFirst = true;
                }
                else {
                    yearIsFirst = (s1.length() > s2.length());
                }
            }
        }
        Year year;
        int month;
        if (yearIsFirst) {
            year = Month.evaluateAsYear(s1);
            month = SerialDate.stringToMonthCode(s2);
        }
        else {
            year = Month.evaluateAsYear(s2);
            month = SerialDate.stringToMonthCode(s1);
        }
        if (month == -1) {
            throw new TimePeriodFormatException("Can't evaluate the month.");
        }
        if (year == null) {
            throw new TimePeriodFormatException("Can't evaluate the year.");
        }
        result = new Month(month, year);
        return result;
    }

    /**
     * Finds the first occurrence of '-', or if that character is not found,
     * the first occurrence of ',', or the first occurrence of ' ' or '.'
     *
     * @param s  the string to parse.
     *
     * @return The position of the separator character, or {@code -1} if
     *     none of the characters were found.
     */
    private static int findSeparator(String s) {
        int result = s.indexOf('-');
        if (result == -1) {
            result = s.indexOf(',');
        }
        if (result == -1) {
            result = s.indexOf(' ');
        }
        if (result == -1) {
            result = s.indexOf('.');
        }
        return result;
    }

    /**
     * Creates a year from a string, or returns {@code null} (format
     * exceptions suppressed).
     *
     * @param s  the string to parse.
     *
     * @return {@code null} if the string is not parseable, the year
     *         otherwise.
     */
    private static Year evaluateAsYear(String s) {
        Year result = null;
        try {
            result = Year.parseYear(s);
        }
        catch (TimePeriodFormatException e) {
            // suppress
        }
        return result;
    }

}
