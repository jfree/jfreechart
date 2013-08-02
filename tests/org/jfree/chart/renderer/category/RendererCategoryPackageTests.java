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
 * ---------------------------------
 * RendererCategoryPackageTests.java
 * ---------------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 23-Aug-2004 : Restructured org.jfree.chart.renderer package (DG);
 * 18-Jan-2005 : Added main() method.
 * 15-Jun-2005 : Added new tests for StatisticalLineAndShapeRendererTests (DG);
 *
 */

package org.jfree.chart.renderer.category;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.renderer.category package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class RendererCategoryPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.renderer.category");
        suite.addTestSuite(AbstractCategoryItemRendererTest.class);
        suite.addTestSuite(AreaRendererTest.class);
        suite.addTestSuite(BarRendererTest.class);
        suite.addTestSuite(BarRenderer3DTest.class);
        suite.addTestSuite(BoxAndWhiskerRendererTest.class);
        suite.addTestSuite(CategoryStepRendererTest.class);
        suite.addTestSuite(DefaultCategoryItemRendererTest.class);
        suite.addTestSuite(GanttRendererTest.class);
        suite.addTestSuite(GradientBarPainterTest.class);
        suite.addTestSuite(GroupedStackedBarRendererTest.class);
        suite.addTestSuite(IntervalBarRendererTest.class);
        suite.addTestSuite(LayeredBarRendererTest.class);
        suite.addTestSuite(LevelRendererTest.class);
        suite.addTestSuite(LineAndShapeRendererTest.class);
        suite.addTestSuite(LineRenderer3DTest.class);
        suite.addTestSuite(MinMaxCategoryRendererTest.class);
        suite.addTestSuite(ScatterRendererTest.class);
        suite.addTestSuite(StackedAreaRendererTest.class);
        suite.addTestSuite(StackedBarRendererTest.class);
        suite.addTestSuite(StackedBarRenderer3DTest.class);
        suite.addTestSuite(StandardBarPainterTest.class);
        suite.addTestSuite(StatisticalBarRendererTest.class);
        suite.addTestSuite(StatisticalLineAndShapeRendererTest.class);
        suite.addTestSuite(WaterfallBarRendererTest.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public RendererCategoryPackageTests(String name) {
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
