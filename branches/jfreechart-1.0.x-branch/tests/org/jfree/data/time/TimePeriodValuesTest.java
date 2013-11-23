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
 * ------------------------
 * TimePeriodValueTest.java
 * ------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jul-2003 : Version 1 (DG);
 * 07-Apr-2008 : Added new tests for min/max-start/middle/end
 *               index updates (DG);
 *
 */

package org.jfree.data.time;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.date.MonthConstants;
import org.junit.Before;
import org.junit.Test;

/**
 * A collection of test cases for the {@link TimePeriodValues} class.
 */
public class TimePeriodValuesTest {

    /** Series A. */
    private TimePeriodValues seriesA;

    /** Series B. */
    private TimePeriodValues seriesB;

    /** Series C. */
    private TimePeriodValues seriesC;


    /**
     * Common test setup.
     */
    @Before
    public void setUp() {
        this.seriesA = new TimePeriodValues("Series A");
        this.seriesA.add(new Year(2000), new Integer(102000));
        this.seriesA.add(new Year(2001), new Integer(102001));
        this.seriesA.add(new Year(2002), new Integer(102002));
        this.seriesA.add(new Year(2003), new Integer(102003));
        this.seriesA.add(new Year(2004), new Integer(102004));
        this.seriesA.add(new Year(2005), new Integer(102005));

        this.seriesB = new TimePeriodValues("Series B");
        this.seriesB.add(new Year(2006), new Integer(202006));
        this.seriesB.add(new Year(2007), new Integer(202007));
        this.seriesB.add(new Year(2008), new Integer(202008));

        this.seriesC = new TimePeriodValues("Series C");
        this.seriesC.add(new Year(1999), new Integer(301999));
        this.seriesC.add(new Year(2000), new Integer(302000));
        this.seriesC.add(new Year(2002), new Integer(302002));
    }

    /**
     * Set up a quarter equal to Q1 1900.  Request the previous quarter, it 
     * should be null.
     */
    @Test
    public void testClone() throws CloneNotSupportedException {
        TimePeriodValues series = new TimePeriodValues("Test Series");
        RegularTimePeriod jan1st2002 = new Day(1, MonthConstants.JANUARY, 2002);
        series.add(jan1st2002, new Integer(42));
        TimePeriodValues clone = (TimePeriodValues) series.clone();
        clone.setKey("Clone Series");
        clone.update(0, new Integer(10));

        int seriesValue = series.getValue(0).intValue();
        int cloneValue = clone.getValue(0).intValue();

        assertEquals(42, seriesValue);
        assertEquals(10, cloneValue);
        assertEquals("Test Series", series.getKey());
        assertEquals("Clone Series", clone.getKey());
    }

