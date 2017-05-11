/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * -------------------------------
 * BubbleXYItemLabelGenerator.java
 * -------------------------------
 * (C) Copyright 2005-2016, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Dec-2005 : Version 1, based on StandardXYZToolTipGenerator (DG);
 * 26-Jan-2006 : Renamed StandardXYZItemLabelGenerator
 *               --> BubbleXYItemLabelGenerator (DG);
 * 23-Nov-2007 : Implemented hashCode() (DG);
 * 23-Apr-2008 : Implemented PublicCloneable (DG);
 * 03-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;

import org.jfree.chart.HashUtils;
import org.jfree.chart.renderer.xy.XYBubbleRenderer;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

/**
 * An item label generator defined for use with the {@link XYBubbleRenderer}
 * class, or any other class that uses an {@link XYZDataset}.
 *
 * @since 1.0.1
 */
public class BubbleXYItemLabelGenerator extends AbstractXYItemLabelGenerator
        implements XYItemLabelGenerator, PublicCloneable, Serializable {

    /** For serialization. */
    static final long serialVersionUID = -8458568928021240922L;

    /** The default item label format. */
    public static final String DEFAULT_FORMAT_STRING = "{3}";

    /**
     * A number formatter for the z value - if this is {@code null}, then
     * zDateFormat must be non-null.
     */
    private NumberFormat zFormat;

    /**
     * A date formatter for the z-value - if this is null, then zFormat must be
     * non-null.
     */
    private DateFormat zDateFormat;

    /**
     * Creates a new tool tip generator using default number formatters for the
     * x, y and z-values.
     */
    public BubbleXYItemLabelGenerator() {
        this(DEFAULT_FORMAT_STRING, NumberFormat.getNumberInstance(),
                NumberFormat.getNumberInstance(),
                NumberFormat.getNumberInstance());
    }

    /**
     * Constructs a new tool tip generator using the specified number
     * formatters.
     *
     * @param formatString  the format string.
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     * @param zFormat  the format object for the z values ({@code null}
     *                 not permitted).
     */
    public BubbleXYItemLabelGenerator(String formatString,
            NumberFormat xFormat, NumberFormat yFormat, NumberFormat zFormat) {
        super(formatString, xFormat, yFormat);
        Args.nullNotPermitted(zFormat, "zFormat");
        this.zFormat = zFormat;
    }

    /**
     * Constructs a new item label generator using the specified date
     * formatters.
     *
     * @param formatString  the format string.
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     * @param zFormat  the format object for the z values ({@code null}
     *                 not permitted).
     */
    public BubbleXYItemLabelGenerator(String formatString,
            DateFormat xFormat, DateFormat yFormat, DateFormat zFormat) {
        super(formatString, xFormat, yFormat);
        Args.nullNotPermitted(zFormat, "zFormat");
        this.zDateFormat = zFormat;
    }

    /**
     * Returns the number formatter for the z-values.
     *
     * @return The number formatter (possibly {@code null}).
     */
    public NumberFormat getZFormat() {
        return this.zFormat;
    }

    /**
     * Returns the date formatter for the z-values.
     *
     * @return The date formatter (possibly {@code null}).
     */
    public DateFormat getZDateFormat() {
        return this.zDateFormat;
    }

    /**
     * Generates an item label for a particular item within a series.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The item label (possibly {@code null}).
     */
    @Override
    public String generateLabel(XYDataset dataset, int series, int item) {
        return generateLabelString(dataset, series, item);
    }

    /**
     * Generates a label string for an item in the dataset.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The label (possibly {@code null}).
     */
    @Override
    public String generateLabelString(XYDataset dataset, int series, int item) {
        String result;
        Object[] items;
        if (dataset instanceof XYZDataset) {
            items = createItemArray((XYZDataset) dataset, series, item);
        }
        else {
            items = createItemArray(dataset, series, item);
        }
        result = MessageFormat.format(getFormatString(), items);
        return result;
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The items (never {@code null}).
     */
    protected Object[] createItemArray(XYZDataset dataset,
                                       int series, int item) {

        Object[] result = new Object[4];
        result[0] = dataset.getSeriesKey(series).toString();

        Number x = dataset.getX(series, item);
        DateFormat xf = getXDateFormat();
        if (xf != null) {
            result[1] = xf.format(x);
        }
        else {
            result[1] = getXFormat().format(x);
        }

        Number y = dataset.getY(series, item);
        DateFormat yf = getYDateFormat();
        if (yf != null) {
            result[2] = yf.format(y);
        }
        else {
            result[2] = getYFormat().format(y);
        }

        Number z = dataset.getZ(series, item);
        if (this.zDateFormat != null) {
            result[3] = this.zDateFormat.format(z);
        }
        else {
            result[3] = this.zFormat.format(z);
        }

        return result;

    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the other object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BubbleXYItemLabelGenerator)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        BubbleXYItemLabelGenerator that = (BubbleXYItemLabelGenerator) obj;
        if (!ObjectUtils.equal(this.zFormat, that.zFormat)) {
            return false;
        }
        if (!ObjectUtils.equal(this.zDateFormat, that.zDateFormat)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int h = super.hashCode();
        h = HashUtils.hashCode(h, this.zFormat);
        h = HashUtils.hashCode(h, this.zDateFormat);
        return h;
    }

}
