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
 * -------------------------------
 * CategoryLineAnnotationTest.java
 * -------------------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Jul-2005 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link CategoryLineAnnotation} class.
 */
public class CategoryLineAnnotationTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        BasicStroke s1 = new BasicStroke(1.0f);
        BasicStroke s2 = new BasicStroke(2.0f);
        CategoryLineAnnotation a1 = new CategoryLineAnnotation("Category 1",
                1.0, "Category 2", 2.0, Color.red, s1);
        CategoryLineAnnotation a2 = new CategoryLineAnnotation("Category 1",
                1.0, "Category 2", 2.0, Color.red, s1);
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        // category 1
        a1.setCategory1("Category A");
        assertFalse(a1.equals(a2));
        a2.setCategory1("Category A");
        assertTrue(a1.equals(a2));

        // value 1
        a1.setValue1(0.15);
        assertFalse(a1.equals(a2));
        a2.setValue1(0.15);
        assertTrue(a1.equals(a2));

        // category 2
        a1.setCategory2("Category B");
        assertFalse(a1.equals(a2));
        a2.setCategory2("Category B");
        assertTrue(a1.equals(a2));

        // value 2
        a1.setValue2(0.25);
        assertFalse(a1.equals(a2));
        a2.setValue2(0.25);
        assertTrue(a1.equals(a2));

        // paint
        a1.setPaint(Color.yellow);
        assertFalse(a1.equals(a2));
        a2.setPaint(Color.yellow);
        assertTrue(a1.equals(a2));

        // stroke
        a1.setStroke(s2);
        assertFalse(a1.equals(a2));
        a2.setStroke(s2);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation("Category 1", 
                1.0, "Category 2", 2.0, Color.red, new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = new CategoryLineAnnotation("Category 1", 
                1.0, "Category 2", 2.0, Color.red, new BasicStroke(1.0f));
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation("Category 1", 
                1.0, "Category 2", 2.0, Color.red, new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = (CategoryLineAnnotation) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation(
                "Category 1", 1.0, "Category 2", 2.0, Color.red,
                new BasicStroke(1.0f));
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation("Category 1", 
                1.0, "Category 2", 2.0, Color.red, new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = (CategoryLineAnnotation) TestUtilities.serialised(a1);
        assertEquals(a1, a2);
    }

}
