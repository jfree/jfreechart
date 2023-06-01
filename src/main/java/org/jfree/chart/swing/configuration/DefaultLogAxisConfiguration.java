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
 * -------------------------
 * DefaultLogAxisConfiguration.java
 * -------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Martin Hoeller;
 * Contributor(s):   Dimitry Polivaev;
 *
 */

package org.jfree.chart.swing.configuration;



import java.util.Map;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberTickUnit;

/**
 * A panel for editing properties of a {@link LogAxis}.
 */
class DefaultLogAxisConfiguration extends DefaultValueAxisConfiguration {

    private static final String MANUAL_TICK_UNIT = "logAxisManualTickUnit";

    public static boolean isContainedIn(Map<String, String> properties, String name) {
        return properties.get(name + "." + MANUAL_TICK_UNIT) != null;
    }


    private final double manualTickUnit;

    /**
     * Standard constructor: builds a property panel for the specified axis.
     *
     * @param axis  the axis, which should be changed.
     */
    DefaultLogAxisConfiguration(LogAxis axis, String name) {
        super(axis, name);
        this.manualTickUnit = axis.getTickUnit().getSize();
    }

    DefaultLogAxisConfiguration(Map<String, String> properties, String name) {
        super(properties, name); // calling the constructor of the superclass with the Map<String, String> and name parameters
        this.manualTickUnit = Double.valueOf(properties.get(name + "." + MANUAL_TICK_UNIT));
    }

    @Override
    void fillProperties(Map<String, String> properties) {
        super.fillProperties(properties); // calling the fillProperties method of the superclass
        properties.put(name + "." + MANUAL_TICK_UNIT, Double.toString(manualTickUnit));
    }

    @Override
    void setAxisProperties(Axis axis) {
        super.setAxisProperties(axis);
        LogAxis logAxis = (LogAxis) axis;
        if (! isAutoTickUnitSelection) {
            logAxis.setTickUnit(new NumberTickUnit(manualTickUnit));
        }
    }
}
