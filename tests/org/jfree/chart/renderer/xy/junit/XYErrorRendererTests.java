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
 * -------------------------
 * XYErrorRendererTests.java
 * -------------------------
 * (C) Copyright 2006-2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Oct-2006 : Version 1 (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 * 28-Jan-2009 : Updated tests for new errorStroke field (DG);
 *
 */

package org.jfree.chart.renderer.xy.junit;

import java.awt.BasicStroke;
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

import org.jfree.chart.renderer.xy.XYErrorRenderer;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link XYErrorRenderer} class.
 */
public class XYErrorRendererTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYErrorRendererTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYErrorRendererTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        XYErrorRenderer r2 = new XYErrorRenderer();
        assertEquals(r1, r2);

        // drawXError
        r1.setDrawXError(false);
        assertFalse(r1.equals(r2));
        r2.setDrawXError(false);
        assertTrue(r1.equals(r2));

        // drawYError
        r1.setDrawYError(false);
        assertFalse(r1.equals(r2));
        r2.setDrawYError(false);
        assertTrue(r1.equals(r2));

        // capLength
        r1.setCapLength(9.0);
        assertFalse(r1.equals(r2));
        r2.setCapLength(9.0);
        assertTrue(r1.equals(r2));

        // errorPaint
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.green));
        assertFalse(r1.equals(r2));
        r2.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.green));
        assertTrue(r1.equals(r2));

        // errorStroke
        r1.setErrorStroke(new BasicStroke(1.5f));
        assertFalse(r1.equals(r2));
        r2.setErrorStroke(new BasicStroke(1.5f));
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        XYErrorRenderer r2 = new XYErrorRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        XYErrorRenderer r2 = null;
        try {
            r2 = (XYErrorRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * A test for cloning.
     */
    public void testCloning2() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorStroke(new BasicStroke(1.5f));
        XYErrorRenderer r2 = null;
        try {
            r2 = (XYErrorRenderer) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        XYErrorRenderer r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (XYErrorRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(r1, r2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization2() {
        XYErrorRenderer r1 = new XYErrorRenderer();
        r1.setErrorStroke(new BasicStroke(1.5f));
        XYErrorRenderer r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            r2 = (XYErrorRenderer) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(r1, r2);
    }

    /**
     * Some checks for the findDomainBounds() method.
     */
    public void testFindDomainBounds() {
        XYErrorRenderer r = new XYErrorRenderer();
        assertNull(r.findDomainBounds(null));
    }


    /**
     * Some checks for the findRangeBounds() method.
     */
    public void testFindRangeBounds() {
        XYErrorRenderer r = new XYErrorRenderer();
        assertNull(r.findRangeBounds(null));
    }
}
