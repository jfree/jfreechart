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
 * --------------
 * TextTitle.java
 * --------------
 * (C) Copyright 2000-2021, by David Berry and Contributors.
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

import org.jfree.chart.block.BlockResult;
import org.jfree.chart.block.EntityBlockParams;
import org.jfree.chart.block.LengthConstraintType;
import org.jfree.chart.block.RectangleConstraint;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.entity.TitleEntity;
import org.jfree.chart.event.TitleChangeEvent;
import org.jfree.chart.text.G2TextMeasurer;
import org.jfree.chart.text.TextBlock;
import org.jfree.chart.text.TextBlockAnchor;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.api.HorizontalAlignment;
import org.jfree.chart.api.RectangleEdge;
import org.jfree.chart.api.RectangleInsets;
import org.jfree.chart.block.Size2D;
import org.jfree.chart.api.VerticalAlignment;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.internal.Args;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.data.Range;

/**
 * A chart title that displays a text string with automatic wrapping as
 * required.
 */
public class TextTitle extends Title implements Serializable, Cloneable, 
        PublicCloneable {

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

    /** The content. */
    private TextBlock content;

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
        this("");
    }

    /**
     * Creates a new title, using default attributes where necessary.
     *
     * @param text  the title text ({@code null} not permitted).
     */
    public TextTitle(String text) {
        this(text, TextTitle.DEFAULT_FONT, TextTitle.DEFAULT_TEXT_PAINT,
                Title.DEFAULT_POSITION, Title.DEFAULT_HORIZONTAL_ALIGNMENT,
                Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
    }

    /**
     * Creates a new title, using default attributes where necessary.
     *
     * @param text  the title text ({@code null} not permitted).
     * @param font  the title font ({@code null} not permitted).
     */
    public TextTitle(String text, Font font) {
        this(text, font, TextTitle.DEFAULT_TEXT_PAINT, Title.DEFAULT_POSITION,
                Title.DEFAULT_HORIZONTAL_ALIGNMENT,
                Title.DEFAULT_VERTICAL_ALIGNMENT, Title.DEFAULT_PADDING);
    }

    /**
     * Creates a new title with the specified attributes.
     *
     * @param text  the text for the title ({@code null} not permitted).
     * @param font  the title font ({@code null} not permitted).
     * @param paint  the title paint ({@code null} not permitted).
     * @param position  the title position ({@code null} not permitted).
     * @param horizontalAlignment  the horizontal alignment ({@code null}
     *                             not permitted).
     * @param verticalAlignment  the vertical alignment ({@code null} not
     *                           permitted).
     * @param padding  the space to leave around the outside of the title.
     */
    public TextTitle(String text, Font font, Paint paint,
                     RectangleEdge position,
                     HorizontalAlignment horizontalAlignment,
                     VerticalAlignment verticalAlignment,
                     RectangleInsets padding) {
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
        this.content = null;
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

    /**
     * Returns the text alignment.  This controls how the text is aligned
     * within the title's bounds, whereas the title's horizontal alignment
     * controls how the title's bounding rectangle is aligned within the
     * drawing space.
     *
     * @return The text alignment.
     */
    public HorizontalAlignment getTextAlignment() {
        return this.textAlignment;
    }

    /**
     * Sets the text alignment and sends a {@link TitleChangeEvent} to
     * all registered listeners.
     *
     * @param alignment  the alignment ({@code null} not permitted).
     */
    public void setTextAlignment(HorizontalAlignment alignment) {
        Args.nullNotPermitted(alignment, "alignment");
        this.textAlignment = alignment;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Returns the font used to display the title string.
     *
     * @return The font (never {@code null}).
     *
     * @see #setFont(Font)
     */
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

    /**
     * Returns the background paint (defaults to {@code null} which makes the 
     * background transparent).
     *
     * @return The paint (possibly {@code null}).
     */
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
     * Returns the flag that controls whether or not the title expands to fit
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

    /**
     * Sets the maximum number of lines to display and sends a
     * {@link TitleChangeEvent} to all registered listeners.
     *
     * @param max  the maximum.
     *
     * @see #getMaximumLinesToDisplay()
     */
    public void setMaximumLinesToDisplay(int max) {
        this.maximumLinesToDisplay = max;
        notifyListeners(new TitleChangeEvent(this));
    }

    /**
     * Arranges the contents of the block, within the given constraints, and
     * returns the block size.
     *
     * @param g2  the graphics device.
     * @param constraint  the constraint ({@code null} not permitted).
     *
     * @return The block size (in Java2D units, never {@code null}).
     */
    @Override
    public Size2D arrange(Graphics2D g2, RectangleConstraint constraint) {
        RectangleConstraint cc = toContentConstraint(constraint);
        LengthConstraintType w = cc.getWidthConstraintType();
        LengthConstraintType h = cc.getHeightConstraintType();
        Size2D contentSize = null;
        if (w == LengthConstraintType.NONE) {
            if (h == LengthConstraintType.NONE) {
                contentSize = arrangeNN(g2);
            }
            else if (h == LengthConstraintType.RANGE) {
                throw new RuntimeException("Not yet implemented.");
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");
            }
        }
        else if (w == LengthConstraintType.RANGE) {
            if (h == LengthConstraintType.NONE) {
                contentSize = arrangeRN(g2, cc.getWidthRange());
            }
            else if (h == LengthConstraintType.RANGE) {
                contentSize = arrangeRR(g2, cc.getWidthRange(),
                        cc.getHeightRange());
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");
            }
        }
        else if (w == LengthConstraintType.FIXED) {
            if (h == LengthConstraintType.NONE) {
                contentSize = arrangeFN(g2, cc.getWidth());
            }
            else if (h == LengthConstraintType.RANGE) {
                throw new RuntimeException("Not yet implemented.");
            }
            else if (h == LengthConstraintType.FIXED) {
                throw new RuntimeException("Not yet implemented.");
            }
        }
        assert contentSize != null; // suppress compiler warning
        return new Size2D(calculateTotalWidth(contentSize.getWidth()),
                calculateTotalHeight(contentSize.getHeight()));
    }

    /**
     * Arranges the content for this title assuming no bounds on the width
     * or the height, and returns the required size.  This will reflect the
     * fact that a text title positioned on the left or right of a chart will
     * be rotated by 90 degrees.
     *
     * @param g2  the graphics target.
     *
     * @return The content size.
     */
    protected Size2D arrangeNN(Graphics2D g2) {
        Range max = new Range(0.0, Float.MAX_VALUE);
        return arrangeRR(g2, max, max);
    }

    /**
     * Arranges the content for this title assuming a fixed width and no bounds
     * on the height, and returns the required size.  This will reflect the
     * fact that a text title positioned on the left or right of a chart will
     * be rotated by 90 degrees.
     *
     * @param g2  the graphics target.
     * @param w  the width.
     *
     * @return The content size.
     */
    protected Size2D arrangeFN(Graphics2D g2, double w) {
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
            float maxWidth = (float) w;
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font,
                    this.paint, maxWidth, this.maximumLinesToDisplay,
                    new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);
            if (this.expandToFitSpace) {
                return new Size2D(maxWidth, contentSize.getHeight());
            }
            else {
                return contentSize;
            }
        }
        else if (position == RectangleEdge.LEFT || position
                == RectangleEdge.RIGHT) {
            float maxWidth = Float.MAX_VALUE;
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font,
                    this.paint, maxWidth, this.maximumLinesToDisplay,
                    new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);

            // transpose the dimensions, because the title is rotated
            if (this.expandToFitSpace) {
                return new Size2D(contentSize.getHeight(), maxWidth);
            }
            else {
                return new Size2D(contentSize.height, contentSize.width);
            }
        }
        else {
            throw new RuntimeException("Unrecognised exception.");
        }
    }

    /**
     * Arranges the content for this title assuming a range constraint for the
     * width and no bounds on the height, and returns the required size.  This
     * will reflect the fact that a text title positioned on the left or right
     * of a chart will be rotated by 90 degrees.
     *
     * @param g2  the graphics target.
     * @param widthRange  the range for the width.
     *
     * @return The content size.
     */
    protected Size2D arrangeRN(Graphics2D g2, Range widthRange) {
        Size2D s = arrangeNN(g2);
        if (widthRange.contains(s.getWidth())) {
            return s;
        }
        double ww = widthRange.constrain(s.getWidth());
        return arrangeFN(g2, ww);
    }

    /**
     * Returns the content size for the title.  This will reflect the fact that
     * a text title positioned on the left or right of a chart will be rotated
     * 90 degrees.
     *
     * @param g2  the graphics device.
     * @param widthRange  the width range.
     * @param heightRange  the height range.
     *
     * @return The content size.
     */
    protected Size2D arrangeRR(Graphics2D g2, Range widthRange,
            Range heightRange) {
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
            float maxWidth = (float) widthRange.getUpperBound();
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font,
                    this.paint, maxWidth, this.maximumLinesToDisplay,
                    new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);
            if (this.expandToFitSpace) {
                return new Size2D(maxWidth, contentSize.getHeight());
            }
            else {
                return contentSize;
            }
        }
        else if (position == RectangleEdge.LEFT || position
                == RectangleEdge.RIGHT) {
            float maxWidth = (float) heightRange.getUpperBound();
            g2.setFont(this.font);
            this.content = TextUtils.createTextBlock(this.text, this.font,
                    this.paint, maxWidth, this.maximumLinesToDisplay,
                    new G2TextMeasurer(g2));
            this.content.setLineAlignment(this.textAlignment);
            Size2D contentSize = this.content.calculateDimensions(g2);

            // transpose the dimensions, because the title is rotated
            if (this.expandToFitSpace) {
                return new Size2D(contentSize.getHeight(), maxWidth);
            }
            else {
                return new Size2D(contentSize.height, contentSize.width);
            }
        }
        else {
            throw new RuntimeException("Unrecognised exception.");
        }
    }

    /**
     * Draws the title on a Java 2D graphics device (such as the screen or a
     * printer).
     *
     * @param g2  the graphics device.
     * @param area  the area allocated for the title.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area) {
        draw(g2, area, null);
    }

    /**
     * Draws the block within the specified area.
     *
     * @param g2  the graphics device.
     * @param area  the area.
     * @param params  if this is an instance of {@link EntityBlockParams} it
     *                is used to determine whether or not an
     *                {@link EntityCollection} is returned by this method.
     *
     * @return An {@link EntityCollection} containing a chart entity for the
     *         title, or {@code null}.
     */
    @Override
    public Object draw(Graphics2D g2, Rectangle2D area, Object params) {
        if (this.content == null) {
            return null;
        }
        area = trimMargin(area);
        drawBorder(g2, area);
        if (this.text.equals("")) {
            return null;
        }
        ChartEntity entity = null;
        if (params instanceof EntityBlockParams) {
            EntityBlockParams p = (EntityBlockParams) params;
            if (p.getGenerateEntities()) {
                entity = new TitleEntity(area, this, this.toolTipText,
                        this.urlText);
            }
        }
        area = trimBorder(area);
        if (this.backgroundPaint != null) {
            g2.setPaint(this.backgroundPaint);
            g2.fill(area);
        }
        area = trimPadding(area);
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.TOP || position == RectangleEdge.BOTTOM) {
            drawHorizontal(g2, area);
        }
        else if (position == RectangleEdge.LEFT
                 || position == RectangleEdge.RIGHT) {
            drawVertical(g2, area);
        }
        BlockResult result = new BlockResult();
        if (entity != null) {
            StandardEntityCollection sec = new StandardEntityCollection();
            sec.add(entity);
            result.setEntityCollection(sec);
        }
        return result;
    }

    /**
     * Draws a the title horizontally within the specified area.  This method
     * will be called from the {@link #draw(Graphics2D, Rectangle2D) draw}
     * method.
     *
     * @param g2  the graphics device.
     * @param area  the area for the title.
     */
    protected void drawHorizontal(Graphics2D g2, Rectangle2D area) {
        Rectangle2D titleArea = (Rectangle2D) area.clone();
        g2.setFont(this.font);
        g2.setPaint(this.paint);
        TextBlockAnchor anchor = null;
        float x = 0.0f;
        HorizontalAlignment horizontalAlignment = getHorizontalAlignment();
        if (horizontalAlignment == HorizontalAlignment.LEFT) {
            x = (float) titleArea.getX();
            anchor = TextBlockAnchor.TOP_LEFT;
        }
        else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
            x = (float) titleArea.getMaxX();
            anchor = TextBlockAnchor.TOP_RIGHT;
        }
        else if (horizontalAlignment == HorizontalAlignment.CENTER) {
            x = (float) titleArea.getCenterX();
            anchor = TextBlockAnchor.TOP_CENTER;
        }
        float y = 0.0f;
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.TOP) {
            y = (float) titleArea.getY();
        }
        else if (position == RectangleEdge.BOTTOM) {
            y = (float) titleArea.getMaxY();
            if (horizontalAlignment == HorizontalAlignment.LEFT) {
                anchor = TextBlockAnchor.BOTTOM_LEFT;
            }
            else if (horizontalAlignment == HorizontalAlignment.CENTER) {
                anchor = TextBlockAnchor.BOTTOM_CENTER;
            }
            else if (horizontalAlignment == HorizontalAlignment.RIGHT) {
                anchor = TextBlockAnchor.BOTTOM_RIGHT;
            }
        }
        this.content.draw(g2, x, y, anchor);
    }

    /**
     * Draws a the title vertically within the specified area.  This method
     * will be called from the {@link #draw(Graphics2D, Rectangle2D) draw}
     * method.
     *
     * @param g2  the graphics device.
     * @param area  the area for the title.
     */
    protected void drawVertical(Graphics2D g2, Rectangle2D area) {
        Rectangle2D titleArea = (Rectangle2D) area.clone();
        g2.setFont(this.font);
        g2.setPaint(this.paint);
        TextBlockAnchor anchor = null;
        float y = 0.0f;
        VerticalAlignment verticalAlignment = getVerticalAlignment();
        if (verticalAlignment == VerticalAlignment.TOP) {
            y = (float) titleArea.getY();
            anchor = TextBlockAnchor.TOP_RIGHT;
        }
        else if (verticalAlignment == VerticalAlignment.BOTTOM) {
            y = (float) titleArea.getMaxY();
            anchor = TextBlockAnchor.TOP_LEFT;
        }
        else if (verticalAlignment == VerticalAlignment.CENTER) {
            y = (float) titleArea.getCenterY();
            anchor = TextBlockAnchor.TOP_CENTER;
        }
        float x = 0.0f;
        RectangleEdge position = getPosition();
        if (position == RectangleEdge.LEFT) {
            x = (float) titleArea.getX();
        }
        else if (position == RectangleEdge.RIGHT) {
            x = (float) titleArea.getMaxX();
            if (verticalAlignment == VerticalAlignment.TOP) {
                anchor = TextBlockAnchor.BOTTOM_RIGHT;
            }
            else if (verticalAlignment == VerticalAlignment.CENTER) {
                anchor = TextBlockAnchor.BOTTOM_CENTER;
            }
            else if (verticalAlignment == VerticalAlignment.BOTTOM) {
                anchor = TextBlockAnchor.BOTTOM_LEFT;
            }
        }
        this.content.draw(g2, x, y, anchor, x, y, -Math.PI / 2.0);
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
        result = 29 * result + (this.backgroundPaint != null
                ? this.backgroundPaint.hashCode() : 0);
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

}

