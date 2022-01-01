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
 * --------------------
 * DrawingSupplier.java
 * --------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.plot;

import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;

/**
 * A supplier of {@code Paint}, {@code Stroke} and {@code Shape}
 * objects for use by plots and renderers.  By providing a central place for
 * obtaining these items, we can ensure that duplication is avoided.
 * <p>
 * To support the cloning of charts, classes that implement this interface
 * should also implement {@code PublicCloneable}.
 */
public interface DrawingSupplier {

    /**
     * Returns the next paint in a sequence maintained by the supplier.
     *
     * @return The paint (never {@code null}).
     */
    Paint getNextPaint();

    /**
     * Returns the next outline paint in a sequence maintained by the supplier.
     *
     * @return The paint (never {@code null}).
     */
    Paint getNextOutlinePaint();

    /**
     * Returns the next fill paint in a sequence maintained by the supplier.
     *
     * @return The paint (never {@code null}).
     */
    Paint getNextFillPaint();

    /**
     * Returns the next {@code Stroke} object in a sequence maintained by
     * the supplier.
     *
     * @return The stroke (never {@code null}).
     */
    Stroke getNextStroke();

    /**
     * Returns the next {@code Stroke} object in a sequence maintained by
     * the supplier.
     *
     * @return The stroke (never {@code null}).
     */
    Stroke getNextOutlineStroke();

    /**
     * Returns the next {@code Shape} object in a sequence maintained by
     * the supplier.
     *
     * @return The shape (never {@code null}).
     */
    Shape getNextShape();

}
