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
 * --------------------
 * TaskSeriesTests.java
 * --------------------
 * (C) Copyright 2004-2008, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jul-2004 : Version 1 (DG);
 * 09-May-2008 : Added independence check in testCloning() (DG);
 *
 */

package org.jfree.data.gantt.junit;

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

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;

/**
 * Tests for the {@link TaskSeries} class.
 */
public class TaskSeriesTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(TaskSeriesTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public TaskSeriesTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {

        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = new TaskSeries("S");
        s2.add(new Task("T1", new Date(1), new Date(2)));
        s2.add(new Task("T2", new Date(11), new Date(22)));
        assertTrue(s1.equals(s2));
        assertTrue(s2.equals(s1));

        s1.add(new Task("T3", new Date(22), new Date(33)));
        assertFalse(s1.equals(s2));
        s2.add(new Task("T3", new Date(22), new Date(33)));
        assertTrue(s1.equals(s2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = null;
        try {
            s2 = (TaskSeries) s1.clone();
        }
        catch (CloneNotSupportedException e) {
            System.err.println("Failed to clone.");
        }
        assertTrue(s1 != s2);
        assertTrue(s1.getClass() == s2.getClass());
        assertTrue(s1.equals(s2));

        // basic check for independence
        s1.add(new Task("T3", new Date(22), new Date(33)));
        assertFalse(s1.equals(s2));
        s2.add(new Task("T3", new Date(22), new Date(33)));
        assertTrue(s1.equals(s2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(s1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            s2 = (TaskSeries) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(s1, s2);

    }

    /**
     * Some checks for the getTask() method.
     */
    public void testGetTask() {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        Task t1 = s1.get("T1");
        assertTrue(t1.equals(new Task("T1", new Date(1), new Date(2))));
        Task t2 = s1.get("T2");
        assertTrue(t2.equals(new Task("T2", new Date(11), new Date(22))));
        Task t3 = s1.get("T3");
        assertTrue(t3 == null);
    }

}
