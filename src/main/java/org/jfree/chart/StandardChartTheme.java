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
 * StandardChartTheme.java
 * -----------------------
 * (C) Copyright 2008-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.PeriodAxis;
import org.jfree.chart.axis.PeriodAxisLabelInfo;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.Block;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.LabelBlock;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.CombinedRangeCategoryPlot;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.DrawingSupplier;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.MeterPlot;
import org.jfree.chart.plot.pie.MultiplePiePlot;
import org.jfree.chart.plot.pie.PieLabelLinkStyle;
import org.jfree.chart.plot.pie.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PolarPlot;
import org.jfree.chart.plot.SpiderWebPlot;
import org.jfree.chart.plot.ThermometerPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.GradientBarPainter;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
import org.jfree.chart.renderer.category.StatisticalBarRenderer;
import org.jfree.chart.renderer.xy.GradientXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.chart.legend.LegendTitle;
import org.jfree.chart.legend.PaintScaleLegend;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.chart.util.ShadowGenerator;

/**
 * A default implementation of the {@link ChartTheme} interface.  This
 * implementation just collects a whole bunch of chart attributes and mimics
 * the manual process of applying each attribute to the right sub-object
 * within the JFreeChart instance.  It's not elegant code, but it works.
 */
public class StandardChartTheme implements ChartTheme, Cloneable,
        PublicCloneable, Serializable {

    /** The name of this theme. */
    private final String name;

    /**
     * The largest font size.  Use for the main chart title.
     */
    private Font extraLargeFont;

    /**
     * A large font.  Used for subtitles.
     */
    private Font largeFont;

    /**
     * The regular font size.  Used for axis tick labels, legend items etc.
     */
    private Font regularFont;

    /**
     * The small font size.
     */
    private Font smallFont;

    /** The paint used to display the main chart title. */
    private transient Paint titlePaint;

    /** The paint used to display subtitles. */
    private transient Paint subtitlePaint;

    /** The background paint for the chart. */
    private transient Paint chartBackgroundPaint;

    /** The legend background paint. */
    private transient Paint legendBackgroundPaint;

    /** The legend item paint. */
    private transient Paint legendItemPaint;

    /** The drawing supplier. */
    private DrawingSupplier drawingSupplier;

    /** The background paint for the plot. */
    private transient Paint plotBackgroundPaint;

    /** The plot outline paint. */
    private transient Paint plotOutlinePaint;

    /** The label link style for pie charts. */
    private PieLabelLinkStyle labelLinkStyle;

    /** The label link paint for pie charts. */
    private transient Paint labelLinkPaint;

    /** The domain grid line paint. */
    private transient Paint domainGridlinePaint;

    /** The range grid line paint. */
    private transient Paint rangeGridlinePaint;

    /**
     * The baseline paint (used for domain and range zero baselines)
     */
    private transient Paint baselinePaint;

    /** The crosshair paint. */
    private transient Paint crosshairPaint;

    /** The axis offsets. */
    private RectangleInsets axisOffset;

    /** The axis label paint. */
    private transient Paint axisLabelPaint;

    /** The tick label paint. */
    private transient Paint tickLabelPaint;

    /** The item label paint. */
    private transient Paint itemLabelPaint;

    /**
     * A flag that controls whether or not shadows are visible (for example,
     * in a bar renderer).
     */
    private boolean shadowVisible;

    /** The shadow paint. */
    private transient Paint shadowPaint;

    /** The bar painter. */
    private BarPainter barPainter;

    /** The XY bar painter. */
    private XYBarPainter xyBarPainter;

    /** The thermometer paint. */
    private transient Paint thermometerPaint;

    /** The error indicator paint for the {@link StatisticalBarRenderer}. */
    private transient Paint errorIndicatorPaint;

    /** The grid band paint for a {@link SymbolAxis}. */
    private transient Paint gridBandPaint = SymbolAxis.DEFAULT_GRID_BAND_PAINT;

    /** The grid band alternate paint for a {@link SymbolAxis}. */
    private transient Paint gridBandAlternatePaint
            = SymbolAxis.DEFAULT_GRID_BAND_ALTERNATE_PAINT;

    /**
     * The shadow generator (can be null).
     */
    private ShadowGenerator shadowGenerator;

    /**
     * Creates and returns the default 'JFree' chart theme.
     *
     * @return A chart theme.
     */
    public static ChartTheme createJFreeTheme() {
        return new StandardChartTheme("JFree");
    }

    /**
     * Creates and returns a theme called "Darkness".  In this theme, the
     * charts have a black background.
     *
     * @return The "Darkness" theme.
     */
    public static ChartTheme createDarknessTheme() {
        StandardChartTheme theme = new StandardChartTheme("Darkness");
        theme.titlePaint = Color.WHITE;
        theme.subtitlePaint = Color.WHITE;
        theme.legendBackgroundPaint = Color.BLACK;
        theme.legendItemPaint = Color.WHITE;
        theme.chartBackgroundPaint = Color.BLACK;
        theme.plotBackgroundPaint = Color.BLACK;
        theme.plotOutlinePaint = Color.YELLOW;
        theme.baselinePaint = Color.WHITE;
        theme.crosshairPaint = Color.RED;
        theme.labelLinkPaint = Color.LIGHT_GRAY;
        theme.tickLabelPaint = Color.WHITE;
        theme.axisLabelPaint = Color.WHITE;
        theme.shadowPaint = Color.DARK_GRAY;
        theme.itemLabelPaint = Color.WHITE;
        theme.drawingSupplier = new DefaultDrawingSupplier(
                new Paint[] {Color.decode("0xFFFF00"),
                        Color.decode("0x0036CC"), Color.decode("0xFF0000"),
                        Color.decode("0xFFFF7F"), Color.decode("0x6681CC"),
                        Color.decode("0xFF7F7F"), Color.decode("0xFFFFBF"),
                        Color.decode("0x99A6CC"), Color.decode("0xFFBFBF"),
                        Color.decode("0xA9A938"), Color.decode("0x2D4587")},
                new Paint[] {Color.decode("0xFFFF00"),
                        Color.decode("0x0036CC")},
                new Stroke[] {new BasicStroke(2.0f)},
                new Stroke[] {new BasicStroke(0.5f)},
                DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
        theme.errorIndicatorPaint = Color.LIGHT_GRAY;
        theme.gridBandPaint = new Color(255, 255, 255, 20);
        theme.gridBandAlternatePaint = new Color(255, 255, 255, 40);
        theme.shadowGenerator = null;
        return theme;
    }

    /**
     * Creates and returns a {@link ChartTheme} that doesn't apply any changes
     * to the JFreeChart defaults.  This produces the "legacy" look for
     * JFreeChart.
     *
     * @return A legacy theme.
     */
    public static ChartTheme createLegacyTheme() {
        StandardChartTheme theme = new StandardChartTheme("Legacy") {
            @Override
            public void apply(JFreeChart chart) {
                // do nothing at all
            }
        };
        return theme;
    }

    /**
     * Creates a new default instance.
     *
     * @param name  the name of the theme ({@code null} not permitted).
     */
    public StandardChartTheme(String name) {
        this(name, false);
    }

    /**
     * Creates a new default instance.
     *
     * @param name  the name of the theme ({@code null} not permitted).
     * @param shadow  a flag that controls whether a shadow generator is 
     *                included.
     */
    public StandardChartTheme(String name, boolean shadow) {
        Args.nullNotPermitted(name, "name");
        this.name = name;
        this.extraLargeFont = new Font("Tahoma", Font.BOLD, 20);
        this.largeFont = new Font("Tahoma", Font.BOLD, 14);
        this.regularFont = new Font("Tahoma", Font.PLAIN, 12);
        this.smallFont = new Font("Tahoma", Font.PLAIN, 10);
        this.titlePaint = Color.BLACK;
        this.subtitlePaint = Color.BLACK;
        this.legendBackgroundPaint = Color.WHITE;
        this.legendItemPaint = Color.DARK_GRAY;
        this.chartBackgroundPaint = Color.WHITE;
        this.drawingSupplier = new DefaultDrawingSupplier();
        this.plotBackgroundPaint = Color.LIGHT_GRAY;
        this.plotOutlinePaint = Color.BLACK;
        this.labelLinkPaint = Color.BLACK;
        this.labelLinkStyle = PieLabelLinkStyle.CUBIC_CURVE;
        this.axisOffset = new RectangleInsets(4, 4, 4, 4);
        this.domainGridlinePaint = Color.WHITE;
        this.rangeGridlinePaint = Color.WHITE;
        this.baselinePaint = Color.BLACK;
        this.crosshairPaint = Color.BLUE;
        this.axisLabelPaint = Color.DARK_GRAY;
        this.tickLabelPaint = Color.DARK_GRAY;
        this.barPainter = new GradientBarPainter();
        this.xyBarPainter = new GradientXYBarPainter();
        this.shadowVisible = false;
        this.shadowPaint = Color.GRAY;
        this.itemLabelPaint = Color.BLACK;
        this.thermometerPaint = Color.WHITE;
        this.errorIndicatorPaint = Color.BLACK;
        this.shadowGenerator = shadow ? new DefaultShadowGenerator() : null;
    }

    /**
     * Returns the largest font for this theme.
     *
     * @return The largest font for this theme.
     *
     * @see #setExtraLargeFont(Font)
     */
    public Font getExtraLargeFont() {
        return this.extraLargeFont;
    }

    /**
     * Sets the largest font for this theme.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getExtraLargeFont()
     */
    public void setExtraLargeFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.extraLargeFont = font;
    }

    /**
     * Returns the large font for this theme.
     *
     * @return The large font (never {@code null}).
     *
     * @see #setLargeFont(Font)
     */
    public Font getLargeFont() {
        return this.largeFont;
    }

    /**
     * Sets the large font for this theme.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getLargeFont()
     */
    public void setLargeFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.largeFont = font;
    }

    /**
     * Returns the regular font.
     *
     * @return The regular font (never {@code null}).
     *
     * @see #setRegularFont(Font)
     */
    public Font getRegularFont() {
        return this.regularFont;
    }

    /**
     * Sets the regular font for this theme.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getRegularFont()
     */
    public void setRegularFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.regularFont = font;
    }

    /**
     * Returns the small font.
     *
     * @return The small font (never {@code null}).
     *
     * @see #setSmallFont(Font)
     */
    public Font getSmallFont() {
        return this.smallFont;
    }

    /**
     * Sets the small font for this theme.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getSmallFont()
     */
    public void setSmallFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.smallFont = font;
    }

    /**
     * Returns the title paint.
     *
     * @return The title paint (never {@code null}).
     *
     * @see #setTitlePaint(Paint)
     */
    public Paint getTitlePaint() {
        return this.titlePaint;
    }

    /**
     * Sets the title paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getTitlePaint()
     */
    public void setTitlePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.titlePaint = paint;
    }

    /**
     * Returns the subtitle paint.
     *
     * @return The subtitle paint (never {@code null}).
     *
     * @see #setSubtitlePaint(Paint)
     */
    public Paint getSubtitlePaint() {
        return this.subtitlePaint;
    }

    /**
     * Sets the subtitle paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getSubtitlePaint()
     */
    public void setSubtitlePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.subtitlePaint = paint;
    }

    /**
     * Returns the chart background paint.
     *
     * @return The chart background paint (never {@code null}).
     *
     * @see #setChartBackgroundPaint(Paint)
     */
    public Paint getChartBackgroundPaint() {
        return this.chartBackgroundPaint;
    }

    /**
     * Sets the chart background paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getChartBackgroundPaint()
     */
    public void setChartBackgroundPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.chartBackgroundPaint = paint;
    }

    /**
     * Returns the legend background paint.
     *
     * @return The legend background paint (never {@code null}).
     *
     * @see #setLegendBackgroundPaint(Paint)
     */
    public Paint getLegendBackgroundPaint() {
        return this.legendBackgroundPaint;
    }

    /**
     * Sets the legend background paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLegendBackgroundPaint()
     */
    public void setLegendBackgroundPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.legendBackgroundPaint = paint;
    }

    /**
     * Returns the legend item paint.
     *
     * @return The legend item paint (never {@code null}).
     *
     * @see #setLegendItemPaint(Paint)
     */
    public Paint getLegendItemPaint() {
        return this.legendItemPaint;
    }

    /**
     * Sets the legend item paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLegendItemPaint()
     */
    public void setLegendItemPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.legendItemPaint = paint;
    }

    /**
     * Returns the plot background paint.
     *
     * @return The plot background paint (never {@code null}).
     *
     * @see #setPlotBackgroundPaint(Paint)
     */
    public Paint getPlotBackgroundPaint() {
        return this.plotBackgroundPaint;
    }

    /**
     * Sets the plot background paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPlotBackgroundPaint()
     */
    public void setPlotBackgroundPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.plotBackgroundPaint = paint;
    }

    /**
     * Returns the plot outline paint.
     *
     * @return The plot outline paint (never {@code null}).
     *
     * @see #setPlotOutlinePaint(Paint)
     */
    public Paint getPlotOutlinePaint() {
        return this.plotOutlinePaint;
    }

    /**
     * Sets the plot outline paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPlotOutlinePaint()
     */
    public void setPlotOutlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.plotOutlinePaint = paint;
    }

    /**
     * Returns the label link style for pie charts.
     *
     * @return The label link style (never {@code null}).
     *
     * @see #setLabelLinkStyle(PieLabelLinkStyle)
     */
    public PieLabelLinkStyle getLabelLinkStyle() {
        return this.labelLinkStyle;
    }

    /**
     * Sets the label link style for pie charts.
     *
     * @param style  the style ({@code null} not permitted).
     *
     * @see #getLabelLinkStyle()
     */
    public void setLabelLinkStyle(PieLabelLinkStyle style) {
        Args.nullNotPermitted(style, "style");
        this.labelLinkStyle = style;
    }

    /**
     * Returns the label link paint for pie charts.
     *
     * @return The label link paint (never {@code null}).
     *
     * @see #setLabelLinkPaint(Paint)
     */
    public Paint getLabelLinkPaint() {
        return this.labelLinkPaint;
    }

    /**
     * Sets the label link paint for pie charts.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLabelLinkPaint()
     */
    public void setLabelLinkPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.labelLinkPaint = paint;
    }

    /**
     * Returns the domain grid line paint.
     *
     * @return The domain grid line paint (never {@code null}).
     *
     * @see #setDomainGridlinePaint(Paint)
     */
    public Paint getDomainGridlinePaint() {
        return this.domainGridlinePaint;
    }

    /**
     * Sets the domain grid line paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getDomainGridlinePaint()
     */
    public void setDomainGridlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.domainGridlinePaint = paint;
    }

    /**
     * Returns the range grid line paint.
     *
     * @return The range grid line paint (never {@code null}).
     *
     * @see #setRangeGridlinePaint(Paint)
     */
    public Paint getRangeGridlinePaint() {
        return this.rangeGridlinePaint;
    }

    /**
     * Sets the range grid line paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getRangeGridlinePaint()
     */
    public void setRangeGridlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.rangeGridlinePaint = paint;
    }

    /**
     * Returns the baseline paint.
     *
     * @return The baseline paint.
     */
    public Paint getBaselinePaint() {
        return this.baselinePaint;
    }

    /**
     * Sets the baseline paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setBaselinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.baselinePaint = paint;
    }

    /**
     * Returns the crosshair paint.
     *
     * @return The crosshair paint.
     */
    public Paint getCrosshairPaint() {
        return this.crosshairPaint;
    }

    /**
     * Sets the crosshair paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setCrosshairPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.crosshairPaint = paint;
    }

    /**
     * Returns the axis offsets.
     *
     * @return The axis offsets (never {@code null}).
     *
     * @see #setAxisOffset(RectangleInsets)
     */
    public RectangleInsets getAxisOffset() {
        return this.axisOffset;
    }

    /**
     * Sets the axis offset.
     *
     * @param offset  the offset ({@code null} not permitted).
     *
     * @see #getAxisOffset()
     */
    public void setAxisOffset(RectangleInsets offset) {
        Args.nullNotPermitted(offset, "offset");
        this.axisOffset = offset;
    }

    /**
     * Returns the axis label paint.
     *
     * @return The axis label paint (never {@code null}).
     *
     * @see #setAxisLabelPaint(Paint)
     */
    public Paint getAxisLabelPaint() {
        return this.axisLabelPaint;
    }

    /**
     * Sets the axis label paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getAxisLabelPaint()
     */
    public void setAxisLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.axisLabelPaint = paint;
    }

    /**
     * Returns the tick label paint.
     *
     * @return The tick label paint (never {@code null}).
     *
     * @see #setTickLabelPaint(Paint)
     */
    public Paint getTickLabelPaint() {
        return this.tickLabelPaint;
    }

    /**
     * Sets the tick label paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getTickLabelPaint()
     */
    public void setTickLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.tickLabelPaint = paint;
    }

    /**
     * Returns the item label paint.
     *
     * @return The item label paint (never {@code null}).
     *
     * @see #setItemLabelPaint(Paint)
     */
    public Paint getItemLabelPaint() {
        return this.itemLabelPaint;
    }

    /**
     * Sets the item label paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getItemLabelPaint()
     */
    public void setItemLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.itemLabelPaint = paint;
    }

    /**
     * Returns the shadow visibility flag.
     *
     * @return The shadow visibility flag.
     *
     * @see #setShadowVisible(boolean)
     */
    public boolean isShadowVisible() {
        return this.shadowVisible;
    }

    /**
     * Sets the shadow visibility flag.
     *
     * @param visible  the flag.
     *
     * @see #isShadowVisible()
     */
    public void setShadowVisible(boolean visible) {
        this.shadowVisible = visible;
    }

    /**
     * Returns the shadow paint.
     *
     * @return The shadow paint (never {@code null}).
     *
     * @see #setShadowPaint(Paint)
     */
    public Paint getShadowPaint() {
        return this.shadowPaint;
    }

    /**
     * Sets the shadow paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getShadowPaint()
     */
    public void setShadowPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.shadowPaint = paint;
    }

    /**
     * Returns the bar painter.
     *
     * @return The bar painter (never {@code null}).
     *
     * @see #setBarPainter(BarPainter)
     */
    public BarPainter getBarPainter() {
        return this.barPainter;
    }

    /**
     * Sets the bar painter.
     *
     * @param painter  the painter ({@code null} not permitted).
     *
     * @see #getBarPainter()
     */
    public void setBarPainter(BarPainter painter) {
        Args.nullNotPermitted(painter, "painter");
        this.barPainter = painter;
    }

    /**
     * Returns the XY bar painter.
     *
     * @return The XY bar painter (never {@code null}).
     *
     * @see #setXYBarPainter(XYBarPainter)
     */
    public XYBarPainter getXYBarPainter() {
        return this.xyBarPainter;
    }

    /**
     * Sets the XY bar painter.
     *
     * @param painter  the painter ({@code null} not permitted).
     *
     * @see #getXYBarPainter()
     */
    public void setXYBarPainter(XYBarPainter painter) {
        Args.nullNotPermitted(painter, "painter");
        this.xyBarPainter = painter;
    }

    /**
     * Returns the thermometer paint.
     *
     * @return The thermometer paint (never {@code null}).
     *
     * @see #setThermometerPaint(Paint)
     */
    public Paint getThermometerPaint() {
        return this.thermometerPaint;
    }

    /**
     * Sets the thermometer paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getThermometerPaint()
     */
    public void setThermometerPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.thermometerPaint = paint;
    }

    /**
     * Returns the error indicator paint.
     *
     * @return The error indicator paint (never {@code null}).
     *
     * @see #setErrorIndicatorPaint(Paint)
     */
    public Paint getErrorIndicatorPaint() {
        return this.errorIndicatorPaint;
    }

    /**
     * Sets the error indicator paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getErrorIndicatorPaint()
     */
    public void setErrorIndicatorPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.errorIndicatorPaint = paint;
    }

    /**
     * Returns the grid band paint.
     *
     * @return The grid band paint (never {@code null}).
     *
     * @see #setGridBandPaint(Paint)
     */
    public Paint getGridBandPaint() {
        return this.gridBandPaint;
    }

    /**
     * Sets the grid band paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getGridBandPaint()
     */
    public void setGridBandPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.gridBandPaint = paint;
    }

    /**
     * Returns the grid band alternate paint (used for a {@link SymbolAxis}).
     *
     * @return The paint (never {@code null}).
     *
     * @see #setGridBandAlternatePaint(Paint)
     */
    public Paint getGridBandAlternatePaint() {
        return this.gridBandAlternatePaint;
    }

    /**
     * Sets the grid band alternate paint (used for a {@link SymbolAxis}).
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getGridBandAlternatePaint()
     */
    public void setGridBandAlternatePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.gridBandAlternatePaint = paint;
    }

    /**
     * Returns the name of this theme.
     *
     * @return The name of this theme.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a clone of the drawing supplier for this theme.
     *
     * @return A clone of the drawing supplier.
     */
    public DrawingSupplier getDrawingSupplier() {
        DrawingSupplier result = null;
        if (this.drawingSupplier instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.drawingSupplier;
              try {
                result = (DrawingSupplier) pc.clone();
            }
            catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * Sets the drawing supplier for this theme.
     *
     * @param supplier  the supplier ({@code null} not permitted).
     *
     * @see #getDrawingSupplier()
     */
    public void setDrawingSupplier(DrawingSupplier supplier) {
        Args.nullNotPermitted(supplier, "supplier");
        this.drawingSupplier = supplier;
    }

    /**
     * Applies this theme to the supplied chart.
     *
     * @param chart  the chart ({@code null} not permitted).
     */
    @Override
    public void apply(JFreeChart chart) {
        Args.nullNotPermitted(chart, "chart");
        TextTitle title = chart.getTitle();
        if (title != null) {
            title.setFont(this.extraLargeFont);
            title.setPaint(this.titlePaint);
        }

        int subtitleCount = chart.getSubtitleCount();
        for (int i = 0; i < subtitleCount; i++) {
            applyToTitle(chart.getSubtitle(i));
        }

        chart.setBackgroundPaint(this.chartBackgroundPaint);

        // now process the plot if there is one
        Plot plot = chart.getPlot();
        if (plot != null) {
            applyToPlot(plot);
        }
    }

    /**
     * Applies the attributes of this theme to the specified title.
     *
     * @param title  the title.
     */
    protected void applyToTitle(Title title) {
        if (title instanceof TextTitle) {
            TextTitle tt = (TextTitle) title;
            tt.setFont(this.largeFont);
            tt.setPaint(this.subtitlePaint);
        }
        else if (title instanceof LegendTitle) {
            LegendTitle lt = (LegendTitle) title;
            if (lt.getBackgroundPaint() != null) {
                lt.setBackgroundPaint(this.legendBackgroundPaint);
            }
            lt.setItemFont(this.regularFont);
            lt.setItemPaint(this.legendItemPaint);
            if (lt.getWrapper() != null) {
                applyToBlockContainer(lt.getWrapper());
            }
        }
        else if (title instanceof PaintScaleLegend) {
            PaintScaleLegend psl = (PaintScaleLegend) title;
            psl.setBackgroundPaint(this.legendBackgroundPaint);
            ValueAxis axis = psl.getAxis();
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }
        else if (title instanceof CompositeTitle) {
            CompositeTitle ct = (CompositeTitle) title;
            BlockContainer bc = ct.getContainer();
            for (Block b: bc.getBlocks()) {
                if (b instanceof Title) {
                    applyToTitle((Title) b);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to the specified container.
     *
     * @param bc  a block container ({@code null} not permitted).
     */
    protected void applyToBlockContainer(BlockContainer bc) {
        for (Block b : bc.getBlocks()) {
            applyToBlock(b);
        }
    }

    /**
     * Applies the attributes of this theme to the specified block.
     *
     * @param b  the block.
     */
    protected void applyToBlock(Block b) {
        if (b instanceof Title) {
            applyToTitle((Title) b);
        }
        else if (b instanceof LabelBlock) {
            LabelBlock lb = (LabelBlock) b;
            lb.setFont(this.regularFont);
            lb.setPaint(this.legendItemPaint);
        }
    }

    /**
     * Applies the attributes of this theme to a plot.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToPlot(Plot plot) {
        Args.nullNotPermitted(plot, "plot");
        if (plot.getDrawingSupplier() != null) {
            plot.setDrawingSupplier(getDrawingSupplier());
        }
        if (plot.getBackgroundPaint() != null) {
            plot.setBackgroundPaint(this.plotBackgroundPaint);
        }
        plot.setOutlinePaint(this.plotOutlinePaint);

        // now handle specific plot types (and yes, I know this is some
        // really ugly code that has to be manually updated any time a new
        // plot type is added - I should have written something much cooler,
        // but I didn't and neither did anyone else).
        if (plot instanceof PiePlot) {
            applyToPiePlot((PiePlot) plot);
        }
        else if (plot instanceof MultiplePiePlot) {
            applyToMultiplePiePlot((MultiplePiePlot) plot);
        }
        else if (plot instanceof CategoryPlot) {
            applyToCategoryPlot((CategoryPlot) plot);
        }
        else if (plot instanceof XYPlot) {
            applyToXYPlot((XYPlot) plot);
        }
        else if (plot instanceof FastScatterPlot) {
            applyToFastScatterPlot((FastScatterPlot) plot);
        }
        else if (plot instanceof MeterPlot) {
            applyToMeterPlot((MeterPlot) plot);
        }
        else if (plot instanceof ThermometerPlot) {
            applyToThermometerPlot((ThermometerPlot) plot);
        }
        else if (plot instanceof SpiderWebPlot) {
            applyToSpiderWebPlot((SpiderWebPlot) plot);
        }
        else if (plot instanceof PolarPlot) {
            applyToPolarPlot((PolarPlot) plot);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link PiePlot} instance.
     * This method also clears any set values for the section paint, outline
     * etc, so that the theme's {@link DrawingSupplier} will be used.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToPiePlot(PiePlot plot) {
        plot.setLabelLinkPaint(this.labelLinkPaint);
        plot.setLabelLinkStyle(this.labelLinkStyle);
        plot.setLabelFont(this.regularFont);
        plot.setShadowGenerator(this.shadowGenerator);

        // clear the section attributes so that the theme's DrawingSupplier
        // will be used
        if (plot.getAutoPopulateSectionPaint()) {
            plot.clearSectionPaints(false);
        }
        if (plot.getAutoPopulateSectionOutlinePaint()) {
            plot.clearSectionOutlinePaints(false);
        }
        if (plot.getAutoPopulateSectionOutlineStroke()) {
            plot.clearSectionOutlineStrokes(false);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link MultiplePiePlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToMultiplePiePlot(MultiplePiePlot plot) {
        apply(plot.getPieChart());
    }

    /**
     * Applies the attributes of this theme to a {@link CategoryPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToCategoryPlot(CategoryPlot plot) {
        plot.setAxisOffset(this.axisOffset);
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        plot.setRangeZeroBaselinePaint(this.baselinePaint);
        plot.setShadowGenerator(this.shadowGenerator);

        // process all domain axes
        int domainAxisCount = plot.getDomainAxisCount();
        for (int i = 0; i < domainAxisCount; i++) {
            CategoryAxis axis = plot.getDomainAxis(i);
            if (axis != null) {
                applyToCategoryAxis(axis);
            }
        }

        // process all range axes
        int rangeAxisCount = plot.getRangeAxisCount();
        for (int i = 0; i < rangeAxisCount; i++) {
            ValueAxis axis = plot.getRangeAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all renderers
        int rendererCount = plot.getRendererCount();
        for (int i = 0; i < rendererCount; i++) {
            CategoryItemRenderer r = plot.getRenderer(i);
            if (r != null) {
                applyToCategoryItemRenderer(r);
            }
        }

        if (plot instanceof CombinedDomainCategoryPlot) {
            CombinedDomainCategoryPlot cp = (CombinedDomainCategoryPlot) plot;
            for (CategoryPlot subplot : cp.getSubplots()) {
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
        if (plot instanceof CombinedRangeCategoryPlot) {
            CombinedRangeCategoryPlot cp = (CombinedRangeCategoryPlot) plot;
            for (CategoryPlot subplot : cp.getSubplots()) {
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to a {@link XYPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     * 
     * @param <S> the type for the series keys.
     */
    protected <S extends Comparable<S>> void applyToXYPlot(XYPlot<S> plot) {
        plot.setAxisOffset(this.axisOffset);
        plot.setDomainZeroBaselinePaint(this.baselinePaint);
        plot.setRangeZeroBaselinePaint(this.baselinePaint);
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        plot.setDomainCrosshairPaint(this.crosshairPaint);
        plot.setRangeCrosshairPaint(this.crosshairPaint);
        plot.setShadowGenerator(this.shadowGenerator);

        // process all domain axes
        int domainAxisCount = plot.getDomainAxisCount();
        for (int i = 0; i < domainAxisCount; i++) {
            ValueAxis axis = plot.getDomainAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all range axes
        int rangeAxisCount = plot.getRangeAxisCount();
        for (int i = 0; i < rangeAxisCount; i++) {
            ValueAxis axis = plot.getRangeAxis(i);
            if (axis != null) {
                applyToValueAxis(axis);
            }
        }

        // process all renderers
        int rendererCount = plot.getRendererCount();
        for (int i = 0; i < rendererCount; i++) {
            XYItemRenderer r = plot.getRenderer(i);
            if (r != null) {
                applyToXYItemRenderer(r);
            }
        }
        // process all annotations

        for (XYAnnotation a : plot.getAnnotations()) {
            applyToXYAnnotation(a);
        }

        if (plot instanceof CombinedDomainXYPlot) {
            CombinedDomainXYPlot<S> cp = (CombinedDomainXYPlot) plot;
            for (XYPlot<S> subplot : cp.getSubplots()) {
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
        if (plot instanceof CombinedRangeXYPlot) {
            CombinedRangeXYPlot<S> cp = (CombinedRangeXYPlot) plot;
            for (XYPlot subplot : cp.getSubplots()) {
                if (subplot != null) {
                    applyToPlot(subplot);
                }
            }
        }
    }

    /**
     * Applies the attributes of this theme to a {@link FastScatterPlot}.
     * 
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToFastScatterPlot(FastScatterPlot plot) {
        plot.setDomainGridlinePaint(this.domainGridlinePaint);
        plot.setRangeGridlinePaint(this.rangeGridlinePaint);
        ValueAxis xAxis = plot.getDomainAxis();
        if (xAxis != null) {
            applyToValueAxis(xAxis);
        }
        ValueAxis yAxis = plot.getRangeAxis();
        if (yAxis != null) {
            applyToValueAxis(yAxis);
        }

    }

    /**
     * Applies the attributes of this theme to a {@link PolarPlot}.  This
     * method is called from the {@link #applyToPlot(Plot)} method.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToPolarPlot(PolarPlot plot) {
        plot.setAngleLabelFont(this.regularFont);
        plot.setAngleLabelPaint(this.tickLabelPaint);
        plot.setAngleGridlinePaint(this.domainGridlinePaint);
        plot.setRadiusGridlinePaint(this.rangeGridlinePaint);
        ValueAxis axis = plot.getAxis();
        if (axis != null) {
            applyToValueAxis(axis);
        }
    }

    /**
     * Applies the attributes of this theme to a {@link SpiderWebPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToSpiderWebPlot(SpiderWebPlot plot) {
        plot.setLabelFont(this.regularFont);
        plot.setLabelPaint(this.axisLabelPaint);
        plot.setAxisLinePaint(this.axisLabelPaint);
    }

    /**
     * Applies the attributes of this theme to a {@link MeterPlot}.
     *
     * @param plot  the plot ({@code null} not permitted).
     */
    protected void applyToMeterPlot(MeterPlot plot) {
        plot.setDialBackgroundPaint(this.plotBackgroundPaint);
        plot.setValueFont(this.largeFont);
        plot.setValuePaint(this.axisLabelPaint);
        plot.setDialOutlinePaint(this.plotOutlinePaint);
        plot.setNeedlePaint(this.thermometerPaint);
        plot.setTickLabelFont(this.regularFont);
        plot.setTickLabelPaint(this.tickLabelPaint);
    }

    /**
     * Applies the attributes for this theme to a {@link ThermometerPlot}.
     * This method is called from the {@link #applyToPlot(Plot)} method.
     *
     * @param plot  the plot.
     */
    protected void applyToThermometerPlot(ThermometerPlot plot) {
        plot.setValueFont(this.largeFont);
        plot.setThermometerPaint(this.thermometerPaint);
        ValueAxis axis = plot.getRangeAxis();
        if (axis != null) {
            applyToValueAxis(axis);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link CategoryAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToCategoryAxis(CategoryAxis axis) {
        axis.setLabelFont(this.largeFont);
        axis.setLabelPaint(this.axisLabelPaint);
        axis.setTickLabelFont(this.regularFont);
        axis.setTickLabelPaint(this.tickLabelPaint);
        if (axis instanceof SubCategoryAxis) {
            SubCategoryAxis sca = (SubCategoryAxis) axis;
            sca.setSubLabelFont(this.regularFont);
            sca.setSubLabelPaint(this.tickLabelPaint);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link ValueAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToValueAxis(ValueAxis axis) {
        axis.setLabelFont(this.largeFont);
        axis.setLabelPaint(this.axisLabelPaint);
        axis.setTickLabelFont(this.regularFont);
        axis.setTickLabelPaint(this.tickLabelPaint);
        if (axis instanceof SymbolAxis) {
            applyToSymbolAxis((SymbolAxis) axis);
        }
        if (axis instanceof PeriodAxis) {
            applyToPeriodAxis((PeriodAxis) axis);
        }
    }

    /**
     * Applies the attributes for this theme to a {@link SymbolAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToSymbolAxis(SymbolAxis axis) {
        axis.setGridBandPaint(this.gridBandPaint);
        axis.setGridBandAlternatePaint(this.gridBandAlternatePaint);
    }

    /**
     * Applies the attributes for this theme to a {@link PeriodAxis}.
     *
     * @param axis  the axis ({@code null} not permitted).
     */
    protected void applyToPeriodAxis(PeriodAxis axis) {
        PeriodAxisLabelInfo[] info = axis.getLabelInfo();
        for (int i = 0; i < info.length; i++) {
            PeriodAxisLabelInfo e = info[i];
            PeriodAxisLabelInfo n = new PeriodAxisLabelInfo(e.getPeriodClass(),
                    e.getDateFormat(), e.getPadding(), this.regularFont,
                    this.tickLabelPaint, e.getDrawDividers(),
                    e.getDividerStroke(), e.getDividerPaint());
            info[i] = n;
        }
        axis.setLabelInfo(info);
    }

    /**
     * Applies the attributes for this theme to an {@link AbstractRenderer}.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToAbstractRenderer(AbstractRenderer renderer) {
        if (renderer.getAutoPopulateSeriesPaint()) {
            renderer.clearSeriesPaints(false);
        }
        if (renderer.getAutoPopulateSeriesStroke()) {
            renderer.clearSeriesStrokes(false);
        }
    }

    /**
     * Applies the settings of this theme to the specified renderer.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToCategoryItemRenderer(CategoryItemRenderer renderer) {
        Args.nullNotPermitted(renderer, "renderer");

        if (renderer instanceof AbstractRenderer) {
            applyToAbstractRenderer((AbstractRenderer) renderer);
        }

        renderer.setDefaultItemLabelFont(this.regularFont);
        renderer.setDefaultItemLabelPaint(this.itemLabelPaint);

        // now we handle some special cases - yes, UGLY code alert!

        // BarRenderer
        if (renderer instanceof BarRenderer) {
            BarRenderer br = (BarRenderer) renderer;
            br.setBarPainter(this.barPainter);
            br.setShadowVisible(this.shadowVisible);
            br.setShadowPaint(this.shadowPaint);
        }


        //  StatisticalBarRenderer
        if (renderer instanceof StatisticalBarRenderer) {
            StatisticalBarRenderer sbr = (StatisticalBarRenderer) renderer;
            sbr.setErrorIndicatorPaint(this.errorIndicatorPaint);
        }

        // MinMaxCategoryRenderer
        if (renderer instanceof MinMaxCategoryRenderer) {
            MinMaxCategoryRenderer mmcr = (MinMaxCategoryRenderer) renderer;
            mmcr.setGroupPaint(this.errorIndicatorPaint);
        }
    }

    /**
     * Applies the settings of this theme to the specified renderer.
     *
     * @param renderer  the renderer ({@code null} not permitted).
     */
    protected void applyToXYItemRenderer(XYItemRenderer renderer) {
        Args.nullNotPermitted(renderer, "renderer");
        if (renderer instanceof AbstractRenderer) {
            applyToAbstractRenderer((AbstractRenderer) renderer);
        }
        renderer.setDefaultItemLabelFont(this.regularFont);
        renderer.setDefaultItemLabelPaint(this.itemLabelPaint);
        if (renderer instanceof XYBarRenderer) {
            XYBarRenderer br = (XYBarRenderer) renderer;
            br.setBarPainter(this.xyBarPainter);
            br.setShadowVisible(this.shadowVisible);
        }
    }

    /**
     * Applies the settings of this theme to the specified annotation.
     *
     * @param annotation  the annotation.
     */
    protected void applyToXYAnnotation(XYAnnotation annotation) {
        Args.nullNotPermitted(annotation, "annotation");
        if (annotation instanceof XYTextAnnotation) {
            XYTextAnnotation xyta = (XYTextAnnotation) annotation;
            xyta.setFont(this.smallFont);
            xyta.setPaint(this.itemLabelPaint);
        }
    }

    /**
     * Tests this theme for equality with an arbitrary object.
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
        if (!(obj instanceof StandardChartTheme)) {
            return false;
        }
        StandardChartTheme that = (StandardChartTheme) obj;
        if (!this.name.equals(that.name)) {
            return false;
        }
        if (!this.extraLargeFont.equals(that.extraLargeFont)) {
            return false;
        }
        if (!this.largeFont.equals(that.largeFont)) {
            return false;
        }
        if (!this.regularFont.equals(that.regularFont)) {
            return false;
        }
        if (!this.smallFont.equals(that.smallFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.titlePaint, that.titlePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.subtitlePaint, that.subtitlePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.chartBackgroundPaint,
                that.chartBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.legendBackgroundPaint,
                that.legendBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.legendItemPaint, that.legendItemPaint)) {
            return false;
        }
        if (!this.drawingSupplier.equals(that.drawingSupplier)) {
            return false;
        }
        if (!PaintUtils.equal(this.plotBackgroundPaint,
                that.plotBackgroundPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.plotOutlinePaint,
                that.plotOutlinePaint)) {
            return false;
        }
        if (!this.labelLinkStyle.equals(that.labelLinkStyle)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelLinkPaint, that.labelLinkPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.domainGridlinePaint,
                that.domainGridlinePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.rangeGridlinePaint,
                that.rangeGridlinePaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.crosshairPaint, that.crosshairPaint)) {
            return false;
        }
        if (!this.axisOffset.equals(that.axisOffset)) {
            return false;
        }
        if (!PaintUtils.equal(this.axisLabelPaint, that.axisLabelPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.tickLabelPaint, that.tickLabelPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.itemLabelPaint, that.itemLabelPaint)) {
            return false;
        }
        if (this.shadowVisible != that.shadowVisible) {
            return false;
        }
        if (!PaintUtils.equal(this.shadowPaint, that.shadowPaint)) {
            return false;
        }
        if (!this.barPainter.equals(that.barPainter)) {
            return false;
        }
        if (!this.xyBarPainter.equals(that.xyBarPainter)) {
            return false;
        }
        if (!PaintUtils.equal(this.thermometerPaint,
                that.thermometerPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.errorIndicatorPaint,
                that.errorIndicatorPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.gridBandPaint, that.gridBandPaint)) {
            return false;
        }
        if (!PaintUtils.equal(this.gridBandAlternatePaint,
                that.gridBandAlternatePaint)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.extraLargeFont);
        hash = 71 * hash + Objects.hashCode(this.largeFont);
        hash = 71 * hash + Objects.hashCode(this.regularFont);
        hash = 71 * hash + Objects.hashCode(this.smallFont);
        hash = 71 * hash + Objects.hashCode(this.titlePaint);
        hash = 71 * hash + Objects.hashCode(this.subtitlePaint);
        hash = 71 * hash + Objects.hashCode(this.chartBackgroundPaint);
        hash = 71 * hash + Objects.hashCode(this.legendBackgroundPaint);
        hash = 71 * hash + Objects.hashCode(this.legendItemPaint);
        hash = 71 * hash + Objects.hashCode(this.plotBackgroundPaint);
        hash = 71 * hash + Objects.hashCode(this.plotOutlinePaint);
        hash = 71 * hash + Objects.hashCode(this.labelLinkStyle);
        hash = 71 * hash + Objects.hashCode(this.labelLinkPaint);
        hash = 71 * hash + Objects.hashCode(this.domainGridlinePaint);
        hash = 71 * hash + Objects.hashCode(this.rangeGridlinePaint);
        hash = 71 * hash + Objects.hashCode(this.crosshairPaint);
        hash = 71 * hash + Objects.hashCode(this.axisOffset);
        hash = 71 * hash + Objects.hashCode(this.axisLabelPaint);
        hash = 71 * hash + Objects.hashCode(this.tickLabelPaint);
        hash = 71 * hash + Objects.hashCode(this.itemLabelPaint);
        hash = 71 * hash + (this.shadowVisible ? 1 : 0);
        hash = 71 * hash + Objects.hashCode(this.shadowPaint);
        hash = 71 * hash + Objects.hashCode(this.barPainter);
        hash = 71 * hash + Objects.hashCode(this.xyBarPainter);
        hash = 71 * hash + Objects.hashCode(this.thermometerPaint);
        hash = 71 * hash + Objects.hashCode(this.errorIndicatorPaint);
        hash = 71 * hash + Objects.hashCode(this.gridBandPaint);
        hash = 71 * hash + Objects.hashCode(this.gridBandAlternatePaint);
        return hash;
    }

    /**
     * Returns a clone of this theme.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the theme cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.titlePaint, stream);
        SerialUtils.writePaint(this.subtitlePaint, stream);
        SerialUtils.writePaint(this.chartBackgroundPaint, stream);
        SerialUtils.writePaint(this.legendBackgroundPaint, stream);
        SerialUtils.writePaint(this.legendItemPaint, stream);
        SerialUtils.writePaint(this.plotBackgroundPaint, stream);
        SerialUtils.writePaint(this.plotOutlinePaint, stream);
        SerialUtils.writePaint(this.labelLinkPaint, stream);
        SerialUtils.writePaint(this.baselinePaint, stream);
        SerialUtils.writePaint(this.domainGridlinePaint, stream);
        SerialUtils.writePaint(this.rangeGridlinePaint, stream);
        SerialUtils.writePaint(this.crosshairPaint, stream);
        SerialUtils.writePaint(this.axisLabelPaint, stream);
        SerialUtils.writePaint(this.tickLabelPaint, stream);
        SerialUtils.writePaint(this.itemLabelPaint, stream);
        SerialUtils.writePaint(this.shadowPaint, stream);
        SerialUtils.writePaint(this.thermometerPaint, stream);
        SerialUtils.writePaint(this.errorIndicatorPaint, stream);
        SerialUtils.writePaint(this.gridBandPaint, stream);
        SerialUtils.writePaint(this.gridBandAlternatePaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.titlePaint = SerialUtils.readPaint(stream);
        this.subtitlePaint = SerialUtils.readPaint(stream);
        this.chartBackgroundPaint = SerialUtils.readPaint(stream);
        this.legendBackgroundPaint = SerialUtils.readPaint(stream);
        this.legendItemPaint = SerialUtils.readPaint(stream);
        this.plotBackgroundPaint = SerialUtils.readPaint(stream);
        this.plotOutlinePaint = SerialUtils.readPaint(stream);
        this.labelLinkPaint = SerialUtils.readPaint(stream);
        this.baselinePaint = SerialUtils.readPaint(stream);
        this.domainGridlinePaint = SerialUtils.readPaint(stream);
        this.rangeGridlinePaint = SerialUtils.readPaint(stream);
        this.crosshairPaint = SerialUtils.readPaint(stream);
        this.axisLabelPaint = SerialUtils.readPaint(stream);
        this.tickLabelPaint = SerialUtils.readPaint(stream);
        this.itemLabelPaint = SerialUtils.readPaint(stream);
        this.shadowPaint = SerialUtils.readPaint(stream);
        this.thermometerPaint = SerialUtils.readPaint(stream);
        this.errorIndicatorPaint = SerialUtils.readPaint(stream);
        this.gridBandPaint = SerialUtils.readPaint(stream);
        this.gridBandAlternatePaint = SerialUtils.readPaint(stream);
    }

}
