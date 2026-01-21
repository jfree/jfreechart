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
 * ----------------------------
 * DefaultNumberAxisConfiguration.java
 * ----------------------------
 * (C) Copyright 2005-2022, David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Arnaud Lelievre;
 *                   Dimitry Polivaev
 *
 */

package org.jfree.chart.swing.configuration;



import java.util.Map;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;

/**
 * A panel for editing the properties of a value axis.
 */
class DefaultNumberAxisConfiguration extends DefaultValueAxisConfiguration {
    private static final String MANUAL_TICK_UNIT = "numberAxisManualTickUnit";


    public static boolean isContainedIn(Map<String, String> properties, String name) {
        return properties.get(name + "." + MANUAL_TICK_UNIT) != null;
    }

    private double manualTickUnit;


    /**
     * Standard constructor: builds a property panel for the specified axis.
     *
     * @param axis  the axis, which should be changed.
     */
    DefaultNumberAxisConfiguration(NumberAxis axis, String name) {

        super(axis, name);

        this.manualTickUnit = axis.getTickUnit().getSize();
    }


    DefaultNumberAxisConfiguration(Map<String, String> properties, String name) {
        super(properties, name);
        this.manualTickUnit = Double.valueOf(properties.get(name + "." + MANUAL_TICK_UNIT));
    }

    @Override
    void fillProperties(Map<String, String> properties) {
        super.fillProperties(properties);
        properties.put(name + "." + MANUAL_TICK_UNIT, Double.toString(manualTickUnit));
    }

     @Override
    void setAxisProperties(Axis axis) {
        super.setAxisProperties(axis);
        NumberAxis numberAxis = (NumberAxis) axis;
        if (! isAutoTickUnitSelection) {
            numberAxis.setTickUnit(new NumberTickUnit(manualTickUnit));
        }
    }
}
