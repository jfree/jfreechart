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
 * -----------------------
 * LegendGraphicTests.java
 * -----------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 01-Sep-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.title.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.title.LegendGraphic;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * Tests for the {@link LegendGraphic} class.
 */
public class LegendGraphicTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(LegendGraphicTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public LegendGraphicTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        LegendGraphic g1 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0,
                3.0, 4.0), Color.black);
        LegendGraphic g2 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0,
                3.0, 4.0), Color.black);
        assertEquals(g1, g2);
        assertEquals(g2, g1);

        // shapeVisible
        g1.setShapeVisible(!g1.isShapeVisible());
        assertFalse(g1.equals(g2));
        g2.setShapeVisible(!g2.isShapeVisible());
        assertTrue(g1.equals(g2));

        // shape
        g1.setShape(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertFalse(g1.equals(g2));
        g2.setShape(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertTrue(g1.equals(g2));

        // shapeFilled
        g1.setShapeFilled(!g1.isShapeFilled());
        assertFalse(g1.equals(g2));
        g2.setShapeFilled(!g2.isShapeFilled());
        assertTrue(g1.equals(g2));

        // fillPaint
        g1.setFillPaint(Color.green);
        assertFalse(g1.equals(g2));
        g2.setFillPaint(Color.green);
        assertTrue(g1.equals(g2));

        // shapeOutlineVisible
        g1.setShapeOutlineVisible(!g1.isShapeOutlineVisible());
        assertFalse(g1.equals(g2));
        g2.setShapeOutlineVisible(!g2.isShapeOutlineVisible());
        assertTrue(g1.equals(g2));

        // outlinePaint
        g1.setOutlinePaint(Color.green);
        assertFalse(g1.equals(g2));
        g2.setOutlinePaint(Color.green);
        assertTrue(g1.equals(g2));

        // outlineStroke
        g1.setOutlineStroke(new BasicStroke(1.23f));
        assertFalse(g1.equals(g2));
        g2.setOutlineStroke(new BasicStroke(1.23f));
        assertTrue(g1.equals(g2));

        // shapeAnchor
        g1.setShapeAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertFalse(g1.equals(g2));
        g2.setShapeAnchor(RectangleAnchor.BOTTOM_RIGHT);
        assertTrue(g1.equals(g2));

        // shapeLocation
        g1.setShapeLocation(RectangleAnchor.BOTTOM_RIGHT);
        assertFalse(g1.equals(g2));
        g2.setShapeLocation(RectangleAnchor.BOTTOM_RIGHT);
        assertTrue(g1.equals(g2));

        // lineVisible
        g1.setLineVisible(!g1.isLineVisible());
        assertFalse(g1.equals(g2));
        g2.setLineVisible(!g2.isLineVisible());
        assertTrue(g1.equals(g2));

        // line
        g1.setLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertFalse(g1.equals(g2));
        g2.setLine(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        assertTrue(g1.equals(g2));

        // linePaint
        g1.setLinePaint(Color.green);
        assertFalse(g1.equals(g2));
        g2.setLinePaint(Color.green);
        assertTrue(g1.equals(g2));

        // lineStroke
        g1.setLineStroke(new BasicStroke(1.23f));
        assertFalse(g1.equals(g2));
        g2.setLineStroke(new BasicStroke(1.23f));
        assertTrue(g1.equals(g2));

        // fillPaintTransformer
        g1.setFillPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_HORIZONTAL));
        assertFalse(g1.equals(g2));
        g2.setFillPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_HORIZONTAL));
        assertTrue(g1.equals(g2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        LegendGraphic g1 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0,
                3.0, 4.0), Color.black);
        LegendGraphic g2 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0,
                3.0, 4.0), Color.black);
        assertTrue(g1.equals(g2));
        int h1 = g1.hashCode();
        int h2 = g2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        Rectangle r = new Rectangle(1, 2, 3, 4);
        LegendGraphic g1 = new LegendGraphic(r, Color.black);
        LegendGraphic g2 = null;
        try {
            g2 = (LegendGraphic) g1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        r.setBounds(4, 3, 2, 1);
        assertFalse(g1.equals(g2));
    }

    /**
     * A test for cloning - checks that the line shape is cloned correctly.
     */
    public void testCloning2() {
        Rectangle r = new Rectangle(1, 2, 3, 4);
        LegendGraphic g1 = new LegendGraphic(r, Color.black);
        Line2D l = new Line2D.Double(1.0, 2.0, 3.0, 4.0);
        g1.setLine(l);
        LegendGraphic g2 = null;
        try {
            g2 = (LegendGraphic) g1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        l.setLine(4.0, 3.0, 2.0, 1.0);
        assertFalse(g1.equals(g2));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        Stroke s = new BasicStroke(1.23f);
        LegendGraphic g1 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), Color.black);
        g1.setOutlineStroke(s);
        LegendGraphic g2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (LegendGraphic) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertTrue(g1.equals(g2));

    }

}
