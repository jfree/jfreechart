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
 * --------------
 * RangeAlign.java
 * --------------
 * (C) Copyright 2000-2021, ESI (TNO) and Contributors.
 *
 * Original Author:  Yuri Blankenstein (for ESI (TNO));
 *
 */
package org.jfree.data;

/**
 * Configures how the auto-range should be aligned when it differs from the
 * preferred range
 */
public enum RangeAlign {
    /**
     * Aligns the length with the lower-bound of the auto-range.
     */
    LOWER {
        @Override
        public Range align(Range range, double length) {
            return new Range(range.getLowerBound(),
                    range.getLowerBound() + length);
        }
    },
    /**
     * Aligns the length in the center of the auto-range.
     */
    CENTER {
        @Override
        public Range align(Range range, double length) {
            double offset = (range.getLength() - length) / 2;
            return new Range(range.getLowerBound() + offset,
                    range.getUpperBound() - offset);
        }
    },
    /**
     * Aligns the length with the upper-bound of the auto-range.
     */
    UPPER {
        @Override
        public Range align(Range range, double length) {
            return new Range(range.getUpperBound() - length,
                    range.getUpperBound());
        }
    };

	/**
	 * Returns the aligned range for this configuration, given the calculated
	 * auto-range and preferred length.
	 * 
	 * @param range  The calculated auto-range.
	 * @param length The preferred length
	 * @return The aligned range for this configuration.
	 */
    public abstract Range align(Range range, double length);
}