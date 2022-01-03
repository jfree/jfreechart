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
 * ---------------------
 * SymbolicAxisTest.java
 * ---------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.Color;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SymbolAxis} class.
 */
public class SymbolAxisTest {

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        String[] tickLabels = new String[] {"One", "Two", "Three"};
        SymbolAxis a1 = new SymbolAxis("Test Axis", tickLabels);
        SymbolAxis a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = CloneUtils.clone(a1);
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = new SymbolAxis("Axis", new String[] {"A", "B"});
        assertEquals(a1, a2);
        assertEquals(a2, a1);

        a1 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertNotEquals(a1, a2);
        a2 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertEquals(a1, a2);

        a1 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertNotEquals(a1, a2);
        a2 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertEquals(a1, a2);

        a1.setGridBandsVisible(false);
        assertNotEquals(a1, a2);
        a2.setGridBandsVisible(false);
        assertEquals(a1, a2);

        a1.setGridBandPaint(Color.BLACK);
        assertNotEquals(a1, a2);
        a2.setGridBandPaint(Color.BLACK);
        assertEquals(a1, a2);

        a1.setGridBandAlternatePaint(Color.RED);
        assertNotEquals(a1, a2);
        a2.setGridBandAlternatePaint(Color.RED);
        assertEquals(a1, a2);
    }

}
