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
 * -----------
 * Marker.java
 * -----------
 * (C) Copyright 2002-2017, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Nicolas Brodu;
 *
 * Changes
 * -------
 * 02-Jul-2002 : Added extra constructor, standard header and Javadoc
 *               comments (DG);
 * 20-Aug-2002 : Added the outline stroke attribute (DG);
 * 02-Oct-2002 : Fixed errors reported by Checkstyle (DG);
 * 16-Oct-2002 : Added new constructor (DG);
 * 26-Mar-2003 : Implemented Serializable (DG);
 * 21-May-2003 : Added labels (DG);
 * 11-Sep-2003 : Implemented Cloneable (NB);
 * 05-Nov-2003 : Added checks to ensure some attributes are never null (DG);
 * 11-Feb-2003 : Moved to org.jfree.chart.plot package, plus significant API
 *               changes to support IntervalMarker in plots (DG);
 * 14-Jun-2004 : Updated equals() method (DG);
 * 21-Jan-2005 : Added settings to control direction of horizontal and
 *               vertical label offsets (DG);
 * 01-Jun-2005 : Modified to use only one label offset type - this will be
 *               applied to the domain or range axis as appropriate (DG);
 * 06-Jun-2005 : Fix equals() method to handle GradientPaint (DG);
 * 19-Aug-2005 : Changed constructor from public --> protected (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 05-Sep-2006 : Added MarkerChangeListener support (DG);
 * 26-Sep-2007 : Fix for serialization bug 1802195 (DG);
 * 02-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.chart.plot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.EventListener;

import javax.swing.event.EventListenerList;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.ui.LengthAdjustmentType;
import org.jfree.chart.ui.RectangleAnchor;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.ObjectUtils;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.SerialUtils;

/**
 * The base class for markers that can be added to plots to highlight a value
 * or range of values.
 * <br><br>
 * An event notification mechanism was added to this class in JFreeChart
 * version 1.0.3.
 */
public abstract class Marker implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -734389651405327166L;

    /** The paint (null is not allowed). */
    private transient Paint paint;

    /** The stroke (null is not allowed). */
    private transient Stroke stroke;

    /** The outline paint. */
    private transient Paint outlinePaint;

    /** The outline stroke. */
    private transient Stroke outlineStroke;

    /** The alpha transparency. */
    private float alpha;

    /** The label. */
    private String label = null;

    /** The label font. */
    private Font labelFont;

    /** The label paint. */
    private transient Paint labelPaint;
    
    /** The label background color. */
    private Color labelBackgroundColor;

    /** The label position. */
    private RectangleAnchor labelAnchor;

    /** The text anchor for the label. */
    private TextAnchor labelTextAnchor;

    /** The label offset from the marker rectangle. */
    private RectangleInsets labelOffset;

    /**
     * The offset type for the domain or range axis (never {@code null}).
     */
    private LengthAdjustmentType labelOffsetType;

    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * Creates a new marker with default attributes.
     */
    protected Marker() {
        this(Color.GRAY);
    }

    /**
     * Constructs a new marker.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    protected Marker(Paint paint) {
        this(paint, new BasicStroke(0.5f), Color.GRAY, new BasicStroke(0.5f),
                0.80f);
    }

    /**
     * Constructs a new marker.
     *
     * @param paint  the paint ({@code null} not permitted).
     * @param stroke  the stroke ({@code null} not permitted).
     * @param outlinePaint  the outline paint ({@code null} permitted).
     * @param outlineStroke  the outline stroke ({@code null} permitted).
     * @param alpha  the alpha transparency (must be in the range 0.0f to
     *     1.0f).
     *
     * @throws IllegalArgumentException if {@code paint} or
     *     {@code stroke} is {@code null}, or {@code alpha} is
     *     not in the specified range.
     */
    protected Marker(Paint paint, Stroke stroke, Paint outlinePaint, 
            Stroke outlineStroke, float alpha) {

        Args.nullNotPermitted(paint, "paint");
        Args.nullNotPermitted(stroke, "stroke");
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException(
                    "The 'alpha' value must be in the range 0.0f to 1.0f");
        }

        this.paint = paint;
        this.stroke = stroke;
        this.outlinePaint = outlinePaint;
        this.outlineStroke = outlineStroke;
        this.alpha = alpha;

        this.labelFont = new Font("SansSerif", Font.PLAIN, 9);
        this.labelPaint = Color.BLACK;
        this.labelBackgroundColor = new Color(100, 100, 100, 100);
        this.labelAnchor = RectangleAnchor.TOP_LEFT;
        this.labelOffset = new RectangleInsets(3.0, 3.0, 3.0, 3.0);
        this.labelOffsetType = LengthAdjustmentType.CONTRACT;
        this.labelTextAnchor = TextAnchor.CENTER;

        this.listenerList = new EventListenerList();
    }

    /**
     * Returns the paint.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setPaint(Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint and sends a {@link MarkerChangeEvent} to all registered
     * listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.paint = paint;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the stroke.
     *
     * @return The stroke (never {@code null}).
     *
     * @see #setStroke(Stroke)
     */
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke and sends a {@link MarkerChangeEvent} to all registered
     * listeners.
     *
     * @param stroke  the stroke ({@code null}not permitted).
     *
     * @see #getStroke()
     */
    public void setStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.stroke = stroke;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the outline paint.
     *
     * @return The outline paint (possibly {@code null}).
     *
     * @see #setOutlinePaint(Paint)
     */
    public Paint getOutlinePaint() {
        return this.outlinePaint;
    }

    /**
     * Sets the outline paint and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param paint  the paint ({@code null} permitted).
     *
     * @see #getOutlinePaint()
     */
    public void setOutlinePaint(Paint paint) {
        this.outlinePaint = paint;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the outline stroke.
     *
     * @return The outline stroke (possibly {@code null}).
     *
     * @see #setOutlineStroke(Stroke)
     */
    public Stroke getOutlineStroke() {
        return this.outlineStroke;
    }

    /**
     * Sets the outline stroke and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param stroke  the stroke ({@code null} permitted).
     *
     * @see #getOutlineStroke()
     */
    public void setOutlineStroke(Stroke stroke) {
        this.outlineStroke = stroke;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the alpha transparency.
     *
     * @return The alpha transparency.
     *
     * @see #setAlpha(float)
     */
    public float getAlpha() {
        return this.alpha;
    }

    /**
     * Sets the alpha transparency that should be used when drawing the
     * marker, and sends a {@link MarkerChangeEvent} to all registered
     * listeners.  The alpha transparency is a value in the range 0.0f
     * (completely transparent) to 1.0f (completely opaque).
     *
     * @param alpha  the alpha transparency (must be in the range 0.0f to
     *     1.0f).
     *
     * @throws IllegalArgumentException if {@code alpha} is not in the
     *     specified range.
     *
     * @see #getAlpha()
     */
    public void setAlpha(float alpha) {
        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException(
                    "The 'alpha' value must be in the range 0.0f to 1.0f");
        }
        this.alpha = alpha;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label (if {@code null} no label is displayed).
     *
     * @return The label (possibly {@code null}).
     *
     * @see #setLabel(String)
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Sets the label (if {@code null} no label is displayed) and sends a
     * {@link MarkerChangeEvent} to all registered listeners.
     *
     * @param label  the label ({@code null} permitted).
     *
     * @see #getLabel()
     */
    public void setLabel(String label) {
        this.label = label;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label font.
     *
     * @return The label font (never {@code null}).
     *
     * @see #setLabelFont(Font)
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Sets the label font and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param font  the font ({@code null} not permitted).
     *
     * @see #getLabelFont()
     */
    public void setLabelFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.labelFont = font;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label paint.
     *
     * @return The label paint (never {@code null}).
     *
     * @see #setLabelPaint(Paint)
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Sets the label paint and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param paint  the paint ({@code null} not permitted).
     *
     * @see #getLabelPaint()
     */
    public void setLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.labelPaint = paint;
        notifyListeners(new MarkerChangeEvent(this));
    }
    
    /**
     * Returns the label background color.  The default value is 
     * {@code Color(100, 100, 100, 100)}..
     * 
     * @return The label background color (never {@code null}).
     * 
     * @since 1.0.18
     */
    public Color getLabelBackgroundColor() {
        return this.labelBackgroundColor;
    }

    /**
     * Sets the label background color.
     * 
     * @param color  the color ({@code null} not permitted).
     * 
     * @since 1.0.18
     */
    public void setLabelBackgroundColor(Color color) {
        Args.nullNotPermitted(color, "color");
        this.labelBackgroundColor = color;
    }

    /**
     * Returns the label anchor.  This defines the position of the label
     * anchor, relative to the bounds of the marker.
     *
     * @return The label anchor (never {@code null}).
     *
     * @see #setLabelAnchor(RectangleAnchor)
     */
    public RectangleAnchor getLabelAnchor() {
        return this.labelAnchor;
    }

    /**
     * Sets the label anchor and sends a {@link MarkerChangeEvent} to all
     * registered listeners.  The anchor defines the position of the label
     * anchor, relative to the bounds of the marker.
     *
     * @param anchor  the anchor ({@code null} not permitted).
     *
     * @see #getLabelAnchor()
     */
    public void setLabelAnchor(RectangleAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.labelAnchor = anchor;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label offset.
     *
     * @return The label offset (never {@code null}).
     *
     * @see #setLabelOffset(RectangleInsets)
     */
    public RectangleInsets getLabelOffset() {
        return this.labelOffset;
    }

    /**
     * Sets the label offset and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param offset  the label offset ({@code null} not permitted).
     *
     * @see #getLabelOffset()
     */
    public void setLabelOffset(RectangleInsets offset) {
        Args.nullNotPermitted(offset, "offset");
        this.labelOffset = offset;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label offset type.
     *
     * @return The type (never {@code null}).
     *
     * @see #setLabelOffsetType(LengthAdjustmentType)
     */
    public LengthAdjustmentType getLabelOffsetType() {
        return this.labelOffsetType;
    }

    /**
     * Sets the label offset type and sends a {@link MarkerChangeEvent} to all
     * registered listeners.
     *
     * @param adj  the type ({@code null} not permitted).
     *
     * @see #getLabelOffsetType()
     */
    public void setLabelOffsetType(LengthAdjustmentType adj) {
        Args.nullNotPermitted(adj, "adj");
        this.labelOffsetType = adj;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Returns the label text anchor.
     *
     * @return The label text anchor (never {@code null}).
     *
     * @see #setLabelTextAnchor(TextAnchor)
     */
    public TextAnchor getLabelTextAnchor() {
        return this.labelTextAnchor;
    }

    /**
     * Sets the label text anchor and sends a {@link MarkerChangeEvent} to
     * all registered listeners.
     *
     * @param anchor  the label text anchor ({@code null} not permitted).
     *
     * @see #getLabelTextAnchor()
     */
    public void setLabelTextAnchor(TextAnchor anchor) {
        Args.nullNotPermitted(anchor, "anchor");
        this.labelTextAnchor = anchor;
        notifyListeners(new MarkerChangeEvent(this));
    }

    /**
     * Registers an object for notification of changes to the marker.
     *
     * @param listener  the object to be registered.
     *
     * @see #removeChangeListener(MarkerChangeListener)
     *
     * @since 1.0.3
     */
    public void addChangeListener(MarkerChangeListener listener) {
        this.listenerList.add(MarkerChangeListener.class, listener);
    }

    /**
     * Unregisters an object for notification of changes to the marker.
     *
     * @param listener  the object to be unregistered.
     *
     * @see #addChangeListener(MarkerChangeListener)
     *
     * @since 1.0.3
     */
    public void removeChangeListener(MarkerChangeListener listener) {
        this.listenerList.remove(MarkerChangeListener.class, listener);
    }

    /**
     * Notifies all registered listeners that the marker has been modified.
     *
     * @param event  information about the change event.
     *
     * @since 1.0.3
     */
    public void notifyListeners(MarkerChangeEvent event) {

        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == MarkerChangeListener.class) {
                ((MarkerChangeListener) listeners[i + 1]).markerChanged(event);
            }
        }

    }

    /**
     * Returns an array containing all the listeners of the specified type.
     *
     * @param listenerType  the listener type.
     *
     * @return The array of listeners.
     *
     * @since 1.0.3
     */
    public EventListener[] getListeners(Class listenerType) {
        return this.listenerList.getListeners(listenerType);
    }

    /**
     * Tests the marker for equality with an arbitrary object.
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
        if (!(obj instanceof Marker)) {
            return false;
        }
        Marker that = (Marker) obj;
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (!ObjectUtils.equal(this.stroke, that.stroke)) {
            return false;
        }
        if (!PaintUtils.equal(this.outlinePaint, that.outlinePaint)) {
            return false;
        }
        if (!ObjectUtils.equal(this.outlineStroke, that.outlineStroke)) {
            return false;
        }
        if (this.alpha != that.alpha) {
            return false;
        }
        if (!ObjectUtils.equal(this.label, that.label)) {
            return false;
        }
        if (!ObjectUtils.equal(this.labelFont, that.labelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelPaint, that.labelPaint)) {
            return false;
        }
        if (!this.labelBackgroundColor.equals(that.labelBackgroundColor)) {
            return false;
        }
        if (this.labelAnchor != that.labelAnchor) {
            return false;
        }
        if (this.labelTextAnchor != that.labelTextAnchor) {
            return false;
        }
        if (!ObjectUtils.equal(this.labelOffset, that.labelOffset)) {
            return false;
        }
        if (!this.labelOffsetType.equals(that.labelOffsetType)) {
            return false;
        }
        return true;
    }

    /**
     * Creates a clone of the marker.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException never.
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
        SerialUtils.writePaint(this.paint, stream);
        SerialUtils.writeStroke(this.stroke, stream);
        SerialUtils.writePaint(this.outlinePaint, stream);
        SerialUtils.writeStroke(this.outlineStroke, stream);
        SerialUtils.writePaint(this.labelPaint, stream);
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
        this.paint = SerialUtils.readPaint(stream);
        this.stroke = SerialUtils.readStroke(stream);
        this.outlinePaint = SerialUtils.readPaint(stream);
        this.outlineStroke = SerialUtils.readStroke(stream);
        this.labelPaint = SerialUtils.readPaint(stream);
        this.listenerList = new EventListenerList();
    }

}
