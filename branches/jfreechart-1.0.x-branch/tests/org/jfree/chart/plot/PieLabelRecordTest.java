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
 * -----------------------
 * PieLabelRecordTest.java
 * -----------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 21-Nov-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;

import org.jfree.text.TextBox;
import org.junit.Test;

/**
 * Some tests for the {@link PieLabelRecord} class.
 */
public class PieLabelRecordTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
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
    @Test
    public void testCloning() {
        PieLabelRecord p1 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        assertFalse(p1 instanceof Cloneable);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PieLabelRecord p1 = new PieLabelRecord("A", 1.0, 2.0, new TextBox("B"),
                3.0, 4.0, 5.0);
        PieLabelRecord p2 = (PieLabelRecord) TestUtilities.serialised(p1);
        boolean b = p1.equals(p2);
        assertTrue(b);
    }
}
