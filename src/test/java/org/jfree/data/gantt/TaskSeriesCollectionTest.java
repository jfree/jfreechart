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
 * -----------------------------
 * TaskSeriesCollectionTest.java
 * -----------------------------
 * (C) Copyright 2004-2022, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.data.gantt;

import java.util.Date;

import org.jfree.chart.TestUtils;
import org.jfree.chart.internal.CloneUtils;

import org.jfree.data.time.SimpleTimePeriod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link TaskSeriesCollection} class.
 */
public class TaskSeriesCollectionTest {

    /**
     * Creates a sample collection for testing purposes.
     *
     * @return A sample collection.
     */
    private TaskSeriesCollection<String, String> createCollection1() {
        TaskSeriesCollection<String, String> result = new TaskSeriesCollection<>();
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        s1.add(new Task("Task 1", new Date(1), new Date(2)));
        s1.add(new Task("Task 2", new Date(3), new Date(4)));
        result.add(s1);
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        s2.add(new Task("Task 3", new Date(5), new Date(6)));
        result.add(s2);
        return result;
    }

    /**
     * Creates a sample collection for testing purposes.
     *
     * @return A sample collection.
     */
    private TaskSeriesCollection<String, String> createCollection2() {
        TaskSeriesCollection<String, String> result = new TaskSeriesCollection<>();
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        Task t1 = new Task("Task 1", new Date(10), new Date(20));
        t1.addSubtask(new Task("Task 1A", new Date(10), new Date(15)));
        t1.addSubtask(new Task("Task 1B", new Date(16), new Date(20)));
        t1.setPercentComplete(0.10);
        s1.add(t1);
        Task t2 = new Task("Task 2", new Date(30), new Date(40));
        t2.addSubtask(new Task("Task 2A", new Date(30), new Date(35)));
        t2.addSubtask(new Task("Task 2B", new Date(36), new Date(40)));
        t2.setPercentComplete(0.20);
        s1.add(t2);
        result.add(s1);
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        Task t3 = new Task("Task 3", new Date(50), new Date(60));
        t3.addSubtask(new Task("Task 3A", new Date(50), new Date(55)));
        t3.addSubtask(new Task("Task 3B", new Date(56), new Date(60)));
        t3.setPercentComplete(0.30);
        s2.add(t3);
        result.add(s2);
        return result;
    }

    /**
     * Creates a sample collection for testing purposes.
     *
     * @return A sample collection.
     */
    private TaskSeriesCollection<String, String> createCollection3() {

        // define subtasks
        Task sub1 = new Task("Sub1", new Date(11), new Date(111));
        Task sub2 = new Task("Sub2", new Date(22), new Date(222));
        Task sub3 = new Task("Sub3", new Date(33), new Date(333));
        Task sub4 = new Task("Sub4", new Date(44), new Date(444));
        Task sub5 = new Task("Sub5", new Date(55), new Date(555));
        Task sub6 = new Task("Sub6", new Date(66), new Date(666));
        sub1.setPercentComplete(0.111);
        sub2.setPercentComplete(0.222);
        sub3.setPercentComplete(0.333);
        sub4.setPercentComplete(0.444);
        sub5.setPercentComplete(0.555);
        sub6.setPercentComplete(0.666);

        TaskSeries<String> seriesA = new TaskSeries<>("Series A");
        Task taskA1 = new Task("Task 1", new SimpleTimePeriod(new Date(100),
                new Date(200)));
        taskA1.setPercentComplete(0.1);
        taskA1.addSubtask(sub1);
        Task taskA2 = new Task("Task 2", new SimpleTimePeriod(new Date(220),
                new Date(350)));
        taskA2.setPercentComplete(0.2);
        taskA2.addSubtask(sub2);
        taskA2.addSubtask(sub3);
        seriesA.add(taskA1);
        seriesA.add(taskA2);

        TaskSeries<String> seriesB = new TaskSeries<>("Series B");
        // note that we don't define taskB1
        Task taskB2 = new Task("Task 2", new SimpleTimePeriod(new Date(2220),
                new Date(3350)));
        taskB2.setPercentComplete(0.3);
        taskB2.addSubtask(sub4);
        taskB2.addSubtask(sub5);
        taskB2.addSubtask(sub6);
        seriesB.add(taskB2);

        TaskSeriesCollection<String, String> tsc = new TaskSeriesCollection<>();
        tsc.add(seriesA);
        tsc.add(seriesB);

        return tsc;
    }

