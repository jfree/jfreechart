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
 * DatasetUtilitiesTest.java
 * -------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-Sep-2003 : Version 1 (DG);
 * 23-Mar-2004 : Added test for maximumStackedRangeValue() method (DG);
 * 04-Oct-2004 : Eliminated NumberUtils usage (DG);
 * 07-Jan-2005 : Updated for method name changes (DG);
 * 03-Feb-2005 : Added testFindStackedRangeBounds2() method (DG);
 * 26-Sep-2007 : Added testIsEmptyOrNullXYDataset() method (DG);
 * 28-Mar-2008 : Added and renamed various tests (DG);
 * 08-Oct-2008 : New tests to support patch 2131001 and related 
 *               changes (DG);
 * 25-Mar-2009 : Added tests for new iterateToFindRangeBounds() method (DG);
 * 16-May-2009 : Added
 *               testIterateToFindRangeBounds_MultiValueCategoryDataset() (DG);
 * 10-Sep-2009 : Added tests for bug 2849731 (DG);
 *
 */

package org.jfree.data.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jfree.data.KeyToGroupMap;
import org.jfree.data.Range;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.function.Function2D;
import org.jfree.data.function.LineFunction2D;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.data.statistics.MultiValueCategoryDataset;
import org.jfree.data.xy.DefaultIntervalXYDataset;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYIntervalSeries;
import org.jfree.data.xy.XYIntervalSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.YIntervalSeries;
import org.jfree.data.xy.YIntervalSeriesCollection;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for the {@link DatasetUtilities} class.
 */
public class DatasetUtilitiesTest {

    private static final double EPSILON = 0.0000000001;

    /**
     * Some tests to verify that Java does what I think it does!
     */
    @Test
    public void testJava() {
        assertTrue(Double.isNaN(Math.min(1.0, Double.NaN)));
        assertTrue(Double.isNaN(Math.max(1.0, Double.NaN)));
    }

    /**
     * Some tests for the calculatePieDatasetTotal() method.
     */
    @Test
    public void testCalculatePieDatasetTotal() {
        DefaultPieDataset d = new DefaultPieDataset();
        assertEquals(0.0, DatasetUtilities.calculatePieDatasetTotal(d),
                EPSILON);
        d.setValue("A", 1.0);
        assertEquals(1.0, DatasetUtilities.calculatePieDatasetTotal(d),
                EPSILON);
        d.setValue("B", 3.0);
        assertEquals(4.0, DatasetUtilities.calculatePieDatasetTotal(d),
                EPSILON);
    }

    /**
     * Some tests for the findDomainBounds() method.
     */
    @Test
    public void testFindDomainBounds() {
        XYDataset dataset = createXYDataset1();
        Range r = DatasetUtilities.findDomainBounds(dataset);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(3.0, r.getUpperBound(), EPSILON);
    }

    /**
     * This test checks that the standard method has 'includeInterval'
     * defaulting to true.
     */
    @Test
    public void testFindDomainBounds2() {
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {1.0, 2.0, 3.0};
        double[] x1Start = new double[] {0.9, 1.9, 2.9};
        double[] x1End = new double[] {1.1, 2.1, 3.1};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        dataset.addSeries("S1", data1);
        Range r = DatasetUtilities.findDomainBounds(dataset);
        assertEquals(0.9, r.getLowerBound(), EPSILON);
        assertEquals(3.1, r.getUpperBound(), EPSILON);
    }

    /**
     * This test checks that when the 'includeInterval' flag is false, the
     * bounds come from the regular x-values.
     */
    @Test
    public void testFindDomainBounds3() {
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {1.0, 2.0, 3.0};
        double[] x1Start = new double[] {0.9, 1.9, 2.9};
        double[] x1End = new double[] {1.1, 2.1, 3.1};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        dataset.addSeries("S1", data1);
        Range r = DatasetUtilities.findDomainBounds(dataset, false);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(3.0, r.getUpperBound(), EPSILON);
    }

