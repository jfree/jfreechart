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
 * ----------------------------
 * PeriodAxisLabelInfoTest.java
 * ----------------------------
 * (C) Copyright 2004-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Jun-2003 : Version 1 (DG);
 * 07-Jan-2005 : Added test for hashCode() (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.chart.TestUtils;
import org.jfree.chart.ui.RectangleInsets;

import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.Year;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link PeriodAxisLabelInfo} class.
 */
public class PeriodAxisLabelInfoTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        PeriodAxisLabelInfo info1 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        PeriodAxisLabelInfo info2 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        assertTrue(info1.equals(info2));
        assertTrue(info2.equals(info1));

        Class c1 = Day.class;
        Class c2 = Month.class;
        DateFormat df1 = new SimpleDateFormat("d");
        DateFormat df2 = new SimpleDateFormat("MMM");
        RectangleInsets sp1 = new RectangleInsets(1, 1, 1, 1);
        RectangleInsets sp2 = new RectangleInsets(2, 2, 2, 2);
        Font lf1 = new Font("SansSerif", Font.PLAIN, 10);
        Font lf2 = new Font("SansSerif", Font.BOLD, 9);
        Paint lp1 = Color.BLACK;
        Paint lp2 = Color.BLUE;
        boolean b1 = true;
        boolean b2 = false;
        Stroke s1 = new BasicStroke(0.5f);
        Stroke s2 = new BasicStroke(0.25f);
        Paint dp1 = Color.RED;
        Paint dp2 = Color.GREEN;

        info1 = new PeriodAxisLabelInfo(c2, df1, sp1, lf1, lp1, b1, s1, dp1);
        info2 = new PeriodAxisLabelInfo(c1, df1, sp1, lf1, lp1, b1, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df1, sp1, lf1, lp1, b1, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp1, lf1, lp1, b1, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp1, lf1, lp1, b1, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf1, lp1, b1, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf1, lp1, b1, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp1, b1, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp1, b1, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b1, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b1, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s1, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s1, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s2, dp1);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s2, dp1);
        assertTrue(info1.equals(info2));

        info1 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s2, dp2);
        assertFalse(info1.equals(info2));
        info2 = new PeriodAxisLabelInfo(c2, df2, sp2, lf2, lp2, b2, s2, dp2);
        assertTrue(info1.equals(info2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        PeriodAxisLabelInfo info1 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        PeriodAxisLabelInfo info2 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        assertTrue(info1.equals(info2));
        int h1 = info1.hashCode();
        int h2 = info2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PeriodAxisLabelInfo info1 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        PeriodAxisLabelInfo info2 = (PeriodAxisLabelInfo) info1.clone();
        assertTrue(info1 != info2);
        assertTrue(info1.getClass() == info2.getClass());
        assertTrue(info1.equals(info2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PeriodAxisLabelInfo info1 = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        PeriodAxisLabelInfo info2 = (PeriodAxisLabelInfo) TestUtils.serialised(info1);
        assertEquals(info1, info2);
    }

    /**
     * A test for the createInstance() method.
     */
    @Test
    public void testCreateInstance() {
        TimeZone zone = TimeZone.getTimeZone("GMT");
        PeriodAxisLabelInfo info = new PeriodAxisLabelInfo(Day.class,
                new SimpleDateFormat("d"));
        Day d = (Day) info.createInstance(new Date(0L), zone, Locale.UK);
        assertEquals(new Day(1, 1, 1970), d);

        info = new PeriodAxisLabelInfo(Year.class, new SimpleDateFormat("YYYY"));
        Year y = (Year) info.createInstance(new Date(0L), zone, Locale.UK);
        assertEquals(new Year(1970), y);
    }

}
