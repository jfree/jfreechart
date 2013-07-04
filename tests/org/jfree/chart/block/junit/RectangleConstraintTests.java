/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * -----------------------------
 * RectangleConstraintTests.java
 * -----------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Oct-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.block.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.data.Range;
import org.jfree.ui.Size2D;

/**
 * Tests for the {@link RectangleConstraint} class.
 */
public class RectangleConstraintTests extends TestCase {

    private static final double EPSILON = 0.0000000001;

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RectangleConstraintTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RectangleConstraintTests(String name) {
        super(name);
    }

    /**
     * Run some checks on the constrained size calculation.
     */
    public void testCalculateConstrainedSize() {
        Size2D s;

        // NONE / NONE
        RectangleConstraint c1 = RectangleConstraint.NONE;
        s = c1.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 3.4, EPSILON);

        // NONE / RANGE
        RectangleConstraint c2 = new RectangleConstraint(
            0.0, new Range(0.0, 0.0), LengthConstraintType.NONE,
            0.0, new Range(2.0, 3.0), LengthConstraintType.RANGE
        );
        s = c2.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 3.0, EPSILON);

        // NONE / FIXED
        RectangleConstraint c3 = new RectangleConstraint(
            0.0, null, LengthConstraintType.NONE,
            9.9, null, LengthConstraintType.FIXED
        );
        s = c3.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 9.9, EPSILON);

        // RANGE / NONE
        RectangleConstraint c4 = new RectangleConstraint(
            0.0, new Range(2.0, 3.0), LengthConstraintType.RANGE,
            0.0, new Range(0.0, 0.0), LengthConstraintType.NONE
        );
        s = c4.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 2.0, EPSILON);
        assertEquals(s.height, 3.4, EPSILON);

        // RANGE / RANGE
        RectangleConstraint c5 = new RectangleConstraint(
            0.0, new Range(2.0, 3.0), LengthConstraintType.RANGE,
            0.0, new Range(2.0, 3.0), LengthConstraintType.RANGE
        );
        s = c5.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 2.0, EPSILON);
        assertEquals(s.height, 3.0, EPSILON);

        // RANGE / FIXED
        RectangleConstraint c6 = new RectangleConstraint(
            0.0, null, LengthConstraintType.NONE,
            9.9, null, LengthConstraintType.FIXED
        );
        s = c6.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 9.9, EPSILON);

        // FIXED / NONE
        RectangleConstraint c7 = RectangleConstraint.NONE;
        s = c7.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 3.4, EPSILON);

        // FIXED / RANGE
        RectangleConstraint c8 = new RectangleConstraint(
            0.0, new Range(0.0, 0.0), LengthConstraintType.NONE,
            0.0, new Range(2.0, 3.0), LengthConstraintType.RANGE
        );
        s = c8.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 3.0, EPSILON);

        // FIXED / FIXED
        RectangleConstraint c9 = new RectangleConstraint(
            0.0, null, LengthConstraintType.NONE,
            9.9, null, LengthConstraintType.FIXED
        );
        s = c9.calculateConstrainedSize(new Size2D(1.2, 3.4));
        assertEquals(s.width, 1.2, EPSILON);
        assertEquals(s.height, 9.9, EPSILON);

    }
}
