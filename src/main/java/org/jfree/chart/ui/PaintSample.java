/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
     * @param paint  the paint to display.
     */
    public PaintSample(Paint paint) {
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
    public void setPaint(Paint paint) {
        this.paint = paint;
        repaint();
    }

    /**
     * Returns the preferred size of the component.
     *
     * @return the preferred size.
     */
    @Override
    public Dimension getPreferredSize() {
        return this.preferredSize;
    }

    /**
     * Fills the component with the current Paint.
     *
     * @param g  the graphics device.
     */
    @Override
    public void paintComponent(Graphics g) {

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
        g2.setPaint(Color.BLACK);
        g2.draw(area);

    }

}

