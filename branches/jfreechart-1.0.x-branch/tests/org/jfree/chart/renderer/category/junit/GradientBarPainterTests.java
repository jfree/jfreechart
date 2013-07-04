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
 * ----------------------------
 * GradientBarPainterTests.java
 * ----------------------------
 * (C) Copyright 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Jun-2008 : Version 1 (DG);
 *
 */

package org.jfree.chart.renderer.category.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link GradientBarPainter} class.
 */
public class GradientBarPainterTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(GradientBarPainterTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public GradientBarPainterTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        GradientBarPainter p1 = new GradientBarPainter(0.1, 0.2, 0.3);
        GradientBarPainter p2 = new GradientBarPainter(0.1, 0.2, 0.3);
        assertEquals(p1, p2);

        p1 = new GradientBarPainter(0.11, 0.2, 0.3);
        assertFalse(p1.equals(p2));
        p2 = new GradientBarPainter(0.11, 0.2, 0.3);
        assertTrue(p1.equals(p2));

        p1 = new GradientBarPainter(0.11, 0.22, 0.3);
        assertFalse(p1.equals(p2));
        p2 = new GradientBarPainter(0.11, 0.22, 0.3);
        assertTrue(p1.equals(p2));

        p1 = new GradientBarPainter(0.11, 0.22, 0.33);
        assertFalse(p1.equals(p2));
        p2 = new GradientBarPainter(0.11, 0.22, 0.33);
        assertTrue(p1.equals(p2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        GradientBarPainter p1 = new GradientBarPainter(0.1, 0.2, 0.3);
        GradientBarPainter p2 = new GradientBarPainter(0.1, 0.2, 0.3);
        assertTrue(p1.equals(p2));
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning isn't implemented (it isn't required, because
     * instances of the class are immutable).
     */
    public void testCloning() {
        GradientBarPainter p1 = new GradientBarPainter(0.1, 0.2, 0.3);
        assertFalse(p1 instanceof Cloneable);
        assertFalse(p1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        GradientBarPainter p1 = new GradientBarPainter(0.1, 0.2, 0.3);
        GradientBarPainter p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (GradientBarPainter) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(p1, p2);
    }

}
