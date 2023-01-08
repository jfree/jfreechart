/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * -----------------------
 * CategoryItemEntity.java
 * -----------------------
 * (C) Copyright 2002-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Richard Atkinson;
 *                   Christian W. Zuckschwerdt;
 *                   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;
import java.util.Objects;
import org.jfree.chart.util.Args;

import org.jfree.data.category.CategoryDataset;

/**
 * A chart entity that represents one item within a category plot.
 */
public class CategoryItemEntity extends ChartEntity
        implements Cloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -8657249457902337349L;

    /** The dataset. */
    private CategoryDataset dataset;

    /**
     * The row key.
     */
    private Comparable rowKey;

    /**
     * The column key.
     */
    private Comparable columnKey;

    /**
     * Creates a new entity instance for an item in the specified dataset.
     *
     * @param area  the 'hotspot' area ({@code null} not permitted).
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text.
     * @param dataset  the dataset ({@code null} not permitted).
     * @param rowKey  the row key ({@code null} not permitted).
     * @param columnKey  the column key ({@code null} not permitted).
     */
    public CategoryItemEntity(Shape area, String toolTipText, String urlText,
            CategoryDataset dataset, Comparable rowKey, Comparable columnKey) {
        super(area, toolTipText, urlText);
        Args.nullNotPermitted(dataset, "dataset");
        this.dataset = dataset;
        this.rowKey = rowKey;
        this.columnKey = columnKey;
    }

    /**
     * Returns the dataset this entity refers to.  This can be used to
     * differentiate between items in a chart that displays more than one
     * dataset.
     *
     * @return The dataset (never {@code null}).
     *
     * @see #setDataset(CategoryDataset)
     */
    public CategoryDataset getDataset() {
        return this.dataset;
    }

    /**
     * Sets the dataset this entity refers to.
     *
     * @param dataset  the dataset ({@code null} not permitted).
     *
     * @see #getDataset()
     */
    public void setDataset(CategoryDataset dataset) {
        Args.nullNotPermitted(dataset, "dataset");
        this.dataset = dataset;
    }

    /**
     * Returns the row key.
     *
     * @return The row key (never {@code null}).
     *
     * @see #setRowKey(Comparable)
     */
    public Comparable getRowKey() {
        return this.rowKey;
    }

    /**
     * Sets the row key.
     *
     * @param rowKey  the row key ({@code null} not permitted).
     *
     * @see #getRowKey()
     */
    public void setRowKey(Comparable rowKey) {
        this.rowKey = rowKey;
    }

    /**
     * Returns the column key.
     *
     * @return The column key (never {@code null}).
     *
     * @see #setColumnKey(Comparable)
     */
    public Comparable getColumnKey() {
        return this.columnKey;
    }

    /**
     * Sets the column key.
     *
     * @param columnKey  the column key ({@code null} not permitted).
     *
     * @see #getColumnKey()
     */
    public void setColumnKey(Comparable columnKey) {
        this.columnKey = columnKey;
    }

    /**
     * Returns a string representing this object (useful for debugging
     * purposes).
     *
     * @return A string (never {@code null}).
     */
    @Override
    public String toString() {
        return "CategoryItemEntity: rowKey=" + this.rowKey
               + ", columnKey=" + this.columnKey + ", dataset=" + this.dataset;
    }

    /**
     * Tests the entity for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CategoryItemEntity)) {
            return false;
        }
        CategoryItemEntity that = (CategoryItemEntity) obj;
        if (!Objects.equals(this.rowKey, that.rowKey)) {
            return false;
        }
        if (!Objects.equals(this.columnKey, that.columnKey)) {
            return false;
        }
        if (!Objects.equals(this.dataset, that.dataset)) {
            return false;
        }
        
        // fix the "equals not symmetric" problem
        if (!that.canEqual(this)) {
            return false;
        }
        
        return super.equals(obj);
    }

    /**
     * Ensures symmetry between super/subclass implementations of equals. For
     * more detail, see http://jqno.nl/equalsverifier/manual/inheritance.
     *
     * @param other Object
     * 
     * @return true ONLY if the parameter is THIS class type
     */
    @Override
    public boolean canEqual(Object other) {
        // fix the "equals not symmetric" problem
        return (other instanceof CategoryItemEntity);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode(); // equals calls superclass, hashCode must also
        hash = 37 * hash + Objects.hashCode(this.dataset);
        hash = 37 * hash + Objects.hashCode(this.rowKey);
        hash = 37 * hash + Objects.hashCode(this.columnKey);
        return hash;
    }

}
