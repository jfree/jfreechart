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
 * CategoryMarkerTest.java
 * -----------------------
 * (C) Copyright 2005-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link CategoryMarker} class.
 */
public class CategoryMarkerTest implements MarkerChangeListener {

    MarkerChangeEvent lastEvent;

    /**
     * Records the last event.
     *
     * @param event  the last event.
     */
    @Override
    public void markerChanged(MarkerChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(CategoryMarker.class)
                .withRedefinedSuperclass() // superclass also defines equals/hashCode
                .withPrefabValues(Font.class, new Font("SansSerif", Font.PLAIN, 10), new Font("Tahoma", Font.BOLD, 12))
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
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
        CategoryMarker m1 = new CategoryMarker("A");
        CategoryMarker m2 = new CategoryMarker("A");
        assertEquals(m1, m2);
        assertEquals(m2, m1);

        //key
        m1 = new CategoryMarker("B");
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("B");
        assertEquals(m1, m2);

        //paint
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(1.1f));
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(1.1f));
        assertEquals(m1, m2);

        //stroke
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f));
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f));
        assertEquals(m1, m2);

        //outlinePaint
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 1.0f);
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 1.0f);
        assertEquals(m1, m2);

        //outlineStroke
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(3.3f), 1.0f);
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(3.3f), 1.0f);
        assertEquals(m1, m2);

        //alpha
        m1 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 0.5f);
        assertNotEquals(m1, m2);
        m2 = new CategoryMarker("A", new GradientPaint(1.0f, 2.0f, Color.WHITE,
                3.0f, 4.0f, Color.YELLOW), new BasicStroke(2.2f), Color.RED,
                new BasicStroke(1.0f), 0.5f);
        assertEquals(m1, m2);

    }

    /**
     * Check cloning.
     * @throws java.lang.CloneNotSupportedException if there is a cloning issue.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryMarker m1 = new CategoryMarker("A", new GradientPaint(1.0f,
                2.0f, Color.WHITE, 3.0f, 4.0f, Color.YELLOW),
                new BasicStroke(1.1f));
        CategoryMarker m2 = (CategoryMarker) m1.clone();
        assertNotSame(m1, m2);
        assertSame(m1.getClass(), m2.getClass());
        assertEquals(m1, m2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryMarker m1 = new CategoryMarker("A", new GradientPaint(1.0f,
                2.0f, Color.WHITE, 3.0f, 4.0f, Color.YELLOW),
                new BasicStroke(1.1f));
        CategoryMarker m2 = TestUtils.serialised(m1);
        assertEquals(m1, m2);
    }

    /**
     * Some checks for the getKey() and setKey() methods.
     */
    @Test
    public void testGetSetKey() {
        CategoryMarker m = new CategoryMarker("X");
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals("X", m.getKey());
        m.setKey("Y");
        assertEquals("Y", m.getKey());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setKey(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getDrawAsLine() and setDrawAsLine() methods.
     */
    @Test
    public void testGetSetDrawAsLine() {
        CategoryMarker m = new CategoryMarker("X");
        m.addChangeListener(this);
        this.lastEvent = null;
        assertFalse(m.getDrawAsLine());
        m.setDrawAsLine(true);
        assertTrue(m.getDrawAsLine());
        assertEquals(m, this.lastEvent.getMarker());
    }
}
