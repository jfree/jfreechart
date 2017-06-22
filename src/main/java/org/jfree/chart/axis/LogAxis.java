/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * ------------
 * LogAxis.java
 * ------------
 * (C) Copyright 2006-2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Andrew Mickish (patch 1868745);
 *                   Peter Kolb (patches 1934255 and 2603321);
 *
 * Changes
 * -------
 * 24-Aug-2006 : Version 1 (DG);
 * 22-Mar-2007 : Use defaultAutoArrange attribute (DG);
 * 02-Aug-2007 : Fixed zooming bug, added support for margins (DG);
 * 14-Feb-2008 : Changed default minorTickCount to 9 - see bug report
 *               1892419 (DG);
 * 15-Feb-2008 : Applied a variation of patch 1868745 by Andrew Mickish to
 *               fix a labelling bug when the axis appears at the top or
 *               right of the chart (DG);
 * 19-Mar-2008 : Applied patch 1902418 by Andrew Mickish to fix bug in tick
 *               labels for vertical axis (DG);
 * 26-Mar-2008 : Changed createTickLabel() method from private to protected -
 *               see patch 1918209 by Andrew Mickish (DG);
 * 25-Sep-2008 : Moved minor tick fields up to superclass, see patch 1934255
 *               by Peter Kolb (DG);
 * 14-Jan-2009 : Fetch minor ticks from TickUnit, and corrected
 *               createLogTickUnits() (DG);
 * 21-Jan-2009 : No need to call setMinorTickCount() in constructor (DG);
 * 19-Mar-2009 : Added entity support - see patch 2603321 by Peter Kolb (DG);
 * 30-Mar-2009 : Added pan(double) method (DG);
 * 28-Oct-2011 : Fixed endless loop for 0 TickUnit, # 3429707 (MH);
 * 02-Jul-2013 : Use ParamChecks (DG);
 * 01-Aug-2013 : Added attributedLabel override to support superscripts,
 *               subscripts and more (DG);
 * 18-Mar-2014 : Add support for super-scripted tick labels (DG);
 * 
 */

package org.jfree.chart.axis;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.AttrStringUtils;
import org.jfree.chart.util.LogFormat;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.data.Range;

/**
 * A numerical axis that uses a logarithmic scale.  The class is an
 * alternative to the {@link LogarithmicAxis} class.
 *
 * @since 1.0.7
 */
public class LogAxis extends ValueAxis {

    /** The logarithm base. */
    private double base = 10.0;

    /** The logarithm of the base value - cached for performance. */
    private double baseLog = Math.log(10.0);

    /** 
     * The base symbol to display (if {@code null} then the numerical
     * value of the base is displayed).
     */
    private String baseSymbol = null;
    
    /** 
     * The formatter to use for the base value when the base is displayed
     * as a numerical value.
     */
    private Format baseFormatter = new DecimalFormat("0");
    
    /**  The smallest value permitted on the axis. */
    private double smallestValue = 1E-100;

    /** The current tick unit. */
    private NumberTickUnit tickUnit;

    /** The override number format. */
    private NumberFormat numberFormatOverride;

    /**
     * Creates a new {@code LogAxis} with no label.
     */
    public LogAxis() {
        this(null);
    }

