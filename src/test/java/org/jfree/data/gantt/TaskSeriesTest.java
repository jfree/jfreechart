/* ======================================================
 * JFreeChart : a chart library for the Java(tm) platform
 * ======================================================
 *
 * (C) Copyright 2000-present, by David Gilbert and Contributors.
 *
 * Project Info:  https://www.jfree.org/jfreechart/index.html
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
 * (C) Copyright 2004-present, by David Gilbert.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   Tracy Hiltbrand;
 *
 */

package org.jfree.data.gantt;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;

import javax.swing.event.EventListenerList;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link TaskSeries} class.
 */
public class TaskSeriesTest {

    @Test
    public void testEqualsHashCode() {
        EqualsVerifier.forClass(TaskSeries.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.TRANSIENT_FIELDS)
                .withRedefinedSuperclass()
                .withPrefabValues(EventListenerList.class,
                        new EventListenerList(),
                        new EventListenerList())
                .withPrefabValues(PropertyChangeSupport.class, new PropertyChangeSupport("A"), new PropertyChangeSupport("B"))
                .withPrefabValues(VetoableChangeSupport.class, new VetoableChangeSupport("A"), new VetoableChangeSupport("B"))
                .withPrefabValues(Map.class, new HashMap(), new HashMap(Collections.singletonMap("K", "V")))
                .withPrefabValues(Task.class,
                                  new Task("T1", new Date(1), new Date(2)),
                                  new Task("T2", new Date(3), new Date(4)))
                .withIgnoredFields("listeners")
                .withIgnoredFields("propertyChangeSupport")
                .withIgnoredFields("vetoableChangeSupport")
                .withIgnoredFields("notify")
                .verify();
    }
    
    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = new TaskSeries("S");
        s2.add(new Task("T1", new Date(1), new Date(2)));
        s2.add(new Task("T2", new Date(11), new Date(22)));
        assertEquals(s1, s2);
        assertEquals(s2, s1);

        s1.add(new Task("T3", new Date(22), new Date(33)));
        assertNotEquals(s1, s2);
        s2.add(new Task("T3", new Date(22), new Date(33)));
        assertEquals(s1, s2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = (TaskSeries) s1.clone();
        assertNotSame(s1, s2);
        assertSame(s1.getClass(), s2.getClass());
        assertEquals(s1, s2);

        // basic check for independence
        s1.add(new Task("T3", new Date(22), new Date(33)));
        assertNotEquals(s1, s2);
        s2.add(new Task("T3", new Date(22), new Date(33)));
        assertEquals(s1, s2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        TaskSeries s2 = TestUtils.serialised(s1);
        assertEquals(s1, s2);
    }

    /**
     * Some checks for the getTask() method.
     */
    @Test
    public void testGetTask() {
        TaskSeries s1 = new TaskSeries("S");
        s1.add(new Task("T1", new Date(1), new Date(2)));
        s1.add(new Task("T2", new Date(11), new Date(22)));
        Task t1 = s1.get("T1");
        assertEquals(t1, new Task("T1", new Date(1), new Date(2)));
        Task t2 = s1.get("T2");
        assertEquals(t2, new Task("T2", new Date(11), new Date(22)));
        Task t3 = s1.get("T3");
        assertNull(t3);
    }

}
