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
 * -------------------------
 * PolynomialFunction2D.java
 * -------------------------
 * (C) Copyright 2009-2022, by David Gilbert.
 *
 * Original Author:  Peter Kolb;
 * Contributor(s):   David Gilbert;
 *
 */

package org.jfree.data.function;

import java.io.Serializable;
import java.util.Arrays;
import org.jfree.chart.internal.HashUtils;
import org.jfree.chart.internal.Args;

/**
 * A function in the form {@code y = a0 + a1 * x + a2 * x^2 + ... + an *
 * x^n}.  Instances of this class are immutable.
 *
 * @since 1.0.14
 */
public class PolynomialFunction2D implements Function2D, Serializable {

    /** The coefficients. */
    private double[] coefficients;

    /**
     * Constructs a new polynomial function {@code y = a0 + a1 * x + a2 * x^2 +
     * ... + an * x^n}
     *
     * @param coefficients  an array with the coefficients [a0, a1, ..., an]
     *         ({@code null} not permitted).
     */
    public PolynomialFunction2D(double[] coefficients) {
        Args.nullNotPermitted(coefficients, "coefficients");
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
    @Override
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
     * @param obj  the object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
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
    @Override
    public int hashCode() {
        return HashUtils.hashCodeForDoubleArray(this.coefficients);
    }

}
