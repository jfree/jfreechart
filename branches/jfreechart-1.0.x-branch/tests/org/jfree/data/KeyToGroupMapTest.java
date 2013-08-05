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
 * KeyToGroupMapTests.java
 * -----------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 29-Apr-2004 : Version 1 (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtilities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for the {@link KeyToGroupMap} class.
 */
public class KeyToGroupMapTest {

    /**
     * Tests the mapKeyToGroup() method.
     */
    @Test
    public void testMapKeyToGroup() {
        KeyToGroupMap m1 = new KeyToGroupMap("G1");

        // map a key to the default group
        m1.mapKeyToGroup("K1", "G1");
        assertEquals("G1", m1.getGroup("K1"));

        // map a key to a new group
        m1.mapKeyToGroup("K2", "G2");
        assertEquals("G2", m1.getGroup("K2"));

        // clear a mapping
        m1.mapKeyToGroup("K2", null);
        assertEquals("G1", m1.getGroup("K2"));  // after clearing, reverts to
                                                // default group

        // check handling of null key
        boolean pass = false;
        try {
            m1.mapKeyToGroup(null, "G1");
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Tests that the getGroupCount() method returns the correct values under
     * various circumstances.
     */
    @Test
    public void testGroupCount() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a new map always has 1 group (the default group)
        assertEquals(1, m1.getGroupCount());

        // if the default group is not mapped to, it should still count towards
        // the group count...
        m1.mapKeyToGroup("C1", "G1");
        assertEquals(2, m1.getGroupCount());

        // now when the default group is mapped to, it shouldn't increase the
        // group count...
        m1.mapKeyToGroup("C2", "Default Group");
        assertEquals(2, m1.getGroupCount());

        // complicate things a little...
        m1.mapKeyToGroup("C3", "Default Group");
        m1.mapKeyToGroup("C4", "G2");
        m1.mapKeyToGroup("C5", "G2");
        m1.mapKeyToGroup("C6", "Default Group");
        assertEquals(3, m1.getGroupCount());

        // now overwrite group "G2"...
        m1.mapKeyToGroup("C4", "G1");
        m1.mapKeyToGroup("C5", "G1");
        assertEquals(2, m1.getGroupCount());
    }

    /**
     * Tests that the getKeyCount() method returns the correct values under
     * various circumstances.
     */
    @Test
    public void testKeyCount() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a new map always has 1 group (the default group)
        assertEquals(0, m1.getKeyCount("Default Group"));

        // simple case
        m1.mapKeyToGroup("K1", "G1");
        assertEquals(1, m1.getKeyCount("G1"));
        m1.mapKeyToGroup("K1", null);
        assertEquals(0, m1.getKeyCount("G1"));

        // if there is an explicit mapping to the default group, it is counted
        m1.mapKeyToGroup("K2", "Default Group");
        assertEquals(1, m1.getKeyCount("Default Group"));

        // complicate things a little...
        m1.mapKeyToGroup("K3", "Default Group");
        m1.mapKeyToGroup("K4", "G2");
        m1.mapKeyToGroup("K5", "G2");
        m1.mapKeyToGroup("K6", "Default Group");
        assertEquals(3, m1.getKeyCount("Default Group"));
        assertEquals(2, m1.getKeyCount("G2"));

        // now overwrite group "G2"...
        m1.mapKeyToGroup("K4", "G1");
        m1.mapKeyToGroup("K5", "G1");
        assertEquals(2, m1.getKeyCount("G1"));
        assertEquals(0, m1.getKeyCount("G2"));
    }

    /**
     * Tests the getGroupIndex() method.
     */
    @Test
    public void testGetGroupIndex() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // the default group is always at index 0
        assertEquals(0, m1.getGroupIndex("Default Group"));

        // a non-existent group should return -1
        assertEquals(-1, m1.getGroupIndex("G3"));

        // indices are assigned in the order that groups are originally mapped
        m1.mapKeyToGroup("K3", "G3");
        m1.mapKeyToGroup("K1", "G1");
        m1.mapKeyToGroup("K2", "G2");
        assertEquals(1, m1.getGroupIndex("G3"));
        assertEquals(2, m1.getGroupIndex("G1"));
        assertEquals(3, m1.getGroupIndex("G2"));
    }

    /**
     * Tests the getGroup() method.
     */
    @Test
    public void testGetGroup() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");

        // a key that hasn't been mapped should return the default group
        assertEquals("Default Group", m1.getGroup("K1"));

        m1.mapKeyToGroup("K1", "G1");
        assertEquals("G1", m1.getGroup("K1"));
        m1.mapKeyToGroup("K1", "G2");
        assertEquals("G2", m1.getGroup("K1"));
        m1.mapKeyToGroup("K1", null);
        assertEquals("Default Group", m1.getGroup("K1"));

        // a null argument should throw an exception
        boolean pass = false;
        try {
            Comparable g = m1.getGroup(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        KeyToGroupMap m1 = new KeyToGroupMap("Default Group");
        KeyToGroupMap m2 = new KeyToGroupMap("Default Group");
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));

        m1.mapKeyToGroup("K1", "G1");
        assertFalse(m1.equals(m2));
        m2.mapKeyToGroup("K1", "G1");
        assertTrue(m1.equals(m2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        KeyToGroupMap m1 = new KeyToGroupMap("Test");
        m1.mapKeyToGroup("K1", "G1");
        KeyToGroupMap m2 = (KeyToGroupMap) m1.clone();
        assertTrue(m1 != m2);
        assertTrue(m1.getClass() == m2.getClass());
        assertTrue(m1.equals(m2));

        // a small check for independence
        m1.mapKeyToGroup("K1", "G2");
        assertFalse(m1.equals(m2));
        m2.mapKeyToGroup("K1", "G2");
        assertTrue(m1.equals(m2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        KeyToGroupMap m1 = new KeyToGroupMap("Test");
        KeyToGroupMap m2 = (KeyToGroupMap) TestUtilities.serialised(m1);
        assertEquals(m1, m2);
    }

}
