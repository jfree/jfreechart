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
 * --------------------------------
 * StatisticalBarRendererTests.java
 * --------------------------------
 * (C) Copyright 2003-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 28-Aug-2007 : Added tests for bug 1779941 (DG);
 * 14-Nov-2007 : Updated testEquals() (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 * 16-May-2009 : Added testFindRangeBounds (DG);
 *
 */

package org.jfree.chart.renderer.category.junit;

import java.awt.BasicStroke;
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

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.data.Range;
import org.jfree.data.statistics.DefaultStatisticalCategoryDataset;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link StatisticalBarRenderer} class.
 */
public class StatisticalBarRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StatisticalBarRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StatisticalBarRendererTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        StatisticalBarRenderer r1 = new StatisticalBarRenderer();
        StatisticalBarRenderer r2 = new StatisticalBarRenderer();
        assertEquals(r1, r2);

        r1.setErrorIndicatorPaint(Color.red);
        assertFalse(r1.equals(r2));
        r2.setErrorIndicatorPaint(Color.red);
        assertTrue(r2.equals(r1));

        r1.setErrorIndicatorStroke(new BasicStroke(1.5f));
        assertFalse(r1.equals(r2));
        r2.setErrorIndicatorStroke(new BasicStroke(1.5f));
        assertTrue(r2.equals(r1));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        StatisticalBarRenderer r1 = new StatisticalBarRenderer();
        StatisticalBarRenderer r2 = new StatisticalBarRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        StatisticalBarRenderer r1 = new StatisticalBarRenderer();
        StatisticalBarRenderer r2 = null;
        try {
            r2 = (StatisticalBarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        StatisticalBarRenderer r1 = new StatisticalBarRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        StatisticalBarRenderer r1 = new StatisticalBarRenderer();
        StatisticalBarRenderer r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            r2 = (StatisticalBarRenderer) in.readObject();
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
            DefaultStatisticalCategoryDataset dataset
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(3.0, 4.0, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalBarRenderer());
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
     * Draws the chart with a <code>null</code> mean value to make sure that
     * no exceptions are thrown (particularly by code in the renderer).  See
     * bug report 1779941.
     */
    public void testDrawWithNullMeanVertical() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(null, new Double(4.0), "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalBarRenderer());
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
     * Draws the chart with a <code>null</code> mean value to make sure that
     * no exceptions are thrown (particularly by code in the renderer).  See
     * bug report 1779941.
     */
    public void testDrawWithNullMeanHorizontal() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(null, new Double(4.0), "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalBarRenderer());
            plot.setOrientation(PlotOrientation.HORIZONTAL);
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
     * Draws the chart with a <code>null</code> standard deviation to make sure
     * that no exceptions are thrown (particularly by code in the renderer).
     * See bug report 1779941.
     */
    public void testDrawWithNullDeviationVertical() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(new Double(4.0), null, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalBarRenderer());
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
     * Draws the chart with a <code>null</code> standard deviation to make sure
     * that no exceptions are thrown (particularly by code in the renderer).
     * See bug report 1779941.
     */
    public void testDrawWithNullDeviationHorizontal() {
        boolean success = false;
        try {
            DefaultStatisticalCategoryDataset dataset
                    = new DefaultStatisticalCategoryDataset();
            dataset.add(1.0, 2.0, "S1", "C1");
            dataset.add(new Double(4.0), null, "S1", "C2");
            CategoryPlot plot = new CategoryPlot(dataset,
                    new CategoryAxis("Category"), new NumberAxis("Value"),
                    new StatisticalBarRenderer());
            plot.setOrientation(PlotOrientation.HORIZONTAL);
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
        StatisticalBarRenderer r = new StatisticalBarRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultStatisticalCategoryDataset dataset
                = new DefaultStatisticalCategoryDataset();
        assertNull(r.findRangeBounds(dataset));

        dataset.add(1.0, 0.5, "R1", "C1");
        assertEquals(new Range(0.0, 1.5), r.findRangeBounds(dataset));

        r.setIncludeBaseInRange(false);
        assertEquals(new Range(0.5, 1.5), r.findRangeBounds(dataset));
        r.setIncludeBaseInRange(true);

        dataset.add(-2.0, 0.2, "R1", "C2");
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));

        dataset.add(null, null, "R1", "C3");
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));

        dataset.add(5.0, 1.0, "R2", "C3");
        assertEquals(new Range(-2.2, 6.0), r.findRangeBounds(dataset));

        // check that the series visible flag is observed
        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-2.2, 1.5), r.findRangeBounds(dataset));
    }

}
