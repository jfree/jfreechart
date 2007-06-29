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
 * ---------------------
 * SimpleTimePeriod.java
 * ---------------------
 * (C) Copyright 2002-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: SimpleTimePeriod.java,v 1.5.2.1 2005/10/25 21:35:24 mungady Exp $
 *
 * Changes
 * -------
 * 07-Oct-2002 : Added Javadocs (DG);
 * 10-Jan-2003 : Renamed TimeAllocation --> SimpleTimePeriod (DG);
 * 13-Mar-2003 : Added equals() method, and Serializable interface (DG);
 * 21-Oct-2003 : Added hashCode() method (DG);
 * 27-Jan-2005 : Implemented Comparable, to enable this class to be used
 *               in the TimeTableXYDataset class (DG);
 *
 */

package org.jfree.data.time;

import java.io.Serializable;
import java.util.Date;

/**
 * An arbitrary period of time, measured to millisecond precision using 
 * <code>java.util.Date</code>.
 * <p>
 * This class is intentionally immutable (that is, once constructed, you cannot 
 * alter the start and end attributes).
 */
public class SimpleTimePeriod implements TimePeriod, Comparable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 8684672361131829554L;
    
    /** The start date/time. */
    private Date start;

    /** The end date/time. */
    private Date end;

    /**
     * Creates a new time allocation.
     *
     * @param start  the start date/time in milliseconds.
     * @param end  the end date/time in milliseconds.
     */
    public SimpleTimePeriod(long start, long end) {
        this(new Date(start), new Date(end));   
    }
    
    /**
     * Creates a new time allocation.
     *
     * @param start  the start date/time (<code>null</code> not permitted).
     * @param end  the end date/time (<code>null</code> not permitted).
     */
    public SimpleTimePeriod(Date start, Date end) {
        if (start.getTime() > end.getTime()) {
            throw new IllegalArgumentException("Requires end >= start.");
        }
        this.start = start;
        this.end = end;
    }

    /**
     * Returns the start date/time.
     *
     * @return The start date/time (never <code>null</code>).
     */
    public Date getStart() {
        return this.start;
    }

    /**
     * Returns the end date/time.
     *
     * @return The end date/time (never <code>null</code>).
     */
    public Date getEnd() {
        return this.end;
    }

    /**
     * Tests this time period instance for equality with an arbitrary object.  
     * The object is considered equal if it is an instance of {@link TimePeriod}
     * and it has the same start and end dates.
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TimePeriod)) {
            return false;
        }
        TimePeriod that = (TimePeriod) obj;
        if (!this.start.equals(that.getStart())) {
            return false;   
        }
        if (!this.end.equals(that.getEnd())) {
            return false;   
        }
        return true;
    }
    
    /**
     * Returns an integer that indicates the relative ordering of two
     * time periods.
     * 
     * @param obj  the object (<code>null</code> not permitted).
     * 
     * @return An integer.
     * 
     * @throws ClassCastException if <code>obj</code> is not an instance of
     *                            {@link TimePeriod}.
     */
    public int compareTo(Object obj) {        
        TimePeriod that = (TimePeriod) obj;
        long t0 = getStart().getTime();
        long t1 = getEnd().getTime();
        long m0 = t0 + (t1 - t0) / 2L;
        long t2 = that.getStart().getTime();
        long t3 = that.getEnd().getTime();
        long m1 = t2 + (t3 - t2) / 2L;
        if (m0 < m1) {
            return -1;   
        }
        else if (m0 > m1) {
            return 1;   
        }
        else {
            if (t0 < t2) {
                return -1;
            }
            else if (t0 > t2) {
                return 1;   
            }
            else {
                if (t1 < t3) {
                    return -1;   
                }
                else if (t1 > t3) {
                    return 1;   
                }
                else {
                    return 0;   
                }
            }
        }
    }
    
    /**
     * Returns a hash code for this object instance.  The approach described by
     * Joshua Bloch in "Effective Java" has been used here - see:
     * <p>
     * <code>http://developer.java.sun.com/
     * developer/Books/effectivejava/Chapter3.pdf</code>
     * 
     * @return A hash code.
     */
    public int hashCode() {
        int result = 17;
        result = 37 * result + this.start.hashCode();
        result = 37 * result + this.end.hashCode();
        return result;
    }

}
