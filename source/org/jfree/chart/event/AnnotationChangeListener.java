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
 * -------------------------
 * AnnotationChangeListener.java
 * -------------------------
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

import java.util.EventListener;

import org.jfree.chart.annotations.Annotation;

/**
 * The interface that must be supported by classes that wish to receive
 * notification of changes to an {@link Annotation}.
 *
 * @since 1.0.14
 */
public interface AnnotationChangeListener extends EventListener {

    /**
     * Receives notification of an annotation change event.
     *
     * @param event  the event.
     *
     * @since 1.0.14
     */
    public void annotationChanged(AnnotationChangeEvent event);

}
