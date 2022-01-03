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
 * -------------
 * PlotTest.java
 * -------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
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
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

import org.jfree.chart.api.RectangleAlignment;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.plot.pie.PiePlot;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link Plot} class.
 */
public class PlotTest {

    /**
     * Check that the equals() method can distinguish all fields (note that
     * the dataset is NOT considered in the equals() method).
     */
    @Test
    public void testEquals() {
        PiePlot plot1 = new PiePlot();
        PiePlot plot2 = new PiePlot();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        // noDataMessage
        plot1.setNoDataMessage("No data XYZ");
        assertNotEquals(plot1, plot2);
        plot2.setNoDataMessage("No data XYZ");
        assertEquals(plot1, plot2);

        // noDataMessageFont
        plot1.setNoDataMessageFont(new Font("SansSerif", Font.PLAIN, 13));
        assertNotEquals(plot1, plot2);
        plot2.setNoDataMessageFont(new Font("SansSerif", Font.PLAIN, 13));
        assertEquals(plot1, plot2);

        // noDataMessagePaint
        plot1.setNoDataMessagePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setNoDataMessagePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        // insets
        plot1.setInsets(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(plot1, plot2);
        plot2.setInsets(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertEquals(plot1, plot2);

        // outlineVisible
        plot1.setOutlineVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setOutlineVisible(false);
        assertEquals(plot1, plot2);

        // outlineStroke
        BasicStroke s = new BasicStroke(1.23f);
        plot1.setOutlineStroke(s);
        assertNotEquals(plot1, plot2);
        plot2.setOutlineStroke(s);
        assertEquals(plot1, plot2);

        // outlinePaint
        plot1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.GREEN));
        assertNotEquals(plot1, plot2);
        plot2.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.GREEN));
        assertEquals(plot1, plot2);

        // backgroundPaint
        plot1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.CYAN,
                3.0f, 4.0f, Color.GREEN));
        assertNotEquals(plot1, plot2);
        plot2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.CYAN,
                3.0f, 4.0f, Color.GREEN));
        assertEquals(plot1, plot2);

//        // backgroundImage
//        plot1.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertFalse(plot1.equals(plot2));
//        plot2.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertTrue(plot1.equals(plot2));

        // backgroundImageAlignment
        plot1.setBackgroundImageAlignment(RectangleAlignment.BOTTOM_RIGHT);
        assertNotEquals(plot1, plot2);
        plot2.setBackgroundImageAlignment(RectangleAlignment.BOTTOM_RIGHT);
        assertEquals(plot1, plot2);

        // backgroundImageAlpha
        plot1.setBackgroundImageAlpha(0.77f);
        assertNotEquals(plot1, plot2);
        plot2.setBackgroundImageAlpha(0.77f);
        assertEquals(plot1, plot2);

        // foregroundAlpha
        plot1.setForegroundAlpha(0.99f);
        assertNotEquals(plot1, plot2);
        plot2.setForegroundAlpha(0.99f);
        assertEquals(plot1, plot2);

        // backgroundAlpha
        plot1.setBackgroundAlpha(0.99f);
        assertNotEquals(plot1, plot2);
        plot2.setBackgroundAlpha(0.99f);
        assertEquals(plot1, plot2);

        // drawingSupplier
        plot1.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[] {Color.BLUE}, new Paint[] {Color.RED},
                new Stroke[] {new BasicStroke(1.1f)},
                new Stroke[] {new BasicStroke(9.9f)},
                new Shape[] {new Rectangle(1, 2, 3, 4)}));
        assertNotEquals(plot1, plot2);
        plot2.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[] {Color.BLUE}, new Paint[] {Color.RED},
                new Stroke[] {new BasicStroke(1.1f)},
                new Stroke[] {new BasicStroke(9.9f)},
                new Shape[] {new Rectangle(1, 2, 3, 4)}));
        assertEquals(plot1, plot2);
    }

}