    /**
     * A test for the getSeriesCount() method.
     */
    @Test
    public void testGetSeriesCount() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(2, c.getSeriesCount());
    }

    /**
     * Some tests for the getSeriesKey() method.
     */
    @Test
    public void testGetSeriesKey() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals("S1", c.getSeriesKey(0));
        assertEquals("S2", c.getSeriesKey(1));
    }

    /**
     * A test for the getRowCount() method.
     */
    @Test
    public void testGetRowCount() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(2, c.getRowCount());
    }

    /**
     * Some tests for the getRowKey() method.
     */
    @Test
    public void testGetRowKey() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals("S1", c.getRowKey(0));
        assertEquals("S2", c.getRowKey(1));
    }

    /**
     * Some tests for the getRowIndex() method.
     */
    @Test
    public void testGetRowIndex() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(0, c.getRowIndex("S1"));
        assertEquals(1, c.getRowIndex("S2"));
    }

    /**
     * Some tests for the getValue() method.
     */
    @Test
    public void testGetValue() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(1L, c.getValue("S1", "Task 1"));
        assertEquals(3L, c.getValue("S1", "Task 2"));
        assertEquals(5L, c.getValue("S2", "Task 3"));

        assertEquals(1L, c.getValue(0, 0));
        assertEquals(3L, c.getValue(0, 1));
        assertNull(c.getValue(0, 2));
        assertNull(c.getValue(1, 0));
        assertNull(c.getValue(1, 1));
        assertEquals(5L, c.getValue(1, 2));
    }

    /**
     * Some tests for the getStartValue() method.
     */
    @Test
    public void testGetStartValue() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(1L, c.getStartValue("S1", "Task 1"));
        assertEquals(3L, c.getStartValue("S1", "Task 2"));
        assertEquals(5L, c.getStartValue("S2", "Task 3"));

        assertEquals(1L, c.getStartValue(0, 0));
        assertEquals(3L, c.getStartValue(0, 1));
        assertNull(c.getStartValue(0, 2));
        assertNull(c.getStartValue(1, 0));
        assertNull(c.getStartValue(1, 1));
        assertEquals(5L, c.getStartValue(1, 2));

        // test collection 3, which doesn't define all tasks in all series
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(100L, c3.getStartValue(0, 0));
        assertEquals(220L, c3.getStartValue(0, 1));
        assertNull(c3.getStartValue(1, 0));
        assertEquals(2220L, c3.getStartValue(1, 1));
    }

    /**
     * Some tests for the getStartValue() method for sub-intervals.
     */
    @Test
    public void testGetStartValue2() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(10L, c.getStartValue("S1", "Task 1", 0));
        assertEquals(16L, c.getStartValue("S1", "Task 1", 1));
        assertEquals(30L, c.getStartValue("S1", "Task 2", 0));
        assertEquals(36L, c.getStartValue("S1", "Task 2", 1));
        assertEquals(50L, c.getStartValue("S2", "Task 3", 0));
        assertEquals(56L, c.getStartValue("S2", "Task 3", 1));

        assertEquals(10L, c.getStartValue(0, 0, 0));
        assertEquals(16L, c.getStartValue(0, 0, 1));
        assertEquals(30L, c.getStartValue(0, 1, 0));
        assertEquals(36L, c.getStartValue(0, 1, 1));
        assertEquals(50L, c.getStartValue(1, 2, 0));
        assertEquals(56L, c.getStartValue(1, 2, 1));

        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(11L, c3.getStartValue(0, 0, 0));
        assertEquals(22L, c3.getStartValue(0, 1, 0));
        assertEquals(33L, c3.getStartValue(0, 1, 1));
        assertNull(c3.getStartValue(1, 0, 0));
        assertEquals(44L, c3.getStartValue(1, 1, 0));
        assertEquals(55L, c3.getStartValue(1, 1, 1));
        assertEquals(66L, c3.getStartValue(1, 1, 2));
    }

    /**
     * A check for a null task duration.
     */
    @Test
    public void testGetStartValue3() {
        TaskSeriesCollection<String, String> c = new TaskSeriesCollection<>();
        TaskSeries<String> s = new TaskSeries<>("Series 1");
        s.add(new Task("Task with null duration", null));
        c.add(s);
        Number millis = c.getStartValue("Series 1", "Task with null duration");
        assertNull(millis);
    }

    /**
     * Some tests for the getEndValue() method.
     */
    @Test
    public void testGetEndValue() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(2L, c.getEndValue("S1", "Task 1"));
        assertEquals(4L, c.getEndValue("S1", "Task 2"));
        assertEquals(6L, c.getEndValue("S2", "Task 3"));

        assertEquals(2L, c.getEndValue(0, 0));
        assertEquals(4L, c.getEndValue(0, 1));
        assertNull(c.getEndValue(0, 2));
        assertNull(c.getEndValue(1, 0));
        assertNull(c.getEndValue(1, 1));
        assertEquals(6L, c.getEndValue(1, 2));

        // test collection 3, which doesn't define all tasks in all series
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(200L, c3.getEndValue(0, 0));
        assertEquals(350L, c3.getEndValue(0, 1));
        assertNull(c3.getEndValue(1, 0));
        assertEquals(3350L, c3.getEndValue(1, 1));
    }

    /**
     * Some tests for the getEndValue() method for sub-intervals.
     */
    @Test
    public void testGetEndValue2() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(15L, c.getEndValue("S1", "Task 1", 0));
        assertEquals(20L, c.getEndValue("S1", "Task 1", 1));
        assertEquals(35L, c.getEndValue("S1", "Task 2", 0));
        assertEquals(40L, c.getEndValue("S1", "Task 2", 1));
        assertEquals(55L, c.getEndValue("S2", "Task 3", 0));
        assertEquals(60L, c.getEndValue("S2", "Task 3", 1));

        assertEquals(15L, c.getEndValue(0, 0, 0));
        assertEquals(20L, c.getEndValue(0, 0, 1));
        assertEquals(35L, c.getEndValue(0, 1, 0));
        assertEquals(40L, c.getEndValue(0, 1, 1));
        assertEquals(55L, c.getEndValue(1, 2, 0));
        assertEquals(60L, c.getEndValue(1, 2, 1));

        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(111L, c3.getEndValue(0, 0, 0));
        assertEquals(222L, c3.getEndValue(0, 1, 0));
        assertEquals(333L, c3.getEndValue(0, 1, 1));
        assertNull(c3.getEndValue(1, 0, 0));
        assertEquals(444L, c3.getEndValue(1, 1, 0));
        assertEquals(555L, c3.getEndValue(1, 1, 1));
        assertEquals(666L, c3.getEndValue(1, 1, 2));
    }

    /**
     * A check for a null task duration.
     */
    @Test
    public void testGetEndValue3() {
        TaskSeriesCollection<String, String> c = new TaskSeriesCollection<>();
        TaskSeries<String> s = new TaskSeries<>("Series 1");
        s.add(new Task("Task with null duration", null));
        c.add(s);
        Number millis = c.getEndValue("Series 1", "Task with null duration");
        assertNull(millis);
    }

    /**
     * Some tests for the getPercentComplete() method.
     */
    @Test
    public void testGetPercentComplete() {
        TaskSeriesCollection<String, String> c = createCollection2();
        assertEquals(0.10, c.getPercentComplete("S1", "Task 1"));
        assertEquals(0.20, c.getPercentComplete("S1", "Task 2"));
        assertEquals(0.30, c.getPercentComplete("S2", "Task 3"));

        assertEquals(0.10, c.getPercentComplete(0, 0));
        assertEquals(0.20, c.getPercentComplete(0, 1));
        assertNull(c.getPercentComplete(0, 2));
        assertNull(c.getPercentComplete(1, 0));
        assertNull(c.getPercentComplete(1, 1));
        assertEquals(0.30, c.getPercentComplete(1, 2));

        // test collection 3, which doesn't define all tasks in all series
        TaskSeriesCollection<String, String> c3 = createCollection3();
        assertEquals(0.1, c3.getPercentComplete(0, 0));
        assertEquals(0.2, c3.getPercentComplete(0, 1));
        assertNull(c3.getPercentComplete(1, 0));
        assertEquals(0.3, c3.getPercentComplete(1, 1));

        assertEquals(0.111, c3.getPercentComplete(0, 0, 0));

        assertEquals(0.222, c3.getPercentComplete(0, 1, 0));
        assertEquals(0.333, c3.getPercentComplete(0, 1, 1));

        assertEquals(0.444, c3.getPercentComplete(1, 1, 0));
        assertEquals(0.555, c3.getPercentComplete(1, 1, 1));
        assertEquals(0.666, c3.getPercentComplete(1, 1, 2));
    }

    /**
     * A test for the getColumnCount() method.
     */
    @Test
    public void testGetColumnCount() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(3, c.getColumnCount());
    }

    /**
     * Some tests for the getColumnKey() method.
     */
    @Test
    public void testGetColumnKey() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals("Task 1", c.getColumnKey(0));
        assertEquals("Task 2", c.getColumnKey(1));
        assertEquals("Task 3", c.getColumnKey(2));
    }

    /**
     * Some tests for the getColumnIndex() method.
     */
    @Test
    public void testGetColumnIndex() {
        TaskSeriesCollection<String, String> c = createCollection1();
        assertEquals(0, c.getColumnIndex("Task 1"));
        assertEquals(1, c.getColumnIndex("Task 2"));
        assertEquals(2, c.getColumnIndex("Task 3"));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {

        TaskSeries<String> s1 = new TaskSeries<>("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries<String> s2 = new TaskSeries<>("S");
        s2.add(new Task("T1", new Date(1), new Date(2)));
        s2.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeriesCollection<String, String> c1 = new TaskSeriesCollection<>();
        c1.add(s1);
        c1.add(s2);

        TaskSeries<String> s1b = new TaskSeries<>("S");
        s1b.add(new Task("T1", new Date(1), new Date(2)));
        s1b.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries<String> s2b = new TaskSeries<>("S");
        s2b.add(new Task("T1", new Date(1), new Date(2)));
        s2b.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeriesCollection<String, String> c2 = new TaskSeriesCollection<>();
        c2.add(s1b);
        c2.add(s2b);

        assertEquals(c1, c2);
        assertEquals(c2, c1);

    }

    /**
     * Confirm that cloning works.
     * @throws java.lang.CloneNotSupportedException
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        s2.add(new Task("T1", new Date(33), new Date(44)));
        s2.add(new Task("T2", new Date(55), new Date(66)));
        TaskSeriesCollection<String, String> c1 = new TaskSeriesCollection<>();
        c1.add(s1);
        c1.add(s2);

        TaskSeriesCollection<String, String> c2 = CloneUtils.clone(c1);
        assertNotSame(c1, c2);
        assertSame(c1.getClass(), c2.getClass());
        assertEquals(c1, c2);

        // basic check for independence
        s1.add(new Task("T3", new Date(21), new Date(33)));
        assertNotEquals(c1, c2);
        TaskSeries<String> series = c2.getSeries("S1");
        series.add(new Task("T3", new Date(21), new Date(33)));
        assertEquals(c1, c2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TaskSeries<String> s1 = new TaskSeries<>("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries<String> s2 = new TaskSeries<>("S");
        s2.add(new Task("T1", new Date(1), new Date(2)));
        s2.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeriesCollection<String, String> c1 = new TaskSeriesCollection<>();
        c1.add(s1);
        c1.add(s2);
        TaskSeriesCollection<String, String> c2 = TestUtils.serialised(c1);
        assertEquals(c1, c2);
    }

    /**
     * A test for bug report 697153.
     */
    @Test
    public void test697153() {

        TaskSeries<String> s1 = new TaskSeries<>("S1");
        s1.add(new Task("Task 1", new SimpleTimePeriod(new Date(),
                new Date())));
        s1.add(new Task("Task 2", new SimpleTimePeriod(new Date(),
                new Date())));
        s1.add(new Task("Task 3", new SimpleTimePeriod(new Date(),
                new Date())));

        TaskSeries<String> s2 = new TaskSeries<>("S2");
        s2.add(new Task("Task 2", new SimpleTimePeriod(new Date(),
                new Date())));
        s2.add(new Task("Task 3", new SimpleTimePeriod(new Date(),
                new Date())));
        s2.add(new Task("Task 4", new SimpleTimePeriod(new Date(),
                new Date())));

        TaskSeriesCollection<String, String> tsc = new TaskSeriesCollection<>();
        tsc.add(s1);
        tsc.add(s2);

        s1.removeAll();

        int taskCount = tsc.getColumnCount();

        assertEquals(3, taskCount);

    }

    /**
     * A test for bug report 800324.
     */
    @Test
    public void test800324() {
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        s1.add(new Task("Task 1", new SimpleTimePeriod(new Date(),
                new Date())));
        s1.add(new Task("Task 2", new SimpleTimePeriod(new Date(),
                new Date())));
        s1.add(new Task("Task 3", new SimpleTimePeriod(new Date(),
                new Date())));

        TaskSeriesCollection<String, String> tsc = new TaskSeriesCollection<>();
        tsc.add(s1);

        // these methods should throw an IndexOutOfBoundsException since the
        // column is too high...
        try {
            /* Number start = */ tsc.getStartValue(0, 3);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            /* Number end = */ tsc.getEndValue(0, 3);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            /* int count = */ tsc.getSubIntervalCount(0, 3);
            fail();
        }
        catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    /**
     * Some tests for the bug report 1099331.  We create a TaskSeriesCollection
     * with two series - the first series has two tasks, but the second has
     * only one.  The key is to ensure that the methods in TaskSeriesCollection
     * translate the index values to key values *before* accessing the tasks
     * in the series.
     */
    @Test
    public void testGetSubIntervalCount() {
        TaskSeriesCollection<String, String> tsc = createCollection3();
        assertEquals(1, tsc.getSubIntervalCount(0, 0));
        assertEquals(2, tsc.getSubIntervalCount(0, 1));
        assertEquals(0, tsc.getSubIntervalCount(1, 0));
        assertEquals(3, tsc.getSubIntervalCount(1, 1));
    }

    /**
     * Some basic tests for the getSeries() methods.
     */
    @Test
    public void testGetSeries() {
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        TaskSeries<String> s2 = new TaskSeries<>("S2");
        TaskSeriesCollection<String, String> c = new TaskSeriesCollection<>();
        c.add(s1);

        assertEquals(c.getSeries(0), s1);
        assertEquals(c.getSeries("S1"), s1);
        assertNull(c.getSeries("XX"));

        c.add(s2);
        assertEquals(c.getSeries(1), s2);
        assertEquals(c.getSeries("S2"), s2);

        boolean pass = false;
        try {
            c.getSeries(null);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    /**
     * Some basic checks for the remove() method.
     */
    @Test
    public void testRemove() {
        TaskSeriesCollection<String, String> c = new TaskSeriesCollection<>();
        TaskSeries<String> s1 = new TaskSeries<>("S1");
        c.add(s1);
        assertEquals("S1", c.getSeries(0).getKey());
        c.remove(0);
        assertEquals(0, c.getSeriesCount());
        c.add(s1);

        boolean pass = false;
        try {
            c.remove(-1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);

        pass = false;
        try {
            c.remove(1);
        }
        catch (IllegalArgumentException e) {
            pass = true;
        }
        assertTrue(pass);
    }

}
