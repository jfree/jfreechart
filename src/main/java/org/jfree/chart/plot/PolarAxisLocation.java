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
 * ----------------------
 * PolarAxisLocation.java
 * ----------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 25-Nov-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate the location of an axis on a {@link PolarPlot}.
 *
 * @since 1.0.14
 */
public final class PolarAxisLocation implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -3276922179323563410L;

    /** Axis left of north. */
    public static final PolarAxisLocation NORTH_LEFT
            = new PolarAxisLocation("PolarAxisLocation.NORTH_LEFT");

    /** Axis right of north. */
    public static final PolarAxisLocation NORTH_RIGHT
            = new PolarAxisLocation("PolarAxisLocation.NORTH_RIGHT");

    /** Axis left of south. */
    public static final PolarAxisLocation SOUTH_LEFT
            = new PolarAxisLocation("PolarAxisLocation.SOUTH_LEFT");

    /** Axis right of south. */
    public static final PolarAxisLocation SOUTH_RIGHT
            = new PolarAxisLocation("PolarAxisLocation.SOUTH_RIGHT");

    /** Axis above east. */
    public static final PolarAxisLocation EAST_ABOVE
            = new PolarAxisLocation("PolarAxisLocation.EAST_ABOVE");

    /** Axis below east. */
    public static final PolarAxisLocation EAST_BELOW
            = new PolarAxisLocation("PolarAxisLocation.EAST_BELOW");

    /** Axis above west. */
    public static final PolarAxisLocation WEST_ABOVE
            = new PolarAxisLocation("PolarAxisLocation.WEST_ABOVE");

    /** Axis below west. */
    public static final PolarAxisLocation WEST_BELOW
            = new PolarAxisLocation("PolarAxisLocation.WEST_BELOW");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private PolarAxisLocation(String name) {
        this.name = name;
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
     * Returns {@code true} if this object is equal to the specified
     * object, and {@code false} otherwise.
     *
     * @param obj  the other object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PolarAxisLocation)) {
            return false;
        }
        PolarAxisLocation location = (PolarAxisLocation) obj;
        if (!this.name.equals(location.toString())) {
            return false;
        }
        return true;
    }

    /**
     * Ensures that serialization returns the unique instances.
     *
     * @return The object.
     *
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        if (this.equals(PolarAxisLocation.NORTH_RIGHT)) {
            return PolarAxisLocation.NORTH_RIGHT;
        }
        else if (this.equals(PolarAxisLocation.NORTH_LEFT)) {
            return PolarAxisLocation.NORTH_LEFT;
        }
        else if (this.equals(PolarAxisLocation.SOUTH_RIGHT)) {
            return PolarAxisLocation.SOUTH_RIGHT;
        }
        else if (this.equals(PolarAxisLocation.SOUTH_LEFT)) {
            return PolarAxisLocation.SOUTH_LEFT;
        }
        else if (this.equals(PolarAxisLocation.EAST_ABOVE)) {
            return PolarAxisLocation.EAST_ABOVE;
        }
        else if (this.equals(PolarAxisLocation.EAST_BELOW)) {
            return PolarAxisLocation.EAST_BELOW;
        }
        else if (this.equals(PolarAxisLocation.WEST_ABOVE)) {
            return PolarAxisLocation.WEST_ABOVE;
        }
        else if (this.equals(PolarAxisLocation.WEST_BELOW)) {
            return PolarAxisLocation.WEST_BELOW;
        }
        return null;
    }

}
