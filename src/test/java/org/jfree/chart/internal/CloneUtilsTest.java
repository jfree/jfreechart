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
 * -------------------
 * CloneUtilsTest.java
 * -------------------
 * (C) Copyright 2008-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.internal;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CloneUtils} class.
 */
public class CloneUtilsTest {

    /**
     * String is immutable so we expect to get back the same object.
     */
    @Test
    public void testCopyString() throws CloneNotSupportedException {
        String s1 = "ABC";
        String s2 = CloneUtils.copy(s1);
        assertSame(s1, s2);
    }
    
    /**
     * The goal of this test is to verify that we can make a clone of a list
     * whether it contains Strings (which are immutable therefore do not 
     * support cloning) or some mutable object like Rectangle.
     * 
     * @throws CloneNotSupportedException 
     */
    @Test
    public void testCloneListOfStrings() throws CloneNotSupportedException {
        List<String> l1 = new ArrayList<>();
        l1.add("ABC");
        List<String> l2 = CloneUtils.clone(l1);
        assertEquals(l1, l2);
        
        l1.add("A");
        assertNotEquals(l1, l2);
        l2 = CloneUtils.cloneList(l1);
        assertEquals(l1, l2);
        
        List<Rectangle> rList1 = new ArrayList<>();
        Rectangle r1 = new Rectangle(1, 2, 3, 4);
        rList1.add(r1);
        List<Rectangle> rList2 = CloneUtils.clone(rList1);
        assertEquals(rList1, rList2);
    }

}

