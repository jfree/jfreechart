package org.jfree.chart.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.jfree.chart.ui.Size2D;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.util.SerialUtils;

/**
 * A text item, with an associated font, that fits on a single line (see 
 * {@link TextLine}).  Instances of the class are immutable.
 */
public class TextFragment implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 4465945952903143262L;
    
    /** The default font. */
    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, 12);
    
    /** The default text color. */
    public static final Paint DEFAULT_PAINT = Color.black;
    
    /** The text. */
    private String text;
    
    /** The font. */
    private Font font;
    
    /** The text color. */
    private transient Paint paint;
    
    /** 
     * The baseline offset (can be used to simulate subscripts and 
     * superscripts). 
     */
    private float baselineOffset;
    
    /**
     * Creates a new text fragment.
     * 
     * @param text  the text (<code>null</code> not permitted).
     */
    public TextFragment(final String text) {
        this(text, DEFAULT_FONT, DEFAULT_PAINT);
    }
    
    /**
     * Creates a new text fragment.
     * 
     * @param text  the text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     */
    public TextFragment(final String text, final Font font) {
        this(text, font, DEFAULT_PAINT);
    }

    /**
     * Creates a new text fragment.
     * 
     * @param text  the text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     * @param paint  the text color (<code>null</code> not permitted).
     */
    public TextFragment(final String text, final Font font, final Paint paint) {
        this(text, font, paint, 0.0f);
    }

    /**
     * Creates a new text fragment.
     * 
     * @param text  the text (<code>null</code> not permitted).
     * @param font  the font (<code>null</code> not permitted).
     * @param paint  the text color (<code>null</code> not permitted).
     * @param baselineOffset  the baseline offset.
     */
    public TextFragment(final String text, final Font font, final Paint paint,
                        final float baselineOffset) {
        if (text == null) {
            throw new IllegalArgumentException("Null 'text' argument.");  
        }
        if (font == null) {
            throw new IllegalArgumentException("Null 'font' argument.");
        }
        if (paint == null) {
            throw new IllegalArgumentException("Null 'paint' argument.");
        }
        this.text = text;
        this.font = font;
        this.paint = paint;
        this.baselineOffset = baselineOffset;
    }

    /**
     * Returns the text.
     * 
     * @return The text (possibly <code>null</code>).
     */
    public String getText() {
        return this.text;
    }
    
    /**
     * Returns the font.
     * 
     * @return The font (never <code>null</code>).
     */
    public Font getFont() {
        return this.font;
    }
    
    /**
     * Returns the text paint.
     * 
     * @return The text paint (never <code>null</code>).
     */
    public Paint getPaint() {
        return this.paint;
    }
    
    /**
     * Returns the baseline offset.
     * 
     * @return The baseline offset.
     */
    public float getBaselineOffset() {
        return this.baselineOffset;   
    }
    
    /**
     * Draws the text fragment.
     * 
     * @param g2  the graphics device.
     * @param anchorX  the x-coordinate of the anchor point.
     * @param anchorY  the y-coordinate of the anchor point.
     * @param anchor  the location of the text that is aligned to the anchor 
     *                point.
     * @param rotateX  the x-coordinate of the rotation point.
     * @param rotateY  the y-coordinate of the rotation point.
     * @param angle  the angle.
     */
    public void draw(final Graphics2D g2, final float anchorX, 
                     final float anchorY, final TextAnchor anchor,
                     final float rotateX, final float rotateY, 
                     final double angle) {
    
        g2.setFont(this.font);
        g2.setPaint(this.paint);
        TextUtilities.drawRotatedString(this.text, g2, anchorX, anchorY 
                + this.baselineOffset, anchor, angle, rotateX, rotateY);
    
    }
    
    /**
     * Calculates the dimensions of the text fragment.
     * 
     * @param g2  the graphics device.
     * 
     * @return The width and height of the text.
     */
    public Size2D calculateDimensions(final Graphics2D g2) {
        final FontMetrics fm = g2.getFontMetrics(this.font);
        final Rectangle2D bounds = TextUtilities.getTextBounds(this.text, g2, 
                fm);
        final Size2D result = new Size2D(bounds.getWidth(), bounds.getHeight());
        return result;
    }
    
    /**
     * Calculates the vertical offset between the baseline and the specified 
     * text anchor.
     * 
     * @param g2  the graphics device.
     * @param anchor  the anchor.
     * 
     * @return the offset.
     */
    public float calculateBaselineOffset(Graphics2D g2, TextAnchor anchor) {
        float result = 0.0f;
        final FontMetrics fm = g2.getFontMetrics(this.font);
        final LineMetrics lm = fm.getLineMetrics("ABCxyz", g2);
        if (anchor.isTop()) {
            result = lm.getAscent();
        }
        else if (anchor.isHalfAscent()) {
            result = lm.getAscent() / 2.0f;
        }
        else if (anchor.isVerticalCenter()) {
            result = lm.getAscent() / 2.0f - lm.getDescent() / 2.0f;
        }
        else if (anchor.isBottom()) {
            result = -lm.getDescent() - lm.getLeading();
        }
        return result;                                             
    }
    
    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object to test against (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;   
        }
        if (obj == this) {
            return true;   
        }
        if (obj instanceof TextFragment) {
            final TextFragment tf = (TextFragment) obj;
            if (!this.text.equals(tf.text)) {
                return false;   
            }
            if (!this.font.equals(tf.font)) {
                return false;   
            }
            if (!this.paint.equals(tf.paint)) {
                return false;   
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a hash code for this object.
     * 
     * @return A hash code.
     */
    public int hashCode() {
        int result;
        result = (this.text != null ? this.text.hashCode() : 0);
        result = 29 * result + (this.font != null ? this.font.hashCode() : 0);
        result = 29 * result + (this.paint != null ? this.paint.hashCode() : 0);
        return result;
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(final ObjectOutputStream stream) 
        throws IOException {
        stream.defaultWriteObject();
        SerialUtils.writePaint(this.paint, stream);
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(final ObjectInputStream stream) 
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.paint = SerialUtils.readPaint(stream);
    }
   
}

