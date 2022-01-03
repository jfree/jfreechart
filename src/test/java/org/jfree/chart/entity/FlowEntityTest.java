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
 * -------------------
 * FlowEntityTest.java
 * -------------------
 * (C) Copyright 2021-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.entity;

import java.awt.Rectangle;
import org.jfree.chart.TestUtils;
import org.jfree.data.flow.FlowKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link FlowEntity} class.
 */
public class FlowEntityTest {
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        FlowEntity f1 = new FlowEntity(new FlowKey<>(0, "A", "B"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        FlowEntity f2 = new FlowEntity(new FlowKey<>(0, "A", "B"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        assertEquals(f1, f2);
        assertEquals(f2, f1);

        f1 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        assertNotEquals(f1, f2);
        f2 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        assertEquals(f1, f2);

        f1 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "tt", "uu");
        assertNotEquals(f1, f2);
        f2 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "tt", "uu");
        assertEquals(f1, f2);
  
        f1 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "TT", "uu");
        assertNotEquals(f1, f2);
        f2 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "TT", "uu");
        assertEquals(f1, f2);

        f1 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "TT", "UU");
        assertNotEquals(f1, f2);
        f2 = new FlowEntity(new FlowKey<>(0, "A", "C"), new Rectangle(4, 1, 2, 3), "TT", "UU");
        assertEquals(f1, f2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        FlowEntity f1 = new FlowEntity(new FlowKey<>(0, "A", "B"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        FlowEntity f2 = (FlowEntity) f1.clone();
        assertNotSame(f1, f2);
        assertSame(f1.getClass(), f2.getClass());
        assertEquals(f1, f2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        FlowEntity f1 = new FlowEntity(new FlowKey<>(0, "A", "B"), new Rectangle(0, 1, 2, 3), "tt", "uu");
        FlowEntity f2 = (FlowEntity) TestUtils.serialised(f1);
        assertEquals(f1, f2);
    }
 
}
