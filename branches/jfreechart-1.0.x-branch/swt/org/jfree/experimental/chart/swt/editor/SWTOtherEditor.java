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
 * -------------------
 * SWTOtherEditor.java
 * -------------------
 * (C) Copyright 2006-2008, by Henry Proudhon and Contributors.
 *
 * Original Author:  Henry Proudhon (henry.proudhon AT ensmp.fr);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 01-Aug-2006 : New class (HP);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see patch 1607918 by
 *               Jess Thrysoee (DG);
 *
 */

package org.jfree.experimental.chart.swt.editor;

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.experimental.swt.SWTPaintCanvas;
import org.jfree.experimental.swt.SWTUtils;

/**
 * An editor for miscellaneous chart properties.
 */
class SWTOtherEditor extends Composite {

    /** A checkbox indicating whether or not
     * the chart is drawn with anti-aliasing. */
    private Button antialias;

    /** The chart background color. */
    private SWTPaintCanvas backgroundPaintCanvas;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            = ResourceBundleWrapper.getBundle(
                    "org.jfree.chart.editor.LocalizationBundle");

    /**
     * Creates a new instance.
     *
     * @param parent  the parent.
     * @param style  the style.
     * @param chart  the chart.
     */
    public SWTOtherEditor(Composite parent, int style, JFreeChart chart) {
        super(parent, style);
        FillLayout layout = new FillLayout();
        layout.marginHeight = layout.marginWidth = 4;
        setLayout(layout);

        Group general = new Group(this, SWT.NONE);
        general.setLayout(new GridLayout(3, false));
        general.setText(localizationResources.getString("General"));

        // row 1: antialiasing
        this.antialias = new Button(general, SWT.CHECK);
        this.antialias.setText(localizationResources.getString(
                "Draw_anti-aliased"));
        this.antialias.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
                false, 3, 1));
        this.antialias.setSelection(chart.getAntiAlias());

        //row 2: background paint for the chart
        new Label(general, SWT.NONE).setText(localizationResources.getString(
                "Background_paint"));
        this.backgroundPaintCanvas = new SWTPaintCanvas(general, SWT.NONE,
                SWTUtils.toSwtColor(getDisplay(), chart.getBackgroundPaint()));
        GridData bgGridData = new GridData(SWT.FILL, SWT.CENTER, true, false);
        bgGridData.heightHint = 20;
        this.backgroundPaintCanvas.setLayoutData(bgGridData);
        Button selectBgPaint = new Button(general, SWT.PUSH);
        selectBgPaint.setText(localizationResources.getString("Select..."));
        selectBgPaint.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
                false));
        selectBgPaint.addSelectionListener(
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        ColorDialog dlg = new ColorDialog(getShell());
                        dlg.setText(localizationResources.getString(
                                "Background_paint"));
                        dlg.setRGB(SWTOtherEditor.this.backgroundPaintCanvas
                                .getColor().getRGB());
                        RGB rgb = dlg.open();
                        if (rgb != null) {
                            SWTOtherEditor.this.backgroundPaintCanvas.setColor(
                                    new Color(getDisplay(), rgb));
                        }
                    }
                }
        );
    }

    /**
     * Updates the chart.
     *
     * @param chart  the chart.
     */
    public void updateChartProperties(JFreeChart chart) {
        chart.setAntiAlias(this.antialias.getSelection());
        chart.setBackgroundPaint(SWTUtils.toAwtColor(
                this.backgroundPaintCanvas.getColor()));
    }

}
