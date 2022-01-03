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
 * ------------------
 * CrosshairTest.java
 * ------------------
 * (C) Copyright 2009-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.GradientPaint;
import java.text.NumberFormat;
import org.jfree.chart.TestUtils;

import org.jfree.chart.labels.StandardCrosshairLabelGenerator;
import org.jfree.chart.api.RectangleAnchor;
import org.jfree.chart.api.PublicCloneable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Crosshair} class.
 */
public class CrosshairTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        Crosshair c1 = new Crosshair(1.0, Color.BLUE, new BasicStroke(1.0f));
        Crosshair c2 = new Crosshair(1.0, Color.BLUE, new BasicStroke(1.0f));
        assertEquals(c1, c1);
        assertEquals(c2, c1);

        c1.setVisible(false);
        assertNotEquals(c1, c2);
        c2.setVisible(false);
        assertEquals(c1, c2);

        c1.setValue(2.0);
        assertNotEquals(c1, c2);
        c2.setValue(2.0);
        assertEquals(c1, c2);

        c1.setPaint(Color.RED);
        assertNotEquals(c1, c2);
        c2.setPaint(Color.RED);
        assertEquals(c1, c2);

        c1.setStroke(new BasicStroke(1.1f));
        assertNotEquals(c1, c2);
        c2.setStroke(new BasicStroke(1.1f));
        assertEquals(c1, c2);

        c1.setLabelVisible(true);
        assertNotEquals(c1, c2);
        c2.setLabelVisible(true);
        assertEquals(c1, c2);

        c1.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        assertNotEquals(c1, c2);
        c2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        assertEquals(c1, c2);

        c1.setLabelGenerator(new StandardCrosshairLabelGenerator("Value = {0}",
                NumberFormat.getNumberInstance()));
        assertNotEquals(c1, c2);
        c2.setLabelGenerator(new StandardCrosshairLabelGenerator("Value = {0}",
                NumberFormat.getNumberInstance()));
        assertEquals(c1, c2);

        c1.setLabelXOffset(11);
        assertNotEquals(c1, c2);
        c2.setLabelXOffset(11);
        assertEquals(c1, c2);

        c1.setLabelYOffset(22);
        assertNotEquals(c1, c2);
        c2.setLabelYOffset(22);
        assertEquals(c1, c2);

        c1.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertNotEquals(c1, c2);
        c2.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertEquals(c1, c2);

        c1.setLabelPaint(Color.RED);
        assertNotEquals(c1, c2);
        c2.setLabelPaint(Color.RED);
        assertEquals(c1, c2);

        c1.setLabelBackgroundPaint(Color.YELLOW);
        assertNotEquals(c1, c2);
        c2.setLabelBackgroundPaint(Color.YELLOW);
        assertEquals(c1, c2);

        c1.setLabelOutlineVisible(false);
        assertNotEquals(c1, c2);
        c2.setLabelOutlineVisible(false);
        assertEquals(c1, c2);

        c1.setLabelOutlineStroke(new BasicStroke(2.0f));
        assertNotEquals(c1, c2);
        c2.setLabelOutlineStroke(new BasicStroke(2.0f));
        assertEquals(c1, c2);

        c1.setLabelOutlinePaint(Color.darkGray);
        assertNotEquals(c1, c2);
        c2.setLabelOutlinePaint(Color.darkGray);
        assertEquals(c1, c2);

    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = new Crosshair(1.0);
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Crosshair c1 = new Crosshair(1.0, new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE), new BasicStroke(1.0f));
        Crosshair c2 = (Crosshair) c1.clone();
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        Crosshair c1 = new Crosshair(1.0);
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Crosshair c1 = new Crosshair(1.0, new GradientPaint(1.0f, 2.0f,
                Color.RED, 3.0f, 4.0f, Color.BLUE), new BasicStroke(1.0f));
        Crosshair c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

}