    /**
     * Add a value to series A for 1999.  It should be added at index 0.
     */
    @Test
    public void testAddValue() {
        TimePeriodValues tpvs = new TimePeriodValues("Test");
        tpvs.add(new Year(1999), new Integer(1));
        int value = tpvs.getValue(0).intValue();
        assertEquals(1, value);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TimePeriodValues s1 = new TimePeriodValues("A test");
        s1.add(new Year(2000), 13.75);
        s1.add(new Year(2001), 11.90);
        s1.add(new Year(2002), null);
        s1.add(new Year(2005), 19.32);
        s1.add(new Year(2007), 16.89);
        TimePeriodValues s2 = (TimePeriodValues) TestUtilities.serialised(s1);
        assertTrue(s1.equals(s2));
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void testEquals() {
        TimePeriodValues s1 = new TimePeriodValues("Time Series 1");
        TimePeriodValues s2 = new TimePeriodValues("Time Series 2");
        boolean b1 = s1.equals(s2);
        assertFalse("b1", b1);

        s2.setKey("Time Series 1");
        boolean b2 = s1.equals(s2);
        assertTrue("b2", b2);

        // domain description
        s1.setDomainDescription("XYZ");
        assertFalse(s1.equals(s2));
        s2.setDomainDescription("XYZ");
        assertTrue(s1.equals(s2));
        
        // domain description - null
        s1.setDomainDescription(null);
        assertFalse(s1.equals(s2));
        s2.setDomainDescription(null);
        assertTrue(s1.equals(s2));
        
        // range description
        s1.setRangeDescription("XYZ");
        assertFalse(s1.equals(s2));
        s2.setRangeDescription("XYZ");
        assertTrue(s1.equals(s2));
        
        // range description - null
        s1.setRangeDescription(null);
        assertFalse(s1.equals(s2));
        s2.setRangeDescription(null);
        assertTrue(s1.equals(s2));

        RegularTimePeriod p1 = new Day();
        RegularTimePeriod p2 = p1.next();
        s1.add(p1, 100.0);
        s1.add(p2, 200.0);
        boolean b3 = s1.equals(s2);
        assertFalse("b3", b3);

        s2.add(p1, 100.0);
        s2.add(p2, 200.0);
        boolean b4 = s1.equals(s2);
        assertTrue("b4", b4);

    }
    
    /**
     * A test for bug report 1161329.
     */
    @Test
    public void test1161329() {
        TimePeriodValues tpv = new TimePeriodValues("Test");
        RegularTimePeriod t = new Day();
        tpv.add(t, 1.0);
        t = t.next();
        tpv.add(t, 2.0);
        tpv.delete(0, 1);
        assertEquals(0, tpv.getItemCount());
        tpv.add(t, 2.0);
        assertEquals(1, tpv.getItemCount());
    }
    
    static final double EPSILON = 0.0000000001;
    
    /**
     * Some checks for the add() methods.
     */
    @Test
    public void testAdd() {
        TimePeriodValues tpv = new TimePeriodValues("Test");
        MySeriesChangeListener listener = new MySeriesChangeListener();
        tpv.addChangeListener(listener);
        tpv.add(new TimePeriodValue(new SimpleTimePeriod(new Date(1L), 
                new Date(3L)), 99.0));
        assertEquals(99.0, tpv.getValue(0).doubleValue(), EPSILON);
        assertEquals(tpv, listener.getLastEvent().getSource());
        
        // a null item should throw an IllegalArgumentException
        boolean pass = false;
        try {
            tpv.add((TimePeriodValue) null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }
    
    /**
     * Some tests for the getMinStartIndex() method.
     */
    @Test
    public void testGetMinStartIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMinStartIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMinStartIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(0, s.getMinStartIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(2, s.getMinStartIndex());
    }
    
    /**
     * Some tests for the getMaxStartIndex() method.
     */
    @Test
    public void testGetMaxStartIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMaxStartIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMaxStartIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(1, s.getMaxStartIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(1, s.getMaxStartIndex());
    }

    /**
     * Some tests for the getMinMiddleIndex() method.
     */
    @Test
    public void testGetMinMiddleIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMinMiddleIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMinMiddleIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(0, s.getMinMiddleIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(2, s.getMinMiddleIndex());
    }
    
    /**
     * Some tests for the getMaxMiddleIndex() method.
     */
    @Test
    public void testGetMaxMiddleIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMaxMiddleIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMaxMiddleIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(1, s.getMaxMiddleIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(1, s.getMaxMiddleIndex());
        s.add(new SimpleTimePeriod(150L, 200L), 4.0);
        assertEquals(1, s.getMaxMiddleIndex());
    }

    /**
     * Some tests for the getMinEndIndex() method.
     */
    @Test
    public void getMinEndIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMinEndIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMinEndIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(0, s.getMinEndIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(2, s.getMinEndIndex());
    }
    
    /**
     * Some tests for the getMaxEndIndex() method.
     */
    @Test
    public void getMaxEndIndex() {
        TimePeriodValues s = new TimePeriodValues("Test");
        assertEquals(-1, s.getMaxEndIndex());
        s.add(new SimpleTimePeriod(100L, 200L), 1.0);
        assertEquals(0, s.getMaxEndIndex());
        s.add(new SimpleTimePeriod(300L, 400L), 2.0);
        assertEquals(1, s.getMaxEndIndex());
        s.add(new SimpleTimePeriod(0L, 50L), 3.0);
        assertEquals(1, s.getMaxEndIndex());
    }

    /**
     * A listener used for detecting series change events.
     */
    static class MySeriesChangeListener implements SeriesChangeListener {
        
        SeriesChangeEvent lastEvent;
        
        /**
         * Creates a new listener.
         */
        public MySeriesChangeListener() {
            this.lastEvent = null;
        }
        
        /**
         * Returns the last event.
         * 
         * @return The last event (possibly <code>null</code>).
         */
        public SeriesChangeEvent getLastEvent() {
            return this.lastEvent;
        }
        
        /**
         * Clears the last event (sets it to <code>null</code>).
         */
        public void clearLastEvent() {
            this.lastEvent = null;
        }
        
        /**
         * Callback method for series change events.
         * 
         * @param event  the event.
         */
        @Override
        public void seriesChanged(SeriesChangeEvent event) {
            this.lastEvent = event;
        }
    }

}
