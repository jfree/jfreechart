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
 * -------------------
 * PeriodAxisTest.java
 * -------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jun-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() method (DG);
 * 08-Apr-2008 : Added test1932146() (DG);
 * 16-Jan-2009 : Added test2490803() (DG);
 * 02-Mar-2009 : Added testEqualsWithLocale (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import org.jfree.chart.TestUtilities;

import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.event.AxisChangeListener;
import org.jfree.data.Range;
import org.jfree.data.time.DateRange;
import org.jfree.data.time.Day;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.Second;
import org.jfree.data.time.Year;
import org.junit.Test;

/**
 * Tests for the {@link PeriodAxis} class.
 */
public class PeriodAxisTest implements AxisChangeListener {

    /** The last event received. */
    private AxisChangeEvent lastEvent;

    /**
     * Receives and records an {@link AxisChangeEvent}.
     *
     * @param event  the event.
     */
    @Override
    public void axisChanged(AxisChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        PeriodAxis a1 = new PeriodAxis("Test");
        PeriodAxis a2 = new PeriodAxis("Test");
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        a1.setFirst(new Year(2000));
        assertFalse(a1.equals(a2));
        a2.setFirst(new Year(2000));
        assertTrue(a1.equals(a2));

        a1.setLast(new Year(2004));
        assertFalse(a1.equals(a2));
        a2.setLast(new Year(2004));
        assertTrue(a1.equals(a2));

        a1.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
        assertFalse(a1.equals(a2));
        a2.setTimeZone(TimeZone.getTimeZone("Pacific/Auckland"));
        assertTrue(a1.equals(a2));

        a1.setAutoRangeTimePeriodClass(Quarter.class);
        assertFalse(a1.equals(a2));
        a2.setAutoRangeTimePeriodClass(Quarter.class);
        assertTrue(a1.equals(a2));

        PeriodAxisLabelInfo info[] = new PeriodAxisLabelInfo[1];
        info[0] = new PeriodAxisLabelInfo(Month.class,
                new SimpleDateFormat("MMM"));

        a1.setLabelInfo(info);
        assertFalse(a1.equals(a2));
        a2.setLabelInfo(info);
        assertTrue(a1.equals(a2));

        a1.setMajorTickTimePeriodClass(Minute.class);
        assertFalse(a1.equals(a2));
        a2.setMajorTickTimePeriodClass(Minute.class);
        assertTrue(a1.equals(a2));

        a1.setMinorTickMarksVisible(!a1.isMinorTickMarksVisible());
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarksVisible(a1.isMinorTickMarksVisible());
        assertTrue(a1.equals(a2));

        a1.setMinorTickTimePeriodClass(Minute.class);
        assertFalse(a1.equals(a2));
        a2.setMinorTickTimePeriodClass(Minute.class);
        assertTrue(a1.equals(a2));

        Stroke s = new BasicStroke(1.23f);
        a1.setMinorTickMarkStroke(s);
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarkStroke(s);
        assertTrue(a1.equals(a2));

        a1.setMinorTickMarkPaint(Color.blue);
        assertFalse(a1.equals(a2));
        a2.setMinorTickMarkPaint(Color.blue);
        assertTrue(a1.equals(a2));
    }

    /**
     * Confirm that the equals() method can distinguish the locale field (which
     * is new in version 1.0.13).
     */
    @Test
    public void testEqualsWithLocale() {
        PeriodAxis a1 = new PeriodAxis("Test", new Year(2000), new Year(2009),
                TimeZone.getDefault(), Locale.JAPAN);
        PeriodAxis a2 = new PeriodAxis("Test", new Year(2000), new Year(2009),
                TimeZone.getDefault(), Locale.JAPAN);
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        a1 = new PeriodAxis("Test", new Year(2000), new Year(2009),
                TimeZone.getDefault(), Locale.UK);
        assertFalse(a1.equals(a2));
        a2 = new PeriodAxis("Test", new Year(2000), new Year(2009),
                TimeZone.getDefault(), Locale.UK);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        PeriodAxis a1 = new PeriodAxis("Test");
        PeriodAxis a2 = new PeriodAxis("Test");
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PeriodAxis a1 = new PeriodAxis("Test");
        PeriodAxis a2 = (PeriodAxis) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));

        // some checks that the clone is independent of the original
        a1.setLabel("New Label");
        assertFalse(a1.equals(a2));
        a2.setLabel("New Label");
        assertTrue(a1.equals(a2));

        a1.setFirst(new Year(1920));
        assertFalse(a1.equals(a2));
        a2.setFirst(new Year(1920));
        assertTrue(a1.equals(a2));

        a1.setLast(new Year(2020));
        assertFalse(a1.equals(a2));
        a2.setLast(new Year(2020));
        assertTrue(a1.equals(a2));

        PeriodAxisLabelInfo[] info = new PeriodAxisLabelInfo[2];
        info[0] = new PeriodAxisLabelInfo(Day.class, new SimpleDateFormat("d"));
        info[1] = new PeriodAxisLabelInfo(Year.class,
                new SimpleDateFormat("yyyy"));
        a1.setLabelInfo(info);
        assertFalse(a1.equals(a2));
        a2.setLabelInfo(info);
        assertTrue(a1.equals(a2));

        a1.setAutoRangeTimePeriodClass(Second.class);
        assertFalse(a1.equals(a2));
        a2.setAutoRangeTimePeriodClass(Second.class);
        assertTrue(a1.equals(a2));

        a1.setTimeZone(new SimpleTimeZone(123, "Bogus"));
        assertFalse(a1.equals(a2));
        a2.setTimeZone(new SimpleTimeZone(123, "Bogus"));
        assertTrue(a1.equals(a2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PeriodAxis a1 = new PeriodAxis("Test Axis");
        PeriodAxis a2 = (PeriodAxis) TestUtilities.serialised(a1);
        boolean b = a1.equals(a2);
        assertTrue(b);
    }

    /**
     * A test for bug 1932146.
     */
    @Test
    public void test1932146() {
        PeriodAxis axis = new PeriodAxis("TestAxis");
        axis.addChangeListener(this);
        this.lastEvent = null;
        axis.setRange(new DateRange(0L, 1000L));
        assertTrue(this.lastEvent != null);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * A test for the setRange() method (because the axis shows whole time
     * periods, the range set for the axis will most likely be wider than the
     * one specified).
     */
    @Test
    public void test2490803() {
        Locale savedLocale = Locale.getDefault();
        TimeZone savedTimeZone = TimeZone.getDefault();
        try {
            Locale.setDefault(Locale.FRANCE);
            TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
            GregorianCalendar c0 = new GregorianCalendar();
            c0.clear();
            /* c0.set(2009, Calendar.JANUARY, 16, 12, 34, 56);
            System.out.println(c0.getTime().getTime());
            c0.clear();
            c0.set(2009, Calendar.JANUARY, 17, 12, 34, 56);
            System.out.println(c0.getTime().getTime()); */
            PeriodAxis axis = new PeriodAxis("TestAxis");
            axis.setRange(new Range(1232105696000L, 1232192096000L), false,
                    false);
            Range r = axis.getRange();
            Day d0 = new Day(16, 1, 2009);
            Day d1 = new Day(17, 1, 2009);
            assertEquals(d0.getFirstMillisecond(), r.getLowerBound(), EPSILON);
            assertEquals(d1.getLastMillisecond() + 1.0, r.getUpperBound(),
                    EPSILON);
        }
        finally {
            TimeZone.setDefault(savedTimeZone);
            Locale.setDefault(savedLocale);
        }
    }

}
