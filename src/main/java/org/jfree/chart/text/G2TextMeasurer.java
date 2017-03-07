/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2017, by Object Refinery Limited and Contributors.
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
 */

package org.jfree.chart.text;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A {@link TextMeasurer} based on a {@link Graphics2D}.
 */
public class G2TextMeasurer implements TextMeasurer {

    /** The graphics device. */
    private Graphics2D g2;
    
    /**
     * Creates a new text measurer.
     * 
     * @param g2  the graphics device.
     */
    public G2TextMeasurer(final Graphics2D g2) {
        this.g2 = g2;
    }

    /**
     * Returns the string width.
     * 
     * @param text  the text.
     * @param start  the index of the first character to measure.
     * @param end  the index of the last character to measure.
     * 
     * @return The string width.
     */
    public float getStringWidth(final String text, 
                                final int start, final int end) {
        final FontMetrics fm = this.g2.getFontMetrics();
        final Rectangle2D bounds = TextUtilities.getTextBounds(
            text.substring(start, end), this.g2, fm
        );
        final float result = (float) bounds.getWidth();
        return result;
    }
    
}

