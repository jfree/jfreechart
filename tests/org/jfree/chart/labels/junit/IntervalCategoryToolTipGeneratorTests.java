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
 * ------------------------------------------
 * IntervalCategoryToolTipGeneratorTests.java
 * ------------------------------------------
 * (C) Copyright 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 07-Oct-2008 : Version 1, based on
 *               IntervalCategoryItemLabelGeneratorTests (DG);
 *
 */

package org.jfree.chart.labels.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.labels.IntervalCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link IntervalCategoryToolTipGenerator} class.
 */
public class IntervalCategoryToolTipGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(IntervalCategoryToolTipGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public IntervalCategoryToolTipGeneratorTests(String name) {
        super(name);
    }

    /**
     * Tests the equals() method.
     */
    public void testEquals() {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator();
        IntervalCategoryToolTipGenerator g2
                = new IntervalCategoryToolTipGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g2.equals(g1));

        g1 = new IntervalCategoryToolTipGenerator("{3} - {4}",
                new DecimalFormat("0.000"));
        assertFalse(g1.equals(g2));
        g2 = new IntervalCategoryToolTipGenerator("{3} - {4}",
                new DecimalFormat("0.000"));
        assertTrue(g1.equals(g2));

        g1 = new IntervalCategoryToolTipGenerator("{3} - {4}",
                new SimpleDateFormat("d-MMM"));
        assertFalse(g1.equals(g2));
        g2 = new IntervalCategoryToolTipGenerator("{3} - {4}",
                new SimpleDateFormat("d-MMM"));
        assertTrue(g1.equals(g2));
    }

    /**
     * Check that the subclass is not equal to an instance of the superclass.
     */
    public void testEquals2() {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator();
        StandardCategoryToolTipGenerator g2
                = new StandardCategoryToolTipGenerator(
                IntervalCategoryToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT_STRING,
                NumberFormat.getInstance());
        assertFalse(g1.equals(g2));
    }

    /**
     * Simple check that hashCode is implemented.
     */
    public void testHashCode() {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator();
        IntervalCategoryToolTipGenerator g2
                = new IntervalCategoryToolTipGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g1.hashCode() == g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() throws CloneNotSupportedException {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator();
        IntervalCategoryToolTipGenerator g2 
                = (IntervalCategoryToolTipGenerator) g1.clone();
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() throws IOException, ClassNotFoundException {
        IntervalCategoryToolTipGenerator g1
                = new IntervalCategoryToolTipGenerator("{3} - {4}",
                DateFormat.getInstance());
        IntervalCategoryToolTipGenerator g2;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(buffer);
        out.writeObject(g1);
        out.close();
        ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                buffer.toByteArray()));
        g2 = (IntervalCategoryToolTipGenerator) in.readObject();
        in.close();
        assertEquals(g1, g2);
    }

}
