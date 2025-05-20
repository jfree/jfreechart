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
 * ------------------
 * ItemLabelClip.java
 * ------------------
 * (C) Copyright 2021-present, by Yuri Blankenstein and Contributors.
 *
 * Original Author:  Yuri Blankenstein;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.chart.labels;

/**
 * The clip type for the label. Only used when
 * {@link ItemLabelAnchor#isInternal()} returns {@code true}, if {@code false}
 * {@code labelClip} is always considered to be {@link ItemLabelClip#NONE})
 */
public enum ItemLabelClip {
    /** Only draw label when it fits the item */
    FIT,
    /** No clipping, labels might overlap */
    NONE,
    /** Does not draw outside the item, just clips the label */
    CLIP,
    /** Truncates the label with '...' to fit the item */
    TRUNCATE,
    /** Truncates the label on whole words with '...' to fit the item */
    TRUNCATE_WORD
}
