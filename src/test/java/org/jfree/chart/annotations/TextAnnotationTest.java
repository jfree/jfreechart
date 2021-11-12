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
 * -----------------------
 * TextAnnotationTest.java
 * -----------------------
 * (C) Copyright 2003-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Martin Hoeller;
                     Tracy Hiltbrand;
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
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import static org.jfree.chart.TestUtils.createFont;

import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;
import org.jfree.chart.ui.TextAnchor;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link TextAnnotation} class.
 */
public class TextAnnotationTest implements AnnotationChangeListener {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(TextAnnotation.class)
            .withRedefinedSuperclass()
            .withRedefinedSubclass(CategoryTextAnnotation.class)
            // Add prefab values for Font
            .withPrefabValues(Font.class, createFont(true), createFont(false))
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
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
