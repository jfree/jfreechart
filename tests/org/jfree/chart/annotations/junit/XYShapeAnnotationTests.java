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
 * ---------------------------
 * XYShapeAnnotationTests.java
 * ---------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Sep-2004 : Version 1 (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
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

import org.jfree.chart.annotations.XYShapeAnnotation;
import org.jfree.util.PublicCloneable;

/**
 * Some tests for the {@link XYShapeAnnotation} class.
 */
public class XYShapeAnnotationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYShapeAnnotationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYShapeAnnotationTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        XYShapeAnnotation a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        // shape
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        assertTrue(a1.equals(a2));

        // stroke
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), Color.red, Color.blue);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), Color.red, Color.blue);
        assertTrue(a1.equals(a2));

        GradientPaint gp1a = new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.red);
        GradientPaint gp1b = new GradientPaint(1.0f, 2.0f, Color.blue,
                3.0f, 4.0f, Color.red);
        GradientPaint gp2a = new GradientPaint(5.0f, 6.0f, Color.pink,
                7.0f, 8.0f, Color.white);
        GradientPaint gp2b = new GradientPaint(5.0f, 6.0f, Color.pink,
                7.0f, 8.0f, Color.white);

        // outlinePaint
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1a, Color.blue);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1b, Color.blue);
        assertTrue(a1.equals(a2));

        // fillPaint
        a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1a, gp2a);
        assertFalse(a1.equals(a2));
        a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.3f), gp1b, gp2b);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        XYShapeAnnotation a2 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        XYShapeAnnotation a2 = null;
        try {
            a2 = (XYShapeAnnotation) a1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        XYShapeAnnotation a1 = new XYShapeAnnotation(
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(1.2f), Color.red, Color.blue);
        XYShapeAnnotation a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            a2 = (XYShapeAnnotation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(a1, a2);
    }

}
