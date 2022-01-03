/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2022, by David Gilbert and Contributors.
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
 * ----------------------
 * XYTaskDatasetTest.java
 * ----------------------
 * (C) Copyright 2008-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.gantt;

import java.util.Date;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link XYTaskDataset} class.
 */
public class XYTaskDatasetTest {

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        TaskSeries<String> s1 = new TaskSeries<>("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        s1.add(new Task("Task 3", new Date(20L), new Date(21L)));
        TaskSeriesCollection<String, String> u1 = new TaskSeriesCollection<>();
        u1.add(s1);
        XYTaskDataset d1 = new XYTaskDataset(u1);
        TaskSeries<String> s2 = new TaskSeries<>("Series");
        s2.add(new Task("Task 1", new Date(0L), new Date(1L)));
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        s2.add(new Task("Task 3", new Date(20L), new Date(21L)));
        TaskSeriesCollection<String, String> u2 = new TaskSeriesCollection<>();
        u2.add(s2);
        XYTaskDataset d2 = new XYTaskDataset(u2);
        assertEquals(d1, d2);

        d1.setSeriesWidth(0.123);
        assertNotEquals(d1, d2);
        d2.setSeriesWidth(0.123);
        assertEquals(d1, d2);

        d1.setTransposed(true);
        assertNotEquals(d1, d2);
        d2.setTransposed(true);
        assertEquals(d1, d2);

        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertNotEquals(d1, d2);
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertEquals(d1, d2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TaskSeries<String> s1 = new TaskSeries<>("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        TaskSeriesCollection<String, String> u1 = new TaskSeriesCollection<>();
        u1.add(s1);
        XYTaskDataset d1 = new XYTaskDataset(u1);
        XYTaskDataset d2 = CloneUtils.clone(d1);
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // basic check for independence
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertNotEquals(d1, d2);
        TaskSeriesCollection<String, String> u2 = d2.getTasks();
        TaskSeries<String> s2 = u2.getSeries("Series");
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TaskSeries<String> s1 = new TaskSeries<>("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        TaskSeriesCollection<String, String> u1 = new TaskSeriesCollection<>();
        u1.add(s1);
        XYTaskDataset d1 = new XYTaskDataset(u1);
        XYTaskDataset d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);

        // basic check for independence
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertNotEquals(d1, d2);
        TaskSeriesCollection<String, String> u2 = d2.getTasks();
        TaskSeries<String> s2 = u2.getSeries("Series");
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertEquals(d1, d2);
    }

}
