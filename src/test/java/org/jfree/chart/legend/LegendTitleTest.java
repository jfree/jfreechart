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
 * --------------------
 * LegendTitleTest.java
 * --------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.legend;

import org.jfree.chart.legend.LegendTitle;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.TestUtils;

import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.api.RectangleAnchor;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.SortOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link LegendTitle} class.
 */
public class LegendTitleTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        XYPlot<String> plot1 = new XYPlot<>();
        LegendTitle t1 = new LegendTitle(plot1);
        LegendTitle t2 = new LegendTitle(plot1);
        assertEquals(t1, t2);

        t1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 
                4.0f, Color.YELLOW));
        assertNotEquals(t1, t2);
        t2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 
                4.0f, Color.YELLOW));
        assertEquals(t1, t2);

        t1.setLegendItemGraphicEdge(RectangleEdge.BOTTOM);
        assertNotEquals(t1, t2);
        t2.setLegendItemGraphicEdge(RectangleEdge.BOTTOM);
        assertEquals(t1, t2);

        t1.setLegendItemGraphicAnchor(RectangleAnchor.BOTTOM_LEFT);
        assertNotEquals(t1, t2);
        t2.setLegendItemGraphicAnchor(RectangleAnchor.BOTTOM_LEFT);
        assertEquals(t1, t2);

        t1.setLegendItemGraphicLocation(RectangleAnchor.TOP_LEFT);
        assertNotEquals(t1, t2);
        t2.setLegendItemGraphicLocation(RectangleAnchor.TOP_LEFT);
        assertEquals(t1, t2);

        t1.setItemFont(new Font("Dialog", Font.PLAIN, 19));
        assertNotEquals(t1, t2);
        t2.setItemFont(new Font("Dialog", Font.PLAIN, 19));
        assertEquals(t1, t2);

        t1.setSortOrder(SortOrder.DESCENDING);
        assertNotEquals(t1, t2);
        t2.setSortOrder(SortOrder.DESCENDING);
        assertEquals(t1, t2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        XYPlot<String> plot1 = new XYPlot<>();
        LegendTitle t1 = new LegendTitle(plot1);
        LegendTitle t2 = new LegendTitle(plot1);
        assertEquals(t1, t2);
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYPlot<String> plot = new XYPlot<>();
        Rectangle2D bounds1 = new Rectangle2D.Double(10.0, 20.0, 30.0, 40.0);
        LegendTitle t1 = new LegendTitle(plot);
        t1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.YELLOW));
        t1.setBounds(bounds1);
        LegendTitle t2 = CloneUtils.clone(t1);
        assertNotSame(t1, t2);
        assertSame(t1.getClass(), t2.getClass());
        assertEquals(t1, t2);

        // check independence
        bounds1.setFrame(40.0, 30.0, 20.0, 10.0);
        assertNotEquals(t1, t2);
        t2.setBounds(new Rectangle2D.Double(40.0, 30.0, 20.0, 10.0));
        assertEquals(t1, t2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYPlot<String> plot = new XYPlot<>();
        LegendTitle t1 = new LegendTitle(plot);
        LegendTitle t2 = TestUtils.serialised(t1);
        assertEquals(t1, t2);
        assertEquals(t2.getSources()[0], plot);
    }
}
