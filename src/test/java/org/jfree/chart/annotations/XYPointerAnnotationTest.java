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
 * XYPointerAnnotationTest.java
 * ----------------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import static org.jfree.chart.TestUtils.createFont;
import static org.junit.jupiter.api.Assertions.*;

import org.jfree.chart.util.PublicCloneable;

import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

/**
 * Tests for the {@link XYPointerAnnotation} class.
 */
public class XYPointerAnnotationTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(XYPointerAnnotation.class)
                .withRedefinedSuperclass() // superclass also defines equals/hashCode
                .withPrefabValues(Font.class, createFont(true), createFont(false))
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
        XYPointerAnnotation a1 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        XYPointerAnnotation a2 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        assertEquals(a1, a2);

        a1 = new XYPointerAnnotation("Label2", 10.0, 20.0, Math.PI);
        assertNotEquals(a1, a2);
        a2 = new XYPointerAnnotation("Label2", 10.0, 20.0, Math.PI);
        assertEquals(a1, a2);

        a1.setX(11.0);
        assertNotEquals(a1, a2);
        a2.setX(11.0);
        assertEquals(a1, a2);

        a1.setY(22.0);
        assertNotEquals(a1, a2);
        a2.setY(22.0);
        assertEquals(a1, a2);

        //private double angle;
        a1.setAngle(Math.PI / 4.0);
        assertNotEquals(a1, a2);
        a2.setAngle(Math.PI / 4.0);
        assertEquals(a1, a2);

        //private double tipRadius;
        a1.setTipRadius(20.0);
        assertNotEquals(a1, a2);
        a2.setTipRadius(20.0);
        assertEquals(a1, a2);

        //private double baseRadius;
        a1.setBaseRadius(5.0);
        assertNotEquals(a1, a2);
        a2.setBaseRadius(5.0);
        assertEquals(a1, a2);

        //private double arrowLength;
        a1.setArrowLength(33.0);
        assertNotEquals(a1, a2);
        a2.setArrowLength(33.0);
        assertEquals(a1, a2);

        //private double arrowWidth;
        a1.setArrowWidth(9.0);
        assertNotEquals(a1, a2);
        a2.setArrowWidth(9.0);
        assertEquals(a1, a2);

        //private Stroke arrowStroke;
        Stroke stroke = new BasicStroke(1.5f);
        a1.setArrowStroke(stroke);
        assertNotEquals(a1, a2);
        a2.setArrowStroke(stroke);
        assertEquals(a1, a2);

        //private Paint arrowPaint;
        a1.setArrowPaint(Color.BLUE);
        assertNotEquals(a1, a2);
        a2.setArrowPaint(Color.BLUE);
        assertEquals(a1, a2);

        //private double labelOffset;
        a1.setLabelOffset(10.0);
        assertNotEquals(a1, a2);
        a2.setLabelOffset(10.0);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        XYPointerAnnotation a1 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        XYPointerAnnotation a2 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException if there is an issue cloning
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYPointerAnnotation a1 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        XYPointerAnnotation a2 = (XYPointerAnnotation) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        XYPointerAnnotation a1 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYPointerAnnotation a1 = new XYPointerAnnotation("Label", 10.0, 20.0,
                Math.PI);
        XYPointerAnnotation a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}
