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
 * DefaultPlotConfiguration.java
 * ----------------------
 * (C) Copyright 2005-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Andrzej Porebski;
 *                   Arnaud Lelievre;
 *                   Daniel Gredler;
 *                   Dimitry Polivaev
 *
 */

package org.jfree.chart.swing.configuration;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Map;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;

/**
 * A panel for editing the properties of a {@link Plot}.
 */
class DefaultPlotConfiguration {
    protected final String name;

    private final Color backgroundColor;

    private final float outlineStrokeWidth;

    private final Color outlineColor;

    private final RectangleInsetsConfiguration plotInsets;

    private final PlotOrientation plotOrientation;

    private final boolean drawLines;

    private final boolean drawShapes;

    private final DefaultAxisConfiguration domainAxisPropertyConfiguration;

    private final DefaultAxisConfiguration rangeAxisPropertyConfiguration;

    DefaultPlotConfiguration(Plot plot, String name) {
        this.name = name;
        this.plotInsets = new RectangleInsetsConfiguration(plot.getInsets(), name + ".plotInsets");
        this.backgroundColor = (Color) plot.getBackgroundPaint();
        this.outlineStrokeWidth = ((BasicStroke)plot.getOutlineStroke()).getLineWidth();
        this.outlineColor = (Color) plot.getOutlinePaint();

        if (plot instanceof CategoryPlot) {
            this.plotOrientation = ((CategoryPlot) plot).getOrientation();
        }
        else if (plot instanceof XYPlot) {
            this.plotOrientation = ((XYPlot) plot).getOrientation();
        }
        else
            this.plotOrientation = null;

        boolean drawLines = false;

        boolean drawShapes = false;

        if (plot instanceof CategoryPlot) {
            CategoryItemRenderer renderer = ((CategoryPlot) plot).getRenderer();
            if (renderer instanceof LineAndShapeRenderer) {
                LineAndShapeRenderer r = (LineAndShapeRenderer) renderer;
                drawLines = r.getDefaultLinesVisible();
                drawShapes = r.getDefaultShapesVisible();
            }
        }
        else if (plot instanceof XYPlot) {
            XYItemRenderer renderer = ((XYPlot) plot).getRenderer();
            if (renderer instanceof StandardXYItemRenderer) {
                StandardXYItemRenderer r = (StandardXYItemRenderer) renderer;
                drawLines = r.getPlotLines();
                drawShapes = r.getBaseShapesVisible();
            }
        }
        this.drawLines = drawLines;
        this.drawShapes = drawShapes;

        Axis domainAxis = null;
        if (plot instanceof CategoryPlot) {
            domainAxis = ((CategoryPlot) plot).getDomainAxis();
        }
        else if (plot instanceof XYPlot) {
            domainAxis = ((XYPlot) plot).getDomainAxis();
        }
        this.domainAxisPropertyConfiguration = DefaultAxisConfiguration.getInstance(
                domainAxis, name + "." + "domainAxis");

        Axis rangeAxis = null;
        if (plot instanceof CategoryPlot) {
            rangeAxis = ((CategoryPlot) plot).getRangeAxis();
        }
        else if (plot instanceof XYPlot) {
            rangeAxis = ((XYPlot) plot).getRangeAxis();
        }
        else if (plot instanceof PolarPlot) {
            rangeAxis = ((PolarPlot) plot).getAxis();
        }

        this.rangeAxisPropertyConfiguration = DefaultAxisConfiguration.getInstance(rangeAxis,
                name + "." + "rangeAxis");
    }

    DefaultPlotConfiguration(Map<String, String> properties, String name) {
        this.backgroundColor = StringMapper.stringToColor(properties.get(name + ".backgroundColor"));
        this.outlineStrokeWidth = Float.parseFloat(properties.get(name + ".outlineStrokeWidth"));
        this.outlineColor = StringMapper.stringToColor(properties.get(name + ".outlineColor"));
        this.plotInsets = new RectangleInsetsConfiguration(properties, name + ".plotInsets");
        this.plotOrientation = StringMapper.optionalStringToEnum(properties.get(name + ".plotOrientation"), PlotOrientation.class);
        this.drawLines = Boolean.parseBoolean(properties.get(name + ".drawLines"));
        this.drawShapes = Boolean.parseBoolean(properties.get(name + ".drawShapes"));
        this.domainAxisPropertyConfiguration = DefaultAxisConfiguration.getInstance(properties, name + ".domainAxis");
        this.rangeAxisPropertyConfiguration = DefaultAxisConfiguration.getInstance(properties, name + ".rangeAxis");
        this.name = name;
    }

