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
 * -------------------------
 * ThermometerPlotTests.java
 * -------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
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

package org.jfree.chart.plot.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.ui.RectangleInsets;

/**
 * Tests for the {@link ThermometerPlot} class.
 */
public class ThermometerPlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ThermometerPlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ThermometerPlotTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
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
        p1.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.red));
        assertFalse(p1.equals(p2));
        p2.setThermometerPaint(new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.red));
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
        p1.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.red,
                6.0f, 7.0f, Color.white));
        assertFalse(p1.equals(p2));
        p2.setValuePaint(new GradientPaint(4.0f, 5.0f, Color.red,
                6.0f, 7.0f, Color.white));
        assertTrue(p2.equals(p1));

        // valueFormat
        p1.setValueFormat(new DecimalFormat("0.0000"));
        assertFalse(p1.equals(p2));
        p2.setValueFormat(new DecimalFormat("0.0000"));
        assertTrue(p2.equals(p1));

        // mercuryPaint
        p1.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertFalse(p1.equals(p2));
        p2.setMercuryPaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertTrue(p2.equals(p1));

        // showValueLines
        p1.setShowValueLines(true);
        assertFalse(p1.equals(p2));
        p2.setShowValueLines(true);
        assertTrue(p2.equals(p1));

        p1.setSubrange(1, 1.0, 2.0);
        assertFalse(p1.equals(p2));
        p2.setSubrange(1, 1.0, 2.0);
        assertTrue(p2.equals(p1));

        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.yellow));
        assertFalse(p1.equals(p2));
        p2.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.yellow));
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
    public void testCloning() {
        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = null;
        try {
            p2 = (ThermometerPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        ThermometerPlot p1 = new ThermometerPlot();
        ThermometerPlot p2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (ThermometerPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(p1.equals(p2));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization2() {
        ThermometerPlot p1 = new ThermometerPlot();
        p1.setSubrangePaint(1, new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.blue));
        ThermometerPlot p2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (ThermometerPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(p1.equals(p2));
    }

    /**
     * Some checks for the setUnits() method.
     */
    public void testSetUnits() {
        ThermometerPlot p1 = new ThermometerPlot();
        assertEquals(ThermometerPlot.UNITS_CELCIUS, p1.getUnits());
        p1.setUnits("FAHRENHEIT");  // this doesn't work
        assertEquals(ThermometerPlot.UNITS_CELCIUS, p1.getUnits());
        p1.setUnits("\u00B0F");     // ...but this does!
        assertEquals(ThermometerPlot.UNITS_FAHRENHEIT, p1.getUnits());
    }
}
