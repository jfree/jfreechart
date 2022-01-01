/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ---------------
 * NodeEntity.java
 * ---------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import org.jfree.chart.internal.Args;
import org.jfree.chart.plot.flow.FlowPlot;
import org.jfree.data.flow.NodeKey;

/**
 * A chart entity representing a node in a {@link FlowPlot}.
 * 
 * @since 1.5.3
 */
public class NodeEntity extends ChartEntity {

    private NodeKey key;
    
    /**
     * Creates a new instance.
     * 
     * @param key  the node key ({@code null} not permitted).
     * @param area  the outline of the entity ({@code null} not permitted).
     * @param toolTipText  the tool tip text.
     */
    public NodeEntity(NodeKey key, Shape area, String toolTipText) {
        super(area, toolTipText);
        Args.nullNotPermitted(key, "key");
        this.key = key;
    }
    
    /**
     * Creates a new instance.
     * 
     * @param area  the outline of the entity ({@code null} not permitted).
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text.
     */
    public NodeEntity(Shape area, String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
    }

    /**
     * Returns the node key.
     * 
     * @return The node key (never {@code null}). 
     */
    public NodeKey getKey() {
        return this.key;
    }
    
    /**
     * Returns a string representation of this instance, primarily for 
     * debugging purposes.
     * 
     * @return A string. 
     */
    @Override
    public String toString() {
        return "[NodeEntity: " + this.key + "]";
    }

}
