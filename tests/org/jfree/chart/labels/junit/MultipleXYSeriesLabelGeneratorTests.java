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
 * ----------------------------------------
 * MultipleXYSeriesLabelGeneratorTests.java
 * ----------------------------------------
 * (C) Copyright 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: MultipleXYSeriesLabelGeneratorTests.java,v 1.1.2.1 2007/02/20 15:32:06 mungady Exp $
 *
 * Changes
 * -------
 * 20-Jan-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.labels.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.labels.MultipleXYSeriesLabelGenerator;

/**
 * Tests for the {@link MultipleXYSeriesLabelGenerator} class.
 */
public class MultipleXYSeriesLabelGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(MultipleXYSeriesLabelGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public MultipleXYSeriesLabelGeneratorTests(String name) {
        super(name);
    }

    /**
     * A series of tests for the equals() method.
     */
    public void testEquals() {
        MultipleXYSeriesLabelGenerator g1 
                = new MultipleXYSeriesLabelGenerator();
        MultipleXYSeriesLabelGenerator g2 
                = new MultipleXYSeriesLabelGenerator();
        assertTrue(g1.equals(g2));
        assertTrue(g2.equals(g1)); 
        
        g1 = new MultipleXYSeriesLabelGenerator("Series {0}");
        assertFalse(g1.equals(g2));
        g2 = new MultipleXYSeriesLabelGenerator("Series {0}");
        assertTrue(g1.equals(g2));
        
        g1.addSeriesLabel(1, "Additional 1");
        assertFalse(g1.equals(g2));
        g2.addSeriesLabel(1, "Additional 1");
        assertTrue(g1.equals(g2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        MultipleXYSeriesLabelGenerator g1 
                = new MultipleXYSeriesLabelGenerator();
        MultipleXYSeriesLabelGenerator g2 = null;
        try {
            g2 = (MultipleXYSeriesLabelGenerator) g1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(g1 != g2);
        assertTrue(g1.getClass() == g2.getClass());
        assertTrue(g1.equals(g2));
        
        // check independence
        g1.addSeriesLabel(3, "Add3");
        assertFalse(g1.equals(g2));
        g2.addSeriesLabel(3, "Add3");
        assertTrue(g1.equals(g2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        MultipleXYSeriesLabelGenerator g1 
                = new MultipleXYSeriesLabelGenerator();
        g1.addSeriesLabel(0, "Add0");
        g1.addSeriesLabel(0, "Add0b");
        g1.addSeriesLabel(1, "Add1");
        MultipleXYSeriesLabelGenerator g2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (MultipleXYSeriesLabelGenerator) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(g1, g2);

    }

}
