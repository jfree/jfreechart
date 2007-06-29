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
 * ---------------------------------
 * StandardPieURLGeneratorTests.java
 * ---------------------------------
 * (C) Copyright 2003-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: StandardPieURLGeneratorTests.java,v 1.1.2.3 2007/04/17 16:05:27 mungady Exp $
 *
 * Changes
 * -------
 * 21-Mar-2003 : Version 1 (DG);
 * 06-Jan-2003 : Added a test for URL generation (DG);
 * 24-Nov-2006 : New equals() test (DG);
 * 17-Apr-2007 : Added additional check to testURL() (DG);
 *
 */

package org.jfree.chart.urls.junit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Tests for the {@link StandardPieURLGenerator} class.
 */
public class StandardPieURLGeneratorTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StandardPieURLGeneratorTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StandardPieURLGeneratorTests(String name) {
        super(name);
    }
    
    /**
     * Some checks for the equals() method.
     */
    public void testEquals() {
        StandardPieURLGenerator g1 = new StandardPieURLGenerator();
        StandardPieURLGenerator g2 = new StandardPieURLGenerator();
        assertTrue(g1.equals(g2));
        
        g1 = new StandardPieURLGenerator("prefix", "category", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix", "category", "index");
        assertTrue(g1.equals(g2));
        
        g1 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category", "index");
        assertTrue(g1.equals(g2));
        
        g1 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", "index2");
        assertTrue(g1.equals(g2));

        g1 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertFalse(g1.equals(g2));
        g2 = new StandardPieURLGenerator("prefix2", "category2", null);
        assertTrue(g1.equals(g2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "index.html?", "cat");
        StandardPieURLGenerator g2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(g1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            g2 = (StandardPieURLGenerator) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(g1, g2);

    }

    /**
     * Test that the generated URL is as expected.
     */
    public void testURL() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Alpha '1'", new Double(5.0));
        dataset.setValue("Beta", new Double(5.5));
        StandardPieURLGenerator g1 = new StandardPieURLGenerator(
                "chart.jsp", "category");
        String url = g1.generateURL(dataset, "Beta", 0);
        assertEquals("chart.jsp?category=Beta&amp;pieIndex=0", url);
        url = g1.generateURL(dataset, "Alpha '1'", 0);
        assertEquals("chart.jsp?category=Alpha+%271%27&amp;pieIndex=0", url);
    }
    
}
