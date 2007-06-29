/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * KeyedObject2D.java
 * ------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: KeyedObjects2D.java,v 1.6.2.1 2005/10/25 21:29:13 mungady Exp $
 *
 * Changes
 * -------
 * 05-Feb-2003 : Version 1 (DG);
 * 01-Mar-2004 : Added equals() and clone() methods and implemented 
 *               Serializable (DG);
 *
 */

package org.jfree.data;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * A data structure that stores zero, one or many objects, where each object is
 * associated with two keys (a 'row' key and a 'column' key).
 */
public class KeyedObjects2D implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -1015873563138522374L;
    
    /** The row keys. */
    private List rowKeys;

    /** The column keys. */
    private List columnKeys;

    /** The row data. */
    private List rows;

    /**
     * Creates a new instance (initially empty).
     */
    public KeyedObjects2D() {
        this.rowKeys = new java.util.ArrayList();
        this.columnKeys = new java.util.ArrayList();
        this.rows = new java.util.ArrayList();
    }

    /**
     * Returns the row count.
     *
     * @return The row count.
     */
    public int getRowCount() {
        return this.rowKeys.size();
    }

    /**
     * Returns the column count.
     *
     * @return The column count.
     */
    public int getColumnCount() {
        return this.columnKeys.size();
    }

    /**
     * Returns the object for a given row and column.
     *
     * @param row  the row index.
     * @param column  the column index.
     *
     * @return The object.
     */
    public Object getObject(int row, int column) {

        Object result = null;
        KeyedObjects rowData = (KeyedObjects) this.rows.get(row);
        if (rowData != null) {
            Comparable columnKey = (Comparable) this.columnKeys.get(column);
            if (columnKey != null) {
                result = rowData.getObject(columnKey);
            }
        }
        return result;

    }

    /**
     * Returns the key for a given row.
     *
     * @param row  the row index (zero based).
     *
     * @return The row index.
     */
    public Comparable getRowKey(int row) {
        return (Comparable) this.rowKeys.get(row);
    }

    /**
     * Returns the row index for a given key.
     *
     * @param key  the key.
     *
     * @return The row index.
     */
    public int getRowIndex(Comparable key) {
        return this.rowKeys.indexOf(key);
    }

    /**
     * Returns the row keys.
     *
     * @return The row keys (never <code>null</code>).
     */
    public List getRowKeys() {
        return Collections.unmodifiableList(this.rowKeys);
    }

    /**
     * Returns the key for a given column.
     *
     * @param column  the column.
     *
     * @return The key.
     */
    public Comparable getColumnKey(int column) {
        return (Comparable) this.columnKeys.get(column);
    }

    /**
     * Returns the column index for a given key.
     *
     * @param key  the key.
     *
     * @return The column index.
     */
    public int getColumnIndex(Comparable key) {
        return this.columnKeys.indexOf(key);
    }

    /**
     * Returns the column keys.
     *
     * @return The column keys (never <code>null</code>).
     */
    public List getColumnKeys() {
        return Collections.unmodifiableList(this.columnKeys);
    }

    /**
     * Returns the object for the given row and column keys.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The object.
     */
    public Object getObject(Comparable rowKey, Comparable columnKey) {

        Object result = null;
        int row = this.rowKeys.indexOf(rowKey);
        if (row >= 0) {
            KeyedObjects rowData = (KeyedObjects) this.rows.get(row);
            result = rowData.getObject(columnKey);
        }
        return result;

    }

    /**
     * Adds an object to the table.  Performs the same function as setObject().
     *
     * @param object  the object.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void addObject(Object object, 
                          Comparable rowKey, 
                          Comparable columnKey) {
        setObject(object, rowKey, columnKey);
    }

    /**
     * Adds or updates an object.
     *
     * @param object  the object.
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void setObject(Object object, 
                          Comparable rowKey, 
                          Comparable columnKey) {

        KeyedObjects row;
        int rowIndex = this.rowKeys.indexOf(rowKey);
        if (rowIndex >= 0) {
            row = (KeyedObjects) this.rows.get(rowIndex);
        }
        else {
            this.rowKeys.add(rowKey);
            row = new KeyedObjects();
            this.rows.add(row);
        }
        row.setObject(columnKey, object);
        int columnIndex = this.columnKeys.indexOf(columnKey);
        if (columnIndex < 0) {
            this.columnKeys.add(columnKey);
        }

    }

    /**
     * Removes an object.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     */
    public void removeObject(Comparable rowKey, Comparable columnKey) {
        setObject(null, rowKey, columnKey);
        // actually, a null value is different to a value that doesn't exist 
        // at all, need to fix this code.
    }

    /**
     * Removes a row.
     *
     * @param rowIndex  the row index.
     */
    public void removeRow(int rowIndex) {
        this.rowKeys.remove(rowIndex);
        this.rows.remove(rowIndex);
    }

    /**
     * Removes a row.
     *
     * @param rowKey  the row key.
     */
    public void removeRow(Comparable rowKey) {
        removeRow(getRowIndex(rowKey));
    }

    /**
     * Removes a column.
     *
     * @param columnIndex  the column index.
     */
    public void removeColumn(int columnIndex) {
        Comparable columnKey = getColumnKey(columnIndex);
        removeColumn(columnKey);
    }

    /**
     * Removes a column.
     *
     * @param columnKey  the column key.
     */
    public void removeColumn(Comparable columnKey) {
        Iterator iterator = this.rows.iterator();
        while (iterator.hasNext()) {
            KeyedObjects rowData = (KeyedObjects) iterator.next();
            rowData.removeValue(columnKey);
        }
        this.columnKeys.remove(columnKey);
    }

    /**
     * Tests this object for equality with an arbitrary object.
     *
     * @param obj  the object to test (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }
        
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof KeyedObjects2D)) {
            return false;
        }
        
        KeyedObjects2D ko2D = (KeyedObjects2D) obj;
        if (!getRowKeys().equals(ko2D.getRowKeys())) {
            return false;
        }
        if (!getColumnKeys().equals(ko2D.getColumnKeys())) {
            return false;
        }
        int rowCount = getRowCount();
        if (rowCount != ko2D.getRowCount()) {
            return false;
        }

        int colCount = getColumnCount();
        if (colCount != ko2D.getColumnCount()) {
            return false;
        }

        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                Object v1 = getObject(r, c);
                Object v2 = ko2D.getObject(r, c);
                if (v1 == null) {
                    if (v2 != null) {
                        return false;
                    }
                }
                else {
                    if (!v1.equals(v2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns a hashcode for this object.
     * 
     * @return A hashcode.
     */
    public int hashCode() {
        int result;
        result = this.rowKeys.hashCode();
        result = 29 * result + this.columnKeys.hashCode();
        result = 29 * result + this.rows.hashCode();
        return result;
    }

    /**
     * Returns a clone.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException  this class will not throw this 
     *         exception, but subclasses (if any) might.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
