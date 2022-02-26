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
 * ----------------------
 * AbstractBlockTest.java
 * ----------------------
 * (C) Copyright 2007-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.block;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.TestUtils;
import org.jfree.chart.api.RectangleInsets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link AbstractBlock} class.
 */
public class AbstractBlockTest {

    /**
     * Confirm that the equals() method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        EmptyBlock b1 = new EmptyBlock(1.0, 2.0);
        EmptyBlock b2 = new EmptyBlock(1.0, 2.0);
        assertEquals(b1, b2);
        assertEquals(b2, b2);

        b1.setID("Test");
        assertNotEquals(b1, b2);
        b2.setID("Test");
        assertEquals(b1, b2);

        b1.setMargin(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(b1, b2);
        b2.setMargin(new RectangleInsets(1.0, 2.0, 3.0, 4.0));
        assertEquals(b1, b2);

        b1.setFrame(new BlockBorder(Color.RED));
        assertNotEquals(b1, b2);
        b2.setFrame(new BlockBorder(Color.RED));
        assertEquals(b1, b2);

        b1.setPadding(new RectangleInsets(2.0, 4.0, 6.0, 8.0));
        assertNotEquals(b1, b2);
        b2.setPadding(new RectangleInsets(2.0, 4.0, 6.0, 8.0));
        assertEquals(b1, b2);

        b1.setWidth(1.23);
        assertNotEquals(b1, b2);
        b2.setWidth(1.23);
        assertEquals(b1, b2);

        b1.setHeight(4.56);
        assertNotEquals(b1, b2);
        b2.setHeight(4.56);
        assertEquals(b1, b2);

        b1.setBounds(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(b1, b2);
        b2.setBounds(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(b1, b2);

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
        Rectangle2D bounds1 = new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0);
        b1.setBounds(bounds1);
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

        bounds1.setFrame(2.0, 4.0, 6.0, 8.0);
        assertNotEquals(b1, b2);
        b2.setBounds(new Rectangle2D.Double(2.0, 4.0, 6.0, 8.0));
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
