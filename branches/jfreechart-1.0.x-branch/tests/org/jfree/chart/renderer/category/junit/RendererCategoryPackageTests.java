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

package org.jfree.chart.renderer.category.junit;

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
        suite.addTestSuite(AbstractCategoryItemRendererTests.class);
        suite.addTestSuite(AreaRendererTests.class);
        suite.addTestSuite(BarRendererTests.class);
        suite.addTestSuite(BarRenderer3DTests.class);
        suite.addTestSuite(BoxAndWhiskerRendererTests.class);
        suite.addTestSuite(CategoryStepRendererTests.class);
        suite.addTestSuite(DefaultCategoryItemRendererTests.class);
        suite.addTestSuite(GanttRendererTests.class);
        suite.addTestSuite(GradientBarPainterTests.class);
        suite.addTestSuite(GroupedStackedBarRendererTests.class);
        suite.addTestSuite(IntervalBarRendererTests.class);
        suite.addTestSuite(LayeredBarRendererTests.class);
        suite.addTestSuite(LevelRendererTests.class);
        suite.addTestSuite(LineAndShapeRendererTests.class);
        suite.addTestSuite(LineRenderer3DTests.class);
        suite.addTestSuite(MinMaxCategoryRendererTests.class);
        suite.addTestSuite(ScatterRendererTests.class);
        suite.addTestSuite(StackedAreaRendererTests.class);
        suite.addTestSuite(StackedBarRendererTests.class);
        suite.addTestSuite(StackedBarRenderer3DTests.class);
        suite.addTestSuite(StandardBarPainterTests.class);
        suite.addTestSuite(StatisticalBarRendererTests.class);
        suite.addTestSuite(StatisticalLineAndShapeRendererTests.class);
        suite.addTestSuite(WaterfallBarRendererTests.class);
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
