/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * --------------------------------
 * ComparableObjectSeriesTests.java
 * --------------------------------
 * (C) Copyright 2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: ComparableObjectSeriesTests.java,v 1.1.2.3 2007/04/13 14:38:31 mungady Exp $
 *
 * Changes
 * -------
 * 20-Oct-2006 : Version 1 (DG);
 *
 */

package org.jfree.data.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.ComparableObjectItem;
import org.jfree.data.ComparableObjectSeries;

/**
 * Tests for the {@link ComparableObjectSeries} class.
 */
public class ComparableObjectSeriesTests extends TestCase {

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
        public void add(Comparable x, Object y) {
            super.add(x, y);
        }

        public ComparableObjectItem remove(Comparable x) {
            return super.remove(x);
        }
    }
    
    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(ComparableObjectSeriesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public ComparableObjectSeriesTests(String name) {
        super(name);
    }

    /**
     * Some checks for the constructor.
     */
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
            s1 = new ComparableObjectSeries(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        MyComparableObjectSeries s2 = new MyComparableObjectSeries("A");
        assertTrue(s1.equals(s2));
        assertTrue(s2.equals(s1));

        // key
        s1 = new MyComparableObjectSeries("B");
        assertFalse(s1.equals(s2));
        s2 = new MyComparableObjectSeries("B");
        assertTrue(s1.equals(s2));
        
        // autoSort
        s1 = new MyComparableObjectSeries("B", false, true);
        assertFalse(s1.equals(s2));
        s2 = new MyComparableObjectSeries("B", false, true);
        assertTrue(s1.equals(s2));

        // allowDuplicateXValues
        s1 = new MyComparableObjectSeries("B", false, false);
        assertFalse(s1.equals(s2));
        s2 = new MyComparableObjectSeries("B", false, false);
        assertTrue(s1.equals(s2));

        // add a value
        s1.add(new Integer(1), "ABC");
        assertFalse(s1.equals(s2));
        s2.add(new Integer(1), "ABC");
        assertTrue(s1.equals(s2));
        
        // add another value
        s1.add(new Integer(0), "DEF");
        assertFalse(s1.equals(s2));
        s2.add(new Integer(0), "DEF");
        assertTrue(s1.equals(s2));
        
        // remove an item
        s1.remove(new Integer(1));
        assertFalse(s1.equals(s2));
        s2.remove(new Integer(1));
        assertTrue(s1.equals(s2));
    }

    /**
     * Some checks for the clone() method.
     */
    public void testCloning() {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        s1.add(new Integer(1), "ABC");
        MyComparableObjectSeries s2 = null;
        try {
            s2 = (MyComparableObjectSeries) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        MyComparableObjectSeries s1 = new MyComparableObjectSeries("A");
        s1.add(new Integer(1), "ABC");
        MyComparableObjectSeries s2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(s1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            s2 = (MyComparableObjectSeries) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(s1, s2);
    }

}
