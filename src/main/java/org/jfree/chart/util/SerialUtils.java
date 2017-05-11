/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2017, by Object Refinery Limited and Contributors.
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
 */

package org.jfree.chart.util;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;

/**
 * A class containing useful utility methods relating to serialization.
 */
public class SerialUtils {

    /**
     * Private constructor prevents object creation.
     */
    private SerialUtils() {
    }

    /**
     * Returns {@code true} if a class implements {@code Serializable}
     * and {@code false} otherwise.
     *
     * @param c  the class.
     *
     * @return A boolean.
     */
    public static boolean isSerializable(Class c) {
        return (Serializable.class.isAssignableFrom(c));
    }

    /**
     * Reads a {@code Paint} object that has been serialised by the
     * {@link #writePaint(Paint, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The paint object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Paint readPaint(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        Paint result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            final Class c = (Class) stream.readObject();
            if (isSerializable(c)) {
                result = (Paint) stream.readObject();
            }
            else if (c.equals(GradientPaint.class)) {
                float x1 = stream.readFloat();
                float y1 = stream.readFloat();
                Color c1 = (Color) stream.readObject();
                float x2 = stream.readFloat();
                float y2 = stream.readFloat();
                Color c2 = (Color) stream.readObject();
                boolean isCyclic = stream.readBoolean();
                result = new GradientPaint(x1, y1, c1, x2, y2, c2, isCyclic);
            }
        }
        return result;

    }

    /**
     * Serialises a {@code Paint} object.
     *
     * @param paint  the paint object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writePaint(Paint paint, ObjectOutputStream stream)
        throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (paint != null) {
            stream.writeBoolean(false);
            stream.writeObject(paint.getClass());
            if (paint instanceof Serializable) {
                stream.writeObject(paint);
            }
            else if (paint instanceof GradientPaint) {
                final GradientPaint gp = (GradientPaint) paint;
                stream.writeFloat((float) gp.getPoint1().getX());
                stream.writeFloat((float) gp.getPoint1().getY());
                stream.writeObject(gp.getColor1());
                stream.writeFloat((float) gp.getPoint2().getX());
                stream.writeFloat((float) gp.getPoint2().getY());
                stream.writeObject(gp.getColor2());
                stream.writeBoolean(gp.isCyclic());
            }
        }
        else {
            stream.writeBoolean(true);
        }

    }

    /**
     * Reads a {@code Stroke} object that has been serialised by the
     * {@link #writeStroke(Stroke, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The stroke object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Stroke readStroke(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        Stroke result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (c.equals(BasicStroke.class)) {
                float width = stream.readFloat();
                int cap = stream.readInt();
                int join = stream.readInt();
                float miterLimit = stream.readFloat();
                float[] dash = (float[]) stream.readObject();
                float dashPhase = stream.readFloat();
                result = new BasicStroke(width, cap, join, miterLimit, dash, 
                        dashPhase);
            }
            else {
                result = (Stroke) stream.readObject();
            }
        }
        return result;

    }

    /**
     * Serialises a {@code Stroke} object.  This code handles the
     * {@code BasicStroke} class which is the only {@code Stroke}
     * implementation provided by the JDK (and isn't directly
     * {@code Serializable}).
     *
     * @param stroke  the stroke object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeStroke(Stroke stroke, ObjectOutputStream stream)
            throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (stroke != null) {
            stream.writeBoolean(false);
            if (stroke instanceof BasicStroke) {
                BasicStroke s = (BasicStroke) stroke;
                stream.writeObject(BasicStroke.class);
                stream.writeFloat(s.getLineWidth());
                stream.writeInt(s.getEndCap());
                stream.writeInt(s.getLineJoin());
                stream.writeFloat(s.getMiterLimit());
                stream.writeObject(s.getDashArray());
                stream.writeFloat(s.getDashPhase());
            } else {
                stream.writeObject(stroke.getClass());
                stream.writeObject(stroke);
            }
        } else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a {@code Composite} object that has been serialised by the
     * {@link #writeComposite(Composite, ObjectOutputStream)}
     * method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The composite object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     * 
     * @since 1.0.17
     */
    public static Composite readComposite(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        Composite result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (isSerializable(c)) {
                result = (Composite) stream.readObject();
            }
            else if (c.equals(AlphaComposite.class)) {
                int rule = stream.readInt();
                float alpha = stream.readFloat();
                result = AlphaComposite.getInstance(rule, alpha);
            }
        }
        return result;

    }

    /**
     * Serialises a {@code Composite} object.
     *
     * @param composite  the composite object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     * 
     * @since 1.0.17
     */
    public static void writeComposite(Composite composite, 
            ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (composite != null) {
            stream.writeBoolean(false);
            stream.writeObject(composite.getClass());
            if (composite instanceof Serializable) {
                stream.writeObject(composite);
            }
            else if (composite instanceof AlphaComposite) {
                AlphaComposite ac = (AlphaComposite) composite;
                stream.writeInt(ac.getRule());
                stream.writeFloat(ac.getAlpha());
            }
        } else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a {@code Shape} object that has been serialised by the
     * {@link #writeShape(Shape, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The shape object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static Shape readShape(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        Shape result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            Class c = (Class) stream.readObject();
            if (c.equals(Line2D.class)) {
                double x1 = stream.readDouble();
                double y1 = stream.readDouble();
                double x2 = stream.readDouble();
                double y2 = stream.readDouble();
                result = new Line2D.Double(x1, y1, x2, y2);
            }
            else if (c.equals(Rectangle2D.class)) {
                double x = stream.readDouble();
                double y = stream.readDouble();
                double w = stream.readDouble();
                double h = stream.readDouble();
                result = new Rectangle2D.Double(x, y, w, h);
            }
            else if (c.equals(Ellipse2D.class)) {
                double x = stream.readDouble();
                double y = stream.readDouble();
                double w = stream.readDouble();
                double h = stream.readDouble();
                result = new Ellipse2D.Double(x, y, w, h);
            }
            else if (c.equals(Arc2D.class)) {
                double x = stream.readDouble();
                double y = stream.readDouble();
                double w = stream.readDouble();
                double h = stream.readDouble();
                double as = stream.readDouble(); // Angle Start
                double ae = stream.readDouble(); // Angle Extent
                int at = stream.readInt();       // Arc type
                result = new Arc2D.Double(x, y, w, h, as, ae, at);
            }
            else if (c.equals(GeneralPath.class)) {
                GeneralPath gp = new GeneralPath();
                float[] args = new float[6];
                boolean hasNext = stream.readBoolean();
                while (!hasNext) {
                    int type = stream.readInt();
                    for (int i = 0; i < 6; i++) {
                        args[i] = stream.readFloat();
                    }
                    switch (type) {
                        case PathIterator.SEG_MOVETO :
                            gp.moveTo(args[0], args[1]);
                            break;
                        case PathIterator.SEG_LINETO :
                            gp.lineTo(args[0], args[1]);
                            break;
                        case PathIterator.SEG_CUBICTO :
                            gp.curveTo(args[0], args[1], args[2],
                                    args[3], args[4], args[5]);
                            break;
                        case PathIterator.SEG_QUADTO :
                            gp.quadTo(args[0], args[1], args[2], args[3]);
                            break;
                        case PathIterator.SEG_CLOSE :
                            gp.closePath();
                            break;
                        default :
                            throw new RuntimeException(
                                    "JFreeChart - No path exists");
                    }
                    gp.setWindingRule(stream.readInt());
                    hasNext = stream.readBoolean();
                }
                result = gp;
            }
            else {
                result = (Shape) stream.readObject();
            }
        }
        return result;

    }

    /**
     * Serialises a {@code Shape} object.
     *
     * @param shape  the shape object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeShape(Shape shape, ObjectOutputStream stream)
            throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (shape != null) {
            stream.writeBoolean(false);
            if (shape instanceof Line2D) {
                final Line2D line = (Line2D) shape;
                stream.writeObject(Line2D.class);
                stream.writeDouble(line.getX1());
                stream.writeDouble(line.getY1());
                stream.writeDouble(line.getX2());
                stream.writeDouble(line.getY2());
            }
            else if (shape instanceof Rectangle2D) {
                final Rectangle2D rectangle = (Rectangle2D) shape;
                stream.writeObject(Rectangle2D.class);
                stream.writeDouble(rectangle.getX());
                stream.writeDouble(rectangle.getY());
                stream.writeDouble(rectangle.getWidth());
                stream.writeDouble(rectangle.getHeight());
            }
            else if (shape instanceof Ellipse2D) {
                final Ellipse2D ellipse = (Ellipse2D) shape;
                stream.writeObject(Ellipse2D.class);
                stream.writeDouble(ellipse.getX());
                stream.writeDouble(ellipse.getY());
                stream.writeDouble(ellipse.getWidth());
                stream.writeDouble(ellipse.getHeight());
            }
            else if (shape instanceof Arc2D) {
                final Arc2D arc = (Arc2D) shape;
                stream.writeObject(Arc2D.class);
                stream.writeDouble(arc.getX());
                stream.writeDouble(arc.getY());
                stream.writeDouble(arc.getWidth());
                stream.writeDouble(arc.getHeight());
                stream.writeDouble(arc.getAngleStart());
                stream.writeDouble(arc.getAngleExtent());
                stream.writeInt(arc.getArcType());
            }
            else if (shape instanceof GeneralPath) {
                stream.writeObject(GeneralPath.class);
                final PathIterator pi = shape.getPathIterator(null);
                final float[] args = new float[6];
                stream.writeBoolean(pi.isDone());
                while (!pi.isDone()) {
                    final int type = pi.currentSegment(args);
                    stream.writeInt(type);
                    // TODO: could write this to only stream the values
                    // required for the segment type
                    for (int i = 0; i < 6; i++) {
                        stream.writeFloat(args[i]);
                    }
                    stream.writeInt(pi.getWindingRule());
                    pi.next();
                    stream.writeBoolean(pi.isDone());
                }
            }
            else {
                stream.writeObject(shape.getClass());
                stream.writeObject(shape);
            }
        }
        else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a {@code Point2D} object that has been serialised by the
     * {@link #writePoint2D(Point2D, ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The point object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     */
    public static Point2D readPoint2D(ObjectInputStream stream)
            throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        Point2D result = null;
        boolean isNull = stream.readBoolean();
        if (!isNull) {
            double x = stream.readDouble();
            double y = stream.readDouble();
            result = new Point2D.Double(x, y);
        }
        return result;

    }

    /**
     * Serialises a {@code Point2D} object.
     *
     * @param p  the point object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writePoint2D(Point2D p, ObjectOutputStream stream)
            throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (p != null) {
            stream.writeBoolean(false);
            stream.writeDouble(p.getX());
            stream.writeDouble(p.getY());
        }
        else {
            stream.writeBoolean(true);
        }
    }

    /**
     * Reads a {@code AttributedString} object that has been serialised by
     * the {@link #writeAttributedString(AttributedString,
     * ObjectOutputStream)} method.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @return The attributed string object (possibly {@code null}).
     *
     * @throws IOException  if there is an I/O problem.
     * @throws ClassNotFoundException  if there is a problem loading a class.
     */
    public static AttributedString readAttributedString(
            ObjectInputStream stream)
            throws IOException, ClassNotFoundException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        AttributedString result = null;
        final boolean isNull = stream.readBoolean();
        if (!isNull) {
            // read string and attributes then create result
            String plainStr = (String) stream.readObject();
            result = new AttributedString(plainStr);
            char c = stream.readChar();
            int start = 0;
            while (c != CharacterIterator.DONE) {
                int limit = stream.readInt();
                Map atts = (Map) stream.readObject();
                result.addAttributes(atts, start, limit);
                start = limit;
                c = stream.readChar();
            }
        }
        return result;
    }

    /**
     * Serialises an {@code AttributedString} object.
     *
     * @param as  the attributed string object ({@code null} permitted).
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException if there is an I/O error.
     */
    public static void writeAttributedString(AttributedString as,
            ObjectOutputStream stream) throws IOException {

        if (stream == null) {
            throw new IllegalArgumentException("Null 'stream' argument.");
        }
        if (as != null) {
            stream.writeBoolean(false);
            AttributedCharacterIterator aci = as.getIterator();
            // build a plain string from aci
            // then write the string
            StringBuffer plainStr = new StringBuffer();
            char current = aci.first();
            while (current != CharacterIterator.DONE) {
                plainStr = plainStr.append(current);
                current = aci.next();
            }
            stream.writeObject(plainStr.toString());

            // then write the attributes and limits for each run
            current = aci.first();
            int begin = aci.getBeginIndex();
            while (current != CharacterIterator.DONE) {
                // write the current character - when the reader sees that this
                // is not CharacterIterator.DONE, it will know to read the
                // run limits and attributes
                stream.writeChar(current);

                // now write the limit, adjusted as if beginIndex is zero
                int limit = aci.getRunLimit();
                stream.writeInt(limit - begin);

                // now write the attribute set
                Map atts = new HashMap(aci.getAttributes());
                stream.writeObject(atts);
                current = aci.setIndex(limit);
            }
            // write a character that signals to the reader that all runs
            // are done...
            stream.writeChar(CharacterIterator.DONE);
        }
        else {
            // write a flag that indicates a null
            stream.writeBoolean(true);
        }

    }

}
