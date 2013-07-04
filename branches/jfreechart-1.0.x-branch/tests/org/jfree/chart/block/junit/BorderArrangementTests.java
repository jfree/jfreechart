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
 * ---------------------------
 * BorderArrangementTests.java
 * ---------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Oct-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.block.junit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.Size2D;

/**
 * Tests for the {@link BorderArrangement} class.
 */
public class BorderArrangementTests extends TestCase {

    private static final double EPSILON = 0.0000000001;

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(BorderArrangementTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public BorderArrangementTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    public void testEquals() {
        BorderArrangement b1 = new BorderArrangement();
        BorderArrangement b2 = new BorderArrangement();
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));

        b1.add(new EmptyBlock(99.0, 99.0), null);
        assertFalse(b1.equals(b2));
        b2.add(new EmptyBlock(99.0, 99.0), null);
        assertTrue(b1.equals(b2));

        b1.add(new EmptyBlock(1.0, 1.0), RectangleEdge.LEFT);
        assertFalse(b1.equals(b2));
        b2.add(new EmptyBlock(1.0, 1.0), RectangleEdge.LEFT);
        assertTrue(b1.equals(b2));

        b1.add(new EmptyBlock(2.0, 2.0), RectangleEdge.RIGHT);
        assertFalse(b1.equals(b2));
        b2.add(new EmptyBlock(2.0, 2.0), RectangleEdge.RIGHT);
        assertTrue(b1.equals(b2));

        b1.add(new EmptyBlock(3.0, 3.0), RectangleEdge.TOP);
        assertFalse(b1.equals(b2));
        b2.add(new EmptyBlock(3.0, 3.0), RectangleEdge.TOP);
        assertTrue(b1.equals(b2));

