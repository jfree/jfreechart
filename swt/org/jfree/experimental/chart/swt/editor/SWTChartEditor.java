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
 * -------------------
 * SWTChartEditor.java
 * -------------------
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

import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.editor.ChartEditor;

/**
 * An editor for chart properties.
 */
public class SWTChartEditor implements ChartEditor {
    
    /** The shell */
    private Shell shell;
    
    /** The chart which the properties have to be edited */
    private JFreeChart chart;
    
    /** A composite for displaying/editing the properties of the title. */
    private SWTTitleEditor titleEditor;

    /** A composite for displaying/editing the properties of the plot. */
    private SWTPlotEditor plotEditor;
    
    /** A composite for displaying/editing the other properties of the chart. */
    private SWTOtherEditor otherEditor;

    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources 
        = ResourceBundle.getBundle("org.jfree.chart.editor.LocalizationBundle");
    
    /**
     * Creates a new editor.
     * 
     * @param display  the display.
     * @param chart2edit  the chart to edit.
     */
    public SWTChartEditor(Display display, JFreeChart chart2edit) {
        shell = new Shell(display, SWT.DIALOG_TRIM);
        shell.setSize(400, 500);
        this.chart = chart2edit;
        shell.setText(ResourceBundle.getBundle(
                "org.jfree.chart.LocalizationBundle").getString(
                        "Chart_Properties"));
        GridLayout layout = new GridLayout(2, true);
        layout.marginLeft = layout.marginTop = layout.marginRight 
                = layout.marginBottom = 5;
        shell.setLayout(layout);
        Composite main = new Composite(shell, SWT.NONE);
        main.setLayout(new FillLayout());
        main.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        
        TabFolder tab = new TabFolder(main, SWT.BORDER);
        // build first tab
        TabItem item1 = new TabItem(tab, SWT.NONE);
        item1.setText(" " + localizationResources.getString("Title") + " ");
        titleEditor = new SWTTitleEditor(tab, SWT.NONE, chart.getTitle());
        item1.setControl(titleEditor);
        // build second tab
        TabItem item2 = new TabItem(tab, SWT.NONE);
        item2.setText(" " + localizationResources.getString( "Plot" ) + " ");
        plotEditor = new SWTPlotEditor(tab, SWT.NONE, chart.getPlot());
        item2.setControl(plotEditor);
        // build the third tab
        TabItem item3 = new TabItem(tab, SWT.NONE);
        item3.setText(" " + localizationResources.getString("Other") + " ");
        otherEditor = new SWTOtherEditor(tab, SWT.NONE, chart);
        item3.setControl(otherEditor);
        
        // ok and cancel buttons
        Button ok = new Button(shell, SWT.PUSH | SWT.OK);
        ok.setText(" Ok ");
        ok.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {            
                updateChart(chart);
                shell.dispose();
            }
        });
        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText(" Cancel ");
        cancel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {            
                shell.dispose();
            }
        } );
    }
    
    /**
     * Opens the editor.
     */
    public void open() {
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }

    /**
     * Updates the chart properties.
     * 
     * @param chart  the chart.
     */
    public void updateChart(JFreeChart chart)
    {
        this.titleEditor.setTitleProperties(chart);
        this.plotEditor.updatePlotProperties(chart.getPlot());
        this.otherEditor.updateChartProperties(chart );
    }

}
