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
 * -----------------------
 * LineRenderer3DTest.java
 * -----------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 15-Oct-2004 : Version 1 (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.renderer.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtilities;

import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link LineRenderer3D} class.
 */
public class LineRenderer3DTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = new LineRenderer3D();
        assertEquals(r1, r2);

        r1.setXOffset(99.9);
        assertFalse(r1.equals(r2));
        r2.setXOffset(99.9);
        assertTrue(r1.equals(r2));

        r1.setYOffset(111.1);
        assertFalse(r1.equals(r2));
        r2.setYOffset(111.1);
        assertTrue(r1.equals(r2));

        r1.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.blue));
        assertFalse(r1.equals(r2));
        r2.setWallPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.blue));
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = new LineRenderer3D();
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
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = (LineRenderer3D) r1.clone();
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
        LineRenderer3D r1 = new LineRenderer3D();
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
    private boolean checkIndependence(LineRenderer3D r1, LineRenderer3D r2) {

        // should be equal...
        boolean b0 = r1.equals(r2);

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
        boolean b5 = !r1.equals(r2);
        r2.setShapesFilled(flag);
        boolean b6 = r1.equals(r2);

        r1.setShapesFilled(false);
        r2.setShapesFilled(false);
        r1.setSeriesShapesFilled(0, false);
        r2.setSeriesShapesFilled(0, true);
        boolean b7 = !r1.equals(r2);
        r2.setSeriesShapesFilled(0, false);
        boolean b8 = (r1.equals(r2));

        r1.setBaseShapesFilled(false);
        r2.setBaseShapesFilled(true);
        boolean b9 = !r1.equals(r2);
        r2.setBaseShapesFilled(false);
        boolean b10 = (r1.equals(r2));

        return b0 && b5 && b6 && b7 && b8 && b9 && b10;

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        LineRenderer3D r1 = new LineRenderer3D();
        LineRenderer3D r2 = (LineRenderer3D) TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

}
