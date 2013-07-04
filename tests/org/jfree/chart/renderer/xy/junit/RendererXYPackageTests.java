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
 * RendererXYPackageTests.java
 * ---------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 23-Aug-2004 : Restructured org.jfree.chart.renderer package (DG);
 * 06-Jan-2005 : Added method to create test dataset (DG);
 * 07-Jan-2005 : Added a second method to create a test dataset (DG);
 * 19-Jan-2005 : Added main() method to run JUnit in text mode (DG);
 * 25-Oct-2006 : Added tests for XYErrorRenderer class (DG);
 * 31-Jan-2007 : Added XYBlockRendererTests (DG);
 * 26-Feb-2007 : Added DeviationRendererTests (DG);
 * 30-Apr-2007 : Added XYLine3DRendererTests (DG);
 * 25-May-2007 : Added VectorRendererTests (DG);
 * 25-Jul-2007 : Added XYSplineAndRendererTests (DG);
 *
 */

package org.jfree.chart.renderer.xy.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * A collection of tests for the org.jfree.chart.renderer.xy package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class RendererXYPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.renderer.xy");
        suite.addTestSuite(AbstractXYItemRendererTests.class);
        suite.addTestSuite(CandlestickRendererTests.class);
        suite.addTestSuite(ClusteredXYBarRendererTests.class);
        suite.addTestSuite(DeviationRendererTests.class);
        suite.addTestSuite(GradientXYBarPainterTests.class);
        suite.addTestSuite(HighLowRendererTests.class);
        suite.addTestSuite(StackedXYAreaRendererTests.class);
        suite.addTestSuite(StackedXYAreaRenderer2Tests.class);
        suite.addTestSuite(StackedXYBarRendererTests.class);
        suite.addTestSuite(StandardXYBarPainterTests.class);
        suite.addTestSuite(StandardXYItemRendererTests.class);
        suite.addTestSuite(VectorRendererTests.class);
        suite.addTestSuite(WindItemRendererTests.class);
        suite.addTestSuite(XYAreaRendererTests.class);
        suite.addTestSuite(XYAreaRenderer2Tests.class);
        suite.addTestSuite(XYBarRendererTests.class);
        suite.addTestSuite(XYBlockRendererTests.class);
        suite.addTestSuite(XYBoxAndWhiskerRendererTests.class);
        suite.addTestSuite(XYBubbleRendererTests.class);
        suite.addTestSuite(XYDifferenceRendererTests.class);
        suite.addTestSuite(XYDotRendererTests.class);
        suite.addTestSuite(XYErrorRendererTests.class);
        suite.addTestSuite(XYLineAndShapeRendererTests.class);
        suite.addTestSuite(XYLine3DRendererTests.class);
        suite.addTestSuite(XYShapeRendererTests.class);
        suite.addTestSuite(XYSplineRendererTests.class);
        suite.addTestSuite(XYStepRendererTests.class);
        suite.addTestSuite(XYStepAreaRendererTests.class);
        suite.addTestSuite(YIntervalRendererTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public RendererXYPackageTests(String name) {
        super(name);
    }

    /**
     * Creates and returns a sample dataset for testing purposes.
     *
     * @return A sample dataset.
     */
    public static XYSeriesCollection createTestXYSeriesCollection() {
        XYSeriesCollection result = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Series 1", false, false);
        series1.add(1.0, 2.0);
        series1.add(2.0, 5.0);
        XYSeries series2 = new XYSeries("Series 2", false, false);
        series2.add(1.0, 4.0);
        series2.add(2.0, 3.0);
        result.addSeries(series1);
        result.addSeries(series2);
        return result;
    }

    /**
     * Creates and returns a sample dataset for testing purposes.
     *
     * @return A sample dataset.
     */
    public static TableXYDataset createTestTableXYDataset() {
        DefaultTableXYDataset result = new DefaultTableXYDataset();
        XYSeries series1 = new XYSeries("Series 1", false, false);
        series1.add(1.0, 2.0);
        series1.add(2.0, 5.0);
        XYSeries series2 = new XYSeries("Series 2", false, false);
        series2.add(1.0, 4.0);
        series2.add(2.0, 3.0);
        result.addSeries(series1);
        result.addSeries(series2);
        return result;
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
