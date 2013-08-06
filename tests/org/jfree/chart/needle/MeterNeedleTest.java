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
 * --------------------
 * MeterNeedleTest.java
 * --------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 08-Jun-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.needle;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Stroke;
import org.junit.Test;


/**
 * Tests for the {@link MeterNeedle} class.
 */
public class MeterNeedleTest {

    /**
     * Check that the equals() method can distinguish all fields.
     */
    @Test
    public void testEquals() {
        MeterNeedle n1 = new LineNeedle();
        MeterNeedle n2 = new LineNeedle();
        assertTrue(n1.equals(n2));

        n1.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.blue));
        assertFalse(n1.equals(n2));
        n2.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f, Color.blue));
        assertTrue(n1.equals(n2));

        n1.setOutlinePaint(new GradientPaint(5.0f, 6.0f, Color.red, 7.0f, 8.0f, Color.blue));
        assertFalse(n1.equals(n2));
        n2.setOutlinePaint(new GradientPaint(5.0f, 6.0f, Color.red, 7.0f, 8.0f, Color.blue));
        assertTrue(n1.equals(n2));

        n1.setHighlightPaint(new GradientPaint(9.0f, 0.0f, Color.red, 1.0f, 2.0f, Color.blue));
        assertFalse(n1.equals(n2));
        n2.setHighlightPaint(new GradientPaint(9.0f, 0.0f, Color.red, 1.0f, 2.0f, Color.blue));
        assertTrue(n1.equals(n2));

        Stroke s = new BasicStroke(1.23f);
        n1.setOutlineStroke(s);
        assertFalse(n1.equals(n2));
        n2.setOutlineStroke(s);
        assertTrue(n1.equals(n2));

        n1.setRotateX(1.23);
        assertFalse(n1.equals(n2));
        n2.setRotateX(1.23);
        assertTrue(n1.equals(n2));

        n1.setRotateY(4.56);
        assertFalse(n1.equals(n2));
        n2.setRotateY(4.56);
        assertTrue(n1.equals(n2));

        n1.setSize(11);
        assertFalse(n1.equals(n2));
        n2.setSize(11);
        assertTrue(n1.equals(n2));
    }

}
