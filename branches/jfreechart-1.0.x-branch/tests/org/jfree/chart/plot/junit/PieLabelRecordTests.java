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
 * ------------------------
 * PieLabelRecordTests.java
 * ------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Nov-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.PieLabelRecord;
import org.jfree.text.TextBox;

/**
 * Some tests for the {@link CategoryMarker} class.
 */
public class PieLabelRecordTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(PieLabelRecordTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public PieLabelRecordTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        PieLabelRecord p1 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        PieLabelRecord p2 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        assertTrue(p1.equals(p2));
        assertTrue(p2.equals(p1));

        p1 = new PieLabelRecord("B", 1.0, 2.0, new TextBox("B"), 3.0, 4.0, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.0, 2.0, new TextBox("B"), 3.0, 4.0, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.0, new TextBox("B"), 3.0, 4.0, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.0, new TextBox("B"), 3.0, 4.0, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("B"), 3.0, 4.0, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("B"), 3.0, 4.0, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.0, 4.0, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.0, 4.0, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.0, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.0, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.4, 5.0);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.4, 5.0);
        assertTrue(p1.equals(p2));

        p1 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.4, 5.5);
        assertFalse(p1.equals(p2));
        p2 = new PieLabelRecord("B", 1.1, 2.2, new TextBox("C"), 3.3, 4.4, 5.5);
        assertTrue(p1.equals(p2));

    }

    /**
     * Confirm that cloning is not implemented.
     */
    public void testCloning() {
        PieLabelRecord p1 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        assertFalse(p1 instanceof Cloneable);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        PieLabelRecord p1 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        PieLabelRecord p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (PieLabelRecord) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        boolean b = p1.equals(p2);
        assertTrue(b);

    }
}
