/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.urls;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.general.DefaultPieDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(g1, g2);

        g1 = new StandardPieURLGenerator("prefix", "category", "index");
        assertNotEquals(g1, g2);
        g2 = new StandardPieURLGenerator("prefix", "category", "index");
        assertEquals(g1, g2);

        g1 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertNotEquals(g1, g2);
        g2 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertEquals(g1, g2);

        g1 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertNotEquals(g1, g2);
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertEquals(g1, g2);

        g1 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertNotEquals(g1, g2);
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertEquals(g1, g2);

        g1 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertNotEquals(g1, g2);
        g2 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertEquals(g1, g2);
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
        StandardPieURLGenerator g2 = TestUtils.serialised(g1);
        assertEquals(g1, g2);
    }

    /**
     * Test that the generated URL is as expected.
     */
    @Test
    public void testURL() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Alpha '1'", 5.0);
        dataset.setValue("Beta", 5.5);
        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "chart.jsp", "category");
        String url = g1.generateURL(dataset, "Beta", 0);
        assertEquals("chart.jsp?category=Beta&amp;pieIndex=0", url);
        url = g1.generateURL(dataset, "Alpha '1'", 0);
        assertEquals("chart.jsp?category=Alpha+%271%27&amp;pieIndex=0", url);
    }

}
