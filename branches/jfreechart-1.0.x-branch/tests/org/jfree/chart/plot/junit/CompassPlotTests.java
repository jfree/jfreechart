/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * CompassPlotTests.java
 * ---------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
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

package org.jfree.chart.plot.junit;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.plot.CompassPlot;
import org.jfree.data.general.DefaultValueDataset;

/**
 * Tests for the {@link CompassPlot} class.
 */
public class CompassPlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CompassPlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CompassPlotTests(String name) {
        super(name);
    }

    /**
     * Test the equals() method.
     */
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
        plot1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));

        // roseCenterPaint
        plot1.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setRoseCenterPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));

        // roseHighlightPaint
        plot1.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setRoseHighlightPaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        CompassPlot p1 = new CompassPlot(null);
        p1.setRosePaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.blue));
        p1.setRoseCenterPaint(new GradientPaint(4.0f, 3.0f, Color.red, 2.0f,
                1.0f, Color.green));
        p1.setRoseHighlightPaint(new GradientPaint(4.0f, 3.0f, Color.red, 2.0f,
                1.0f, Color.green));
        CompassPlot p2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (CompassPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, p2);

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        CompassPlot p1 = new CompassPlot(new DefaultValueDataset(15.0));
        CompassPlot p2 = null;
        try {
            p2 = (CompassPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

}
