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
 * -----------------
 * FlowPlotTest.java
 * -----------------
 * (C) Copyright 2021-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.flow;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import org.jfree.chart.TestUtils;
import org.jfree.chart.api.VerticalAlignment;
import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.labels.StandardFlowLabelGenerator;
import org.jfree.data.flow.NodeKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link FlowPlot} class.
 */
public class FlowPlotTest implements PlotChangeListener {

    private PlotChangeEvent lastEvent = null;

    /**
     * Receives notification of a plot change event.
     *
     * @param event  the event.
     */
    @Override
    public void plotChanged(PlotChangeEvent event) {
        this.lastEvent = event;        
    }
    
    @Test
    public void setDefaultNodeColorTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setDefaultNodeColor(Color.GREEN);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setDefaultNodeLabelFontTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setDefaultNodeLabelFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setDefaultNodeLabelPaintTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setDefaultNodeLabelPaint(Color.RED);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setDefaultNodeLabelOffsetXTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setNodeLabelOffsetX(12.3);
        assertNotNull(this.lastEvent);
    }
    
    @Test
    public void setDefaultNodeLabelOffsetYTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setNodeLabelOffsetY(12.4);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setNodeMarginTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setNodeMargin(0.02);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setNodeLabelAlignmentTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setNodeLabelAlignment(VerticalAlignment.CENTER);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setNodeFillColorTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setNodeFillColor(new NodeKey<>(0, "A"), Color.RED);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setFlowMarginTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setFlowMargin(0.1);
        assertNotNull(this.lastEvent);
    }

    @Test
    public void setToolTipGeneratorTriggersChangeEvent() {
        this.lastEvent = null;
        FlowPlot p1 = new FlowPlot(null);
        p1.addChangeListener(this);
        p1.setToolTipGenerator(null);
        assertNotNull(this.lastEvent);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        FlowPlot p1 = new FlowPlot(null);
        FlowPlot p2 = new FlowPlot(null);
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        // test fields one by one - the independence checker does this
        testIndependence(p1, p2);
    }

    /**
     * Confirm that cloning works.
     * 
     * @throws CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        FlowPlot p1 = new FlowPlot(null);
        p1.setNodeFillColor(new NodeKey<>(0, "A"), Color.BLUE);
        FlowPlot p2 = (FlowPlot) p1.clone();
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
        
        testIndependence(p1, p2);
    }
    
    /**
     * Tests that two plot instances do not share any state.
     * 
     * @param p1  plot 1.
     * @param p2  plot 2.
     */
    private void testIndependence(FlowPlot p1, FlowPlot p2) {
        // test fields one by one 
        p1.setFlowMargin(0.01);
        assertNotEquals(p1, p2);
        p2.setFlowMargin(0.01);
        assertEquals(p1, p1);
        
        p1.setDefaultNodeColor(Color.GREEN);
        assertNotEquals(p1, p2);
        p2.setDefaultNodeColor(Color.GREEN);
        assertEquals(p1, p1);
        
        p1.setDefaultNodeLabelFont(new Font(Font.DIALOG, Font.PLAIN, 22));
        assertNotEquals(p1, p2);
        p2.setDefaultNodeLabelFont(new Font(Font.DIALOG, Font.PLAIN, 22));
        assertEquals(p1, p1);
        
        p1.setDefaultNodeLabelPaint(Color.WHITE);
        assertNotEquals(p1, p2);
        p2.setDefaultNodeLabelPaint(Color.WHITE);
        assertEquals(p1, p1);
        
        p1.setNodeMargin(0.05);
        assertNotEquals(p1, p2);
        p2.setNodeMargin(0.05);
        assertEquals(p1, p1);
        
        p1.setNodeLabelOffsetX(99.0);
        assertNotEquals(p1, p2);
        p2.setNodeLabelOffsetX(99.0);
        assertEquals(p1, p1);
        
        p1.setNodeLabelOffsetY(88.0);
        assertNotEquals(p1, p2);
        p2.setNodeLabelOffsetY(88.0);
        assertEquals(p1, p1);

        p1.setNodeWidth(9.0);
        assertNotEquals(p1, p2);
        p2.setNodeWidth(9.0);
        assertEquals(p1, p1);
        
        p1.setNodeLabelAlignment(VerticalAlignment.BOTTOM);
        assertNotEquals(p1, p2);
        p2.setNodeLabelAlignment(VerticalAlignment.BOTTOM);
        assertEquals(p1, p1);
        
        p1.setToolTipGenerator(null);
        assertNotEquals(p1, p2);
        p2.setToolTipGenerator(null);
        assertEquals(p1, p2);
        
        p1.setToolTipGenerator(new StandardFlowLabelGenerator("%4$,.0f"));
        assertNotEquals(p1, p2);
        p2.setToolTipGenerator(new StandardFlowLabelGenerator("%4$,.0f"));
        assertEquals(p1, p2);
        
        p1.setNodeFillColor(new NodeKey<>(0, "A"), Color.RED);
        assertNotEquals(p1, p2);
        p2.setNodeFillColor(new NodeKey<>(0, "A"), Color.RED);
        assertEquals(p1, p2);
        
        List<Color> colors1 = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE);
        p1.setNodeColorSwatch(colors1);
        assertNotEquals(p1, p2);
        List<Color> colors2 = Arrays.asList(Color.RED, Color.GREEN, Color.BLUE);
        p2.setNodeColorSwatch(colors2);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        FlowPlot p1 = new FlowPlot(null);
        FlowPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }
 
}
