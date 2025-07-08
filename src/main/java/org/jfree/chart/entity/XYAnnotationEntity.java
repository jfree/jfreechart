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
 * -----------------------
 * XYAnnotationEntity.java
 * -----------------------
 * (C) Copyright 2004-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand (equals/hashCode comply with EqualsVerifier);
 *
 */

package org.jfree.chart.entity;

import java.awt.Shape;
import java.io.Serializable;

import org.jfree.chart.annotations.XYAnnotation;

/**
 * A chart entity that represents an annotation on an
 * {@link org.jfree.chart.plot.XYPlot}.
 */
public class XYAnnotationEntity extends ChartEntity
                                implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 2340334068383660799L;

    /** The annotation */
    private transient XYAnnotation annotation;
    
    /** The renderer index. */
    private int rendererIndex;

    /**
     * Creates a new entity.
     *
     * @param hotspot  the area.
     * @param annotation  the annotation.
     * @param rendererIndex  the rendererIndex (zero-based index).
     * @param toolTipText  the tool tip text.
     * @param urlText  the URL text for HTML image maps.
     */
    public XYAnnotationEntity(Shape hotspot, XYAnnotation annotation,
            int rendererIndex, String toolTipText, String urlText) {
        super(hotspot, toolTipText, urlText);
        this.annotation = annotation;
        this.rendererIndex = rendererIndex;
    }
    
    /**
     * Returns the annotation this entity refers to.
     *
     * @return The annotation.
     */
    public XYAnnotation getAnnotation() {
        return annotation;
    }

    /**
     * Returns the renderer index.
     *
     * @return The renderer index.
     */
    public int getRendererIndex() {
        return this.rendererIndex;
    }

    /**
     * Sets the renderer index.
     *
     * @param index  the item index (zero-based).
     */
    public void setRendererIndex(int index) {
        this.rendererIndex = index;
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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof XYAnnotationEntity)) {
            return false;
        }
        XYAnnotationEntity that = (XYAnnotationEntity) obj;

        // fix the "equals not symmetric" problem
        if (!that.canEqual(this)) {
            return false;
        }
        // compare fields in this class
        if (this.rendererIndex != that.rendererIndex) {
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
        return (other instanceof XYAnnotationEntity);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode(); // equals calls superclass function, so hashCode must also
        hash = 37 * hash + this.rendererIndex;
        return hash;
    }
}
