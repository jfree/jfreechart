/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * DefaultTableXYDatasetTest.java
 * ------------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Dec-2003 : Version 1 (DG);
 * 06-Oct-2005 : Added test for new data updating interval width (DG);
 * 08-Mar-2007 : Added testGetSeries() (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.data.xy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DefaultTableXYDataset} class.
 */
public class DefaultTableXYDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        XYSeries s1 = new XYSeries("Series 1", true, false);
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        d1.addSeries(s1);

        DefaultTableXYDataset d2 = new DefaultTableXYDataset();
        XYSeries s2 = new XYSeries("Series 1", true, false);
        s2.add(1.0, 1.1);
        s2.add(2.0, 2.2);
        d2.addSeries(s2);

        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        s1.add(3.0, 3.3);
        assertFalse(d1.equals(d2));

        s2.add(3.0, 3.3);
        assertTrue(d1.equals(d2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        XYSeries s1 = new XYSeries("Series 1", true, false);
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        d1.addSeries(s1);

        DefaultTableXYDataset d2 = (DefaultTableXYDataset) d1.clone();

        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        s1.add(3.0, 3.3);
        assertFalse(d1.equals(d2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        XYSeries s1 = new XYSeries("Series 1", true, false);
        s1.add(1.0, 1.1);
        s1.add(2.0, 2.2);
        d1.addSeries(s1);

        DefaultTableXYDataset d2 = (DefaultTableXYDataset) 
                TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * This is a test for bug 1312066 - adding a new series should trigger a
     * recalculation of the interval width, if it is being automatically
     * calculated.
     */
    @Test
    public void testAddSeries() {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        d1.setAutoWidth(true);
        XYSeries s1 = new XYSeries("Series 1", true, false);
        s1.add(3.0, 1.1);
        s1.add(7.0, 2.2);
        d1.addSeries(s1);
        assertEquals(3.0, d1.getXValue(0, 0), EPSILON);
        assertEquals(7.0, d1.getXValue(0, 1), EPSILON);
        assertEquals(1.0, d1.getStartXValue(0, 0), EPSILON);
        assertEquals(5.0, d1.getStartXValue(0, 1), EPSILON);
        assertEquals(5.0, d1.getEndXValue(0, 0), EPSILON);
        assertEquals(9.0, d1.getEndXValue(0, 1), EPSILON);

        // now add another series
        XYSeries s2 = new XYSeries("Series 2", true, false);
        s2.add(7.5, 1.1);
        s2.add(9.0, 2.2);
        d1.addSeries(s2);

        assertEquals(3.0, d1.getXValue(1, 0), EPSILON);
        assertEquals(7.0, d1.getXValue(1, 1), EPSILON);
        assertEquals(7.5, d1.getXValue(1, 2), EPSILON);
        assertEquals(9.0, d1.getXValue(1, 3), EPSILON);

        assertEquals(7.25, d1.getStartXValue(1, 2), EPSILON);
        assertEquals(8.75, d1.getStartXValue(1, 3), EPSILON);
        assertEquals(7.75, d1.getEndXValue(1, 2), EPSILON);
        assertEquals(9.25, d1.getEndXValue(1, 3), EPSILON);

        // and check the first series too...
        assertEquals(2.75, d1.getStartXValue(0, 0), EPSILON);
        assertEquals(6.75, d1.getStartXValue(0, 1), EPSILON);
        assertEquals(3.25, d1.getEndXValue(0, 0), EPSILON);
        assertEquals(7.25, d1.getEndXValue(0, 1), EPSILON);
    }

    /**
     * Some basic checks for the getSeries() method.
     */
    @Test
    public void testGetSeries() {
        DefaultTableXYDataset d1 = new DefaultTableXYDataset();
        XYSeries s1 = new XYSeries("Series 1", true, false);
        d1.addSeries(s1);
        assertEquals("Series 1", d1.getSeries(0).getKey());

        boolean pass = false;
        try {
            d1.getSeries(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            d1.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

}
