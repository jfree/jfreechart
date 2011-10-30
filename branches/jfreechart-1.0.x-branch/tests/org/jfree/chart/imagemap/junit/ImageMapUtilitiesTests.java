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
 * ---------------------------
 * ImageMapUtilitiesTests.java
 * ---------------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.imagemap.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.imagemap.ImageMapUtilities;

/**
 * Tests for the {@link ImageMapUtilities} class.
 */
public class ImageMapUtilitiesTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ImageMapUtilitiesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ImageMapUtilitiesTests(String name) {
        super(name);
    }

    /**
     * Some checks for the htmlEscape() method.
     */
    public void testHTMLEscape() {
        assertEquals("", ImageMapUtilities.htmlEscape(""));
        assertEquals("abc", ImageMapUtilities.htmlEscape("abc"));
        assertEquals("&amp;", ImageMapUtilities.htmlEscape("&"));
        assertEquals("&quot;", ImageMapUtilities.htmlEscape("\""));
        assertEquals("&lt;", ImageMapUtilities.htmlEscape("<"));
        assertEquals("&gt;", ImageMapUtilities.htmlEscape(">"));
        assertEquals("&#39;", ImageMapUtilities.htmlEscape("\'"));
        assertEquals("&#092;abc", ImageMapUtilities.htmlEscape("\\abc"));
        assertEquals("abc\n", ImageMapUtilities.htmlEscape("abc\n"));
    }

    /**
     * Some checks for the javascriptEscape() method.
     */
    public void testJavascriptEscape() {
        assertEquals("", ImageMapUtilities.javascriptEscape(""));
        assertEquals("abc", ImageMapUtilities.javascriptEscape("abc"));
        assertEquals("\\\'", ImageMapUtilities.javascriptEscape("\'"));
        assertEquals("\\\"", ImageMapUtilities.javascriptEscape("\""));   
        assertEquals("\\\\", ImageMapUtilities.javascriptEscape("\\"));
    }

}
