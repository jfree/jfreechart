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
 * -------------------------
 * ScatterRendererTests.java
 * -------------------------
 * (C) Copyright 2007-2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Oct-2007 : Version 1 (DG);
 * 11-Oct-2007 : Renamed ScatterRenderer (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
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

import java.util.Arrays;
import java.util.List;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.renderer.category.ScatterRenderer;
import org.jfree.data.Range;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link ScatterRenderer} class.
 */
public class ScatterRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ScatterRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ScatterRendererTests(String name) {
        super(name);
    }

    /**
     * Test that the equals() method distinguishes all fields.
     */
    public void testEquals() {

        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = new ScatterRenderer();
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesShapesFilled(1, true);
        assertTrue(r1.equals(r2));

        r1.setBaseShapesFilled(false);
        assertFalse(r1.equals(r2));
        r2.setBaseShapesFilled(false);
        assertTrue(r1.equals(r2));

        r1.setUseFillPaint(true);
        assertFalse(r1.equals(r2));
        r2.setUseFillPaint(true);
        assertTrue(r1.equals(r2));

        r1.setDrawOutlines(true);
        assertFalse(r1.equals(r2));
        r2.setDrawOutlines(true);
        assertTrue(r1.equals(r2));

        r1.setUseOutlinePaint(true);
        assertFalse(r1.equals(r2));
        r2.setUseOutlinePaint(true);
        assertTrue(r1.equals(r2));

        r1.setUseSeriesOffset(false);
        assertFalse(r1.equals(r2));
        r2.setUseSeriesOffset(false);
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = new ScatterRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = null;
        try {
            r2 = (ScatterRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        assertTrue(checkIndependence(r1, r2));
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        ScatterRenderer r1 = new ScatterRenderer();
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
    private boolean checkIndependence(ScatterRenderer r1,
            ScatterRenderer r2) {

        // should be equal...
        if (!r1.equals(r2)) {
            return false;
        }

        // and independent...
        r1.setSeriesShapesFilled(1, true);
        if (r1.equals(r2)) {
            return false;
        }
        r2.setSeriesShapesFilled(1, true);
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
    public void testSerialization() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (ScatterRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(r1, r2);
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    public void testFindRangeBounds() {
        ScatterRenderer r = new ScatterRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultMultiValueCategoryDataset dataset
                = new DefaultMultiValueCategoryDataset();
        assertNull(r.findRangeBounds(dataset));

        List values = Arrays.asList(new Double[] {new Double(1.0)});
        dataset.add(values, "R1", "C1");
        assertEquals(new Range(1.0, 1.0), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {new Double(2.0), new Double(2.2)});
        dataset.add(values, "R1", "C2");
        assertEquals(new Range(1.0, 2.2), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {new Double(-3.0),
                new Double(-3.2)});
        dataset.add(values, "R1", "C3");
        assertEquals(new Range(-3.2, 2.2), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {new Double(6.0)});
        dataset.add(values, "R2", "C1");
        assertEquals(new Range(-3.2, 6.0), r.findRangeBounds(dataset));

        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-3.2, 2.2), r.findRangeBounds(dataset));
    }


}
