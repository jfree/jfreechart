/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2014, by Object Refinery Limited and Contributors.
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
 * -------------------
 * TickUnitSource.java
 * -------------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 23-Sep-2003 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

/**
 * An interface used by the {@link DateAxis} and {@link NumberAxis} classes to
 * obtain a suitable {@link TickUnit}.
 */
public interface TickUnitSource {

    /**
     * Returns the smallest tick unit available in the source that is larger
     * than <code>unit</code> or, if there is no larger unit, returns 
     * <code>unit</code>.
     *
     * @param unit  the unit (<code>null</code> not permitted).
     *
     * @return A tick unit that is larger than the supplied unit.
     */
    public TickUnit getLargerTickUnit(TickUnit unit);

    /**
     * Returns the tick unit in the collection that is greater than or equal
     * to (in size) the specified unit.
     *
     * @param unit  the unit.
     *
     * @return A unit from the collection.
     */
    public TickUnit getCeilingTickUnit(TickUnit unit);

    /**
     * Returns the smallest tick unit available in the source that is greater 
     * than or equal to the specified size.  If there is no such tick unit,
     * the method should return the largest available tick in the source.
     *
     * @param size  the size.
     *
     * @return A unit from the collection (never <code>null</code>).
     */
    public TickUnit getCeilingTickUnit(double size);

}
