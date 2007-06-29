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
 * ------------------
 * OHLCItemTests.java
 * ------------------
 * (C) Copyright 2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: OHLCItemTests.java,v 1.1.2.1 2006/12/04 17:08:36 mungady Exp $
 *
 * Changes
 * -------
 * 04-Dec-2006 : Version 1 (DG);
 *
 */

package org.jfree.data.time.ohlc.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.time.Year;
import org.jfree.data.time.ohlc.OHLCItem;

/**
 * Tests for the {@link OHLCItem} class.
 */
public class OHLCItemTests extends TestCase {
    
    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(OHLCItemTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public OHLCItemTests(String name) {
        super(name);
    }

    private static final double EPSILON = 0.00000000001;
    
    /**
     * Some checks for the constructor.
     */
    public void testConstructor1() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertEquals(new Year(2006), item1.getPeriod());
        assertEquals(2.0, item1.getOpenValue(), EPSILON);
        assertEquals(4.0, item1.getHighValue(), EPSILON);
        assertEquals(1.0, item1.getLowValue(), EPSILON);
        assertEquals(3.0, item1.getCloseValue(), EPSILON);
    }
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCItem item2 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        assertTrue(item1.equals(item2));
        assertTrue(item2.equals(item1));

        // period
        item1 = new OHLCItem(new Year(2007), 2.0, 4.0, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.0, 4.0, 1.0, 3.0);
        assertTrue(item1.equals(item2));
        
        // open
        item1 = new OHLCItem(new Year(2007), 2.2, 4.0, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.0, 1.0, 3.0);
        assertTrue(item1.equals(item2));

        // high
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.0, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.0, 3.0);
        assertTrue(item1.equals(item2));

        // low
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.0);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.0);
        assertTrue(item1.equals(item2));

        // close
        item1 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.3);
        assertFalse(item1.equals(item2));
        item2 = new OHLCItem(new Year(2007), 2.2, 4.4, 1.1, 3.3);
        assertTrue(item1.equals(item2));
    }

    /**
     * Some checks for the clone() method.
     */
    public void testCloning() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCItem item2 = null;
        try {
            item2 = (OHLCItem) item1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(item1 != item2);
        assertTrue(item1.getClass() == item2.getClass());
        assertTrue(item1.equals(item2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        OHLCItem item1 = new OHLCItem(new Year(2006), 2.0, 4.0, 1.0, 3.0);
        OHLCItem item2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(item1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            item2 = (OHLCItem) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(item1, item2);
    }

}
