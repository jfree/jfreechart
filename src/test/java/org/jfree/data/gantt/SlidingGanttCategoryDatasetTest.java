/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
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
 * ------------------------------------
 * SlidingGanttCategoryDatasetTest.java
 * ------------------------------------
 * (C) Copyright 2008-present, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.data.gantt;

import java.util.Date;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.jfree.chart.TestUtils;
import org.jfree.data.general.SeriesChangeEvent;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link SlidingGanttCategoryDataset} class.
 */
public class SlidingGanttCategoryDatasetTest {

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(SlidingGanttCategoryDataset.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withRedefinedSuperclass()
                .verify();
    }

    /**
     * Some checks for the equals() method.
     */
    @Test
    public void testEquals() {
        TaskSeries s1 = new TaskSeries("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        s1.add(new Task("Task 3", new Date(20L), new Date(21L)));
        TaskSeriesCollection u1 = new TaskSeriesCollection();
        u1.add(s1);
        SlidingGanttCategoryDataset d1 = new SlidingGanttCategoryDataset(
                u1, 0, 5);
        TaskSeries s2 = new TaskSeries("Series");
        s2.add(new Task("Task 1", new Date(0L), new Date(1L)));
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        s2.add(new Task("Task 3", new Date(20L), new Date(21L)));
        TaskSeriesCollection u2 = new TaskSeriesCollection();
        u2.add(s2);
        SlidingGanttCategoryDataset d2 = new SlidingGanttCategoryDataset(
                u2, 0, 5);
        assertEquals(d1, d2);

        d1.setFirstCategoryIndex(1);
        assertNotEquals(d1, d2);
        d2.setFirstCategoryIndex(1);
        assertEquals(d1, d2);

        d1.setMaximumCategoryCount(99);
        assertNotEquals(d1, d2);
        d2.setMaximumCategoryCount(99);
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
        TaskSeries s1 = new TaskSeries("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        TaskSeriesCollection u1 = new TaskSeriesCollection();
        u1.add(s1);
        SlidingGanttCategoryDataset d1 = new SlidingGanttCategoryDataset(
                u1, 0, 5);
        SlidingGanttCategoryDataset d2 = (SlidingGanttCategoryDataset) 
                d1.clone();
        assertNotSame(d1, d2);
        assertSame(d1.getClass(), d2.getClass());
        assertEquals(d1, d2);

        // basic check for independence
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertNotEquals(d1, d2);
        TaskSeriesCollection u2
                = (TaskSeriesCollection) d2.getUnderlyingDataset();
        TaskSeries s2 = u2.getSeries("Series");
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));

        // equals checks the keys - make sure they get updated
        u1.seriesChanged(new SeriesChangeEvent(this));
        u2.seriesChanged(new SeriesChangeEvent(this));

        assertEquals(d1, d2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TaskSeries s1 = new TaskSeries("Series");
        s1.add(new Task("Task 1", new Date(0L), new Date(1L)));
        TaskSeriesCollection c1 = new TaskSeriesCollection();
        c1.add(s1);
        SlidingGanttCategoryDataset d1 = new SlidingGanttCategoryDataset(
                c1, 0, 5);
        SlidingGanttCategoryDataset d2 = TestUtils.serialised(d1);
        assertEquals(d1, d2);

        // basic check for independence
        s1.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertNotEquals(d1, d2);
        TaskSeriesCollection c2
                = (TaskSeriesCollection) d2.getUnderlyingDataset();
        TaskSeries s2 = c2.getSeries("Series");
        s2.add(new Task("Task 2", new Date(10L), new Date(11L)));
        assertEquals(d1, d2);
    }

}
