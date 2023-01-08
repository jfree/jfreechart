/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * XYInversePointerAnnotation.java
 * ------------------------
 * (C) Copyright 2021-present, by Yuri Blankenstein and Contributors.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.util.GeomUtil;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.chart.util.PublicCloneable;

/**
 * An arrow and label that can be placed on an {@link XYPlot}. The arrow is
 * drawn at a user-definable angle but points towards the label of the
 * annotation.
 * <p>
 * The arrow length (and its offset from the (x, y) location) is controlled by
 * the tip radius and the base radius attributes. Imagine two circles around the
 * (x, y) coordinate: the inner circle defined by the tip radius, and the outer
 * circle defined by the base radius. Now, draw the arrow starting at some point
 * on the outer circle (the point is determined by the angle), with the arrow
 * tip being drawn at a corresponding point on the inner circle.
 */
public class XYInversePointerAnnotation extends XYPointerAnnotation
        implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4031161445009858551L;

    /** The default dot radius (in Java2D units). */
    public static final double DEFAULT_DOT_RADIUS = 0;

    /** The radius of the dot at the start of the arrow. */
    private double dotRadius;

    /**
     * Creates a new label and arrow annotation.
     *
     * @param label the label ({@code null} permitted).
     * @param x     the x-coordinate (measured against the chart's domain axis).
     * @param y     the y-coordinate (measured against the chart's range axis).
     * @param angle the angle of the arrow's line (in radians).
     */
    public XYInversePointerAnnotation(String label, double x, double y,
            double angle) {
        super(label, x, y, angle);
        this.dotRadius = DEFAULT_DOT_RADIUS;
    }

    /**
     * Returns the radius of the dot at the start of the arrow.
     * 
     * @return the radius of the dot at the start of the arrow
     * @see #setDotRadius(double)
     */
    public double getDotRadius() {
        return dotRadius;
    }

    /**
     * Sets the radius of the dot at the start of the arrow, &#8804; 0 will omit
     * the dot.
     * 
     * @param dotRadius the radius of the dot at the start of the arrow
     * @see #getDotRadius()
     */
    public void setDotRadius(double dotRadius) {
        this.dotRadius = dotRadius;
        fireAnnotationChanged();
    }

    /**
     * Draws the annotation.
     *
     * @param g2            the graphics device.
     * @param plot          the plot.
     * @param dataArea      the data area.
     * @param domainAxis    the domain axis.
     * @param rangeAxis     the range axis.
     * @param rendererIndex the renderer index.
     * @param info          the plot rendering info.
     */
    @Override
    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea,
            ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex,
            PlotRenderingInfo info) {

        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(
                plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(
                plot.getRangeAxisLocation(), orientation);
        double j2DX = domainAxis.valueToJava2D(getX(), dataArea, domainEdge);
        double j2DY = rangeAxis.valueToJava2D(getY(), dataArea, rangeEdge);
        if (orientation == PlotOrientation.HORIZONTAL) {
            double temp = j2DX;
            j2DX = j2DY;
            j2DY = temp;
        }

        double startX = j2DX + Math.cos(getAngle()) * getBaseRadius();
        double startY = j2DY + Math.sin(getAngle()) * getBaseRadius();

        double endX = j2DX + Math.cos(getAngle()) * getTipRadius();
        double endY = j2DY + Math.sin(getAngle()) * getTipRadius();

        // Already calculate the label bounds to adjust the start point
        double labelX = j2DX
                + Math.cos(getAngle()) * (getBaseRadius() + getLabelOffset());
        double labelY = j2DY
                + Math.sin(getAngle()) * (getBaseRadius() + getLabelOffset());
        Shape hotspot = TextUtils.calculateRotatedStringBounds(getText(), g2,
                (float) labelX, (float) labelY, getTextAnchor(),
                getRotationAngle(), getRotationAnchor());

        Point2D[] pointOnLabelBounds = GeomUtil.calculateIntersectionPoints(
                new Line2D.Double(startX, startY, endX, endY),
                GeomUtil.getLines(hotspot, null));
        if (pointOnLabelBounds.length > 0) {
            // Adjust the start point to the intersection with the label bounds
            startX = pointOnLabelBounds[0].getX();
            startY = pointOnLabelBounds[0].getY();
        }

        double arrowBaseX = startX - Math.cos(getAngle()) * getArrowLength();
        double arrowBaseY = startY - Math.sin(getAngle()) * getArrowLength();

        double arrowLeftX = arrowBaseX
                + Math.cos(getAngle() + Math.PI / 2.0) * getArrowWidth();
        double arrowLeftY = arrowBaseY
                + Math.sin(getAngle() + Math.PI / 2.0) * getArrowWidth();

        double arrowRightX = arrowBaseX
                - Math.cos(getAngle() + Math.PI / 2.0) * getArrowWidth();
        double arrowRightY = arrowBaseY
                - Math.sin(getAngle() + Math.PI / 2.0) * getArrowWidth();

        GeneralPath arrow = new GeneralPath();
        arrow.moveTo((float) startX, (float) startY);
        arrow.lineTo((float) arrowLeftX, (float) arrowLeftY);
        arrow.lineTo((float) arrowRightX, (float) arrowRightY);
        arrow.closePath();

        g2.setStroke(getArrowStroke());
        g2.setPaint(getArrowPaint());
        Line2D line = new Line2D.Double(arrowBaseX, arrowBaseY, endX, endY);
        g2.draw(line);
        g2.fill(arrow);

        if (dotRadius > 0) {
            Ellipse2D.Double dot = new Ellipse2D.Double(endX - dotRadius,
                    endY - dotRadius, 2 * dotRadius, 2 * dotRadius);
            g2.fill(dot);
        }

        // draw the label
        g2.setFont(getFont());
        if (getBackgroundPaint() != null) {
            g2.setPaint(getBackgroundPaint());
            g2.fill(hotspot);
        }

        g2.setPaint(getPaint());
        TextUtils.drawRotatedString(getText(), g2, (float) labelX,
                (float) labelY, getTextAnchor(), getRotationAngle(),
                getRotationAnchor());
        if (isOutlineVisible()) {
            g2.setStroke(getOutlineStroke());
            g2.setPaint(getOutlinePaint());
            g2.draw(hotspot);
        }

        String toolTip = getToolTipText();
        String url = getURL();
        if (toolTip != null || url != null) {
            addEntity(info, hotspot, rendererIndex, toolTip, url);
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(dotRadius);
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
        XYInversePointerAnnotation other = (XYInversePointerAnnotation) obj;
        if (Double.doubleToLongBits(dotRadius) != Double
                .doubleToLongBits(other.dotRadius))
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
}
