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
 * HMSNumberFormatTest.java
 * ------------------------
 * (C) Copyright 2013-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.text.format;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link HMSNumberFormat} class.
 */
public class HMSNumberFormatTest {
    
    @Test
    public void testGeneral() {
        HMSNumberFormat formatter = new HMSNumberFormat();
        assertEquals("00:00:00", formatter.format(0));
        assertEquals("00:00:59", formatter.format(59));
        assertEquals("00:01:01", formatter.format(61));
        assertEquals("00:59:59", formatter.format(3599));
        assertEquals("01:00:00", formatter.format(3600));
        assertEquals("01:00:01", formatter.format(3601));
    }
}
