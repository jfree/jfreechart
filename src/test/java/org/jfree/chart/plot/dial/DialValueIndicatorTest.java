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
 * ---------------------------
 * DialValueIndicatorTest.java
 * ---------------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.RectangleAnchor;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.text.TextAnchor;
import org.jfree.chart.internal.CloneUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DialValueIndicator} class.
 */
public class DialValueIndicatorTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DialValueIndicator i1 = new DialValueIndicator(0);
        DialValueIndicator i2 = new DialValueIndicator(0);
        assertEquals(i1, i2);

        // dataset index
        i1.setDatasetIndex(99);
        assertNotEquals(i1, i2);
        i2.setDatasetIndex(99);
        assertEquals(i1, i2);

        // angle
        i1.setAngle(43);
        assertNotEquals(i1, i2);
        i2.setAngle(43);
        assertEquals(i1, i2);

        // radius
        i1.setRadius(0.77);
        assertNotEquals(i1, i2);
        i2.setRadius(0.77);
        assertEquals(i1, i2);

        // frameAnchor
        i1.setFrameAnchor(RectangleAnchor.TOP_LEFT);
        assertNotEquals(i1, i2);
        i2.setFrameAnchor(RectangleAnchor.TOP_LEFT);
        assertEquals(i1, i2);

        // templateValue
        i1.setTemplateValue(1.23);
        assertNotEquals(i1, i2);
        i2.setTemplateValue(1.23);
        assertEquals(i1, i2);

        // font
        i1.setFont(new Font("Dialog", Font.PLAIN, 7));
        assertNotEquals(i1, i2);
        i2.setFont(new Font("Dialog", Font.PLAIN, 7));
        assertEquals(i1, i2);

        // paint
        i1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.GREEN));
        assertNotEquals(i1, i2);
        i2.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.GREEN));
        assertEquals(i1, i2);

        // backgroundPaint
        i1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.GREEN));
        assertNotEquals(i1, i2);
        i2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.GREEN));
        assertEquals(i1, i2);

        // outlineStroke
        i1.setOutlineStroke(new BasicStroke(1.1f));
        assertNotEquals(i1, i2);
        i2.setOutlineStroke(new BasicStroke(1.1f));
        assertEquals(i1, i2);

        // outlinePaint
        i1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.GREEN));
        assertNotEquals(i1, i2);
        i2.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.GREEN));
        assertEquals(i1, i2);

        // insets
        i1.setInsets(new RectangleInsets(1, 2, 3, 4));
        assertNotEquals(i1, i2);
        i2.setInsets(new RectangleInsets(1, 2, 3, 4));
        assertEquals(i1, i2);

        // valueAnchor
        i1.setValueAnchor(RectangleAnchor.BOTTOM_LEFT);
        assertNotEquals(i1, i2);
        i2.setValueAnchor(RectangleAnchor.BOTTOM_LEFT);
        assertEquals(i1, i2);

        // textAnchor
        i1.setTextAnchor(TextAnchor.TOP_LEFT);
        assertNotEquals(i1, i2);
        i2.setTextAnchor(TextAnchor.TOP_LEFT);
        assertEquals(i1, i2);

        // check an inherited attribute
        i1.setVisible(false);
        assertNotEquals(i1, i2);
        i2.setVisible(false);
        assertEquals(i1, i2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DialValueIndicator i1 = new DialValueIndicator(0);
        DialValueIndicator i2 = new DialValueIndicator(0);
        assertEquals(i1, i2);
        int h1 = i1.hashCode();
        int h2 = i2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        // test a default instance
        DialValueIndicator i1 = new DialValueIndicator(0);
        DialValueIndicator i2 = CloneUtils.clone(i1);
        assertNotSame(i1, i2);
        assertSame(i1.getClass(), i2.getClass());
        assertEquals(i1, i2);

        // check that the listener lists are independent
        MyDialLayerChangeListener l1 = new MyDialLayerChangeListener();
        i1.addChangeListener(l1);
        assertTrue(i1.hasListener(l1));
        assertFalse(i2.hasListener(l1));
    }


    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DialValueIndicator i1 = new DialValueIndicator(0);
        DialValueIndicator i2 = TestUtils.serialised(i1);
        assertEquals(i1, i2);
    }

}
