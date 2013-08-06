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
 * ---------------------------------
 * DefaultPolarItemRendererTest.java
 * ---------------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Aug-2006 : Version 1 (DG);
 * 15-Mar-2007 : Added independence check to testCloning() (DG);
 *
 */

package org.jfree.chart.renderer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link DefaultPolarItemRenderer} class.
 */
public class DefaultPolarItemRendererTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        DefaultPolarItemRenderer r1 = new DefaultPolarItemRenderer();
        DefaultPolarItemRenderer r2 = new DefaultPolarItemRenderer();
        assertEquals(r1, r2);

        r1.setSeriesFilled(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesFilled(1, true);
        assertTrue(r1.equals(r2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        DefaultPolarItemRenderer r1 = new DefaultPolarItemRenderer();
        DefaultPolarItemRenderer r2 = new DefaultPolarItemRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultPolarItemRenderer r1 = new DefaultPolarItemRenderer();
        DefaultPolarItemRenderer r2 = (DefaultPolarItemRenderer) r1.clone();

        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));

        r1.setSeriesFilled(1, true);
        assertFalse(r1.equals(r2));
        r2.setSeriesFilled(1, true);
        assertTrue(r1.equals(r2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultPolarItemRenderer r1 = new DefaultPolarItemRenderer();
        DefaultPolarItemRenderer r2 = (DefaultPolarItemRenderer) 
                TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

}
