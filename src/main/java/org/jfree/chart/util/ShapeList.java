package org.jfree.chart.util;

import java.awt.Shape;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * A table of {@link Shape} objects.
 */
public class ShapeList extends AbstractObjectList {

    /**
     * Creates a new list.
     */
    public ShapeList() {
        super();
    }

    /**
     * Returns a {@link Shape} object from the list.
     *
     * @param index the index (zero-based).
     *
     * @return The object.
     */
    public Shape getShape(final int index) {
        return (Shape) get(index);
    }

    /**
     * Sets the {@link Shape} for an item in the list.  The list is expanded
     * if necessary.
     *
     * @param index  the index (zero-based).
     * @param shape  the {@link Shape}.
     */
    public void setShape(final int index, final Shape shape) {
        set(index, shape);
    }

    /**
     * Returns an independent copy of the list.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException if an item in the list does not
     *         support cloning.
     */
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Tests the list for equality with another object (typically also a list).
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShapeList)) {
            return false;
        }
        ShapeList that = (ShapeList) obj;
        int listSize = size();
        for (int i = 0; i < listSize; i++) {
           if (!ShapeUtils.equal((Shape) get(i), (Shape) that.get(i))) {
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
            final Shape shape = getShape(i);
            if (shape != null) {
                stream.writeInt(i);
                SerialUtils.writeShape(shape, stream);
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
                setShape(index, SerialUtils.readShape(stream));
            }
        }

    }

}


