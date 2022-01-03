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
 * MinuteTest.java
 * ---------------
 * (C) Copyright 2002-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Consumer;

import org.jfree.chart.TestUtils;
import org.jfree.chart.date.MonthConstants;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Minute} class.
 */
public class MinuteTest {

    /**
     * Check that a Minute instance is equal to itself.
     *
     * SourceForge Bug ID: 558850.
     */
    @Test
    public void testEqualsSelf() {
        Minute minute = new Minute();
        assertEquals(minute, minute);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        Day day1 = new Day(29, MonthConstants.MARCH, 2002);
        Hour hour1 = new Hour(15, day1);
        Minute minute1 = new Minute(15, hour1);
        Day day2 = new Day(29, MonthConstants.MARCH, 2002);
        Hour hour2 = new Hour(15, day2);
        Minute minute2 = new Minute(15, hour2);
        assertEquals(minute1, minute2);
    }

    /**
     * In GMT, the 4.55pm on 21 Mar 2002 is java.util.Date(1016729700000L).
     * Use this to check the Minute constructor.
     */
    @Test
    public void testDateConstructor1() {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        Calendar cal = Calendar.getInstance(zone);
        Locale locale = Locale.getDefault(); // locale should not matter here
        Minute m1 = new Minute(new Date(1016729699999L), zone, locale);
        Minute m2 = new Minute(new Date(1016729700000L), zone, locale);

        assertEquals(54, m1.getMinute());
        assertEquals(1016729699999L, m1.getLastMillisecond(cal));

        assertEquals(55, m2.getMinute());
        assertEquals(1016729700000L, m2.getFirstMillisecond(cal));
    }

    /**
     * In Singapore, the 4.55pm on 21 Mar 2002 is
     * java.util.Date(1,014,281,700,000L). Use this to check the Minute
     * constructor.
     */
    @Test
    public void testDateConstructor2() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Singapore");
        Calendar cal = Calendar.getInstance(zone);
        Locale locale = Locale.getDefault(); // locale should not matter here
        Minute m1 = new Minute(new Date(1016700899999L), zone, locale);
        Minute m2 = new Minute(new Date(1016700900000L), zone, locale);

        assertEquals(54, m1.getMinute());
        assertEquals(1016700899999L, m1.getLastMillisecond(cal));

