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
 * --------------------------
 * DefaultKeyedValueTest.java
 * --------------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultKeyedValue} class.
 */
public class DefaultKeyedValueTest {

    /**
     * Simple checks for the constructor.
     */
    @Test
    public void testConstructor() {
        DefaultKeyedValue<String> v = new DefaultKeyedValue<>("A", 1);
        assertEquals("A", v.getKey());
        assertEquals(1, v.getValue());

        // try null key
        boolean pass = false;
        try {
            v = new DefaultKeyedValue<>(null, 1);
        } catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        // try a null value
        v = new DefaultKeyedValue<>("A", null);
        assertNull(v.getValue());
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultKeyedValue<String> v1 = new DefaultKeyedValue<>("Test", 45.5);
        DefaultKeyedValue<String> v2 = new DefaultKeyedValue<>("Test", 45.5);
        assertEquals(v1, v2);
        assertEquals(v2, v1);

        v1 = new DefaultKeyedValue<>("Test 1", 45.5);
        v2 = new DefaultKeyedValue<>("Test 2", 45.5);
        assertNotEquals(v1, v2);

        v1 = new DefaultKeyedValue<>("Test", 45.5);
        v2 = new DefaultKeyedValue<>("Test", 45.6);
        assertNotEquals(v1, v2);
    }

    /**
     * Confirm that the equals method works correctly for null values.
     */
    @Test
    public void testEqualsForNullValues() {
        DefaultKeyedValue<String> v1 = new DefaultKeyedValue<>("K1", null);
        DefaultKeyedValue<String> v2 = new DefaultKeyedValue<>("K1", null);
        assertEquals(v1, v2);
        v1.setValue(1);
        assertNotEquals(v1, v2);
        assertNotEquals(v2, v1);
    }

    /**
     * Some checks for the clone() method.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValue<String> v1 = new DefaultKeyedValue<>("Test", 45.5);
        DefaultKeyedValue<String> v2 = CloneUtils.clone(v1);
        assertNotSame(v1, v2);
        assertSame(v1.getClass(), v2.getClass());
        assertEquals(v1, v2);

        // confirm that the clone is independent of the original
        v2.setValue(12.3);
        assertNotEquals(v1, v2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValue<String> v1 = new DefaultKeyedValue<>("Test", 25.3);
        DefaultKeyedValue<String> v2 = TestUtils.serialised(v1);
        assertEquals(v1, v2);
    }

}
