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
 * ExtendedCategoryAxisTest.java
 * -----------------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Mar-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link ExtendedCategoryAxis} class.
 */
public class ExtendedCategoryAxisTest {


    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        ExtendedCategoryAxis a1 = new ExtendedCategoryAxis("Test");
        ExtendedCategoryAxis a2 = new ExtendedCategoryAxis("Test");
        assertTrue(a1.equals(a2));

        a1.addSubLabel("C1", "C1-sublabel");
        assertFalse(a1.equals(a2));
        a2.addSubLabel("C1", "C1-sublabel");
        assertTrue(a1.equals(a2));

        a1.setSubLabelFont(new Font("Dialog", Font.BOLD, 8));
        assertFalse(a1.equals(a2));
        a2.setSubLabelFont(new Font("Dialog", Font.BOLD, 8));
        assertTrue(a1.equals(a2));

        a1.setSubLabelPaint(Color.red);
        assertFalse(a1.equals(a2));
        a2.setSubLabelPaint(Color.red);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        ExtendedCategoryAxis a1 = new ExtendedCategoryAxis("Test");
        ExtendedCategoryAxis a2 = new ExtendedCategoryAxis("Test");
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
        ExtendedCategoryAxis a1 = new ExtendedCategoryAxis("Test");
        ExtendedCategoryAxis a2 = (ExtendedCategoryAxis) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));

        // check independence
        a1.addSubLabel("C1", "ABC");
        assertFalse(a1.equals(a2));
        a2.addSubLabel("C1", "ABC");
        assertTrue(a1.equals(a2));
    }

    /**
     * Confirm that cloning works.  This test customises the font and paint
     * per category label.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        ExtendedCategoryAxis a1 = new ExtendedCategoryAxis("Test");
        a1.setTickLabelFont("C1", new Font("Dialog", Font.PLAIN, 15));
        a1.setTickLabelPaint("C1", new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.white));
        ExtendedCategoryAxis a2 = (ExtendedCategoryAxis) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));

        // check that changing a tick label font in a1 doesn't change a2
        a1.setTickLabelFont("C1", null);
        assertFalse(a1.equals(a2));
        a2.setTickLabelFont("C1", null);
        assertTrue(a1.equals(a2));

        // check that changing a tick label paint in a1 doesn't change a2
        a1.setTickLabelPaint("C1", Color.yellow);
        assertFalse(a1.equals(a2));
        a2.setTickLabelPaint("C1", Color.yellow);
        assertTrue(a1.equals(a2));

        // check that changing a category label tooltip in a1 doesn't change a2
        a1.addCategoryLabelToolTip("C1", "XYZ");
        assertFalse(a1.equals(a2));
        a2.addCategoryLabelToolTip("C1", "XYZ");
        assertTrue(a1.equals(a2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ExtendedCategoryAxis a1 = new ExtendedCategoryAxis("Test");
        a1.setSubLabelPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.blue));
        ExtendedCategoryAxis a2 = (ExtendedCategoryAxis) 
                TestUtilities.serialised(a1);
        assertEquals(a1, a2);
    }

}
