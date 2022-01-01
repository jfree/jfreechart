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
 * ----------------------
 * RegularTimePeriod.java
 * ----------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 * 
 */

package org.jfree.data.time;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

import org.jfree.chart.date.MonthConstants;

/**
 * An abstract class representing a unit of time.  Convenient methods are
 * provided for calculating the next and previous time periods.  Conversion
 * methods are defined that return the first and last milliseconds of the time
 * period.  The results from these methods are timezone dependent.
 * <P>
 * This class is immutable, and all subclasses should be immutable also.
 */
public abstract class RegularTimePeriod implements TimePeriod, Comparable,
        MonthConstants {

    private static final AtomicReference<Calendar> calendarPrototype = new AtomicReference<>();

    private static final ThreadLocal<Calendar> threadLocalCalendar = new ThreadLocal<>();

    /**
     * Creates a time period that includes the specified millisecond, assuming
     * the given time zone.
     *
     * @param c  the time period class.
     * @param millisecond  the time.
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @return The time period.
     */
    public static RegularTimePeriod createInstance(Class c, Date millisecond,
            TimeZone zone, Locale locale) {
        RegularTimePeriod result = null;
        try {
            Constructor constructor = c.getDeclaredConstructor(
                    new Class[] {Date.class, TimeZone.class, Locale.class});
            result = (RegularTimePeriod) constructor.newInstance(
                    new Object[] {millisecond, zone, locale});
        }
        catch (Exception e) {
            // do nothing, so null is returned
        }
        return result;
    }

    /**
     * Returns a subclass of {@link RegularTimePeriod} that is smaller than
     * the specified class.
     *
     * @param c  a subclass of {@link RegularTimePeriod}.
     *
     * @return A class.
     */
    public static Class downsize(Class c) {
        if (c.equals(Year.class)) {
            return Quarter.class;
        }
        else if (c.equals(Quarter.class)) {
            return Month.class;
        }
        else if (c.equals(Month.class)) {
            return Day.class;
        }
        else if (c.equals(Day.class)) {
            return Hour.class;
        }
        else if (c.equals(Hour.class)) {
            return Minute.class;
        }
        else if (c.equals(Minute.class)) {
            return Second.class;
        }
        else if (c.equals(Second.class)) {
            return Millisecond.class;
        }
        else {
            return Millisecond.class;
        }
    }

    /**
     * Creates or returns a thread-local Calendar instance.
     * This function is used by the various subclasses to obtain a calendar for
     * date-time to/from ms-since-epoch conversions (and to determine
     * the first day of the week, in case of {@link Week}).
     * <p>
     * If a thread-local calendar was set with {@link #setThreadLocalCalendarInstance(Calendar)},
     * then it is simply returned.
     * <p>
     * Otherwise, If a global calendar prototype was set with {@link #setCalendarInstancePrototype(Calendar)},
     * then it is cloned and set as the thread-local calendar instance for future use,
     * as if it was set with {@link #setThreadLocalCalendarInstance(Calendar)}.
     * <p>
     * Otherwise, if neither is set, a new instance will be created every
     * time with {@link Calendar#getInstance()}, resorting to JFreeChart 1.5.0
     * behavior (leading to huge load on GC and high memory consumption
     * if many instances are created).
     *
     * @return a thread-local Calendar instance
     */
    protected static Calendar getCalendarInstance() {
        Calendar calendar = threadLocalCalendar.get();
        if (calendar == null) {
            Calendar prototype = calendarPrototype.get();
            if (prototype != null) {
                calendar = (Calendar) prototype.clone();
                threadLocalCalendar.set(calendar);
            }
        }
        return calendar != null ? calendar : Calendar.getInstance();
    }

    /**
     * Sets the thread-local calendar instance for time calculations.
     * <p>
     * {@code RegularTimePeriod} instances sometimes need a {@link Calendar}
     * to perform time calculations (date-time from/to milliseconds-since-epoch).
     * In JFreeChart 1.5.0, they created a new {@code Calendar} instance
     * every time they needed one.  This created a huge load on GC and lead
     * to high memory consumption.  To avoid this, a thread-local {@code Calendar}
     * instance can be set, which will then be used for time calculations
     * every time, unless the caller passes a specific {@code Calendar}
     * instance in places where the API allows it.
     * <p>
     * If the specified calendar is {@code null}, or if this method was never called,
     * then the next time a calendar instance is needed, a new one will be created by cloning
     * the global prototype set with {@link #setCalendarInstancePrototype(Calendar)}.
     * If none was set either, then a new instance will be created every time
     * with {@link Calendar#getInstance()}, resorting to JFreeChart 1.5.0 behavior.
     *
     * @param calendar the new thread-local calendar instance
     */
    public static void setThreadLocalCalendarInstance(Calendar calendar) {
        threadLocalCalendar.set(calendar);
    }


    /**
     * Sets a global calendar prototype for time calculations.
     * <p>
     * {@code RegularTimePeriod} instances sometimes need a {@link Calendar}
     * to perform time calculations (date-time from/to milliseconds-since-epoch).
     * In JFreeChart 1.5.0, they created a new {@code Calendar} instance
     * every time they needed one.  This created a huge load on GC and lead
     * to high memory consumption.  To avoid this, a prototype {@code Calendar}
     * can be set, which will be then cloned by every thread that needs
     * a {@code Calendar} instance.  The prototype is not cloned right away,
     * and stored instead for later cloning, therefore the caller must not
     * alter the prototype after it has been passed to this method.
     * <p>
     * If the prototype is {@code null}, then thread-local calendars
     * set with {@link #setThreadLocalCalendarInstance(Calendar)} will be
     * used instead.  If none was set for some thread, then a new instance will be
     * created with {@link Calendar#getInstance()} every time one is needed.
     * However, if the prototype was already cloned by some thread,
     * then setting it to {@code null} has no effect, and that thread must
     * explicitly set its own instance to {@code null} or something else to get
     * rid of the cloned calendar.
     * <p>
     * Calling {@code setCalendarInstancePrototype(Calendar.getInstance())}
     * somewhere early in an application will effectively mimic JFreeChart
     * 1.5.0 behavior (using the default calendar everywhere unless explicitly
     * specified), while preventing the many-allocations problem.  There is one
     * important caveat, however: once a prototype is cloned by some
     * thread, calling {@link TimeZone#setDefault(TimeZone)}
     * or {@link Locale#setDefault(Locale)}} will have no
     * effect on future calculations.  To avoid this problem, simply set
     * the default time zone and locale before setting the prototype.
     *
     * @param calendar the new thread-local calendar instance
     */
    public static void setCalendarInstancePrototype(Calendar calendar) {
        calendarPrototype.set(calendar);
    }

    /**
     * Returns the time period preceding this one, or {@code null} if some
     * lower limit has been reached.
     *
     * @return The previous time period (possibly {@code null}).
     */
    public abstract RegularTimePeriod previous();

    /**
     * Returns the time period following this one, or {@code null} if some
     * limit has been reached.
     *
     * @return The next time period (possibly {@code null}).
     */
    public abstract RegularTimePeriod next();

    /**
     * Returns a serial index number for the time unit.
     *
     * @return The serial index number.
     */
    public abstract long getSerialIndex();

    //////////////////////////////////////////////////////////////////////////

    /**
     * Recalculates the start date/time and end date/time for this time period
     * relative to the supplied calendar (which incorporates a time zone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @since 1.0.3
     */
    public abstract void peg(Calendar calendar);

    /**
     * Returns the date/time that marks the start of the time period.  This
     * method returns a new {@code Date} instance every time it is called.
     *
     * @return The start date/time.
     *
     * @see #getFirstMillisecond()
     */
    @Override
    public Date getStart() {
        return new Date(getFirstMillisecond());
    }

    /**
     * Returns the date/time that marks the end of the time period.  This
     * method returns a new {@code Date} instance every time it is called.
     *
     * @return The end date/time.
     *
     * @see #getLastMillisecond()
     */
    @Override
    public Date getEnd() {
        return new Date(getLastMillisecond());
    }

    /**
     * Returns the first millisecond of the time period.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The first millisecond of the time period.
     *
     * @see #getLastMillisecond()
     */
    public abstract long getFirstMillisecond();

    /**
     * Returns the first millisecond of the time period, evaluated using the
     * supplied calendar (which incorporates a timezone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The first millisecond of the time period.
     *
     * @throws NullPointerException if {@code calendar} is {@code null}.
     *
     * @see #getLastMillisecond(Calendar)
     */
    public abstract long getFirstMillisecond(Calendar calendar);

    /**
     * Returns the last millisecond of the time period.  This will be
     * determined relative to the time zone specified in the constructor, or
     * in the calendar instance passed in the most recent call to the
     * {@link #peg(Calendar)} method.
     *
     * @return The last millisecond of the time period.
     *
     * @see #getFirstMillisecond()
     */
    public abstract long getLastMillisecond();

    /**
     * Returns the last millisecond of the time period, evaluated using the
     * supplied calendar (which incorporates a timezone).
     *
     * @param calendar  the calendar ({@code null} not permitted).
     *
     * @return The last millisecond of the time period.
     *
     * @see #getFirstMillisecond(Calendar)
     */
    public abstract long getLastMillisecond(Calendar calendar);

    /**
     * Returns the millisecond closest to the middle of the time period.
     *
     * @return The middle millisecond.
     */
    public long getMiddleMillisecond() {
        long m1 = getFirstMillisecond();
        long m2 = getLastMillisecond();
        return m1 + (m2 - m1) / 2;
    }

    /**
     * Returns the millisecond closest to the middle of the time period,
     * evaluated using the supplied calendar (which incorporates a timezone).
     *
     * @param calendar  the calendar.
     *
     * @return The middle millisecond.
     */
    public long getMiddleMillisecond(Calendar calendar) {
        long m1 = getFirstMillisecond(calendar);
        long m2 = getLastMillisecond(calendar);
        return m1 + (m2 - m1) / 2;
    }

    /**
     * Returns the millisecond (relative to the epoch) corresponding to the 
     * specified {@code anchor} using the supplied {@code calendar} 
     * (which incorporates a time zone).
     * 
     * @param anchor  the anchor ({@code null} not permitted).
     * @param calendar  the calendar ({@code null} not permitted).
     * 
     * @return Milliseconds since the epoch.
     * 
     * @since 1.0.18
     */
    public long getMillisecond(TimePeriodAnchor anchor, Calendar calendar) {
        if (anchor.equals(TimePeriodAnchor.START)) {
            return getFirstMillisecond(calendar);
        } else if (anchor.equals(TimePeriodAnchor.MIDDLE)) {
            return getMiddleMillisecond(calendar);
        } else if (anchor.equals(TimePeriodAnchor.END)) {
            return getLastMillisecond(calendar);
        } else {
            throw new IllegalStateException("Unrecognised anchor: " + anchor);
        }
    }
    
    /**
     * Returns a string representation of the time period.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return String.valueOf(getStart());
    }

}
