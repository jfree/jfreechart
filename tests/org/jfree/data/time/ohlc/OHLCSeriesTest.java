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
 * OHLCSeriesTest.java
 * -------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1, based on XYSeriesTests (DG);
 * 27-Nov-2007 : Added testClear() method (DG);
 * 23-May-2009 : Added testHashCode() (DG);
 * 17-Jun-2009 : Added testRemove_int() (DG);
 *
 */

package org.jfree.data.time.ohlc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Year;
import org.junit.Test;

/**
 * Tests for the {@link OHLCSeries} class.
 */
public class OHLCSeriesTest implements SeriesChangeListener {

    SeriesChangeEvent lastEvent;

    /**
     * Records a change event.
     *
     * @param event  the event.
     */
    @Override
    public void seriesChanged(SeriesChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        OHLCSeries s1 = new OHLCSeries("s1");
        OHLCSeries s2 = new OHLCSeries("s1");
        assertTrue(s1.equals(s2));

        // seriesKey
        s1 = new OHLCSeries("s2");
        assertFalse(s1.equals(s2));
        s2 = new OHLCSeries("s2");
        assertTrue(s1.equals(s2));

        // add a value
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertFalse(s1.equals(s2));
        s2.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertTrue(s2.equals(s1));

        // add another value
        s1.add(new Year(2008), 2.0, 4.0, 1.0, 3.0);
        assertFalse(s1.equals(s2));
        s2.add(new Year(2008), 2.0, 4.0, 1.0, 3.0);
        assertTrue(s2.equals(s1));

        // remove a value
        s1.remove(new Year(2008));
        assertFalse(s1.equals(s2));
        s2.remove(new Year(2008));
        assertTrue(s2.equals(s1));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        OHLCSeries s1 = new OHLCSeries("Test");
        s1.add(new Year(2009), 1.0, 3.0, 2.0, 1.4);
        OHLCSeries s2 = new OHLCSeries("Test");
        s2.add(new Year(2009), 1.0, 3.0, 2.0, 1.4);
        assertTrue(s1.equals(s2));
        int h1 = s1.hashCode();
        int h2 = s2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCSeries s2 = (OHLCSeries) s1.clone();
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCSeries s2 = (OHLCSeries) TestUtilities.serialised(s1);
        assertEquals(s1, s2);
    }

    /**
     * Simple test for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        s1.add(new Year(2011), 2.0, 4.0, 1.0, 3.0);
        s1.add(new Year(2010), 2.0, 4.0, 1.0, 3.0);
        assertEquals(0, s1.indexOf(new Year(2006)));
        assertEquals(1, s1.indexOf(new Year(2010)));
        assertEquals(2, s1.indexOf(new Year(2011)));
    }

    /**
     * Simple test for the remove() method.
     */
    @Test
    public void testRemove() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        s1.add(new Year(2011), 2.1, 4.1, 1.1, 3.1);
        s1.add(new Year(2010), 2.2, 4.2, 1.2, 3.2);
        assertEquals(3, s1.getItemCount());

        s1.remove(new Year(2010));
        assertEquals(new Year(2011), s1.getPeriod(1));

        s1.remove(new Year(2006));
        assertEquals(new Year(2011), s1.getPeriod(0));
    }

    /**
     * A check for the remove(int) method.
     */
    @Test
    public void testRemove_int() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        s1.add(new Year(2011), 2.1, 4.1, 1.1, 3.1);
        s1.add(new Year(2010), 2.2, 4.2, 1.2, 3.2);
        assertEquals(3, s1.getItemCount());

        s1.remove(s1.getItemCount() - 1);
        assertEquals(2, s1.getItemCount());
        assertEquals(new Year(2010), s1.getPeriod(1));
    }

    /**
     * If you add a duplicate period, an exception should be thrown.
     */
    @Test
    public void testAdditionOfDuplicatePeriod() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 1.0, 1.0, 1.0, 1.0);
        boolean pass = false;
        try {
            s1.add(new Year(2006), 1.0, 1.0, 1.0, 1.0);
        }
        catch (SeriesException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * A simple check that the maximumItemCount attribute is working.
     */
    @Test
    public void testSetMaximumItemCount() {
        OHLCSeries s1 = new OHLCSeries("s1");
        assertEquals(Integer.MAX_VALUE, s1.getMaximumItemCount());
        s1.setMaximumItemCount(2);
        assertEquals(2, s1.getMaximumItemCount());
        s1.add(new Year(2006), 1.0, 1.1, 1.1, 1.1);
        s1.add(new Year(2007), 2.0, 2.2, 2.2, 2.2);
        s1.add(new Year(2008), 3.0, 3.3, 3.3, 3.3);
        assertEquals(new Year(2007), s1.getPeriod(0));
        assertEquals(new Year(2008), s1.getPeriod(1));
    }

    /**
     * Check that the maximum item count can be applied retrospectively.
     */
    @Test
    public void testSetMaximumItemCount2() {
        OHLCSeries s1 = new OHLCSeries("s1");
        s1.add(new Year(2006), 1.0, 1.1, 1.1, 1.1);
        s1.add(new Year(2007), 2.0, 2.2, 2.2, 2.2);
        s1.add(new Year(2008), 3.0, 3.3, 3.3, 3.3);
        s1.setMaximumItemCount(2);
        assertEquals(new Year(2007), s1.getPeriod(0));
        assertEquals(new Year(2008), s1.getPeriod(1));
    }

    /**
     * Some checks for the clear() method.
     */
    @Test
    public void testClear() {
        OHLCSeries s1 = new OHLCSeries("S1");
        s1.addChangeListener(this);
        s1.clear();
        assertNull(this.lastEvent);
        assertTrue(s1.isEmpty());
        s1.add(new Year(2006), 1.0, 1.1, 1.1, 1.1);
        assertFalse(s1.isEmpty());
        s1.clear();
        assertNotNull(this.lastEvent);
        assertTrue(s1.isEmpty());
    }

}
