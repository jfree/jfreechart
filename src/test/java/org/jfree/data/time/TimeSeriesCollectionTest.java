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
 * -----------------------------
 * TimeSeriesCollectionTest.java
 * -----------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.time;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DatasetChangeConfirmation;
import org.jfree.data.Range;
import org.jfree.data.general.DatasetUtils;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * A collection of test cases for the {@link TimeSeriesCollection} class.
 */
public class TimeSeriesCollectionTest {

    /**
     * Some tests for the equals() method.
     */
    @Test
    public void testEquals() {
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>();
        TimeSeriesCollection<String> c2 = new TimeSeriesCollection<>();

        TimeSeries<String> s1 = new TimeSeries<>("Series 1");
        TimeSeries<String> s2 = new TimeSeries<>("Series 2");

        // newly created collections should be equal
        boolean b1 = c1.equals(c2);
        assertTrue(b1, "b1");

        // add series to collection 1, should be not equal
        c1.addSeries(s1);
        c1.addSeries(s2);
        boolean b2 = c1.equals(c2);
        assertFalse(b2, "b2");

        // now add the same series to collection 2 to make them equal again...
        c2.addSeries(s1);
        c2.addSeries(s2);
        boolean b3 = c1.equals(c2);
        assertTrue(b3, "b3");

        // now remove series 2 from collection 2
        c2.removeSeries(s2);
        boolean b4 = c1.equals(c2);
        assertFalse(b4, "b4");

        // now remove series 2 from collection 1 to make them equal again
        c1.removeSeries(s2);
        boolean b5 = c1.equals(c2);
        assertTrue(b5, "b5");
    }

    /**
     * Tests the remove series method.
     */
    @Test
    public void testRemoveSeries() {
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>();

        TimeSeries<String> s1 = new TimeSeries<>("Series 1");
        TimeSeries<String> s2 = new TimeSeries<>("Series 2");
        TimeSeries<String> s3 = new TimeSeries<>("Series 3");
        TimeSeries<String> s4 = new TimeSeries<>("Series 4");

        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.addSeries(s3);
        c1.addSeries(s4);

        c1.removeSeries(s3);

        TimeSeries<String> s = c1.getSeries(2);
        boolean b1 = s.equals(s4);
        assertTrue(b1);
    }

    /**
     * Some checks for the {@link TimeSeriesCollection#removeSeries(int)}
     * method.
     */
    @Test
    public void testRemoveSeries_int() {
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>();
        TimeSeries<String> s1 = new TimeSeries<>("Series 1");
        TimeSeries<String> s2 = new TimeSeries<>("Series 2");
        TimeSeries<String> s3 = new TimeSeries<>("Series 3");
        TimeSeries<String> s4 = new TimeSeries<>("Series 4");
        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.addSeries(s3);
        c1.addSeries(s4);
        c1.removeSeries(2);
        assertEquals(c1.getSeries(2), s4);
        c1.removeSeries(0);
        assertEquals(c1.getSeries(0), s2);
        assertEquals(2, c1.getSeriesCount());
    }

    /**
     * Test the getSurroundingItems() method to ensure it is returning the
     * values we expect.
     */
    @Test
    public void testGetSurroundingItems() {
        TimeSeries<String> series = new TimeSeries<>("Series 1");
        TimeSeriesCollection<String> collection = new TimeSeriesCollection<>(series);
        collection.setXPosition(TimePeriodAnchor.MIDDLE);

        // for a series with no data, we expect {-1, -1}...
        int[] result = collection.getSurroundingItems(0, 1000L);
        assertEquals(-1, result[0]);
        assertEquals(-1, result[1]);

        // now test with a single value in the series...
        Day today = new Day();
        long start1 = today.getFirstMillisecond();
        long middle1 = today.getMiddleMillisecond();
        long end1 = today.getLastMillisecond();

        series.add(today, 99.9);
        result = collection.getSurroundingItems(0, start1);
        assertEquals(-1, result[0]);
        assertEquals(0, result[1]);

        result = collection.getSurroundingItems(0, middle1);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);

        result = collection.getSurroundingItems(0, end1);
        assertEquals(0, result[0]);
        assertEquals(-1, result[1]);

        // now add a second value to the series...
        Day tomorrow = (Day) today.next();
        long start2 = tomorrow.getFirstMillisecond();
        long middle2 = tomorrow.getMiddleMillisecond();
        long end2 = tomorrow.getLastMillisecond();

