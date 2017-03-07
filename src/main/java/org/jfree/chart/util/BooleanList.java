package org.jfree.chart.util;

/**
 * A list of <code>Boolean</code> objects.
 */
public class BooleanList extends AbstractObjectList {

    /** For serialization. */
    private static final long serialVersionUID = -8543170333219422042L;
    
    /**
     * Creates a new list.
     */
    public BooleanList() {
    }

    /**
     * Returns a {@link Boolean} from the list.
     *
     * @param index the index (zero-based).
     *
     * @return a {@link Boolean} from the list.
     */
    public Boolean getBoolean(final int index) {
        return (Boolean) get(index);
    }

    /**
     * Sets the value for an item in the list.  The list is expanded if 
     * necessary.
     *
     * @param index  the index (zero-based).
     * @param b  the boolean.
     */
    public void setBoolean(final int index, final Boolean b) {
        set(index, b);
    }

    /**
     * Tests the list for equality with another object (typically also a list).
     *
     * @param o  the other object.
     *
     * @return A boolean.
     */
    public boolean equals(final Object o) {

        if (o instanceof BooleanList) {
            return super.equals(o);
        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
     */
    public int hashCode() {
        return super.hashCode();
    }
}

