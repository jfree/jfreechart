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
 * ------------------------------
 * CategoryItemRendererState.java
 * ------------------------------
 * (C) Copyright 2003-2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: CategoryItemRendererState.java,v 1.2.2.2 2006/12/01 16:30:22 mungady Exp $
 *
 * Changes (since 20-Oct-2003):
 * ----------------------------
 * 20-Oct-2003 : Added series running total (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 01-Dec-2006 : Updated API docs (DG);
 *
 */

package org.jfree.chart.renderer.category;

import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.renderer.RendererState;

/**
 * An object that retains temporary state information for a 
 * {@link CategoryItemRenderer}.
 */
public class CategoryItemRendererState extends RendererState {

    /** The bar width. */
    private double barWidth;
    
    /** The series running total. */
    private double seriesRunningTotal;
    
    /**
     * Creates a new object for recording temporary state information for a
     * renderer.
     * 
     * @param info  the plot rendering info (<code>null</code> permitted).
     */
    public CategoryItemRendererState(PlotRenderingInfo info) {
        super(info);
        this.barWidth = 0.0;
        this.seriesRunningTotal = 0.0;
    }
    
    /**
     * Returns the bar width.
     * 
     * @return The bar width.
     * 
     * @see #setBarWidth(double)
     */
    public double getBarWidth() {
        return this.barWidth;
    }
    
    /**
     * Sets the bar width.  The renderer calculates this value and stores it 
     * here - it is not intended that users can manually set the bar width.
     * 
     * @param width  the width.
     * 
     * @see #getBarWidth()
     */
    public void setBarWidth(double width) {
        this.barWidth = width;
    }
    
    /**
     * Returns the series running total.
     * 
     * @return The running total.
     * 
     * @see #setSeriesRunningTotal(double)
     */
    public double getSeriesRunningTotal() {
        return this.seriesRunningTotal;    
    }
    
    /**
     * Sets the series running total (this method is intended for the use of 
     * the renderer only).
     * 
     * @param total  the new total.
     * 
     * @see #getSeriesRunningTotal()
     */
    void setSeriesRunningTotal(double total) {
        this.seriesRunningTotal = total;
    }
    
}
