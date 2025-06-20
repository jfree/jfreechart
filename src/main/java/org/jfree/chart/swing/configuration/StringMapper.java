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
 * ---------------------------
 * StringMapper.java
 * ---------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Dimitry Polivaev;
 *
 * Contributor(s):   -;
 *
 */
package org.jfree.chart.swing.configuration;

import java.awt.Color;
import java.awt.Font;

class StringMapper{

    static Color stringToColor(String color) {
            if (color == null) {
                return null;
            }
            if (color.length() != 7 || color.charAt(0) != '#') {
                throw new NumberFormatException("wrong color format in " + color + ". Expecting #rrggbb");
            }
            final int r = Integer.parseInt(color.substring(1, 3), 16);
            final int g = Integer.parseInt(color.substring(3, 5), 16);
            final int b = Integer.parseInt(color.substring(5, 7), 16);
            return new Color(r, g, b);
        }

    public static String colorToString(final Color col) {
        if (col == null) {
            return null;
        }
        return String.format("#%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());
    }


    static Font stringToFont(String font) {
        return Font.decode(font);
    }

    static String fontToString(Font font) {
        // Font name
        String str = font.getName();

        // Font style
        int style = font.getStyle();
        if (style == Font.PLAIN) {
            str += "-PLAIN";
        } if (style == Font.BOLD) {
            str += "-BOLD";
        } else if (style == Font.ITALIC) {
            str += "-ITALIC";
        } else if (style == (Font.BOLD | Font.ITALIC)) {
            str += "-BOLDITALIC";
        }

        // Font size
        str += "-" + font.getSize();

        return str;
    }

    static <T extends Enum<T>> T optionalStringToEnum(String optionalString, Class<T> enumClass) {
        return optionalString == null ? null : Enum.valueOf(enumClass, optionalString);
    }
}