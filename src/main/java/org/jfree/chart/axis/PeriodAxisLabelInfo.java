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
 * PeriodAxisLabelInfo.java
 * ------------------------
 * (C) Copyright 2004-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.SerialUtils;

import org.jfree.data.time.RegularTimePeriod;

/**
 * A record that contains information for one "band" of date labels in
 * a {@link PeriodAxis}.
 */
public class PeriodAxisLabelInfo implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 5710451740920277357L;

    /** The default insets. */
    public static final RectangleInsets DEFAULT_INSETS
            = new RectangleInsets(2, 2, 2, 2);

    /** The default font. */
    public static final Font DEFAULT_FONT
            = new Font("SansSerif", Font.PLAIN, 10);

    /** The default label paint. */
    public static final Paint DEFAULT_LABEL_PAINT = Color.BLACK;

    /** The default divider stroke. */
    public static final Stroke DEFAULT_DIVIDER_STROKE = new BasicStroke(0.5f);

    /** The default divider paint. */
    public static final Paint DEFAULT_DIVIDER_PAINT = Color.GRAY;

    /** The subclass of {@link RegularTimePeriod} to use for this band. */
    private Class periodClass;

    /** Controls the gaps around the band. */
    private RectangleInsets padding;

    /** The date formatter. */
    private DateFormat dateFormat;

    /** The label font. */
    private Font labelFont;

    /** The label paint. */
    private transient Paint labelPaint;

    /** A flag that controls whether or not dividers are visible. */
    private boolean drawDividers;

    /** The stroke used to draw the dividers. */
    private transient Stroke dividerStroke;

    /** The paint used to draw the dividers. */
    private transient Paint dividerPaint;

    /**
     * Creates a new instance.
     *
     * @param periodClass  the subclass of {@link RegularTimePeriod} to use
     *                     ({@code null} not permitted).
     * @param dateFormat  the date format ({@code null} not permitted).
     */
    public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat) {
        this(periodClass, dateFormat, DEFAULT_INSETS, DEFAULT_FONT,
            DEFAULT_LABEL_PAINT, true, DEFAULT_DIVIDER_STROKE,
            DEFAULT_DIVIDER_PAINT);
    }

    /**
     * Creates a new instance.
     *
     * @param periodClass  the subclass of {@link RegularTimePeriod} to use
     *                     ({@code null} not permitted).
     * @param dateFormat  the date format ({@code null} not permitted).
     * @param padding  controls the space around the band ({@code null}
     *                 not permitted).
     * @param labelFont  the label font ({@code null} not permitted).
     * @param labelPaint  the label paint ({@code null} not permitted).
     * @param drawDividers  a flag that controls whether dividers are drawn.
     * @param dividerStroke  the stroke used to draw the dividers
     *                       ({@code null} not permitted).
     * @param dividerPaint  the paint used to draw the dividers
     *                      ({@code null} not permitted).
     */
    public PeriodAxisLabelInfo(Class periodClass, DateFormat dateFormat,
            RectangleInsets padding, Font labelFont, Paint labelPaint,
            boolean drawDividers, Stroke dividerStroke, Paint dividerPaint) {
        Args.nullNotPermitted(periodClass, "periodClass");
        Args.nullNotPermitted(dateFormat, "dateFormat");
        Args.nullNotPermitted(padding, "padding");
        Args.nullNotPermitted(labelFont, "labelFont");
        Args.nullNotPermitted(labelPaint, "labelPaint");
        Args.nullNotPermitted(dividerStroke, "dividerStroke");
        Args.nullNotPermitted(dividerPaint, "dividerPaint");
        this.periodClass = periodClass;
        this.dateFormat = (DateFormat) dateFormat.clone();
        this.padding = padding;
        this.labelFont = labelFont;
        this.labelPaint = labelPaint;
        this.drawDividers = drawDividers;
        this.dividerStroke = dividerStroke;
        this.dividerPaint = dividerPaint;
    }

    /**
     * Returns the subclass of {@link RegularTimePeriod} that should be used
     * to generate the date labels.
     *
     * @return The class.
     */
    public Class getPeriodClass() {
        return this.periodClass;
    }

    /**
     * Returns a copy of the date formatter.
     *
     * @return A copy of the date formatter (never {@code null}).
     */
    public DateFormat getDateFormat() {
        return (DateFormat) this.dateFormat.clone();
    }

    /**
     * Returns the padding for the band.
     *
     * @return The padding.
     */
    public RectangleInsets getPadding() {
        return this.padding;
    }

    /**
     * Returns the label font.
     *
     * @return The label font (never {@code null}).
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Returns the label paint.
     *
     * @return The label paint.
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Returns a flag that controls whether or not dividers are drawn.
     *
     * @return A flag.
     */
    public boolean getDrawDividers() {
        return this.drawDividers;
    }

    /**
     * Returns the stroke used to draw the dividers.
     *
     * @return The stroke.
     */
    public Stroke getDividerStroke() {
        return this.dividerStroke;
    }

    /**
     * Returns the paint used to draw the dividers.
     *
     * @return The paint.
     */
    public Paint getDividerPaint() {
        return this.dividerPaint;
    }

    /**
     * Creates a time period that includes the specified millisecond, assuming
     * the given time zone.
     *
     * @param millisecond  the time.
     * @param zone  the time zone.
     * @param locale  the locale.
     *
     * @return The time period.
     */
    public RegularTimePeriod createInstance(Date millisecond, TimeZone zone,
            Locale locale) {
        RegularTimePeriod result = null;
        try {
            Constructor c = this.periodClass.getDeclaredConstructor(
                    new Class[] {Date.class, TimeZone.class, Locale.class});
            result = (RegularTimePeriod) c.newInstance(new Object[] {
                    millisecond, zone, locale});
        }
        catch (Exception e) {
            // do nothing
        }
        return result;
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof PeriodAxisLabelInfo) {
            PeriodAxisLabelInfo info = (PeriodAxisLabelInfo) obj;
            if (!info.periodClass.equals(this.periodClass)) {
                return false;
            }
            if (!info.dateFormat.equals(this.dateFormat)) {
                return false;
            }
            if (!info.padding.equals(this.padding)) {
                return false;
            }
            if (!info.labelFont.equals(this.labelFont)) {
                return false;
            }
            if (!info.labelPaint.equals(this.labelPaint)) {
                return false;
            }
            if (info.drawDividers != this.drawDividers) {
                return false;
            }
            if (!info.dividerStroke.equals(this.dividerStroke)) {
                return false;
            }
            if (!info.dividerPaint.equals(this.dividerPaint)) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a hash code for this object.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 41;
        result = result + 37 * this.periodClass.hashCode();
        result = result + 37 * this.dateFormat.hashCode();
        return result;
    }

    /**
     * Returns a clone of the object.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        PeriodAxisLabelInfo clone = (PeriodAxisLabelInfo) super.clone();
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.labelPaint, stream);
        SerialUtils.writeStroke(this.dividerStroke, stream);
        SerialUtils.writePaint(this.dividerPaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.labelPaint = SerialUtils.readPaint(stream);
        this.dividerStroke = SerialUtils.readStroke(stream);
        this.dividerPaint = SerialUtils.readPaint(stream);
    }

}
