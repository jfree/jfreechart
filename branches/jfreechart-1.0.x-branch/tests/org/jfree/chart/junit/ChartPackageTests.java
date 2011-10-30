/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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

package org.jfree.chart.junit;

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
        suite.addTestSuite(AreaChartTests.class);
        suite.addTestSuite(BarChartTests.class);
        suite.addTestSuite(BarChart3DTests.class);
        suite.addTestSuite(ChartPanelTests.class);
        suite.addTestSuite(ChartRenderingInfoTests.class);
        suite.addTestSuite(GanttChartTests.class);
        suite.addTestSuite(HashUtilitiesTests.class);
        suite.addTestSuite(JFreeChartTests.class);
        suite.addTestSuite(LegendItemTests.class);
        suite.addTestSuite(LegendItemCollectionTests.class);
        suite.addTestSuite(LineChartTests.class);
        suite.addTestSuite(LineChart3DTests.class);
        suite.addTestSuite(MeterChartTests.class);
        suite.addTestSuite(PaintMapTests.class);
        suite.addTestSuite(PieChartTests.class);
        suite.addTestSuite(PieChart3DTests.class);
        suite.addTestSuite(ScatterPlotTests.class);
        suite.addTestSuite(StackedAreaChartTests.class);
        suite.addTestSuite(StackedBarChartTests.class);
        suite.addTestSuite(StackedBarChart3DTests.class);
        suite.addTestSuite(StandardChartThemeTests.class);
        suite.addTestSuite(StrokeMapTests.class);
        suite.addTestSuite(TimeSeriesChartTests.class);
        suite.addTestSuite(WaterfallChartTests.class);
        suite.addTestSuite(XYAreaChartTests.class);
        suite.addTestSuite(XYBarChartTests.class);
        suite.addTestSuite(XYLineChartTests.class);
        suite.addTestSuite(XYStepAreaChartTests.class);
        suite.addTestSuite(XYStepChartTests.class);
        return suite;
    }

}
