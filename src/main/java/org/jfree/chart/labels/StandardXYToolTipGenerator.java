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
 * -------------------------------
 * StandardXYToolTipGenerator.java
 * -------------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.xy.XYDataset;

/**
 * A standard tool tip generator for use with an
 * {@link org.jfree.chart.renderer.xy.XYItemRenderer}.
 */
public class StandardXYToolTipGenerator extends AbstractXYItemLabelGenerator
        implements XYToolTipGenerator, Cloneable, PublicCloneable,
                   Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -3564164459039540784L;

    /** The default tooltip format. */
    public static final String DEFAULT_TOOL_TIP_FORMAT = "{0}: ({1}, {2})";

    /**
     * Returns a tool tip generator that formats the x-values as dates and the
     * y-values as numbers.
     *
     * @return A tool tip generator (never {@code null}).
     */
    public static StandardXYToolTipGenerator getTimeSeriesInstance() {
        return new StandardXYToolTipGenerator(DEFAULT_TOOL_TIP_FORMAT,
                DateFormat.getInstance(), NumberFormat.getInstance());
    }

    /**
     * Creates a tool tip generator using default number formatters.
     */
    public StandardXYToolTipGenerator() {
        this(DEFAULT_TOOL_TIP_FORMAT, NumberFormat.getNumberInstance(),
                NumberFormat.getNumberInstance());
    }

    /**
     * Creates a tool tip generator using the specified number formatters.
     *
     * @param formatString  the item label format string ({@code null} not
     *                      permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public StandardXYToolTipGenerator(String formatString,
            NumberFormat xFormat, NumberFormat yFormat) {

        super(formatString, xFormat, yFormat);

    }

    /**
     * Creates a tool tip generator using the specified number formatters.
     *
     * @param formatString  the label format string ({@code null} not
     *                      permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public StandardXYToolTipGenerator(String formatString, DateFormat xFormat,
            NumberFormat yFormat) {

        super(formatString, xFormat, yFormat);

    }

    /**
     * Creates a tool tip generator using the specified formatters (a
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
    public StandardXYToolTipGenerator(String formatString,
            NumberFormat xFormat, DateFormat yFormat) {

        super(formatString, xFormat, yFormat);
    }
    /**
     * Creates a tool tip generator using the specified date formatters.
     *
     * @param formatString  the label format string ({@code null} not
     *                      permitted).
     * @param xFormat  the format object for the x values ({@code null}
     *                 not permitted).
     * @param yFormat  the format object for the y values ({@code null}
     *                 not permitted).
     */
    public StandardXYToolTipGenerator(String formatString,
            DateFormat xFormat, DateFormat yFormat) {

        super(formatString, xFormat, yFormat);

    }

    /**
     * Generates the tool tip text for an item in a dataset.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     *
     * @return The tooltip text (possibly {@code null}).
     */
    @Override
    public String generateToolTip(XYDataset dataset, int series, int item) {
        return generateLabelString(dataset, series, item);
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
        if (!(obj instanceof StandardXYToolTipGenerator)) {
            return false;
        }
        return super.equals(obj);
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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

}
