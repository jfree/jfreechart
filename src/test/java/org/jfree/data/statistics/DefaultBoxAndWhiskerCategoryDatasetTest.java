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
 * --------------------------------------------
 * DefaultBoxAndWhiskerCategoryDatasetTest.java
 * --------------------------------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.statistics;

import java.util.ArrayList;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.Range;
import org.jfree.data.UnknownKeyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultBoxAndWhiskerCategoryDataset} class.
 */
public class DefaultBoxAndWhiskerCategoryDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> d1
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        d1.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "ROW1", "COLUMN1");
        DefaultBoxAndWhiskerCategoryDataset<String, String> d2
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        d2.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "ROW1", "COLUMN1");
        assertEquals(d1, d2);
        assertEquals(d2, d1);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> d1
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        d1.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "ROW1", "COLUMN1");
        DefaultBoxAndWhiskerCategoryDataset<String, String> d2 
                = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultBoxAndWhiskerCategoryDataset<String, String> d1
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        d1.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "ROW1", "COLUMN1");
        DefaultBoxAndWhiskerCategoryDataset<String, String> d2 
                = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // test independence
        d1.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "ROW2", "COLUMN1");
        assertNotEquals(d1, d2);
    }

    /**
     * A simple test for bug report 1701822.
     */
    @Test
    public void test1701822() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> dataset
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        try {
            dataset.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, null, 
                    8.0, new ArrayList<>()), "ROW1", "COLUMN1");
            dataset.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 
                    null, new ArrayList<>()), "ROW1", "COLUMN2");
        }
        catch (NullPointerException e) {
            fail();
        }

    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the add() method.
     */
    @Test
    public void testAdd() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> dataset
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0, new ArrayList<>());
        dataset.add(item1, "R1", "C1");

        assertEquals(2.0, dataset.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1.0, dataset.getMeanValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(2.0, dataset.getMedianValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(3.0, dataset.getQ1Value("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(4.0, dataset.getQ3Value("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(5.0, dataset.getMinRegularValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(6.0, dataset.getMaxRegularValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(7.0, dataset.getMinOutlier("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(8.0, dataset.getMaxOutlier("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(new Range(7.0, 8.0), dataset.getRangeBounds(false));
    }

    /**
     * Some checks for the add() method.
     */
    @Test
    public void testAddUpdatesCachedRange() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> dataset
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        BoxAndWhiskerItem item1 = new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0, new ArrayList<>());
        dataset.add(item1, "R1", "C1");

        // now overwrite this item with another
        BoxAndWhiskerItem item2 = new BoxAndWhiskerItem(1.5, 2.5, 3.5, 4.5,
                5.5, 6.5, 7.5, 8.5, new ArrayList<>());
        dataset.add(item2, "R1", "C1");

        assertEquals(2.5, dataset.getValue("R1", "C1").doubleValue(), EPSILON);
        assertEquals(1.5, dataset.getMeanValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(2.5, dataset.getMedianValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(3.5, dataset.getQ1Value("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(4.5, dataset.getQ3Value("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(5.5, dataset.getMinRegularValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(6.5, dataset.getMaxRegularValue("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(7.5, dataset.getMinOutlier("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(8.5, dataset.getMaxOutlier("R1", "C1").doubleValue(),
                EPSILON);
        assertEquals(new Range(7.5, 8.5), dataset.getRangeBounds(false));
    }

    /**
     * Some basic checks for the constructor.
     */
    @Test
    public void testConstructor() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> dataset
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        assertEquals(0, dataset.getColumnCount());
        assertEquals(0, dataset.getRowCount());
        assertTrue(Double.isNaN(dataset.getRangeLowerBound(false)));
        assertTrue(Double.isNaN(dataset.getRangeUpperBound(false)));
    }

    /**
     * Some checks for the getRangeBounds() method.
     */
    @Test
    public void testGetRangeBounds() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> d1
                = new DefaultBoxAndWhiskerCategoryDataset<>();
        d1.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "R1", "C1");
        assertEquals(new Range(7.0, 8.0), d1.getRangeBounds(false));
        assertEquals(new Range(7.0, 8.0), d1.getRangeBounds(true));

        d1.add(new BoxAndWhiskerItem(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5,
                new ArrayList<>()), "R1", "C1");
        assertEquals(new Range(7.5, 8.5), d1.getRangeBounds(false));
        assertEquals(new Range(7.5, 8.5), d1.getRangeBounds(true));

        d1.add(new BoxAndWhiskerItem(2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5,
                new ArrayList<>()), "R2", "C1");
        assertEquals(new Range(7.5, 9.5), d1.getRangeBounds(false));
        assertEquals(new Range(7.5, 9.5), d1.getRangeBounds(true));

        // this replaces the entry with the current minimum value, but the new
        // minimum value is now in a different item
        d1.add(new BoxAndWhiskerItem(1.5, 2.5, 3.5, 4.5, 5.5, 6.5, 8.6, 9.6,
                new ArrayList<>()), "R1", "C1");
        assertEquals(new Range(8.5, 9.6), d1.getRangeBounds(false));
        assertEquals(new Range(8.5, 9.6), d1.getRangeBounds(true));
    }

    /**
     * Some checks for the remove method.
     */
    @Test
    public void testRemove() {
        DefaultBoxAndWhiskerCategoryDataset<String, String> data
                = new DefaultBoxAndWhiskerCategoryDataset<>();

        boolean pass = false;
        try {
            data.remove("R1", "R2");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
        data.add(new BoxAndWhiskerItem(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0,
                new ArrayList<>()), "R1", "C1");
        assertEquals(new Range(7.0, 8.0), data.getRangeBounds(false));
        assertEquals(new Range(7.0, 8.0), data.getRangeBounds(true));

        data.add(new BoxAndWhiskerItem(2.5, 3.5, 4.5, 5.5, 6.5, 7.5, 8.5, 9.5,
                new ArrayList<>()), "R2", "C1");
        assertEquals(new Range(7.0, 9.5), data.getRangeBounds(false));
        assertEquals(new Range(7.0, 9.5), data.getRangeBounds(true));

        data.remove("R1", "C1");
        assertEquals(new Range(8.5, 9.5), data.getRangeBounds(false));
        assertEquals(new Range(8.5, 9.5), data.getRangeBounds(true));
    }

}
