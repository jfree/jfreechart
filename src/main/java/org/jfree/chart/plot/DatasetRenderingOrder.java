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
 * --------------------------
 * DatasetRenderingOrder.java
 * --------------------------
 * (C) Copyright 2003-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 02-May-2003 : Version 1 (DG);
 * 02-Jun-2004 : Changed 'STANDARD' --> 'FORWARD' (DG);
 * 21-Nov-2007 : Implemented hashCode() (DG);
 * 08-Jul-2018 : Made DatasetRenderingOrder an enum (TH);
 *
 */

package org.jfree.chart.plot;

/**
 * Defines the tokens that indicate the rendering order for datasets in a
 * {@link org.jfree.chart.plot.CategoryPlot} or an
 * {@link org.jfree.chart.plot.XYPlot}.
 */
public enum DatasetRenderingOrder {

    /**
     * Render datasets in the order 0, 1, 2, ..., N-1, where N is the number
     * of datasets.
     */
    FORWARD,

    /**
     * Render datasets in the order N-1, N-2, ..., 2, 1, 0, where N is the
     * number of datasets.
     */
    REVERSE

}
