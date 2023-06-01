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
 * ----------------------
 * DefaultAxisConfiguration.java
 * ----------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Andrzej Porebski;
 *                   Arnaud Lelievre;
 *                   Dimitry Polivaev
 *
 */

package org.jfree.chart.swing.configuration;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberAxis;

/**
 * A panel for editing the properties of an axis.
 */
class DefaultAxisConfiguration {


    public static boolean isContainedIn(Map<String, String> properties, String name) {
        return properties.get(name + "." + "areTickLabelsVisible") != null;
    }
    protected final String name;

    private final String label;

    private Font labelFont;

    private final Color labelColor;

    private Font tickLabelFont;


    private final Color tickLabelColor;

    private final boolean areTickLabelsVisible;

    private final boolean areTickMarksVisible;


    private final RectangleInsetsConfiguration tickLabelInsets;

    private final RectangleInsetsConfiguration labelInsets;

    /**
     * A static method that returns a configuration that is appropriate for the axis
     * type.
     *
     * @param axis  the axis whose properties are to be displayed/edited in
     *              the panel.
     *
     * @return A panel or {@code null} if axis is {@code null}.
     */
    static DefaultAxisConfiguration getInstance(Axis axis, String name) {

        if (axis != null) {
            // figure out what type of axis we have and instantiate the
            // appropriate panel
            if (axis instanceof NumberAxis) {
                return new DefaultNumberAxisConfiguration((NumberAxis) axis, name);
            }
            if (axis instanceof LogAxis) {
                return new DefaultLogAxisConfiguration((LogAxis) axis, name);
            }
            else {
                return new DefaultAxisConfiguration(axis, name);
            }
        }
        else {
            return null;
        }

    }

    public static DefaultAxisConfiguration getInstance(Map<String, String> properties, String name) {
        if(DefaultNumberAxisConfiguration.isContainedIn(properties, name))
            return new DefaultNumberAxisConfiguration(properties, name);
        if(DefaultLogAxisConfiguration.isContainedIn(properties, name))
            return new DefaultLogAxisConfiguration(properties, name);
        if(DefaultValueAxisConfiguration.isContainedIn(properties, name))
            return new DefaultValueAxisConfiguration(properties, name);
        if(DefaultAxisConfiguration.isContainedIn(properties, name))
            return new DefaultAxisConfiguration(properties, name);
        return null;

    }


    /**
     * Standard constructor: builds a panel for displaying/editing the
     * properties of the specified axis.
     *
     * @param axis  the axis whose properties are to be displayed/edited in
     *              the panel.
     */
    DefaultAxisConfiguration(Axis axis, String name) {
        this.name = name;
        this.label = axis.getLabel();
        this.labelFont = axis.getLabelFont();
        this.labelColor = (Color) axis.getLabelPaint();
        this.tickLabelFont = axis.getTickLabelFont();
        this.tickLabelColor = (Color) axis.getTickLabelPaint();

        // Insets values
        this.tickLabelInsets = new RectangleInsetsConfiguration(axis.getTickLabelInsets(), name + "." + "tickLabelInsets");
        this.labelInsets = new RectangleInsetsConfiguration(axis.getLabelInsets(), name + "." + "labelInsets");

        this.areTickLabelsVisible =axis.isTickLabelsVisible();
        this.areTickMarksVisible =axis.isTickMarksVisible();
    }


    DefaultAxisConfiguration(Map<String, String> properties, String name) {
        this.name = name;
        this.label = properties.get(name + "." + "label");
        this.labelFont = StringMapper.stringToFont(properties.get(name + "." + "labelFont"));
        this.labelColor = StringMapper.stringToColor(properties.get(name + "." + "labelColor"));
        this.tickLabelFont = StringMapper.stringToFont(properties.get(name + "." + "tickLabelFont"));
        this.tickLabelColor = StringMapper.stringToColor(properties.get(name + "." + "tickLabelColor"));
        this.tickLabelInsets = new RectangleInsetsConfiguration(properties, name + "." + "tickLabelInsets");
        this.labelInsets = new RectangleInsetsConfiguration(properties, name + "." + "labelInsets");
        this.areTickLabelsVisible = Boolean.valueOf(properties.get(name + "." + "areTickLabelsVisible"));
        this.areTickMarksVisible = Boolean.valueOf(properties.get(name + "." + "areTickMarksVisible"));
    }

    void fillProperties(Map<String, String> properties) {
        if(label != null)
            properties.put(name + "." + "label", label);
        properties.put(name + "." + "labelFont", StringMapper.fontToString(labelFont));
        properties.put(name + "." + "labelColor", StringMapper.colorToString(labelColor));
        properties.put(name + "." + "tickLabelFont", StringMapper.fontToString(tickLabelFont));
        properties.put(name + "." + "tickLabelColor", StringMapper.colorToString(tickLabelColor));
        tickLabelInsets.fillProperties(properties);
        labelInsets.fillProperties(properties);
        properties.put(name + "." + "areTickLabelsVisible", Boolean.toString(areTickLabelsVisible));
        properties.put(name + "." + "areTickMarksVisible", Boolean.toString(areTickMarksVisible));
    }

    void setAxisProperties(Axis axis) {
        axis.setLabel(label);
        axis.setLabelFont(labelFont);
        axis.setLabelPaint(labelColor);
        axis.setTickMarksVisible(areTickMarksVisible);
        axis.setTickLabelsVisible(areTickLabelsVisible);
        axis.setTickLabelFont(tickLabelFont);
        axis.setTickLabelPaint(tickLabelColor);
        axis.setTickLabelInsets(tickLabelInsets.getRectangleInsets());
        axis.setLabelInsets(labelInsets.getRectangleInsets());
    }
}
