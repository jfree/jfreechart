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
 * --------------------
 * CompassPlotTest.java
 * --------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 27-Mar-2003 : Version 1 (DG);
 * 20-Mar-2007 : Extended serialization tests (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;

import org.jfree.data.general.DefaultValueDataset;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link CompassPlot} class.
 */
public class CompassPlotTest {

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {
        CompassPlot plot1 = new CompassPlot();
        CompassPlot plot2 = new CompassPlot();
        assertTrue(plot1.equals(plot2));

        // labelType...
        plot1.setLabelType(CompassPlot.VALUE_LABELS);
        assertFalse(plot1.equals(plot2));
        plot2.setLabelType(CompassPlot.VALUE_LABELS);
        assertTrue(plot1.equals(plot2));

        // labelFont
        plot1.setLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertFalse(plot1.equals(plot2));
        plot2.setLabelFont(new Font("Serif", Font.PLAIN, 10));
        assertTrue(plot1.equals(plot2));

        // drawBorder
        plot1.setDrawBorder(true);
        assertFalse(plot1.equals(plot2));
        plot2.setDrawBorder(true);
        assertTrue(plot1.equals(plot2));

        // rosePaint
        plot1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        // roseCenterPaint
        plot1.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        // roseHighlightPaint
        plot1.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CompassPlot p1 = new CompassPlot(null);
        p1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.BLUE));
        p1.setRoseCenterPaint(new GradientPaint(4.0f, 3.0f, Color.RED, 2.0f,
                1.0f, Color.GREEN));
        p1.setRoseHighlightPaint(new GradientPaint(4.0f, 3.0f, Color.RED, 2.0f,
                1.0f, Color.GREEN));
        CompassPlot p2 = (CompassPlot) TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CompassPlot p1 = new CompassPlot(new DefaultValueDataset(15.0));
        CompassPlot p2 = (CompassPlot) p1.clone();
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

}
