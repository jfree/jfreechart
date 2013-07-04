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

package org.jfree.chart.axis.junit;

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
        suite.addTestSuite(AxisLocationTests.class);
        suite.addTestSuite(AxisSpaceTests.class);
        suite.addTestSuite(AxisTests.class);
        suite.addTestSuite(CategoryAnchorTests.class);
        suite.addTestSuite(CategoryAxisTests.class);
        suite.addTestSuite(CategoryAxis3DTests.class);
        suite.addTestSuite(CategoryLabelPositionTests.class);
        suite.addTestSuite(CategoryLabelPositionsTests.class);
        suite.addTestSuite(CategoryLabelWidthTypeTests.class);
        suite.addTestSuite(CategoryTickTests.class);
        suite.addTestSuite(ColorBarTests.class);
        suite.addTestSuite(CyclicNumberAxisTests.class);
        suite.addTestSuite(DateAxisTests.class);
        suite.addTestSuite(DateTickTests.class);
        suite.addTestSuite(DateTickMarkPositionTests.class);
        suite.addTestSuite(DateTickUnitTests.class);
        suite.addTestSuite(ExtendedCategoryAxisTests.class);
        suite.addTestSuite(LogAxisTests.class);
        suite.addTestSuite(LogarithmicAxisTests.class);
        suite.addTestSuite(MarkerAxisBandTests.class);
        suite.addTestSuite(ModuloAxisTests.class);
        suite.addTestSuite(MonthDateFormatTests.class);
        suite.addTestSuite(NumberAxisTests.class);
        suite.addTestSuite(NumberAxis3DTests.class);
        suite.addTestSuite(NumberTickUnitTests.class);
        suite.addTestSuite(PeriodAxisTests.class);
        suite.addTestSuite(PeriodAxisLabelInfoTests.class);
        suite.addTestSuite(QuarterDateFormatTests.class);
        suite.addTestSuite(SegmentedTimelineTests.class);
        suite.addTestSuite(SegmentedTimelineTests2.class);
        suite.addTestSuite(StandardTickUnitSourceTests.class);
        suite.addTestSuite(SubCategoryAxisTests.class);
        suite.addTestSuite(SymbolAxisTests.class);
        suite.addTestSuite(TickUnitsTests.class);
        suite.addTestSuite(ValueAxisTests.class);
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

