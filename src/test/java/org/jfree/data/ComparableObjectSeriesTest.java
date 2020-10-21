/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -------------------------------
 * ComparableObjectSeriesTest.java
 * -------------------------------
 * (C) Copyright 2006-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
 * 31-Oct-2007 : New hashCode() test (DG);
 *
 */

package org.jfree.data;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for the {@link ComparableObjectSeries} class.
 */
public class ComparableObjectSeriesTest {

    static class MyComparableObjectSeries extends ComparableObjectSeries {
        /**
         * Creates a new instance.
         *
         * @param key  the series key.
         */
        public MyComparableObjectSeries(Comparable key) {
            super(key);
        }
        /**
         * Creates a new instance.
         *
         * @param key  the series key.
         * @param autoSort  automatically sort by x-value?
         * @param allowDuplicateXValues  allow duplicate values?
         */
        public MyComparableObjectSeries(Comparable key, boolean autoSort,
                boolean allowDuplicateXValues) {
            super(key, autoSort, allowDuplicateXValues);
        }
        @Override
        public void add(Comparable x, Object y) {
            super.add(x, y);
        }

        @Override
        public ComparableObjectItem remove(Comparable x) {
            return super.remove(x);
        }
    }

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor1() {
        ComparableObjectSeries s1 = new ComparableObjectSeries("s1");
        assertEquals("s1", s1.getKey());
        assertNull(s1.getDescription());
        assertTrue(s1.getAllowDuplicateXValues());
        assertTrue(s1.getAutoSort());
        assertEquals(0, s1.getItemCount());
        assertEquals(Integer.MAX_VALUE, s1.getMaximumItemCount());

        // try null key
        boolean pass = false;
        try {
            /*s1 = */new ComparableObjectSeries(null);
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
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        MyComparableObjectSeries s2 = new MyComparableObjectSeries("A");
        assertTrue(s1.equals(s2));
        assertTrue(s2.equals(s1));

        // key
        s1 = new MyComparableObjectSeries("B");
        assertNotEquals(s1, s2);
        s2 = new MyComparableObjectSeries("B");
        assertTrue(s1.equals(s2));

        // autoSort
        s1 = new MyComparableObjectSeries("B", false, true);
        assertNotEquals(s1, s2);
        s2 = new MyComparableObjectSeries("B", false, true);
        assertTrue(s1.equals(s2));

        // allowDuplicateXValues
        s1 = new MyComparableObjectSeries("B", false, false);
        assertNotEquals(s1, s2);
        s2 = new MyComparableObjectSeries("B", false, false);
        assertTrue(s1.equals(s2));

        // add a value
        s1.add(1, "ABC");
        assertNotEquals(s1, s2);
        s2.add(1, "ABC");
        assertTrue(s1.equals(s2));

        // add another value
        s1.add(0, "DEF");
        assertNotEquals(s1, s2);
        s2.add(0, "DEF");
        assertTrue(s1.equals(s2));

        // remove an item
        s1.remove(1);
        assertNotEquals(s1, s2);
        s2.remove(1);
        assertTrue(s1.equals(s2));
    }

    /**
     * Some checks for the clone() method.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        s1.add(1, "ABC");
        MyComparableObjectSeries s2 = (MyComparableObjectSeries) s1.clone();
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        s1.add(1, "ABC");
        MyComparableObjectSeries s2 = (MyComparableObjectSeries) 
                TestUtils.serialised(s1);
        assertEquals(s1, s2);
    }

    /**
     * Some simple checks for the hashCode() method.
     */
    @Test
    public void testHashCode() {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("Test");
        MyComparableObjectSeries s2 = new MyComparableObjectSeries("Test");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add("A", "1");
        s2.add("A", "1");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add("B", null);
        s2.add("B", null);
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add("C", "3");
        s2.add("C", "3");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());

        s1.add("D", "4");
        s2.add("D", "4");
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

}
