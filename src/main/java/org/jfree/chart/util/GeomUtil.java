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
 * -------------
 * GeomUtil.java
 * -------------
 * (C) Copyright 2021-present, by Yuri Blankenstein and Contributors.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.util;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Some utility methods for working with geometry in Java2D.
 */
public final class GeomUtil {
    private GeomUtil() {
        // Empty for utility classes
    }

    /**
     * For each line in {@code lines}, calculates its intersection point with
     * {@code lineA}, possibly no intersection point exists (i.e. parallel
     * lines).
     * 
     * @param lineA line to calculate the intersection point for.
     * @param lines lines to calculate the intersection points with.
     * @return all intersections points between {@code lineA} and {@code lines}.
     * @see #calculateIntersectionPoint(Line2D, Line2D)
     */
    public static Point2D[] calculateIntersectionPoints(Line2D lineA,
                                                        Line2D... lines) {
        ArrayList<Point2D> intersectionPoints = new ArrayList<>(
                lines.length);
        for (Line2D lineB : lines) {
            if (lineA.intersectsLine(lineB)) {
                // Why does Java have the tester method, but not the method to
                // get the point itself :S
                intersectionPoints.add(calculateIntersectionPoint(lineA, lineB));
            }
        }
        return intersectionPoints.toArray(new Point2D[0]);
    }

    /**
     * Calculates the intersection point of {@code lineA} with {@code lineB},
     * possibly {@code null} if no intersection point exists (i.e. parallel
     * lines).
     * 
     * @param lineA the first line for the calculation
     * @param lineB the second line for the calculation
     * @return the intersection point of {@code lineA} with {@code lineB},
     *         possibly {@code null} if no intersection point exists
     */
    public static Point2D calculateIntersectionPoint(Line2D lineA,
                                                     Line2D lineB) {
        double x1 = lineA.getX1();
        double y1 = lineA.getY1();
        double x2 = lineA.getX2();
        double y2 = lineA.getY2();

        double x3 = lineB.getX1();
        double y3 = lineB.getY1();
        double x4 = lineB.getX2();
        double y4 = lineB.getY2();

        Point2D p = null;

        double d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (d != 0) {
            double xi = ((x3 - x4) * (x1 * y2 - y1 * x2)
                    - (x1 - x2) * (x3 * y4 - y3 * x4)) / d;
            double yi = ((y3 - y4) * (x1 * y2 - y1 * x2)
                    - (y1 - y2) * (x3 * y4 - y3 * x4)) / d;

            p = new Point2D.Double(xi, yi);
        }
        return p;
    }

    /**
     * Returns all {@link PathIterator#SEG_LINETO line segments} building up a
     * {@code shape}.
     * 
     * @param shape a shape that is built up of {@link PathIterator#SEG_LINETO}
     *              elements.
     * @param at    an optional {@code AffineTransform} to be applied to the
     *              coordinates as they are returned in the iteration, or
     *              {@code null} if untransformed coordinates are desired
     * @return all {@link PathIterator#SEG_LINETO line segments} building up the
     *         {@code shape}
     * @throws IllegalArgumentException if {@code shape} contains non-straight
     *                                  line segments (i.e.
     *                                  {@link PathIterator#SEG_CUBICTO} or
     *                                  {@link PathIterator#SEG_QUADTO})
     */
    public static Line2D[] getLines(Shape shape, AffineTransform at)
            throws IllegalArgumentException {
        ArrayList<Line2D> lines = new ArrayList<>();
        Point2D first = null;
        Point2D current = null;
        double[] coords = new double[6];
        for (PathIterator pathIterator = shape.getPathIterator(at); 
                !pathIterator.isDone(); pathIterator.next()) {
            switch (pathIterator.currentSegment(coords)) {
            case PathIterator.SEG_MOVETO:
                current = new Point2D.Double(coords[0], coords[1]);
                break;
            case PathIterator.SEG_LINETO:
                Point2D to = new Point2D.Double(coords[0], coords[1]);
                lines.add(new Line2D.Double(current, to));
                current = to;
                break;
            case PathIterator.SEG_CLOSE:
                lines.add(new Line2D.Double(current, first));
                current = first;
                break;
            default:
                throw new IllegalArgumentException(
                        "Shape contains non-straight line segments");
            }
            if (null == first)
                first = current;
        }
        return lines.toArray(new Line2D[0]);
    }
}
