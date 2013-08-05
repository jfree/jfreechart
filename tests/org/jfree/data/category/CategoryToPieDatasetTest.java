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
 * ------------------------------
 * CategoryToPieDatasetTests.java
 * ------------------------------
 * (C) Copyright 2006-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 26-Jul-2006 : Version 1 (DG);
 * 01-Aug-2006 : Added testGetIndex() method (DG);
 *
 */

package org.jfree.data.category;

import org.jfree.chart.TestUtilities;

import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.TableOrder;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Tests for the {@link CategoryToPieDataset} class.
 */
public class CategoryToPieDatasetTest {

    /**
     * Some tests for the constructor.
     */
    @Test
    public void testConstructor() {
        // try a null source
        CategoryToPieDataset p1 = new CategoryToPieDataset(null,
                TableOrder.BY_COLUMN, 0);
        assertNull(p1.getUnderlyingDataset());
        assertEquals(p1.getItemCount(), 0);
        assertTrue(p1.getKeys().isEmpty());
        assertNull(p1.getValue("R1"));
    }

    /**
     * Some checks for the getValue() method.
     */
    @Test
    public void testGetValue() {
        DefaultCategoryDataset underlying = new DefaultCategoryDataset();
        underlying.addValue(1.1, "R1", "C1");
        underlying.addValue(2.2, "R1", "C2");
        CategoryToPieDataset d1 = new CategoryToPieDataset(underlying,
                TableOrder.BY_ROW, 0);
        assertEquals(d1.getValue("C1"), new Double(1.1));
        assertEquals(d1.getValue("C2"), new Double(2.2));

        // check negative index throws exception
        try {
            /* Number n = */ d1.getValue(-1);
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }

        // check index == getItemCount() throws exception
        try {
            /* Number n = */ d1.getValue(d1.getItemCount());
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }

        // test null source
        CategoryToPieDataset p1 = new CategoryToPieDataset(null,
                TableOrder.BY_COLUMN, 0);
        try {
            /* Number n = */ p1.getValue(0);
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }
    }

    /**
     * Some checks for the getKey(int) method.
     */
    @Test
    public void testGetKey() {
        DefaultCategoryDataset underlying = new DefaultCategoryDataset();
        underlying.addValue(1.1, "R1", "C1");
        underlying.addValue(2.2, "R1", "C2");
        CategoryToPieDataset d1 = new CategoryToPieDataset(underlying,
                TableOrder.BY_ROW, 0);
        assertEquals(d1.getKey(0), "C1");
        assertEquals(d1.getKey(1), "C2");

        // check negative index throws exception
        try {
            /* Number n = */ d1.getKey(-1);
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }

        // check index == getItemCount() throws exception
        try {
            /* Number n = */ d1.getKey(d1.getItemCount());
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }

        // test null source
        CategoryToPieDataset p1 = new CategoryToPieDataset(null,
                TableOrder.BY_COLUMN, 0);
        try {
            /* Number n = */ p1.getKey(0);
            fail("Expected IndexOutOfBoundsException.");
        }
        catch (IndexOutOfBoundsException e) {
            // this is expected
        }
    }

    /**
     * Some checks for the getIndex() method.
     */
    @Test
    public void testGetIndex() {
        DefaultCategoryDataset underlying = new DefaultCategoryDataset();
        underlying.addValue(1.1, "R1", "C1");
        underlying.addValue(2.2, "R1", "C2");
        CategoryToPieDataset d1 = new CategoryToPieDataset(underlying,
                TableOrder.BY_ROW, 0);
        assertEquals(0, d1.getIndex("C1"));
        assertEquals(1, d1.getIndex("C2"));
        assertEquals(-1, d1.getIndex("XX"));

        // try null
        boolean pass = false;
        try {
            d1.getIndex(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * For datasets, the equals() method just checks keys and values.
     */
    @Test
    public void testEquals() {
        DefaultCategoryDataset underlying = new DefaultCategoryDataset();
        underlying.addValue(1.1, "R1", "C1");
        underlying.addValue(2.2, "R1", "C2");
        CategoryToPieDataset d1 = new CategoryToPieDataset(underlying,
                TableOrder.BY_COLUMN, 1);
        DefaultPieDataset d2 = new DefaultPieDataset();
        d2.setValue("R1", 2.2);
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        DefaultCategoryDataset underlying = new DefaultCategoryDataset();
        underlying.addValue(1.1, "R1", "C1");
        underlying.addValue(2.2, "R1", "C2");
        CategoryToPieDataset d1 = new CategoryToPieDataset(underlying,
                TableOrder.BY_COLUMN, 1);
        CategoryToPieDataset d2 = (CategoryToPieDataset) 
                TestUtilities.serialised(d1);
        assertEquals(d1, d2);

        // regular equality for the datasets doesn't check the fields, just
        // the data values...so let's check some more things...
        assertEquals(d1.getUnderlyingDataset(), d2.getUnderlyingDataset());
        assertEquals(d1.getExtractType(), d2.getExtractType());
        assertEquals(d1.getExtractIndex(), d2.getExtractIndex());
    }

}
