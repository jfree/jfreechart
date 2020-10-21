/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * DataUtilsTest.java
 * ------------------
 * (C) Copyright 2005-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Mar-2005 : Version 1 (DG);
 * 28-Jan-2009 : Added tests for equal(double[][], double[][]) method (DG);
 * 28-Jan-2009 : Added tests for clone(double[][]) (DG);
 * 04-Feb-2009 : Added tests for new calculateColumnTotal/RowTotal methods (DG);
 *
 */

package org.jfree.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

/**
 * Some tests for the {@link DataUtils} class.
 */
public class DataUtilsTest {

    /**
     * Tests the createNumberArray2D() method.
     */
    @Test
    public void testCreateNumberArray2D() {
        double[][] d = new double[2][];
        d[0] = new double[] {1.1, 2.2, 3.3, 4.4};
        d[1] = new double[] {1.1, 2.2, 3.3, 4.4, 5.5};
        Number[][] n = DataUtils.createNumberArray2D(d);
        assertEquals(2, n.length);
        assertEquals(4, n[0].length);
        assertEquals(5, n[1].length);
    }

    private static final double EPSILON = 0.000000001;

    /**
     * Some checks for the calculateColumnTotal() method.
     */
    @Test
    public void testCalculateColumnTotal() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(1.0, "R0", "C0");
        table.addValue(2.0, "R0", "C1");
        table.addValue(3.0, "R1", "C0");
        table.addValue(4.0, "R1", "C1");
        assertEquals(4.0, DataUtils.calculateColumnTotal(table, 0), EPSILON);
        assertEquals(6.0, DataUtils.calculateColumnTotal(table, 1), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(2.0, DataUtils.calculateColumnTotal(table, 1), EPSILON);
    }

    /**
     * Some checks for the calculateColumnTotal() method.
     */
    @Test
    public void testCalculateColumnTotal2() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(1.0, "R0", "C0");
        table.addValue(2.0, "R0", "C1");
        table.addValue(3.0, "R1", "C0");
        table.addValue(4.0, "R1", "C1");
        assertEquals(4.0, DataUtils.calculateColumnTotal(table, 0,
                new int[] {0, 1}), EPSILON);
        assertEquals(1.0, DataUtils.calculateColumnTotal(table, 0,
                new int[] {0}), EPSILON);
        assertEquals(3.0, DataUtils.calculateColumnTotal(table, 0,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtils.calculateColumnTotal(table, 0,
                new int[] {}), EPSILON);

        assertEquals(6.0, DataUtils.calculateColumnTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(2.0, DataUtils.calculateColumnTotal(table, 1,
                new int[] {0}), EPSILON);
        assertEquals(4.0, DataUtils.calculateColumnTotal(table, 1,
                new int[] {1}), EPSILON);

        table.setValue(null, "R1", "C1");
        assertEquals(2.0, DataUtils.calculateColumnTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(0.0, DataUtils.calculateColumnTotal(table, 1,
                new int[] {1}), EPSILON);
    }

    /**
     * Some checks for the calculateRowTotal() method.
     */
    @Test
    public void testCalculateRowTotal() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(1.0, "R0", "C0");
        table.addValue(2.0, "R0", "C1");
        table.addValue(3.0, "R1", "C0");
        table.addValue(4.0, "R1", "C1");
        assertEquals(3.0, DataUtils.calculateRowTotal(table, 0), EPSILON);
        assertEquals(7.0, DataUtils.calculateRowTotal(table, 1), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(3.0, DataUtils.calculateRowTotal(table, 1), EPSILON);
    }

    /**
     * Some checks for the calculateRowTotal() method.
     */
    @Test
    public void testCalculateRowTotal2() {
        DefaultKeyedValues2D table = new DefaultKeyedValues2D();
        table.addValue(1.0, "R0", "C0");
        table.addValue(2.0, "R0", "C1");
        table.addValue(3.0, "R1", "C0");
        table.addValue(4.0, "R1", "C1");
        assertEquals(3.0, DataUtils.calculateRowTotal(table, 0,
                new int[] {0, 1}), EPSILON);
        assertEquals(1.0, DataUtils.calculateRowTotal(table, 0,
                new int[] {0}), EPSILON);
        assertEquals(2.0, DataUtils.calculateRowTotal(table, 0,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtils.calculateRowTotal(table, 0,
                new int[] {}), EPSILON);

        assertEquals(7.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(3.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {0}), EPSILON);
        assertEquals(4.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {1}), EPSILON);
        assertEquals(0.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {}), EPSILON);
        table.setValue(null, "R1", "C1");
        assertEquals(3.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {0, 1}), EPSILON);
        assertEquals(0.0, DataUtils.calculateRowTotal(table, 1,
                new int[] {1}), EPSILON);
    }

    /**
     * Some tests for the equal(double[][], double[][]) method.
     */
    @Test
    public void testEqual() {
        assertTrue(DataUtils.equal(null, null));
        
        double[][] a = new double[5][];
        double[][] b = new double[5][];
        assertTrue(DataUtils.equal(a, b));

        a = new double[4][];
        assertFalse(DataUtils.equal(a, b));
        b = new double[4][];
        assertTrue(DataUtils.equal(a, b));

        a[0] = new double[6];
        assertFalse(DataUtils.equal(a, b));
        b[0] = new double[6];
        assertTrue(DataUtils.equal(a, b));

        a[0][0] = 1.0;
        assertFalse(DataUtils.equal(a, b));
        b[0][0] = 1.0;
        assertTrue(DataUtils.equal(a, b));

        a[0][1] = Double.NaN;
        assertFalse(DataUtils.equal(a, b));
        b[0][1] = Double.NaN;
        assertTrue(DataUtils.equal(a, b));

        a[0][2] = Double.NEGATIVE_INFINITY;
        assertFalse(DataUtils.equal(a, b));
        b[0][2] = Double.NEGATIVE_INFINITY;
        assertTrue(DataUtils.equal(a, b));

        a[0][3] = Double.POSITIVE_INFINITY;
        assertFalse(DataUtils.equal(a, b));
        b[0][3] = Double.POSITIVE_INFINITY;
        assertTrue(DataUtils.equal(a, b));

        a[0][4] = Double.POSITIVE_INFINITY;
        assertFalse(DataUtils.equal(a, b));
        b[0][4] = Double.NEGATIVE_INFINITY;
        assertFalse(DataUtils.equal(a, b));
        b[0][4] = Double.POSITIVE_INFINITY;
        assertTrue(DataUtils.equal(a, b));
    }

    /**
     * Some tests for the clone() method.
     */
    @Test
    public void testClone() {
        double[][] a = new double[1][];
        double[][] b = DataUtils.clone(a);
        assertTrue(DataUtils.equal(a, b));
        a[0] = new double[] { 3.0, 4.0 };
        assertFalse(DataUtils.equal(a, b));
        b[0] = new double[] { 3.0, 4.0 };
        assertTrue(DataUtils.equal(a, b));

        a = new double[2][3];
        a[0][0] = 1.23;
        a[1][1] = Double.NaN;
        b = DataUtils.clone(a);
        assertTrue(DataUtils.equal(a, b));

        a[0][0] = 99.9;
        assertFalse(DataUtils.equal(a, b));
        b[0][0] = 99.9;
        assertTrue(DataUtils.equal(a, b));
    }

}
