/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * --------------------------------
 * StandardPieToolTipGenerator.java
 * --------------------------------
 * (C) Copyright 2001-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Richard Atkinson;
 *                   Andreas Schroeder;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.general.PieDataset;

/**
 * A standard item label generator for plots that use data from a
 * {@link PieDataset}.
 * <p>
 * For the label format, use {0} where the pie section key should be inserted,
 * {1} for the absolute section value and {2} for the percent amount of the pie
 * section, e.g. {@code "{0} = {1} ({2})"} will display as
 * {@code apple = 120 (5%)}.
 *
 * @param <K> the dataset key type.
 */
public class StandardPieToolTipGenerator<K extends Comparable<K>> extends AbstractPieItemLabelGenerator<K>
        implements PieToolTipGenerator<K>, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2995304200445733779L;

    /** The default tooltip format. */
    public static final String DEFAULT_TOOLTIP_FORMAT = "{0}: ({1}, {2})";

    /**
     * Creates an item label generator using default number formatters.
     */
    public StandardPieToolTipGenerator() {
        this(DEFAULT_TOOLTIP_FORMAT);
    }

    /**
     * Creates a pie tool tip generator for the specified locale, using the
     * default format string.
     *
     * @param locale  the locale ({@code null} not permitted).
     */
    public StandardPieToolTipGenerator(Locale locale) {
        this(DEFAULT_TOOLTIP_FORMAT, locale);
    }

    /**
     * Creates a pie tool tip generator for the default locale.
     *
     * @param labelFormat  the label format ({@code null} not permitted).
     */
    public StandardPieToolTipGenerator(String labelFormat) {
        this(labelFormat, Locale.getDefault());
    }

    /**
     * Creates a pie tool tip generator for the specified locale.
     *
     * @param labelFormat  the label format ({@code null} not permitted).
     * @param locale  the locale ({@code null} not permitted).
     */
    public StandardPieToolTipGenerator(String labelFormat, Locale locale) {
        this(labelFormat, NumberFormat.getNumberInstance(locale),
                NumberFormat.getPercentInstance(locale));
    }

    /**
     * Creates an item label generator using the specified number formatters.
     *
     * @param labelFormat  the label format string ({@code null} not
     *                     permitted).
     * @param numberFormat  the format object for the values ({@code null}
     *                      not permitted).
     * @param percentFormat  the format object for the percentages
     *                       ({@code null} not permitted).
     */
    public StandardPieToolTipGenerator(String labelFormat,
            NumberFormat numberFormat, NumberFormat percentFormat) {
        super(labelFormat, numberFormat, percentFormat);
    }

    /**
     * Generates a tool tip text item for one section in a pie chart.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the section key ({@code null} not permitted).
     *
     * @return The tool tip text (possibly {@code null}).
     */
    @Override
    public String generateToolTip(PieDataset<K> dataset, K key) {
        return generateSectionLabel(dataset, key);
    }

    /**
     * Returns an independent copy of the generator.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  should not happen.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
