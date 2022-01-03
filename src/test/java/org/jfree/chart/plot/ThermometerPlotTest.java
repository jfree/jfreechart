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
 * ------------------------
 * ThermometerPlotTest.java
 * ------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
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
import java.text.DecimalFormat;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.CloneUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        // padding
        p1.setPadding(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(p1, p2);
        p2.setPadding(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertEquals(p2, p1);

        // thermometerStroke
        BasicStroke s = new BasicStroke(1.23f);
        p1.setThermometerStroke(s);
        assertNotEquals(p1, p2);
        p2.setThermometerStroke(s);
        assertEquals(p2, p1);

        // thermometerPaint
        p1.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertNotEquals(p1, p2);
        p2.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.RED));
        assertEquals(p2, p1);

        // units
        p1.setUnits(ThermometerPlot.UNITS_KELVIN);
        assertNotEquals(p1, p2);
        p2.setUnits(ThermometerPlot.UNITS_KELVIN);
        assertEquals(p2, p1);

        // valueLocation
        p1.setValueLocation(ThermometerPlot.LEFT);
        assertNotEquals(p1, p2);
        p2.setValueLocation(ThermometerPlot.LEFT);
        assertEquals(p2, p1);

        // axisLocation
        p1.setAxisLocation(ThermometerPlot.RIGHT);
        assertNotEquals(p1, p2);
        p2.setAxisLocation(ThermometerPlot.RIGHT);
        assertEquals(p2, p1);

        // valueFont
        p1.setValueFont(new Font("Serif", Font.PLAIN, 9));
        assertNotEquals(p1, p2);
        p2.setValueFont(new Font("Serif", Font.PLAIN, 9));
        assertEquals(p2, p1);

        // valuePaint
        p1.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.WHITE));
        assertNotEquals(p1, p2);
        p2.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.RED,
                6.0f, 7.0f, Color.WHITE));
        assertEquals(p2, p1);

        // valueFormat
        p1.setValueFormat(new DecimalFormat("0.0000"));
        assertNotEquals(p1, p2);
        p2.setValueFormat(new DecimalFormat("0.0000"));
        assertEquals(p2, p1);

        // mercuryPaint
        p1.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertNotEquals(p1, p2);
        p2.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertEquals(p2, p1);

        p1.setSubrange(1, 1.0, 2.0);
        assertNotEquals(p1, p2);
        p2.setSubrange(1, 1.0, 2.0);
        assertEquals(p2, p1);

        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(p1, p2);
        p2.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(p2, p1);

        p1.setBulbRadius(9);
        assertNotEquals(p1, p2);
        p2.setBulbRadius(9);
        assertEquals(p2, p1);

        p1.setColumnRadius(8);
        assertNotEquals(p1, p2);
        p2.setColumnRadius(8);
        assertEquals(p2, p1);

        p1.setGap(7);
        assertNotEquals(p1, p2);
        p2.setGap(7);
        assertEquals(p2, p1);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        ThermometerPlot p1 = new ThermometerPlot();
        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.BLUE));
        ThermometerPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

}
