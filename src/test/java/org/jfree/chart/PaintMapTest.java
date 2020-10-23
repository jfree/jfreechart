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
 * -----------------
 * PaintMapTest.java
 * -----------------
 * (C) Copyright 2006-2020, by Object Refinery Limited.
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

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.GradientPaint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

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
        m1.put("A", Color.RED);
        assertEquals(Color.RED, m1.getPaint("A"));
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
        m1.put("A", Color.RED);
        assertEquals(Color.RED, m1.getPaint("A"));

        // a null key should throw an IllegalArgumentException

        try {
            m1.put(null, Color.BLUE);
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

        m1.put("K1", Color.RED);
        assertFalse(m1.equals(m2));
        m2.put("K1", Color.RED);
        assertEquals(m1, m2);

        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.GREEN, 3.0f, 4.0f,
                Color.YELLOW));
        assertFalse(m1.equals(m2));
        m2.put("K2", new GradientPaint(1.0f, 2.0f, Color.GREEN, 3.0f, 4.0f,
                Color.YELLOW));
        assertEquals(m1, m2);

        m1.put("K2", null);
        assertFalse(m1.equals(m2));
        m2.put("K2", null);
        assertEquals(m1, m2);
    }

    /**
     * Some checks for cloning.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PaintMap m1 = new PaintMap();
        PaintMap m2 = (PaintMap) m1.clone();
        assertEquals(m1, m2);

        m1.put("K1", Color.RED);
        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.GREEN, 3.0f, 4.0f,
                Color.YELLOW));
        m2 = (PaintMap) m1.clone();
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization1() {
        PaintMap m1 = new PaintMap();
        PaintMap m2 = (PaintMap) TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    @Test
    public void testSerialization2() {
        PaintMap m1 = new PaintMap();
        m1.put("K1", Color.RED);
        m1.put("K2", new GradientPaint(1.0f, 2.0f, Color.GREEN, 3.0f, 4.0f,
                Color.YELLOW));
        PaintMap m2 = (PaintMap) TestUtils.serialised(m1);
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
        m.put("ABC", Color.RED);
        m.put(99, Color.BLUE);
        assertEquals(Color.BLUE, m.getPaint(99));
    }

}

