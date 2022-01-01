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
 * ------------------------------------
 * BoxAndWhiskerXYToolTipGenerator.java
 * ------------------------------------
 * (C) Copyright 2003-2020, by David Browning and Contributors.
 *
 * Original Author:  David Browning;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;

import org.jfree.data.statistics.BoxAndWhiskerXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 * An item label generator for plots that use data from a
 * {@link BoxAndWhiskerXYDataset}.
 * <P>
 * The tooltip text and item label text are composed using a
 * {@link java.text.MessageFormat} object, that can aggregate some or all of
 * the following string values into a message.
 * <ul>
 * <li>0 : Series Name</li>
 * <li>1 : X (value or date)</li>
 * <li>2 : Mean</li>
 * <li>3 : Median</li>
 * <li>4 : Minimum</li>
 * <li>5 : Maximum</li>
 * <li>6 : Quartile 1</li>
 * <li>7 : Quartile 3</li>
 * </ul>
 */
public class BoxAndWhiskerXYToolTipGenerator extends StandardXYToolTipGenerator
        implements XYToolTipGenerator, Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2648775791161459710L;

    /** The default tooltip format string. */
    public static final String DEFAULT_TOOL_TIP_FORMAT
            = "X: {1} Mean: {2} Median: {3} Min: {4} Max: {5} Q1: {6} Q3: {7} ";

    /**
     * Creates a default item label generator.
     */
    public BoxAndWhiskerXYToolTipGenerator() {
        super(DEFAULT_TOOL_TIP_FORMAT, NumberFormat.getInstance(),
                NumberFormat.getInstance());
    }

    /**
     * Creates a new item label generator.  If the date formatter is not
     * {@code null}, the x-values will be formatted as dates.
     *
     * @param toolTipFormat  the tool tip format string ({@code null} not
     *                       permitted).
     * @param numberFormat  the number formatter ({@code null} not
     *                      permitted).
     * @param dateFormat  the date formatter ({@code null} permitted).
     */
    public BoxAndWhiskerXYToolTipGenerator(String toolTipFormat,
                                           DateFormat dateFormat,
                                           NumberFormat numberFormat) {

        super(toolTipFormat, dateFormat, numberFormat);

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
    @Override
    protected Object[] createItemArray(XYDataset dataset, int series,
                                       int item) {
        Object[] result = new Object[8];
        result[0] = dataset.getSeriesKey(series).toString();
        Number x = dataset.getX(series, item);
        if (getXDateFormat() != null) {
            result[1] = getXDateFormat().format(new Date(x.longValue()));
        }
        else {
            result[1] = getXFormat().format(x);
        }
        NumberFormat formatter = getYFormat();

        if (dataset instanceof BoxAndWhiskerXYDataset) {
            BoxAndWhiskerXYDataset d = (BoxAndWhiskerXYDataset) dataset;
            result[2] = formatter.format(d.getMeanValue(series, item));
            result[3] = formatter.format(d.getMedianValue(series, item));
            result[4] = formatter.format(d.getMinRegularValue(series, item));
            result[5] = formatter.format(d.getMaxRegularValue(series, item));
            result[6] = formatter.format(d.getQ1Value(series, item));
            result[7] = formatter.format(d.getQ3Value(series, item));
        }
        return result;
    }

    /**
     * Tests if this object is equal to another.
     *
     * @param obj  the other object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BoxAndWhiskerXYToolTipGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

}
