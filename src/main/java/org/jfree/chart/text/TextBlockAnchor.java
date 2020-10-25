/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate the position of an anchor point for a text block.
 */
public final class TextBlockAnchor implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -3045058380983401544L;
    
    /** Top/left. */
    public static final TextBlockAnchor TOP_LEFT 
        = new TextBlockAnchor("TextBlockAnchor.TOP_LEFT");

    /** Top/center. */
    public static final TextBlockAnchor TOP_CENTER = new TextBlockAnchor(
        "TextBlockAnchor.TOP_CENTER"
    );

    /** Top/right. */
    public static final TextBlockAnchor TOP_RIGHT = new TextBlockAnchor(
       "TextBlockAnchor.TOP_RIGHT"
    );

    /** Middle/left. */
    public static final TextBlockAnchor CENTER_LEFT = new TextBlockAnchor(
        "TextBlockAnchor.CENTER_LEFT"
    );

    /** Middle/center. */
    public static final TextBlockAnchor CENTER 
        = new TextBlockAnchor("TextBlockAnchor.CENTER");

    /** Middle/right. */
    public static final TextBlockAnchor CENTER_RIGHT = new TextBlockAnchor(
        "TextBlockAnchor.CENTER_RIGHT"
    );

    /** Bottom/left. */
    public static final TextBlockAnchor BOTTOM_LEFT 
        = new TextBlockAnchor("TextBlockAnchor.BOTTOM_LEFT");

    /** Bottom/center. */
    public static final TextBlockAnchor BOTTOM_CENTER 
        = new TextBlockAnchor("TextBlockAnchor.BOTTOM_CENTER");

    /** Bottom/right. */
    public static final TextBlockAnchor BOTTOM_RIGHT 
        = new TextBlockAnchor("TextBlockAnchor.BOTTOM_RIGHT");

    /** The name. */
    private final String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private TextBlockAnchor(String name) {
        this.name = name;
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
        if (!(o instanceof TextBlockAnchor)) {
            return false;
        }

        TextBlockAnchor other = (TextBlockAnchor) o;
        if (!this.name.equals(other.name)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
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
        if (this.equals(TextBlockAnchor.TOP_CENTER)) {
            return TextBlockAnchor.TOP_CENTER;
        }
        else if (this.equals(TextBlockAnchor.TOP_LEFT)) {
            return TextBlockAnchor.TOP_LEFT;
        }
        else if (this.equals(TextBlockAnchor.TOP_RIGHT)) {
            return TextBlockAnchor.TOP_RIGHT;
        }
        else if (this.equals(TextBlockAnchor.CENTER)) {
            return TextBlockAnchor.CENTER;
        }
        else if (this.equals(TextBlockAnchor.CENTER_LEFT)) {
            return TextBlockAnchor.CENTER_LEFT;
        }
        else if (this.equals(TextBlockAnchor.CENTER_RIGHT)) {
            return TextBlockAnchor.CENTER_RIGHT;
        }
        else if (this.equals(TextBlockAnchor.BOTTOM_CENTER)) {
            return TextBlockAnchor.BOTTOM_CENTER;
        }
        else if (this.equals(TextBlockAnchor.BOTTOM_LEFT)) {
            return TextBlockAnchor.BOTTOM_LEFT;
        }
        else if (this.equals(TextBlockAnchor.BOTTOM_RIGHT)) {
            return TextBlockAnchor.BOTTOM_RIGHT;
        }
        return null;
    }

}

