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
 * -----------------------
 * AbstractAnnotation.java
 * -----------------------
 * (C) Copyright 2009-2020, by Object Refinery Limited and Contributors.
 *
 * Original Author:  Peter Kolb (see patch 2809117);
 * Contributor(s):   -;
 *
 */

package org.jfree.chart.annotations;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.jfree.chart.event.AnnotationChangeEvent;
import org.jfree.chart.event.AnnotationChangeListener;

/**
 * An abstract implementation of the {@link Annotation} interface, containing a
 * mechanism for registering change listeners.
 *
 * @since 1.0.14
 */
public abstract class AbstractAnnotation implements Annotation, Cloneable,
        Serializable {

    /** Storage for registered change listeners. */
    private transient EventListenerList listenerList;

    /**
     * A flag that indicates whether listeners should be notified
     * about changes of the annotation.
     */
    private boolean notify = true;

    /**
     * Constructs an annotation.
     */
    protected AbstractAnnotation() {
        this.listenerList = new EventListenerList();
    }

    /**
     * Registers an object to receive notification of changes to the
     * annotation.
     *
     * @param listener  the object to register.
     *
     * @see #removeChangeListener(AnnotationChangeListener)
     */
    @Override
    public void addChangeListener(AnnotationChangeListener listener) {
        this.listenerList.add(AnnotationChangeListener.class, listener);
    }

    /**
     * Deregisters an object so that it no longer receives notification of
     * changes to the annotation.
     *
     * @param listener  the object to deregister.
     *
     * @see #addChangeListener(AnnotationChangeListener)
     */
    @Override
    public void removeChangeListener(AnnotationChangeListener listener) {
        this.listenerList.remove(AnnotationChangeListener.class, listener);
    }

    /**
     * Returns {@code true} if the specified object is registered with
     * the annotation as a listener.  Most applications won't need to call this
     * method, it exists mainly for use by unit testing code.
     *
     * @param listener  the listener.
     *
     * @return A boolean.
     *
     * @see #addChangeListener(AnnotationChangeListener)
     * @see #removeChangeListener(AnnotationChangeListener)
     */
    public boolean hasListener(EventListener listener) {
        List list = Arrays.asList(this.listenerList.getListenerList());
        return list.contains(listener);
    }

    /**
     * Notifies all registered listeners that the annotation has changed.
     *
     * @see #addChangeListener(AnnotationChangeListener)
     */
    protected void fireAnnotationChanged() {
        if (notify) {
            notifyListeners(new AnnotationChangeEvent(this, this));
        }
    }

    /**
     * Notifies all registered listeners that the annotation has changed.
     *
     * @param event  contains information about the event that triggered the
     *               notification.
     *
     * @see #addChangeListener(AnnotationChangeListener)
     * @see #removeChangeListener(AnnotationChangeListener)
     */
    protected void notifyListeners(AnnotationChangeEvent event) {

        Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == AnnotationChangeListener.class) {
                ((AnnotationChangeListener) listeners[i + 1]).annotationChanged(
                        event);
            }
        }

    }

    /**
     * Returns a flag that indicates whether listeners should be 
     * notified about changes to the annotation.
     *
     * @return  the flag.
     *
     * @see #setNotify(boolean)
     */
    public boolean getNotify(){
        return this.notify;
    }

    /**
     * Sets a flag that indicates whether listeners should be notified about
     * changes of an annotation.
     *
     * @param flag  the flag
     *
     * @see #getNotify()
     */
    public void setNotify(boolean flag){
        this.notify = flag;
        if (notify) {
            fireAnnotationChanged();
        }
    }

    /**
     * Returns a clone of the annotation. The cloned annotation will NOT 
     * include the {@link AnnotationChangeListener} references that have been
     * registered with this annotation.
     *
     * @return A clone.
     *
     * @throws CloneNotSupportedException  if the annotation does not support
     *                                     cloning.
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        AbstractAnnotation clone = (AbstractAnnotation) super.clone();
        clone.listenerList = new EventListenerList();
        return clone;
    }

    /**
     * Handles serialization.
     *
     * @param stream  the output stream.
     *
     * @throws IOException if there is an I/O problem.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    /**
     * Restores a serialized object.
     *
     * @param stream  the input stream.
     *
     * @throws IOException if there is an I/O problem.
     * @throws ClassNotFoundException if there is a problem loading a class.
     */
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.listenerList = new EventListenerList();
    }

}
