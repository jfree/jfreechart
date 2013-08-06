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
 * -----------------------------
 * LineAndShapeRendererTest.java
 * -----------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Sep-2003 : Version 1 (DG);
 * 17-May-2007 : Added check for getLegendItem() method (DG);
 * 27-Sep-2007 : Extended equals() test (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 * 16-May-2009 : Added testFindRangeBounds() (DG);
 *
 */

package org.jfree.chart.renderer.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.TestUtilities;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link LineAndShapeRenderer} class.
 */
public class LineAndShapeRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {

        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        LineAndShapeRenderer r2 = new LineAndShapeRenderer();
        assertEquals(r1, r2);

        r1.setBaseLinesVisible(!r1.getBaseLinesVisible());
        assertFalse(r1.equals(r2));
        r2.setBaseLinesVisible(r1.getBaseLinesVisible());
        assertTrue(r1.equals(r2));

        r1.setSeriesLinesVisible(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesLinesVisible(1, true);
        assertTrue(r1.equals(r2));

        r1.setLinesVisible(false);
        assertFalse(r1.equals(r2));
        r2.setLinesVisible(false);
        assertTrue(r1.equals(r2));

        r1.setBaseShapesVisible(!r1.getBaseShapesVisible());
        assertFalse(r1.equals(r2));
        r2.setBaseShapesVisible(r1.getBaseShapesVisible());
        assertTrue(r1.equals(r2));

        r1.setSeriesShapesVisible(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesShapesVisible(1, true);
        assertTrue(r1.equals(r2));

        r1.setShapesVisible(false);
        assertFalse(r1.equals(r2));
        r2.setShapesVisible(false);
        assertTrue(r1.equals(r2));

        r1.setShapesFilled(false);
        assertFalse(r1.equals(r2));
        r2.setShapesFilled(false);
        assertTrue(r1.equals(r2));

        r1.setSeriesShapesFilled(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesShapesFilled(1, true);
        assertTrue(r1.equals(r2));

        r1.setBaseShapesFilled(false);
        assertFalse(r1.equals(r2));
        r2.setBaseShapesFilled(false);
        assertTrue(r1.equals(r2));

        r1.setUseOutlinePaint(true);
        assertFalse(r1.equals(r2));
        r2.setUseOutlinePaint(true);
        assertTrue(r1.equals(r2));

        r1.setUseSeriesOffset(true);
        assertFalse(r1.equals(r2));
        r2.setUseSeriesOffset(true);
        assertTrue(r1.equals(r2));

        r1.setItemMargin(0.14);
        assertFalse(r1.equals(r2));
        r2.setItemMargin(0.14);
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        LineAndShapeRenderer r2 = new LineAndShapeRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
        assertTrue(checkIndependence(r1, r2));
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Checks that the two renderers are equal but independent of one another.
     *
     * @param r1  renderer 1.
     * @param r2  renderer 2.
     *
     * @return A boolean.
     */
    private boolean checkIndependence(LineAndShapeRenderer r1,
                                      LineAndShapeRenderer r2) {

        // should be equal...
        if (!r1.equals(r2)) {
            return false;
        }

        // and independent...
        r1.setBaseLinesVisible(!r1.getBaseLinesVisible());
        if (r1.equals(r2)) {
            return false;
        }
        r2.setBaseLinesVisible(r1.getBaseLinesVisible());
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setSeriesLinesVisible(1, true);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setSeriesLinesVisible(1, true);
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setLinesVisible(false);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setLinesVisible(false);
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setBaseShapesVisible(!r1.getBaseShapesVisible());
        if (r1.equals(r2)) {
            return false;
        }
        r2.setBaseShapesVisible(r1.getBaseShapesVisible());
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setSeriesShapesVisible(1, true);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setSeriesShapesVisible(1, true);
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setShapesVisible(false);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setShapesVisible(false);
        if (!r1.equals(r2)) {
            return false;
        }

        boolean flag = true;
        Boolean existing = r1.getShapesFilled();
        if (existing != null) {
            flag = !existing.booleanValue();
        }
        r1.setShapesFilled(flag);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setShapesFilled(flag);
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setShapesFilled(false);
        r2.setShapesFilled(false);
        r1.setSeriesShapesFilled(0, false);
        r2.setSeriesShapesFilled(0, true);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setSeriesShapesFilled(0, false);
        if (!r1.equals(r2)) {
            return false;
        }

        r1.setBaseShapesFilled(false);
        r2.setBaseShapesFilled(true);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setBaseShapesFilled(false);
        if (!r1.equals(r2)) {
            return false;
        }
        return true;

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        LineAndShapeRenderer r1 = new LineAndShapeRenderer();
        LineAndShapeRenderer r2 = (LineAndShapeRenderer) 
                TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * A check for the datasetIndex and seriesIndex fields in the LegendItem
     * returned by the getLegendItem() method.
     */
    @Test
    public void testGetLegendItemSeriesIndex() {
        DefaultCategoryDataset dataset0 = new DefaultCategoryDataset();
        dataset0.addValue(21.0, "R1", "C1");
        dataset0.addValue(22.0, "R2", "C1");
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        dataset1.addValue(23.0, "R3", "C1");
        dataset1.addValue(24.0, "R4", "C1");
        dataset1.addValue(25.0, "R5", "C1");
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        CategoryPlot plot = new CategoryPlot(dataset0, new CategoryAxis("x"),
                new NumberAxis("y"), r);
        plot.setDataset(1, dataset1);
        /*JFreeChart chart =*/ new JFreeChart(plot);
        LegendItem li = r.getLegendItem(1, 2);
        assertEquals("R5", li.getLabel());
        assertEquals(1, li.getDatasetIndex());
        assertEquals(2, li.getSeriesIndex());
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        LineAndShapeRenderer r = new LineAndShapeRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        assertNull(r.findRangeBounds(dataset));

        dataset.addValue(1.0, "R1", "C1");
        assertEquals(new Range(1.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(-2.0, "R1", "C2");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(null, "R1", "C3");
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));

        dataset.addValue(-6.0, "R2", "C1");
        assertEquals(new Range(-6.0, 1.0), r.findRangeBounds(dataset));

        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-2.0, 1.0), r.findRangeBounds(dataset));
    }

}
