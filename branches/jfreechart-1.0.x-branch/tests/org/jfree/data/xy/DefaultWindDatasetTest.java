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
 * ---------------------------
 * DefaultWindDatasetTest.java
 * ---------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 12-Jul-2006 : Version 1 (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.jfree.chart.TestUtilities;

import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for {@link DefaultWindDataset}.
 */
public class DefaultWindDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultWindDataset d1 = new DefaultWindDataset();
        DefaultWindDataset d2 = new DefaultWindDataset();
        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        d1 = createSampleDataset1();
        assertFalse(d1.equals(d2));
        d2 = createSampleDataset1();
        assertTrue(d1.equals(d2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultWindDataset d1 = new DefaultWindDataset();
        DefaultWindDataset d2 = (DefaultWindDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // try a dataset with some content...
        d1 = createSampleDataset1();
        d2 = (DefaultWindDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        DefaultWindDataset d1 = new DefaultWindDataset();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultWindDataset d1 = new DefaultWindDataset();
        DefaultWindDataset d2 = (DefaultWindDataset) TestUtilities.serialised(d1);
        assertEquals(d1, d2);

        // try a dataset with some content...
        d1 = createSampleDataset1();
        d2 = (DefaultWindDataset) TestUtilities.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Some checks for the getSeriesKey(int) method.
     */
    @Test
    public void testGetSeriesKey() {
        DefaultWindDataset d = createSampleDataset1();
        assertEquals("Series 1", d.getSeriesKey(0));
        assertEquals("Series 2", d.getSeriesKey(1));

        // check for series key out of bounds
        boolean pass = false;
        try {
            /*Comparable k =*/ d.getSeriesKey(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            /*Comparable k =*/ d.getSeriesKey(2);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the indexOf(Comparable) method.
     */
    @Test
    public void testIndexOf() {
        DefaultWindDataset d = createSampleDataset1();
        assertEquals(0, d.indexOf("Series 1"));
        assertEquals(1, d.indexOf("Series 2"));
        assertEquals(-1, d.indexOf("Green Eggs and Ham"));
        assertEquals(-1, d.indexOf(null));
    }

    /**
     * Creates a sample dataset for testing.
     *
     * @return A sample dataset.
     */
    public DefaultWindDataset createSampleDataset1() {
        Day t = new Day(1, 4, 2006);
        Object[] item1 = createItem(t, 3, 7);
        Object[] item2 = createItem(t.next(), 4, 8);
        Object[] item3 = createItem(t.next(), 5, 9);
        Object[][] series1 = new Object[][] {item1, item2, item3};
        Object[] item1b = createItem(t, 6, 10);
        Object[] item2b = createItem(t.next(), 7, 11);
        Object[] item3b = createItem(t.next(), 8, 12);
        Object[][] series2 = new Object[][] {item1b, item2b, item3b};
        Object[][][] data = new Object[][][] {series1, series2};
        return new DefaultWindDataset(data);
    }

    /**
     * Creates an array representing one item in a series.
     *
     * @param t  the time period.
     * @param dir  the wind direction.
     * @param force  the wind force.
     *
     * @return An array containing the specified items.
     */
    private Object[] createItem(RegularTimePeriod t, int dir, int force) {
        return new Object[] {new Long(t.getMiddleMillisecond()),
                new Integer(dir), new Integer(force)};
    }
}
