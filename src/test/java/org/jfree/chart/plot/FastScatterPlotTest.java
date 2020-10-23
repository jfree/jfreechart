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
 * FastScatterPlotTest.java
 * ------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-Mar-2003 : Version 1 (DG);
 * 29-Jan-2009 : Updated testEquals() (DG);
 * 26-Mar-2009 : Updated testEquals() for new panning fields (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.junit.jupiter.api.Test;

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
        assertTrue(plot1.equals(plot2));
        assertTrue(plot2.equals(plot1));

        plot1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        plot1.setDomainGridlinesVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinesVisible(false);
        assertTrue(plot1.equals(plot2));

        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        Stroke s = new BasicStroke(1.5f);
        plot1.setDomainGridlineStroke(s);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlineStroke(s);
        assertTrue(plot1.equals(plot2));

        plot1.setRangeGridlinesVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinesVisible(false);
        assertTrue(plot1.equals(plot2));

        plot1.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.YELLOW));
        assertTrue(plot1.equals(plot2));

        Stroke s2 = new BasicStroke(1.5f);
        plot1.setRangeGridlineStroke(s2);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlineStroke(s2);
        assertTrue(plot1.equals(plot2));

        plot1.setDomainPannable(true);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainPannable(true);
        assertTrue(plot1.equals(plot2));

        plot1.setRangePannable(true);
        assertFalse(plot1.equals(plot2));
        plot2.setRangePannable(true);
        assertTrue(plot1.equals(plot2));

    }

    /**
     * Some tests for the data array equality in the equals() method.
     */
    @Test
    public void testEquals2() {
        FastScatterPlot plot1 = new FastScatterPlot();
        FastScatterPlot plot2 = new FastScatterPlot();
        assertTrue(plot1.equals(plot2));
        assertTrue(plot2.equals(plot1));

        float[][] a = new float[2][];
        float[][] b = new float[2][];
        plot1.setData(a);
        assertFalse(plot1.equals(plot2));
        plot2.setData(b);
        assertTrue(plot1.equals(plot2));

        a[0] = new float[6];
        assertFalse(plot1.equals(plot2));
        b[0] = new float[6];
        assertTrue(plot1.equals(plot2));

        a[0][0] = 1.0f;
        assertFalse(plot1.equals(plot2));
        b[0][0] = 1.0f;
        assertTrue(plot1.equals(plot2));

        a[0][1] = Float.NaN;
        assertFalse(plot1.equals(plot2));
        b[0][1] = Float.NaN;
        assertTrue(plot1.equals(plot2));

        a[0][2] = Float.POSITIVE_INFINITY;
        assertFalse(plot1.equals(plot2));
        b[0][2] = Float.POSITIVE_INFINITY;
        assertTrue(plot1.equals(plot2));

        a[0][3] = Float.NEGATIVE_INFINITY;
        assertFalse(plot1.equals(plot2));
        b[0][3] = Float.NEGATIVE_INFINITY;
        assertTrue(plot1.equals(plot2));
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        FastScatterPlot p1 = new FastScatterPlot();
        FastScatterPlot p2 = (FastScatterPlot) p1.clone();
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
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
        FastScatterPlot p2 = (FastScatterPlot) TestUtils.serialised(p1);
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
