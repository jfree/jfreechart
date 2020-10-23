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
 * ------------------------------------
 * DefaultKeyedValues2DDatasetTest.java
 * ------------------------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.general;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the {@link DefaultKeyedValues2DDataset} class.
 */
public class DefaultKeyedValues2DDatasetTest {

    /**
     * Confirm that cloning works.
     * 
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultKeyedValues2DDataset d1 = new DefaultKeyedValues2DDataset();
        d1.setValue(1, "V1", "C1");
        d1.setValue(null, "V2", "C1");
        d1.setValue(3, "V3", "C2");
        DefaultKeyedValues2DDataset d2 = (DefaultKeyedValues2DDataset) 
                d1.clone();
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultKeyedValues2DDataset d1 = new DefaultKeyedValues2DDataset();
        d1.addValue(234.2, "Row1", "Col1");
        d1.addValue(null, "Row1", "Col2");
        d1.addValue(345.9, "Row2", "Col1");
        d1.addValue(452.7, "Row2", "Col2");

        DefaultKeyedValues2DDataset d2 = (DefaultKeyedValues2DDataset) 
                TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

}
