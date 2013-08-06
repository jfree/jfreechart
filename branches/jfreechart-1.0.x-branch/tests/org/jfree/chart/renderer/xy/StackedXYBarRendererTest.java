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
 * -----------------------------
 * StackedXYBarRendererTest.java
 * -----------------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Sep-2004 : Version 1 (DG);
 * 06-Jan-2005 : Added test for auto range calculation (DG);
 * 06-Dec-2006 : Confirm serialization of GradientPaint (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtilities;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.xy.TableXYDataset;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link StackedXYBarRenderer} class.
 */
public class StackedXYBarRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        StackedXYBarRenderer r1 = new StackedXYBarRenderer();
        StackedXYBarRenderer r2 = new StackedXYBarRenderer();
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));

        r1.setRenderAsPercentages(true);
        assertFalse(r1.equals(r2));
        r2.setRenderAsPercentages(true);
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        StackedXYBarRenderer r1 = new StackedXYBarRenderer();
        StackedXYBarRenderer r2 = new StackedXYBarRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);

        r1.setRenderAsPercentages(true);
        h1 = r1.hashCode();
        h2 = r2.hashCode();
        assertFalse(h1 == h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StackedXYBarRenderer r1 = new StackedXYBarRenderer();
        StackedXYBarRenderer r2 = (StackedXYBarRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        StackedXYBarRenderer r1 = new StackedXYBarRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        StackedXYBarRenderer r1 = new StackedXYBarRenderer();
        r1.setSeriesPaint(0, new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.yellow));
        StackedXYBarRenderer r2 = (StackedXYBarRenderer) 
                TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * Check that the renderer is calculating the domain bounds correctly.
     */
    @Test
    public void testFindDomainBounds() {
        TableXYDataset dataset
                = RendererXYPackageUtils.createTestTableXYDataset();
        JFreeChart chart = ChartFactory.createStackedXYAreaChart(
                "Test Chart", "X", "Y", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(new StackedXYBarRenderer());
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setAutoRangeIncludesZero(false);
        Range bounds = domainAxis.getRange();
        assertFalse(bounds.contains(0.3));
        assertTrue(bounds.contains(0.5));
        assertTrue(bounds.contains(2.5));
        assertFalse(bounds.contains(2.8));
    }

    /**
     * Check that the renderer is calculating the range bounds correctly.
     */
    @Test
    public void testFindRangeBounds() {
        TableXYDataset dataset
                = RendererXYPackageUtils.createTestTableXYDataset();
        JFreeChart chart = ChartFactory.createStackedXYAreaChart(
                "Test Chart", "X", "Y", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setRenderer(new StackedXYBarRenderer());
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        Range bounds = rangeAxis.getRange();
        assertTrue(bounds.contains(6.0));
        assertTrue(bounds.contains(8.0));
    }

}
