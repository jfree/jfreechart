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
 * --------------
 * PaintList.java
 * --------------
 * (C) Copyright 2000-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributors:     -;
 */

package org.jfree.chart.util;

import java.awt.Paint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A table of {@link Paint} objects.
 */
public class PaintList extends AbstractObjectList {

    private static final long serialVersionUID = -708669381577938219L;

    /**
     * Creates a new list.
     */
    public PaintList() {
        super();
    }

    /**
     * Returns a {@link Paint} object from the list.
     *
     * @param index the index (zero-based).
     *
     * @return The object.
     */
    public Paint getPaint(int index) {
        return (Paint) get(index);
    }

    /**
     * Sets the {@link Paint} for an item in the list.  The list is expanded if necessary.
     *
     * @param index  the index (zero-based).
     * @param paint  the {@link Paint}.
     */
    public void setPaint(int index, Paint paint) {
        set(index, paint);
    }

    /**
     * Tests the list for equality with another object (typically also a list).
     *
     * @param obj  the other object ({@code null} permitted).
     *
     * @return A boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PaintList)) {
            return false;
        }
        PaintList that = (PaintList) obj;
        int listSize = size();
        for (int i = 0; i < listSize; i++) {
           if (!PaintUtils.equal(getPaint(i), that.getPaint(i))) {
               return false;
           }
        }
        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Provides serialization support.
     *
     * @param stream  the output stream.
     *
     * @throws IOException  if there is an I/O error.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {

        stream.defaultWriteObject();
        int count = size();
        stream.writeInt(count);
        for (int i = 0; i < count; i++) {
            Paint paint = getPaint(i);
            if (paint != null) {
                stream.writeInt(i);
                SerialUtils.writePaint(paint, stream);
            }
            else {
                stream.writeInt(-1);
            }
        }

    }
    
    /**
     * Provides serialization support.
     *
     * @param stream  the input stream.
     *
     * @throws IOException  if there is an I/O error.
     * @throws ClassNotFoundException  if there is a classpath problem.
     */
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {

        stream.defaultReadObject();
        int count = stream.readInt();
        for (int i = 0; i < count; i++) {
            int index = stream.readInt();
            if (index != -1) {
                setPaint(index, SerialUtils.readPaint(stream));
            }
        }
        
    }

}