    void fillProperties(Map<String, String> properties) {
        properties.put(name + ".backgroundColor", StringMapper.colorToString(backgroundColor));
        properties.put(name + ".outlineStrokeWidth", Float.toString(outlineStrokeWidth));
        properties.put(name + ".outlineColor", StringMapper.colorToString(outlineColor));
        plotInsets.fillProperties(properties);
        if(plotOrientation != null)
            properties.put(name + ".plotOrientation", plotOrientation.name());
        properties.put(name + ".drawLines", Boolean.toString(drawLines));
        properties.put(name + ".drawShapes", Boolean.toString(drawShapes));
        if(domainAxisPropertyConfiguration != null)
            domainAxisPropertyConfiguration.fillProperties(properties);
        if(rangeAxisPropertyConfiguration != null)
            rangeAxisPropertyConfiguration.fillProperties(properties);
    }

   void updatePlotProperties(Plot plot) {

        // set the plot properties...
        plot.setOutlinePaint(outlineColor);
        plot.setOutlineStroke(new BasicStroke(outlineStrokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        plot.setBackgroundPaint(backgroundColor);
        plot.setInsets(plotInsets.getRectangleInsets());

        // then the axis properties...
        if (this.domainAxisPropertyConfiguration != null) {
            Axis domainAxis = null;
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                domainAxis = p.getDomainAxis();
            }
            else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                domainAxis = p.getDomainAxis();
            }
            if (domainAxis != null) {
                this.domainAxisPropertyConfiguration.setAxisProperties(domainAxis);
            }
        }

        if (this.rangeAxisPropertyConfiguration != null) {
            Axis rangeAxis = null;
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                rangeAxis = p.getRangeAxis();
            }
            else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                rangeAxis = p.getRangeAxis();
            }
            else if (plot instanceof PolarPlot) {
                PolarPlot p = (PolarPlot) plot;
                rangeAxis = p.getAxis();
            }
            if (rangeAxis != null) {
                this.rangeAxisPropertyConfiguration.setAxisProperties(rangeAxis);
            }
        }

        if (this.plotOrientation != null) {
            if (plot instanceof CategoryPlot) {
                CategoryPlot p = (CategoryPlot) plot;
                p.setOrientation(this.plotOrientation);
            }
            else if (plot instanceof XYPlot) {
                XYPlot p = (XYPlot) plot;
                p.setOrientation(this.plotOrientation);
            }
        }

        if (plot instanceof CategoryPlot) {
            CategoryPlot p = (CategoryPlot) plot;
            CategoryItemRenderer r = p.getRenderer();
            if (r instanceof LineAndShapeRenderer) {
                ((LineAndShapeRenderer) r).setDefaultLinesVisible(this.drawLines);
            }
        }
        else if (plot instanceof XYPlot) {
            XYPlot p = (XYPlot) plot;
            XYItemRenderer r = p.getRenderer();
            if (r instanceof StandardXYItemRenderer) {
                ((StandardXYItemRenderer) r).setPlotLines(this.drawLines);
            }
        }

        if (plot instanceof CategoryPlot) {
            CategoryPlot p = (CategoryPlot) plot;
            CategoryItemRenderer r = p.getRenderer();
            if (r instanceof LineAndShapeRenderer) {
                ((LineAndShapeRenderer) r).setDefaultShapesVisible(this.drawShapes);
            }
        }
        else if (plot instanceof XYPlot) {
            XYPlot p = (XYPlot) plot;
            XYItemRenderer r = p.getRenderer();
            if (r instanceof StandardXYItemRenderer) {
                ((StandardXYItemRenderer) r).setBaseShapesVisible(
                        this.drawShapes);
            }
        }

    }
}
