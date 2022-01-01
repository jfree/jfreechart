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
 * DataUtils.java
 * --------------
 * (C) Copyright 2003-2022, by David Gilbert and contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Peter Kolb (patch 2511330);
 *
 */

package org.jfree.data;

import java.util.Arrays;
import org.jfree.chart.internal.Args;
import org.jfree.data.general.DatasetUtils;

/**
 * Utility methods for use with some of the data classes (but not the datasets,
 * see {@link DatasetUtils}).
 */
public abstract class DataUtils {

    /**
     * Tests two arrays for equality.  To be considered equal, the arrays must
     * have exactly the same dimensions, and the values in each array must also
     * match (two values that qre both NaN or both INF are considered equal
     * in this test).
     *
     * @param a  the first array ({@code null} permitted).
     * @param b  the second array ({@code null} permitted).
     *
     * @return A boolean.
     */
    public static boolean equal(double[][] a, double[][] b) {
        if (a == null) {
            return (b == null);
        }
        if (b == null) {
            return false;  // already know 'a' isn't null
        }
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!Arrays.equals(a[i], b[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a clone of the specified array.
     *
     * @param source  the source array ({@code null} not permitted).
     *
     * @return A clone of the array.
     */
    public static double[][] clone(double[][] source) {
        Args.nullNotPermitted(source, "source");
        double[][] clone = new double[source.length][];
        for (int i = 0; i < source.length; i++) {
            if (source[i] != null) {
                double[] row = new double[source[i].length];
                System.arraycopy(source[i], 0, row, 0, source[i].length);
                clone[i] = row;
            }
        }
        return clone;
    }

    /**
     * Returns the total of the values in one column of the supplied data
     * table.
     *
     * @param data  the table of values ({@code null} not permitted).
     * @param column  the column index (zero-based).
     *
     * @return The total of the values in the specified column.
     */
    public static double calculateColumnTotal(Values2D data, int column) {
        Args.nullNotPermitted(data, "data");
        double total = 0.0;
        int rowCount = data.getRowCount();
        for (int r = 0; r < rowCount; r++) {
            Number n = data.getValue(r, column);
            if (n != null) {
                total += n.doubleValue();
            }
        }
        return total;
    }

    /**
     * Returns the total of the values in one column of the supplied data
     * table by taking only the row numbers in the array into account.
     *
     * @param data  the table of values ({@code null} not permitted).
     * @param column  the column index (zero-based).
     * @param validRows the array with valid rows (zero-based).
     *
     * @return The total of the valid values in the specified column.
     */
    public static double calculateColumnTotal(Values2D data, int column,
             int[] validRows) {
        Args.nullNotPermitted(data, "data");
        double total = 0.0;
        int rowCount = data.getRowCount();
        for (int row : validRows) {
            if (row < rowCount) {
                Number n = data.getValue(row, column);
                if (n != null) {
                    total += n.doubleValue();
                }
            }
        }
        return total;
    }

    /**
     * Returns the total of the values in one row of the supplied data
     * table.
     *
     * @param data  the table of values ({@code null} not permitted).
     * @param row  the row index (zero-based).
     *
     * @return The total of the values in the specified row.
     */
    public static double calculateRowTotal(Values2D data, int row) {
        Args.nullNotPermitted(data, "data");
        double total = 0.0;
        int columnCount = data.getColumnCount();
        for (int c = 0; c < columnCount; c++) {
            Number n = data.getValue(row, c);
            if (n != null) {
                total += n.doubleValue();
            }
        }
        return total;
    }

    /**
     * Returns the total of the values in one row of the supplied data
     * table by taking only the column numbers in the array into account.
     *
     * @param data  the table of values ({@code null} not permitted).
     * @param row  the row index (zero-based).
     * @param validCols the array with valid cols (zero-based).
     *
     * @return The total of the valid values in the specified row.
     */
    public static double calculateRowTotal(Values2D data, int row,
             int[] validCols) {
        Args.nullNotPermitted(data, "data");
        double total = 0.0;
        int colCount = data.getColumnCount();
        for (int col : validCols) {
            if (col < colCount) {
                Number n = data.getValue(row, col);
                if (n != null) {
                    total += n.doubleValue();
                }
            }
        }
        return total;
    }

    /**
     * Constructs an array of {@code Number} objects from an array of
     * {@code double} primitives.
     *
     * @param data  the data ({@code null} not permitted).
     *
     * @return An array of {@code double}.
     */
    public static Number[] createNumberArray(double[] data) {
        Args.nullNotPermitted(data, "data");
        Number[] result = new Number[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = data[i];
        }
        return result;
    }

    /**
     * Constructs an array of arrays of {@code Number} objects from a
     * corresponding structure containing {@code double} primitives.
     *
     * @param data  the data ({@code null} not permitted).
     *
     * @return An array of {@code double}.
     */
    public static Number[][] createNumberArray2D(double[][] data) {
        Args.nullNotPermitted(data, "data");
        int l1 = data.length;
        Number[][] result = new Number[l1][];
        for (int i = 0; i < l1; i++) {
            result[i] = createNumberArray(data[i]);
        }
        return result;
    }

    /**
     * Returns a {@link KeyedValues} instance that contains the cumulative
     * percentage values for the data in another {@link KeyedValues} instance.
     * <p>
     * The percentages are values between 0.0 and 1.0 (where 1.0 = 100%).
     *
     * @param data  the data ({@code null} not permitted).
     *
     * @return The cumulative percentages.
     */
    public static <K extends Comparable<K>> KeyedValues<K> getCumulativePercentages(KeyedValues<K> data) {
        Args.nullNotPermitted(data, "data");
        DefaultKeyedValues<K> result = new DefaultKeyedValues<>();
        double total = 0.0;
        for (int i = 0; i < data.getItemCount(); i++) {
            Number v = data.getValue(i);
            if (v != null) {
                total = total + v.doubleValue();
            }
        }
        double runningTotal = 0.0;
        for (int i = 0; i < data.getItemCount(); i++) {
            Number v = data.getValue(i);
            if (v != null) {
                runningTotal = runningTotal + v.doubleValue();
            }
            result.addValue(data.getKey(i), runningTotal / total);
        }
        return result;
    }

}
