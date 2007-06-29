/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * MyPlotChangeListener.java
 * -------------------------
 * (C) Copyright 2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: MyPlotChangeListener.java,v 1.1.2.1 2006/10/03 15:41:26 mungady Exp $
 *
 * Changes
 * -------
 * 21-Mar-2005 : Version 1 (DG);
 * 
 */

package org.jfree.chart.plot.junit;

import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;

/**
 * A utility class for testing plot events.
 */
public class MyPlotChangeListener implements PlotChangeListener {
   
    private PlotChangeEvent event;
    
    /**
     * Creates a new instance.
     */
    public MyPlotChangeListener() {
        this.event = null;
    }
    
    /**
     * Returns the last event received by the listener.
     * 
     * @return The event.
     */
    public PlotChangeEvent getEvent() {
        return this.event;   
    }
    
    /**
     * Sets the event for the listener.
     * 
     * @param e  the event.
     */
    public void setEvent(PlotChangeEvent e) {
        this.event = e;   
    }
    
    /**
     * Receives notification of a plot change event.
     * 
     * @param e  the event.
     */
    public void plotChanged(PlotChangeEvent e) {
        this.event = e;
    }
    
}
