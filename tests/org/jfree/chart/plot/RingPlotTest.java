/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * -----------------
 * RingPlotTest.java
 * -----------------
 * (C) Copyright 2004-2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 09-Nov-2004 : Version 1 (DG);
 * 12-Oct-2006 : Updated testEquals() (DG);
 * 28-Feb-2014 : Add tests for new fields (DG);
 * 
 */

package org.jfree.chart.plot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Stroke;
import java.text.DecimalFormat;

import org.jfree.chart.TestUtilities;

/**
 * Tests for the {@link RingPlot} class.
 */
public class RingPlotTest {


    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {

        RingPlot plot1 = new RingPlot(null);
        RingPlot plot2 = new RingPlot(null);
        assertTrue(plot1.equals(plot2));
        assertTrue(plot2.equals(plot1));

        plot1.setCenterTextMode(CenterTextMode.FIXED);
        assertFalse(plot1.equals(plot2));
        plot2.setCenterTextMode(CenterTextMode.FIXED);
        assertTrue(plot1.equals(plot2));

        plot1.setCenterText("ABC");
        assertFalse(plot1.equals(plot2));
        plot2.setCenterText("ABC");
        assertTrue(plot1.equals(plot2));
        
        plot1.setCenterTextColor(Color.RED);
        assertFalse(plot1.equals(plot2));
        plot2.setCenterTextColor(Color.RED);
        assertTrue(plot1.equals(plot2));
        
        plot1.setCenterTextFont(new Font(Font.SERIF, Font.PLAIN, 7));
        assertFalse(plot1.equals(plot2));
        plot2.setCenterTextFont(new Font(Font.SERIF, Font.PLAIN, 7));
        assertTrue(plot1.equals(plot2));

        plot1.setCenterTextFormatter(new DecimalFormat("0.000"));
        assertFalse(plot1.equals(plot2));
        plot2.setCenterTextFormatter(new DecimalFormat("0.000"));
        assertTrue(plot1.equals(plot2));
        
        // separatorsVisible
        plot1.setSeparatorsVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setSeparatorsVisible(false);
        assertTrue(plot1.equals(plot2));

        // separatorStroke
        Stroke s = new BasicStroke(1.1f);
        plot1.setSeparatorStroke(s);
        assertFalse(plot1.equals(plot2));
        plot2.setSeparatorStroke(s);
        assertTrue(plot1.equals(plot2));

        // separatorPaint
        plot1.setSeparatorPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                2.0f, 1.0f, Color.blue));
        assertFalse(plot1.equals(plot2));
        plot2.setSeparatorPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                2.0f, 1.0f, Color.blue));
        assertTrue(plot1.equals(plot2));

        // innerSeparatorExtension
        plot1.setInnerSeparatorExtension(0.01);
        assertFalse(plot1.equals(plot2));
        plot2.setInnerSeparatorExtension(0.01);
        assertTrue(plot1.equals(plot2));

        // outerSeparatorExtension
        plot1.setOuterSeparatorExtension(0.02);
        assertFalse(plot1.equals(plot2));
        plot2.setOuterSeparatorExtension(0.02);
        assertTrue(plot1.equals(plot2));

        // sectionDepth
        plot1.setSectionDepth(0.12);
        assertFalse(plot1.equals(plot2));
        plot2.setSectionDepth(0.12);
        assertTrue(plot1.equals(plot2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        RingPlot p1 = new RingPlot(null);
        GradientPaint gp = new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.red);
        p1.setSeparatorPaint(gp);
        RingPlot p2 = (RingPlot) p1.clone();
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        RingPlot p1 = new RingPlot(null);
        GradientPaint gp = new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.red);
        p1.setSeparatorPaint(gp);
        RingPlot p2 = (RingPlot) TestUtilities.serialised(p1);
        assertEquals(p1, p2);
    }

}
