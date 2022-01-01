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
 * -------------------
 * NumberTickUnit.java
 * -------------------
 * (C) Copyright 2001-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.axis;

import java.io.Serializable;
import java.text.NumberFormat;
import org.jfree.chart.internal.Args;

/**
 * A numerical tick unit.
 */
public class NumberTickUnit extends TickUnit implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 3849459506627654442L;

    /** A formatter for the tick unit. */
    private NumberFormat formatter;

    /**
     * Creates a new number tick unit.
     *
     * @param size  the size of the tick unit.
     */
    public NumberTickUnit(double size) {
        this(size, NumberFormat.getNumberInstance());
    }

    /**
     * Creates a new number tick unit.
     *
     * @param size  the size of the tick unit.
     * @param formatter  a number formatter for the tick unit ({@code null}
     *                   not permitted).
     */
    public NumberTickUnit(double size, NumberFormat formatter) {
        super(size);
        Args.nullNotPermitted(formatter, "formatter");
        this.formatter = formatter;
    }

    /**
     * Creates a new number tick unit.
     *
     * @param size  the size of the tick unit.
     * @param formatter  a number formatter for the tick unit ({@code null}
     *                   not permitted).
     * @param minorTickCount  the number of minor ticks.
     */
    public NumberTickUnit(double size, NumberFormat formatter,
            int minorTickCount) {
        super(size, minorTickCount);
        Args.nullNotPermitted(formatter, "formatter");
        this.formatter = formatter;
    }

    /**
     * Converts a value to a string.
     *
     * @param value  the value.
     *
     * @return The formatted string.
     */
    @Override
    public String valueToString(double value) {
        return this.formatter.format(value);
    }

    /**
     * Tests this formatter for equality with an arbitrary object.
     *
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumberTickUnit)) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        NumberTickUnit that = (NumberTickUnit) obj;
        if (!this.formatter.equals(that.formatter)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a string representing this unit.
     *
     * @return A string.
     */
    @Override
    public String toString() {
        return "[NumberTickUnit: size=" + this.valueToString(this.getSize()) 
                + ", formatter=" + this.formatter + "]";
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 29 * result + (this.formatter != null
                ? this.formatter.hashCode() : 0);
        return result;
    }

}
