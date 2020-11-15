/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * --------------------
 * PublicCloneable.java
 * --------------------
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributors:     -;
 */

package org.jfree.chart.util;

/**
 * An interface that exposes the clone() method.  In order to support the 
 * cloning of {@link org.jfree.chart.JFreeChart} instances, it is advisable to implement this
 * interface for custom plots, renderers and other chart components.  If
 * this interface is not implemented, cloning will still be attempted via 
 * reflection.
 */
public interface PublicCloneable extends Cloneable {
    
    /**
     * Returns a clone of the object.
     * 
     * @return A clone.
     * 
     * @throws CloneNotSupportedException if cloning is not supported for some reason.
     */
    public Object clone() throws CloneNotSupportedException;

}

