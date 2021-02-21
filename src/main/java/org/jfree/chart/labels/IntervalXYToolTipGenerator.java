/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * IntervalXYToolTipGenerator.java
 * -------------------------------
 * (C) Copyright 2015-2021, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import org.jfree.chart.util.PublicCloneable;

import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * A tooltip generator for datasets that implement the
 * {@link IntervalXYDataset} interface.
 */
public class IntervalXYToolTipGenerator extends AbstractXYItemLabelGenerator
        implements XYToolTipGenerator, Cloneable, PublicCloneable,
                   Serializable {

    /** The default item label format. */
    public static final String DEFAULT_TOOL_TIP_FORMAT 
            = "{0}: ({1} - {2}), ({5} - {6})";

    /**
     * Creates a new tooltip generator using default number formatters.
     */
    public IntervalXYToolTipGenerator() {
        this(DEFAULT_TOOL_TIP_FORMAT, NumberFormat.getNumberInstance(),
            NumberFormat.getNumberInstance());
    }

    /**
     * Creates a new tooltip generator using the specified number formatters.
     *
     * @param formatString  the item label format string ({@code null} not
     *                      permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public IntervalXYToolTipGenerator(String formatString,
            NumberFormat xFormat, NumberFormat yFormat) {
        super(formatString, xFormat, yFormat);
    }

    /**
     * Creates a new tool tip generator using the specified formatters.
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public IntervalXYToolTipGenerator(String formatString,
            DateFormat xFormat, NumberFormat yFormat) {
        super(formatString, xFormat, yFormat);
    }

    /**
     * Creates a new tool tip generator using the specified formatters (a
     * number formatter for the x-values and a date formatter for the
     * y-values).
     *
     * @param formatString  the item label format string ({@code null}
     *                      not permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public IntervalXYToolTipGenerator(String formatString,
            NumberFormat xFormat, DateFormat yFormat) {
        super(formatString, xFormat, yFormat);
    }

    /**
     * Creates a new tool tip generator using the specified date formatters.
     *
     * @param formatString  the label format string ({@code null} not
     *                      permitted).
     * @param xFormat  the format object for the x values ({@code null} not 
     *                 permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public IntervalXYToolTipGenerator(String formatString,
            DateFormat xFormat, DateFormat yFormat) {
        super(formatString, xFormat, yFormat);
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return An array of seven items from the dataset formatted as
     *         {@code String} objects (never {@code null}).
     */
    @Override
    protected Object[] createItemArray(XYDataset dataset, int series, 
            int item) {
        IntervalXYDataset intervalDataset = null;
        if (dataset instanceof IntervalXYDataset) {
            intervalDataset = (IntervalXYDataset) dataset;
        }
        Object[] result = new Object[7];
        result[0] = dataset.getSeriesKey(series).toString();

        double x = dataset.getXValue(series, item);
        double xs = x;
        double xe = x;
        double y = dataset.getYValue(series, item);
        double ys = y;
        double ye = y;
        if (intervalDataset != null) {
            xs = intervalDataset.getStartXValue(series, item);
            xe = intervalDataset.getEndXValue(series, item);
            ys = intervalDataset.getStartYValue(series, item);
            ye = intervalDataset.getEndYValue(series, item);
        }

        DateFormat xdf = getXDateFormat();
        if (xdf != null) {
            result[1] = xdf.format(new Date((long) x));
            result[2] = xdf.format(new Date((long) xs));
            result[3] = xdf.format(new Date((long) xe));
        } else {
            NumberFormat xnf = getXFormat();
            result[1] = xnf.format(x);
            result[2] = xnf.format(xs);
            result[3] = xnf.format(xe);
        }

        NumberFormat ynf = getYFormat();
        DateFormat ydf = getYDateFormat();
        if (Double.isNaN(y) && dataset.getY(series, item) == null) {
            result[4] = getNullYString();
        } else {
            if (ydf != null) {
                result[4] = ydf.format(new Date((long) y));
            }
            else {
                result[4] = ynf.format(y);
            }
        }
        if (Double.isNaN(ys) && intervalDataset != null
                && intervalDataset.getStartY(series, item) == null) {
            result[5] = getNullYString();
        } else {
            if (ydf != null) {
                result[5] = ydf.format(new Date((long) ys));
            }
            else {
                result[5] = ynf.format(ys);
            }
        }
        if (Double.isNaN(ye) && intervalDataset != null
                && intervalDataset.getEndY(series, item) == null) {
            result[6] = getNullYString();
        } else {
            if (ydf != null) {
                result[6] = ydf.format(new Date((long) ye));
            }
            else {
                result[6] = ynf.format(ye);
            }
        }
        return result;
    }

    /**
     * Generates the tool tip text for an item in a dataset.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The tool tip text (possibly {@code null}).
     */
    @Override
    public String generateToolTip(XYDataset dataset, int series, int item) {
        return generateLabelString(dataset, series, item);
    }

    /**
     * Returns an independent copy of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if cloning is not supported.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        if (!(obj instanceof IntervalXYToolTipGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

}
