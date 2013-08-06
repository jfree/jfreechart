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
 * ----------------------------
 * CandlestickRendererTest.java
 * ----------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 * 22-Oct-2003 : Added hashCode test (DG);
 * 17-Aug-2006 : Strengthened testEquals() and added testFindRangeBounds()
 *               method (DG);
 * 05-Mar-2007 : Added new field to testEquals() (DG);
 * 08-Oct-2007 : Added tests for new volumePaint field (DG);
 * 22-Apr-2008 : Added testPublicCloneable() (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.awt.Color;
import java.awt.GradientPaint;
import java.util.Date;

import org.jfree.chart.TestUtilities;

import org.jfree.data.Range;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.OHLCDataset;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link CandlestickRenderer} class.
 */
public class CandlestickRendererTest {

    private static final double EPSILON = 0.0000000001;

    /**
     * Some checks for the constructor.
     */
    @Test
    public void testConstructor() {
        CandlestickRenderer r1 = new CandlestickRenderer();

        // check defaults
        assertEquals(Color.green, r1.getUpPaint());
        assertEquals(Color.red, r1.getDownPaint());
        assertFalse(r1.getUseOutlinePaint());
        assertTrue(r1.getDrawVolume());
        assertEquals(Color.gray, r1.getVolumePaint());
        assertEquals(-1.0, r1.getCandleWidth(), EPSILON);
    }

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        CandlestickRenderer r1 = new CandlestickRenderer();
        CandlestickRenderer r2 = new CandlestickRenderer();
        assertEquals(r1, r2);

        // upPaint
        r1.setUpPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        assertFalse(r1.equals(r2));
        r2.setUpPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f, 4.0f,
                Color.white));
        assertTrue(r1.equals(r2));

        // downPaint
        r1.setDownPaint(new GradientPaint(5.0f, 6.0f, Color.green, 7.0f, 8.0f,
                Color.yellow));
        assertFalse(r1.equals(r2));
        r2.setDownPaint(new GradientPaint(5.0f, 6.0f, Color.green, 7.0f, 8.0f,
                Color.yellow));
        assertTrue(r1.equals(r2));

        // drawVolume
        r1.setDrawVolume(false);
        assertFalse(r1.equals(r2));
        r2.setDrawVolume(false);
        assertTrue(r1.equals(r2));

        // candleWidth
        r1.setCandleWidth(3.3);
        assertFalse(r1.equals(r2));
        r2.setCandleWidth(3.3);
        assertTrue(r1.equals(r2));

        // maxCandleWidthInMilliseconds
        r1.setMaxCandleWidthInMilliseconds(123);
        assertFalse(r1.equals(r2));
        r2.setMaxCandleWidthInMilliseconds(123);
        assertTrue(r1.equals(r2));

        // autoWidthMethod
        r1.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        assertFalse(r1.equals(r2));
        r2.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        assertTrue(r1.equals(r2));

        // autoWidthFactor
        r1.setAutoWidthFactor(0.22);
        assertFalse(r1.equals(r2));
        r2.setAutoWidthFactor(0.22);
        assertTrue(r1.equals(r2));

        // autoWidthGap
        r1.setAutoWidthGap(1.1);
        assertFalse(r1.equals(r2));
        r2.setAutoWidthGap(1.1);
        assertTrue(r1.equals(r2));

        r1.setUseOutlinePaint(true);
        assertFalse(r1.equals(r2));
        r2.setUseOutlinePaint(true);
        assertTrue(r1.equals(r2));

        r1.setVolumePaint(Color.blue);
        assertFalse(r1.equals(r2));
        r2.setVolumePaint(Color.blue);
        assertTrue(r1.equals(r2));
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        CandlestickRenderer r1 = new CandlestickRenderer();
        CandlestickRenderer r2 = new CandlestickRenderer();
        assertTrue(r1.equals(r2));
        int h1 = r1.hashCode();
        int h2 = r2.hashCode();
        assertEquals(h1, h2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        CandlestickRenderer r1 = new CandlestickRenderer();
        CandlestickRenderer r2 = (CandlestickRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        CandlestickRenderer r1 = new CandlestickRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        CandlestickRenderer r1 = new CandlestickRenderer();
        CandlestickRenderer r2 = (CandlestickRenderer) 
                TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * Some checks for the findRangeBounds() method.
     */
    @Test
    public void testFindRangeBounds() {
        CandlestickRenderer renderer = new CandlestickRenderer();

        OHLCDataItem item1 = new OHLCDataItem(new Date(1L), 2.0, 4.0, 1.0, 3.0,
                100);
        OHLCDataset dataset = new DefaultOHLCDataset("S1",
                new OHLCDataItem[] {item1});
        Range range = renderer.findRangeBounds(dataset);
        assertEquals(new Range(1.0, 4.0), range);

        OHLCDataItem item2 = new OHLCDataItem(new Date(1L), -1.0, 3.0, -1.0,
                3.0, 100);
        dataset = new DefaultOHLCDataset("S1", new OHLCDataItem[] {item1,
                item2});
        range = renderer.findRangeBounds(dataset);
        assertEquals(new Range(-1.0, 4.0), range);

        // try an empty dataset - should return a null range
        dataset = new DefaultOHLCDataset("S1", new OHLCDataItem[] {});
        range = renderer.findRangeBounds(dataset);
        assertNull(range);

        // try a null dataset - should return a null range
        range = renderer.findRangeBounds(null);
        assertNull(range);
    }

}
