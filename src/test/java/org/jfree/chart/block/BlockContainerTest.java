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
 * -----------------------
 * BlockContainerTest.java
 * -----------------------
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
 * Tests for the {@link BlockContainer} class.
 */
public class BlockContainerTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(BlockContainer.class)
            .withRedefinedSuperclass() // superclass also defines equals/hashCode
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
        BlockContainer c1 = new BlockContainer(new FlowArrangement());
        BlockContainer c2 = new BlockContainer(new FlowArrangement());
        assertEquals(c1, c2);
        assertEquals(c2, c2);

        c1.setArrangement(new ColumnArrangement());
        assertNotEquals(c1, c2);
        c2.setArrangement(new ColumnArrangement());
        assertEquals(c1, c2);

        c1.add(new EmptyBlock(1.2, 3.4));
        assertNotEquals(c1, c2);
        c2.add(new EmptyBlock(1.2, 3.4));
        assertEquals(c1, c2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException if there is a problem cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        BlockContainer c1 = new BlockContainer(new FlowArrangement());
        c1.add(new EmptyBlock(1.2, 3.4));
        BlockContainer c2 = (BlockContainer) c1.clone();
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        BlockContainer c1 = new BlockContainer();
        c1.add(new EmptyBlock(1.2, 3.4));
        BlockContainer c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

}
