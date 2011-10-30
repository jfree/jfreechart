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
 * ------------------
 * XYSeriesTests.java
 * ------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Dec-2003 : Version 1 (DG);
 * 15-Jan-2007 : Added tests for new toArray() method (DG);
 * 30-Jan-2007 : Fixed some code that won't compile with Java 1.4 (DG);
 * 31-Oct-2007 : New hashCode() test (DG);
 * 01-May-2008 : Added testAddOrUpdate3() (DG);
 * 24-Nov-2008 : Added testBug1955483() (DG);
 * 06-Mar-2009 : Added tests for cached bounds values (DG);
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

import org.jfree.data.general.SeriesException;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

/**
 * Tests for the {@link XYSeries} class.
 */
public class XYSeriesTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYSeriesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYSeriesTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeries s2 = new XYSeries("Series");
        s2.add(1.0, 1.1);
        assertTrue(s1.equals(s2));
        assertTrue(s2.equals(s1));

        s1.setKey("Series X");
        assertFalse(s1.equals(s2));
        s2.setKey("Series X");
        assertTrue(s1.equals(s2));

        s1.add(2.0, 2.2);
        assertFalse(s1.equals(s2));
        s2.add(2.0, 2.2);
        assertTrue(s1.equals(s2));
    }

    /**
     * Some simple checks for the hashCode() method.
     */
    public void testHashCode() {
        XYSeries s1 = new XYSeries("Test");
        XYSeries s2 = new XYSeries("Test");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(1.0, 500.0);
        s2.add(1.0, 500.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(2.0, null);
        s2.add(2.0, null);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(5.0, 111.0);
        s2.add(5.0, 111.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add(9.0, 1.0);
        s2.add(9.0, 1.0);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeries s2 = null;
        try {
            s2 = (XYSeries) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

    /**
     * Another test of the clone() method.
     */
    public void testCloning2() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 100.0);
        s1.add(2.0, null);
        s1.add(3.0, 200.0);
        XYSeries s2 = null;
        try {
            s2 = (XYSeries) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(s1.equals(s2));

        // check independence
        s2.add(4.0, 300.0);
        assertFalse(s1.equals(s2));
        s1.add(4.0, 300.0);
        assertTrue(s1.equals(s2));
    }

    /**
     * Another test of the clone() method.
     */
    public void testCloning3() {
        XYSeries s1 = new XYSeries("S1");
        XYSeries s2 = null;
        try {
            s2 = (XYSeries) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(s1.equals(s2));

        // check independence
        s2.add(4.0, 300.0);
        assertFalse(s1.equals(s2));
        s1.add(4.0, 300.0);
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYSeries s1 = new XYSeries("Series");
        s1.add(1.0, 1.1);
        XYSeries s2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(s1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            s2 = (XYSeries) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(s1, s2);
    }

    /**
     * Simple test for the indexOf() method.
     */
    public void testIndexOf() {
        XYSeries s1 = new XYSeries("Series 1");
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        s1.add(3.0, 3.0);
        assertEquals(0, s1.indexOf(new Double(1.0)));
        assertEquals(1, s1.indexOf(new Double(2.0)));
        assertEquals(2, s1.indexOf(new Double(3.0)));
        assertEquals(-4, s1.indexOf(new Double(99.9)));
    }

    /**
     * A check for the indexOf() method for an unsorted series.
     */
    public void testIndexOf2() {
        XYSeries s1 = new XYSeries("Series 1", false, true);
        s1.add(1.0, 1.0);
        s1.add(3.0, 3.0);
        s1.add(2.0, 2.0);
        assertEquals(0, s1.indexOf(new Double(1.0)));
        assertEquals(1, s1.indexOf(new Double(3.0)));
        assertEquals(2, s1.indexOf(new Double(2.0)));
    }

    /**
     * A check for the indexOf(Number) method when the series has duplicate
     * x-values.
     */
    public void testIndexOf3() {
        XYSeries s1 = new XYSeries("Series 1");
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        s1.add(2.0, 3.0);
        assertEquals(0, s1.indexOf(new Double(1.0)));
        assertEquals(1, s1.indexOf(new Double(2.0)));
    }

    /**
     * Simple test for the remove() method.
     */
    public void testRemove() {
        XYSeries s1 = new XYSeries("Series 1");
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        s1.add(3.0, 3.0);
        assertEquals(3, s1.getItemCount());

        s1.remove(new Double(2.0));
        assertEquals(new Double(3.0), s1.getX(1));

        s1.remove(0);
        assertEquals(new Double(3.0), s1.getX(0));
    }

    /**
     * Some checks for the remove(int) method.
     */
    public void testRemove2() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        s1.add(4.0, 4.4);
        s1.add(5.0, 5.5);
        s1.add(6.0, 6.6);
        assertEquals(6, s1.getItemCount());
        assertEquals(1.0, s1.getMinX(), EPSILON);
        assertEquals(6.0, s1.getMaxX(), EPSILON);
        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(6.6, s1.getMaxY(), EPSILON);

        s1.remove(5);
        assertEquals(5, s1.getItemCount());
        assertEquals(1.0, s1.getMinX(), EPSILON);
        assertEquals(5.0, s1.getMaxX(), EPSILON);
        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(5.5, s1.getMaxY(), EPSILON);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * When items are added with duplicate x-values, we expect them to remain
     * in the order they were added.
     */
    public void testAdditionOfDuplicateXValues() {
        XYSeries s1 = new XYSeries("Series 1");
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        s1.add(2.0, 3.0);
        s1.add(2.0, 4.0);
        s1.add(3.0, 5.0);
        assertEquals(1.0, s1.getY(0).doubleValue(), EPSILON);
        assertEquals(2.0, s1.getY(1).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getY(2).doubleValue(), EPSILON);
        assertEquals(4.0, s1.getY(3).doubleValue(), EPSILON);
        assertEquals(5.0, s1.getY(4).doubleValue(), EPSILON);
    }

    /**
     * Some checks for the update(Number, Number) method.
     */
    public void testUpdate() {
        XYSeries series = new XYSeries("S1");
        series.add(new Integer(1), new Integer(2));
        assertEquals(new Integer(2), series.getY(0));
        series.update(new Integer(1), new Integer(3));
        assertEquals(new Integer(3), series.getY(0));
        try {
            series.update(new Integer(2), new Integer(99));
            assertTrue(false);
        }
        catch (SeriesException e) {
            // got the required exception
        }
    }

    /**
     * Some checks for the update() method for an unsorted series.
     */
    public void testUpdate2() {
       XYSeries series = new XYSeries("Series", false, true);
       series.add(5.0, 55.0);
       series.add(4.0, 44.0);
       series.add(6.0, 66.0);
       series.update(new Double(4.0), new Double(99.0));
       assertEquals(new Double(99.0), series.getY(1));
    }

    /**
     * Some checks for the addOrUpdate() method.
     */
    public void testAddOrUpdate() {
        XYSeries series = new XYSeries("S1", true, false);
        XYDataItem old = series.addOrUpdate(new Long(1), new Long(2));
        assertTrue(old == null);
        assertEquals(1, series.getItemCount());
        assertEquals(new Long(2), series.getY(0));

        old = series.addOrUpdate(new Long(2), new Long(3));
        assertTrue(old == null);
        assertEquals(2, series.getItemCount());
        assertEquals(new Long(3), series.getY(1));

        old = series.addOrUpdate(new Long(1), new Long(99));
        assertEquals(new XYDataItem(new Long(1), new Long(2)), old);
        assertEquals(2, series.getItemCount());
        assertEquals(new Long(99), series.getY(0));
        assertEquals(new Long(3), series.getY(1));
    }

    /**
     * Some checks for the addOrUpdate() method for an UNSORTED series.
     */
    public void testAddOrUpdate2() {
        XYSeries series = new XYSeries("Series", false, false);
        series.add(5.0, 5.5);
        series.add(6.0, 6.6);
        series.add(3.0, 3.3);
        series.add(4.0, 4.4);
        series.add(2.0, 2.2);
        series.add(1.0, 1.1);
        series.addOrUpdate(new Double(3.0), new Double(33.3));
        series.addOrUpdate(new Double(2.0), new Double(22.2));
        assertEquals(33.3, series.getY(2).doubleValue(), EPSILON);
        assertEquals(22.2, series.getY(4).doubleValue(), EPSILON);
    }

    /**
     * Another test for the addOrUpdate() method.
     */
    public void testAddOrUpdate3() {
        XYSeries series = new XYSeries("Series", false, true);
        series.addOrUpdate(1.0, 1.0);
        series.addOrUpdate(1.0, 2.0);
        series.addOrUpdate(1.0, 3.0);
        assertEquals(new Double(1.0), series.getY(0));
        assertEquals(new Double(2.0), series.getY(1));
        assertEquals(new Double(3.0), series.getY(2));
        assertEquals(3, series.getItemCount());
    }

    /**
     * Some checks for the add() method for an UNSORTED series.
     */
    public void testAdd() {
        XYSeries series = new XYSeries("Series", false, true);
        series.add(5.0, 5.50);
        series.add(5.1, 5.51);
        series.add(6.0, 6.6);
        series.add(3.0, 3.3);
        series.add(4.0, 4.4);
        series.add(2.0, 2.2);
        series.add(1.0, 1.1);
        assertEquals(5.5, series.getY(0).doubleValue(), EPSILON);
        assertEquals(5.51, series.getY(1).doubleValue(), EPSILON);
        assertEquals(6.6, series.getY(2).doubleValue(), EPSILON);
        assertEquals(3.3, series.getY(3).doubleValue(), EPSILON);
        assertEquals(4.4, series.getY(4).doubleValue(), EPSILON);
        assertEquals(2.2, series.getY(5).doubleValue(), EPSILON);
        assertEquals(1.1, series.getY(6).doubleValue(), EPSILON);
    }

    /**
     * A simple check that the maximumItemCount attribute is working.
     */
    public void testSetMaximumItemCount() {
        XYSeries s1 = new XYSeries("S1");
        assertEquals(Integer.MAX_VALUE, s1.getMaximumItemCount());
        s1.setMaximumItemCount(2);
        assertEquals(2, s1.getMaximumItemCount());
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getX(1).doubleValue(), EPSILON);
    }

    /**
     * Check that the maximum item count can be applied retrospectively.
     */
    public void testSetMaximumItemCount2() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        s1.setMaximumItemCount(2);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getX(1).doubleValue(), EPSILON);
    }

    /**
     * Check that the item bounds are determined correctly when there is a
     * maximum item count.
     */
    public void testSetMaximumItemCount3() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        s1.add(4.0, 4.4);
        s1.add(5.0, 5.5);
        s1.add(6.0, 6.6);
        s1.setMaximumItemCount(2);
        assertEquals(5.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(6.0, s1.getX(1).doubleValue(), EPSILON);
        assertEquals(5.0, s1.getMinX(), EPSILON);
        assertEquals(6.0, s1.getMaxX(), EPSILON);
        assertEquals(5.5, s1.getMinY(), EPSILON);
        assertEquals(6.6, s1.getMaxY(), EPSILON);
    }

    /**
     * Check that the item bounds are determined correctly when there is a
     * maximum item count.
     */
    public void testSetMaximumItemCount4() {
        XYSeries s1 = new XYSeries("S1");
        s1.setMaximumItemCount(2);
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        assertEquals(2.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(3.0, s1.getX(1).doubleValue(), EPSILON);
        assertEquals(2.0, s1.getMinX(), EPSILON);
        assertEquals(3.0, s1.getMaxX(), EPSILON);
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the toArray() method.
     */
    public void testToArray() {
        XYSeries s = new XYSeries("S1");
        double[][] array = s.toArray();
        assertEquals(2, array.length);
        assertEquals(0, array[0].length);
        assertEquals(0, array[1].length);

        s.add(1.0, 2.0);
        array = s.toArray();
        assertEquals(1, array[0].length);
        assertEquals(1, array[1].length);
        assertEquals(2, array.length);
        assertEquals(1.0, array[0][0], EPSILON);
        assertEquals(2.0, array[1][0], EPSILON);

        s.add(2.0, null);
        array = s.toArray();
        assertEquals(2, array.length);
        assertEquals(2, array[0].length);
        assertEquals(2, array[1].length);
        assertEquals(2.0, array[0][1], EPSILON);
        assertTrue(Double.isNaN(array[1][1]));
    }

    /**
     * Some checks for an example using the toArray() method.
     */
    public void testToArrayExample() {
        XYSeries s = new XYSeries("S");
        s.add(1.0, 11.0);
        s.add(2.0, 22.0);
        s.add(3.5, 35.0);
        s.add(5.0, null);
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("S", s.toArray());
        assertEquals(1, dataset.getSeriesCount());
        assertEquals(4, dataset.getItemCount(0));
        assertEquals("S", dataset.getSeriesKey(0));
        assertEquals(1.0, dataset.getXValue(0, 0), EPSILON);
        assertEquals(2.0, dataset.getXValue(0, 1), EPSILON);
        assertEquals(3.5, dataset.getXValue(0, 2), EPSILON);
        assertEquals(5.0, dataset.getXValue(0, 3), EPSILON);
        assertEquals(11.0, dataset.getYValue(0, 0), EPSILON);
        assertEquals(22.0, dataset.getYValue(0, 1), EPSILON);
        assertEquals(35.0, dataset.getYValue(0, 2), EPSILON);
        assertTrue(Double.isNaN(dataset.getYValue(0, 3)));
    }

    /**
     * Another test for the addOrUpdate() method.
     */
    public void testBug1955483() {
        XYSeries series = new XYSeries("Series", true, true);
        series.addOrUpdate(1.0, 1.0);
        series.addOrUpdate(1.0, 2.0);
        assertEquals(new Double(1.0), series.getY(0));
        assertEquals(new Double(2.0), series.getY(1));
        assertEquals(2, series.getItemCount());
    }

    /**
     * Some checks for the delete(int, int) method.
     */
    public void testDelete() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);
        s1.add(4.0, 4.4);
        s1.add(5.0, 5.5);
        s1.add(6.0, 6.6);
        s1.delete(2, 5);
        assertEquals(2, s1.getItemCount());
        assertEquals(1.0, s1.getX(0).doubleValue(), EPSILON);
        assertEquals(2.0, s1.getX(1).doubleValue(), EPSILON);
        assertEquals(1.0, s1.getMinX(), EPSILON);
        assertEquals(2.0, s1.getMaxX(), EPSILON);
        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(2.2, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the getMinX() method.
     */
    public void testGetMinX() {
        XYSeries s1 = new XYSeries("S1");
        assertTrue(Double.isNaN(s1.getMinX()));

        s1.add(1.0, 1.1);
        assertEquals(1.0, s1.getMinX(), EPSILON);

        s1.add(2.0, 2.2);
        assertEquals(1.0, s1.getMinX(), EPSILON);

        s1.add(Double.NaN, 99.9);
        assertEquals(1.0, s1.getMinX(), EPSILON);

        s1.add(-1.0, -1.1);
        assertEquals(-1.0, s1.getMinX(), EPSILON);

        s1.add(0.0, null);
        assertEquals(-1.0, s1.getMinX(), EPSILON);
    }

    /**
     * Some checks for the getMaxX() method.
     */
    public void testGetMaxX() {
        XYSeries s1 = new XYSeries("S1");
        assertTrue(Double.isNaN(s1.getMaxX()));

        s1.add(1.0, 1.1);
        assertEquals(1.0, s1.getMaxX(), EPSILON);

        s1.add(2.0, 2.2);
        assertEquals(2.0, s1.getMaxX(), EPSILON);

        s1.add(Double.NaN, 99.9);
        assertEquals(2.0, s1.getMaxX(), EPSILON);

        s1.add(-1.0, -1.1);
        assertEquals(2.0, s1.getMaxX(), EPSILON);

        s1.add(0.0, null);
        assertEquals(2.0, s1.getMaxX(), EPSILON);
    }

    /**
     * Some checks for the getMinY() method.
     */
    public void testGetMinY() {
        XYSeries s1 = new XYSeries("S1");
        assertTrue(Double.isNaN(s1.getMinY()));

        s1.add(1.0, 1.1);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(2.0, 2.2);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(Double.NaN, 99.9);
        assertEquals(1.1, s1.getMinY(), EPSILON);

        s1.add(-1.0, -1.1);
        assertEquals(-1.1, s1.getMinY(), EPSILON);

        s1.add(0.0, null);
        assertEquals(-1.1, s1.getMinY(), EPSILON);
   }

    /**
     * Some checks for the getMaxY() method.
     */
    public void testGetMaxY() {
        XYSeries s1 = new XYSeries("S1");
        assertTrue(Double.isNaN(s1.getMaxY()));

        s1.add(1.0, 1.1);
        assertEquals(1.1, s1.getMaxY(), EPSILON);

        s1.add(2.0, 2.2);
        assertEquals(2.2, s1.getMaxY(), EPSILON);

        s1.add(Double.NaN, 99.9);
        assertEquals(99.9, s1.getMaxY(), EPSILON);

        s1.add(-1.0, -1.1);
        assertEquals(99.9, s1.getMaxY(), EPSILON);

        s1.add(0.0, null);
        assertEquals(99.9, s1.getMaxY(), EPSILON);
    }

    /**
     * A test for the clear method.
     */
    public void testClear() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);

        assertEquals(3, s1.getItemCount());

        s1.clear();
        assertEquals(0, s1.getItemCount());
        assertTrue(Double.isNaN(s1.getMinX()));
        assertTrue(Double.isNaN(s1.getMaxX()));
        assertTrue(Double.isNaN(s1.getMinY()));
        assertTrue(Double.isNaN(s1.getMaxY()));
    }

    /**
     * Some checks for the updateByIndex() method.
     */
    public void testUpdateByIndex() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);

        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);

        s1.updateByIndex(0, new Double(-5.0));
        assertEquals(-5.0, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);

        s1.updateByIndex(0, null);
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);

        s1.updateByIndex(2, null);
        assertEquals(2.2, s1.getMinY(), EPSILON);
        assertEquals(2.2, s1.getMaxY(), EPSILON);

        s1.updateByIndex(1, null);
        assertTrue(Double.isNaN(s1.getMinY()));
        assertTrue(Double.isNaN(s1.getMaxY()));
    }

    /**
     * Some checks for the updateByIndex() method.
     */
    public void testUpdateByIndex2() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, Double.NaN);

        assertTrue(Double.isNaN(s1.getMinY()));
        assertTrue(Double.isNaN(s1.getMaxY()));

        s1.updateByIndex(0, new Double(1.0));
        assertEquals(1.0, s1.getMinY(), EPSILON);
        assertEquals(1.0, s1.getMaxY(), EPSILON);

        s1.updateByIndex(0, new Double(2.0));
        assertEquals(2.0, s1.getMinY(), EPSILON);
        assertEquals(2.0, s1.getMaxY(), EPSILON);

        s1.add(-1.0, -1.0);
        s1.updateByIndex(0, new Double(0.0));
        assertEquals(0.0, s1.getMinY(), EPSILON);
        assertEquals(2.0, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the updateByIndex() method.
     */
    public void testUpdateByIndex3() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        s1.add(3.0, 3.3);

        s1.updateByIndex(1, new Double(2.05));
        assertEquals(1.1, s1.getMinY(), EPSILON);
        assertEquals(3.3, s1.getMaxY(), EPSILON);
    }

    /**
     * Some checks for the update(Number, Number) method.
     */
    public void testUpdateXY() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, Double.NaN);

        assertTrue(Double.isNaN(s1.getMinY()));
        assertTrue(Double.isNaN(s1.getMaxY()));

        s1.update(new Double(1.0), new Double(1.0));
        assertEquals(1.0, s1.getMinY(), EPSILON);
        assertEquals(1.0, s1.getMaxY(), EPSILON);

        s1.update(new Double(1.0), new Double(2.0));
        assertEquals(2.0, s1.getMinY(), EPSILON);
        assertEquals(2.0, s1.getMaxY(), EPSILON);
    }

}
