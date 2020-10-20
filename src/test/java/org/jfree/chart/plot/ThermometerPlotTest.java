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
 * ------------------------
 * ThermometerPlotTest.java
 * ------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 30-Apr-2007 : Added new serialization test (DG);
 * 03-May-2007 : Added cloning test (DG);
 * 08-Oct-2007 : Updated testEquals() for new fields (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.RectangleInsets;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link ThermometerPlot} class.
 */
public class ThermometerPlotTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = new ThermometerPlot();
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        // padding
        p1.setPadding(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertFalse(p1.equals(p2));
        p2.setPadding(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertTrue(p2.equals(p1));

        // thermometerStroke
        BasicStroke s = new BasicStroke(1.23f);
        p1.setThermometerStroke(s);
        assertFalse(p1.equals(p2));
        p2.setThermometerStroke(s);
        assertTrue(p2.equals(p1));

        // thermometerPaint
        p1.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertFalse(p1.equals(p2));
        p2.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertTrue(p2.equals(p1));

        // units
        p1.setUnits(ThermometerPlot.UNITS_KELVIN);
        assertFalse(p1.equals(p2));
        p2.setUnits(ThermometerPlot.UNITS_KELVIN);
        assertTrue(p2.equals(p1));

        // valueLocation
        p1.setValueLocation(ThermometerPlot.LEFT);
        assertFalse(p1.equals(p2));
        p2.setValueLocation(ThermometerPlot.LEFT);
        assertTrue(p2.equals(p1));

        // axisLocation
        p1.setAxisLocation(ThermometerPlot.RIGHT);
        assertFalse(p1.equals(p2));
        p2.setAxisLocation(ThermometerPlot.RIGHT);
        assertTrue(p2.equals(p1));

        // valueFont
        p1.setValueFont(new Font("Serif", Font.PLAIN, 9));
        assertFalse(p1.equals(p2));
        p2.setValueFont(new Font("Serif", Font.PLAIN, 9));
        assertTrue(p2.equals(p1));

        // valuePaint
        p1.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.WHITE));
        assertFalse(p1.equals(p2));
        p2.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.WHITE));
        assertTrue(p2.equals(p1));

        // valueFormat
        p1.setValueFormat(new DecimalFormat("0.0000"));
        assertFalse(p1.equals(p2));
        p2.setValueFormat(new DecimalFormat("0.0000"));
        assertTrue(p2.equals(p1));

        // mercuryPaint
        p1.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertFalse(p1.equals(p2));
        p2.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertTrue(p2.equals(p1));

        p1.setSubrange(1, 1.0, 2.0);
        assertFalse(p1.equals(p2));
        p2.setSubrange(1, 1.0, 2.0);
        assertTrue(p2.equals(p1));

        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(p1.equals(p2));
        p2.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(p2.equals(p1));

        p1.setBulbRadius(9);
        assertFalse(p1.equals(p2));
        p2.setBulbRadius(9);
        assertTrue(p2.equals(p1));

        p1.setColumnRadius(8);
        assertFalse(p1.equals(p2));
        p2.setColumnRadius(8);
        assertTrue(p2.equals(p1));

        p1.setGap(7);
        assertFalse(p1.equals(p2));
        p2.setGap(7);
        assertTrue(p2.equals(p1));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = (ThermometerPlot) p1.clone();
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = (ThermometerPlot) TestUtils.serialised(p1);
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        ThermometerPlot p1 = new ThermometerPlot();
        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        ThermometerPlot p2 = (ThermometerPlot) TestUtils.serialised(p1);
        assertTrue(p1.equals(p2));
    }

}
