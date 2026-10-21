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
 */

package org.jfree.chart.util;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Regression tests for the logical contents and size of a shape list. */
public class ShapeListTest {

    @Test
    public void testEmptyAndNonEmpty() {
        ShapeList empty = new ShapeList();
        ShapeList one = new ShapeList();
        one.setShape(0, new Rectangle2D.Double());
        assertAll(() -> assertFalse(empty.equals(one)),
                () -> assertFalse(one.equals(empty)));
    }

    @Test
    public void testMatchingPrefixDifferentLengths() {
        ShapeList shorter = new ShapeList();
        shorter.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        ShapeList longer = new ShapeList();
        longer.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        longer.setShape(1, new Line2D.Double(0, 0, 1, 1));
        assertAll(() -> assertFalse(shorter.equals(longer)),
                () -> assertFalse(longer.equals(shorter)));
    }

    @Test
    public void testTrailingNullStillChangesSize() {
        ShapeList shorter = new ShapeList();
        shorter.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        ShapeList longer = new ShapeList();
        longer.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        longer.setShape(1, null);
        assertAll(() -> assertFalse(shorter.equals(longer)),
                () -> assertFalse(longer.equals(shorter)));
    }

    @Test
    public void testAllNullListsWithDifferentSizes() {
        ShapeList empty = new ShapeList();
        ShapeList one = new ShapeList();
        one.setShape(0, null);
        ShapeList sparse = new ShapeList();
        sparse.setShape(10, null);
        assertAll(() -> assertFalse(empty.equals(one)),
                () -> assertFalse(one.equals(empty)),
                () -> assertFalse(one.equals(sparse)),
                () -> assertFalse(sparse.equals(one)));
    }

    @Test
    public void testEqualSparseLists() {
        ShapeList first = new ShapeList();
        first.setShape(10, new Line2D.Double(1, 2, 3, 4));
        ShapeList second = new ShapeList();
        second.setShape(10, new Line2D.Double(1, 2, 3, 4));
        assertAll(() -> assertTrue(first.equals(second)),
                () -> assertTrue(second.equals(first)));
    }

    @Test
    public void testSameSizeDifferentShapes() {
        ShapeList first = new ShapeList();
        first.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        ShapeList second = new ShapeList();
        second.setShape(0, new Rectangle2D.Double(1, 2, 3, 5));
        assertAll(() -> assertFalse(first.equals(second)),
                () -> assertFalse(second.equals(first)));
    }

    @Test
    public void testShapeOrderMatters() {
        ShapeList first = new ShapeList();
        first.setShape(0, new Rectangle2D.Double(1, 2, 3, 4));
        first.setShape(1, new Line2D.Double(0, 0, 1, 1));
        ShapeList second = new ShapeList();
        second.setShape(0, new Line2D.Double(0, 0, 1, 1));
        second.setShape(1, new Rectangle2D.Double(1, 2, 3, 4));
        assertAll(() -> assertFalse(first.equals(second)),
                () -> assertFalse(second.equals(first)));
    }

    @Test
    public void testReflexivityNullAndOtherTypes() {
        ShapeList list = new ShapeList();
        list.setShape(0, null);
        assertAll(() -> assertTrue(list.equals(list)),
                () -> assertFalse(list.equals(null)),
                () -> assertFalse(list.equals(new ObjectList())));
    }

    @Test
    public void testClearedListEqualsNewEmptyList() {
        ShapeList cleared = new ShapeList();
        cleared.setShape(20, new Rectangle2D.Double());
        cleared.clear();
        ShapeList empty = new ShapeList();
        assertAll(() -> assertTrue(cleared.equals(empty)),
                () -> assertTrue(empty.equals(cleared)));
    }
}
