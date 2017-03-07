package org.jfree.chart.ui;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Used to indicate either the foreground or background layer.
 */
public final class Layer implements Serializable {

    /** For serialization. */
    private static final long serialVersionUID = -1470104570733183430L;
    
    /** Foreground. */
    public static final Layer FOREGROUND = new Layer("Layer.FOREGROUND");

    /** Background. */
    public static final Layer BACKGROUND = new Layer("Layer.BACKGROUND");

    /** The name. */
    private String name;

    /**
     * Private constructor.
     *
     * @param name  the name.
     */
    private Layer(final String name) {
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
     * @param o  the other object.
     *
     * @return A boolean.
     */
    public boolean equals(final Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Layer)) {
            return false;
        }

        final Layer layer = (Layer) o;
        if (!this.name.equals(layer.name)) {
            return false;
        }

        return true;

    }

    /**
     * Returns a hash code value for the object.
     *
     * @return the hashcode
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
        Layer result = null;
        if (this.equals(Layer.FOREGROUND)) {
            result = Layer.FOREGROUND;
        }
        else if (this.equals(Layer.BACKGROUND)) {
            result = Layer.BACKGROUND;
        }
        return result;
    }
    
}


