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
 * RendererPackageTests.java
 * -------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 21-Mar-2003 : Version 1 (DG);
 * 22-Oct-2003 : Added BoxAndWhiskerRendererTests (DG);
 * 04-Aug-2006 : Added DefaultPolarItemRendererTests (DG);
 * 31-Jan-2007 : Added GrayPaintScaleTests and LookupPaintScaleTests (DG);
 * 21-Nov-2007 : Added OutlierTests and missing RendererUtilitiesTests (DG);
 *
 */

package org.jfree.chart.renderer.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the org.jfree.chart.renderer package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class RendererPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.renderer");
        suite.addTestSuite(AbstractRendererTests.class);
        suite.addTestSuite(AreaRendererEndTypeTests.class);
        suite.addTestSuite(DefaultPolarItemRendererTests.class);
        suite.addTestSuite(GrayPaintScaleTests.class);
        suite.addTestSuite(LookupPaintScaleTests.class);
        suite.addTestSuite(OutlierTests.class);
        suite.addTestSuite(RendererUtilitiesTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public RendererPackageTests(String name) {
        super(name);
    }

}
