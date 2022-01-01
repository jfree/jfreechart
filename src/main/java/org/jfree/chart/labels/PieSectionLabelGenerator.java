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
 * PieSectionLabelGenerator.java
 * -----------------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import java.awt.Font;
import java.awt.Paint;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import org.jfree.data.general.PieDataset;

/**
 * Interface for a label generator for plots that use data from
 * a {@link PieDataset}.
 */
public interface PieSectionLabelGenerator<K extends Comparable<K>> {

    /**
     * Generates a label for a pie section.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     * @param key  the section key ({@code null} not permitted).
     *
     * @return The label (possibly {@code null}).
     */
    String generateSectionLabel(PieDataset<K> dataset, K key);

    /**
     * Generates an attributed label for the specified series, or
     * {@code null} if no attributed label is available (in which case,
     * the string returned by
     * {@link #generateSectionLabel(PieDataset, Comparable)} will
     * provide the fallback).  Only certain attributes are recognised by the
     * code that ultimately displays the labels:
     * <ul>
     * <li>{@link TextAttribute#FONT}: will set the font;</li>
     * <li>{@link TextAttribute#POSTURE}: a value of
     *     {@link TextAttribute#POSTURE_OBLIQUE} will add {@link Font#ITALIC} to
     *     the current font;</li>
     * <li>{@link TextAttribute#WEIGHT}: a value of
     *     {@link TextAttribute#WEIGHT_BOLD} will add {@link Font#BOLD} to the
     *     current font;</li>
     * <li>{@link TextAttribute#FOREGROUND}: this will set the {@link Paint}
     *     for the current</li>
     * <li>{@link TextAttribute#SUPERSCRIPT}: the values
     *     {@link TextAttribute#SUPERSCRIPT_SUB} and
     *     {@link TextAttribute#SUPERSCRIPT_SUPER} are recognised.</li>
     * </ul>
     *
     * @param dataset  the dataset.
     * @param key  the key.
     *
     * @return An attributed label (possibly {@code null}).
     */
    AttributedString generateAttributedSectionLabel(PieDataset<K> dataset,
            K key);

}
