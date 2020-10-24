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
 * -------------------------
 * NumberTickUnitSource.java
 * -------------------------
 * (C) Copyright 2014-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-Mar-2016 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.jfree.chart.util.ObjectUtils;

/**
 * A tick unit source implementation that returns NumberTickUnit instances 
 * that are multiples of 1, 2 or 5 times some power of 10.
 * 
 * @since 1.0.18
 */
public class NumberTickUnitSource implements TickUnitSource, Serializable {

    private boolean integers;
    
    private int power = 0;
    
    private int factor = 1;
    
    /** The number formatter to use (an override, it can be null). */
    private NumberFormat formatter;

    /**
     * Creates a new instance.
     */
    public NumberTickUnitSource() {
        this(false);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param integers  show integers only. 
     */
    public NumberTickUnitSource(boolean integers) {
        this(integers, null);
    }
    
    /**
     * Creates a new instance.
     * 
     * @param integers  show integers only?
     * @param formatter  a formatter for the axis tick labels ({@code null} 
     *         permitted).
     */
    public NumberTickUnitSource(boolean integers, NumberFormat formatter) {
        this.integers = integers;
        this.formatter = formatter;
        this.power = 0;
        this.factor = 1;
    }
    
    @Override
    public TickUnit getLargerTickUnit(TickUnit unit) {
        TickUnit t = getCeilingTickUnit(unit);
        if (t.equals(unit)) {
            next();
            t = new NumberTickUnit(getTickSize(), getTickLabelFormat(), 
                    getMinorTickCount());
        }
        return t; 
    }

    @Override
    public TickUnit getCeilingTickUnit(TickUnit unit) {
        return getCeilingTickUnit(unit.getSize());
    }

    @Override
    public TickUnit getCeilingTickUnit(double size) {
        if (Double.isInfinite(size)) {
            throw new IllegalArgumentException("Must be finite.");
        }
        this.power = (int) Math.ceil(Math.log10(size));
        if (this.integers) {
            power = Math.max(this.power, 0);
        }
        this.factor = 1;
        boolean done = false;
        // step down in size until the current size is too small or there are 
        // no more units
        while (!done) {
            done = !previous();
            if (getTickSize() < size) {
                next();
                done = true;
            }
        }
        return new NumberTickUnit(getTickSize(), getTickLabelFormat(), 
                getMinorTickCount());
    }
    
    private boolean next() {
        if (factor == 1) {
            factor = 2;
            return true;
        }
        if (factor == 2) {
            factor = 5;
            return true;
        }
        if (factor == 5) {
            if (power == 300) {
                return false;
            }
            power++;
            factor = 1;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    private boolean previous() {
        if (factor == 1) {
            if (this.integers && power == 0 || power == -300) {
                return false;
            }
            factor = 5;
            power--;
            return true;
        } 
        if (factor == 2) {
            factor = 1;
            return true;
        }
        if (factor == 5) {
            factor = 2;
            return true;
        } 
        throw new IllegalStateException("We should never get here.");
    }

    private double getTickSize() {
        return this.factor * Math.pow(10.0, this.power);
    }
    
    private DecimalFormat dfNeg4 = new DecimalFormat("0.0000");
    private DecimalFormat dfNeg3 = new DecimalFormat("0.000");
    private DecimalFormat dfNeg2 = new DecimalFormat("0.00");
    private DecimalFormat dfNeg1 = new DecimalFormat("0.0");
    private DecimalFormat df0 = new DecimalFormat("#,##0");
    private DecimalFormat df = new DecimalFormat("#.######E0");
    
    private NumberFormat getTickLabelFormat() {
        if (this.formatter != null) {
            return this.formatter;
        }
        if (power == -4) {
            return dfNeg4;
        }
        if (power == -3) {
            return dfNeg3;
        }
        if (power == -2) {
            return dfNeg2;
        }
        if (power == -1) {
            return dfNeg1;
        }
        if (power >= 0 && power <= 6) {
            return df0;
        }
        return df;
    }
    
    private int getMinorTickCount() {
        if (factor == 1) {
            return 10;
        } else if (factor == 5) {
            return 5;
        }
        return 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof NumberTickUnitSource)) {
            return false;
        }
        NumberTickUnitSource that = (NumberTickUnitSource) obj;
        if (this.integers != that.integers) {
            return false;
        }
        if (!ObjectUtils.equal(this.formatter, that.formatter)) {
            return false;
        }
        if (this.power != that.power) {
            return false;
        }
        if (this.factor != that.factor) {
            return false;
        }
        return true;
    }
}
