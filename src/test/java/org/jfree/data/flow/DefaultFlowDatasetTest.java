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
 * ---------------------------
 * DefaultFlowDatasetTest.java
 * ---------------------------
 * (C) Copyright 2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.PublicCloneable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link DefaultFlowDataset} class.
 */
public class DefaultFlowDatasetTest {

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetFlow() {
        DefaultFlowDataset<String> d = new DefaultFlowDataset<>();
        d.setFlow(0, "A", "Z", 1.5);
        assertEquals(1.5, d.getFlow(0, "A", "Z"));
    }

    /**
     * Some tests for the getStageCount() method.
     */
    @Test
    public void testGetStageCount() {
        DefaultFlowDataset<String> d = new DefaultFlowDataset<>();
        assertEquals(1, d.getStageCount());

        d.setFlow(0, "A", "Z", 11.1);
        assertEquals(1, d.getStageCount());

        // a row of all null values is still counted...
        d.setFlow(1, "Z", "P", 5.0);
        assertEquals(2, d.getStageCount());
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DefaultFlowDataset<String> d1 = new DefaultFlowDataset<>();
        DefaultFlowDataset<String> d2 = new DefaultFlowDataset<>();
        assertEquals(d1, d2);
        
        d1.setFlow(0, "A", "Z", 1.0);
        assertNotEquals(d1, d2);
        d2.setFlow(0, "A", "Z", 1.0);
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultFlowDataset<String> d1 = new DefaultFlowDataset<>();
        d1.setFlow(0, "A", "Z", 1.0);
        DefaultFlowDataset<String> d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DefaultFlowDataset<String> d1 = new DefaultFlowDataset<>();
        d1.setFlow(0, "A", "Z", 1.0);
        DefaultFlowDataset<String> d2 = (DefaultFlowDataset<String>) d1.clone();

        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));

        // check that the clone doesn't share the same underlying arrays.
        d1.setFlow(0, "A", "Y", 8.0);
        assertNotEquals(d1, d2);
        d2.setFlow(0, "A", "Y", 8.0);
        assertEquals(d1, d2);
    }

    /**
     * Check that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        DefaultFlowDataset<String> d = new DefaultFlowDataset<>();
        assertTrue(d instanceof PublicCloneable);
    }

}

