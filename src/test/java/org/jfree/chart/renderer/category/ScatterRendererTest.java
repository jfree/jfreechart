/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ------------------------
 * ScatterRendererTest.java
 * ------------------------
 * (C) Copyright 2007-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer.category;

import java.util.Arrays;
import java.util.List;
import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.Range;
import org.jfree.data.statistics.DefaultMultiValueCategoryDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ScatterRenderer} class.
 */
public class ScatterRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = new ScatterRenderer();
        assertEquals(r1, r2);

        r1.setSeriesShapesFilled(1, true);
        assertNotEquals(r1, r2);
        r2.setSeriesShapesFilled(1, true);
        assertEquals(r1, r2);

        r1.setBaseShapesFilled(false);
        assertNotEquals(r1, r2);
        r2.setBaseShapesFilled(false);
        assertEquals(r1, r2);

        r1.setUseFillPaint(true);
        assertNotEquals(r1, r2);
        r2.setUseFillPaint(true);
        assertEquals(r1, r2);

        r1.setDrawOutlines(true);
        assertNotEquals(r1, r2);
        r2.setDrawOutlines(true);
        assertEquals(r1, r2);

        r1.setUseOutlinePaint(true);
        assertNotEquals(r1, r2);
        r2.setUseOutlinePaint(true);
        assertEquals(r1, r2);

        r1.setUseSeriesOffset(false);
        assertNotEquals(r1, r2);
        r2.setUseSeriesOffset(false);
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = new ScatterRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = CloneUtils.clone(r1);
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);

        assertTrue(checkIndependence(r1, r2));
        TestUtils.checkIndependence(r1, r2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
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
    private boolean checkIndependence(ScatterRenderer r1, ScatterRenderer r2) {

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
        return r1.equals(r2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        ScatterRenderer r1 = new ScatterRenderer();
        ScatterRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
        TestUtils.checkIndependence(r1, r2);
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        ScatterRenderer r = new ScatterRenderer();
        assertNull(r.findRangeBounds(null));

        // an empty dataset should return a null range
        DefaultMultiValueCategoryDataset<String, String> dataset
                = new DefaultMultiValueCategoryDataset<>();
        assertNull(r.findRangeBounds(dataset));

        List<Number> values = Arrays.asList(new Double[] {1.0});
        dataset.add(values, "R1", "C1");
        assertEquals(new Range(1.0, 1.0), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {2.0, 2.2});
        dataset.add(values, "R1", "C2");
        assertEquals(new Range(1.0, 2.2), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {-3.0, -3.2});
        dataset.add(values, "R1", "C3");
        assertEquals(new Range(-3.2, 2.2), r.findRangeBounds(dataset));

        values = Arrays.asList(new Double[] {6.0});
        dataset.add(values, "R2", "C1");
        assertEquals(new Range(-3.2, 6.0), r.findRangeBounds(dataset));

        r.setSeriesVisible(1, Boolean.FALSE);
        assertEquals(new Range(-3.2, 2.2), r.findRangeBounds(dataset));
    }

}
