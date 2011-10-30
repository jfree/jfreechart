/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * ------------------------------
 * OHLCSeriesCollectionTests.java
 * ------------------------------
 * (C) Copyright 2006-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 * 10-Jul-2008 : Updated testEquals() method (DG);
 * 26-Jun-2009 : Added tests for removeSeries() methods (DG);
 *
 */

package org.jfree.data.time.ohlc.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.time.TimePeriodAnchor;
import org.jfree.data.time.Year;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;

/**
 * Tests for the {@link OHLCSeriesCollectionTests} class.
 */
public class OHLCSeriesCollectionTests extends TestCase
        implements DatasetChangeListener {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(OHLCSeriesCollectionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public OHLCSeriesCollectionTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeriesCollection c2 = new OHLCSeriesCollection();
        assertEquals(c1, c2);

        // add a series
        OHLCSeries s1 = new OHLCSeries("Series");
        s1.add(new Year(2006), 1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        assertFalse(c1.equals(c2));
        OHLCSeries s2 = new OHLCSeries("Series");
        s2.add(new Year(2006), 1.0, 1.1, 1.2, 1.3);
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));

        // add an empty series
        c1.addSeries(new OHLCSeries("Empty Series"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new OHLCSeries("Empty Series"));
        assertTrue(c1.equals(c2));

        c1.setXPosition(TimePeriodAnchor.END);
        assertFalse(c1.equals(c2));
        c2.setXPosition(TimePeriodAnchor.END);
        assertTrue(c1.equals(c2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeries s1 = new OHLCSeries("Series");
        s1.add(new Year(2006), 1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        OHLCSeriesCollection c2 = null;
        try {
            c2 = (OHLCSeriesCollection) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        s1.setDescription("XYZ");
        assertFalse(c1.equals(c2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeries s1 = new OHLCSeries("Series");
        s1.add(new Year(2006), 1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        OHLCSeriesCollection c2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            c2 = (OHLCSeriesCollection) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);
    }

    /**
     * A test for bug report 1170825 (originally affected XYSeriesCollection,
     * this test is just copied over).
     */
    public void test1170825() {
        OHLCSeries s1 = new OHLCSeries("Series1");
        OHLCSeriesCollection dataset = new OHLCSeriesCollection();
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
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeries s1 = new OHLCSeries("S");
        s1.add(new Year(2009), 1.0, 4.0, 0.5, 2.0);
        c1.addSeries(s1);
        OHLCSeriesCollection c2 = new OHLCSeriesCollection();
        OHLCSeries s2 = new OHLCSeries("S");
        s2.add(new Year(2009), 1.0, 4.0, 0.5, 2.0);
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));
        int h1 = c1.hashCode();
        int h2 = c2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Some checks for the {@link OHLCSeriesCollection#removeSeries(int)}
     * method.
     */
    public void testRemoveSeries_int() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeries s1 = new OHLCSeries("Series 1");
        OHLCSeries s2 = new OHLCSeries("Series 2");
        OHLCSeries s3 = new OHLCSeries("Series 3");
        OHLCSeries s4 = new OHLCSeries("Series 4");
        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.addSeries(s3);
        c1.addSeries(s4);
        c1.removeSeries(2);
        assertTrue(c1.getSeries(2).equals(s4));
        c1.removeSeries(0);
        assertTrue(c1.getSeries(0).equals(s2));
        assertEquals(2, c1.getSeriesCount());
    }

    /**
     * Some checks for the
     * {@link OHLCSeriesCollection#removeSeries(OHLCSeries)} method.
     */
    public void testRemoveSeries() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        OHLCSeries s1 = new OHLCSeries("Series 1");
        OHLCSeries s2 = new OHLCSeries("Series 2");
        OHLCSeries s3 = new OHLCSeries("Series 3");
        OHLCSeries s4 = new OHLCSeries("Series 4");
        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.addSeries(s3);
        c1.addSeries(s4);
        c1.removeSeries(s3);
        assertTrue(c1.getSeries(2).equals(s4));
        c1.removeSeries(s1);
        assertTrue(c1.getSeries(0).equals(s2));
        assertEquals(2, c1.getSeriesCount());
    }

    /**
     * A simple check for the removeAllSeries() method.
     */
    public void testRemoveAllSeries() {
        OHLCSeriesCollection c1 = new OHLCSeriesCollection();
        c1.addChangeListener(this);

        // there should be no change event when clearing an empty series
        this.lastEvent = null;
        c1.removeAllSeries();
        assertNull(this.lastEvent);

        OHLCSeries s1 = new OHLCSeries("Series 1");
        OHLCSeries s2 = new OHLCSeries("Series 2");
        c1.addSeries(s1);
        c1.addSeries(s2);
        c1.removeAllSeries();
        assertEquals(0, c1.getSeriesCount());
        assertNotNull(this.lastEvent);
        this.lastEvent = null;  // clean up
    }

    /** The last received event. */
    private DatasetChangeEvent lastEvent;

    /**
     * Receives dataset change events.
     *
     * @param event  the event.
     */
    public void datasetChanged(DatasetChangeEvent event) {
        this.lastEvent = event;
    }

}
