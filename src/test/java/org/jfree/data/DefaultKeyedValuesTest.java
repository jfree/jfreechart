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
 * ---------------------------
 * DefaultKeyedValuesTest.java
 * ---------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data;

import java.util.List;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.api.SortOrder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DefaultKeyedValues} class.
 */
public class DefaultKeyedValuesTest {

    /**
     * Checks that a new instance is empty.
     */
    @Test
    public void testConstructor() {
        DefaultKeyedValues<String> d = new DefaultKeyedValues<>();
        assertEquals(0, d.getItemCount());
    }

    /**
     * Some checks for the getItemCount() method.
     */
    @Test
    public void testGetItemCount() {
        DefaultKeyedValues<String> d = new DefaultKeyedValues<>();
        assertEquals(0, d.getItemCount());
        d.addValue("A", 1.0);
        assertEquals(1, d.getItemCount());
        d.addValue("B", 2.0);
        assertEquals(2, d.getItemCount());
        d.clear();
        assertEquals(0, d.getItemCount());
    }

    /**
     * Some checks for the getKeys() method.
     */
    @Test
    public void testGetKeys() {
        DefaultKeyedValues<String> d = new DefaultKeyedValues<>();
        List<String> keys = d.getKeys();
        assertTrue(keys.isEmpty());
        d.addValue("A", 1.0);
        keys = d.getKeys();
        assertEquals(1, keys.size());
        assertTrue(keys.contains("A"));
        d.addValue("B", 2.0);
        keys = d.getKeys();
        assertEquals(2, keys.size());
        assertTrue(keys.contains("A"));
        assertTrue(keys.contains("B"));
        d.clear();
        keys = d.getKeys();
        assertEquals(0, keys.size());
    }

