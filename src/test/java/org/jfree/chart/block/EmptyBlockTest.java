/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * -------------------
 * EmptyBlockTest.java
 * -------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.block;

import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link EmptyBlock} class.
 */
public class EmptyBlockTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(EmptyBlock.class)
            .withPrefabValues(Rectangle2D.class,
                              TestUtils.createR2D(true),
                              TestUtils.createR2D(false))
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .suppress(Warning.TRANSIENT_FIELDS)
            .verify();
    }

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        EmptyBlock b1 = new EmptyBlock(1.0, 2.0);
        EmptyBlock b2 = new EmptyBlock(1.0, 2.0);
        assertEquals(b1, b2);
        assertEquals(b2, b2);

        b1 = new EmptyBlock(1.1, 2.0);
        assertNotEquals(b1, b2);
        b2 = new EmptyBlock(1.1, 2.0);
        assertEquals(b1, b2);

        b1 = new EmptyBlock(1.1, 2.2);
        assertNotEquals(b1, b2);
        b2 = new EmptyBlock(1.1, 2.2);
        assertEquals(b1, b2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        EmptyBlock b1 = new EmptyBlock(1.0, 2.0);
        EmptyBlock b2 = null;

        try {
            b2 = (EmptyBlock) b1.clone();
        }
        catch (CloneNotSupportedException e) {
            fail(e.toString());
        }
        assertNotSame(b1, b2);
        assertSame(b1.getClass(), b2.getClass());
        assertEquals(b1, b2);
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
