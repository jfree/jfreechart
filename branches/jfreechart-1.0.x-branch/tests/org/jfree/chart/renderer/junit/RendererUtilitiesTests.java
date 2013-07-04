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
 * ---------------------------
 * RendererUtilitiesTests.java
 * ---------------------------
 * (C) Copyright 2007-2012, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Apr-2007 : Version 1 (DG);
 * 23-Aug-2012 : Added test3561093() (DG);
 *
 */

package org.jfree.chart.renderer.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.renderer.RendererUtilities;
import org.jfree.data.DomainOrder;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Some checks for the {@link RendererUtilities} class.
 */
public class RendererUtilitiesTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RendererUtilitiesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RendererUtilitiesTests(String name) {
        super(name);
    }

    /**
     * Some checks for the findLiveItemsLowerBound() method when the dataset is
     * unordered.
     */
    public void testFindLiveItemsLowerBound_Unordered() {
        DefaultXYDataset d = new DefaultXYDataset();

        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 0, 10.0,
                11.0));

        // check a series with one item
        d.addSeries("S2", new double[][] {{0.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 2.0,
                3.3));

        // check a series with two items
        d.addSeries("S3", new double[][] {{0.0, 1.0}, {9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 0.0,
                1.1));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 2.0,
                3.3));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 3.0,
                4.4));

        // check a series with three items
        d.addSeries("S4", new double[][] {{1.0, 2.0, 1.5}, {9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 3, 2.0,
                3.3));
        assertEquals(2, RendererUtilities.findLiveItemsLowerBound(d, 3, 3.0,
                4.4));

        // check a series with four items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 1.5, 1.8}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 4, 2.0,
                3.3));
        assertEquals(3, RendererUtilities.findLiveItemsLowerBound(d, 4, 3.0,
                4.4));
        assertEquals(3, RendererUtilities.findLiveItemsLowerBound(d, 4, 4.0,
                5.5));
    }

    /**
     * Some checks for the findLiveItemsLowerBound() method when the dataset is
     * ASCENDING.
     */
    public void testFindLiveItemsLowerBound_Ascending() {
        DefaultXYDataset d = new DefaultXYDataset() {
            public DomainOrder getDomainOrder() {
                // we're doing this for testing only, and make sure that we
                // only add data in ascending order by x-value
                return DomainOrder.ASCENDING;
            }
        };
        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 0, 10.0,
                11.1));

        // check a series with one item
        d.addSeries("S2", new double[][] {{1.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 2.0,
                2.2));

        // check a series with two items
        d.addSeries("S3", new double[][] {{1.0, 2.0}, {9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 2.0,
                3.3));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 3.0,
                4.4));

        // check a series with three items
        d.addSeries("S4", new double[][] {{1.0, 2.0, 3.0}, {9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 3, 2.0,
                3.3));
        assertEquals(2, RendererUtilities.findLiveItemsLowerBound(d, 3, 3.0,
                4.4));

        // check a series with four items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 3.0, 4.0}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 4, 2.0,
                3.3));
        assertEquals(2, RendererUtilities.findLiveItemsLowerBound(d, 4, 3.0,
                4.4));
        assertEquals(3, RendererUtilities.findLiveItemsLowerBound(d, 4, 4.0,
                5.5));

        // check a series with repeating items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 2.0, 2.0, 3.0}, {9.9, 9.9,
                9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 0.0,
                4.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 1.0,
                4.0));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 4, 2.0,
                4.0));
        assertEquals(4, RendererUtilities.findLiveItemsLowerBound(d, 4, 3.0,
                4.0));

    }

    /**
     * Some checks for the findLiveItemsLowerBound() method when the dataset is
     * DESCENDING.
     */
    public void testFindLiveItemsLowerBound_Descending() {
        DefaultXYDataset d = new DefaultXYDataset() {
            public DomainOrder getDomainOrder() {
                // we're doing this for testing only, and make sure that we
                // only add data in descending order by x-value
                return DomainOrder.DESCENDING;
            }
        };
        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 0, 10.0,
                11.0));

        // check a series with one item
        d.addSeries("S2", new double[][] {{1.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 0.0,
                1.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 1, 1.1,
                2.0));

        // check a series with two items
        d.addSeries("S3", new double[][] {{2.0, 1.0}, {9.9, 9.9}});
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 0.1,
                0.5));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 2, 0.1,
                1.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 1.1,
                2.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 2.2,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 2, 3.3,
                4.0));

        // check a series with three items
        d.addSeries("S4", new double[][] {{3.0, 2.0, 1.0}, {9.9, 9.9, 9.9}});
        assertEquals(2, RendererUtilities.findLiveItemsLowerBound(d, 3, 0.0,
                1.0));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 3, 1.0,
                2.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 2.0,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 3, 3.0,
                4.0));

        // check a series with four items
        d.addSeries("S5", new double[][] {{4.0, 3.0, 2.0, 1.0}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(3, RendererUtilities.findLiveItemsLowerBound(d, 4, 0.1,
                0.5));
        assertEquals(3, RendererUtilities.findLiveItemsLowerBound(d, 4, 0.1,
                1.0));
        assertEquals(2, RendererUtilities.findLiveItemsLowerBound(d, 4, 1.1,
                2.0));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 4, 2.2,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 3.3,
                4.0));
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 4, 4.4,
                5.0));

        // check a series with repeating items
        d.addSeries("S6", new double[][] {{3.0, 2.0, 2.0, 2.0, 1.0}, {9.9, 9.9,
                9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsLowerBound(d, 5, 0.0,
                3.0));
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(d, 5, 0.0,
                2.0));
        assertEquals(4, RendererUtilities.findLiveItemsLowerBound(d, 5, 0.0,
                1.0));
        assertEquals(4, RendererUtilities.findLiveItemsLowerBound(d, 5, 0.0,
                0.5));
    }

    /**
     * Some checks for the findLiveItemsUpperBound() method when the dataset is
     * unordered.
     */
    public void testFindLiveItemsUpperBound_Unordered() {
        DefaultXYDataset d = new DefaultXYDataset();

        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 0, 10.0,
                11.0));

        // check a series with one item
        d.addSeries("S2", new double[][] {{1.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 2.0,
                3.3));

        // check a series with two items
        d.addSeries("S3", new double[][] {{1.0, 2.0}, {9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 2, 0.0,
                1.1));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 2.0,
                3.3));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 3.0,
                4.4));

        // check a series with three items
        d.addSeries("S4", new double[][] {{1.0, 2.0, 1.5}, {9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 3, 0.0,
                1.1));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 1.0,
                2.2));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 2.0,
                3.3));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 3.0,
                4.4));

        // check a series with four items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 1.5, 1.8}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                1.1));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 1.0,
                2.2));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 2.0,
                3.3));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 3.0,
                4.4));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 4.0,
                5.5));
    }

    /**
     * Some checks for the findLiveItemsUpperBound() method when the dataset is
     * ASCENDING.
     */
    public void testFindLiveItemsUpperBound_Ascending() {
        DefaultXYDataset d = new DefaultXYDataset() {
            public DomainOrder getDomainOrder() {
                // we're doing this for testing only, and make sure that we
                // only add data in ascending order by x-value
                return DomainOrder.ASCENDING;
            }
        };
        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 0, 10.0,
                11.1));

        // check a series with one item
        d.addSeries("S2", new double[][] {{1.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 0.0,
                1.1));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 2.0,
                2.2));

        // check a series with two items
        d.addSeries("S3", new double[][] {{1.0, 2.0}, {9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 2, 0.0,
                1.0));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 1.0,
                2.2));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 2.0,
                3.3));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 3.0,
                4.4));

        // check a series with three items
        d.addSeries("S4", new double[][] {{1.0, 2.0, 3.0}, {9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 3, 0.0,
                1.1));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 3, 1.0,
                2.2));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 2.0,
                3.3));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 3.0,
                4.4));

        // check a series with four items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 3.0, 4.0}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                1.1));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 4, 1.0,
                2.2));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 4, 2.0,
                3.3));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 3.0,
                4.4));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 4.0,
                5.5));

        // check a series with repeating items
        d.addSeries("S5", new double[][] {{1.0, 2.0, 2.0, 2.0, 3.0}, {9.9, 9.9,
                9.9, 9.9, 9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                1.0));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                2.0));
        assertEquals(4, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                3.0));
        assertEquals(4, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.0,
                4.0));

    }

    /**
     * Some checks for the findLiveItemsUpperBound() method when the dataset is
     * DESCENDING.
     */
    public void testFindLiveItemsUpperBound_Descending() {
        DefaultXYDataset d = new DefaultXYDataset() {
            public DomainOrder getDomainOrder() {
                // we're doing this for testing only, and make sure that we
                // only add data in descending order by x-value
                return DomainOrder.DESCENDING;
            }
        };
        // check a series with no items
        d.addSeries("S1", new double[][] {{}, {}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 0, 10.0,
                11.0));

        // check a series with one item
        d.addSeries("S2", new double[][] {{1.0}, {9.9}});
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 0.0,
                1.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 1, 1.1,
                2.0));

        // check a series with two items
        d.addSeries("S3", new double[][] {{2.0, 1.0}, {9.9, 9.9}});
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 0.1,
                0.5));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 2, 0.1,
                1.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 2, 1.1,
                2.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 2, 2.2,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 2, 3.3,
                4.0));

        // check a series with three items
        d.addSeries("S4", new double[][] {{3.0, 2.0, 1.0}, {9.9, 9.9, 9.9}});
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 0.0,
                1.0));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 3, 1.0,
                2.0));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 3, 2.0,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 3, 3.0,
                4.0));

        // check a series with four items
        d.addSeries("S5", new double[][] {{4.0, 3.0, 2.0, 1.0}, {9.9, 9.9,
                9.9, 9.9}});
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.1,
                0.5));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 4, 0.1,
                1.0));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(d, 4, 1.1,
                2.0));
        assertEquals(1, RendererUtilities.findLiveItemsUpperBound(d, 4, 2.2,
                3.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 4, 3.3,
                4.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 4, 4.4,
                5.0));

        // check a series with repeating items
        d.addSeries("S6", new double[][] {{3.0, 2.0, 2.0, 2.0, 1.0}, {9.9, 9.9,
                9.9, 9.9, 9.9}});
        assertEquals(4, RendererUtilities.findLiveItemsUpperBound(d, 5, 0.0,
                5.0));
        assertEquals(4, RendererUtilities.findLiveItemsUpperBound(d, 5, 1.0,
                5.0));
        assertEquals(3, RendererUtilities.findLiveItemsUpperBound(d, 5, 2.0,
                5.0));
        assertEquals(0, RendererUtilities.findLiveItemsUpperBound(d, 5, 3.0,
                5.0));
    }

    /**
     * Checks the bounds calculation for a series where the x-ordering is not
     * known.  See bug 3561093.
     */
    public void test3561093() {
        XYSeries s = new XYSeries("S1", false);
        s.add(0.0, 0.0);
        s.add(21.0, 0.0);
        s.add(2.0, 0.0);
        s.add(23.0, 0.0);
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s);
        
        assertEquals(1, RendererUtilities.findLiveItemsLowerBound(dataset, 0, 
                10.0, 20.0));
        assertEquals(2, RendererUtilities.findLiveItemsUpperBound(dataset, 0, 
                10.0, 20.0));
        
        int[] bounds = RendererUtilities.findLiveItems(dataset, 0, 10.0, 20.0);
        assertEquals(1, bounds[0]);
        assertEquals(2, bounds[1]);
    }

}
