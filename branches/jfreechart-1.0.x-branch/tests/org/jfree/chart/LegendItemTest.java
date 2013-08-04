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
 * -------------------
 * LegendItemTest.java
 * -------------------
 * (C) Copyright 2004-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 04-Jun-2004 : Version 1 (DG);
 * 10-Dec-2005 : Addded new test to cover bug report 1374328 (DG);
 * 13-Dec-2006 : Extended testEquals() for new fillPaintTransformer
 *               attribute (DG);
 * 23-Apr-2008 : Implemented Cloneable (DG);
 * 17-Jun-2008 : Included new fields in existing tests (DG);
 *
 */

package org.jfree.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.font.TextAttribute;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.AttributedString;

import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNotSame;


/**
 * Tests for the {@link LegendItem} class.
 */
public class LegendItemTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        LegendItem item1 = new LegendItem("Label", "Description",
                "ToolTip", "URL", true,
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true, Color.red,
                true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(2.1f), Color.green);
        LegendItem item2 = new LegendItem("Label", "Description",
                "ToolTip", "URL", true,
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);
        assertEquals(item2, item1);

        item1 = new LegendItem("Label2", "Description", "ToolTip", "URL",
                true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), true,
                Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description", "ToolTip", "URL",
                true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", true, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                true, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.red, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, true, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.blue, new BasicStroke(1.2f),
                true, new Line2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(2.1f), Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.blue, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.yellow, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.yellow, new BasicStroke(1.2f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.yellow, new BasicStroke(2.1f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.yellow, new BasicStroke(2.1f), true,
                new Line2D.Double(1.0, 2.0, 3.0, 4.0), new BasicStroke(2.1f),
                Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(2.1f), Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(1.0, 2.0, 3.0, 4.0),
                new BasicStroke(2.1f),  Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.1f), Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(2.1f), Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(3.3f), Color.green);
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(3.3f), Color.green);
        assertEquals(item1, item2);

        item1 = new LegendItem("Label2", "Description2", "ToolTip", "URL",
                false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0), false,
                Color.black, false, Color.yellow, new BasicStroke(2.1f), false,
            new Line2D.Double(4.0, 3.0, 2.0, 1.0), new BasicStroke(3.3f),
            Color.white
        );
        assertFalse(item1.equals(item2));
        item2 = new LegendItem("Label2", "Description2", "ToolTip",
                "URL", false, new Rectangle2D.Double(4.0, 3.0, 2.0, 1.0),
                false, Color.black, false, Color.yellow, new BasicStroke(2.1f),
                false, new Line2D.Double(4.0, 3.0, 2.0, 1.0),
                new BasicStroke(3.3f),
                Color.white);
        assertEquals(item1, item2);

        // fillPaintTransformer
        item1.setFillPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_VERTICAL));
        assertFalse(item1.equals(item2));
        item2.setFillPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.CENTER_VERTICAL));
        assertEquals(item1, item2);

        // labelFont
        item1.setLabelFont(new Font("Dialog", Font.PLAIN, 13));
        assertFalse(item1.equals(item2));
        item2.setLabelFont(new Font("Dialog", Font.PLAIN, 13));
        assertEquals(item1, item2);

        // labelPaint
        item1.setLabelPaint(Color.red);
        assertFalse(item1.equals(item2));
        item2.setLabelPaint(Color.red);
        assertEquals(item1, item2);

        // fillPaint
        item1.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.green, 3.0f,
                4.0f, Color.blue));
        assertFalse(item1.equals(item2));
        item2.setFillPaint(new GradientPaint(1.0f, 2.0f, Color.green, 3.0f,
                4.0f, Color.blue));
        assertEquals(item1, item2);

        // outlinePaint
        item1.setOutlinePaint(new GradientPaint(1.1f, 2.2f, Color.green, 3.3f,
                4.4f, Color.blue));
        assertFalse(item1.equals(item2));
        item2.setOutlinePaint(new GradientPaint(1.1f, 2.2f, Color.green, 3.3f,
                4.4f, Color.blue));
        assertEquals(item1, item2);

        // linePaint
        item1.setLinePaint(new GradientPaint(0.1f, 0.2f, Color.green, 0.3f,
                0.4f, Color.blue));
        assertFalse(item1.equals(item2));
        item2.setLinePaint(new GradientPaint(0.1f, 0.2f, Color.green, 0.3f,
                0.4f, Color.blue));
        assertEquals(item1, item2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        LegendItem item1 = new LegendItem("Item", "Description",
                "ToolTip", "URL",
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), new GradientPaint(
                        5.0f, 6.0f, Color.blue, 7.0f, 8.0f, Color.gray));
        item1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.red, 3.0f,
                4.0f, Color.yellow));
        item1.setOutlinePaint(new GradientPaint(4.0f, 3.0f, Color.green, 2.0f,
                1.0f, Color.red));
        item1.setLinePaint(new GradientPaint(1.0f, 2.0f, Color.white, 3.0f,
                4.0f, Color.red));
        LegendItem item2;
        item2 = (LegendItem) TestUtilities.serialised(item1);
        assertEquals(item1, item2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization2() {
        AttributedString as = new AttributedString("Test String");
        as.addAttribute(TextAttribute.FONT, new Font("Dialog", Font.PLAIN, 12));
        LegendItem item1 = new LegendItem(as, "Description", "ToolTip", "URL",
                new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0), Color.red);
        LegendItem item2 = (LegendItem) TestUtilities.serialised(item1);
        assertEquals(item1, item2);
    }

    /**
     * Basic checks for cloning.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        LegendItem item1 = new LegendItem("Item");
        LegendItem item2 = (LegendItem) item1.clone();

        assertNotSame(item1, item2);
        assertSame(item1.getClass(), item2.getClass());
        assertEquals(item1, item2);

        // the clone references the same dataset as the original
        assertSame(item1.getDataset(), item2.getDataset());
    }

}