        b1.add(new EmptyBlock(4.0, 4.0), RectangleEdge.BOTTOM);
        assertFalse(b1.equals(b2));
        b2.add(new EmptyBlock(4.0, 4.0), RectangleEdge.BOTTOM);
        assertTrue(b1.equals(b2));
    }

    /**
     * Immutable - cloning is not necessary.
     */
    public void testCloning() {
        BorderArrangement b1 = new BorderArrangement();
        assertFalse(b1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        BorderArrangement b1 = new BorderArrangement();
        BorderArrangement b2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(b1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            b2 = (BorderArrangement) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(b1, b2);
    }

    /**
     * Run some checks on sizing.
     */
    public void testSizing() {
        BlockContainer container = new BlockContainer(new BorderArrangement());
        BufferedImage image = new BufferedImage(200, 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        // TBLRC
        // 00000 - no items
        Size2D size = container.arrange(g2);
        assertEquals(0.0, size.width, EPSILON);
        assertEquals(0.0, size.height, EPSILON);

        // TBLRC
        // 00001 - center item only
        container.add(new EmptyBlock(123.4, 567.8));
        size = container.arrange(g2);
        assertEquals(123.4, size.width, EPSILON);
        assertEquals(567.8, size.height, EPSILON);

        // TBLRC
        // 00010 - right item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00011 - right and center items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(22.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // try case where right item is shorter than center item
        container.clear();
        Block rb = new EmptyBlock(12.3, 15.6);
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(rb, RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(22.3, size.width, EPSILON);
        assertEquals(20.0, size.height, EPSILON);
        assertEquals(20.0, rb.getBounds().getHeight(), EPSILON);

        // TBLRC
        // 00100 - left item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00101 - left and center items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(22.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // try case where left item is shorter than center item
        container.clear();
        Block lb = new EmptyBlock(12.3, 15.6);
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(lb, RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(22.3, size.width, EPSILON);
        assertEquals(20.0, size.height, EPSILON);
        assertEquals(20.0, lb.getBounds().getHeight(), EPSILON);

        // TBLRC
        // 00110 - left and right items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(22.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00111 - left, right and center items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        container.add(new EmptyBlock(5.4, 3.2), RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(27.7, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 01000 - bottom item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 01001 - bottom and center only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01010 - bottom and right only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01011 - bottom, right and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(31.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01100
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01101 - bottom, left and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(31.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01110 - bottom. left and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(31.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01111
        container.clear();
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(14.0, size.height, EPSILON);

        // TBLRC
        // 10000 - top item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 10001 - top and center only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10010 - right and top only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10011 - top, right and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(33.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10100 - top and left only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10101 - top, left and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(33.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10110 - top, left and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2);
        assertEquals(33.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10111
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(12.0, size.height, EPSILON);

        // TBLRC
        // 11000 - top and bottom only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(12.3, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 11001
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11010 - top, bottom and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11011
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2);
        assertEquals(16.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 11100
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.LEFT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11101
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2);
        assertEquals(14.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 11110
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        size = container.arrange(g2);
        assertEquals(12.0, size.width, EPSILON);
        assertEquals(14.0, size.height, EPSILON);

        // TBLRC
        // 11111 - all
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2);
        assertEquals(21.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

    }

    /**
     * Run some checks on sizing when there is a fixed width constraint.
     */
    public void testSizingWithWidthConstraint() {
        RectangleConstraint constraint = new RectangleConstraint(
                10.0, new Range(10.0, 10.0), LengthConstraintType.FIXED,
                0.0, new Range(0.0, 0.0), LengthConstraintType.NONE);

        BlockContainer container = new BlockContainer(new BorderArrangement());
        BufferedImage image = new BufferedImage(200, 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        // TBLRC
        // 00001 - center item only
        container.add(new EmptyBlock(5.0, 6.0));
        Size2D size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(6.0, size.height, EPSILON);

        container.clear();
        container.add(new EmptyBlock(15.0, 16.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 00010 - right item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00011 - right and center items
        container.clear();
        container.add(new EmptyBlock(7.0, 20.0));
        container.add(new EmptyBlock(8.0, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00100 - left item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00101 - left and center items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00110 - left and right items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 00111 - left, right and center items
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        container.add(new EmptyBlock(5.4, 3.2), RectangleEdge.RIGHT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 01000 - bottom item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 01001 - bottom and center only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01010 - bottom and right only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01011 - bottom, right and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01100
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01101 - bottom, left and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01110 - bottom. left and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 01111
        container.clear();
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(14.0, size.height, EPSILON);

        // TBLRC
        // 10000 - top item only
        container.clear();
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(45.6, size.height, EPSILON);

        // TBLRC
        // 10001 - top and center only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0));
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10010 - right and top only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10011 - top, right and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.RIGHT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10100 - top and left only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.TOP);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10101 - top, left and center
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10110 - top, left and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.LEFT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 10111
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(12.0, size.height, EPSILON);

        // TBLRC
        // 11000 - top and bottom only
        container.clear();
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(65.6, size.height, EPSILON);

        // TBLRC
        // 11001
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3));
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11010 - top, bottom and right
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11011
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 11100
        container.clear();
        container.add(new EmptyBlock(21.0, 12.3), RectangleEdge.LEFT);
        container.add(new EmptyBlock(10.0, 20.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(12.3, 45.6), RectangleEdge.BOTTOM);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(77.9, size.height, EPSILON);

        // TBLRC
        // 11101
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 11110
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(14.0, size.height, EPSILON);

        // TBLRC
        // 11111 - all
        container.clear();
        container.add(new EmptyBlock(1.0, 2.0), RectangleEdge.TOP);
        container.add(new EmptyBlock(3.0, 4.0), RectangleEdge.BOTTOM);
        container.add(new EmptyBlock(5.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(7.0, 8.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(9.0, 10.0));
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(16.0, size.height, EPSILON);

        // TBLRC
        // 00000 - no items
        container.clear();
        size = container.arrange(g2, constraint);
        assertEquals(10.0, size.width, EPSILON);
        assertEquals(0.0, size.height, EPSILON);

    }

    /**
     * This test is for a particular bug that arose just prior to the release
     * of JFreeChart 1.0.10.  A BorderArrangement with LEFT, CENTRE and RIGHT
     * blocks that is too wide, by default, for the available space, wasn't
     * shrinking the centre block as expected.
     */
    public void testBugX() {
        RectangleConstraint constraint = new RectangleConstraint(
                new Range(0.0, 200.0), new Range(0.0, 100.0));
        BlockContainer container = new BlockContainer(new BorderArrangement());
        BufferedImage image = new BufferedImage(200, 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();

        container.add(new EmptyBlock(10.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(20.0, 6.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(30.0, 6.0));
        Size2D size = container.arrange(g2, constraint);
        assertEquals(60.0, size.width, EPSILON);
        assertEquals(6.0, size.height, EPSILON);

        container.clear();
        container.add(new EmptyBlock(10.0, 6.0), RectangleEdge.LEFT);
        container.add(new EmptyBlock(20.0, 6.0), RectangleEdge.RIGHT);
        container.add(new EmptyBlock(300.0, 6.0));
        size = container.arrange(g2, constraint);
        assertEquals(200.0, size.width, EPSILON);
        assertEquals(6.0, size.height, EPSILON);


    }
}

