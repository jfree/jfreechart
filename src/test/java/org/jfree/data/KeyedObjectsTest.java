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
 * ---------------------
 * KeyedObjectsTest.java
 * ---------------------
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
 * Tests for the {@link KeyedObjects} class.
 */
public class KeyedObjectsTest {

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("V1", 1);
        ko1.addObject("V2", null);
        ko1.addObject("V3", 3);
        KeyedObjects<String> ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);
    }

    /**
     * Confirm special features of cloning.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        // case 1 - object is mutable but not PublicCloneable
        Object obj1 = new ArrayList<>();
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("K1", obj1);
        KeyedObjects<String> ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);

        // the clone contains a reference to the original object
        assertSame(ko2.getObject("K1"), obj1);

        // CASE 2 - object is mutable AND PublicCloneable
        obj1 = new DefaultPieDataset<String>();
        ko1 = new KeyedObjects<>();
        ko1.addObject("K1", obj1);
        ko2 = CloneUtils.clone(ko1);
        assertNotSame(ko1, ko2);
        assertSame(ko1.getClass(), ko2.getClass());
        assertEquals(ko1, ko2);

        // the clone contains a reference to a CLONE of the original object
        assertNotSame(ko2.getObject("K1"), obj1);
    }

    /**
     * Check that inserting and retrieving values works as expected.
     */
    @Test
    public void testInsertAndRetrieve() {

        KeyedObjects<String> data = new KeyedObjects<>();
        data.addObject("A", 1.0);
        data.addObject("B", 2.0);
        data.addObject("C", 3.0);
        data.addObject("D", null);

        // check key order
        assertEquals(data.getKey(0), "A");
        assertEquals(data.getKey(1), "B");
        assertEquals(data.getKey(2), "C");
        assertEquals(data.getKey(3), "D");

        // check retrieve value by key
        assertEquals(data.getObject("A"), 1.0);
        assertEquals(data.getObject("B"), 2.0);
        assertEquals(data.getObject("C"), 3.0);
        assertNull(data.getObject("D"));

        boolean pass = false;
        try {
            data.getObject("Not a key");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // check retrieve value by index
        assertEquals(data.getObject(0), 1.0);
        assertEquals(data.getObject(1), 2.0);
        assertEquals(data.getObject(2), 3.0);
        assertNull(data.getObject(3));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("Key 1", "Object 1");
        ko1.addObject("Key 2", null);
        ko1.addObject("Key 3", "Object 2");
        KeyedObjects<String> ko2 = TestUtils.serialised(ko1);
        assertEquals(ko1, ko2);
    }

    /**
     * Simple checks for the getObject(int) method.
     */
    @Test
    public void testGetObject() {
        // retrieve an item
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("Key 1", "Object 1");
        ko1.addObject("Key 2", null);
        ko1.addObject("Key 3", "Object 2");
        assertEquals("Object 1", ko1.getObject(0));
        assertNull(ko1.getObject(1));
        assertEquals("Object 2", ko1.getObject(2));

        // request with a negative index
        boolean pass = false;
        try {
            ko1.getObject(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        // request width index == itemCount
        pass = false;
        try {
            ko1.getObject(3);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Simple checks for the getKey(int) method.
     */
    @Test
    public void testGetKey() {
        // retrieve an item
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("Key 1", "Object 1");
        ko1.addObject("Key 2", null);
        ko1.addObject("Key 3", "Object 2");
        assertEquals("Key 1", ko1.getKey(0));
        assertEquals("Key 2", ko1.getKey(1));
        assertEquals("Key 3", ko1.getKey(2));

        // request with a negative index
        boolean pass = false;
        try {
            ko1.getKey(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        // request width index == itemCount
        pass = false;
        try {
            ko1.getKey(3);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Simple checks for the getIndex(Comparable) method.
     */
    @Test
    public void testGetIndex() {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.addObject("Key 1", "Object 1");
        ko1.addObject("Key 2", null);
        ko1.addObject("Key 3", "Object 2");
        assertEquals(0, ko1.getIndex("Key 1"));
        assertEquals(1, ko1.getIndex("Key 2"));
        assertEquals(2, ko1.getIndex("Key 3"));

        // check null argument
        boolean pass = false;
        try {
            ko1.getIndex(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the setObject(Comparable, Object) method.
     */
    @Test
    public void testSetObject() {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.setObject("Key 1", "Object 1");
        ko1.setObject("Key 2", null);
        ko1.setObject("Key 3", "Object 2");

        assertEquals("Object 1", ko1.getObject("Key 1"));
        assertNull(ko1.getObject("Key 2"));
        assertEquals("Object 2", ko1.getObject("Key 3"));

        // replace an existing value
        ko1.setObject("Key 2", "AAA");
        ko1.setObject("Key 3", "BBB");
        assertEquals("AAA", ko1.getObject("Key 2"));
        assertEquals("BBB", ko1.getObject("Key 3"));

        // try a null key - should throw an exception
        boolean pass = false;
        try {
            ko1.setObject(null, "XX");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeValue() methods.
     */
    @Test
    public void testRemoveValue() {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.setObject("Key 1", "Object 1");
        ko1.setObject("Key 2", null);
        ko1.setObject("Key 3", "Object 2");

        ko1.removeValue(1);
        assertEquals(2, ko1.getItemCount());
        assertEquals(1, ko1.getIndex("Key 3"));

        ko1.removeValue("Key 1");
        assertEquals(1, ko1.getItemCount());
        assertEquals(0, ko1.getIndex("Key 3"));

        // try unknown key
        boolean pass = false;
        try {
            ko1.removeValue("UNKNOWN");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // try null argument
        pass = false;
        try {
            ko1.removeValue(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the removeValue(int) method.
     */
    @Test
    public void testRemoveValueInt() {
        KeyedObjects<String> ko1 = new KeyedObjects<>();
        ko1.setObject("Key 1", "Object 1");
        ko1.setObject("Key 2", null);
        ko1.setObject("Key 3", "Object 2");

        ko1.removeValue(1);
        assertEquals(2, ko1.getItemCount());
        assertEquals(1, ko1.getIndex("Key 3"));


        // try negative key index
        boolean pass = false;
        try {
            ko1.removeValue(-1);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);

        // try key index == itemCount
        pass = false;
        try {
            ko1.removeValue(2);
        }
        catch (IndexOutOfBoundsException e) {
            pass = true;
        }
        assertTrue(pass);
    }

}
