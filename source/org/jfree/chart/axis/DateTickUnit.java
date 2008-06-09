/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -----------------
 * DateTickUnit.java
 * -----------------
 * (C) Copyright 2000-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Chris Boek;
 *
 * Changes
 * -------
 * 08-Nov-2002 : Moved to new package com.jrefinery.chart.axis (DG);
 * 27-Nov-2002 : Added IllegalArgumentException to getMillisecondCount()
 *               method (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 12-Nov-2003 : Added roll fields that can improve the labelling on segmented
 *               date axes (DG);
 * 03-Dec-2003 : DateFormat constructor argument is now filled with an default
 *               if null (TM);
 * 07-Dec-2003 : Fixed bug (null pointer exception) in constructor (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 21-Mar-2007 : Added toString() for debugging (DG);
 * 04-Apr-2007 : Added new methods addToDate(Date, TimeZone) and rollDate(Date,
 *               TimeZone) (CB);
 * 09-Jun-2008 : Deprecated addToDate(Date) (DG);
 *
 */

package org.jfree.chart.axis;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jfree.util.ObjectUtilities;

/**
 * A tick unit for use by subclasses of {@link DateAxis}. Instances of this
 * class are immutable.
 */
public class DateTickUnit extends TickUnit implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -7289292157229621901L;

    /** A constant for years. */
    public static final int YEAR = 0;

    /** A constant for months. */
    public static final int MONTH = 1;

    /** A constant for days. */
    public static final int DAY = 2;

    /** A constant for hours. */
    public static final int HOUR = 3;

    /** A constant for minutes. */
    public static final int MINUTE = 4;

    /** A constant for seconds. */
    public static final int SECOND = 5;

    /** A constant for milliseconds. */
    public static final int MILLISECOND = 6;

    /** The unit. */
    private int unit;

    /** The unit count. */
    private int count;

    /** The roll unit. */
    private int rollUnit;

    /** The roll count. */
    private int rollCount;

    /** The date formatter. */
    private DateFormat formatter;

    /**
     * Creates a new date tick unit.  The dates will be formatted using a
     * SHORT format for the default locale.
     *
     * @param unit  the unit.
     * @param count  the unit count.
     */
    public DateTickUnit(int unit, int count) {
        this(unit, count, null);
    }

    /**
     * Creates a new date tick unit.  You can specify the units using one of
     * the constants YEAR, MONTH, DAY, HOUR, MINUTE, SECOND or MILLISECOND.
     * In addition, you can specify a unit count, and a date format.
     *
     * @param unit  the unit.
     * @param count  the unit count.
     * @param formatter  the date formatter (defaults to DateFormat.SHORT).
     */
    public DateTickUnit(int unit, int count, DateFormat formatter) {

        this(unit, count, unit, count, formatter);

    }

    /**
     * Creates a new unit.
     *
     * @param unit  the unit.
     * @param count  the count.
     * @param rollUnit  the roll unit.
     * @param rollCount  the roll count.
     * @param formatter  the date formatter (defaults to DateFormat.SHORT).
     */
    public DateTickUnit(int unit, int count, int rollUnit, int rollCount,
                        DateFormat formatter) {
        super(DateTickUnit.getMillisecondCount(unit, count));
        this.unit = unit;
        this.count = count;
        this.rollUnit = rollUnit;
        this.rollCount = rollCount;
        this.formatter = formatter;
        if (formatter == null) {
            this.formatter = DateFormat.getDateInstance(DateFormat.SHORT);
        }
    }

    /**
     * Returns the date unit.  This will be one of the constants
     * <code>YEAR</code>, <code>MONTH</code>, <code>DAY</code>,
     * <code>HOUR</code>, <code>MINUTE</code>, <code>SECOND</code> or
     * <code>MILLISECOND</code>, defined by this class.  Note that these
     * constants do NOT correspond to those defined in Java's
     * <code>Calendar</code> class.
     *
     * @return The date unit.
     */
    public int getUnit() {
        return this.unit;
    }

    /**
     * Returns the unit count.
     *
     * @return The unit count.
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Returns the roll unit.  This is the amount by which the tick advances if
     * it is "hidden" when displayed on a segmented date axis.  Typically the
     * roll will be smaller than the regular tick unit (for example, a 7 day
     * tick unit might use a 1 day roll).
     *
     * @return The roll unit.
     */
    public int getRollUnit() {
        return this.rollUnit;
    }

    /**
     * Returns the roll count.
     *
     * @return The roll count.
     */
    public int getRollCount() {
        return this.rollCount;
    }

    /**
     * Formats a value.
     *
     * @param milliseconds  date in milliseconds since 01-01-1970.
     *
     * @return The formatted date.
     */
    public String valueToString(double milliseconds) {
        return this.formatter.format(new Date((long) milliseconds));
    }

    /**
     * Formats a date using the tick unit's formatter.
     *
     * @param date  the date.
     *
     * @return The formatted date.
     */
    public String dateToString(Date date) {
        return this.formatter.format(date);
    }

    /**
     * Calculates a new date by adding this unit to the base date, with
     * calculations performed in the default timezone and locale.
     *
     * @param base  the base date.
     *
     * @return A new date one unit after the base date.
     *
     * @see #addToDate(Date, TimeZone)
     *
     * @deprecated As of JFreeChart 1.0.10, this method is deprecated - you
     *     should use {@link #addToDate(Date, TimeZone)} instead.
     */
    public Date addToDate(Date base) {
    	return addToDate(base, TimeZone.getDefault());
    }

    /**
     * Calculates a new date by adding this unit to the base date.
     *
     * @param base  the base date.
     * @param zone  the time zone for the date calculation.
     *
     * @return A new date one unit after the base date.
     *
     * @since 1.0.6
     */
    public Date addToDate(Date base, TimeZone zone) {
        // as far as I know, the Locale for the calendar only affects week
    	// number calculations, and since DateTickUnit doesn't do week
    	// arithmetic, the default locale (whatever it is) should be fine
    	// here...
    	Calendar calendar = Calendar.getInstance(zone);
        calendar.setTime(base);
        calendar.add(getCalendarField(this.unit), this.count);
        return calendar.getTime();
    }

    /**
     * Rolls the date forward by the amount specified by the roll unit and
     * count.
     *
     * @param base  the base date.

     * @return The rolled date.
     *
     * @see #rollDate(Date, TimeZone)
     */
    public Date rollDate(Date base) {
    	return rollDate(base, TimeZone.getDefault());
    }

    /**
     * Rolls the date forward by the amount specified by the roll unit and
     * count.
     *
     * @param base  the base date.
     * @param zone  the time zone.
     *
     * @return The rolled date.
     *
     * @since 1.0.6
     */
    public Date rollDate(Date base, TimeZone zone) {
        // as far as I know, the Locale for the calendar only affects week
    	// number calculations, and since DateTickUnit doesn't do week
    	// arithmetic, the default locale (whatever it is) should be fine
    	// here...
        Calendar calendar = Calendar.getInstance(zone);
        calendar.setTime(base);
        calendar.add(getCalendarField(this.rollUnit), this.rollCount);
        return calendar.getTime();
    }

    /**
     * Returns a field code that can be used with the <code>Calendar</code>
     * class.
     *
     * @return The field code.
     */
    public int getCalendarField() {
        return getCalendarField(this.unit);
    }

    /**
     * Returns a field code (that can be used with the Calendar class) for a
     * given 'unit' code.  The 'unit' is one of:  {@link #YEAR}, {@link #MONTH},
     * {@link #DAY}, {@link #HOUR}, {@link #MINUTE}, {@link #SECOND} and
     * {@link #MILLISECOND}.
     *
     * @param tickUnit  the unit.
     *
     * @return The field code.
     */
    private int getCalendarField(int tickUnit) {

        switch (tickUnit) {
            case (YEAR):
                return Calendar.YEAR;
            case (MONTH):
                return Calendar.MONTH;
            case (DAY):
                return Calendar.DATE;
            case (HOUR):
                return Calendar.HOUR_OF_DAY;
            case (MINUTE):
                return Calendar.MINUTE;
            case (SECOND):
                return Calendar.SECOND;
            case (MILLISECOND):
                return Calendar.MILLISECOND;
            default:
                return Calendar.MILLISECOND;
        }

    }

    /**
     * Returns the (approximate) number of milliseconds for the given unit and
     * unit count.
     * <P>
     * This value is an approximation some of the time (e.g. months are
     * assumed to have 31 days) but this shouldn't matter.
     *
     * @param unit  the unit.
     * @param count  the unit count.
     *
     * @return The number of milliseconds.
     */
    private static long getMillisecondCount(int unit, int count) {

        switch (unit) {
            case (YEAR):
                return (365L * 24L * 60L * 60L * 1000L) * count;
            case (MONTH):
                return (31L * 24L * 60L * 60L * 1000L) * count;
            case (DAY):
                return (24L * 60L * 60L * 1000L) * count;
            case (HOUR):
                return (60L * 60L * 1000L) * count;
            case (MINUTE):
                return (60L * 1000L) * count;
            case (SECOND):
                return 1000L * count;
            case (MILLISECOND):
                return count;
            default:
                throw new IllegalArgumentException(
                    "DateTickUnit.getMillisecondCount() : unit must "
                    + "be one of the constants YEAR, MONTH, DAY, HOUR, MINUTE, "
                    + "SECOND or MILLISECOND defined in the DateTickUnit "
                    + "class. Do *not* use the constants defined in "
                    + "java.util.Calendar."
                );
        }

    }

    /**
     * Tests this unit for equality with another object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return <code>true</code> or <code>false</code>.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DateTickUnit)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        DateTickUnit that = (DateTickUnit) obj;
        if (this.unit != that.unit) {
            return false;
        }
        if (this.count != that.count) {
            return false;
        }
        if (!ObjectUtilities.equal(this.formatter, that.formatter)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this object.
     *
     * @return A hash code.
     */
    public int hashCode() {
        int result = 19;
        result = 37 * result + this.unit;
        result = 37 * result + this.count;
        result = 37 * result + this.formatter.hashCode();
        return result;
    }

    /**
     * Strings for use by the toString() method.
     */
    private static final String[] units = {"YEAR", "MONTH", "DAY", "HOUR",
            "MINUTE", "SECOND", "MILLISECOND"};

    /**
     * Returns a string representation of this instance, primarily used for
     * debugging purposes.
     *
     * @return A string representation of this instance.
     */
    public String toString() {
        return "DateTickUnit[" + DateTickUnit.units[this.unit] + ", "
                + this.count + "]";
    }

}
