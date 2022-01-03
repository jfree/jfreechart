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
 * ---------------------------
 * XYSeriesCollectionTest.java
 * ---------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.data.DatasetChangeConfirmation;
import org.jfree.data.Range;
import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        XYSeriesCollection<String> xysc = new XYSeriesCollection<>();
        assertEquals(0, xysc.getSeriesCount());
        assertEquals(1.0, xysc.getIntervalWidth(), EPSILON);
        assertEquals(0.5, xysc.getIntervalPositionFactor(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        XYSeries<String> s1 = new XYSeries<>("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection<String> c1 = new XYSeriesCollection<>();
        c1.addSeries(s1);
        XYSeries<String> s2 = new XYSeries<>("Series");
        s2.add(1.0, 1.1);
        XYSeriesCollection<String> c2 = new XYSeriesCollection<>();
        c2.addSeries(s2);
        assertEquals(c1, c2);
        assertEquals(c2, c1);

        c1.addSeries(new XYSeries<>("Empty Series"));
        assertNotEquals(c1, c2);
        c2.addSeries(new XYSeries<>("Empty Series"));
        assertEquals(c1, c2);

        c1.setIntervalWidth(5.0);
        assertNotEquals(c1, c2);
        c2.setIntervalWidth(5.0);
        assertEquals(c1, c2);

        c1.setIntervalPositionFactor(0.75);
        assertNotEquals(c1, c2);
        c2.setIntervalPositionFactor(0.75);
        assertEquals(c1, c2);

        c1.setAutoWidth(true);
        assertNotEquals(c1, c2);
        c2.setAutoWidth(true);
        assertEquals(c1, c2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYSeries<String> s1 = new XYSeries<>("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection<String> c1 = new XYSeriesCollection<>();
        c1.addSeries(s1);
        XYSeriesCollection<String> c2 = CloneUtils.clone(c1);
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        c2.setIntervalWidth(99.9);
        assertNotEquals(c1, c2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        Object c1 = new XYSeriesCollection<String>();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYSeries<String> s1 = new XYSeries<>("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection<String> c1 = new XYSeriesCollection<>();
        c1.addSeries(s1);
        XYSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);

        // check independence
        s1.add(2.0, 2.2);
        assertNotEquals(c1, c2);
        XYSeries s2 = c2.getSeries(0);
        s2.add(2.0, 2.2);
        assertEquals(c1, c2);

        // check that c2 gets notifications when s2 is changed
        DatasetChangeConfirmation listener = new DatasetChangeConfirmation();
        c2.addChangeListener(listener);
        s2.add(3.0, 3.3);
        assertNotNull(listener.event);
    }

    /**
     * A test for bug report 1170825.
     */
    @Test
    public void test1170825() {
        var s1 = new XYSeries<String>("Series1");
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        dataset.addSeries(s1);
        try {
            /* XYSeries s = */ dataset.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            // correct outcome
        }
        catch (IndexOutOfBoundsException e) {
            fail();  // wrong outcome
        }
    }

    /**
     * Some basic checks for the getSeries() method.
     */
    @Test
    public void testGetSeries() {
        XYSeriesCollection<String> c = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("s1");
        c.addSeries(s1);
        assertEquals("s1", c.getSeries(0).getKey());

        try {
            c.getSeries(-1);
            fail("Should have thrown IndexOutOfBoundsException on negative key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Require 'series' (-1) to be in the range 0 to 0", 
                    e.getMessage());
        }

        try {
            c.getSeries(1);
            fail("Should have thrown IndexOutOfBoundsException on key out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Require 'series' (1) to be in the range 0 to 0", e.getMessage());
        }
    }

    /**
     * Some checks for the getSeries() method.
     */
    @Test
    public void testGetSeriesByKey() {
        XYSeriesCollection<String> c = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("s1");
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
        XYSeriesCollection<String> c = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("s1");
        c.addSeries(s1);

        // the dataset should prevent the addition of a series with the
        // same name as an existing series in the dataset
        XYSeries<String> s2 = new XYSeries<>("s1");
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
        XYSeriesCollection<String> c = new XYSeriesCollection<>();
        XYSeries<String> s1 = new XYSeries<>("s1");
        c.addSeries(s1);
        c.removeSeries(0);
        assertEquals(0, c.getSeriesCount());
        c.addSeries(s1);

        try {
            c.removeSeries(-1);
            fail("Should have thrown IndexOutOfBoundsException on negative key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Require 'series' (-1) to be in the range 0 to 0", e.getMessage());
        }

        try {
            c.removeSeries(1);
            fail("Should have thrown IndexOutOfBoundsException on key out of range");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Require 'series' (1) to be in the range 0 to 0", e.getMessage());
        }
    }

    /**
     * Some tests for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        XYSeries<String> s1 = new XYSeries<>("S1");
        XYSeries<String> s2 = new XYSeries<>("S2");
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
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

        XYSeries<String> s2b = new XYSeries<>("S2");
        assertEquals(0, dataset.indexOf(s2b));
    }

    /**
     * Some checks for the getDomainBounds() method.
     */
    @Test
    public void testGetDomainBounds() {
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        Range r = dataset.getDomainBounds(false);
        assertNull(r);
        r = dataset.getDomainBounds(true);
        assertNull(r);

        XYSeries<String> series = new XYSeries<>("S1");
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
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        
        // when the dataset contains no series, we expect the value range to 
        // be null
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));

        // when the dataset contains one or more series, but those series 
        // contain no items, we expect the value range to be null
        var series = new XYSeries<String>("S1");
        dataset.addSeries(series);
        assertNull(dataset.getRangeBounds(false));
        assertNull(dataset.getRangeBounds(true));

        // tests with values
        series.add(1.0, 1.1);
        assertEquals(new Range(1.1, 1.1), dataset.getRangeBounds(false));
        assertEquals(new Range(1.1, 1.1), dataset.getRangeBounds(true));

        series.add(-1.0, -1.1);
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(true));
        
        series.add(0.0, null);
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(true));
        
        var s2 = new XYSeries<String>("S2");
        dataset.addSeries(s2);
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.1, 1.1), dataset.getRangeBounds(true));
        
        s2.add(2.0, 5.0);
        assertEquals(new Range(-1.1, 5.0), dataset.getRangeBounds(false));
        assertEquals(new Range(-1.1, 5.0), dataset.getRangeBounds(true));
    }

    @Test
    public void testGetRangeLowerBound() {
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        
        // when the dataset contains no series, we expect the value range to 
        // be null
        assertTrue(Double.isNaN(dataset.getRangeLowerBound(false)));
        assertTrue(Double.isNaN(dataset.getRangeLowerBound(true)));

        // when the dataset contains one or more series, but those series 
        // contain no items, we expect the value range to be null
        XYSeries<String> series = new XYSeries<>("S1");
        dataset.addSeries(series);
        assertTrue(Double.isNaN(dataset.getRangeLowerBound(false)));
        assertTrue(Double.isNaN(dataset.getRangeLowerBound(true)));

        // tests with values
        series.add(1.0, 1.1);
        assertEquals(1.1, dataset.getRangeLowerBound(false), EPSILON);
        assertEquals(1.1, dataset.getRangeLowerBound(true), EPSILON);

        series.add(-1.0, -1.1);
        assertEquals(-1.1, dataset.getRangeLowerBound(false), EPSILON);
        assertEquals(-1.1, dataset.getRangeLowerBound(true), EPSILON);
        
        series.add(0.0, null);
        assertEquals(-1.1, dataset.getRangeLowerBound(false), EPSILON);
        assertEquals(-1.1, dataset.getRangeLowerBound(true), EPSILON);
        
        XYSeries<String> s2 = new XYSeries<>("S2");
        dataset.addSeries(s2);
        assertEquals(-1.1, dataset.getRangeLowerBound(false), EPSILON);
        assertEquals(-1.1, dataset.getRangeLowerBound(true), EPSILON);
        
        s2.add(2.0, 5.0);
        assertEquals(-1.1, dataset.getRangeLowerBound(false), EPSILON);
        assertEquals(-1.1, dataset.getRangeLowerBound(true), EPSILON);
    }
    
    @Test
    public void testGetRangeUpperBound() {
        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        
        // when the dataset contains no series, we expect the value range to 
        // be null
        assertTrue(Double.isNaN(dataset.getRangeUpperBound(false)));
        assertTrue(Double.isNaN(dataset.getRangeUpperBound(true)));

        // when the dataset contains one or more series, but those series 
        // contain no items, we expect the value range to be null
        XYSeries<String> series = new XYSeries<>("S1");
        dataset.addSeries(series);
        assertTrue(Double.isNaN(dataset.getRangeUpperBound(false)));
        assertTrue(Double.isNaN(dataset.getRangeUpperBound(true)));

        // tests with values
        series.add(1.0, 1.1);
        assertEquals(1.1, dataset.getRangeUpperBound(false), EPSILON);
        assertEquals(1.1, dataset.getRangeUpperBound(true), EPSILON);

        series.add(-1.0, -1.1);
        assertEquals(1.1, dataset.getRangeUpperBound(false), EPSILON);
        assertEquals(1.1, dataset.getRangeUpperBound(true), EPSILON);
        
        series.add(0.0, null);
        assertEquals(1.1, dataset.getRangeUpperBound(false), EPSILON);
        assertEquals(1.1, dataset.getRangeUpperBound(true), EPSILON);
        
        XYSeries<String> s2 = new XYSeries<>("S2");
        dataset.addSeries(s2);
        assertEquals(1.1, dataset.getRangeUpperBound(false), EPSILON);
        assertEquals(1.1, dataset.getRangeUpperBound(true), EPSILON);
        
        s2.add(2.0, 5.0);
        assertEquals(5.0, dataset.getRangeUpperBound(false), EPSILON);
        assertEquals(5.0, dataset.getRangeUpperBound(true), EPSILON);
    }

    /**
     * A test to cover bug 3445507.  The issue does not affect
     * XYSeriesCollection.
     */
    @Test
    public void testBug3445507() {
        XYSeries<String> s1 = new XYSeries<>("S1");
        s1.add(1.0, null);
        s1.add(2.0, null);

        XYSeries<String> s2 = new XYSeries<>("S2");
        s1.add(1.0, 5.0);
        s1.add(2.0, 6.0);

        XYSeriesCollection<String> dataset = new XYSeriesCollection<>();
        dataset.addSeries(s1);
        dataset.addSeries(s2);

        Range r = dataset.getRangeBounds(false);
        assertEquals(5.0, r.getLowerBound(), EPSILON);
        assertEquals(6.0, r.getUpperBound(), EPSILON);
    }

}
