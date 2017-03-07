package org.jfree.chart.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Represents the three options for adjusting a length:  expand, contract, and
 * no change.
 */
public final class LengthAdjustmentType implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -6097408511380545010L;
    
    /** NO_CHANGE. */
    public static final LengthAdjustmentType NO_CHANGE 
        = new LengthAdjustmentType("NO_CHANGE");

    /** EXPAND. */
    public static final LengthAdjustmentType EXPAND 
        = new LengthAdjustmentType("EXPAND");

    /** CONTRACT. */
    public static final LengthAdjustmentType CONTRACT 
        = new LengthAdjustmentType("CONTRACT");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private LengthAdjustmentType(final String name) {
        this.name = name;
    }

    /**
     * Returns a string representing the object.
     *
     * @return The string.
     */
    public String toString() {
        return this.name;
    }

    /**
     * Returns <code>true</code> if this object is equal to the specified 
     * object, and <code>false</code> otherwise.
     *
     * @param obj  the other object (<code>null</code> permitted).
     *
     * @return A boolean.
     */
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof LengthAdjustmentType)) {
            return false;
        }
        final LengthAdjustmentType that = (LengthAdjustmentType) obj;
        if (!this.name.equals(that.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return The hashcode
     */
    public int hashCode() {
        return this.name.hashCode();
    }

    /**
     * Ensures that serialization returns the unique instances.
     * 
     * @return The object.
     * 
     * @throws ObjectStreamException if there is a problem.
     */
    private Object readResolve() throws ObjectStreamException {
        if (this.equals(LengthAdjustmentType.NO_CHANGE)) {
            return LengthAdjustmentType.NO_CHANGE;
        }
        else if (this.equals(LengthAdjustmentType.EXPAND)) {
            return LengthAdjustmentType.EXPAND;
        }
        else if (this.equals(LengthAdjustmentType.CONTRACT)) {
            return LengthAdjustmentType.CONTRACT;
        }
        return null;
    }
    
}

