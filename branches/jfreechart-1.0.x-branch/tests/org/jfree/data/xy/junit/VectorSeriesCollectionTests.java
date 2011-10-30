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
 * --------------------------------
 * VectorSeriesCollectionTests.java
 * --------------------------------
 * (C) Copyright 2007, 2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jan-2007 : Version 1 (DG);
 * 24-May-2007 : Added testRemoveSeries() (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 *
 */

package org.jfree.data.xy.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link VectorSeriesCollection} class.
 */
public class VectorSeriesCollectionTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(VectorSeriesCollectionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public VectorSeriesCollectionTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeries s2 = new VectorSeries("Series");
        s2.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c2 = new VectorSeriesCollection();
        c2.addSeries(s2);
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c1));

        c1.addSeries(new VectorSeries("Empty Series"));
        assertFalse(c1.equals(c2));

        c2.addSeries(new VectorSeries("Empty Series"));
        assertTrue(c1.equals(c2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeriesCollection c2 = null;
        try {
            c2 = (VectorSeriesCollection) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        s1.setDescription("XYZ");
        assertFalse(c1.equals(c2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    public void testPublicCloneable() {
        VectorSeriesCollection d1 = new VectorSeriesCollection();
        assertTrue(d1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        VectorSeries s1 = new VectorSeries("Series");
        s1.add(1.0, 1.1, 1.2, 1.3);
        VectorSeriesCollection c1 = new VectorSeriesCollection();
        c1.addSeries(s1);
        VectorSeriesCollection c2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            c2 = (VectorSeriesCollection) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);
    }

    /**
     * Some checks for the removeSeries() method.
     */
    public void testRemoveSeries() {
        VectorSeries s1 = new VectorSeries("S1");
        VectorSeries s2 = new VectorSeries("S2");
        VectorSeriesCollection vsc = new VectorSeriesCollection();
        vsc.addSeries(s1);
        vsc.addSeries(s2);
        assertEquals(2, vsc.getSeriesCount());
        boolean b = vsc.removeSeries(s1);
        assertTrue(b);
        assertEquals(1, vsc.getSeriesCount());
        assertEquals("S2", vsc.getSeriesKey(0));
        b = vsc.removeSeries(new VectorSeries("NotInDataset"));
        assertFalse(b);
        assertEquals(1, vsc.getSeriesCount());
        b = vsc.removeSeries(s2);
        assertTrue(b);
        assertEquals(0, vsc.getSeriesCount());
    }

}
