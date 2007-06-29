/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * ------------
 * LogAxis.java
 * ------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LogAxis.java,v 1.1.2.2 2007/03/22 16:39:18 mungady Exp $
 *
 * Changes
 * -------
 * 24-Aug-2006 : Version 1 (DG);
 * 22-Mar-2007 : Use defaultAutoArrange attribute (DG);
 *
 */

package org.jfree.experimental.chart.axis;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.axis.AxisState;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTick;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.data.Range;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

/**
 * A numerical axis that uses a logarithmic scale.  The plan is for this class
 * to replace the {@link LogarithmicAxis} class.
 * 
 * WARNING: THIS CLASS IS NOT PART OF THE STANDARD JFREECHART API AND IS 
 * SUBJECT TO ALTERATION OR REMOVAL.  DO NOT RELY ON THIS CLASS FOR 
 * PRODUCTION USE.  Please experiment with this code and provide feedback.
 */

// TODO: support for margins that get inherited from ValueAxis
// TODO: add auto tick unit selection
// TODO: number formatting options
// TODO: write JUnit tests

public class LogAxis extends ValueAxis {

    /** The logarithm base. */
    private double base = 10.0;
    
    /** The logarithm of the base value - cached for performance. */
    private double baseLog = Math.log(10.0);
    
    /**  The smallest value permitted on the axis. */
    private double smallestValue = 1E-100;
    
    /** The current tick unit. */
    private NumberTickUnit tickUnit;
    
    /** The override number format. */
    private NumberFormat numberFormatOverride;

    /** The number of minor ticks per major tick unit. */
    private int minorTickCount; 
    
    /**
     * Creates a new <code>LogAxis</code> with no label.
     */
    public LogAxis() {
        this(null);    
    }
    
    /**
     * Creates a new <code>LogAxis</code> with the given label.
     * 
     * @param label  the axis label (<code>null</code> permitted).
     */
    public LogAxis(String label) {
        super(label, NumberAxis.createIntegerTickUnits());
        setDefaultAutoRange(new Range(0.01, 1.0));
        this.tickUnit = new NumberTickUnit(1.0);
        this.minorTickCount = 10;
        this.setTickMarksVisible(false);
    }
    
    /**
     * Returns the base for the logarithm calculation.
     * 
     * @return The base for the logarithm calculation.
     */
    public double getBase() {
        return this.base;
    }
    
    /**
     * Sets the base for the logarithm calculation and sends an 
     * {@link AxisChangeEvent} to all registered listeners.
     * 
     * @param base  the base value (must be > 1.0).
     */
    public void setBase(double base) {
        if (base <= 1.0) {
            throw new IllegalArgumentException("Requires 'base' > 1.0.");
        }
        this.base = base;
        this.baseLog = Math.log(base);
        notifyListeners(new AxisChangeEvent(this));
    }
    
    /**
     * Returns the smallest value represented by the axis.
     * 
     * @return The smallest value represented by the axis.
     */
    public double getSmallestValue() {
        return this.smallestValue;
    }
    
    /**
     * Sets the smallest value represented by the axis.
     * 
     * @param value  the value.
     */
    public void setSmallestValue(double value) {
        if (value <= 0.0) {
            throw new IllegalArgumentException("Requires 'value' > 0.0.");
        }
        this.smallestValue = value;
    }
    
    /**
     * Returns the current tick unit.
     * 
     * @return The current tick unit.
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
     * @param unit  the new tick unit (<code>null</code> not permitted).
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
     * @param unit  the new tick unit (<code>null</code> not permitted).
     * @param notify  notify listeners?
     * @param turnOffAutoSelect  turn off the auto-tick selection?
     */
    public void setTickUnit(NumberTickUnit unit, boolean notify, 
                            boolean turnOffAutoSelect) {

        if (unit == null) {
            throw new IllegalArgumentException("Null 'unit' argument.");   
        }
        this.tickUnit = unit;
        if (turnOffAutoSelect) {
            setAutoTickUnitSelection(false, false);
        }
        if (notify) {
            notifyListeners(new AxisChangeEvent(this));
        }

    }
    
