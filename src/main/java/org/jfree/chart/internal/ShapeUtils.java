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
 * ---------------
 * ShapeUtils.java
 * ---------------
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributors:     -;
 */

package org.jfree.chart.internal;

import org.jfree.chart.api.RectangleAnchor;

import java.awt.*;
import java.awt.geom.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utility methods for {@link Shape} objects.
 */
public class ShapeUtils {

    /**
     * Prevents instantiation.
     */
    private ShapeUtils() {
    }

    /**
     * Tests two shapes for equality.  If both shapes are {@code null},
     * this method will return {@code true}.
     * <p>
     * In the current implementation, the following shapes are supported:
     * {@code Ellipse2D}, {@code Line2D} and {@code Rectangle2D}
     * (implicit).
     *
     * @param s1  the first shape ({@code null} permitted).
     * @param s2  the second shape ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(Shape s1, Shape s2) {
        if (s1 instanceof Line2D && s2 instanceof Line2D) {
            return equal((Line2D) s1, (Line2D) s2);
        } else if (s1 instanceof Polygon && s2 instanceof Polygon) {
            return equal((Polygon) s1, (Polygon) s2);
        } else if (s1 instanceof Path2D && s2 instanceof Path2D) {
            return equal((Path2D) s1, (Path2D) s2);
        } else {
            // this will handle Arc2D, Ellipse2D, Rectangle2D...
            return Objects.equals(s1, s2);
        }
    }

    /**
     * Compares two lines and returns {@code true} if they are equal or
     * both {@code null}.
     *
     * @param l1  the first line ({@code null} permitted).
     * @param l2  the second line ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(Line2D l1, Line2D l2) {
        if (l1 == null) {
            return (l2 == null);
        }
        if (l2 == null) {
            return false;
        }
        if (!l1.getP1().equals(l2.getP1())) {
            return false;
        }
        if (!l1.getP2().equals(l2.getP2())) {
            return false;
        }
        return true;
    }

    /**
     * Tests two polygons for equality.  If both are {@code null} this
     * method returns {@code true}.
     *
     * @param p1  polygon 1 ({@code null} permitted).
     * @param p2  polygon 2 ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(Polygon p1, Polygon p2) {
        if (p1 == null) {
            return (p2 == null);
        }
        if (p2 == null) {
            return false;
        }
        if (p1.npoints != p2.npoints) {
            return false;
        }
        if (!Arrays.equals(p1.xpoints, p2.xpoints)) {
            return false;
        }
        if (!Arrays.equals(p1.ypoints, p2.ypoints)) {
            return false;
        }
        return true;
    }

    /**
     * Tests two {@code GeneralPath} instances for equality.  If both are 
     * {@code null} this method returns {@code true}.
     *
     * @param p1  path 1 ({@code null} permitted).
     * @param p2  path 2 ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(Path2D p1, Path2D p2) {
        if (p1 == null) {
            return (p2 == null);
        }
        if (p2 == null) {
            return false;
        }
        if (p1.getWindingRule() != p2.getWindingRule()) {
            return false;
        }
        PathIterator iterator1 = p1.getPathIterator(null);
        PathIterator iterator2 = p2.getPathIterator(null);
        double[] d1 = new double[6];
        double[] d2 = new double[6];
        boolean done = iterator1.isDone() && iterator2.isDone();
        while (!done) {
            if (iterator1.isDone() != iterator2.isDone()) {
                return false;
            }
            int seg1 = iterator1.currentSegment(d1);
            int seg2 = iterator2.currentSegment(d2);
            if (seg1 != seg2) {
                return false;
            }
            if (!Arrays.equals(d1, d2)) {
                return false;
            }
            iterator1.next();
            iterator2.next();
            done = iterator1.isDone() && iterator2.isDone();
        }
        return true;
    }

    /**
     * Creates and returns a translated shape.
     *
     * @param shape  the shape ({@code null} not permitted).
     * @param transX  the x translation (in Java2D space).
     * @param transY  the y translation (in Java2D space).
     *
     * @return The translated shape.
     */
    public static Shape createTranslatedShape(Shape shape, double transX,
            double transY) {
        Args.nullNotPermitted(shape, "shape");
        final AffineTransform transform = AffineTransform.getTranslateInstance(
                transX, transY);
        return transform.createTransformedShape(shape);
    }

    /**
     * Translates a shape to a new location such that the anchor point
     * (relative to the rectangular bounds of the shape) aligns with the
     * specified (x, y) coordinate in Java2D space.
     *
     * @param shape  the shape ({@code null} not permitted).
     * @param anchor  the anchor ({@code null} not permitted).
     * @param locationX  the x-coordinate (in Java2D space).
     * @param locationY  the y-coordinate (in Java2D space).
     *
     * @return A new and translated shape.
     */
    public static Shape createTranslatedShape(Shape shape, 
            RectangleAnchor anchor, double locationX, double locationY) {
        Args.nullNotPermitted(shape, "shape");
        Args.nullNotPermitted(anchor, "anchor");
        Point2D anchorPoint = anchor.getAnchorPoint(shape.getBounds2D());
        final AffineTransform transform = AffineTransform.getTranslateInstance(
                locationX - anchorPoint.getX(), locationY - anchorPoint.getY());
        return transform.createTransformedShape(shape);
    }

