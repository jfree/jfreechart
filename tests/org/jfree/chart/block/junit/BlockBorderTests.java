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
 * ---------------------
 * BlockBorderTests.java
 * ---------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Feb-2005 : Version 1 (DG);
 * 23-Feb-2005 : Extended equals() test (DG);
 *
 */

package org.jfree.chart.block.junit;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.block.BlockBorder;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.UnitType;

/**
 * Tests for the {@link BlockBorder} class.
 */
public class BlockBorderTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(BlockBorderTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public BlockBorderTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    public void testEquals() {
        BlockBorder b1 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0,
                4.0), Color.red);
        BlockBorder b2 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0,
                4.0), Color.red);
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b2));

        // insets
        b1 = new BlockBorder(new RectangleInsets(UnitType.RELATIVE, 1.0, 2.0,
                3.0, 4.0), Color.red);
        assertFalse(b1.equals(b2));
        b2 = new BlockBorder(new RectangleInsets(UnitType.RELATIVE, 1.0, 2.0,
                3.0, 4.0), Color.red);
        assertTrue(b1.equals(b2));

        // paint
        b1 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0, 4.0),
                Color.blue);
        assertFalse(b1.equals(b2));
        b2 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0, 4.0),
                Color.blue);
        assertTrue(b1.equals(b2));
    }

    /**
     * Immutable - cloning not necessary.
     */
    public void testCloning() {
        BlockBorder b1 = new BlockBorder();
        assertFalse(b1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        BlockBorder b1 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0,
                4.0), new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.yellow));
        BlockBorder b2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(b1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            b2 = (BlockBorder) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertTrue(b1.equals(b2));
    }

}
