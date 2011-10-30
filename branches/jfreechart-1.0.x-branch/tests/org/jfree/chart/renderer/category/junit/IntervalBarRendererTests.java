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
 * -----------------------------
 * IntervalBarRendererTests.java
 * -----------------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable (DG);
 * 16-May-2009 : Added testFindRangeBounds() (DG);
 *
 */

package org.jfree.chart.renderer.category.junit;

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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.IntervalBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link IntervalBarRenderer} class.
 */
public class IntervalBarRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(IntervalBarRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public IntervalBarRendererTests(String name) {
        super(name);
    }

    /**
     * Problem that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        IntervalBarRenderer r1 = new IntervalBarRenderer();
        IntervalBarRenderer r2 = new IntervalBarRenderer();
        assertEquals(r1, r2);

        // the renderer should not be equal to a BarRenderer
        BarRenderer br = new BarRenderer();
        assertFalse(r1.equals(br));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        IntervalBarRenderer r1 = new IntervalBarRenderer();
        IntervalBarRenderer r2 = new IntervalBarRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        IntervalBarRenderer r1 = new IntervalBarRenderer();
        IntervalBarRenderer r2 = null;
        try {
            r2 = (IntervalBarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        IntervalBarRenderer r1 = new IntervalBarRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        IntervalBarRenderer r1 = new IntervalBarRenderer();
        IntervalBarRenderer r2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();
            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            r2 = (IntervalBarRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
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
            double[][] starts = new double[][] {{0.1, 0.2, 0.3},
                    {0.3, 0.4, 0.5}};
            double[][] ends = new double[][] {{0.5, 0.6, 0.7}, {0.7, 0.8, 0.9}};
            DefaultIntervalCategoryDataset dataset
                    = new DefaultIntervalCategoryDataset(starts, ends);
            IntervalBarRenderer renderer = new IntervalBarRenderer();
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    renderer);
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
     * Some checks for the findRangeBounds() method.
     */
    public void testFindRangeBounds() {
        IntervalBarRenderer r = new IntervalBarRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultIntervalCategoryDataset dataset
                = new DefaultIntervalCategoryDataset(new double[0][0],
                new double[0][0]);
        assertNull(r.findRangeBounds(dataset));

        double[][] starts = new double[][] {{0.1, 0.2, 0.3}, {0.3, 0.4, 0.5}};
        double[][] ends = new double[][] {{0.5, 0.6, 0.7}, {0.7, 0.8, 0.9}};
        dataset = new DefaultIntervalCategoryDataset(starts, ends);
        assertEquals(new Range(0.0, 0.9), r.findRangeBounds(dataset));
        r.setIncludeBaseInRange(false);
        assertEquals(new Range(0.1, 0.9), r.findRangeBounds(dataset));
        r.setIncludeBaseInRange(true);

        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(0.0, 0.7), r.findRangeBounds(dataset));
    }

}
