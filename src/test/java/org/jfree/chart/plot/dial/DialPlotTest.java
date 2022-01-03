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
 * DialPlotTest.java
 * -----------------
 * (C) Copyright 2006-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.dial;

import java.awt.Color;
import java.awt.GradientPaint;

import org.jfree.chart.TestUtils;

import org.jfree.chart.event.PlotChangeEvent;
import org.jfree.chart.event.PlotChangeListener;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link DialPlot} class.
 */
public class DialPlotTest implements PlotChangeListener {

    /** The last plot change event received. */
    private PlotChangeEvent lastEvent;

    /**
     * Records the last plot change event received.
     *
     * @param event  the event.
     */
    @Override
    public void plotChanged(PlotChangeEvent event) {
        this.lastEvent = event;
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        DialPlot p1 = new DialPlot();
        DialPlot p2 = new DialPlot();
        assertEquals(p1, p2);

        // background
        p1.setBackground(new DialBackground(Color.GREEN));
        assertNotEquals(p1, p2);
        p2.setBackground(new DialBackground(Color.GREEN));
        assertEquals(p1, p2);

        p1.setBackground(null);
        assertNotEquals(p1, p2);
        p2.setBackground(null);
        assertEquals(p1, p2);

        // dial cap
        DialCap cap1 = new DialCap();
        cap1.setFillPaint(Color.RED);
        p1.setCap(cap1);
        assertNotEquals(p1, p2);
        DialCap cap2 = new DialCap();
        cap2.setFillPaint(Color.RED);
        p2.setCap(cap2);
        assertEquals(p1, p2);

        p1.setCap(null);
        assertNotEquals(p1, p2);
        p2.setCap(null);
        assertEquals(p1, p2);

        // frame
        StandardDialFrame f1 = new StandardDialFrame();
        f1.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.WHITE));
        p1.setDialFrame(f1);
        assertNotEquals(p1, p2);
        StandardDialFrame f2 = new StandardDialFrame();
        f2.setBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f,
                4.0f, Color.WHITE));
        p2.setDialFrame(f2);
        assertEquals(p1, p2);

        // view
        p1.setView(0.2, 0.0, 0.8, 1.0);
        assertNotEquals(p1, p2);
        p2.setView(0.2, 0.0, 0.8, 1.0);
        assertEquals(p1, p2);

        // layer
        p1.addLayer(new StandardDialScale());
        assertNotEquals(p1, p2);
        p2.addLayer(new StandardDialScale());
        assertEquals(p1, p2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashCode() {
        DialPlot p1 = new DialPlot();
        DialPlot p2 = new DialPlot();
        assertEquals(p1, p2);
        int h1 = p1.hashCode();
        int h2 = p2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        DialPlot p1 = new DialPlot();
        DialPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }


    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DialPlot p1 = new DialPlot();
        DialPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Check the notification event mechanism for the dial background.
     */
    @Test
    public void testBackgroundListener() {
        DialPlot p = new DialPlot();
        DialBackground b1 = new DialBackground(Color.RED);
        p.setBackground(b1);
        p.addChangeListener(this);
        this.lastEvent = null;
        b1.setPaint(Color.BLUE);
        assertNotNull(this.lastEvent);

        DialBackground b2 = new DialBackground(Color.GREEN);
        p.setBackground(b2);
        this.lastEvent = null;
        b1.setPaint(Color.RED);
        assertNull(this.lastEvent);
        b2.setPaint(Color.RED);
        assertNotNull(this.lastEvent);
    }

    /**
     * Check the notification event mechanism for the dial cap.
     */
    @Test
    public void testCapListener() {
        DialPlot p = new DialPlot();
        DialCap c1 = new DialCap();
        p.setCap(c1);
        p.addChangeListener(this);
        this.lastEvent = null;
        c1.setFillPaint(Color.RED);
        assertNotNull(this.lastEvent);

        DialCap c2 = new DialCap();
        p.setCap(c2);
        this.lastEvent = null;
        c1.setFillPaint(Color.BLUE);
        assertNull(this.lastEvent);
        c2.setFillPaint(Color.GREEN);
        assertNotNull(this.lastEvent);
    }

    /**
     * Check the notification event mechanism for the dial frame.
     */
    @Test
    public void testFrameListener() {
        DialPlot p = new DialPlot();
        ArcDialFrame f1 = new ArcDialFrame();
        p.setDialFrame(f1);
        p.addChangeListener(this);
        this.lastEvent = null;
        f1.setBackgroundPaint(Color.GRAY);
        assertNotNull(this.lastEvent);

        ArcDialFrame f2 = new ArcDialFrame();
        p.setDialFrame(f2);
        this.lastEvent = null;
        f1.setBackgroundPaint(Color.BLUE);
        assertNull(this.lastEvent);
        f2.setBackgroundPaint(Color.GREEN);
        assertNotNull(this.lastEvent);
    }

    /**
     * Check the notification event mechanism for the dial scales.
     */
    @Test
    public void testScaleListener() {
        DialPlot p = new DialPlot();
        StandardDialScale s1 = new StandardDialScale();
        p.addScale(0, s1);
        p.addChangeListener(this);
        this.lastEvent = null;
        s1.setStartAngle(22.0);
        assertNotNull(this.lastEvent);

        StandardDialScale s2 = new StandardDialScale();
        p.addScale(0, s2);
        this.lastEvent = null;
        s1.setStartAngle(33.0);
        assertNull(this.lastEvent);
        s2.setStartAngle(33.0);
        assertNotNull(this.lastEvent);
    }

    /**
     * Check the notification event mechanism for a layer.
     */
    @Test
    public void testLayerListener() {
        DialPlot p = new DialPlot();
        DialBackground b1 = new DialBackground(Color.RED);
        p.addLayer(b1);
        p.addChangeListener(this);
        this.lastEvent = null;
        b1.setPaint(Color.BLUE);
        assertNotNull(this.lastEvent);

        DialBackground b2 = new DialBackground(Color.GREEN);
        p.addLayer(b2);
        this.lastEvent = null;
        b1.setPaint(Color.RED);
        assertNotNull(this.lastEvent);
        b2.setPaint(Color.GREEN);
        assertNotNull(this.lastEvent);

        p.removeLayer(b2);
        this.lastEvent = null;
        b2.setPaint(Color.RED);
        assertNull(this.lastEvent);
    }

}
