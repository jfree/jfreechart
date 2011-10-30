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
 * -------------------------
 * SubCategoryAxisTests.java
 * -------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 12-May-2004 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() (DG);
 * 13-Nov-2008 : Added test2275695() (DG);
 *
 */

package org.jfree.chart.axis.junit;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

/**
 * Tests for the {@link SubCategoryAxis} class.
 */
public class SubCategoryAxisTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(SubCategoryAxisTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public SubCategoryAxisTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        SubCategoryAxis a1 = new SubCategoryAxis("Test");
        SubCategoryAxis a2 = new SubCategoryAxis("Test");
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        // subcategories
        a1.addSubCategory("Sub 1");
        assertFalse(a1.equals(a2));
        a2.addSubCategory("Sub 1");
        assertTrue(a1.equals(a2));

        // subLabelFont
        a1.setSubLabelFont(new Font("Serif", Font.BOLD, 15));
        assertFalse(a1.equals(a2));
        a2.setSubLabelFont(new Font("Serif", Font.BOLD, 15));
        assertTrue(a1.equals(a2));

        // subLabelPaint
        a1.setSubLabelPaint(Color.red);
        assertFalse(a1.equals(a2));
        a2.setSubLabelPaint(Color.red);
        assertTrue(a1.equals(a2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        SubCategoryAxis a1 = new SubCategoryAxis("Test");
        SubCategoryAxis a2 = new SubCategoryAxis("Test");
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        SubCategoryAxis a1 = new SubCategoryAxis("Test");
        a1.addSubCategory("SubCategoryA");
        SubCategoryAxis a2 = null;
        try {
            a2 = (SubCategoryAxis) a1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        SubCategoryAxis a1 = new SubCategoryAxis("Test Axis");
        a1.addSubCategory("SubCategoryA");
        SubCategoryAxis a2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            a2 = (SubCategoryAxis) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(a1, a2);
    }

    /**
     * A check for the NullPointerException in bug 2275695.
     */
    public void test2275695() {
        JFreeChart chart = ChartFactory.createStackedBarChart("Test",
                "Category", "Value", null, PlotOrientation.VERTICAL,
                true, false, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setDomainAxis(new SubCategoryAxis("SubCategoryAxis"));
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(200 , 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        assertTrue(success);
    }

}
