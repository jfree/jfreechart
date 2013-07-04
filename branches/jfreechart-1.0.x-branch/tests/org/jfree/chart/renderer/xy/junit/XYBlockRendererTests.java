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
 * XYBlockRendererTests.java
 * -------------------------
 * (C) Copyright 2006-2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Jul-2006 : Version 1 (DG);
 * 09-Mar-2007 : Added independence check to testCloning (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 * 20-Oct-2011 : Added testFindDomainBounds() and testFindRangeBounds() (DG);
 *
 */

package org.jfree.chart.renderer.xy.junit;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.renderer.GrayPaintScale;
import org.jfree.chart.renderer.LookupPaintScale;
import org.jfree.chart.renderer.xy.XYBlockRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.DefaultXYZDataset;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link XYBlockRenderer} class.
 */
public class XYBlockRendererTests extends TestCase {

    private static final double EPSILON = 0.0000000001;

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYBlockRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYBlockRendererTests(String name) {
        super(name);
    }

    /**
     * Test that the equals() method distinguishes all fields.
     */
    public void testEquals() {

        // default instances
        XYBlockRenderer r1 = new XYBlockRenderer();
        XYBlockRenderer r2 = new XYBlockRenderer();
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));

        // blockHeight
        r1.setBlockHeight(2.0);
        assertFalse(r1.equals(r2));
        r2.setBlockHeight(2.0);
        assertTrue(r1.equals(r2));

        // blockWidth
        r1.setBlockWidth(2.0);
        assertFalse(r1.equals(r2));
        r2.setBlockWidth(2.0);
        assertTrue(r1.equals(r2));

        // paintScale
        r1.setPaintScale(new GrayPaintScale(0.0, 1.0));
        assertFalse(r1.equals(r2));
        r2.setPaintScale(new GrayPaintScale(0.0, 1.0));
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        XYBlockRenderer r1 = new XYBlockRenderer();
        XYBlockRenderer r2 = new XYBlockRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYBlockRenderer r1 = new XYBlockRenderer();
        LookupPaintScale scale1 = new LookupPaintScale();
        r1.setPaintScale(scale1);
        XYBlockRenderer r2 = null;
        try {
            r2 = (XYBlockRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        // check independence
        scale1.add(0.5, Color.red);
        assertFalse(r1.equals(r2));
        LookupPaintScale scale2 = (LookupPaintScale) r2.getPaintScale();
        scale2.add(0.5, Color.red);
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        XYBlockRenderer r1 = new XYBlockRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYBlockRenderer r1 = new XYBlockRenderer();
        XYBlockRenderer r2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (XYBlockRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(r1, r2);
    }

    /**
     * A simple test for bug 1766646.
     */
    public void testBug1766646A() {
        XYBlockRenderer r = new XYBlockRenderer();
        Range range = r.findDomainBounds(null);
        assertTrue(range == null);
        DefaultXYZDataset emptyDataset = new DefaultXYZDataset();
        range = r.findDomainBounds(emptyDataset);
        assertTrue(range == null);
    }

    /**
     * A simple test for bug 1766646.
     */
    public void testBug1766646B() {
        XYBlockRenderer r = new XYBlockRenderer();
        Range range = r.findRangeBounds(null);
        assertTrue(range == null);
        DefaultXYZDataset emptyDataset = new DefaultXYZDataset();
        range = r.findRangeBounds(emptyDataset);
        assertTrue(range == null);
    }

    /**
     * Some tests for the findRangeBounds() method.
     */
    public void testFindRangeBounds() {
        XYBlockRenderer renderer = new XYBlockRenderer();
        assertNull(renderer.findRangeBounds(null));

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("S1");
        series.add(1.0, null);
        dataset.addSeries(series);
        Range r = renderer.findRangeBounds(dataset);
        assertNull(r);
    }
    
    /**
     * Some tests for the findDomainBounds() method.
     */
    public void testFindDomainBounds() {
        XYBlockRenderer renderer = new XYBlockRenderer();
        assertNull(renderer.findRangeBounds(null));

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("S1");
        series.add(1.0, null);
        dataset.addSeries(series);
        Range r = renderer.findDomainBounds(dataset);
        assertEquals(0.5, r.getLowerBound(), EPSILON);
        assertEquals(1.5, r.getUpperBound(), EPSILON);

        dataset.removeAllSeries();
        r = renderer.findDomainBounds(dataset);
        assertNull(r);
    }    
           
}
