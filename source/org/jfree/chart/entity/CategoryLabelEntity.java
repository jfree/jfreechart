/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * ------------------------
 * CategoryLabelEntity.java
 * ------------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 02-Oct-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;

import org.jfree.chart.axis.CategoryAxis;

/**
 * An entity to represent the labels on a {@link CategoryAxis}.
 * 
 * @since 1.0.3
 */
public class CategoryLabelEntity extends TickLabelEntity {
    
    /** The category key. */
    private Comparable key;
    
    /**
     * Creates a new entity.
     * 
     * @param key  the category key.
     * @param area  the hotspot.
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text.
     */
    public CategoryLabelEntity(Comparable key, Shape area, 
            String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
        this.key = key;
    }
    
    /**
     * Returns the category key.
     * 
     * @return The category key.
     */
    public Comparable getKey() {
        return this.key;
    }
    
    /**
     * Returns a string representation of this entity.  This is primarily 
     * useful for debugging.
     * 
     * @return A string representation of this entity.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer("CategoryLabelEntity: ");
        buf.append("category=");
        buf.append(this.key);
        buf.append(", tooltip=" + getToolTipText());
        buf.append(", url=" + getURLText());
        return buf.toString();
    }
}
