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
 * ----------------------
 * CategoryTickTests.java
 * ----------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-May-2004 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() (DG);
 *
 */

package org.jfree.chart.axis.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.CategoryTick;
import org.jfree.text.TextBlock;
import org.jfree.text.TextBlockAnchor;
import org.jfree.text.TextLine;
import org.jfree.ui.TextAnchor;

/**
 * Tests for the {@link CategoryTick} class.
 */
public class CategoryTickTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CategoryTickTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CategoryTickTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        Comparable c1 = "C1";
        Comparable c2 = "C2";
        TextBlock tb1 = new TextBlock();
        tb1.addLine(new TextLine("Block 1"));
        TextBlock tb2 = new TextBlock();
        tb1.addLine(new TextLine("Block 2"));
        TextBlockAnchor tba1 = TextBlockAnchor.CENTER;
        TextBlockAnchor tba2 = TextBlockAnchor.BOTTOM_CENTER;
        TextAnchor ta1 = TextAnchor.CENTER;
        TextAnchor ta2 = TextAnchor.BASELINE_LEFT;

        CategoryTick t1 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        CategoryTick t2 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        assertTrue(t1.equals(t2));

        t1 = new CategoryTick(c2, tb1, tba1, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb1, tba1, ta1, 1.0f);
        assertTrue(t1.equals(t2));

        t1 = new CategoryTick(c2, tb2, tba1, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba1, ta1, 1.0f);
        assertTrue(t1.equals(t2));

        t1 = new CategoryTick(c2, tb2, tba2, ta1, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta1, 1.0f);
        assertTrue(t1.equals(t2));

        t1 = new CategoryTick(c2, tb2, tba2, ta2, 1.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta2, 1.0f);
        assertTrue(t1.equals(t2));

        t1 = new CategoryTick(c2, tb2, tba2, ta2, 2.0f);
        assertFalse(t1.equals(t2));
        t2 = new CategoryTick(c2, tb2, tba2, ta2, 2.0f);
        assertTrue(t1.equals(t2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashCode() {
        Comparable c1 = "C1";
        TextBlock tb1 = new TextBlock();
        tb1.addLine(new TextLine("Block 1"));
        tb1.addLine(new TextLine("Block 2"));
        TextBlockAnchor tba1 = TextBlockAnchor.CENTER;
        TextAnchor ta1 = TextAnchor.CENTER;

        CategoryTick t1 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        CategoryTick t2 = new CategoryTick(c1, tb1, tba1, ta1, 1.0f);
        assertTrue(t1.equals(t2));
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        CategoryTick t1 = new CategoryTick(
            "C1", new TextBlock(), TextBlockAnchor.CENTER,
            TextAnchor.CENTER, 1.5f
        );
        CategoryTick t2 = null;
        try {
            t2 = (CategoryTick) t1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(t1 != t2);
        assertTrue(t1.getClass() == t2.getClass());
        assertTrue(t1.equals(t2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        CategoryTick t1 = new CategoryTick("C1", new TextBlock(),
                TextBlockAnchor.CENTER, TextAnchor.CENTER, 1.5f);
        CategoryTick t2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(t1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            t2 = (CategoryTick) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(t1, t2);

    }

}
