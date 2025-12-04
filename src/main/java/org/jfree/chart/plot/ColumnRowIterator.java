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
 */

package org.jfree.chart.plot;

import org.jfree.chart.api.SortOrder;

/**
 * Strategy interface for iterating over column and row indices in different
 * orders. This eliminates code duplication from nested loops with different
 * SortOrder combinations.
 *
 * @since 2.1.0
 */
@FunctionalInterface
public interface ColumnRowIterator {

    /**
     * Iterates over columns and rows in the specified order, calling the
     * provided consumer for each (row, column) pair.
     *
     * @param columnCount the number of columns.
     * @param rowCount the number of rows.
     * @param consumer the consumer to process each (row, column) pair.
     */
    void iterate(int columnCount, int rowCount, RowColumnConsumer consumer);

    /**
     * Returns a ColumnRowIterator for the given sort orders.
     *
     * @param columnOrder the column sort order ({@code null} not permitted).
     * @param rowOrder the row sort order ({@code null} not permitted).
     *
     * @return A ColumnRowIterator.
     */
    static ColumnRowIterator forOrders(SortOrder columnOrder, SortOrder rowOrder) {
        boolean columnAscending = columnOrder == SortOrder.ASCENDING;
        boolean rowAscending = rowOrder == SortOrder.ASCENDING;

        if (columnAscending && rowAscending) {
            return (colCount, rowCount, consumer) -> {
                for (int c = 0; c < colCount; c++) {
                    for (int r = 0; r < rowCount; r++) {
                        consumer.accept(r, c);
                    }
                }
            };
        } else if (columnAscending && !rowAscending) {
            return (colCount, rowCount, consumer) -> {
                for (int c = 0; c < colCount; c++) {
                    for (int r = rowCount - 1; r >= 0; r--) {
                        consumer.accept(r, c);
                    }
                }
            };
        } else if (!columnAscending && rowAscending) {
            return (colCount, rowCount, consumer) -> {
                for (int c = colCount - 1; c >= 0; c--) {
                    for (int r = 0; r < rowCount; r++) {
                        consumer.accept(r, c);
                    }
                }
            };
        } else {
            // columnDescending && rowDescending
            return (colCount, rowCount, consumer) -> {
                for (int c = colCount - 1; c >= 0; c--) {
                    for (int r = rowCount - 1; r >= 0; r--) {
                        consumer.accept(r, c);
                    }
                }
            };
        }
    }

    /**
     * Functional interface for consuming row and column indices.
     */
    @FunctionalInterface
    interface RowColumnConsumer {
        /**
         * Accepts a row and column index.
         *
         * @param row the row index.
         * @param column the column index.
         */
        void accept(int row, int column);
    }
}
