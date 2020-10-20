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
 * -------------------------------------
 * StandardCategoryURLGeneratorTest.java
 * -------------------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 13-Dec-2007 : Added testGenerateURL() and testEquals() (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.urls;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link StandardCategoryURLGenerator} class.
 */
public class StandardCategoryURLGeneratorTest {

    /**
     * Some tests for the generateURL() method.
     */
    @Test
    public void testGenerateURL() {
        StandardCategoryURLGenerator g1 = new StandardCategoryURLGenerator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, "R1", "C1");
        dataset.addValue(2.0, "R2", "C2");
        dataset.addValue(3.0, "R&", "C&");
        assertEquals("index.html?series=R1&amp;category=C1",
                g1.generateURL(dataset, 0, 0));
        assertEquals("index.html?series=R1&amp;category=C2",
                g1.generateURL(dataset, 0, 1));
        assertEquals("index.html?series=R2&amp;category=C2",
                g1.generateURL(dataset, 1, 1));
        assertEquals("index.html?series=R%26&amp;category=C%26",
                g1.generateURL(dataset, 2, 2));
    }

    /**
     * Checks that the class does not implement PublicCloneable (the generator
     * is immutable, so cloning is not necessary).
     */
    @Test
    public void testPublicCloneable() {
        StandardCategoryURLGenerator g1 = new StandardCategoryURLGenerator();
        assertFalse(g1 instanceof PublicCloneable);
    }

    /**
     * Some tests for the equals() method.
     */
    @Test
    public void testEquals() {
        StandardCategoryURLGenerator g1 = new StandardCategoryURLGenerator();
        StandardCategoryURLGenerator g2 = new StandardCategoryURLGenerator();
        assertTrue(g1.equals(g2));

        g1 = new StandardCategoryURLGenerator("index2.html?");
        assertFalse(g1.equals(g2));
        g2 = new StandardCategoryURLGenerator("index2.html?");
        assertTrue(g1.equals(g2));

        g1 = new StandardCategoryURLGenerator("index2.html?", "A", "B");
        assertFalse(g1.equals(g2));
        g2 = new StandardCategoryURLGenerator("index2.html?", "A", "B");
        assertTrue(g1.equals(g2));

        g1 = new StandardCategoryURLGenerator("index2.html?", "A", "C");
        assertFalse(g1.equals(g2));
        g2 = new StandardCategoryURLGenerator("index2.html?", "A", "C");
        assertTrue(g1.equals(g2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StandardCategoryURLGenerator g1 = new StandardCategoryURLGenerator(
                "index.html?");
        StandardCategoryURLGenerator g2 = (StandardCategoryURLGenerator) 
                TestUtils.serialised(g1);
        assertEquals(g1, g2);
    }

}
