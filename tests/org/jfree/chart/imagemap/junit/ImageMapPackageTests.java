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
 * -------------------------
 * ImageMapPackageTests.java
 * -------------------------
 * (C) Copyright 2007-2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 05-Dec-2007 : Version 1 (DG);
 * 25-Mar-2009 : Added DynamicDriveToolTipTagFragmentGeneratorTests, 
 *               ImageMapUtilitiesTests and
 *               OverLIBToolTipTagFragmentGeneratorTests (DG);
 *
 */

package org.jfree.chart.imagemap.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.imagemap package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class ImageMapPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.imagemap");
        suite.addTestSuite(DynamicDriveToolTipTagFragmentGeneratorTests.class);
        suite.addTestSuite(ImageMapUtilitiesTests.class);
        suite.addTestSuite(OverLIBToolTipTagFragmentGeneratorTests.class);
        suite.addTestSuite(StandardToolTipTagFragmentGeneratorTests.class);
        suite.addTestSuite(StandardURLTagFragmentGeneratorTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public ImageMapPackageTests(String name) {
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

