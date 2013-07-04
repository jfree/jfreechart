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
 * ----------------------------
 * DataGeneralPackageTests.java
 * ----------------------------
 * (C) Copyright 2004-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Aug-2004 : Restructured org.jfree.data package (DG);
 * 18-Jan-2005 : Added main() method (DG);
 * 04-Feb-2009 : Added DefaultHeatMapDatasetTests (DG);
 *
 */

package org.jfree.data.general.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Some tests for the <code>org.jfree.data</code> package that can be run
 * using JUnit.  You can find more information about JUnit at
 * <a href="http://www.junit.org">http://www.junit.org</a>.
 */
public class DataGeneralPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.data.general");
        suite.addTestSuite(DatasetGroupTests.class);
        suite.addTestSuite(DatasetUtilitiesTests.class);
        suite.addTestSuite(DefaultHeatMapDatasetTests.class);
        suite.addTestSuite(DefaultKeyedValueDatasetTests.class);
        suite.addTestSuite(DefaultKeyedValuesDatasetTests.class);
        suite.addTestSuite(DefaultKeyedValues2DDatasetTests.class);
        suite.addTestSuite(DefaultPieDatasetTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the test suite name.
     */
    public DataGeneralPackageTests(String name) {
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
