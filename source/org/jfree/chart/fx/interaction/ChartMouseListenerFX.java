/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * ChartMouseListenerFX.java
 * -------------------------
 * (C) Copyright 2014, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Jun-2014 : Version 1 (DG);
 *
 */

package org.jfree.chart.fx.interaction;

import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.fx.ChartViewer;

/**
 * A mouse listener that can receive event notifications from a (JavaFX) 
 * {@link ChartViewer} instance.  This interface is equivalent to the 
 * {@link ChartMouseListener} interface used for charts in Swing.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public interface ChartMouseListenerFX {
    
    /**
     * Receives notification of a mouse click on a chart.
     * 
     * @param event  event information (never <code>null</code>). 
     */
    void chartMouseClicked(ChartMouseEventFX event);

    /**
     * Receives notification of a mouse move event on a chart.
     *
     * @param event  event information (never <code>null</code>). 
     */
    void chartMouseMoved(ChartMouseEventFX event);

}
