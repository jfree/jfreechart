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
 * -------------------
 * CombinedXYPlot.java
 * -------------------
 * (C) Copyright 2008, by Richard West and Contributors.
 *
 * Original Author:  Richard West, Advanced Micro Devices, Inc.;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes
 * -------
 * 05-May-2008 : Version 1, contributed by Richard West - see
 *               patch 1924543 (DG);
 *
 */

package org.jfree.experimental.chart.plot;

import java.util.Iterator;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;

/**
 * A specialised form of {@link CombinedDomainCategoryPlot} where the
 * subplots share not only the same x-axis, but also the same y-axis.
 */
public class CombinedXYPlot extends CombinedDomainXYPlot {

    /**
     * Creates a new instance with the specified axes.
     *
     * @param domainAxis  the x-axis.
     * @param rangeAxis  the y-axis.
     */
    public CombinedXYPlot(ValueAxis domainAxis, ValueAxis rangeAxis) {
        super(domainAxis);
        super.setGap(10.0);
        super.setRangeAxis(rangeAxis);
    }

    /**
     * Adds a new subplot with weight <code>1</code>.
     *
     * @param subplot  the subplot.
     */
    public void add(XYPlot subplot) {
        this.add(subplot, 1);
    }

    /**
     * Adds a new subplot with the specified weight.
     *
     * @param subplot  the subplot.
     * @param weight  the weight for the subplot.
     */
    public void add(XYPlot subplot, int weight) {
        super.add(subplot, weight);

        ValueAxis l_range = super.getRangeAxis();
        subplot.setRangeAxis(0, l_range, false);

        super.setRangeAxis(l_range);
        if (null == l_range) {
            return;
        }

        l_range.configure();
    }

    /**
     * Returns the bounds of the data values that will be plotted against
     * the specified axis.
     *
     * @param axis  the axis.
     *
     * @return The bounds.
     */
    public Range getDataRange(ValueAxis axis) {
        Range l_result = null;
        Iterator l_itr = getSubplots().iterator();
        while (l_itr.hasNext()) {
            XYPlot l_subplot = (XYPlot) l_itr.next();

            l_result = Range.combine(l_result, l_subplot.getDataRange(axis));
        }
        return l_result;
    }

    /**
     * Sets the range axis that is shared by all the subplots.
     *
     * @param axis  the axis.
     */
    public void setRangeAxis(ValueAxis axis) {
        Iterator l_itr = getSubplots().iterator();
        while (l_itr.hasNext()) {
            XYPlot l_subplot = (XYPlot) l_itr.next();
            l_subplot.setRangeAxis(0, axis, false);
        }

        super.setRangeAxis(axis);
        if (null == axis) {
            return;
        }

        axis.configure();
    }

}

