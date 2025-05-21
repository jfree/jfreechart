/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * -----------------
 * ChartDeleter.java
 * -----------------
  * (C) Copyright 2002-present, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.servlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * Used for deleting charts from the temporary directory when the users session
 * expires.
 *
 * @deprecated To be removed in JFreeChart 2.0
 */
public class ChartDeleter implements HttpSessionBindingListener, Serializable {

    /** The chart names. */
    private final List<String> chartNames = new ArrayList<>();

    /**
     * Blank constructor.
     */
    public ChartDeleter() {
        super();
    }

    /**
     * Add a chart to be deleted when the session expires
     *
     * @param filename  the name of the chart in the temporary directory to be
     *                  deleted.
     */
    public void addChart(String filename) {
        this.chartNames.add(filename);
    }

    /**
     * Checks to see if a chart is in the list of charts to be deleted
     *
     * @param filename  the name of the chart in the temporary directory.
     *
     * @return A boolean value indicating whether the chart is present in the
     *         list.
     */
    public boolean isChartAvailable(String filename) {
        return (this.chartNames.contains(filename));
    }

    /**
     * Binding this object to the session has no additional effects.
     *
     * @param event  the session bind event.
     */
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        // nothing to do
    }

    /**
     * When this object is unbound from the session (including upon session
     * expiry) the files that have been added to the ArrayList are iterated
     * and deleted.
     *
     * @param event  the session unbind event.
     */
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        for (String filename : this.chartNames) {
            File file = new File(System.getProperty("java.io.tmpdir"), filename);
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