    /**
     * Creates a new {@code LogAxis} with the given label.
     *
     * @param label  the axis label ({@code null} permitted).
     */
    public LogAxis(String label) {
        super(label, new NumberTickUnitSource());
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0, new DecimalFormat("0.#"), 10);
    }

    /**
     * Returns the base for the logarithm calculation.  The default value is
     * {@code 10.0}.
     *
     * @return The base for the logarithm calculation.
     *
     * @see #setBase(double)
     */
    public double getBase() {
        return this.base;
    }

    /**
     * Sets the base for the logarithm calculation and sends a change event to
     * all registered listeners.
     *
     * @param base  the base value (must be &gt; 1.0).
     *
     * @see #getBase()
     */
    public void setBase(double base) {
        if (base <= 1.0) {
            throw new IllegalArgumentException("Requires 'base' > 1.0.");
        }
        this.base = base;
        this.baseLog = Math.log(base);
        fireChangeEvent();
    }

    /**
     * Returns the symbol used to represent the base of the logarithmic scale
     * for the axis.  If this is {@code null} (the default) then the 
     * numerical value of the base is displayed.
     * 
     * @return The base symbol (possibly {@code null}).
     * 
     * @since 1.0.18
     */
    public String getBaseSymbol() {
        return this.baseSymbol;
    }
    
    /**
     * Sets the symbol used to represent the base value of the logarithmic 
     * scale and sends a change event to all registered listeners.
     * 
     * @param symbol  the symbol ({@code null} permitted).
     * 
     * @since 1.0.18
     */
    public void setBaseSymbol(String symbol) {
        this.baseSymbol = symbol;
        fireChangeEvent();
    }
    
    /**
     * Returns the formatter used to format the base value of the logarithmic
     * scale when it is displayed numerically.  The default value is
     * {@code new DecimalFormat("0")}.
     * 
     * @return The base formatter (never {@code null}).
     * 
     * @since 1.0.18
     */
    public Format getBaseFormatter() {
        return this.baseFormatter;
    }
    
    /**
     * Sets the formatter used to format the base value of the logarithmic 
     * scale when it is displayed numerically and sends a change event to all
     * registered listeners.
     * 
     * @param formatter  the formatter ({@code null} not permitted).
     * 
     * @since 1.0.18
     */
    public void setBaseFormatter(Format formatter) {
        Args.nullNotPermitted(formatter, "formatter");
        this.baseFormatter = formatter;
        fireChangeEvent();
    }
    
    /**
     * Returns the smallest value represented by the axis.
     *
     * @return The smallest value represented by the axis.
     *
     * @see #setSmallestValue(double)
     */
    public double getSmallestValue() {
        return this.smallestValue;
    }

    /**
     * Sets the smallest value represented by the axis and sends a change event
     * to all registered listeners.
     *
     * @param value  the value.
     *
     * @see #getSmallestValue()
     */
    public void setSmallestValue(double value) {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Requires 'value' > 0.0.");
        }
        this.smallestValue = value;
        fireChangeEvent();
    }

    /**
     * Returns the current tick unit.
     *
     * @return The current tick unit.
     *
     * @see #setTickUnit(NumberTickUnit)
     */
    public NumberTickUnit getTickUnit() {
        return this.tickUnit;
    }

    /**
     * Sets the tick unit for the axis and sends an {@link AxisChangeEvent} to
     * all registered listeners.  A side effect of calling this method is that
     * the "auto-select" feature for tick units is switched off (you can
     * restore it using the {@link ValueAxis#setAutoTickUnitSelection(boolean)}
     * method).
     *
     * @param unit  the new tick unit ({@code null} not permitted).
     *
     * @see #getTickUnit()
     */
    public void setTickUnit(NumberTickUnit unit) {
        // defer argument checking...
        setTickUnit(unit, true, true);
    }

    /**
     * Sets the tick unit for the axis and, if requested, sends an
     * {@link AxisChangeEvent} to all registered listeners.  In addition, an
     * option is provided to turn off the "auto-select" feature for tick units
     * (you can restore it using the
     * {@link ValueAxis#setAutoTickUnitSelection(boolean)} method).
     *
     * @param unit  the new tick unit ({@code null} not permitted).
     * @param notify  notify listeners?
     * @param turnOffAutoSelect  turn off the auto-tick selection?
     *
     * @see #getTickUnit()
     */
    public void setTickUnit(NumberTickUnit unit, boolean notify,
            boolean turnOffAutoSelect) {
        Args.nullNotPermitted(unit, "unit");
        this.tickUnit = unit;
        if (turnOffAutoSelect) {
            setAutoTickUnitSelection(false, false);
        }
        if (notify) {
            fireChangeEvent();
        }
    }

    /**
     * Returns the number format override.  If this is non-{@code null}, 
     * then it will be used to format the numbers on the axis.
     *
     * @return The number formatter (possibly {@code null}).
     *
     * @see #setNumberFormatOverride(NumberFormat)
     */
    public NumberFormat getNumberFormatOverride() {
        return this.numberFormatOverride;
    }

    /**
     * Sets the number format override and sends a change event to all 
     * registered listeners.  If this is non-{@code null}, then it will be
     * used to format the numbers on the axis.
     *
     * @param formatter  the number formatter ({@code null} permitted).
     *
     * @see #getNumberFormatOverride()
     */
    public void setNumberFormatOverride(NumberFormat formatter) {
        this.numberFormatOverride = formatter;
        fireChangeEvent();
    }

    /**
     * Calculates the log of the given value, using the current base.
     *
     * @param value  the value.
     *
     * @return The log of the given value.
     *
     * @see #calculateValue(double)
     * @see #getBase()
     */
    public double calculateLog(double value) {
        return Math.log(value) / this.baseLog;
    }

    /**
     * Calculates the value from a given log.
     *
     * @param log  the log value.
     *
     * @return The value with the given log.
     *
     * @see #calculateLog(double)
     * @see #getBase()
     */
    public double calculateValue(double log) {
        return Math.pow(this.base, log);
    }
    
    private double calculateValueNoINF(double log) {
        double result = calculateValue(log);
        if (Double.isInfinite(result)) {
            result = Double.MAX_VALUE;
        }
        if (result <= 0.0) {
            result = Double.MIN_VALUE;
        }
        return result;
    }

    /**
     * Converts a Java2D coordinate to an axis value, assuming that the
     * axis is aligned to the specified {@code edge} of the {@code area}.
     *
     * @param java2DValue  the Java2D coordinate.
     * @param area  the area for plotting data ({@code null} not 
     *     permitted).
     * @param edge  the edge that the axis is aligned to ({@code null} not
     *     permitted).
     *
     * @return A value along the axis scale.
     */
    @Override
    public double java2DToValue(double java2DValue, Rectangle2D area,
            RectangleEdge edge) {

        Range range = getRange();
        double axisMin = calculateLog(Math.max(this.smallestValue, 
                range.getLowerBound()));
        double axisMax = calculateLog(range.getUpperBound());

        double min = 0.0;
        double max = 0.0;
        if (RectangleEdge.isTopOrBottom(edge)) {
            min = area.getX();
            max = area.getMaxX();
        } else if (RectangleEdge.isLeftOrRight(edge)) {
            min = area.getMaxY();
            max = area.getY();
        }
        double log;
        if (isInverted()) {
            log = axisMax - (java2DValue - min) / (max - min)
                    * (axisMax - axisMin);
        } else {
            log = axisMin + (java2DValue - min) / (max - min)
                    * (axisMax - axisMin);
        }
        return calculateValue(log);
    }

    /**
     * Converts a value on the axis scale to a Java2D coordinate relative to
     * the given {@code area}, based on the axis running along the
     * specified {@code edge}.
     *
     * @param value  the data value.
     * @param area  the area ({@code null} not permitted).
     * @param edge  the edge ({@code null} not permitted).
     *
     * @return The Java2D coordinate corresponding to {@code value}.
     */
    @Override
    public double valueToJava2D(double value, Rectangle2D area,
            RectangleEdge edge) {

        Range range = getRange();
        double axisMin = calculateLog(range.getLowerBound());
        double axisMax = calculateLog(range.getUpperBound());
        value = calculateLog(value);

        double min = 0.0;
        double max = 0.0;
        if (RectangleEdge.isTopOrBottom(edge)) {
            min = area.getX();
            max = area.getMaxX();
        } else if (RectangleEdge.isLeftOrRight(edge)) {
            max = area.getMinY();
            min = area.getMaxY();
        }
        if (isInverted()) {
            return max
                   - ((value - axisMin) / (axisMax - axisMin)) * (max - min);
        } else {
            return min
                   + ((value - axisMin) / (axisMax - axisMin)) * (max - min);
        }
    }

    /**
     * Configures the axis.  This method is typically called when an axis
     * is assigned to a new plot.
     */
    @Override
    public void configure() {
        if (isAutoRange()) {
            autoAdjustRange();
        }
    }

    /**
     * Adjusts the axis range to match the data range that the axis is
     * required to display.
     */
    @Override
    protected void autoAdjustRange() {
        Plot plot = getPlot();
        if (plot == null) {
            return;  // no plot, no data
        }

        if (plot instanceof ValueAxisPlot) {
            ValueAxisPlot vap = (ValueAxisPlot) plot;

            Range r = vap.getDataRange(this);
            if (r == null) {
                r = getDefaultAutoRange();
            }

            double upper = r.getUpperBound();
            double lower = Math.max(r.getLowerBound(), this.smallestValue);
            double range = upper - lower;

            // if fixed auto range, then derive lower bound...
            double fixedAutoRange = getFixedAutoRange();
            if (fixedAutoRange > 0.0) {
                lower = Math.max(upper - fixedAutoRange, this.smallestValue);
            }
            else {
                // ensure the autorange is at least <minRange> in size...
                double minRange = getAutoRangeMinimumSize();
                if (range < minRange) {
                    double expand = (minRange - range) / 2;
                    upper = upper + expand;
                    lower = lower - expand;
                }

                // apply the margins - these should apply to the exponent range
                double logUpper = calculateLog(upper);
                double logLower = calculateLog(lower);
                double logRange = logUpper - logLower;
                logUpper = logUpper + getUpperMargin() * logRange;
                logLower = logLower - getLowerMargin() * logRange;
                upper = calculateValueNoINF(logUpper);
                lower = calculateValueNoINF(logLower);
            }
            setRange(new Range(lower, upper), false, false);
        }

    }

    /**
     * Draws the axis on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param cursor  the cursor location (determines where to draw the axis).
     * @param plotArea  the area within which the axes and plot should be drawn.
     * @param dataArea  the area within which the data should be drawn.
     * @param edge  the axis location ({@code null} not permitted).
     * @param plotState  collects information about the plot ({@code null} 
     *         permitted).
     *
     * @return The axis state (never {@code null}).
     */
    @Override
    public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea,
            Rectangle2D dataArea, RectangleEdge edge,
            PlotRenderingInfo plotState) {

        AxisState state;
        // if the axis is not visible, don't draw it...
        if (!isVisible()) {
            state = new AxisState(cursor);
            // even though the axis is not visible, we need ticks for the
            // gridlines...
            List ticks = refreshTicks(g2, state, dataArea, edge);
            state.setTicks(ticks);
            return state;
        }
        state = drawTickMarksAndLabels(g2, cursor, plotArea, dataArea, edge);
        if (getAttributedLabel() != null) {
            state = drawAttributedLabel(getAttributedLabel(), g2, plotArea, 
                    dataArea, edge, state);
            
        } else {
            state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
        }
        createAndAddEntity(cursor, state, dataArea, edge, plotState);
        return state;
    }

    /**
     * Calculates the positions of the tick labels for the axis, storing the
     * results in the tick label list (ready for drawing).
     *
     * @param g2  the graphics device.
     * @param state  the axis state.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the location of the axis.
     *
     * @return A list of ticks.
     */
    @Override
    public List refreshTicks(Graphics2D g2, AxisState state,
            Rectangle2D dataArea, RectangleEdge edge) {
        List result = new java.util.ArrayList();
        if (RectangleEdge.isTopOrBottom(edge)) {
            result = refreshTicksHorizontal(g2, dataArea, edge);
        }
        else if (RectangleEdge.isLeftOrRight(edge)) {
            result = refreshTicksVertical(g2, dataArea, edge);
        }
        return result;
    }
    
    /**
     * Returns a list of ticks for an axis at the top or bottom of the chart.
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param dataArea  the data area ({@code null} not permitted).
     * @param edge  the edge ({@code null} not permitted).
     *
     * @return A list of ticks.
     */
    protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {

        Range range = getRange();
        List ticks = new ArrayList();
        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);
        TextAnchor textAnchor;
        if (edge == RectangleEdge.TOP) {
            textAnchor = TextAnchor.BOTTOM_CENTER;
        }
        else {
            textAnchor = TextAnchor.TOP_CENTER;
        }

        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);
        }
        int minorTickCount = this.tickUnit.getMinorTickCount();
        double unit = getTickUnit().getSize();
        double index = Math.ceil(calculateLog(getRange().getLowerBound()) 
                / unit);
        double start = index * unit;
        double end = calculateLog(getUpperBound());
        double current = start;
        boolean hasTicks = (this.tickUnit.getSize() > 0.0)
                           && !Double.isInfinite(start);
        while (hasTicks && current <= end) {
            double v = calculateValueNoINF(current);
            if (range.contains(v)) {
                ticks.add(new LogTick(TickType.MAJOR, v, createTickLabel(v),
                        textAnchor));
            }
            // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current
                    + this.tickUnit.getSize());
            for (int i = 1; i < minorTickCount; i++) {
                double minorV = v + i * ((next - v) / minorTickCount);
                if (range.contains(minorV)) {
                    ticks.add(new LogTick(TickType.MINOR, minorV, null,
                            textAnchor));
                }
            }
            current = current + this.tickUnit.getSize();
        }
        return ticks;
    }

    /**
     * Returns a list of ticks for an axis at the left or right of the chart.
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param dataArea  the data area ({@code null} not permitted).
     * @param edge  the edge that the axis is aligned to ({@code null} 
     *     not permitted).
     *
     * @return A list of ticks.
     */
    protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {

        Range range = getRange();
        List ticks = new ArrayList();
        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);
        TextAnchor textAnchor;
        if (edge == RectangleEdge.RIGHT) {
            textAnchor = TextAnchor.CENTER_LEFT;
        }
        else {
            textAnchor = TextAnchor.CENTER_RIGHT;
        }

        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);
        }
        int minorTickCount = this.tickUnit.getMinorTickCount();
        double unit = getTickUnit().getSize();
        double index = Math.ceil(calculateLog(getRange().getLowerBound()) 
                / unit);
        double start = index * unit;
        double end = calculateLog(getUpperBound());
        double current = start;
        boolean hasTicks = (this.tickUnit.getSize() > 0.0)
                           && !Double.isInfinite(start);
        while (hasTicks && current <= end) {
            double v = calculateValueNoINF(current);
            if (range.contains(v)) {
                ticks.add(new LogTick(TickType.MAJOR, v, createTickLabel(v),
                        textAnchor));
            }
            // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current
                    + this.tickUnit.getSize());
            for (int i = 1; i < minorTickCount; i++) {
                double minorV = v + i * ((next - v) / minorTickCount);
                if (range.contains(minorV)) {
                    ticks.add(new LogTick(TickType.MINOR, minorV, null,
                            textAnchor));
                }
            }
            current = current + this.tickUnit.getSize();
        }
        return ticks;
    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param dataArea  the area defined by the axes ({@code null} not 
     *     permitted).
     * @param edge  the axis location ({@code null} not permitted).
     *
     * @since 1.0.7
     */
    protected void selectAutoTickUnit(Graphics2D g2, Rectangle2D dataArea,
            RectangleEdge edge) {
        if (RectangleEdge.isTopOrBottom(edge)) {
            selectHorizontalAutoTickUnit(g2, dataArea, edge);
        }
        else if (RectangleEdge.isLeftOrRight(edge)) {
            selectVerticalAutoTickUnit(g2, dataArea, edge);
        }
    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area defined by the axes.
     * @param edge  the axis location.
     *
     * @since 1.0.7
     */
    protected void selectHorizontalAutoTickUnit(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        // select a tick unit that is the next one bigger than the current
        // (log) range divided by 50
        Range range = getRange();
        double logAxisMin = calculateLog(Math.max(this.smallestValue, 
                range.getLowerBound()));
        double logAxisMax = calculateLog(range.getUpperBound());
        double size = (logAxisMax - logAxisMin) / 50;
        TickUnitSource tickUnits = getStandardTickUnits();
        TickUnit candidate = tickUnits.getCeilingTickUnit(size);
        TickUnit prevCandidate = candidate;
        boolean found = false;
        while (!found) {
        // while the tick labels overlap and there are more tick sizes available,
            // choose the next bigger label
            this.tickUnit = (NumberTickUnit) candidate;
            double tickLabelWidth = estimateMaximumTickLabelWidth(g2, 
                    candidate);
            // what is the available space for one unit?
            double candidateWidth = exponentLengthToJava2D(candidate.getSize(), 
                    dataArea, edge);
            if (tickLabelWidth < candidateWidth) {
                found = true;
            } else if (Double.isNaN(candidateWidth)) {
                candidate = prevCandidate;
                found = true;
            } else {
                prevCandidate = candidate;
                candidate = tickUnits.getLargerTickUnit(prevCandidate);
                if (candidate.equals(prevCandidate)) {
                    found = true;  // there are no more candidates
                }
            }
        } 
        setTickUnit((NumberTickUnit) candidate, false, false);
    }

    /**
     * Converts a length in data coordinates into the corresponding length in
     * Java2D coordinates.
     *
     * @param length  the length.
     * @param area  the plot area.
     * @param edge  the edge along which the axis lies.
     *
     * @return The length in Java2D coordinates.
     *
     * @since 1.0.7
     */
    public double exponentLengthToJava2D(double length, Rectangle2D area,
                                RectangleEdge edge) {
        double one = valueToJava2D(calculateValueNoINF(1.0), area, edge);
        double l = valueToJava2D(calculateValueNoINF(length + 1.0), area, edge);
        return Math.abs(l - one);
    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the axis location.
     *
     * @since 1.0.7
     */
    protected void selectVerticalAutoTickUnit(Graphics2D g2, 
            Rectangle2D dataArea, RectangleEdge edge) {
        // select a tick unit that is the next one bigger than the current
        // (log) range divided by 50
        Range range = getRange();
        double logAxisMin = calculateLog(Math.max(this.smallestValue, 
                range.getLowerBound()));
        double logAxisMax = calculateLog(range.getUpperBound());
        double size = (logAxisMax - logAxisMin) / 50;
        TickUnitSource tickUnits = getStandardTickUnits();
        TickUnit candidate = tickUnits.getCeilingTickUnit(size);
        TickUnit prevCandidate = candidate;
        boolean found = false;
        while (!found) {
        // while the tick labels overlap and there are more tick sizes available,
            // choose the next bigger label
            this.tickUnit = (NumberTickUnit) candidate;
            double tickLabelHeight = estimateMaximumTickLabelHeight(g2);
            // what is the available space for one unit?
            double candidateHeight = exponentLengthToJava2D(candidate.getSize(), 
                    dataArea, edge);
            if (tickLabelHeight < candidateHeight) {
                found = true;
            } else if (Double.isNaN(candidateHeight)) {
                candidate = prevCandidate;
                found = true;
            } else {
                prevCandidate = candidate;
                candidate = tickUnits.getLargerTickUnit(prevCandidate);
                if (candidate.equals(prevCandidate)) {
                    found = true;  // there are no more candidates
                }
            }
        } 
        setTickUnit((NumberTickUnit) candidate, false, false);
    }

    /**
     * Creates a tick label for the specified value based on the current
     * tick unit (used for formatting the exponent).
     *
     * @param value  the value.
     *
     * @return The label.
     *
     * @since 1.0.18
     */
    protected AttributedString createTickLabel(double value) {
        if (this.numberFormatOverride != null) {
            return new AttributedString(
                    this.numberFormatOverride.format(value));
        } else {
            String baseStr = this.baseSymbol;
            if (baseStr == null) {
                baseStr = this.baseFormatter.format(this.base);
            }
            double logy = calculateLog(value);
            String exponentStr = getTickUnit().valueToString(logy);
            AttributedString as = new AttributedString(baseStr + exponentStr);
            as.addAttributes(getTickLabelFont().getAttributes(), 0, (baseStr 
                    + exponentStr).length());
            as.addAttribute(TextAttribute.SUPERSCRIPT, 
                    TextAttribute.SUPERSCRIPT_SUPER, baseStr.length(), 
                    baseStr.length() + exponentStr.length());
            return as;
        }
    }

    /**
     * Estimates the maximum tick label height.
     *
     * @param g2  the graphics device.
     *
     * @return The maximum height.
     *
     * @since 1.0.7
     */
    protected double estimateMaximumTickLabelHeight(Graphics2D g2) {
        RectangleInsets tickLabelInsets = getTickLabelInsets();
        double result = tickLabelInsets.getTop() + tickLabelInsets.getBottom();

        Font tickLabelFont = getTickLabelFont();
        FontRenderContext frc = g2.getFontRenderContext();
        result += tickLabelFont.getLineMetrics("123", frc).getHeight();
        return result;
    }

    /**
     * Estimates the maximum width of the tick labels, assuming the specified
     * tick unit is used.
     * <P>
     * Rather than computing the string bounds of every tick on the axis, we
     * just look at two values: the lower bound and the upper bound for the
     * axis.  These two values will usually be representative.
     *
     * @param g2  the graphics device.
     * @param unit  the tick unit to use for calculation.
     *
     * @return The estimated maximum width of the tick labels.
     *
     * @since 1.0.7
     */
    protected double estimateMaximumTickLabelWidth(Graphics2D g2, 
            TickUnit unit) {

        RectangleInsets tickLabelInsets = getTickLabelInsets();
        double result = tickLabelInsets.getLeft() + tickLabelInsets.getRight();

        if (isVerticalTickLabels()) {
            // all tick labels have the same width (equal to the height of the
            // font)...
            FontRenderContext frc = g2.getFontRenderContext();
            LineMetrics lm = getTickLabelFont().getLineMetrics("0", frc);
            result += lm.getHeight();
        }
        else {
            // look at lower and upper bounds...
            Range range = getRange();
            double lower = range.getLowerBound();
            double upper = range.getUpperBound();
            AttributedString lowerStr = createTickLabel(lower);
            AttributedString upperStr = createTickLabel(upper);
            double w1 = AttrStringUtils.getTextBounds(lowerStr, g2).getWidth();
            double w2 = AttrStringUtils.getTextBounds(upperStr, g2).getWidth();
            result += Math.max(w1, w2);
        }
        return result;
    }

    /**
     * Zooms in on the current range.
     *
     * @param lowerPercent  the new lower bound.
     * @param upperPercent  the new upper bound.
     */
    @Override
    public void zoomRange(double lowerPercent, double upperPercent) {
        Range range = getRange();
        double start = range.getLowerBound();
        double end = range.getUpperBound();
        double log1 = calculateLog(start);
        double log2 = calculateLog(end);
        double length = log2 - log1;
        Range adjusted;
        if (isInverted()) {
            double logA = log1 + length * (1 - upperPercent);
            double logB = log1 + length * (1 - lowerPercent);
            adjusted = new Range(calculateValueNoINF(logA), 
                    calculateValueNoINF(logB));
        }
        else {
            double logA = log1 + length * lowerPercent;
            double logB = log1 + length * upperPercent;
            adjusted = new Range(calculateValueNoINF(logA), 
                    calculateValueNoINF(logB));
        }
        setRange(adjusted);
    }

    /**
     * Slides the axis range by the specified percentage.
     *
     * @param percent  the percentage.
     *
     * @since 1.0.13
     */
    @Override
    public void pan(double percent) {
        Range range = getRange();
        double lower = range.getLowerBound();
        double upper = range.getUpperBound();
        double log1 = calculateLog(lower);
        double log2 = calculateLog(upper);
        double length = log2 - log1;
        double adj = length * percent;
        log1 = log1 + adj;
        log2 = log2 + adj;
        setRange(calculateValueNoINF(log1), calculateValueNoINF(log2));
    }
    
    /**
     * Increases or decreases the axis range by the specified percentage about
     * the central value and sends an {@link AxisChangeEvent} to all registered
     * listeners.
     * <P>
     * To double the length of the axis range, use 200% (2.0).
     * To halve the length of the axis range, use 50% (0.5).
     *
     * @param percent  the resize factor.
     *
     * @see #resizeRange(double, double)
     */
    @Override
    public void resizeRange(double percent) {
        Range range = getRange();
        double logMin = calculateLog(range.getLowerBound());
        double logMax = calculateLog(range.getUpperBound());
        double centralValue = calculateValueNoINF((logMin + logMax) / 2.0);
        resizeRange(percent, centralValue);
    }

    @Override
    public void resizeRange(double percent, double anchorValue) {
        resizeRange2(percent, anchorValue);
    }

    /**
     * Resizes the axis length to the specified percentage of the current
     * range and sends a change event to all registered listeners.  If 
     * {@code percent} is greater than 1.0 (100 percent) then the axis
     * range is increased (which has the effect of zooming out), while if the
     * {@code percent} is less than 1.0 the axis range is decreased 
     * (which has the effect of zooming in).  The resize occurs around an 
     * anchor value (which may not be in the center of the axis).  This is used
     * to support mouse wheel zooming around an arbitrary point on the plot.
     * <br><br>
     * This method is overridden to perform the percentage calculations on the
     * log values (which are linear for this axis).
     * 
     * @param percent  the percentage (must be greater than zero).
     * @param anchorValue  the anchor value.
     */
    @Override
    public void resizeRange2(double percent, double anchorValue) {
        if (percent > 0.0) {
            double logAnchorValue = calculateLog(anchorValue);
            Range range = getRange();
            double logAxisMin = calculateLog(range.getLowerBound());
            double logAxisMax = calculateLog(range.getUpperBound());

            double left = percent * (logAnchorValue - logAxisMin);
            double right = percent * (logAxisMax - logAnchorValue);
            
            double upperBound = calculateValueNoINF(logAnchorValue + right);
            Range adjusted = new Range(calculateValueNoINF(
                    logAnchorValue - left), upperBound);
            setRange(adjusted);
        }
        else {
            setAutoRange(true);
        }
    }

    /**
     * Tests this axis for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LogAxis)) {
            return false;
        }
        LogAxis that = (LogAxis) obj;
        if (this.base != that.base) {
            return false;
        }
        if (!ObjectUtils.equal(this.baseSymbol, that.baseSymbol)) {
            return false;
        }
        if (!this.baseFormatter.equals(that.baseFormatter)) {
            return false;
        }
        if (this.smallestValue != that.smallestValue) {
            return false;
        }
        if (!ObjectUtils.equal(this.numberFormatOverride, 
                that.numberFormatOverride)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 193;
        long temp = Double.doubleToLongBits(this.base);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.smallestValue);
        result = 37 * result + (int) (temp ^ (temp >>> 32));
        if (this.numberFormatOverride != null) {
            result = 37 * result + this.numberFormatOverride.hashCode();
        }
        result = 37 * result + this.tickUnit.hashCode();
        return result;
    }

}
