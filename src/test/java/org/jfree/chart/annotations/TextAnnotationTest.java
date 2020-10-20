/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -----------------------
 * TextAnnotationTest.java
 * -----------------------
 * (C) Copyright 2003-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Martin Hoeller;
 *
 * Changes
 * -------
 * 19-Aug-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added testHashCode() method (DG);
 * 28-Oct-2011 : Added testSetRotationAnchor() method for bug #3428870 (MH);
 * 01-Jul-2013 : Added testChangeEvents() (DG);
 * 
 */

package org.jfree.chart.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;

import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.ui.TextAnchor;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TextAnnotation} class.
 */
public class TextAnnotationTest implements AnnotationChangeListener {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        TextAnnotation a1 = new CategoryTextAnnotation("Test", "Category", 1.0);
        TextAnnotation a2 = new CategoryTextAnnotation("Test", "Category", 1.0);
        assertTrue(a1.equals(a2));

        // text
        a1.setText("Text");
        assertFalse(a1.equals(a2));
        a2.setText("Text");
        assertTrue(a1.equals(a2));

        // font
        a1.setFont(new Font("Serif", Font.BOLD, 18));
        assertFalse(a1.equals(a2));
        a2.setFont(new Font("Serif", Font.BOLD, 18));
        assertTrue(a1.equals(a2));

        // paint
        a1.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.pink));
        assertFalse(a1.equals(a2));
        a2.setPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.pink));
        assertTrue(a1.equals(a2));

        // textAnchor
        a1.setTextAnchor(TextAnchor.BOTTOM_LEFT);
        assertFalse(a1.equals(a2));
        a2.setTextAnchor(TextAnchor.BOTTOM_LEFT);
        assertTrue(a1.equals(a2));

        // rotationAnchor
        a1.setRotationAnchor(TextAnchor.BOTTOM_LEFT);
        assertFalse(a1.equals(a2));
        a2.setRotationAnchor(TextAnchor.BOTTOM_LEFT);
        assertTrue(a1.equals(a2));

        // rotationAngle
        a1.setRotationAngle(Math.PI);
        assertFalse(a1.equals(a2));
        a2.setRotationAngle(Math.PI);
        assertTrue(a1.equals(a2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        TextAnnotation a1 = new CategoryTextAnnotation("Test", "Category", 1.0);
        TextAnnotation a2 = new CategoryTextAnnotation("Test", "Category", 1.0);
        assertTrue(a1.equals(a2));
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Test null-argument (Bug #3428870).
     */
    @Test
    public void testSetRotationAnchor() {
        TextAnnotation a = new CategoryTextAnnotation("Test", "Category", 1.0);
        try {
            a.setRotationAnchor(null);
            fail("Should have thrown Exception.");
        } catch (IllegalArgumentException e) {
            // ok, exception is expected
        }
    }
 
    /**
     * Some tests to ensure that change events are generated as expected.
     */
    @Test
    public void testChangeEvents() {
        TextAnnotation a = new CategoryTextAnnotation("Test", "A", 1.0);
        a.addChangeListener(this);
        this.lastEvent = null;
        a.setText("B");
        assertNotNull(this.lastEvent);
                this.lastEvent = null;
        a.setText("B");
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        a.setFont(new Font("SansSerif", Font.PLAIN, 12));
        assertNotNull(this.lastEvent);

        this.lastEvent = null;
        a.setPaint(Color.BLUE);
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        a.setTextAnchor(TextAnchor.CENTER_LEFT);
        assertNotNull(this.lastEvent);
        
        this.lastEvent = null;
        a.setRotationAnchor(TextAnchor.CENTER_LEFT);
        assertNotNull(this.lastEvent);

        this.lastEvent = null;
        a.setRotationAngle(123.4);
        assertNotNull(this.lastEvent);
   }

    private AnnotationChangeEvent lastEvent;
    
    /**
     * Receives notification of a change to an annotation.
     * 
     * @param event  the event. 
     */
    @Override
    public void annotationChanged(AnnotationChangeEvent event) {
        this.lastEvent = event;  
    }
 
}
