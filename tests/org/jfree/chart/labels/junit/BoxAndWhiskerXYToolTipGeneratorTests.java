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
 * -----------------------------------------
 * BoxAndWhiskerXYToolTipGeneratorTests.java
 * -----------------------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Aug-2003 : Version 1 (DG);
 * 27-Feb-2004 : Renamed BoxAndWhiskerItemLabelGenerator
 *               --> XYBoxAndWhiskerItemLabelGenerator (DG);
 * 23-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.labels.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.labels.BoxAndWhiskerXYToolTipGenerator;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link BoxAndWhiskerXYToolTipGenerator} class.
 */
public class BoxAndWhiskerXYToolTipGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(BoxAndWhiskerXYToolTipGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public BoxAndWhiskerXYToolTipGeneratorTests(String name) {
        super(name);
    }

    /**
     * A series of tests for the equals() method.
     */
    public void testEquals() {

        // standard test
        BoxAndWhiskerXYToolTipGenerator g1
                = new BoxAndWhiskerXYToolTipGenerator();
        BoxAndWhiskerXYToolTipGenerator g2
                = new BoxAndWhiskerXYToolTipGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g2.equals(g1));

        // tooltip format
        g1 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        assertFalse(g1.equals(g2));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        assertTrue(g1.equals(g2));

        // date format
        g1 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("MMM-yyyy"), new DecimalFormat("0.0"));
        assertFalse(g1.equals(g2));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        assertTrue(g1.equals(g2));

        // Y format
        g1 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.00"));
        assertFalse(g1.equals(g2));
        g2 = new BoxAndWhiskerXYToolTipGenerator("{0} --> {1} {2}",
                new SimpleDateFormat("yyyy"), new DecimalFormat("0.0"));
        assertTrue(g1.equals(g2));
    }

    /**
     * Simple check that hashCode is implemented.
     */
    public void testHashCode() {
        BoxAndWhiskerXYToolTipGenerator g1
                = new BoxAndWhiskerXYToolTipGenerator();
        BoxAndWhiskerXYToolTipGenerator g2
                = new BoxAndWhiskerXYToolTipGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g1.hashCode() == g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        BoxAndWhiskerXYToolTipGenerator g1
                = new BoxAndWhiskerXYToolTipGenerator();
        BoxAndWhiskerXYToolTipGenerator g2 = null;
        try {
            g2 = (BoxAndWhiskerXYToolTipGenerator) g1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));
    }

    /**
     * Check to ensure that this class implements PublicCloneable.
     */
    public void testPublicCloneable() {
        BoxAndWhiskerXYToolTipGenerator g1
                = new BoxAndWhiskerXYToolTipGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        BoxAndWhiskerXYToolTipGenerator g1
                = new BoxAndWhiskerXYToolTipGenerator();
        BoxAndWhiskerXYToolTipGenerator g2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (BoxAndWhiskerXYToolTipGenerator) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(g1, g2);

    }

}
