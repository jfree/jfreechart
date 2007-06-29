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
 * --------------------------------
 * CategoryLineAnnotationTests.java
 * --------------------------------
 * (C) Copyright 2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: CategoryLineAnnotationTests.java,v 1.1.2.1 2006/10/03 15:41:40 mungady Exp $
 *
 * Changes
 * -------
 * 29-Jul-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.annotations.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.annotations.CategoryLineAnnotation;

/**
 * Tests for the {@link CategoryLineAnnotationTests} class.
 */
public class CategoryLineAnnotationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CategoryLineAnnotationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CategoryLineAnnotationTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        
        BasicStroke s1 = new BasicStroke(1.0f);
        BasicStroke s2 = new BasicStroke(2.0f);
        CategoryLineAnnotation a1 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, s1);
        CategoryLineAnnotation a2 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, s1);
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));
        
        // category 1 
        a1.setCategory1("Category A");
        assertFalse(a1.equals(a2));
        a2.setCategory1("Category A");
        assertTrue(a1.equals(a2));

        // value 1
        a1.setValue1(0.15);
        assertFalse(a1.equals(a2));
        a2.setValue1(0.15);
        assertTrue(a1.equals(a2));
      
        // category 2 
        a1.setCategory2("Category B");
        assertFalse(a1.equals(a2));
        a2.setCategory2("Category B");
        assertTrue(a1.equals(a2));

        // value 2
        a1.setValue2(0.25);
        assertFalse(a1.equals(a2));
        a2.setValue2(0.25);
        assertTrue(a1.equals(a2));
        
        // paint
        a1.setPaint(Color.yellow);
        assertFalse(a1.equals(a2));
        a2.setPaint(Color.yellow);
        assertTrue(a1.equals(a2));
        
        // stroke
        a1.setStroke(s2);
        assertFalse(a1.equals(a2));
        a2.setStroke(s2);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashcode() {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, 
            new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, 
            new BasicStroke(1.0f));
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        CategoryLineAnnotation a1 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, 
            new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = null;
        try {
            a2 = (CategoryLineAnnotation) a1.clone();
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

        CategoryLineAnnotation a1 = new CategoryLineAnnotation(
            "Category 1", 1.0, "Category 2", 2.0, Color.red, 
            new BasicStroke(1.0f));
        CategoryLineAnnotation a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            a2 = (CategoryLineAnnotation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(a1, a2);

    }

}
