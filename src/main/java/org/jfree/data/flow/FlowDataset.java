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
 * ----------------
 * FlowDataset.java
 * ----------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import java.util.List;
import java.util.Set;
import org.jfree.data.general.Dataset;

/**
 * A dataset representing value flows from a set of source nodes to a set of 
 * destination nodes.  The number of source nodes does not need to match the
 * number of destination nodes.
 * <br><br>
 * The dataset can represent flows in one or many stages.  In the simple case,
 * the flows are defined in one stage, going directly from a source node to 
 * the final destination node.  In a multi-stage dataset there will be groups
 * of intermediate nodes and the flows are defined stage-by-stage from the
 * source to the destination.
 * 
 * @param <K> the type for the keys used to identify sources and destinations 
 *     (instances should be immutable, {@code String} is a good default choice).
 * 
 * @since 1.5.3
 */
public interface FlowDataset<K extends Comparable<K>> extends Dataset {

    /**
     * Returns the number of flow stages (never less than one).
     * 
     * @return The number of flow stages.
     */
    int getStageCount();
    
    /**
     * Returns a list of the sources at the specified stage.
     * 
     * @param stage  the stage index (0 to {@code getStageCount()} - 1).
     * 
     * @return A list of the sources at the specified stage (never {@code null}).
     */
    List<K> getSources(int stage);
    
    /**
     * Returns a list of the destinations at the specified stage.  For a 
     * multi-stage dataset, the destinations at stage N are the same as the
     * sources at stage N+1.
     * 
     * @param stage  the stage index (0 to {@code getStageCount()} - 1).
     * 
     * @return A list of the sources at the specified stage (never {@code null}).
     */
    List<K> getDestinations(int stage);

    /**
     * Returns the set of keys for all the nodes in the dataset.
     * 
     * @return The set of keys for all the nodes in the dataset (possibly empty but never {@code null}).
     */
    Set<NodeKey<K>> getAllNodes();
    
    /**
     * Returns the value of a property, if specified, for the specified node.  
     *
     * @param nodeKey  the node key ({@code null} not permitted).
     * @param propertyKey  the node key ({@code null} not permitted).
     * 
     * @return The property value, or {@code null}. 
     */    
    Object getNodeProperty(NodeKey<K> nodeKey, String propertyKey);

    /**
     * Returns the flow between a source node and a destination node at a
     * specified stage.  This must be 0 or greater.  The dataset can return
     * {@code null} to represent an unknown value.
     * 
     * @param stage  the stage index (0 to {@code getStageCount()} - 1).
     * @param source  the source ({@code null} not permitted). 
     * @param destination  the destination ({@code null} not permitted).
     * 
     * @return The flow (zero or greater, possibly {@code null}). 
     */
    Number getFlow(int stage, K source, K destination);
    
    /**
     * Returns a set of keys for all the flows in the dataset.
     * 
     * @return A set. 
     */
    Set<FlowKey<K>> getAllFlows();

    /**
     * Returns the value of a property, if specified, for the specified flow.  
     * 
     * @param flowKey  the flow key ({@code null} not permitted).
     * @param propertyKey  the property key ({@code null} not permitted).
     * 
     * @return The property value, or {@code null}. 
     */    
    Object getFlowProperty(FlowKey<K> flowKey, String propertyKey);

}


