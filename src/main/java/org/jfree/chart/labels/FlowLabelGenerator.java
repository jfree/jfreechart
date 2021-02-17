/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by Object Refinery Limited and Contributors.
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
 * -----------------------
 * FlowLabelGenerator.java
 * -----------------------
 * (C) Copyright 2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.labels;

import org.jfree.data.flow.FlowDataset;
import org.jfree.data.flow.FlowKey;

/**
 * A label generator for a flow in a flow dataset.
 * 
 * @since 1.5.3
 */
public interface FlowLabelGenerator {
    
    /**
     * Returns a label for the specified flow.
     * 
     * @param dataset  the flow dataset ({@code null} not permitted).
     * @param key  the flow key ({@code null} not permitted).
     * 
     * @return The label (possibly {@code null}). 
     */
    String generateLabel(FlowDataset dataset, FlowKey key);
    
}

