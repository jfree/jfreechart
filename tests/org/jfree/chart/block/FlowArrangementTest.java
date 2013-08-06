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
 * ------------------------
 * FlowArrangementTest.java
 * ------------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Feb-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;

import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.VerticalAlignment;
import org.junit.Test;

/**
 * Tests for the {@link FlowArrangement} class.
 */
public class FlowArrangementTest {

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        FlowArrangement f1 = new FlowArrangement(HorizontalAlignment.LEFT,
                VerticalAlignment.TOP, 1.0, 2.0);
        FlowArrangement f2 = new FlowArrangement(HorizontalAlignment.LEFT,
                VerticalAlignment.TOP, 1.0, 2.0);
        assertTrue(f1.equals(f2));
        assertTrue(f2.equals(f1));

        f1 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.TOP, 1.0, 2.0);
        assertFalse(f1.equals(f2));
        f2 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.TOP, 1.0, 2.0);
        assertTrue(f1.equals(f2));

        f1 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.0, 2.0);
        assertFalse(f1.equals(f2));
        f2 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.0, 2.0);
        assertTrue(f1.equals(f2));

        f1 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.1, 2.0);
        assertFalse(f1.equals(f2));
        f2 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.1, 2.0);
        assertTrue(f1.equals(f2));

        f1 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.1, 2.2);
        assertFalse(f1.equals(f2));
        f2 = new FlowArrangement(HorizontalAlignment.RIGHT,
                VerticalAlignment.BOTTOM, 1.1, 2.2);
        assertTrue(f1.equals(f2));

    }

    /**
     * Immutable - cloning is not necessary.
     */
    @Test
    public void testCloning() {
        FlowArrangement f1 = new FlowArrangement();
        assertFalse(f1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        FlowArrangement f1 = new FlowArrangement(HorizontalAlignment.LEFT,
                VerticalAlignment.TOP, 1.0, 2.0);
        FlowArrangement f2 = (FlowArrangement) TestUtilities.serialised(f1);
        assertEquals(f1, f2);
    }

}
