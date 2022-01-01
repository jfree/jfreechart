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
 * --------------
 * HashUtils.java
 * --------------
 * (C) Copyright 2006-2022, by David Gilbert;
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.internal;

import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;

/**
 * Some utility methods for calculating hash codes.  
 */
public class HashUtils {
    
    /**
     * Returns a hash code for a {@code Paint} instance.  If 
     * {@code p} is {@code null}, this method returns zero.
     * 
     * @param p  the paint ({@code null} permitted).
     * 
     * @return The hash code.
     */
    public static int hashCodeForPaint(Paint p) {
        if (p == null) {
            return 0;
        }
        int result;
        // handle GradientPaint as a special case
        if (p instanceof GradientPaint) {
            GradientPaint gp = (GradientPaint) p;
            result = 193;
            result = 37 * result + gp.getColor1().hashCode();
            result = 37 * result + gp.getPoint1().hashCode();
            result = 37 * result + gp.getColor2().hashCode();
            result = 37 * result + gp.getPoint2().hashCode();
        }
        else {
            // we assume that all other Paint instances implement equals() and
            // hashCode()...of course that might not be true, but what can we
            // do about it?
            result = p.hashCode();
        }
        return result;
    }
    
    /**
     * Returns a hash code for a {@code double[]} instance.  If the array
     * is {@code null}, this method returns zero.
     * 
     * @param a  the array ({@code null} permitted).
     * 
     * @return The hash code.
     */
    public static int hashCodeForDoubleArray(double[] a) {
        if (a == null) { 
            return 0;
        }
        int result = 193;
        long temp;
        for (double v : a) {
            temp = Double.doubleToLongBits(v);
            result = 29 * result + (int) (temp ^ (temp >>> 32));
        }
        return result;
    }
    
    /**
     * Returns a hash value based on a seed value and the value of a boolean
     * primitive.
     * 
     * @param pre  the seed value.
     * @param b  the boolean value.
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, boolean b) {
        return 37 * pre + (b ? 0 : 1);
    }
    
    /**
     * Returns a hash value based on a seed value and the value of an int
     * primitive.
     * 
     * @param pre  the seed value.
     * @param i  the int value.
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, int i) {
        return 37 * pre + i;
    }

    /**
     * Returns a hash value based on a seed value and the value of a double
     * primitive.
     * 
     * @param pre  the seed value.
     * @param d  the double value.
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, double d) {
        long l = Double.doubleToLongBits(d);
        return 37 * pre + (int) (l ^ (l >>> 32));
    }
    
    /**
     * Returns a hash value based on a seed value and a paint instance.
     * 
     * @param pre  the seed value.
     * @param p  the paint ({@code null} permitted).
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, Paint p) {
        return 37 * pre + hashCodeForPaint(p);
    }

    /**
     * Returns a hash value based on a seed value and a stroke instance.
     * 
     * @param pre  the seed value.
     * @param s  the stroke ({@code null} permitted).
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, Stroke s) {
        int h = (s != null ? s.hashCode() : 0);
        return 37 * pre + h;
    }

    /**
     * Returns a hash value based on a seed value and a string instance.
     * 
     * @param pre  the seed value.
     * @param s  the string ({@code null} permitted).
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, String s) {
        int h = (s != null ? s.hashCode() : 0);
        return 37 * pre + h;
    }

    /**
     * Returns a hash value based on a seed value and a {@code Comparable}
     * instance.
     * 
     * @param pre  the seed value.
     * @param c  the comparable ({@code null} permitted).
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, Comparable<?> c) {
        int h = (c != null ? c.hashCode() : 0);
        return 37 * pre + h;
    }

    /**
     * Returns a hash value based on a seed value and an {@code Object}
     * instance.
     * 
     * @param pre  the seed value.
     * @param obj  the object ({@code null} permitted).
     * 
     * @return A hash value.
     */
    public static int hashCode(int pre, Object obj) {
        int h = (obj != null ? obj.hashCode() : 0);
        return 37 * pre + h;
    }

}
