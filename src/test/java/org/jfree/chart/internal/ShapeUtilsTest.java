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
 * -------------------
 * ShapeUtilsTest.java
 * -------------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.internal;

import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for the {@link ShapeUtils} class.
 */
public class ShapeUtilsTest {

    @Test
    public void testEqualsLine2D() {
        Line2D.Double line1 = new Line2D.Double(1.0, 2.0, 3.0, 4.0);
        Line2D.Double line2 = new Line2D.Double(1.0, 2.0, 3.0, 4.0);
        assertNotEquals(line1, line2); // Object.equals
        assertTrue(ShapeUtils.equal(line1, line2));
    }
    
    @Test
    public void testEqualsEllipse2D() {
        Ellipse2D.Double line1 = new Ellipse2D.Double(1.0, 2.0, 3.0, 4.0);
        Ellipse2D.Double line2 = new Ellipse2D.Double(1.0, 2.0, 3.0, 4.0);
        assertEquals(line1, line2); // don't need special handling in ShapeUtils
        assertTrue(ShapeUtils.equal(line1, line2));
    }

    /**
     * String is immutable so we expect to get back the same object.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testCloneRectangularShape() throws CloneNotSupportedException {
        Rectangle r1 = new Rectangle(1, 2, 3, 4);
        Rectangle r2 = (Rectangle) CloneUtils.clone(r1);
        assertEquals(r1, r2);
        
        r1.setRect(2, 2, 2, 2);
        assertNotEquals(r1, r2);
        r2.setRect(2, 2, 2, 2);
        assertEquals(r1, r2);
    }

    /**
     * The goal of this test is to verify that cloning works for {@code Line2D} 
     * instances.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testCloneLine2D() throws CloneNotSupportedException {
        Line2D line1 = new Line2D.Double(1.0, 2.0, 3.0, 4.0);
        Line2D line2 = (Line2D) CloneUtils.clone(line1);
        assertTrue(ShapeUtils.equal(line1, line2));
        
        line1.setLine(2.0, 3.0, 4.0, 5.0);
        assertFalse(ShapeUtils.equal(line1, line2));
        line2.setLine(2.0, 3.0, 4.0, 5.0);
        assertTrue(ShapeUtils.equal(line1, line2));
    }

    /**
     * The goal of this test is to verify that cloning works for {@code Path2D} 
     * instances.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testClonePath2D() throws CloneNotSupportedException {
        Path2D p1 = new Path2D.Double();
        p1.moveTo(1.0, 2.0);
        p1.lineTo(3.0, 4.0);
        p1.closePath();
        Path2D p2 = (Path2D) CloneUtils.clone(p1);
        assertTrue(ShapeUtils.equal(p1, p2));
        
        p1 = new Path2D.Double();
        p1.moveTo(3.0, 4.0);
        p1.lineTo(1.0, 2.0);
        p1.closePath();
        assertFalse(ShapeUtils.equal(p1, p2));
        p2 = new Path2D.Double();
        p2.moveTo(3.0, 4.0);
        p2.lineTo(1.0, 2.0);
        p2.closePath();
        assertTrue(ShapeUtils.equal(p1, p2));
    }

}