        series.add(tomorrow, 199.9);
        result = collection.getSurroundingItems(0, start2);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);

        result = collection.getSurroundingItems(0, middle2);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);

        result = collection.getSurroundingItems(0, end2);
        assertEquals(1, result[0]);
        assertEquals(-1, result[1]);

        // now add a third value to the series...
        Day yesterday = (Day) today.previous();
        long start3 = yesterday.getFirstMillisecond();
        long middle3 = yesterday.getMiddleMillisecond();
        long end3 = yesterday.getLastMillisecond();

        series.add(yesterday, 1.23);
        result = collection.getSurroundingItems(0, start3);
        assertEquals(-1, result[0]);
        assertEquals(0, result[1]);

        result = collection.getSurroundingItems(0, middle3);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);

        result = collection.getSurroundingItems(0, end3);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        var s1 = createSeries();
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>(s1);
        TimeSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);

        // check independence
        s1.add(new Day(26, 4, 2000), 99.9);
        assertNotEquals(c1, c2);
        var s2 = c2.getSeries(0);
        s2.add(new Day(26, 4, 2000), 99.9);
        assertEquals(c1, c2);

        // check that c2 gets notifications when s2 is changed
        DatasetChangeConfirmation listener = new DatasetChangeConfirmation();
        c2.addChangeListener(listener);
        s2.add(new Day(27, 4, 2000), 99.9);
        assertNotNull(listener.event);
    }

    /**
     * Creates a time series for testing.
     *
     * @return A time series.
     */
    private TimeSeries<String> createSeries() {
        RegularTimePeriod t = new Day();
        TimeSeries<String> series = new TimeSeries<>("Test");
        series.add(t, 1.0);
        t = t.next();
        series.add(t, 2.0);
        t = t.next();
        series.add(t, null);
        t = t.next();
        series.add(t, 4.0);
        return series;
    }

    /**
     * A test for bug report 1170825.
     */
    @Test
    public void test1170825() {
        TimeSeries<String> s1 = new TimeSeries<>("Series1");
        TimeSeriesCollection<String> dataset = new TimeSeriesCollection<>();
        dataset.addSeries(s1);
        try {
            /* TimeSeries s = */ dataset.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            // correct outcome
        }
        catch (IndexOutOfBoundsException e) {
            fail();  // wrong outcome
        }
    }

    /**
     * Some tests for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        TimeSeries<String> s2 = new TimeSeries<>("S2");
        TimeSeriesCollection<String> dataset = new TimeSeriesCollection<>();
        assertEquals(-1, dataset.indexOf(s1));
        assertEquals(-1, dataset.indexOf(s2));

        dataset.addSeries(s1);
        assertEquals(0, dataset.indexOf(s1));
        assertEquals(-1, dataset.indexOf(s2));

        dataset.addSeries(s2);
        assertEquals(0, dataset.indexOf(s1));
        assertEquals(1, dataset.indexOf(s2));

        dataset.removeSeries(s1);
        assertEquals(-1, dataset.indexOf(s1));
        assertEquals(0, dataset.indexOf(s2));

        TimeSeries<String> s2b = new TimeSeries<>("S2");
        assertEquals(0, dataset.indexOf(s2b));
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * This method provides a check for the bounds calculated using the
     * {@link DatasetUtils#findDomainBounds(org.jfree.data.xy.XYDataset,
     * java.util.List, boolean)} method.
     */
    @Test
    public void testFindDomainBounds() {
        // store the current time zone
        TimeZone saved = TimeZone.getDefault();
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));

        TimeSeriesCollection<String> dataset = new TimeSeriesCollection<>();
        List<String> visibleSeriesKeys = new ArrayList<>();
        Range r = DatasetUtils.findDomainBounds(dataset, visibleSeriesKeys,
                true);
        assertNull(r);

        TimeSeries<String> s1 = new TimeSeries<>("S1");
        dataset.addSeries(s1);
        visibleSeriesKeys.add("S1");
        r = DatasetUtils.findDomainBounds(dataset, visibleSeriesKeys, true);
        assertNull(r);

        s1.add(new Year(2008), 8.0);
        r = DatasetUtils.findDomainBounds(dataset, visibleSeriesKeys, true);
        assertEquals(1199142000000.0, r.getLowerBound(), EPSILON);
        assertEquals(1230764399999.0, r.getUpperBound(), EPSILON);

        TimeSeries<String> s2 = new TimeSeries<>("S2");
        dataset.addSeries(s2);
        s2.add(new Year(2009), 9.0);
        s2.add(new Year(2010), 10.0);
        r = DatasetUtils.findDomainBounds(dataset, visibleSeriesKeys, true);
        assertEquals(1199142000000.0, r.getLowerBound(), EPSILON);
        assertEquals(1230764399999.0, r.getUpperBound(), EPSILON);

        visibleSeriesKeys.add("S2");
        r = DatasetUtils.findDomainBounds(dataset, visibleSeriesKeys, true);
        assertEquals(1199142000000.0, r.getLowerBound(), EPSILON);
        assertEquals(1293836399999.0, r.getUpperBound(), EPSILON);

        // restore the default time zone
        TimeZone.setDefault(saved);
    }

    /**
     * Basic checks for cloning.
     *
     * @throws java.lang.CloneNotSupportedException if there is a problem cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TimeSeries<String> s1 = new TimeSeries<>("Series");
        s1.add(new Year(2009), 1.1);
        TimeSeriesCollection<String> c1 = new TimeSeriesCollection<>();
        c1.addSeries(s1);
        TimeSeriesCollection<String> c2 = CloneUtils.clone(c1);
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        s1.add(new Year(2022), 123.4);
        assertNotEquals(c1, c2);
        c2.getSeries(0).add(new Year(2022), 123.4);
        assertEquals(c1, c2);
    }

    /**
     * A test to cover bug 3445507.
     */
    @Test
    public void testBug3445507() {
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Year(2011), null);
        s1.add(new Year(2012), null);

        TimeSeries<String> s2 = new TimeSeries<>("S2");
        s2.add(new Year(2011), 5.0);
        s2.add(new Year(2012), 6.0);

        TimeSeriesCollection<String> dataset = new TimeSeriesCollection<>();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        List<String> keys = new ArrayList<>();
        keys.add("S1");
        keys.add("S2");
        Range r = dataset.getRangeBounds(keys, new Range(
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY), false);
        assertEquals(5.0, r.getLowerBound(), EPSILON);
        assertEquals(6.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the getRangeBounds() method.
     */
    @Test
    public void testGetRangeBounds() {
        TimeSeriesCollection<String> dataset = new TimeSeriesCollection<>();
        
        // when the dataset contains no series, we expect the range to be null
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));

        // when the dataset contains one or more series, but those series
        // contain no items, we still expect the range to be null
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        dataset.addSeries(s1);
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));

        // tests with values
        s1.add(new Year(2012), 1.0);
        assertEquals(new Range(1.0, 1.0), dataset.getRangeBounds(false));
        assertEquals(new Range(1.0, 1.0), dataset.getRangeBounds(true));
        s1.add(new Year(2013), -1.0);
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(true));
        s1.add(new Year(2014), null);
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(true));
        
        // adding a second series
        TimeSeries<String> s2 = new TimeSeries<>("S2");
        dataset.addSeries(s2);
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.0, 1.0), dataset.getRangeBounds(true));
        
        s2.add(new Year(2014), 5.0);
        assertEquals(new Range(-1.0, 5.0), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.0, 5.0), dataset.getRangeBounds(true));
        
        dataset.removeAllSeries();
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));
        
        s1 = new TimeSeries<>("s1");
        s2 = new TimeSeries<>("s2");
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));
        
        s2.add(new Year(2014), 100.0);
        assertEquals(new Range(100.0, 100.0), dataset.getRangeBounds(false));
        assertEquals(new Range(100.0, 100.0), dataset.getRangeBounds(true));
    }

    @Test
    public void testGetRangeBounds2() {
        TimeZone tzone = TimeZone.getTimeZone("Europe/London");
        Calendar calendar = new GregorianCalendar(tzone, Locale.UK);
        calendar.clear();
        calendar.set(2014, Calendar.FEBRUARY, 23, 6, 0);
        long start = calendar.getTimeInMillis();
        calendar.clear();
        calendar.set(2014, Calendar.FEBRUARY, 24, 18, 0);
        long end = calendar.getTimeInMillis();
        Range range = new Range(start, end);
        
        TimeSeriesCollection<String> collection = new TimeSeriesCollection<>(tzone);
        assertNull(collection.getRangeBounds(Collections.EMPTY_LIST, range, 
                true));
        
        TimeSeries<String> s1 = new TimeSeries<>("S1");
        s1.add(new Day(24, 2, 2014), 10.0);
        collection.addSeries(s1);
        assertEquals(new Range(10.0, 10.0), collection.getRangeBounds(
                Collections.singletonList("S1"), range, true));
        collection.setXPosition(TimePeriodAnchor.MIDDLE);
        assertEquals(new Range(10.0, 10.0), collection.getRangeBounds(
                Collections.singletonList("S1"), range, true));
        collection.setXPosition(TimePeriodAnchor.END);
        assertNull(collection.getRangeBounds(Collections.singletonList("S1"), range, true));
    }
}
