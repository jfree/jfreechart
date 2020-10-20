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
 * ---------------
 * ChartHints.java
 * ---------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 27-Jun-2014 : Version 1 (DG);
 *
 */

package org.jfree.chart;

import java.util.Map;

/**
 * Special rendering hints that can be used internally by JFreeChart or by
 * specialised implementations of the {@code Graphics2D} API.  For example,
 * JFreeSVG's {@code SVGGraphics2D} class, will use the 
 * {@code KEY_BEGIN_ELEMENT} and {@code KEY_END_ELEMENT} hints to drive the 
 * output content.
 * 
 * @since 1.0.18
 */
public final class ChartHints {

    private ChartHints() {
        // no need to instantiate this    
    }
    
    /**
     * The key for a hint to signal the beginning of an element.  The value
     * should be a string containing the element id or, alternatively, a Map 
     * containing the 'id' (String) and 'ref' (String in JSON format).
     */
    public static final Key KEY_BEGIN_ELEMENT = new ChartHints.Key(0);
    
    /**
     * The key for a hint that ends an element.
     */
    public static final Key KEY_END_ELEMENT = new ChartHints.Key(1);
    
    /**
     * A key for rendering hints that can be used with JFreeChart (in 
     * addition to the regular Java2D rendering hints).
     */
    public static class Key extends java.awt.RenderingHints.Key {

        /**
         * Creates a new key.
         * 
         * @param privateKey  the private key. 
         */
        public Key(int privateKey) {
            super(privateKey);    
        }
    
        /**
         * Returns {@code true} if {@code val} is a value that is
         * compatible with this key, and {@code false} otherwise.
         * 
         * @param val  the value.
         * 
         * @return A boolean. 
         */
        @Override
        public boolean isCompatibleValue(Object val) {
            switch (intKey()) {
                case 0:
                    return val == null || val instanceof String 
                            || val instanceof Map;
                case 1:
                    return val == null || val instanceof Object;
                default:
                    throw new RuntimeException("Not possible!");
            }
        }
    }
    
}
