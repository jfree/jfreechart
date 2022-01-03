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
 * LookupPaintScaleTest.java
 * -------------------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link LookupPaintScale} class.
 */
public class LookupPaintScaleTest {

    /**
     * A test for the equals() method.
     */
    @Test
    public void testEquals() {
        LookupPaintScale g1 = new LookupPaintScale();
        LookupPaintScale g2 = new LookupPaintScale();
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        g1 = new LookupPaintScale(1.0, 2.0, Color.RED);
        assertNotEquals(g1, g2);
        g2 = new LookupPaintScale(1.0, 2.0, Color.RED);
        assertEquals(g1, g2);

        g1.add(1.5, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));
        assertNotEquals(g1, g2);
        g2.add(1.5, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));
        assertEquals(g1, g2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LookupPaintScale g1 = new LookupPaintScale();
        LookupPaintScale g2 = CloneUtils.clone(g1);
        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);

        // check independence
        g1.add(0.5, Color.RED);
        assertNotEquals(g1, g2);
        g2.add(0.5, Color.RED);
        assertEquals(g1, g2);

        // try with gradient paint
        g1 = new LookupPaintScale(1.0, 2.0, new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.GREEN));
        g1.add(1.5, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));
        g2 = (LookupPaintScale) g1.clone();
        assertNotSame(g1, g2);
        assertSame(g1.getClass(), g2.getClass());
        assertEquals(g1, g2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        LookupPaintScale g1 = new LookupPaintScale();
        LookupPaintScale g2 = TestUtils.serialised(g1);
        assertEquals(g1, g2);

        g1 = new LookupPaintScale(1.0, 2.0, new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.YELLOW));
        g1.add(1.5, new GradientPaint(1.1f, 2.2f, Color.RED, 3.3f, 4.4f,
                Color.BLUE));
        g2 = TestUtils.serialised(g1);
        assertEquals(g1, g2);
    }

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the default constructor.
     */
    @Test
    public void testConstructor1() {
        LookupPaintScale s = new LookupPaintScale();
        assertEquals(0.0, s.getLowerBound(), EPSILON);
        assertEquals(1.0, s.getUpperBound(), EPSILON);
    }

    /**
     * Some checks for the other constructor.
     */
    @Test
    public void testConstructor2() {
        LookupPaintScale s = new LookupPaintScale(1.0, 2.0, Color.RED);
        assertEquals(1.0, s.getLowerBound(), EPSILON);
        assertEquals(2.0, s.getUpperBound(), EPSILON);
        assertEquals(Color.RED, s.getDefaultPaint());
    }

    /**
     * Some general checks for the lookup table.
     */
    @Test
    public void testGeneral() {

        LookupPaintScale s = new LookupPaintScale(0.0, 100.0, Color.BLACK);
        assertEquals(Color.BLACK, s.getPaint(-1.0));
        assertEquals(Color.BLACK, s.getPaint(0.0));
        assertEquals(Color.BLACK, s.getPaint(50.0));
        assertEquals(Color.BLACK, s.getPaint(100.0));
        assertEquals(Color.BLACK, s.getPaint(101.0));

        s.add(50.0, Color.BLUE);
        assertEquals(Color.BLACK, s.getPaint(-1.0));
        assertEquals(Color.BLACK, s.getPaint(0.0));
        assertEquals(Color.BLUE, s.getPaint(50.0));
        assertEquals(Color.BLUE, s.getPaint(100.0));
        assertEquals(Color.BLACK, s.getPaint(101.0));

        s.add(50.0, Color.RED);
        assertEquals(Color.BLACK, s.getPaint(-1.0));
        assertEquals(Color.BLACK, s.getPaint(0.0));
        assertEquals(Color.RED, s.getPaint(50.0));
        assertEquals(Color.RED, s.getPaint(100.0));
        assertEquals(Color.BLACK, s.getPaint(101.0));

        s.add(25.0, Color.GREEN);
        assertEquals(Color.BLACK, s.getPaint(-1.0));
        assertEquals(Color.BLACK, s.getPaint(0.0));
        assertEquals(Color.GREEN, s.getPaint(25.0));
        assertEquals(Color.RED, s.getPaint(50.0));
        assertEquals(Color.RED, s.getPaint(100.0));
        assertEquals(Color.BLACK, s.getPaint(101.0));

        s.add(75.0, Color.YELLOW);
        assertEquals(Color.BLACK, s.getPaint(-1.0));
        assertEquals(Color.BLACK, s.getPaint(0.0));
        assertEquals(Color.GREEN, s.getPaint(25.0));
        assertEquals(Color.RED, s.getPaint(50.0));
        assertEquals(Color.YELLOW, s.getPaint(75.0));
        assertEquals(Color.YELLOW, s.getPaint(100.0));
        assertEquals(Color.BLACK, s.getPaint(101.0));
    }

}
