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
 * DefaultChartConfiguration.java
 * -----------------------
 * (C) Copyright 2000-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Arnaud Lelievre;
 *                   Daniel Gredler;
 *                   Dimitry Polivaev
*
 */

package org.jfree.chart.swing.configuration;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.title.Title;

/**
 * A panel for editing chart properties (includes subpanels for the title,
 * legend and plot).
 */
public class DefaultChartConfiguration {

    private final DefaultTitleConfiguration titleConfiguration;

    private final DefaultPlotConfiguration plotConfiguration;

    private final boolean antialias;

    private final Color background;

    DefaultChartConfiguration(JFreeChart chart) {
        this.antialias = chart.getAntiAlias();
        this.background = (Color) chart.getBackgroundPaint();

        Title title = chart.getTitle();
        Plot plot = chart.getPlot();
        this.titleConfiguration = new DefaultTitleConfiguration(title, "title");
        if (plot instanceof PolarPlot) {
            this.plotConfiguration = new DefaultPolarPlotConfiguration((PolarPlot) plot, "plot");
        }
        else {
            this.plotConfiguration = new DefaultPlotConfiguration(plot, "plot");
        }
    }

    DefaultChartConfiguration(Map<String, String> properties) {
        antialias = Boolean.parseBoolean(properties.getOrDefault("antialias", "true"));
        String x = properties.get("background");
        background = StringMapper.stringToColor(x);
        this.titleConfiguration = new DefaultTitleConfiguration(properties, "title");
        if(Boolean.valueOf(properties.get("polarPlot"))) {
                this.plotConfiguration = new DefaultPolarPlotConfiguration(properties, "plot");
            }
            else {
                this.plotConfiguration = new DefaultPlotConfiguration(properties, "plot");
            }
    }

    public Map<String, String> getProperties() {
        Map<String, String> properties = new HashMap<String, String>();
        fillProperties(properties);
        return properties;
    }

    public void fillProperties(Map<String, String> properties) {
        properties.put("antialias", Boolean.toString(antialias));
        properties.put("background", StringMapper.colorToString(background));
        properties.put("polarPlot", Boolean.toString(plotConfiguration instanceof DefaultPolarPlotConfiguration));
        titleConfiguration.fillProperties(properties);
        plotConfiguration.fillProperties(properties);
    }

    public void updateChart(JFreeChart chart) {
        this.titleConfiguration.setTitleProperties(chart);
        this.plotConfiguration.updatePlotProperties(chart.getPlot());
        chart.setAntiAlias(antialias);
        chart.setBackgroundPaint(background);
    }

}
