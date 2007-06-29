/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
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
 * ----------------------------
 * RelativeDateFormatTests.java
 * ----------------------------
 * (C) Copyright 2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: RelativeDateFormatTests.java,v 1.1.2.1 2006/11/23 15:42:24 mungady Exp $
 *
 * Changes
 * -------
 * 23-Nov-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart.util.junit;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.util.RelativeDateFormat;


/**
 * Tests for the {@link RelativeDateFormat} class.
 */
public class RelativeDateFormatTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(RelativeDateFormatTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public RelativeDateFormatTests(String name) {
        super(name);
    }
    
    /**
     * Check that the equals() method can distinguish all fields.
     */
    public void testEquals() {
        RelativeDateFormat df1 = new RelativeDateFormat();
        RelativeDateFormat df2 = new RelativeDateFormat();
        assertEquals(df1, df2);
        
        df1.setBaseMillis(123L);
        assertFalse(df1.equals(df2));
        df2.setBaseMillis(123L);
        assertTrue(df1.equals(df2));
        
        df1.setDaySuffix("D");
        assertFalse(df1.equals(df2));
        df2.setDaySuffix("D");
        assertTrue(df1.equals(df2));
        
        df1.setHourSuffix("H");
        assertFalse(df1.equals(df2));
        df2.setHourSuffix("H");
        assertTrue(df1.equals(df2));
        
        df1.setMinuteSuffix("M");
        assertFalse(df1.equals(df2));
        df2.setMinuteSuffix("M");
        assertTrue(df1.equals(df2));
        
        df1.setSecondSuffix("S");
        assertFalse(df1.equals(df2));
        df2.setSecondSuffix("S");
        assertTrue(df1.equals(df2));
        
        df1.setShowZeroDays(!df1.getShowZeroDays()); 
        assertFalse(df1.equals(df2));
        df2.setShowZeroDays(!df2.getShowZeroDays()); 
        assertTrue(df1.equals(df2));
        
        df1.setSecondFormatter(new DecimalFormat("0.0"));
        assertFalse(df1.equals(df2));
        df2.setSecondFormatter(new DecimalFormat("0.0"));
        assertTrue(df1.equals(df2));
    }
    
    /**
     * Two objects that are equal are required to return the same hashCode. 
     */
    public void testHashCode() {
        RelativeDateFormat df1 = new RelativeDateFormat(123L);
        RelativeDateFormat df2 = new RelativeDateFormat(123L);
        assertTrue(df1.equals(df2));
        int h1 = df1.hashCode();
        int h2 = df2.hashCode();
        assertEquals(h1, h2);
    }    
    
    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        NumberFormat nf = new DecimalFormat("0");
        RelativeDateFormat df1 = new RelativeDateFormat();
        df1.setSecondFormatter(nf);
        RelativeDateFormat df2 = null;
        df2 = (RelativeDateFormat) df1.clone();
        assertTrue(df1 != df2);
        assertTrue(df1.getClass() == df2.getClass());
        assertTrue(df1.equals(df2));
    
        // is the clone independent
        nf.setMinimumFractionDigits(2);
        assertFalse(df1.equals(df2));
    }
    
    
}

