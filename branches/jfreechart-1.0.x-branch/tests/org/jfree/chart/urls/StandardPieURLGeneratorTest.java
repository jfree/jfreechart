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
 * --------------------------------
 * StandardPieURLGeneratorTest.java
 * --------------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Mar-2003 : Version 1 (DG);
 * 06-Jan-2003 : Added a test for URL generation (DG);
 * 24-Nov-2006 : New equals() test (DG);
 * 17-Apr-2007 : Added additional check to testURL() (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.urls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link StandardPieURLGenerator} class.
 */
public class StandardPieURLGeneratorTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardPieURLGenerator g1 = new StandardPieURLGenerator();
        StandardPieURLGenerator g2 = new StandardPieURLGenerator();
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix", "category", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix", "category", "index");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertTrue(g1.equals(g2));
    }

    /**
     * Checks that the class does not implement PublicCloneable (the generator
     * is immutable).
     */
    @Test
    public void testPublicCloneable() {
        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "index.html?", "cat");
        assertFalse(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "index.html?", "cat");
        StandardPieURLGenerator g2 = (StandardPieURLGenerator) 
                TestUtilities.serialised(g1);
        assertEquals(g1, g2);
    }

    /**
     * Test that the generated URL is as expected.
     */
    @Test
    public void testURL() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Alpha '1'", new Double(5.0));
        dataset.setValue("Beta", new Double(5.5));
        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "chart.jsp", "category");
        String url = g1.generateURL(dataset, "Beta", 0);
        assertEquals("chart.jsp?category=Beta&amp;pieIndex=0", url);
        url = g1.generateURL(dataset, "Alpha '1'", 0);
        assertEquals("chart.jsp?category=Alpha+%271%27&amp;pieIndex=0", url);
    }

}
