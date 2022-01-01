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
 * ---------------------
 * CategoryAxisTest.java
 * ---------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryAxis} class.
 */
public class CategoryAxisTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        CategoryAxis a1 = new CategoryAxis("Test");
        CategoryAxis a2 = new CategoryAxis("Test");
        assertEquals(a1, a2);

        // lowerMargin
        a1.setLowerMargin(0.15);
        assertNotEquals(a1, a2);
        a2.setLowerMargin(0.15);
        assertEquals(a1, a2);

        // upperMargin
        a1.setUpperMargin(0.15);
        assertNotEquals(a1, a2);
        a2.setUpperMargin(0.15);
        assertEquals(a1, a2);

        // categoryMargin
        a1.setCategoryMargin(0.15);
        assertNotEquals(a1, a2);
        a2.setCategoryMargin(0.15);
        assertEquals(a1, a2);

        // maxCategoryLabelWidthRatio
        a1.setMaximumCategoryLabelWidthRatio(0.98f);
        assertNotEquals(a1, a2);
        a2.setMaximumCategoryLabelWidthRatio(0.98f);
        assertEquals(a1, a2);

        // categoryLabelPositionOffset
        a1.setCategoryLabelPositionOffset(11);
        assertNotEquals(a1, a2);
        a2.setCategoryLabelPositionOffset(11);
        assertEquals(a1, a2);

        // categoryLabelPositions
        a1.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        assertNotEquals(a1, a2);
        a2.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        assertEquals(a1, a2);

        // categoryLabelToolTips
        a1.addCategoryLabelToolTip("Test", "Check");
        assertNotEquals(a1, a2);
        a2.addCategoryLabelToolTip("Test", "Check");
        assertEquals(a1, a2);

        // categoryLabelURLs
        a1.addCategoryLabelURL("Test", "http://www.jfree.org/");
        assertNotEquals(a1, a2);
        a2.addCategoryLabelURL("Test", "http://www.jfree.org/");
        assertEquals(a1, a2);

        // tickLabelFont
        a1.setTickLabelFont("C1", new Font("Dialog", Font.PLAIN, 21));
        assertNotEquals(a1, a2);
        a2.setTickLabelFont("C1", new Font("Dialog", Font.PLAIN, 21));
        assertEquals(a1, a2);

        // tickLabelPaint
        a1.setTickLabelPaint("C1", Color.RED);
        assertNotEquals(a1, a2);
        a2.setTickLabelPaint("C1", Color.RED);
        assertEquals(a1, a2);

        // tickLabelPaint2
        a1.setTickLabelPaint("C1", new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(a1, a2);
        a2.setTickLabelPaint("C1", new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(a1, a2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        CategoryAxis a1 = new CategoryAxis("Test");
        CategoryAxis a2 = new CategoryAxis("Test");
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryAxis a1 = new CategoryAxis("Test");
        CategoryAxis a2 = (CategoryAxis) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Confirm that cloning works.  This test customises the font and paint
     * per category label.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        CategoryAxis a1 = new CategoryAxis("Test");
        a1.setTickLabelFont("C1", new Font("Dialog", Font.PLAIN, 15));
        a1.setTickLabelPaint("C1", new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        CategoryAxis a2 = (CategoryAxis) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);

        // check that changing a tick label font in a1 doesn't change a2
        a1.setTickLabelFont("C1", null);
        assertNotEquals(a1, a2);
        a2.setTickLabelFont("C1", null);
        assertEquals(a1, a2);

        // check that changing a tick label paint in a1 doesn't change a2
        a1.setTickLabelPaint("C1", Color.YELLOW);
        assertNotEquals(a1, a2);
        a2.setTickLabelPaint("C1", Color.YELLOW);
        assertEquals(a1, a2);

        // check that changing a category label tooltip in a1 doesn't change a2
        a1.addCategoryLabelToolTip("C1", "XYZ");
        assertNotEquals(a1, a2);
        a2.addCategoryLabelToolTip("C1", "XYZ");
        assertEquals(a1, a2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryAxis a1 = new CategoryAxis("Test Axis");
        a1.setTickLabelPaint("C1", new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        CategoryAxis a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}
