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
 * -----------------------
 * DataXYPackageTests.java
 * -----------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Aug-2004 : Restructured org.jfree.data package (DG);
 * 18-Jan-2005 : Added main() method (DG);
 * 21-Jan-2005 : Added IntervalXYDelegateTests (DG);
 * 29-Apr-2005 : Added DefaultOHLCDatasetTests and OHLCDataItemTests (DG);
 * 06-Oct-2005 : Added CategoryTableXYDatasetTests (DG);
 * 06-Jul-2006 : Added new DefaultXYDatasetTests (DG);
 * 12-Jul-2006 : Added new DefaultXYZDatasetTests and
 *               DefaultWindDatasetTests (DG);
 * 27-Nov-2006 : Added MatrixSeriesCollectionTests (DG);
 * 25-Jan-2007 : Added XYBarDatasetTests (DG);
 * 25-May-2007 : Added VectorXXX tests (DG);
 *
 */

package org.jfree.data.xy;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for the <code>org.jfree.data.category</code> package that can
 * be run using JUnit.  You can find more information about JUnit at
 * <a href="http://www.junit.org">http://www.junit.org</a>.
 */
public class DataXYPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.data.xy");
        suite.addTestSuite(CategoryTableXYDatasetTest.class);
        suite.addTestSuite(DefaultHighLowDatasetTest.class);
        suite.addTestSuite(DefaultIntervalXYDatasetTest.class);
        suite.addTestSuite(DefaultOHLCDatasetTest.class);
        suite.addTestSuite(DefaultTableXYDatasetTest.class);
        suite.addTestSuite(DefaultWindDatasetTest.class);
        suite.addTestSuite(DefaultXYDatasetTest.class);
        suite.addTestSuite(DefaultXYZDatasetTest.class);
        suite.addTestSuite(IntervalXYDelegateTest.class);
        suite.addTestSuite(MatrixSeriesCollectionTest.class);
        suite.addTestSuite(MatrixSeriesTest.class);
        suite.addTestSuite(OHLCDataItemTest.class);
        suite.addTestSuite(TableXYDatasetTest.class);
        suite.addTestSuite(VectorDataItemTest.class);
        suite.addTestSuite(VectorSeriesCollectionTest.class);
        suite.addTestSuite(VectorSeriesTest.class);
        suite.addTestSuite(VectorTest.class);
        suite.addTestSuite(XIntervalDataItemTest.class);
        suite.addTestSuite(XIntervalSeriesCollectionTest.class);
        suite.addTestSuite(XIntervalSeriesTest.class);
        suite.addTestSuite(XYBarDatasetTest.class);
        suite.addTestSuite(XYCoordinateTest.class);
        suite.addTestSuite(XYDataItemTest.class);
        suite.addTestSuite(XYIntervalDataItemTest.class);
        suite.addTestSuite(XYIntervalSeriesCollectionTest.class);
        suite.addTestSuite(XYIntervalSeriesTest.class);
        suite.addTestSuite(XYIntervalTest.class);
        suite.addTestSuite(XYSeriesCollectionTest.class);
        suite.addTestSuite(XYSeriesTest.class);
        suite.addTestSuite(YIntervalDataItemTest.class);
        suite.addTestSuite(YIntervalSeriesCollectionTest.class);
        suite.addTestSuite(YIntervalSeriesTest.class);
        suite.addTestSuite(YIntervalTest.class);
        suite.addTestSuite(YWithXIntervalTest.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the test suite name.
     */
    public DataXYPackageTests(String name) {
        super(name);
    }

    /**
     * Runs the test suite using JUnit's text-based runner.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
