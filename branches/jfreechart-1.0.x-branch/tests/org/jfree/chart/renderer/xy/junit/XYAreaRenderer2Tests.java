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
 * AreaXYRenderer2Tests.java
 * -------------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 24-May-2005 : Version 1 (DG);
 * 30-Nov-2006 : Extended testEquals() and testCloning() (DG);
 * 17-May-2007 : Added testGetLegendItemSeriesIndex() (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.chart.renderer.xy.junit;

import java.awt.Rectangle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer2;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link XYAreaRenderer2} class.
 */
public class XYAreaRenderer2Tests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYAreaRenderer2Tests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYAreaRenderer2Tests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        XYAreaRenderer2 r1 = new XYAreaRenderer2();
        XYAreaRenderer2 r2 = new XYAreaRenderer2();
        assertEquals(r1, r2);

        r1.setOutline(!r1.isOutline());
        assertFalse(r1.equals(r2));
        r2.setOutline(r1.isOutline());
        assertTrue(r1.equals(r2));

        r1.setLegendArea(new Rectangle(1, 2, 3, 4));
        assertFalse(r1.equals(r2));
        r2.setLegendArea(new Rectangle(1, 2, 3, 4));
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        XYAreaRenderer2 r1 = new XYAreaRenderer2();
        XYAreaRenderer2 r2 = new XYAreaRenderer2();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYAreaRenderer2 r1 = new XYAreaRenderer2();
        Rectangle rect = new Rectangle(1, 2, 3, 4);
        r1.setLegendArea(rect);
        XYAreaRenderer2 r2 = null;
        try {
            r2 = (XYAreaRenderer2) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        // check independence
        rect.setBounds(99, 99, 99, 99);
        assertFalse(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        XYAreaRenderer2 r1 = new XYAreaRenderer2();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        XYAreaRenderer2 r1 = new XYAreaRenderer2();
        XYAreaRenderer2 r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (XYAreaRenderer2) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(r1, r2);

    }

    /**
     * Draws the chart with a <code>null</code> info object to make sure that
     * no exceptions are thrown (particularly by code in the renderer).
     */
    public void testDrawWithNullInfo() {
        boolean success = false;
        try {
            DefaultTableXYDataset dataset = new DefaultTableXYDataset();

            XYSeries s1 = new XYSeries("Series 1", true, false);
            s1.add(5.0, 5.0);
            s1.add(10.0, 15.5);
            s1.add(15.0, 9.5);
            s1.add(20.0, 7.5);
            dataset.addSeries(s1);

            XYSeries s2 = new XYSeries("Series 2", true, false);
            s2.add(5.0, 5.0);
            s2.add(10.0, 15.5);
            s2.add(15.0, 9.5);
            s2.add(20.0, 3.5);
            dataset.addSeries(s2);
            XYPlot plot = new XYPlot(dataset,
                    new NumberAxis("X"), new NumberAxis("Y"),
                    new XYAreaRenderer2());
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200,
                    null);
            success = true;
        }
        catch (NullPointerException e) {
            e.printStackTrace();
            success = false;
        }
        assertTrue(success);
    }

    /**
     * A check for the datasetIndex and seriesIndex fields in the LegendItem
     * returned by the getLegendItem() method.
     */
    public void testGetLegendItemSeriesIndex() {
        XYSeriesCollection d1 = new XYSeriesCollection();
        XYSeries s1 = new XYSeries("S1");
        s1.add(1.0, 1.1);
        XYSeries s2 = new XYSeries("S2");
        s2.add(1.0, 1.1);
        d1.addSeries(s1);
        d1.addSeries(s2);

        XYSeriesCollection d2 = new XYSeriesCollection();
        XYSeries s3 = new XYSeries("S3");
        s3.add(1.0, 1.1);
        XYSeries s4 = new XYSeries("S4");
        s4.add(1.0, 1.1);
        XYSeries s5 = new XYSeries("S5");
        s5.add(1.0, 1.1);
        d2.addSeries(s3);
        d2.addSeries(s4);
        d2.addSeries(s5);

        XYAreaRenderer2 r = new XYAreaRenderer2();
        XYPlot plot = new XYPlot(d1, new NumberAxis("x"),
                new NumberAxis("y"), r);
        plot.setDataset(1, d2);
        /*JFreeChart chart =*/ new JFreeChart(plot);
        LegendItem li = r.getLegendItem(1, 2);
        assertEquals("S5", li.getLabel());
        assertEquals(1, li.getDatasetIndex());
        assertEquals(2, li.getSeriesIndex());
    }

}
