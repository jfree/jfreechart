/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * MarkerTests.java
 * ----------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 05-Sep-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.EventListener;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.event.MarkerChangeEvent;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.LengthAdjustmentType;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

/**
 * Tests for the {@link Marker} class.
 */
public class MarkerTests extends TestCase implements MarkerChangeListener {

    MarkerChangeEvent lastEvent;

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MarkerTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MarkerTests(String name) {
        super(name);
    }

    /**
     * Some checks for the getPaint() and setPaint() methods.
     */
    public void testGetSetPaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.gray, m.getPaint());
        m.setPaint(Color.blue);
        assertEquals(Color.blue, m.getPaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setPaint(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getStroke() and setStroke() methods.
     */
    public void testGetSetStroke() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new BasicStroke(0.5f), m.getStroke());
        m.setStroke(new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m.getStroke());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setStroke(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getOutlinePaint() and setOutlinePaint() methods.
     */
    public void testGetSetOutlinePaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.gray, m.getOutlinePaint());
        m.setOutlinePaint(Color.yellow);
        assertEquals(Color.yellow, m.getOutlinePaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setOutlinePaint(null);
        assertEquals(null, m.getOutlinePaint());
    }

    /**
     * Some checks for the getOutlineStroke() and setOutlineStroke() methods.
     */
    public void testGetSetOutlineStroke() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new BasicStroke(0.5f), m.getOutlineStroke());
        m.setOutlineStroke(new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m.getOutlineStroke());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setOutlineStroke(null);
        assertEquals(null, m.getOutlineStroke());
    }

    private static final float EPSILON = 0.000000001f;

    /**
     * Some checks for the getAlpha() and setAlpha() methods.
     */
    public void testGetSetAlpha() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(0.8f, m.getAlpha(), EPSILON);
        m.setAlpha(0.5f);
        assertEquals(0.5f, m.getAlpha(), EPSILON);
        assertEquals(m, this.lastEvent.getMarker());
    }

    /**
     * Some checks for the getLabel() and setLabel() methods.
     */
    public void testGetSetLabel() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(null, m.getLabel());
        m.setLabel("XYZ");
        assertEquals("XYZ", m.getLabel());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        m.setLabel(null);
        assertEquals(null, m.getLabel());
    }

    /**
     * Some checks for the getLabelFont() and setLabelFont() methods.
     */
    public void testGetSetLabelFont() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new Font("SansSerif", Font.PLAIN, 9), m.getLabelFont());
        m.setLabelFont(new Font("SansSerif", Font.BOLD, 10));
        assertEquals(new Font("SansSerif", Font.BOLD, 10), m.getLabelFont());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelFont(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getLabelPaint() and setLabelPaint() methods.
     */
    public void testGetSetLabelPaint() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(Color.black, m.getLabelPaint());
        m.setLabelPaint(Color.red);
        assertEquals(Color.red, m.getLabelPaint());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelPaint(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getLabelAnchor() and setLabelAnchor() methods.
     */
    public void testGetSetLabelAnchor() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(RectangleAnchor.TOP_LEFT, m.getLabelAnchor());
        m.setLabelAnchor(RectangleAnchor.TOP);
        assertEquals(RectangleAnchor.TOP, m.getLabelAnchor());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelAnchor(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getLabelOffset() and setLabelOffset() methods.
     */
    public void testGetSetLabelOffset() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(new RectangleInsets(3, 3, 3, 3), m.getLabelOffset());
        m.setLabelOffset(new RectangleInsets(1, 2, 3, 4));
        assertEquals(new RectangleInsets(1, 2, 3, 4), m.getLabelOffset());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelOffset(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getLabelOffsetType() and setLabelOffsetType()
     * methods.
     */
    public void testGetSetLabelOffsetType() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(LengthAdjustmentType.CONTRACT, m.getLabelOffsetType());
        m.setLabelOffsetType(LengthAdjustmentType.EXPAND);
        assertEquals(LengthAdjustmentType.EXPAND, m.getLabelOffsetType());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelOffsetType(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Some checks for the getLabelTextAnchor() and setLabelTextAnchor()
     * methods.
     */
    public void testGetSetLabelTextAnchor() {
        // we use ValueMarker for the tests, because we need a concrete
        // subclass...
        ValueMarker m = new ValueMarker(1.1);
        m.addChangeListener(this);
        this.lastEvent = null;
        assertEquals(TextAnchor.CENTER, m.getLabelTextAnchor());
        m.setLabelTextAnchor(TextAnchor.BASELINE_LEFT);
        assertEquals(TextAnchor.BASELINE_LEFT, m.getLabelTextAnchor());
        assertEquals(m, this.lastEvent.getMarker());

        // check null argument...
        try {
            m.setLabelTextAnchor(null);
            fail("Expected an IllegalArgumentException for null.");
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    /**
     * Checks that a CategoryPlot deregisters listeners when clearing markers.
     */
    public void testListenersWithCategoryPlot() {
        CategoryPlot plot = new CategoryPlot();
        CategoryMarker marker1 = new CategoryMarker("X");
        ValueMarker marker2 = new ValueMarker(1.0);
        plot.addDomainMarker(marker1);
        plot.addRangeMarker(marker2);
        EventListener[] listeners1 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners1).contains(plot));
        EventListener[] listeners2 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners2).contains(plot));
        plot.clearDomainMarkers();
        plot.clearRangeMarkers();
        listeners1 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners1).contains(plot));
        listeners2 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners2).contains(plot));
    }

    /**
     * Checks that an XYPlot deregisters listeners when clearing markers.
     */
    public void testListenersWithXYPlot() {
        XYPlot plot = new XYPlot();
        ValueMarker marker1 = new ValueMarker(1.0);
        ValueMarker marker2 = new ValueMarker(2.0);
        plot.addDomainMarker(marker1);
        plot.addRangeMarker(marker2);
        EventListener[] listeners1 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners1).contains(plot));
        EventListener[] listeners2 = marker1.getListeners(
                MarkerChangeListener.class);
        assertTrue(Arrays.asList(listeners2).contains(plot));
        plot.clearDomainMarkers();
        plot.clearRangeMarkers();
        listeners1 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners1).contains(plot));
        listeners2 = marker1.getListeners(MarkerChangeListener.class);
        assertFalse(Arrays.asList(listeners2).contains(plot));
    }

    /**
     * Records the last event.
     *
     * @param event  the event.
     */
    public void markerChanged(MarkerChangeEvent event) {
        this.lastEvent = event;
    }

}
