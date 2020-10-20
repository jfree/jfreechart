/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * ----------------
 * OutlierTest.java
 * ----------------
 * (C) Copyright 2007-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Nov-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;
import java.io.Serializable;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Outlier} class.
 */
public class OutlierTest {

    private static final double EPSILON = 0.000000001;

    /**
     * Simple check for the default constructor.
     */
    @Test
    public void testConstructor() {
        Outlier out = new Outlier(1.0, 2.0, 3.0);
        assertEquals(-2.0, out.getX(), EPSILON);
        assertEquals(-1.0, out.getY(), EPSILON);
        assertEquals(3.0, out.getRadius(), EPSILON);
    }

    /**
     * A test for the equals() method.
     */
    @Test
    public void testEquals() {
        Outlier out1 = new Outlier(1.0, 2.0, 3.0);
        Outlier out2 = new Outlier(1.0, 2.0, 3.0);
        assertTrue(out1.equals(out2));
        assertTrue(out2.equals(out1));

        out1.setPoint(new Point2D.Double(2.0, 2.0));
        assertFalse(out1.equals(out2));
        out2.setPoint(new Point2D.Double(2.0, 2.0));
        assertTrue(out1.equals(out2));

        out1.setPoint(new Point2D.Double(2.0, 3.0));
        assertFalse(out1.equals(out2));
        out2.setPoint(new Point2D.Double(2.0, 3.0));
        assertTrue(out1.equals(out2));

        out1.setRadius(4.0);
        assertFalse(out1.equals(out2));
        out2.setRadius(4.0);
        assertTrue(out1.equals(out2));
    }

    /**
     * Confirm that cloning is not implemented.
     */
    @Test
    public void testCloning() {
        Outlier out1 = new Outlier(1.0, 2.0, 3.0);
        assertFalse(out1 instanceof Cloneable);
    }

    /**
     * Confirm that serialization is not implemented.
     */
    @Test
    public void testSerialization() {
        Outlier out1 = new Outlier(1.0, 2.0, 3.0);
        assertFalse(out1 instanceof Serializable);
    }

}

