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
 * ---------------------
 * KeyedObjectsTest.java
 * ---------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 27-Jan-2004 : Version 1 (DG);
 * 28-Sep-2007 : Added testCloning2() (DG);
 * 03-Oct-2007 : New tests (DG);
 *
 */

package org.jfree.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import java.util.ArrayList;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.DefaultPieDataset;
import org.junit.Test;

/**
 * Tests for the {@link KeyedObjects} class.
 */
public class KeyedObjectsTest {

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyedObjects ko1 = new KeyedObjects();
        ko1.addObject("V1", new Integer(1));
        ko1.addObject("V2", null);
        ko1.addObject("V3", new Integer(3));
        KeyedObjects ko2 = (KeyedObjects) ko1.clone();
        assertTrue(ko1 != ko2);
        assertTrue(ko1.getClass() == ko2.getClass());
        assertTrue(ko1.equals(ko2));
    }

    /**
     * Confirm special features of cloning.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        // case 1 - object is mutable but not PublicCloneable
        Object obj1 = new ArrayList();
        KeyedObjects ko1 = new KeyedObjects();
        ko1.addObject("K1", obj1);
        KeyedObjects ko2 = (KeyedObjects) ko1.clone();
        assertTrue(ko1 != ko2);
        assertTrue(ko1.getClass() == ko2.getClass());
        assertTrue(ko1.equals(ko2));

        // the clone contains a reference to the original object
        assertTrue(ko2.getObject("K1") == obj1);

        // CASE 2 - object is mutable AND PublicCloneable
        obj1 = new DefaultPieDataset();
        ko1 = new KeyedObjects();
        ko1.addObject("K1", obj1);
        ko2 = (KeyedObjects) ko1.clone();
        assertTrue(ko1 != ko2);
        assertTrue(ko1.getClass() == ko2.getClass());
        assertTrue(ko1.equals(ko2));

        // the clone contains a reference to a CLONE of the original object
        assertTrue(ko2.getObject("K1") != obj1);
    }

    /**
     * Check that inserting and retrieving values works as expected.
     */
    @Test
    public void testInsertAndRetrieve() {

        KeyedObjects data = new KeyedObjects();
        data.addObject("A", new Double(1.0));
        data.addObject("B", new Double(2.0));
        data.addObject("C", new Double(3.0));
        data.addObject("D", null);

        // check key order
        assertEquals(data.getKey(0), "A");
        assertEquals(data.getKey(1), "B");
        assertEquals(data.getKey(2), "C");
        assertEquals(data.getKey(3), "D");

        // check retrieve value by key
        assertEquals(data.getObject("A"), new Double(1.0));
        assertEquals(data.getObject("B"), new Double(2.0));
        assertEquals(data.getObject("C"), new Double(3.0));
        assertEquals(data.getObject("D"), null);

        boolean pass = false;
        try {
            data.getObject("Not a key");
        }
        catch (UnknownKeyException e) {
            pass = true;
        }
        assertTrue(pass);

        // check retrieve value by index
        assertEquals(data.getObject(0), new Double(1.0));
        assertEquals(data.getObject(1), new Double(2.0));
        assertEquals(data.getObject(2), new Double(3.0));
        assertEquals(data.getObject(3), null);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyedObjects ko1 = new KeyedObjects();
        ko1.addObject("Key 1", "Object 1");
        ko1.addObject("Key 2", null);
        ko1.addObject("Key 3", "Object 2");
        KeyedObjects ko2 = (KeyedObjects) TestUtilities.serialised(ko1);
        assertEquals(ko1, ko2);
    }

    /**
     * Simple checks for the getObject(int) method.
     */
    @Test
    public void testGetObject() {
        // retrieve an item
        KeyedObjects ko1 = new KeyedObjects();
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
        KeyedObjects ko1 = new KeyedObjects();
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
        KeyedObjects ko1 = new KeyedObjects();
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
        KeyedObjects ko1 = new KeyedObjects();
        ko1.setObject("Key 1", "Object 1");
        ko1.setObject("Key 2", null);
        ko1.setObject("Key 3", "Object 2");

        assertEquals("Object 1", ko1.getObject("Key 1"));
        assertEquals(null, ko1.getObject("Key 2"));
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
        KeyedObjects ko1 = new KeyedObjects();
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
        KeyedObjects ko1 = new KeyedObjects();
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
