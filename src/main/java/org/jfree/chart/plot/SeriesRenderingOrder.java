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
 * SeriesRenderingOrder.java
 * --------------------------
 * (C) Copyright 2005-2022, by David Gilbert.
 *
 * Original Author:  Eric Thomas (www.isti.com);
 * Contributor(s):   David Gilbert;
 *
 * Changes:
 * --------
 * 21-Apr-2005 : Version 1 contributed by Eric Thomas (ET);
 * 21-Nov-2007 : Implemented hashCode() (DG);
 * 08-Jul-2018 : Made SeriesRenderingOrder an enum (TH);
 *
 */

package org.jfree.chart.plot;

/**
 * Defines the tokens that indicate the rendering order for series in a
 * {@link org.jfree.chart.plot.XYPlot}.
 */
public enum SeriesRenderingOrder {

    /**
     * Render series in the order 0, 1, 2, ..., N-1, where N is the number
     * of series.
     */
    FORWARD,

    /**
     * Render series in the order N-1, N-2, ..., 2, 1, 0, where N is the
     * number of series.
     */
    REVERSE

}
