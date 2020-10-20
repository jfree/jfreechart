/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * ImageMapUtilsTest.java
 * ----------------------
 * (C) Copyright 2009-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.imagemap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ImageMapUtils} class.
 */
public class ImageMapUtilsTest {

    /**
     * Some checks for the htmlEscape() method.
     */
    @Test
    public void testHTMLEscape() {
        assertEquals("", ImageMapUtils.htmlEscape(""));
        assertEquals("abc", ImageMapUtils.htmlEscape("abc"));
        assertEquals("&amp;", ImageMapUtils.htmlEscape("&"));
        assertEquals("&quot;", ImageMapUtils.htmlEscape("\""));
        assertEquals("&lt;", ImageMapUtils.htmlEscape("<"));
        assertEquals("&gt;", ImageMapUtils.htmlEscape(">"));
        assertEquals("&#39;", ImageMapUtils.htmlEscape("\'"));
        assertEquals("&#092;abc", ImageMapUtils.htmlEscape("\\abc"));
        assertEquals("abc\n", ImageMapUtils.htmlEscape("abc\n"));
    }

    /**
     * Some checks for the javascriptEscape() method.
     */
    @Test
    public void testJavascriptEscape() {
        assertEquals("", ImageMapUtils.javascriptEscape(""));
        assertEquals("abc", ImageMapUtils.javascriptEscape("abc"));
        assertEquals("\\\'", ImageMapUtils.javascriptEscape("\'"));
        assertEquals("\\\"", ImageMapUtils.javascriptEscape("\""));   
        assertEquals("\\\\", ImageMapUtils.javascriptEscape("\\"));
    }

}
