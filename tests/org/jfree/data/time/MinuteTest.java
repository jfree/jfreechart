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
 * ---------------
 * MinuteTest.java
 * ---------------
 * (C) Copyright 2002-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Jan-2002 : Version 1 (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 13-Mar-2003 : Added serialization test (DG);
 * 21-Oct-2003 : Added hashCode test (DG);
 * 11-Jan-2005 : Added test for non-clonability (DG);
 * 05-Oct-2006 : Added new tests (DG);
 * 11-Dec-2006 : Added test1611872() (DG);
 * 11-Jul-2007 : Fixed bad time zone assumption (DG);
 *
 */

package org.jfree.data.time;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.chart.TestUtilities;

import org.jfree.date.MonthConstants;
import org.junit.Test;

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
        assertTrue(minute.equals(minute));
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
        assertTrue(minute1.equals(minute2));
    }

    /**
     * In GMT, the 4.55pm on 21 Mar 2002 is java.util.Date(1016729700000L).
     * Use this to check the Minute constructor.
     */
    @Test
    public void testDateConstructor1() {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        Locale locale = Locale.getDefault(); // locale should not matter here
        Minute m1 = new Minute(new Date(1016729699999L), zone, locale);
        Minute m2 = new Minute(new Date(1016729700000L), zone, locale);

        assertEquals(54, m1.getMinute());
        assertEquals(1016729699999L, m1.getLastMillisecond(zone));

        assertEquals(55, m2.getMinute());
        assertEquals(1016729700000L, m2.getFirstMillisecond(zone));
    }

    /**
     * In Singapore, the 4.55pm on 21 Mar 2002 is
     * java.util.Date(1,014,281,700,000L). Use this to check the Minute
     * constructor.
     */
    @Test
    public void testDateConstructor2() {
        TimeZone zone = TimeZone.getTimeZone("Asia/Singapore");
        Locale locale = Locale.getDefault(); // locale should not matter here
        Minute m1 = new Minute(new Date(1016700899999L), zone, locale);
        Minute m2 = new Minute(new Date(1016700900000L), zone, locale);

        assertEquals(54, m1.getMinute());
        assertEquals(1016700899999L, m1.getLastMillisecond(zone));

        assertEquals(55, m2.getMinute());
        assertEquals(1016700900000L, m2.getFirstMillisecond(zone));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Minute m1 = new Minute();
        Minute m2 = (Minute) TestUtilities.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        Minute m1 = new Minute(45, 5, 1, 2, 2003);
        Minute m2 = new Minute(45, 5, 1, 2, 2003);
        assertTrue(m1.equals(m2));
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
        assertEquals(-623289660000L, m.getFirstMillisecond(zone));

        // try null calendar
        boolean pass = false;
        try {
            m.getFirstMillisecond((TimeZone) null);
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
        assertEquals(-614962680001L, m.getLastMillisecond(zone));

        // try null calendar
        boolean pass = false;
        try {
            m.getLastMillisecond((TimeZone) null);
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

    /**
     * Some checks for the testNext() method.
     */
    @Test
    public void testNext() {
        Minute m = new Minute(30, 1, 12, 12, 2000);
        m = (Minute) m.next();
        assertEquals(2000, m.getHour().getYear());
        assertEquals(12, m.getHour().getMonth());
        assertEquals(12, m.getHour().getDayOfMonth());
        assertEquals(1, m.getHour().getHour());
        assertEquals(31, m.getMinute());
        m = new Minute(59, 23, 31, 12, 9999);
        assertNull(m.next());
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
