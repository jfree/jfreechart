/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * -----------------------------
 * MeanAndStandardDeviation.java
 * -----------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: MeanAndStandardDeviation.java,v 1.3.2.1 2005/10/25 21:34:46 mungady Exp $
 *
 * Changes:
 * --------
 * 05-Feb-2002 : Version 1 (DG);
 * 05-Feb-2005 : Added equals() method and implemented Serializable (DG);
 *
 */

package org.jfree.data.statistics;

import java.io.Serializable;

import org.jfree.util.ObjectUtilities;

/**
 * A simple data structure that holds a mean value and a standard deviation 
 * value.  This is used in the 
 * {@link org.jfree.data.statistics.DefaultStatisticalCategoryDataset} class.
 */
public class MeanAndStandardDeviation implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 7413468697315721515L;
    
    /** The mean. */
    private Number mean;

    /** The standard deviation. */
    private Number standardDeviation;

    /**
     * Creates a new mean and standard deviation record.
     * 
     * @param mean  the mean.
     * @param standardDeviation  the standard deviation.
     */
    public MeanAndStandardDeviation(double mean, double standardDeviation) {
        this(new Double(mean), new Double(standardDeviation));   
    }
    
    /**
     * Creates a new mean and standard deviation record.
     *
     * @param mean  the mean (<code>null</code> permitted).
     * @param standardDeviation  the standard deviation (<code>null</code>
     *                           permitted.
     */
    public MeanAndStandardDeviation(Number mean, Number standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    /**
     * Returns the mean.
     *
     * @return The mean.
     */
    public Number getMean() {
        return this.mean;
    }

    /**
     * Returns the standard deviation.
     *
     * @return The standard deviation.
     */
    public Number getStandardDeviation() {
        return this.standardDeviation;
    }

    /**
     * Tests this instance for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;   
        }
        if (!(obj instanceof MeanAndStandardDeviation)) {
            return false;   
        }
        MeanAndStandardDeviation that = (MeanAndStandardDeviation) obj;
        if (!ObjectUtilities.equal(this.mean, that.mean)) {
            return false;   
        }
        if (!ObjectUtilities.equal(
            this.standardDeviation, that.standardDeviation)
        ) {
            return false;   
        }
        return true;
    }
}
