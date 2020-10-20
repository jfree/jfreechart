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
 * ---------------------
 * SymbolicAxisTest.java
 * ---------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 25-Jul-2007 : Added new field in testEquals() (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

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
        SymbolAxis a2 = (SymbolAxis) TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = (SymbolAxis) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        SymbolAxis a1 = new SymbolAxis("Axis", new String[] {"A", "B"});
        SymbolAxis a2 = new SymbolAxis("Axis", new String[] {"A", "B"});
        assertTrue(a1.equals(a2));
        assertTrue(a2.equals(a1));

        a1 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertFalse(a1.equals(a2));
        a2 = new SymbolAxis("Axis 2", new String[] {"A", "B"});
        assertTrue(a1.equals(a2));

        a1 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertFalse(a1.equals(a2));
        a2 = new SymbolAxis("Axis 2", new String[] {"C", "B"});
        assertTrue(a1.equals(a2));

        a1.setGridBandsVisible(false);
        assertFalse(a1.equals(a2));
        a2.setGridBandsVisible(false);
        assertTrue(a1.equals(a2));

        a1.setGridBandPaint(Color.BLACK);
        assertFalse(a1.equals(a2));
        a2.setGridBandPaint(Color.BLACK);
        assertTrue(a1.equals(a2));

        a1.setGridBandAlternatePaint(Color.RED);
        assertFalse(a1.equals(a2));
        a2.setGridBandAlternatePaint(Color.RED);
        assertTrue(a1.equals(a2));
    }

}
