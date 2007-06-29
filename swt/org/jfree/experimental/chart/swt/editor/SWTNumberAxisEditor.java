/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------------
 * SWTNumberAxisEditor.java
 * ------------------------
 * (C) Copyright 2006, by Henry Proudhon and Contributors.
 *
 * Original Author:  Henry Proudhon (henry.proudhon AT insa-lyon.fr);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 01-Aug-2006 : New class (HP);
 * 
 */

package org.jfree.experimental.chart.swt.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;

/**
 * An editor for {@link NumberAxis} properties.
 */
class SWTNumberAxisEditor extends SWTAxisEditor implements FocusListener {
    
    /** A flag that indicates whether or not the axis range is determined
     *  automatically.
     */
    private boolean autoRange;

    /** The lowest value in the axis range. */
    private double minimumValue;

    /** The highest value in the axis range. */
    private double maximumValue;

    /** A checkbox that indicates whether or not the axis range is determined
     *  automatically.
     */
    private Button autoRangeCheckBox;

    /** A text field for entering the minimum value in the axis range. */
    private Text minimumRangeValue;

    /** A text field for entering the maximum value in the axis range. */
    private Text maximumRangeValue;

    /**
     * Creates a new editor.
     * 
     * @param parent  the parent.
     * @param style  the style.
     * @param axis  the axis.
     */
    public SWTNumberAxisEditor(Composite parent, int style, NumberAxis axis) {
        super(parent, style, axis);
        this.autoRange = axis.isAutoRange();
        this.minimumValue = axis.getLowerBound();
        this.maximumValue = axis.getUpperBound();

        TabItem item2 = new TabItem(getOtherTabs(), SWT.NONE);
        item2.setText(" " + localizationResources.getString("Range") + " ");
        Composite range = new Composite(getOtherTabs(), SWT.NONE);
        range.setLayout(new GridLayout(2, true));
        item2.setControl(range);
        
        autoRangeCheckBox = new Button(range, SWT.CHECK);
        autoRangeCheckBox.setText(localizationResources.getString(
                "Auto-adjust_range"));
        autoRangeCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, 
                true, false, 2, 1));
        autoRangeCheckBox.setSelection(this.autoRange);
        autoRangeCheckBox.addSelectionListener( 
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent e) { 
                        toggleAutoRange();
                    }
                });
        new Label(range, SWT.NONE).setText(localizationResources.getString(
                "Minimum_range_value"));
        this.minimumRangeValue = new Text(range, SWT.BORDER);
        this.minimumRangeValue.setText(String.valueOf(this.minimumValue));
        this.minimumRangeValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        this.minimumRangeValue.setEnabled(!this.autoRange);
        //this.minimumRangeValue.addModifyListener(this);
        //this.minimumRangeValue.addVerifyListener(this);
        this.minimumRangeValue.addFocusListener(this);
        new Label(range, SWT.NONE).setText(localizationResources.getString(
                "Maximum_range_value"));
        this.maximumRangeValue = new Text(range, SWT.BORDER);
        this.maximumRangeValue.setText(String.valueOf(this.maximumValue));
        this.maximumRangeValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        this.maximumRangeValue.setEnabled(!this.autoRange);
        //this.maximumRangeValue.addModifyListener(this);
        //this.maximumRangeValue.addVerifyListener(this);
        this.maximumRangeValue.addFocusListener(this);
    }

    /**
     *  Toggle the auto range setting.
     */
    public void toggleAutoRange() {
        this.autoRange = this.autoRangeCheckBox.getSelection();
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

    /**
     * Revalidate the range minimum:
     * it should be less than the current maximum.
     * 
     * @param candidate  the minimum value
     * 
     * @return A boolean.
     */
    public boolean validateMinimum(String candidate)
    {
        boolean valid = true;
        try {
            if (Double.parseDouble(candidate) >= this.maximumValue) {
                valid = false;
            }
        }
        catch (NumberFormatException e) {
            valid = false;
        }
        return valid;
    }

    /**
     * Revalidate the range maximum:
     * it should be greater than the current minimum
     * 
     * @param candidate  the maximum value
     * 
     * @return A boolean.
     */
    public boolean validateMaximum(String candidate)
    {
        boolean valid = true;
        try {
            if (Double.parseDouble(candidate) <= this.minimumValue) {
                valid = false;
            }
        }
        catch (NumberFormatException e) {
            valid = false;
        }
        return valid;
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.FocusListener#focusGained(
     * org.eclipse.swt.events.FocusEvent)
     */
    public void focusGained(FocusEvent e) {
        // don't need to do anything
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.FocusListener#focusLost(
     * org.eclipse.swt.events.FocusEvent)
     */
    public void focusLost(FocusEvent e) {
        if (e.getSource() == this.minimumRangeValue) {
            // verify min value
            if (! validateMinimum( this.minimumRangeValue.getText()))
                this.minimumRangeValue.setText(String.valueOf(
                        this.minimumValue));
            else
                this.minimumValue = Double.parseDouble(
                        this.minimumRangeValue.getText());
        }
        else if (e.getSource() == this.maximumRangeValue) {
            // verify max value
            if (! validateMaximum(this.maximumRangeValue.getText()))
                this.maximumRangeValue.setText(String.valueOf(
                        this.maximumValue));
            else
                this.maximumValue = Double.parseDouble(
                        this.maximumRangeValue.getText());
        }
    }

    /**
     * Sets the properties of the specified axis to match 
     * the properties defined on this panel.
     *
     * @param axis  the axis.
     */
    public void setAxisProperties(Axis axis) {
        super.setAxisProperties(axis);
        NumberAxis numberAxis = (NumberAxis) axis;
        numberAxis.setAutoRange(this.autoRange);
        if (! this.autoRange)
            numberAxis.setRange(this.minimumValue, this.maximumValue);
    }
}
