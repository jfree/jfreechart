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
 * ------------------
 * CrosshairTest.java
 * ------------------
 * (C) Copyright 2009-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 09-Apr-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Font;
import java.awt.GradientPaint;
import java.text.NumberFormat;
import org.jfree.chart.TestUtilities;

import org.jfree.chart.labels.StandardCrosshairLabelGenerator;
import org.jfree.ui.RectangleAnchor;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link Crosshair} class.
 */
public class CrosshairTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        Crosshair c1 = new Crosshair(1.0, Color.blue, new BasicStroke(1.0f));
        Crosshair c2 = new Crosshair(1.0, Color.blue, new BasicStroke(1.0f));
        assertTrue(c1.equals(c1));
        assertTrue(c2.equals(c1));

        c1.setVisible(false);
        assertFalse(c1.equals(c2));
        c2.setVisible(false);
        assertTrue(c1.equals(c2));

        c1.setValue(2.0);
        assertFalse(c1.equals(c2));
        c2.setValue(2.0);
        assertTrue(c1.equals(c2));

        c1.setPaint(Color.red);
        assertFalse(c1.equals(c2));
        c2.setPaint(Color.red);
        assertTrue(c1.equals(c2));

        c1.setStroke(new BasicStroke(1.1f));
        assertFalse(c1.equals(c2));
        c2.setStroke(new BasicStroke(1.1f));
        assertTrue(c1.equals(c2));

        c1.setLabelVisible(true);
        assertFalse(c1.equals(c2));
        c2.setLabelVisible(true);
        assertTrue(c1.equals(c2));

        c1.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        assertFalse(c1.equals(c2));
        c2.setLabelAnchor(RectangleAnchor.TOP_LEFT);
        assertTrue(c1.equals(c2));

        c1.setLabelGenerator(new StandardCrosshairLabelGenerator("Value = {0}",
                NumberFormat.getNumberInstance()));
        assertFalse(c1.equals(c2));
        c2.setLabelGenerator(new StandardCrosshairLabelGenerator("Value = {0}",
                NumberFormat.getNumberInstance()));
        assertTrue(c1.equals(c2));

        c1.setLabelXOffset(11);
        assertFalse(c1.equals(c2));
        c2.setLabelXOffset(11);
        assertTrue(c1.equals(c2));

        c1.setLabelYOffset(22);
        assertFalse(c1.equals(c2));
        c2.setLabelYOffset(22);
        assertTrue(c1.equals(c2));

        c1.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertFalse(c1.equals(c2));
        c2.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertTrue(c1.equals(c2));

        c1.setLabelPaint(Color.red);
        assertFalse(c1.equals(c2));
        c2.setLabelPaint(Color.red);
        assertTrue(c1.equals(c2));

        c1.setLabelBackgroundPaint(Color.yellow);
        assertFalse(c1.equals(c2));
        c2.setLabelBackgroundPaint(Color.yellow);
        assertTrue(c1.equals(c2));

        c1.setLabelOutlineVisible(false);
        assertFalse(c1.equals(c2));
        c2.setLabelOutlineVisible(false);
        assertTrue(c1.equals(c2));

        c1.setLabelOutlineStroke(new BasicStroke(2.0f));
        assertFalse(c1.equals(c2));
        c2.setLabelOutlineStroke(new BasicStroke(2.0f));
        assertTrue(c1.equals(c2));

        c1.setLabelOutlinePaint(Color.darkGray);
        assertFalse(c1.equals(c2));
        c2.setLabelOutlinePaint(Color.darkGray);
        assertTrue(c1.equals(c2));

    }

    /**
     * Simple check that hashCode is implemented.
     */
    @Test
    public void testHashCode() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = new Crosshair(1.0);
        assertTrue(c1.equals(c2));
        assertTrue(c1.hashCode() == c2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Crosshair c1 = new Crosshair(1.0, new GradientPaint(1.0f, 2.0f,
                Color.red, 3.0f, 4.0f, Color.BLUE), new BasicStroke(1.0f));
        Crosshair c2 = (Crosshair) c1.clone();
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));
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
                Color.red, 3.0f, 4.0f, Color.BLUE), new BasicStroke(1.0f));
        Crosshair c2 = (Crosshair) TestUtilities.serialised(c1);
        assertEquals(c1, c2);
    }

}
