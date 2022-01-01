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
 * LegendItem.java
 * ---------------
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Andrzej Porebski;
 *                   David Li;
 *                   Wolfgang Irler;
 *                   Luke Quinane;
 *
 */

package org.jfree.chart.legend;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.Objects;
import org.jfree.chart.text.AttributedStringUtils;
import org.jfree.chart.util.GradientPaintTransformer;
import org.jfree.chart.util.StandardGradientPaintTransformer;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.PaintUtils;
import org.jfree.chart.api.PublicCloneable;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.internal.SerialUtils;
import org.jfree.chart.internal.ShapeUtils;

import org.jfree.data.general.Dataset;

/**
 * A temporary storage object for recording the properties of a legend item,
 * without any consideration for layout issues.
 */
public class LegendItem implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -797214582948827144L;

    /** The dataset. */
    private Dataset dataset;

    /** The series key. */
    private Comparable seriesKey;

    /** The dataset index. */
    private int datasetIndex;

    /** The series index. */
    private int series;

    /** The label. */
    private final String label;

    /** The label font ({@code null} is permitted). */
    private Font labelFont;

    /** The label paint ({@code null} is permitted). */
    private transient Paint labelPaint;

    /** The attributed label (if null, fall back to the regular label). */
    private transient AttributedString attributedLabel;

    /**
     * The description (not currently used - could be displayed as a tool tip).
     */
    private String description;

    /** The tool tip text. */
    private String toolTipText;

    /** The url text. */
    private String urlText;

    /** A flag that controls whether or not the shape is visible. */
    private boolean shapeVisible;

    /** The shape. */
    private transient Shape shape;

    /** A flag that controls whether or not the shape is filled. */
    private final boolean shapeFilled;

    /** The paint. */
    private transient Paint fillPaint;

    /** A gradient paint transformer. */
    private GradientPaintTransformer fillPaintTransformer;

    /** A flag that controls whether or not the shape outline is visible. */
    private final boolean shapeOutlineVisible;

    /** The outline paint. */
    private transient Paint outlinePaint;

    /** The outline stroke. */
    private transient Stroke outlineStroke;

    /** A flag that controls whether or not the line is visible. */
    private boolean lineVisible;

    /** The line. */
    private transient Shape line;

    /** The stroke. */
    private transient Stroke lineStroke;

    /** The line paint. */
    private transient Paint linePaint;

    /**
     * The shape must be non-null for a LegendItem - if no shape is required,
     * use this.
     */
    private static final Shape UNUSED_SHAPE = new Line2D.Float();

    /**
     * The stroke must be non-null for a LegendItem - if no stroke is required,
     * use this.
     */
    private static final Stroke UNUSED_STROKE = new BasicStroke(0.0f);

    /**
     * Creates a legend item with the specified label.  The remaining
     * attributes take default values.
     *
     * @param label  the label ({@code null} not permitted).
     */
    public LegendItem(String label) {
        this(label, Color.BLACK);
    }

    /**
     * Creates a legend item with the specified label and fill paint.  The
     * remaining attributes take default values.
     *
     * @param label  the label ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     */
    public LegendItem(String label, Paint paint) {
        this(label, null, null, null, new Rectangle2D.Double(-4.0, -4.0, 8.0,
                8.0), paint);
    }

    /**
     * Creates a legend item with a filled shape.  The shape is not outlined,
     * and no line is visible.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param fillPaint  the paint used to fill the shape ({@code null}
     *                   not permitted).
     */
    public LegendItem(String label, String description,
                      String toolTipText, String urlText,
                      Shape shape, Paint fillPaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ true, shape,
                /* shape filled = */ true, fillPaint,
                /* shape outlined */ false, Color.BLACK, UNUSED_STROKE,
                /* line visible */ false, UNUSED_SHAPE, UNUSED_STROKE,
                Color.BLACK);

    }

    /**
     * Creates a legend item with a filled and outlined shape.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param fillPaint  the paint used to fill the shape ({@code null}
     *                   not permitted).
     * @param outlineStroke  the outline stroke ({@code null} not
     *                       permitted).
     * @param outlinePaint  the outline paint ({@code null} not
     *                      permitted).
     */
    public LegendItem(String label, String description, String toolTipText, 
            String urlText, Shape shape, Paint fillPaint, Stroke outlineStroke, 
            Paint outlinePaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ true, shape,
                /* shape filled = */ true, fillPaint,
                /* shape outlined = */ true, outlinePaint, outlineStroke,
                /* line visible */ false, UNUSED_SHAPE, UNUSED_STROKE,
                Color.BLACK);

    }

    /**
     * Creates a legend item using a line.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param line  the line ({@code null} not permitted).
     * @param lineStroke  the line stroke ({@code null} not permitted).
     * @param linePaint  the line paint ({@code null} not permitted).
     */
    public LegendItem(String label, String description, String toolTipText, 
            String urlText, Shape line, Stroke lineStroke, Paint linePaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ false, UNUSED_SHAPE,
                /* shape filled = */ false, Color.BLACK,
                /* shape outlined = */ false, Color.BLACK, UNUSED_STROKE,
                /* line visible = */ true, line, lineStroke, linePaint);
    }

    /**
     * Creates a new legend item.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description (not currently used,
     *        {@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shapeVisible  a flag that controls whether or not the shape is
     *                      displayed.
     * @param shape  the shape ({@code null} permitted).
     * @param shapeFilled  a flag that controls whether or not the shape is
     *                     filled.
     * @param fillPaint  the fill paint ({@code null} not permitted).
     * @param shapeOutlineVisible  a flag that controls whether or not the
     *                             shape is outlined.
     * @param outlinePaint  the outline paint ({@code null} not permitted).
     * @param outlineStroke  the outline stroke ({@code null} not
     *                       permitted).
     * @param lineVisible  a flag that controls whether or not the line is
     *                     visible.
     * @param line  the line.
     * @param lineStroke  the stroke ({@code null} not permitted).
     * @param linePaint  the line paint ({@code null} not permitted).
     */
    public LegendItem(String label, String description,
                      String toolTipText, String urlText,
                      boolean shapeVisible, Shape shape,
                      boolean shapeFilled, Paint fillPaint,
                      boolean shapeOutlineVisible, Paint outlinePaint,
                      Stroke outlineStroke,
                      boolean lineVisible, Shape line,
                      Stroke lineStroke, Paint linePaint) {

        Args.nullNotPermitted(label, "label");
        Args.nullNotPermitted(fillPaint, "fillPaint");
        Args.nullNotPermitted(lineStroke, "lineStroke");
        Args.nullNotPermitted(outlinePaint, "outlinePaint");
        Args.nullNotPermitted(outlineStroke, "outlineStroke");
        this.label = label;
        this.labelPaint = null;
        this.attributedLabel = null;
        this.description = description;
        this.shapeVisible = shapeVisible;
        this.shape = shape;
        this.shapeFilled = shapeFilled;
        this.fillPaint = fillPaint;
        this.fillPaintTransformer = new StandardGradientPaintTransformer();
        this.shapeOutlineVisible = shapeOutlineVisible;
        this.outlinePaint = outlinePaint;
        this.outlineStroke = outlineStroke;
        this.lineVisible = lineVisible;
        this.line = line;
        this.lineStroke = lineStroke;
        this.linePaint = linePaint;
        this.toolTipText = toolTipText;
        this.urlText = urlText;
    }

    /**
     * Creates a legend item with a filled shape.  The shape is not outlined,
     * and no line is visible.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param fillPaint  the paint used to fill the shape ({@code null}
     *                   not permitted).
     */
    public LegendItem(AttributedString label, String description,
                      String toolTipText, String urlText,
                      Shape shape, Paint fillPaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ true, shape,
                /* shape filled = */ true, fillPaint,
                /* shape outlined = */ false, Color.BLACK, UNUSED_STROKE,
                /* line visible = */ false, UNUSED_SHAPE, UNUSED_STROKE,
                Color.BLACK);

    }

    /**
     * Creates a legend item with a filled and outlined shape.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shape  the shape ({@code null} not permitted).
     * @param fillPaint  the paint used to fill the shape ({@code null}
     *                   not permitted).
     * @param outlineStroke  the outline stroke ({@code null} not
     *                       permitted).
     * @param outlinePaint  the outline paint ({@code null} not
     *                      permitted).
     */
    public LegendItem(AttributedString label, String description,
                      String toolTipText, String urlText,
                      Shape shape, Paint fillPaint,
                      Stroke outlineStroke, Paint outlinePaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ true, shape,
                /* shape filled = */ true, fillPaint,
                /* shape outlined = */ true, outlinePaint, outlineStroke,
                /* line visible = */ false, UNUSED_SHAPE, UNUSED_STROKE,
                Color.BLACK);
    }

    /**
     * Creates a legend item using a line.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description ({@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param line  the line ({@code null} not permitted).
     * @param lineStroke  the line stroke ({@code null} not permitted).
     * @param linePaint  the line paint ({@code null} not permitted).
     */
    public LegendItem(AttributedString label, String description,
                      String toolTipText, String urlText,
                      Shape line, Stroke lineStroke, Paint linePaint) {

        this(label, description, toolTipText, urlText,
                /* shape visible = */ false, UNUSED_SHAPE,
                /* shape filled = */ false, Color.BLACK,
                /* shape outlined = */ false, Color.BLACK, UNUSED_STROKE,
                /* line visible = */ true, line, lineStroke, linePaint);
    }

    /**
     * Creates a new legend item.
     *
     * @param label  the label ({@code null} not permitted).
     * @param description  the description (not currently used,
     *        {@code null} permitted).
     * @param toolTipText  the tool tip text ({@code null} permitted).
     * @param urlText  the URL text ({@code null} permitted).
     * @param shapeVisible  a flag that controls whether or not the shape is
     *                      displayed.
     * @param shape  the shape ({@code null} permitted).
     * @param shapeFilled  a flag that controls whether or not the shape is
     *                     filled.
     * @param fillPaint  the fill paint ({@code null} not permitted).
     * @param shapeOutlineVisible  a flag that controls whether or not the
     *                             shape is outlined.
     * @param outlinePaint  the outline paint ({@code null} not permitted).
     * @param outlineStroke  the outline stroke ({@code null} not
     *                       permitted).
     * @param lineVisible  a flag that controls whether or not the line is
     *                     visible.
     * @param line  the line ({@code null} not permitted).
     * @param lineStroke  the stroke ({@code null} not permitted).
     * @param linePaint  the line paint ({@code null} not permitted).
     */
    public LegendItem(AttributedString label, String description,
                      String toolTipText, String urlText,
                      boolean shapeVisible, Shape shape,
                      boolean shapeFilled, Paint fillPaint,
                      boolean shapeOutlineVisible, Paint outlinePaint,
                      Stroke outlineStroke,
                      boolean lineVisible, Shape line, Stroke lineStroke,
                      Paint linePaint) {

        Args.nullNotPermitted(label, "label");
        Args.nullNotPermitted(fillPaint, "fillPaint");
        Args.nullNotPermitted(lineStroke, "lineStroke");
        Args.nullNotPermitted(line, "line");
        Args.nullNotPermitted(linePaint, "linePaint");
        Args.nullNotPermitted(outlinePaint, "outlinePaint");
        Args.nullNotPermitted(outlineStroke, "outlineStroke");
        this.label = characterIteratorToString(label.getIterator());
        this.attributedLabel = label;
        this.description = description;
        this.shapeVisible = shapeVisible;
        this.shape = shape;
        this.shapeFilled = shapeFilled;
        this.fillPaint = fillPaint;
        this.fillPaintTransformer = new StandardGradientPaintTransformer();
        this.shapeOutlineVisible = shapeOutlineVisible;
        this.outlinePaint = outlinePaint;
        this.outlineStroke = outlineStroke;
        this.lineVisible = lineVisible;
        this.line = line;
        this.lineStroke = lineStroke;
        this.linePaint = linePaint;
        this.toolTipText = toolTipText;
        this.urlText = urlText;
    }

    /**
     * Returns a string containing the characters from the given iterator.
     *
     * @param iterator  the iterator ({@code null} not permitted).
     *
     * @return A string.
     */
    private String characterIteratorToString(CharacterIterator iterator) {
        int endIndex = iterator.getEndIndex();
        int beginIndex = iterator.getBeginIndex();
        int count = endIndex - beginIndex;
        if (count <= 0) {
            return "";
        }
        char[] chars = new char[count];
        int i = 0;
        char c = iterator.first();
        while (c != CharacterIterator.DONE) {
            chars[i] = c;
            i++;
            c = iterator.next();
        }
        return new String(chars);
    }

    /**
     * Returns the dataset.
     *
     * @return The dataset.
     *
     * @see #setDatasetIndex(int)
     */
    public Dataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset.
     *
     * @param dataset  the dataset.
     */
    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    /**
     * Returns the dataset index for this legend item.
     *
     * @return The dataset index.
     *
     * @see #setDatasetIndex(int)
     * @see #getDataset()
     */
    public int getDatasetIndex() {
        return this.datasetIndex;
    }

    /**
     * Sets the dataset index for this legend item.
     *
     * @param index  the index.
     *
     * @see #getDatasetIndex()
     */
    public void setDatasetIndex(int index) {
        this.datasetIndex = index;
    }

    /**
     * Returns the series key.
     *
     * @return The series key.
     *
     * @see #setSeriesKey(Comparable)
     */
    public Comparable getSeriesKey() {
        return this.seriesKey;
    }

    /**
     * Sets the series key.
     *
     * @param key  the series key.
     */
    public void setSeriesKey(Comparable key) {
        this.seriesKey = key;
    }

    /**
     * Returns the series index for this legend item.
     *
     * @return The series index.
     */
    public int getSeriesIndex() {
        return this.series;
    }

    /**
     * Sets the series index for this legend item.
     *
     * @param index  the index.
     */
    public void setSeriesIndex(int index) {
        this.series = index;
    }

    /**
     * Returns the label.
     *
     * @return The label (never {@code null}).
     */
    public String getLabel() {
        return this.label;
    }

    /**
     * Returns the label font.
     *
     * @return The label font (possibly {@code null}).
     */
    public Font getLabelFont() {
        return this.labelFont;
    }

    /**
     * Sets the label font.
     *
     * @param font  the font ({@code null} permitted).
     */
    public void setLabelFont(Font font) {
        this.labelFont = font;
    }

    /**
     * Returns the paint used to draw the label.
     *
     * @return The paint (possibly {@code null}).
     */
    public Paint getLabelPaint() {
        return this.labelPaint;
    }

    /**
     * Sets the paint used to draw the label.
     *
     * @param paint  the paint ({@code null} permitted).
     */
    public void setLabelPaint(Paint paint) {
        this.labelPaint = paint;
    }

    /**
     * Returns the attributed label.
     *
     * @return The attributed label (possibly {@code null}).
     */
    public AttributedString getAttributedLabel() {
        return this.attributedLabel;
    }

    /**
     * Returns the description for the legend item.
     *
     * @return The description (possibly {@code null}).
     *
     * @see #setDescription(java.lang.String) 
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description for this legend item.
     *
     * @param text  the description ({@code null} permitted).
     *
     * @see #getDescription()
     */
    public void setDescription(String text) {
        this.description = text;
    }

    /**
     * Returns the tool tip text.
     *
     * @return The tool tip text (possibly {@code null}).
     *
     * @see #setToolTipText(java.lang.String) 
     */
    public String getToolTipText() {
        return this.toolTipText;
    }

    /**
     * Sets the tool tip text for this legend item.
     *
     * @param text  the text ({@code null} permitted).
     *
     * @see #getToolTipText()
     */
    public void setToolTipText(String text) {
        this.toolTipText = text;
    }

    /**
     * Returns the URL text.
     *
     * @return The URL text (possibly {@code null}).
     *
     * @see #setURLText(java.lang.String) 
     */
    public String getURLText() {
        return this.urlText;
    }

    /**
     * Sets the URL text.
     *
     * @param text  the text ({@code null} permitted).
     *
     * @see #getURLText()
     */
    public void setURLText(String text) {
        this.urlText = text;
    }

    /**
     * Returns a flag that indicates whether or not the shape is visible.
     *
     * @return A boolean.
     *
     * @see #setShapeVisible(boolean)
     */
    public boolean isShapeVisible() {
        return this.shapeVisible;
    }

    /**
     * Sets the flag that controls whether or not the shape is visible.
     *
     * @param visible  the new flag value.
     *
     * @see #isShapeVisible()
     * @see #isLineVisible()
     */
    public void setShapeVisible(boolean visible) {
        this.shapeVisible = visible;
    }

    /**
     * Returns the shape used to label the series represented by this legend
     * item.
     *
     * @return The shape (never {@code null}).
     *
     * @see #setShape(java.awt.Shape) 
     */
    public Shape getShape() {
        return this.shape;
    }

    /**
     * Sets the shape for the legend item.
     *
     * @param shape  the shape ({@code null} not permitted).
     *
     * @see #getShape()
     */
    public void setShape(Shape shape) {
        Args.nullNotPermitted(shape, "shape");
        this.shape = shape;
    }

    /**
     * Returns a flag that controls whether or not the shape is filled.
     *
     * @return A boolean.
     */
    public boolean isShapeFilled() {
        return this.shapeFilled;
    }

    /**
     * Returns the fill paint.
     *
     * @return The fill paint (never {@code null}).
     */
    public Paint getFillPaint() {
        return this.fillPaint;
    }

    /**
     * Sets the fill paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setFillPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.fillPaint = paint;
    }

    /**
     * Returns the flag that controls whether or not the shape outline
     * is visible.
     *
     * @return A boolean.
     */
    public boolean isShapeOutlineVisible() {
        return this.shapeOutlineVisible;
    }

    /**
     * Returns the line stroke for the series.
     *
     * @return The stroke (never {@code null}).
     */
    public Stroke getLineStroke() {
        return this.lineStroke;
    }
    
    /**
     * Sets the line stroke.
     * 
     * @param stroke  the stroke ({@code null} not permitted).
     */
    public void setLineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.lineStroke = stroke;
    }

    /**
     * Returns the paint used for lines.
     *
     * @return The paint (never {@code null}).
     */
    public Paint getLinePaint() {
        return this.linePaint;
    }

    /**
     * Sets the line paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setLinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.linePaint = paint;
    }

    /**
     * Returns the outline paint.
     *
     * @return The outline paint (never {@code null}).
     */
    public Paint getOutlinePaint() {
        return this.outlinePaint;
    }

    /**
     * Sets the outline paint.
     *
     * @param paint  the paint ({@code null} not permitted).
     */
    public void setOutlinePaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.outlinePaint = paint;
    }

    /**
     * Returns the outline stroke.
     *
     * @return The outline stroke (never {@code null}).
     *
     * @see #setOutlineStroke(java.awt.Stroke) 
     */
    public Stroke getOutlineStroke() {
        return this.outlineStroke;
    }

    /**
     * Sets the outline stroke.
     *
     * @param stroke  the stroke ({@code null} not permitted).
     *
     * @see #getOutlineStroke()
     */
    public void setOutlineStroke(Stroke stroke) {
        Args.nullNotPermitted(stroke, "stroke");
        this.outlineStroke = stroke;
    }

    /**
     * Returns a flag that indicates whether or not the line is visible.
     *
     * @return A boolean.
     *
     * @see #setLineVisible(boolean) 
     */
    public boolean isLineVisible() {
        return this.lineVisible;
    }

    /**
     * Sets the flag that controls whether or not the line shape is visible for
     * this legend item.
     *
     * @param visible  the new flag value.
     *
     * @see #isLineVisible()
     */
    public void setLineVisible(boolean visible) {
        this.lineVisible = visible;
    }

    /**
     * Returns the line.
     *
     * @return The line (never {@code null}).
     *
     * @see #setLine(java.awt.Shape)
     * @see #isLineVisible() 
     */
    public Shape getLine() {
        return this.line;
    }

    /**
     * Sets the line.
     *
     * @param line  the line ({@code null} not permitted).
     *
     * @see #getLine()
     */
    public void setLine(Shape line) {
        Args.nullNotPermitted(line, "line");
        this.line = line;
    }

    /**
     * Returns the transformer used when the fill paint is an instance of
     * {@code GradientPaint}.
     *
     * @return The transformer (never {@code null}).
     *
     * @see #setFillPaintTransformer(GradientPaintTransformer)
     */
    public GradientPaintTransformer getFillPaintTransformer() {
        return this.fillPaintTransformer;
    }

    /**
     * Sets the transformer used when the fill paint is an instance of
     * {@code GradientPaint}.
     *
     * @param transformer  the transformer ({@code null} not permitted).
     *
     * @see #getFillPaintTransformer()
     */
    public void setFillPaintTransformer(GradientPaintTransformer transformer) {
        Args.nullNotPermitted(transformer, "transformer");
        this.fillPaintTransformer = transformer;
    }

    /**
     * Tests this item for equality with an arbitrary object.
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
        if (!(obj instanceof LegendItem)) {
            return false;
        }
        LegendItem that = (LegendItem) obj;
        if (this.datasetIndex != that.datasetIndex) {
            return false;
        }
        if (this.series != that.series) {
            return false;
        }
        if (!this.label.equals(that.label)) {
            return false;
        }
        if (!AttributedStringUtils.equal(this.attributedLabel,
                that.attributedLabel)) {
            return false;
        }
        if (!Objects.equals(this.description, that.description)) {
            return false;
        }
        if (this.shapeVisible != that.shapeVisible) {
            return false;
        }
        if (!ShapeUtils.equal(this.shape, that.shape)) {
            return false;
        }
        if (this.shapeFilled != that.shapeFilled) {
            return false;
        }
        if (!PaintUtils.equal(this.fillPaint, that.fillPaint)) {
            return false;
        }
        if (!Objects.equals(this.fillPaintTransformer, that.fillPaintTransformer)) {
            return false;
        }
        if (this.shapeOutlineVisible != that.shapeOutlineVisible) {
            return false;
        }
        if (!this.outlineStroke.equals(that.outlineStroke)) {
            return false;
        }
        if (!PaintUtils.equal(this.outlinePaint, that.outlinePaint)) {
            return false;
        }
        if (!this.lineVisible == that.lineVisible) {
            return false;
        }
        if (!ShapeUtils.equal(this.line, that.line)) {
            return false;
        }
        if (!this.lineStroke.equals(that.lineStroke)) {
            return false;
        }
        if (!PaintUtils.equal(this.linePaint, that.linePaint)) {
            return false;
        }
        if (!Objects.equals(this.labelFont, that.labelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.labelPaint, that.labelPaint)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.datasetIndex;
        hash = 79 * hash + this.series;
        hash = 79 * hash + Objects.hashCode(this.label);
        hash = 79 * hash + Objects.hashCode(this.labelFont);
        hash = 79 * hash + Objects.hashCode(this.labelPaint);
        hash = 79 * hash + Objects.hashCode(this.attributedLabel);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + (this.shapeVisible ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.shape);
        hash = 79 * hash + (this.shapeFilled ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.fillPaint);
        hash = 79 * hash + Objects.hashCode(this.fillPaintTransformer);
        hash = 79 * hash + (this.shapeOutlineVisible ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.outlinePaint);
        hash = 79 * hash + Objects.hashCode(this.outlineStroke);
        hash = 79 * hash + (this.lineVisible ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.line);
        hash = 79 * hash + Objects.hashCode(this.lineStroke);
        hash = 79 * hash + Objects.hashCode(this.linePaint);
        return hash;
    }

    /**
     * Returns an independent copy of this object (except that the clone will
     * still reference the same dataset as the original {@code LegendItem}).
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if the legend item cannot be cloned.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        LegendItem clone = (LegendItem) super.clone();
        if (this.seriesKey instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.seriesKey;
            clone.seriesKey = (Comparable) pc.clone();
        }
        // FIXME: Clone the attributed string if it is not null
        clone.shape = CloneUtils.clone(this.shape);
        if (this.fillPaintTransformer instanceof PublicCloneable) {
            PublicCloneable pc = (PublicCloneable) this.fillPaintTransformer;
            clone.fillPaintTransformer = (GradientPaintTransformer) pc.clone();

        }
        clone.line = CloneUtils.clone(this.line);
        return clone;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writeAttributedString(this.attributedLabel, stream);
        SerialUtils.writeShape(this.shape, stream);
        SerialUtils.writePaint(this.fillPaint, stream);
        SerialUtils.writeStroke(this.outlineStroke, stream);
        SerialUtils.writePaint(this.outlinePaint, stream);
        SerialUtils.writeShape(this.line, stream);
        SerialUtils.writeStroke(this.lineStroke, stream);
        SerialUtils.writePaint(this.linePaint, stream);
        SerialUtils.writePaint(this.labelPaint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream ({@code null} not permitted).
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.attributedLabel = SerialUtils.readAttributedString(stream);
        this.shape = SerialUtils.readShape(stream);
        this.fillPaint = SerialUtils.readPaint(stream);
        this.outlineStroke = SerialUtils.readStroke(stream);
        this.outlinePaint = SerialUtils.readPaint(stream);
        this.line = SerialUtils.readShape(stream);
        this.lineStroke = SerialUtils.readStroke(stream);
        this.linePaint = SerialUtils.readPaint(stream);
        this.labelPaint = SerialUtils.readPaint(stream);
    }

}
