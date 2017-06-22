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
 */

package org.jfree.chart.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.lang.reflect.Method;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * A collection of utility methods relating to user interfaces.
 */
public class UIUtils {

    private UIUtils() {
    }

    /**
     * Positions the specified frame in the middle of the screen.
     *
     * @param frame  the frame to be centered on the screen.
     */
    public static void centerFrameOnScreen(Window frame) {
        positionFrameOnScreen(frame, 0.5, 0.5);
    }

    /**
     * Positions the specified frame at a relative position in the screen, where 50% is considered
     * to be the center of the screen.
     *
     * @param frame  the frame.
     * @param horizontalPercent  the relative horizontal position of the frame (0.0 to 1.0,
     *                           where 0.5 is the center of the screen).
     * @param verticalPercent  the relative vertical position of the frame (0.0 to 1.0, where
     *                         0.5 is the center of the screen).
     */
    public static void positionFrameOnScreen(Window frame, 
            double horizontalPercent, double verticalPercent) {

        Rectangle s = frame.getGraphicsConfiguration().getBounds();
        Dimension f = frame.getSize();
        int w = Math.max(s.width - f.width, 0);
        int h = Math.max(s.height - f.height, 0);
        int x = (int) (horizontalPercent * w) + s.x;
        int y = (int) (verticalPercent * h) + s.y;
        frame.setBounds(x, y, f.width, f.height);

    }

    /**
     * Positions the specified frame at a random location on the screen while ensuring that the
     * entire frame is visible (provided that the frame is smaller than the screen).
     *
     * @param frame  the frame.
     */
    public static void positionFrameRandomly(Window frame) {
        positionFrameOnScreen(frame, Math.random(), Math.random());
    }

    /**
     * Positions the specified dialog within its parent.
     *
     * @param dialog  the dialog to be positioned on the screen.
     */
    public static void centerDialogInParent(Dialog dialog) {
        positionDialogRelativeToParent(dialog, 0.5, 0.5);
    }

    /**
     * Positions the specified dialog at a position relative to its parent.
     *
     * @param dialog  the dialog to be positioned.
     * @param horizontalPercent  the relative location.
     * @param verticalPercent  the relative location.
     */
    public static void positionDialogRelativeToParent(Dialog dialog,
            double horizontalPercent, double verticalPercent) {
        Container parent = dialog.getParent();
        if (parent == null) {
            centerFrameOnScreen(dialog);
            return;
        }

        Dimension d = dialog.getSize();
        Dimension p = parent.getSize();

        int baseX = parent.getX();
        int baseY = parent.getY();

        int x = baseX + (int) (horizontalPercent * p.width);
        int y = baseY + (int) (verticalPercent * p.height);

        // make sure the dialog fits completely on the screen...
        Rectangle s = parent.getGraphicsConfiguration().getBounds();
        Rectangle r = new Rectangle(x, y, d.width, d.height);
        dialog.setBounds(r.intersection(s));
    }

    /**
     * Creates a panel that contains a table based on the specified table model.
     *
     * @param model  the table model to use when constructing the table.
     *
     * @return The panel.
     */
    public static JPanel createTablePanel(TableModel model) {

        JPanel panel = new JPanel(new BorderLayout());
        JTable table = new JTable(model);
        for (int columnIndex = 0; columnIndex < model.getColumnCount(); columnIndex++) {
            TableColumn column = table.getColumnModel().getColumn(columnIndex);
            Class c = model.getColumnClass(columnIndex);
            if (c.equals(Number.class)) {
                column.setCellRenderer(new NumberCellRenderer());
            }
        }
        panel.add(new JScrollPane(table));
        return panel;

    }

    /**
     * Creates a label with a specific font.
     *
     * @param text  the text for the label.
     * @param font  the font.
     *
     * @return The label.
     */
    public static JLabel createJLabel(String text, Font font) {
        JLabel result = new JLabel(text);
        result.setFont(font);
        return result;
    }

    /**
     * Creates a label with a specific font and color.
     *
     * @param text  the text for the label.
     * @param font  the font.
     * @param color  the color.
     *
     * @return The label.
     */
    public static JLabel createJLabel(String text, Font font, Color color) {
        JLabel result = new JLabel(text);
        result.setFont(font);
        result.setForeground(color);
        return result;
    }

    /**
     * Creates a {@link JButton}.
     *
     * @param label  the label.
     * @param font  the font.
     *
     * @return The button.
     */
    public static JButton createJButton(String label, Font font) {
        JButton result = new JButton(label);
        result.setFont(font);
        return result;
    }

}



