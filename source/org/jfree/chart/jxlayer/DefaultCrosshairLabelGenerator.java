/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2009, by Object Refinery Limited and Contributors.
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
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 * -----------------------------------
 * DefaultCrosshairLabelGenerator.java
 * -----------------------------------
 * (C) Copyright 2009, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes:
 * --------
 * 13-Feb-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.jxlayer;

import java.io.Serializable;

/**
 * A default label generator.
 *
 * @since 1.0.13
 */
public class DefaultCrosshairLabelGenerator implements CrosshairLabelGenerator,
        Serializable {

    // TODO : add a number formatter and a template string
    //        and implement equals, cloning and serialization
    /**
     * Creates a new instance.
     */
    public DefaultCrosshairLabelGenerator() {
        super();
    }

    /**
     * Returns a string that can be used as the label for a crosshair.
     *
     * @param crosshair  the crosshair (<code>null</code> not permitted).
     *
     * @return The label (possibly <code>null</code>).
     */
    public String generateLabel(Crosshair crosshair) {
        return Double.toString(crosshair.getValue());
    }

    /**
     * Tests this generator for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DefaultCrosshairLabelGenerator)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 7;
    }
}
