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
 * ---------------------
 * FlowDatasetUtils.java
 * ---------------------
 * (C) Copyright 2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import java.util.List;
import org.jfree.chart.util.Args;

/**
 * Utility methods related to {@link FlowDataset}.
 * 
 * @since 1.5.3
 */
public class FlowDatasetUtils {
    
    private FlowDatasetUtils() {
        // no need for instantiation
    }

    /**
     * Returns the total inflow for the specified node (a destination node for
     * the specified stage).
     * 
     * @param <K> the type for the flow identifiers.
     * @param dataset  the dataset ({@code null} not permitted).
     * @param node  the node ({@code null} not permitted).
     * @param stage  the stage.
     * 
     * @return The total inflow volume. 
     */
    public static <K extends Comparable<K>> double calculateInflow(FlowDataset<K> dataset, K node, int stage) {
        Args.nullNotPermitted(dataset, "dataset");
        Args.nullNotPermitted(node, "node");
        Args.requireInRange(stage, "stage", 0, dataset.getStageCount());
        if (stage == 0) {
            return 0.0;  // there are no inflows for stage 0
        }
        double inflow = 0.0;
        List<K> sourceKeys = dataset.getSources(stage - 1);
        for (K key : sourceKeys) {
            Number n = dataset.getFlow(stage - 1, key, node);
            if (n != null) {
                inflow = inflow + n.doubleValue();
            }
        }
        return inflow;
    }

    /**
     * Returns the total outflow for the specified node (a source node for the
     * specified stage).
     * 
     * @param <K> the type for the flow identifiers.
     * @param dataset  the dataset ({@code null} not permitted).
     * @param source  the source node ({@code null} not permitted).
     * @param stage  the stage.
     * 
     * @return The total outflow volume.
     */
    public static <K extends Comparable<K>> double calculateOutflow(FlowDataset<K> dataset, K source, int stage) {
        Args.nullNotPermitted(dataset, "dataset");
        Args.nullNotPermitted(source, "source");
        Args.requireInRange(stage, "stage", 0, dataset.getStageCount());
        if (stage == dataset.getStageCount()) {
            return 0.0;  // there are no outflows for the last stage
        }
        double outflow = 0.0;
        List<K> destinationKeys = dataset.getDestinations(stage);
        for (K key : destinationKeys) {
            Number n = dataset.getFlow(stage, source, key);
            if (n != null) {
                outflow = outflow + n.doubleValue();
            }
        }
        return outflow;
    }

    /**
     * Returns the total flow from all sources to all destinations at the 
     * specified stage.
     * 
     * @param <K> the type for the flow identifiers.
     * @param dataset  the dataset ({@code null} not permitted).
     * @param stage  the stage.
     * 
     * @return The total flow.
     */
    public static <K extends Comparable<K>> double calculateTotalFlow(FlowDataset<K> dataset, int stage) {
        Args.nullNotPermitted(dataset, "dataset");
        double total = 0.0;
        for (K source : dataset.getSources(stage)) {
            for (K destination : dataset.getDestinations(stage)) {
                Number flow = dataset.getFlow(stage, source, destination);
                if (flow != null) {
                    total = total + flow.doubleValue();
                }
            }
        }
        return total;
    }
    
    /**
     * Returns {@code true} if any of the nodes in the dataset have a property 
     * 'selected' with the value {@code Boolean.TRUE}, and 
     * {@code false} otherwise.
     * 
     * @param <K> the type for the node identifiers.
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    public static <K extends Comparable<K>> boolean hasNodeSelections(FlowDataset<K> dataset) {
        Args.nullNotPermitted(dataset, "dataset");
        for (int stage = 0; stage < dataset.getStageCount() + 1; stage++) { // '+1' to include final destination nodes 
            for (K source : dataset.getSources(stage)) {
                NodeKey<K> nodeKey = new NodeKey<>(stage, source);
                if (Boolean.TRUE.equals(dataset.getNodeProperty(nodeKey, NodeKey.SELECTED_PROPERTY_KEY))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Returns the number of selected nodes.
     * 
     * @param <K> the type for the node keys.
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return The number of selected nodes. 
     */
    public static <K extends Comparable<K>> int selectedNodeCount(FlowDataset<K> dataset) {
        Args.nullNotPermitted(dataset, "dataset");
        int result = 0;
        for (int stage = 0; stage < dataset.getStageCount() + 1; stage++) { // '+1' to include final destination nodes 
            for (K source : dataset.getSources(stage)) {
                NodeKey<K> nodeKey = new NodeKey<>(stage, source);
                if (Boolean.TRUE.equals(dataset.getNodeProperty(nodeKey, NodeKey.SELECTED_PROPERTY_KEY))) {
                    result++;
                }
            }
        }
        return result;
    }

    /**
     * Returns {@code true} if any of the flows in the dataset have a property 
     * 'selected' with the value {@code Boolean.TRUE}, and 
     * {@code false} otherwise.
     * 
     * @param <K> the type for the flow keys.
     * @param dataset  the dataset ({@code null} not permitted).
     * 
     * @return A boolean. 
     */
    public static <K extends Comparable<K>> boolean hasFlowSelections(FlowDataset<K> dataset) {
        Args.nullNotPermitted(dataset, "dataset");
        for (int s = 0; s < dataset.getStageCount(); s++) { 
            for (K source : dataset.getSources(s)) {
                for (K destination : dataset.getDestinations(s)) {
                    FlowKey<K> flowKey = new FlowKey<>(s, source, destination);
                    if (Boolean.TRUE.equals(dataset.getFlowProperty(flowKey, FlowKey.SELECTED_PROPERTY_KEY))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

