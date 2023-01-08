/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * -----------------------
 * DatasetChangeEvent.java
 * -----------------------
 * (C) Copyright 2000-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.general;

/**
 * A change event that encapsulates information about a change to a dataset.
 */
public class DatasetChangeEvent extends java.util.EventObject {

    /**
     * The dataset that generated the change event.
     */
    private Dataset dataset;

    /**
     * Constructs a new event.  The source is either the dataset or the
     * {@link org.jfree.chart.plot.Plot} class.  The dataset can be
     * {@code null} (in this case the source will be the
     * {@link org.jfree.chart.plot.Plot} class).
     *
     * @param source  the source of the event.
     * @param dataset  the dataset that generated the event ({@code null}
     *                 permitted).
     */
    public DatasetChangeEvent(Object source, Dataset dataset) {
        super(source);
        this.dataset = dataset;
    }

    /**
     * Returns the dataset that generated the event.  Note that the dataset
     * may be {@code null} since adding a {@code null} dataset to a
     * plot will generated a change event.
     *
     * @return The dataset (possibly {@code null}).
     */
    public Dataset getDataset() {
        return this.dataset;
    }

}
