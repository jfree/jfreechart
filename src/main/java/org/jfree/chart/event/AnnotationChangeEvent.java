/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2016, by Object Refinery Limited and Contributors.
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
 * --------------------------
 * AnnotationChangeEvent.java
 * --------------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Peter Kolb (patch 2809117);
 * Contributor(s):   ;
 *
 * Changes:
 * --------
 * 20-Jun-2009 : Version 1 (PK);
 *
 */

package org.jfree.chart.event;

import org.jfree.chart.annotations.Annotation;
import org.jfree.chart.util.Args;

/**
 * An event that can be forwarded to any {@link AnnotationChangeListener} to
 * signal a change to an {@link Annotation}.
 *
 * @since 1.0.14
 */
public class AnnotationChangeEvent extends ChartChangeEvent {

    /** The annotation that generated the event. */
    private Annotation annotation;

    /**
     * Creates a new {@code AnnotationChangeEvent} instance.
     *
     * @param source  the event source.
     * @param annotation  the annotation that triggered the event
     *     ({@code null} not permitted).
     *
     * @since 1.0.14
     */
    public AnnotationChangeEvent(Object source, Annotation annotation) {
        super(source);
        Args.nullNotPermitted(annotation, "annotation");
        this.annotation = annotation;
    }

    /**
     * Returns the annotation that triggered the event.
     *
     * @return The annotation that triggered the event (never {@code null}).
     *
     * @since 1.0.14
     */
    public Annotation getAnnotation() {
        return this.annotation;
    }

}
