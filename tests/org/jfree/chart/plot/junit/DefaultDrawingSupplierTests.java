/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * --------------------------------
 * DefaultDrawingSupplierTests.java
 * --------------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: DefaultDrawingSupplierTests.java,v 1.1.2.1 2006/10/03 15:41:28 mungady Exp $
 *
 * Changes
 * -------
 * 25-Mar-2003 : Version 1 (DG);
 *
 */

package org.jfree.chart.plot.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
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

import org.jfree.chart.plot.DefaultDrawingSupplier;

/**
 * Tests for the {@link DefaultDrawingSupplier} class.
 */
public class DefaultDrawingSupplierTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(DefaultDrawingSupplierTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public DefaultDrawingSupplierTests(String name) {
        super(name);
    }
    
    /**
     * Check that the equals() method can distinguish all required fields.
     */
    public void testEquals() {
        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = new DefaultDrawingSupplier();
        assertTrue(r1.equals(r2));
        assertTrue(r2.equals(r1));
        
        // set up some objects...
        Paint[] ps1A = new Paint[] {Color.red, Color.blue};
        Paint[] ps2A = new Paint[] {Color.green, Color.yellow, Color.white};
        Paint[] ops1A = new Paint[] {Color.lightGray, Color.blue};
        Paint[] ops2A = new Paint[] {Color.black, Color.yellow, Color.cyan};
        Stroke[] ss1A = new Stroke[] {new BasicStroke(1.1f)};
        Stroke[] ss2A 
            = new Stroke[] {new BasicStroke(2.2f), new BasicStroke(3.3f)};
        Stroke[] oss1A = new Stroke[] {new BasicStroke(4.4f)};
        Stroke[] oss2A 
            = new Stroke[] {new BasicStroke(5.5f), new BasicStroke(6.6f)};
        Shape[] shapes1A = new Shape[] {
            new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0)
        };
        Shape[] shapes2A = new Shape[] {
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0),
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0)
        };
        Paint[] ps1B = new Paint[] {Color.red, Color.blue};
        Paint[] ps2B = new Paint[] {Color.green, Color.yellow, Color.white};
        Paint[] ops1B = new Paint[] {Color.lightGray, Color.blue};
        Paint[] ops2B = new Paint[] {Color.black, Color.yellow, Color.cyan};
        Stroke[] ss1B = new Stroke[] {new BasicStroke(1.1f)};
        Stroke[] ss2B 
            = new Stroke[] {new BasicStroke(2.2f), new BasicStroke(3.3f)};
        Stroke[] oss1B = new Stroke[] {new BasicStroke(4.4f)};
        Stroke[] oss2B 
            = new Stroke[] {new BasicStroke(5.5f), new BasicStroke(6.6f)};
        Shape[] shapes1B = new Shape[] {
            new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0)
        };
        Shape[] shapes2B = new Shape[] {
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0),
            new Rectangle2D.Double(2.0, 2.0, 2.0, 2.0)
        };
        
        r1 = new DefaultDrawingSupplier(ps1A, ops1A, ss1A, oss1A, shapes1A);
        r2 = new DefaultDrawingSupplier(ps1B, ops1B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        
        // paint sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops1A, ss1A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops1B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // outline paint sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss1A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss1B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // stroke sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss1A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss1B, shapes1B);
        assertTrue(r1.equals(r2));
        // outline stroke sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss2A, shapes1A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss2B, shapes1B);
        assertTrue(r1.equals(r2));
        // shape sequence
        r1 = new DefaultDrawingSupplier(ps2A, ops2A, ss2A, oss2A, shapes2A);
        assertFalse(r1.equals(r2));
        r2 = new DefaultDrawingSupplier(ps2B, ops2B, ss2B, oss2B, shapes2B);
        assertTrue(r1.equals(r2));
        
        // paint index
        r1.getNextPaint();
        assertFalse(r1.equals(r2));
        r2.getNextPaint();
        assertTrue(r1.equals(r2));
        
        // outline paint index
        r1.getNextOutlinePaint();
        assertFalse(r1.equals(r2));
        r2.getNextOutlinePaint();
        assertTrue(r1.equals(r2));
        
        // stroke index
        r1.getNextStroke();
        assertFalse(r1.equals(r2));
        r2.getNextStroke();
        assertTrue(r1.equals(r2));
        
        // outline stroke index
        r1.getNextOutlineStroke();
        assertFalse(r1.equals(r2));
        r2.getNextOutlineStroke();
        assertTrue(r1.equals(r2));
        
        // shape index
        r1.getNextShape();
        assertFalse(r1.equals(r2));
        r2.getNextShape();
        assertTrue(r1.equals(r2));
    }

    /**
     * Some basic checks for the clone() method.
     */
    public void testCloning() {
        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = null;
        try {
            r2 = (DefaultDrawingSupplier) r1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.err.println("Failed to clone.");
        }
        assertTrue(r1 != r2);
        assertTrue(r1.getClass() == r2.getClass());
        assertTrue(r1.equals(r2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DefaultDrawingSupplier r1 = new DefaultDrawingSupplier();
        DefaultDrawingSupplier r2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(r1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                new ByteArrayInputStream(buffer.toByteArray())
            );
            r2 = (DefaultDrawingSupplier) in.readObject();
            in.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(r1, r2);

    }

}
