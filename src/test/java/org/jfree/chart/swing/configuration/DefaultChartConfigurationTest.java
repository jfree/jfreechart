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
 * DefaultChartConfigurationTest.java
 * ---------------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  Dimitry Polivaev;
 *
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.swing.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.junit.jupiter.api.Test;

public class DefaultChartConfigurationTest {
    private static void verifyRoundTrip(JFreeChart chart, JFreeChart updatedChart) {
        DefaultChartConfiguration saved = new DefaultChartConfiguration(chart);
        Map<String, String> savedProperties = saved.getProperties();
        DefaultChartConfiguration uut = new DefaultChartConfiguration(savedProperties);
        Map<String, String> roundtripProperties = uut.getProperties();
        assertEquals(savedProperties, roundtripProperties);
        uut.updateChart(updatedChart);
        assertEquals(chart, updatedChart);

    }

    @Test
    void pieChartRoundTrip() {
        JFreeChart chart = ChartFactory.createPieChart("title", new DefaultPieDataset<>());
        JFreeChart updatedChart = ChartFactory.createPieChart("title", new DefaultPieDataset<>());
        verifyRoundTrip(chart, updatedChart);
    }

    @Test
    void polarChartRoundTrip() {
        JFreeChart chart = ChartFactory.createPolarChart("title", new DefaultXYDataset<>(), true, true, true);
        JFreeChart updatedChart = ChartFactory.createPolarChart("title", new DefaultXYDataset<>(), true, true, true);
        verifyRoundTrip(chart, updatedChart);
    }

    @Test
    void xyLineChartRoundTrip() {
        JFreeChart chart = ChartFactory.createXYLineChart("title", "x", "y", new DefaultXYDataset<>());
        chart.setBackgroundPaint(Color.RED);
        chart.setAntiAlias(true);
        JFreeChart updatedChart = ChartFactory.createXYLineChart("title", "x", "y", new DefaultXYDataset<>());
        updatedChart.setBackgroundPaint(Color.BLUE);
        chart.setAntiAlias(false);
        verifyRoundTrip(chart, updatedChart);
    }


    @Test
    void logAxisXyLineChartRoundTrip() {
        JFreeChart chart = ChartFactory.createXYLineChart("title", "x", "y", new DefaultXYDataset<>());
        LogAxis axis = new LogAxis("logX");
        axis.setLabelFont(new Font("dialog", Font.BOLD | Font.ITALIC, 24));
        ((XYPlot<?>) chart.getPlot()).setDomainAxis(axis);
        chart.setBackgroundPaint(Color.RED);
        chart.setAntiAlias(true);
        JFreeChart updatedChart = ChartFactory.createXYLineChart("title", "x", "y", new DefaultXYDataset<>());
        updatedChart.setBackgroundPaint(Color.BLUE);
        ((XYPlot<?>) updatedChart.getPlot()).setDomainAxis(new LogAxis("logY"));
        chart.setAntiAlias(false);
        verifyRoundTrip(chart, updatedChart);
    }
}
