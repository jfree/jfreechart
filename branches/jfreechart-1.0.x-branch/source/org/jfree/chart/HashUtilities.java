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
 * $Id: HashUtilities.java,v 1.1.2.2 2007/03/06 16:00:44 mungady Exp $
 *
 * Changes
 * -------
 * 03-Oct-2006 : Version 1 (DG);
 * 06-Mar-2007 : Fix for hashCodeForDoubleArray() method (DG);
 *
 */

package org.jfree.chart;

import java.awt.GradientPaint;
import java.awt.Paint;

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
        if (p == null) 
            return 0;
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

}
