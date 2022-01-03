/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * NodeKeyTest.java
 * ----------------
 * (C) Copyright 2021-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import org.jfree.chart.TestUtils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link NodeKey} class.
 */
public class NodeKeyTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        NodeKey<String> k1 = new NodeKey<>(0, "A");
        NodeKey<String> k2 = new NodeKey<>(0, "A");
        assertEquals(k1, k2);
        assertEquals(k2, k1);

        k1 = new NodeKey<>(1, "A");
        assertNotEquals(k1, k2);
        k2 = new NodeKey<>(1, "A");
        assertEquals(k1, k2);
  
        k1 = new NodeKey<>(1, "B");
        assertNotEquals(k1, k2);
        k2 = new NodeKey<>(1, "B");
        assertEquals(k1, k2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        NodeKey<String> k1 = new NodeKey<>(2, "A");
        NodeKey<String> k2 = (NodeKey<String>) k1.clone();
        assertNotSame(k1, k2);
        assertSame(k1.getClass(), k2.getClass());
        assertEquals(k1, k2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        NodeKey<String> k1 = new NodeKey<>(1, "S1");
        NodeKey<String> k2 = TestUtils.serialised(k1);
        assertEquals(k1, k2);
    }

}

