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
 * -------------------------------
 * DataStatisticsPackageTests.java
 * -------------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 28-Aug-2003 : Version 1 (DG);
 * 01-Mar-2004 : Added tests for BoxAndWhiskerItem class (DG);
 * 25-Mar-2004 : Added tests for Statistics class (DG);
 * 10-Jan-2005 : Added tests for new SimpleHistogramDataset and
 *               SimpleHistogramBin classes (DG);
 * 18-Jan-2005 : Added main() method (DG);
 * 12-Nov-2007 : Added DefaultBoxAndWhiskerXYDatasetTests (DG);
 *
 */

package org.jfree.data.statistics.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for the <code>org.jfree.data.statistics</code> package that can
 * be run using JUnit.  You can find more information about JUnit at
 * <a href="http://www.junit.org">http://www.junit.org</a>.
 */
public class DataStatisticsPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.data.statistics");
        suite.addTestSuite(BoxAndWhiskerCalculatorTests.class);
        suite.addTestSuite(BoxAndWhiskerItemTests.class);
        suite.addTestSuite(DefaultBoxAndWhiskerCategoryDatasetTests.class);
        suite.addTestSuite(DefaultBoxAndWhiskerXYDatasetTests.class);
        suite.addTestSuite(DefaultStatisticalCategoryDatasetTests.class);
        suite.addTestSuite(HistogramBinTests.class);
        suite.addTestSuite(HistogramDatasetTests.class);
        suite.addTestSuite(MeanAndStandardDeviationTests.class);
        suite.addTestSuite(RegressionTests.class);
        suite.addTestSuite(SimpleHistogramBinTests.class);
        suite.addTestSuite(SimpleHistogramDatasetTests.class);
        suite.addTestSuite(StatisticsTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the test suite name.
     */
    public DataStatisticsPackageTests(String name) {
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
