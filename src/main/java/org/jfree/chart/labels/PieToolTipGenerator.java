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
 * ------------------------
 * PieToolTipGenerator.java
 * ------------------------
 * (C) Copyright 2001-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import org.jfree.data.general.PieDataset;

/**
 * A tool tip generator that is used by the
 * {@link org.jfree.chart.plot.pie.PiePlot} class.
 *
 * @param <K> the dataset key type.
 */
public interface PieToolTipGenerator<K extends Comparable<K>> {

    /**
     * Generates a tool tip text item for the specified item in the dataset.
     * This method can return {@code null} to indicate that no tool tip
     * should be displayed for an item.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the section key ({@code null} not permitted).
     *
     * @return The tool tip text (possibly {@code null}).
     */
    String generateToolTip(PieDataset<K> dataset, K key);

}
