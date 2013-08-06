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
 * -----------------------------
 * CustomXYURLGeneratorTest.java
 * -----------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Mar-2003 : Version 1 (DG);
 * 11-Apr-2008 : Added testCloning() and testEquals() (DG);
 * 21-Apr-2008 : Enhanced testCloning() (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.urls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link CustomXYURLGenerator} class.
 */
public class CustomXYURLGeneratorTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        CustomXYURLGenerator g1 = new CustomXYURLGenerator();
        CustomXYURLGenerator g2 = new CustomXYURLGenerator();
        assertTrue(g1.equals(g2));
        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");
        g1.addURLSeries(u1);
        assertFalse(g1.equals(g2));
        List u2 = new java.util.ArrayList();
        u2.add("URL A1");
        u2.add("URL A2");
        u2.add("URL A3");
        g2.addURLSeries(u2);
        assertTrue(g1.equals(g2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CustomXYURLGenerator g1 = new CustomXYURLGenerator();
        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");
        g1.addURLSeries(u1);
        CustomXYURLGenerator g2 = (CustomXYURLGenerator) g1.clone();
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        List u2 = new java.util.ArrayList();
        u2.add("URL XXX");
        g1.addURLSeries(u2);
        assertFalse(g1.equals(g2));
        g2.addURLSeries(new java.util.ArrayList(u2));
        assertTrue(g1.equals(g2));
    }

    /**
     * Checks that the class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        CustomXYURLGenerator g1 = new CustomXYURLGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");

        List u2 = new java.util.ArrayList();
        u2.add("URL B1");
        u2.add("URL B2");
        u2.add("URL B3");

        CustomXYURLGenerator g1 = new CustomXYURLGenerator();
        g1.addURLSeries(u1);
        g1.addURLSeries(u2);
        CustomXYURLGenerator g2 = (CustomXYURLGenerator) 
                TestUtilities.serialised(g1);
        assertEquals(g1, g2);
    }

    /**
     * Some checks for the addURLSeries() method.
     */
    @Test
    public void testAddURLSeries() {
        CustomXYURLGenerator g1 = new CustomXYURLGenerator();
        // you can add a null list - it would have been better if this
        // required EMPTY_LIST
        g1.addURLSeries(null);
        assertEquals(1, g1.getListCount());
        assertEquals(0, g1.getURLCount(0));

        List list1 = new java.util.ArrayList();
        list1.add("URL1");
        g1.addURLSeries(list1);
        assertEquals(2, g1.getListCount());
        assertEquals(0, g1.getURLCount(0));
        assertEquals(1, g1.getURLCount(1));
        assertEquals("URL1", g1.getURL(1, 0));

        // if we modify the original list, it's best if the URL generator is
        // not affected
        list1.clear();
        assertEquals("URL1", g1.getURL(1, 0));
    }

}
