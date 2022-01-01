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
 * --------------------
 * SerialUtilsTest.java
 * --------------------
 * (C) Copyright 2021-2022, by David Gilbert and Contributors.
 *
 * Original Author:  David Gilbert;
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.internal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.jfree.chart.TestUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link SerialUtils} class.
 */
public class SerialUtilsTest {

    @Test
    public void mapOfPaintSerialisation() {
        Map<Integer, Paint> m1 = new HashMap<>();
        m1.put(5, Color.BLUE);
        ObjectWithMapOfPaint obj1 = new ObjectWithMapOfPaint(m1);
        ObjectWithMapOfPaint obj2 = TestUtils.serialised(obj1);
        assertTrue(PaintUtils.equal(obj1.getMap(), obj2.getMap()));
        
        m1.put(11, new GradientPaint(1.0f, 2.0f, Color.RED, 3.0f, 4.0f, Color.GREEN));
        obj1 = new ObjectWithMapOfPaint(m1);
        obj2 = TestUtils.serialised(obj1);
        assertTrue(PaintUtils.equal(obj1.getMap(), obj2.getMap()));
    }

    @Test
    public void mapOfStrokeSerialisation() {
        Map<Integer, Stroke> m1 = new HashMap<>();
        m1.put(5, new BasicStroke(9.0f));
        ObjectWithMapOfStroke obj1 = new ObjectWithMapOfStroke(m1);
        ObjectWithMapOfStroke obj2 = TestUtils.serialised(obj1);
        assertEquals(obj1.getMap(), obj2.getMap());
    }

    static class ObjectWithMapOfPaint implements Serializable {
        
        private transient Map<Integer, Paint> map;
        
        public ObjectWithMapOfPaint(Map<Integer, Paint> map) {
            this.map = map;
        }
        
        public Map<Integer, Paint> getMap() {
            return this.map;
        }

        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtils.writeMapOfPaint(this.map, stream);
        }

        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.map = SerialUtils.readMapOfPaint(stream);
        }
    }

    static class ObjectWithMapOfStroke implements Serializable {
        
        private transient Map<Integer, Stroke> map;
        
        public ObjectWithMapOfStroke(Map<Integer, Stroke> map) {
            this.map = map;
        }
        
        public Map<Integer, Stroke> getMap() {
            return this.map;
        }

        private void writeObject(ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            SerialUtils.writeMapOfStroke(this.map, stream);
        }

        private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.map = SerialUtils.readMapOfStroke(stream);
        }
    }

}
