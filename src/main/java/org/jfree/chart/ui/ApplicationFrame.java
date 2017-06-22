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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 * A base class for creating the main frame for simple applications.  The frame 
 * listens for window closing events, and responds by shutting down the JVM.  
 * This is OK for small demo applications...for more serious applications, 
 * you'll want to use something more robust.
 */
public class ApplicationFrame extends JFrame implements WindowListener {

    /**
     * Constructs a new application frame.
     *
     * @param title  the frame title.
     */
    public ApplicationFrame(String title) {
        super(title);
        addWindowListener(this);
    }

    /**
     * Listens for the main window closing, and shuts down the application.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowClosing(WindowEvent event) {
        if (event.getWindow() == this) {
            dispose();
            System.exit(0);
        }
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowClosed(WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowActivated(WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowDeactivated(WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowDeiconified(WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowIconified(WindowEvent event) {
        // ignore
    }

    /**
     * Required for WindowListener interface, but not used by this class.
     *
     * @param event  information about the window event.
     */
    @Override
    public void windowOpened(WindowEvent event) {
        // ignore
    }

}
