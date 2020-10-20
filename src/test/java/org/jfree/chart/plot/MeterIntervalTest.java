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
 * ----------------------
 * MeterIntervalTest.java
 * ----------------------
 * (C) Copyright 2005-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Mar-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;

import org.jfree.chart.TestUtils;

import org.jfree.data.Range;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link MeterInterval} class.
 */
public class MeterIntervalTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        MeterInterval m1 = new MeterInterval(
            "Label 1", new Range(1.2, 3.4), Color.RED, new BasicStroke(1.0f),
            Color.BLUE
        );
        MeterInterval m2 = new MeterInterval(
            "Label 1", new Range(1.2, 3.4), Color.RED, new BasicStroke(1.0f),
            Color.BLUE
        );
        assertTrue(m1.equals(m2));
        assertTrue(m2.equals(m1));

        m1 = new MeterInterval(
            "Label 2", new Range(1.2, 3.4), Color.RED, new BasicStroke(1.0f),
            Color.BLUE
        );
        assertFalse(m1.equals(m2));
        m2 = new MeterInterval(
            "Label 2", new Range(1.2, 3.4), Color.RED, new BasicStroke(1.0f),
            Color.BLUE
        );
        assertTrue(m1.equals(m2));

    }

    /**
     * This class is immutable so cloning isn't required.
     */
    @Test
    public void testCloning() {
        MeterInterval m1 = new MeterInterval("X", new Range(1.0, 2.0));
        assertFalse(m1 instanceof Cloneable);
    }

   /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MeterInterval m1 = new MeterInterval("X", new Range(1.0, 2.0));
        MeterInterval m2 = (MeterInterval) TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

}
