/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * -------------------
 * CloneUtilsTest.java
 * -------------------
 * (C) Copyright 2008-2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.internal;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Tests for the {@link CloneUtils} class.
 */
public class CloneUtilsTest {

    /**
     * String is immutable so we expect to get back the same object.
     */
    @Test
    public void testCloneString() throws CloneNotSupportedException {
        String s1 = "ABC";
        String s2 = CloneUtils.copy(s1);
        assertTrue(s1 == s2);
    }
    
    @Test
    public void testCloneListOfStrings() throws CloneNotSupportedException {
        List<String> l1 = new ArrayList<>();
        List<String> l2 = CloneUtils.clone(l1);
        assertEquals(l1, l2);
        
        l1.add("A");
        assertNotEquals(l1, l2);
        l2 = CloneUtils.cloneList(l1);
        assertEquals(l1, l2);
    }

}

