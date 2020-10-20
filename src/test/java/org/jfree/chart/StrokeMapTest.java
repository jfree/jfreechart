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
 * ------------------
 * StrokeMapTest.java
 * ------------------
 * (C) Copyright 2006-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 27-Sep-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.BasicStroke;
import org.junit.jupiter.api.Test;

/**
 * Some tests for the {@link StrokeMap} class.
 */
public class StrokeMapTest {

    /**
     * Some checks for the getStroke() method.
     */
    @Test
    public void testGetStroke() {
        StrokeMap m1 = new StrokeMap();
        assertEquals(null, m1.getStroke("A"));
        m1.put("A", new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m1.getStroke("A"));
        m1.put("A", null);
        assertEquals(null, m1.getStroke("A"));

        // a null key should throw an IllegalArgumentException
        boolean pass = false;
        try {
            m1.getStroke(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the put() method.
     */
    @Test
    public void testPut() {
        StrokeMap m1 = new StrokeMap();
        m1.put("A", new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m1.getStroke("A"));

        // a null key should throw an IllegalArgumentException
        boolean pass = false;
        try {
            m1.put(null, new BasicStroke(1.1f));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = new StrokeMap();
        assertTrue(m1.equals(m1));
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(null));
        assertFalse(m1.equals("ABC"));

        m1.put("K1", new BasicStroke(1.1f));
        assertFalse(m1.equals(m2));
        m2.put("K1", new BasicStroke(1.1f));
        assertTrue(m1.equals(m2));

        m1.put("K2", new BasicStroke(2.2f));
        assertFalse(m1.equals(m2));
        m2.put("K2", new BasicStroke(2.2f));
        assertTrue(m1.equals(m2));

        m1.put("K2", null);
        assertFalse(m1.equals(m2));
        m2.put("K2", null);
        assertTrue(m1.equals(m2));
    }

    /**
     * Some checks for cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = (StrokeMap) m1.clone();
        assertTrue(m1.equals(m2));

        m1.put("K1", new BasicStroke(1.1f));
        m1.put("K2", new BasicStroke(2.2f));
        m2 = (StrokeMap) m1.clone();
        assertTrue(m1.equals(m2));
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization1() {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = (StrokeMap) TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization2() {
        StrokeMap m1 = new StrokeMap();
        m1.put("K1", new BasicStroke(1.1f));
        m1.put("K2", new BasicStroke(2.2f));
        StrokeMap m2 = (StrokeMap) TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

}

