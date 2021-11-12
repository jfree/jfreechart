/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by David Gilbert and Contributors.
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
 * --------------------
 * BlockBorderTest.java
 * --------------------
 * (C) Copyright 2005-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.GradientPaint;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.util.PaintUtils;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link BlockBorder} class.
 */
public class BlockBorderTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashcode() {
        EqualsVerifier.forClass(BlockBorder.class)
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }

    /**
     * Immutable - cloning not necessary.
     */
    @Test
    public void testCloning() {
        BlockBorder b1 = new BlockBorder();
        assertFalse(b1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        BlockBorder b1 = new BlockBorder(new RectangleInsets(1.0, 2.0, 3.0,
                4.0), new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f,
                Color.YELLOW));
        BlockBorder b2 = TestUtils.serialised(b1);

        // transient field 'paint' not included in equals or hashCode, so test
        // equality using PaintUtils method
        assertTrue(PaintUtils.equal(b1.getPaint(), b2.getPaint()));
    }

}