    /**
     * This test checks that NaN values are ignored.
     */
    @Test
    public void testFindDomainBounds_NaN() {
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {1.0, 2.0, Double.NaN};
        double[] x1Start = new double[] {0.9, 1.9, Double.NaN};
        double[] x1End = new double[] {1.1, 2.1, Double.NaN};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        dataset.addSeries("S1", data1);
        Range r = DatasetUtilities.findDomainBounds(dataset);
        assertEquals(0.9, r.getLowerBound(), EPSILON);
        assertEquals(2.1, r.getUpperBound(), EPSILON);

        r = DatasetUtilities.findDomainBounds(dataset, false);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the iterateDomainBounds() method.
     */
    @Test
    public void testIterateDomainBounds() {
        XYDataset dataset = createXYDataset1();
        Range r = DatasetUtilities.iterateDomainBounds(dataset);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(3.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Check that NaN values in the dataset are ignored.
     */
    @Test
    public void testIterateDomainBounds_NaN() {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[] x = new double[] {1.0, 2.0, Double.NaN, 3.0};
        double[] y = new double[] {9.0, 8.0, 7.0, 6.0};
        dataset.addSeries("S1", new double[][] {x, y});
        Range r = DatasetUtilities.iterateDomainBounds(dataset);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(3.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Check that NaN values in the IntervalXYDataset are ignored.
     */
    @Test
    public void testIterateDomainBounds_NaN2() {
        DefaultIntervalXYDataset dataset = new DefaultIntervalXYDataset();
        double[] x1 = new double[] {Double.NaN, 2.0, 3.0};
        double[] x1Start = new double[] {0.9, Double.NaN, 2.9};
        double[] x1End = new double[] {1.1, Double.NaN, 3.1};
        double[] y1 = new double[] {4.0, 5.0, 6.0};
        double[] y1Start = new double[] {1.09, 2.09, 3.09};
        double[] y1End = new double[] {1.11, 2.11, 3.11};
        double[][] data1 = new double[][] {x1, x1Start, x1End, y1, y1Start,
                y1End};
        dataset.addSeries("S1", data1);
        Range r = DatasetUtilities.iterateDomainBounds(dataset, false);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(3.0, r.getUpperBound(), EPSILON);
        r = DatasetUtilities.iterateDomainBounds(dataset, true);
        assertEquals(0.9, r.getLowerBound(), EPSILON);
        assertEquals(3.1, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the findRangeBounds() for a CategoryDataset method.
     */
    @Test
    public void testFindRangeBounds_CategoryDataset() {
        CategoryDataset dataset = createCategoryDataset1();
        Range r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(6.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the findRangeBounds() method on an XYDataset.
     */
    @Test
    public void testFindRangeBounds() {
        XYDataset dataset = createXYDataset1();
        Range r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(100.0, r.getLowerBound(), EPSILON);
        assertEquals(105.0, r.getUpperBound(), EPSILON);
    }

    /**
     * A test for the findRangeBounds(XYDataset) method using
     * an IntervalXYDataset.
     */
    @Test
    public void testFindRangeBounds2() {
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
        Range r = DatasetUtilities.findRangeBounds(dataset);
        assertNull(r);
        YIntervalSeries s1 = new YIntervalSeries("S1");
        dataset.addSeries(s1);
        r = DatasetUtilities.findRangeBounds(dataset);
        assertNull(r);

        // try a single item
        s1.add(1.0, 2.0, 1.5, 2.5);
        r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(1.5, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        r = DatasetUtilities.findRangeBounds(dataset, false);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);

        // another item
        s1.add(2.0, 2.0, 1.4, 2.1);
        r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        // another empty series
        YIntervalSeries s2 = new YIntervalSeries("S2");
        dataset.addSeries(s2);
        r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        // an item in series 2
        s2.add(1.0, 2.0, 1.9, 2.6);
        r = DatasetUtilities.findRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.6, r.getUpperBound(), EPSILON);

        // what if we don't want the interval?
        r = DatasetUtilities.findRangeBounds(dataset, false);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the iterateRangeBounds() method.
     */
    @Test
    public void testIterateRangeBounds_CategoryDataset() {
        CategoryDataset dataset = createCategoryDataset1();
        Range r = DatasetUtilities.iterateRangeBounds(dataset, false);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(6.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the iterateRangeBounds() method.
     */
    @Test
    public void testIterateRangeBounds2_CategoryDataset() {
        // an empty dataset should return a null range
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Range r = DatasetUtilities.iterateRangeBounds(dataset, false);
        assertNull(r);

        // a dataset with a single value
        dataset.addValue(1.23, "R1", "C1");
        r = DatasetUtilities.iterateRangeBounds(dataset, false);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);

        // null is ignored
        dataset.addValue(null, "R2", "C1");
        r = DatasetUtilities.iterateRangeBounds(dataset, false);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);

        // a Double.NaN should be ignored
        dataset.addValue(Double.NaN, "R2", "C1");
        r = DatasetUtilities.iterateRangeBounds(dataset, false);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the iterateRangeBounds() method using an
     * IntervalCategoryDataset.
     */
    @Test
    public void testIterateRangeBounds3_CategoryDataset() {
        Number[][] starts = new Double[2][3];
        Number[][] ends = new Double[2][3];
        starts[0][0] = new Double(1.0);
        starts[0][1] = new Double(2.0);
        starts[0][2] = new Double(3.0);
        starts[1][0] = new Double(11.0);
        starts[1][1] = new Double(12.0);
        starts[1][2] = new Double(13.0);
        ends[0][0] = new Double(4.0);
        ends[0][1] = new Double(5.0);
        ends[0][2] = new Double(6.0);
        ends[1][0] = new Double(16.0);
        ends[1][1] = new Double(15.0);
        ends[1][2] = new Double(14.0);

        DefaultIntervalCategoryDataset d = new DefaultIntervalCategoryDataset(
                starts, ends);
        Range r = DatasetUtilities.iterateRangeBounds(d, false);
        assertEquals(4.0, r.getLowerBound(), EPSILON);
        assertEquals(16.0, r.getUpperBound(), EPSILON);
        r = DatasetUtilities.iterateRangeBounds(d, true);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(16.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the iterateRangeBounds() method.
     */
    @Test
    public void testIterateRangeBounds() {
        XYDataset dataset = createXYDataset1();
        Range r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(100.0, r.getLowerBound(), EPSILON);
        assertEquals(105.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Check the range returned when a series contains a null value.
     */
    @Test
    public void testIterateRangeBounds2() {
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        s1.add(2.0, null);
        s1.add(3.0, 3.3);
        XYSeriesCollection dataset = new XYSeriesCollection(s1);
        Range r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.1, r.getLowerBound(), EPSILON);
        assertEquals(3.3, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the iterateRangeBounds() method.
     */
    @Test
    public void testIterateRangeBounds3() {
        // an empty dataset should return a null range
        XYSeriesCollection dataset = new XYSeriesCollection();
        Range r = DatasetUtilities.iterateRangeBounds(dataset);
        assertNull(r);
        XYSeries s1 = new XYSeries("S1");
        dataset.addSeries(s1);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertNull(r);

        // a dataset with a single value
        s1.add(1.0, 1.23);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);

        // null is ignored
        s1.add(2.0, null);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);

        // Double.NaN DOESN'T mess things up
        s1.add(3.0, Double.NaN);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.23, r.getLowerBound(), EPSILON);
        assertEquals(1.23, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the range bounds of a dataset that implements the
     * {@link IntervalXYDataset} interface.
     */
    @Test
    public void testIterateRangeBounds4() {
        YIntervalSeriesCollection dataset = new YIntervalSeriesCollection();
        Range r = DatasetUtilities.iterateRangeBounds(dataset);
        assertNull(r);
        YIntervalSeries s1 = new YIntervalSeries("S1");
        dataset.addSeries(s1);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertNull(r);

        // try a single item
        s1.add(1.0, 2.0, 1.5, 2.5);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.5, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        // another item
        s1.add(2.0, 2.0, 1.4, 2.1);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        // another empty series
        YIntervalSeries s2 = new YIntervalSeries("S2");
        dataset.addSeries(s2);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        // an item in series 2
        s2.add(1.0, 2.0, 1.9, 2.6);
        r = DatasetUtilities.iterateRangeBounds(dataset);
        assertEquals(1.4, r.getLowerBound(), EPSILON);
        assertEquals(2.6, r.getUpperBound(), EPSILON);
    }

    /**
     * Some tests for the findMinimumDomainValue() method.
     */
    @Test
    public void testFindMinimumDomainValue() {
        XYDataset dataset = createXYDataset1();
        Number minimum = DatasetUtilities.findMinimumDomainValue(dataset);
        assertEquals(new Double(1.0), minimum);
    }

    /**
     * Some tests for the findMaximumDomainValue() method.
     */
    @Test
    public void testFindMaximumDomainValue() {
        XYDataset dataset = createXYDataset1();
        Number maximum = DatasetUtilities.findMaximumDomainValue(dataset);
        assertEquals(new Double(3.0), maximum);
    }

    /**
     * Some tests for the findMinimumRangeValue() method.
     */
    @Test
    public void testFindMinimumRangeValue() {
        CategoryDataset d1 = createCategoryDataset1();
        Number min1 = DatasetUtilities.findMinimumRangeValue(d1);
        assertEquals(new Double(1.0), min1);

        XYDataset d2 = createXYDataset1();
        Number min2 = DatasetUtilities.findMinimumRangeValue(d2);
        assertEquals(new Double(100.0), min2);
    }

    /**
     * Some tests for the findMaximumRangeValue() method.
     */
    @Test
    public void testFindMaximumRangeValue() {
        CategoryDataset d1 = createCategoryDataset1();
        Number max1 = DatasetUtilities.findMaximumRangeValue(d1);
        assertEquals(new Double(6.0), max1);

        XYDataset dataset = createXYDataset1();
        Number maximum = DatasetUtilities.findMaximumRangeValue(dataset);
        assertEquals(new Double(105.0), maximum);
    }

    /**
     * A quick test of the min and max range value methods.
     */
    @Test
    public void testMinMaxRange() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100.0, "Series 1", "Type 1");
        dataset.addValue(101.1, "Series 1", "Type 2");
        Number min = DatasetUtilities.findMinimumRangeValue(dataset);
        assertTrue(min.doubleValue() < 100.1);
        Number max = DatasetUtilities.findMaximumRangeValue(dataset);
        assertTrue(max.doubleValue() > 101.0);
    }

    /**
     * A test to reproduce bug report 803660.
     */
    @Test
    public void test803660() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(100.0, "Series 1", "Type 1");
        dataset.addValue(101.1, "Series 1", "Type 2");
        Number n = DatasetUtilities.findMaximumRangeValue(dataset);
        assertTrue(n.doubleValue() > 101.0);
    }

    /**
     * A simple test for the cumulative range calculation.  The sequence of
     * "cumulative" values are considered to be { 0.0, 10.0, 25.0, 18.0 } so
     * the range should be 0.0 -> 25.0.
     */
    @Test
    public void testCumulativeRange1() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10.0, "Series 1", "Start");
        dataset.addValue(15.0, "Series 1", "Delta 1");
        dataset.addValue(-7.0, "Series 1", "Delta 2");
        Range range = DatasetUtilities.findCumulativeRangeBounds(dataset);
        assertEquals(0.0, range.getLowerBound(), 0.00000001);
        assertEquals(25.0, range.getUpperBound(), 0.00000001);
    }

    /**
     * A further test for the cumulative range calculation.
     */
    @Test
    public void testCumulativeRange2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(-21.4, "Series 1", "Start Value");
        dataset.addValue(11.57, "Series 1", "Delta 1");
        dataset.addValue(3.51, "Series 1", "Delta 2");
        dataset.addValue(-12.36, "Series 1", "Delta 3");
        dataset.addValue(3.39, "Series 1", "Delta 4");
        dataset.addValue(38.68, "Series 1", "Delta 5");
        dataset.addValue(-43.31, "Series 1", "Delta 6");
        dataset.addValue(-29.59, "Series 1", "Delta 7");
        dataset.addValue(35.30, "Series 1", "Delta 8");
        dataset.addValue(5.0, "Series 1", "Delta 9");
        Range range = DatasetUtilities.findCumulativeRangeBounds(dataset);
        assertEquals(-49.51, range.getLowerBound(), 0.00000001);
        assertEquals(23.39, range.getUpperBound(), 0.00000001);
    }

    /**
     * A further test for the cumulative range calculation.
     */
    @Test
    public void testCumulativeRange3() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15.76, "Product 1", "Labour");
        dataset.addValue(8.66, "Product 1", "Administration");
        dataset.addValue(4.71, "Product 1", "Marketing");
        dataset.addValue(3.51, "Product 1", "Distribution");
        dataset.addValue(32.64, "Product 1", "Total Expense");
        Range range = DatasetUtilities.findCumulativeRangeBounds(dataset);
        assertEquals(0.0, range.getLowerBound(), EPSILON);
        assertEquals(65.28, range.getUpperBound(), EPSILON);
    }

    /**
     * Check that the findCumulativeRangeBounds() method ignores Double.NaN
     * values.
     */
    @Test
    public void testCumulativeRange_NaN() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(10.0, "Series 1", "Start");
        dataset.addValue(15.0, "Series 1", "Delta 1");
        dataset.addValue(Double.NaN, "Series 1", "Delta 2");
        Range range = DatasetUtilities.findCumulativeRangeBounds(dataset);
        assertEquals(0.0, range.getLowerBound(), EPSILON);
        assertEquals(25.0, range.getUpperBound(), EPSILON);
    }

    /**
     * Test the creation of a dataset from an array.
     */
    @Test
    public void testCreateCategoryDataset1() {
        String[] rowKeys = {"R1", "R2", "R3"};
        String[] columnKeys = {"C1", "C2"};
        double[][] data = new double[3][];
        data[0] = new double[] {1.1, 1.2};
        data[1] = new double[] {2.1, 2.2};
        data[2] = new double[] {3.1, 3.2};
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
                rowKeys, columnKeys, data);
        assertTrue(dataset.getRowCount() == 3);
        assertTrue(dataset.getColumnCount() == 2);
    }

    /**
     * Test the creation of a dataset from an array.  This time is should fail
     * because the array dimensions are around the wrong way.
     */
    @Test
    public void testCreateCategoryDataset2() {
        boolean pass = false;
        String[] rowKeys = {"R1", "R2", "R3"};
        String[] columnKeys = {"C1", "C2"};
        double[][] data = new double[2][];
        data[0] = new double[] {1.1, 1.2, 1.3};
        data[1] = new double[] {2.1, 2.2, 2.3};
        CategoryDataset dataset = null;
        try {
            dataset = DatasetUtilities.createCategoryDataset(rowKeys,
                    columnKeys, data);
        }
        catch (IllegalArgumentException e) {
            pass = true;  // got it!
        }
        assertTrue(pass);
        assertTrue(dataset == null);
    }

    /**
     * Test for a bug reported in the forum:
     *
     * http://www.jfree.org/phpBB2/viewtopic.php?t=7903
     */
    @Test
    public void testMaximumStackedRangeValue() {
        double v1 = 24.3;
        double v2 = 14.2;
        double v3 = 33.2;
        double v4 = 32.4;
        double v5 = 26.3;
        double v6 = 22.6;
        Number answer = new Double(Math.max(v1 + v2 + v3, v4 + v5 + v6));
        DefaultCategoryDataset d = new DefaultCategoryDataset();
        d.addValue(v1, "Row 0", "Column 0");
        d.addValue(v2, "Row 1", "Column 0");
        d.addValue(v3, "Row 2", "Column 0");
        d.addValue(v4, "Row 0", "Column 1");
        d.addValue(v5, "Row 1", "Column 1");
        d.addValue(v6, "Row 2", "Column 1");
        Number max = DatasetUtilities.findMaximumStackedRangeValue(d);
        assertTrue(max.equals(answer));
    }

    /**
     * Some checks for the findStackedRangeBounds() method.
     */
    @Test
    public void testFindStackedRangeBounds_CategoryDataset1() {
        CategoryDataset d1 = createCategoryDataset1();
        Range r = DatasetUtilities.findStackedRangeBounds(d1);
        assertEquals(0.0, r.getLowerBound(), EPSILON);
        assertEquals(15.0, r.getUpperBound(), EPSILON);

        d1 = createCategoryDataset2();
        r = DatasetUtilities.findStackedRangeBounds(d1);
        assertEquals(-2.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the findStackedRangeBounds() method.
     */
    @Test
    public void testFindStackedRangeBounds_CategoryDataset2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Range r = DatasetUtilities.findStackedRangeBounds(dataset);
        assertTrue(r == null);

        dataset.addValue(5.0, "R1", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, 3.0);
        assertEquals(3.0, r.getLowerBound(), EPSILON);
        assertEquals(8.0, r.getUpperBound(), EPSILON);

        dataset.addValue(-1.0, "R2", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, 3.0);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(8.0, r.getUpperBound(), EPSILON);

        dataset.addValue(null, "R3", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, 3.0);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(8.0, r.getUpperBound(), EPSILON);

        dataset.addValue(Double.NaN, "R4", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, 3.0);
        assertEquals(2.0, r.getLowerBound(), EPSILON);
        assertEquals(8.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the findStackedRangeBounds(CategoryDataset,
     * KeyToGroupMap) method.
     */
    @Test
    public void testFindStackedRangeBounds_CategoryDataset3() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        KeyToGroupMap map = new KeyToGroupMap("Group A");
        Range r = DatasetUtilities.findStackedRangeBounds(dataset, map);
        assertTrue(r == null);

        dataset.addValue(1.0, "R1", "C1");
        dataset.addValue(2.0, "R2", "C1");
        dataset.addValue(3.0, "R3", "C1");
        dataset.addValue(4.0, "R4", "C1");

        map.mapKeyToGroup("R1", "Group A");
        map.mapKeyToGroup("R2", "Group A");
        map.mapKeyToGroup("R3", "Group B");
        map.mapKeyToGroup("R4", "Group B");

        r = DatasetUtilities.findStackedRangeBounds(dataset, map);
        assertEquals(0.0, r.getLowerBound(), EPSILON);
        assertEquals(7.0, r.getUpperBound(), EPSILON);

        dataset.addValue(null, "R5", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, map);
        assertEquals(0.0, r.getLowerBound(), EPSILON);
        assertEquals(7.0, r.getUpperBound(), EPSILON);

        dataset.addValue(Double.NaN, "R6", "C1");
        r = DatasetUtilities.findStackedRangeBounds(dataset, map);
        assertEquals(0.0, r.getLowerBound(), EPSILON);
        assertEquals(7.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the findStackedRangeBounds() method.
     */
    @Test
    public void testFindStackedRangeBoundsForTableXYDataset1() {
        TableXYDataset d2 = createTableXYDataset1();
        Range r = DatasetUtilities.findStackedRangeBounds(d2);
        assertEquals(-2.0, r.getLowerBound(), EPSILON);
        assertEquals(2.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the findStackedRangeBounds() method.
     */
    @Test
    public void testFindStackedRangeBoundsForTableXYDataset2() {
        DefaultTableXYDataset d = new DefaultTableXYDataset();
        Range r = DatasetUtilities.findStackedRangeBounds(d);
        assertEquals(r, new Range(0.0, 0.0));
    }

    /**
     * Tests the stacked range extent calculation.
     */
    @Test
    public void testStackedRangeWithMap() {
        CategoryDataset d = createCategoryDataset1();
        KeyToGroupMap map = new KeyToGroupMap("G0");
        map.mapKeyToGroup("R2", "G1");
        Range r = DatasetUtilities.findStackedRangeBounds(d, map);
        assertEquals(0.0, r.getLowerBound(), EPSILON);
        assertEquals(9.0, r.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the isEmptyOrNull(XYDataset) method.
     */
    @Test
    public void testIsEmptyOrNullXYDataset() {
        XYSeriesCollection dataset = null;
        assertTrue(DatasetUtilities.isEmptyOrNull(dataset));
        dataset = new XYSeriesCollection();
        assertTrue(DatasetUtilities.isEmptyOrNull(dataset));
        XYSeries s1 = new XYSeries("S1");
        dataset.addSeries(s1);
        assertTrue(DatasetUtilities.isEmptyOrNull(dataset));
        s1.add(1.0, 2.0);
        assertFalse(DatasetUtilities.isEmptyOrNull(dataset));
        s1.clear();
        assertTrue(DatasetUtilities.isEmptyOrNull(dataset));
    }

    /**
     * Some checks for the limitPieDataset() methods.
     */
    @Test
    public void testLimitPieDataset() {

        // check that empty dataset is handled OK
        DefaultPieDataset d1 = new DefaultPieDataset();
        PieDataset d2 = DatasetUtilities.createConsolidatedPieDataset(d1,
                "Other", 0.05);
        assertEquals(0, d2.getItemCount());

        // check that minItem limit is observed
        d1.setValue("Item 1", 1.0);
        d1.setValue("Item 2", 49.50);
        d1.setValue("Item 3", 49.50);
        d2 = DatasetUtilities.createConsolidatedPieDataset(d1, "Other", 0.05);
        assertEquals(3, d2.getItemCount());
        assertEquals("Item 1", d2.getKey(0));
        assertEquals("Item 2", d2.getKey(1));
        assertEquals("Item 3", d2.getKey(2));

        // check that minItem limit is observed
        d1.setValue("Item 4", 1.0);
        d2 = DatasetUtilities.createConsolidatedPieDataset(d1, "Other", 0.05,
                2);

        // and that simple aggregation works
        assertEquals(3, d2.getItemCount());
        assertEquals("Item 2", d2.getKey(0));
        assertEquals("Item 3", d2.getKey(1));
        assertEquals("Other", d2.getKey(2));
        assertEquals(new Double(2.0), d2.getValue("Other"));

    }

    /**
     * Some checks for the sampleFunction2D() method.
     */
    @Test
    public void testSampleFunction2D() {
        Function2D f = new LineFunction2D(0, 1);
        XYDataset dataset = DatasetUtilities.sampleFunction2D(f, 0.0, 1.0, 2,
                "S1");
        assertEquals(1, dataset.getSeriesCount());
        assertEquals("S1", dataset.getSeriesKey(0));
        assertEquals(2, dataset.getItemCount(0));
        assertEquals(0.0, dataset.getXValue(0, 0), EPSILON);
        assertEquals(0.0, dataset.getYValue(0, 0), EPSILON);
        assertEquals(1.0, dataset.getXValue(0, 1), EPSILON);
        assertEquals(1.0, dataset.getYValue(0, 1), EPSILON);
    }

    /**
     * A simple check for the findMinimumStackedRangeValue() method.
     */
    @Test
    public void testFindMinimumStackedRangeValue() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // an empty dataset should return a null max
        Number min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertNull(min);

        dataset.addValue(1.0, "R1", "C1");
        min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(0.0, min.doubleValue(), EPSILON);

        dataset.addValue(2.0, "R2", "C1");
        min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(0.0, min.doubleValue(), EPSILON);

        dataset.addValue(-3.0, "R3", "C1");
        min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(-3.0, min.doubleValue(), EPSILON);

        dataset.addValue(Double.NaN, "R4", "C1");
        min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(-3.0, min.doubleValue(), EPSILON);
    }

    /**
     * A simple check for the findMaximumStackedRangeValue() method.
     */
    @Test
    public void testFindMinimumStackedRangeValue2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(-1.0, "R1", "C1");
        Number min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(-1.0, min.doubleValue(), EPSILON);

        dataset.addValue(-2.0, "R2", "C1");
        min = DatasetUtilities.findMinimumStackedRangeValue(dataset);
        assertEquals(-3.0, min.doubleValue(), EPSILON);
    }

    /**
     * A simple check for the findMaximumStackedRangeValue() method.
     */
    @Test
    public void testFindMaximumStackedRangeValue() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // an empty dataset should return a null max
        Number max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertNull(max);

        dataset.addValue(1.0, "R1", "C1");
        max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(1.0, max.doubleValue(), EPSILON);

        dataset.addValue(2.0, "R2", "C1");
        max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(3.0, max.doubleValue(), EPSILON);

        dataset.addValue(-3.0, "R3", "C1");
        max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(3.0, max.doubleValue(), EPSILON);

        dataset.addValue(Double.NaN, "R4", "C1");
        max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(3.0, max.doubleValue(), EPSILON);
    }

    /**
     * A simple check for the findMaximumStackedRangeValue() method.
     */
    @Test
    public void testFindMaximumStackedRangeValue2() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(-1.0, "R1", "C1");
        Number max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(0.0, max.doubleValue(), EPSILON);

        dataset.addValue(-2.0, "R2", "C1");
        max = DatasetUtilities.findMaximumStackedRangeValue(dataset);
        assertEquals(0.0, max.doubleValue(), EPSILON);
    }

    /**
     * Creates a dataset for testing.
     *
     * @return A dataset.
     */
    private CategoryDataset createCategoryDataset1() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        result.addValue(1.0, "R0", "C0");
        result.addValue(1.0, "R1", "C0");
        result.addValue(1.0, "R2", "C0");
        result.addValue(4.0, "R0", "C1");
        result.addValue(5.0, "R1", "C1");
        result.addValue(6.0, "R2", "C1");
        return result;
    }

    /**
     * Creates a dataset for testing.
     *
     * @return A dataset.
     */
    private CategoryDataset createCategoryDataset2() {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        result.addValue(1.0, "R0", "C0");
        result.addValue(-2.0, "R1", "C0");
        result.addValue(2.0, "R0", "C1");
        result.addValue(-1.0, "R1", "C1");
        return result;
    }


    /**
     * Creates a dataset for testing.
     *
     * @return A dataset.
     */
    private XYDataset createXYDataset1() {
        XYSeries series1 = new XYSeries("S1");
        series1.add(1.0, 100.0);
        series1.add(2.0, 101.0);
        series1.add(3.0, 102.0);
        XYSeries series2 = new XYSeries("S2");
        series2.add(1.0, 103.0);
        series2.add(2.0, null);
        series2.add(3.0, 105.0);
        XYSeriesCollection result = new XYSeriesCollection();
        result.addSeries(series1);
        result.addSeries(series2);
        result.setIntervalWidth(0.0);
        return result;
    }

    /**
     * Creates a sample dataset for testing purposes.
     *
     * @return A sample dataset.
     */
    private TableXYDataset createTableXYDataset1() {
        DefaultTableXYDataset dataset = new DefaultTableXYDataset();

        XYSeries s1 = new XYSeries("Series 1", true, false);
        s1.add(1.0, 1.0);
        s1.add(2.0, 2.0);
        dataset.addSeries(s1);

        XYSeries s2 = new XYSeries("Series 2", true, false);
        s2.add(1.0, -2.0);
        s2.add(2.0, -1.0);
        dataset.addSeries(s2);

        return dataset;
    }

    /**
     * Some checks for the iteratorToFindRangeBounds(XYDataset...) method.
     */
    @Test
    public void testIterateToFindRangeBounds1_XYDataset() {
        // null dataset throws IllegalArgumentException
        boolean pass = false;
        try {
            DatasetUtilities.iterateToFindRangeBounds(null, new ArrayList(),
                    new Range(0.0, 1.0), true);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // null list throws IllegalArgumentException
        pass = false;
        try {
            DatasetUtilities.iterateToFindRangeBounds(new XYSeriesCollection(),
                    null, new Range(0.0, 1.0), true);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
        
        // null range throws IllegalArgumentException
        pass = false;
        try {
            DatasetUtilities.iterateToFindRangeBounds(new XYSeriesCollection(),
                    new ArrayList(), null, true);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some tests for the iterateToFindRangeBounds() method.
     */
    @Test
    public void testIterateToFindRangeBounds2_XYDataset() {
        List visibleSeriesKeys = new ArrayList();
        Range xRange = new Range(0.0, 10.0);

        // empty dataset returns null
        XYSeriesCollection dataset = new XYSeriesCollection();
        Range r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertNull(r);

        // add an empty series
        XYSeries s1 = new XYSeries("A");
        dataset.addSeries(s1);
        visibleSeriesKeys.add("A");
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertNull(r);

        // check a null value
        s1.add(1.0, null);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertNull(r);

        // check a NaN
        s1.add(2.0, Double.NaN);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertNull(r);

        // check a regular value
        s1.add(3.0, 5.0);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 5.0), r);

        // check another regular value
        s1.add(4.0, 6.0);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 6.0), r);

        // add a second series
        XYSeries s2 = new XYSeries("B");
        dataset.addSeries(s2);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 6.0), r);
        visibleSeriesKeys.add("B");
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 6.0), r);

        // add a value to the second series
        s2.add(5.0, 15.0);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 15.0), r);

        // add a value that isn't in the xRange
        s2.add(15.0, 150.0);
        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false);
        assertEquals(new Range(5.0, 15.0), r);

        r = DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, new Range(0.0, 20.0), false);
        assertEquals(new Range(5.0, 150.0), r);
    }

    /**
     * Some checks for the iterateToFindRangeBounds() method when applied to
     * a BoxAndWhiskerXYDataset.
     */
    @Test
    public void testIterateToFindRangeBounds_BoxAndWhiskerXYDataset() {
        DefaultBoxAndWhiskerXYDataset dataset
                = new DefaultBoxAndWhiskerXYDataset("Series 1");
        List visibleSeriesKeys = new ArrayList();
        visibleSeriesKeys.add("Series 1");
        Range xRange = new Range(Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY);
        assertNull(DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false));

        dataset.add(new Date(50L), new BoxAndWhiskerItem(5.0, 4.9, 2.0, 8.0,
                1.0, 9.0, 0.0, 10.0, new ArrayList()));
        assertEquals(new Range(5.0, 5.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, false));
        assertEquals(new Range(1.0, 9.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, xRange, true));
    }

    /**
     * Some checks for the iterateToFindRangeBounds(CategoryDataset...)
     * method.
     */
    @Test
    public void testIterateToFindRangeBounds_StatisticalCategoryDataset() {
        DefaultStatisticalCategoryDataset dataset
                = new DefaultStatisticalCategoryDataset();
        List visibleSeriesKeys = new ArrayList();
        assertNull(DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, false));
        dataset.add(1.0, 0.5, "R1", "C1");
        visibleSeriesKeys.add("R1");
        assertEquals(new Range(1.0, 1.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, false));
        assertEquals(new Range(0.5, 1.5),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));
    }

    /**
     * Some checks for the iterateToFindRangeBounds(CategoryDataset...) method
     * with a {@link MultiValueCategoryDataset}.
     */
    @Test
    public void testIterateToFindRangeBounds_MultiValueCategoryDataset() {
        DefaultMultiValueCategoryDataset dataset
                = new DefaultMultiValueCategoryDataset();
        List visibleSeriesKeys = new ArrayList();
        assertNull(DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));
        List values = Arrays.asList(new Double[] {new Double(1.0)});
        dataset.add(values, "R1", "C1");
        visibleSeriesKeys.add("R1");
        assertEquals(new Range(1.0, 1.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));

        values = Arrays.asList(new Double[] {new Double(2.0), new Double(3.0)});
        dataset.add(values, "R1", "C2");
        assertEquals(new Range(1.0, 3.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));

        values = Arrays.asList(new Double[] {new Double(-1.0),
                new Double(-2.0)});
        dataset.add(values, "R2", "C1");
        assertEquals(new Range(1.0, 3.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));
        visibleSeriesKeys.add("R2");
        assertEquals(new Range(-2.0, 3.0),
                DatasetUtilities.iterateToFindRangeBounds(dataset,
                visibleSeriesKeys, true));
    }

    /**
     * Some checks for the iterateRangeBounds() method when passed an
     * IntervalCategoryDataset.
     */
    @Test
    public void testIterateRangeBounds_IntervalCategoryDataset() {
        TestIntervalCategoryDataset d = new TestIntervalCategoryDataset();
        d.addItem(1.0, 2.0, 3.0, "R1", "C1");
        assertEquals(new Range(1.0, 3.0),
                DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(2.5, 2.0, 3.0, "R1", "C1");
        assertEquals(new Range(2.0, 3.0),
                DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(4.0, 2.0, 3.0, "R1", "C1");
        assertEquals(new Range(2.0, 4.0),
                DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(null, new Double(2.0), new Double(3.0), "R1", "C1");
        assertEquals(new Range(2.0, 3.0),
                DatasetUtilities.iterateRangeBounds(d));

        // try some nulls
        d = new TestIntervalCategoryDataset();
        d.addItem(null, null, null, "R1", "C1");
        assertNull(DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(new Double(1.0), null, null, "R1", "C1");
        assertEquals(new Range(1.0, 1.0),
                DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(null, new Double(1.0), null, "R1", "C1");
        assertEquals(new Range(1.0, 1.0),
                DatasetUtilities.iterateRangeBounds(d));

        d = new TestIntervalCategoryDataset();
        d.addItem(null, null, new Double(1.0), "R1", "C1");
        assertEquals(new Range(1.0, 1.0),
                DatasetUtilities.iterateRangeBounds(d));
    }

    /**
     * A test for bug 2849731.
     */
    @Test
    public void testBug2849731() {
        TestIntervalCategoryDataset d = new TestIntervalCategoryDataset();
        d.addItem(2.5, 2.0, 3.0, "R1", "C1");
        d.addItem(new Double(4.0), null, null, "R2", "C1");
        assertEquals(new Range(2.0, 4.0),
                DatasetUtilities.iterateRangeBounds(d));
    }

    /**
     * Another test for bug 2849731.
     */
    @Test
    public void testBug2849731_2() {
        XYIntervalSeriesCollection d = new XYIntervalSeriesCollection();
        XYIntervalSeries s = new XYIntervalSeries("S1");
        s.add(1.0, Double.NaN, Double.NaN, Double.NaN, 1.5, Double.NaN);
        d.addSeries(s);
        Range r = DatasetUtilities.iterateDomainBounds(d);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(1.0, r.getUpperBound(), EPSILON);

        s.add(1.0, 1.5, Double.NaN, Double.NaN, 1.5, Double.NaN);
        r = DatasetUtilities.iterateDomainBounds(d);
        assertEquals(1.0, r.getLowerBound(), EPSILON);
        assertEquals(1.5, r.getUpperBound(), EPSILON);

        s.add(1.0, Double.NaN, 0.5, Double.NaN, 1.5, Double.NaN);
        r = DatasetUtilities.iterateDomainBounds(d);
        assertEquals(0.5, r.getLowerBound(), EPSILON);
        assertEquals(1.5, r.getUpperBound(), EPSILON);
    }
    /**
     * Yet another test for bug 2849731.
     */
    @Test
    public void testBug2849731_3() {
        XYIntervalSeriesCollection d = new XYIntervalSeriesCollection();
        XYIntervalSeries s = new XYIntervalSeries("S1");
        s.add(1.0, Double.NaN, Double.NaN, 1.5, Double.NaN, Double.NaN);
        d.addSeries(s);
        Range r = DatasetUtilities.iterateRangeBounds(d);
        assertEquals(1.5, r.getLowerBound(), EPSILON);
        assertEquals(1.5, r.getUpperBound(), EPSILON);

        s.add(1.0, 1.5, Double.NaN, Double.NaN, Double.NaN, 2.5);
        r = DatasetUtilities.iterateRangeBounds(d);
        assertEquals(1.5, r.getLowerBound(), EPSILON);
        assertEquals(2.5, r.getUpperBound(), EPSILON);

        s.add(1.0, Double.NaN, 0.5, Double.NaN, 3.5, Double.NaN);
        r = DatasetUtilities.iterateRangeBounds(d);
        assertEquals(1.5, r.getLowerBound(), EPSILON);
        assertEquals(3.5, r.getUpperBound(), EPSILON);
    }
    
    /**
     * Check the findYValue() method with a dataset that is in ascending order 
     * of x-values.
     */
    @Test
    public void testFindYValue() {
        XYSeries series = new XYSeries("S1");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 100.0)));
        
        series.add(1.0, 5.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 2.0)));
        
        series.add(2.0, 10.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertEquals(6.25, DatasetUtilities.findYValue(dataset, 0, 1.25), EPSILON);
        assertEquals(7.5, DatasetUtilities.findYValue(dataset, 0, 1.5), EPSILON);
        assertEquals(10.0, DatasetUtilities.findYValue(dataset, 0, 2.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 3.0)));
    }
    
    /**
     * Check the findYValue() method with a dataset that is not sorted.
     */
    @Test
    public void testFindYValueNonSorted() {
        XYSeries series = new XYSeries("S1", false);
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 100.0)));
        
        series.add(1.0, 5.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 2.0)));
        
        series.add(0.0, 10.0);
        series.add(4.0, 20.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, -0.5)));
        assertEquals(10.0, DatasetUtilities.findYValue(dataset, 0, 0.0), EPSILON);
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertEquals(15.0, DatasetUtilities.findYValue(dataset, 0, 2.0), EPSILON);
        assertEquals(20.0, DatasetUtilities.findYValue(dataset, 0, 4.0), EPSILON);
        assertEquals(17.5, DatasetUtilities.findYValue(dataset, 0, 3.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 5.0)));        
    }
    
    /**
     * Check the findYValue() method with a dataset that allows duplicate
     * values.
     */
    @Test
    public void testFindYValueWithDuplicates() {
        XYSeries series = new XYSeries("S1", true, true);
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 100.0)));
        
        series.add(1.0, 5.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 2.0)));
        
        series.add(1.0, 10.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 2.0)));
        
        series.add(2.0, 10.0);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 0.0)));
        assertEquals(5.0, DatasetUtilities.findYValue(dataset, 0, 1.0), EPSILON);
        assertEquals(10.0, DatasetUtilities.findYValue(dataset, 0, 1.25), EPSILON);
        assertEquals(10.0, DatasetUtilities.findYValue(dataset, 0, 1.5), EPSILON);
        assertEquals(10.0, DatasetUtilities.findYValue(dataset, 0, 2.0), EPSILON);
        assertTrue(Double.isNaN(DatasetUtilities.findYValue(dataset, 0, 3.0)));
    }

}
