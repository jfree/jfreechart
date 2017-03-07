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
    public Paint getPaint(final int index) {
        return (Paint) get(index);
    }

    /**
     * Sets the {@link Paint} for an item in the list.  The list is expanded if necessary.
     *
     * @param index  the index (zero-based).
     * @param paint  the {@link Paint}.
     */
    public void setPaint(final int index, final Paint paint) {
        set(index, paint);
    }

    /**
     * Tests the list for equality with another object (typically also a list).
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(final Object obj) {
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
    private void writeObject(final ObjectOutputStream stream) throws IOException {

        stream.defaultWriteObject();
        final int count = size();
        stream.writeInt(count);
        for (int i = 0; i < count; i++) {
            final Paint paint = getPaint(i);
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
    private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {

        stream.defaultReadObject();
        final int count = stream.readInt();
        for (int i = 0; i < count; i++) {
            final int index = stream.readInt();
            if (index != -1) {
                setPaint(index, SerialUtils.readPaint(stream));
            }
        }
        
    }

}

