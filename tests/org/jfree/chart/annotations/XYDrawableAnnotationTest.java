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
 * -----------------------------
 * XYDrawableAnnotationTest.java
 * -----------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Aug-2003 : Version 1 (DG);
 * 01-Oct-2004 : Fixed bugs in tests (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.annotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.chart.TestUtilities;

import org.jfree.ui.Drawable;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link XYDrawableAnnotation} class.
 */
public class XYDrawableAnnotationTest {

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
        @Override
        public void draw(Graphics2D g2, Rectangle2D area) {
            // do nothing
        }
        /**
         * Tests this object for equality with an arbitrary object.
         * @param obj  the object to test against (<code>null</code> permitted).
         * @return A boolean.
         */
        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof TestDrawable)) {
                return false;
            }
            return true;
        }
        /**
         * Returns a clone.
         *
         * @return A clone.
         *
         * @throws CloneNotSupportedException if there is a problem cloning.
         */
        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        XYDrawableAnnotation a2 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        assertTrue(a1.equals(a2));

        a1 = new XYDrawableAnnotation(11.0, 20.0, 100.0, 200.0,
                new TestDrawable());
        assertFalse(a1.equals(a2));
        a2 = new XYDrawableAnnotation(11.0, 20.0, 100.0, 200.0,
                new TestDrawable());
        assertTrue(a1.equals(a2));

        a1 = new XYDrawableAnnotation(11.0, 22.0, 100.0, 200.0,
                new TestDrawable());
        assertFalse(a1.equals(a2));
        a2 = new XYDrawableAnnotation(11.0, 22.0, 100.0, 200.0,
                new TestDrawable());
        assertTrue(a1.equals(a2));

        a1 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 200.0,
                new TestDrawable());
        assertFalse(a1.equals(a2));
        a2 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 200.0,
                new TestDrawable());
        assertTrue(a1.equals(a2));

        a1 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 202.0,
                new TestDrawable());
        assertFalse(a1.equals(a2));
        a2 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 202.0,
                new TestDrawable());
        assertTrue(a1.equals(a2));

        a1 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 202.0, 2.0,
                new TestDrawable());
        assertFalse(a1.equals(a2));
        a2 = new XYDrawableAnnotation(11.0, 22.0, 101.0, 202.0, 2.0,
                new TestDrawable());
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        XYDrawableAnnotation a2 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        XYDrawableAnnotation a2 = (XYDrawableAnnotation) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYDrawableAnnotation a1 = new XYDrawableAnnotation(10.0, 20.0, 100.0,
                200.0, new TestDrawable());
        XYDrawableAnnotation a2 = (XYDrawableAnnotation) TestUtilities.serialised(a1);
        assertEquals(a1, a2);
    }

}
