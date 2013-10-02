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
 * XYSeriesCollectionTest.java
 * ---------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-May-2003 : Version 1 (DG);
 * 27-Nov-2006 : Updated testCloning() (DG);
 * 08-Mar-2007 : Added testGetSeries() and testRemoveSeries() (DG);
 * 08-May-2007 : Added testIndexOf() (DG);
 * 03-Dec-2007 : Added testGetSeriesByKey() (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 * 06-Mar-2009 : Added testGetDomainBounds (DG);
 * 17-May-2010 : Added checks for duplicate series names (DG);
 * 08-Jan-2012 : Added testBug3445507() (DG);
 * 28-Jul-2012 : Added testSeriesRename() (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail
        ;
import org.jfree.chart.TestUtilities;

import org.jfree.data.Range;
import org.jfree.data.UnknownKeyException;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link XYSeriesCollection} class.
 */
public class XYSeriesCollectionTest {

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor() {
        XYSeriesCollection xysc = new XYSeriesCollection();
        assertEquals(0, xysc.getSeriesCount());
        assertEquals(1.0, xysc.getIntervalWidth(), EPSILON);
        assertEquals(0.5, xysc.getIntervalPositionFactor(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeries s2 = new XYSeries("Series");
        s2.add(1.0, 1.1);
        XYSeriesCollection c2 = new XYSeriesCollection();
        c2.addSeries(s2);
        assertEquals(c1, c2);
        assertEquals(c2, c1);

        c1.addSeries(new XYSeries("Empty Series"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new XYSeries("Empty Series"));
        assertEquals(c1, c2);

        c1.setIntervalWidth(5.0);
        assertFalse(c1.equals(c2));
        c2.setIntervalWidth(5.0);
        assertEquals(c1, c2);

        c1.setIntervalPositionFactor(0.75);
        assertFalse(c1.equals(c2));
        c2.setIntervalPositionFactor(0.75);
        assertEquals(c1, c2);

        c1.setAutoWidth(true);
        assertFalse(c1.equals(c2));
        c2.setAutoWidth(true);
        assertEquals(c1, c2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeriesCollection c2 = (XYSeriesCollection) c1.clone();
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        s1.setDescription("XYZ");
        assertFalse(c1.equals(c2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        Object c1 = new XYSeriesCollection();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeriesCollection c2 = (XYSeriesCollection) 
                TestUtilities.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * A test for bug report 1170825.
     */
    @Test
    public void test1170825() {
        XYSeries s1 = new XYSeries("Series1");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        try {
            /* XYSeries s = */ dataset.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            // correct outcome
        }
        catch (IndexOutOfBoundsException e) {
            assertTrue(false);  // wrong outcome
        }
    }

    /**
     * Some basic checks for the getSeries() method.
     */
    @Test
    public void testGetSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        assertEquals("s1", c.getSeries(0).getKey());

        try {
            c.getSeries(-1);
            fail("Should have thrown IndexOutOfBoundsException on negative key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Series index out of bounds", e.getMessage());
        }

        try {
            c.getSeries(1);
            fail("Should have thrown IndexOutOfBoundsException on key out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Series index out of bounds", e.getMessage());
        }
    }

    /**
     * Some checks for the getSeries(Comparable) method.
     */
    @Test
    public void testGetSeriesByKey() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        assertEquals("s1", c.getSeries("s1").getKey());

        try {
            c.getSeries("s2");
            fail("Should have thrown UnknownKeyException on unknown key");
        }
        catch (UnknownKeyException e) {
            assertEquals("Key not found: s2", e.getMessage());
        }

        try {
            c.getSeries(null);
            fail("Should have thrown IndexOutOfBoundsException on null key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'key' argument.", e.getMessage());
        }
    }
    
    /**
     * Some basic checks for the addSeries() method.
     */
    @Test
    public void testAddSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);

        // the dataset should prevent the addition of a series with the
        // same name as an existing series in the dataset
        XYSeries s2 = new XYSeries("s1");
        try {
            c.addSeries(s2);
            fail("Should have thrown IllegalArgumentException on duplicate key");
        } catch (IllegalArgumentException e) {
            assertEquals("This dataset already contains a series with the key s1", e.getMessage());
        }
        assertEquals(1, c.getSeriesCount());
    }

    /**
     * Some basic checks for the removeSeries() method.
     */
    @Test
    public void testRemoveSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        c.removeSeries(0);
        assertEquals(0, c.getSeriesCount());
        c.addSeries(s1);

        try {
            c.removeSeries(-1);
            fail("Should have thrown IndexOutOfBoundsException on negative key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Series index out of bounds.", e.getMessage());
        }

        try {
            c.removeSeries(1);
            fail("Should have thrown IndexOutOfBoundsException on key out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Series index out of bounds.", e.getMessage());
        }
    }

    /**
     * Some tests for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        XYSeries s1 = new XYSeries("S1");
        XYSeries s2 = new XYSeries("S2");
        XYSeriesCollection dataset = new XYSeriesCollection();
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

        XYSeries s2b = new XYSeries("S2");
        assertEquals(0, dataset.indexOf(s2b));
    }

    /**
     * Some checks for the getDomainBounds() method.
     */
    @Test
    public void testGetDomainBounds() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Range r = dataset.getDomainBounds(false);
        assertNull(r);
        r = dataset.getDomainBounds(true);
        assertNull(r);

        XYSeries series = new XYSeries("S1");
        dataset.addSeries(series);
        r = dataset.getDomainBounds(false);
        assertNull(r);
        r = dataset.getDomainBounds(true);
        assertNull(r);

        series.add(1.0, 1.1);
        r = dataset.getDomainBounds(false);
        assertEquals(new Range(1.0, 1.0), r);
        r = dataset.getDomainBounds(true);
        assertEquals(new Range(0.5, 1.5), r);

        series.add(-1.0, -1.1);
        r = dataset.getDomainBounds(false);
        assertEquals(new Range(-1.0, 1.0), r);
        r = dataset.getDomainBounds(true);
        assertEquals(new Range(-1.5, 1.5), r);
    }

    /**
     * Some checks for the getRangeBounds() method.
     */
    @Test
    public void testGetRangeBounds() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        Range r = dataset.getRangeBounds(false);
        assertNull(r);
        r = dataset.getRangeBounds(true);
        assertNull(r);

        XYSeries series = new XYSeries("S1");
        dataset.addSeries(series);
        r = dataset.getRangeBounds(false);
        assertNull(r);
        r = dataset.getRangeBounds(true);
        assertNull(r);

        series.add(1.0, 1.1);
        r = dataset.getRangeBounds(false);
        assertEquals(new Range(1.1, 1.1), r);
        r = dataset.getRangeBounds(true);
        assertEquals(new Range(1.1, 1.1), r);

        series.add(-1.0, -1.1);
        r = dataset.getRangeBounds(false);
        assertEquals(new Range(-1.1, 1.1), r);
        r = dataset.getRangeBounds(true);
        assertEquals(new Range(-1.1, 1.1), r);
    }

    /**
     * A check that the dataset prevents renaming a series to the name of an 
     * existing series in the dataset.
     */
    @Test
    public void testRenameSeries() {
        XYSeries s1 = new XYSeries("S1");
        XYSeries s2 = new XYSeries("S2");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        try {
            s2.setKey("S1");
            fail("Should have thrown IllegalArgumentException on negative key");
        }
        catch (IllegalArgumentException e) {
           assertEquals("Duplicate key2", e.getMessage());
        }
    }

    /**
     * A test to cover bug 3445507.  The issue does not affect
     * XYSeriesCollection.
     */
    @Test
    public void testBug3445507() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, null);
        s1.add(2.0, null);

        XYSeries s2 = new XYSeries("S2");
        s1.add(1.0, 5.0);
        s1.add(2.0, 6.0);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        Range r = dataset.getRangeBounds(false);
        assertEquals(5.0, r.getLowerBound(), EPSILON);
        assertEquals(6.0, r.getUpperBound(), EPSILON);
    }
 
    /**
     * Test that a series belonging to a collection can be renamed (in fact, 
     * because of a bug this was not possible in JFreeChart 1.0.14).
     */
    @Test
    public void testSeriesRename() {
        // first check that a valid renaming works
        XYSeries series1 = new XYSeries("A");
        XYSeries series2 = new XYSeries("B");
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(series1);
        collection.addSeries(series2);
        series1.setKey("C");
        assertEquals("C", collection.getSeries(0).getKey());
        
        // next, check that setting a duplicate key fails
        try {
            series2.setKey("C");
            fail("Expected an IllegalArgumentException.");
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals("B", series2.getKey());  // the series name should not 
        // change because "C" is already the key for the other series in the
        // collection
    }
}
