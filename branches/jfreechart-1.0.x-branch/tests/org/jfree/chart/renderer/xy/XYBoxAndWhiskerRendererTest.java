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
 * --------------------------------
 * XYBoxAndWhiskerRendererTest.java
 * --------------------------------
 * (C) Copyright 2003-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 22-Oct-2003 : Version 1 (DG);
 * 23-Apr-2004 : Extended testEquals() method (DG);
 * 27-Mar-2008 : Extended testEquals() some more (DG);
 * 22-Apr-2008 : Added testPublicCloneable (DG);
 * 08-Dec-2008 : Added test2909215() (DG);
 *
 */

package org.jfree.chart.renderer.xy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtilities;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerXYDataset;
import org.jfree.util.PublicCloneable;
import org.junit.Test;

/**
 * Tests for the {@link XYBoxAndWhiskerRenderer} class.
 */
public class XYBoxAndWhiskerRendererTest {

    /**
     * Check that the equals() method distinguishes all fields.
     */
    @Test
    public void testEquals() {
        XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
        XYBoxAndWhiskerRenderer r2 = new XYBoxAndWhiskerRenderer();
        assertEquals(r1, r2);

        r1.setPaint(new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.red));
        assertFalse(r1.equals(r2));
        r2.setPaint(new GradientPaint(1.0f, 2.0f, Color.yellow,
                3.0f, 4.0f, Color.red));
        assertEquals(r1, r2);

        r1.setArtifactPaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.red));
        assertFalse(r1.equals(r2));
        r2.setArtifactPaint(new GradientPaint(1.0f, 2.0f, Color.green,
                3.0f, 4.0f, Color.red));
        assertEquals(r1, r2);

        r1.setBoxWidth(0.55);
        assertFalse(r1.equals(r2));
        r2.setBoxWidth(0.55);
        assertEquals(r1, r2);

        r1.setFillBox(!r1.getFillBox());
        assertFalse(r1.equals(r2));
        r2.setFillBox(!r2.getFillBox());
        assertEquals(r1, r2);

        r1.setBoxPaint(Color.yellow);
        assertFalse(r1.equals(r2));
        r2.setBoxPaint(Color.yellow);
        assertEquals(r1, r2);

        // check boxPaint null also
        r1.setBoxPaint(null);
        assertFalse(r1.equals(r2));
        r2.setBoxPaint(null);
        assertEquals(r1, r2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode.
     */
    @Test
    public void testHashcode() {
        XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
        XYBoxAndWhiskerRenderer r2 = new XYBoxAndWhiskerRenderer();
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
        XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
        XYBoxAndWhiskerRenderer r2 = (XYBoxAndWhiskerRenderer) r1.clone();
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Verify that this class implements {@link PublicCloneable}.
     */
    @Test
    public void testPublicCloneable() {
        XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
        assertTrue(r1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYBoxAndWhiskerRenderer r1 = new XYBoxAndWhiskerRenderer();
        XYBoxAndWhiskerRenderer r2 = (XYBoxAndWhiskerRenderer) 
                TestUtilities.serialised(r1);
        assertEquals(r1, r2);
    }

    /**
     * A test for bug report 2909215.
     */
    @Test
    public void test2909215() {
        DefaultBoxAndWhiskerXYDataset d1 = new DefaultBoxAndWhiskerXYDataset(
                "Series");
        d1.add(new Date(1L), new BoxAndWhiskerItem(new Double(1.0),
                new Double(2.0), new Double(3.0), new Double(4.0),
                new Double(5.0), new Double(6.0), null, null, null));
        JFreeChart chart = ChartFactory.createBoxAndWhiskerChart("Title", "X",
                "Y", d1, true);
        try {
            BufferedImage image = new BufferedImage(400, 200,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 400, 200), null, null);
            g2.dispose();
        }
        catch (Exception e) {
            fail("No exception should be thrown.");
        }
    }

}

