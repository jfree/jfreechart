/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * ---------------------
 * XYMeasurementAnnotation.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.annotations;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.jfree.chart.HashUtils;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.MovableChartEntity;
import org.jfree.chart.entity.XYAnnotationEntity;
import org.jfree.chart.entity.XYMeasurementAnnotationEntity;
import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.chart.util.SerialUtils;

/**
 * An annotation to illustrate a measurement between two points on an XYPlot.
 */
public class XYMeasurementAnnotation extends XYTextAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    private static final long serialVersionUID = -3774229610407585254L;

    /** Specifies how to draw this annotation. */
    public enum Orientation {
        /** Draw this annotation horizontally. */
        HORIZONTAL,
        /** Draw this annotation vertically. */
        VERTICAL
    };

    /** The default label offset (in Java2D units). */
    public static final double DEFAULT_LABEL_OFFSET = 30.0;

    /** The default arrow length (in Java2D units). */
    public static final double DEFAULT_ARROW_LENGTH = 10.0;

    /** The default arrow width (in Java2D units). */
    public static final double DEFAULT_ARROW_WIDTH = 5.0;

    /** The annotation orientation */
    private Orientation orientation;

    /** The second x-coordinate. */
    private double x2;

    /** The second y-coordinate. */
    private double y2;

    /** The length of the arrow head (in Java2D units). */
    private double arrowLength;

    /** The arrow width (in Java2D units, per side). */
    private double arrowWidth;

    /** The arrow stroke. */
    private transient Stroke arrowStroke;

    /** The arrow paint. */
    private transient Paint arrowPaint;

    /** The measure line stroke. */
    private transient Stroke measureLineStroke;

    /** The measure line paint. */
    private transient Paint measureLinePaint;

    /** The radius from the base point to the anchor point for the label. */
    private double labelOffset;

    /**
     * If {@code true}, always creates an entity, regardless of tooltip or url
     */
    private boolean baseCreateEntity;

    /**
     * Creates a new annotation to be displayed at the given coordinates. The
     * coordinates are specified in data space (they will be converted to Java2D
     * space for display).
     *
     * @param orientation the orientation to use when drawing this annotation.
     * @param text        the text ({@code null} not permitted).
     * @param p1          the first coordinate (in data space).
     * @param p2          the second coordinate (in data space).
     */
    public XYMeasurementAnnotation(Orientation orientation, String text,
            Point2D p1, Point2D p2) {
        this(orientation, text, p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Creates a new annotation to be displayed at the given coordinates. The
     * coordinates are specified in data space (they will be converted to Java2D
     * space for display).
     *
     * @param orientation the orientation to use when drawing this annotation.
     * @param text        the text ({@code null} not permitted).
     * @param x1          the first x-coordinate (in data space).
     * @param y1          the first y-coordinate (in data space).
     * @param x2          the second x-coordinate (in data space).
     * @param y2          the second y-coordinate (in data space).
     */
    public XYMeasurementAnnotation(Orientation orientation, String text,
            double x1, double y1, double x2, double y2) {
        super(text, x1, y1);
        this.orientation = orientation;
        this.x2 = x2;
        this.y2 = y2;
        this.arrowLength = DEFAULT_ARROW_LENGTH;
        this.arrowWidth = DEFAULT_ARROW_WIDTH;
        this.labelOffset = DEFAULT_LABEL_OFFSET;
        this.arrowStroke = new BasicStroke(1.0f);
        this.arrowPaint = Color.BLACK;
        this.measureLineStroke = new BasicStroke(2.0f);
        this.measureLinePaint = Color.BLACK;
        this.baseCreateEntity = true;

        setPaint(Color.RED);
        setFont(getFont().deriveFont(Font.BOLD));
        setBackgroundPaint(Color.WHITE);
        setOutlineVisible(true);
        setOutlinePaint(Color.DARK_GRAY);
    }

    /**
     * Returns how this annotation is drawn.
     * 
     * @return how this annotation is drawn
     * @see #setOrientation(Orientation)
     * @see #drawHorizontal(Graphics2D, XYPlot, Rectangle2D, ValueAxis,
     *      ValueAxis, int, PlotRenderingInfo)
     * @see #drawVertical(Graphics2D, XYPlot, Rectangle2D, ValueAxis, ValueAxis,
     *      int, PlotRenderingInfo)
     */
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Sets the orientation to use when drawing this annotation.
     * 
     * @param orientation the orientation to use when drawing this annotation.
     * @see #getOrientation()
     */
    public void setOrientation(Orientation orientation) {
        Args.nullNotPermitted(orientation, "orientation");
        this.orientation = orientation;
        fireAnnotationChanged();
    }

    /**
     * Returns the x2 coordinate for the text anchor point (measured against the
     * domain axis).
     *
     * @return The x2 coordinate (in data space).
     *
     * @see #setX2(double)
     */
    public double getX2() {
        return this.x2;
    }

    /**
     * Sets the x2 coordinate for the text anchor point (measured against the
     * domain axis) and sends an {@link AnnotationChangeEvent} to all registered
     * listeners.
     *
     * @param x the x2 coordinate (in data space).
     *
     * @see #getX()
     */
    public void setX2(double x) {
        this.x2 = x;
        fireAnnotationChanged();
    }

    /**
     * Returns the y2 coordinate for the text anchor point (measured against the
     * range axis).
     *
     * @return The y2 coordinate (in data space).
     *
     * @see #setY2(double)
     */
    public double getY2() {
        return this.y2;
    }

    /**
     * Sets the y2 coordinate for the text anchor point (measured against the
     * range axis) and sends an {@link AnnotationChangeEvent} to all registered
     * listeners.
     *
     * @param y the y2 coordinate.
     *
     * @see #getY2()
     */
    public void setY2(double y) {
        this.y2 = y;
        fireAnnotationChanged();
    }

    /**
     * Returns the label offset.
     *
     * @return The label offset (in Java2D units).
     *
     * @see #setLabelOffset(double)
     */
    public double getLabelOffset() {
        return this.labelOffset;
    }

    /**
     * Sets the label offset (from the arrow base, continuing in a straight
     * line, in Java2D units) and sends an {@link AnnotationChangeEvent} to all
     * registered listeners.
     *
     * @param offset the offset (in Java2D units).
     *
     * @see #getLabelOffset()
     */
    public void setLabelOffset(double offset) {
        this.labelOffset = offset;
        fireAnnotationChanged();
    }

    /**
     * Returns the arrow length.
     *
     * @return The arrow length.
     *
     * @see #setArrowLength(double)
     */
    public double getArrowLength() {
        return this.arrowLength;
    }

    /**
     * Sets the arrow length and sends an {@link AnnotationChangeEvent} to all
     * registered listeners.
     *
     * @param length the length.
     *
     * @see #getArrowLength()
     */
    public void setArrowLength(double length) {
        this.arrowLength = length;
        fireAnnotationChanged();
    }

    /**
     * Returns the arrow width.
     *
     * @return The arrow width (in Java2D units).
     *
     * @see #setArrowWidth(double)
     */
    public double getArrowWidth() {
        return this.arrowWidth;
    }

    /**
     * Sets the arrow width and sends an {@link AnnotationChangeEvent} to all
     * registered listeners.
     *
     * @param width the width (in Java2D units).
     *
     * @see #getArrowWidth()
     */
    public void setArrowWidth(double width) {
        this.arrowWidth = width;
        fireAnnotationChanged();
    }

    /**
     * Returns the stroke used to draw the arrow line.
     *
     * @return The arrow stroke (never {@code null}).
     *
     * @see #setArrowStroke(Stroke)
     */
    public Stroke getArrowStroke() {
        return this.arrowStroke;
    }

    /**
     * Sets the stroke used to draw the arrow line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     *
     * @see #getArrowStroke()
     */
    public void setArrowStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.arrowStroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint used for the arrow.
     *
     * @return The arrow paint (never {@code null}).
     *
     * @see #setArrowPaint(Paint)
     */
    public Paint getArrowPaint() {
        return this.arrowPaint;
    }

    /**
     * Sets the paint used for the arrow and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint the arrow paint ({@code null} not permitted).
     *
     * @see #getArrowPaint()
     */
    public void setArrowPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.arrowPaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns the stroke used to draw the measure line.
     *
     * @return The measure line stroke (never {@code null}).
     *
     * @see #setMeasureLineStroke(Stroke)
     */
    public Stroke getMeasureLineStroke() {
        return measureLineStroke;
    }

    /**
     * Sets the stroke used to draw the measure line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param stroke the stroke ({@code null} not permitted).
     *
     * @see #getMeasureLineStroke()
     */
    public void setMeasureLineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.measureLineStroke = stroke;
        fireAnnotationChanged();
    }

    /**
     * Returns the paint used for the measure line.
     *
     * @return The measure line paint (never {@code null}).
     *
     * @see #setMeasureLinePaint(Paint)
     */
    public Paint getMeasureLinePaint() {
        return measureLinePaint;
    }

    /**
     * Sets the paint used for the measure line and sends an
     * {@link AnnotationChangeEvent} to all registered listeners.
     *
     * @param paint the measure line paint ({@code null} not permitted).
     *
     * @see #getMeasureLinePaint()
     */
    public void setMeasureLinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.measureLinePaint = paint;
        fireAnnotationChanged();
    }

    /**
     * Returns {@code true} (is default) if a chart entity is always created.
     * This is required to enable the chart entity to be
     * {@link MovableChartEntity movable}.
     * 
     * @return {@code true} if a chart entity is always created, {@code false}
     *         otherwise.
     */
    public boolean isBaseCreateEntity() {
        return baseCreateEntity;
    }

    /**
     * If {@code true}, always creates an entity, regardless of tooltip or url.
     * 
     * @param createEntity {@code true} if a chart entity needs to be created at
     *                     all times.
     * @see #isBaseCreateEntity()
     */
    public void setBaseCreateEntity(boolean createEntity) {
        this.baseCreateEntity = createEntity;
        fireAnnotationChanged();
    }

    /**
     * Returns {@code true} if a chart entity should be created, {@code false}
     *         otherwise.
     * 
     * @return {@code true} if a chart entity should be created, {@code false}
     *         otherwise.
     */
    protected boolean isCreateEntity() {
        return isBaseCreateEntity() || getToolTipText() != null
                || getURL() != null;
    }

    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
            ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex,
            PlotRenderingInfo info) {
        if (orientation == Orientation.HORIZONTAL) {
            drawHorizontal(g2, plot, dataArea, domainAxis, rangeAxis,
                    rendererIndex, info);
        } else {
            drawVertical(g2, plot, dataArea, domainAxis, rangeAxis,
                    rendererIndex, info);
        }
    }

    /**
     * Draws the annotation horizontally.
     *
     * @param g2            the graphics device.
     * @param plot          the plot.
     * @param dataArea      the data area.
     * @param domainAxis    the domain axis.
     * @param rangeAxis     the range axis.
     * @param rendererIndex the renderer index.
     * @param info          if supplied, this info object will be populated with
     *                      entity information.
     */
    protected void drawHorizontal(Graphics2D g2, XYPlot plot,
            Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis,
            int rendererIndex, PlotRenderingInfo info) {
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);
        double j2DX1 = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
        double j2DY1 = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
        double j2DX2 = domainAxis.valueToJava2D(getX2(), dataArea, domainEdge);
        double j2DY2 = rangeAxis.valueToJava2D(getY2(), dataArea, rangeEdge);
        if (orientation == PlotOrientation.HORIZONTAL) {
            double temp = j2DX1;
            j2DX1 = j2DY1;
            j2DY1 = temp;

            temp = j2DX2;
            j2DX2 = j2DY2;
            j2DY2 = temp;
        }

        double j2DXleft = Math.min(j2DX1, j2DX2);
        double j2DXright = Math.max(j2DX1, j2DX2);
        double labelX = (j2DXleft + j2DXright) / 2;
        double labelY = j2DY1 - this.labelOffset;

        g2.setStroke(this.measureLineStroke);
        g2.setPaint(this.measureLinePaint);
        g2.draw(new Line2D.Double(j2DX1,
                Math.min(labelY - this.arrowWidth, j2DY1), j2DX1,
                Math.max(labelY + this.arrowWidth, j2DY1)));
        g2.draw(new Line2D.Double(j2DX2,
                Math.min(labelY - this.arrowWidth, j2DY2), j2DX2,
                Math.max(labelY + this.arrowWidth, j2DY2)));

        String text = getText();
        TextAnchor textAnchor = getTextAnchor();
        g2.setFont(getFont());
        g2.setStroke(this.arrowStroke);
        g2.setPaint(this.arrowPaint);
        Shape hotspot = TextUtils.calculateRotatedStringBounds(text, g2,
                (float) labelX, (float) labelY, textAnchor, getRotationAngle(),
                getRotationAnchor());

        if (hotspot.getBounds2D().getWidth() > j2DXright - j2DXleft
                - 3 * this.arrowLength) {
            // The text doesn't fit within the measurement, so place it to the
            // right like
            // ->|--|<- text
            // | |
            labelX = j2DXright + this.arrowLength * 2;
            textAnchor = TextAnchor.CENTER_LEFT;

            hotspot = TextUtils.calculateRotatedStringBounds(text, g2,
                    (float) labelX, (float) labelY, textAnchor,
                    getRotationAngle(), getRotationAnchor());

            g2.draw(new Line2D.Double(j2DXleft - this.arrowLength * 2, labelY,
                    j2DXright + this.arrowLength * 2, labelY));

            GeneralPath arrowLeft = new GeneralPath();
            arrowLeft.moveTo((float) j2DXleft, (float) labelY);
            arrowLeft.lineTo((float) j2DXleft - this.arrowLength,
                    (float) labelY - this.arrowWidth);
            arrowLeft.lineTo((float) j2DXleft - this.arrowLength,
                    (float) labelY + this.arrowWidth);
            arrowLeft.closePath();
            g2.fill(arrowLeft);

            GeneralPath arrowRight = new GeneralPath();
            arrowRight.moveTo((float) j2DXright, (float) labelY);
            arrowRight.lineTo((float) j2DXright + this.arrowLength,
                    (float) labelY - this.arrowWidth);
            arrowRight.lineTo((float) j2DXright + this.arrowLength,
                    (float) labelY + this.arrowWidth);
            arrowRight.closePath();
            g2.fill(arrowRight);
        } else {
            // The text fits, so draw like
            // |<--text-->|
            // | |
            g2.draw(new Line2D.Double(j2DXleft, labelY, j2DXright, labelY));

            GeneralPath arrowLeft = new GeneralPath();
            arrowLeft.moveTo((float) j2DXleft, (float) labelY);
            arrowLeft.lineTo((float) j2DXleft + this.arrowLength,
                    (float) labelY - this.arrowWidth);
            arrowLeft.lineTo((float) j2DXleft + this.arrowLength,
                    (float) labelY + this.arrowWidth);
            arrowLeft.closePath();
            g2.fill(arrowLeft);

            GeneralPath arrowRight = new GeneralPath();
            arrowRight.moveTo((float) j2DXright, (float) labelY);
            arrowRight.lineTo((float) j2DXright - this.arrowLength,
                    (float) labelY - this.arrowWidth);
            arrowRight.lineTo((float) j2DXright - this.arrowLength,
                    (float) labelY + this.arrowWidth);
            arrowRight.closePath();
            g2.fill(arrowRight);
        }

        if (text.isEmpty()) {
            return;
        }

        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(hotspot);
        }
        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, (float) labelX,
                (float) labelY, textAnchor, getRotationAngle(),
                getRotationAnchor());
        if (isOutlineVisible()) {
            g2.setStroke(getOutlineStroke());
            g2.setPaint(getOutlinePaint());
            g2.draw(hotspot);
        }

        if (isCreateEntity()) {
            addEntity(info, hotspot, rendererIndex, getToolTipText(), getURL());
        }
    }

    /**
     * Draws the annotation vertically.
     *
     * @param g2            the graphics device.
     * @param plot          the plot.
     * @param dataArea      the data area.
     * @param domainAxis    the domain axis.
     * @param rangeAxis     the range axis.
     * @param rendererIndex the renderer index.
     * @param info          if supplied, this info object will be populated with
     *                      entity information.
     */
    protected void drawVertical(Graphics2D g2, XYPlot plot,
            Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis,
            int rendererIndex, PlotRenderingInfo info) {
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);
        double j2DX1 = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
        double j2DY1 = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
        double j2DX2 = domainAxis.valueToJava2D(getX2(), dataArea, domainEdge);
        double j2DY2 = rangeAxis.valueToJava2D(getY2(), dataArea, rangeEdge);
        if (orientation == PlotOrientation.HORIZONTAL) {
            double temp = j2DX1;
            j2DX1 = j2DY1;
            j2DY1 = temp;

            temp = j2DX2;
            j2DX2 = j2DY2;
            j2DY2 = temp;
        }

        double j2DYtop = Math.min(j2DY1, j2DY2);
        double j2DYbottom = Math.max(j2DY1, j2DY2);
        double labelY = (j2DYtop + j2DYbottom) / 2;
        double labelX = j2DX1 + this.labelOffset;

        g2.setStroke(this.measureLineStroke);
        g2.setPaint(this.measureLinePaint);
        g2.draw(new Line2D.Double(Math.min(labelX - this.arrowWidth, j2DX1),
                j2DY1, Math.max(labelX + this.arrowWidth, j2DX1), j2DY1));
        g2.draw(new Line2D.Double(Math.min(labelX - this.arrowWidth, j2DX2),
                j2DY2, Math.max(labelX + this.arrowWidth, j2DX2), j2DY2));

        String text = getText();
        TextAnchor textAnchor = getTextAnchor();
        g2.setFont(getFont());
        g2.setStroke(this.arrowStroke);
        g2.setPaint(this.arrowPaint);
        Shape hotspot = TextUtils.calculateRotatedStringBounds(text, g2,
                (float) labelX, (float) labelY, textAnchor, getRotationAngle(),
                getRotationAnchor());

        if (hotspot.getBounds2D().getHeight() > j2DYbottom - j2DYtop
                - 3 * this.arrowLength) {
            // The text doesn't fit within the measurement, so place it at the
            // top like
            // text
            // |
            // v
            // ----
            // |
            // ----
            // ^
            // |
            labelY = j2DYtop - this.arrowLength * 2;
            textAnchor = TextAnchor.BOTTOM_CENTER;

            hotspot = TextUtils.calculateRotatedStringBounds(text, g2,
                    (float) labelX, (float) labelY, textAnchor,
                    getRotationAngle(), getRotationAnchor());

            g2.draw(new Line2D.Double(labelX, j2DYtop - this.arrowLength * 2,
                    labelX, j2DYbottom + this.arrowLength * 2));

            GeneralPath arrowTop = new GeneralPath();
            arrowTop.moveTo((float) labelX, (float) j2DYtop);
            arrowTop.lineTo((float) labelX - this.arrowWidth,
                    (float) j2DYtop - this.arrowLength);
            arrowTop.lineTo((float) labelX + this.arrowWidth,
                    (float) j2DYtop - this.arrowLength);
            arrowTop.closePath();
            g2.fill(arrowTop);

            GeneralPath arrowBottom = new GeneralPath();
            arrowBottom.moveTo((float) labelX, (float) j2DYbottom);
            arrowBottom.lineTo((float) labelX - this.arrowWidth,
                    (float) j2DYbottom + this.arrowLength);
            arrowBottom.lineTo((float) labelX + this.arrowWidth,
                    (float) j2DYbottom + this.arrowLength);
            arrowBottom.closePath();
            g2.fill(arrowBottom);
        } else {
            // The text fits, so draw like
            // ----
            // ^
            // |
            // text
            // |
            // v
            // ----
            g2.draw(new Line2D.Double(labelX, j2DYtop, labelX, j2DYbottom));

            GeneralPath arrowTop = new GeneralPath();
            arrowTop.moveTo((float) labelX, (float) j2DYtop);
            arrowTop.lineTo((float) labelX - this.arrowWidth,
                    (float) j2DYtop + this.arrowLength);
            arrowTop.lineTo((float) labelX + this.arrowWidth,
                    (float) j2DYtop + this.arrowLength);
            arrowTop.closePath();
            g2.fill(arrowTop);

            GeneralPath arrowBottom = new GeneralPath();
            arrowBottom.moveTo((float) labelX, (float) j2DYbottom);
            arrowBottom.lineTo((float) labelX - this.arrowWidth,
                    (float) j2DYbottom - this.arrowLength);
            arrowBottom.lineTo((float) labelX + this.arrowWidth,
                    (float) j2DYbottom - this.arrowLength);
            arrowBottom.closePath();
            g2.fill(arrowBottom);
        }

        if (text.isEmpty()) {
            return;
        }

        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(hotspot);
        }
        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, (float) labelX,
                (float) labelY, textAnchor, getRotationAngle(),
                getRotationAnchor());
        if (isOutlineVisible()) {
            g2.setStroke(getOutlineStroke());
            g2.setPaint(getOutlinePaint());
            g2.draw(hotspot);
        }

        if (isCreateEntity()) {
            addEntity(info, hotspot, rendererIndex, getToolTipText(), getURL());
        }
    }

    @Override
    protected XYAnnotationEntity createEntity(Shape hotspot, int rendererIndex,
            String toolTipText, String urlText) {
        return new XYMeasurementAnnotationEntity(hotspot, this, rendererIndex,
                toolTipText, urlText);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(arrowLength);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + HashUtils.hashCodeForPaint(arrowPaint);
        result = prime * result + arrowStroke.hashCode();
        temp = Double.doubleToLongBits(arrowWidth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (baseCreateEntity ? 1231 : 1237);
        temp = Double.doubleToLongBits(labelOffset);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + HashUtils.hashCodeForPaint(measureLinePaint);
        result = prime * result + measureLineStroke.hashCode();
        result = prime * result + orientation.hashCode();
        temp = Double.doubleToLongBits(x2);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y2);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        XYMeasurementAnnotation other = (XYMeasurementAnnotation) obj;
        if (Double.doubleToLongBits(arrowLength) != Double
                .doubleToLongBits(other.arrowLength))
            return false;
        if (!arrowPaint.equals(other.arrowPaint))
            return false;
        if (!arrowStroke.equals(other.arrowStroke))
            return false;
        if (Double.doubleToLongBits(arrowWidth) != Double
                .doubleToLongBits(other.arrowWidth))
            return false;
        if (baseCreateEntity != other.baseCreateEntity)
            return false;
        if (Double.doubleToLongBits(labelOffset) != Double
                .doubleToLongBits(other.labelOffset))
            return false;
        if (!measureLinePaint.equals(other.measureLinePaint))
            return false;
        if (!measureLineStroke.equals(other.measureLineStroke))
            return false;
        if (orientation != other.orientation)
            return false;
        if (Double.doubleToLongBits(x2) != Double.doubleToLongBits(other.x2))
            return false;
        if (Double.doubleToLongBits(y2) != Double.doubleToLongBits(other.y2))
            return false;
        return true;
    }

    /**
     * Returns a clone of the annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the annotation can't be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Provides serialization support.
     *
     * @param stream the output stream.
     *
     * @throws IOException if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.arrowPaint, stream);
        SerialUtils.writeStroke(this.arrowStroke, stream);
        SerialUtils.writePaint(this.measureLinePaint, stream);
        SerialUtils.writeStroke(this.measureLineStroke, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream the input stream.
     *
     * @throws IOException            if there is an I/O error.
     * @throws ClassNotFoundException if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.arrowPaint = SerialUtils.readPaint(stream);
        this.arrowStroke = SerialUtils.readStroke(stream);
        this.measureLinePaint = SerialUtils.readPaint(stream);
        this.measureLineStroke = SerialUtils.readStroke(stream);
    }
}