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
 * ---------------------
 * TooltipHandlerFX.java
 * ---------------------
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

import javafx.scene.input.MouseEvent;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.fx.ChartCanvas;

/**
 * Handles the updating of tooltips on a {@link ChartCanvas}.
 * 
 * <p>THE API FOR THIS CLASS IS SUBJECT TO CHANGE IN FUTURE RELEASES.  This is
 * so that we can incorporate feedback on the (new) JavaFX support in 
 * JFreeChart.</p>
 * 
 * @since 1.0.18
 */
public class TooltipHandlerFX extends AbstractMouseHandlerFX 
        implements MouseHandlerFX {
    
    /**
     * Creates a new instance with the specified ID.
     * 
     * @param id  the handler id (<code>null</code> not permitted).
     */
    public TooltipHandlerFX(String id) {
        super(id, false, false, false, false);
    }

    /**
     * Handles a mouse moved event by updating the tooltip.
     * 
     * @param canvas  the chart canvas (<code>null</code> not permitted).
     * @param e  the mouse event.
     */
    @Override
    public void handleMouseMoved(ChartCanvas canvas, MouseEvent e) {
        if (!canvas.isTooltipEnabled()) {
            return;
        }
        String text = getTooltipText(canvas, e.getX(), e.getY());
        canvas.setTooltip(text, e.getScreenX(), e.getScreenY());
    }
    
    /**
     * Returns the tooltip text.
     * 
     * @param canvas  the canvas that is displaying the chart.
     * @param x  the x-coordinate of the mouse pointer.
     * @param y  the y-coordinate of the mouse pointer.
     * 
     * @return String The tooltip text (possibly <code>null</code>).
      */
    private String getTooltipText(ChartCanvas canvas, double x, double y) {
        ChartRenderingInfo info = canvas.getRenderingInfo();
        if (info == null) {
            return null;
        }
        EntityCollection entities = info.getEntityCollection();
        if (entities == null) {
            return null;
        }
        ChartEntity entity = entities.getEntity(x, y);
        if (entity == null) {
            return null;
        }
        return entity.getToolTipText();
    }
    
}
