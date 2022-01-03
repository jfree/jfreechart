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
 * --------------------------
 * DeviationStepRendererTest.java
 * --------------------------
 * (C) Copyright 2020-2022, by David Gilbert and Contributors.
 *
 * Original Author:  https://github.com/phl;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.renderer.xy;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.PublicCloneable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DeviationStepRenderer} class.
 */
public class DeviationStepRendererTest {

    /**
     * Test that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        // default instances
        DeviationStepRenderer r1 = new DeviationStepRenderer();
        DeviationStepRenderer r2 = new DeviationStepRenderer();
        assertEquals(r1, r2);
        assertEquals(r2, r1);

        r1.setAlpha(0.1f);
        assertNotEquals(r1, r2);
        r2.setAlpha(0.1f);
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        DeviationStepRenderer r1 = new DeviationStepRenderer();
        DeviationStepRenderer r2 = new DeviationStepRenderer();
        assertEquals(r1, r2);
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DeviationStepRenderer r1 = new DeviationStepRenderer();
        DeviationStepRenderer r2 = (DeviationStepRenderer) r1.clone();
        assertNotSame(r1, r2);
        assertSame(r1.getClass(), r2.getClass());
        assertEquals(r1, r2);
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        DeviationStepRenderer r1 = new DeviationStepRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DeviationStepRenderer r1 = new DeviationStepRenderer();
        DeviationStepRenderer r2 = TestUtils.serialised(r1);
        assertEquals(r1, r2);
    }

}
