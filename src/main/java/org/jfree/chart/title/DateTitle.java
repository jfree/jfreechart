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
 * --------------
 * DateTitle.java
 * --------------
 * (C) Copyright 2000-2021, by David Berry and Contributors.
 *
 * Original Author:  David Berry;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.jfree.chart.api.HorizontalAlignment;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.VerticalAlignment;

/**
 * A chart title that displays the date.
 * <p>
 * Keep in mind that a chart can have several titles, and that they can appear
 * at the top, left, right or bottom of the chart - a {@code DateTitle}
 * will commonly appear at the bottom of a chart, although you can place it
 * anywhere.
 * <P>
 * By specifying the locale, dates are formatted to the correct standard for
 * the given locale. For example, a date would appear as "January 17, 2000" in
 * the US, but "17 January 2000" in most European locales.
 */
public class DateTitle extends TextTitle implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -465434812763159881L;

    /**
     * Creates a new chart title that displays the current date in the default
     * (LONG) format for the locale, positioned to the bottom right of the
     * chart.
     * <P>
     * The color will be black in 12 point, plain Helvetica font (maps to Arial
     * on Win32 systems without Helvetica).
     */
    public DateTitle() {
        this(DateFormat.LONG);
    }

    /**
     * Creates a new chart title that displays the current date with the
     * specified style (for the default locale).
     * <P>
     * The date style should be one of:  {@code SHORT},
     * {@code MEDIUM}, {@code LONG} or {@code FULL}
     * (defined in {@code java.util.DateFormat}).
     *
     * @param style  the date style.
     */
    public DateTitle(int style) {
        this(style, Locale.getDefault(), new Font("Dialog", Font.PLAIN, 12),
                Color.BLACK);
    }

    /**
     * Creates a new chart title that displays the current date.
     * <p>
     * The date style should be one of:  {@code SHORT},
     * {@code MEDIUM}, {@code LONG} or {@code FULL} (defined
     * in {@code java.util.DateFormat}).
     * <P>
     * For the locale, you can use {@code Locale.getDefault()} for the
     * default locale.
     *
     * @param style  the date style.
     * @param locale  the locale.
     * @param font  the font.
     * @param paint  the text color.
     */
    public DateTitle(int style, Locale locale, Font font, Paint paint) {
        this(style, locale, font, paint, RectangleEdge.BOTTOM,
                HorizontalAlignment.RIGHT, VerticalAlignment.CENTER,
                Title.DEFAULT_PADDING);
    }

    /**
     * Creates a new chart title that displays the current date.
     * <p>
     * The date style should be one of:  {@code SHORT},
     * {@code MEDIUM}, {@code LONG} or {@code FULL} (defined
     * in {@code java.util.DateFormat}).
     * <P>
     * For the locale, you can use {@code Locale.getDefault()} for the
     * default locale.
     *
     * @param style  the date style.
     * @param locale  the locale.
     * @param font  the font (not null).
     * @param paint  the text color (not null).
     * @param position  the relative location of this title (use constants in
     *                  Title).
     * @param horizontalAlignment  the horizontal text alignment of this title
     *                             (use constants in Title).
     * @param verticalAlignment  the vertical text alignment of this title (use
     *                           constants in Title).
     * @param padding  determines the blank space around the outside of the
     *                 title (not null).
     */
    public DateTitle(int style, Locale locale, Font font, Paint paint,
            RectangleEdge position, HorizontalAlignment horizontalAlignment,
            VerticalAlignment verticalAlignment, RectangleInsets padding) {
        super(DateFormat.getDateInstance(style, locale).format(new Date()),
                font, paint, position, horizontalAlignment, verticalAlignment,
                padding);
    }

    /**
     * Set the format of the date.
     * <P>
     * The date style should be one of:  {@code SHORT},
     * {@code MEDIUM}, {@code LONG} or {@code FULL} (defined
     * in {@code java.util.DateFormat}).
     * <P>
     * For the locale, you can use {@code Locale.getDefault()} for the
     * default locale.
     *
     * @param style  the date style.
     * @param locale  the locale.
     */
    public void setDateFormat(int style, Locale locale) {
        setText(DateFormat.getDateInstance(style, locale).format(new Date()));
    }

}
