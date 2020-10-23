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
 * -------------
 * PlotTest.java
 * -------------
 * (C) Copyright 2005-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 06-Jun-2005 : Version 1 (DG);
 * 30-Jun-2006 : Extended equals() test to cover new field (DG);
 * 11-May-2007 : Another new field in testEquals() (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;

import org.jfree.chart.ui.Align;
import org.jfree.chart.ui.RectangleInsets;
import org.junit.jupiter.api.Test;

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
        assertTrue(plot1.equals(plot2));
        assertTrue(plot2.equals(plot1));

        // noDataMessage
        plot1.setNoDataMessage("No data XYZ");
        assertFalse(plot1.equals(plot2));
        plot2.setNoDataMessage("No data XYZ");
        assertTrue(plot1.equals(plot2));

        // noDataMessageFont
        plot1.setNoDataMessageFont(new Font("SansSerif", Font.PLAIN, 13));
        assertFalse(plot1.equals(plot2));
        plot2.setNoDataMessageFont(new Font("SansSerif", Font.PLAIN, 13));
        assertTrue(plot1.equals(plot2));

        // noDataMessagePaint
        plot1.setNoDataMessagePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertFalse(plot1.equals(plot2));
        plot2.setNoDataMessagePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertTrue(plot1.equals(plot2));

        // insets
        plot1.setInsets(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertFalse(plot1.equals(plot2));
        plot2.setInsets(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertTrue(plot1.equals(plot2));

        // outlineVisible
        plot1.setOutlineVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setOutlineVisible(false);
        assertTrue(plot1.equals(plot2));

        // outlineStroke
        BasicStroke s = new BasicStroke(1.23f);
        plot1.setOutlineStroke(s);
        assertFalse(plot1.equals(plot2));
        plot2.setOutlineStroke(s);
        assertTrue(plot1.equals(plot2));

        // outlinePaint
        plot1.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.GREEN));
        assertFalse(plot1.equals(plot2));
        plot2.setOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.GREEN));
        assertTrue(plot1.equals(plot2));

        // backgroundPaint
        plot1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.GREEN));
        assertFalse(plot1.equals(plot2));
        plot2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.cyan,
                3.0f, 4.0f, Color.GREEN));
        assertTrue(plot1.equals(plot2));

//        // backgroundImage
//        plot1.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertFalse(plot1.equals(plot2));
//        plot2.setBackgroundImage(JFreeChart.INFO.getLogo());
//        assertTrue(plot1.equals(plot2));

        // backgroundImageAlignment
        plot1.setBackgroundImageAlignment(Align.BOTTOM_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setBackgroundImageAlignment(Align.BOTTOM_RIGHT);
        assertTrue(plot1.equals(plot2));

        // backgroundImageAlpha
        plot1.setBackgroundImageAlpha(0.77f);
        assertFalse(plot1.equals(plot2));
        plot2.setBackgroundImageAlpha(0.77f);
        assertTrue(plot1.equals(plot2));

        // foregroundAlpha
        plot1.setForegroundAlpha(0.99f);
        assertFalse(plot1.equals(plot2));
        plot2.setForegroundAlpha(0.99f);
        assertTrue(plot1.equals(plot2));

        // backgroundAlpha
        plot1.setBackgroundAlpha(0.99f);
        assertFalse(plot1.equals(plot2));
        plot2.setBackgroundAlpha(0.99f);
        assertTrue(plot1.equals(plot2));

        // drawingSupplier
        plot1.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[] {Color.BLUE}, new Paint[] {Color.RED},
                new Stroke[] {new BasicStroke(1.1f)},
                new Stroke[] {new BasicStroke(9.9f)},
                new Shape[] {new Rectangle(1, 2, 3, 4)}));
        assertFalse(plot1.equals(plot2));
        plot2.setDrawingSupplier(new DefaultDrawingSupplier(
                new Paint[] {Color.BLUE}, new Paint[] {Color.RED},
                new Stroke[] {new BasicStroke(1.1f)},
                new Stroke[] {new BasicStroke(9.9f)},
                new Shape[] {new Rectangle(1, 2, 3, 4)}));
        assertTrue(plot1.equals(plot2));
    }

}
