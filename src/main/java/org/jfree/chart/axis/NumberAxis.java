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
 * ---------------
 * NumberAxis.java
 * ---------------
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Laurence Vanhelsuwe;
 *                   Peter Kolb (patches 1934255 and 2603321);
 *
 * Changes
 * -------
 * 18-Sep-2001 : Added standard header and fixed DOS encoding problem (DG);
 * 22-Sep-2001 : Changed setMinimumAxisValue() and setMaximumAxisValue() so
 *               that they clear the autoRange flag (DG);
 * 27-Nov-2001 : Removed old, redundant code (DG);
 * 30-Nov-2001 : Added accessor methods for the standard tick units (DG);
 * 08-Jan-2002 : Added setAxisRange() method (since renamed setRange()) (DG);
 * 16-Jan-2002 : Added setTickUnit() method.  Extended ValueAxis to support an
 *               optional cross-hair (DG);
 * 08-Feb-2002 : Fixes bug to ensure the autorange is recalculated if the
 *               setAutoRangeIncludesZero flag is changed (DG);
 * 25-Feb-2002 : Added a new flag autoRangeStickyZero to provide further
 *               control over margins in the auto-range mechanism.  Updated
 *               constructors.  Updated import statements.  Moved the
 *               createStandardTickUnits() method to the TickUnits class (DG);
 * 19-Apr-2002 : Updated Javadoc comments (DG);
 * 01-May-2002 : Updated for changes to TickUnit class, removed valueToString()
 *               method (DG);
 * 25-Jul-2002 : Moved the lower and upper margin attributes, and the
 *               auto-range minimum size, up one level to the ValueAxis
 *               class (DG);
 * 05-Sep-2002 : Updated constructor to match changes in Axis class (DG);
 * 01-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 04-Oct-2002 : Moved standardTickUnits from NumberAxis --> ValueAxis (DG);
 * 24-Oct-2002 : Added a number format override (DG);
 * 08-Nov-2002 : Moved to new package com.jrefinery.chart.axis (DG);
 * 19-Nov-2002 : Removed grid settings (now controlled by the plot) (DG);
 * 14-Jan-2003 : Changed autoRangeMinimumSize from Number --> double, and moved
 *               crosshair settings to the plot classes (DG);
 * 20-Jan-2003 : Removed the monolithic constructor (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 16-Jul-2003 : Reworked to allow for multiple secondary axes (DG);
 * 13-Aug-2003 : Implemented Cloneable (DG);
 * 07-Oct-2003 : Fixed bug (815028) in the auto range calculation (DG);
 * 29-Oct-2003 : Added workaround for font alignment in PDF output (DG);
 * 07-Nov-2003 : Modified to use NumberTick class (DG);
 * 21-Jan-2004 : Renamed translateJava2DToValue --> java2DToValue, and
 *               translateValueToJava2D --> valueToJava2D (DG);
 * 03-Mar-2004 : Added plotState to draw() method (DG);
 * 07-Apr-2004 : Changed string width calculation (DG);
 * 11-Jan-2005 : Removed deprecated methods in preparation for 1.0.0
 *               release (DG);
 * 28-Mar-2005 : Renamed autoRangeIncludesZero() --> getAutoRangeIncludesZero()
 *               and autoRangeStickyZero() --> getAutoRangeStickyZero() (DG);
 * 21-Apr-2005 : Removed redundant argument from selectAutoTickUnit() (DG);
 * 22-Apr-2005 : Renamed refreshHorizontalTicks --> refreshTicksHorizontal
 *               (and likewise the vertical version) for consistency with
 *               other axis classes (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 10-Feb-2006 : Added some API doc comments in respect of bug 821046 (DG);
 * 20-Feb-2006 : Modified equals() method to check rangeType field (fixes bug
 *               1435461) (DG);
 * 04-Sep-2006 : Fix auto range calculation for the case where all data values
 *               are constant and large (see bug report 1549218) (DG);
 * 11-Dec-2006 : Fix bug in auto-tick unit selection with tick format override,
 *               see bug 1608371 (DG);
 * 22-Mar-2007 : Use new defaultAutoRange attribute (DG);
 * 25-Sep-2008 : Added minor tick support, see patch 1934255 by Peter Kolb (DG);
 * 21-Jan-2009 : Default minor tick counts will now come from the tick unit
 *               collection (DG);
 * 19-Mar-2009 : Added entity support - see patch 2603321 by Peter Kolb (DG);
 * 02-Jul-2013 : Use ParamChecks (DG);
 * 01-Aug-2013 : Added attributedLabel override to support superscripts,
 *               subscripts and more (DG);
 * 18-Jan-2016 : Update auto-tick unit selection to work better for large 
 *               values (DG);
 * 
 */

package org.jfree.chart.axis;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.ValueAxisPlot;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.Args;
import org.jfree.data.Range;
import org.jfree.data.RangeType;

/**
 * An axis for displaying numerical data.
 * <P>
 * If the axis is set up to automatically determine its range to fit the data,
 * you can ensure that the range includes zero (statisticians usually prefer
 * this) by setting the {@code autoRangeIncludesZero} flag to
 * {@code true}.
 * <P>
 * The {@code NumberAxis} class has a mechanism for automatically
 * selecting a tick unit that is appropriate for the current axis range.
 */
public class NumberAxis extends ValueAxis implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2805933088476185789L;

    /** The default value for the autoRangeIncludesZero flag. */
    public static final boolean DEFAULT_AUTO_RANGE_INCLUDES_ZERO = true;

    /** The default value for the autoRangeStickyZero flag. */
    public static final boolean DEFAULT_AUTO_RANGE_STICKY_ZERO = true;

    /** The default tick unit. */
    public static final NumberTickUnit DEFAULT_TICK_UNIT = new NumberTickUnit(
            1.0, new DecimalFormat("0"));

    /** The default setting for the vertical tick labels flag. */
    public static final boolean DEFAULT_VERTICAL_TICK_LABELS = false;

    /**
     * The range type (can be used to force the axis to display only positive
     * values or only negative values).
     */
    private RangeType rangeType;

    /**
     * A flag that affects the axis range when the range is determined
     * automatically.  If the auto range does NOT include zero and this flag
     * is TRUE, then the range is changed to include zero.
     */
    private boolean autoRangeIncludesZero;

    /**
     * A flag that affects the size of the margins added to the axis range when
     * the range is determined automatically.  If the value 0 falls within the
     * margin and this flag is TRUE, then the margin is truncated at zero.
     */
    private boolean autoRangeStickyZero;

    /** The tick unit for the axis. */
    private NumberTickUnit tickUnit;

    /** The override number format. */
    private NumberFormat numberFormatOverride;

    /** An optional band for marking regions on the axis. */
    private MarkerAxisBand markerBand;

    /**
     * Default constructor.
     */
    public NumberAxis() {
        this(null);
    }

    /**
     * Constructs a number axis, using default values where necessary.
     *
     * @param label  the axis label ({@code null} permitted).
     */
    public NumberAxis(String label) {
        super(label, NumberAxis.createStandardTickUnits());
        this.rangeType = RangeType.FULL;
        this.autoRangeIncludesZero = DEFAULT_AUTO_RANGE_INCLUDES_ZERO;
        this.autoRangeStickyZero = DEFAULT_AUTO_RANGE_STICKY_ZERO;
        this.tickUnit = DEFAULT_TICK_UNIT;
        this.numberFormatOverride = null;
        this.markerBand = null;
    }

    /**
     * Returns the axis range type.
     *
     * @return The axis range type (never {@code null}).
     *
     * @see #setRangeType(RangeType)
     */
    public RangeType getRangeType() {
        return this.rangeType;
    }

    /**
     * Sets the axis range type.
     *
     * @param rangeType  the range type ({@code null} not permitted).
     *
     * @see #getRangeType()
     */
    public void setRangeType(RangeType rangeType) {
        Args.nullNotPermitted(rangeType, "rangeType");
        this.rangeType = rangeType;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Returns the flag that indicates whether or not the automatic axis range
     * (if indeed it is determined automatically) is forced to include zero.
     *
     * @return The flag.
     */
    public boolean getAutoRangeIncludesZero() {
        return this.autoRangeIncludesZero;
    }

    /**
     * Sets the flag that indicates whether or not the axis range, if
     * automatically calculated, is forced to include zero.
     * <p>
     * If the flag is changed to {@code true}, the axis range is
     * recalculated.
     * <p>
     * Any change to the flag will trigger an {@link AxisChangeEvent}.
     *
     * @param flag  the new value of the flag.
     *
     * @see #getAutoRangeIncludesZero()
     */
    public void setAutoRangeIncludesZero(boolean flag) {
        if (this.autoRangeIncludesZero != flag) {
            this.autoRangeIncludesZero = flag;
            if (isAutoRange()) {
                autoAdjustRange();
            }
            notifyListeners(new AxisChangeEvent(this));
        }
    }

    /**
     * Returns a flag that affects the auto-range when zero falls outside the
     * data range but inside the margins defined for the axis.
     *
     * @return The flag.
     *
     * @see #setAutoRangeStickyZero(boolean)
     */
    public boolean getAutoRangeStickyZero() {
        return this.autoRangeStickyZero;
    }

    /**
     * Sets a flag that affects the auto-range when zero falls outside the data
     * range but inside the margins defined for the axis.
     *
     * @param flag  the new flag.
     *
     * @see #getAutoRangeStickyZero()
     */
    public void setAutoRangeStickyZero(boolean flag) {
        if (this.autoRangeStickyZero != flag) {
            this.autoRangeStickyZero = flag;
            if (isAutoRange()) {
                autoAdjustRange();
            }
            notifyListeners(new AxisChangeEvent(this));
        }
    }

    /**
     * Returns the tick unit for the axis.
     * <p>
     * Note: if the {@code autoTickUnitSelection} flag is
     * {@code true} the tick unit may be changed while the axis is being
     * drawn, so in that case the return value from this method may be
     * irrelevant if the method is called before the axis has been drawn.
     *
     * @return The tick unit for the axis.
     *
     * @see #setTickUnit(NumberTickUnit)
     * @see ValueAxis#isAutoTickUnitSelection()
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
     * @see #setTickUnit(NumberTickUnit, boolean, boolean)
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
     */
    public void setTickUnit(NumberTickUnit unit, boolean notify,
            boolean turnOffAutoSelect) {

        Args.nullNotPermitted(unit, "unit");
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
     * @return The number formatter (possibly {@code null}).
     *
     * @see #setNumberFormatOverride(NumberFormat)
     */
    public NumberFormat getNumberFormatOverride() {
        return this.numberFormatOverride;
    }

    /**
     * Sets the number format override.  If this is non-null, then it will be
     * used to format the numbers on the axis.
     *
     * @param formatter  the number formatter ({@code null} permitted).
     *
     * @see #getNumberFormatOverride()
     */
    public void setNumberFormatOverride(NumberFormat formatter) {
        this.numberFormatOverride = formatter;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Returns the (optional) marker band for the axis.
     *
     * @return The marker band (possibly {@code null}).
     *
     * @see #setMarkerBand(MarkerAxisBand)
     */
    public MarkerAxisBand getMarkerBand() {
        return this.markerBand;
    }

    /**
     * Sets the marker band for the axis.
     * <P>
     * The marker band is optional, leave it set to {@code null} if you
     * don't require it.
     *
     * @param band the new band ({@code null} permitted).
     *
     * @see #getMarkerBand()
     */
    public void setMarkerBand(MarkerAxisBand band) {
        this.markerBand = band;
        notifyListeners(new AxisChangeEvent(this));
    }

    /**
     * Configures the axis to work with the specified plot.  If the axis has
     * auto-scaling, then sets the maximum and minimum values.
     */
    @Override
    public void configure() {
        if (isAutoRange()) {
            autoAdjustRange();
        }
    }

    /**
     * Rescales the axis to ensure that all data is visible.
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
            double lower = r.getLowerBound();
            if (this.rangeType == RangeType.POSITIVE) {
                lower = Math.max(0.0, lower);
                upper = Math.max(0.0, upper);
            }
            else if (this.rangeType == RangeType.NEGATIVE) {
                lower = Math.min(0.0, lower);
                upper = Math.min(0.0, upper);
            }

            if (getAutoRangeIncludesZero()) {
                lower = Math.min(lower, 0.0);
                upper = Math.max(upper, 0.0);
            }
            double range = upper - lower;

            // if fixed auto range, then derive lower bound...
            double fixedAutoRange = getFixedAutoRange();
            if (fixedAutoRange > 0.0) {
                lower = upper - fixedAutoRange;
            }
            else {
                // ensure the autorange is at least <minRange> in size...
                double minRange = getAutoRangeMinimumSize();
                if (range < minRange) {
                    double expand = (minRange - range) / 2;
                    upper = upper + expand;
                    lower = lower - expand;
                    if (lower == upper) { // see bug report 1549218
                        double adjust = Math.abs(lower) / 10.0;
                        lower = lower - adjust;
                        upper = upper + adjust;
                    }
                    if (this.rangeType == RangeType.POSITIVE) {
                        if (lower < 0.0) {
                            upper = upper - lower;
                            lower = 0.0;
                        }
                    }
                    else if (this.rangeType == RangeType.NEGATIVE) {
                        if (upper > 0.0) {
                            lower = lower - upper;
                            upper = 0.0;
                        }
                    }
                }

                if (getAutoRangeStickyZero()) {
                    if (upper <= 0.0) {
                        upper = Math.min(0.0, upper + getUpperMargin() * range);
                    }
                    else {
                        upper = upper + getUpperMargin() * range;
                    }
                    if (lower >= 0.0) {
                        lower = Math.max(0.0, lower - getLowerMargin() * range);
                    }
                    else {
                        lower = lower - getLowerMargin() * range;
                    }
                }
                else {
                    upper = upper + getUpperMargin() * range;
                    lower = lower - getLowerMargin() * range;
                }
            }

            setRange(new Range(lower, upper), false, false);
        }

    }

    /**
     * Converts a data value to a coordinate in Java2D space, assuming that the
     * axis runs along one edge of the specified dataArea.
     * <p>
     * Note that it is possible for the coordinate to fall outside the plotArea.
     *
     * @param value  the data value.
     * @param area  the area for plotting the data.
     * @param edge  the axis location.
     *
     * @return The Java2D coordinate.
     *
     * @see #java2DToValue(double, Rectangle2D, RectangleEdge)
     */
    @Override
    public double valueToJava2D(double value, Rectangle2D area,
            RectangleEdge edge) {

        Range range = getRange();
        double axisMin = range.getLowerBound();
        double axisMax = range.getUpperBound();

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
     * Converts a coordinate in Java2D space to the corresponding data value,
     * assuming that the axis runs along one edge of the specified dataArea.
     *
     * @param java2DValue  the coordinate in Java2D space.
     * @param area  the area in which the data is plotted.
     * @param edge  the location.
     *
     * @return The data value.
     *
     * @see #valueToJava2D(double, Rectangle2D, RectangleEdge)
     */
    @Override
    public double java2DToValue(double java2DValue, Rectangle2D area,
            RectangleEdge edge) {

        Range range = getRange();
        double axisMin = range.getLowerBound();
        double axisMax = range.getUpperBound();

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
        if (isInverted()) {
            return axisMax
                   - (java2DValue - min) / (max - min) * (axisMax - axisMin);
        }
        else {
            return axisMin
                   + (java2DValue - min) / (max - min) * (axisMax - axisMin);
        }

    }

    /**
     * Calculates the value of the lowest visible tick on the axis.
     *
     * @return The value of the lowest visible tick on the axis.
     *
     * @see #calculateHighestVisibleTickValue()
     */
    protected double calculateLowestVisibleTickValue() {
        double unit = getTickUnit().getSize();
        double index = Math.ceil(getRange().getLowerBound() / unit);
        return index * unit;
    }

    /**
     * Calculates the value of the highest visible tick on the axis.
     *
     * @return The value of the highest visible tick on the axis.
     *
     * @see #calculateLowestVisibleTickValue()
     */
    protected double calculateHighestVisibleTickValue() {
        double unit = getTickUnit().getSize();
        double index = Math.floor(getRange().getUpperBound() / unit);
        return index * unit;
    }

    /**
     * Calculates the number of visible ticks.
     *
     * @return The number of visible ticks on the axis.
     */
    protected int calculateVisibleTickCount() {
        double unit = getTickUnit().getSize();
        Range range = getRange();
        return (int) (Math.floor(range.getUpperBound() / unit)
                      - Math.ceil(range.getLowerBound() / unit) + 1);
    }

    /**
     * Draws the axis on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param cursor  the cursor location.
     * @param plotArea  the area within which the axes and data should be drawn
     *                  ({@code null} not permitted).
     * @param dataArea  the area within which the data should be drawn
     *                  ({@code null} not permitted).
     * @param edge  the location of the axis ({@code null} not permitted).
     * @param plotState  collects information about the plot
     *                   ({@code null} permitted).
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

        // draw the tick marks and labels...
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
     * Creates the standard tick units.
     * <P>
     * If you don't like these defaults, create your own instance of TickUnits
     * and then pass it to the setStandardTickUnits() method in the
     * NumberAxis class.
     *
     * @return The standard tick units.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     * @see #createIntegerTickUnits()
     */
    public static TickUnitSource createStandardTickUnits() {
        return new NumberTickUnitSource();
    }

    /**
     * Returns a collection of tick units for integer values.
     *
     * @return A collection of tick units for integer values.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     * @see #createStandardTickUnits()
     */
    public static TickUnitSource createIntegerTickUnits() {
        return new NumberTickUnitSource(true);
    }

    /**
     * Creates a collection of standard tick units.  The supplied locale is
     * used to create the number formatter (a localised instance of
     * {@code NumberFormat}).
     * <P>
     * If you don't like these defaults, create your own instance of
     * {@link TickUnits} and then pass it to the
     * {@code setStandardTickUnits()} method.
     *
     * @param locale  the locale.
     *
     * @return A tick unit collection.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     */
    public static TickUnitSource createStandardTickUnits(Locale locale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return new NumberTickUnitSource(false, numberFormat);
    }

    /**
     * Returns a collection of tick units for integer values.
     * Uses a given Locale to create the DecimalFormats.
     *
     * @param locale the locale to use to represent Numbers.
     *
     * @return A collection of tick units for integer values.
     *
     * @see #setStandardTickUnits(TickUnitSource)
     */
    public static TickUnitSource createIntegerTickUnits(Locale locale) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        return new NumberTickUnitSource(true, numberFormat);
    }

    /**
     * Estimates the maximum tick label height.
     *
     * @param g2  the graphics device.
     *
     * @return The maximum height.
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
            FontMetrics fm = g2.getFontMetrics(getTickLabelFont());
            Range range = getRange();
            double lower = range.getLowerBound();
            double upper = range.getUpperBound();
            String lowerStr, upperStr;
            NumberFormat formatter = getNumberFormatOverride();
            if (formatter != null) {
                lowerStr = formatter.format(lower);
                upperStr = formatter.format(upper);
            }
            else {
                lowerStr = unit.valueToString(lower);
                upperStr = unit.valueToString(upper);
            }
            double w1 = fm.stringWidth(lowerStr);
            double w2 = fm.stringWidth(upperStr);
            result += Math.max(w1, w2);
        }

        return result;

    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area defined by the axes.
     * @param edge  the axis location.
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
     */
    protected void selectHorizontalAutoTickUnit(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        TickUnit unit = getTickUnit();
        TickUnitSource tickUnitSource = getStandardTickUnits();
        // we should use the current tick unit if it gives a count in the range
        // 2 to 40 otherwise just estimate one that will give a count <= 20
        double length = getRange().getLength();
        int count = (int) (length / unit.getSize());
        if (count < 2 || count > 40) {
            unit = tickUnitSource.getCeilingTickUnit(length / 20);
        }
        double tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit);

        TickUnit unit1 = tickUnitSource.getCeilingTickUnit(unit);
        double unit1Width = lengthToJava2D(unit1.getSize(), dataArea, edge);

        // then extrapolate...
        double guess = (tickLabelWidth / unit1Width) * unit1.getSize();
        NumberTickUnit unit2 = (NumberTickUnit) 
                tickUnitSource.getCeilingTickUnit(guess);
        double unit2Width = lengthToJava2D(unit2.getSize(), dataArea, edge);

        tickLabelWidth = estimateMaximumTickLabelWidth(g2, unit2);
        if (tickLabelWidth > unit2Width) {
            unit2 = (NumberTickUnit) tickUnitSource.getLargerTickUnit(unit2);
        }
        setTickUnit(unit2, false, false);
    }

    /**
     * Selects an appropriate tick value for the axis.  The strategy is to
     * display as many ticks as possible (selected from an array of 'standard'
     * tick units) without the labels overlapping.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the axis location.
     */
    protected void selectVerticalAutoTickUnit(Graphics2D g2, 
            Rectangle2D dataArea, RectangleEdge edge) {

        double tickLabelHeight = estimateMaximumTickLabelHeight(g2);

        // start with the current tick unit...
        TickUnitSource tickUnits = getStandardTickUnits();
        TickUnit unit1 = tickUnits.getCeilingTickUnit(getTickUnit());
        double unitHeight = lengthToJava2D(unit1.getSize(), dataArea, edge);
        double guess;
        if (unitHeight > 0) { // then extrapolate...
            guess = (tickLabelHeight / unitHeight) * unit1.getSize();
        } else { 
            guess = getRange().getLength() / 20.0;
        }
        NumberTickUnit unit2 = (NumberTickUnit) tickUnits.getCeilingTickUnit(
                guess);
        double unit2Height = lengthToJava2D(unit2.getSize(), dataArea, edge);

        tickLabelHeight = estimateMaximumTickLabelHeight(g2);
        if (tickLabelHeight > unit2Height) {
            unit2 = (NumberTickUnit) tickUnits.getLargerTickUnit(unit2);
        }

        setTickUnit(unit2, false, false);

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
     * Calculates the positions of the tick labels for the axis, storing the
     * results in the tick label list (ready for drawing).
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the data should be drawn.
     * @param edge  the location of the axis.
     *
     * @return A list of ticks.
     */
    protected List refreshTicksHorizontal(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        List result = new java.util.ArrayList();

        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);

        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);
        }

        TickUnit tu = getTickUnit();
        double size = tu.getSize();
        int count = calculateVisibleTickCount();
        double lowestTickValue = calculateLowestVisibleTickValue();

        if (count <= ValueAxis.MAXIMUM_TICK_COUNT) {
            int minorTickSpaces = getMinorTickCount();
            if (minorTickSpaces <= 0) {
                minorTickSpaces = tu.getMinorTickCount();
            }
            for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
                double minorTickValue = lowestTickValue 
                        - size * minorTick / minorTickSpaces;
                if (getRange().contains(minorTickValue)) {
                    result.add(new NumberTick(TickType.MINOR, minorTickValue,
                            "", TextAnchor.TOP_CENTER, TextAnchor.CENTER,
                            0.0));
                }
            }
            for (int i = 0; i < count; i++) {
                double currentTickValue = lowestTickValue + (i * size);
                String tickLabel;
                NumberFormat formatter = getNumberFormatOverride();
                if (formatter != null) {
                    tickLabel = formatter.format(currentTickValue);
                }
                else {
                    tickLabel = getTickUnit().valueToString(currentTickValue);
                }
                TextAnchor anchor, rotationAnchor;
                double angle = 0.0;
                if (isVerticalTickLabels()) {
                    anchor = TextAnchor.CENTER_RIGHT;
                    rotationAnchor = TextAnchor.CENTER_RIGHT;
                    if (edge == RectangleEdge.TOP) {
                        angle = Math.PI / 2.0;
                    }
                    else {
                        angle = -Math.PI / 2.0;
                    }
                }
                else {
                    if (edge == RectangleEdge.TOP) {
                        anchor = TextAnchor.BOTTOM_CENTER;
                        rotationAnchor = TextAnchor.BOTTOM_CENTER;
                    }
                    else {
                        anchor = TextAnchor.TOP_CENTER;
                        rotationAnchor = TextAnchor.TOP_CENTER;
                    }
                }

                Tick tick = new NumberTick(new Double(currentTickValue),
                        tickLabel, anchor, rotationAnchor, angle);
                result.add(tick);
                double nextTickValue = lowestTickValue + ((i + 1) * size);
                for (int minorTick = 1; minorTick < minorTickSpaces;
                        minorTick++) {
                    double minorTickValue = currentTickValue
                            + (nextTickValue - currentTickValue)
                            * minorTick / minorTickSpaces;
                    if (getRange().contains(minorTickValue)) {
                        result.add(new NumberTick(TickType.MINOR,
                                minorTickValue, "", TextAnchor.TOP_CENTER,
                                TextAnchor.CENTER, 0.0));
                    }
                }
            }
        }
        return result;

    }

    /**
     * Calculates the positions of the tick labels for the axis, storing the
     * results in the tick label list (ready for drawing).
     *
     * @param g2  the graphics device.
     * @param dataArea  the area in which the plot should be drawn.
     * @param edge  the location of the axis.
     *
     * @return A list of ticks.
     */
    protected List refreshTicksVertical(Graphics2D g2,
            Rectangle2D dataArea, RectangleEdge edge) {

        List result = new java.util.ArrayList();
        result.clear();

        Font tickLabelFont = getTickLabelFont();
        g2.setFont(tickLabelFont);
        if (isAutoTickUnitSelection()) {
            selectAutoTickUnit(g2, dataArea, edge);
        }

        TickUnit tu = getTickUnit();
        double size = tu.getSize();
        int count = calculateVisibleTickCount();
        double lowestTickValue = calculateLowestVisibleTickValue();

        if (count <= ValueAxis.MAXIMUM_TICK_COUNT) {
            int minorTickSpaces = getMinorTickCount();
            if (minorTickSpaces <= 0) {
                minorTickSpaces = tu.getMinorTickCount();
            }
            for (int minorTick = 1; minorTick < minorTickSpaces; minorTick++) {
                double minorTickValue = lowestTickValue
                        - size * minorTick / minorTickSpaces;
                if (getRange().contains(minorTickValue)) {
                    result.add(new NumberTick(TickType.MINOR, minorTickValue,
                            "", TextAnchor.TOP_CENTER, TextAnchor.CENTER,
                            0.0));
                }
            }

            for (int i = 0; i < count; i++) {
                double currentTickValue = lowestTickValue + (i * size);
                String tickLabel;
                NumberFormat formatter = getNumberFormatOverride();
                if (formatter != null) {
                    tickLabel = formatter.format(currentTickValue);
                }
                else {
                    tickLabel = getTickUnit().valueToString(currentTickValue);
                }

                TextAnchor anchor;
                TextAnchor rotationAnchor;
                double angle = 0.0;
                if (isVerticalTickLabels()) {
                    if (edge == RectangleEdge.LEFT) {
                        anchor = TextAnchor.BOTTOM_CENTER;
                        rotationAnchor = TextAnchor.BOTTOM_CENTER;
                        angle = -Math.PI / 2.0;
                    }
                    else {
                        anchor = TextAnchor.BOTTOM_CENTER;
                        rotationAnchor = TextAnchor.BOTTOM_CENTER;
                        angle = Math.PI / 2.0;
                    }
                }
                else {
                    if (edge == RectangleEdge.LEFT) {
                        anchor = TextAnchor.CENTER_RIGHT;
                        rotationAnchor = TextAnchor.CENTER_RIGHT;
                    }
                    else {
                        anchor = TextAnchor.CENTER_LEFT;
                        rotationAnchor = TextAnchor.CENTER_LEFT;
                    }
                }

                Tick tick = new NumberTick(new Double(currentTickValue),
                        tickLabel, anchor, rotationAnchor, angle);
                result.add(tick);

                double nextTickValue = lowestTickValue + ((i + 1) * size);
                for (int minorTick = 1; minorTick < minorTickSpaces;
                        minorTick++) {
                    double minorTickValue = currentTickValue
                            + (nextTickValue - currentTickValue)
                            * minorTick / minorTickSpaces;
                    if (getRange().contains(minorTickValue)) {
                        result.add(new NumberTick(TickType.MINOR,
                                minorTickValue, "", TextAnchor.TOP_CENTER,
                                TextAnchor.CENTER, 0.0));
                    }
                }
            }
        }
        return result;

    }

    /**
     * Returns a clone of the axis.
     *
     * @return A clone
     *
     * @throws CloneNotSupportedException if some component of the axis does
     *         not support cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        NumberAxis clone = (NumberAxis) super.clone();
        if (this.numberFormatOverride != null) {
            clone.numberFormatOverride
                = (NumberFormat) this.numberFormatOverride.clone();
        }
        return clone;
    }

    /**
     * Tests the axis for equality with an arbitrary object.
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
        if (!(obj instanceof NumberAxis)) {
            return false;
        }
        NumberAxis that = (NumberAxis) obj;
        if (this.autoRangeIncludesZero != that.autoRangeIncludesZero) {
            return false;
        }
        if (this.autoRangeStickyZero != that.autoRangeStickyZero) {
            return false;
        }
        if (!ObjectUtils.equal(this.tickUnit, that.tickUnit)) {
            return false;
        }
        if (!ObjectUtils.equal(this.numberFormatOverride,
                that.numberFormatOverride)) {
            return false;
        }
        if (!this.rangeType.equals(that.rangeType)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code for this object.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
