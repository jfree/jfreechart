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
 * ---------------------------------
 * MeanAndStandardDeviationTest.java
 * ---------------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.statistics;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MeanAndStandardDeviation} class.
 */
public class MeanAndStandardDeviationTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        MeanAndStandardDeviation m2 = new MeanAndStandardDeviation(1.2, 3.4);
        assertEquals(m1, m2);
        assertEquals(m2, m1);

        m1 = new MeanAndStandardDeviation(1.0, 3.4);
        assertNotEquals(m1, m2);
        m2 = new MeanAndStandardDeviation(1.0, 3.4);
        assertEquals(m1, m2);

        m1 = new MeanAndStandardDeviation(1.0, 3.0);
        assertNotEquals(m1, m2);
        m2 = new MeanAndStandardDeviation(1.0, 3.0);
        assertEquals(m1, m2);
    }

    /**
     * Immutable class - should not be cloneable.
     */
    @Test
    public void testCloning() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        assertFalse(m1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MeanAndStandardDeviation m1 = new MeanAndStandardDeviation(1.2, 3.4);
        MeanAndStandardDeviation m2 = TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }
}
