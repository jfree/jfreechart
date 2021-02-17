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
 * DefaultFlowDataset.java
 * -----------------------
 * (C) Copyright 2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.data.flow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.CloneUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.general.AbstractDataset;

/**
 * A dataset representing flows between source and destination nodes.
 * 
 * @param <K> the type for the keys used to identify sources and destinations 
 *     (instances should be immutable, {@code String} is a good default choice).
 * 
 * @since 1.5.3
 */
public class DefaultFlowDataset<K extends Comparable<K>> extends AbstractDataset 
        implements FlowDataset<K>, PublicCloneable, Serializable {

    /** 
     * The nodes at each stage.  The list will have N+1 entries, where N is
     * the number of stages - the last entry contains the destination nodes for 
     * the final stage.
     */
    private List<List<K>> nodes;
    
    /** Node properties. */
    private Map<NodeKey, Map<String, Object>> nodeProperties;
    
    /** Storage for the flows. */
    private Map<FlowKey<K>, Number> flows;
    
    /** Flow properties. */
    private Map<FlowKey, Map<String, Object>> flowProperties;

    /**
     * Creates a new dataset that is initially empty.
     */
    public DefaultFlowDataset() {
        this.nodes = new ArrayList<>();
        this.nodes.add(new ArrayList<>());
        this.nodes.add(new ArrayList<>());
        this.nodeProperties = new HashMap<>();
        this.flows = new HashMap<>();
        this.flowProperties = new HashMap<>();
    }

    /**
     * Returns a list of the source nodes for the specified stage.
     * 
     * @param stage  the stage (0 to {@code getStageCount() - 1}).
     * 
     * @return A list of source nodes (possibly empty but never {@code null}). 
     */
    @Override
    public List<K> getSources(int stage) {
        return new ArrayList<>(this.nodes.get(stage));
    }

    /**
     * Returns a list of the destination nodes for the specified stage.
     * 
     * @param stage  the stage (0 to {@code getStageCount() - 1}).
     * 
     * @return A list of destination nodes (possibly empty but never {@code null}). 
     */
    @Override
    public List<K> getDestinations(int stage) {
        return new ArrayList<>(this.nodes.get(stage + 1));
    }

    /**
     * Returns the set of keys for all the nodes in the dataset.
     * 
     * @return The set of keys for all the nodes in the dataset (possibly empty 
     *     but never {@code null}).
     */
    @Override
    public Set<NodeKey<K>> getAllNodes() {
        Set<NodeKey<K>> result = new HashSet<>();
        for (int s = 0; s <= this.getStageCount(); s++) {
            for (K key : this.getSources(s)) {
                result.add(new NodeKey<>(s, key));
            }
        }
        return result;
    }
 
    /**
     * Returns the value of a property, if specified, for the specified node.  
     *
     * @param nodeKey  the node key ({@code null} not permitted).
     * @param propertyKey  the node key ({@code null} not permitted).
     * 
     * @return The property value, or {@code null}. 
     */    
    @Override
    public Object getNodeProperty(NodeKey<K> nodeKey, String propertyKey) {
        Map<String, Object> props = this.nodeProperties.get(nodeKey);
        if (props != null) {
            return props.get(propertyKey);
        }
        return null;
    }
    
    /**
     * Sets a property for the specified node and notifies registered listeners
     * that the dataset has changed.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * @param propertyKey  the property key ({@code null} not permitted).
     * @param value  the property value.
     */
    public void setNodeProperty(NodeKey<K> nodeKey, String propertyKey, Object value) {
        Map<String, Object> props = this.nodeProperties.get(nodeKey);
        if (props == null) {
            props = new HashMap<>();
            this.nodeProperties.put(nodeKey, props);
        }
        props.put(propertyKey, value);
        fireDatasetChanged();
    }

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
    @Override
    public Number getFlow(int stage, K source, K destination) {
        return this.flows.get(new FlowKey<>(stage, source, destination));
    }

    /**
     * Sets the flow between a source node and a destination node at the 
     * specified stage.  A new stage will be added if {@code stage} is equal
     * to {@code getStageCount()}.
     * 
     * @param stage  the stage (0 to {@code getStageCount()}.
     * @param source  the source ({@code null} not permitted).
     * @param destination  the destination ({@code null} not permitted).
     * @param flow  the flow (0 or greater).
     */
    public void setFlow(int stage, K source, K destination, double flow) {
        Args.requireInRange(stage, "stage", 0, getStageCount());
        Args.nullNotPermitted(source, "source");
        Args.nullNotPermitted(destination, "destination");
        if (stage > this.nodes.size() - 2) {
            this.nodes.add(new ArrayList<>());
        }
        if (!getSources(stage).contains(source)) {
            this.nodes.get(stage).add(source);
        }
        if (!getDestinations(stage).contains(destination)) {
            this.nodes.get(stage + 1).add(destination);
        }
        this.flows.put(new FlowKey<>(stage, source, destination), flow);
        fireDatasetChanged();
    }

    /**
     * Returns the value of a property, if specified, for the specified flow.  
     * 
     * @param flowKey  flowKey ({@code null} not permitted).
     * 
     * @return The property value, or {@code null}. 
     */    
    @Override
    public Object getFlowProperty(FlowKey<K> flowKey, String propertyKey) {
        Map<String, Object> props = this.flowProperties.get(flowKey);
        if (props != null) {
            return props.get(propertyKey);
        }
        return null;      
    }

    /**
     * Sets a property for the specified flow and notifies registered listeners
     * that the dataset has changed.
     * 
     * @param flowKey  the node key ({@code null} not permitted).
     * @param propertyKey  the property key ({@code null} not permitted).
     * @param value  the property value.
     */

    public void setFlowProperty(FlowKey<K> flowKey, String propertyKey, Object value) {
        Map<String, Object> props = this.flowProperties.get(flowKey);
        if (props == null) {
            props = new HashMap<>();
            this.flowProperties.put(flowKey, props);
        }
        props.put(propertyKey, value);
        fireDatasetChanged();
    }

    /**
     * Returns the number of flow stages.  A flow dataset always has one or
     * more stages, so this method will return {@code 1} even for an empty
     * dataset (one with no sources, destinations or flows defined).
     * 
     * @return The number of flow stages.
     */
    @Override
    public int getStageCount() {
        return this.nodes.size() - 1;
    }
    
    /**
     * Returns a set of keys for all the flows in the dataset.
     * 
     * @return A set. 
     */
    @Override
    public Set<FlowKey<K>> getAllFlows() {
        return new HashSet<>(this.flows.keySet());    
    }
    
    /**
     * Returns a list of flow keys for all the flows coming into this node.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * 
     * @return A list of flow keys (possibly empty but never {@code null}). 
     */
    public List<FlowKey<K>> getInFlows(NodeKey nodeKey) {
        Args.nullNotPermitted(nodeKey, "nodeKey");
        if (nodeKey.getStage() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<FlowKey<K>> result = new ArrayList<>();
        for (FlowKey<K> flowKey : this.flows.keySet()) {
            if (flowKey.getStage() == nodeKey.getStage() - 1 && flowKey.getDestination().equals(nodeKey.getNode())) {
                result.add(flowKey);
            }
        }
        return result;
    }

    /**
     * Returns a list of flow keys for all the flows going out of this node.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * 
     * @return A list of flow keys (possibly empty but never {@code null}). 
     */
    public List<FlowKey> getOutFlows(NodeKey nodeKey) {
        Args.nullNotPermitted(nodeKey, "nodeKey");
        if (nodeKey.getStage() == this.getStageCount()) {
            return Collections.EMPTY_LIST;
        }
        List<FlowKey> result = new ArrayList<>();
        for (FlowKey flowKey : this.flows.keySet()) {
            if (flowKey.getStage() == nodeKey.getStage() && flowKey.getSource().equals(nodeKey.getNode())) {
                result.add(flowKey);
            }
        }
        return result;
    }

    /**
     * Returns a clone of the dataset.
     * 
     * @return A clone of the dataset.
     * 
     * @throws CloneNotSupportedException if there is a problem with cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        DefaultFlowDataset<K> clone = (DefaultFlowDataset) super.clone();
        clone.flows = new HashMap<>(this.flows);
        clone.nodes = new ArrayList<>();
        for (List<?> list : nodes) {
            clone.nodes.add((List<K>) CloneUtils.cloneList(list));
        }
        return clone;
    }

    /**
     * Tests this dataset for equality with an arbitrary object.  This method
     * will return {@code true} if the object implements the 
     * {@link FlowDataset} and defines the exact same set of nodes and flows 
     * as this dataset.
     * 
     * @param obj  the object to test equality against ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FlowDataset)) {
            return false;
        }
        final FlowDataset other = (FlowDataset) obj;
        if (other.getStageCount() != getStageCount()) {
            return false;
        }
        for (int stage = 0; stage < getStageCount(); stage++) {
            if (!Objects.equals(other.getSources(stage), getSources(stage))) {
                return false;
            }
            if (!Objects.equals(other.getDestinations(stage), getDestinations(stage))) {
                return false;
            }
            for (K source : getSources(stage)) {
                for (K destination : getDestinations(stage)) {
                    if (!Objects.equals(other.getFlow(stage, source, destination), getFlow(stage, source, destination))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(getSources(0));
        hash = 89 * hash + Objects.hashCode(getDestinations(getStageCount() - 1));
        return hash;
    }

}
