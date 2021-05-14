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
 * XYMeasurementAnnotationEntity.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.entity;

import java.awt.Shape;
import java.awt.geom.Point2D;

import org.jfree.chart.annotations.XYMeasurementAnnotation;
import org.jfree.chart.annotations.XYMeasurementAnnotation.Orientation;

/**
 * A chart entity that allows to reposition an {@link XYMeasurementAnnotation}.
 */
public class XYMeasurementAnnotationEntity extends XYAnnotationEntity
        implements MovableChartEntity {
    private static final long serialVersionUID = 3673107973561822541L;

    /**
     * Creates a new entity.
     *
     * @param hotspot  the area.
     * @param annotation  the annotation.
     * @param rendererIndex  the rendererIndex (zero-based index).
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text for HTML image maps.
     */
    public XYMeasurementAnnotationEntity(Shape hotspot,
            XYMeasurementAnnotation annotation, int rendererIndex,
            String toolTipText, String urlText) {
        super(hotspot, annotation, rendererIndex, toolTipText, urlText);
    }

    @Override
    public XYMeasurementAnnotation getAnnotation() {
        return (XYMeasurementAnnotation) super.getAnnotation();
    }

    @Override
    public Point2D tryMove(Point2D from, Point2D to) {
        if (getAnnotation().getOrientation() == Orientation.HORIZONTAL) {
            // This annotation can only be moved in Y-direction
            return new Point2D.Double(from.getX(), to.getY());
        } else {
            // This annotation can only be moved in X-direction
            return new Point2D.Double(to.getX(), from.getY());
        }
    }

    @Override
    public void move(Point2D from, Point2D to) {
        double offset = getAnnotation().getLabelOffset();
        double move = getAnnotation().getOrientation() == Orientation.HORIZONTAL ?
                from.getY() - to.getY() :
                to.getX() - from.getX();
        getAnnotation().setLabelOffset(offset + move);
    }
}
