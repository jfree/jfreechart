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
 * ---------------------
 * DateTickUnitType.java
 * ---------------------
 * (C) Copyright 2009-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.util.Calendar;

/**
 * An enumeration of the unit types for a {@link DateTickUnit} instance.
 */
public enum DateTickUnitType {

    /** Year. */
    YEAR(Calendar.YEAR),

    /** Month. */
    MONTH( Calendar.MONTH),

    /** Day. */
    DAY( Calendar.DATE),

    /** Hour. */
    HOUR(Calendar.HOUR_OF_DAY),

    /** Minute. */
    MINUTE(Calendar.MINUTE),

    /** Second. */
    SECOND(Calendar.SECOND),

    /** Millisecond. */
    MILLISECOND(Calendar.MILLISECOND);

    /** The corresponding field value in Java's Calendar class. */
    private int calendarField;

    /**
     * Private constructor.
     *
     * @param calendarField  the calendar field.
     */
    DateTickUnitType(int calendarField) {
        this.calendarField = calendarField;
    }

    /**
     * Returns the calendar field.
     *
     * @return The calendar field.
     */
    public int getCalendarField() {
        return this.calendarField;
    }

}
