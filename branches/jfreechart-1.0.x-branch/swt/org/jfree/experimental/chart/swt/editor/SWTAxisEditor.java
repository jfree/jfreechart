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
 * ------------------
 * SWTAxisEditor.java
 * ------------------
 * (C) Copyright 2006-2008, by Henry Proudhon and Contributors.
 *
 * Original Author:  Henry Proudhon (henry.proudhon AT ensmp.fr);
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 01-Aug-2006 : New class (HP);
 * 07-Feb-2007 : Fixed bug 1647749, handle null axis labels (DG);
 * 18-Dec-2008 : Use ResourceBundleWrapper - see patch 1607918 by
 *               Jess Thrysoee (DG);
 *
 */

package org.jfree.experimental.chart.swt.editor;

import java.awt.Paint;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.util.ResourceBundleWrapper;
import org.jfree.experimental.swt.SWTPaintCanvas;
import org.jfree.experimental.swt.SWTUtils;

/**
 * An editor for axis properties.
 */
class SWTAxisEditor extends Composite {

    /** The axis label. */
    private Text label;

    /** The font used to draw the axis labels. */
    private FontData labelFont;

    /** The paint (color) used to draw the axis labels. */
    private Color labelPaintColor;

    /** The font used to draw the axis tick labels. */
    private FontData tickLabelFont;

    /** The paint (color) used to draw the axis tick labels. */
    private Color tickLabelPaintColor;

    /** A field showing a description of the label font. */
    private Text labelFontField;

    /**
     * A field containing a description of the font
     * for displaying tick labels on the axis.
     */
    private Text tickLabelFontField;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources
            =  ResourceBundleWrapper.getBundle(
                    "org.jfree.chart.editor.LocalizationBundle");

    /** Font object used to handle a change of font. */
    private Font font;

    /** A flag that indicates whether or not the tick labels are visible. */
    private Button showTickLabelsCheckBox;

    /** A flag that indicates whether or not the tick marks are visible. */
    private Button showTickMarksCheckBox;

    /** A tabbed pane for... */
    private TabFolder otherTabs;

