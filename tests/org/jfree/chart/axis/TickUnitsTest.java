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
 * ------------------
 * TickUnitsTest.java
 * ------------------
 * (C) Copyright 2007-2013, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 02-Aug-2007 : Version 1 (DG);
 *
 */

package org.jfree.chart.axis;

import java.text.DecimalFormat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jfree.chart.TestUtilities;
import org.junit.Test;

/**
 * Tests for the {@link TickUnits} class.
 */
public class TickUnitsTest {

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    @Test
    public void testSerialization() {
        TickUnits t1 = new TickUnits();
        t1.add(new NumberTickUnit(10, new DecimalFormat("0.00")));
        TickUnits t2 = (TickUnits) TestUtilities.serialised(t1);
        assertEquals(t1, t2);
    }

    /**
     * Confirm that cloning works.
     */
    @Test
    public void testCloning() throws CloneNotSupportedException {
        TickUnits t1 = new TickUnits();
        t1.add(new NumberTickUnit(10, new DecimalFormat("0.00")));
        TickUnits t2 = (TickUnits) t1.clone();
        assertTrue(t1 != t2);
        assertTrue(t1.getClass() == t2.getClass());
        assertTrue(t1.equals(t2));
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    @Test
    public void testEquals() {
        TickUnits t1 = new TickUnits();
        t1.add(new NumberTickUnit(10, new DecimalFormat("0.00")));
        TickUnits t2 = new TickUnits();
        t2.add(new NumberTickUnit(10, new DecimalFormat("0.00")));
        assertTrue(t1.equals(t2));
        assertTrue(t2.equals(t1));
    }

}
