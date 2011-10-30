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
 * --------------------------------------
 * AbstractCategoryItemRendererTests.java
 * --------------------------------------
 * (C) Copyright 2004-2010, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 12-Feb-2004 : Version 1 (DG);
 * 24-Nov-2006 : New cloning tests (DG);
 * 07-Dec-2006 : Added testEquals() method (DG);
 * 25-Nov-2008 : Added testFindRangeBounds() (DG);
 * 09-Feb-2010 : Added test2947660() (DG);
 *
 */

package org.jfree.chart.renderer.category.junit;

import java.text.NumberFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.labels.IntervalCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategorySeriesLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.AbstractCategoryItemRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.urls.StandardCategoryURLGenerator;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tests for the {@link AbstractCategoryItemRenderer} class.
 */
public class AbstractCategoryItemRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(AbstractCategoryItemRendererTests.class);
    }

    /**
     * Checks that all fields are distinguished.
     */
    public void testEquals() {
        BarRenderer r1 = new BarRenderer();
        BarRenderer r2 = new BarRenderer();
        assertEquals(r1, r2);

        // the plot field is NOT tested

        // toolTipGenerator
        r1.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertFalse(r1.equals(r2));
        r2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        assertTrue(r1.equals(r2));

        // toolTipGeneratorList
        r1.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator());
        assertFalse(r1.equals(r2));
        r2.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator());
        assertTrue(r1.equals(r2));

        // baseToolTipGenerator
        r1.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",
                NumberFormat.getInstance()));
        assertFalse(r1.equals(r2));
        r2.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("{2}",
                NumberFormat.getInstance()));
        assertTrue(r1.equals(r2));

        // itemLabelGenerator
        r1.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        assertFalse(r1.equals(r2));
        r2.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        assertTrue(r1.equals(r2));

        // itemLabelGeneratorList
        r1.setSeriesItemLabelGenerator(1,
                new StandardCategoryItemLabelGenerator());
        assertFalse(r1.equals(r2));
        r2.setSeriesItemLabelGenerator(1,
                new StandardCategoryItemLabelGenerator());
        assertTrue(r1.equals(r2));

        // baseItemLabelGenerator
        r1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                "{2}", NumberFormat.getInstance()));
        assertFalse(r1.equals(r2));
        r2.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                "{2}", NumberFormat.getInstance()));
        assertTrue(r1.equals(r2));

        // urlGenerator
        r1.setItemURLGenerator(new StandardCategoryURLGenerator());
        assertFalse(r1.equals(r2));
        r2.setItemURLGenerator(new StandardCategoryURLGenerator());
        assertTrue(r1.equals(r2));

        // urlGeneratorList
        r1.setSeriesItemURLGenerator(1, new StandardCategoryURLGenerator());
        assertFalse(r1.equals(r2));
        r2.setSeriesItemURLGenerator(1, new StandardCategoryURLGenerator());
        assertTrue(r1.equals(r2));

        // baseItemURLGenerator
        r1.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
                "abc.html"));
        assertFalse(r1.equals(r2));
        r2.setBaseItemURLGenerator(new StandardCategoryURLGenerator(
                "abc.html"));
        assertTrue(r1.equals(r2));

        // legendItemLabelGenerator
        r1.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator(
                "XYZ"));
        assertFalse(r1.equals(r2));
        r2.setLegendItemLabelGenerator(new StandardCategorySeriesLabelGenerator(
                "XYZ"));
        assertTrue(r1.equals(r2));

        // legendItemToolTipGenerator
        r1.setLegendItemToolTipGenerator(
                new StandardCategorySeriesLabelGenerator("ToolTip"));
        assertFalse(r1.equals(r2));
        r2.setLegendItemToolTipGenerator(
                new StandardCategorySeriesLabelGenerator("ToolTip"));
        assertTrue(r1.equals(r2));

        // legendItemURLGenerator
        r1.setLegendItemURLGenerator(
                new StandardCategorySeriesLabelGenerator("URL"));
        assertFalse(r1.equals(r2));
        r2.setLegendItemURLGenerator(
                new StandardCategorySeriesLabelGenerator("URL"));
        assertTrue(r1.equals(r2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning1() {
        AbstractCategoryItemRenderer r1 = new BarRenderer();
        r1.setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        AbstractCategoryItemRenderer r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1 = new BarRenderer();
        r1.setSeriesItemLabelGenerator(0,
                new StandardCategoryItemLabelGenerator());
        r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1 = new BarRenderer();
        r1.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning2() {
        BarRenderer r1 = new BarRenderer();
        r1.setItemLabelGenerator(new IntervalCategoryItemLabelGenerator());
        BarRenderer r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1 = new BarRenderer();
        r1.setSeriesItemLabelGenerator(0,
                new IntervalCategoryItemLabelGenerator());
        r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1 = new BarRenderer();
        r1.setBaseItemLabelGenerator(new IntervalCategoryItemLabelGenerator());
        r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Check that the legendItemLabelGenerator is cloned.
     */
    public void testCloning_LegendItemLabelGenerator() {
        StandardCategorySeriesLabelGenerator generator
                = new StandardCategorySeriesLabelGenerator("Series {0}");
        BarRenderer r1 = new BarRenderer();
        r1.setLegendItemLabelGenerator(generator);
        BarRenderer r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        // check that the generator has been cloned
        assertTrue(r1.getLegendItemLabelGenerator()
                != r2.getLegendItemLabelGenerator());
    }

    /**
     * Check that the legendItemToolTipGenerator is cloned.
     */
    public void testCloning_LegendItemToolTipGenerator() {
        StandardCategorySeriesLabelGenerator generator
                = new StandardCategorySeriesLabelGenerator("Series {0}");
        BarRenderer r1 = new BarRenderer();
        r1.setLegendItemToolTipGenerator(generator);
        BarRenderer r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        // check that the generator has been cloned
        assertTrue(r1.getLegendItemToolTipGenerator()
                != r2.getLegendItemToolTipGenerator());
    }

    /**
     * Check that the legendItemURLGenerator is cloned.
     */
    public void testCloning_LegendItemURLGenerator() {
        StandardCategorySeriesLabelGenerator generator
                = new StandardCategorySeriesLabelGenerator("Series {0}");
        BarRenderer r1 = new BarRenderer();
        r1.setLegendItemURLGenerator(generator);
        BarRenderer r2 = null;
        try {
            r2 = (BarRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        // check that the generator has been cloned
        assertTrue(r1.getLegendItemURLGenerator()
                != r2.getLegendItemURLGenerator());
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    public void testFindRangeBounds() {
        AbstractCategoryItemRenderer r = new LineAndShapeRenderer();
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
    }

    /**
     * A test that reproduces the problem reported in bug 2947660.
     */
    public void test2947660() {
        AbstractCategoryItemRenderer r = new LineAndShapeRenderer();
        assertNotNull(r.getLegendItems());
        assertEquals(0, r.getLegendItems().getItemCount());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CategoryPlot plot = new CategoryPlot();
        plot.setDataset(dataset);
        plot.setRenderer(r);
        assertEquals(0, r.getLegendItems().getItemCount());

        dataset.addValue(1.0, "S1", "C1");
        LegendItemCollection lic = r.getLegendItems();
        assertEquals(1, lic.getItemCount());
        assertEquals("S1", lic.get(0).getLabel());
    }

}

