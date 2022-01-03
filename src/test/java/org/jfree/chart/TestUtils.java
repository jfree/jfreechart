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
 * --------------
 * TestUtils.java
 * --------------
 * (C) Copyright 2007-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart;

import org.jfree.chart.renderer.AbstractRenderer;

import java.awt.*;
import java.io.*;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some utility methods for use by the testing code.
 */
public class TestUtils {

    /**
     * Returns {@code true} if the collections contains any object that
     * is an instance of the specified class, and {@code false} otherwise.
     *
     * @param collection  the collection.
     * @param c  the class.
     *
     * @return A boolean.
     */
    public static boolean containsInstanceOf(Collection<?> collection, Class c) {
        for (Object obj : collection) {
            if (obj != null && obj.getClass().equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Serialises an object, deserialises it and returns the deserialised 
     * version.
     * 
     * @param original  the original object.
     * 
     * @return A serialised and deserialised version of the original.
     */
    public static <K> K serialised(K original) {
        K result = null;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(buffer);
            out.writeObject(original);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            result = (K) in.readObject();
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    
    /**
     * Checks the two renderers for independence.  It is expected that the 
     * two renderers have the same attributes (typically one is a clone of the
     * other or has been deserialised from the serialised representation of
     * the other).  This method will update each attribute in turn and check
     * that updating one renderer does not impact the other. Note that this
     * method is destructive in the sense that it changes all the attributes
     * of the renderer (maybe the code can be updated later to restore each
     * attribute setting after it has been tested).
     * 
     * @param r1  renderer one ({@code null} not permitted).
     * @param r2  renderer two ({@code null} not permitted).
     */
    public static void checkIndependence(AbstractRenderer r1, AbstractRenderer r2) {
        assertNotSame(r1, r2);
        r1.setAutoPopulateSeriesFillPaint(!r1.getAutoPopulateSeriesFillPaint());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesFillPaint(r1.getAutoPopulateSeriesFillPaint());
        assertEquals(r1, r2);
        
        r1.setAutoPopulateSeriesOutlinePaint(!r1.getAutoPopulateSeriesOutlinePaint());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesOutlinePaint(r1.getAutoPopulateSeriesOutlinePaint());
        assertEquals(r1, r2);

        r1.setAutoPopulateSeriesOutlineStroke(!r1.getAutoPopulateSeriesOutlineStroke());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesOutlineStroke(r1.getAutoPopulateSeriesOutlineStroke());
        assertEquals(r1, r2);
        
        r1.setAutoPopulateSeriesPaint(!r1.getAutoPopulateSeriesPaint());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesPaint(r1.getAutoPopulateSeriesPaint());
        assertEquals(r1, r2);
        
        r1.setAutoPopulateSeriesShape(!r1.getAutoPopulateSeriesShape());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesShape(r1.getAutoPopulateSeriesShape());
        assertEquals(r1, r2);
        
        r1.setAutoPopulateSeriesStroke(!r1.getAutoPopulateSeriesStroke());
        assertNotEquals(r1, r2);
        r2.setAutoPopulateSeriesStroke(r1.getAutoPopulateSeriesStroke());
        assertEquals(r1, r2);
   
        r1.setDataBoundsIncludesVisibleSeriesOnly(!r1.getDataBoundsIncludesVisibleSeriesOnly());
        assertNotEquals(r1, r2);
        r2.setDataBoundsIncludesVisibleSeriesOnly(r1.getDataBoundsIncludesVisibleSeriesOnly());
        assertEquals(r1, r2);
        
        r1.setDefaultCreateEntities(!r1.getDefaultCreateEntities());
        assertNotEquals(r1, r2);
        r2.setDefaultCreateEntities(r1.getDefaultCreateEntities());
        assertEquals(r1, r2);
        
        r1.setDefaultEntityRadius(66);
        assertNotEquals(r1, r2);
        r2.setDefaultEntityRadius(66);
        assertEquals(r1, r2);
        
        r1.setDefaultFillPaint(new GradientPaint(4.0f, 5.0f, Color.RED, 6.0f, 7.0f, Color.YELLOW));
        assertNotEquals(r1, r2);
        r2.setDefaultFillPaint(new GradientPaint(4.0f, 5.0f, Color.RED, 6.0f, 7.0f, Color.YELLOW));
        assertEquals(r1, r2);
        
        r1.setDefaultItemLabelFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
        assertNotEquals(r1, r2);
        r2.setDefaultItemLabelFont(new Font(Font.MONOSPACED, Font.BOLD, 11));
        assertEquals(r1, r2);

        r1.setDefaultItemLabelPaint(new GradientPaint(4.0f, 4.0f, Color.BLUE, 4.0f, 4.0f, Color.RED));
        assertNotEquals(r1, r2);
        r2.setDefaultItemLabelPaint(new GradientPaint(4.0f, 4.0f, Color.BLUE, 4.0f, 4.0f, Color.RED));
        assertEquals(r1, r2);
        
        r1.setDefaultItemLabelsVisible(!r1.getDefaultItemLabelsVisible());
        assertNotEquals(r1, r2);
        r2.setDefaultItemLabelsVisible(r1.getDefaultItemLabelsVisible());
        assertEquals(r1, r2);
                
        r1.setDefaultLegendShape(new Rectangle(6, 5, 4, 3));
        assertNotEquals(r1, r2);
        r2.setDefaultLegendShape(new Rectangle(6, 5, 4, 3));
        assertEquals(r1, r2);
        
        r1.setDefaultLegendTextFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        assertNotEquals(r1, r2);
        r2.setDefaultLegendTextFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
        assertEquals(r1, r2);
        
        r1.setDefaultLegendTextPaint(Color.RED);
        assertNotEquals(r1, r2);
        r2.setDefaultLegendTextPaint(Color.RED);
        assertEquals(r1, r2);
        
        // TODO many remaining attributes
    }
    
}
