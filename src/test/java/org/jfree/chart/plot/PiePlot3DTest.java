/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * Pie3DPlotTest.java
 * ------------------
 * (C) Copyright 2003-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link PiePlot3D} class.
 */
public class PiePlot3DTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        PiePlot3D p1 = new PiePlot3D();
        PiePlot3D p2 = new PiePlot3D();
        assertEquals(p1, p2);
        assertEquals(p2, p1);

        p1.setDepthFactor(1.23);
        assertNotEquals(p1, p2);
        p2.setDepthFactor(1.23);
        assertEquals(p1, p2);

        p1.setDarkerSides(true);
        assertNotEquals(p1, p2);
        p2.setDarkerSides(true);
        assertEquals(p1, p2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PiePlot3D p1 = new PiePlot3D(null);
        PiePlot3D p2 = (PiePlot3D) TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Draws a pie chart where the label generator returns null.
     */
    @Test
    public void testDrawWithNullDataset() {
        JFreeChart chart = ChartFactory.createPieChart3D("Test", null, true,
                false, false);
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(200 , 100,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            success = false;
        }
        assertTrue(success);
    }

}
