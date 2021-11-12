/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2021, by David Gilbert and Contributors.
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
 * ------------------------
 * XYBoxAnnotationTest.java
 * ------------------------
 * (C) Copyright 2005-2021, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.annotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.BasicStroke;
import java.awt.Color;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import java.awt.GradientPaint;
import java.awt.Stroke;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.util.PublicCloneable;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.junit.jupiter.api.Test;

/**
 * Some tests for the {@link XYBoxAnnotation} class.
 */
public class XYBoxAnnotationTest {

    @Test
    public void testConstructorExceptions() {
        Stroke stroke = new BasicStroke(2.0f);
        assertThrows(IllegalArgumentException.class, () -> {
            XYBoxAnnotation a1 = new XYBoxAnnotation(Double.NaN, 20.0, 100.0, 200.0,
                stroke, Color.BLUE, Color.RED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYBoxAnnotation a1 = new XYBoxAnnotation(10.0, Double.NaN, 100.0, 200.0,
                stroke, Color.BLUE, Color.RED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYBoxAnnotation a1 = new XYBoxAnnotation(10.0, 20.0, Double.NaN, 200.0,
                stroke, Color.BLUE, Color.RED);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            XYBoxAnnotation a1 = new XYBoxAnnotation(10.0, 20.0, 100.0, Double.NaN,
                stroke, Color.BLUE, Color.RED);
        });
    }

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(XYBoxAnnotation.class)
            .withRedefinedSuperclass() // superclass also defines equals/hashCode
            .suppress(Warning.STRICT_INHERITANCE)
            .suppress(Warning.NONFINAL_FIELDS)
            .verify();
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        XYBoxAnnotation a1 = new XYBoxAnnotation(1.0, 2.0, 3.0, 4.0,
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        XYBoxAnnotation a2 = (XYBoxAnnotation) a1.clone();
        assertTrue(a1 != a2);
        assertTrue(a1.getClass() == a2.getClass());
        assertTrue(a1.equals(a2));
    }

    /**
     * Checks that this class implements PublicCloneable.
     */
    @Test
    public void testPublicCloneable() {
        XYBoxAnnotation a1 = new XYBoxAnnotation(1.0, 2.0, 3.0, 4.0,
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        assertTrue(a1 instanceof PublicCloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        XYBoxAnnotation a1 = new XYBoxAnnotation(1.0, 2.0, 3.0, 4.0,
                new BasicStroke(1.2f), Color.RED, Color.BLUE);
        XYBoxAnnotation a2 = TestUtils.serialised(a1);
        assertEquals(a1, a2);
    }

    /**
     * Draws the chart with a {@code null} info object to make sure that
     * no exceptions are thrown.
     */
    @Test
    public void testDrawWithNullInfo() {
        try {
            DefaultTableXYDataset dataset = new DefaultTableXYDataset();

            XYSeries s1 = new XYSeries("Series 1", true, false);
            s1.add(5.0, 5.0);
            s1.add(10.0, 15.5);
            s1.add(15.0, 9.5);
            s1.add(20.0, 7.5);
            dataset.addSeries(s1);

            XYSeries s2 = new XYSeries("Series 2", true, false);
            s2.add(5.0, 5.0);
            s2.add(10.0, 15.5);
            s2.add(15.0, 9.5);
            s2.add(20.0, 3.5);
            dataset.addSeries(s2);
            XYPlot plot = new XYPlot(dataset,
                    new NumberAxis("X"), new NumberAxis("Y"),
                    new XYLineAndShapeRenderer());
            plot.addAnnotation(new XYBoxAnnotation(10.0, 12.0, 3.0, 4.0,
                    new BasicStroke(1.2f), Color.RED, Color.BLUE));
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200,
                    null);
        }
        catch (NullPointerException e) {
            fail("No exception should be triggered.");
        }
    }

}
