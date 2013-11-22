/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * ----------------------
 * DefaultValueAxisEditor.java
 * ----------------------
 * (C) Copyright 2005-2011, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Martin Hoeller (base on DefaultNumberAxisEditor
 *                                   by David Gilbert);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Nov-2011 : Version 1, based on DefaultNumberAxisEditor.java (MH);
 *
 */

package org.jfree.chart.editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.layout.LCBLayout;
import org.jfree.ui.PaintSample;
import org.jfree.ui.StrokeChooserPanel;
import org.jfree.ui.StrokeSample;

/**
 * A panel for editing properties of a {@link ValueAxis}.
 */
class DefaultValueAxisEditor extends DefaultAxisEditor
    implements FocusListener {

    /** A flag that indicates whether or not the axis range is determined
     *  automatically.
     */
    private boolean autoRange;

    /** Flag if auto-tickunit-selection is enabled. */
    private boolean autoTickUnitSelection;

    /** The lowest value in the axis range. */
    private double minimumValue;

    /** The highest value in the axis range. */
    private double maximumValue;

    /** A checkbox that indicates whether or not the axis range is determined
     *  automatically.
     */
    private JCheckBox autoRangeCheckBox;

    /** A check-box enabling/disabling auto-tickunit-selection. */
    private JCheckBox autoTickUnitSelectionCheckBox;

    /** A text field for entering the minimum value in the axis range. */
    private JTextField minimumRangeValue;

    /** A text field for entering the maximum value in the axis range. */
    private JTextField maximumRangeValue;

    /** The paint selected for drawing the gridlines. */
    private PaintSample gridPaintSample;

    /** The stroke selected for drawing the gridlines. */
    private StrokeSample gridStrokeSample;

    /** An array of stroke samples to choose from (since I haven't written a
     *  decent StrokeChooser component yet).
     */
    private StrokeSample[] availableStrokeSamples;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            = ResourceBundleWrapper.getBundle(
                "org.jfree.chart.editor.LocalizationBundle");

    /**
     * Standard constructor: builds a property panel for the specified axis.
     *
     * @param axis  the axis, which should be changed.
     */
    public DefaultValueAxisEditor(ValueAxis axis) {

        super(axis);

        this.autoRange = axis.isAutoRange();
        this.minimumValue = axis.getLowerBound();
        this.maximumValue = axis.getUpperBound();
        this.autoTickUnitSelection = axis.isAutoTickUnitSelection();

        this.gridPaintSample = new PaintSample(Color.blue);
        this.gridStrokeSample = new StrokeSample(new BasicStroke(1.0f));

        this.availableStrokeSamples = new StrokeSample[3];
        this.availableStrokeSamples[0] = new StrokeSample(
                new BasicStroke(1.0f));
        this.availableStrokeSamples[1] = new StrokeSample(
                new BasicStroke(2.0f));
        this.availableStrokeSamples[2] = new StrokeSample(
                new BasicStroke(3.0f));

        JTabbedPane other = getOtherTabs();

        JPanel range = new JPanel(new LCBLayout(3));
        range.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        range.add(new JPanel());
        this.autoRangeCheckBox = new JCheckBox(localizationResources.getString(
                "Auto-adjust_range"), this.autoRange);
        this.autoRangeCheckBox.setActionCommand("AutoRangeOnOff");
        this.autoRangeCheckBox.addActionListener(this);
        range.add(this.autoRangeCheckBox);
        range.add(new JPanel());

        range.add(new JLabel(localizationResources.getString(
                "Minimum_range_value")));
        this.minimumRangeValue = new JTextField(Double.toString(
                this.minimumValue));
        this.minimumRangeValue.setEnabled(!this.autoRange);
        this.minimumRangeValue.setActionCommand("MinimumRange");
        this.minimumRangeValue.addActionListener(this);
        this.minimumRangeValue.addFocusListener(this);
        range.add(this.minimumRangeValue);
        range.add(new JPanel());

        range.add(new JLabel(localizationResources.getString(
                "Maximum_range_value")));
        this.maximumRangeValue = new JTextField(Double.toString(
                this.maximumValue));
        this.maximumRangeValue.setEnabled(!this.autoRange);
        this.maximumRangeValue.setActionCommand("MaximumRange");
        this.maximumRangeValue.addActionListener(this);
        this.maximumRangeValue.addFocusListener(this);
        range.add(this.maximumRangeValue);
        range.add(new JPanel());

        other.add(localizationResources.getString("Range"), range);

        other.add(localizationResources.getString("TickUnit"),
                createTickUnitPanel());
    }

    protected JPanel createTickUnitPanel() {
        JPanel tickUnitPanel = new JPanel(new LCBLayout(3));
        tickUnitPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        tickUnitPanel.add(new JPanel());
        this.autoTickUnitSelectionCheckBox = new JCheckBox(
                localizationResources.getString("Auto-TickUnit_Selection"),
                this.autoTickUnitSelection);
        this.autoTickUnitSelectionCheckBox.setActionCommand("AutoTickOnOff");
        this.autoTickUnitSelectionCheckBox.addActionListener(this);
        tickUnitPanel.add(this.autoTickUnitSelectionCheckBox);
        tickUnitPanel.add(new JPanel());

        return tickUnitPanel;
    }

    /**
     * Getter for the {@link #autoTickUnitSelection} flag.
     * 
     * @return The value of the flag for enabling auto-tickunit-selection.
     */
    protected boolean isAutoTickUnitSelection() {
        return autoTickUnitSelection;
    }

    /**
     * Setter for the {@link #autoTickUnitSelection} flag.
     * @param autoTickUnitSelection The new value for auto-tickunit-selection.
     */
    protected void setAutoTickUnitSelection(boolean autoTickUnitSelection) {
        this.autoTickUnitSelection = autoTickUnitSelection;
    }

    /**
     * Get the checkbox that enables/disables auto-tickunit-selection.
     * 
     * @return The checkbox.
     */
    protected JCheckBox getAutoTickUnitSelectionCheckBox() {
        return autoTickUnitSelectionCheckBox;
    }

    /**
     * Set the checkbox that enables/disables auto-tickunit-selection.
     *
     * @param autoTickUnitSelectionCheckBox The checkbox.
     */
    protected void setAutoTickUnitSelectionCheckBox(
            JCheckBox autoTickUnitSelectionCheckBox) {
        this.autoTickUnitSelectionCheckBox = autoTickUnitSelectionCheckBox;
    }

    /**
     * Returns the current setting of the auto-range property.
     *
     * @return <code>true</code> if auto range is enabled.
     */
    public boolean isAutoRange() {
        return this.autoRange;
    }

    /**
     * Returns the current setting of the minimum value in the axis range.
     *
     * @return The current setting of the minimum value in the axis range.
     */
    public double getMinimumValue() {
        return this.minimumValue;
    }

    /**
     * Returns the current setting of the maximum value in the axis range.
     *
     * @return The current setting of the maximum value in the axis range.
     */
    public double getMaximumValue() {
        return this.maximumValue;
    }

    /**
     * Handles actions from within the property panel.
     * @param event an event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("GridStroke")) {
            attemptGridStrokeSelection();
        }
        else if (command.equals("GridPaint")) {
            attemptGridPaintSelection();
        }
        else if (command.equals("AutoRangeOnOff")) {
            toggleAutoRange();
        }
        else if (command.equals("MinimumRange")) {
            validateMinimum();
        }
        else if (command.equals("MaximumRange")) {
            validateMaximum();
        }
        else if (command.equals("AutoTickOnOff")) {
            toggleAutoTick();
        }
        else {
            // pass to the super-class for handling
            super.actionPerformed(event);
        }
    }

    /**
     * Handle a grid stroke selection.
     */
    protected void attemptGridStrokeSelection() {
        StrokeChooserPanel panel = new StrokeChooserPanel(this.gridStrokeSample,
                this.availableStrokeSamples);
        int result = JOptionPane.showConfirmDialog(this, panel,
                localizationResources.getString("Stroke_Selection"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            this.gridStrokeSample.setStroke(panel.getSelectedStroke());
        }
    }

    /**
     * Handle a grid paint selection.
     */
    protected void attemptGridPaintSelection() {
        Color c;
        c = JColorChooser.showDialog(this, localizationResources.getString(
                "Grid_Color"), Color.blue);
        if (c != null) {
            this.gridPaintSample.setPaint(c);
        }
    }

    /**
     * Does nothing.
     *
     * @param event  the event.
     */
    @Override
    public void focusGained(FocusEvent event) {
        // don't need to do anything
    }

    /**
     *  Revalidates minimum/maximum range.
     *
     *  @param event  the event.
     */
    @Override
    public void focusLost(FocusEvent event) {
        if (event.getSource() == this.minimumRangeValue) {
            validateMinimum();
        }
        else if (event.getSource() == this.maximumRangeValue) {
            validateMaximum();
        }
    }

    /**
     *  Toggle the auto range setting.
     */
    public void toggleAutoRange() {
        this.autoRange = this.autoRangeCheckBox.isSelected();
        if (this.autoRange) {
            this.minimumRangeValue.setText(Double.toString(this.minimumValue));
            this.minimumRangeValue.setEnabled(false);
            this.maximumRangeValue.setText(Double.toString(this.maximumValue));
            this.maximumRangeValue.setEnabled(false);
        }
        else {
            this.minimumRangeValue.setEnabled(true);
            this.maximumRangeValue.setEnabled(true);
        }
    }

    public void toggleAutoTick() {
        this.autoTickUnitSelection = this.autoTickUnitSelectionCheckBox.isSelected();
    }

    /**
     * Revalidate the range minimum.
     */
    public void validateMinimum() {
        double newMin;
        try {
            newMin = Double.parseDouble(this.minimumRangeValue.getText());
            if (newMin >= this.maximumValue) {
                newMin = this.minimumValue;
            }
        }
        catch (NumberFormatException e) {
            newMin = this.minimumValue;
        }

        this.minimumValue = newMin;
        this.minimumRangeValue.setText(Double.toString(this.minimumValue));
    }

    /**
     * Revalidate the range maximum.
     */
    public void validateMaximum() {
        double newMax;
        try {
            newMax = Double.parseDouble(this.maximumRangeValue.getText());
            if (newMax <= this.minimumValue) {
                newMax = this.maximumValue;
            }
        }
        catch (NumberFormatException e) {
            newMax = this.maximumValue;
        }

        this.maximumValue = newMax;
        this.maximumRangeValue.setText(Double.toString(this.maximumValue));
    }

    /**
     * Sets the properties of the specified axis to match the properties
     * defined on this panel.
     *
     * @param axis  the axis.
     */
    @Override
    public void setAxisProperties(Axis axis) {
        super.setAxisProperties(axis);
        ValueAxis valueAxis = (ValueAxis) axis;
        valueAxis.setAutoRange(this.autoRange);
        if (!this.autoRange) {
            valueAxis.setRange(this.minimumValue, this.maximumValue);
        }
        valueAxis.setAutoTickUnitSelection(this.autoTickUnitSelection);
    }
}
