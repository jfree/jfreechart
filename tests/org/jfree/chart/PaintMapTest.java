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
 * -----------------
 * PaintMapTest.java
 * -----------------
 * (C) Copyright 2006-2013, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 27-Sep-2006 : Version 1 (DG);
 * 17-Jan-2007 : Added testKeysOfDifferentClasses() (DG);
 *
 */

package org.jfree.chart;

import org.junit.Test;

import java.awt.Color;
import java.awt.GradientPaint;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Some tests for the {@link PaintMap} class.
 */
public class PaintMapTest  {

    /**
     * Some checks for the getPaint() method.
     */
    @Test
    public void testGetPaint() {
        PaintMap m1 = new PaintMap();
        assertEquals(null, m1.getPaint("A"));
        m1.put("A", Color.red);
        assertEquals(Color.red, m1.getPaint("A"));
        m1.put("A", null);
        assertEquals(null, m1.getPaint("A"));

        // a null key should throw an IllegalArgumentException
        try {
            m1.getPaint(null);
            fail("IllegalArgumentException should have been thrown on passing null value");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'key' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the put() method.
     */
    @Test
    public void testPut() {
        PaintMap m1 = new PaintMap();
        m1.put("A", Color.red);
        assertEquals(Color.red, m1.getPaint("A"));

        // a null key should throw an IllegalArgumentException

        try {
            m1.put(null, Color.blue);
            fail("IllegalArgumentException should have been thrown on null key");
        }
        catch (IllegalArgumentException e) {
            assertEquals("Null 'key' argument.", e.getMessage());
        }
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        PaintMap m1 = new PaintMap();
        PaintMap m2 = new PaintMap();
        assertEquals(m1, m1);
        assertEquals(m1, m2);
        assertFalse(m1.equals(null));
        assertFalse(m1.equals("ABC"));

        m1.put("K1", Color.red);
        assertFalse(m1.equals(m2));
        m2.put("K1", Color.red);
        assertEquals(m1, m2);

        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.green, 3.0f, 4.0f,
                Color.yellow));
        assertFalse(m1.equals(m2));
        m2.put("K2", new GradientPaint(1.0f, 2.0f, Color.green, 3.0f, 4.0f,
                Color.yellow));
        assertEquals(m1, m2);

        m1.put("K2", null);
        assertFalse(m1.equals(m2));
        m2.put("K2", null);
        assertEquals(m1, m2);
    }

    /**
     * Some checks for cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PaintMap m1 = new PaintMap();
        PaintMap m2 = (PaintMap) m1.clone();
        assertEquals(m1, m2);

        m1.put("K1", Color.red);
        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.green, 3.0f, 4.0f,
                Color.yellow));
        m2 = (PaintMap) m1.clone();
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization1() {
        PaintMap m1 = new PaintMap();
        PaintMap m2 = (PaintMap) TestUtilities.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization2() {
        PaintMap m1 = new PaintMap();
        m1.put("K1", Color.red);
        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.green, 3.0f, 4.0f,
                Color.yellow));
        PaintMap m2 = (PaintMap) TestUtilities.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * This test covers a bug reported in the forum:
     *
     * http://www.jfree.org/phpBB2/viewtopic.php?t=19980
     */
    @Test
    public void testKeysOfDifferentClasses() {
        PaintMap m = new PaintMap();
        m.put("ABC", Color.red);
        m.put(new Integer(99), Color.blue);
        assertEquals(Color.blue, m.getPaint(new Integer(99)));
    }

}

