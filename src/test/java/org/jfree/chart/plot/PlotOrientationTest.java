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
 * ------------------------
 * PlotOrientationTest.java
 * ------------------------
 * (C) Copyright 2004-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-Apr-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PlotOrientation} class.
 *
 */
public class PlotOrientationTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        assertEquals(PlotOrientation.HORIZONTAL, PlotOrientation.HORIZONTAL);
        assertEquals(PlotOrientation.VERTICAL, PlotOrientation.VERTICAL);
        assertFalse(
            PlotOrientation.HORIZONTAL.equals(PlotOrientation.VERTICAL)
        );
        assertFalse(
            PlotOrientation.VERTICAL.equals(PlotOrientation.HORIZONTAL)
        );
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PlotOrientation orientation1 = PlotOrientation.HORIZONTAL;
        PlotOrientation orientation2 = (PlotOrientation) 
                TestUtils.serialised(orientation1);
        assertEquals(orientation1, orientation2);
        boolean same = orientation1 == orientation2;
        assertEquals(true, same);
    }

}
