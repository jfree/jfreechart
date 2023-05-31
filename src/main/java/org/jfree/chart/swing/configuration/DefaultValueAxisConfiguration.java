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
 * DefaultValueAxisConfiguration.java
 * ---------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Martin Hoeller (base on DefaultNumberAxisConfiguration
 *                                   by David Gilbert);
 * Contributor(s):   Dimitry Polivaev;
 *
 */

package org.jfree.chart.swing.configuration;

import java.awt.Color;
import java.util.Properties;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.ValueAxis;

/**
 * A panel for editing properties of a {@link ValueAxis}.
 */
class DefaultValueAxisConfiguration extends DefaultAxisConfiguration {

    public static boolean isContainedIn(Properties properties, String name) {
        return properties.getProperty(name + "." + "autoRange") != null;
    }
    /** A flag that indicates whether or not the axis range is determined
     *  automatically.
     */
    private final boolean autoRange;

    /** Flag if auto-tickunit-selection is enabled. */
    protected final boolean isAutoTickUnitSelection;

    /** The lowest value in the axis range. */
    private final double minimum;

    /** The highest value in the axis range. */
    private final double maximum;

    /** A text field for entering the minimum value in the axis range. */
    private final String minimumRange;

    /** A text field for entering the maximum value in the axis range. */
    private final String maximumRange;

    /** The paint selected for drawing the gridlines. */
    private final Color gridColor;

    /** The stroke selected for drawing the gridlines. */
    private final float gridStrokeWidth;

    /**
     * Standard constructor: builds a property panel for the specified axis.
     *
     * @param axis  the axis, which should be changed.
     */
    DefaultValueAxisConfiguration(ValueAxis axis, String name) {

        super(axis, name);

        this.autoRange = axis.isAutoRange();
        this.minimum = axis.getLowerBound();
        this.maximum = axis.getUpperBound();
        this.isAutoTickUnitSelection = axis.isAutoTickUnitSelection();

        this.gridColor = Color.BLUE;
        this.gridStrokeWidth = 1f;

        this.minimumRange = Double.toString(
                this.minimum);
         this.maximumRange = Double.toString(
                this.maximum);
      }

    DefaultValueAxisConfiguration(Properties properties, String name) {
        super(properties, name);
        this.autoRange = Boolean.parseBoolean(properties.getProperty(name + ".autoRange"));
        this.isAutoTickUnitSelection = Boolean.parseBoolean(properties.getProperty(name + ".autoTickUnitSelection"));
        this.minimum = Double.parseDouble(properties.getProperty(name + ".minimum"));
        this.maximum = Double.parseDouble(properties.getProperty(name + ".maximum"));
        this.minimumRange = properties.getProperty(name + ".minimumRange");
        this.maximumRange = properties.getProperty(name + ".maximumRange");
        this.gridColor = StringMapper.stringToColor(properties.getProperty(name + ".gridColor"));
        this.gridStrokeWidth = Float.parseFloat(properties.getProperty(name + ".gridStrokeWidth"));
    }

    @Override
    void fillProperties(Properties properties) {
        super.fillProperties(properties);
        properties.setProperty(name + ".autoRange", Boolean.toString(autoRange));
        properties.setProperty(name + ".autoTickUnitSelection", Boolean.toString(isAutoTickUnitSelection));
        properties.setProperty(name + ".minimum", Double.toString(minimum));
        properties.setProperty(name + ".maximum", Double.toString(maximum));
        properties.setProperty(name + ".minimumRange", minimumRange);
        properties.setProperty(name + ".maximumRange", maximumRange);
        properties.setProperty(name + ".gridColor", StringMapper.colorToString(gridColor));
        properties.setProperty(name + ".gridStrokeWidth", Float.toString(gridStrokeWidth));
    }

    @Override
    void setAxisProperties(Axis axis) {
        super.setAxisProperties(axis);
        ValueAxis valueAxis = (ValueAxis) axis;
        valueAxis.setAutoRange(this.autoRange);
        if (!this.autoRange) {
            valueAxis.setRange(this.minimum, this.maximum);
        }
        valueAxis.setAutoTickUnitSelection(this.isAutoTickUnitSelection);
    }
}
