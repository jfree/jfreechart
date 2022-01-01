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

package org.jfree.chart.swing.editor;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;

/**
 * Specialised layout manager for a grid of components.
 */
public class LCBLayout implements LayoutManager, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2531780832406163833L;
    
    /** A constant for the number of columns in the layout. */
    private static final int COLUMNS = 3;

    /** Tracks the column widths. */
    private int[] colWidth;

    /** Tracks the row heights. */
    private int[] rowHeight;

    /** The gap between each label and component. */
    private int labelGap;

    /** The gap between each component and button. */
    private int buttonGap;

    /** The gap between rows. */
    private int vGap;

    /**
     * Creates a new LCBLayout with the specified maximum number of rows.
     *
     * @param maxrows  the maximum number of rows.
     */
    public LCBLayout(int maxrows) {
        this.labelGap = 10;
        this.buttonGap = 6;
        this.vGap = 2;
        this.colWidth = new int[COLUMNS];
        this.rowHeight = new int[maxrows];
    }

    /**
     * Returns the preferred size using this layout manager.
     *
     * @param parent  the parent.
     *
     * @return the preferred size using this layout manager.
    */
    @Override
    public Dimension preferredLayoutSize(Container parent) {

        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = ncomponents / COLUMNS;
            for (int c = 0; c < COLUMNS; c++) {
                for (int r = 0; r < nrows; r++) {
                    Component component = parent.getComponent(r * COLUMNS + c);
                    Dimension d = component.getPreferredSize();
                    if (this.colWidth[c] < d.width) {
                        this.colWidth[c] = d.width;
                    }
                    if (this.rowHeight[r] < d.height) {
                        this.rowHeight[r] = d.height;
                    }
                }
            }
            int totalHeight = this.vGap * (nrows - 1);
            for (int r = 0; r < nrows; r++) {
                totalHeight = totalHeight + this.rowHeight[r];
            }
            int totalWidth = this.colWidth[0] + this.labelGap 
                + this.colWidth[1] + this.buttonGap + this.colWidth[2];
            return new Dimension(
                insets.left + insets.right + totalWidth + this.labelGap 
                    + this.buttonGap,
                insets.top + insets.bottom + totalHeight + this.vGap
            );
        }

    }

    /**
     * Returns the minimum size using this layout manager.
     *
     * @param parent  the parent.
     *
     * @return the minimum size using this layout manager.
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {

        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = ncomponents / COLUMNS;
            for (int c = 0; c < COLUMNS; c++) {
                for (int r = 0; r < nrows; r++) {
                    Component component = parent.getComponent(r * COLUMNS + c);
                    Dimension d = component.getMinimumSize();
                    if (this.colWidth[c] < d.width) {
                        this.colWidth[c] = d.width;
                    }
                    if (this.rowHeight[r] < d.height) {
                        this.rowHeight[r] = d.height;
                    }
                }
            }
            int totalHeight = this.vGap * (nrows - 1);
            for (int r = 0; r < nrows; r++) {
                totalHeight = totalHeight + this.rowHeight[r];
            }
            int totalWidth = this.colWidth[0] + this.labelGap 
                + this.colWidth[1] + this.buttonGap + this.colWidth[2];
            return new Dimension(
                insets.left + insets.right + totalWidth + this.labelGap 
                + this.buttonGap,
                insets.top + insets.bottom + totalHeight + this.vGap
            );
        }

    }

    /**
     * Lays out the components.
     *
     * @param parent  the parent.
     */
    @Override
    public void layoutContainer(Container parent) {

        synchronized (parent.getTreeLock()) {
            Insets insets = parent.getInsets();
            int ncomponents = parent.getComponentCount();
            int nrows = ncomponents / COLUMNS;
            for (int c = 0; c < COLUMNS; c++) {
                for (int r = 0; r < nrows; r++) {
                    Component component = parent.getComponent(r * COLUMNS + c);
                    Dimension d = component.getPreferredSize();
                    if (this.colWidth[c] < d.width) {
                        this.colWidth[c] = d.width;
                    }
                    if (this.rowHeight[r] < d.height) {
                        this.rowHeight[r] = d.height;
                    }
                }
            }
            int totalHeight = this.vGap * (nrows - 1);
            for (int r = 0; r < nrows; r++) {
                totalHeight = totalHeight + this.rowHeight[r];
            }
            int totalWidth = this.colWidth[0] + this.colWidth[1] 
                                                    + this.colWidth[2];

            // adjust the width of the second column to use up all of parent
            int available = parent.getWidth() - insets.left 
                - insets.right - this.labelGap - this.buttonGap;
            this.colWidth[1] = this.colWidth[1] + (available - totalWidth);

            // *** DO THE LAYOUT ***
            int x = insets.left;
            for (int c = 0; c < COLUMNS; c++) {
                int y = insets.top;
                for (int r = 0; r < nrows; r++) {
                    int i = r * COLUMNS + c;
                    if (i < ncomponents) {
                        Component component = parent.getComponent(i);
                        Dimension d = component.getPreferredSize();
                        int h = d.height;
                        int adjust = (this.rowHeight[r] - h) / 2;
                        parent.getComponent(i).setBounds(x, y + adjust, 
                                this.colWidth[c], h);
                    }
                    y = y + this.rowHeight[r] + this.vGap;
                }
                x = x + this.colWidth[c];
                if (c == 0) {
                    x = x + this.labelGap;
                }
                if (c == 1) {
                    x = x + this.buttonGap;
                }
            }

        }

    }

    /**
     * Not used.
     *
     * @param comp  the component.
     */
    public void addLayoutComponent(Component comp) {
        // not used
    }

    /**
     * Not used.
     *
     * @param comp  the component.
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        // not used
    }

    /**
     * Not used.
     *
     * @param name  the component name.
     * @param comp  the component.
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
        // not used
    }

    /**
     * Not used.
     *
     * @param name  the component name.
     * @param comp  the component.
     */
    public void removeLayoutComponent(String name, Component comp) {
        // not used
    }

}
