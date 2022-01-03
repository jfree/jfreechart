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
 * --------------------
 * KeyedObjectTest.java
 * --------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import java.util.ArrayList;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.general.DefaultPieDataset;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link KeyedObject} class.
 */
public class KeyedObjectTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        KeyedObject<String> ko1 = new KeyedObject<>("Test", "Object");
        KeyedObject<String> ko2 = new KeyedObject<>("Test", "Object");
        assertEquals(ko1, ko2);
        assertEquals(ko2, ko1);

        ko1 = new KeyedObject<>("Test 1", "Object");
        ko2 = new KeyedObject<>("Test 2", "Object");
        assertNotEquals(ko1, ko2);

        ko1 = new KeyedObject<>("Test", "Object 1");
        ko2 = new KeyedObject<>("Test", "Object 2");
        assertNotEquals(ko1, ko2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyedObject<String> ko1 = new KeyedObject<>("Test", "Object");
        KeyedObject<String> ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);
    }

    /**
     * Confirm special features of cloning.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        // case 1 - object is mutable but not PublicCloneable
        Object obj1 = new ArrayList<String>();
        KeyedObject<String> ko1 = new KeyedObject<>("Test", obj1);
        KeyedObject<String> ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);

        // the clone contains a reference to the original object
        assertSame(ko2.getObject(), obj1);

        // CASE 2 - object is mutable AND PublicCloneable
        obj1 = new DefaultPieDataset<String>();
        ko1 = new KeyedObject<>("Test", obj1);
        ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);

        // the clone contains a reference to a CLONE of the original object
        assertNotSame(ko2.getObject(), obj1);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyedObject<String> ko1 = new KeyedObject<>("Test", "Object");
        KeyedObject<String> ko2 = TestUtils.serialised(ko1);
        assertEquals(ko1, ko2);
    }

}
