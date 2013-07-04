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
 * -------------------------------------
 * StandardXYZToolTipGeneratorTests.java
 * -------------------------------------
 * (C) Copyright 2003-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Mar-2003 : Version 1 (DG);
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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.labels.StandardXYZToolTipGenerator;
import org.jfree.util.PublicCloneable;

/**
 * Tests for the {@link StandardXYZToolTipGenerator} class.
 */
public class StandardXYZToolTipGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StandardXYZToolTipGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StandardXYZToolTipGeneratorTests(String name) {
        super(name);
    }

    /**
     * Tests that the equals() method can distinguish all fields.
     */
    public void testEquals() {

        // some setup...
        String f1 = "{1}";
        String f2 = "{2}";
        NumberFormat xnf1 = new DecimalFormat("0.00");
        NumberFormat xnf2 = new DecimalFormat("0.000");
        NumberFormat ynf1 = new DecimalFormat("0.00");
        NumberFormat ynf2 = new DecimalFormat("0.000");
        NumberFormat znf1 = new DecimalFormat("0.00");
        NumberFormat znf2 = new DecimalFormat("0.000");

        DateFormat xdf1 = new SimpleDateFormat("d-MMM");
        DateFormat xdf2 = new SimpleDateFormat("d-MMM-yyyy");
        DateFormat ydf1 = new SimpleDateFormat("d-MMM");
        DateFormat ydf2 = new SimpleDateFormat("d-MMM-yyyy");
        DateFormat zdf1 = new SimpleDateFormat("d-MMM");
        DateFormat zdf2 = new SimpleDateFormat("d-MMM-yyyy");

        StandardXYZToolTipGenerator g1 = null;
        StandardXYZToolTipGenerator g2 = null;

        g1 = new StandardXYZToolTipGenerator(f1, xnf1, ynf1, znf1);
        g2 = new StandardXYZToolTipGenerator(f1, xnf1, ynf1, znf1);
        assertTrue(g1.equals(g2));

        // format string...
        g1 = new StandardXYZToolTipGenerator(f2, xnf1, ynf1, znf1);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xnf1, ynf1, znf1);
        assertTrue(g1.equals(g2));

        // x number format
        g1 = new StandardXYZToolTipGenerator(f2, xnf2, ynf1, znf1);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xnf2, ynf1, znf1);
        assertTrue(g1.equals(g2));

        // y number format
        g1 = new StandardXYZToolTipGenerator(f2, xnf2, ynf2, znf1);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xnf2, ynf2, znf1);
        assertTrue(g1.equals(g2));

        // z number format
        g1 = new StandardXYZToolTipGenerator(f2, xnf2, ynf2, znf2);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xnf2, ynf2, znf2);
        assertTrue(g1.equals(g2));

        g1 = new StandardXYZToolTipGenerator(f2, xdf1, ydf1, zdf1);
        g2 = new StandardXYZToolTipGenerator(f2, xdf1, ydf1, zdf1);
        assertTrue(g1.equals(g2));

        // x date format
        g1 = new StandardXYZToolTipGenerator(f2, xdf2, ydf1, zdf1);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xdf2, ydf1, zdf1);
        assertTrue(g1.equals(g2));

        // y date format
        g1 = new StandardXYZToolTipGenerator(f2, xdf2, ydf2, zdf1);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xdf2, ydf2, zdf1);
        assertTrue(g1.equals(g2));

        // z date format
        g1 = new StandardXYZToolTipGenerator(f2, xdf2, ydf2, zdf2);
        assertFalse(g1.equals(g2));
        g2 = new StandardXYZToolTipGenerator(f2, xdf2, ydf2, zdf2);
        assertTrue(g1.equals(g2));

    }

    /**
     * Simple check that hashCode is implemented.
     */
    public void testHashCode() {
        StandardXYZToolTipGenerator g1
                = new StandardXYZToolTipGenerator();
        StandardXYZToolTipGenerator g2
                = new StandardXYZToolTipGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g1.hashCode() == g2.hashCode());
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        StandardXYZToolTipGenerator g1 = new StandardXYZToolTipGenerator();
        StandardXYZToolTipGenerator g2 = null;
        try {
            g2 = (StandardXYZToolTipGenerator) g1.clone();
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
        StandardXYZToolTipGenerator g1 = new StandardXYZToolTipGenerator();
        assertTrue(g1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        StandardXYZToolTipGenerator g1 = new StandardXYZToolTipGenerator();
        StandardXYZToolTipGenerator g2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (StandardXYZToolTipGenerator) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(g1, g2);

    }

}
