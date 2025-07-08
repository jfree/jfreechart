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
 * ----------------------------
 * CategoryLabelEntityTest.java
 * ----------------------------
 * (C) Copyright 2007-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */
package org.jfree.chart.entity;

import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link CategoryLabelEntity} class.
 */
public class CategoryLabelEntityTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashcode() {
        EqualsVerifier.forClass(CategoryLabelEntity.class)
            .withRedefinedSuperclass() // superclass also defines equals/hashCode
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .suppress(Warning.TRANSIENT_FIELDS)
            .verify();
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        CategoryLabelEntity e1 = new CategoryLabelEntity("A",
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), "ToolTip", "URL");
        CategoryLabelEntity e2 = new CategoryLabelEntity("A",
                                                         new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), "ToolTip", "URL");
        assertEquals(e1, e2);

        e1 = new CategoryLabelEntity("B", new Rectangle2D.Double(1.0, 2.0,
                3.0, 4.0), "ToolTip", "URL");
        assertNotEquals(e1, e2);
        e2 = new CategoryLabelEntity("B", new Rectangle2D.Double(1.0, 2.0,
                                                                 3.0, 4.0), "ToolTip", "URL");
        assertEquals(e1, e2);

        e1.setArea(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertNotEquals(e1, e2);
        e2.setArea(new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0));
        assertEquals(e1, e2);
        
        e1.setToolTipText("New ToolTip");
        assertNotEquals(e1, e2);
        e2.setToolTipText("New ToolTip");
        assertEquals(e1, e2);

        e1.setURLText("New URL");
        assertNotEquals(e1, e2);
        e2.setURLText("New URL");
        assertEquals(e1, e2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException if there is a problem cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryLabelEntity e1 = new CategoryLabelEntity("A",
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), "ToolTip", "URL");
        CategoryLabelEntity e2 = (CategoryLabelEntity) e1.clone();
        assertNotSame(e1, e2);
        assertSame(e1.getClass(), e2.getClass());
        assertEquals(e1, e2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryLabelEntity e1 = new CategoryLabelEntity("A",
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), "ToolTip", "URL");
        CategoryLabelEntity e2 = TestUtils.serialised(e1);
        assertEquals(e1, e2);
    }

}
