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
 * -------------------
 * TimeSeriesTest.java
 * -------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 * 
 */

package org.jfree.data.time;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.chart.TestUtils;
import org.jfree.chart.date.MonthConstants;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.Range;
import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.general.SeriesException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A collection of test cases for the {@link TimeSeries} class.
 */
public class TimeSeriesTest implements SeriesChangeListener {

    /** A time series. */
    private TimeSeries<String> seriesA;

    /** A flag that indicates whether a change event was fired. */
    private boolean gotSeriesChangeEvent = false;

    /**
     * Common test setup.
     */
    @BeforeEach
    public void setUp() {
        this.seriesA = new TimeSeries<>("Series A");
        this.seriesA.add(new Year(2000), 102000);
        this.seriesA.add(new Year(2001), 102001);
        this.seriesA.add(new Year(2002), 102002);
        this.seriesA.add(new Year(2003), 102003);
        this.seriesA.add(new Year(2004), 102004);
        this.seriesA.add(new Year(2005), 102005);
    }

    /**
     * Sets the flag to indicate that a {@link SeriesChangeEvent} has been
     * received.
     *
     * @param event  the event.
     */
    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.gotSeriesChangeEvent = true;
    }

    /**
     * Check that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        TimeSeries<String> series = new TimeSeries<>("Test Series");
        RegularTimePeriod jan1st2002 = new Day(1, MonthConstants.JANUARY, 2002);
        series.add(jan1st2002, 42);

        TimeSeries<String> clone = CloneUtils.clone(series);
        clone.update(jan1st2002, 10);

        int seriesValue = series.getValue(jan1st2002).intValue();
        int cloneValue = clone.getValue(jan1st2002).intValue();

        assertEquals(42, seriesValue);
        assertEquals(10, cloneValue);
        assertEquals("Test Series", series.getKey());
        assertEquals("Test Series", clone.getKey());
    }

    /**
     * Another test of the clone() method.
     *
     * @throws java.lang.CloneNotSupportedException if there is a problem cloning.
     */
    @Test
    public void testClone2() throws CloneNotSupportedException {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2007), 100.0);
        s1.add(new Year(2008), null);
        s1.add(new Year(2009), 200.0);
        TimeSeries<String> s2 = CloneUtils.clone(s1);
        assertEquals(s1, s2);

        // check independence
        s2.addOrUpdate(new Year(2009), 300.0);
        assertNotEquals(s1, s2);
        s1.addOrUpdate(new Year(2009), 300.0);
        assertEquals(s1, s2);
    }

    /**
     * Add a value to series A for 1999.  It should be added at index 0.
     */
    @Test
    public void testAddValue() {
        this.seriesA.add(new Year(1999), 1);
        int value = this.seriesA.getValue(0).intValue();
        assertEquals(1, value);
    }

    /**
     * Tests the retrieval of values.
     */
    @Test
    public void testGetValue() {
        assertNull(seriesA.getValue(new Year(1999)));
        assertEquals(102000.0, seriesA.getValue(new Year(2000)));
    }

    /**
     * Tests the deletion of values.
     */
    @Test
    public void testDelete() {
        seriesA.delete(0, 0);
        assertEquals(5, seriesA.getItemCount());
        assertNull(seriesA.getValue(new Year(2000)));
    }

    /**
     * Basic tests for the delete() method.
     */
    @Test
    public void testDelete2() {
        TimeSeries<String> s1 = new TimeSeries<>("Series");
        s1.add(new Year(2000), 13.75);
        s1.add(new Year(2001), 11.90);
        s1.add(new Year(2002), null);
        s1.addChangeListener(this);
        this.gotSeriesChangeEvent = false;
        s1.delete(new Year(2001));
        assertTrue(this.gotSeriesChangeEvent);
        assertEquals(2, s1.getItemCount());
        assertNull(s1.getValue(new Year(2001)));

        // try deleting a time period that doesn't exist...
        this.gotSeriesChangeEvent = false;
        s1.delete(new Year(2006));
        assertFalse(this.gotSeriesChangeEvent);

        // try deleting null
        try {
            s1.delete(null);
            fail("Expected IllegalArgumentException.");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
    }

    /**
     * Some checks for the delete(int, int) method.
     */
    @Test
    public void testDelete3() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2011), 1.1);
        s1.add(new Year(2012), 2.2);
        s1.add(new Year(2013), 3.3);
        s1.add(new Year(2014), 4.4);
        s1.add(new Year(2015), 5.5);
        s1.add(new Year(2016), 6.6);
        s1.delete(2, 5);
        assertEquals(2, s1.getItemCount());
        assertEquals(new Year(2011), s1.getTimePeriod(0));
        assertEquals(new Year(2012), s1.getTimePeriod(1));
        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(2.2, s1.getMaxY(), EPSILON);
    }

    /**
     * Check that the item bounds are determined correctly when there is a
     * maximum item count and a new value is added.
     */
    @Test
    public void testDelete_RegularTimePeriod() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2010), 1.1);
        s1.add(new Year(2011), 2.2);
        s1.add(new Year(2012), 3.3);
        s1.add(new Year(2013), 4.4);
        s1.delete(new Year(2010));
        s1.delete(new Year(2013));
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TimeSeries<String> s1 = new TimeSeries<>("A test");
        s1.add(new Year(2000), 13.75);
        s1.add(new Year(2001), 11.90);
        s1.add(new Year(2002), null);
        s1.add(new Year(2005), 19.32);
        s1.add(new Year(2007), 16.89);
        TimeSeries<String> s2 = TestUtils.serialised(s1);
        assertEquals(s1, s2);
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        TimeSeries<String> s1 = new TimeSeries<>("Time Series 1");
        TimeSeries<String> s2 = new TimeSeries<>("Time Series 2");
        assertNotEquals(s1, s2);

        s2 = new TimeSeries<>("Time Series 1");
        assertEquals(s1, s2);

        RegularTimePeriod p1 = new Day();
        RegularTimePeriod p2 = p1.next();
        s1.add(p1, 100.0);
        s1.add(p2, 200.0);
        assertNotEquals(s1, s2);

        s2.add(p1, 100.0);
        s2.add(p2, 200.0);
        assertEquals(s1, s2);

        s1.setMaximumItemCount(100);
        assertNotEquals(s1, s2);

        s2.setMaximumItemCount(100);
        assertEquals(s1, s2);

        s1.setMaximumItemAge(100);
        assertNotEquals(s1, s2);

        s2.setMaximumItemAge(100);
        assertEquals(s1, s2);
    }

    /**
     * Some tests to ensure that the createCopy(RegularTimePeriod,
     * RegularTimePeriod) method is functioning correctly.
     */
    @Test
    public void testCreateCopy1() {
        TimeSeries<String> series = new TimeSeries<>("Series");
        series.add(new Month(MonthConstants.JANUARY, 2003), 45.0);
        series.add(new Month(MonthConstants.FEBRUARY, 2003), 55.0);
        series.add(new Month(MonthConstants.JUNE, 2003), 35.0);
        series.add(new Month(MonthConstants.NOVEMBER, 2003), 85.0);
        series.add(new Month(MonthConstants.DECEMBER, 2003), 75.0);

        try {
            // copy a range before the start of the series data...
            TimeSeries<String> result1 = series.createCopy(
                    new Month(MonthConstants.NOVEMBER, 2002),
                    new Month(MonthConstants.DECEMBER, 2002));
            assertEquals(0, result1.getItemCount());

            // copy a range that includes only the first item in the series...
            TimeSeries<String> result2 = series.createCopy(
                    new Month(MonthConstants.NOVEMBER, 2002),
                    new Month(MonthConstants.JANUARY, 2003));
            assertEquals(1, result2.getItemCount());

            // copy a range that begins before and ends in the middle of the
            // series...
            TimeSeries<String> result3 = series.createCopy(
                    new Month(MonthConstants.NOVEMBER, 2002),
                    new Month(MonthConstants.APRIL, 2003));
            assertEquals(2, result3.getItemCount());

            TimeSeries<String> result4 = series.createCopy(
                    new Month(MonthConstants.NOVEMBER, 2002),
                    new Month(MonthConstants.DECEMBER, 2003));
            assertEquals(5, result4.getItemCount());

            TimeSeries<String> result5 = series.createCopy(
                    new Month(MonthConstants.NOVEMBER, 2002),
                    new Month(MonthConstants.MARCH, 2004));
            assertEquals(5, result5.getItemCount());

            TimeSeries<String> result6 = series.createCopy(
                    new Month(MonthConstants.JANUARY, 2003),
                    new Month(MonthConstants.JANUARY, 2003));
            assertEquals(1, result6.getItemCount());

            TimeSeries<String> result7 = series.createCopy(
                    new Month(MonthConstants.JANUARY, 2003),
                    new Month(MonthConstants.APRIL, 2003));
            assertEquals(2, result7.getItemCount());

            TimeSeries<String> result8 = series.createCopy(
                    new Month(MonthConstants.JANUARY, 2003),
                    new Month(MonthConstants.DECEMBER, 2003));
            assertEquals(5, result8.getItemCount());

            TimeSeries<String> result9 = series.createCopy(
                    new Month(MonthConstants.JANUARY, 2003),
                    new Month(MonthConstants.MARCH, 2004));
            assertEquals(5, result9.getItemCount());

            TimeSeries<String> result10 = series.createCopy(
                    new Month(MonthConstants.MAY, 2003),
                    new Month(MonthConstants.DECEMBER, 2003));
            assertEquals(3, result10.getItemCount());

            TimeSeries<String> result11 = series.createCopy(
                    new Month(MonthConstants.MAY, 2003),
                    new Month(MonthConstants.MARCH, 2004));
            assertEquals(3, result11.getItemCount());

            TimeSeries<String> result12 = series.createCopy(
                    new Month(MonthConstants.DECEMBER, 2003),
                    new Month(MonthConstants.DECEMBER, 2003));
            assertEquals(1, result12.getItemCount());

            TimeSeries<String> result13 = series.createCopy(
                    new Month(MonthConstants.DECEMBER, 2003),
                    new Month(MonthConstants.MARCH, 2004));
            assertEquals(1, result13.getItemCount());

            TimeSeries<String> result14 = series.createCopy(
                    new Month(MonthConstants.JANUARY, 2004),
                    new Month(MonthConstants.MARCH, 2004));
            assertEquals(0, result14.getItemCount());
        }
        catch (CloneNotSupportedException e) {
            fail();
        }
    }

    /**
     * Some tests to ensure that the createCopy(int, int) method is
     * functioning correctly.
     */
    @Test
    public void testCreateCopy2() {
        TimeSeries<String> series = new TimeSeries<>("Series");
        series.add(new Month(MonthConstants.JANUARY, 2003), 45.0);
        series.add(new Month(MonthConstants.FEBRUARY, 2003), 55.0);
        series.add(new Month(MonthConstants.JUNE, 2003), 35.0);
        series.add(new Month(MonthConstants.NOVEMBER, 2003), 85.0);
        series.add(new Month(MonthConstants.DECEMBER, 2003), 75.0);

        try {
            // copy just the first item...
            TimeSeries<String> result1 = series.createCopy(0, 0);
            assertEquals(new Month(1, 2003), result1.getTimePeriod(0));

            // copy the first two items...
            result1 = series.createCopy(0, 1);
            assertEquals(new Month(2, 2003), result1.getTimePeriod(1));

            // copy the middle three items...
            result1 = series.createCopy(1, 3);
            assertEquals(new Month(2, 2003), result1.getTimePeriod(0));
            assertEquals(new Month(11, 2003), result1.getTimePeriod(2));

            // copy the last two items...
            result1 = series.createCopy(3, 4);
            assertEquals(new Month(11, 2003), result1.getTimePeriod(0));
            assertEquals(new Month(12, 2003), result1.getTimePeriod(1));

            // copy the last item...
            result1 = series.createCopy(4, 4);
            assertEquals(new Month(12, 2003), result1.getTimePeriod(0));
        }
        catch (CloneNotSupportedException e) {
            fail();
        }

        // check negative first argument
        boolean pass = false;
        try {
            /* TimeSeries result = */ series.createCopy(-1, 1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        catch (CloneNotSupportedException e) {
            pass = false;
        }
        assertTrue(pass);

        // check second argument less than first argument
        pass = false;
        try {
            /* TimeSeries result = */ series.createCopy(1, 0);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        catch (CloneNotSupportedException e) {
            pass = false;
        }
        assertTrue(pass);

        TimeSeries<String> series2 = new TimeSeries<>("Series 2");
        try {
            TimeSeries<String> series3 = series2.createCopy(99, 999);
            assertEquals(0, series3.getItemCount());
        }
        catch (CloneNotSupportedException e) {
            fail();
        }
    }

    /**
     * Checks that the min and max y values are updated correctly when copying
     * a subset.
     *
     * @throws java.lang.CloneNotSupportedException if there is a problem cloning.
     */
    @Test
    public void testCreateCopy3() throws CloneNotSupportedException {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2009), 100.0);
        s1.add(new Year(2010), 101.0);
        s1.add(new Year(2011), 102.0);
        assertEquals(100.0, s1.getMinY(), EPSILON);
        assertEquals(102.0, s1.getMaxY(), EPSILON);
        
        TimeSeries<String> s2 = s1.createCopy(0, 1);
        assertEquals(100.0, s2.getMinY(), EPSILON);
        assertEquals(101.0, s2.getMaxY(), EPSILON);

        TimeSeries<String> s3 = s1.createCopy(1, 2);
        assertEquals(101.0, s3.getMinY(), EPSILON);
        assertEquals(102.0, s3.getMaxY(), EPSILON);
    }

    /**
     * Test the setMaximumItemCount() method to ensure that it removes items
     * from the series if necessary.
     */
    @Test
    public void testSetMaximumItemCount() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2000), 13.75);
        s1.add(new Year(2001), 11.90);
        s1.add(new Year(2002), null);
        s1.add(new Year(2005), 19.32);
        s1.add(new Year(2007), 16.89);
        assertEquals(5, s1.getItemCount());

        s1.setMaximumItemCount(3);
        assertEquals(3, s1.getItemCount());
        TimeSeriesDataItem item = s1.getDataItem(0);
        assertEquals(item.getPeriod(), new Year(2002));
        assertEquals(16.89, s1.getMinY(), EPSILON);
        assertEquals(19.32, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the addOrUpdate() method.
     */
    @Test
    public void testAddOrUpdate() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.setMaximumItemCount(2);
        s1.addOrUpdate(new Year(2000), 100.0);
        assertEquals(1, s1.getItemCount());
        s1.addOrUpdate(new Year(2001), 101.0);
        assertEquals(2, s1.getItemCount());
        s1.addOrUpdate(new Year(2001), 102.0);
        assertEquals(2, s1.getItemCount());
        s1.addOrUpdate(new Year(2002), 103.0);
        assertEquals(2, s1.getItemCount());
    }

    /**
     * Test the add branch of the addOrUpdate() method.
     */
    @Test
    public void testAddOrUpdate2() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.setMaximumItemCount(2);
        s1.addOrUpdate(new Year(2010), 1.1);
        s1.addOrUpdate(new Year(2011), 2.2);
        s1.addOrUpdate(new Year(2012), 3.3);
        assertEquals(2, s1.getItemCount());
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Test that the addOrUpdate() method won't allow multiple time period
     * classes.
     */
    @Test
    public void testAddOrUpdate3() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.addOrUpdate(new Year(2010), 1.1);
        assertEquals(Year.class, s1.getTimePeriodClass());

        boolean pass = false;
        try {
            s1.addOrUpdate(new Month(1, 2009), 0.0);
        }
        catch (SeriesException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some more checks for the addOrUpdate() method.
     */
    @Test
    public void testAddOrUpdate4() {
        TimeSeries<String> ts = new TimeSeries<>("S");
        TimeSeriesDataItem overwritten = ts.addOrUpdate(new Year(2009), 20.09);
        assertNull(overwritten);
        overwritten = ts.addOrUpdate(new Year(2009), 1.0);
        assertEquals(20.09, overwritten.getValue());
        assertEquals(1.0, ts.getValue(new Year(2009)));

        // changing the overwritten record shouldn't affect the series
        overwritten.setValue(null);
        assertEquals(1.0, ts.getValue(new Year(2009)));

        TimeSeriesDataItem item = new TimeSeriesDataItem(new Year(2010), 20.10);
        overwritten = ts.addOrUpdate(item);
        assertNull(overwritten);
        assertEquals(20.10, ts.getValue(new Year(2010)));
        // changing the item that was added should not change the series
        item.setValue(null);
        assertEquals(20.10, ts.getValue(new Year(2010)));
    }

    /**
     * A test for the bug report 1075255.
     */
    @Test
    public void testBug1075255() {
        TimeSeries<String> ts = new TimeSeries<>("dummy");
        ts.add(new FixedMillisecond(0L), 0.0);
        TimeSeries<String> ts2 = new TimeSeries<>("dummy2");
        ts2.add(new FixedMillisecond(0L), 1.0);
        try {
            ts.addAndOrUpdate(ts2);
        }
        catch (Exception e) {
            fail("No exceptions should be thrown.");
        }
        assertEquals(1, ts.getItemCount());
    }

    /**
     * A test for bug 1832432.
     * @throws java.lang.CloneNotSupportedException if there is a cloning problem.
     */
    @Test
    public void testBug1832432() throws CloneNotSupportedException {
        TimeSeries<String> s1 = new TimeSeries<>("Series");
        TimeSeries<String> s2 = CloneUtils.clone(s1);
        assertNotSame(s1, s2);
        assertSame(s1.getClass(), s2.getClass());
        assertEquals(s1, s2);

        // test independence
        s1.add(new Day(1, 1, 2007), 100.0);
        assertNotEquals(s1, s2);
    }

    /**
     * Some checks for the getIndex() method.
     */
    @Test
    public void testGetIndex() {
        TimeSeries<String> series = new TimeSeries<>("Series");
        assertEquals(-1, series.getIndex(new Month(1, 2003)));

        series.add(new Month(1, 2003), 45.0);
        assertEquals(0, series.getIndex(new Month(1, 2003)));
        assertEquals(-1, series.getIndex(new Month(12, 2002)));
        assertEquals(-2, series.getIndex(new Month(2, 2003)));

        series.add(new Month(3, 2003), 55.0);
        assertEquals(-1, series.getIndex(new Month(12, 2002)));
        assertEquals(0, series.getIndex(new Month(1, 2003)));
        assertEquals(-2, series.getIndex(new Month(2, 2003)));
        assertEquals(1, series.getIndex(new Month(3, 2003)));
        assertEquals(-3, series.getIndex(new Month(4, 2003)));
    }

    /**
     * Some checks for the getDataItem(int) method.
     */
    @Test
    public void testGetDataItem1() {
        TimeSeries<String> series = new TimeSeries<>("S");

        // can't get anything yet...just an exception
        boolean pass = false;
        try {
            /*TimeSeriesDataItem item =*/ series.getDataItem(0);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        series.add(new Year(2006), 100.0);
        TimeSeriesDataItem item = series.getDataItem(0);
        assertEquals(new Year(2006), item.getPeriod());
        pass = false;
        try {
            /*item = */series.getDataItem(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            /*item = */series.getDataItem(1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getDataItem(RegularTimePeriod) method.
     */
    @Test
    public void testGetDataItem2() {
        TimeSeries<String> series = new TimeSeries<>("S");
        assertNull(series.getDataItem(new Year(2006)));

        // try a null argument
        boolean pass = false;
        try {
            /* TimeSeriesDataItem item = */ series.getDataItem(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeAgedItems() method.
     */
    @Test
    public void testRemoveAgedItems() {
        TimeSeries<String> series = new TimeSeries<>("Test Series");
        series.addChangeListener(this);
        assertEquals(Long.MAX_VALUE, series.getMaximumItemAge());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        this.gotSeriesChangeEvent = false;

        // test empty series
        series.removeAgedItems(true);
        assertEquals(0, series.getItemCount());
        assertFalse(this.gotSeriesChangeEvent);

        // test series with one item
        series.add(new Year(1999), 1.0);
        series.setMaximumItemAge(0);
        this.gotSeriesChangeEvent = false;
        series.removeAgedItems(true);
        assertEquals(1, series.getItemCount());
        assertFalse(this.gotSeriesChangeEvent);

        // test series with two items
        series.setMaximumItemAge(10);
        series.add(new Year(2001), 2.0);
        this.gotSeriesChangeEvent = false;
        series.setMaximumItemAge(2);
        assertEquals(2, series.getItemCount());
        assertEquals(0, series.getIndex(new Year(1999)));
        assertFalse(this.gotSeriesChangeEvent);
        series.setMaximumItemAge(1);
        assertEquals(1, series.getItemCount());
        assertEquals(0, series.getIndex(new Year(2001)));
        assertTrue(this.gotSeriesChangeEvent);
    }

    /**
     * Some checks for the removeAgedItems(long, boolean) method.
     */
    @Test
    public void testRemoveAgedItems2() {
        long y2006 = 1157087372534L;  // milliseconds somewhere in 2006
        TimeSeries<String> series = new TimeSeries<>("Test Series");
        series.addChangeListener(this);
        assertEquals(Long.MAX_VALUE, series.getMaximumItemAge());
        assertEquals(Integer.MAX_VALUE, series.getMaximumItemCount());
        this.gotSeriesChangeEvent = false;

        // test empty series
        series.removeAgedItems(y2006, true);
        assertEquals(0, series.getItemCount());
        assertFalse(this.gotSeriesChangeEvent);

        // test a series with 1 item
        series.add(new Year(2004), 1.0);
        series.setMaximumItemAge(1);
        this.gotSeriesChangeEvent = false;
        series.removeAgedItems(new Year(2005).getMiddleMillisecond(), true);
        assertEquals(1, series.getItemCount());
        assertFalse(this.gotSeriesChangeEvent);
        series.removeAgedItems(y2006, true);
        assertEquals(0, series.getItemCount());
        assertTrue(this.gotSeriesChangeEvent);

        // test a series with two items
        series.setMaximumItemAge(2);
        series.add(new Year(2003), 1.0);
        series.add(new Year(2005), 2.0);
        assertEquals(2, series.getItemCount());
        this.gotSeriesChangeEvent = false;
        assertEquals(2, series.getItemCount());

        series.removeAgedItems(new Year(2005).getMiddleMillisecond(), true);
        assertEquals(2, series.getItemCount());
        assertFalse(this.gotSeriesChangeEvent);
        series.removeAgedItems(y2006, true);
        assertEquals(1, series.getItemCount());
        assertTrue(this.gotSeriesChangeEvent);
    }

    /**
     * Calling removeAgedItems() on an empty series should not throw any
     * exception.
     */
    @Test
    public void testRemoveAgedItems3() {
        TimeSeries<String> s = new TimeSeries<>("Test");
        boolean pass = true;
        try {
            s.removeAgedItems(0L, true);
        }
        catch (Exception e) {
            pass = false;
        }
        assertTrue(pass);
    }

    /**
     * Check that the item bounds are determined correctly when there is a
     * maximum item count.
     */
    @Test
    public void testRemoveAgedItems4() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.setMaximumItemAge(2);
        s1.add(new Year(2010), 1.1);
        s1.add(new Year(2011), 2.2);
        s1.add(new Year(2012), 3.3);
        s1.add(new Year(2013), 2.5);
        assertEquals(3, s1.getItemCount());
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Check that the item bounds are determined correctly after a call to
     * removeAgedItems().
     */
    @Test
    public void testRemoveAgedItems5() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.setMaximumItemAge(4);
        s1.add(new Year(2010), 1.1);
        s1.add(new Year(2011), 2.2);
        s1.add(new Year(2012), 3.3);
        s1.add(new Year(2013), 2.5);
        s1.removeAgedItems(new Year(2015).getMiddleMillisecond(), true);
        assertEquals(3, s1.getItemCount());
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Some simple checks for the hashCode() method.
     */
    @Test
    public void testHashCode() {
        TimeSeries<String> s1 = new TimeSeries<>("Test");
        TimeSeries<String> s2 = new TimeSeries<>("Test");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(new Day(1, 1, 2007), 500.0);
        s2.add(new Day(1, 1, 2007), 500.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(new Day(2, 1, 2007), null);
        s2.add(new Day(2, 1, 2007), null);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(new Day(5, 1, 2007), 111.0);
        s2.add(new Day(5, 1, 2007), 111.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(new Day(9, 1, 2007), 1.0);
        s2.add(new Day(9, 1, 2007), 1.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    /**
     * Test for bug report 1864222.
     */
    @Test
    public void testBug1864222() {
        TimeSeries<String> s = new TimeSeries<>("S");
        s.add(new Day(19, 8, 2005), 1);
        s.add(new Day(31, 1, 2006), 1);
        boolean pass = true;
        try {
            s.createCopy(new Day(1, 12, 2005), new Day(18, 1, 2006));
        }
        catch (CloneNotSupportedException e) {
            pass = false;
        }
        assertTrue(pass);
    }

    /**
     * Test for bug report 3446965.
     */
    @Test
    public void testBug3446965() {
        TimeSeries<String> s = new TimeSeries<>("s");
        s.addOrUpdate(new Year(2011), 100.0);
        s.addOrUpdate(new Year(2012), 150.0);
        s.addOrUpdate(new Year(2013), 200.0);
        s.addOrUpdate(new Year(2012), 250.0);  // this line triggers the defect
        assertEquals(100.0, s.getMinY(), EPSILON);
        assertEquals(250.0, s.getMaxY(), EPSILON);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the getMinY() method.
     */
    @Test
    public void testGetMinY() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        assertTrue(Double.isNaN(s1.getMinY()));

        s1.add(new Year(2008), 1.1);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(new Year(2009), 2.2);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(new Year(2000), 99.9);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(new Year(2002), -1.1);
        assertEquals(-1.1, s1.getMinY(), EPSILON);

        s1.add(new Year(2003), null);
        assertEquals(-1.1, s1.getMinY(), EPSILON);

        s1.addOrUpdate(new Year(2002), null);
        assertEquals(1.1, s1.getMinY(), EPSILON);
   }

    @Test
    public void testGetMinY2() {
        TimeSeries<String> ts = new TimeSeries<>("Time Series");
        assertTrue(Double.isNaN(ts.getMinY()));
        
        ts.add(new Year(2014), 1.0);
        assertEquals(1.0, ts.getMinY(), EPSILON);
        
        ts.addOrUpdate(new Year(2014), null);
        assertTrue(Double.isNaN(ts.getMinY()));
        
        ts.addOrUpdate(new Year(2014), 1.0);
        assertEquals(1.0, ts.getMinY(), EPSILON);

        ts.clear();
        assertTrue(Double.isNaN(ts.getMinY()));
    }
    
    /**
     * Some checks for the getMaxY() method.
     */
    @Test
    public void testGetMaxY() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        assertTrue(Double.isNaN(s1.getMaxY()));

        s1.add(new Year(2008), 1.1);
        assertEquals(1.1, s1.getMaxY(), EPSILON);

        s1.add(new Year(2009), 2.2);
        assertEquals(2.2, s1.getMaxY(), EPSILON);

        s1.add(new Year(2000), 99.9);
        assertEquals(99.9, s1.getMaxY(), EPSILON);

        s1.add(new Year(2002), -1.1);
        assertEquals(99.9, s1.getMaxY(), EPSILON);

        s1.add(new Year(2003), null);
        assertEquals(99.9, s1.getMaxY(), EPSILON);

        s1.addOrUpdate(new Year(2000), null);
        assertEquals(2.2, s1.getMaxY(), EPSILON);
    }

    @Test
    public void testGetMaxY2() {
        TimeSeries<String> ts = new TimeSeries<>("Time Series");
        assertTrue(Double.isNaN(ts.getMaxY()));
        
        ts.add(new Year(2014), 1.0);
        assertEquals(1.0, ts.getMaxY(), EPSILON);
        
        ts.addOrUpdate(new Year(2014), null);
        assertTrue(Double.isNaN(ts.getMaxY()));
        
        ts.addOrUpdate(new Year(2014), 1.0);
        assertEquals(1.0, ts.getMaxY(), EPSILON);

        ts.clear();
        assertTrue(Double.isNaN(ts.getMaxY()));
    }

    /**
     * A test for the clear method.
     */
    @Test
    public void testClear() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2009), 1.1);
        s1.add(new Year(2010), 2.2);

        assertEquals(2, s1.getItemCount());

        s1.clear();
        assertEquals(0, s1.getItemCount());
        assertTrue(Double.isNaN(s1.getMinY()));
        assertTrue(Double.isNaN(s1.getMaxY()));
    }

    /**
     * Check that the item bounds are determined correctly when there is a
     * maximum item count and a new value is added.
     */
    @Test
    public void testAdd() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.setMaximumItemCount(2);
        s1.add(new Year(2010), 1.1);
        s1.add(new Year(2011), 2.2);
        s1.add(new Year(2012), 3.3);
        assertEquals(2, s1.getItemCount());
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the update(RegularTimePeriod...method).
     */
    @Test
    public void testUpdate_RegularTimePeriod() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2010), 1.1);
        s1.add(new Year(2011), 2.2);
        s1.add(new Year(2012), 3.3);
        s1.update(new Year(2012), 4.4);
        assertEquals(4.4, s1.getMaxY(), EPSILON);
        s1.update(new Year(2010), 0.5);
        assertEquals(0.5, s1.getMinY(), EPSILON);
        s1.update(new Year(2012), null);
        assertEquals(2.2, s1.getMaxY(), EPSILON);
        s1.update(new Year(2010), null);
        assertEquals(2.2, s1.getMinY(), EPSILON);
    }

    /**
     * Create a TimeSeriesDataItem, add it to a TimeSeries.  Now, modifying
     * the original TimeSeriesDataItem should NOT affect the TimeSeries.
     */
    @Test
    public void testAdd_TimeSeriesDataItem() {
        TimeSeriesDataItem item = new TimeSeriesDataItem(new Year(2009), 1.0);
        TimeSeries<String> series = new TimeSeries<>("S1");
        series.add(item);
        assertEquals(item, series.getDataItem(0));
        item.setValue(99.9);
        assertNotEquals(item, series.getDataItem(0));
    }

    @Test
    public void testFindValueRange() {
        TimeSeries<String> ts = new TimeSeries<>("Time Series");
        assertNull(ts.findValueRange());
        
        ts.add(new Year(2014), 1.0);
        assertEquals(new Range(1.0, 1.0), ts.findValueRange());
        
        ts.add(new Year(2015), 2.0);
        assertEquals(new Range(1.0, 2.0), ts.findValueRange());

        // null items are ignored
        ts.add(new Year(2016), null);
        assertEquals(new Range(1.0, 2.0), ts.findValueRange());
        
        // Double.NaN values are also ignored
        ts.add(new Year(2017), Double.NaN);
        assertEquals(new Range(1.0, 2.0), ts.findValueRange());
        
        ts.clear();
        assertNull(ts.findValueRange());
        
        // if there are only null items, we get a NaNRange
        ts.add(new Year(2014), null);
        assertTrue(ts.findValueRange().isNaNRange()); 
    }

    /**
     * Tests for:
     * public Range findValueRange(Range, TimePeriodAnchor, TimeZone)
     */
    @Test
    public void testFindValueRange2() {
        TimeZone tzone = TimeZone.getTimeZone("Europe/London");
        Calendar calendar = new GregorianCalendar(tzone, Locale.UK);
        calendar.clear();
        calendar.set(2014, Calendar.FEBRUARY, 23, 6, 0);
        long start = calendar.getTimeInMillis();
        calendar.clear();
        calendar.set(2014, Calendar.FEBRUARY, 24, 18, 0);
        long end = calendar.getTimeInMillis();
        Range range = new Range(start, end);
        
        TimeSeries<String> ts = new TimeSeries<>("Time Series");
        assertNull(ts.findValueRange(range, TimePeriodAnchor.START, tzone));
        assertNull(ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertNull(ts.findValueRange(range, TimePeriodAnchor.END, tzone));
        
        ts.add(new Day(23, 2, 2014), 5.0);
        assertTrue(ts.findValueRange(range, TimePeriodAnchor.START, tzone).isNaNRange());
        assertEquals(new Range(5.0, 5.0), 
                ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertEquals(new Range(5.0, 5.0), 
                ts.findValueRange(range, TimePeriodAnchor.END, tzone));
        
        ts.add(new Day(24, 2, 2014), 6.0);
        assertEquals(new Range(6.0, 6.0), 
                ts.findValueRange(range, TimePeriodAnchor.START, tzone));
        assertEquals(new Range(5.0, 6.0), 
                ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertEquals(new Range(5.0, 5.0), 
                ts.findValueRange(range, TimePeriodAnchor.END, tzone));
        
        ts.clear();
        ts.add(new Day(24, 2, 2014), null);
        assertTrue(ts.findValueRange(range, TimePeriodAnchor.START, tzone).isNaNRange());
        assertTrue(ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone).isNaNRange());
        assertTrue(ts.findValueRange(range, TimePeriodAnchor.END, tzone).isNaNRange());
    }
    
    /**
     * Test findValueRange() method when there are Double.NaN values present.
     */
    @Test
    public void testFindValueRange3() {
        TimeZone tzone = TimeZone.getTimeZone("Europe/London");
        Calendar calendar = new GregorianCalendar(tzone, Locale.UK);
        calendar.clear();
        calendar.set(2015, Calendar.SEPTEMBER, 1, 6, 0);
        long start = calendar.getTimeInMillis();
        calendar.clear();
        calendar.set(2015, Calendar.SEPTEMBER, 30, 18, 0);
        long end = calendar.getTimeInMillis();
        Range range = new Range(start, end);
        
        TimeSeries<String> ts = new TimeSeries<>("Time Series");
        assertNull(ts.findValueRange(range, TimePeriodAnchor.START, tzone));
        assertNull(ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertNull(ts.findValueRange(range, TimePeriodAnchor.END, tzone));
        
        ts.add(new Day(1, 9, 2015), 1.0);
        ts.add(new Day(2, 9, 2015), 99.0);
        ts.add(new Day(30, 9, 2015), 2.0);

        assertEquals(new Range(2.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.START, tzone));
        assertEquals(new Range(1.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertEquals(new Range(1.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.END, tzone));
        
        ts.add(new Day(10, 9, 2015), Double.NaN);
        assertEquals(new Range(2.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.START, tzone));
        assertEquals(new Range(1.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.MIDDLE, tzone));
        assertEquals(new Range(1.0, 99.0), 
                ts.findValueRange(range, TimePeriodAnchor.END, tzone));

    }
}
