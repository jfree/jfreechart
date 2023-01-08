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
 * --------------------------
 * XYDataRangeAnnotation.java
 * --------------------------
 * (C) Copyright 2021-present, by Yuri Blankenstein and Contributors.
 *
 * Original Author:  Yuri Blankenstein (for ESI TNO);
 *
 */
package org.jfree.chart.annotations;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;

/**
 * This annotation can be put on an {@link XYPlot} to ensure a visible data
 * range. The range values should be specified w.r.t. to the first (index 0)
 * domain or range axis.
 *
 * @see XYPlot#getDataRange(ValueAxis)
 * @see XYPlot#getDomainAxis()
 * @see XYPlot#getRangeAxis()
 */
public class XYDataRangeAnnotation extends AbstractXYAnnotation implements XYAnnotationBoundsInfo {
	private static final long serialVersionUID = 2058170262687146829L;

	private final Range minimumDomainRange;
	private final Range minimumRangeRange;
	
    /**
     * Creates a new instance.
     * 
     * @param minimumDomainRange the range to ensure on the domain axis
     *                           ({@code null} permitted).
     * @param minimumRangeRange  the range to ensure on the range axis
     *                           ({@code null} permitted).
     */
	public XYDataRangeAnnotation(Range minimumDomainRange, Range minimumRangeRange) {
		this.minimumDomainRange = minimumDomainRange;
		this.minimumRangeRange = minimumRangeRange;
	}
	
	@Override
	public boolean getIncludeInDataBounds() {
		return true;
	}

	@Override
	public Range getXRange() {
		return minimumDomainRange;
	}

	@Override
	public Range getYRange() {
		return minimumRangeRange;
	}

	@Override
	public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis,
			int rendererIndex, PlotRenderingInfo info) {
		// Nothing to do here
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((minimumDomainRange == null) ? 0 : minimumDomainRange.hashCode());
		result = prime * result + ((minimumRangeRange == null) ? 0 : minimumRangeRange.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		XYDataRangeAnnotation other = (XYDataRangeAnnotation) obj;
		if (minimumDomainRange == null) {
			if (other.minimumDomainRange != null)
				return false;
		} else if (!minimumDomainRange.equals(other.minimumDomainRange))
			return false;
		if (minimumRangeRange == null) {
			if (other.minimumRangeRange != null)
				return false;
		} else if (!minimumRangeRange.equals(other.minimumRangeRange))
			return false;
		return true;
	}
}