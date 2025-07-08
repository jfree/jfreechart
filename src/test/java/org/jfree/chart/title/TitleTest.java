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
 * --------------
 * TitleTest.java
 * --------------
 * (C) Copyright 2004-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.title;

import java.awt.geom.Rectangle2D;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.VerticalAlignment;

import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the abstract {@link Title} class.
 */
public class TitleTest {
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(Title.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withRedefinedSuperclass()
                .withRedefinedSubclass(CompositeTitle.class)
                .withRedefinedSubclass(DateTitle.class)
                .withRedefinedSubclass(ShortTextTitle.class)
                .withRedefinedSubclass(TextTitle.class)
                .withRedefinedSubclass(LegendTitle.class)
                .withRedefinedSubclass(PaintScaleLegend.class)
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withPrefabValues(Rectangle2D.class,
                                  TestUtils.createR2D(true),
                                  TestUtils.createR2D(false))
                .verify();
    }
	
    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        // use the TextTitle class because it is a concrete subclass
        Title t1 = new TextTitle();
        Title t2 = new TextTitle();
        assertEquals(t1, t2);

        t1.setPosition(RectangleEdge.LEFT);
        assertNotEquals(t1, t2);
        t2.setPosition(RectangleEdge.LEFT);
        assertEquals(t1, t2);

        t1.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        assertNotEquals(t1, t2);
        t2.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        assertEquals(t1, t2);

        t1.setVerticalAlignment(VerticalAlignment.BOTTOM);
        assertNotEquals(t1, t2);
        t2.setVerticalAlignment(VerticalAlignment.BOTTOM);
        assertEquals(t1, t2);

        t1.setVisible(false);
        assertNotEquals(t1, t2);
        t2.setVisible(false);
        assertEquals(t1, t2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        TextTitle t1 = new TextTitle();
        TextTitle t2 = new TextTitle();
        assertEquals(t1, t2);
        int h1 = t1.hashCode();
        int h2 = t2.hashCode();
        assertEquals(h1, h2);
    }

}
