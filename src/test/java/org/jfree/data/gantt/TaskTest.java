/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
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
 * -------------
 * TaskTest.java
 * -------------
 * (C) Copyright 2004-2020, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 30-Jul-2004 : Version 1 (DG);
 *
 */

package org.jfree.data.gantt;

import java.util.Date;

import org.jfree.chart.TestUtils;

import org.jfree.data.time.SimpleTimePeriod;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link Task} class.
 */
public class TaskTest {

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        Task t1 = new Task("T", new Date(1), new Date(2));
        Task t2 = new Task("T", new Date(1), new Date(2));
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));

        t1.setDescription("X");
        assertFalse(t1.equals(t2));
        t2.setDescription("X");
        assertTrue(t1.equals(t2));

        t1.setDuration(new SimpleTimePeriod(new Date(2), new Date(3)));
        assertFalse(t1.equals(t2));
        t2.setDuration(new SimpleTimePeriod(new Date(2), new Date(3)));
        assertTrue(t1.equals(t2));

        t1.setPercentComplete(0.5);
        assertFalse(t1.equals(t2));
        t2.setPercentComplete(0.5);
        assertTrue(t1.equals(t2));

        t1.addSubtask(new Task("T", new Date(22), new Date(33)));
        assertFalse(t1.equals(t2));
        t2.addSubtask(new Task("T", new Date(22), new Date(33)));
        assertTrue(t1.equals(t2));


    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        Task t1 = new Task("T", new Date(1), new Date(2));
        Task t2 = (Task) t1.clone();
        assertTrue(t1 != t2);
        assertTrue(t1.getClass() == t2.getClass());
        assertTrue(t1.equals(t2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        Task t1 = new Task("T", new Date(1), new Date(2));
        Task t2 = (Task) TestUtils.serialised(t1);
        assertEquals(t1, t2);
    }

    /**
     * Check the getSubTaskCount() method.
     */
    @Test
    public void testGetSubTaskCount() {
        Task t1 = new Task("T", new Date(100), new Date(200));
        assertEquals(0, t1.getSubtaskCount());
        t1.addSubtask(new Task("S1", new Date(100), new Date(110)));
        assertEquals(1, t1.getSubtaskCount());
        Task s2 = new Task("S2", new Date(111), new Date(120));
        t1.addSubtask(s2);
        assertEquals(2, t1.getSubtaskCount());
        t1.addSubtask(new Task("S3", new Date(121), new Date(130)));
        assertEquals(3, t1.getSubtaskCount());
        t1.removeSubtask(s2);
        assertEquals(2, t1.getSubtaskCount());
    }

}
