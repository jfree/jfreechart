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
 * ---------------------------
 * RendererChangeDetector.java
 * ---------------------------
 * (C) Copyright 2003, 2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: RendererChangeDetector.java,v 1.1.2.1 2006/10/03 15:41:48 mungady Exp $
 *
 * Changes
 * -------
 * 29-Oct-2003 : Version 1 (DG);
 *
 */

package org.jfree.chart.renderer.junit;

import org.jfree.chart.event.RendererChangeEvent;
import org.jfree.chart.event.RendererChangeListener;

/**
 * A simple class for detecting whether or not a renderer has generated 
 * a {@link RendererChangeEvent}.
 */
public class RendererChangeDetector implements RendererChangeListener {
   
    /** A flag that records whether or not a change event has been received. */ 
    private boolean notified;
    
    /**
     * Creates a new detector.
     */
    public RendererChangeDetector() {
        this.notified = false;
    }
    
    /**
     * Returns the flag that indicates whether or not a change event has been 
     * received.
     * 
     * @return The flag.
     */
    public boolean getNotified() {
        return this.notified;
    }
    
    /**
     * Sets the flag that indicates whether or not a change event has been 
     * received.
     * 
     * @param notified  the new value of the flag.
     */
    public void setNotified(boolean notified) {
        this.notified = notified;
    }
    
    /**
     * Receives a {@link RendererChangeEvent} from a renderer.
     * 
     * @param event  the event.
     */
    public void rendererChanged(RendererChangeEvent event) {
        this.notified = true;
    }
    
}
