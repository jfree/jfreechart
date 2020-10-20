/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * DefaultLogAxisEditor.java
 * -------------------------
 * (C) Copyright 2005-2016, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Martin Hoeller;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Nov-2011 : Version 1 (MH);
 *
 */

package org.jfree.chart.editor;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.axis.NumberTickUnit;

/**
 * A panel for editing properties of a {@link LogAxis}.
 */
public class DefaultLogAxisEditor extends DefaultValueAxisEditor {

    private double manualTickUnitValue;

    private JTextField manualTickUnit;

    /**
     * Standard constructor: builds a property panel for the specified axis.
     *
     * @param axis  the axis, which should be changed.
     */
    public DefaultLogAxisEditor(LogAxis axis) {
        super(axis);
        this.manualTickUnitValue = axis.getTickUnit().getSize();
        manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
    }
    
    /**
     * Creates a panel for editing the tick unit.
     * 
     * @return A panel.
     */
    @Override
    protected JPanel createTickUnitPanel() {
        JPanel tickUnitPanel = super.createTickUnitPanel();

        tickUnitPanel.add(new JLabel(localizationResources.getString(
                "Manual_TickUnit_value")));
        this.manualTickUnit = new JTextField(Double.toString(
                this.manualTickUnitValue));
        this.manualTickUnit.setEnabled(!isAutoTickUnitSelection());
        this.manualTickUnit.setActionCommand("TickUnitValue");
        this.manualTickUnit.addActionListener(this);
        this.manualTickUnit.addFocusListener(this);
        tickUnitPanel.add(this.manualTickUnit);
        tickUnitPanel.add(new JPanel());

        return tickUnitPanel;
    }

    /**
     * Handles actions from within the property panel.
     * 
     * @param event an event.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command.equals("TickUnitValue")) {
            validateTickUnit();
        }
        else {
            // pass to the super-class for handling
            super.actionPerformed(event);
        }
    }

    @Override
    public void focusLost(FocusEvent event) {
        super.focusLost(event);
        if (event.getSource() == this.manualTickUnit) {
            validateTickUnit();
        }
    }

    /**
     * Toggles the auto-tick-unit setting.
     */
    @Override
    public void toggleAutoTick() {
        super.toggleAutoTick();
        if (isAutoTickUnitSelection()) {
            this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
            this.manualTickUnit.setEnabled(false);
        }
        else {
            this.manualTickUnit.setEnabled(true);
        }
    }

    /**
     * Validates the tick unit entered.
     */
    public void validateTickUnit() {
        double newTickUnit;
        try {
            newTickUnit = Double.parseDouble(this.manualTickUnit.getText());
        }
        catch (NumberFormatException e) {
            newTickUnit = this.manualTickUnitValue;
        }

        if (newTickUnit > 0.0) {
            this.manualTickUnitValue = newTickUnit;
        }
        this.manualTickUnit.setText(Double.toString(this.manualTickUnitValue));
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
        LogAxis logAxis = (LogAxis) axis;
        if (!isAutoTickUnitSelection()) {
            logAxis.setTickUnit(new NumberTickUnit(manualTickUnitValue));
        }
    }
}
