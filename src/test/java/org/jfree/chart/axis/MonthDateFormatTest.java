/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(mf1, mf2);
        assertEquals(mf2, mf1);

        boolean[] showYear1 = new boolean [12];
        showYear1[0] = true;
        boolean[] showYear2 = new boolean [12];
        showYear1[1] = true;

        // time zone
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.US, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertNotEquals(mf1, mf2);
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.US, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertEquals(mf1, mf2);

        // locale
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertNotEquals(mf1, mf2);
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 1,
            showYear1, new SimpleDateFormat("yy"));
        assertEquals(mf1, mf2);

        // chars
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear1, new SimpleDateFormat("yy"));
        assertNotEquals(mf1, mf2);
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear1, new SimpleDateFormat("yy"));
        assertEquals(mf1, mf2);

        // showYear[]
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yy"));
        assertNotEquals(mf1, mf2);
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yy"));
        assertEquals(mf1, mf2);

        // yearFormatter
        mf1 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yyyy"));
        assertNotEquals(mf1, mf2);
        mf2 = new MonthDateFormat(TimeZone.getTimeZone("PST"), Locale.FRANCE, 2,
            showYear2, new SimpleDateFormat("yyyy"));
        assertEquals(mf1, mf2);

    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = new MonthDateFormat();
        assertEquals(mf1, mf2);
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
        assertNotSame(mf1, mf2);
        assertSame(mf1.getClass(), mf2.getClass());
        assertEquals(mf1, mf2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        MonthDateFormat mf1 = new MonthDateFormat();
        MonthDateFormat mf2 = TestUtils.serialised(mf1);
        assertEquals(mf1, mf2);
    }

}
