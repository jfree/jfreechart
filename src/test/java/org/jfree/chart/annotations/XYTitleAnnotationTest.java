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
 * --------------------------
 * XYTitleAnnotationTest.java
 * --------------------------
 * (C) Copyright 2007-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.chart.annotations;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.TestUtils;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.title.Title;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.XYSeries;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYTitleAnnotation} class.
 */
public class XYTitleAnnotationTest {

    /**
     * Use EqualsVerifier to test that the contract between equals and hashCode
     * is properly implemented.
     */
    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(XYTitleAnnotation.class)
                .withRedefinedSuperclass() // superclass also defines equals/hashCode
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withPrefabValues(Title.class,
                        new TextTitle("abc"),
                        new TextTitle("def"))
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())

                .verify();
    }
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        TextTitle t = new TextTitle("Title");
        XYTitleAnnotation a1 = new XYTitleAnnotation(1.0, 2.0, t);
        XYTitleAnnotation a2 = new XYTitleAnnotation(1.0, 2.0, t);
        assertEquals(a1, a2);
        
        a1 = new XYTitleAnnotation(1.1, 2.0, t);
        assertNotEquals(a1, a2);
        a2 = new XYTitleAnnotation(1.1, 2.0, t);
        assertEquals(a1, a2);

        a1 = new XYTitleAnnotation(1.1, 2.2, t);
        assertNotEquals(a1, a2);
        a2 = new XYTitleAnnotation(1.1, 2.2, t);
        assertEquals(a1, a2);
        
        TextTitle t2 = new TextTitle("Title 2");
        a1 = new XYTitleAnnotation(1.1, 2.2, t2);
        assertNotEquals(a1, a2);
        a2 = new XYTitleAnnotation(1.1, 2.2, t2);
        assertEquals(a1, a2);
    }

    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    @Test
    public void testHashCode() {
        TextTitle t = new TextTitle("Title");
        XYTitleAnnotation a1 = new XYTitleAnnotation(1.0, 2.0, t);
        XYTitleAnnotation a2 = new XYTitleAnnotation(1.0, 2.0, t);
        assertEquals(a1, a2);
        int h1 = a1.hashCode();
        int h2 = a2.hashCode();
        assertEquals(h1, h2);
    }
    
    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException if there is a cloning issue.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TextTitle t = new TextTitle("Title");
        XYTitleAnnotation a1 = new XYTitleAnnotation(1.0, 2.0, t);
        XYTitleAnnotation a2 = (XYTitleAnnotation) a1.clone();
        assertNotSame(a1, a2);
        assertSame(a1.getClass(), a2.getClass());
        assertEquals(a1, a2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TextTitle t = new TextTitle("Title");
        XYTitleAnnotation a1 = new XYTitleAnnotation(1.0, 2.0, t);
        XYTitleAnnotation a2 = TestUtils.serialised(a1);
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
            plot.addAnnotation(new XYTitleAnnotation(5.0, 6.0, 
                    new TextTitle("Hello World!")));
            JFreeChart chart = new JFreeChart(plot);
            /* BufferedImage image = */ chart.createBufferedImage(300, 200, 
                    null);
        }
        catch (NullPointerException e) {
            fail("There should be no exception.");
        }
    }

}
