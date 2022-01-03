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
 * -------------------------
 * XYLineAnnotationTest.java
 * -------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.PublicCloneable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYLineAnnotation} class.
 */
public class XYLineAnnotationTest {

    private static final double EPSILON = 0.000000001;

    @Test
    public void testConstructor() {
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        assertEquals(10.0, a1.getX1(), EPSILON);
        assertEquals(20.0, a1.getY1(), EPSILON);
        assertEquals(100.0, a1.getX2(), EPSILON);
        assertEquals(200.0, a1.getY2(), EPSILON);
        assertEquals(stroke, a1.getStroke());
        assertEquals(Color.BLUE, a1.getPaint());
    }
    
    @Test
    public void testConstructorExceptions() {
        Stroke stroke = new BasicStroke(2.0f);
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(Double.NaN, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(10.0, Double.NaN, 100.0, 200.0,
                stroke, Color.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, Double.NaN, 200.0,
                stroke, Color.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, Double.NaN,
                stroke, Color.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                null, Color.BLUE);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, null);
        });
    }
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        XYLineAnnotation a2 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        assertEquals(a1, a2);
        assertEquals(a2, a1);

        a1 = new XYLineAnnotation(11.0, 20.0, 100.0, 200.0, stroke, Color.BLUE);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 20.0, 100.0, 200.0, stroke, Color.BLUE);
        assertEquals(a1, a2);

        a1 = new XYLineAnnotation(11.0, 21.0, 100.0, 200.0, stroke, Color.BLUE);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 21.0, 100.0, 200.0, stroke, Color.BLUE);
        assertEquals(a1, a2);

        a1 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke, Color.BLUE);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke, Color.BLUE);
        assertEquals(a1, a2);

        a1 = new XYLineAnnotation(11.0, 21.0, 101.0, 201.0, stroke, Color.BLUE);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 21.0, 101.0, 201.0, stroke, Color.BLUE);
        assertEquals(a1, a2);

        Stroke stroke2 = new BasicStroke(0.99f);
        a1 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke2, Color.BLUE);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke2, Color.BLUE);
        assertEquals(a1, a2);

        GradientPaint g1 = new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE);
        GradientPaint g2 = new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE);
        a1 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke2, g1);
        assertNotEquals(a1, a2);
        a2 = new XYLineAnnotation(11.0, 21.0, 101.0, 200.0, stroke2, g2);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        XYLineAnnotation a2 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
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
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        XYLineAnnotation a2 = (XYLineAnnotation) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Stroke stroke = new BasicStroke(2.0f);
        XYLineAnnotation a1 = new XYLineAnnotation(10.0, 20.0, 100.0, 200.0,
                stroke, Color.BLUE);
        XYLineAnnotation a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}
