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
 * ------------------------
 * MonthDateFormatTest.java
 * ------------------------
 * (C) Copyright 2005-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-May-2005 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Some tests for the {@link MonthDateFormat} class.
 */
public class MonthDateFormatTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = new MonthDateFormat();
        assertTrue(mf1.equals(mf2));
        assertTrue(mf2.equals(mf1));

        boolean[] showYear1 = new boolean [12];
        showYear1[0] = true;
        boolean[] showYear2 = new boolean [12];
        showYear1[1] = true;

        // time zone
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.US, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertFalse(mf1.equals(mf2));
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.US, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertTrue(mf1.equals(mf2));

        // locale
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertFalse(mf1.equals(mf2));
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertTrue(mf1.equals(mf2));

        // chars
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear1, new SimpleDateFormat("yy"));
        assertFalse(mf1.equals(mf2));
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear1, new SimpleDateFormat("yy"));
        assertTrue(mf1.equals(mf2));

        // showYear[]
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yy"));
        assertFalse(mf1.equals(mf2));
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yy"));
        assertTrue(mf1.equals(mf2));

        // yearFormatter
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yyyy"));
        assertFalse(mf1.equals(mf2));
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yyyy"));
        assertTrue(mf1.equals(mf2));

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = new MonthDateFormat();
        assertTrue(mf1.equals(mf2));
        int h1 = mf1.hashCode();
        int h2 = mf2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = null;
        mf2 = (MonthDateFormat) mf1.clone();
        assertTrue(mf1 != mf2);
        assertTrue(mf1.getClass() == mf2.getClass());
        assertTrue(mf1.equals(mf2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = (MonthDateFormat) TestUtilities.serialised(mf1);
        assertTrue(mf1.equals(mf2));
    }

}
