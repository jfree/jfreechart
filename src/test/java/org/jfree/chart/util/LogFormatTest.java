/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * ------------------
 * LogFormatTest.java
 * ------------------
 * (C) Copyright 2008-2016, by Object Refinery Limited and Contributors.
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

package org.jfree.chart.util;

import java.text.DecimalFormat;

import org.jfree.chart.TestUtils;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

/**
 * Tests for the {@link LogFormat} class.
 */
public class LogFormatTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
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
    @Test
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
    @Test
    public void testCloning() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = (LogFormat) f1.clone();
        assertTrue(f1 != f2);
        assertTrue(f1.getClass() == f2.getClass());
        assertTrue(f1.equals(f2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        LogFormat f1 = new LogFormat(10.0, "10", true);
        LogFormat f2 = (LogFormat) TestUtils.serialised(f1);
        assertEquals(f1, f2);
    }

}
