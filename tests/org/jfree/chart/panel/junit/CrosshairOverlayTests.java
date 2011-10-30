/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2011, by Object Refinery Limited and Contributors.
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
 * --------------------------
 * CrosshairOverlayTests.java
 * --------------------------
 * (C) Copyright 2009, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 10-Apr-2009 : Version 1 (DG);
 *
 */

package org.jfree.chart.panel.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;

/**
 * Tests for the {@link CrosshairOverlay} class.
 */
public class CrosshairOverlayTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CrosshairOverlayTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CrosshairOverlayTests(String name) {
        super(name);
    }

    /**
     * Confirm that the equals method can distinguish all the required fields.
     */
    public void testEquals() {
        CrosshairOverlay o1 = new CrosshairOverlay();
        CrosshairOverlay o2 = new CrosshairOverlay();
        assertEquals(o1, o2);
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {
        CrosshairOverlay o1 = new CrosshairOverlay();
        o1.addDomainCrosshair(new Crosshair(99.9));
        o1.addRangeCrosshair(new Crosshair(1.23, new GradientPaint(1.0f, 2.0f,
                Color.red, 3.0f, 4.0f, Color.blue), new BasicStroke(1.1f)));
        CrosshairOverlay o2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(o1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            o2 = (CrosshairOverlay) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(o1.equals(o2));
    }

    /**
     * Basic checks for cloning.
     */
    public void testCloning() {
        CrosshairOverlay o1 = new CrosshairOverlay();
        o1.addDomainCrosshair(new Crosshair(99.9));
        o1.addRangeCrosshair(new Crosshair(1.23, new GradientPaint(1.0f, 2.0f,
                Color.red, 3.0f, 4.0f, Color.blue), new BasicStroke(1.1f)));
        CrosshairOverlay o2 = null;
        try {
            o2 = (CrosshairOverlay) o1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        assertTrue(o1 != o2);
        assertTrue(o1.getClass() == o2.getClass());
        assertTrue(o1.equals(o2));

        o1.addDomainCrosshair(new Crosshair(3.21));
        o1.addRangeCrosshair(new Crosshair(4.32));
        assertFalse(o1.equals(o2));
    }

}
