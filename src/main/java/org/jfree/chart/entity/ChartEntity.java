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
 * ----------------
 * ChartEntity.java
 * ----------------
 * (C) Copyright 2002-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Richard Atkinson;
 *                   Xavier Poinsard;
 *                   Robert Fuller;
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.imagemap.ToolTipTagFragmentGenerator;
import org.jfree.chart.imagemap.URLTagFragmentGenerator;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;

/**
 * A class that captures information about some component of a chart (a bar,
 * line etc).
 */
public class ChartEntity implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4445994133561919083L;

    /** The area occupied by the entity (in Java 2D space). */
    private transient Shape area;

    /** The tool tip text for the entity. */
    private String toolTipText;

    /** The URL text for the entity. */
    private String urlText;

    /**
     * Creates a new chart entity.
     *
     * @param area  the area ({@code null} not permitted).
     */
    public ChartEntity(Shape area) {
        // defer argument checks...
        this(area, null);
    }

    /**
     * Creates a new chart entity.
     *
     * @param area  the area ({@code null} not permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     */
    public ChartEntity(Shape area, String toolTipText) {
        // defer argument checks...
        this(area, toolTipText, null);
    }

    /**
     * Creates a new entity.
     *
     * @param area  the area ({@code null} not permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text for HTML image maps ({@code null}
     *                 permitted).
     */
    public ChartEntity(Shape area, String toolTipText, String urlText) {
        Args.nullNotPermitted(area, "area");
        this.area = area;
        this.toolTipText = toolTipText;
        this.urlText = urlText;
    }

    /**
     * Returns the area occupied by the entity (in Java 2D space).
     *
     * @return The area (never {@code null}).
     */
    public Shape getArea() {
        return this.area;
    }

    /**
     * Sets the area for the entity.
     * <P>
     * This class conveys information about chart entities back to a client.
     * Setting this area doesn't change the entity (which has already been
     * drawn).
     *
     * @param area  the area ({@code null} not permitted).
     */
    public void setArea(Shape area) {
        Args.nullNotPermitted(area, "area");
        this.area = area;
    }

    /**
     * Returns the tool tip text for the entity.  Be aware that this text
     * may have been generated from user supplied data, so for security
     * reasons some form of filtering should be applied before incorporating
     * this text into any HTML output.
     *
     * @return The tool tip text (possibly {@code null}).
     */
    public String getToolTipText() {
        return this.toolTipText;
    }

    /**
     * Sets the tool tip text.
     *
     * @param text  the text ({@code null} permitted).
     */
    public void setToolTipText(String text) {
        this.toolTipText = text;
    }

    /**
     * Returns the URL text for the entity.  Be aware that this text
     * may have been generated from user supplied data, so some form of
     * filtering should be applied before this "URL" is used in any output.
     *
     * @return The URL text (possibly {@code null}).
     */
    public String getURLText() {
        return this.urlText;
    }

    /**
     * Sets the URL text.
     *
     * @param text the text ({@code null} permitted).
     */
    public void setURLText(String text) {
        this.urlText = text;
    }

    /**
     * Returns a string describing the entity area.  This string is intended
     * for use in an AREA tag when generating an image map.
     *
     * @return The shape type (never {@code null}).
     */
    public String getShapeType() {
        if (this.area instanceof Rectangle2D) {
            return "rect";
        }
        else {
            return "poly";
        }
    }

    /**
     * Returns the shape coordinates as a string.
     *
     * @return The shape coordinates (never {@code null}).
     */
    public String getShapeCoords() {
        if (this.area instanceof Rectangle2D) {
            return getRectCoords((Rectangle2D) this.area);
        }
        else {
            return getPolyCoords(this.area);
        }
    }

    /**
     * Returns a string containing the coordinates (x1, y1, x2, y2) for a given
     * rectangle.  This string is intended for use in an image map.
     *
     * @param rectangle  the rectangle ({@code null} not permitted).
     *
     * @return Upper left and lower right corner of a rectangle.
     */
    private String getRectCoords(Rectangle2D rectangle) {
        Args.nullNotPermitted(rectangle, "rectangle");
        int x1 = (int) rectangle.getX();
        int y1 = (int) rectangle.getY();
        int x2 = x1 + (int) rectangle.getWidth();
        int y2 = y1 + (int) rectangle.getHeight();
        //      fix by rfuller
        if (x2 == x1) {
            x2++;
        }
        if (y2 == y1) {
            y2++;
        }
        //      end fix by rfuller
        return x1 + "," + y1 + "," + x2 + "," + y2;
    }

    /**
     * Returns a string containing the coordinates for a given shape.  This
     * string is intended for use in an image map.
     *
     * @param shape  the shape ({@code null} not permitted).
     *
     * @return The coordinates for a given shape as string.
     */
    private String getPolyCoords(Shape shape) {
        Args.nullNotPermitted(shape, "shape");
        StringBuilder result = new StringBuilder();
        boolean first = true;
        float[] coords = new float[6];
        PathIterator pi = shape.getPathIterator(null, 1.0);
        while (!pi.isDone()) {
            pi.currentSegment(coords);
            if (first) {
                first = false;
                result.append((int) coords[0]);
                result.append(",").append((int) coords[1]);
            }
            else {
                result.append(",");
                result.append((int) coords[0]);
                result.append(",");
                result.append((int) coords[1]);
            }
            pi.next();
        }
        return result.toString();
    }

    /**
     * Returns an HTML image map tag for this entity.  The returned fragment
     * should be {@code XHTML 1.0} compliant.
     *
     * @param toolTipTagFragmentGenerator  a generator for the HTML fragment
     *     that will contain the tooltip text ({@code null} not permitted
     *     if this entity contains tooltip information).
     * @param urlTagFragmentGenerator  a generator for the HTML fragment that
     *     will contain the URL reference ({@code null} not permitted if
     *     this entity has a URL).
     *
     * @return The HTML tag.
     */
    public String getImageMapAreaTag(
            ToolTipTagFragmentGenerator toolTipTagFragmentGenerator,
            URLTagFragmentGenerator urlTagFragmentGenerator) {

        StringBuilder tag = new StringBuilder();
        boolean hasURL = (this.urlText == null ? false
                : !this.urlText.equals(""));
        boolean hasToolTip = (this.toolTipText == null ? false
                : !this.toolTipText.equals(""));
        if (hasURL || hasToolTip) {
            tag.append("<area shape=\"").append(getShapeType()).append("\"")
                    .append(" coords=\"").append(getShapeCoords()).append("\"");
            if (hasToolTip) {
                tag.append(toolTipTagFragmentGenerator.generateToolTipFragment(
                        this.toolTipText));
            }
            if (hasURL) {
                tag.append(urlTagFragmentGenerator.generateURLFragment(
                        this.urlText));
            }
            else {
                tag.append(" nohref=\"nohref\"");
            }
            // if there is a tool tip, we expect it to generate the title and
            // alt values, so we only add an empty alt if there is no tooltip
            if (!hasToolTip) {
                tag.append(" alt=\"\"");
            }
            tag.append("/>");
        }
        return tag.toString();
    }

    /**
     * Returns a string representation of the chart entity, useful for
     * debugging.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ChartEntity: ");
        sb.append("tooltip = ");
        sb.append(this.toolTipText);
        return sb.toString();
    }

    /**
     * Tests the entity for equality with an arbitrary object.
     *
     * @param obj  the object to test against ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ChartEntity)) {
            return false;
        }
        ChartEntity that = (ChartEntity) obj;
        if (!this.area.equals(that.area)) {
            return false;
        }
        if (!Objects.equals(this.toolTipText, that.toolTipText)) {
            return false;
        }
        if (!Objects.equals(this.urlText, that.urlText)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = 37;
        result = HashUtils.hashCode(result, this.toolTipText);
        result = HashUtils.hashCode(result, this.urlText);
        return result;
    }

    /**
     * Returns a clone of the entity.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if there is a problem cloning the
     *         entity.
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
        SerialUtils.writeShape(this.area, stream);
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
        this.area = SerialUtils.readShape(stream);
    }

}
