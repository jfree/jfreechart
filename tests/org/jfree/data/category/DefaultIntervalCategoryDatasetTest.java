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
 * ---------------------------------------
 * DefaultIntervalCategoryDatasetTest.java
 * ---------------------------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Mar-2007 : Version 1 (DG);
 * 25-Feb-2008 : Added new tests to check behaviour of an empty dataset (DG);
 * 11-Feb-2009 : Fixed locale-sensitive failures (DG);
 *
 */

package org.jfree.data.category;

import java.util.List;

import org.jfree.chart.TestUtilities;

import org.jfree.data.DataUtilities;
import org.jfree.data.UnknownKeyException;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the {@link DefaultIntervalCategoryDataset} class.
 */
public class DefaultIntervalCategoryDatasetTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d = new DefaultIntervalCategoryDataset(
                new Comparable[] {"Series 1", "Series 2"},
                new Comparable[] {"Category 1", "Category 2", "Category 3"},
                DataUtilities.createNumberArray2D(starts),
                DataUtilities.createNumberArray2D(ends));

        assertEquals(new Double(0.1), d.getStartValue("Series 1",
                "Category 1"));
        assertEquals(new Double(0.2), d.getStartValue("Series 1",
                "Category 2"));
        assertEquals(new Double(0.3), d.getStartValue("Series 1",
                "Category 3"));
        assertEquals(new Double(0.3), d.getStartValue("Series 2",
                "Category 1"));
        assertEquals(new Double(0.4), d.getStartValue("Series 2",
                "Category 2"));
        assertEquals(new Double(0.5), d.getStartValue("Series 2",
                "Category 3"));

        assertEquals(new Double(0.5), d.getEndValue("Series 1",
                "Category 1"));
        assertEquals(new Double(0.6), d.getEndValue("Series 1",
                "Category 2"));
        assertEquals(new Double(0.7), d.getEndValue("Series 1",
                "Category 3"));
        assertEquals(new Double(0.7), d.getEndValue("Series 2",
                "Category 1"));
        assertEquals(new Double(0.8), d.getEndValue("Series 2",
                "Category 2"));
        assertEquals(new Double(0.9), d.getEndValue("Series 2",
                "Category 3"));

        boolean pass = false;
        try {
            d.getValue("XX", "Category 1");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d.getValue("Series 1", "XX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }


    /**
     * Some tests for the getRowCount() method.
     */
    @Test
    public void testGetRowAndColumnCount() {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d
                = new DefaultIntervalCategoryDataset(starts, ends);

        assertEquals(2, d.getRowCount());
        assertEquals(3, d.getColumnCount());
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        double[] starts_S1A = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2A = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1A = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2A = new double[] {0.7, 0.8, 0.9};
        double[][] startsA = new double[][] {starts_S1A, starts_S2A};
        double[][] endsA = new double[][] {ends_S1A, ends_S2A};
        DefaultIntervalCategoryDataset dA
                = new DefaultIntervalCategoryDataset(startsA, endsA);

        double[] starts_S1B = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2B = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1B = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2B = new double[] {0.7, 0.8, 0.9};
        double[][] startsB = new double[][] {starts_S1B, starts_S2B};
        double[][] endsB = new double[][] {ends_S1B, ends_S2B};
        DefaultIntervalCategoryDataset dB
                = new DefaultIntervalCategoryDataset(startsB, endsB);

        assertTrue(dA.equals(dB));
        assertTrue(dB.equals(dA));

        // check that two empty datasets are equal
        DefaultIntervalCategoryDataset empty1
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        DefaultIntervalCategoryDataset empty2
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertTrue(empty1.equals(empty2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d1
                = new DefaultIntervalCategoryDataset(starts, ends);
        DefaultIntervalCategoryDataset d2 = (DefaultIntervalCategoryDataset) 
                TestUtilities.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d1 = new DefaultIntervalCategoryDataset(
                new Comparable[] {"Series 1", "Series 2"},
                new Comparable[] {"Category 1", "Category 2", "Category 3"},
                DataUtilities.createNumberArray2D(starts),
                DataUtilities.createNumberArray2D(ends));
        DefaultIntervalCategoryDataset d2 = null;
        d2 = (DefaultIntervalCategoryDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // check that the clone doesn't share the same underlying arrays.
        d1.setStartValue(0, "Category 1", new Double(0.99));
        assertFalse(d1.equals(d2));
        d2.setStartValue(0, "Category 1", new Double(0.99));
        assertTrue(d1.equals(d2));
    }

    /**
     * A check to ensure that an empty dataset can be cloned.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        DefaultIntervalCategoryDataset d1
                = new DefaultIntervalCategoryDataset(new double[0][0],
                    new double[0][0]);
        DefaultIntervalCategoryDataset d2 = null;
        d2 = (DefaultIntervalCategoryDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Some basic checks for the setStartValue() method.
     */
    @Test
    public void testSetStartValue() {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d1 = new DefaultIntervalCategoryDataset(
                new Comparable[] {"Series 1", "Series 2"},
                new Comparable[] {"Category 1", "Category 2", "Category 3"},
                DataUtilities.createNumberArray2D(starts),
                DataUtilities.createNumberArray2D(ends));
        d1.setStartValue(0, "Category 2", new Double(99.9));
        assertEquals(new Double(99.9), d1.getStartValue("Series 1",
                "Category 2"));

        boolean pass = false;
        try {
            d1.setStartValue(-1, "Category 2", new Double(99.9));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d1.setStartValue(2, "Category 2", new Double(99.9));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some basic checks for the setEndValue() method.
     */
    @Test
    public void testSetEndValue() {
        double[] starts_S1 = new double[] {0.1, 0.2, 0.3};
        double[] starts_S2 = new double[] {0.3, 0.4, 0.5};
        double[] ends_S1 = new double[] {0.5, 0.6, 0.7};
        double[] ends_S2 = new double[] {0.7, 0.8, 0.9};
        double[][] starts = new double[][] {starts_S1, starts_S2};
        double[][] ends = new double[][] {ends_S1, ends_S2};
        DefaultIntervalCategoryDataset d1 = new DefaultIntervalCategoryDataset(
                new Comparable[] {"Series 1", "Series 2"},
                new Comparable[] {"Category 1", "Category 2", "Category 3"},
                DataUtilities.createNumberArray2D(starts),
                DataUtilities.createNumberArray2D(ends));
        d1.setEndValue(0, "Category 2", new Double(99.9));
        assertEquals(new Double(99.9), d1.getEndValue("Series 1",
                "Category 2"));

        boolean pass = false;
        try {
            d1.setEndValue(-1, "Category 2", new Double(99.9));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d1.setEndValue(2, "Category 2", new Double(99.9));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getSeriesCount() method.
     */
    @Test
    public void testGetSeriesCount() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(0, empty.getSeriesCount());
    }

    /**
     * Some checks for the getCategoryCount() method.
     */
    @Test
    public void testGetCategoryCount() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(0, empty.getCategoryCount());
    }

    /**
     * Some checks for the getSeriesIndex() method.
     */
    @Test
    public void testGetSeriesIndex() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(-1, empty.getSeriesIndex("ABC"));
    }

    /**
     * Some checks for the getRowIndex() method.
     */
    @Test
    public void testGetRowIndex() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(-1, empty.getRowIndex("ABC"));
    }

    /**
     * Some checks for the setSeriesKeys() method.
     */
    @Test
    public void testSetSeriesKeys() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        boolean pass = true;
        try {
            empty.setSeriesKeys(new String[0]);
        }
        catch (RuntimeException e) {
            pass = false;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getCategoryIndex() method.
     */
    @Test
    public void testGetCategoryIndex() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(-1, empty.getCategoryIndex("ABC"));
    }

    /**
     * Some checks for the getColumnIndex() method.
     */
    @Test
    public void testGetColumnIndex() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(-1, empty.getColumnIndex("ABC"));
    }

    /**
     * Some checks for the setCategoryKeys() method.
     */
    @Test
    public void testSetCategoryKeys() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        boolean pass = true;
        try {
            empty.setCategoryKeys(new String[0]);
        }
        catch (RuntimeException e) {
            pass = false;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getColumnKeys() method.
     */
    @Test
    public void testGetColumnKeys() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        List keys = empty.getColumnKeys();
        assertEquals(0, keys.size());
    }

    /**
     * Some checks for the getRowKeys() method.
     */
    @Test
    public void testGetRowKeys() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        List keys = empty.getRowKeys();
        assertEquals(0, keys.size());
    }

    /**
     * Some checks for the getColumnCount() method.
     */
    @Test
    public void testGetColumnCount() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(0, empty.getColumnCount());
    }

    /**
     * Some checks for the getRowCount() method.
     */
    @Test
    public void testGetRowCount() {
        // check an empty dataset
        DefaultIntervalCategoryDataset empty
                = new DefaultIntervalCategoryDataset(new double[0][0],
                        new double[0][0]);
        assertEquals(0, empty.getColumnCount());
    }

}
