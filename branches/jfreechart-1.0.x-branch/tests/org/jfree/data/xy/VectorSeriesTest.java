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
 * ---------------------
 * VectorSeriesTest.java
 * ---------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jan-2007 : Version 1, based on XYSeriesTests (DG);
 * 24-May-2007 : Updated for modified method names (DG);
 * 27-Nov-2007 : Added testClear() method (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.junit.Test;

/**
 * Tests for the {@link VectorSeries} class.
 */
public class VectorSeriesTest implements SeriesChangeListener {

    SeriesChangeEvent lastEvent;

    /**
     * Records the last event.
     *
     * @param event  the event.
     */
    public void seriesChanged(SeriesChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        VectorSeries s1 = new VectorSeries("s1");
        VectorSeries s2 = new VectorSeries("s1");
        assertTrue(s1.equals(s2));

        // seriesKey
        s1 = new VectorSeries("s2");
        assertFalse(s1.equals(s2));
        s2 = new VectorSeries("s2");
        assertTrue(s1.equals(s2));

        // autoSort
        s1 = new VectorSeries("s2", true, true);
        assertFalse(s1.equals(s2));
        s2 = new VectorSeries("s2", true, true);
        assertTrue(s1.equals(s2));

        // allowDuplicateValues
        s1 = new VectorSeries("s2", false, false);
        assertFalse(s1.equals(s2));
        s2 = new VectorSeries("s2", false, false);
        assertTrue(s1.equals(s2));

        // add a value
        s1.add(1.0, 0.5, 1.5, 2.0);
        assertFalse(s1.equals(s2));
        s2.add(1.0, 0.5, 1.5, 2.0);
        assertTrue(s2.equals(s1));

        // add another value
        s1.add(2.0, 0.5, 1.5, 2.0);
        assertFalse(s1.equals(s2));
        s2.add(2.0, 0.5, 1.5, 2.0);
        assertTrue(s2.equals(s1));

        // remove a value
        s1.remove(new XYCoordinate(1.0, 0.5));
        assertFalse(s1.equals(s2));
        s2.remove(new XYCoordinate(1.0, 0.5));
        assertTrue(s2.equals(s1));

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        VectorSeries s1 = new VectorSeries("s1");
        s1.add(1.0, 0.5, 1.5, 2.0);
        VectorSeries s2 = (VectorSeries) s1.clone();
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        VectorSeries s1 = new VectorSeries("s1");
        s1.add(1.0, 0.5, 1.5, 2.0);
        VectorSeries s2 = (VectorSeries) TestUtilities.serialised(s1);
        assertEquals(s1, s2);
    }

    /**
     * Simple test for the indexOf() method.
     */
    @Test
    public void testIndexOf() {
        VectorSeries s1 = new VectorSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 2.0);
        s1.add(2.0, 2.0, 2.0, 3.0);
        s1.add(3.0, 3.0, 3.0, 4.0);
        assertEquals(0, s1.indexOf(new XYCoordinate(1.0, 1.0)));
    }

    /**
     * A check for the indexOf() method for an unsorted series.
     */
    @Test
    public void testIndexOf2() {
        VectorSeries s1 = new VectorSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 2.0);
        s1.add(3.0, 3.0, 3.0, 3.0);
        s1.add(2.0, 2.0, 2.0, 2.0);
        assertEquals(0, s1.indexOf(new XYCoordinate(1.0, 1.0)));
        assertEquals(1, s1.indexOf(new XYCoordinate(3.0, 3.0)));
        assertEquals(2, s1.indexOf(new XYCoordinate(2.0, 2.0)));
    }

    /**
     * Simple test for the remove() method.
     */
    @Test
    public void testRemove() {
        VectorSeries s1 = new VectorSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 2.0);
        s1.add(3.0, 3.0, 3.0, 3.0);
        s1.add(2.0, 2.0, 2.0, 2.0);
        assertEquals(3, s1.getItemCount());

        s1.remove(new XYCoordinate(2.0, 2.0));
        assertEquals(3.0, s1.getXValue(1), EPSILON);

        s1.remove(new XYCoordinate(1.0, 1.0));
        assertEquals(3.0, s1.getXValue(0), EPSILON);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * When items are added with duplicate x-values, we expect them to remain
     * in the order they were added.
     */
    @Test
    public void testAdditionOfDuplicateXValues() {
        VectorSeries s1 = new VectorSeries("Series 1");
        s1.add(1.0, 1.0, 1.0, 1.0);
        s1.add(2.0, 2.0, 2.0, 2.0);
        s1.add(2.0, 2.0, 3.0, 3.0);
        s1.add(2.0, 3.0, 4.0, 4.0);
        s1.add(3.0, 5.0, 5.0, 5.0);
        assertEquals(1.0, s1.getVectorXValue(0), EPSILON);
        assertEquals(2.0, s1.getVectorXValue(1), EPSILON);
        assertEquals(3.0, s1.getVectorXValue(2), EPSILON);
        assertEquals(4.0, s1.getVectorXValue(3), EPSILON);
        assertEquals(5.0, s1.getVectorXValue(4), EPSILON);
    }

    /**
     * Some checks for the add() method for an UNSORTED series.
     */
    @Test
    public void testAdd() {
        VectorSeries series = new VectorSeries("Series", false, true);
        series.add(5.0, 5.50, 5.50, 5.50);
        series.add(5.1, 5.51, 5.51, 5.51);
        series.add(6.0, 6.6, 6.6, 6.6);
        series.add(3.0, 3.3, 3.3, 3.3);
        series.add(4.0, 4.4, 4.4, 4.4);
        series.add(2.0, 2.2, 2.2, 2.2);
        series.add(1.0, 1.1, 1.1, 1.1);
        assertEquals(5.5, series.getVectorXValue(0), EPSILON);
        assertEquals(5.51, series.getVectorXValue(1), EPSILON);
        assertEquals(6.6, series.getVectorXValue(2), EPSILON);
        assertEquals(3.3, series.getVectorXValue(3), EPSILON);
        assertEquals(4.4, series.getVectorXValue(4), EPSILON);
        assertEquals(2.2, series.getVectorXValue(5), EPSILON);
        assertEquals(1.1, series.getVectorXValue(6), EPSILON);
    }

    /**
     * A simple check that the maximumItemCount attribute is working.
     */
    @Test
    public void testSetMaximumItemCount() {
        VectorSeries s1 = new VectorSeries("S1");
        assertEquals(Integer.MAX_VALUE, s1.getMaximumItemCount());
        s1.setMaximumItemCount(2);
        assertEquals(2, s1.getMaximumItemCount());
        s1.add(1.0, 1.1, 1.1, 1.1);
        s1.add(2.0, 2.2, 2.2, 2.2);
        s1.add(3.0, 3.3, 3.3, 3.3);
        assertEquals(2.0, s1.getXValue(0), EPSILON);
        assertEquals(3.0, s1.getXValue(1), EPSILON);
    }

    /**
     * Check that the maximum item count can be applied retrospectively.
     */
    @Test
    public void testSetMaximumItemCount2() {
        VectorSeries s1 = new VectorSeries("S1");
        s1.add(1.0, 1.1, 1.1, 1.1);
        s1.add(2.0, 2.2, 2.2, 2.2);
        s1.add(3.0, 3.3, 3.3, 3.3);
        s1.setMaximumItemCount(2);
        assertEquals(2.0, s1.getXValue(0), EPSILON);
        assertEquals(3.0, s1.getXValue(1), EPSILON);
    }

    /**
     * Some checks for the clear() method.
     */
    @Test
    public void testClear() {
        VectorSeries s1 = new VectorSeries("S1");
        s1.addChangeListener(this);
        s1.clear();
        assertNull(this.lastEvent);
        assertTrue(s1.isEmpty());
        s1.add(1.0, 2.0, 3.0, 4.0);
        assertFalse(s1.isEmpty());
        s1.clear();
        assertNotNull(this.lastEvent);
        assertTrue(s1.isEmpty());
    }

}
