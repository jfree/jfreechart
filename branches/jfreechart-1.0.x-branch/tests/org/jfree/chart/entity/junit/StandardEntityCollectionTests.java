/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2013, by Object Refinery Limited and Contributors.
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
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 *
 * ----------------------------------
 * StandardEntityCollectionTests.java
 * ----------------------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 19-May-2004 : Version 1 (DG);
 *
 */

package org.jfree.chart.entity.junit;

import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.entity.PieSectionEntity;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Tests for the {@link StandardEntityCollection} class.
 */
public class StandardEntityCollectionTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(StandardEntityCollectionTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public StandardEntityCollectionTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        StandardEntityCollection c1 = new StandardEntityCollection();
        StandardEntityCollection c2 = new StandardEntityCollection();
        assertTrue(c1.equals(c2));

        PieSectionEntity e1 = new PieSectionEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0), new DefaultPieDataset(), 0, 1, "Key",
                "ToolTip", "URL");
        c1.add(e1);
        assertFalse(c1.equals(c2));
        PieSectionEntity e2 = new PieSectionEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0), new DefaultPieDataset(), 0, 1, "Key",
                "ToolTip", "URL");
        c2.add(e2);
        assertTrue(c1.equals(c2));
    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        PieSectionEntity e1 = new PieSectionEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0), new DefaultPieDataset(), 0, 1, "Key",
                "ToolTip", "URL");
        StandardEntityCollection c1 = new StandardEntityCollection();
        c1.add(e1);
        StandardEntityCollection c2 = null;
        try {
            c2 = (StandardEntityCollection) c1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(c1 != c2);
        assertTrue(c1.getClass() == c2.getClass());
        assertTrue(c1.equals(c2));

        // check independence
        c1.clear();
        assertFalse(c1.equals(c2));
        c2.clear();
        assertTrue(c1.equals(c2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        PieSectionEntity e1 = new PieSectionEntity(new Rectangle2D.Double(1.0,
                2.0, 3.0, 4.0), new DefaultPieDataset(), 0, 1, "Key",
                "ToolTip", "URL");
        StandardEntityCollection c1 = new StandardEntityCollection();
        c1.add(e1);
        StandardEntityCollection c2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(c1);
            out.close();

            ObjectInput in = new ObjectInputStream(new ByteArrayInputStream(
                    buffer.toByteArray()));
            c2 = (StandardEntityCollection) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(c1, c2);
    }

}
