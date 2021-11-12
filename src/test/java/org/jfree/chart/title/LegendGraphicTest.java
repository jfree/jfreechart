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
 * LegendGraphicTest.java
 * ----------------------
 * (C) Copyright 2005-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.title;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.chart.util.ShapeUtils;

import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link LegendGraphic} class.
 */
public class LegendGraphicTest {

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(LegendGraphic.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .withRedefinedSuperclass()
                .withPrefabValues(Rectangle2D.class,
                                  TestUtils.createR2D(true),
                                  TestUtils.createR2D(false))
                .withPrefabValues(Font.class,
                                  TestUtils.createFont(true),
                                  TestUtils.createFont(false))
                .verify();
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Rectangle r = new Rectangle(1, 2, 3, 4);
        LegendGraphic g1 = new LegendGraphic(r, Color.BLACK);
        LegendGraphic g2 = (LegendGraphic) g1.clone();
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        r.setBounds(4, 3, 2, 1);
        assertFalse(ShapeUtils.equal(g1.getShape(), g2.getShape()));
    }

    /**
     * A test for cloning - checks that the line shape is cloned correctly.
     */
    @Test
    public void testCloning2() throws CloneNotSupportedException {
        Rectangle r = new Rectangle(1, 2, 3, 4);
        LegendGraphic g1 = new LegendGraphic(r, Color.BLACK);
        Line2D l = new Line2D.Double(1.0, 2.0, 3.0, 4.0);
        g1.setLine(l);
        LegendGraphic g2 = (LegendGraphic) g1.clone();
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));

        // check independence
        l.setLine(4.0, 3.0, 2.0, 1.0);
        assertFalse(ShapeUtils.equal(g1.getLine(), g2.getLine()));

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Stroke s = new BasicStroke(1.23f);
        LegendGraphic g1 = new LegendGraphic(new Rectangle2D.Double(1.0, 2.0, 
                3.0, 4.0), Color.BLACK);
        g1.setOutlineStroke(s);
        LegendGraphic g2 = TestUtils.serialised(g1);
        assertTrue(g1.equals(g2));
    }

}
