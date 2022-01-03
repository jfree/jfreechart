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
 * FastScatterPlotTest.java
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
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link FastScatterPlot} class.
 */
public class FastScatterPlotTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {

        FastScatterPlot plot1 = new FastScatterPlot();
        FastScatterPlot plot2 = new FastScatterPlot();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        plot1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        plot1.setDomainGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinesVisible(false);
        assertEquals(plot1, plot2);

        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        Stroke s = new BasicStroke(1.5f);
        plot1.setDomainGridlineStroke(s);
        assertNotEquals(plot1, plot2);
        plot2.setDomainGridlineStroke(s);
        assertEquals(plot1, plot2);

        plot1.setRangeGridlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlinesVisible(false);
        assertEquals(plot1, plot2);

        plot1.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertEquals(plot1, plot2);

        Stroke s2 = new BasicStroke(1.5f);
        plot1.setRangeGridlineStroke(s2);
        assertNotEquals(plot1, plot2);
        plot2.setRangeGridlineStroke(s2);
        assertEquals(plot1, plot2);

        plot1.setDomainPannable(true);
        assertNotEquals(plot1, plot2);
        plot2.setDomainPannable(true);
        assertEquals(plot1, plot2);

        plot1.setRangePannable(true);
        assertNotEquals(plot1, plot2);
        plot2.setRangePannable(true);
        assertEquals(plot1, plot2);

    }

    /**
     * Some tests for the data array equality in the equals() method.
     */
    @Test
    public void testEquals2() {
        FastScatterPlot plot1 = new FastScatterPlot();
        FastScatterPlot plot2 = new FastScatterPlot();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        float[][] a = new float[2][];
        float[][] b = new float[2][];
        plot1.setData(a);
        assertNotEquals(plot1, plot2);
        plot2.setData(b);
        assertEquals(plot1, plot2);

        a[0] = new float[6];
        assertNotEquals(plot1, plot2);
        b[0] = new float[6];
        assertEquals(plot1, plot2);

        a[0][0] = 1.0f;
        assertNotEquals(plot1, plot2);
        b[0][0] = 1.0f;
        assertEquals(plot1, plot2);

        a[0][1] = Float.NaN;
        assertNotEquals(plot1, plot2);
        b[0][1] = Float.NaN;
        assertEquals(plot1, plot2);

        a[0][2] = Float.POSITIVE_INFINITY;
        assertNotEquals(plot1, plot2);
        b[0][2] = Float.POSITIVE_INFINITY;
        assertEquals(plot1, plot2);

        a[0][3] = Float.NEGATIVE_INFINITY;
        assertNotEquals(plot1, plot2);
        b[0][3] = Float.NEGATIVE_INFINITY;
        assertEquals(plot1, plot2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        FastScatterPlot p1 = new FastScatterPlot();
        FastScatterPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        float[][] data = createData();
        ValueAxis domainAxis = new NumberAxis("X");
        ValueAxis rangeAxis = new NumberAxis("Y");
        FastScatterPlot p1 = new FastScatterPlot(data, domainAxis, rangeAxis);
        FastScatterPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Draws the chart with a {@code null} info object to make sure that
     * no exceptions are thrown.
     */
    @Test
    public void testDrawWithNullInfo() {
        try {
            float[][] data = createData();

            ValueAxis domainAxis = new NumberAxis("X");
            ValueAxis rangeAxis = new NumberAxis("Y");
            FastScatterPlot plot = new FastScatterPlot(data, domainAxis,
                    rangeAxis);
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200,
                    null);
        }
        catch (NullPointerException e) {
            fail("No exception should be thrown.");
        }
    }

    /**
     * Populates the data array with random values.
     *
     * @return Random data.
     */
    private float[][] createData() {
        float[][] result = new float[2][1000];
        for (int i = 0; i < result[0].length; i++) {
            float x = (float) i + 100;
            result[0][i] = x;
            result[1][i] = 100 + (float) Math.random() * 1000;
        }
        return result;
    }

}
