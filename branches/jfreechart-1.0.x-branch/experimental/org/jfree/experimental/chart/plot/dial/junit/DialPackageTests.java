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
 * DialPackageTests.java
 * ---------------------
 * (C) Copyright 2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DialPackageTests.java,v 1.1.2.4 2007/04/30 21:34:50 mungady Exp $
 *
 * Changes:
 * --------
 * 03-Nov-2006 : Version 1 (DG);
 *
 */

package org.jfree.experimental.chart.plot.dial.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the 
 * <code>org.jfree.experimental.chart.plot.dial</code> package.  These tests 
 * can be run using JUnit (http://www.junit.org).
 */
public class DialPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite 
                = new TestSuite("org.jfree.experimental.chart.plot.dial");
        suite.addTestSuite(DialBackgroundTests.class);
        suite.addTestSuite(DialCapTests.class);
        suite.addTestSuite(DialPlotTests.class);
        suite.addTestSuite(DialPointerTests.class);
        suite.addTestSuite(DialTextAnnotationTests.class);
        suite.addTestSuite(DialValueIndicatorTests.class);
        suite.addTestSuite(SimpleDialFrameTests.class);
        suite.addTestSuite(StandardDialFrameTests.class);
        suite.addTestSuite(StandardDialRangeTests.class);
        suite.addTestSuite(StandardDialScaleTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public DialPackageTests(String name) {
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

