/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -------------------------
 * XYBoxAnnotationTests.java
 * -------------------------
 * (C) Copyright 2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: XYBoxAnnotationTests.java,v 1.1.2.1 2006/10/03 15:41:40 mungady Exp $
 *
 * Changes
 * -------
 * 19-Jan-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.annotations.junit;

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

import org.jfree.chart.annotations.XYBoxAnnotation;

/**
 * Some tests for the {@link XYBoxAnnotation} class.
 */
public class XYBoxAnnotationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYBoxAnnotationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYBoxAnnotationTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        
        XYBoxAnnotation a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        XYBoxAnnotation a2 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
      
        // x0
        a1 = new XYBoxAnnotation(
            2.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        assertFalse(a1.equals(a2));
        a2 = new XYBoxAnnotation(
            2.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        assertTrue(a1.equals(a2));
        
        // stroke
        a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), Color.red, Color.blue
        );
        assertFalse(a1.equals(a2));
        a2 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), Color.red, Color.blue
        );
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
        a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), gp1a, Color.blue
        );
        assertFalse(a1.equals(a2));
        a2 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), gp1b, Color.blue
        );
        assertTrue(a1.equals(a2));
        
        // fillPaint
        a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), gp1a, gp2a
        );
        assertFalse(a1.equals(a2));
        a2 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(2.3f), gp1b, gp2b
        );
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashCode() {
        XYBoxAnnotation a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        XYBoxAnnotation a2 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {

        XYBoxAnnotation a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, new BasicStroke(1.2f), Color.red, Color.blue
        );
        XYBoxAnnotation a2 = null;
        try {
            a2 = (XYBoxAnnotation) a1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        XYBoxAnnotation a1 = new XYBoxAnnotation(
            1.0, 2.0, 3.0, 4.0, 
            new BasicStroke(1.2f), Color.red, Color.blue
        );
        XYBoxAnnotation a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            a2 = (XYBoxAnnotation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(a1, a2);

    }

}
