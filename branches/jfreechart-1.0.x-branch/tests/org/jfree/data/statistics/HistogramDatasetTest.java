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
 * -------------------------
 * HistogramDatasetTest.java
 * -------------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 01-Mar-2004 : Version 1 (DG);
 * 08-Jun-2005 : Added test for getSeriesKey(int) bug (DG);
 * 03-Aug-2006 : Added testAddSeries() and testBinBoundaries() method (DG);
 * 22-May-2008 : Added testAddSeries2() and enhanced testCloning() (DG);
 * 08-Dec-2009 : Added test2902842() for patch at SourceForge (DG);
 *
 */

package org.jfree.data.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.junit.Test;

/**
 * Tests for the {@link HistogramDataset} class.
 */
public class HistogramDatasetTest implements DatasetChangeListener {

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks that the correct values are assigned to bins.
     */
    @Test
    public void testBins() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 6.0, 12.0, 5.0, 6.3, 4.5};
        HistogramDataset hd = new HistogramDataset();
        hd.addSeries("Series 1", values, 5);
        assertEquals(hd.getYValue(0, 0), 3.0, EPSILON);
        assertEquals(hd.getYValue(0, 1), 3.0, EPSILON);
        assertEquals(hd.getYValue(0, 2), 2.0, EPSILON);
        assertEquals(hd.getYValue(0, 3), 0.0, EPSILON);
        assertEquals(hd.getYValue(0, 4), 1.0, EPSILON);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 6.0, 12.0, 5.0, 6.3, 4.5};
        HistogramDataset d1 = new HistogramDataset();
        d1.addSeries("Series 1", values, 5);
        HistogramDataset d2 = new HistogramDataset();
        d2.addSeries("Series 1", values, 5);

        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        d1.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertFalse(d1.equals(d2));
        d2.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertTrue(d1.equals(d2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        double[] values = {1.0, 2.0, 3.0, 4.0, 6.0, 12.0, 5.0, 6.3, 4.5};
        HistogramDataset d1 = new HistogramDataset();
        d1.addSeries("Series 1", values, 5);
        HistogramDataset d2 = (HistogramDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // simple check for independence
        d1.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertFalse(d1.equals(d2));
        d2.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 6.0, 12.0, 5.0, 6.3, 4.5};
        HistogramDataset d1 = new HistogramDataset();
        d1.addSeries("Series 1", values, 5);
        HistogramDataset d2 = (HistogramDataset) TestUtilities.serialised(d1);
        assertEquals(d1, d2);

        // simple check for independence
        d1.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertFalse(d1.equals(d2));
        d2.addSeries("Series 2", new double[] {1.0, 2.0, 3.0}, 2);
        assertTrue(d1.equals(d2));
    }

    /**
     * A test for a bug reported in the forum where the series name isn't being
     * returned correctly.
     */
    @Test
    public void testGetSeriesKey() {
        double[] values = {1.0, 2.0, 3.0, 4.0, 6.0, 12.0, 5.0, 6.3, 4.5};
        HistogramDataset d1 = new HistogramDataset();
        d1.addSeries("Series 1", values, 5);
        assertEquals("Series 1", d1.getSeriesKey(0));
    }

    /**
     * Some checks for the addSeries() method.
     */
    @Test
    public void testAddSeries() {
        double[] values = {-1.0, 0.0, 0.1, 0.9, 1.0, 1.1, 1.9, 2.0, 3.0};
        HistogramDataset d = new HistogramDataset();
        d.addSeries("S1", values, 2, 0.0, 2.0);
        assertEquals(0.0, d.getStartXValue(0, 0), EPSILON);
        assertEquals(1.0, d.getEndXValue(0, 0), EPSILON);
        assertEquals(4.0, d.getYValue(0, 0), EPSILON);

        assertEquals(1.0, d.getStartXValue(0, 1), EPSILON);
        assertEquals(2.0, d.getEndXValue(0, 1), EPSILON);
        assertEquals(5.0, d.getYValue(0, 1), EPSILON);
    }

    /**
     * Another check for the addSeries() method.
     */
    @Test
    public void testAddSeries2() {
        double[] values = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        HistogramDataset hd = new HistogramDataset();
        hd.addSeries("S1", values, 5);
        assertEquals(0.0, hd.getStartXValue(0, 0), EPSILON);
        assertEquals(1.0, hd.getEndXValue(0, 0), EPSILON);
        assertEquals(1.0, hd.getYValue(0, 0), EPSILON);
        assertEquals(1.0, hd.getStartXValue(0, 1), EPSILON);
        assertEquals(2.0, hd.getEndXValue(0, 1), EPSILON);
        assertEquals(1.0, hd.getYValue(0, 1), EPSILON);
        assertEquals(2.0, hd.getStartXValue(0, 2), EPSILON);
        assertEquals(3.0, hd.getEndXValue(0, 2), EPSILON);
        assertEquals(1.0, hd.getYValue(0, 2), EPSILON);
        assertEquals(3.0, hd.getStartXValue(0, 3), EPSILON);
        assertEquals(4.0, hd.getEndXValue(0, 3), EPSILON);
        assertEquals(1.0, hd.getYValue(0, 3), EPSILON);
        assertEquals(4.0, hd.getStartXValue(0, 4), EPSILON);
        assertEquals(5.0, hd.getEndXValue(0, 4), EPSILON);
        assertEquals(2.0, hd.getYValue(0, 4), EPSILON);
    }

    /**
     * This test is derived from a reported bug.
     */
    @Test
    public void testBinBoundaries() {
        double[] values = {-5.000000000000286E-5};
        int bins = 1260;
        double minimum = -0.06307522528160199;
        double maximum = 0.06297522528160199;
        HistogramDataset d = new HistogramDataset();
        d.addSeries("S1", values, bins, minimum, maximum);
        assertEquals(0.0, d.getYValue(0, 629), EPSILON);
        assertEquals(1.0, d.getYValue(0, 630), EPSILON);
        assertEquals(0.0, d.getYValue(0, 631), EPSILON);
        assertTrue(values[0] > d.getStartXValue(0, 630));
        assertTrue(values[0] < d.getEndXValue(0, 630));
    }

    /**
     * Some checks for bug 1553088.  An IndexOutOfBoundsException is thrown
     * when a data value is *very* close to the upper limit of the last bin.
     */
    @Test
    public void test1553088() {
        double[] values = {-1.0, 0.0, -Double.MIN_VALUE, 3.0};
        HistogramDataset d = new HistogramDataset();
        d.addSeries("S1", values, 2, -1.0, 0.0);
        assertEquals(-1.0, d.getStartXValue(0, 0), EPSILON);
        assertEquals(-0.5, d.getEndXValue(0, 0), EPSILON);
        assertEquals(1.0, d.getYValue(0, 0), EPSILON);

        assertEquals(-0.5, d.getStartXValue(0, 1), EPSILON);
        assertEquals(0.0, d.getEndXValue(0, 1), EPSILON);
        assertEquals(3.0, d.getYValue(0, 1), EPSILON);
    }

    /**
     * A test to show the limitation addressed by patch 2902842.
     */
    @Test
    public void test2902842() {
        this.lastEvent = null;
        double[] values = {0.0, 1.0, 2.0, 3.0, 4.0, 5.0};
        HistogramDataset hd = new HistogramDataset();
        hd.addChangeListener(this);
        hd.addSeries("S1", values, 5);
        assertNotNull(this.lastEvent);
    }

    /**
     * A reference to the last event received by the datasetChanged() method.
     */
    private DatasetChangeEvent lastEvent;

    /**
     * Receives event notification.
     *
     * @param event  the event.
     */
    @Override
    public void datasetChanged(DatasetChangeEvent event) {
        this.lastEvent = event;
    }

}