    /**
     * Returns the number format override.  If this is non-null, then it will 
     * be used to format the numbers on the axis.
     *
     * @return The number formatter (possibly <code>null</code>).
     */
    public NumberFormat getNumberFormatOverride() {
        return this.numberFormatOverride;
    }

    /**
     * Sets the number format override.  If this is non-null, then it will be 
     * used to format the numbers on the axis.
     *
     * @param formatter  the number formatter (<code>null</code> permitted).
     */
    public void setNumberFormatOverride(NumberFormat formatter) {
        this.numberFormatOverride = formatter;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Returns the number of minor tick marks to display.
     * 
     * @return The number of minor tick marks to display.
     */
    public int getMinorTickCount() {
        return this.minorTickCount;
    }
    
    /**
     * Sets the number of minor tick marks to display.
     * 
     * @param count  the count.
     */
    public void setMinorTickCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Requires 'count' > 0.");
        }
        this.minorTickCount = count;
        notifyListeners(new AxisChangeEvent(this));
    }
    
    /**
     * Calculates the log of the given value, using the current base.
     * 
     * @param value  the value.
     * 
     * @return The log of the given value.
     * 
     * @see #getBase()
     */
    public double calculateLog(double value) {
        return Math.log(value) / this.baseLog;  
    }
    
    /**
     * Calculates the value from a given log.
     * 
     * @param log  the log value (must be > 0.0).
     * 
     * @return The value with the given log.
     */
    public double calculateValue(double log) {
        return Math.pow(this.base, log);
    }
    
    /**
     * Converts a Java2D coordinate to an axis value, assuming that the
     * axis covers the specified <code>edge</code> of the <code>area</code>.
     * 
     * @param java2DValue  the Java2D coordinate.
     * @param area  the area.
     * @param edge  the edge that the axis belongs to.
     * 
     * @return A value along the axis scale.
     */
    public double java2DToValue(double java2DValue, Rectangle2D area, 
            RectangleEdge edge) {
        
        Range range = getRange();
        double axisMin = calculateLog(range.getLowerBound());
        double axisMax = calculateLog(range.getUpperBound());

        double min = 0.0;
        double max = 0.0;
        if (RectangleEdge.isTopOrBottom(edge)) {
            min = area.getX();
            max = area.getMaxX();
        }
        else if (RectangleEdge.isLeftOrRight(edge)) {
            min = area.getMaxY();
            max = area.getY();
        }
        double log = 0.0;
        if (isInverted()) {
            log = axisMax - (java2DValue - min) / (max - min) 
                    * (axisMax - axisMin);
        }
        else {
            log = axisMin + (java2DValue - min) / (max - min) 
                    * (axisMax - axisMin);
        }
        return calculateValue(log);
    }

    /**
     * Converts a value on the axis scale to a Java2D coordinate relative to 
     * the given <code>area</code>, based on the axis running along the 
     * specified <code>edge</code>.
     * 
     * @param value  the data value.
     * @param area  the area.
     * @param edge  the edge.
     * 
     * @return The Java2D coordinate corresponding to <code>value</code>.
     */
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
        }
        else if (RectangleEdge.isLeftOrRight(edge)) {
            max = area.getMinY();
            min = area.getMaxY();
        }
        if (isInverted()) {
            return max 
                   - ((value - axisMin) / (axisMax - axisMin)) * (max - min);
        }
        else {
            return min 
                   + ((value - axisMin) / (axisMax - axisMin)) * (max - min);
        }
    }
    
    /**
     * Configures the axis.  This method is typically called when an axis
     * is assigned to a new plot.
     */
    public void configure() {
        if (isAutoRange()) {
            autoAdjustRange();
        }
    }

    /**
     * Adjusts the axis range to match the data range that the axis is
     * required to display.
     */
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
            double lower = r.getLowerBound();
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
//                upper = upper + getUpperMargin() * range;
//                lower = lower - getLowerMargin() * range;
            }

            setRange(new Range(lower, upper), false, false);
        }

    }

    /**
     * Draws the axis on a Java 2D graphics device (such as the screen or a 
     * printer).
     *
     * @param g2  the graphics device (<code>null</code> not permitted).
     * @param cursor  the cursor location (determines where to draw the axis).
     * @param plotArea  the area within which the axes and plot should be drawn.
     * @param dataArea  the area within which the data should be drawn.
     * @param edge  the axis location (<code>null</code> not permitted).
     * @param plotState  collects information about the plot 
     *                   (<code>null</code> permitted).
     * 
     * @return The axis state (never <code>null</code>).
     */
    public AxisState draw(Graphics2D g2, double cursor, Rectangle2D plotArea, 
            Rectangle2D dataArea, RectangleEdge edge, 
            PlotRenderingInfo plotState) {
        
        AxisState state = null;
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
        state = drawLabel(getLabel(), g2, plotArea, dataArea, edge, state);
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
     *
     */
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
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param edge  the edge.
     * 
     * @return A list of ticks.
     */
    protected List refreshTicksHorizontal(Graphics2D g2, Rectangle2D dataArea, 
            RectangleEdge edge) {
        Range range = getRange();
        List ticks = new ArrayList();
        double start = Math.floor(calculateLog(getLowerBound()));
        double end = Math.ceil(calculateLog(getUpperBound()));
        double current = start;
        while (current <= end) {
            double v = calculateValue(current);
            if (range.contains(v)) {
                ticks.add(new NumberTick(new Double(v), createTickLabel(v), 
                        TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0));
            }
            // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current 
                    + this.tickUnit.getSize());
            for (int i = 1; i < this.minorTickCount; i++) {
                double minorV = v + i * ((next - v) / this.minorTickCount);
                if (range.contains(minorV)) {
                    ticks.add(new NumberTick(new Double(minorV), 
                        "", TextAnchor.TOP_CENTER, TextAnchor.CENTER, 0.0));
                }
            }
            current = current + this.tickUnit.getSize();
        }
        return ticks;
    }
    
    /**
     * Returns a list of ticks for an axis at the left or right of the chart.
     * 
     * @param g2  the graphics device.
     * @param dataArea  the data area.
     * @param edge  the edge.
     * 
     * @return A list of ticks.
     */
    protected List refreshTicksVertical(Graphics2D g2, Rectangle2D dataArea, 
            RectangleEdge edge) {
        Range range = getRange();
        List ticks = new ArrayList();
        double start = Math.floor(calculateLog(getLowerBound()));
        double end = Math.ceil(calculateLog(getUpperBound()));
        double current = start;
        while (current <= end) {
            double v = calculateValue(current);
            if (range.contains(v)) {
                ticks.add(new NumberTick(new Double(v), createTickLabel(v), 
                        TextAnchor.CENTER_RIGHT, TextAnchor.CENTER, 0.0));
            }
            // add minor ticks (for gridlines)
            double next = Math.pow(this.base, current 
                    + this.tickUnit.getSize());
            for (int i = 1; i < this.minorTickCount; i++) {
                double minorV = v + i * ((next - v) / this.minorTickCount);
                if (range.contains(minorV)) {
                    ticks.add(new NumberTick(new Double(minorV), "", 
                            TextAnchor.CENTER_RIGHT, TextAnchor.CENTER, 0.0));
                }
            }
            current = current + this.tickUnit.getSize();
        }
        return ticks;
    }

    /**
     * Creates a tick label for the specified value.
     * 
     * @param value  the value.
     * 
     * @return The label.
     */
    private String createTickLabel(double value) {
        if (this.numberFormatOverride != null) {
            return this.numberFormatOverride.format(value);
        }
        else {
            return this.tickUnit.valueToString(value);
        }
    }

}
