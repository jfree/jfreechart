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
 * --------------------
 * ImageTitleTests.java
 * --------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 17-Feb-2004 : Version 1 (DG);
 * 21-Mar-2008 : Added tests for arrange method (DG);
 *
 */

package org.jfree.chart.title.junit;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.ImageTitle;
import org.jfree.ui.Size2D;

/**
 * Tests for the {@link ImageTitle} class.
 */
public class ImageTitleTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ImageTitleTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ImageTitleTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        ImageTitle t1 = new ImageTitle(JFreeChart.INFO.getLogo());
        ImageTitle t2 = new ImageTitle(JFreeChart.INFO.getLogo());
        assertEquals(t1, t2);

        t1.setImage(new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB));
        assertFalse(t1.equals(t2));
        t2.setImage(new BufferedImage(2, 1, BufferedImage.TYPE_INT_RGB));
        // images considered equal only if they're the SAME object
        // TODO: is there a way to do a better test?
        assertFalse(t1.equals(t2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        ImageTitle t1 = new ImageTitle(JFreeChart.INFO.getLogo());
        ImageTitle t2 = new ImageTitle(JFreeChart.INFO.getLogo());
        assertTrue(t1.equals(t2));
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        ImageTitle t1 = new ImageTitle(JFreeChart.INFO.getLogo());
        ImageTitle t2 = null;
        try {
            t2 = (ImageTitle) t1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(t1 != t2);
        assertTrue(t1.getClass() == t2.getClass());
        assertTrue(t1.equals(t2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        // TODO: add serialization support for images
    }

    private static final double EPSILON = 0.00000001;

    /**
     * Check the width and height.
     */
    public void testWidthAndHeight() {
        ImageTitle t1 = new ImageTitle(JFreeChart.INFO.getLogo());
        assertEquals(100, t1.getWidth(), EPSILON);
        assertEquals(100, t1.getHeight(), EPSILON);
    }

    /**
     * Some checks for the arrange method.
     */
    public void testArrangeNN() {
        BufferedImage image = new BufferedImage(100, 100,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        ImageTitle t = new ImageTitle(JFreeChart.INFO.getLogo());
        Size2D s = t.arrange(g2);
        assertEquals(102.0, s.getWidth(), EPSILON);
        assertEquals(102.0, s.getHeight(), EPSILON);

        t.setPadding(1.0, 2.0, 3.0, 4.0);
        s = t.arrange(g2);
        assertEquals(106.0, s.getWidth(), EPSILON);
        assertEquals(104.0, s.getHeight(), EPSILON);

        t.setMargin(5.0, 6.0, 7.0, 8.0);
        s = t.arrange(g2);
        assertEquals(120.0, s.getWidth(), EPSILON);
        assertEquals(116.0, s.getHeight(), EPSILON);
    }

}
