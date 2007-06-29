/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ---------------------
 * PlotPackageTests.java
 * ---------------------
 * (C) Copyright 2003-2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PlotPackageTests.java,v 1.1.2.1 2006/10/03 15:41:28 mungady Exp $
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
 *
 */

package org.jfree.chart.plot.junit;

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
        suite.addTestSuite(CategoryMarkerTests.class);
        suite.addTestSuite(CategoryPlotTests.class);
        suite.addTestSuite(ColorPaletteTests.class);
        suite.addTestSuite(CombinedDomainCategoryPlotTests.class);
        suite.addTestSuite(CombinedDomainXYPlotTests.class);
        suite.addTestSuite(CombinedRangeCategoryPlotTests.class);
        suite.addTestSuite(CombinedRangeXYPlotTests.class);
        suite.addTestSuite(CompassPlotTests.class);
        suite.addTestSuite(ContourPlotTests.class);
        suite.addTestSuite(DefaultDrawingSupplierTests.class);
        suite.addTestSuite(FastScatterPlotTests.class);
        suite.addTestSuite(IntervalMarkerTests.class);
        suite.addTestSuite(MarkerTests.class);
        suite.addTestSuite(MeterIntervalTests.class);
        suite.addTestSuite(MeterPlotTests.class);
        suite.addTestSuite(MultiplePiePlotTests.class);
        suite.addTestSuite(PiePlotTests.class);
        suite.addTestSuite(PiePlot3DTests.class);
        suite.addTestSuite(PlotOrientationTests.class);
        suite.addTestSuite(PlotRenderingInfoTests.class);
        suite.addTestSuite(PlotTests.class);
        suite.addTestSuite(PolarPlotTests.class);
        suite.addTestSuite(RingPlotTests.class);
        suite.addTestSuite(SpiderWebPlotTests.class);
        suite.addTestSuite(ThermometerPlotTests.class);
        suite.addTestSuite(ValueMarkerTests.class);
        suite.addTestSuite(XYPlotTests.class);
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
