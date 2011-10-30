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
 * SymbolicAxisTests.java
 * ----------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 25-Jul-2007 : Added new field in testEquals() (DG);
 *
 */

package org.jfree.chart.axis.junit;

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

import org.jfree.chart.axis.SymbolAxis;

/**
 * Tests for the {@link SymbolAxis} class.
 */
public class SymbolAxisTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(SymbolAxisTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public SymbolAxisTests(String name) {
        super(name);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        String[] tickLabels = new String[] {"One", "Two", "Three"};
        SymbolAxis a1 = new SymbolAxis("Test Axis", tickLabels);
        SymbolAxis a2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(a1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            a2 = (SymbolAxis) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(a1, a2);

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = null;
        try {
            a2 = (SymbolAxis) a1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = new SymbolAxis("Axis", new String[] {"A", "B"});
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        a1 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertFalse(a1.equals(a2));
        a2 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertTrue(a1.equals(a2));

        a1 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertFalse(a1.equals(a2));
        a2 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertTrue(a1.equals(a2));

        a1.setGridBandsVisible(false);
        assertFalse(a1.equals(a2));
        a2.setGridBandsVisible(false);
        assertTrue(a1.equals(a2));

        a1.setGridBandPaint(Color.black);
        assertFalse(a1.equals(a2));
        a2.setGridBandPaint(Color.black);
        assertTrue(a1.equals(a2));

        a1.setGridBandAlternatePaint(Color.red);
        assertFalse(a1.equals(a2));
        a2.setGridBandAlternatePaint(Color.red);
        assertTrue(a1.equals(a2));
    }

}
