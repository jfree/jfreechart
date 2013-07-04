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
 * ----------------------------------
 * DefaultIntervalXYDatasetTests.java
 * ----------------------------------
 * (C) Copyright 2006-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Oct-2006 : Version 1 (DG);
 * 02-Nov-2006 : Added testAddSeries() method (DG);
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

import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.util.PublicCloneable;

/**
 * Some tests for the {@link DefaultIntervalXYDataset} class.
 */
public class DefaultIntervalXYDatasetTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DefaultIntervalXYDatasetTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DefaultIntervalXYDatasetTests(String name) {
        super(name);
    }

    /**
     * Some checks for the getSeriesCount() method.
     */
    public void testGetSeriesCount() {
        DefaultIntervalXYDataset d = new DefaultIntervalXYDataset();
        assertEquals(0, d.getSeriesCount());
        d = createSampleDataset1();
        assertEquals(2, d.getSeriesCount());
    }

    /**
     * Some checks for the getSeriesKey(int) method.
     */
    public void testGetSeriesKey() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals("S1", d.getSeriesKey(0));
        assertEquals("S2", d.getSeriesKey(1));

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
     * Some checks for the getItemCount() method.
     */
    public void testGetItemCount() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(3, d.getItemCount(0));
        assertEquals(3, d.getItemCount(1));

        // try an index out of bounds
        boolean pass = false;
        try {
            d.getItemCount(2);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the getXValue() method.
     */
    public void testGetXValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(1.0, d.getXValue(0, 0), EPSILON);
        assertEquals(2.0, d.getXValue(0, 1), EPSILON);
        assertEquals(3.0, d.getXValue(0, 2), EPSILON);
        assertEquals(11.0, d.getXValue(1, 0), EPSILON);
        assertEquals(12.0, d.getXValue(1, 1), EPSILON);
        assertEquals(13.0, d.getXValue(1, 2), EPSILON);
    }

    /**
     * Some checks for the getYValue() method.
     */
    public void testGetYValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(4.0, d.getYValue(0, 0), EPSILON);
        assertEquals(5.0, d.getYValue(0, 1), EPSILON);
        assertEquals(6.0, d.getYValue(0, 2), EPSILON);
        assertEquals(14.0, d.getYValue(1, 0), EPSILON);
        assertEquals(15.0, d.getYValue(1, 1), EPSILON);
        assertEquals(16.0, d.getYValue(1, 2), EPSILON);
    }

    /**
     * Some checks for the getStartXValue() method.
     */
    public void testGetStartXValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(0.9, d.getStartXValue(0, 0), EPSILON);
        assertEquals(1.9, d.getStartXValue(0, 1), EPSILON);
        assertEquals(2.9, d.getStartXValue(0, 2), EPSILON);
        assertEquals(10.9, d.getStartXValue(1, 0), EPSILON);
        assertEquals(11.9, d.getStartXValue(1, 1), EPSILON);
        assertEquals(12.9, d.getStartXValue(1, 2), EPSILON);
    }

    /**
     * Some checks for the getEndXValue() method.
     */
    public void testGetEndXValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(1.1, d.getEndXValue(0, 0), EPSILON);
        assertEquals(2.1, d.getEndXValue(0, 1), EPSILON);
        assertEquals(3.1, d.getEndXValue(0, 2), EPSILON);
        assertEquals(11.1, d.getEndXValue(1, 0), EPSILON);
        assertEquals(12.1, d.getEndXValue(1, 1), EPSILON);
        assertEquals(13.1, d.getEndXValue(1, 2), EPSILON);
    }

    /**
     * Some checks for the getStartYValue() method.
     */
    public void testGetStartYValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(1.09, d.getStartYValue(0, 0), EPSILON);
        assertEquals(2.09, d.getStartYValue(0, 1), EPSILON);
        assertEquals(3.09, d.getStartYValue(0, 2), EPSILON);
        assertEquals(11.09, d.getStartYValue(1, 0), EPSILON);
        assertEquals(12.09, d.getStartYValue(1, 1), EPSILON);
        assertEquals(13.09, d.getStartYValue(1, 2), EPSILON);
    }

    /**
     * Some checks for the getEndYValue() method.
     */
    public void testGetEndYValue() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(1.11, d.getEndYValue(0, 0), EPSILON);
        assertEquals(2.11, d.getEndYValue(0, 1), EPSILON);
        assertEquals(3.11, d.getEndYValue(0, 2), EPSILON);
        assertEquals(11.11, d.getEndYValue(1, 0), EPSILON);
        assertEquals(12.11, d.getEndYValue(1, 1), EPSILON);
        assertEquals(13.11, d.getEndYValue(1, 2), EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        DefaultIntervalXYDataset d1 = new DefaultIntervalXYDataset();
        DefaultIntervalXYDataset d2 = new DefaultIntervalXYDataset();
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
    public void testCloning() {
        DefaultIntervalXYDataset d1 = new DefaultIntervalXYDataset();
        DefaultIntervalXYDataset d2 = null;
        try {
            d2 = (DefaultIntervalXYDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // try a dataset with some content...
        d1 = createSampleDataset1();
        try {
            d2 = (DefaultIntervalXYDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Another test for cloning.
     */
    public void testCloning2() {
        DefaultIntervalXYDataset d1 = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {1.0, 2.0, 3.0};
        double[] x1Start = new double[] {0.9, 1.9, 2.9};
        double[] x1End = new double[] {1.1, 2.1, 3.1};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        d1.addSeries("S1", data1);
        DefaultIntervalXYDataset d2 = null;
        try {
            d2 = (DefaultIntervalXYDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // check independence
        x1[0] = 111.1;
        assertFalse(d1.equals(d2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        DefaultIntervalXYDataset d1 = new DefaultIntervalXYDataset();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DefaultIntervalXYDataset d1 = new DefaultIntervalXYDataset();
        DefaultIntervalXYDataset d2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            d2 = (DefaultIntervalXYDataset) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(d1, d2);

        // try a dataset with some content...
        d1 = createSampleDataset1();
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            d2 = (DefaultIntervalXYDataset) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(d1, d2);

    }

    /**
     * Some checks for the indexOf(Comparable) method.
     */
    public void testIndexOf() {
        DefaultIntervalXYDataset d = createSampleDataset1();
        assertEquals(0, d.indexOf("S1"));
        assertEquals(1, d.indexOf("S2"));
        assertEquals(-1, d.indexOf("Green Eggs and Ham"));
        assertEquals(-1, d.indexOf(null));
    }

    /**
     * Some tests for the addSeries() method.
     */
    public void testAddSeries() {
        DefaultIntervalXYDataset d = new DefaultIntervalXYDataset();
        d.addSeries("S1", new double[][] {{1.0}, {0.5}, {1.5}, {2.0}, {2.5},
                {1.5}});
        assertEquals(1, d.getSeriesCount());
        assertEquals("S1", d.getSeriesKey(0));

        // check that adding a series will overwrite the old series
        d.addSeries("S1", new double[][] {{1.1}, {0.6}, {1.6}, {2.1}, {2.6},
                {1.6}});
        assertEquals(1, d.getSeriesCount());
        assertEquals(2.1, d.getYValue(0, 0), EPSILON);

        // check null key
        boolean pass = false;
        try
        {
          d.addSeries(null, new double[][] {{1.1}, {0.6}, {1.6}, {2.1}, {2.6},
                  {1.6}});
        }
        catch (IllegalArgumentException e)
        {
          pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Creates a sample dataset for testing.
     *
     * @return A sample dataset.
     */
    public DefaultIntervalXYDataset createSampleDataset1() {
        DefaultIntervalXYDataset d = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {1.0, 2.0, 3.0};
        double[] x1Start = new double[] {0.9, 1.9, 2.9};
        double[] x1End = new double[] {1.1, 2.1, 3.1};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        d.addSeries("S1", data1);

        double[] x2 = new double[] {11.0, 12.0, 13.0};
        double[] x2Start = new double[] {10.9, 11.9, 12.9};
        double[] x2End = new double[] {11.1, 12.1, 13.1};
        double[] y2 = new double[] {14.0, 15.0, 16.0};
        double[] y2Start = new double[] {11.09, 12.09, 13.09};
        double[] y2End = new double[] {11.11, 12.11, 13.11};
        double[][] data2 = new double[][] {x2, x2Start, x2End, y2, y2Start,
                y2End};
        d.addSeries("S2", data2);
        return d;
    }

}
