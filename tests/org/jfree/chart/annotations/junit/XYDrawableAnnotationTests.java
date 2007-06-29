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
 * ------------------------------
 * XYDrawableAnnotationTests.java
 * ------------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: XYDrawableAnnotationTests.java,v 1.1.2.1 2006/10/03 15:41:41 mungady Exp $
 *
 * Changes
 * -------
 * 19-Aug-2003 : Version 1 (DG);
 * 01-Oct-2004 : Fixed bugs in tests (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 *
 */

package org.jfree.chart.annotations.junit;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.annotations.XYDrawableAnnotation;
import org.jfree.ui.Drawable;

/**
 * Tests for the {@link XYDrawableAnnotation} class.
 */
public class XYDrawableAnnotationTests extends TestCase {

    static class TestDrawable implements Drawable, Cloneable, Serializable {
        /**
         * Default constructor.
         */
        public TestDrawable() {
        }
        /**
         * Draws something.
         * @param g2  the graphics device.
         * @param area  the area in which to draw.
         */
        public void draw(Graphics2D g2, Rectangle2D area) {
            // do nothing
        }
        /**
         * Tests this object for equality with an arbitrary object.
         * @param obj  the object to test against (<code>null</code> permitted).
         * @return A boolean.
         */
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TestDrawable)) {
                return false;
            }
            return true;
        }
    }
    
    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(XYDrawableAnnotationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public XYDrawableAnnotationTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {    
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
        );
        XYDrawableAnnotation a2 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
        );
        assertTrue(a1.equals(a2));
    }
    
    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashCode() {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
        );
        XYDrawableAnnotation a2 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
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
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
        );
        XYDrawableAnnotation a2 = null;
        try {
            a2 = (XYDrawableAnnotation) a1.clone();
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

        XYDrawableAnnotation a1 = new XYDrawableAnnotation(
            10.0, 20.0, 100.0, 200.0, new TestDrawable()
        );
        XYDrawableAnnotation a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            a2 = (XYDrawableAnnotation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(a1, a2);

    }

}
