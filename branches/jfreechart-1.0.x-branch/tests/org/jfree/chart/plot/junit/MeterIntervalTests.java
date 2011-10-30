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
 * -----------------------
 * MeterIntervalTests.java
 * -----------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Mar-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot.junit;

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

import org.jfree.chart.plot.MeterInterval;
import org.jfree.data.Range;

/**
 * Tests for the {@link MeterInterval} class.
 */
public class MeterIntervalTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MeterIntervalTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MeterIntervalTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        MeterInterval m1 = new MeterInterval(
            "Label 1", new Range(1.2, 3.4), Color.red, new BasicStroke(1.0f),
            Color.blue
        );
        MeterInterval m2 = new MeterInterval(
            "Label 1", new Range(1.2, 3.4), Color.red, new BasicStroke(1.0f),
            Color.blue
        );
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));

        m1 = new MeterInterval(
            "Label 2", new Range(1.2, 3.4), Color.red, new BasicStroke(1.0f),
            Color.blue
        );
        assertFalse(m1.equals(m2));
        m2 = new MeterInterval(
            "Label 2", new Range(1.2, 3.4), Color.red, new BasicStroke(1.0f),
            Color.blue
        );
        assertTrue(m1.equals(m2));

    }

    /**
     * This class is immutable so cloning isn't required.
     */
    public void testCloning() {
        MeterInterval m1 = new MeterInterval("X", new Range(1.0, 2.0));
        assertFalse(m1 instanceof Cloneable);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        MeterInterval m1 = new MeterInterval("X", new Range(1.0, 2.0));
        MeterInterval m2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(m1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            m2 = (MeterInterval) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        boolean b = m1.equals(m2);
        assertTrue(b);

    }

}
