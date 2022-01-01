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
 * --------------------------------
 * DefaultKeyedValues2DDataset.java
 * --------------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 13-Mar-2003 : Version 1 (copied from DefaultCategoryDataset) (DG);
 * 23-Apr-2003 : Moved implementation into the DefaultCategoryDataset
 *               class (DG);
 *
 */

package org.jfree.data.general;

import java.io.Serializable;

import org.jfree.data.category.DefaultCategoryDataset;

/**
 * A default implementation of the {@link KeyedValues2DDataset} interface.
 *
 * @param <R> The type for the row (series) keys.
 * @param <C> The type for the column (item) keys.
 */
public class DefaultKeyedValues2DDataset<R extends Comparable<R>, C extends Comparable<C>> 
        extends DefaultCategoryDataset<R, C>
        implements KeyedValues2DDataset<R, C>, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 4288210771905990424L;

    // no new methods

}
