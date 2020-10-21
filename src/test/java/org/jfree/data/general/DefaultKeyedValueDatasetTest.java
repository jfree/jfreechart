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
 * ---------------------------------
 * DefaultKeyedValueDatasetTest.java
 * ---------------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-Aug-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.general;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.jfree.chart.TestUtils;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DefaultKeyedValueDataset} class.
 */
public class DefaultKeyedValueDatasetTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        DefaultKeyedValueDataset d1
                = new DefaultKeyedValueDataset("Test", 45.5);
        DefaultKeyedValueDataset d2
                = new DefaultKeyedValueDataset("Test", 45.5);
        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        d1 = new DefaultKeyedValueDataset("Test 1", 45.5);
        d2 = new DefaultKeyedValueDataset("Test 2", 45.5);
        assertFalse(d1.equals(d2));

        d1 = new DefaultKeyedValueDataset("Test", 45.5);
        d2 = new DefaultKeyedValueDataset("Test", 45.6);
        assertFalse(d1.equals(d2));

    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValueDataset d1
                = new DefaultKeyedValueDataset("Test", 45.5);
        DefaultKeyedValueDataset d2 = (DefaultKeyedValueDataset) d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Confirm that the clone is independent of the original.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloneIndependence() throws CloneNotSupportedException {
        DefaultKeyedValueDataset d1
            = new DefaultKeyedValueDataset("Key", 10.0);
        DefaultKeyedValueDataset d2 = (DefaultKeyedValueDataset) d1.clone();
        assertTrue(d1.equals(d2));
        d2.updateValue(99.9);
        assertFalse(d1.equals(d2));
        d2.updateValue(10.0);
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValueDataset d1
                = new DefaultKeyedValueDataset("Test", 25.3);
        DefaultKeyedValueDataset d2 = (DefaultKeyedValueDataset) 
                TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

}