    /**
     * Rotates a shape about the specified coordinates.
     *
     * @param base  the shape ({@code null} permitted, returns {@code null}).
     * @param angle  the angle (in radians).
     * @param x  the x coordinate for the rotation point (in Java2D space).
     * @param y  the y coordinate for the rotation point (in Java2D space).
     *
     * @return the rotated shape.
     */
    public static Shape rotateShape(Shape base, double angle, float x, float y) {
        if (base == null) {
            return null;
        }
        AffineTransform rotate = AffineTransform.getRotateInstance(angle, x, y);
        return rotate.createTransformedShape(base);
    }

    /**
     * Draws a shape with the specified rotation about {@code (x, y)}.
     *
     * @param g2  the graphics device ({@code null} not permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param angle  the angle (in radians).
     * @param x  the x coordinate for the rotation point.
     * @param y  the y coordinate for the rotation point.
     */
    public static void drawRotatedShape(Graphics2D g2, Shape shape, double angle,
            float x, float y) {
        Args.nullNotPermitted(g2, "g2");
        Args.nullNotPermitted(shape, "shape");
        AffineTransform saved = g2.getTransform();
        AffineTransform rotate = AffineTransform.getRotateInstance(angle, x, y);
        g2.transform(rotate);
        g2.draw(shape);
        g2.setTransform(saved);

    }

    /** A useful constant used internally. */
    private static final float SQRT2 = (float) Math.pow(2.0, 0.5);

    /**
     * Creates a diagonal cross shape.
     *
     * @param l  the length of each 'arm'.
     * @param t  the thickness.
     *
     * @return A diagonal cross shape.
     */
    public static Shape createDiagonalCross(float l, float t) {
        final GeneralPath p0 = new GeneralPath();
        p0.moveTo(-l - t, -l + t);
        p0.lineTo(-l + t, -l - t);
        p0.lineTo(0.0f, -t * SQRT2);
        p0.lineTo(l - t, -l - t);
        p0.lineTo(l + t, -l + t);
        p0.lineTo(t * SQRT2, 0.0f);
        p0.lineTo(l + t, l - t);
        p0.lineTo(l - t, l + t);
        p0.lineTo(0.0f, t * SQRT2);
        p0.lineTo(-l + t, l + t);
        p0.lineTo(-l - t, l - t);
        p0.lineTo(-t * SQRT2, 0.0f);
        p0.closePath();
        return p0;
    }

    /**
     * Creates a diagonal cross shape.
     *
     * @param l  the length of each 'arm'.
     * @param t  the thickness.
     *
     * @return A diagonal cross shape.
     */
    public static Shape createRegularCross(float l, float t) {
        final GeneralPath p0 = new GeneralPath();
        p0.moveTo(-l, t);
        p0.lineTo(-t, t);
        p0.lineTo(-t, l);
        p0.lineTo(t, l);
        p0.lineTo(t, t);
        p0.lineTo(l, t);
        p0.lineTo(l, -t);
        p0.lineTo(t, -t);
        p0.lineTo(t, -l);
        p0.lineTo(-t, -l);
        p0.lineTo(-t, -t);
        p0.lineTo(-l, -t);
        p0.closePath();
        return p0;
    }

    /**
     * Creates a diamond shape.
     *
     * @param s  the size factor (equal to half the height of the diamond).
     *
     * @return A diamond shape.
     */
    public static Shape createDiamond(float s) {
        final GeneralPath p0 = new GeneralPath();
        p0.moveTo(0.0f, -s);
        p0.lineTo(s, 0.0f);
        p0.lineTo(0.0f, s);
        p0.lineTo(-s, 0.0f);
        p0.closePath();
        return p0;
    }

    /**
     * Creates a triangle shape that points upwards.
     *
     * @param s  the size factor (equal to half the height of the triangle).
     *
     * @return A triangle shape.
     */
    public static Shape createUpTriangle(float s) {
        final GeneralPath p0 = new GeneralPath();
        p0.moveTo(0.0f, -s);
        p0.lineTo(s, s);
        p0.lineTo(-s, s);
        p0.closePath();
        return p0;
    }

    /**
     * Creates a triangle shape that points downwards.
     *
     * @param s  the size factor (equal to half the height of the triangle).
     *
     * @return A triangle shape.
     */
    public static Shape createDownTriangle(float s) {
        final GeneralPath p0 = new GeneralPath();
        p0.moveTo(0.0f, s);
        p0.lineTo(s, -s);
        p0.lineTo(-s, -s);
        p0.closePath();
        return p0;
    }

