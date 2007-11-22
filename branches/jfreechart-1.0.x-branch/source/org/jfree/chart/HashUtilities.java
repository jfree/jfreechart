/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
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
 * ------------------
 * HashUtilities.java
 * ------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited;
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 03-Oct-2006 : Version 1 (DG);
 * 06-Mar-2007 : Fix for hashCodeForDoubleArray() method (DG);
 * 13-Nov-2007 : Added new utility methods (DG);
 * 22-Nov-2007 : Added hashCode() method for 'int' (DG);
 *
 */

package org.jfree.chart;

import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;

/**
 * Some utility methods for calculating hash codes.  
 * 
 * @since 1.0.3
 */
public class HashUtilities {
    
    /**
     * Returns a hash code for a <code>Paint</code> instance.  If 
     * <code>p</code> is <code>null</code>, this method returns zero.
     * 
     * @param p  the paint (<code>null</code> permitted).
     * 
     * @return The hash code.
     */
    public static int hashCodeForPaint(Paint p) {
        if (p == null) {
            return 0;
        }
        int result = 0;
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
     * Returns a hash code for a <code>double[]</code> instance.  If the array
     * is <code>null</code>, this method returns zero.
     * 
     * @param a  the array (<code>null</code> permitted).
     * 
     * @return The hash code.
     */
    public static int hashCodeForDoubleArray(double[] a) {
        if (a == null) { 
            return 0;
        }
        int result = 193;
        long temp;
        for (int i = 0; i < a.length; i++) {
            temp = Double.doubleToLongBits(a[i]);
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
     * 
     * @since 1.0.7
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
     * 
     * @since 1.0.8
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
     * 
     * @since 1.0.7
     */
    public static int hashCode(int pre, double d) {
        long l = Double.doubleToLongBits(d);
        return 37 * pre + (int) (l ^ (l >>> 32));
    }
    
    /**
     * Returns a hash value based on a seed value and a paint instance.
     * 
     * @param pre  the seed value.
     * @param p  the paint (<code>null</code> permitted).
     * 
     * @return A hash value.
     * 
     * @since 1.0.7
     */
    public static int hashCode(int pre, Paint p) {
        return 37 * pre + hashCodeForPaint(p);
    }

    /**
     * Returns a hash value based on a seed value and a stroke instance.
     * 
     * @param pre  the seed value.
     * @param s  the stroke (<code>null</code> permitted).
     * 
     * @return A hash value.
     * 
     * @since 1.0.7
     */
    public static int hashCode(int pre, Stroke s) {
        int h = (s != null ? s.hashCode() : 0);
        return 37 * pre + h;
    }

    /**
     * Returns a hash value based on a seed value and a string instance.
     * 
     * @param pre  the seed value.
     * @param s  the string (<code>null</code> permitted).
     * 
     * @return A hash value.
     * 
     * @since 1.0.7
     */
    public static int hashCode(int pre, String s) {
        int h = (s != null ? s.hashCode() : 0);
        return 37 * pre + h;
    }

    /**
     * Returns a hash value based on a seed value and a <code>Comparable</code>
     * instance.
     * 
     * @param pre  the seed value.
     * @param c  the comparable (<code>null</code> permitted).
     * 
     * @return A hash value.
     * 
     * @since 1.0.7
     */
    public static int hashCode(int pre, Comparable c) {
        int h = (c != null ? c.hashCode() : 0);
        return 37 * pre + h;
    }
}
