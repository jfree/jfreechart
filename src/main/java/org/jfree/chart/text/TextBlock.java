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
 */

package org.jfree.chart.text;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.chart.api.HorizontalAlignment;
import org.jfree.chart.block.Size2D;
import org.jfree.chart.internal.Args;
import org.jfree.chart.internal.ShapeUtils;

/**
 * A list of {@link TextLine} objects that form a block of text.
 * 
 * @see TextUtils#createTextBlock(String, Font, Paint)
 */
public class TextBlock implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -4333175719424385526L;
    
    /** Storage for the lines of text. */
    private List<TextLine> lines;
    
    /** The alignment of the lines. */
    private HorizontalAlignment lineAlignment;

    /**
     * Creates a new empty text block.
     */
    public TextBlock() {
        this.lines = new ArrayList<>();
        this.lineAlignment = HorizontalAlignment.CENTER;
    }
    
    /**
     * Returns the alignment of the lines of text within the block.
     * 
     * @return The alignment (never {@code null}).
     */
    public HorizontalAlignment getLineAlignment() {
        return this.lineAlignment;   
    }
    
    /**
     * Sets the alignment of the lines of text within the block.
     * 
     * @param alignment  the alignment ({@code null} not permitted).
     */
    public void setLineAlignment(HorizontalAlignment alignment) {
        Args.nullNotPermitted(alignment, "alignment");
        this.lineAlignment = alignment;   
    }
    
    /**
     * Adds a line of text that will be displayed using the specified font and
     * color.
     * 
     * @param text  the text ({@code null} not permitted).
     * @param font  the font ({@code null} not permitted).
     * @param paint  the paint ({@code null} not permitted).
     */
    public void addLine(String text, Font font, Paint paint) {
        addLine(new TextLine(text, font, paint));
    }
    
    /**
     * Adds a {@link TextLine} to the block.
     * 
     * @param line  the line.
     */
    public void addLine(TextLine line) {
        this.lines.add(line);    
    }
    
    /**
     * Returns the last line in the block.
     * 
     * @return The last line in the block.
     */
    public TextLine getLastLine() {
        TextLine last = null;
        final int index = this.lines.size() - 1;
        if (index >= 0) {
            last = this.lines.get(index);
        }
        return last;
    }
    
    /**
     * Returns an unmodifiable list containing the lines for the text block.
     *
     * @return A list of {@link TextLine} objects.
     */
    public List<TextLine> getLines() {
        return Collections.unmodifiableList(this.lines);
    }
    
    /**
     * Returns the width and height of the text block.
     * 
     * @param g2  the graphics device.
     * 
     * @return The width and height.
     */
    public Size2D calculateDimensions(Graphics2D g2) {
        double width = 0.0;
        double height = 0.0;
        for (TextLine line : this.lines) {
            final Size2D dimension = line.calculateDimensions(g2);
            width = Math.max(width, dimension.getWidth());
            height = height + dimension.getHeight();
        }
        return new Size2D(width, height);
    }
    
    /**
     * Returns the bounds of the text block.
     * 
     * @param g2  the graphics device ({@code null} not permitted).
     * @param anchorX  the x-coordinate for the anchor point.
     * @param anchorY  the y-coordinate for the anchor point.
     * @param anchor  the text block anchor ({@code null} not permitted).
     * @param rotateX  the x-coordinate for the rotation point.
     * @param rotateY  the y-coordinate for the rotation point.
     * @param angle  the rotation angle.
     * 
     * @return The bounds.
     */
    public Shape calculateBounds(Graphics2D g2, float anchorX, float anchorY, 
            TextBlockAnchor anchor, float rotateX, float rotateY, double angle) {
        Size2D d = calculateDimensions(g2);
        float[] offsets = calculateOffsets(anchor, d.getWidth(), d.getHeight());
        Rectangle2D bounds = new Rectangle2D.Double(anchorX + offsets[0], 
                anchorY + offsets[1], d.getWidth(), d.getHeight());
        Shape rotatedBounds = ShapeUtils.rotateShape(bounds, angle, rotateX, 
                rotateY);
        return rotatedBounds;
    }
    
    /**
     * Draws the text block at a specific location.
     * 
     * @param g2  the graphics device.
     * @param x  the x-coordinate for the anchor point.
     * @param y  the y-coordinate for the anchor point.
     * @param anchor  the anchor point.
     */
    public void draw(Graphics2D g2, float x, float y, TextBlockAnchor anchor) {
        draw(g2, x, y, anchor, 0.0f, 0.0f, 0.0);
    }
    
    /**
     * Draws the text block, aligning it with the specified anchor point and 
     * rotating it about the specified rotation point.
     * 
     * @param g2  the graphics device.
     * @param anchorX  the x-coordinate for the anchor point.
     * @param anchorY  the y-coordinate for the anchor point.
     * @param anchor  the point on the text block that is aligned to the 
     *                anchor point.
     * @param rotateX  the x-coordinate for the rotation point.
     * @param rotateY  the x-coordinate for the rotation point.
     * @param angle  the rotation (in radians).
     */
    public void draw(Graphics2D g2, float anchorX, float anchorY, 
            TextBlockAnchor anchor, float rotateX, float rotateY, double angle) {
    
        Size2D d = calculateDimensions(g2);
        float[] offsets = calculateOffsets(anchor, d.getWidth(), 
                d.getHeight());
        float yCursor = 0.0f;
        for (TextLine line : this.lines) {
            Size2D dimension = line.calculateDimensions(g2);
            float lineOffset = 0.0f;
            if (this.lineAlignment == HorizontalAlignment.CENTER) {
                lineOffset = (float) (d.getWidth() - dimension.getWidth()) 
                    / 2.0f;   
            } else if (this.lineAlignment == HorizontalAlignment.RIGHT) {
                lineOffset = (float) (d.getWidth() - dimension.getWidth());   
            }
            line.draw(g2, anchorX + offsets[0] + lineOffset, 
                    anchorY + offsets[1] + yCursor, TextAnchor.TOP_LEFT, 
                    rotateX, rotateY, angle);
            yCursor = yCursor + (float) dimension.getHeight();
        }
        
    }
 
    /**
     * Calculates the x and y offsets required to align the text block with the
     * specified anchor point.  This assumes that the top left of the text 
     * block is at (0.0, 0.0).
     * 
     * @param anchor  the anchor position.
     * @param width  the width of the text block.
     * @param height  the height of the text block.
     * 
     * @return The offsets (float[0] = x offset, float[1] = y offset).
     */
    private float[] calculateOffsets(TextBlockAnchor anchor, double width, 
            double height) {
        float[] result = new float[2];
        float xAdj = 0.0f;
        float yAdj = 0.0f;

        if (anchor == TextBlockAnchor.TOP_CENTER
                || anchor == TextBlockAnchor.CENTER
                || anchor == TextBlockAnchor.BOTTOM_CENTER) {
                    
            xAdj = (float) -width / 2.0f;
            
        } else if (anchor == TextBlockAnchor.TOP_RIGHT
                || anchor == TextBlockAnchor.CENTER_RIGHT
                || anchor == TextBlockAnchor.BOTTOM_RIGHT) {
                    
            xAdj = (float) -width;
            
        }

        if (anchor == TextBlockAnchor.TOP_LEFT
                || anchor == TextBlockAnchor.TOP_CENTER
                || anchor == TextBlockAnchor.TOP_RIGHT) {
                    
            yAdj = 0.0f;
            
        } else if (anchor == TextBlockAnchor.CENTER_LEFT
                || anchor == TextBlockAnchor.CENTER
                || anchor == TextBlockAnchor.CENTER_RIGHT) {
                    
            yAdj = (float) -height / 2.0f;
            
        } else if (anchor == TextBlockAnchor.BOTTOM_LEFT
                || anchor == TextBlockAnchor.BOTTOM_CENTER
                || anchor == TextBlockAnchor.BOTTOM_RIGHT) {
                    
            yAdj = (float) -height;
            
        }
        result[0] = xAdj;
        result[1] = yAdj;
        return result;
    }   
    
    /**
     * Tests this object for equality with an arbitrary object.
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
        if (obj instanceof TextBlock) {
            TextBlock block = (TextBlock) obj;
            return this.lines.equals(block.lines);
        }
        return false;
    }

    /**
     * Returns a hash code for this object.
     * 
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return (this.lines != null ? this.lines.hashCode() : 0);
    }
}

