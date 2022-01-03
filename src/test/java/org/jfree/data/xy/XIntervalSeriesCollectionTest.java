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
 * ----------------------------------
 * XIntervalSeriesCollectionTest.java
 * ----------------------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.DatasetChangeConfirmation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XIntervalSeriesCollection} class.
 */
public class XIntervalSeriesCollectionTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        XIntervalSeriesCollection<String> c1 = new XIntervalSeriesCollection<>();
        XIntervalSeriesCollection<String> c2 = new XIntervalSeriesCollection<>();
        assertEquals(c1, c2);

        // add a series
        XIntervalSeries<String> s1 = new XIntervalSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        assertNotEquals(c1, c2);
        XIntervalSeries<String> s2 = new XIntervalSeries<>("Series");
        s2.add(1.0, 1.1, 1.2, 1.3);
        c2.addSeries(s2);
        assertEquals(c1, c2);

        // add an empty series
        c1.addSeries(new XIntervalSeries<>("Empty Series"));
        assertNotEquals(c1, c2);
        c2.addSeries(new XIntervalSeries<>("Empty Series"));
        assertEquals(c1, c2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XIntervalSeriesCollection<String> c1 = new XIntervalSeriesCollection<>();
        XIntervalSeries<String> s1 = new XIntervalSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        XIntervalSeriesCollection<String> c2 = CloneUtils.clone(c1);
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // check independence
        c1.removeSeries(0);
        assertNotEquals(c1, c2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        XIntervalSeriesCollection<String> c1 = new XIntervalSeriesCollection<>();
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XIntervalSeriesCollection<String> c1 = new XIntervalSeriesCollection<>();
        XIntervalSeries<String> s1 = new XIntervalSeries<>("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        c1.addSeries(s1);
        XIntervalSeriesCollection<String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);

        // check independence
        s1.add(2.0, 1.99, 2.01, 2.3);
        assertNotEquals(c1, c2);
        c2.getSeries(0).add(2.0, 1.99, 2.01, 2.3);
        assertEquals(c1, c2);

        // check that c2 gets notifications when s2 is changed
        DatasetChangeConfirmation listener = new DatasetChangeConfirmation();
        c2.addChangeListener(listener);
        c2.getSeries(0).add(3.0, 2.99, 3.01, 3.4);
        assertNotNull(listener.event);
    }

    /**
     * Some basic checks for the removeSeries() method.
     */
    @Test
    public void testRemoveSeries() {
        XIntervalSeriesCollection<String> c = new XIntervalSeriesCollection<>();
        XIntervalSeries<String> s1 = new XIntervalSeries<>("s1");
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
    @Test
    public void test1170825() {
        XIntervalSeries<String> s1 = new XIntervalSeries<>("Series1");
        XIntervalSeriesCollection<String> dataset = new XIntervalSeriesCollection<>();
        dataset.addSeries(s1);
        try {
            /* XYSeries s = */ dataset.getSeries(1);
        }
        catch (IllegalArgumentException e) {
            // correct outcome
        }
        catch (IndexOutOfBoundsException e) {
            fail();  // wrong outcome
        }
    }

}
