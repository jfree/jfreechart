/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * ---------------
 * Annotation.java
 * ---------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Peter Kolb (see patch 2809117);
 * Contributor(s):   ;
 *
 * Changes:
 * --------
 * 20-Jun-2009 : Version 1 (PK);
 *
 */

package org.jfree.chart.annotations;

import org.jfree.chart.event.AnnotationChangeListener;

/**
 * The base interface for annotations.  All annotations should support the
 * {@link AnnotationChangeEvent} mechanism by allowing listeners to register
 * and receive notification of any changes to the annotation.
 */
public interface Annotation {

    /**
     * Registers an object for notification of changes to the annotation.
     *
     * @param listener  the object to register.
     */
    public void addChangeListener(AnnotationChangeListener listener);

    /**
     * Deregisters an object for notification of changes to the annotation.
     *
     * @param listener  the object to deregister.
     */
    public void removeChangeListener(AnnotationChangeListener listener);

}
