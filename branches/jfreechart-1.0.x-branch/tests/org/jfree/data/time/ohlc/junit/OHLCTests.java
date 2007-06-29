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
 * --------------
 * OHLCTests.java
 * --------------
 * (C) Copyright 2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: OHLCTests.java,v 1.1.2.1 2006/12/04 17:08:36 mungady Exp $
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

import org.jfree.data.time.ohlc.OHLC;

/**
 * Tests for the {@link OHLC} class.
 */
public class OHLCTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(OHLCTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public OHLCTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        OHLC i2 = new OHLC(2.0, 4.0, 1.0, 3.0);
        assertEquals(i1, i2);
        
        i1 = new OHLC(2.2, 4.0, 1.0, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.0, 1.0, 3.0);
        assertTrue(i1.equals(i2));

        i1 = new OHLC(2.2, 4.4, 1.0, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.0, 3.0);
        assertTrue(i1.equals(i2));
    
        i1 = new OHLC(2.2, 4.4, 1.1, 3.0);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.1, 3.0);
        assertTrue(i1.equals(i2));

        i1 = new OHLC(2.2, 4.4, 1.1, 3.3);
        assertFalse(i1.equals(i2));
        i2 = new OHLC(2.2, 4.4, 1.1, 3.3);
        assertTrue(i1.equals(i2));
    }

    /**
     * This class is immutable.
     */
    public void testCloning() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        assertFalse(i1 instanceof Cloneable);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        OHLC i1 = new OHLC(2.0, 4.0, 1.0, 3.0);
        OHLC i2 = null;
        
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(i1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            i2 = (OHLC) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(i1, i2);
    }
    
}
