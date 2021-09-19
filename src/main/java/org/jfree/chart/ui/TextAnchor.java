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
 */

package org.jfree.chart.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate the position of an anchor point for a text string.  This is
 * frequently used to align a string to a fixed point in some coordinate space.
 */
public final class TextAnchor implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8219158940496719660L;
    
    /** Top/left. */
    public static final TextAnchor TOP_LEFT 
        = new TextAnchor("TextAnchor.TOP_LEFT");

    /** Top/center. */
    public static final TextAnchor TOP_CENTER 
        = new TextAnchor("TextAnchor.TOP_CENTER");

    /** Top/right. */
    public static final TextAnchor TOP_RIGHT 
        = new TextAnchor("TextAnchor.TOP_RIGHT");

    /** Half-ascent/left. */
    public static final TextAnchor HALF_ASCENT_LEFT 
        = new TextAnchor("TextAnchor.HALF_ASCENT_LEFT");

    /** Half-ascent/center. */
    public static final TextAnchor HALF_ASCENT_CENTER 
        = new TextAnchor("TextAnchor.HALF_ASCENT_CENTER");

    /** Half-ascent/right. */
    public static final TextAnchor HALF_ASCENT_RIGHT 
        = new TextAnchor("TextAnchor.HALF_ASCENT_RIGHT");

    /** Middle/left. */
    public static final TextAnchor CENTER_LEFT 
        = new TextAnchor("TextAnchor.CENTER_LEFT");

    /** Middle/center. */
    public static final TextAnchor CENTER = new TextAnchor("TextAnchor.CENTER");

    /** Middle/right. */
    public static final TextAnchor CENTER_RIGHT 
        = new TextAnchor("TextAnchor.CENTER_RIGHT");

    /** Baseline/left. */
    public static final TextAnchor BASELINE_LEFT 
        = new TextAnchor("TextAnchor.BASELINE_LEFT");

    /** Baseline/center. */
    public static final TextAnchor BASELINE_CENTER 
        = new TextAnchor("TextAnchor.BASELINE_CENTER");

    /** Baseline/right. */
    public static final TextAnchor BASELINE_RIGHT 
        = new TextAnchor("TextAnchor.BASELINE_RIGHT");

    /** Bottom/left. */
    public static final TextAnchor BOTTOM_LEFT 
        = new TextAnchor("TextAnchor.BOTTOM_LEFT");

    /** Bottom/center. */
    public static final TextAnchor BOTTOM_CENTER 
        = new TextAnchor("TextAnchor.BOTTOM_CENTER");

    /** Bottom/right. */
    public static final TextAnchor BOTTOM_RIGHT 
        = new TextAnchor("TextAnchor.BOTTOM_RIGHT");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private TextAnchor(String name) {
        this.name = name;
    }
    
    /** 
     * Returns {@code true} if the anchor is a left-side anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isLeft() {
        return this == BASELINE_LEFT || this == BOTTOM_LEFT 
                || this == CENTER_LEFT || this == HALF_ASCENT_LEFT 
                || this == TOP_LEFT;
    }

    /** 
     * Returns {@code true} if the anchor is a right-side anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isRight() {
        return this == BASELINE_RIGHT || this == BOTTOM_RIGHT 
                || this == CENTER_RIGHT || this == HALF_ASCENT_RIGHT 
                || this == TOP_RIGHT;
    }

    /** 
     * Returns {@code true} if the anchor is a center anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isHorizontalCenter() {
        return this == BASELINE_CENTER || this == BOTTOM_CENTER 
                || this == CENTER || this == HALF_ASCENT_CENTER 
                || this == TOP_CENTER;
    }

    /** 
     * Returns {@code true} if the anchor is a top anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isTop() {
        return this == TOP_LEFT || this == TOP_CENTER || this == TOP_RIGHT;
    }

    /** 
     * Returns {@code true} if the anchor is a bottom anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isBottom() {
        return this == BOTTOM_LEFT || this == BOTTOM_CENTER 
                || this == BOTTOM_RIGHT;
    }
    
    /** 
     * Returns {@code true} if the anchor is a baseline anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isBaseline() {
        return this == BASELINE_LEFT || this == BASELINE_CENTER 
                || this == BASELINE_RIGHT;
    }
    
    /** 
     * Returns {@code true} if the anchor is a half-ascent anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isHalfAscent() {
        return this == HALF_ASCENT_LEFT || this == HALF_ASCENT_CENTER 
                || this == HALF_ASCENT_RIGHT;
    }
    
    /** 
     * Returns {@code true} if the anchor is a half-ascent anchor, and
     * {@code false} otherwise.
     * 
     * @return A boolean.
     */
    public boolean isVerticalCenter() {
        return this == CENTER_LEFT || this == CENTER  || this == CENTER_RIGHT;
    }
    
    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns {@code true} if this object is equal to the specified 
     * object, and {@code false} otherwise.
     *
     * @param o  the other object.
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof TextAnchor)) {
            return false;
        }

        TextAnchor order = (TextAnchor) o;
        if (!this.name.equals(order.name)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return The object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        TextAnchor result = null;
        if (this.equals(TextAnchor.TOP_LEFT)) {
            result = TextAnchor.TOP_LEFT;
        }
        else if (this.equals(TextAnchor.TOP_CENTER)) {
            result = TextAnchor.TOP_CENTER;
        }
        else if (this.equals(TextAnchor.TOP_RIGHT)) {
            result = TextAnchor.TOP_RIGHT;
        }
        else if (this.equals(TextAnchor.BOTTOM_LEFT)) {
            result = TextAnchor.BOTTOM_LEFT;
        }
        else if (this.equals(TextAnchor.BOTTOM_CENTER)) {
            result = TextAnchor.BOTTOM_CENTER;
        }
        else if (this.equals(TextAnchor.BOTTOM_RIGHT)) {
            result = TextAnchor.BOTTOM_RIGHT;
        }
        else if (this.equals(TextAnchor.BASELINE_LEFT)) {
            result = TextAnchor.BASELINE_LEFT;
        }
        else if (this.equals(TextAnchor.BASELINE_CENTER)) {
            result = TextAnchor.BASELINE_CENTER;
        }
        else if (this.equals(TextAnchor.BASELINE_RIGHT)) {
            result = TextAnchor.BASELINE_RIGHT;
        }
        else if (this.equals(TextAnchor.CENTER_LEFT)) {
            result = TextAnchor.CENTER_LEFT;
        }
        else if (this.equals(TextAnchor.CENTER)) {
            result = TextAnchor.CENTER;
        }
        else if (this.equals(TextAnchor.CENTER_RIGHT)) {
            result = TextAnchor.CENTER_RIGHT;
        }
        else if (this.equals(TextAnchor.HALF_ASCENT_LEFT)) {
            result = TextAnchor.HALF_ASCENT_LEFT;
        }
        else if (this.equals(TextAnchor.HALF_ASCENT_CENTER)) {
            result = TextAnchor.HALF_ASCENT_CENTER;
        }
        else if (this.equals(TextAnchor.HALF_ASCENT_RIGHT)) {
            result = TextAnchor.HALF_ASCENT_RIGHT;
        }
        return result;
    }

}
