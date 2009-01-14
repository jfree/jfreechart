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
 * LogFormatTests.java
 * -------------------
 * (C) Copyright 2008, 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Feb-2008 : Version 1 (DG);
 * 14-Jan-2009 : Updated testEquals() for new field (DG);
 *
 */

package org.jfree.chart.util.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.util.LogFormat;

/**
 * Tests for the {@link LogFormat} class.
 */
public class LogFormatTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(LogFormatTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public LogFormatTests(String name) {
        super(name);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    public void testEquals() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = new LogFormat(10.0, "10", true);
        assertEquals(f1, f2);

        f1 = new LogFormat(11.0, "10", true);
        assertFalse(f1.equals(f2));
        f2 = new LogFormat(11.0, "10", true);
        assertTrue(f1.equals(f2));

        f1 = new LogFormat(11.0, "11", true);
        assertFalse(f1.equals(f2));
        f2 = new LogFormat(11.0, "11", true);
        assertTrue(f1.equals(f2));

        f1 = new LogFormat(11.0, "11", false);
        assertFalse(f1.equals(f2));
        f2 = new LogFormat(11.0, "11", false);
        assertTrue(f1.equals(f2));

        f1.setExponentFormat(new DecimalFormat("0.000"));
        assertFalse(f1.equals(f2));
        f2.setExponentFormat(new DecimalFormat("0.000"));
        assertTrue(f1.equals(f2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    public void testHashcode() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = new LogFormat(10.0, "10", true);
        assertTrue(f1.equals(f2));
        int h1 = f1.hashCode();
        int h2 = f2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = null;
        try {
            f2 = (LogFormat) f1.clone();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(f1 != f2);
        assertTrue(f1.getClass() == f2.getClass());
        assertTrue(f1.equals(f2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(f1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            f2 = (LogFormat) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(f1, f2);
    }

}
