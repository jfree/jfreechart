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
 * -------------------------
 * PolynomialFunction2D.java
 * -------------------------
 * (C) Copyright 2009-2013, by Object Refinery Limited.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * Changes:
 * --------
 * 23-Mar-2009 : Version 1, patch 2795746 (PK);
 * 28-May-2009 : Integrated in JFreeChart with modifications (DG);
 * 03-Jul-2013 : Use ParamChecks (DG);
 *
 */

package org.jfree.data.function;

import java.io.Serializable;
import java.util.Arrays;
import org.jfree.chart.HashUtilities;
import org.jfree.chart.util.ParamChecks;

/**
 * A function in the form <code>y = a0 + a1 * x + a2 * x^2 + ... + an *
 * x^n</code>.  Instances of this class are immutable.
 *
 * @since 1.0.14
 */
public class PolynomialFunction2D implements Function2D, Serializable {

    /** The coefficients. */
    private double[] coefficients;

    /**
     * Constructs a new polynomial function <code>y = a0 + a1 * x + a2 * x^2 +
     * ... + an * x^n</code>
     *
     * @param coefficients  an array with the coefficients [a0, a1, ..., an]
     *         (<code>null</code> not permitted).
     */
    public PolynomialFunction2D(double[] coefficients) {
        ParamChecks.nullNotPermitted(coefficients, "coefficients");
        this.coefficients = (double[]) coefficients.clone();
    }

    /**
     * Returns a copy of the coefficients array that was specified in the
     * constructor.
     *
     * @return The coefficients array.
     */
    public double[] getCoefficients() {
        return (double[]) this.coefficients.clone();
    }

    /**
     * Returns the order of the polynomial.
     *
     * @return The order.
     */
    public int getOrder() {
        return this.coefficients.length - 1;
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    public double getValue(double x) {
        double y = 0;
        for(int i = 0; i < coefficients.length; i++){
            y += coefficients[i] * Math.pow(x, i);
        }
        return y;
    }

    /**
     * Tests this function for equality with an arbitrary object.
     *
     * @param obj  the object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof PolynomialFunction2D)) {
            return false;
        }
        PolynomialFunction2D that = (PolynomialFunction2D) obj;
        return Arrays.equals(this.coefficients, that.coefficients);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return A hash code.
     */
    public int hashCode() {
        return HashUtilities.hashCodeForDoubleArray(this.coefficients);
    }

}
