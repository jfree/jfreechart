/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * -------------------
 * CrosshairTests.java
 * -------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Feb-2009 : Version 1 (DG);
 *
 *
 */

package org.jfree.chart.jxlayer.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.jxlayer.Crosshair;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link Crosshair} class.
 */
public class CrosshairTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CrosshairTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CrosshairTests(String name) {
        super(name);
    }

    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = new Crosshair(1.0);
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c1));
    }

    /**
     * Simple check that hashCode is implemented.
     */
    public void testHashCode() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = new Crosshair(1.0);
        assertTrue(c1.equals(c2));
        assertTrue(c1.hashCode() == c2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = null;
        try {
            c2 = (Crosshair) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        Crosshair c1 = new Crosshair(1.0);
        assertTrue(c1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        Crosshair c1 = new Crosshair(1.0);
        Crosshair c2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            c2 = (Crosshair) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);
    }

}
