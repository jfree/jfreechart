/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * --------------
 * TextTitle.java
 * --------------
 * (C) Copyright 2000-present, by David Berry and Contributors.
 *
 * Original Author:  David Berry;
 * Contributor(s):   David Gilbert;
 *                   Nicolas Brodu;
 *                   Peter Kolb - patch 2603321;
 */

package org.jfree.chart.title;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

import org.jfree.chart.api.HorizontalAlignment;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.api.VerticalAlignment;
import org.jfree.chart.block.BlockResult;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.SerialUtils;

/**
 * A chart title that displays a text string with automatic wrapping as
 * required.
 */

public class TextTitle extends Title implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8372008692127477443L;

    /** The default font. */
    public static final Font DEFAULT_FONT = new Font("SansSerif", Font.BOLD,
            12);

    /** The default text color. */
    public static final Paint DEFAULT_TEXT_PAINT = Color.BLACK;

    /** The title text. */
    private String text;

    /** The font used to display the title. */
    private Font font;

    /** The text alignment. */
    private HorizontalAlignment textAlignment;

    /** The paint used to display the title text. */
    private transient Paint paint;

    /** The background paint. */
    private transient Paint backgroundPaint;

    /** The tool tip text (can be {@code null}). */
    private String toolTipText;

    /** The URL text (can be {@code null}). */
    private String urlText;

    /**
     * A flag that controls whether the title expands to fit the available
     * space..
     */
    private boolean expandToFitSpace = false;

    /**
     * The maximum number of lines to display.
     */
    private int maximumLinesToDisplay = Integer.MAX_VALUE;

    /**
     * Creates a new title, using default attributes where necessary.
     */
    public TextTitle() {
        this("", DEFAULT_FONT, DEFAULT_TEXT_PAINT);
    }

    /**
     * Creates a new title, using default attributes where necessary.
     *
     * @param text  the title text ({@code null} not permitted).
     */
    public TextTitle(String text) {
        this(text, DEFAULT_FONT, DEFAULT_TEXT_PAINT);
    }

    /**
     * Creates a new title, using default attributes where necessary.
     *
     * @param text  the title text ({@code null} not permitted).
     * @param font  the title font ({@code null} not permitted).
     */
    public TextTitle(String text, Font font) {
        this(text, font, DEFAULT_TEXT_PAINT);
    }

    private TextTitle(String text, Font font, Paint paint) {
        this(text, font, paint, RectangleEdge.TOP, HorizontalAlignment.CENTER,
                VerticalAlignment.CENTER, Title.DEFAULT_PADDING);
    }

    public TextTitle(String text, Font font, Paint paint, RectangleEdge position,
                     HorizontalAlignment horizontalAlignment,
                     VerticalAlignment verticalAlignment, RectangleInsets padding) {

        super(position, horizontalAlignment, verticalAlignment, padding);
        Args.nullNotPermitted(text, "text");
        Args.nullNotPermitted(font, "font");
        Args.nullNotPermitted(paint, "paint");
        this.text = text;
        this.font = font;
        this.paint = paint;
        // the textAlignment and the horizontalAlignment are separate things,
        // but it makes sense for the default textAlignment to match the
        // title's horizontal alignment...
        this.textAlignment = horizontalAlignment;
        this.backgroundPaint = null;
        this.toolTipText = null;
        this.urlText = null;
    }

    TextTitle(TextTitleBuilder builder) {
        super(builder.position, builder.horizontalAlignment,
                builder.verticalAlignment, builder.padding);
        this.text = builder.text;
        this.font = builder.font;
        this.paint = builder.paint;
        this.textAlignment = builder.horizontalAlignment;
        this.backgroundPaint = builder.backgroundPaint;
        this.expandToFitSpace = builder.expandToFitSpace;
        this.maximumLinesToDisplay = builder.maximumLinesToDisplay;
        this.toolTipText = null;
        this.urlText = null;
    }

    public TextTitle(String text, TextStyle style, TitlePosition position) {
        super(position.getPosition(), position.getHorizontalAlignment(),
                position.getVerticalAlignment(), position.getPadding());
        Args.nullNotPermitted(text, "text");
        Args.nullNotPermitted(style, "style");
        Args.nullNotPermitted(position, "position");
        this.text = text;
        this.font = style.getFont();
        this.paint = style.getPaint();
        this.backgroundPaint = style.getBackgroundPaint();
        this.textAlignment = position.getHorizontalAlignment();
        this.toolTipText = null;
        this.urlText = null;
    }

    /**
     * Returns the title text.
     *
     * @return The text (never {@code null}).
     *
     * @see #setText(String)
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the title to the specified text and sends a
     * {@link TitleChangeEvent} to all registered listeners.
     *
     * @param text  the text ({@code null} not permitted).
     */
    public void setText(String text) {
        Args.nullNotPermitted(text, "text");
        if (!this.text.equals(text)) {
            this.text = text;
            notifyListeners(new TitleChangeEvent(this));
        }
    }

    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font used to display the title string.  Registered listeners
     * are notified that the title has been modified.
     *
     * @param font  the new font ({@code null} not permitted).
     *
     * @see #getFont()
     */
    public void setFont(Font font) {
        Args.nullNotPermitted(font, "font");
        if (!this.font.equals(font)) {
            this.font = font;
            notifyListeners(new TitleChangeEvent(this));
        }
    }

    /**
     * Returns the paint used to display the title string.
     *
     * @return The paint (never {@code null}).
     *
     * @see #setPaint(Paint)
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint used to display the title string.  Registered listeners
     * are notified that the title has been modified.
     *
     * @param paint  the new paint ({@code null} not permitted).
     *
     * @see #getPaint()
     */
    public void setPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        if (!this.paint.equals(paint)) {
            this.paint = paint;
            notifyListeners(new TitleChangeEvent(this));
        }
    }

    public HorizontalAlignment getTextAlignment() {
        return this.textAlignment;
    }

    public void setTextAlignment(HorizontalAlignment alignment) {
        Args.nullNotPermitted(alignment, "alignment");
        this.textAlignment = alignment;
        notifyListeners(new TitleChangeEvent(this));
    }

    public Paint getBackgroundPaint() {
        return this.backgroundPaint;
    }

    /**
     * Sets the background paint and sends a {@link TitleChangeEvent} to all
     * registered listeners.  If you set this attribute to {@code null},
     * no background is painted (which makes the title background transparent).
     *
     * @param paint  the background paint ({@code null} permitted).
     */
    public void setBackgroundPaint(Paint paint) {
        this.backgroundPaint = paint;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Returns the tool tip text.
     *
     * @return The tool tip text (possibly {@code null}).
     */
    public String getToolTipText() {
        return this.toolTipText;
    }

    /**
     * Sets the tool tip text to the specified text and sends a
     * {@link TitleChangeEvent} to all registered listeners.
     *
     * @param text  the text ({@code null} permitted).
     */
    public void setToolTipText(String text) {
        this.toolTipText = text;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Returns the URL text.
     *
     * @return The URL text (possibly {@code null}).
     */
    public String getURLText() {
        return this.urlText;
    }

    /**
     * Sets the URL text to the specified text and sends a
     * {@link TitleChangeEvent} to all registered listeners.
     *
     * @param text  the text ({@code null} permitted).
     */
    public void setURLText(String text) {
        this.urlText = text;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Returns the flag that controls whether the title expands to fit
     * the available space.
     *
     * @return The flag.
     */
    public boolean getExpandToFitSpace() {
        return this.expandToFitSpace;
    }

    /**
     * Sets the flag that controls whether the title expands to fit the
     * available space, and sends a {@link TitleChangeEvent} to all registered
     * listeners.
     *
     * @param expand  the flag.
     */
    public void setExpandToFitSpace(boolean expand) {
        this.expandToFitSpace = expand;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Returns the maximum number of lines to display.
     *
     * @return The maximum.
     *
     * @see #setMaximumLinesToDisplay(int)
     */
    public int getMaximumLinesToDisplay() {
        return this.maximumLinesToDisplay;
    }

    public void setMaximumLinesToDisplay(int max) {
        this.maximumLinesToDisplay = max;
        notifyListeners(new TitleChangeEvent(this));
    }

    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        draw(g2, area, null);
    }

    @Override
    public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(area);
        }
        getPosition();
        RectangleInsets padding = getPadding();
        g2.setFont(this.font);
        g2.setPaint(this.paint);

        BlockResult result = new BlockResult();
        if (params instanceof EntityCollection) {
            EntityCollection ec = (EntityCollection) params;
            if (this.toolTipText != null || this.urlText != null) {
                ChartEntity entity = new ChartEntity(area, this.toolTipText,
                        this.urlText);
                ec.add(entity);
            }
            result.setEntityCollection(ec);
        }
        return result;
    }

    /**
     * Tests this title for equality with another object.
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
        if (!(obj instanceof TextTitle)) {
            return false;
        }
        TextTitle that = (TextTitle) obj;
        if (!Objects.equals(this.text, that.text)) {
            return false;
        }
        if (!Objects.equals(this.font, that.font)) {
            return false;
        }
        if (!PaintUtils.equal(this.paint, that.paint)) {
            return false;
        }
        if (this.textAlignment != that.textAlignment) {
            return false;
        }
        if (!PaintUtils.equal(this.backgroundPaint, that.backgroundPaint)) {
            return false;
        }
        if (this.maximumLinesToDisplay != that.maximumLinesToDisplay) {
            return false;
        }
        if (this.expandToFitSpace != that.expandToFitSpace) {
            return false;
        }
        if (!Objects.equals(this.toolTipText, that.toolTipText)) {
            return false;
        }
        if (!Objects.equals(this.urlText, that.urlText)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hash code.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (this.text != null ? this.text.hashCode() : 0);
        result = 29 * result + (this.font != null ? this.font.hashCode() : 0);
        result = 29 * result + (this.paint != null ? this.paint.hashCode() : 0);
        return result;
    }

    /**
     * Returns a clone of this object.
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
        SerialUtils.writePaint(this.backgroundPaint, stream);
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
        this.backgroundPaint = SerialUtils.readPaint(stream);
    }

    public static TextTitleBuilder builder(String text) {
        return new TextTitleBuilder(text);
    }

    public static class TextTitleBuilder {
        private final String text;
        private Font font = DEFAULT_FONT;
        private Paint paint = DEFAULT_TEXT_PAINT;
        private Paint backgroundPaint = null;
        private RectangleEdge position = RectangleEdge.TOP;
        private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;
        private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
        private RectangleInsets padding = Title.DEFAULT_PADDING;
        private boolean expandToFitSpace = false;
        private int maximumLinesToDisplay = Integer.MAX_VALUE;

        public TextTitleBuilder(String text) {
            Args.nullNotPermitted(text, "text");
            this.text = text;
        }

        public TextTitleBuilder font(Font font) {
            Args.nullNotPermitted(font, "font");
            this.font = font;
            return this;
        }

        public TextTitleBuilder paint(Paint paint) {
            Args.nullNotPermitted(paint, "paint");
            this.paint = paint;
            return this;
        }

        public TextTitleBuilder backgroundPaint(Paint paint) {
            this.backgroundPaint = paint;
            return this;
        }

        public TextTitleBuilder position(RectangleEdge position) {
            Args.nullNotPermitted(position, "position");
            this.position = position;
            return this;
        }

        public TextTitleBuilder horizontalAlignment(HorizontalAlignment alignment) {
            Args.nullNotPermitted(alignment, "alignment");
            this.horizontalAlignment = alignment;
            return this;
        }

        public TextTitleBuilder verticalAlignment(VerticalAlignment alignment) {
            Args.nullNotPermitted(alignment, "alignment");
            this.verticalAlignment = alignment;
            return this;
        }

        public TextTitleBuilder padding(RectangleInsets padding) {
            Args.nullNotPermitted(padding, "padding");
            this.padding = padding;
            return this;
        }

        public TextTitleBuilder expandToFitSpace(boolean expand) {
            this.expandToFitSpace = expand;
            return this;
        }

        public TextTitleBuilder maximumLinesToDisplay(int max) {
            this.maximumLinesToDisplay = max;
            return this;
        }

        public TextTitle build() {
            return new TextTitle(this);
        }
    }

    public static class TextStyle implements Serializable {
        private static final long serialVersionUID = 1L;
        private final Font font;
        private final Paint paint;
        private final Paint backgroundPaint;

        public TextStyle(Font font, Paint paint) {
            this(font, paint, null);
        }

        public TextStyle(Font font, Paint paint, Paint backgroundPaint) {
            Args.nullNotPermitted(font, "font");
            Args.nullNotPermitted(paint, "paint");
            this.font = font;
            this.paint = paint;
            this.backgroundPaint = backgroundPaint;
        }

        public Font getFont() {
            return this.font;
        }

        public Paint getPaint() {
            return this.paint;
        }

        public Paint getBackgroundPaint() {
            return this.backgroundPaint;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TextStyle)) {
                return false;
            }
            TextStyle that = (TextStyle) obj;
            return Objects.equals(this.font, that.font)
                    && PaintUtils.equal(this.paint, that.paint)
                    && PaintUtils.equal(this.backgroundPaint, that.backgroundPaint);
        }

        @Override
        public int hashCode() {
            return Objects.hash(font, paint);
        }
    }

    public static class TitlePosition implements Serializable {
        private static final long serialVersionUID = 1L;
        private final RectangleEdge position;
        private final HorizontalAlignment horizontalAlignment;
        private final VerticalAlignment verticalAlignment;
        private final RectangleInsets padding;

        public TitlePosition(RectangleEdge position,
                             HorizontalAlignment horizontalAlignment,
                             VerticalAlignment verticalAlignment,
                             RectangleInsets padding) {
            Args.nullNotPermitted(position, "position");
            Args.nullNotPermitted(horizontalAlignment, "horizontalAlignment");
            Args.nullNotPermitted(verticalAlignment, "verticalAlignment");
            Args.nullNotPermitted(padding, "padding");
            this.position = position;
            this.horizontalAlignment = horizontalAlignment;
            this.verticalAlignment = verticalAlignment;
            this.padding = padding;
        }

        public RectangleEdge getPosition() {
            return this.position;
        }

        public HorizontalAlignment getHorizontalAlignment() {
            return this.horizontalAlignment;
        }

        public VerticalAlignment getVerticalAlignment() {
            return this.verticalAlignment;
        }

        public RectangleInsets getPadding() {
            return this.padding;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TitlePosition)) {
                return false;
            }
            TitlePosition that = (TitlePosition) obj;
            return this.position == that.position
                    && this.horizontalAlignment == that.horizontalAlignment
                    && this.verticalAlignment == that.verticalAlignment
                    && Objects.equals(this.padding, that.padding);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, horizontalAlignment,
                    verticalAlignment, padding);
        }
    }
}