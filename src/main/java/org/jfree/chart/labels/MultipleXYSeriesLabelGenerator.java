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
 * -----------------------------------
 * MultipleXYSeriesLabelGenerator.java
 * -----------------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.HashUtils;
import org.jfree.data.xy.XYDataset;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A series label generator for plots that use data from
 * an {@link org.jfree.data.xy.XYDataset}.
 */
public class MultipleXYSeriesLabelGenerator implements XYSeriesLabelGenerator,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 138976236941898560L;

    /** The default item label format. */
    public static final String DEFAULT_LABEL_FORMAT = "{0}";

    /** The format pattern for the initial part of the label. */
    private final String formatPattern;

    /** The format pattern for additional labels. */
    private final String additionalFormatPattern;

    /** Storage for the additional series labels. */
    private Map seriesLabelLists;

    /**
     * Creates an item label generator using default number formatters.
     */
    public MultipleXYSeriesLabelGenerator() {
        this(DEFAULT_LABEL_FORMAT);
    }

    /**
     * Creates a new series label generator.
     *
     * @param format  the format pattern ({@code null} not permitted).
     */
    public MultipleXYSeriesLabelGenerator(String format) {
        Args.nullNotPermitted(format, "format");
        this.formatPattern = format;
        this.additionalFormatPattern = "\n{0}";
        this.seriesLabelLists = new HashMap();
    }

    /**
     * Adds an extra label for the specified series.
     *
     * @param series  the series index.
     * @param label  the label.
     */
    public void addSeriesLabel(int series, String label) {
        Integer key = series;
        List labelList = (List) this.seriesLabelLists.get(key);
        if (labelList == null) {
            labelList = new java.util.ArrayList();
            this.seriesLabelLists.put(key, labelList);
        }
        labelList.add(label);
    }

    /**
     * Clears the extra labels for the specified series.
     *
     * @param series  the series index.
     */
    public void clearSeriesLabels(int series) {
        Integer key = series;
        this.seriesLabelLists.put(key, null);
    }

    /**
     * Generates a label for the specified series.  This label will be
     * used for the chart legend.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series.
     *
     * @return A series label.
     */
    @Override
    public String generateLabel(XYDataset dataset, int series) {
        Args.nullNotPermitted(dataset, "dataset");
        StringBuilder label = new StringBuilder();
        label.append(MessageFormat.format(this.formatPattern,
                createItemArray(dataset, series)));
        Integer key = series;
        List extraLabels = (List) this.seriesLabelLists.get(key);
        if (extraLabels != null) {
            Object[] temp = new Object[1];
            for (int i = 0; i < extraLabels.size(); i++) {
                temp[0] = extraLabels.get(i);
                String labelAddition = MessageFormat.format(
                        this.additionalFormatPattern, temp);
                label.append(labelAddition);
            }
        }
        return label.toString();
    }

    /**
     * Creates the array of items that can be passed to the
     * {@link MessageFormat} class for creating labels.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param series  the series (zero-based index).
     *
     * @return The items (never {@code null}).
     */
    protected Object[] createItemArray(XYDataset dataset, int series) {
        Object[] result = new Object[1];
        result[0] = dataset.getSeriesKey(series).toString();
        return result;
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
        MultipleXYSeriesLabelGenerator clone
                = (MultipleXYSeriesLabelGenerator) super.clone();
        clone.seriesLabelLists = new HashMap();
        Set keys = this.seriesLabelLists.keySet();
        for (Object key : keys) {
            Object entry = this.seriesLabelLists.get(key);
            Object toAdd = entry;
            if (entry instanceof PublicCloneable) {
                PublicCloneable pc = (PublicCloneable) entry;
                toAdd = pc.clone();
            }
            clone.seriesLabelLists.put(key, toAdd);
        }
        return clone;
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
        if (!(obj instanceof MultipleXYSeriesLabelGenerator)) {
            return false;
        }
        MultipleXYSeriesLabelGenerator that
                = (MultipleXYSeriesLabelGenerator) obj;
        if (!this.formatPattern.equals(that.formatPattern)) {
            return false;
        }
        if (!this.additionalFormatPattern.equals(
                that.additionalFormatPattern)) {
            return false;
        }
        if (!this.seriesLabelLists.equals(that.seriesLabelLists)) {
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
        int result = 127;
        result = HashUtils.hashCode(result, this.formatPattern);
        result = HashUtils.hashCode(result, this.additionalFormatPattern);
        result = HashUtils.hashCode(result, this.seriesLabelLists);
        return result;
    }

}
