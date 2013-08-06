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
 * ------------------------------
 * CustomPieURLGeneratorTest.java
 * ------------------------------
 * (C) Copyright 2008-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 11-Apr-2008 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.urls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link CustomPieURLGenerator} class.
 */
public class CustomPieURLGeneratorTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        CustomPieURLGenerator g1 = new CustomPieURLGenerator();
        CustomPieURLGenerator g2 = new CustomPieURLGenerator();
        assertTrue(g1.equals(g2));

        Map m1 = new HashMap();
        m1.put("A", "http://www.jfree.org/");
        g1.addURLs(m1);
        assertFalse(g1.equals(g2));
        g2.addURLs(m1);
        assertTrue(g1.equals(g2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CustomPieURLGenerator g1 = new CustomPieURLGenerator();
        Map m1 = new HashMap();
        m1.put("A", "http://www.jfree.org/");
        g1.addURLs(m1);
        CustomPieURLGenerator g2 = (CustomPieURLGenerator) g1.clone();
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        Map m2 = new HashMap();
        m2.put("B", "XYZ");
        g1.addURLs(m2);
        assertFalse(g1.equals(g2));
    }

    /**
     * Checks that the class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        CustomPieURLGenerator g1 = new CustomPieURLGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CustomPieURLGenerator g1 = new CustomPieURLGenerator();
        Map m1 = new HashMap();
        m1.put("A", "http://www.jfree.org/");
        g1.addURLs(m1);
        CustomPieURLGenerator g2 = (CustomPieURLGenerator) 
                TestUtilities.serialised(g1);
        assertEquals(g1, g2);
    }

}
