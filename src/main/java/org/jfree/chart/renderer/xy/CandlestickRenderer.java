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
 * ------------------------
 * CandlestickRenderer.java
 * ------------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Authors:  David Gilbert;
 *                    Sylvain Vieujot;
 * Contributor(s):    Richard Atkinson;
 *                    Christian W. Zuckschwerdt;
 *                    Jerome Fisher;
 *
 */

package org.jfree.chart.renderer.xy;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.labels.HighLowItemLabelGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.data.Range;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.data.xy.XYDataset;

/**
 * A renderer that draws candlesticks on an {@link XYPlot} (requires a
 * {@link OHLCDataset}).  The example shown here is generated
 * by the {@code CandlestickChartDemo1.java} program included in the
 * JFreeChart demo collection:
 * <br><br>
 * <img src="doc-files/CandlestickRendererSample.png" 
 * alt="CandlestickRendererSample.png">
 * <P>
 * This renderer does not include code to calculate the crosshair point for the
 * plot.
 */
public class CandlestickRenderer extends AbstractXYItemRenderer
        implements XYItemRenderer, Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 50390395841817121L;

    /** The average width method. */
    public static final int WIDTHMETHOD_AVERAGE = 0;

    /** The smallest width method. */
    public static final int WIDTHMETHOD_SMALLEST = 1;

    /** The interval data method. */
    public static final int WIDTHMETHOD_INTERVALDATA = 2;

    /** The method of automatically calculating the candle width. */
    private int autoWidthMethod = WIDTHMETHOD_AVERAGE;

    /**
     * The number (generally between 0.0 and 1.0) by which the available space
     * automatically calculated for the candles will be multiplied to determine
     * the actual width to use.
     */
    private double autoWidthFactor = 4.5 / 7;

    /** The minimum gap between one candle and the next */
    private double autoWidthGap = 0.0;

    /** The candle width. */
    private double candleWidth;

    /** The maximum candlewidth in milliseconds. */
    private double maxCandleWidthInMilliseconds = 1000.0 * 60.0 * 60.0 * 20.0;

    /** Temporary storage for the maximum candle width. */
    private double maxCandleWidth;

    /**
     * The paint used to fill the candle when the price moved up from open to
     * close.
     */
    private transient Paint upPaint;

    /**
     * The paint used to fill the candle when the price moved down from open
     * to close.
     */
    private transient Paint downPaint;

    /** A flag controlling whether or not volume bars are drawn on the chart. */
    private boolean drawVolume;

    /**
     * The paint used to fill the volume bars (if they are visible).  Once
     * initialised, this field should never be set to {@code null}.
     */
    private transient Paint volumePaint;

    /** Temporary storage for the maximum volume. */
    private transient double maxVolume;

    /**
     * A flag that controls whether or not the renderer's outline paint is
     * used to draw the outline of the candlestick.  The default value is
     * {@code false} to avoid a change of behaviour for existing code.
     */
    private boolean useOutlinePaint;

    /**
     * Creates a new renderer for candlestick charts.
     */
    public CandlestickRenderer() {
        this(-1.0);
    }

    /**
     * Creates a new renderer for candlestick charts.
     * <P>
     * Use -1 for the candle width if you prefer the width to be calculated
     * automatically.
     *
     * @param candleWidth  The candle width.
     */
    public CandlestickRenderer(double candleWidth) {
        this(candleWidth, true, new HighLowItemLabelGenerator());
    }

    /**
     * Creates a new renderer for candlestick charts.
     * <P>
     * Use -1 for the candle width if you prefer the width to be calculated
     * automatically.
     *
     * @param candleWidth  the candle width.
     * @param drawVolume  a flag indicating whether or not volume bars should
     *                    be drawn.
     * @param toolTipGenerator  the tool tip generator. {@code null} is
     *                          none.
     */
    public CandlestickRenderer(double candleWidth, boolean drawVolume,
                               XYToolTipGenerator toolTipGenerator) {
        super();
        setDefaultToolTipGenerator(toolTipGenerator);
        this.candleWidth = candleWidth;
        this.drawVolume = drawVolume;
        this.volumePaint = Color.GRAY;
        this.upPaint = Color.GREEN;
        this.downPaint = Color.RED;
        this.useOutlinePaint = false;  // false preserves the old behaviour
                                       // prior to introducing this flag
    }

    /**
     * Returns the width of each candle.
     *
     * @return The candle width.
     *
     * @see #setCandleWidth(double)
     */
    public double getCandleWidth() {
        return this.candleWidth;
    }

    /**
     * Sets the candle width and sends a {@link RendererChangeEvent} to all
     * registered listeners.
     * <P>
     * If you set the width to a negative value, the renderer will calculate
     * the candle width automatically based on the space available on the chart.
     *
     * @param width  The width.
     * @see #setAutoWidthMethod(int)
     * @see #setAutoWidthGap(double)
     * @see #setAutoWidthFactor(double)
     * @see #setMaxCandleWidthInMilliseconds(double)
     */
    public void setCandleWidth(double width) {
        if (width != this.candleWidth) {
            this.candleWidth = width;
            fireChangeEvent();
        }
    }

    /**
     * Returns the maximum width (in milliseconds) of each candle.
     *
     * @return The maximum candle width in milliseconds.
     *
     * @see #setMaxCandleWidthInMilliseconds(double)
     */
    public double getMaxCandleWidthInMilliseconds() {
        return this.maxCandleWidthInMilliseconds;
    }

    /**
     * Sets the maximum candle width (in milliseconds) and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param millis  The maximum width.
     *
     * @see #getMaxCandleWidthInMilliseconds()
     * @see #setCandleWidth(double)
     * @see #setAutoWidthMethod(int)
     * @see #setAutoWidthGap(double)
     * @see #setAutoWidthFactor(double)
     */
    public void setMaxCandleWidthInMilliseconds(double millis) {
        this.maxCandleWidthInMilliseconds = millis;
        fireChangeEvent();
    }

    /**
     * Returns the method of automatically calculating the candle width.
     *
     * @return The method of automatically calculating the candle width.
     *
     * @see #setAutoWidthMethod(int)
     */
    public int getAutoWidthMethod() {
        return this.autoWidthMethod;
    }

    /**
     * Sets the method of automatically calculating the candle width and
     * sends a {@link RendererChangeEvent} to all registered listeners.
     * <p>
     * {@code WIDTHMETHOD_AVERAGE}: Divides the entire display (ignoring
     * scale factor) by the number of items, and uses this as the available
     * width.<br>
     * {@code WIDTHMETHOD_SMALLEST}: Checks the interval between each
     * item, and uses the smallest as the available width.<br>
     * {@code WIDTHMETHOD_INTERVALDATA}: Assumes that the dataset supports
     * the IntervalXYDataset interface, and uses the startXValue - endXValue as
     * the available width.
     * <br>
     *
     * @param autoWidthMethod  The method of automatically calculating the
     * candle width.
     *
     * @see #WIDTHMETHOD_AVERAGE
     * @see #WIDTHMETHOD_SMALLEST
     * @see #WIDTHMETHOD_INTERVALDATA
     * @see #getAutoWidthMethod()
     * @see #setCandleWidth(double)
     * @see #setAutoWidthGap(double)
     * @see #setAutoWidthFactor(double)
     * @see #setMaxCandleWidthInMilliseconds(double)
     */
    public void setAutoWidthMethod(int autoWidthMethod) {
        if (this.autoWidthMethod != autoWidthMethod) {
            this.autoWidthMethod = autoWidthMethod;
            fireChangeEvent();
        }
    }

    /**
     * Returns the factor by which the available space automatically
     * calculated for the candles will be multiplied to determine the actual
     * width to use.
     *
     * @return The width factor (generally between 0.0 and 1.0).
     *
     * @see #setAutoWidthFactor(double)
     */
    public double getAutoWidthFactor() {
        return this.autoWidthFactor;
    }

    /**
     * Sets the factor by which the available space automatically calculated
     * for the candles will be multiplied to determine the actual width to use.
     *
     * @param autoWidthFactor The width factor (generally between 0.0 and 1.0).
     *
     * @see #getAutoWidthFactor()
     * @see #setCandleWidth(double)
     * @see #setAutoWidthMethod(int)
     * @see #setAutoWidthGap(double)
     * @see #setMaxCandleWidthInMilliseconds(double)
     */
    public void setAutoWidthFactor(double autoWidthFactor) {
        if (this.autoWidthFactor != autoWidthFactor) {
            this.autoWidthFactor = autoWidthFactor;
            fireChangeEvent();
        }
    }

    /**
     * Returns the amount of space to leave on the left and right of each
     * candle when automatically calculating widths.
     *
     * @return The gap.
     *
     * @see #setAutoWidthGap(double)
     */
    public double getAutoWidthGap() {
        return this.autoWidthGap;
    }

    /**
     * Sets the amount of space to leave on the left and right of each candle
     * when automatically calculating widths and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param autoWidthGap The gap.
     *
     * @see #getAutoWidthGap()
     * @see #setCandleWidth(double)
     * @see #setAutoWidthMethod(int)
     * @see #setAutoWidthFactor(double)
     * @see #setMaxCandleWidthInMilliseconds(double)
     */
    public void setAutoWidthGap(double autoWidthGap) {
        if (this.autoWidthGap != autoWidthGap) {
            this.autoWidthGap = autoWidthGap;
            fireChangeEvent();
        }
    }

    /**
     * Returns the paint used to fill candles when the price moves up from open
     * to close.
     *
     * @return The paint (possibly {@code null}).
     *
     * @see #setUpPaint(Paint)
     */
    public Paint getUpPaint() {
        return this.upPaint;
    }

    /**
     * Sets the paint used to fill candles when the price moves up from open
     * to close and sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getUpPaint()
     */
    public void setUpPaint(Paint paint) {
        this.upPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the paint used to fill candles when the price moves down from
     * open to close.
     *
     * @return The paint (possibly {@code null}).
     *
     * @see #setDownPaint(Paint)
     */
    public Paint getDownPaint() {
        return this.downPaint;
    }

    /**
     * Sets the paint used to fill candles when the price moves down from open
     * to close and sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param paint  The paint ({@code null} permitted).
     */
    public void setDownPaint(Paint paint) {
        this.downPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns a flag indicating whether or not volume bars are drawn on the
     * chart.
     *
     * @return A boolean.
     *
     * @see #setDrawVolume(boolean)
     */
    public boolean getDrawVolume() {
        return this.drawVolume;
    }

    /**
     * Sets a flag that controls whether or not volume bars are drawn in the
     * background and sends a {@link RendererChangeEvent} to all registered
     * listeners.
     *
     * @param flag  the flag.
     *
     * @see #getDrawVolume()
     */
    public void setDrawVolume(boolean flag) {
        if (this.drawVolume != flag) {
            this.drawVolume = flag;
            fireChangeEvent();
        }
    }

    /**
     * Returns the paint that is used to fill the volume bars if they are
     * visible.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setVolumePaint(Paint)
     */
    public Paint getVolumePaint() {
        return this.volumePaint;
    }

    /**
     * Sets the paint used to fill the volume bars, and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getVolumePaint()
     * @see #getDrawVolume()
     */
    public void setVolumePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.volumePaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the flag that controls whether or not the renderer's outline
     * paint is used to draw the candlestick outline.  The default value is
     * {@code false}.
     *
     * @return A boolean.
     *
     * @see #setUseOutlinePaint(boolean)
     */
    public boolean getUseOutlinePaint() {
        return this.useOutlinePaint;
    }

    /**
     * Sets the flag that controls whether or not the renderer's outline
     * paint is used to draw the candlestick outline, and sends a
     * {@link RendererChangeEvent} to all registered listeners.
     *
     * @param use  the new flag value.
     *
     * @see #getUseOutlinePaint()
     */
    public void setUseOutlinePaint(boolean use) {
        if (this.useOutlinePaint != use) {
            this.useOutlinePaint = use;
            fireChangeEvent();
        }
    }

    /**
     * Returns the range of values the renderer requires to display all the
     * items from the specified dataset.
     *
     * @param dataset  the dataset ({@code null} permitted).
     *
     * @return The range ({@code null} if the dataset is {@code null}
     *         or empty).
     */
    @Override
    public Range findRangeBounds(XYDataset dataset) {
        return findRangeBounds(dataset, true);
    }

    /**
     * Initialises the renderer then returns the number of 'passes' through the
     * data that the renderer will require (usually just one).  This method
     * will be called before the first item is rendered, giving the renderer
     * an opportunity to initialise any state information it wants to maintain.
     * The renderer can do nothing if it chooses.
     *
     * @param g2  the graphics device.
     * @param dataArea  the area inside the axes.
     * @param plot  the plot.
     * @param dataset  the data.
     * @param info  an optional info collection object to return data back to
     *              the caller.
     *
     * @return The number of passes the renderer requires.
     */
    @Override
    public XYItemRendererState initialise(Graphics2D g2, Rectangle2D dataArea,
            XYPlot plot, XYDataset dataset, PlotRenderingInfo info) {

        // calculate the maximum allowed candle width from the axis...
        ValueAxis axis = plot.getDomainAxis();
        double x1 = axis.getLowerBound();
        double x2 = x1 + this.maxCandleWidthInMilliseconds;
        RectangleEdge edge = plot.getDomainAxisEdge();
        double xx1 = axis.valueToJava2D(x1, dataArea, edge);
        double xx2 = axis.valueToJava2D(x2, dataArea, edge);
        this.maxCandleWidth = Math.abs(xx2 - xx1);
            // Absolute value, since the relative x
            // positions are reversed for horizontal orientation

        // calculate the highest volume in the dataset...
        if (this.drawVolume) {
            OHLCDataset highLowDataset = (OHLCDataset) dataset;
            this.maxVolume = 0.0;
            for (int series = 0; series < highLowDataset.getSeriesCount();
                 series++) {
                for (int item = 0; item < highLowDataset.getItemCount(series);
                     item++) {
                    double volume = highLowDataset.getVolumeValue(series, item);
                    if (volume > this.maxVolume) {
                        this.maxVolume = volume;
                    }

                }
            }
        }

        return new XYItemRendererState(info);
    }

    /**
     * Draws the visual representation of a single data item.
     *
     * @param g2  the graphics device.
     * @param state  the renderer state.
     * @param dataArea  the area within which the plot is being drawn.
     * @param info  collects info about the drawing.
     * @param plot  the plot (can be used to obtain standard color
     *              information etc).
     * @param domainAxis  the domain axis.
     * @param rangeAxis  the range axis.
     * @param dataset  the dataset.
     * @param series  the series index (zero-based).
     * @param item  the item index (zero-based).
     * @param crosshairState  crosshair information for the plot
     *                        ({@code null} permitted).
     * @param pass  the pass index.
     */
    @Override
    public void drawItem(Graphics2D g2, XYItemRendererState state,
            Rectangle2D dataArea, PlotRenderingInfo info, XYPlot plot,
            ValueAxis domainAxis, ValueAxis rangeAxis, XYDataset dataset,
            int series, int item, CrosshairState crosshairState, int pass) {

        boolean horiz;
        PlotOrientation orientation = plot.getOrientation();
        if (orientation == PlotOrientation.HORIZONTAL) {
            horiz = true;
        }
        else if (orientation == PlotOrientation.VERTICAL) {
            horiz = false;
        }
        else {
            return;
        }

        // setup for collecting optional entity info...
        EntityCollection entities = null;
        if (info != null) {
            entities = info.getOwner().getEntityCollection();
        }

        OHLCDataset highLowData = (OHLCDataset) dataset;

        double x = highLowData.getXValue(series, item);
        double yHigh = highLowData.getHighValue(series, item);
        double yLow = highLowData.getLowValue(series, item);
        double yOpen = highLowData.getOpenValue(series, item);
        double yClose = highLowData.getCloseValue(series, item);

        RectangleEdge domainEdge = plot.getDomainAxisEdge();
        double xx = domainAxis.valueToJava2D(x, dataArea, domainEdge);

        RectangleEdge edge = plot.getRangeAxisEdge();
        double yyHigh = rangeAxis.valueToJava2D(yHigh, dataArea, edge);
        double yyLow = rangeAxis.valueToJava2D(yLow, dataArea, edge);
        double yyOpen = rangeAxis.valueToJava2D(yOpen, dataArea, edge);
        double yyClose = rangeAxis.valueToJava2D(yClose, dataArea, edge);

        double volumeWidth;
        double stickWidth;
        if (this.candleWidth > 0) {
            // These are deliberately not bounded to minimums/maxCandleWidth to
            //  retain old behaviour.
            volumeWidth = this.candleWidth;
            stickWidth = this.candleWidth;
        }
        else {
            double xxWidth = 0;
            int itemCount;
            switch (this.autoWidthMethod) {

                case WIDTHMETHOD_AVERAGE:
                    itemCount = highLowData.getItemCount(series);
                    if (horiz) {
                        xxWidth = dataArea.getHeight() / itemCount;
                    }
                    else {
                        xxWidth = dataArea.getWidth() / itemCount;
                    }
                    break;

                case WIDTHMETHOD_SMALLEST:
                    // Note: It would be nice to pre-calculate this per series
                    itemCount = highLowData.getItemCount(series);
                    double lastPos = -1;
                    xxWidth = dataArea.getWidth();
                    for (int i = 0; i < itemCount; i++) {
                        double pos = domainAxis.valueToJava2D(
                                highLowData.getXValue(series, i), dataArea,
                                domainEdge);
                        if (lastPos != -1) {
                            xxWidth = Math.min(xxWidth,
                                    Math.abs(pos - lastPos));
                        }
                        lastPos = pos;
                    }
                    break;

                case WIDTHMETHOD_INTERVALDATA:
                    IntervalXYDataset intervalXYData
                            = (IntervalXYDataset) dataset;
                    double startPos = domainAxis.valueToJava2D(
                            intervalXYData.getStartXValue(series, item),
                            dataArea, plot.getDomainAxisEdge());
                    double endPos = domainAxis.valueToJava2D(
                            intervalXYData.getEndXValue(series, item),
                            dataArea, plot.getDomainAxisEdge());
                    xxWidth = Math.abs(endPos - startPos);
                    break;

            }
            xxWidth -= 2 * this.autoWidthGap;
            xxWidth *= this.autoWidthFactor;
            xxWidth = Math.min(xxWidth, this.maxCandleWidth);
            volumeWidth = Math.max(Math.min(1, this.maxCandleWidth), xxWidth);
            stickWidth = Math.max(Math.min(3, this.maxCandleWidth), xxWidth);
        }

        Paint p = getItemPaint(series, item);
        Paint outlinePaint = null;
        if (this.useOutlinePaint) {
            outlinePaint = getItemOutlinePaint(series, item);
        }
        Stroke s = getItemStroke(series, item);

        g2.setStroke(s);

        if (this.drawVolume) {
            int volume = (int) highLowData.getVolumeValue(series, item);
            double volumeHeight = volume / this.maxVolume;

            double min, max;
            if (horiz) {
                min = dataArea.getMinX();
                max = dataArea.getMaxX();
            }
            else {
                min = dataArea.getMinY();
                max = dataArea.getMaxY();
            }

            double zzVolume = volumeHeight * (max - min);

            g2.setPaint(getVolumePaint());
            Composite originalComposite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f));

            if (horiz) {
                g2.fill(new Rectangle2D.Double(min, xx - volumeWidth / 2,
                        zzVolume, volumeWidth));
            }
            else {
                g2.fill(new Rectangle2D.Double(xx - volumeWidth / 2,
                        max - zzVolume, volumeWidth, zzVolume));
            }

            g2.setComposite(originalComposite);
        }

        if (this.useOutlinePaint) {
            g2.setPaint(outlinePaint);
        }
        else {
            g2.setPaint(p);
        }

        double yyMaxOpenClose = Math.max(yyOpen, yyClose);
        double yyMinOpenClose = Math.min(yyOpen, yyClose);
        double maxOpenClose = Math.max(yOpen, yClose);
        double minOpenClose = Math.min(yOpen, yClose);

        // draw the upper shadow
        if (yHigh > maxOpenClose) {
            if (horiz) {
                g2.draw(new Line2D.Double(yyHigh, xx, yyMaxOpenClose, xx));
            }
            else {
                g2.draw(new Line2D.Double(xx, yyHigh, xx, yyMaxOpenClose));
            }
        }

        // draw the lower shadow
        if (yLow < minOpenClose) {
            if (horiz) {
                g2.draw(new Line2D.Double(yyLow, xx, yyMinOpenClose, xx));
            }
            else {
                g2.draw(new Line2D.Double(xx, yyLow, xx, yyMinOpenClose));
            }
        }

        // draw the body
        Rectangle2D body;
        Rectangle2D hotspot;
        double length = Math.abs(yyHigh - yyLow);
        double base = Math.min(yyHigh, yyLow);
        if (horiz) {
            body = new Rectangle2D.Double(yyMinOpenClose, xx - stickWidth / 2,
                    yyMaxOpenClose - yyMinOpenClose, stickWidth);
            hotspot = new Rectangle2D.Double(base, xx - stickWidth / 2,
                    length, stickWidth);
        }
        else {
            body = new Rectangle2D.Double(xx - stickWidth / 2, yyMinOpenClose,
                    stickWidth, yyMaxOpenClose - yyMinOpenClose);
            hotspot = new Rectangle2D.Double(xx - stickWidth / 2,
                    base, stickWidth, length);
        }
        if (yClose > yOpen) {
            if (this.upPaint != null) {
                g2.setPaint(this.upPaint);
            }
            else {
                g2.setPaint(p);
            }
            g2.fill(body);
        }
        else {
            if (this.downPaint != null) {
                g2.setPaint(this.downPaint);
            }
            else {
                g2.setPaint(p);
            }
            g2.fill(body);
        }
        if (this.useOutlinePaint) {
            g2.setPaint(outlinePaint);
        }
        else {
            g2.setPaint(p);
        }
        g2.draw(body);

        // add an entity for the item...
        if (entities != null) {
            addEntity(entities, hotspot, dataset, series, item, 0.0, 0.0);
        }

    }

    /**
     * Tests this renderer for equality with another object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return {@code true} or {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CandlestickRenderer)) {
            return false;
        }
        CandlestickRenderer that = (CandlestickRenderer) obj;
        if (this.candleWidth != that.candleWidth) {
            return false;
        }
        if (!PaintUtils.equal(this.upPaint, that.upPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.downPaint, that.downPaint)) {
            return false;
        }
        if (this.drawVolume != that.drawVolume) {
            return false;
        }
        if (this.maxCandleWidthInMilliseconds
                != that.maxCandleWidthInMilliseconds) {
            return false;
        }
        if (this.autoWidthMethod != that.autoWidthMethod) {
            return false;
        }
        if (this.autoWidthFactor != that.autoWidthFactor) {
            return false;
        }
        if (this.autoWidthGap != that.autoWidthGap) {
            return false;
        }
        if (this.useOutlinePaint != that.useOutlinePaint) {
            return false;
        }
        if (!PaintUtils.equal(this.volumePaint, that.volumePaint)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a clone of the renderer.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the renderer cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.upPaint, stream);
        SerialUtils.writePaint(this.downPaint, stream);
        SerialUtils.writePaint(this.volumePaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.upPaint = SerialUtils.readPaint(stream);
        this.downPaint = SerialUtils.readPaint(stream);
        this.volumePaint = SerialUtils.readPaint(stream);
    }

}
