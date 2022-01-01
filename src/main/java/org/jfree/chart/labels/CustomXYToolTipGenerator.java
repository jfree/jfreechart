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
 * -----------------------------
 * CustomXYToolTipGenerator.java
 * -----------------------------
 * (C) Copyright 2002-2020, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.labels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.api.PublicCloneable;

import org.jfree.data.xy.XYDataset;

/**
 * A tool tip generator that stores custom tooltips. The dataset passed into
 * the generateToolTip method is ignored.
 */
public class CustomXYToolTipGenerator implements XYToolTipGenerator,
        Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8636030004670141362L;

    /** Storage for the tooltip lists. */
    private List<List<String>> toolTipSeries = new ArrayList<>();

    /**
     * Default constructor.
     */
    public CustomXYToolTipGenerator() {
        super();
    }

    /**
     * Returns the number of tool tip lists stored by the renderer.
     *
     * @return The list count.
     */
    public int getListCount() {
        return this.toolTipSeries.size();
    }

    /**
     * Returns the number of tool tips in a given list.
     *
     * @param list  the list index (zero based).
     *
     * @return The tooltip count.
     */
    public int getToolTipCount(int list) {

        int result = 0;
        List<String> tooltips = this.toolTipSeries.get(list);
        if (tooltips != null) {
            result = tooltips.size();
        }
        return result;
    }

    /**
     * Returns the tool tip text for an item.
     *
     * @param series  the series index.
     * @param item  the item index.
     *
     * @return The tool tip text (possibly {@code null}).
     */
    public String getToolTipText(int series, int item) {
        String result = null;
        if (series < getListCount()) {
            List<String> tooltips = this.toolTipSeries.get(series);
            if (tooltips != null) {
                if (item < tooltips.size()) {
                    result = tooltips.get(item);
                }
            }
        }
        return result;
    }

    /**
     * Adds a list of tooltips for a series.
     *
     * @param toolTips  the list of tool tips.
     */
    public void addToolTipSeries(List<String> toolTips) {
        this.toolTipSeries.add(toolTips);
    }

    /**
     * Generates a tool tip text item for a particular item within a series.
     *
     * @param data  the dataset (ignored in this implementation).
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return The tooltip text.
     */
    @Override
    public String generateToolTip(XYDataset data, int series, int item) {
        return getToolTipText(series, item);
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
        CustomXYToolTipGenerator clone
            = (CustomXYToolTipGenerator) super.clone();
        if (this.toolTipSeries != null) {
            clone.toolTipSeries = new java.util.ArrayList(this.toolTipSeries);
        }
        return clone;
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
        if (obj instanceof CustomXYToolTipGenerator) {
            CustomXYToolTipGenerator generator = (CustomXYToolTipGenerator) obj;
            boolean result = true;
            for (int series = 0; series < getListCount(); series++) {
                for (int item = 0; item < getToolTipCount(series); item++) {
                    String t1 = getToolTipText(series, item);
                    String t2 = generator.getToolTipText(series, item);
                    if (t1 != null) {
                        result = result && t1.equals(t2);
                    }
                    else {
                        result = result && (t2 == null);
                    }
                }
            }
            return result;
        }
        return false;
    }

}
