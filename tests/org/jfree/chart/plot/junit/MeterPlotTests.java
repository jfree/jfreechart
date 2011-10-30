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
 * -------------------
 * MeterPlotTests.java
 * -------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 27-Mar-2003 : Version 1 (DG);
 * 12-May-2004 : Updated testEquals() (DG);
 * 29-Nov-2007 : Updated testEquals() and testSerialization1() for
 *               dialOutlinePaint (DG)
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
import java.text.DecimalFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.plot.DialShape;
import org.jfree.chart.plot.MeterInterval;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;

/**
 * Tests for the {@link MeterPlot} class.
 */
public class MeterPlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MeterPlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MeterPlotTests(String name) {
        super(name);
    }

    /**
     * Test the equals method to ensure that it can distinguish the required
     * fields.  Note that the dataset is NOT considered in the equals test.
     */
    public void testEquals() {
        MeterPlot plot1 = new MeterPlot();
        MeterPlot plot2 = new MeterPlot();
        assertTrue(plot1.equals(plot2));

        // units
        plot1.setUnits("mph");
        assertFalse(plot1.equals(plot2));
        plot2.setUnits("mph");
        assertTrue(plot1.equals(plot2));

        // range
        plot1.setRange(new Range(50.0, 70.0));
        assertFalse(plot1.equals(plot2));
        plot2.setRange(new Range(50.0, 70.0));
        assertTrue(plot1.equals(plot2));

        // interval
        plot1.addInterval(new MeterInterval("Normal", new Range(55.0, 60.0)));
        assertFalse(plot1.equals(plot2));
        plot2.addInterval(new MeterInterval("Normal", new Range(55.0, 60.0)));
        assertTrue(plot1.equals(plot2));

        // dial outline paint
        plot1.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        assertFalse(plot1.equals(plot2));
        plot2.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        assertTrue(plot1.equals(plot2));

        // dial shape
        plot1.setDialShape(DialShape.CHORD);
        assertFalse(plot1.equals(plot2));
        plot2.setDialShape(DialShape.CHORD);
        assertTrue(plot1.equals(plot2));

        // dial background paint
        plot1.setDialBackgroundPaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertFalse(plot1.equals(plot2));
        plot2.setDialBackgroundPaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertTrue(plot1.equals(plot2));

        // dial outline paint
        plot1.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.red));
        assertFalse(plot1.equals(plot2));
        plot2.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.red));
        assertTrue(plot1.equals(plot2));

        // needle paint
        plot1.setNeedlePaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertFalse(plot1.equals(plot2));
        plot2.setNeedlePaint(new GradientPaint(9.0f, 8.0f, Color.red,
                7.0f, 6.0f, Color.blue));
        assertTrue(plot1.equals(plot2));

        // value font
        plot1.setValueFont(new Font("Serif", Font.PLAIN, 6));
        assertFalse(plot1.equals(plot2));
        plot2.setValueFont(new Font("Serif", Font.PLAIN, 6));
        assertTrue(plot1.equals(plot2));

        // value paint
        plot1.setValuePaint(new GradientPaint(1.0f, 2.0f, Color.black,
                3.0f, 4.0f, Color.white));
        assertFalse(plot1.equals(plot2));
        plot2.setValuePaint(new GradientPaint(1.0f, 2.0f, Color.black,
                3.0f, 4.0f, Color.white));
        assertTrue(plot1.equals(plot2));

        // tick labels visible
        plot1.setTickLabelsVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setTickLabelsVisible(false);
        assertTrue(plot1.equals(plot2));

        // tick label font
        plot1.setTickLabelFont(new Font("Serif", Font.PLAIN, 6));
        assertFalse(plot1.equals(plot2));
        plot2.setTickLabelFont(new Font("Serif", Font.PLAIN, 6));
        assertTrue(plot1.equals(plot2));

        // tick label paint
        plot1.setTickLabelPaint(Color.red);
        assertFalse(plot1.equals(plot2));
        plot2.setTickLabelPaint(Color.red);
        assertTrue(plot1.equals(plot2));

        // tick label format
        plot1.setTickLabelFormat(new DecimalFormat("0"));
        assertFalse(plot1.equals(plot2));
        plot2.setTickLabelFormat(new DecimalFormat("0"));
        assertTrue(plot1.equals(plot2));

        // tick paint
        plot1.setTickPaint(Color.green);
        assertFalse(plot1.equals(plot2));
        plot2.setTickPaint(Color.green);
        assertTrue(plot1.equals(plot2));

        // tick size
        plot1.setTickSize(1.23);
        assertFalse(plot1.equals(plot2));
        plot2.setTickSize(1.23);
        assertTrue(plot1.equals(plot2));

        // draw border
        plot1.setDrawBorder(!plot1.getDrawBorder());
        assertFalse(plot1.equals(plot2));
        plot2.setDrawBorder(plot1.getDrawBorder());
        assertTrue(plot1.equals(plot2));

        // meter angle
        plot1.setMeterAngle(22);
        assertFalse(plot1.equals(plot2));
        plot2.setMeterAngle(22);
        assertTrue(plot1.equals(plot2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        MeterPlot p1 = new MeterPlot();
        MeterPlot p2 = null;
        try {
            p2 = (MeterPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));

        // the clone and the original share a reference to the SAME dataset
        assertTrue(p1.getDataset() == p2.getDataset());

        // try a few checks to ensure that the clone is independent of the
        // original
        p1.getTickLabelFormat().setMinimumIntegerDigits(99);
        assertFalse(p1.equals(p2));
        p2.getTickLabelFormat().setMinimumIntegerDigits(99);
        assertTrue(p1.equals(p2));

        p1.addInterval(new MeterInterval("Test", new Range(1.234, 5.678)));
        assertFalse(p1.equals(p2));
        p2.addInterval(new MeterInterval("Test", new Range(1.234, 5.678)));
        assertTrue(p1.equals(p2));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization1() {
        MeterPlot p1 = new MeterPlot(null);
        p1.setDialBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        p1.setDialOutlinePaint(new GradientPaint(4.0f, 3.0f, Color.red,
                2.0f, 1.0f, Color.blue));
        p1.setNeedlePaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        p1.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        p1.setTickPaint(new GradientPaint(1.0f, 2.0f, Color.red,
                3.0f, 4.0f, Color.blue));
        MeterPlot p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                     new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (MeterPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization2() {
        MeterPlot p1 = new MeterPlot(new DefaultValueDataset(1.23));
        MeterPlot p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                     new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (MeterPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, p2);

    }

}
