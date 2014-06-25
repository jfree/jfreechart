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
 * --------------------
 * PlotOrientation.java
 * --------------------
 * (C) Copyright 2003-2014, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 02-May-2003 : Version 1 (DG);
 * 17-Jul-2003 : Added readResolve() method (DG);
 * 21-Nov-2007 : Implemented hashCode() (DG);
 * 14-May-2014 : Added isHorizontal() and isVertical() methods (DG);
 *
 */

package org.jfree.chart.plot;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate the orientation (horizontal or vertical) of a 2D plot.
 * It is the direction of the y-axis that is the determinant (a conventional
 * plot has a vertical y-axis).
 */
public final class PlotOrientation implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -2508771828190337782L;

    /** For a plot where the range axis is horizontal. */
    public static final PlotOrientation HORIZONTAL
            = new PlotOrientation("PlotOrientation.HORIZONTAL");

    /** For a plot where the range axis is vertical. */
    public static final PlotOrientation VERTICAL
            = new PlotOrientation("PlotOrientation.VERTICAL");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private PlotOrientation(String name) {
        this.name = name;
    }

    /**
     * Returns <code>true</code> if this orientation is <code>HORIZONTAL</code>,
     * and <code>false</code> otherwise.  
     * 
     * @return A boolean.
     * 
     * @since 1.0.18
     */
    public boolean isHorizontal() {
        return this.equals(PlotOrientation.HORIZONTAL);
    }
    
    /**
     * Returns <code>true</code> if this orientation is <code>VERTICAL</code>,
     * and <code>false</code> otherwise.
     * 
     * @return A boolean.
     * 
     * @since 1.0.18
     */
    public boolean isVertical() {
        return this.equals(PlotOrientation.VERTICAL);
    }
    
    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Returns <code>true</code> if this object is equal to the specified
     * object, and <code>false</code> otherwise.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PlotOrientation)) {
            return false;
        }
        PlotOrientation orientation = (PlotOrientation) obj;
        if (!this.name.equals(orientation.toString())) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     *
     * @return The object.
     *
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        Object result = null;
        if (this.equals(PlotOrientation.HORIZONTAL)) {
            result = PlotOrientation.HORIZONTAL;
        }
        else if (this.equals(PlotOrientation.VERTICAL)) {
            result = PlotOrientation.VERTICAL;
        }
        return result;
    }

}