    /**
     * A simple test for the clear() method.
     */
    @Test
    public void testClear() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        v1.addValue("A", 1.0);
        v1.addValue("B", 2.0);
        assertEquals(2, v1.getItemCount());
        v1.clear();
        assertEquals(0, v1.getItemCount());
    }

    /**
     * Some checks for the getValue() methods.
     */
    @Test
    public void testGetValue() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        try {
            /* Number n = */ v1.getValue(-1);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            /* Number n = */ v1.getValue(0);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        DefaultKeyedValues<String> v2 = new DefaultKeyedValues<>();
        v2.addValue("K1", Integer.valueOf(1));
        v2.addValue("K2", Integer.valueOf(2));
        v2.addValue("K3", Integer.valueOf(3));
        assertEquals(3, v2.getValue(2));

        boolean pass = false;
        try {
            /* Number n = */ v2.getValue("KK");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the getKey() methods.
     */
    @Test
    public void testGetKey() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        try {
            /* Comparable k = */ v1.getKey(-1);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            /* Comparable k = */ v1.getKey(0);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        DefaultKeyedValues<String> v2 = new DefaultKeyedValues<>();
        v2.addValue("K1", 1);
        v2.addValue("K2", 2);
        v2.addValue("K3", 3);
        assertEquals("K2", v2.getKey(1));
    }

    /**
     * Some checks for the getIndex() methods.
     */
    @Test
    public void testGetIndex() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        assertEquals(-1, v1.getIndex("K1"));

        DefaultKeyedValues<String> v2 = new DefaultKeyedValues<>();
        v2.addValue("K1", 1);
        v2.addValue("K2", 2);
        v2.addValue("K3", 3);
        assertEquals(2, v2.getIndex("K3"));

        // try null
        boolean pass = false;
        try {
            v2.getIndex(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Another check for the getIndex(Comparable) method.
     */
    @Test
    public void testGetIndex2() {
        DefaultKeyedValues<String> v = new DefaultKeyedValues<>();
        assertEquals(-1, v.getIndex("K1"));
        v.addValue("K1", 1.0);
        assertEquals(0, v.getIndex("K1"));
        v.removeValue("K1");
        assertEquals(-1, v.getIndex("K1"));
    }
    /**
     * Some checks for the addValue() method.
     */
    @Test
    public void testAddValue() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        v1.addValue("A", 1.0);
        assertEquals(1.0, v1.getValue("A"));
        v1.addValue("B", 2.0);
        assertEquals(2.0, v1.getValue("B"));
        v1.addValue("B", 3.0);
        assertEquals(3.0, v1.getValue("B"));
        assertEquals(2, v1.getItemCount());
        v1.addValue("A", null);
        assertNull(v1.getValue("A"));
        assertEquals(2, v1.getItemCount());

        boolean pass = false;
        try {
            v1.addValue(null, 99.9);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the insertValue() method.
     */
    @Test
    public void testInsertValue() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        v1.insertValue(0, "A", 1.0);
        assertEquals(1.0, v1.getValue(0));
        v1.insertValue(0, "B", 2.0);
        assertEquals(2.0, v1.getValue(0));
        assertEquals(1.0, v1.getValue(1));

        // it's OK to use an index equal to the size of the list
        v1.insertValue(2, "C", 3.0);
        assertEquals(2.0, v1.getValue(0));
        assertEquals(1.0, v1.getValue(1));
        assertEquals(3.0, v1.getValue(2));

        // try replacing an existing value
        v1.insertValue(2, "B", 4.0);
        assertEquals(1.0, v1.getValue(0));
        assertEquals(3.0, v1.getValue(1));
        assertEquals(4.0, v1.getValue(2));
    }

    /**
     * Some checks for the clone() method.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        v1.addValue("V1", 1);
        v1.addValue("V2", null);
        v1.addValue("V3", 3);
        DefaultKeyedValues<String> v2 = CloneUtils.clone(v1);
        assertNotSame(v1, v2);
        assertSame(v1.getClass(), v2.getClass());
        assertEquals(v1, v2);

        // confirm that the clone is independent of the original
        v2.setValue("V1", 44);
        assertNotEquals(v1, v2);
    }

    /**
     * Check that inserting and retrieving values works as expected.
     */
    @Test
    public void testInsertAndRetrieve() {

        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("A", 1.0);
        data.addValue("B", 2.0);
        data.addValue("C", 3.0);
        data.addValue("D", null);

        // check key order
        assertEquals(data.getKey(0), "A");
        assertEquals(data.getKey(1), "B");
        assertEquals(data.getKey(2), "C");
        assertEquals(data.getKey(3), "D");

        // check retrieve value by key
        assertEquals(data.getValue("A"), 1.0);
        assertEquals(data.getValue("B"), 2.0);
        assertEquals(data.getValue("C"), 3.0);
        assertNull(data.getValue("D"));

        // check retrieve value by index
        assertEquals(data.getValue(0), 1.0);
        assertEquals(data.getValue(1), 2.0);
        assertEquals(data.getValue(2), 3.0);
        assertNull(data.getValue(3));

    }

    /**
     * Some tests for the removeValue() method.
     */
    @Test
    public void testRemoveValue() {
        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("A", 1.0);
        data.addValue("B", null);
        data.addValue("C", 3.0);
        data.addValue("D", 2.0);
        assertEquals(1, data.getIndex("B"));
        data.removeValue("B");
        assertEquals(-1, data.getIndex("B"));

        boolean pass = false;
        try {
            data.removeValue("XXX");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Tests sorting of data by key (ascending).
     */
    @Test
    public void testSortByKeyAscending() {

        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("C", 1.0);
        data.addValue("B", null);
        data.addValue("D", 3.0);
        data.addValue("A", 2.0);

        data.sortByKeys(SortOrder.ASCENDING);

        // check key order
        assertEquals(data.getKey(0), "A");
        assertEquals(data.getKey(1), "B");
        assertEquals(data.getKey(2), "C");
        assertEquals(data.getKey(3), "D");

        // check retrieve value by key
        assertEquals(data.getValue("A"), 2.0);
        assertNull(data.getValue("B"));
        assertEquals(data.getValue("C"), 1.0);
        assertEquals(data.getValue("D"), 3.0);

        // check retrieve value by index
        assertEquals(data.getValue(0), 2.0);
        assertNull(data.getValue(1));
        assertEquals(data.getValue(2), 1.0);
        assertEquals(data.getValue(3), 3.0);

    }

    /**
     * Tests sorting of data by key (descending).
     */
    @Test
    public void testSortByKeyDescending() {

        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("C", 1.0);
        data.addValue("B", null);
        data.addValue("D", 3.0);
        data.addValue("A", 2.0);

        data.sortByKeys(SortOrder.DESCENDING);

        // check key order
        assertEquals(data.getKey(0), "D");
        assertEquals(data.getKey(1), "C");
        assertEquals(data.getKey(2), "B");
        assertEquals(data.getKey(3), "A");

        // check retrieve value by key
        assertEquals(data.getValue("A"), 2.0);
        assertNull(data.getValue("B"));
        assertEquals(data.getValue("C"), 1.0);
        assertEquals(data.getValue("D"), 3.0);

        // check retrieve value by index
        assertEquals(data.getValue(0), 3.0);
        assertEquals(data.getValue(1), 1.0);
        assertNull(data.getValue(2));
        assertEquals(data.getValue(3), 2.0);

    }

    /**
     * Tests sorting of data by value (ascending).
     */
    @Test
    public void testSortByValueAscending() {

        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("C", 1.0);
        data.addValue("B", null);
        data.addValue("D", 3.0);
        data.addValue("A", 2.0);

        data.sortByValues(SortOrder.ASCENDING);

        // check key order
        assertEquals(data.getKey(0), "C");
        assertEquals(data.getKey(1), "A");
        assertEquals(data.getKey(2), "D");
        assertEquals(data.getKey(3), "B");

        // check retrieve value by key
        assertEquals(data.getValue("A"), 2.0);
        assertNull(data.getValue("B"));
        assertEquals(data.getValue("C"), 1.0);
        assertEquals(data.getValue("D"), 3.0);

        // check retrieve value by index
        assertEquals(data.getValue(0), 1.0);
        assertEquals(data.getValue(1), 2.0);
        assertEquals(data.getValue(2), 3.0);
        assertNull(data.getValue(3));

    }

    /**
     * Tests sorting of data by key (descending).
     */
    @Test
    public void testSortByValueDescending() {

        DefaultKeyedValues<String> data = new DefaultKeyedValues<>();
        data.addValue("C", 1.0);
        data.addValue("B", null);
        data.addValue("D", 3.0);
        data.addValue("A", 2.0);

        data.sortByValues(SortOrder.DESCENDING);

        // check key order
        assertEquals(data.getKey(0), "D");
        assertEquals(data.getKey(1), "A");
        assertEquals(data.getKey(2), "C");
        assertEquals(data.getKey(3), "B");

        // check retrieve value by key
        assertEquals(data.getValue("A"), 2.0);
        assertNull(data.getValue("B"));
        assertEquals(data.getValue("C"), 1.0);
        assertEquals(data.getValue("D"), 3.0);

        // check retrieve value by index
        assertEquals(data.getValue(0), 3.0);
        assertEquals(data.getValue(1), 2.0);
        assertEquals(data.getValue(2), 1.0);
        assertNull(data.getValue(3));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValues<String> v1 = new DefaultKeyedValues<>();
        v1.addValue("Key 1", 23);
        v1.addValue("Key 2", null);
        v1.addValue("Key 3", 42);

        DefaultKeyedValues<String> v2 = TestUtils.serialised(v1);
        assertEquals(v1, v2);
    }

}
