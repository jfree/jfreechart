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
 * ------------------------------------
 * XYIntervalSeriesCollectionTests.java
 * ------------------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
 * 13-Feb-2007 : Check for independence in testCloning() (DG);
 * 18-Jan-2008 : Added testRemoveSeries() (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
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

import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link XYIntervalSeriesCollection} class.
 */
public class XYIntervalSeriesCollectionTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYIntervalSeriesCollectionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYIntervalSeriesCollectionTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        XYIntervalSeriesCollection c1 = new XYIntervalSeriesCollection();
        XYIntervalSeriesCollection c2 = new XYIntervalSeriesCollection();
        assertEquals(c1, c2);

        // add a series
        XYIntervalSeries s1 = new XYIntervalSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3, 1.4, 1.5);
        c1.addSeries(s1);
        assertFalse(c1.equals(c2));
        XYIntervalSeries s2 = new XYIntervalSeries("Series");
        s2.add(1.0, 1.1, 1.2, 1.3, 1.4, 1.5);
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));

        // add an empty series
        c1.addSeries(new XYIntervalSeries("Empty Series"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new XYIntervalSeries("Empty Series"));
        assertTrue(c1.equals(c2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYIntervalSeriesCollection c1 = new XYIntervalSeriesCollection();
        XYIntervalSeries s1 = new XYIntervalSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3, 1.4, 1.5);
        XYIntervalSeriesCollection c2 = null;
        try {
            c2 = (XYIntervalSeriesCollection) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        c1.addSeries(new XYIntervalSeries("Empty"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new XYIntervalSeries("Empty"));
        assertTrue(c1.equals(c2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        XYIntervalSeriesCollection c1 = new XYIntervalSeriesCollection();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYIntervalSeriesCollection c1 = new XYIntervalSeriesCollection();
        XYIntervalSeries s1 = new XYIntervalSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3, 1.4, 1.5);
        XYIntervalSeriesCollection c2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            c2 = (XYIntervalSeriesCollection) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);

        // check independence
        c1.addSeries(new XYIntervalSeries("Empty"));
        assertFalse(c1.equals(c2));
        c2.addSeries(new XYIntervalSeries("Empty"));
        assertTrue(c1.equals(c2));
    }

    /**
     * Some basic checks for the removeSeries() method.
     */
    public void testRemoveSeries() {
        XYIntervalSeriesCollection c = new XYIntervalSeriesCollection();
        XYIntervalSeries s1 = new XYIntervalSeries("s1");
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
     * A test for bug report 1170825 (originally affected XYSeriesCollection,
     * this test is just copied over).
     */
    public void test1170825() {
        XYIntervalSeries s1 = new XYIntervalSeries("Series1");
        XYIntervalSeriesCollection dataset = new XYIntervalSeriesCollection();
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

}
