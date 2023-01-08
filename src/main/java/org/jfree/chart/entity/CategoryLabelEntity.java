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
 * ------------------------
 * CategoryLabelEntity.java
 * ------------------------
 * (C) Copyright 2006-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.util.Objects;

import org.jfree.chart.HashUtils;
import org.jfree.chart.axis.CategoryAxis;

/**
 * An entity to represent the labels on a {@link CategoryAxis}.
 */
public class CategoryLabelEntity extends TickLabelEntity {

    /** The category key. */
    private final Comparable key;

    /**
     * Creates a new entity.
     *
     * @param key  the category key ({@code null} not permitted).
     * @param area  the hotspot.
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text.
     */
    public CategoryLabelEntity(Comparable key, Shape area,
            String toolTipText, String urlText) {
        super(area, toolTipText, urlText);
        Objects.requireNonNull(key);
        this.key = key;
    }

    /**
     * Returns the category key.
     *
     * @return The category key.
     */
    public Comparable getKey() {
        return this.key;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
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
        if (!(obj instanceof CategoryLabelEntity)) {
            return false;
        }
        CategoryLabelEntity that = (CategoryLabelEntity) obj;

        // fix the "equals not symmetric" problem
        if (!that.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(this.key, that.key)) {
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
        // Solves Problem: equals not symmetric
        return (other instanceof CategoryLabelEntity);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode(); // equals calls superclass, hashCode must also
        result = HashUtils.hashCode(result, this.key);
        return result;
    }

    /**
     * Returns a string representation of this entity.  This is primarily
     * useful for debugging.
     *
     * @return A string representation of this entity.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CategoryLabelEntity: ");
        sb.append("category=");
        sb.append(this.key);
        sb.append(", tooltip=").append(getToolTipText());
        sb.append(", url=").append(getURLText());
        return sb.toString();
    }
}
