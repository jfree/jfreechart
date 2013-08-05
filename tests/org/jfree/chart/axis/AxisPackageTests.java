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
 * AxisPackageTests.java
 * ---------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Bill Kelemen;
 *
 * Changes:
 * --------
 * 26-Mar-2003 : Version 1 (DG);
 * 25-May-2003 : Added SegmentedTimelineTests (BK);
 * 17-Feb-2004 : Added extra tests (DG);
 * 19-Jan-2005 : Added main() method to run JUnit in text mode (DG);
 * 21-Mar-2007 : Added ExtendedCategoryAxisTests (DG);
 * 02-Aug-2007 : Added LogAxisTests and TickUnitsTests (DG);
 * 25-Oct-2007 : Added StandardTickUnitSourceTests (DG);
 * 13-Nov-2007 : Added ModuloAxisTests (DG);
 *
 */

package org.jfree.chart.axis;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.axis package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class AxisPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.axis");
        suite.addTestSuite(AxisLocationTest.class);
        suite.addTestSuite(AxisSpaceTest.class);
        suite.addTestSuite(CategoryAnchorTest.class);
        suite.addTestSuite(CategoryAxisTest.class);
        suite.addTestSuite(CategoryAxis3DTest.class);
        suite.addTestSuite(CategoryLabelPositionTest.class);
        suite.addTestSuite(CategoryLabelPositionsTest.class);
        suite.addTestSuite(CategoryLabelWidthTypeTest.class);
        suite.addTestSuite(CategoryTickTest.class);
        suite.addTestSuite(ColorBarTest.class);
        suite.addTestSuite(CyclicNumberAxisTest.class);
        suite.addTestSuite(DateAxisTest.class);
        suite.addTestSuite(DateTickTest.class);
        suite.addTestSuite(DateTickMarkPositionTest.class);
        suite.addTestSuite(DateTickUnitTest.class);
        suite.addTestSuite(ExtendedCategoryAxisTest.class);
        suite.addTestSuite(LogAxisTest.class);
        suite.addTestSuite(LogarithmicAxisTest.class);
        suite.addTestSuite(MarkerAxisBandTest.class);
        suite.addTestSuite(ModuloAxisTest.class);
        suite.addTestSuite(MonthDateFormatTest.class);
        suite.addTestSuite(NumberAxisTest.class);
        suite.addTestSuite(NumberAxis3DTest.class);
        suite.addTestSuite(NumberTickUnitTest.class);
        suite.addTestSuite(PeriodAxisTest.class);
        suite.addTestSuite(PeriodAxisLabelInfoTest.class);
        suite.addTestSuite(QuarterDateFormatTest.class);
        suite.addTestSuite(SegmentedTimelineTest.class);
        suite.addTestSuite(SegmentedTimelineTest2.class);
        suite.addTestSuite(StandardTickUnitSourceTest.class);
        suite.addTestSuite(SubCategoryAxisTest.class);
        suite.addTestSuite(SymbolAxisTest.class);
        suite.addTestSuite(TickUnitsTest.class);
        suite.addTestSuite(ValueAxisTest.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public AxisPackageTests(String name) {
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

