package org.jfree.chart.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;

/**
 * A panel that displays a paint sample.
 */
public class PaintSample extends JComponent {

    /** The paint. */
    private Paint paint;

    /** The preferred size of the component. */
    private Dimension preferredSize;

    /**
     * Standard constructor - builds a paint sample.
     *
     * @param paint   the paint to display.
     */
    public PaintSample(final Paint paint) {
        this.paint = paint;
        this.preferredSize = new Dimension(80, 12);
    }

    /**
     * Returns the current Paint object being displayed in the panel.
     *
     * @return the paint.
     */
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the Paint object being displayed in the panel.
     *
     * @param paint  the paint.
     */
    public void setPaint(final Paint paint) {
        this.paint = paint;
        repaint();
    }

    /**
     * Returns the preferred size of the component.
     *
     * @return the preferred size.
     */
    public Dimension getPreferredSize() {
        return this.preferredSize;
    }

    /**
     * Fills the component with the current Paint.
     *
     * @param g  the graphics device.
     */
    public void paintComponent(final Graphics g) {

        final Graphics2D g2 = (Graphics2D) g;
        final Dimension size = getSize();
        final Insets insets = getInsets();
        final double xx = insets.left;
        final double yy = insets.top;
        final double ww = size.getWidth() - insets.left - insets.right - 1;
        final double hh = size.getHeight() - insets.top - insets.bottom - 1;
        final Rectangle2D area = new Rectangle2D.Double(xx, yy, ww, hh);
        g2.setPaint(this.paint);
        g2.fill(area);
        g2.setPaint(Color.black);
        g2.draw(area);

    }

}

