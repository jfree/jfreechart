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
 * PlotPackageTests.java
 * ---------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 18-Mar-2003 : Version 1 (DG);
 * 09-Nov-2004 : Added tests for DonutPlot (DG);
 * 19-Jan-2005 : Added main() method to run JUnit in text mode (DG);
 * 06-Jun-2005 : Added PlotTests (DG);
 * 16-Jun-2005 : Added MultiplePiePlotTests (DG);
 * 19-Aug-2005 : Added CategoryMarkerTests (DG);
 * 05-Sep-2006 : Added MarkerTests (DG);
 * 21-Nov-2007 : Added PieLabelRecordTests (DG);
 * 09-Apr-2009 : Added CrosshairTests (DG);
 *
 */

package org.jfree.chart.plot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.plot package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class PlotPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.plot");
        suite.addTestSuite(CategoryMarkerTest.class);
        suite.addTestSuite(CategoryPlotTest.class);
        suite.addTestSuite(ColorPaletteTest.class);
        suite.addTestSuite(CombinedDomainCategoryPlotTest.class);
        suite.addTestSuite(CombinedDomainXYPlotTest.class);
        suite.addTestSuite(CombinedRangeCategoryPlotTest.class);
        suite.addTestSuite(CombinedRangeXYPlotTest.class);
        suite.addTestSuite(CompassPlotTest.class);
        suite.addTestSuite(ContourPlotTest.class);
        suite.addTestSuite(CrosshairTest.class);
        suite.addTestSuite(DefaultDrawingSupplierTest.class);
        suite.addTestSuite(FastScatterPlotTest.class);
        suite.addTestSuite(IntervalMarkerTest.class);
        suite.addTestSuite(MarkerTest.class);
        suite.addTestSuite(MeterIntervalTest.class);
        suite.addTestSuite(MeterPlotTest.class);
        suite.addTestSuite(MultiplePiePlotTest.class);
        suite.addTestSuite(PieLabelRecordTest.class);
        suite.addTestSuite(PiePlotTest.class);
        suite.addTestSuite(PiePlot3DTest.class);
        suite.addTestSuite(PlotOrientationTest.class);
        suite.addTestSuite(PlotRenderingInfoTest.class);
        suite.addTestSuite(PlotTest.class);
        suite.addTestSuite(PolarPlotTest.class);
        suite.addTestSuite(RingPlotTest.class);
        suite.addTestSuite(SpiderWebPlotTest.class);
        suite.addTestSuite(ThermometerPlotTest.class);
        suite.addTestSuite(ValueMarkerTest.class);
        suite.addTestSuite(XYPlotTest.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public PlotPackageTests(String name) {
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
