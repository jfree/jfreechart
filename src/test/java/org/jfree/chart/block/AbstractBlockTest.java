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
 * ----------------------
 * AbstractBlockTest.java
 * ----------------------
 * (C) Copyright 2007-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.block;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.RectangleInsets;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link AbstractBlock} class.
 */
public class AbstractBlockTest{

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashcode() {
        EqualsVerifier.forClass(AbstractBlock.class)
            // Add prefab values for java.awt.geom.Rectangle2D.
            .withPrefabValues(Rectangle2D.class,
                              new Rectangle2D.Double(0, 0, 1, 1),
                              new Rectangle2D.Double(1, 1, 2, 2))
            .withRedefinedSubclass(BlockContainer.class) // subclass(es) also define equals/hashCode
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        EmptyBlock b1 = new EmptyBlock(1.0, 2.0);
        Rectangle2D bounds1 = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        b1.setBounds(bounds1);
        EmptyBlock b2 = null;

        try {
            b2 = (EmptyBlock) b1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail(e.toString());
        }
        assertTrue(b1 != b2);
        assertTrue(b1.getClass() == b2.getClass());
        assertTrue(b1.equals(b2));

        // transient field 'bounds' not included in equals or hashCode, so test
        // using the 'get' method
        bounds1.setFrame(2.0, 4.0, 6.0, 8.0);
        assertFalse(b1.getBounds().equals(b2.getBounds()));
        b2.setBounds(new Rectangle2D.Double(2.0, 4.0, 6.0, 8.0));
        assertTrue(b1.getBounds().equals(b2.getBounds()));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        EmptyBlock b1 = new EmptyBlock(1.0, 2.0);
        EmptyBlock b2 = TestUtils.serialised(b1);
        assertEquals(b1, b2);
    }

}
