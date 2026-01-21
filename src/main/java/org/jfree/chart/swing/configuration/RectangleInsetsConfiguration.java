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
 * RectangleInsetsConfiguration.java
 * ---------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Dimitry Polivaev;
 *
 * Contributor(s):   -;
 *
 */
package org.jfree.chart.swing.configuration;



import java.util.Map;

import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.UnitType;

class RectangleInsetsConfiguration {

    private final RectangleInsets rectangleInsets;
    private final String name;

    RectangleInsetsConfiguration(RectangleInsets rectangleInsets, String name) {
        this.rectangleInsets = rectangleInsets;
        this.name = name;
    }

    RectangleInsetsConfiguration(Map<String, String> properties, String name) {
        this.name = name;
        UnitType unitType = UnitType.valueOf(properties.get(name + "." + "unitType"));
        double top = Double.valueOf(properties.get(name + "." + "top"));
        double left = Double.valueOf(properties.get(name + "." + "left"));
        double bottom = Double.valueOf(properties.get(name + "." + "bottom"));
        double right = Double.valueOf(properties.get(name + "." + "right"));
        this.rectangleInsets = new RectangleInsets(unitType, top, left, bottom, right);
    }

    void fillProperties(Map<String, String> properties) {
        properties.put(name + "." + "unitType", rectangleInsets.getUnitType().toString());
        properties.put(name + "." + "top", Double.toString(rectangleInsets.getTop()));
        properties.put(name + "." + "left", Double.toString(rectangleInsets.getLeft()));
        properties.put(name + "." + "bottom", Double.toString(rectangleInsets.getBottom()));
        properties.put(name + "." + "right", Double.toString(rectangleInsets.getRight()));
    }
    RectangleInsets getRectangleInsets() {
         return rectangleInsets;
    }
}
