/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * ----------------
 * FlowKeyTest.java
 * ----------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import org.jfree.chart.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link FlowKey} class.
 */
public class FlowKeyTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        FlowKey<String> k1 = new FlowKey<>(0, "A", "B");
        FlowKey<String> k2 = new FlowKey<>(0, "A", "B");
        assertTrue(k1.equals(k2));
        assertTrue(k2.equals(k1));

        k1 = new FlowKey<>(1, "A", "B");
        assertFalse(k1.equals(k2));
        k2 = new FlowKey<>(1, "A", "B");
        assertTrue(k1.equals(k2));
  
        k1 = new FlowKey<>(1, "C", "B");
        assertFalse(k1.equals(k2));
        k2 = new FlowKey<>(1, "C", "B");
        assertTrue(k1.equals(k2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        FlowKey<String> k1 = new FlowKey<>(0, "A", "B");
        FlowKey<String> k2 = (FlowKey<String>) k1.clone();
        assertTrue(k1 != k2);
        assertTrue(k1.getClass() == k2.getClass());
        assertTrue(k1.equals(k2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        FlowKey<String> k1 = new FlowKey<>(1, "S1", "D1");
        FlowKey<String> k2 = TestUtils.serialised(k1);
        assertEquals(k1, k2);
    }

}
