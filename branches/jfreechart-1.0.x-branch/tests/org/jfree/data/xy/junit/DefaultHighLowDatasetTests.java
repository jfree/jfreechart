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
 * -------------------------------
 * DefaultHighLowDatasetTests.java
 * -------------------------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DefaultHighLowDatasetTests.java,v 1.1.2.2 2007/02/02 13:54:18 mungady Exp $
 *
 * Changes
 * -------
 * 28-Nov-2006 : Version 1 (DG);
 *
 */

package org.jfree.data.xy.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.xy.DefaultHighLowDataset;

/**
 * Tests for the {@link DefaultHighLowDataset} class.
 */
public class DefaultHighLowDatasetTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DefaultHighLowDatasetTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DefaultHighLowDatasetTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        DefaultHighLowDataset d1 = new DefaultHighLowDataset("Series 1", 
                new Date[0], new double[0], new double[0], new double[0], 
                new double[0], new double[0]);
        DefaultHighLowDataset d2 = new DefaultHighLowDataset("Series 1", 
                new Date[0], new double[0], new double[0], new double[0], 
                new double[0], new double[0]);
        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));
        
        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[0], new double[0], new double[0], new double[0], 
                new double[0], new double[0]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[0], new double[0], new double[0], new double[0], 
                new double[0], new double[0]);
        assertTrue(d1.equals(d2));

        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[1], new double[1], 
                new double[1], new double[1], new double[1]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[1], new double[1], 
                new double[1], new double[1], new double[1]);
        assertTrue(d1.equals(d2));
    
        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, new double[1], 
                new double[1], new double[1], new double[1]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, new double[1], 
                new double[1], new double[1], new double[1]);
        assertTrue(d1.equals(d2));

        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[1], new double[1], 
                new double[1]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[1], new double[1], 
                new double[1]);
        assertTrue(d1.equals(d2));
    
        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[1], 
                new double[1]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[1], 
                new double[1]);
        assertTrue(d1.equals(d2));

        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[1]);
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[1]);
        assertTrue(d1.equals(d2));

        d1 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[] {99.9});
        assertFalse(d1.equals(d2));
        d2 = new DefaultHighLowDataset("Series 2", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[] {99.9});
        assertTrue(d1.equals(d2));
    
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        DefaultHighLowDataset d1 = new DefaultHighLowDataset("Series 1", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[] {99.9});
        DefaultHighLowDataset d2 = null;
        try {
            d2 = (DefaultHighLowDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        DefaultHighLowDataset d1 = new DefaultHighLowDataset("Series 1", 
                new Date[] {new Date(123L)}, new double[] {1.2}, 
                new double[] {3.4}, new double[] {5.6}, new double[] {7.8}, 
                new double[] {99.9});
        DefaultHighLowDataset d2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            d2 = (DefaultHighLowDataset) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(d1, d2);
    }

}
