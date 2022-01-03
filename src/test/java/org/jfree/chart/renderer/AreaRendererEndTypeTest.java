/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ----------------------------
 * AreaRendererEndTypeTest.java
 * ----------------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link AreaRendererEndType} class.
 */
public class AreaRendererEndTypeTest {

    /**
     * A test for the equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(AreaRendererEndType.LEVEL, AreaRendererEndType.LEVEL);
        assertEquals(AreaRendererEndType.TAPER, AreaRendererEndType.TAPER);
        assertEquals(AreaRendererEndType.TRUNCATE, AreaRendererEndType.TRUNCATE);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        AreaRendererEndType t1 = AreaRendererEndType.TAPER;
        AreaRendererEndType t2 = TestUtils.serialised(t1);
        assertEquals(t1, t2);
        boolean same = t1 == t2;
        assertTrue(same);
    }

}
