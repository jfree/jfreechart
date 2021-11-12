/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by David Gilbert and Contributors.
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
 * (C) Copyright 2003-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import org.junit.jupiter.api.Test;

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
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(XYLineAnnotation.class)
            .withRedefinedSuperclass() // superclass also defines equals/hashCode
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
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
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
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
