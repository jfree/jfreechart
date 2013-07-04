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
 * ----------------------------------
 * MeanAndStandardDeviationTests.java
 * ----------------------------------
 * (C) Copyright 2005-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Feb-2005 : Version 1 (DG);
 *
 */

package org.jfree.data.statistics.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.statistics.MeanAndStandardDeviation;

/**
 * Tests for the {@link MeanAndStandardDeviation} class.
 */
public class MeanAndStandardDeviationTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MeanAndStandardDeviationTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MeanAndStandardDeviationTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        MeanAndStandardDeviation m2 = new MeanAndStandardDeviation(1.2, 3.4);
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));

        m1 = new MeanAndStandardDeviation(1.0, 3.4);
        assertFalse(m1.equals(m2));
        m2 = new MeanAndStandardDeviation(1.0, 3.4);
        assertTrue(m1.equals(m2));

        m1 = new MeanAndStandardDeviation(1.0, 3.0);
        assertFalse(m1.equals(m2));
        m2 = new MeanAndStandardDeviation(1.0, 3.0);
        assertTrue(m1.equals(m2));
    }

    /**
     * Immutable class - should not be cloneable.
     */
    public void testCloning() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        assertFalse(m1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        MeanAndStandardDeviation m2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(m1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            m2 = (MeanAndStandardDeviation) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(m1, m2);

    }
}