    /**
     * Creates a region surrounding a line segment by 'widening' the line
     * segment.  A typical use for this method is the creation of a
     * 'clickable' region for a line that is displayed on-screen.
     *
     * @param line  the line ({@code null} not permitted).
     * @param width  the width of the region.
     *
     * @return A region that surrounds the line.
     */
    public static Shape createLineRegion(Line2D line, float width) {
        Args.nullNotPermitted(line, "line");
        final GeneralPath result = new GeneralPath();
        final float x1 = (float) line.getX1();
        final float x2 = (float) line.getX2();
        final float y1 = (float) line.getY1();
        final float y2 = (float) line.getY2();
        if ((x2 - x1) != 0.0) {
            final double theta = Math.atan((y2 - y1) / (x2 - x1));
            final float dx = (float) Math.sin(theta) * width;
            final float dy = (float) Math.cos(theta) * width;
            result.moveTo(x1 - dx, y1 + dy);
            result.lineTo(x1 + dx, y1 - dy);
            result.lineTo(x2 + dx, y2 - dy);
            result.lineTo(x2 - dx, y2 + dy);
            result.closePath();
        } else {
            // special case, vertical line
            result.moveTo(x1 - width / 2.0f, y1);
            result.lineTo(x1 + width / 2.0f, y1);
            result.lineTo(x2 + width / 2.0f, y2);
            result.lineTo(x2 - width / 2.0f, y2);
            result.closePath();
        }
        return result;
    }

    /**
     * Returns a point based on (x, y) but constrained to be within the bounds
     * of a given rectangle.
     *
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param area  the constraining rectangle ({@code null} not permitted).
     *
     * @return A point within the rectangle.
     *
     * @throws NullPointerException if {@code area} is {@code null}.
     */
    public static Point2D getPointInRectangle(double x, double y,
            Rectangle2D area) {
        x = Math.max(area.getMinX(), Math.min(x, area.getMaxX()));
        y = Math.max(area.getMinY(), Math.min(y, area.getMaxY()));
        return new Point2D.Double(x, y);
    }

    /**
     * Checks, whether the given rectangle1 fully contains rectangle 2
     * (even if rectangle 2 has a height or width of zero!).
     *
     * @param rect1  the first rectangle ({@code null} not permitted).
     * @param rect2  the second rectangle ({@code null} not permitted).
     *
     * @return A boolean.
     */
    public static boolean contains(Rectangle2D rect1, Rectangle2D rect2) {
        Args.nullNotPermitted(rect1, "rect1");
        Args.nullNotPermitted(rect2, "rect2");
        final double x0 = rect1.getX();
        final double y0 = rect1.getY();
        final double x = rect2.getX();
        final double y = rect2.getY();
        final double w = rect2.getWidth();
        final double h = rect2.getHeight();
        return ((x >= x0) && (y >= y0)
                && ((x + w) <= (x0 + rect1.getWidth()))
                && ((y + h) <= (y0 + rect1.getHeight())));
    }

    /**
     * Checks, whether the given rectangle1 fully contains rectangle 2
     * (even if rectangle 2 has a height or width of zero!).
     *
     * @param rect1  the first rectangle.
     * @param rect2  the second rectangle.
     *
     * @return A boolean.
     */
    public static boolean intersects(Rectangle2D rect1, Rectangle2D rect2) {
        Args.nullNotPermitted(rect1, "rect1");
        Args.nullNotPermitted(rect2, "rect2");
        final double x0 = rect1.getX();
        final double y0 = rect1.getY();

        final double x = rect2.getX();
        final double width = rect2.getWidth();
        final double y = rect2.getY();
        final double height = rect2.getHeight();
        return (x + width >= x0 && y + height >= y0 && x <= x0 + rect1.getWidth()
              && y <= y0 + rect1.getHeight());
    }
    
    /**
     * Returns {@code true} if the specified point (x, y) falls within or
     * on the boundary of the specified rectangle.
     *
     * @param rect  the rectangle ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     *
     * @return A boolean.
     */
    public static boolean isPointInRect(Rectangle2D rect, double x, double y) {
        return (x >= rect.getMinX() && x <= rect.getMaxX()
                && y >= rect.getMinY() && y <= rect.getMaxY());
    }
    
    /**
     * Clones a map containing shapes.
     * 
     * @param source  the source map.
     * 
     * @return A new map containing cloned shapes.
     */
    public static <K extends Comparable<K>> Map<K, Shape> cloneMap(Map<K, Shape> source) {
        Map<K, Shape> result = new HashMap<>();
        for (K key : source.keySet()) {
            result.put(key, source.get(key));
        }
        return result;
    }
    
    /**
     * Returns {@code true} if the two maps contain the same set of entries and 
     * {@code false} otherwise.
     * 
     * @param <K>  the key type.
     * @param map1  the first map.
     * @param map2  the second map.
     * 
     * @return A boolean.
     */
    public static <K extends Comparable<K>> boolean equal(Map<K, Shape> map1, Map<K, Shape> map2) {
        if (!map1.keySet().equals(map2.keySet())) {
            return false;
        }
        for (K key : map1.keySet()) {
            Shape p1 = map1.get(key);
            Shape p2 = map2.get(key);
            if (!ShapeUtils.equal(p1, p2)) {
                return false;
            }
        }
        return true;
    }
 
}

