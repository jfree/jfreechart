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
 * ----------------------------------
 * DefaultKeyedValueDatasetTests.java
 * ----------------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DefaultKeyedValueDatasetTests.java,v 1.1.2.1 2006/10/03 15:41:43 mungady Exp $
 *
 * Changes
 * -------
 * 18-Aug-2003 : Version 1 (DG);
 *
 */

package org.jfree.data.general.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.data.general.DefaultKeyedValueDataset;

/**
 * Tests for the {@link DefaultKeyedValueDataset} class.
 */
public class DefaultKeyedValueDatasetTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DefaultKeyedValueDatasetTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DefaultKeyedValueDatasetTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        
        DefaultKeyedValueDataset d1 
            = new DefaultKeyedValueDataset("Test", new Double(45.5));
        DefaultKeyedValueDataset d2 
            = new DefaultKeyedValueDataset("Test", new Double(45.5));
        assertTrue(d1.equals(d2));
        assertTrue(d2.equals(d1));

        d1 = new DefaultKeyedValueDataset("Test 1", new Double(45.5));
        d2 = new DefaultKeyedValueDataset("Test 2", new Double(45.5));
        assertFalse(d1.equals(d2));

        d1 = new DefaultKeyedValueDataset("Test", new Double(45.5));
        d2 = new DefaultKeyedValueDataset("Test", new Double(45.6));
        assertFalse(d1.equals(d2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        DefaultKeyedValueDataset d1 
            = new DefaultKeyedValueDataset("Test", new Double(45.5));
        DefaultKeyedValueDataset d2 = null;
        try {
            d2 = (DefaultKeyedValueDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(d1 != d2);
        assertTrue(d1.getClass() == d2.getClass());
        assertTrue(d1.equals(d2));
    }
    
    /**
     * Confirm that the clone is independent of the original.
     */
    public void testCloneIndependence() {
        DefaultKeyedValueDataset d1 
            = new DefaultKeyedValueDataset("Key", new Double(10.0));
        DefaultKeyedValueDataset d2 = null;
        try {
            d2 = (DefaultKeyedValueDataset) d1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(d1.equals(d2));
        d2.updateValue(new Double(99.9));
        assertFalse(d1.equals(d2));
        d2.updateValue(new Double(10.0));
        assertTrue(d1.equals(d2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DefaultKeyedValueDataset d1  
            = new DefaultKeyedValueDataset("Test", new Double(25.3));
        DefaultKeyedValueDataset d2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(d1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            d2 = (DefaultKeyedValueDataset) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(d1, d2);
    }

}
