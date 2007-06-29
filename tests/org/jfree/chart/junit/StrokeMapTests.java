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
 * -------------------
 * StrokeMapTests.java
 * -------------------
 * (C) Copyright 2006, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: StrokeMapTests.java,v 1.1.2.1 2006/10/03 15:41:32 mungady Exp $
 *
 * Changes:
 * --------
 * 27-Sep-2006 : Version 1 (DG);
 *
 */

package org.jfree.chart.junit;

import java.awt.BasicStroke;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.StrokeMap;

/**
 * Some tests for the {@link StrokeMap} class.
 */
public class StrokeMapTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StrokeMapTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StrokeMapTests(String name) {
        super(name);
    }

    /**
     * Some checks for the getStroke() method.
     */
    public void testGetStroke() {
        StrokeMap m1 = new StrokeMap();
        assertEquals(null, m1.getStroke("A"));
        m1.put("A", new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m1.getStroke("A"));
        m1.put("A", null);
        assertEquals(null, m1.getStroke("A"));
        
        // a null key should throw an IllegalArgumentException
        boolean pass = false;
        try {
            m1.getStroke(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some checks for the put() method.
     */
    public void testPut() {
        StrokeMap m1 = new StrokeMap();
        m1.put("A", new BasicStroke(1.1f));
        assertEquals(new BasicStroke(1.1f), m1.getStroke("A"));
        
        // a null key should throw an IllegalArgumentException
        boolean pass = false;
        try {
            m1.put(null, new BasicStroke(1.1f));
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }
    
    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = new StrokeMap();
        assertTrue(m1.equals(m1));
        assertTrue(m1.equals(m2));
        assertFalse(m1.equals(null));
        assertFalse(m1.equals("ABC"));
        
        m1.put("K1", new BasicStroke(1.1f));
        assertFalse(m1.equals(m2));
        m2.put("K1", new BasicStroke(1.1f));
        assertTrue(m1.equals(m2));
        
        m1.put("K2", new BasicStroke(2.2f));
        assertFalse(m1.equals(m2));
        m2.put("K2", new BasicStroke(2.2f));
        assertTrue(m1.equals(m2));
        
        m1.put("K2", null);
        assertFalse(m1.equals(m2));
        m2.put("K2", null);
        assertTrue(m1.equals(m2));
    }
    
    /**
     * Some checks for cloning.
     */
    public void testCloning() {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = null;
        try {
            m2 = (StrokeMap) m1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(m1.equals(m2));
        
        m1.put("K1", new BasicStroke(1.1f));
        m1.put("K2", new BasicStroke(2.2f));
        try {
            m2 = (StrokeMap) m1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(m1.equals(m2));
    }
    
    /**
     * A check for serialization.
     */
    public void testSerialization1() {
        StrokeMap m1 = new StrokeMap();
        StrokeMap m2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(m1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            m2 = (StrokeMap) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(m1, m2);
    }

    /**
     * A check for serialization.
     */
    public void testSerialization2() {
        StrokeMap m1 = new StrokeMap();
        m1.put("K1", new BasicStroke(1.1f));
        m1.put("K2", new BasicStroke(2.2f));
        StrokeMap m2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(m1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            m2 = (StrokeMap) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(m1, m2);
    }

}