        assertEquals(55, m2.getMinute());
        assertEquals(1016700900000L, m2.getFirstMillisecond(cal));
    }

    /**
     * If a thread-local calendar was set, the Date constructor should use it.
     */
    @Test
    public void testDateConstructorWithThreadLocalCalendar() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setThreadLocalCalendarInstance(
                Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testDateConstructorWithCustomCalendar(3, calendarSetup);
        testDateConstructorWithCustomCalendar(4, calendarSetup);
    }

    /**
     * If a calendar prototype was set, the Date constructor should use it.
     */
    @Test
    public void testDateConstructorWithCalendarPrototype() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setCalendarInstancePrototype(
                Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testDateConstructorWithCustomCalendar(3, calendarSetup);
        testDateConstructorWithCustomCalendar(4, calendarSetup);
    }

    private void testDateConstructorWithCustomCalendar(int hoursOffset, Consumer<Integer> calendarSetup) {
        try {
            calendarSetup.accept(hoursOffset);
            Minute m = new Minute(new Date(0L));
            assertEquals(1970, m.getHour().getYear());
            assertEquals(1, m.getHour().getMonth());
            assertEquals(1, m.getHour().getDayOfMonth());
            assertEquals(hoursOffset, m.getHour().getHour());
            assertEquals(0, m.getMinute());
            assertEquals(0L, m.getFirstMillisecond());
        } finally {
            // reset everything, to avoid affecting other tests
            RegularTimePeriod.setThreadLocalCalendarInstance(null);
            RegularTimePeriod.setCalendarInstancePrototype(null);
        }
    }

    /**
     * If a thread-local calendar was set, the minute-hour constructor should use it.
     */
    @Test
    public void testMinuteHourConstructorWithThreadLocalCalendar() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setThreadLocalCalendarInstance(
                Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testMinuteHourConstructorWithCustomCalendar(3, calendarSetup);
        testMinuteHourConstructorWithCustomCalendar(4, calendarSetup);
    }

    /**
     * If a calendar prototype was set, the minute-hour constructor should use it.
     */
    @Test
    public void testMinuteHourConstructorWithCalendarPrototype() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setCalendarInstancePrototype(
                Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testMinuteHourConstructorWithCustomCalendar(3, calendarSetup);
        testMinuteHourConstructorWithCustomCalendar(4, calendarSetup);
    }

    private void testMinuteHourConstructorWithCustomCalendar(int hoursOffset, Consumer<Integer> calendarSetup) {
        try {
            calendarSetup.accept(hoursOffset);
            Minute m = new Minute(0, new Hour(new Date(0L)));
            assertEquals(1970, m.getHour().getYear());
            assertEquals(1, m.getHour().getMonth());
            assertEquals(1, m.getHour().getDayOfMonth());
            assertEquals(hoursOffset, m.getHour().getHour());
            assertEquals(0, m.getMinute());
            assertEquals(0L, m.getFirstMillisecond());
        } finally {
            // reset everything, to avoid affecting other tests
            RegularTimePeriod.setThreadLocalCalendarInstance(null);
            RegularTimePeriod.setCalendarInstancePrototype(null);
        }
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Minute m1 = new Minute();
        Minute m2 = TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        Minute m1 = new Minute(45, 5, 1, 2, 2003);
        Minute m2 = new Minute(45, 5, 1, 2, 2003);
        assertEquals(m1, m2);
        int h1 = m1.hashCode();
        int h2 = m2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * The {@link Minute} class is immutable, so should not be
     * {@link Cloneable}.
     */
    @Test
    public void testNotCloneable() {
        Minute m = new Minute(45, 5, 1, 2, 2003);
        assertFalse(m instanceof Cloneable);
    }

    /**
     * Some checks for the getFirstMillisecond() method.
     */
    @Test
    public void testGetFirstMillisecond() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Minute m = new Minute(43, 15, 1, 4, 2006);
        assertEquals(1143902580000L, m.getFirstMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithTimeZone() {
        Minute m = new Minute(59, 15, 1, 4, 1950);
        TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar cal = Calendar.getInstance(zone);
        assertEquals(-623289660000L, m.getFirstMillisecond(cal));

        // try null calendar
        boolean pass = false;
        try {
            m.getFirstMillisecond((Calendar) null);
        }
        catch (NullPointerException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getFirstMillisecond(TimeZone) method.
     */
    @Test
    public void testGetFirstMillisecondWithCalendar() {
        Minute m = new Minute(40, 2, 15, 4, 2000);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(955766400000L, m.getFirstMillisecond(calendar));

        // try null calendar
        boolean pass = false;
        try {
            m.getFirstMillisecond((Calendar) null);
        }
        catch (NullPointerException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getLastMillisecond() method.
     */
    @Test
    public void testGetLastMillisecond() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.UK);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/London"));
        Minute m = new Minute(1, 1, 1, 1, 1970);
        assertEquals(119999L, m.getLastMillisecond());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithTimeZone() {
        Minute m = new Minute(1, 2, 7, 7, 1950);
        TimeZone zone = TimeZone.getTimeZone("America/Los_Angeles");
        Calendar cal = Calendar.getInstance(zone);
        assertEquals(-614962680001L, m.getLastMillisecond(cal));

        // try null calendar
        boolean pass = false;
        try {
            m.getLastMillisecond((Calendar) null);
        }
        catch (NullPointerException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getLastMillisecond(TimeZone) method.
     */
    @Test
    public void testGetLastMillisecondWithCalendar() {
        Minute m = new Minute(45, 21, 21, 4, 2001);
        GregorianCalendar calendar = new GregorianCalendar(Locale.GERMANY);
        calendar.setTimeZone(TimeZone.getTimeZone("Europe/Frankfurt"));
        assertEquals(987889559999L, m.getLastMillisecond(calendar));

        // try null calendar
        boolean pass = false;
        try {
            m.getLastMillisecond((Calendar) null);
        }
        catch (NullPointerException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getSerialIndex() method.
     */
    @Test
    public void testGetSerialIndex() {
        Minute m = new Minute(1, 1, 1, 1, 2000);
        assertEquals(52597501L, m.getSerialIndex());
        m = new Minute(1, 1, 1, 1, 1900);
        assertEquals(2941L, m.getSerialIndex());
    }

    @Test
    public void testNext() {
        Minute m = new Minute(30, 1, 12, 12, 2000);
        m = (Minute) m.next();
        assertEquals(2000, m.getHour().getYear());
        assertEquals(12, m.getHour().getMonth());
        assertEquals(12, m.getHour().getDayOfMonth());
        assertEquals(1, m.getHour().getHour());
        assertEquals(31, m.getMinute());
        m = (Minute) m.previous();
        assertEquals(2000, m.getHour().getYear());
        assertEquals(12, m.getHour().getMonth());
        assertEquals(12, m.getHour().getDayOfMonth());
        assertEquals(1, m.getHour().getHour());
        assertEquals(30, m.getMinute());
        m = new Minute(59, 23, 31, 12, 9999);
        assertNull(m.next());
    }

    /**
     * If a thread-local calendar was set, next() and previous() should use its time zone.
     */
    @Test
    public void testNextPreviousWithThreadLocalCalendar() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setThreadLocalCalendarInstance(
                        Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testNextPreviousWithCustomCalendar(3, calendarSetup);
        testNextPreviousWithCustomCalendar(4, calendarSetup);
    }

    /**
     * If a calendar prototype was set, next() should use its time zone.
     */
    @Test
    public void testNextPreviousWithCalendarPrototype() {
        Consumer<Integer> calendarSetup = hours -> RegularTimePeriod.setCalendarInstancePrototype(
                        Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.ofHours(hours)))
        );
        testNextPreviousWithCustomCalendar(3, calendarSetup);
        testNextPreviousWithCustomCalendar(4, calendarSetup);
    }

    private void testNextPreviousWithCustomCalendar(int hoursOffset, Consumer<Integer> calendarSetup) {
        try {
            calendarSetup.accept(hoursOffset);
            Minute m = new Minute(new Date(0L));
            m = (Minute) m.next();
            assertEquals(1970, m.getHour().getYear());
            assertEquals(1, m.getHour().getMonth());
            assertEquals(1, m.getHour().getDayOfMonth());
            assertEquals(hoursOffset, m.getHour().getHour());
            assertEquals(1, m.getMinute());
            assertEquals(60_000L, m.getFirstMillisecond());
            m = (Minute) m.previous();
            assertEquals(1970, m.getHour().getYear());
            assertEquals(1, m.getHour().getMonth());
            assertEquals(1, m.getHour().getDayOfMonth());
            assertEquals(hoursOffset, m.getHour().getHour());
            assertEquals(0, m.getMinute());
            assertEquals(0L, m.getFirstMillisecond());
        } finally {
            // reset everything, to avoid affecting other tests
            RegularTimePeriod.setThreadLocalCalendarInstance(null);
            RegularTimePeriod.setCalendarInstancePrototype(null);
        }
    }

    /**
     * Some checks for the getStart() method.
     */
    @Test
    public void testGetStart() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.set(2006, Calendar.JANUARY, 16, 3, 47, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Minute m = new Minute(47, 3, 16, 1, 2006);
        assertEquals(cal.getTime(), m.getStart());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Some checks for the getEnd() method.
     */
    @Test
    public void testGetEnd() {
        Locale saved = Locale.getDefault();
        Locale.setDefault(Locale.ITALY);
        TimeZone savedZone = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Rome"));
        Calendar cal = Calendar.getInstance(Locale.ITALY);
        cal.set(2006, Calendar.JANUARY, 16, 3, 47, 59);
        cal.set(Calendar.MILLISECOND, 999);
        Minute m = new Minute(47, 3, 16, 1, 2006);
        assertEquals(cal.getTime(), m.getEnd());
        Locale.setDefault(saved);
        TimeZone.setDefault(savedZone);
    }

    /**
     * Test for bug 1611872 - previous() fails for first minute in hour.
     */
    @Test
    public void test1611872() {
        Minute m1 = new Minute(0, 10, 15, 4, 2000);
        Minute m2 = (Minute) m1.previous();
        assertEquals(m2, new Minute(59, 9, 15, 4, 2000));
    }

}
