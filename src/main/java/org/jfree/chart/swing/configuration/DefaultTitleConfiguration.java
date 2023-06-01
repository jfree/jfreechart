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
 * -----------------------
 * DefaultTitleConfiguration.java
 * -----------------------
 * (C) Copyright 2005-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Arnaud Lelievre;
 *                   Daniel Gredler;
 *                   Dimitry Polivaev;
 *
 */

package org.jfree.chart.swing.configuration;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;

class DefaultTitleConfiguration {
    private final String name;

    private final boolean showTitle;

    private final String titleText;

    private final Font titleFont;

    private final Color titleColor;


    /**
     * Standard constructor: builds a panel for displaying/editing the
     * properties of the specified title.
     *
     * @param title  the title, which should be changed.
     */
    DefaultTitleConfiguration(Title title, String name) {

        this.name = name;
        TextTitle t = (title != null ? (TextTitle) title
                : new TextTitle());
        this.showTitle = (title != null);
        this.titleFont = t.getFont();
        this.titleText = t.getText();
        this.titleColor = (Color) t.getPaint();
    }

    DefaultTitleConfiguration(Map<String, String> properties, java.lang.String name) {
        this.name = name;
        this.showTitle = Boolean.valueOf(properties.get(name + "." + "showTitle"));
        this.titleFont = StringMapper.stringToFont(properties.get(name + "." + "titleFont"));
        this.titleText = properties.get(name + "." + "titleText");
        this.titleColor = StringMapper.stringToColor(properties.get(name + "." + "titleColor"));

    }


    void fillProperties(Map<String, String> properties) {
        properties.put(name + "." + "showTitle", Boolean.toString(showTitle));
        properties.put(name + "." + "titleFont", StringMapper.fontToString(titleFont));
        properties.put(name + "." + "titleText", titleText);
        properties.put(name + "." + "titleColor", StringMapper.colorToString(titleColor));
    }

    void setTitleProperties(JFreeChart chart) {
        if (this.showTitle) {
            TextTitle title = chart.getTitle();
            if (title == null) {
                title = new TextTitle();
                chart.setTitle(title);
            }
            title.setText(titleText);
            title.setFont(titleFont);
            title.setPaint(titleColor);
        }
        else {
            chart.setTitle((TextTitle) null);
        }
    }

}
