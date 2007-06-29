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
 * --------------
 * AxisTests.java
 * --------------
 * (C) Copyright 2003-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: AxisTests.java,v 1.1.2.1 2006/10/03 15:41:23 mungady Exp $
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 06-Jan-2004 : Added tests for axis line attributes (DG);
 * 07-Jan-2005 : Added hashCode() test (DG);
 */

package org.jfree.chart.axis.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.ui.RectangleInsets;

/**
 * Tests for the {@link Axis} class.
 */
public class AxisTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(AxisTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public AxisTests(String name) {
        super(name);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        CategoryAxis a1 = new CategoryAxis("Test");
        a1.setAxisLinePaint(Color.red);
        CategoryAxis a2 = null;
        try {
            a2 = (CategoryAxis) a1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        
        Axis a1 = new CategoryAxis("Test");
        Axis a2 = new CategoryAxis("Test");
        assertTrue(a1.equals(a2));
        
        // visible flag...
        a1.setVisible(false);
        assertFalse(a1.equals(a2));
        a2.setVisible(false);
        assertTrue(a1.equals(a2));
                
        // label...
        a1.setLabel("New Label");
        assertFalse(a1.equals(a2));
        a2.setLabel("New Label");
        assertTrue(a1.equals(a2));

        // label font...
        a1.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertFalse(a1.equals(a2));
        a2.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
        assertTrue(a1.equals(a2));

        // label paint...
        a1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.white, 
                3.0f, 4.0f, Color.black));
        assertFalse(a1.equals(a2));
        a2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.white, 
                3.0f, 4.0f, Color.black));
        assertTrue(a1.equals(a2));
       
        // label insets...
        a1.setLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertFalse(a1.equals(a2));
        a2.setLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertTrue(a1.equals(a2));

        // label angle...
        a1.setLabelAngle(1.23);
        assertFalse(a1.equals(a2));
        a2.setLabelAngle(1.23);
        assertTrue(a1.equals(a2));

        // axis line visible...
        a1.setAxisLineVisible(false);
        assertFalse(a1.equals(a2));
        a2.setAxisLineVisible(false);
        assertTrue(a1.equals(a2));
        
        // axis line stroke...
        BasicStroke s = new BasicStroke(1.1f);
        a1.setAxisLineStroke(s);
        assertFalse(a1.equals(a2));
        a2.setAxisLineStroke(s);
        assertTrue(a1.equals(a2));
        
        // axis line paint...
        a1.setAxisLinePaint(new GradientPaint(1.0f, 2.0f, Color.red, 
                3.0f, 4.0f, Color.black));
        assertFalse(a1.equals(a2));
        a2.setAxisLinePaint(new GradientPaint(1.0f, 2.0f, Color.red, 
                3.0f, 4.0f, Color.black));
        assertTrue(a1.equals(a2));
        
        // tick labels visible flag...
        a1.setTickLabelsVisible(false);
        assertFalse(a1.equals(a2));
        a2.setTickLabelsVisible(false);
        assertTrue(a1.equals(a2));
                
        // tick label font...
        a1.setTickLabelFont(new Font("Dialog", Font.PLAIN, 12));
        assertFalse(a1.equals(a2));
        a2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 12));
        assertTrue(a1.equals(a2));

        // tick label paint...
        a1.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.yellow, 
                3.0f, 4.0f, Color.black));
        assertFalse(a1.equals(a2));
        a2.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.yellow, 
                3.0f, 4.0f, Color.black));
        assertTrue(a1.equals(a2));

        // tick label insets...
        a1.setTickLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertFalse(a1.equals(a2));
        a2.setTickLabelInsets(new RectangleInsets(10.0, 10.0, 10.0, 10.0));
        assertTrue(a1.equals(a2));

        // tick marks visible flag...
        a1.setTickMarksVisible(true);
        assertFalse(a1.equals(a2));
        a2.setTickMarksVisible(true);
        assertTrue(a1.equals(a2));
                
        // tick mark inside length...
        a1.setTickMarkInsideLength(1.23f);
        assertFalse(a1.equals(a2));
        a2.setTickMarkInsideLength(1.23f);
        assertTrue(a1.equals(a2));

        // tick mark outside length...
        a1.setTickMarkOutsideLength(1.23f);
        assertFalse(a1.equals(a2));
        a2.setTickMarkOutsideLength(1.23f);
        assertTrue(a1.equals(a2));

        // tick mark stroke...
        a1.setTickMarkStroke(new BasicStroke(2.0f));
        assertFalse(a1.equals(a2));
        a2.setTickMarkStroke(new BasicStroke(2.0f));
        assertTrue(a1.equals(a2));

        // tick mark paint...
        a1.setTickMarkPaint(new GradientPaint(1.0f, 2.0f, Color.cyan, 
                3.0f, 4.0f, Color.black));
        assertFalse(a1.equals(a2));
        a2.setTickMarkPaint(new GradientPaint(1.0f, 2.0f, Color.cyan, 
                3.0f, 4.0f, Color.black));
        assertTrue(a1.equals(a2));

        // tick mark outside length...
        a1.setFixedDimension(3.21f);
        assertFalse(a1.equals(a2));
        a2.setFixedDimension(3.21f);
        assertTrue(a1.equals(a2));

    }
    
    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashCode() {
        Axis a1 = new CategoryAxis("Test");
        Axis a2 = new CategoryAxis("Test");
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

}
