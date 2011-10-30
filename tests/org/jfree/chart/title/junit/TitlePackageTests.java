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
 * TitlePackageTests.java
 * ----------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 17-Feb-2004 : Version 1 (DG);
 * 19-Jan-2005 : Added main() method to run JUnit in text mode (DG);
 * 01-Sep-2005 : Added tests for LegendGraphic (DG);
 * 31-Jan-2007 : Added PaintScaleLegendTests (DG);
 * 05-May-2008 : Added ShortTextTitleTests (DG);
 *
 */

package org.jfree.chart.title.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.title package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class TitlePackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.title");
        suite.addTestSuite(CompositeTitleTests.class);
        suite.addTestSuite(DateTitleTests.class);
        suite.addTestSuite(ImageTitleTests.class);
        suite.addTestSuite(LegendGraphicTests.class);
        suite.addTestSuite(LegendTitleTests.class);
        suite.addTestSuite(PaintScaleLegendTests.class);
        suite.addTestSuite(ShortTextTitleTests.class);
        suite.addTestSuite(TextTitleTests.class);
        suite.addTestSuite(TitleTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public TitlePackageTests(String name) {
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
