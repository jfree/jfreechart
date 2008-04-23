/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
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
 * ------------------------------------
 * CustomCategoryURLGeneratorTests.java
 * ------------------------------------
 * (C) Copyright 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Apr-2008 : Version 1, based on CustomXYURLGeneratorTests.java (DG);
 *
 */

package org.jfree.chart.urls.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.urls.CustomCategoryURLGenerator;

/**
 * Tests for the {@link CustomCategoryURLGenerator} class.
 */
public class CustomCategoryURLGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CustomCategoryURLGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CustomCategoryURLGeneratorTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        CustomCategoryURLGenerator g1 = new CustomCategoryURLGenerator();
        CustomCategoryURLGenerator g2 = new CustomCategoryURLGenerator();
        assertTrue(g1.equals(g2));
        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");
        g1.addURLSeries(u1);
        assertFalse(g1.equals(g2));
        List u2 = new java.util.ArrayList();
        u2.add("URL A1");
        u2.add("URL A2");
        u2.add("URL A3");
        g2.addURLSeries(u2);
        assertTrue(g1.equals(g2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
    	CustomCategoryURLGenerator g1 = new CustomCategoryURLGenerator();
        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");
        g1.addURLSeries(u1);
        CustomCategoryURLGenerator g2 = null;
        try {
            g2 = (CustomCategoryURLGenerator) g1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        List u2 = new java.util.ArrayList();
        u2.add("URL XXX");
        g1.addURLSeries(u2);
        assertFalse(g1.equals(g2));
        g2.addURLSeries(new java.util.ArrayList(u2));
        assertTrue(g1.equals(g2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        List u1 = new java.util.ArrayList();
        u1.add("URL A1");
        u1.add("URL A2");
        u1.add("URL A3");

        List u2 = new java.util.ArrayList();
        u2.add("URL B1");
        u2.add("URL B2");
        u2.add("URL B3");

        CustomCategoryURLGenerator g1 = new CustomCategoryURLGenerator();
        CustomCategoryURLGenerator g2 = null;

        g1.addURLSeries(u1);
        g1.addURLSeries(u2);

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (CustomCategoryURLGenerator) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(g1, g2);

    }

    public void testAddURLSeries() {
    	CustomCategoryURLGenerator g1 = new CustomCategoryURLGenerator();
    	// you can add a null list - it would have been better if this
    	// required EMPTY_LIST
    	g1.addURLSeries(null);
    	assertEquals(1, g1.getListCount());
    	assertEquals(0, g1.getURLCount(0));

    	List list1 = new java.util.ArrayList();
    	list1.add("URL1");
    	g1.addURLSeries(list1);
    	assertEquals(2, g1.getListCount());
    	assertEquals(0, g1.getURLCount(0));
    	assertEquals(1, g1.getURLCount(1));
    	assertEquals("URL1", g1.getURL(1, 0));

    	// if we modify the original list, it's best if the URL generator is
    	// not affected
    	list1.clear();
    	assertEquals("URL1", g1.getURL(1, 0));
    }

}
