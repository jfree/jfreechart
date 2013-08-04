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
 * ----------------------
 * ChartPackageTests.java
 * ----------------------
 * (C) Copyright 2002-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 11-Jun-2002 : Version 1 (DG);
 * 17-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 21-May-2004 : Added PieChart3DTests (DG);
 * 02-Mar-2007 : Added missing tests (DG);
 * 06-Mar-2007 : Added HashUtilitiesTests (DG);
 * 14-Aug-2008 : Added StandardChartThemeTests (DG);
 *
 */

package org.jfree.chart;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the <code>org.jfree.chart</code> package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class ChartPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart");
        suite.addTestSuite(LineChartTest.class);
        suite.addTestSuite(LineChart3DTest.class);
        suite.addTestSuite(MeterChartTest.class);
        suite.addTestSuite(PaintMapTest.class);
        suite.addTestSuite(PieChartTest.class);
        suite.addTestSuite(PieChart3DTest.class);
        suite.addTestSuite(ScatterPlotTest.class);
        suite.addTestSuite(StackedAreaChartTest.class);
        suite.addTestSuite(StackedBarChartTest.class);
        suite.addTestSuite(StackedBarChart3DTest.class);
        suite.addTestSuite(StandardChartThemeTest.class);
        suite.addTestSuite(StrokeMapTest.class);
        suite.addTestSuite(TimeSeriesChartTest.class);
        suite.addTestSuite(WaterfallChartTest.class);
        suite.addTestSuite(XYAreaChartTest.class);
        suite.addTestSuite(XYBarChartTest.class);
        suite.addTestSuite(XYLineChartTest.class);
        suite.addTestSuite(XYStepAreaChartTest.class);
        suite.addTestSuite(XYStepChartTest.class);
        return suite;
    }

}
