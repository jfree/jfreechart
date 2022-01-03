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
 * JSONUtils.java
 * --------------
 * (C) Copyright 2014-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.json;

import org.jfree.chart.internal.Args;
import org.jfree.data.KeyedValues;
import org.jfree.data.KeyedValues2D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.json.impl.JSONValue;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * A utility class that can read and write data in specific JSON formats.
 * 
 * @since 1.0.20
 */
public class JSONUtils {

    /**
     * Returns a string containing the data in JSON format.  The format is
     * an array of arrays, where each sub-array represents one data value.
     * The sub-array should contain two items, first the item key as a string
     * and second the item value as a number.  For example:
     * {@code [["Key A", 1.0], ["Key B", 2.0]]}
     * <br><br>
     * Note that this method can be used with instances of {@link PieDataset}.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return A string in JSON format. 
     */
    public static String writeKeyedValues(KeyedValues data) {
        Args.nullNotPermitted(data, "data");
        StringWriter sw = new StringWriter();
        try {
            writeKeyedValues(data, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }

    /**
     * Writes the data in JSON format to the supplied writer.
     * <br><br>
     * Note that this method can be used with instances of {@link PieDataset}.
     * 
     * @param data  the data ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    public static void writeKeyedValues(KeyedValues data, Writer writer) 
            throws IOException {
        Args.nullNotPermitted(data, "data");
        Args.nullNotPermitted(writer, "writer");
        writer.write("[");
        boolean first = true;
        for (Object o : data.getKeys()) {
            Comparable key = (Comparable) o;
            if (!first) {
                writer.write(", ");
            } else {
                first = false;
            }
            writer.write("[");
            writer.write(JSONValue.toJSONString(key.toString()));
            writer.write(", ");
            writer.write(JSONValue.toJSONString(data.getValue(key)));
            writer.write("]");
        }
        writer.write("]");
    }
    
    /**
     * Returns a string containing the data in JSON format.  The format is...
     * <br><br>
     * Note that this method can be used with instances of 
     * {@link CategoryDataset}.
     * 
     * @param data  the data ({@code null} not permitted).
     * 
     * @return A string in JSON format. 
     */
    public static String writeKeyedValues2D(KeyedValues2D data) {
        Args.nullNotPermitted(data, "data");
        StringWriter sw = new StringWriter();
        try {
            writeKeyedValues2D(data, sw);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return sw.toString();
    }

    /**
     * Writes the data in JSON format to the supplied writer.
     * <br><br>
     * Note that this method can be used with instances of 
     * {@link CategoryDataset}.
     * 
     * @param data  the data ({@code null} not permitted).
     * @param writer  the writer ({@code null} not permitted).
     * 
     * @throws IOException if there is an I/O problem.
     */
    public static void writeKeyedValues2D(KeyedValues2D data, Writer writer) 
            throws IOException {
        Args.nullNotPermitted(data, "data");
        Args.nullNotPermitted(writer, "writer");
        List<Comparable<?>> columnKeys = data.getColumnKeys();
        List<Comparable<?>> rowKeys = data.getRowKeys();
        writer.write("{");
        if (!columnKeys.isEmpty()) {
            writer.write("\"columnKeys\": [");
            boolean first = true;
            for (Comparable<?> columnKey : columnKeys) {
                if (!first) {
                    writer.write(", ");
                } else {
                    first = false;
                }
                writer.write(JSONValue.toJSONString(columnKey.toString()));
            }
            writer.write("]");
        }
        if (!rowKeys.isEmpty()) {
            writer.write(", \"rows\": [");
            boolean firstRow = true;
            for (Comparable<?> rowKey : rowKeys) {   
                if (!firstRow) {
                    writer.write(", [");
                } else {
                    writer.write("[");
                    firstRow = false;
                }
                // write the row data 
                writer.write(JSONValue.toJSONString(rowKey.toString()));
                writer.write(", [");
                boolean first = true;
                for (Comparable<?> columnKey : columnKeys) {
                    if (!first) {
                        writer.write(", ");
                    } else {
                        first = false;
                    }
                    writer.write(JSONValue.toJSONString(data.getValue(rowKey, 
                            columnKey)));
                }
                writer.write("]]");
            }
            writer.write("]");
        }
        writer.write("}");    
    }
    
}
