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
 * BlockContainerTest.java
 * -----------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Feb-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.block;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link BlockContainer} class.
 */
public class BlockContainerTest {

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        BlockContainer c1 = new BlockContainer(new FlowArrangement());
        BlockContainer c2 = new BlockContainer(new FlowArrangement());
        assertTrue(c1.equals(c2));
        assertTrue(c2.equals(c2));

        c1.setArrangement(new ColumnArrangement());
        assertFalse(c1.equals(c2));
        c2.setArrangement(new ColumnArrangement());
        assertTrue(c1.equals(c2));

        c1.add(new EmptyBlock(1.2, 3.4));
        assertFalse(c1.equals(c2));
        c2.add(new EmptyBlock(1.2, 3.4));
        assertTrue(c1.equals(c2));
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        BlockContainer c1 = new BlockContainer(new FlowArrangement());
        c1.add(new EmptyBlock(1.2, 3.4));
        BlockContainer c2 = (BlockContainer) c1.clone();
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        BlockContainer c1 = new BlockContainer();
        c1.add(new EmptyBlock(1.2, 3.4));
        BlockContainer c2 = (BlockContainer) TestUtilities.serialised(c1);
        assertEquals(c1, c2);
    }

}