    /**
     * Standard constructor: builds a composite for displaying/editing
     * the properties of the specified axis.
     *
     * @param parent The parent composite.
     * @param style The SWT style of the SwtAxisEditor.
     * @param axis  the axis whose properties are to be displayed/edited
     *              in the composite.
     */
    public SWTAxisEditor(Composite parent, int style, Axis axis) {
        super(parent, style);
        this.labelFont = SWTUtils.toSwtFontData(getDisplay(),
                axis.getLabelFont(), true);
        this.labelPaintColor = SWTUtils.toSwtColor(getDisplay(),
                axis.getLabelPaint());
        this.tickLabelFont = SWTUtils.toSwtFontData(getDisplay(),
                axis.getTickLabelFont(), true);
        this.tickLabelPaintColor = SWTUtils.toSwtColor(getDisplay(),
                axis.getTickLabelPaint());

        FillLayout layout = new FillLayout(SWT.VERTICAL);
        layout.marginHeight = layout.marginWidth = 4;
        setLayout(layout);
        Group general = new Group(this, SWT.NONE);
        general.setLayout(new GridLayout(3, false));
        general.setText(localizationResources.getString("General"));
        // row 1
        new Label(general, SWT.NONE).setText(localizationResources.getString(
                "Label"));
        this.label = new Text(general, SWT.BORDER);
        if (axis.getLabel() != null) {
            this.label.setText(axis.getLabel());
        }
        this.label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        new Label(general, SWT.NONE).setText(""); //empty cell
        // row 2
        new Label(general, SWT.NONE).setText(localizationResources.getString(
                "Font"));
        this.labelFontField = new Text(general, SWT.BORDER);
        this.labelFontField.setText(this.labelFont.toString());
        this.labelFontField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        Button selectFontButton = new Button(general, SWT.PUSH);
        selectFontButton.setText(localizationResources.getString("Select..."));
        selectFontButton.addSelectionListener(
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        // Create the color-change dialog
                        FontDialog dlg = new FontDialog(getShell());
                        dlg.setText(localizationResources.getString(
                                "Font_Selection"));
                        dlg.setFontList(new FontData[] {
                                SWTAxisEditor.this.labelFont });
                        if (dlg.open() != null) {
                            // Dispose of any fonts we have created
                            if (SWTAxisEditor.this.font != null) {
                                SWTAxisEditor.this.font.dispose();
                            }
                            // Create the new font and set it into the title
                            // label
                            SWTAxisEditor.this.font = new Font(
                                    getShell().getDisplay(), dlg.getFontList());
                            //label.setFont(font);
                            SWTAxisEditor.this.labelFontField.setText(
                                    SWTAxisEditor.this.font.getFontData()[0]
                                    .toString());
                            SWTAxisEditor.this.labelFont
                                    = SWTAxisEditor.this.font.getFontData()[0];
                        }
                    }
                }
        );
        // row 3
        new Label(general, SWT.NONE).setText(localizationResources.getString(
                "Paint"));
        // Use a colored text field to show the color
        final SWTPaintCanvas colorCanvas = new SWTPaintCanvas(general,
                SWT.NONE, this.labelPaintColor);
        GridData canvasGridData = new GridData(SWT.FILL, SWT.CENTER, true,
                false);
        canvasGridData.heightHint = 20;
        colorCanvas.setLayoutData(canvasGridData);
        Button selectColorButton = new Button(general, SWT.PUSH);
        selectColorButton.setText(localizationResources.getString("Select..."));
        selectColorButton.addSelectionListener(
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        // Create the color-change dialog
                        ColorDialog dlg = new ColorDialog(getShell());
                        dlg.setText(localizationResources.getString(
                                "Title_Color"));
                        dlg.setRGB(SWTAxisEditor.this.labelPaintColor.getRGB());
                        RGB rgb = dlg.open();
                        if (rgb != null) {
                          // create the new color and set it to the
                          // SwtPaintCanvas
                            SWTAxisEditor.this.labelPaintColor = new Color(
                                    getDisplay(), rgb);
                            colorCanvas.setColor(
                                    SWTAxisEditor.this.labelPaintColor);
                        }
                    }
                }
        );
        Group other = new Group(this, SWT.NONE);
        FillLayout tabLayout = new FillLayout();
        tabLayout.marginHeight = tabLayout.marginWidth = 4;
        other.setLayout(tabLayout);
        other.setText(localizationResources.getString("Other"));

        this.otherTabs = new TabFolder(other, SWT.NONE);
        TabItem item1 = new TabItem(this.otherTabs, SWT.NONE);
        item1.setText(" " + localizationResources.getString("Ticks") + " ");
        Composite ticks = new Composite(this.otherTabs, SWT.NONE);
        ticks.setLayout(new GridLayout(3, false));
        this.showTickLabelsCheckBox = new Button(ticks, SWT.CHECK);
        this.showTickLabelsCheckBox.setText(localizationResources.getString(
                "Show_tick_labels"));
        this.showTickLabelsCheckBox.setSelection(axis.isTickLabelsVisible());
        this.showTickLabelsCheckBox.setLayoutData(new GridData(SWT.FILL,
                SWT.CENTER, true, false, 3, 1));
        new Label(ticks, SWT.NONE).setText(localizationResources.getString(
                "Tick_label_font"));
        this.tickLabelFontField = new Text(ticks, SWT.BORDER);
        this.tickLabelFontField.setText(this.tickLabelFont.toString());
        //tickLabelFontField.setFont(SwtUtils.toSwtFontData(getDisplay(),
        // axis.getTickLabelFont()));
        this.tickLabelFontField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
                true, false));
        Button selectTickLabelFontButton = new Button(ticks, SWT.PUSH);
        selectTickLabelFontButton.setText(localizationResources.getString(
                "Select..."));
        selectTickLabelFontButton.addSelectionListener(
                new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent event) {
                        // Create the font-change dialog
                        FontDialog dlg = new FontDialog(getShell());
                        dlg.setText(localizationResources.getString(
                                "Font_Selection"));
                        dlg.setFontList(new FontData[] {
                                SWTAxisEditor.this.tickLabelFont});
                        if (dlg.open() != null) {
                            // Dispose of any fonts we have created
                            if (SWTAxisEditor.this.font != null) {
                                SWTAxisEditor.this.font.dispose();
                            }
                            // Create the new font and set it into the title
                            // label
                            SWTAxisEditor.this.font = new Font(
                                    getShell().getDisplay(), dlg.getFontList());
                            //tickLabelFontField.setFont(font);
                            SWTAxisEditor.this.tickLabelFontField.setText(
                                    SWTAxisEditor.this.font.getFontData()[0]
                                    .toString());
                            SWTAxisEditor.this.tickLabelFont
                                    = SWTAxisEditor.this.font.getFontData()[0];
                        }
                    }
                }
        );
        this.showTickMarksCheckBox = new Button(ticks, SWT.CHECK);
        this.showTickMarksCheckBox.setText(localizationResources.getString(
                "Show_tick_marks"));
        this.showTickMarksCheckBox.setSelection(axis.isTickMarksVisible());
        this.showTickMarksCheckBox.setLayoutData(new GridData(SWT.FILL,
                SWT.CENTER, true, false, 3, 1));
        item1.setControl(ticks);
    }

    /**
     * A static method that returns a panel that is appropriate
     * for the axis type.
     *
     * @param parent  the parent.
     * @param style  the style.
     * @param axis  the axis whose properties are to be displayed/edited
     *              in the composite.
     * @return A composite or <code>null</code< if axis is <code>null</code>.
     */
    public static SWTAxisEditor getInstance(Composite parent, int style,
            Axis axis) {

        if (axis != null) {
            // return the appropriate axis editor
            if (axis instanceof NumberAxis)
                return new SWTNumberAxisEditor(parent, style,
                        (NumberAxis) axis);
            else return new SWTAxisEditor(parent, style, axis);
        }
        else return null;
    }

    /**
     * Returns a reference to the tabbed composite.
     *
     * @return A reference to the tabbed composite.
     */
    public TabFolder getOtherTabs() {
        return this.otherTabs;
    }

    /**
     * Returns the current axis label.
     *
     * @return The current axis label.
     */
    public String getLabel() {
        return this.label.getText();
    }

    /**
     * Returns the current label font.
     *
     * @return The current label font.
     */
    public java.awt.Font getLabelFont() {
        return SWTUtils.toAwtFont(getDisplay(), this.labelFont, true);
    }

    /**
     * Returns the current label paint.
     *
     * @return The current label paint.
     */
    public Paint getTickLabelPaint() {
        return SWTUtils.toAwtColor(this.tickLabelPaintColor);
    }

    /**
     * Returns the current label font.
     *
     * @return The current label font.
     */
    public java.awt.Font getTickLabelFont() {
        return SWTUtils.toAwtFont(getDisplay(), this.tickLabelFont, true);
    }

    /**
     * Returns the current label paint.
     *
     * @return The current label paint.
     */
    public Paint getLabelPaint() {
        return SWTUtils.toAwtColor(this.labelPaintColor);
    }

    /**
     * Sets the properties of the specified axis to match
     * the properties defined on this panel.
     *
     * @param axis  the axis.
     */
    public void setAxisProperties(Axis axis) {
        axis.setLabel(getLabel());
        axis.setLabelFont(getLabelFont());
        axis.setLabelPaint(getLabelPaint());
        axis.setTickMarksVisible(this.showTickMarksCheckBox.getSelection());
        axis.setTickLabelsVisible(this.showTickLabelsCheckBox.getSelection());
        axis.setTickLabelFont(getTickLabelFont());
        axis.setTickLabelPaint(getTickLabelPaint());
    }
}
