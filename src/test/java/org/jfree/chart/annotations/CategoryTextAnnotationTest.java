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
 * -------------------------------
 * CategoryTextAnnotationTest.java
 * -------------------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.annotations;

import java.awt.Font;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import static org.jfree.chart.TestUtils.createFont;
import static org.junit.jupiter.api.Assertions.*;

import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.util.PublicCloneable;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

/**
 * Tests for the {@link CategoryTextAnnotation} class.
 */
public class CategoryTextAnnotationTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(CategoryTextAnnotation.class)
                .withRedefinedSuperclass() // superclass also defines equals/hashCode
                // Add prefab values for Font
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
        CategoryTextAnnotation a1 = new CategoryTextAnnotation("Test",
                "Category", 1.0);
        CategoryTextAnnotation a2 = new CategoryTextAnnotation("Test",
                "Category", 1.0);
        assertEquals(a1, a2);
    
        // category
        a1.setCategory("Category 2");
        assertNotEquals(a1, a2);
        a2.setCategory("Category 2");
        assertEquals(a1, a2);

        // categoryAnchor
        a1.setCategoryAnchor(CategoryAnchor.START);
        assertNotEquals(a1, a2);
        a2.setCategoryAnchor(CategoryAnchor.START);
        assertEquals(a1, a2);

        // value
        a1.setValue(0.15);
        assertNotEquals(a1, a2);
        a2.setValue(0.15);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        CategoryTextAnnotation a1 = new CategoryTextAnnotation("Test", 
                "Category", 1.0);
        CategoryTextAnnotation a2 = new CategoryTextAnnotation("Test", 
                "Category", 1.0);
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException if there is a cloning issue.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CategoryTextAnnotation a1 = new CategoryTextAnnotation(
                "Test", "Category", 1.0);
        CategoryTextAnnotation a2 = (CategoryTextAnnotation) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        CategoryTextAnnotation a1 = new CategoryTextAnnotation(
                "Test", "Category", 1.0);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CategoryTextAnnotation a1 = new CategoryTextAnnotation("Test", 
                "Category", 1.0);
        CategoryTextAnnotation a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

}
