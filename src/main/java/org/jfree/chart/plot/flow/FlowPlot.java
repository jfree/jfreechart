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
 * -------------
 * FlowPlot.java
 * -------------
 * (C) Copyright 2021, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.flow;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.FlowEntity;
import org.jfree.chart.entity.NodeEntity;
import org.jfree.chart.labels.FlowLabelGenerator;
import org.jfree.chart.labels.StandardFlowLabelGenerator;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.chart.text.TextUtils;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.chart.util.Args;
import org.jfree.chart.util.PaintUtils;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.flow.FlowDataset;
import org.jfree.data.flow.FlowDatasetUtils;
import org.jfree.data.flow.FlowKey;
import org.jfree.data.flow.NodeKey;

/**
 * A plot for visualising flows defined in a {@link FlowDataset}.  This enables
 * the production of a type of Sankey chart.  The example shown here is 
 * produced by the {@code FlowPlotDemo1.java} program included in the JFreeChart 
 * Demo Collection:
 * <img src="doc-files/FlowPlotDemo1.svg" width="600" height="400" alt="FlowPlotDemo1.svg">
 * 
 * @since 1.5.3
 */
public class FlowPlot extends Plot implements Cloneable, PublicCloneable, 
        Serializable {

    /** The source of data. */
    private FlowDataset dataset;
    
    /** 
     * The node width in Java 2D user-space units.
     */
    private double nodeWidth = 20.0;
    
    /** The gap between nodes (expressed as a percentage of the plot height). */
    private double nodeMargin = 0.01;

    /** 
     * The percentage of the plot width to assign to a gap between the nodes
     * and the flow representation. 
     */
    private double flowMargin = 0.005;
    
    /** 
     * Stores colors for specific nodes - if there isn't a color in here for
     * the node, the default node color will be used (unless the color swatch
     * is active).
     */
    private Map<NodeKey, Color> nodeColorMap;
    
    private List<Color> nodeColorSwatch;
    
    /** A pointer into the color swatch. */
    private int nodeColorSwatchPointer = 0;

    /** The default node color if nothing is defined in the nodeColorMap. */
    private Color defaultNodeColor;

    private Font defaultNodeLabelFont;
    
    private Paint defaultNodeLabelPaint;
    
    private VerticalAlignment nodeLabelAlignment;
    
    /** The x-offset for node labels. */
    private double nodeLabelOffsetX;
    
    /** The y-offset for node labels. */
    private double nodeLabelOffsetY;
    
    /** The tool tip generator - if null, no tool tips will be displayed. */
    private FlowLabelGenerator toolTipGenerator; 
    
    /**
     * Creates a new instance that will source data from the specified dataset.
     * 
     * @param dataset  the dataset. 
     */
    public FlowPlot(FlowDataset dataset) {
        this.dataset = dataset;
        if (dataset != null) {
            dataset.addChangeListener(this);
        }
        this.nodeColorMap = new HashMap<>();
        this.nodeColorSwatch = new ArrayList<>();
        this.defaultNodeColor = Color.GRAY;
        this.defaultNodeLabelFont = new Font(Font.DIALOG, Font.BOLD, 12);
        this.defaultNodeLabelPaint = Color.BLACK;
        this.nodeLabelAlignment = VerticalAlignment.CENTER;
        this.nodeLabelOffsetX = 2.0;
        this.nodeLabelOffsetY = 2.0;
        this.toolTipGenerator = new StandardFlowLabelGenerator();
    }

    /**
     * Returns a string identifying the plot type.
     * 
     * @return A string identifying the plot type.
     */
    @Override
    public String getPlotType() {
        return "FlowPlot";
    }

    /**
     * Returns a reference to the dataset.
     * 
     * @return A reference to the dataset (possibly {@code null}).
     */
    public FlowDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset for the plot and sends a change notification to all
     * registered listeners.
     * 
     * @param dataset  the dataset ({@code null} permitted). 
     */
    public void setDataset(FlowDataset dataset) {
        this.dataset = dataset;
        fireChangeEvent();
    }

    /**
     * Returns the node margin (expressed as a percentage of the available
     * plotting space) which is the gap between nodes (sources or destinations).
     * The initial (default) value is {@code 0.01} (1 percent).
     * 
     * @return The node margin. 
     */
    public double getNodeMargin() {
        return this.nodeMargin;
    }

    /**
     * Sets the node margin and sends a change notification to all registered
     * listeners.
     * 
     * @param margin  the margin (expressed as a percentage). 
     */
    public void setNodeMargin(double margin) {
        Args.requireNonNegative(margin, "margin");
        this.nodeMargin = margin;
        fireChangeEvent();
    }
    

    /**
     * Returns the flow margin.  This determines the gap between the graphic 
     * representation of the nodes (sources and destinations) and the curved
     * flow representation.  This is expressed as a percentage of the plot 
     * width so that it remains proportional as the plot is resized.  The
     * initial (default) value is {@code 0.005} (0.5 percent).
     * 
     * @return The flow margin. 
     */
    public double getFlowMargin() {
        return this.flowMargin;
    }
    
    /**
     * Sets the flow margin and sends a change notification to all registered
     * listeners.
     * 
     * @param margin  the margin (must be 0.0 or higher).
     */
    public void setFlowMargin(double margin) {
        Args.requireNonNegative(margin, "margin");
        this.flowMargin = margin;
        fireChangeEvent();
    }

    /**
     * Returns the width of the source and destination nodes, expressed in 
     * Java2D user-space units.  The initial (default) value is {@code 20.0}.
     * 
     * @return The width. 
     */
    public double getNodeWidth() {
        return this.nodeWidth;
    }

    /**
     * Sets the width for the source and destination nodes and sends a change
     * notification to all registered listeners.
     * 
     * @param width  the width. 
     */
    public void setNodeWidth(double width) {
        this.nodeWidth = width;
        fireChangeEvent();
    }

    /**
     * Returns the list of colors that will be used to auto-populate the node
     * colors when they are first rendered.  If the list is empty, no color 
     * will be assigned to the node so, unless it is manually set, the default
     * color will apply.  This method returns a copy of the list, modifying
     * the returned list will not affect the plot.
     * 
     * @return The list of colors (possibly empty, but never {@code null}). 
     */
    public List<Color> getNodeColorSwatch() {
        return new ArrayList<>(this.nodeColorSwatch);
    }

    /**
     * Sets the color swatch for the plot.
     * 
     * @param colors  the list of colors ({@code null} not permitted). 
     */
    public void setNodeColorSwatch(List<Color> colors) {
        Args.nullNotPermitted(colors, "colors");
        this.nodeColorSwatch = colors;
        
    }
    
    /**
     * Returns the fill color for the specified node.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * 
     * @return The fill color (possibly {@code null}).
     */
    public Color getNodeFillColor(NodeKey nodeKey) {
        return this.nodeColorMap.get(nodeKey);
    }
    
    /**
     * Sets the fill color for the specified node and sends a change 
     * notification to all registered listeners.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * @param color  the fill color ({@code null} permitted).
     */
    public void setNodeFillColor(NodeKey nodeKey, Color color) {
        this.nodeColorMap.put(nodeKey, color);
        fireChangeEvent();
    }
    
    /**
     * Returns the default node color.  This is used when no specific node color
     * has been specified.  The initial (default) value is {@code Color.GRAY}.
     * 
     * @return The default node color (never {@code null}). 
     */
    public Color getDefaultNodeColor() {
        return this.defaultNodeColor;
    }
    
    /**
     * Sets the default node color and sends a change event to registered
     * listeners.
     * 
     * @param color  the color ({@code null} not permitted). 
     */
    public void setDefaultNodeColor(Color color) {
        Args.nullNotPermitted(color, "color");
        this.defaultNodeColor = color;
        fireChangeEvent();
    }

    /**
     * Returns the default font used to display labels for the source and
     * destination nodes.  The initial (default) value is 
     * {@code Font(Font.DIALOG, Font.BOLD, 12)}.
     * 
     * @return The default font (never {@code null}). 
     */
    public Font getDefaultNodeLabelFont() {
        return this.defaultNodeLabelFont;
    }

    /**
     * Sets the default font used to display labels for the source and
     * destination nodes and sends a change notification to all registered
     * listeners.
     * 
     * @param font  the font ({@code null} not permitted). 
     */
    public void setDefaultNodeLabelFont(Font font) {
        Args.nullNotPermitted(font, "font");
        this.defaultNodeLabelFont = font;
        fireChangeEvent();
    }

    /**
     * Returns the default paint used to display labels for the source and
     * destination nodes.  The initial (default) value is {@code Color.BLACK}.
     * 
     * @return The default paint (never {@code null}). 
     */
    public Paint getDefaultNodeLabelPaint() {
        return this.defaultNodeLabelPaint;
    }

    /**
     * Sets the default paint used to display labels for the source and
     * destination nodes and sends a change notification to all registered
     * listeners.
     * 
     * @param paint  the paint ({@code null} not permitted). 
     */
    public void setDefaultNodeLabelPaint(Paint paint) {
        Args.nullNotPermitted(paint, "paint");
        this.defaultNodeLabelPaint = paint;
        fireChangeEvent();
    }

    /**
     * Returns the vertical alignment of the node labels relative to the node.
     * The initial (default) value is {@link VerticalAlignment#CENTER}.
     * 
     * @return The alignment (never {@code null}). 
     */
    public VerticalAlignment getNodeLabelAlignment() {
        return this.nodeLabelAlignment;
    }
    
    /**
     * Sets the vertical alignment of the node labels and sends a change 
     * notification to all registered listeners.
     * 
     * @param alignment  the new alignment ({@code null} not permitted). 
     */
    public void setNodeLabelAlignment(VerticalAlignment alignment) {
        Args.nullNotPermitted(alignment, "alignment");
        this.nodeLabelAlignment = alignment;
        fireChangeEvent();
    }
    
    /**
     * Returns the x-offset for the node labels.
     * 
     * @return The x-offset for the node labels.
     */
    public double getNodeLabelOffsetX() {
        return this.nodeLabelOffsetX;
    }

    /**
     * Sets the x-offset for the node labels and sends a change notification
     * to all registered listeners.
     * 
     * @param offsetX  the node label x-offset in Java2D units.
     */
    public void setNodeLabelOffsetX(double offsetX) {
        this.nodeLabelOffsetX = offsetX;
        fireChangeEvent();
    }

    /**
     * Returns the y-offset for the node labels.
     * 
     * @return The y-offset for the node labels.
     */
    public double getNodeLabelOffsetY() {
        return nodeLabelOffsetY;
    }

    /**
     * Sets the y-offset for the node labels and sends a change notification
     * to all registered listeners.
     * 
     * @param offsetY  the node label y-offset in Java2D units.
     */
    public void setNodeLabelOffsetY(double offsetY) {
        this.nodeLabelOffsetY = offsetY;
        fireChangeEvent();
    }

    /**
     * Returns the tool tip generator that creates the strings that are 
     * displayed as tool tips for the flows displayed in the plot.
     * 
     * @return The tool tip generator (possibly {@code null}). 
     */
    public FlowLabelGenerator getToolTipGenerator() {
        return this.toolTipGenerator;
    }
    
    /**
     * Sets the tool tip generator and sends a change notification to all
     * registered listeners.  If the generator is set to {@code null}, no tool 
     * tips will be displayed for the flows.
     * 
     * @param generator  the new generator ({@code null} permitted). 
     */
    public void setToolTipGenerator(FlowLabelGenerator generator) {
        this.toolTipGenerator = generator;
        fireChangeEvent();
    }

    /**
     * Draws the flow plot within the specified area of the supplied graphics
     * target {@code g2}.
     * 
     * @param g2  the graphics target ({@code null} not permitted).
     * @param area  the plot area ({@code null} not permitted).
     * @param anchor  the anchor point (ignored).
     * @param parentState  the parent state (ignored).
     * @param info  the plot rendering info.
     */
    @Override
    public void draw(Graphics2D g2, Rectangle2D area, Point2D anchor, PlotState parentState, PlotRenderingInfo info) {
        Args.nullNotPermitted(g2, "g2");
        Args.nullNotPermitted(area, "area");
 
        EntityCollection entities = null;
        if (info != null) {
            info.setPlotArea(area);
            entities = info.getOwner().getEntityCollection();
        }
        RectangleInsets insets = getInsets();
        insets.trim(area);
        if (info != null) {
            info.setDataArea(area);
        }
        
        // use default JFreeChart background handling
        drawBackground(g2, area);

        // we need to ensure there is space to show all the inflows and all 
        // the outflows at each node group, so first we calculate the max
        // flow space required - for each node in the group, consider the 
        // maximum of the inflow and the outflow
        double flow2d = Double.POSITIVE_INFINITY;
        double nodeMargin2d = this.nodeMargin * area.getHeight();
        int stageCount = this.dataset.getStageCount();
        for (int stage = 0; stage < this.dataset.getStageCount(); stage++) {
            List<Comparable> sources = this.dataset.getSources(stage);
            int nodeCount = sources.size();
            double flowTotal = 0.0;
            for (Comparable source : sources) {
                double inflow = FlowDatasetUtils.calculateInflow(this.dataset, source, stage);
                double outflow = FlowDatasetUtils.calculateOutflow(this.dataset, source, stage);
                flowTotal = flowTotal + Math.max(inflow, outflow);
            }
            if (flowTotal > 0.0) {
                double availableH = area.getHeight() - (nodeCount - 1) * nodeMargin2d;
                flow2d = Math.min(availableH / flowTotal, flow2d);
            }
            
            if (stage == this.dataset.getStageCount() - 1) {
                // check inflows to the final destination nodes...
                List<Comparable> destinations = this.dataset.getDestinations(stage);
                int destinationCount = destinations.size();
                flowTotal = 0.0;
                for (Comparable destination : destinations) {
                    double inflow = FlowDatasetUtils.calculateInflow(this.dataset, destination, stage + 1);
                    flowTotal = flowTotal + inflow;
                }
                if (flowTotal > 0.0) {
                    double availableH = area.getHeight() - (destinationCount - 1) * nodeMargin2d;
                    flow2d = Math.min(availableH / flowTotal, flow2d);
                }
            }
        }

        double stageWidth = (area.getWidth() - ((stageCount + 1) * this.nodeWidth)) / stageCount;
        double flowOffset = area.getWidth() * this.flowMargin;
        
        Map<NodeKey, Rectangle2D> nodeRects = new HashMap<>();
        boolean hasNodeSelections = FlowDatasetUtils.hasNodeSelections(this.dataset);
        boolean hasFlowSelections = FlowDatasetUtils.hasFlowSelections(this.dataset);
        
        // iterate over all the stages, we can render the source node rects and
        // the flows ... we should add the destination node rects last, then
        // in a final pass add the labels
        for (int stage = 0; stage < this.dataset.getStageCount(); stage++) {
            
            double stageLeft = area.getX() + (stage + 1) * this.nodeWidth + (stage * stageWidth);
            double stageRight = stageLeft + stageWidth;
            
            // calculate the source node and flow rectangles
            Map<FlowKey, Rectangle2D> sourceFlowRects = new HashMap<>();
            double nodeY = area.getY();
            for (Object s : this.dataset.getSources(stage)) {
                Comparable source = (Comparable) s;
                double inflow = FlowDatasetUtils.calculateInflow(dataset, source, stage);
                double outflow = FlowDatasetUtils.calculateOutflow(dataset, source, stage);
                double nodeHeight = (Math.max(inflow, outflow) * flow2d);
                Rectangle2D nodeRect = new Rectangle2D.Double(stageLeft - nodeWidth, nodeY, nodeWidth, nodeHeight);
                if (entities != null) {
                    entities.add(new NodeEntity(new NodeKey<>(stage, source), nodeRect, source.toString()));                
                }
                nodeRects.put(new NodeKey<>(stage, source), nodeRect);
                double y = nodeY;
                for (Object d : this.dataset.getDestinations(stage)) {
                    Comparable destination = (Comparable) d;
                    Number flow = this.dataset.getFlow(stage, source, destination);
                    if (flow != null) {
                        double height = flow.doubleValue() * flow2d;
                        Rectangle2D rect = new Rectangle2D.Double(stageLeft - nodeWidth, y, nodeWidth, height);
                        sourceFlowRects.put(new FlowKey<>(stage, source, destination), rect);
                        y = y + height;
                    }
                }
                nodeY = nodeY + nodeHeight + nodeMargin2d;
            }
            
            // calculate the destination rectangles
            Map<FlowKey, Rectangle2D> destFlowRects = new HashMap<>();
            nodeY = area.getY();
            for (Object d : this.dataset.getDestinations(stage)) {
                Comparable destination = (Comparable) d;
                double inflow = FlowDatasetUtils.calculateInflow(dataset, destination, stage + 1);
                double outflow = FlowDatasetUtils.calculateOutflow(dataset, destination, stage + 1);
                double nodeHeight = Math.max(inflow, outflow) * flow2d;
                nodeRects.put(new NodeKey<>(stage + 1, destination), new Rectangle2D.Double(stageRight, nodeY, nodeWidth, nodeHeight));
                double y = nodeY;
                for (Object s : this.dataset.getSources(stage)) {
                    Comparable source = (Comparable) s;
                    Number flow = this.dataset.getFlow(stage, source, destination);
                    if (flow != null) {
                        double height = flow.doubleValue() * flow2d;
                        Rectangle2D rect = new Rectangle2D.Double(stageRight, y, nodeWidth, height);
                        y = y + height;
                        destFlowRects.put(new FlowKey<>(stage, source, destination), rect);
                    }
                }
                nodeY = nodeY + nodeHeight + nodeMargin2d;
            }
        
            for (Object s : this.dataset.getSources(stage)) {
                Comparable source = (Comparable) s;
                NodeKey nodeKey = new NodeKey<>(stage, source);
                Rectangle2D nodeRect = nodeRects.get(nodeKey);
                Color ncol = lookupNodeColor(nodeKey);
                if (hasNodeSelections) {
                    if (!Boolean.TRUE.equals(dataset.getNodeProperty(nodeKey, NodeKey.SELECTED_PROPERTY_KEY))) {
                        int g = (ncol.getRed() + ncol.getGreen() + ncol.getBlue()) / 3;
                        ncol = new Color(g, g, g, ncol.getAlpha());
                    }
                }
                g2.setPaint(ncol);
                g2.fill(nodeRect);
                                
                for (Object d : this.dataset.getDestinations(stage)) {
                    Comparable destination = (Comparable) d;
                    FlowKey flowKey = new FlowKey<>(stage, source, destination);
                    Rectangle2D sourceRect = sourceFlowRects.get(flowKey);
                    if (sourceRect == null) { 
                        continue; 
                    }
                    Rectangle2D destRect = destFlowRects.get(flowKey);
                
                    Path2D connect = new Path2D.Double();
                    connect.moveTo(sourceRect.getMaxX() + flowOffset, sourceRect.getMinY());
                    connect.curveTo(stageLeft + stageWidth / 2.0, sourceRect.getMinY(), stageLeft + stageWidth / 2.0, destRect.getMinY(), destRect.getX() - flowOffset, destRect.getMinY());
                    connect.lineTo(destRect.getX() - flowOffset, destRect.getMaxY());
                    connect.curveTo(stageLeft + stageWidth / 2.0, destRect.getMaxY(), stageLeft + stageWidth / 2.0, sourceRect.getMaxY(), sourceRect.getMaxX() + flowOffset, sourceRect.getMaxY());
                    connect.closePath();
                    Color nc = lookupNodeColor(nodeKey);
                    if (hasFlowSelections) {
                        if (!Boolean.TRUE.equals(dataset.getFlowProperty(flowKey, FlowKey.SELECTED_PROPERTY_KEY))) {
                            int g = (ncol.getRed() + ncol.getGreen() + ncol.getBlue()) / 3;
                            nc = new Color(g, g, g, ncol.getAlpha());
                        }
                    }
                    
                    GradientPaint gp = new GradientPaint((float) sourceRect.getMaxX(), 0, nc, (float) destRect.getMinX(), 0, new Color(nc.getRed(), nc.getGreen(), nc.getBlue(), 128));
                    Composite saved = g2.getComposite();
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
                    g2.setPaint(gp);
                    g2.fill(connect);
                    if (entities != null) {
                        String toolTip = null;
                        if (this.toolTipGenerator != null) {
                            toolTip = this.toolTipGenerator.generateLabel(this.dataset, flowKey);
                        }
                        entities.add(new FlowEntity(flowKey, connect, toolTip, ""));                
                    }
                    g2.setComposite(saved);
                }
                
            }
        }
        
        // now draw the destination nodes
        int lastStage = this.dataset.getStageCount() - 1;
        for (Object d : this.dataset.getDestinations(lastStage)) {
            Comparable destination = (Comparable) d;
            NodeKey nodeKey = new NodeKey<>(lastStage + 1, destination);
            Rectangle2D nodeRect = nodeRects.get(nodeKey);
            if (nodeRect != null) {
                Color ncol = lookupNodeColor(nodeKey);
                if (hasNodeSelections) {
                    if (!Boolean.TRUE.equals(dataset.getNodeProperty(nodeKey, NodeKey.SELECTED_PROPERTY_KEY))) {
                        int g = (ncol.getRed() + ncol.getGreen() + ncol.getBlue()) / 3;
                        ncol = new Color(g, g, g, ncol.getAlpha());
                    }
                }
                g2.setPaint(ncol);
                g2.fill(nodeRect);
                if (entities != null) {
                    entities.add(new NodeEntity(new NodeKey<>(lastStage + 1, destination), nodeRect, destination.toString()));                
                }
            }
        }
        
        // now draw all the labels over top of everything else
        g2.setFont(this.defaultNodeLabelFont);
        g2.setPaint(this.defaultNodeLabelPaint);
        for (NodeKey key : nodeRects.keySet()) {
            Rectangle2D r = nodeRects.get(key);
            if (key.getStage() < this.dataset.getStageCount()) {
                TextUtils.drawAlignedString(key.getNode().toString(), g2, 
                        (float) (r.getMaxX() + flowOffset + this.nodeLabelOffsetX), 
                        (float) labelY(r), TextAnchor.CENTER_LEFT);                
            } else {
                TextUtils.drawAlignedString(key.getNode().toString(), g2, 
                        (float) (r.getX() - flowOffset - this.nodeLabelOffsetX), 
                        (float) labelY(r), TextAnchor.CENTER_RIGHT);                
            }
        }
    }
    
    /**
     * Performs a lookup on the color for the specified node.
     * 
     * @param nodeKey  the node key ({@code null} not permitted).
     * 
     * @return The node color. 
     */
    protected Color lookupNodeColor(NodeKey nodeKey) {
        Color result = this.nodeColorMap.get(nodeKey);
        if (result == null) {
            // if the color swatch is non-empty, we use it to autopopulate 
            // the node colors...
            if (!this.nodeColorSwatch.isEmpty()) {
                // look through previous stages to see if this source key is already seen
                for (int s = 0; s < nodeKey.getStage(); s++) {
                    for (Object key : dataset.getSources(s)) {
                        if (nodeKey.getNode().equals(key)) {
                            Color color = this.nodeColorMap.get(new NodeKey<>(s, (Comparable) key));
                            setNodeFillColor(nodeKey, color);
                            return color;
                        }
                    }
                }

                result = this.nodeColorSwatch.get(Math.min(this.nodeColorSwatchPointer, this.nodeColorSwatch.size() - 1));
                this.nodeColorSwatchPointer++;
                if (this.nodeColorSwatchPointer > this.nodeColorSwatch.size() - 1) { 
                    this.nodeColorSwatchPointer = 0;
                }
                setNodeFillColor(nodeKey, result);
                return result;
            } else {
                result = this.defaultNodeColor;
            }
        }
        return result;
    }

    /**
     * Computes the y-coordinate for a node label taking into account the 
     * current alignment settings.
     * 
     * @param r  the node rectangle.
     * 
     * @return The y-coordinate for the label. 
     */
    private double labelY(Rectangle2D r) {
        if (this.nodeLabelAlignment == VerticalAlignment.TOP) {
            return r.getY() + this.nodeLabelOffsetY;
        } else if (this.nodeLabelAlignment == VerticalAlignment.BOTTOM) {
            return r.getMaxY() - this.nodeLabelOffsetY;
        } else {
            return r.getCenterY();
        }
    }
    
    /**
     * Tests this plot for equality with an arbitrary object.  Note that, for 
     * the purposes of this equality test, the dataset is ignored.
     * 
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FlowPlot)) {
            return false;
        }
        FlowPlot that = (FlowPlot) obj;
        if (!this.defaultNodeColor.equals(that.defaultNodeColor)) {
            return false;
        }
        if (!this.nodeColorMap.equals(that.nodeColorMap)) {
            return false;
        }
        if (!this.nodeColorSwatch.equals(that.nodeColorSwatch)) {
            return false;
        }
        if (!this.defaultNodeLabelFont.equals(that.defaultNodeLabelFont)) {
            return false;
        }
        if (!PaintUtils.equal(this.defaultNodeLabelPaint, that.defaultNodeLabelPaint)) {
            return false;
        }
        if (this.flowMargin != that.flowMargin) {
            return false;
        }
        if (this.nodeMargin != that.nodeMargin) {
            return false;
        }
        if (this.nodeWidth != that.nodeWidth) {
            return false;
        }
        if (this.nodeLabelOffsetX != that.nodeLabelOffsetX) {
            return false;
        }
        if (this.nodeLabelOffsetY != that.nodeLabelOffsetY) {
            return false;
        }
        if (this.nodeLabelAlignment != that.nodeLabelAlignment) {
            return false;
        }
        if (!Objects.equals(this.toolTipGenerator, that.toolTipGenerator)) {
            return false;
        }
        return super.equals(obj);
    }

    /**
     * Returns a hashcode for this instance.
     * 
     * @return A hashcode. 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.nodeWidth) ^ (Double.doubleToLongBits(this.nodeWidth) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.nodeMargin) ^ (Double.doubleToLongBits(this.nodeMargin) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.flowMargin) ^ (Double.doubleToLongBits(this.flowMargin) >>> 32));
        hash = 83 * hash + Objects.hashCode(this.nodeColorMap);
        hash = 83 * hash + Objects.hashCode(this.nodeColorSwatch);
        hash = 83 * hash + Objects.hashCode(this.defaultNodeColor);
        hash = 83 * hash + Objects.hashCode(this.defaultNodeLabelFont);
        hash = 83 * hash + Objects.hashCode(this.defaultNodeLabelPaint);
        hash = 83 * hash + Objects.hashCode(this.nodeLabelAlignment);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.nodeLabelOffsetX) ^ (Double.doubleToLongBits(this.nodeLabelOffsetX) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.nodeLabelOffsetY) ^ (Double.doubleToLongBits(this.nodeLabelOffsetY) >>> 32));
        hash = 83 * hash + Objects.hashCode(this.toolTipGenerator);
        return hash;
    }

    /**
     * Returns an independent copy of this {@code FlowPlot} instance (note, 
     * however, that the dataset is NOT cloned).
     * 
     * @return A close of this instance.
     * 
     * @throws CloneNotSupportedException 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        FlowPlot clone = (FlowPlot) super.clone();
        clone.nodeColorMap = new HashMap<>(this.nodeColorMap);
        return clone;
    }

}
