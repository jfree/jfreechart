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
 * ----------------------------
 * XYSeriesCollectionTests.java
 * ----------------------------
 * (C) Copyright 2003-2012, by Object Refinery Limited and Contributors.
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

package org.jfree.data.xy.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.Range;
import org.jfree.data.UnknownKeyException;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link XYSeriesCollection} class.
 */
public class XYSeriesCollectionTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYSeriesCollectionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYSeriesCollectionTests(String name) {
        super(name);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the constructor.
     */
    public void testConstructor() {
        XYSeriesCollection xysc = new XYSeriesCollection();
        assertEquals(0, xysc.getSeriesCount());
        assertEquals(1.0, xysc.getIntervalWidth(), EPSILON);
        assertEquals(0.5, xysc.getIntervalPositionFactor(), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeries s2 = new XYSeries("Series");
        s2.add(1.0, 1.1);
        XYSeriesCollection c2 = new XYSeriesCollection();
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c1));

        c1.addSeries(new XYSeries("Empty Series"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new XYSeries("Empty Series"));
        assertTrue(c1.equals(c2));

        c1.setIntervalWidth(5.0);
        assertFalse(c1.equals(c2));
        c2.setIntervalWidth(5.0);
        assertTrue(c1.equals(c2));

        c1.setIntervalPositionFactor(0.75);
        assertFalse(c1.equals(c2));
        c2.setIntervalPositionFactor(0.75);
        assertTrue(c1.equals(c2));

        c1.setAutoWidth(true);
        assertFalse(c1.equals(c2));
        c2.setAutoWidth(true);
        assertTrue(c1.equals(c2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeriesCollection c2 = null;
        try {
            c2 = (XYSeriesCollection) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            assertTrue(false);
            return;
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        s1.setDescription("XYZ");
        assertFalse(c1.equals(c2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        Object c1 = new XYSeriesCollection();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeriesCollection c1 = new XYSeriesCollection();
        c1.addSeries(s1);
        XYSeriesCollection c2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            c2 = (XYSeriesCollection) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);
    }

    /**
     * A test for bug report 1170825.
     */
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
    public void testGetSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        assertEquals("s1", c.getSeries(0).getKey());

        boolean pass = false;
        try {
            c.getSeries(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            c.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getSeries(Comparable) method.
     */
    public void testGetSeriesByKey() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        assertEquals("s1", c.getSeries("s1").getKey());

        boolean pass = false;
        try {
            c.getSeries("s2");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            c.getSeries(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }
    
    /**
     * Some basic checks for the addSeries() method.
     */
    public void testAddSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);

        // the dataset should prevent the addition of a series with the
        // same name as an existing series in the dataset
        XYSeries s2 = new XYSeries("s1");
        boolean pass = false;
        try {
            c.addSeries(s2);
        } catch (RuntimeException e) {
            pass = true;
        }
        assertTrue(pass);
        assertEquals(1, c.getSeriesCount());
    }

    /**
     * Some basic checks for the removeSeries() method.
     */
    public void testRemoveSeries() {
        XYSeriesCollection c = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("s1");
        c.addSeries(s1);
        c.removeSeries(0);
        assertEquals(0, c.getSeriesCount());
        c.addSeries(s1);

        boolean pass = false;
        try {
            c.removeSeries(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            c.removeSeries(1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some tests for the indexOf() method.
     */
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
    public void testRenameSeries() {
        XYSeries s1 = new XYSeries("S1");
        XYSeries s2 = new XYSeries("S2");
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
        dataset.addSeries(s2);
        boolean pass = false;
        try {
            s2.setKey("S1");
        }
        catch (RuntimeException e) {
           pass = true;
        }
        assertTrue(pass);
    }

    /**
     * A test to cover bug 3445507.  The issue does not affect
     * XYSeriesCollection.
     */
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
        }
        catch (IllegalArgumentException e) {
            // expected
        }
        assertEquals("B", series2.getKey());  // the series name should not 
        // change because "C" is already the key for the other series in the
        // collection
    }
}
