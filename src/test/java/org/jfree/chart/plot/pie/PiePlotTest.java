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
 * ----------------
 * PiePlotTest.java
 * ----------------
 * (C) Copyright 2003-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot.pie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.AttributedString;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.legend.LegendItemCollection;
import org.jfree.chart.TestUtils;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.urls.CustomPieURLGenerator;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.chart.internal.CloneUtils;
import org.jfree.chart.util.DefaultShadowGenerator;
import org.jfree.chart.api.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for the {@link PiePlot} class.
 */
public class PiePlotTest {

    /**
     * Test the equals() method.
     */
    @Test
    public void testEquals() {

        PiePlot plot1 = new PiePlot();
        PiePlot plot2 = new PiePlot();
        assertEquals(plot1, plot2);
        assertEquals(plot2, plot1);

        // pieIndex...
        plot1.setPieIndex(99);
        assertNotEquals(plot1, plot2);
        plot2.setPieIndex(99);
        assertEquals(plot1, plot2);

        // interiorGap...
        plot1.setInteriorGap(0.15);
        assertNotEquals(plot1, plot2);
        plot2.setInteriorGap(0.15);
        assertEquals(plot1, plot2);

        // circular
        plot1.setCircular(!plot1.isCircular());
        assertNotEquals(plot1, plot2);
        plot2.setCircular(false);
        assertEquals(plot1, plot2);

        // startAngle
        plot1.setStartAngle(Math.PI);
        assertNotEquals(plot1, plot2);
        plot2.setStartAngle(Math.PI);
        assertEquals(plot1, plot2);

        // direction
        plot1.setDirection(Rotation.ANTICLOCKWISE);
        assertNotEquals(plot1, plot2);
        plot2.setDirection(Rotation.ANTICLOCKWISE);
        assertEquals(plot1, plot2);

        // ignoreZeroValues
        plot1.setIgnoreZeroValues(true);
        plot2.setIgnoreZeroValues(false);
        assertNotEquals(plot1, plot2);
        plot2.setIgnoreZeroValues(true);
        assertEquals(plot1, plot2);

        // ignoreNullValues
        plot1.setIgnoreNullValues(true);
        plot2.setIgnoreNullValues(false);
        assertNotEquals(plot1, plot2);
        plot2.setIgnoreNullValues(true);
        assertEquals(plot1, plot2);

        // sectionPaintMap
        plot1.setSectionPaint("A", new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setSectionPaint("A", new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // defaultSectionPaint
        plot1.setDefaultSectionPaint(new GradientPaint(1.0f, 2.0f, Color.BLACK,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setDefaultSectionPaint(new GradientPaint(1.0f, 2.0f, Color.BLACK,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // sectionOutlinesVisible
        plot1.setSectionOutlinesVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setSectionOutlinesVisible(false);
        assertEquals(plot1, plot2);

        // sectionOutlinePaintList
        plot1.setSectionOutlinePaint("A", new GradientPaint(1.0f, 2.0f,
                Color.GREEN, 3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setSectionOutlinePaint("A", new GradientPaint(1.0f, 2.0f,
                Color.GREEN, 3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // defaultSectionOutlinePaint
        plot1.setDefaultSectionOutlinePaint(new GradientPaint(1.0f, 2.0f,
                Color.GRAY, 3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setDefaultSectionOutlinePaint(new GradientPaint(1.0f, 2.0f,
                Color.GRAY, 3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // sectionOutlineStrokeList
        plot1.setSectionOutlineStroke("A", new BasicStroke(1.0f));
        assertNotEquals(plot1, plot2);
        plot2.setSectionOutlineStroke("A", new BasicStroke(1.0f));
        assertEquals(plot1, plot2);

        // defaultSectionOutlineStroke
        plot1.setDefaultSectionOutlineStroke(new BasicStroke(1.0f));
        assertNotEquals(plot1, plot2);
        plot2.setDefaultSectionOutlineStroke(new BasicStroke(1.0f));
        assertEquals(plot1, plot2);

        // shadowPaint
        plot1.setShadowPaint(new GradientPaint(1.0f, 2.0f, Color.ORANGE,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setShadowPaint(new GradientPaint(1.0f, 2.0f, Color.ORANGE,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // shadowXOffset
        plot1.setShadowXOffset(4.4);
        assertNotEquals(plot1, plot2);
        plot2.setShadowXOffset(4.4);
        assertEquals(plot1, plot2);

        // shadowYOffset
        plot1.setShadowYOffset(4.4);
        assertNotEquals(plot1, plot2);
        plot2.setShadowYOffset(4.4);
        assertEquals(plot1, plot2);

        // labelFont
        plot1.setLabelFont(new Font("Serif", Font.PLAIN, 18));
        assertNotEquals(plot1, plot2);
        plot2.setLabelFont(new Font("Serif", Font.PLAIN, 18));
        assertEquals(plot1, plot2);

        // labelPaint
        plot1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.DARK_GRAY,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.DARK_GRAY,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // labelBackgroundPaint
        plot1.setLabelBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelBackgroundPaint(new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // labelOutlinePaint
        plot1.setLabelOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelOutlinePaint(new GradientPaint(1.0f, 2.0f, Color.BLUE,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // labelOutlineStroke
        Stroke s = new BasicStroke(1.1f);
        plot1.setLabelOutlineStroke(s);
        assertNotEquals(plot1, plot2);
        plot2.setLabelOutlineStroke(s);
        assertEquals(plot1, plot2);

        // labelShadowPaint
        plot1.setLabelShadowPaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelShadowPaint(new GradientPaint(1.0f, 2.0f, Color.YELLOW,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // explodePercentages
        plot1.setExplodePercent("A", 0.33);
        assertNotEquals(plot1, plot2);
        plot2.setExplodePercent("A", 0.33);
        assertEquals(plot1, plot2);

        // labelGenerator
        plot1.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{2}{1}{0}"));
        assertNotEquals(plot1, plot2);
        plot2.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{2}{1}{0}"));
        assertEquals(plot1, plot2);

        // labelFont
        Font f = new Font("SansSerif", Font.PLAIN, 20);
        plot1.setLabelFont(f);
        assertNotEquals(plot1, plot2);
        plot2.setLabelFont(f);
        assertEquals(plot1, plot2);

        // labelPaint
        plot1.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.MAGENTA,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelPaint(new GradientPaint(1.0f, 2.0f, Color.MAGENTA,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // maximumLabelWidth
        plot1.setMaximumLabelWidth(0.33);
        assertNotEquals(plot1, plot2);
        plot2.setMaximumLabelWidth(0.33);
        assertEquals(plot1, plot2);

        // labelGap
        plot1.setLabelGap(0.11);
        assertNotEquals(plot1, plot2);
        plot2.setLabelGap(0.11);
        assertEquals(plot1, plot2);

        // links visible
        plot1.setLabelLinksVisible(false);
        assertNotEquals(plot1, plot2);
        plot2.setLabelLinksVisible(false);
        assertEquals(plot1, plot2);

        plot1.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
        assertNotEquals(plot1, plot2);
        plot2.setLabelLinkStyle(PieLabelLinkStyle.QUAD_CURVE);
        assertEquals(plot1, plot2);

        // linkMargin
        plot1.setLabelLinkMargin(0.11);
        assertNotEquals(plot1, plot2);
        plot2.setLabelLinkMargin(0.11);
        assertEquals(plot1, plot2);

        // labelLinkPaint
        plot1.setLabelLinkPaint(new GradientPaint(1.0f, 2.0f, Color.MAGENTA,
                3.0f, 4.0f, Color.WHITE));
        assertNotEquals(plot1, plot2);
        plot2.setLabelLinkPaint(new GradientPaint(1.0f, 2.0f, Color.MAGENTA,
                3.0f, 4.0f, Color.WHITE));
        assertEquals(plot1, plot2);

        // labelLinkStroke
        plot1.setLabelLinkStroke(new BasicStroke(1.0f));
        assertNotEquals(plot1, plot2);
        plot2.setLabelLinkStroke(new BasicStroke(1.0f));
        assertEquals(plot1, plot2);

        // toolTipGenerator
        plot1.setToolTipGenerator(new StandardPieToolTipGenerator("{2}{1}{0}"));
        assertNotEquals(plot1, plot2);
        plot2.setToolTipGenerator(new StandardPieToolTipGenerator("{2}{1}{0}"));
        assertEquals(plot1, plot2);

        // urlGenerator
        plot1.setURLGenerator(new StandardPieURLGenerator("xx"));
        assertNotEquals(plot1, plot2);
        plot2.setURLGenerator(new StandardPieURLGenerator("xx"));
        assertEquals(plot1, plot2);

        // minimumArcAngleToDraw
        plot1.setMinimumArcAngleToDraw(1.0);
        assertNotEquals(plot1, plot2);
        plot2.setMinimumArcAngleToDraw(1.0);
        assertEquals(plot1, plot2);

        // legendItemShape
        plot1.setLegendItemShape(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertNotEquals(plot1, plot2);
        plot2.setLegendItemShape(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        assertEquals(plot1, plot2);

        // legendLabelGenerator
        plot1.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} --> {1}"));
        assertNotEquals(plot1, plot2);
        plot2.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0} --> {1}"));
        assertEquals(plot1, plot2);

        // legendLabelToolTipGenerator
        plot1.setLegendLabelToolTipGenerator(
                new StandardPieSectionLabelGenerator("{0} is {1}"));
        assertNotEquals(plot1, plot2);
        plot2.setLegendLabelToolTipGenerator(
                new StandardPieSectionLabelGenerator("{0} is {1}"));
        assertEquals(plot1, plot2);

        // legendLabelURLGenerator
        plot1.setLegendLabelURLGenerator(new StandardPieURLGenerator(
                "index.html"));
        assertNotEquals(plot1, plot2);
        plot2.setLegendLabelURLGenerator(new StandardPieURLGenerator(
                "index.html"));
        assertEquals(plot1, plot2);

        // autoPopulateSectionPaint
        plot1.setAutoPopulateSectionPaint(false);
        assertNotEquals(plot1, plot2);
        plot2.setAutoPopulateSectionPaint(false);
        assertEquals(plot1, plot2);

        // autoPopulateSectionOutlinePaint
        plot1.setAutoPopulateSectionOutlinePaint(true);
        assertNotEquals(plot1, plot2);
        plot2.setAutoPopulateSectionOutlinePaint(true);
        assertEquals(plot1, plot2);

        // autoPopulateSectionOutlineStroke
        plot1.setAutoPopulateSectionOutlineStroke(true);
        assertNotEquals(plot1, plot2);
        plot2.setAutoPopulateSectionOutlineStroke(true);
        assertEquals(plot1, plot2);

        // shadowGenerator
        plot1.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertNotEquals(plot1, plot2);
        plot2.setShadowGenerator(new DefaultShadowGenerator(5, Color.GRAY,
                0.6f, 4, -Math.PI / 4));
        assertEquals(plot1, plot2);

        plot1.setShadowGenerator(null);
        assertNotEquals(plot1, plot2);
        plot2.setShadowGenerator(null);
        assertEquals(plot1, plot2);
    }

    /**
     * Some basic checks for the clone() method.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        PiePlot p1 = new PiePlot();
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);
    }

    /**
     * Check cloning of the urlGenerator field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_URLGenerator() throws CloneNotSupportedException {
        CustomPieURLGenerator generator = new CustomPieURLGenerator();
        PiePlot p1 = new PiePlot();
        p1.setURLGenerator(generator);
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check that the URL generator has been cloned
        assertNotSame(p1.getURLGenerator(), p2.getURLGenerator());
    }

    /**
     * Check cloning of the legendItemShape field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_LegendItemShape() throws CloneNotSupportedException {
        Rectangle shape = new Rectangle(-4, -4, 8, 8);
        PiePlot p1 = new PiePlot();
        p1.setLegendItemShape(shape);
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // change the shape and make sure it only affects p1
        shape.setRect(1.0, 2.0, 3.0, 4.0);
        assertNotEquals(p1, p2);
    }

    /**
     * Check cloning of the legendLabelGenerator field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_LegendLabelGenerator() throws CloneNotSupportedException {
        StandardPieSectionLabelGenerator generator
                = new StandardPieSectionLabelGenerator();
        PiePlot p1 = new PiePlot();
        p1.setLegendLabelGenerator(generator);
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // change the generator and make sure it only affects p1
        generator.getNumberFormat().setMinimumFractionDigits(2);
        assertNotEquals(p1, p2);
    }

    /**
     * Check cloning of the legendLabelToolTipGenerator field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_LegendLabelToolTipGenerator() throws CloneNotSupportedException {
        StandardPieSectionLabelGenerator generator
                = new StandardPieSectionLabelGenerator();
        PiePlot p1 = new PiePlot();
        p1.setLegendLabelToolTipGenerator(generator);
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // change the generator and make sure it only affects p1
        generator.getNumberFormat().setMinimumFractionDigits(2);
        assertNotEquals(p1, p2);
    }

    /**
     * Check cloning of the legendLabelURLGenerator field.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning_LegendLabelURLGenerator() throws CloneNotSupportedException {
        CustomPieURLGenerator generator = new CustomPieURLGenerator();
        PiePlot p1 = new PiePlot();
        p1.setLegendLabelURLGenerator(generator);
        PiePlot p2 = CloneUtils.clone(p1);
        assertNotSame(p1, p2);
        assertSame(p1.getClass(), p2.getClass());
        assertEquals(p1, p2);

        // check that the URL generator has been cloned
        assertNotSame(p1.getLegendLabelURLGenerator(), p2.getLegendLabelURLGenerator());
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        PiePlot p1 = new PiePlot(null);
        PiePlot p2 = TestUtils.serialised(p1);
        assertEquals(p1, p2);
    }

    /**
     * Some checks for the getLegendItems() method.
     */
    @Test
    public void testGetLegendItems() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Item 1", 1.0);
        dataset.setValue("Item 2", 2.0);
        dataset.setValue("Item 3", 0.0);
        dataset.setValue("Item 4", null);

        PiePlot plot = new PiePlot(dataset);
        plot.setIgnoreNullValues(false);
        plot.setIgnoreZeroValues(false);
        LegendItemCollection items = plot.getLegendItems();
        assertEquals(4, items.getItemCount());

        // check that null items are ignored if requested
        plot.setIgnoreNullValues(true);
        items = plot.getLegendItems();
        assertEquals(3, items.getItemCount());

        // check that zero items are ignored if requested
        plot.setIgnoreZeroValues(true);
        items = plot.getLegendItems();
        assertEquals(2, items.getItemCount());

        // check that negative items are always ignored
        dataset.setValue("Item 5", -1.0);
        items = plot.getLegendItems();
        assertEquals(2, items.getItemCount());
    }

    /**
     * Check that the default section paint is not null, and that you
     * can never set it to null.
     */
    @Test
    public void testGetDefaultSectionPaint() {
        PiePlot plot = new PiePlot();
        assertNotNull(plot.getDefaultSectionPaint());

        boolean pass = false;
        try {
            plot.setDefaultSectionPaint(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    static class NullLegendLabelGenerator implements PieSectionLabelGenerator<String> {
        @Override
        public AttributedString generateAttributedSectionLabel(
                PieDataset<String> dataset, String key) {
            return null;
        }
        @Override
        public String generateSectionLabel(PieDataset<String> dataset, String key) {
            return null;
        }
    }

    /**
     * Draws a pie chart where the label generator returns null.
     */
    @Test
    public void testDrawWithNullLegendLabels() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("L1", 12.0);
        dataset.setValue("L2", 11.0);
        JFreeChart chart = ChartFactory.createPieChart("Test", dataset, true,
                false, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLegendLabelGenerator(new NullLegendLabelGenerator());
        boolean success;
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
    
    @Test
    public void testBug1126() throws CloneNotSupportedException {
        DefaultPieDataset<String> dataset1 = new DefaultPieDataset<>();
        PiePlot plot1 = new PiePlot(dataset1);
        plot1.setSectionPaint("A", Color.RED);
        plot1.setSectionPaint("B", Color.GREEN);
        PiePlot plot2 = CloneUtils.clone(plot1);
        plot2.setSectionPaint("A", Color.BLUE);
        plot2.setSectionPaint("B", Color.YELLOW);
        assertEquals(Color.RED, plot1.getSectionPaint("A"));
        assertEquals(Color.GREEN, plot1.getSectionPaint("B"));
        assertEquals(Color.BLUE, plot2.getSectionPaint("A"));
        assertEquals(Color.YELLOW, plot2.getSectionPaint("B"));
    }
    
    @Test
    public void testBug1126_b() throws CloneNotSupportedException {
        DefaultPieDataset<String> dataset1 = new DefaultPieDataset<>();
        PiePlot plot1 = new PiePlot(dataset1);
        plot1.setSectionOutlinePaint("A", Color.RED);
        plot1.setSectionOutlinePaint("B", Color.GREEN);
        PiePlot plot2 = CloneUtils.clone(plot1);
        plot2.setSectionOutlinePaint("A", Color.BLUE);
        plot2.setSectionOutlinePaint("B", Color.YELLOW);
        assertEquals(Color.RED, plot1.getSectionOutlinePaint("A"));
        assertEquals(Color.GREEN, plot1.getSectionOutlinePaint("B"));
        assertEquals(Color.BLUE, plot2.getSectionOutlinePaint("A"));
        assertEquals(Color.YELLOW, plot2.getSectionOutlinePaint("B"));
    }
    
    @Test
    public void testBug1126_c() throws CloneNotSupportedException {
        DefaultPieDataset<String> dataset1 = new DefaultPieDataset<>();
        PiePlot plot1 = new PiePlot(dataset1);
        plot1.setSectionOutlineStroke("A", new BasicStroke(5.0f));
        plot1.setSectionOutlineStroke("B", new BasicStroke(6.0f));
        PiePlot plot2 = CloneUtils.clone(plot1);
        plot2.setSectionOutlineStroke("A", new BasicStroke(7.0f));
        plot2.setSectionOutlineStroke("B", new BasicStroke(8.0f));
        assertEquals(new BasicStroke(5.0f), plot1.getSectionOutlineStroke("A"));
        assertEquals(new BasicStroke(6.0f), plot1.getSectionOutlineStroke("B"));
        assertEquals(new BasicStroke(7.0f), plot2.getSectionOutlineStroke("A"));
        assertEquals(new BasicStroke(8.0f), plot2.getSectionOutlineStroke("B"));
    }
    
    @Test
    public void testBug1126_d() throws CloneNotSupportedException {
        DefaultPieDataset<String> dataset1 = new DefaultPieDataset<>();
        PiePlot plot1 = new PiePlot(dataset1);
        plot1.setExplodePercent("A", 0.1);
        plot1.setExplodePercent("B", 0.2);
        PiePlot plot2 = CloneUtils.clone(plot1);
        plot2.setExplodePercent("A", 0.3);
        plot2.setExplodePercent("B", 0.4);
        assertEquals(0.1, plot1.getExplodePercent("A"), EPSILON);
        assertEquals(0.2, plot1.getExplodePercent("B"), EPSILON);
        assertEquals(0.3, plot2.getExplodePercent("A"), EPSILON);
        assertEquals(0.4, plot2.getExplodePercent("B"), EPSILON);
    }
    
    private static final double EPSILON = 0.000000001;

    @Test
    public void testBug1126_e() throws CloneNotSupportedException {
        DefaultPieDataset<String> dataset1 = new DefaultPieDataset<>();
        PiePlot plot1 = new PiePlot(dataset1);
        plot1.setLabelGenerator(new StandardPieSectionLabelGenerator());
        PiePlot plot2 = CloneUtils.clone(plot1);
        StandardPieSectionLabelGenerator g2 
                = (StandardPieSectionLabelGenerator) plot2.getLabelGenerator();
        g2.setAttributedLabel(1, new AttributedString("TESTING"));
        assertNotEquals(plot1, plot2);
    }

}
