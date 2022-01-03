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
 * ------------------
 * MeterPlotTest.java
 * ------------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.Range;
import org.jfree.data.general.DefaultValueDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link MeterPlot} class.
 */
public class MeterPlotTest {

    /**
     * Test the equals method to ensure that it can distinguish the required
     * fields.  Note that the dataset is NOT considered in the equals test.
     */
    @Test
    public void testEquals() {
        MeterPlot plot1 = new MeterPlot();
        MeterPlot plot2 = new MeterPlot();
        assertEquals(plot1, plot2);

        // units
        plot1.setUnits("mph");
        assertNotEquals(plot1, plot2);
        plot2.setUnits("mph");
        assertEquals(plot1, plot2);

        // range
        plot1.setRange(new Range(50.0, 70.0));
        assertNotEquals(plot1, plot2);
        plot2.setRange(new Range(50.0, 70.0));
        assertEquals(plot1, plot2);

        // interval
        plot1.addInterval(new MeterInterval("Normal", new Range(55.0, 60.0)));
        assertNotEquals(plot1, plot2);
        plot2.addInterval(new MeterInterval("Normal", new Range(55.0, 60.0)));
        assertEquals(plot1, plot2);

        // dial outline paint
        plot1.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        // dial shape
        plot1.setDialShape(DialShape.CHORD);
        assertNotEquals(plot1, plot2);
        plot2.setDialShape(DialShape.CHORD);
        assertEquals(plot1, plot2);

        // dial background paint
        plot1.setDialBackgroundPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setDialBackgroundPaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        // dial outline paint
        plot1.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.RED));
        assertNotEquals(plot1, plot2);
        plot2.setDialOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.GREEN,
                3.0f, 4.0f, Color.RED));
        assertEquals(plot1, plot2);

        // needle paint
        plot1.setNeedlePaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertNotEquals(plot1, plot2);
        plot2.setNeedlePaint(new GradientPaint(9.0f, 8.0f, Color.RED,
                7.0f, 6.0f, Color.BLUE));
        assertEquals(plot1, plot2);

        // value visible
        plot1.setValueVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setValueVisible(false);
        assertEquals(plot1, plot2);

        // value font
        plot1.setValueFont(new Font("Serif", Font.PLAIN, 6));
        assertNotEquals(plot1, plot2);
        plot2.setValueFont(new Font("Serif", Font.PLAIN, 6));
        assertEquals(plot1, plot2);

        // value paint
        plot1.setValuePaint(new GradientPaint(1.0f, 2.0f, Color.BLACK,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setValuePaint(new GradientPaint(1.0f, 2.0f, Color.BLACK,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // tick labels visible
        plot1.setTickLabelsVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setTickLabelsVisible(false);
        assertEquals(plot1, plot2);

        // tick label font
        plot1.setTickLabelFont(new Font("Serif", Font.PLAIN, 6));
        assertNotEquals(plot1, plot2);
        plot2.setTickLabelFont(new Font("Serif", Font.PLAIN, 6));
        assertEquals(plot1, plot2);

        // tick label paint
        plot1.setTickLabelPaint(Color.RED);
        assertNotEquals(plot1, plot2);
        plot2.setTickLabelPaint(Color.RED);
        assertEquals(plot1, plot2);

        // tick label format
        plot1.setTickLabelFormat(new DecimalFormat("0"));
        assertNotEquals(plot1, plot2);
        plot2.setTickLabelFormat(new DecimalFormat("0"));
        assertEquals(plot1, plot2);

        // tick paint
        plot1.setTickPaint(Color.GREEN);
        assertNotEquals(plot1, plot2);
        plot2.setTickPaint(Color.GREEN);
        assertEquals(plot1, plot2);

        // tick size
        plot1.setTickSize(1.23);
        assertNotEquals(plot1, plot2);
        plot2.setTickSize(1.23);
        assertEquals(plot1, plot2);

        // draw border
        plot1.setDrawBorder(!plot1.getDrawBorder());
        assertNotEquals(plot1, plot2);
        plot2.setDrawBorder(plot1.getDrawBorder());
        assertEquals(plot1, plot2);

        // meter angle
        plot1.setMeterAngle(22);
        assertNotEquals(plot1, plot2);
        plot2.setMeterAngle(22);
        assertEquals(plot1, plot2);

    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        MeterPlot p1 = new MeterPlot();
        MeterPlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // the clone and the original share a reference to the SAME dataset
        assertSame(p1.getDataset(), p2.getDataset());

        // try a few checks to ensure that the clone is independent of the
        // original
        p1.getTickLabelFormat().setMinimumIntegerDigits(99);
        assertNotEquals(p1, p2);
        p2.getTickLabelFormat().setMinimumIntegerDigits(99);
        assertEquals(p1, p2);

        p1.addInterval(new MeterInterval("Test", new Range(1.234, 5.678)));
        assertNotEquals(p1, p2);
        p2.addInterval(new MeterInterval("Test", new Range(1.234, 5.678)));
        assertEquals(p1, p2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization1() {
        MeterPlot p1 = new MeterPlot(null);
        p1.setDialBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        p1.setDialOutlinePaint(new GradientPaint(4.0f, 3.0f, Color.RED,
                2.0f, 1.0f, Color.BLUE));
        p1.setNeedlePaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        p1.setTickLabelPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        p1.setTickPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE));
        MeterPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        MeterPlot p1 = new MeterPlot(new DefaultValueDataset(1.23));
        MeterPlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);

    }

}
